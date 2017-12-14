package com.tong.dex;

import com.tong.dex.core.DexFile;
import java.io.*;

/**
 * https://source.android.com/devices/tech/dalvik/dex-format?hl=zh-cn
 * Created by tong on 2017/12/13.
 */
public class MainClass {
    public static void main(String[] args) throws Exception {
//        URL url = MainClass.class.getClassLoader().getResource("");
//        File rootPath = new File(url.toURI());
//        System.out.println(rootPath);
//        File dexPath = new File(rootPath,"Hello.dex");
//        generateDex(dexPath,rootPath);

        File dexPath = new File("/Users/tong/Projects/dex-bytecode-learning/src/main/resources/Hello.dex");
        DexFile dexFile = new DexFile(dexPath);
        //System.out.println(dexFile);
    }

    private static void generateDex(File dexPath,File classesDir) throws InterruptedException, IOException {
        ProcessBuilder builder = new ProcessBuilder(
                "/Users/tong/Library/Android/sdk/build-tools/26.0.2/dx"
                ,"--dex"
                ,"--output=" + dexPath.getAbsolutePath()
                ,classesDir.getAbsolutePath());
        Process process = builder.start();
        int status = process.waitFor();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = null;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        System.out.println("status: " + status);
    }
}
