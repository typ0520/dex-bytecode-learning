package com.tong.dextest.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tong on 2017/12/13.
 */
public class DexFile {
    public static final short SECTION_TYPE_HEADER = 0x0000;
    public static final short SECTION_TYPE_STRINGIDS = 0x0001;
    public static final short SECTION_TYPE_TYPEIDS = 0x0002;
    public static final short SECTION_TYPE_PROTOIDS = 0x0003;
    public static final short SECTION_TYPE_FIELDIDS = 0x0004;
    public static final short SECTION_TYPE_METHODIDS = 0x0005;
    public static final short SECTION_TYPE_CLASSDEFS = 0x0006;
    public static final short SECTION_TYPE_MAPLIST = 0x1000;
    public static final short SECTION_TYPE_TYPELISTS = 0x1001;
    public static final short SECTION_TYPE_ANNOTATIONSETREFLISTS = 0x1002;
    public static final short SECTION_TYPE_ANNOTATIONSETS = 0x1003;
    public static final short SECTION_TYPE_CLASSDATA = 0x2000;
    public static final short SECTION_TYPE_CODES = 0x2001;
    public static final short SECTION_TYPE_STRINGDATAS = 0x2002;
    public static final short SECTION_TYPE_DEBUGINFOS = 0x2003;
    public static final short SECTION_TYPE_ANNOTATIONS = 0x2004;
    public static final short SECTION_TYPE_ENCODEDARRAYS = 0x2005;
    public static final short SECTION_TYPE_ANNOTATIONSDIRECTORIES = 0x2006;

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

        ByteCodeStream streamCopy = stream.copy((int) header.getMapOff());
        int mapSize = streamCopy.readInt();
        LogUtil.d("mapSize: " + mapSize);

        for (int i = 0; i < mapSize; i++) {
            int type = streamCopy.readUnsignedShort();
            streamCopy.readUnsignedShort();
            int size = streamCopy.readInt();
            int offset = streamCopy.readInt();

            LogUtil.d("type: " + Integer.toHexString(type) + " ,size: " + size + " ,offset: " + offset);

            ByteCodeStream sectionStream = stream.copy(offset);

            if (type == SECTION_TYPE_HEADER) {

            }
            else if (type == SECTION_TYPE_STRINGIDS) {

            }
            else if (type == SECTION_TYPE_TYPEIDS) {

            }
            else if (type == SECTION_TYPE_PROTOIDS) {

            }
            else if (type == SECTION_TYPE_FIELDIDS) {

            }
            else if (type == SECTION_TYPE_METHODIDS) {

            }
            else if (type == SECTION_TYPE_CLASSDEFS) {

            }
            else if (type == SECTION_TYPE_MAPLIST) {

            }
            else if (type == SECTION_TYPE_TYPELISTS) {

            }
            else if (type == SECTION_TYPE_ANNOTATIONSETREFLISTS) {
                //annotation_set_ref_list
                int annoSize = sectionStream.readInt();
                LogUtil.d("anno size: " + annoSize);

                List<AnnotationSetRefList> annotationSetRefLists = new ArrayList<>();

                for (int j = 0; j < annoSize; j++) {
                    int annotation_set_ref_item_off = sectionStream.readInt();
                    ByteCodeStream annotation_set_item_stream = stream.copy(annotation_set_ref_item_off);

                    int[] annotationSetRefItems = new int[annotation_set_item_stream.readInt()];
                    for (int k = 0; k < annotationSetRefItems.length; k++) {
                        annotationSetRefItems[k] = annotation_set_item_stream.readInt();
                    }

                    annotationSetRefLists.add(new AnnotationSetRefList(annotationSetRefItems));
                }

                for (AnnotationSetRefList annotationSetRefList : annotationSetRefLists) {
                    for (int j = 0; j < annotationSetRefList.annotationSetRefItems.length; j++) {
                        int annotations_off = annotationSetRefList.annotationSetRefItems[j];

                        ByteCodeStream annoStream = stream.copy(annotations_off);

                        int visibility = annoStream.readUnsignedByte();
                        LogUtil.d("visibility: " + visibility);
                    }
                }
            }
            else if (type == SECTION_TYPE_ANNOTATIONSETS) {

            }
            else if (type == SECTION_TYPE_CLASSDATA) {

            }
            else if (type == SECTION_TYPE_CODES) {

            }
            else if (type == SECTION_TYPE_STRINGDATAS) {

            }
            else if (type == SECTION_TYPE_DEBUGINFOS) {

            }
            else if (type == SECTION_TYPE_ANNOTATIONS) {

            }
            else if (type == SECTION_TYPE_ENCODEDARRAYS) {

            }
            else if (type == SECTION_TYPE_ANNOTATIONSDIRECTORIES) {
                int classAnnotationsOffset = sectionStream.readInt();
                int fieldsSize = sectionStream.readInt();
                int methodsSize = sectionStream.readInt();
                int parameterListSize = sectionStream.readInt();

                int[][] fieldAnnotations = new int[fieldsSize][2];
                for (int j = 0; j < fieldsSize; j++) {
                    fieldAnnotations[j][0] = sectionStream.readInt();
                    fieldAnnotations[j][1] = sectionStream.readInt();
                }

                int[][] methodAnnotations = new int[methodsSize][2];
                for (int j = 0; j < methodsSize; j++) {
                    methodAnnotations[j][0] = sectionStream.readInt();
                    methodAnnotations[j][1] = sectionStream.readInt();
                }

                int[][] parameterAnnotations = new int[parameterListSize][2];
                for (int j = 0; j < parameterListSize; j++) {
                    parameterAnnotations[j][0] = sectionStream.readInt();
                    parameterAnnotations[j][1] = sectionStream.readInt();
                }
            }
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


