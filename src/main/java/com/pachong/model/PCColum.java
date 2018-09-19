package com.pachong.model;

import java.util.Date;

public class PCColum {
    private Integer columId;

    private String columName;

    private Date addtime;

    private Integer orderVal;

    public Integer getColumId() {
        return columId;
    }

    public void setColumId(Integer columId) {
        this.columId = columId;
    }

    public String getColumName() {
        return columName;
    }

    public void setColumName(String columName) {
        this.columName = columName == null ? null : columName.trim();
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public Integer getOrderVal() {
        return orderVal;
    }

    public void setOrderVal(Integer orderVal) {
        this.orderVal = orderVal;
    }
}