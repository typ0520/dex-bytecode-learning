package com.tong.dextest.core;

/**
 * Created by tong on 2017/12/1.
 */
public class LogUtil {
    private static boolean debug = true;

    public static void d(Object msg) {
        if (!debug) {
            return;
        }
        System.out.println(msg.toString());
    }

    public static void v(Object msg) {
        System.out.println(msg);
    }
}
