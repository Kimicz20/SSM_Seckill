package org.seckill.exception;

/**
 * Created by geek on 2017/4/27.
 */
public class RepeatKillException extends  SeckillException {

    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
