package com.qwerty.qroad.QRcode;

class Image{

}

public class QRcode{

    //В QR коде храним уникальный id здания, номер этажа, на котором находится QR, и номер капчи. Позже по этим
    // данным ищем карту и информацию для отображения местоположения

    private int house_id;
    private int floor;
    private int captcha_id;

    QRcode(Image image) {

    }

    public String toFilePath(){

        return "./"+(house_id+"")+"/"+(floor+"");

    }

}