package com.tong.dextest.core;

import java.util.Arrays;

/**
 * Created by tong on 2017/12/13.
 */
public class HeaderItem {
    /**
     * 小端
     */
    public static final long ENDIAN_CONSTANT = 0x12345678L;
    /**
     * 大段
     */
    public static final long REVERSE_ENDIAN_CONSTANT = 0x78563412L;

    /**
     * 魔法值
     */
    private final byte[] magic;
    /**
     * 文件剩余内容（除 magic 和此字段之外的所有内容）的 adler32 校验和；用于检测文件损坏情况
     */
    private final long checksum;
    /**
     * 文件剩余内容（除 magic、checksum 和此字段之外的所有内容）的 SHA-1 签名（哈希）；用于对文件进行唯一标识
     */
    private final String signature;
    /**
     * 整个文件（包括标头）的大小，以字节为单位
     */
    private final long fileSize;
    /**
     * 标头（整个区段）的大小，以字节为单位。这一项允许至少一定程度的向后/向前兼容性，而不必让格式失效。
     */
    private final long headerSize;

    /**
     * 字节序标记。更多详情，请参阅上文中“ENDIAN_CONSTANT 和 REVERSE_ENDIAN_CONSTANT”下的讨论。
     */
    private final long endianTag;
    /**
     * 链接区段的大小；如果此文件未进行静态链接，则该值为 0
     */
    private final long linkSize;
    /**
     * 从文件开头到链接区段的偏移量；如果 link_size == 0，则该值为 0。该偏移量（如果为非零值）应该是到 link_data 区段的偏移量。本文档尚未指定此处所指的数据格式；此标头字段（和之前的字段）会被保留为钩子，以供运行时实现使用。
     */
    private final long linkOff;
    /**
     * 从文件开头到映射项的偏移量。该偏移量（必须为非零）应该是到 data 区段的偏移量，而数据应采用下文中“map_list”指定的格式。
     */
    private final long mapOff;
    /**
     * 字符串标识符列表中的字符串数量
     */
    private final long stringIdsSize;
    /**
     * 从文件开头到字符串标识符列表的偏移量；如果 string_ids_size == 0（不可否认是一种奇怪的极端情况），则该值为 0。该偏移量（如果为非零值）应该是到 string_ids 区段开头的偏移量。
     */
    private final long stringIdsOff;
    /**
     * 类型标识符列表中的元素数量，最多为 65535
     */
    private final long typeIdsSize;
    /**
     * 从文件开头到类型标识符列表的偏移量；如果 type_ids_size == 0（不可否认是一种奇怪的极端情况），则该值为 0。该偏移量（如果为非零值）应该是到 type_ids 区段开头的偏移量。
     */
    private final long typeIdsOff;
    /**
     * 原型标识符列表中的元素数量，最多为 65535
     */
    private final long protoIdsSize;
    /**
     * 从文件开头到原型标识符列表的偏移量；如果 proto_ids_size == 0（不可否认是一种奇怪的极端情况），则该值为 0。该偏移量（如果为非零值）应该是到 proto_ids 区段开头的偏移量。
     */
    private final long protoIdsOff;
    /**
     * 字段标识符列表中的元素数量
     */
    private final long fieldIdsSize;
    /**
     * 从文件开头到字段标识符列表的偏移量；如果 field_ids_size == 0，则该值为 0。该偏移量（如果为非零值）应该是到 field_ids 区段开头的偏移量。
     */
    private final long fieldIdsOff;
    /**
     * 方法标识符列表中的元素数量
     */
    private final long methodIdsSize;
    /**
     * 从文件开头到方法标识符列表的偏移量；如果 method_ids_size == 0，则该值为 0。该偏移量（如果为非零值）应该是到 method_ids 区段开头的偏移量。
     */
    private final long methodIdsOff;
    /**
     * 类定义列表中的元素数量
     */
    private final long classDefsSize;
    /**
     * 从文件开头到类定义列表的偏移量；如果 class_defs_size == 0（不可否认是一种奇怪的极端情况），则该值为 0。该偏移量（如果为非零值）应该是到 class_defs 区段开头的偏移量。
     */
    private final long classDefsOff;

    /**
     * data 区段的大小（以字节为单位）。该数值必须是 sizeof(uint) 的偶数倍。
     */
    private final long dataSize;
    /**
     * 从文件开头到 data 区段开头的偏移量。
     */
    private final long dataOff;

    public HeaderItem(ByteCodeStream stream) {
        //header item
        this.magic = stream.readBytes(8);
        LogUtil.d("magic: " + new String(magic));
        this.checksum = stream.readUnsignedInt();
        LogUtil.d("checksum: " + Long.toHexString(checksum));

        byte[] signatureBytes = stream.readBytes(20);
        this.signature = new String(signatureBytes);
        LogUtil.d("signature: " + signature);

        this.fileSize = stream.readUnsignedInt();
        LogUtil.d("fileSize: " + fileSize);

        this.headerSize = stream.readUnsignedInt();
        this.endianTag = stream.readUnsignedInt();
        LogUtil.d("endianTag: " + Long.toHexString(endianTag));

        this.linkSize = stream.readUnsignedInt();
        this.linkOff = stream.readUnsignedInt();
        this.mapOff = stream.readUnsignedInt();
        this.stringIdsSize = stream.readUnsignedInt();
        this.stringIdsOff = stream.readUnsignedInt();
        this.typeIdsSize = stream.readUnsignedInt();
        this.typeIdsOff = stream.readUnsignedInt();
        this.protoIdsSize = stream.readUnsignedInt();
        this.protoIdsOff = stream.readUnsignedInt();
        this.fieldIdsSize = stream.readUnsignedInt();
        this.fieldIdsOff = stream.readUnsignedInt();
        this.methodIdsSize = stream.readUnsignedInt();
        this.methodIdsOff = stream.readUnsignedInt();
        this.classDefsSize = stream.readUnsignedInt();
        this.classDefsOff = stream.readUnsignedInt();
        this.dataSize = stream.readUnsignedInt();
        this.dataOff = stream.readUnsignedInt();

        LogUtil.d("dataSize: " + Long.toHexString(dataSize));
        LogUtil.d("dataOff: " + Long.toHexString(dataOff));
    }

    public byte[] getMagic() {
        return magic;
    }

    public long getChecksum() {
        return checksum;
    }

    public String getSignature() {
        return signature;
    }

    public long getFileSize() {
        return fileSize;
    }

    public long getHeaderSize() {
        return headerSize;
    }

    public long getEndianTag() {
        return endianTag;
    }

    public long getLinkSize() {
        return linkSize;
    }

    public long getLinkOff() {
        return linkOff;
    }

    public long getMapOff() {
        return mapOff;
    }

    public long getStringIdsSize() {
        return stringIdsSize;
    }

    public long getStringIdsOff() {
        return stringIdsOff;
    }

    public long getTypeIdsSize() {
        return typeIdsSize;
    }

    public long getTypeIdsOff() {
        return typeIdsOff;
    }

    public long getProtoIdsSize() {
        return protoIdsSize;
    }

    public long getProtoIdsOff() {
        return protoIdsOff;
    }

    public long getFieldIdsSize() {
        return fieldIdsSize;
    }

    public long getFieldIdsOff() {
        return fieldIdsOff;
    }

    public long getMethodIdsSize() {
        return methodIdsSize;
    }

    public long getMethodIdsOff() {
        return methodIdsOff;
    }

    public long getClassDefsSize() {
        return classDefsSize;
    }

    public long getClassDefsOff() {
        return classDefsOff;
    }

    public long getDataSize() {
        return dataSize;
    }

    public long getDataOff() {
        return dataOff;
    }

    @Override
    public String toString() {
        return "HeaderItem{" +
                "magic=" + Arrays.toString(magic) +
                ", checksum=" + checksum +
                ", signature='" + signature + '\'' +
                ", fileSize=" + fileSize +
                ", headerSize=" + headerSize +
                ", endianTag=" + endianTag +
                ", linkSize=" + linkSize +
                ", linkOff=" + linkOff +
                ", mapOff=" + mapOff +
                ", stringIdsSize=" + stringIdsSize +
                ", stringIdsOff=" + stringIdsOff +
                ", typeIdsSize=" + typeIdsSize +
                ", typeIdsOff=" + typeIdsOff +
                ", protoIdsSize=" + protoIdsSize +
                ", protoIdsOff=" + protoIdsOff +
                ", fieldIdsSize=" + fieldIdsSize +
                ", fieldIdsOff=" + fieldIdsOff +
                ", methodIdsSize=" + methodIdsSize +
                ", methodIdsOff=" + methodIdsOff +
                ", classDefsSize=" + classDefsSize +
                ", classDefsOff=" + classDefsOff +
                ", dataSize=" + dataSize +
                ", dataOff=" + dataOff +
                '}';
    }
}
