package com.doan.shop.model;

public class DanhMuc {
    private int id_danh_muc;
    private String ten_danh_muc;
    private String hinh_anh;

    public DanhMuc() {
    }

    public DanhMuc(int id_danh_muc, String ten_danh_muc, String hinh_anh) {
        this.id_danh_muc = id_danh_muc;
        this.ten_danh_muc = ten_danh_muc;
        this.hinh_anh = hinh_anh;
    }

    public int getId_danh_muc() {
        return id_danh_muc;
    }

    public void setId_danh_muc(int id_danh_muc) {
        this.id_danh_muc = id_danh_muc;
    }

    public String getTen_danh_muc() {
        return ten_danh_muc;
    }

    public void setTen_danh_muc(String ten_danh_muc) {
        this.ten_danh_muc = ten_danh_muc;
    }

    public String getHinh_anh() {
        return hinh_anh;
    }

    public void setHinh_anh(String hinh_anh) {
        this.hinh_anh = hinh_anh;
    }
}
