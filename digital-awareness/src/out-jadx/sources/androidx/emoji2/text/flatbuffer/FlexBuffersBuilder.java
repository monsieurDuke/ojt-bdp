package androidx.emoji2.text.flatbuffer;

import androidx.emoji2.text.flatbuffer.FlexBuffers;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/* loaded from: classes.dex */
public class FlexBuffersBuilder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int BUILDER_FLAG_NONE = 0;
    public static final int BUILDER_FLAG_SHARE_ALL = 7;
    public static final int BUILDER_FLAG_SHARE_KEYS = 1;
    public static final int BUILDER_FLAG_SHARE_KEYS_AND_STRINGS = 3;
    public static final int BUILDER_FLAG_SHARE_KEY_VECTORS = 4;
    public static final int BUILDER_FLAG_SHARE_STRINGS = 2;
    private static final int WIDTH_16 = 1;
    private static final int WIDTH_32 = 2;
    private static final int WIDTH_64 = 3;
    private static final int WIDTH_8 = 0;
    private final ReadWriteBuf bb;
    private boolean finished;
    private final int flags;
    private Comparator<Value> keyComparator;
    private final HashMap<String, Integer> keyPool;
    private final ArrayList<Value> stack;
    private final HashMap<String, Integer> stringPool;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class Value {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        final double dValue;
        long iValue;
        int key;
        final int minBitWidth;
        final int type;

        Value(int key, int type, int bitWidth, double dValue) {
            this.key = key;
            this.type = type;
            this.minBitWidth = bitWidth;
            this.dValue = dValue;
            this.iValue = Long.MIN_VALUE;
        }

        Value(int key, int type, int bitWidth, long iValue) {
            this.key = key;
            this.type = type;
            this.minBitWidth = bitWidth;
            this.iValue = iValue;
            this.dValue = Double.MIN_VALUE;
        }

        static Value blob(int key, int position, int type, int bitWidth) {
            return new Value(key, type, bitWidth, position);
        }

        static Value bool(int key, boolean b) {
            return new Value(key, 26, 0, b ? 1L : 0L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int elemWidth(int bufSize, int elemIndex) {
            return elemWidth(this.type, this.minBitWidth, this.iValue, bufSize, elemIndex);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static int elemWidth(int type, int minBitWidth, long iValue, int bufSize, int elemIndex) {
            if (FlexBuffers.isTypeInline(type)) {
                return minBitWidth;
            }
            for (int i = 1; i <= 32; i *= 2) {
                int widthUInBits = FlexBuffersBuilder.widthUInBits((int) (((paddingBytes(bufSize, i) + bufSize) + (elemIndex * i)) - iValue));
                if ((1 << widthUInBits) == i) {
                    return widthUInBits;
                }
            }
            throw new AssertionError();
        }

        static Value float32(int key, float value) {
            return new Value(key, 3, 2, value);
        }

        static Value float64(int key, double value) {
            return new Value(key, 3, 3, value);
        }

        static Value int16(int key, int value) {
            return new Value(key, 1, 1, value);
        }

        static Value int32(int key, int value) {
            return new Value(key, 1, 2, value);
        }

        static Value int64(int key, long value) {
            return new Value(key, 1, 3, value);
        }

        static Value int8(int key, int value) {
            return new Value(key, 1, 0, value);
        }

        private static byte packedType(int bitWidth, int type) {
            return (byte) ((type << 2) | bitWidth);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static int paddingBytes(int bufSize, int scalarSize) {
            return ((~bufSize) + 1) & (scalarSize - 1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public byte storedPackedType() {
            return storedPackedType(0);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public byte storedPackedType(int parentBitWidth) {
            return packedType(storedWidth(parentBitWidth), this.type);
        }

        private int storedWidth(int parentBitWidth) {
            return FlexBuffers.isTypeInline(this.type) ? Math.max(this.minBitWidth, parentBitWidth) : this.minBitWidth;
        }

        static Value uInt16(int key, int value) {
            return new Value(key, 2, 1, value);
        }

        static Value uInt32(int key, int value) {
            return new Value(key, 2, 2, value);
        }

        static Value uInt64(int key, long value) {
            return new Value(key, 2, 3, value);
        }

        static Value uInt8(int key, int value) {
            return new Value(key, 2, 0, value);
        }
    }

    public FlexBuffersBuilder() {
        this(256);
    }

    public FlexBuffersBuilder(int bufSize) {
        this(new ArrayReadWriteBuf(bufSize), 1);
    }

    public FlexBuffersBuilder(ReadWriteBuf bb, int flags) {
        this.stack = new ArrayList<>();
        this.keyPool = new HashMap<>();
        this.stringPool = new HashMap<>();
        this.finished = false;
        this.keyComparator = new Comparator<Value>() { // from class: androidx.emoji2.text.flatbuffer.FlexBuffersBuilder.1
            @Override // java.util.Comparator
            public int compare(Value o1, Value o2) {
                byte b;
                byte b2;
                int i = o1.key;
                int i2 = o2.key;
                do {
                    b = FlexBuffersBuilder.this.bb.get(i);
                    b2 = FlexBuffersBuilder.this.bb.get(i2);
                    if (b == 0) {
                        return b - b2;
                    }
                    i++;
                    i2++;
                } while (b == b2);
                return b - b2;
            }
        };
        this.bb = bb;
        this.flags = flags;
    }

    public FlexBuffersBuilder(ByteBuffer bb) {
        this(bb, 1);
    }

    @Deprecated
    public FlexBuffersBuilder(ByteBuffer bb, int flags) {
        this(new ArrayReadWriteBuf(bb.array()), flags);
    }

    private int align(int alignment) {
        int i = 1 << alignment;
        int paddingBytes = Value.paddingBytes(this.bb.writePosition(), i);
        while (true) {
            int i2 = paddingBytes - 1;
            if (paddingBytes == 0) {
                return i;
            }
            this.bb.put((byte) 0);
            paddingBytes = i2;
        }
    }

    private Value createKeyVector(int start, int length) {
        int max = Math.max(0, widthUInBits(length));
        for (int i = start; i < this.stack.size(); i++) {
            max = Math.max(max, Value.elemWidth(4, 0, this.stack.get(i).key, this.bb.writePosition(), i + 1));
        }
        int align = align(max);
        writeInt(length, align);
        int writePosition = this.bb.writePosition();
        for (int i2 = start; i2 < this.stack.size(); i2++) {
            if (this.stack.get(i2).key == -1) {
                throw new AssertionError();
            }
            writeOffset(this.stack.get(i2).key, align);
        }
        return new Value(-1, FlexBuffers.toTypedVector(4, 0), max, writePosition);
    }

    private Value createVector(int key, int start, int length, boolean typed, boolean fixed, Value keys) {
        int typedVector;
        if (fixed && !typed) {
            throw new AssertionError();
        }
        int max = Math.max(0, widthUInBits(length));
        int i = 1;
        if (keys != null) {
            max = Math.max(max, keys.elemWidth(this.bb.writePosition(), 0));
            i = 1 + 2;
        }
        int i2 = 4;
        for (int i3 = start; i3 < this.stack.size(); i3++) {
            max = Math.max(max, this.stack.get(i3).elemWidth(this.bb.writePosition(), i3 + i));
            if (typed) {
                if (i3 == start) {
                    i2 = this.stack.get(i3).type;
                    if (!FlexBuffers.isTypedVectorElementType(i2)) {
                        throw new FlexBuffers.FlexBufferException("TypedVector does not support this element type");
                    }
                } else if (i2 != this.stack.get(i3).type) {
                    throw new AssertionError();
                }
            }
        }
        if (fixed && !FlexBuffers.isTypedVectorElementType(i2)) {
            throw new AssertionError();
        }
        int align = align(max);
        if (keys != null) {
            writeOffset(keys.iValue, align);
            writeInt(1 << keys.minBitWidth, align);
        }
        if (!fixed) {
            writeInt(length, align);
        }
        int writePosition = this.bb.writePosition();
        for (int i4 = start; i4 < this.stack.size(); i4++) {
            writeAny(this.stack.get(i4), align);
        }
        if (!typed) {
            for (int i5 = start; i5 < this.stack.size(); i5++) {
                this.bb.put(this.stack.get(i5).storedPackedType(max));
            }
        }
        if (keys != null) {
            typedVector = 9;
        } else {
            typedVector = typed ? FlexBuffers.toTypedVector(i2, fixed ? length : 0) : 10;
        }
        return new Value(key, typedVector, max, writePosition);
    }

    private int putKey(String key) {
        if (key == null) {
            return -1;
        }
        int writePosition = this.bb.writePosition();
        if ((this.flags & 1) == 0) {
            byte[] bytes = key.getBytes(StandardCharsets.UTF_8);
            this.bb.put(bytes, 0, bytes.length);
            this.bb.put((byte) 0);
            this.keyPool.put(key, Integer.valueOf(writePosition));
            return writePosition;
        }
        Integer num = this.keyPool.get(key);
        if (num != null) {
            return num.intValue();
        }
        byte[] bytes2 = key.getBytes(StandardCharsets.UTF_8);
        this.bb.put(bytes2, 0, bytes2.length);
        this.bb.put((byte) 0);
        this.keyPool.put(key, Integer.valueOf(writePosition));
        return writePosition;
    }

    private void putUInt(String key, long value) {
        int putKey = putKey(key);
        int widthUInBits = widthUInBits(value);
        this.stack.add(widthUInBits == 0 ? Value.uInt8(putKey, (int) value) : widthUInBits == 1 ? Value.uInt16(putKey, (int) value) : widthUInBits == 2 ? Value.uInt32(putKey, (int) value) : Value.uInt64(putKey, value));
    }

    private void putUInt64(String key, long value) {
        this.stack.add(Value.uInt64(putKey(key), value));
    }

    static int widthUInBits(long len) {
        if (len <= FlexBuffers.Unsigned.byteToUnsignedInt((byte) -1)) {
            return 0;
        }
        if (len <= FlexBuffers.Unsigned.shortToUnsignedInt((short) -1)) {
            return 1;
        }
        return len <= FlexBuffers.Unsigned.intToUnsignedLong(-1) ? 2 : 3;
    }

    private void writeAny(Value val, int byteWidth) {
        switch (val.type) {
            case 0:
            case 1:
            case 2:
            case 26:
                writeInt(val.iValue, byteWidth);
                return;
            case 3:
                writeDouble(val.dValue, byteWidth);
                return;
            default:
                writeOffset(val.iValue, byteWidth);
                return;
        }
    }

    private Value writeBlob(int key, byte[] blob, int type, boolean trailing) {
        int widthUInBits = widthUInBits(blob.length);
        writeInt(blob.length, align(widthUInBits));
        int writePosition = this.bb.writePosition();
        this.bb.put(blob, 0, blob.length);
        if (trailing) {
            this.bb.put((byte) 0);
        }
        return Value.blob(key, writePosition, type, widthUInBits);
    }

    private void writeDouble(double val, int byteWidth) {
        if (byteWidth == 4) {
            this.bb.putFloat((float) val);
        } else if (byteWidth == 8) {
            this.bb.putDouble(val);
        }
    }

    private void writeInt(long value, int byteWidth) {
        switch (byteWidth) {
            case 1:
                this.bb.put((byte) value);
                return;
            case 2:
                this.bb.putShort((short) value);
                return;
            case 4:
                this.bb.putInt((int) value);
                return;
            case 8:
                this.bb.putLong(value);
                return;
            default:
                return;
        }
    }

    private void writeOffset(long val, int byteWidth) {
        int writePosition = (int) (this.bb.writePosition() - val);
        if (byteWidth != 8 && writePosition >= (1 << (byteWidth * 8))) {
            throw new AssertionError();
        }
        writeInt(writePosition, byteWidth);
    }

    private Value writeString(int key, String s) {
        return writeBlob(key, s.getBytes(StandardCharsets.UTF_8), 5, true);
    }

    public int endMap(String key, int start) {
        int putKey = putKey(key);
        ArrayList<Value> arrayList = this.stack;
        Collections.sort(arrayList.subList(start, arrayList.size()), this.keyComparator);
        Value createVector = createVector(putKey, start, this.stack.size() - start, false, false, createKeyVector(start, this.stack.size() - start));
        while (this.stack.size() > start) {
            this.stack.remove(r1.size() - 1);
        }
        this.stack.add(createVector);
        return (int) createVector.iValue;
    }

    public int endVector(String key, int start, boolean typed, boolean fixed) {
        Value createVector = createVector(putKey(key), start, this.stack.size() - start, typed, fixed, null);
        while (this.stack.size() > start) {
            this.stack.remove(r1.size() - 1);
        }
        this.stack.add(createVector);
        return (int) createVector.iValue;
    }

    public ByteBuffer finish() {
        if (this.stack.size() != 1) {
            throw new AssertionError();
        }
        int align = align(this.stack.get(0).elemWidth(this.bb.writePosition(), 0));
        writeAny(this.stack.get(0), align);
        this.bb.put(this.stack.get(0).storedPackedType());
        this.bb.put((byte) align);
        this.finished = true;
        return ByteBuffer.wrap(this.bb.data(), 0, this.bb.writePosition());
    }

    public ReadWriteBuf getBuffer() {
        if (this.finished) {
            return this.bb;
        }
        throw new AssertionError();
    }

    public int putBlob(String key, byte[] val) {
        Value writeBlob = writeBlob(putKey(key), val, 25, false);
        this.stack.add(writeBlob);
        return (int) writeBlob.iValue;
    }

    public int putBlob(byte[] value) {
        return putBlob(null, value);
    }

    public void putBoolean(String key, boolean val) {
        this.stack.add(Value.bool(putKey(key), val));
    }

    public void putBoolean(boolean val) {
        putBoolean(null, val);
    }

    public void putFloat(double value) {
        putFloat((String) null, value);
    }

    public void putFloat(float value) {
        putFloat((String) null, value);
    }

    public void putFloat(String key, double val) {
        this.stack.add(Value.float64(putKey(key), val));
    }

    public void putFloat(String key, float val) {
        this.stack.add(Value.float32(putKey(key), val));
    }

    public void putInt(int val) {
        putInt((String) null, val);
    }

    public void putInt(long value) {
        putInt((String) null, value);
    }

    public void putInt(String key, int val) {
        putInt(key, val);
    }

    public void putInt(String key, long val) {
        int putKey = putKey(key);
        if (-128 <= val && val <= 127) {
            this.stack.add(Value.int8(putKey, (int) val));
            return;
        }
        if (-32768 <= val && val <= 32767) {
            this.stack.add(Value.int16(putKey, (int) val));
        } else if (-2147483648L > val || val > 2147483647L) {
            this.stack.add(Value.int64(putKey, val));
        } else {
            this.stack.add(Value.int32(putKey, (int) val));
        }
    }

    public int putString(String value) {
        return putString(null, value);
    }

    public int putString(String key, String val) {
        int putKey = putKey(key);
        if ((this.flags & 2) == 0) {
            Value writeString = writeString(putKey, val);
            this.stack.add(writeString);
            return (int) writeString.iValue;
        }
        Integer num = this.stringPool.get(val);
        if (num != null) {
            this.stack.add(Value.blob(putKey, num.intValue(), 5, widthUInBits(val.length())));
            return num.intValue();
        }
        Value writeString2 = writeString(putKey, val);
        this.stringPool.put(val, Integer.valueOf((int) writeString2.iValue));
        this.stack.add(writeString2);
        return (int) writeString2.iValue;
    }

    public void putUInt(int value) {
        putUInt(null, value);
    }

    public void putUInt(long value) {
        putUInt(null, value);
    }

    public void putUInt64(BigInteger value) {
        putUInt64(null, value.longValue());
    }

    public int startMap() {
        return this.stack.size();
    }

    public int startVector() {
        return this.stack.size();
    }
}
