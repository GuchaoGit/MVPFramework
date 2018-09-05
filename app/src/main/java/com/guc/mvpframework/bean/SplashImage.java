package com.guc.mvpframework.bean;

/**
 * Created by guc on 2018/9/5.
 * 描述：
 */
public class SplashImage {
    private String text;//图片出处
    private String img;//图片地址

    public String getText() {
        return text;
    }

    public String getImg() {
        return img;
    }

    @Override
    public String toString() {
        return "SplashImage{" +
                "text='" + text + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
