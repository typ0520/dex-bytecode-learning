/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tong.dextest.core;

/**
 * A type definition.
 */
public final class ClassDef {
    public static final int NO_INDEX = -1;
    public static final int NO_OFFSET = 0;

    public int typeIndex;
    public int accessFlags;
    public int supertypeIndex;
    public int interfacesOffset;
    public int sourceFileIndex;
    public int annotationsOffset;
    public int classDataOffset;
    public int staticValuesOffset;

    public ClassDef(int typeIndex, int accessFlags,
            int supertypeIndex, int interfacesOffset, int sourceFileIndex,
            int annotationsOffset, int classDataOffset, int staticValuesOffset) {
        this.typeIndex = typeIndex;
        this.accessFlags = accessFlags;
        this.supertypeIndex = supertypeIndex;
        this.interfacesOffset = interfacesOffset;
        this.sourceFileIndex = sourceFileIndex;
        this.annotationsOffset = annotationsOffset;
        this.classDataOffset = classDataOffset;
        this.staticValuesOffset = staticValuesOffset;
    }
}
