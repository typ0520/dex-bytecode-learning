package com.tong.dex.core;

import java.io.UTFDataFormatException;

/**
 * Created by tong on 2017/12/13.
 */
public class StringIdItem {
    /**
     * 从文件开头到此项的字符串数据的偏移量。该偏移量应该是到 data 区段中某个位置的偏移量，而数据应采用下文中“string_data_item”指定的格式。没有偏移量对齐要求。
     */
    private final long stringDataOff;
    private final int expectedLength;
    public String value;

    public StringIdItem(ByteCodeStream stream) throws UTFDataFormatException {
        this.stringDataOff = stream.readUnsignedInt();

        ByteCodeStream streamCopy = stream.copy((int) stringDataOff);
        this.expectedLength = streamCopy.readUleb128();

        LogUtil.d("stringDataOff: " + stringDataOff + " ,expectedLength: " + expectedLength);
        value = Mutf8.decode(streamCopy, new char[expectedLength]);
        LogUtil.d(value);
    }

    @Override
    public String toString() {
        return "StringIdItem{" +
                "value='" + value + '\'' +
                '}';
    }
}
