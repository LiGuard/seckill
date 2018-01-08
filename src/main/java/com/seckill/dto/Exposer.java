package com.seckill.dto;

/**
 * 描述:
 * 暴露秒杀地址
 *
 * Aouthor ljw
 * create 2018-01-05 20:36
 */
public class Exposer {

    //是否开启秒杀
    private boolean exposer;

    private String md5;

    private long seckillId;
    //当前系统时间
    private long now;
    //秒杀开始时间
    private long start;
    //秒杀结束时间
    private long end;

    public Exposer(boolean exposer, String md5, long seckillId) {
        this.exposer = exposer;
        this.md5 = md5;
        this.seckillId = seckillId;
    }

    public Exposer(boolean exposer, long now, long start, long end, long seckillId) {
        this.exposer = exposer;
        this.now = now;
        this.start = start;
        this.end = end;
        this.seckillId = seckillId;
    }

    public Exposer(boolean exposer, long seckillId) {
        this.exposer = exposer;
        this.seckillId = seckillId;
    }

    public boolean isExposer() {
        return exposer;
    }

    public void setExposer(boolean exposer) {
        this.exposer = exposer;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Exposer{" + "exposer=" + exposer + ", md5='" + md5 + '\'' + ", seckillId=" + seckillId + ", now=" + now + ", start=" + start + ", end=" + end + '}';
    }
}
