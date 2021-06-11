package com.doan.shop.model;

import java.sql.Date;

public class User {

    private int id_user;
    private String username;
    private String password;
    private String ho_ten;
    private String ngay_sinh;
    private String dia_chi;
    private String email;
    private String sdt;

    public User() {
    }

    public User(int id_user, String username, String password, String ho_ten, String ngay_sinh, String dia_chi, String email, String sdt) {
        this.id_user = id_user;
        this.username = username;
        this.password = password;
        this.ho_ten = ho_ten;
        this.ngay_sinh = ngay_sinh;
        this.dia_chi = dia_chi;
        this.email = email;
        this.sdt = sdt;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHo_ten() {
        return ho_ten;
    }

    public void setHo_ten(String ho_ten) {
        this.ho_ten = ho_ten;
    }

    public String getNgay_sinh() {
        return ngay_sinh;
    }

    public void setNgay_sinh(String ngay_sinh) {
        this.ngay_sinh = ngay_sinh;
    }

    public String getDia_chi() {
        return dia_chi;
    }

    public void setDia_chi(String dia_chi) {
        this.dia_chi = dia_chi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
}
