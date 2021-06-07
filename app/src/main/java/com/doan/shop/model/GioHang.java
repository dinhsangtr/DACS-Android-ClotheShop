package com.doan.shop.model;

public class GioHang {
    private int soluong;
    private double tonggia;
    private SanPham sanpham;

    public GioHang() {
    }

    public GioHang(int soluong, double tonggia, SanPham sanpham) {
        this.soluong = soluong;
        this.tonggia = tonggia;
        this.sanpham = sanpham;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public double getTonggia() {
        return tonggia;
    }

    public void setTonggia(double tonggia) {
        this.tonggia = tonggia;
    }

    public SanPham getSanpham() {
        return sanpham;
    }

    public void setSanpham(SanPham sanpham) {
        this.sanpham = sanpham;
    }
}
