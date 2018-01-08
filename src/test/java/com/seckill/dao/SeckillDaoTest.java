package com.seckill.dao;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.seckill.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @outhor ljw
 * @create 2018-01-05 15:36
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

    @Autowired
    private SeckillDao seckillDao;
    @Test
    public void reduceNumber() throws Exception {

        int i = seckillDao.reduceNumber(1000l, new Date());
        System.out.println(i);
    }

    @Test
    public void queryById() throws Exception {
        long seckillId = 1000;
        Seckill seckill = seckillDao.queryById(seckillId);
        System.out.print(seckill);

    }

    @Test
    public void queryAll() throws Exception {
        List<Seckill> seckills = seckillDao.queryAll(0, 100);
        for (Seckill seckill:seckills){
            System.out.println(seckill);
        }
    }

}