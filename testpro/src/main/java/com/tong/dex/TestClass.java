package com.tong.dex;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tong on 2017/11/29.
 */
//@TestClassAnno("我是TestClassAnno")
public final class TestClass<T> implements Serializable,Runnable {
    //public static final long XXX = 100L;

    static {
        System.out.println("static{}");
    }

    private static volatile int m;
    //@Deprecated
    //@SerializedName("xx")
    private final int m1 = 2;
    private final int m2 = 3;
    private final Integer m3 = 4;

    private int m4;

    private List list = new ArrayList();

    public int inc() {
        return m + 1;
    }

    public int inc2() throws RuntimeException {
        int x;
        try {
            x = 1;
        } catch (Exception e) {
            x = 2;
            return x;
        } finally {
            x = 3;
        }


        return x;
    }

    public int inc(
            //@TestParamAnno("我是@TestParamAnno a")
                    int a,
            //@TestParamInvisibleAnno("我是@TestParamInvisibleAnno")
                    int b,
            //@TestParamAnno("我是@TestParamAnno c") @TestParam2Anno
                    int c) {
        return m + a;
    }

    @Override
    public void run() {
        class EncloseingClass {

        }
    }

    private static class Test2 {

    }
}
