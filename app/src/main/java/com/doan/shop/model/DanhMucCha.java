package com.doan.shop.model;

public class DanhMucCha {
    private int id_danh_muc_cha;
    private String ten_danh_muc_cha;

    public DanhMucCha() {
    }

    public DanhMucCha(int id_danh_muc_cha, String ten_danh_muc_cha) {
        this.id_danh_muc_cha = id_danh_muc_cha;
        this.ten_danh_muc_cha = ten_danh_muc_cha;
    }

    public int getId_danh_muc_cha() {
        return id_danh_muc_cha;
    }

    public void setId_danh_muc_cha(int id_danh_muc_cha) {
        this.id_danh_muc_cha = id_danh_muc_cha;
    }

    public String getTen_danh_muc_cha() {
        return ten_danh_muc_cha;
    }

    public void setTen_danh_muc_cha(String ten_danh_muc_cha) {
        this.ten_danh_muc_cha = ten_danh_muc_cha;
    }
}
