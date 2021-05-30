package com.doan.shop.model;

public class Slider {
    public int id_slider;
    public String link;
    public String mo_ta_1;
    public String mo_ta_2;

    public Slider() {
    }

    public Slider(int id_slider, String link, String mo_ta_1, String mo_ta_2) {
        this.id_slider = id_slider;
        this.link = link;
        this.mo_ta_1 = mo_ta_1;
        this.mo_ta_2 = mo_ta_2;

    }

    public int getId_slider() {
        return id_slider;
    }

    public void setId_slider(int id_slider) {
        this.id_slider = id_slider;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getMo_ta_1() {
        return mo_ta_1;
    }

    public void setMo_ta_1(String mo_ta_1) {
        this.mo_ta_1 = mo_ta_1;
    }

    public String getMo_ta_2() {
        return mo_ta_2;
    }

    public void setMo_ta_2(String mo_ta_2) {
        this.mo_ta_2 = mo_ta_2;
    }
}
