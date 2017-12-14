package com.tong.dex.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tong on 2017/12/13.
 */
public class DexFile {
    private final HeaderItem header;
    private final List<StringIdItem> stringIds = new ArrayList<>();
    private final List<Integer> typeIds = new ArrayList<>();
    private final List<ProtoId> protoIds = new ArrayList<>();
    private final List<FieldId> fieldIds = new ArrayList<>();
    private final List<MethodId> methodIds = new ArrayList<>();
    private final List<ClassDef> classDefs = new ArrayList<>();

    public DexFile(File file) throws Exception {
        ByteCodeStream stream = new ByteCodeStream(file);
        header = new HeaderItem(stream);

        for (int i = 0; i < header.getStringIdsSize(); i++) {
            StringIdItem item = new StringIdItem(stream);
            stringIds.add(item);
        }

        for (int i = 0; i < header.getTypeIdsSize(); i++) {
            int typeIndex = stream.readInt();
            typeIds.add(typeIndex);
            LogUtil.d("type: " + stringIds.get(typeIndex));
        }

        for (int i = 0; i < header.getProtoIdsSize(); i++) {
            int shortyIndex = stream.readInt();
            int returnTypeIndex = stream.readInt();
            int parametersOffset = stream.readInt();

            if (parametersOffset != 0) {
                ByteCodeStream streamCopy = stream.copy(parametersOffset);
                int paramSize = streamCopy.readInt();
                for (int j = 0; j < paramSize; j++) {
                    int type_index = streamCopy.readUnsignedShort();

                    LogUtil.d("proto type: " + stringIds.get(typeIds.get(type_index)));
                }
            }
            protoIds.add(new ProtoId(shortyIndex,returnTypeIndex,parametersOffset));
        }

        for (int i = 0; i < header.getFieldIdsSize(); i++) {
            int declaringClassIndex = stream.readUnsignedShort();
            int typeIndex = stream.readUnsignedShort();
            int nameIndex = stream.readInt();

            fieldIds.add(new FieldId(declaringClassIndex,typeIndex,nameIndex));
            LogUtil.d("declaringClassIndex: " + declaringClassIndex + " ,type: " + stringIds.get(typeIds.get(typeIndex)) + " ,name: " + stringIds.get(nameIndex));
        }

        for (int i = 0; i < header.getMethodIdsSize(); i++) {
            int declaringClassIndex = stream.readUnsignedShort();
            int protoIndex = stream.readUnsignedShort();
            int nameIndex = stream.readInt();

            methodIds.add(new MethodId(declaringClassIndex,protoIndex,nameIndex));
            LogUtil.d("declaringClassIndex: " + declaringClassIndex + " ,protoIndex: " + protoIds.get(protoIndex) + " ,name: " + stringIds.get(nameIndex));
        }

        for (int i = 0; i < header.getClassDefsSize(); i++) {
            int typeIndex = stream.readInt();
            int accessFlags = stream.readInt();
            int supertypeIndex = stream.readInt();
            int interfacesOffset = stream.readInt();
            int sourceFileIndex = stream.readInt();
            int annotationsOffset = stream.readInt();
            int classDataOffset = stream.readInt();
            int staticValuesOffset = stream.readInt();

            classDefs.add(new ClassDef(typeIndex, accessFlags, supertypeIndex,
                    interfacesOffset, sourceFileIndex, annotationsOffset, classDataOffset,
                    staticValuesOffset));
        }
    }

    public HeaderItem getHeader() {
        return header;
    }

    public List<StringIdItem> getStringIds() {
        return stringIds;
    }

    public List<Integer> getTypeIds() {
        return typeIds;
    }

    public List<ProtoId> getProtoIds() {
        return protoIds;
    }

    @Override
    public String toString() {
        return "DexFile{" +
                "header=" + header +
                ", stringIds=" + stringIds +
                ", typeIds=" + typeIds +
                '}';
    }
}


