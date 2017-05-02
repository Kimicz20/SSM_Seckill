package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by geek on 2017/4/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:Spring/Spring-dao.xml"})
public class SuccessKilledDaoTest {
    @Resource
    private SuccessKilledDao successKilledDao;
    @Test
    public void insertSuccessKilled() throws Exception {
        long id =1000;
        long phone =18106567009L;
        successKilledDao.insertSuccessKilled(id,phone);
    }

    @Test
    public void queryByIdWithSeckill() throws Exception {
        long id =1000;
        long phone =18106567009L;
        SuccessKilled success =successKilledDao.queryByIdWithSeckill(id,phone);
        System.out.println(success.getSeckill().getName());
    }

}