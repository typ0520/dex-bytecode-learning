package com.tong.dextest.core;

import com.tong.dextest.MainClass;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by tong on 2017/12/1.
 */
public class ByteCodeStream implements ByteInput,ByteOutput {
    private final byte[] buffer;
    private int offset;

    public ByteCodeStream(Class clazz) {
        try {
            this.buffer = getClassBytes(clazz);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public ByteCodeStream(File file) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            FileInputStream fis = new FileInputStream(file);
            int len = -1;
            byte[] array = new byte[1024];

            while ((len = fis.read(array)) != -1) {
                bos.write(array,0,len);
            }
            this.buffer = bos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ByteCodeStream(byte[] buffer) {
        this.buffer = buffer;
    }

    public ByteCodeStream(byte[] buffer, int offset) {
        this.buffer = buffer;
        this.offset = offset;
    }

    public byte readByte() {
        byte result = buffer[offset];
        offset += 1;
        return result;
    }

    /**
     * class的字节序为Big-Endian(高位在前)
     * @return
     */
    public int readUnsignedByte() {
        int result = ((int) buffer[offset]) & 0xff;
        offset += 1;
        return result;
    }

    /**
     * class的字节序为Big-Endian(高位在前)
     * @return
     */
    public int readUnsignedShort() {
        int result = (((buffer[offset + 1] & 0xFF) << 8) | (buffer[offset] & 0xFF)) & 0xffff;
        offset += 2;
        return result;
    }

    /**
     * class的字节序为Big-Endian(高位在前)
     * @return
     */
    public long readUnsignedInt() {
        return ((long)(readInt())) & 0xffffffffL;
    }

    public int readInt() {
        int result = ((buffer[offset + 3] & 0xFF) << 24) | ((buffer[offset + 2] & 0xFF) << 16) | ((buffer[offset + 1] & 0xFF) << 8) | (buffer[offset] & 0xFF);
        offset += 4;
        return result;
    }

    public ByteCodeStream copy(int offset) {
        return new ByteCodeStream(buffer,offset);
    }


    /**
     * 读取字节数组
     * @param length
     * @return
     */
    public byte[] readBytes(int length) {
        byte[] result = new byte[length];
        System.arraycopy(buffer,offset,result,0,length);
        offset += length;
        return result;
    }

    public int getOffset() {
        return offset;
    }

    /**
     * 获取class的字节码
     * @param clazz
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public static byte[] getClassBytes(Class clazz) throws IOException, URISyntaxException {
        URL url = MainClass.class.getClassLoader().getResource("");
        File rootPath = new File(url.toURI());
        File classFile = new File(rootPath,clazz.getName().replaceAll("\\.",File.separator) + ".class");
        System.out.println("加载字节码: " + classFile);

        FileInputStream fis = new FileInputStream(classFile);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        try {
            while ((len = fis.read(buffer)) != -1) {
                bos.write(buffer,0,len);
            }
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
        return bos.toByteArray();
    }

    @Override
    public void writeByte(int i) {

    }

    public int readUleb128() {
        return Leb128.readUnsignedLeb128(this);
    }

    public int readUleb128p1() {
        return Leb128.readUnsignedLeb128(this) - 1;
    }

    public int readSleb128() {
        return Leb128.readSignedLeb128(this);
    }
}
