package com.seckill.service.impl;

import com.seckill.dao.SeckillDao;
import com.seckill.dao.SuccessKilledDao;
import com.seckill.dto.Exposer;
import com.seckill.dto.SuccessExecution;
import com.seckill.entity.Seckill;
import com.seckill.entity.SuccessKilled;
import com.seckill.enums.SeckillStatEnum;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillException;
import com.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import sun.org.mozilla.javascript.internal.ast.TryStatement;

import java.util.Date;
import java.util.List;


/**
 * 描述:
 * 秒杀业务逻辑实现类
 *
 * Aouthor ljw
 * create 2018-01-05 17:48
 */
@Service
public class SeckillServiceImpl implements SeckillService{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    private final String salt = "23423?/';';''][]";

    

    /**
     * Author: ljw
     * Description:
     * param offet
     * param limit
     * Date: 12:43 2018/1/6
     */
    @Override
    public List<Seckill> queryAllSeckill(int offet, int limit) {

        return seckillDao.queryAll(offet, limit);
    }

    /**
     * Author: ljw
     * Description: 查询单个秒杀
     * param seckillId
     * Date: 20:42 2018/1/5
     */
    @Override
    public Seckill querySeckillById(long seckillId) {

        return seckillDao.queryById(seckillId);
    }

    /**
     * Author: ljw
     * Description: 暴露秒杀接口
     * param seckillId
     * Date: 20:43 2018/1/5
     */
    @Override
    public Exposer exportSeckillUrl(long seckillId) {

        Seckill seckill = seckillDao.queryById(seckillId);

        if(seckill == null){
            return new Exposer(false,seckillId);
        }

        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        //系统当前时间
        Date nowTime = new Date();

        if(nowTime.getTime() > endTime.getTime() || nowTime.getTime() < startTime.getTime()){
            return new Exposer(false,nowTime.getTime(), startTime.getTime(), endTime.getTime(), seckillId);
        }
        String md5 = getMD5(seckillId);

        return new Exposer(true, md5, seckillId);
    }



    private String getMD5(long seckillId){
        String base = seckillId + "/" + salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    /**
     * Author: ljw
     * Description: 执行秒杀
     * param seckillId
     * param userPhone
     * param md5
     * Date: 21:14 2018/1/5
     */
    @Override
    @Transactional
    public SuccessExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException, RepeatKillException, SeckillCloseException {
        
        if(md5 == null || !md5.equals(getMD5(seckillId))){
            throw new SeckillException("秒杀数据被篡改");
        }

        //减库存
        //当前时间
        Date nowTime = new Date();
        try {
            int updateCount = seckillDao.reduceNumber(seckillId, nowTime);

            if(updateCount <= 0){
                throw new SeckillCloseException("秒杀已经关闭");
            } else {
                int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
                if (insertCount <= 0){
                    throw new RepeatKillException("重复秒杀");
                } else {
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SuccessExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
                }
            }
        } catch (SeckillCloseException e1) {
            throw e1;
        } catch (RepeatKillException e2) {
            throw e2;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SeckillException("秒杀业务异常" + e.getMessage());
        }

    }
}
