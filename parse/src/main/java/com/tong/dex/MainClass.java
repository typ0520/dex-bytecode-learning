package com.tong.dex;

import com.tong.dex.core.DexFile;
import java.io.*;

/**
 * https://source.android.com/devices/tech/dalvik/dex-format?hl=zh-cn
 * Created by tong on 2017/12/13.
 */
public class MainClass {
    public static void main(String[] args) throws Exception {
        File dexPath = new File("/Users/tong/Projects/dex-bytecode-learning/src/main/resources/classes.dex");
        DexFile dexFile = new DexFile(dexPath);
        //System.out.println(dexFile);
    }
}
