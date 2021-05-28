package com.ducanh.bai2.model;

import java.util.Date;

public class LichThi {
    private int id;
    private String name;
    private String ngay;
    private boolean thiViet;

    public LichThi(int id, String name, String ngay, boolean thiViet) {
        this.id = id;
        this.name = name;
        this.ngay = ngay;
        this.thiViet = thiViet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public boolean isThiViet() {
        return thiViet;
    }

    public void setThiViet(boolean thiViet) {
        this.thiViet = thiViet;
    }
}
