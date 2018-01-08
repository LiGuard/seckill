package com.seckill.service;

import com.seckill.dto.Exposer;
import com.seckill.dto.SuccessExecution;
import com.seckill.entity.Seckill;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.PortableInterceptor.LOCATION_FORWARD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * 描述: ${DESCRIPTION}
 * Aouthor ljw
 * createTime 2018-01-06 13:43
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml",
                       "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;


    @Test
    public void queryAllSeckill() throws Exception {
        List<Seckill> seckills = seckillService.queryAllSeckill(0, 30);
        logger.info("list={}", seckills);


    }

    @Test
    public void querySeckillById() throws Exception {
        long seckillId = 1000L;
        Seckill seckill = seckillService.querySeckillById(seckillId);
        logger.info("seckill={}", seckill);
    }

    @Test
    public void exportSeckillUrl() throws Exception {
        long seckillId = 1000L;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        logger.info("exposer={}", exposer);
    }

    @Test
    public void executeSeckill() throws Exception {
        long seckillId = 1000L;
        String md5 = "8b1dfb829ea413d058b4a8ab73a525a2";  //TODO
        long userPhone = 18211130279L;
        SuccessExecution successExecution = seckillService.executeSeckill(seckillId, userPhone, md5);
        logger.info("successExecution={}", successExecution);
    }

    @Test
    public void testSeckillLogic() throws Exception {
        long seckillId = 1001L;
        long userPhone = 18211130279L;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        if(exposer.isExposer()) {
            logger.info("exposer={}", exposer);
            try {
                SuccessExecution successExecution = seckillService.executeSeckill(exposer.getSeckillId(), userPhone, exposer.getMd5());
                logger.info("successExecution={}", successExecution);
            } catch (SeckillCloseException e){
                logger.error(e.getMessage());
            } catch (RepeatKillException e) {
                logger.error(e.getMessage());
            }
        } else {
            logger.warn("exposer={}", exposer);
        }

    }


}