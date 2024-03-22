package com.google.android.material.color;

import android.content.Context;
import android.util.Pair;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.UByte;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: 00E9.java */
/* loaded from: classes.dex */
public final class ColorResourcesTableCreator {
    private static final byte ANDROID_PACKAGE_ID = 1;
    private static final byte APPLICATION_PACKAGE_ID = Byte.MAX_VALUE;
    private static final short HEADER_TYPE_PACKAGE = 512;
    private static final short HEADER_TYPE_RES_TABLE = 2;
    private static final short HEADER_TYPE_STRING_POOL = 1;
    private static final short HEADER_TYPE_TYPE = 513;
    private static final short HEADER_TYPE_TYPE_SPEC = 514;
    private static final String RESOURCE_TYPE_NAME_COLOR = "color";
    private static byte typeIdColor;
    private static final PackageInfo ANDROID_PACKAGE_INFO = new PackageInfo(1, "android");
    private static final Comparator<ColorResource> COLOR_RESOURCE_COMPARATOR = new Comparator<ColorResource>() { // from class: com.google.android.material.color.ColorResourcesTableCreator.1
        @Override // java.util.Comparator
        public int compare(ColorResource res1, ColorResource res2) {
            return res1.entryId - res2.entryId;
        }
    };

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class ColorResource {
        private final short entryId;
        private final String name;
        private final byte packageId;
        private final byte typeId;
        private final int value;

        ColorResource(int id, String name, int value) {
            this.name = name;
            this.value = value;
            this.entryId = (short) (65535 & id);
            this.typeId = (byte) ((id >> 16) & 255);
            this.packageId = (byte) ((id >> 24) & 255);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: 00E8.java */
    /* loaded from: classes.dex */
    public static class PackageChunk {
        private static final short HEADER_SIZE = 288;
        private static final int PACKAGE_NAME_MAX_LENGTH = 128;
        private final ResChunkHeader header;
        private final StringPoolChunk keyStrings;
        private final PackageInfo packageInfo;
        private final TypeSpecChunk typeSpecChunk;
        private final StringPoolChunk typeStrings = new StringPoolChunk(false, "?1", "?2", "?3", "?4", "?5", "color");

        /* JADX WARN: Failed to parse debug info
        jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
        	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
         */
        PackageChunk(PackageInfo packageInfo, List<ColorResource> list) {
            this.packageInfo = packageInfo;
            String[] strArr = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                String str = list.get(i).name;
                Log5ECF72.a(str);
                LogE84000.a(str);
                Log229316.a(str);
                strArr[i] = str;
            }
            this.keyStrings = new StringPoolChunk(true, strArr);
            this.typeSpecChunk = new TypeSpecChunk(list);
            this.header = new ResChunkHeader(ColorResourcesTableCreator.HEADER_TYPE_PACKAGE, HEADER_SIZE, getChunkSize());
        }

        int getChunkSize() {
            return this.typeStrings.getChunkSize() + 288 + this.keyStrings.getChunkSize() + this.typeSpecChunk.getChunkSizeWithTypeChunk();
        }

        /* JADX WARN: Failed to parse debug info
        jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
        	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
         */
        void writeTo(ByteArrayOutputStream byteArrayOutputStream) throws IOException {
            this.header.writeTo(byteArrayOutputStream);
            byteArrayOutputStream.write(ColorResourcesTableCreator.intToByteArray(this.packageInfo.id));
            String str = this.packageInfo.name;
            Log5ECF72.a(str);
            LogE84000.a(str);
            Log229316.a(str);
            char[] charArray = str.toCharArray();
            for (int i = 0; i < 128; i++) {
                if (i < charArray.length) {
                    byteArrayOutputStream.write(ColorResourcesTableCreator.charToByteArray(charArray[i]));
                } else {
                    byteArrayOutputStream.write(ColorResourcesTableCreator.charToByteArray((char) 0));
                }
            }
            byteArrayOutputStream.write(ColorResourcesTableCreator.intToByteArray(288));
            byteArrayOutputStream.write(ColorResourcesTableCreator.intToByteArray(0));
            byteArrayOutputStream.write(ColorResourcesTableCreator.intToByteArray(this.typeStrings.getChunkSize() + 288));
            byteArrayOutputStream.write(ColorResourcesTableCreator.intToByteArray(0));
            byteArrayOutputStream.write(ColorResourcesTableCreator.intToByteArray(0));
            this.typeStrings.writeTo(byteArrayOutputStream);
            this.keyStrings.writeTo(byteArrayOutputStream);
            this.typeSpecChunk.writeTo(byteArrayOutputStream);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class PackageInfo {
        private final int id;
        private final String name;

        PackageInfo(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class ResChunkHeader {
        private final int chunkSize;
        private final short headerSize;
        private final short type;

        ResChunkHeader(short type, short headerSize, int chunkSize) {
            this.type = type;
            this.headerSize = headerSize;
            this.chunkSize = chunkSize;
        }

        void writeTo(ByteArrayOutputStream outputStream) throws IOException {
            outputStream.write(ColorResourcesTableCreator.shortToByteArray(this.type));
            outputStream.write(ColorResourcesTableCreator.shortToByteArray(this.headerSize));
            outputStream.write(ColorResourcesTableCreator.intToByteArray(this.chunkSize));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class ResEntry {
        private static final byte DATA_TYPE_AARRGGBB = 28;
        private static final short ENTRY_SIZE = 8;
        private static final short FLAG_PUBLIC = 2;
        private static final int SIZE = 16;
        private static final short VALUE_SIZE = 8;
        private final int data;
        private final int keyStringIndex;

        ResEntry(int keyStringIndex, int data) {
            this.keyStringIndex = keyStringIndex;
            this.data = data;
        }

        void writeTo(ByteArrayOutputStream outputStream) throws IOException {
            outputStream.write(ColorResourcesTableCreator.shortToByteArray((short) 8));
            outputStream.write(ColorResourcesTableCreator.shortToByteArray(FLAG_PUBLIC));
            outputStream.write(ColorResourcesTableCreator.intToByteArray(this.keyStringIndex));
            outputStream.write(ColorResourcesTableCreator.shortToByteArray((short) 8));
            outputStream.write(new byte[]{0, DATA_TYPE_AARRGGBB});
            outputStream.write(ColorResourcesTableCreator.intToByteArray(this.data));
        }
    }

    /* loaded from: classes.dex */
    private static class ResTable {
        private static final short HEADER_SIZE = 12;
        private final ResChunkHeader header;
        private final int packageCount;
        private final List<PackageChunk> packageChunks = new ArrayList();
        private final StringPoolChunk stringPool = new StringPoolChunk(new String[0]);

        ResTable(Map<PackageInfo, List<ColorResource>> map) {
            this.packageCount = map.size();
            for (Map.Entry<PackageInfo, List<ColorResource>> entry : map.entrySet()) {
                List<ColorResource> value = entry.getValue();
                Collections.sort(value, ColorResourcesTableCreator.COLOR_RESOURCE_COMPARATOR);
                this.packageChunks.add(new PackageChunk(entry.getKey(), value));
            }
            this.header = new ResChunkHeader(ColorResourcesTableCreator.HEADER_TYPE_RES_TABLE, HEADER_SIZE, getOverallSize());
        }

        private int getOverallSize() {
            int i = 0;
            Iterator<PackageChunk> it = this.packageChunks.iterator();
            while (it.hasNext()) {
                i += it.next().getChunkSize();
            }
            return this.stringPool.getChunkSize() + 12 + i;
        }

        void writeTo(ByteArrayOutputStream outputStream) throws IOException {
            this.header.writeTo(outputStream);
            outputStream.write(ColorResourcesTableCreator.intToByteArray(this.packageCount));
            this.stringPool.writeTo(outputStream);
            Iterator<PackageChunk> it = this.packageChunks.iterator();
            while (it.hasNext()) {
                it.next().writeTo(outputStream);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class StringPoolChunk {
        private static final int FLAG_UTF8 = 256;
        private static final short HEADER_SIZE = 28;
        private static final int STYLED_SPAN_LIST_END = -1;
        private final int chunkSize;
        private final ResChunkHeader header;
        private final int stringCount;
        private final List<Integer> stringIndex;
        private final List<byte[]> strings;
        private final int stringsPaddingSize;
        private final int stringsStart;
        private final int styledSpanCount;
        private final List<Integer> styledSpanIndex;
        private final List<List<StringStyledSpan>> styledSpans;
        private final int styledSpansStart;
        private final boolean utf8Encode;

        /* JADX WARN: Multi-variable type inference failed */
        StringPoolChunk(boolean utf8, String... rawStrings) {
            this.stringIndex = new ArrayList();
            this.styledSpanIndex = new ArrayList();
            this.strings = new ArrayList();
            this.styledSpans = new ArrayList();
            this.utf8Encode = utf8;
            int i = 0;
            for (String str : rawStrings) {
                Pair<byte[], List<StringStyledSpan>> processString = processString(str);
                this.stringIndex.add(Integer.valueOf(i));
                i += ((byte[]) processString.first).length;
                this.strings.add(processString.first);
                this.styledSpans.add(processString.second);
            }
            int i2 = 0;
            for (List<StringStyledSpan> list : this.styledSpans) {
                for (StringStyledSpan stringStyledSpan : list) {
                    this.stringIndex.add(Integer.valueOf(i));
                    i += stringStyledSpan.styleString.length;
                    this.strings.add(stringStyledSpan.styleString);
                }
                this.styledSpanIndex.add(Integer.valueOf(i2));
                i2 += (list.size() * 12) + 4;
            }
            int i3 = i % 4;
            int i4 = i3 == 0 ? 0 : 4 - i3;
            this.stringsPaddingSize = i4;
            int size = this.strings.size();
            this.stringCount = size;
            this.styledSpanCount = this.strings.size() - rawStrings.length;
            boolean z = this.strings.size() - rawStrings.length > 0;
            if (!z) {
                this.styledSpanIndex.clear();
                this.styledSpans.clear();
            }
            int size2 = (size * 4) + 28 + (this.styledSpanIndex.size() * 4);
            this.stringsStart = size2;
            int i5 = i4 + i;
            this.styledSpansStart = z ? size2 + i5 : 0;
            int i6 = size2 + i5 + (z ? i2 : 0);
            this.chunkSize = i6;
            this.header = new ResChunkHeader(ColorResourcesTableCreator.HEADER_TYPE_STRING_POOL, HEADER_SIZE, i6);
        }

        StringPoolChunk(String... rawStrings) {
            this(false, rawStrings);
        }

        private Pair<byte[], List<StringStyledSpan>> processString(String rawString) {
            return new Pair<>(this.utf8Encode ? ColorResourcesTableCreator.stringToByteArrayUtf8(rawString) : ColorResourcesTableCreator.stringToByteArray(rawString), Collections.emptyList());
        }

        int getChunkSize() {
            return this.chunkSize;
        }

        void writeTo(ByteArrayOutputStream outputStream) throws IOException {
            this.header.writeTo(outputStream);
            outputStream.write(ColorResourcesTableCreator.intToByteArray(this.stringCount));
            outputStream.write(ColorResourcesTableCreator.intToByteArray(this.styledSpanCount));
            outputStream.write(ColorResourcesTableCreator.intToByteArray(this.utf8Encode ? 256 : 0));
            outputStream.write(ColorResourcesTableCreator.intToByteArray(this.stringsStart));
            outputStream.write(ColorResourcesTableCreator.intToByteArray(this.styledSpansStart));
            Iterator<Integer> it = this.stringIndex.iterator();
            while (it.hasNext()) {
                outputStream.write(ColorResourcesTableCreator.intToByteArray(it.next().intValue()));
            }
            Iterator<Integer> it2 = this.styledSpanIndex.iterator();
            while (it2.hasNext()) {
                outputStream.write(ColorResourcesTableCreator.intToByteArray(it2.next().intValue()));
            }
            Iterator<byte[]> it3 = this.strings.iterator();
            while (it3.hasNext()) {
                outputStream.write(it3.next());
            }
            int i = this.stringsPaddingSize;
            if (i > 0) {
                outputStream.write(new byte[i]);
            }
            Iterator<List<StringStyledSpan>> it4 = this.styledSpans.iterator();
            while (it4.hasNext()) {
                Iterator<StringStyledSpan> it5 = it4.next().iterator();
                while (it5.hasNext()) {
                    it5.next().writeTo(outputStream);
                }
                outputStream.write(ColorResourcesTableCreator.intToByteArray(-1));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class StringStyledSpan {
        private int firstCharacterIndex;
        private int lastCharacterIndex;
        private int nameReference;
        private byte[] styleString;

        private StringStyledSpan() {
        }

        void writeTo(ByteArrayOutputStream outputStream) throws IOException {
            outputStream.write(ColorResourcesTableCreator.intToByteArray(this.nameReference));
            outputStream.write(ColorResourcesTableCreator.intToByteArray(this.firstCharacterIndex));
            outputStream.write(ColorResourcesTableCreator.intToByteArray(this.lastCharacterIndex));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class TypeChunk {
        private static final byte CONFIG_SIZE = 64;
        private static final short HEADER_SIZE = 84;
        private static final int OFFSET_NO_ENTRY = -1;
        private final byte[] config;
        private final int entryCount;
        private final ResChunkHeader header;
        private final int[] offsetTable;
        private final ResEntry[] resEntries;

        TypeChunk(List<ColorResource> list, Set<Short> set, int entryCount) {
            byte[] bArr = new byte[64];
            this.config = bArr;
            this.entryCount = entryCount;
            bArr[0] = CONFIG_SIZE;
            this.resEntries = new ResEntry[list.size()];
            for (int i = 0; i < list.size(); i++) {
                this.resEntries[i] = new ResEntry(i, list.get(i).value);
            }
            this.offsetTable = new int[entryCount];
            int i2 = 0;
            for (short s = 0; s < entryCount; s = (short) (s + ColorResourcesTableCreator.HEADER_TYPE_STRING_POOL)) {
                if (set.contains(Short.valueOf(s))) {
                    this.offsetTable[s] = i2;
                    i2 += 16;
                } else {
                    this.offsetTable[s] = -1;
                }
            }
            this.header = new ResChunkHeader(ColorResourcesTableCreator.HEADER_TYPE_TYPE, HEADER_SIZE, getChunkSize());
        }

        private int getEntryStart() {
            return getOffsetTableSize() + 84;
        }

        private int getOffsetTableSize() {
            return this.offsetTable.length * 4;
        }

        int getChunkSize() {
            return getEntryStart() + (this.resEntries.length * 16);
        }

        void writeTo(ByteArrayOutputStream outputStream) throws IOException {
            this.header.writeTo(outputStream);
            outputStream.write(new byte[]{ColorResourcesTableCreator.typeIdColor, 0, 0, 0});
            outputStream.write(ColorResourcesTableCreator.intToByteArray(this.entryCount));
            outputStream.write(ColorResourcesTableCreator.intToByteArray(getEntryStart()));
            outputStream.write(this.config);
            for (int i : this.offsetTable) {
                outputStream.write(ColorResourcesTableCreator.intToByteArray(i));
            }
            for (ResEntry resEntry : this.resEntries) {
                resEntry.writeTo(outputStream);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class TypeSpecChunk {
        private static final short HEADER_SIZE = 16;
        private static final int SPEC_PUBLIC = 1073741824;
        private final int entryCount;
        private final int[] entryFlags;
        private final ResChunkHeader header;
        private final TypeChunk typeChunk;

        TypeSpecChunk(List<ColorResource> list) {
            this.entryCount = list.get(list.size() - 1).entryId + ColorResourcesTableCreator.HEADER_TYPE_STRING_POOL;
            HashSet hashSet = new HashSet();
            Iterator<ColorResource> it = list.iterator();
            while (it.hasNext()) {
                hashSet.add(Short.valueOf(it.next().entryId));
            }
            this.entryFlags = new int[this.entryCount];
            for (short s = 0; s < this.entryCount; s = (short) (s + ColorResourcesTableCreator.HEADER_TYPE_STRING_POOL)) {
                if (hashSet.contains(Short.valueOf(s))) {
                    this.entryFlags[s] = 1073741824;
                }
            }
            this.header = new ResChunkHeader(ColorResourcesTableCreator.HEADER_TYPE_TYPE_SPEC, HEADER_SIZE, getChunkSize());
            this.typeChunk = new TypeChunk(list, hashSet, this.entryCount);
        }

        private int getChunkSize() {
            return (this.entryCount * 4) + 16;
        }

        int getChunkSizeWithTypeChunk() {
            return getChunkSize() + this.typeChunk.getChunkSize();
        }

        void writeTo(ByteArrayOutputStream outputStream) throws IOException {
            this.header.writeTo(outputStream);
            outputStream.write(new byte[]{ColorResourcesTableCreator.typeIdColor, 0, 0, 0});
            outputStream.write(ColorResourcesTableCreator.intToByteArray(this.entryCount));
            for (int i : this.entryFlags) {
                outputStream.write(ColorResourcesTableCreator.intToByteArray(i));
            }
            this.typeChunk.writeTo(outputStream);
        }
    }

    private ColorResourcesTableCreator() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte[] charToByteArray(char value) {
        return new byte[]{(byte) (value & 255), (byte) ((value >> '\b') & 255)};
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public static byte[] create(Context context, Map<Integer, Integer> map) throws IOException {
        PackageInfo packageInfo;
        if (map.entrySet().isEmpty()) {
            throw new IllegalArgumentException("No color resources provided for harmonization.");
        }
        PackageInfo packageInfo2 = new PackageInfo(127, context.getPackageName());
        HashMap hashMap = new HashMap();
        ColorResource colorResource = null;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            colorResource = new ColorResource(entry.getKey().intValue(), context.getResources().getResourceName(entry.getKey().intValue()), entry.getValue().intValue());
            if (!context.getResources().getResourceTypeName(entry.getKey().intValue()).equals("color")) {
                StringBuilder append = new StringBuilder().append("Non color resource found: name=");
                String str = colorResource.name;
                Log5ECF72.a(str);
                LogE84000.a(str);
                Log229316.a(str);
                StringBuilder append2 = append.append(str).append(", typeId=");
                String hexString = Integer.toHexString(colorResource.typeId & UByte.MAX_VALUE);
                Log5ECF72.a(hexString);
                LogE84000.a(hexString);
                Log229316.a(hexString);
                throw new IllegalArgumentException(append2.append(hexString).toString());
            }
            if (colorResource.packageId == 1) {
                packageInfo = ANDROID_PACKAGE_INFO;
            } else {
                if (colorResource.packageId != Byte.MAX_VALUE) {
                    throw new IllegalArgumentException("Not supported with unknown package id: " + ((int) colorResource.packageId));
                }
                packageInfo = packageInfo2;
            }
            if (!hashMap.containsKey(packageInfo)) {
                hashMap.put(packageInfo, new ArrayList());
            }
            ((List) hashMap.get(packageInfo)).add(colorResource);
        }
        byte b = colorResource.typeId;
        typeIdColor = b;
        if (b == 0) {
            throw new IllegalArgumentException("No color resources found for harmonization.");
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        new ResTable(hashMap).writeTo(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte[] intToByteArray(int value) {
        return new byte[]{(byte) (value & 255), (byte) ((value >> 8) & 255), (byte) ((value >> 16) & 255), (byte) ((value >> 24) & 255)};
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte[] shortToByteArray(short value) {
        return new byte[]{(byte) (value & 255), (byte) ((value >> 8) & 255)};
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte[] stringToByteArray(String value) {
        char[] charArray = value.toCharArray();
        byte[] bArr = new byte[(charArray.length * 2) + 4];
        byte[] shortToByteArray = shortToByteArray((short) charArray.length);
        bArr[0] = shortToByteArray[0];
        bArr[1] = shortToByteArray[1];
        for (int i = 0; i < charArray.length; i++) {
            byte[] charToByteArray = charToByteArray(charArray[i]);
            bArr[(i * 2) + 2] = charToByteArray[0];
            bArr[(i * 2) + 3] = charToByteArray[1];
        }
        bArr[bArr.length - 2] = 0;
        bArr[bArr.length - 1] = 0;
        return bArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte[] stringToByteArrayUtf8(String value) {
        byte[] bytes = value.getBytes(Charset.forName("UTF-8"));
        byte length = (byte) bytes.length;
        byte[] bArr = new byte[bytes.length + 3];
        System.arraycopy(bytes, 0, bArr, 2, length);
        bArr[1] = length;
        bArr[0] = length;
        bArr[bArr.length - 1] = 0;
        return bArr;
    }
}
