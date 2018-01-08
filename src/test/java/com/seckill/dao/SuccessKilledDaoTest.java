package com.seckill.dao;

import com.seckill.entity.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @outhor ljw
 * @create 2018-01-05 17:20
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {

    @Autowired
    private SuccessKilledDao successKilledDao;
    @Test
    public void insertSuccessKilled() throws Exception {
        long seckillId = 1002L;
        long userPhone = 18211130279L;
        int i = successKilledDao.insertSuccessKilled(seckillId, userPhone);
        System.out.println(i);
    }

    @Test
    public void queryByIdWithSeckill() throws Exception {
        long seckillId = 1002L;
        long userPhone = 18211130279L;
        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());
    }

}