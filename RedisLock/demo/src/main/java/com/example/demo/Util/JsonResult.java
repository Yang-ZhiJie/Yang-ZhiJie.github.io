package com.example.demo.Util;

public class JsonResult<T> {
    //成功与否
    private boolean status;
    //提示数据
    private T data;
    //数字状态
    private String code;

    //成功或失败，决于status
    public JsonResult(T data, boolean status) {
        this.data = data;
        this.status = status;
        this.code = "-1";
    }
    public JsonResult(boolean status) {
        this.data = data;
        this.status = status;
        this.code = "-1";
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
