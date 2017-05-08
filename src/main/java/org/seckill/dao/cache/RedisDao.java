package org.seckill.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.seckill.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by geek on 2017/5/8.
 */

/**
 * 在get/set时对于对象的实例化操作
 */
public class RedisDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private JedisPool jedisPool;

    //序列化的模式
    private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);

    public RedisDao(String ip,int port) {
        this.jedisPool = new JedisPool(ip,port);
    }

    /**
     * 从redis缓存中获取 对象
     * 需要先将对象序列化 采用goole序列化工作protostuff
     * @param seckillId
     * @return
     */
    public Seckill getSeckill(long seckillId){
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckillId:"+seckillId;
                //自定义序列化操作
                byte[] bytes = jedis.get(key.getBytes());
                //取到则反序列化生成对象
                if(bytes != null){
                    //构造空对象
                    Seckill seckill = schema.newMessage();
                    ProtobufIOUtil.mergeFrom(bytes,seckill,schema);
                    return seckill;
                }
            } catch (Exception e) {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }

    public String putSeckill(Seckill seckill){
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                //构造key
                String key = "seckillId:"+seckill.getSeckillId();
                //将对象按照序列化模式生成字节数组
                byte[] bytes = ProtobufIOUtil.toByteArray(seckill,schema,
                        LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));

                String result = jedis.setex(key.getBytes(),60*60,bytes);
                return result;
            } catch (Exception e) {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }
}
