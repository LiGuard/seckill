package com.seckill.dao;

import com.seckill.entity.Seckill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @outhor ljw
 * @create 2018-01-04 14:12
 */
public interface SeckillDao {

    /**
     *
     * @param seckillId
     * @param killTime
     * @return
     */
    int reduceNumber(@Param(value = "seckillId") long seckillId,@Param(value = "killTime") Date killTime);

    /**
     *
     * @param seckillId
     * @return
     */
    Seckill queryById(long seckillId);

    /**
     *
     * @param offet
     * @param limit
     * @return
     */
    List<Seckill> queryAll(@Param(value = "offet") int offet, @Param(value = "limit") int limit);
   
}
