package com.seckill.service;

import com.seckill.dto.Exposer;
import com.seckill.dto.SuccessExecution;
import com.seckill.entity.Seckill;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillException;

import java.util.List;

/**
 * 描述: 秒杀业务逻辑
 * Aouthor ljw
 * create 2018-01-05 17:47
 * @author ljw
 */
public interface SeckillService {

    /**
     * 查询所有秒杀列表
     * param offet
     * param limit
     * return
     */
    List<Seckill> queryAllSeckill(int offet, int limit);

    /**
     * 查询单个秒杀列表
     * param seckillId
     * return
     */
    Seckill querySeckillById(long seckillId);

    /**
     * 秒杀开启时输出秒杀接口地址,
     * param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀
     * param seckillId
     * param userPhone
     * param md5
     */
    SuccessExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException, RepeatKillException, SeckillCloseException;

}
