package com.tong.dextest;

import com.google.gson.annotations.SerializedName;

public class Hello {
    @SerializedName("哈哈")
	private int mm;
	
    public static void MyPrint(String str)
    {
        System.out.printf(str + "\r\n");
    }
    
    public static void main(String[] argc)
    {
        MyPrint("nihao, shijie");
        System.out.println("Hello World!");
    }
}