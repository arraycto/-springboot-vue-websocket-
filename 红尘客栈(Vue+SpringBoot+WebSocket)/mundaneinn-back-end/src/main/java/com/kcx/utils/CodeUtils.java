package com.kcx.utils;

public class CodeUtils {
    public static String getCode(){
        //首先定义一个字符串，用于保存需要生成的数字字母库。
        String randomNumberSize = "0123456789qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
        //定义一个用于保存生成数字字母的变量
        String randomNumber = "";
        //(int)(Math.random()*62) 随机生成0-61的数
        //charAt（int index）方法通过括号中的整数来当作下表获取字符串中的单个字符
        for(int i = 5;i>0;i--) {
            randomNumber+=randomNumberSize.charAt((int)(Math.random()*62));
        }
        return randomNumber;
    }
}
