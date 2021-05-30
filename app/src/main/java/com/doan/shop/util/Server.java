package com.doan.shop.util;

public class Server {
    public static String localhost = "192.168.1.6";
    public static String port = "8099";

    //Trang Chu
    public static String URL_ImgSanPham = "http://" + localhost + ":" + port + "/ShopSpringMVC/assets/user/images/sanpham/";
    public static String URL_ImgSlider = "http://" + localhost + ":" + port + "/ShopSpringMVC/assets/user/images/slider/";
    public static String URL_SP_Moi = "http://" + localhost + ":" + port + "/ShopSpringMVC/api/san-pham-moi";
    public static String URL_SP_NoiBat = "http://" + localhost + ":" + port + "/ShopSpringMVC/api/san-pham-noi-bat";
    public static String URL_Slider = "http://" + localhost + ":" + port + "/ShopSpringMVC/api/slider";

    //Danh Muc
    public static String URL_DMucCha = "http://" + localhost + ":" + port + "/ShopSpringMVC/api/danh-muc-cha";

}
