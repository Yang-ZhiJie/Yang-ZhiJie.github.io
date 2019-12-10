package com.example.util;

public class JsonResult<T> {
    private Boolean status;
    private String code;
    private T data;

    //成功, 没有数据
    public JsonResult() {
        this.data = null;
        this.code = "-1";
        this.status = true;
    }

    //成功，有数据
    public JsonResult(T data) {
        this.data = data;
        this.status = true;
        this.code = "-1";
    }

    //成功或失败，决于status
    public JsonResult(T data, boolean status) {
        this.data = data;
        this.status = status;
        this.code = "-1";
    }

    public JsonResult(T data, boolean status, String code) {
        this.data = data;
        this.status = status;
        this.code = code;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
