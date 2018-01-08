package com.seckill.exception;

/**
 * 描述:
 * 重复秒杀异常
 *
 * @outhor ljw
 * @create 2018-01-05 20:59
 */
public class RepeatKillException extends SeckillException{

    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
