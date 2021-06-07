package com.doan.shop.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class SanPham implements Serializable {
    public int id_san_pham;
    public String ten_san_pham;
    public int id_danh_muc;
    public int id_thuong_hieu;
    public String mo_ta;
    public String kich_thuoc;
    public double gia_ban;
    public double gia_khuyen_mai;
    public String hinh_anh_sp;
    public int id_mau_sac;
    public String ten_mau;
    public String code;
    public String hinh_anh_sp_ms;

    public SanPham() {
    }

    public SanPham(int id_san_pham, String ten_san_pham,
                   int id_danh_muc, int id_thuong_hieu,
                   String mo_ta, String kich_thuoc,
                   double gia_ban, double gia_khuyen_mai,
                   String hinh_anh_sp, int id_mau_sac,
                   String ten_mau, String code,
                   String hinh_anh_sp_ms) {
        this.id_san_pham = id_san_pham;
        this.ten_san_pham = ten_san_pham;
        this.id_danh_muc = id_danh_muc;
        this.id_thuong_hieu = id_thuong_hieu;
        this.mo_ta = mo_ta;
        this.kich_thuoc = kich_thuoc;
        this.gia_ban = gia_ban;
        this.gia_khuyen_mai = gia_khuyen_mai;
        this.hinh_anh_sp = hinh_anh_sp;
        this.id_mau_sac = id_mau_sac;
        this.ten_mau = ten_mau;
        this.code = code;
        this.hinh_anh_sp_ms = hinh_anh_sp_ms;
    }

    public int getId_san_pham() {
        return id_san_pham;
    }

    public void setId_san_pham(int id_san_pham) {
        this.id_san_pham = id_san_pham;
    }

    public String getTen_san_pham() {
        return ten_san_pham;
    }

    public void setTen_san_pham(String ten_san_pham) {
        this.ten_san_pham = ten_san_pham;
    }

    public int getId_danh_muc() {
        return id_danh_muc;
    }

    public void setId_danh_muc(int id_danh_muc) {
        this.id_danh_muc = id_danh_muc;
    }

    public int getId_thuong_hieu() {
        return id_thuong_hieu;
    }

    public void setId_thuong_hieu(int id_thuong_hieu) {
        this.id_thuong_hieu = id_thuong_hieu;
    }

    public String getMo_ta() {
        return mo_ta;
    }

    public void setMo_ta(String mo_ta) {
        this.mo_ta = mo_ta;
    }

    public String getKich_thuoc() {
        return kich_thuoc;
    }

    public void setKich_thuoc(String kich_thuoc) {
        this.kich_thuoc = kich_thuoc;
    }

    public double getGia_ban() {
        return gia_ban;
    }

    public void setGia_ban(double gia_ban) {
        this.gia_ban = gia_ban;
    }

    public double getGia_khuyen_mai() {
        return gia_khuyen_mai;
    }

    public void setGia_khuyen_mai(double gia_khuyen_mai) {
        this.gia_khuyen_mai = gia_khuyen_mai;
    }

    public String getHinh_anh_sp() {
        return hinh_anh_sp;
    }

    public void setHinh_anh_sp(String hinh_anh_sp) {
        this.hinh_anh_sp = hinh_anh_sp;
    }

    public int getId_mau_sac() {
        return id_mau_sac;
    }

    public void setId_mau_sac(int id_mau_sac) {
        this.id_mau_sac = id_mau_sac;
    }

    public String getTen_mau() {
        return ten_mau;
    }

    public void setTen_mau(String ten_mau) {
        this.ten_mau = ten_mau;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getHinh_anh_sp_ms() {
        return hinh_anh_sp_ms;
    }

    public void setHinh_anh_sp_ms(String hinh_anh_sp_ms) {
        this.hinh_anh_sp_ms = hinh_anh_sp_ms;
    }
}
