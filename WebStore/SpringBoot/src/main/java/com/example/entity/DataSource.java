package com.example.entity;

public class DataSource {
    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DataSource{" +
                "data=" + data +
                '}';
    }
}
