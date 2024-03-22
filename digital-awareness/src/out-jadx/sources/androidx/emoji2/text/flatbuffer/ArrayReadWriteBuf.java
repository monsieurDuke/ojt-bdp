package androidx.emoji2.text.flatbuffer;

import java.util.Arrays;
import kotlin.UByte;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

/* compiled from: 006F.java */
/* loaded from: classes.dex */
public class ArrayReadWriteBuf implements ReadWriteBuf {
    private byte[] buffer;
    private int writePos;

    public ArrayReadWriteBuf() {
        this(10);
    }

    public ArrayReadWriteBuf(int initialCapacity) {
        this(new byte[initialCapacity]);
    }

    public ArrayReadWriteBuf(byte[] buffer) {
        this.buffer = buffer;
        this.writePos = 0;
    }

    public ArrayReadWriteBuf(byte[] buffer, int startPos) {
        this.buffer = buffer;
        this.writePos = startPos;
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadBuf
    public byte[] data() {
        return this.buffer;
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadBuf
    public byte get(int index) {
        return this.buffer[index];
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadBuf
    public boolean getBoolean(int index) {
        return this.buffer[index] != 0;
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadBuf
    public double getDouble(int index) {
        return Double.longBitsToDouble(getLong(index));
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadBuf
    public float getFloat(int index) {
        return Float.intBitsToFloat(getInt(index));
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadBuf
    public int getInt(int index) {
        byte[] bArr = this.buffer;
        return (bArr[index] & UByte.MAX_VALUE) | (bArr[index + 3] << 24) | ((bArr[index + 2] & UByte.MAX_VALUE) << 16) | ((bArr[index + 1] & UByte.MAX_VALUE) << 8);
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadBuf
    public long getLong(int index) {
        byte[] bArr = this.buffer;
        long j = bArr[index] & 255;
        int index2 = index + 1 + 1;
        long j2 = j | ((bArr[r1] & 255) << 8) | ((bArr[index2] & 255) << 16);
        int index3 = index2 + 1 + 1;
        long j3 = j2 | ((bArr[r3] & 255) << 24);
        long j4 = j3 | ((bArr[index3] & 255) << 32);
        int index4 = index3 + 1 + 1;
        return j4 | ((bArr[r3] & 255) << 40) | ((255 & bArr[index4]) << 48) | (bArr[index4 + 1] << 56);
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadBuf
    public short getShort(int index) {
        byte[] bArr = this.buffer;
        return (short) ((bArr[index] & UByte.MAX_VALUE) | (bArr[index + 1] << 8));
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    @Override // androidx.emoji2.text.flatbuffer.ReadBuf
    public String getString(int i, int i2) {
        String decodeUtf8Array = Utf8Safe.decodeUtf8Array(this.buffer, i, i2);
        Log5ECF72.a(decodeUtf8Array);
        LogE84000.a(decodeUtf8Array);
        Log229316.a(decodeUtf8Array);
        return decodeUtf8Array;
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadWriteBuf, androidx.emoji2.text.flatbuffer.ReadBuf
    public int limit() {
        return this.writePos;
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadWriteBuf
    public void put(byte value) {
        set(this.writePos, value);
        this.writePos++;
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadWriteBuf
    public void put(byte[] value, int start, int length) {
        set(this.writePos, value, start, length);
        this.writePos += length;
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadWriteBuf
    public void putBoolean(boolean value) {
        setBoolean(this.writePos, value);
        this.writePos++;
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadWriteBuf
    public void putDouble(double value) {
        setDouble(this.writePos, value);
        this.writePos += 8;
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadWriteBuf
    public void putFloat(float value) {
        setFloat(this.writePos, value);
        this.writePos += 4;
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadWriteBuf
    public void putInt(int value) {
        setInt(this.writePos, value);
        this.writePos += 4;
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadWriteBuf
    public void putLong(long value) {
        setLong(this.writePos, value);
        this.writePos += 8;
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadWriteBuf
    public void putShort(short value) {
        setShort(this.writePos, value);
        this.writePos += 2;
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadWriteBuf
    public boolean requestCapacity(int capacity) {
        byte[] bArr = this.buffer;
        if (bArr.length > capacity) {
            return true;
        }
        int length = bArr.length;
        this.buffer = Arrays.copyOf(bArr, (length >> 1) + length);
        return true;
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadWriteBuf
    public void set(int index, byte value) {
        requestCapacity(index + 1);
        this.buffer[index] = value;
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadWriteBuf
    public void set(int index, byte[] toCopy, int start, int length) {
        requestCapacity((length - start) + index);
        System.arraycopy(toCopy, start, this.buffer, index, length);
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadWriteBuf
    public void setBoolean(int i, boolean z) {
        set(i, z ? (byte) 1 : (byte) 0);
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadWriteBuf
    public void setDouble(int index, double value) {
        requestCapacity(index + 8);
        long doubleToRawLongBits = Double.doubleToRawLongBits(value);
        int i = (int) doubleToRawLongBits;
        byte[] bArr = this.buffer;
        int i2 = index + 1;
        bArr[index] = (byte) (i & 255);
        int index2 = i2 + 1;
        bArr[i2] = (byte) ((i >> 8) & 255);
        int i3 = index2 + 1;
        bArr[index2] = (byte) ((i >> 16) & 255);
        int index3 = i3 + 1;
        bArr[i3] = (byte) ((i >> 24) & 255);
        int i4 = (int) (doubleToRawLongBits >> 32);
        int i5 = index3 + 1;
        bArr[index3] = (byte) (i4 & 255);
        int index4 = i5 + 1;
        bArr[i5] = (byte) ((i4 >> 8) & 255);
        bArr[index4] = (byte) ((i4 >> 16) & 255);
        bArr[index4 + 1] = (byte) ((i4 >> 24) & 255);
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadWriteBuf
    public void setFloat(int index, float value) {
        requestCapacity(index + 4);
        int floatToRawIntBits = Float.floatToRawIntBits(value);
        byte[] bArr = this.buffer;
        int i = index + 1;
        bArr[index] = (byte) (floatToRawIntBits & 255);
        int index2 = i + 1;
        bArr[i] = (byte) ((floatToRawIntBits >> 8) & 255);
        bArr[index2] = (byte) ((floatToRawIntBits >> 16) & 255);
        bArr[index2 + 1] = (byte) ((floatToRawIntBits >> 24) & 255);
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadWriteBuf
    public void setInt(int index, int value) {
        requestCapacity(index + 4);
        byte[] bArr = this.buffer;
        int i = index + 1;
        bArr[index] = (byte) (value & 255);
        int index2 = i + 1;
        bArr[i] = (byte) ((value >> 8) & 255);
        bArr[index2] = (byte) ((value >> 16) & 255);
        bArr[index2 + 1] = (byte) ((value >> 24) & 255);
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadWriteBuf
    public void setLong(int index, long value) {
        requestCapacity(index + 8);
        int i = (int) value;
        byte[] bArr = this.buffer;
        int i2 = index + 1;
        bArr[index] = (byte) (i & 255);
        int index2 = i2 + 1;
        bArr[i2] = (byte) ((i >> 8) & 255);
        int i3 = index2 + 1;
        bArr[index2] = (byte) ((i >> 16) & 255);
        int index3 = i3 + 1;
        bArr[i3] = (byte) ((i >> 24) & 255);
        int i4 = (int) (value >> 32);
        int i5 = index3 + 1;
        bArr[index3] = (byte) (i4 & 255);
        int index4 = i5 + 1;
        bArr[i5] = (byte) ((i4 >> 8) & 255);
        bArr[index4] = (byte) ((i4 >> 16) & 255);
        bArr[index4 + 1] = (byte) ((i4 >> 24) & 255);
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadWriteBuf
    public void setShort(int index, short value) {
        requestCapacity(index + 2);
        byte[] bArr = this.buffer;
        bArr[index] = (byte) (value & 255);
        bArr[index + 1] = (byte) ((value >> 8) & 255);
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadWriteBuf
    public int writePosition() {
        return this.writePos;
    }
}
