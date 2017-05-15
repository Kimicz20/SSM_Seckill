package org.seckill.service;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;

import java.util.List;

/**
 * Created by geek on 2017/4/27.
 */
public interface SeckillService {

    /**
     * 查询所有的秒杀商品
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * 查询单个的秒杀商品
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * 是否暴露秒杀接口
     * @param seckillId
     * @return
     */
    Exposer exportSeckillURL(long seckillId);

    /**
     * 执行秒杀操作
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone , String md5)
        throws SeckillException,SeckillCloseException,RepeatKillException;

    /**
     * 执行秒杀操作
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SeckillExecution executeSeckillProcedure(long seckillId, long userPhone , String md5)
            throws SeckillException,SeckillCloseException,RepeatKillException;
}
