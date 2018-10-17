package com.huowolf.dto;

import java.util.List;

/**
 * Created by huowolf on 2018/10/13.
 */
public class TableResponse<E> {
    private Integer code=0;
    private String msg="";
    private Integer count;
    private List<E> data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<E> getData() {
        return data;
    }

    public void setData(List<E> data) {
        this.data = data;
    }
}
