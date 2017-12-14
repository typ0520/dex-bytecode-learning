package com.tong.dex.core;

/**
 * Created by tong on 2017/12/14.
 */
public class ProtoId {
    public int shortyIndex;
    public int returnTypeIndex;
    public int parametersOffset;

    public ProtoId(int shortyIndex, int returnTypeIndex, int parametersOffset) {
        this.shortyIndex = shortyIndex;
        this.returnTypeIndex = returnTypeIndex;
        this.parametersOffset = parametersOffset;
    }

    @Override
    public String toString() {
        return "ProtoId{" +
                "shortyIndex=" + shortyIndex +
                ", returnTypeIndex=" + returnTypeIndex +
                ", parametersOffset=" + parametersOffset +
                '}';
    }
}
