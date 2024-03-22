package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Comparator;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

/* compiled from: 0071.java */
/* loaded from: classes.dex */
public class Table {
    protected ByteBuffer bb;
    protected int bb_pos;
    Utf8 utf8 = Utf8.getDefault();
    private int vtable_size;
    private int vtable_start;

    protected static boolean __has_identifier(ByteBuffer bb, String ident) {
        if (ident.length() != 4) {
            throw new AssertionError("FlatBuffers: file identifier must be length 4");
        }
        for (int i = 0; i < 4; i++) {
            if (ident.charAt(i) != ((char) bb.get(bb.position() + 4 + i))) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static int __indirect(int offset, ByteBuffer bb) {
        return bb.getInt(offset) + offset;
    }

    protected static int __offset(int vtable_offset, int offset, ByteBuffer bb) {
        int capacity = bb.capacity() - offset;
        return bb.getShort((capacity + vtable_offset) - bb.getInt(capacity)) + capacity;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static String __string(int offset, ByteBuffer bb, Utf8 utf8) {
        int offset2 = offset + bb.getInt(offset);
        return utf8.decodeUtf8(bb, offset2 + 4, bb.getInt(offset2));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static Table __union(Table t, int offset, ByteBuffer bb) {
        t.__reset(__indirect(offset, bb), bb);
        return t;
    }

    protected static int compareStrings(int offset_1, int offset_2, ByteBuffer bb) {
        int offset_12 = offset_1 + bb.getInt(offset_1);
        int offset_22 = offset_2 + bb.getInt(offset_2);
        int i = bb.getInt(offset_12);
        int i2 = bb.getInt(offset_22);
        int i3 = offset_12 + 4;
        int i4 = offset_22 + 4;
        int min = Math.min(i, i2);
        for (int i5 = 0; i5 < min; i5++) {
            if (bb.get(i5 + i3) != bb.get(i5 + i4)) {
                return bb.get(i5 + i3) - bb.get(i5 + i4);
            }
        }
        return i - i2;
    }

    protected static int compareStrings(int offset_1, byte[] key, ByteBuffer bb) {
        int offset_12 = offset_1 + bb.getInt(offset_1);
        int i = bb.getInt(offset_12);
        int length = key.length;
        int i2 = offset_12 + 4;
        int min = Math.min(i, length);
        for (int i3 = 0; i3 < min; i3++) {
            if (bb.get(i3 + i2) != key[i3]) {
                return bb.get(i3 + i2) - key[i3];
            }
        }
        return i - length;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int __indirect(int offset) {
        return this.bb.getInt(offset) + offset;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int __offset(int vtable_offset) {
        if (vtable_offset < this.vtable_size) {
            return this.bb.getShort(this.vtable_start + vtable_offset);
        }
        return 0;
    }

    public void __reset() {
        __reset(0, null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void __reset(int _i, ByteBuffer _bb) {
        this.bb = _bb;
        if (_bb == null) {
            this.bb_pos = 0;
            this.vtable_start = 0;
            this.vtable_size = 0;
        } else {
            this.bb_pos = _i;
            int i = _i - _bb.getInt(_i);
            this.vtable_start = i;
            this.vtable_size = this.bb.getShort(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public String __string(int i) {
        String __string = __string(i, this.bb, this.utf8);
        Log5ECF72.a(__string);
        LogE84000.a(__string);
        Log229316.a(__string);
        return __string;
    }

    protected Table __union(Table t, int offset) {
        return __union(t, offset, this.bb);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int __vector(int offset) {
        int offset2 = offset + this.bb_pos;
        return this.bb.getInt(offset2) + offset2 + 4;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public ByteBuffer __vector_as_bytebuffer(int vector_offset, int elem_size) {
        int __offset = __offset(vector_offset);
        if (__offset == 0) {
            return null;
        }
        ByteBuffer order = this.bb.duplicate().order(ByteOrder.LITTLE_ENDIAN);
        int __vector = __vector(__offset);
        order.position(__vector);
        order.limit((__vector_len(__offset) * elem_size) + __vector);
        return order;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public ByteBuffer __vector_in_bytebuffer(ByteBuffer bb, int vector_offset, int elem_size) {
        int __offset = __offset(vector_offset);
        if (__offset == 0) {
            return null;
        }
        int __vector = __vector(__offset);
        bb.rewind();
        bb.limit((__vector_len(__offset) * elem_size) + __vector);
        bb.position(__vector);
        return bb;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int __vector_len(int offset) {
        int offset2 = offset + this.bb_pos;
        return this.bb.getInt(offset2 + this.bb.getInt(offset2));
    }

    public ByteBuffer getByteBuffer() {
        return this.bb;
    }

    protected int keysCompare(Integer o1, Integer o2, ByteBuffer bb) {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void sortTables(int[] offsets, final ByteBuffer bb) {
        Integer[] numArr = new Integer[offsets.length];
        for (int i = 0; i < offsets.length; i++) {
            numArr[i] = Integer.valueOf(offsets[i]);
        }
        Arrays.sort(numArr, new Comparator<Integer>() { // from class: androidx.emoji2.text.flatbuffer.Table.1
            @Override // java.util.Comparator
            public int compare(Integer o1, Integer o2) {
                return Table.this.keysCompare(o1, o2, bb);
            }
        });
        for (int i2 = 0; i2 < offsets.length; i2++) {
            offsets[i2] = numArr[i2].intValue();
        }
    }
}
