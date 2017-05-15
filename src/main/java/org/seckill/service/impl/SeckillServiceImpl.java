package org.seckill.service.impl;

import org.apache.commons.collections.MapUtils;
import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dao.cache.RedisDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStatEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by geek on 2017/4/27.
 */
@Service
public class SeckillServiceImpl implements SeckillService {

    //日志对象
    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    @Autowired
    private RedisDao redisDao;

    private final String slat ="12iafdhai9812300-nckjnasodhj";

    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0,4);
    }

    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    public Exposer exportSeckillURL(long seckillId) {
        //redis 后端优化:超时一致性维护
        //1.访问redis缓存
        Seckill seckill;
        seckill = redisDao.getSeckill(seckillId);
        if(seckill == null){
            //2.访问数据库
            seckill = seckillDao.queryById(seckillId);
            if(seckill == null){
                return new Exposer(false,seckillId);
            }else{
                //3.存入redis缓存
                redisDao.putSeckill(seckill);
            }
        }

        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();

        //系统时间
        Date now = new Date();
        //超过或者没有到秒杀时间
        if(now.getTime() < startTime.getTime()
                || now.getTime() >endTime.getTime()){
            return new Exposer(false,seckillId,now.getTime(),startTime.getTime(),endTime.getTime());
        }
        //转换URL
        String md5 = getMD5(seckillId);
        return new Exposer(true,md5,seckillId);
    }

    @Transactional
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, SeckillCloseException, RepeatKillException {
        if(md5 == null || !md5.equals(getMD5(seckillId))){
            throw new SeckillException("seckill data rewrite");
        }
        try {
            //执行秒杀
            int insert = successKilledDao.insertSuccessKilled(seckillId,userPhone);
            if(insert <=0 ){
                //重复秒杀
                throw new RepeatKillException("repeate seckill");
            }else{
                int update = seckillDao.reduceNumber(seckillId,new Date());
                if(update <=0){
                    //秒杀已经结束
                    throw new SeckillCloseException("seckill is close");
                }else{
                    //秒杀成功
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId,userPhone);
                    return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS,successKilled);
                }
            }
        }catch(SeckillCloseException e1){
            throw e1;
        }catch(RepeatKillException e2){
            throw e2;
        }catch (Exception e){
            logger.error(e.toString(),e);
            throw new SeckillException("seckill inner error:"+e.getMessage());
        }
    }

    public SeckillExecution executeSeckillProcedure(long seckillId, long userPhone, String md5){
        if(md5 == null || !md5.equals(getMD5(seckillId))){
            return new SeckillExecution(seckillId,SeckillStatEnum.DATA_REWRITE);
        }else{
            Map<String,Object> map = new HashMap<String,Object>();
            //  传递数据到存储过程
            map.put("seckillId",seckillId);
            map.put("phone",userPhone);
            map.put("killTime",new Date());
            map.put("result",null);
            try {
                // 执行存储过程
                seckillDao.killByProcedure(map);
                int result = MapUtils.getInteger(map,"result",-2);
                if(result == 1){
                    // 秒杀成功查询
                    SuccessKilled sk = successKilledDao.
                            queryByIdWithSeckill(seckillId,userPhone);
                    return new SeckillExecution(seckillId,SeckillStatEnum.SUCCESS,sk);
                }else{
                    // 错误信息返回
                    return new SeckillExecution(seckillId,SeckillStatEnum.stateOf(result));
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new SeckillExecution(seckillId,SeckillStatEnum.INNER_ERROR);
            }
        }
    }

    private String getMD5(long seckillId){
        String base = seckillId+"/"+slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }
}
