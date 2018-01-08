package com.seckill.dao;

import com.seckill.entity.SuccessKilled;
import org.apache.ibatis.annotations.Param;

/**
 * 描述:
 *
 * @outhor ljw
 * @create 2018-01-04 14:56
 */
public interface SuccessKilledDao {


    /**
     *
     * @param seckillId
     * @param userPhone
     * @return
     */
    int insertSuccessKilled(@Param(value = "seckillId") long seckillId,@Param(value = "userPhone") long userPhone);

    /**
     *
     * @param seckillId
     * @return
     */
    SuccessKilled queryByIdWithSeckill(@Param(value = "seckillId") long seckillId,@Param(value = "userPhone") long userPhone);


}
