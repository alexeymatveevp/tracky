package com.crispysoft.tracky.dto;

/**
 * User: david
 * Date: 27.09.2016
 * Time: 23:50
 */
public class Result<T> {
    boolean success;
    T data;

    public Result() {
    }

    public Result(boolean success) {
        this.success = success;
    }

    public Result(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
