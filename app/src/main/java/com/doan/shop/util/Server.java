package com.doan.shop.util;

public class Server {
    public static String localhost = "192.168.1.7";
    public static String port = "8099";

    //Trang Chu
    public static String URL_ImgSanPham = "http://" + localhost + ":" + port + "/ShopSpringMVC/assets/user/images/sanpham/";
    public static String URL_ImgSlider = "http://" + localhost + ":" + port + "/ShopSpringMVC/assets/user/images/slider/";
    public static String URL_SP_Moi = "http://" + localhost + ":" + port + "/ShopSpringMVC/api/san-pham-moi";
    public static String URL_SP_NoiBat = "http://" + localhost + ":" + port + "/ShopSpringMVC/api/san-pham-noi-bat";
    public static String URL_Slider = "http://" + localhost + ":" + port + "/ShopSpringMVC/api/slider";

    //Danh Muc
    public static String URL_ImgDMucCha = "http://" + localhost + ":" + port + "/ShopSpringMVC/assets/user/images/danhmuc/";
    public static String URL_DMucCha = "http://" + localhost + ":" + port + "/ShopSpringMVC/api/danh-muc-cha";
    public static String URL_DMucCon = "http://" + localhost + ":" + port + "/ShopSpringMVC/api/danh-muc/";
    public static String URL_SP = "http://" + localhost + ":" + port + "/ShopSpringMVC/api/san-pham-theo-loai/";
    public static String URL_SPTheoLoai = "http://" + localhost + ":" + port + "/ShopSpringMVC/api/san-pham-theo-loai?id=";
    public static String URL_SPLienQuan = "http://" + localhost + ":" + port + "/ShopSpringMVC/api/san-pham-lien-quan";
    //Gio Hang

    //Thong Bao

    //User
    public static String URL_CheckUser = "http://" + localhost + ":" + port + "/ShopSpringMVC/api/usercheck";
    public static String URL_CheckUserBool = "http://" + localhost + ":" + port + "/ShopSpringMVC/api/usercheckbool";
    public static String URL_UpdateInfo = "http://" + localhost + ":" + port + "/ShopSpringMVC/api/updateinfo";
    public static String URL_InsertUser = "http://" + localhost + ":" + port + "/ShopSpringMVC/api/insertuser";
    public static String URL_ChangePW = "http://" + localhost + ":" + port + "/ShopSpringMVC/api/changepw";
}
