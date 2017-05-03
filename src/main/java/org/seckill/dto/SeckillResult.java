package org.seckill.dto;

/**
 * Created by geek on 2017/5/3.
 */
public class SeckillResult<T> {

    private boolean result;

    private T data;

    private String error;

    public SeckillResult(boolean result, T data) {
        this.result = result;
        this.data = data;
    }

    public SeckillResult(boolean result, String error) {
        this.result = result;
        this.error = error;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
