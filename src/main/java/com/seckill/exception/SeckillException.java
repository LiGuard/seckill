package com.seckill.exception;

/**
 * 描述:
 * 秒杀相关业务的异常
 *
 * @outhor ljw
 * @create 2018-01-05 21:05
 */
public class SeckillException extends RuntimeException {

    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
