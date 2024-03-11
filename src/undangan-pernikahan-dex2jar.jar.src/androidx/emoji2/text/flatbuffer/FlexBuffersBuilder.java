package androidx.emoji2.text.flatbuffer;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class FlexBuffersBuilder
{
  static final boolean $assertionsDisabled = false;
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
  private boolean finished = false;
  private final int flags;
  private Comparator<Value> keyComparator = new Comparator()
  {
    public int compare(FlexBuffersBuilder.Value paramAnonymousValue1, FlexBuffersBuilder.Value paramAnonymousValue2)
    {
      int j = paramAnonymousValue1.key;
      int i = paramAnonymousValue2.key;
      for (;;)
      {
        int k = FlexBuffersBuilder.this.bb.get(j);
        int m = FlexBuffersBuilder.this.bb.get(i);
        if (k == 0) {
          return k - m;
        }
        j++;
        i++;
        if (k != m) {
          return k - m;
        }
      }
    }
  };
  private final HashMap<String, Integer> keyPool = new HashMap();
  private final ArrayList<Value> stack = new ArrayList();
  private final HashMap<String, Integer> stringPool = new HashMap();
  
  public FlexBuffersBuilder()
  {
    this(256);
  }
  
  public FlexBuffersBuilder(int paramInt)
  {
    this(new ArrayReadWriteBuf(paramInt), 1);
  }
  
  public FlexBuffersBuilder(ReadWriteBuf paramReadWriteBuf, int paramInt)
  {
    this.bb = paramReadWriteBuf;
    this.flags = paramInt;
  }
  
  public FlexBuffersBuilder(ByteBuffer paramByteBuffer)
  {
    this(paramByteBuffer, 1);
  }
  
  @Deprecated
  public FlexBuffersBuilder(ByteBuffer paramByteBuffer, int paramInt)
  {
    this(new ArrayReadWriteBuf(paramByteBuffer.array()), paramInt);
  }
  
  private int align(int paramInt)
  {
    int i = 1 << paramInt;
    for (paramInt = Value.paddingBytes(this.bb.writePosition(), i); paramInt != 0; paramInt--) {
      this.bb.put((byte)0);
    }
    return i;
  }
  
  private Value createKeyVector(int paramInt1, int paramInt2)
  {
    int i = Math.max(0, widthUInBits(paramInt2));
    for (int j = paramInt1; j < this.stack.size(); j++) {
      i = Math.max(i, Value.elemWidth(4, 0, ((Value)this.stack.get(j)).key, this.bb.writePosition(), j + 1));
    }
    j = align(i);
    writeInt(paramInt2, j);
    paramInt2 = this.bb.writePosition();
    while (paramInt1 < this.stack.size()) {
      if (((Value)this.stack.get(paramInt1)).key != -1)
      {
        writeOffset(((Value)this.stack.get(paramInt1)).key, j);
        paramInt1++;
      }
      else
      {
        throw new AssertionError();
      }
    }
    return new Value(-1, FlexBuffers.toTypedVector(4, 0), i, paramInt2);
  }
  
  private Value createVector(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean1, boolean paramBoolean2, Value paramValue)
  {
    if ((paramBoolean2) && (!paramBoolean1)) {
      throw new AssertionError();
    }
    int i = widthUInBits(paramInt3);
    int n = 0;
    int j = Math.max(0, i);
    int k = 1;
    i = j;
    if (paramValue != null)
    {
      i = Math.max(j, paramValue.elemWidth(this.bb.writePosition(), 0));
      k = 1 + 2;
    }
    j = 4;
    for (int m = paramInt2; m < this.stack.size(); m++)
    {
      i = Math.max(i, ((Value)this.stack.get(m)).elemWidth(this.bb.writePosition(), m + k));
      if (paramBoolean1) {
        if (m == paramInt2)
        {
          j = ((Value)this.stack.get(m)).type;
          if (!FlexBuffers.isTypedVectorElementType(j)) {
            throw new FlexBuffers.FlexBufferException("TypedVector does not support this element type");
          }
        }
        else if (j != ((Value)this.stack.get(m)).type)
        {
          throw new AssertionError();
        }
      }
    }
    if ((paramBoolean2) && (!FlexBuffers.isTypedVectorElementType(j))) {
      throw new AssertionError();
    }
    int i1 = align(i);
    if (paramValue != null)
    {
      writeOffset(paramValue.iValue, i1);
      writeInt(1L << paramValue.minBitWidth, i1);
    }
    if (!paramBoolean2) {
      writeInt(paramInt3, i1);
    }
    m = this.bb.writePosition();
    for (k = paramInt2; k < this.stack.size(); k++) {
      writeAny((Value)this.stack.get(k), i1);
    }
    if (!paramBoolean1) {
      while (paramInt2 < this.stack.size())
      {
        this.bb.put(((Value)this.stack.get(paramInt2)).storedPackedType(i));
        paramInt2++;
      }
    }
    if (paramValue != null)
    {
      paramInt2 = 9;
    }
    else if (paramBoolean1)
    {
      paramInt2 = n;
      if (paramBoolean2) {
        paramInt2 = paramInt3;
      }
      paramInt2 = FlexBuffers.toTypedVector(j, paramInt2);
    }
    else
    {
      paramInt2 = 10;
    }
    return new Value(paramInt1, paramInt2, i, m);
  }
  
  private int putKey(String paramString)
  {
    if (paramString == null) {
      return -1;
    }
    int i = this.bb.writePosition();
    Object localObject;
    if ((this.flags & 0x1) != 0)
    {
      localObject = (Integer)this.keyPool.get(paramString);
      if (localObject == null)
      {
        localObject = paramString.getBytes(StandardCharsets.UTF_8);
        this.bb.put((byte[])localObject, 0, localObject.length);
        this.bb.put((byte)0);
        this.keyPool.put(paramString, Integer.valueOf(i));
      }
      else
      {
        i = ((Integer)localObject).intValue();
      }
    }
    else
    {
      localObject = paramString.getBytes(StandardCharsets.UTF_8);
      this.bb.put((byte[])localObject, 0, localObject.length);
      this.bb.put((byte)0);
      this.keyPool.put(paramString, Integer.valueOf(i));
    }
    return i;
  }
  
  private void putUInt(String paramString, long paramLong)
  {
    int j = putKey(paramString);
    int i = widthUInBits(paramLong);
    if (i == 0) {
      paramString = Value.uInt8(j, (int)paramLong);
    } else if (i == 1) {
      paramString = Value.uInt16(j, (int)paramLong);
    } else if (i == 2) {
      paramString = Value.uInt32(j, (int)paramLong);
    } else {
      paramString = Value.uInt64(j, paramLong);
    }
    this.stack.add(paramString);
  }
  
  private void putUInt64(String paramString, long paramLong)
  {
    this.stack.add(Value.uInt64(putKey(paramString), paramLong));
  }
  
  static int widthUInBits(long paramLong)
  {
    if (paramLong <= FlexBuffers.Unsigned.byteToUnsignedInt((byte)-1)) {
      return 0;
    }
    if (paramLong <= FlexBuffers.Unsigned.shortToUnsignedInt((short)-1)) {
      return 1;
    }
    if (paramLong <= FlexBuffers.Unsigned.intToUnsignedLong(-1)) {
      return 2;
    }
    return 3;
  }
  
  private void writeAny(Value paramValue, int paramInt)
  {
    switch (paramValue.type)
    {
    default: 
      writeOffset(paramValue.iValue, paramInt);
      break;
    case 3: 
      writeDouble(paramValue.dValue, paramInt);
      break;
    case 0: 
    case 1: 
    case 2: 
    case 26: 
      writeInt(paramValue.iValue, paramInt);
    }
  }
  
  private Value writeBlob(int paramInt1, byte[] paramArrayOfByte, int paramInt2, boolean paramBoolean)
  {
    int i = widthUInBits(paramArrayOfByte.length);
    int j = align(i);
    writeInt(paramArrayOfByte.length, j);
    j = this.bb.writePosition();
    this.bb.put(paramArrayOfByte, 0, paramArrayOfByte.length);
    if (paramBoolean) {
      this.bb.put((byte)0);
    }
    return Value.blob(paramInt1, j, paramInt2, i);
  }
  
  private void writeDouble(double paramDouble, int paramInt)
  {
    if (paramInt == 4) {
      this.bb.putFloat((float)paramDouble);
    } else if (paramInt == 8) {
      this.bb.putDouble(paramDouble);
    }
  }
  
  private void writeInt(long paramLong, int paramInt)
  {
    switch (paramInt)
    {
    default: 
      break;
    case 8: 
      this.bb.putLong(paramLong);
      break;
    case 4: 
      this.bb.putInt((int)paramLong);
      break;
    case 2: 
      this.bb.putShort((short)(int)paramLong);
      break;
    case 1: 
      this.bb.put((byte)(int)paramLong);
    }
  }
  
  private void writeOffset(long paramLong, int paramInt)
  {
    int i = (int)(this.bb.writePosition() - paramLong);
    if ((paramInt != 8) && (i >= 1L << paramInt * 8)) {
      throw new AssertionError();
    }
    writeInt(i, paramInt);
  }
  
  private Value writeString(int paramInt, String paramString)
  {
    return writeBlob(paramInt, paramString.getBytes(StandardCharsets.UTF_8), 5, true);
  }
  
  public int endMap(String paramString, int paramInt)
  {
    int i = putKey(paramString);
    paramString = this.stack;
    Collections.sort(paramString.subList(paramInt, paramString.size()), this.keyComparator);
    paramString = createKeyVector(paramInt, this.stack.size() - paramInt);
    paramString = createVector(i, paramInt, this.stack.size() - paramInt, false, false, paramString);
    while (this.stack.size() > paramInt)
    {
      ArrayList localArrayList = this.stack;
      localArrayList.remove(localArrayList.size() - 1);
    }
    this.stack.add(paramString);
    return (int)paramString.iValue;
  }
  
  public int endVector(String paramString, int paramInt, boolean paramBoolean1, boolean paramBoolean2)
  {
    paramString = createVector(putKey(paramString), paramInt, this.stack.size() - paramInt, paramBoolean1, paramBoolean2, null);
    while (this.stack.size() > paramInt)
    {
      ArrayList localArrayList = this.stack;
      localArrayList.remove(localArrayList.size() - 1);
    }
    this.stack.add(paramString);
    return (int)paramString.iValue;
  }
  
  public ByteBuffer finish()
  {
    if (this.stack.size() == 1)
    {
      int i = align(((Value)this.stack.get(0)).elemWidth(this.bb.writePosition(), 0));
      writeAny((Value)this.stack.get(0), i);
      this.bb.put(((Value)this.stack.get(0)).storedPackedType());
      this.bb.put((byte)i);
      this.finished = true;
      return ByteBuffer.wrap(this.bb.data(), 0, this.bb.writePosition());
    }
    throw new AssertionError();
  }
  
  public ReadWriteBuf getBuffer()
  {
    if (this.finished) {
      return this.bb;
    }
    throw new AssertionError();
  }
  
  public int putBlob(String paramString, byte[] paramArrayOfByte)
  {
    paramString = writeBlob(putKey(paramString), paramArrayOfByte, 25, false);
    this.stack.add(paramString);
    return (int)paramString.iValue;
  }
  
  public int putBlob(byte[] paramArrayOfByte)
  {
    return putBlob(null, paramArrayOfByte);
  }
  
  public void putBoolean(String paramString, boolean paramBoolean)
  {
    this.stack.add(Value.bool(putKey(paramString), paramBoolean));
  }
  
  public void putBoolean(boolean paramBoolean)
  {
    putBoolean(null, paramBoolean);
  }
  
  public void putFloat(double paramDouble)
  {
    putFloat(null, paramDouble);
  }
  
  public void putFloat(float paramFloat)
  {
    putFloat(null, paramFloat);
  }
  
  public void putFloat(String paramString, double paramDouble)
  {
    this.stack.add(Value.float64(putKey(paramString), paramDouble));
  }
  
  public void putFloat(String paramString, float paramFloat)
  {
    this.stack.add(Value.float32(putKey(paramString), paramFloat));
  }
  
  public void putInt(int paramInt)
  {
    putInt(null, paramInt);
  }
  
  public void putInt(long paramLong)
  {
    putInt(null, paramLong);
  }
  
  public void putInt(String paramString, int paramInt)
  {
    putInt(paramString, paramInt);
  }
  
  public void putInt(String paramString, long paramLong)
  {
    int i = putKey(paramString);
    if ((-128L <= paramLong) && (paramLong <= 127L)) {
      this.stack.add(Value.int8(i, (int)paramLong));
    } else if ((-32768L <= paramLong) && (paramLong <= 32767L)) {
      this.stack.add(Value.int16(i, (int)paramLong));
    } else if ((-2147483648L <= paramLong) && (paramLong <= 2147483647L)) {
      this.stack.add(Value.int32(i, (int)paramLong));
    } else {
      this.stack.add(Value.int64(i, paramLong));
    }
  }
  
  public int putString(String paramString)
  {
    return putString(null, paramString);
  }
  
  public int putString(String paramString1, String paramString2)
  {
    int i = putKey(paramString1);
    if ((this.flags & 0x2) != 0)
    {
      paramString1 = (Integer)this.stringPool.get(paramString2);
      if (paramString1 == null)
      {
        paramString1 = writeString(i, paramString2);
        this.stringPool.put(paramString2, Integer.valueOf((int)paramString1.iValue));
        this.stack.add(paramString1);
        return (int)paramString1.iValue;
      }
      int j = widthUInBits(paramString2.length());
      this.stack.add(Value.blob(i, paramString1.intValue(), 5, j));
      return paramString1.intValue();
    }
    paramString1 = writeString(i, paramString2);
    this.stack.add(paramString1);
    return (int)paramString1.iValue;
  }
  
  public void putUInt(int paramInt)
  {
    putUInt(null, paramInt);
  }
  
  public void putUInt(long paramLong)
  {
    putUInt(null, paramLong);
  }
  
  public void putUInt64(BigInteger paramBigInteger)
  {
    putUInt64(null, paramBigInteger.longValue());
  }
  
  public int startMap()
  {
    return this.stack.size();
  }
  
  public int startVector()
  {
    return this.stack.size();
  }
  
  private static class Value
  {
    static final boolean $assertionsDisabled = false;
    final double dValue;
    long iValue;
    int key;
    final int minBitWidth;
    final int type;
    
    Value(int paramInt1, int paramInt2, int paramInt3, double paramDouble)
    {
      this.key = paramInt1;
      this.type = paramInt2;
      this.minBitWidth = paramInt3;
      this.dValue = paramDouble;
      this.iValue = Long.MIN_VALUE;
    }
    
    Value(int paramInt1, int paramInt2, int paramInt3, long paramLong)
    {
      this.key = paramInt1;
      this.type = paramInt2;
      this.minBitWidth = paramInt3;
      this.iValue = paramLong;
      this.dValue = Double.MIN_VALUE;
    }
    
    static Value blob(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      return new Value(paramInt1, paramInt3, paramInt4, paramInt2);
    }
    
    static Value bool(int paramInt, boolean paramBoolean)
    {
      long l;
      if (paramBoolean) {
        l = 1L;
      } else {
        l = 0L;
      }
      return new Value(paramInt, 26, 0, l);
    }
    
    private int elemWidth(int paramInt1, int paramInt2)
    {
      return elemWidth(this.type, this.minBitWidth, this.iValue, paramInt1, paramInt2);
    }
    
    private static int elemWidth(int paramInt1, int paramInt2, long paramLong, int paramInt3, int paramInt4)
    {
      if (FlexBuffers.isTypeInline(paramInt1)) {
        return paramInt2;
      }
      paramInt1 = 1;
      while (paramInt1 <= 32)
      {
        paramInt2 = FlexBuffersBuilder.widthUInBits((int)(paddingBytes(paramInt3, paramInt1) + paramInt3 + paramInt4 * paramInt1 - paramLong));
        if (1L << paramInt2 == paramInt1) {
          return paramInt2;
        }
        paramInt1 *= 2;
      }
      throw new AssertionError();
    }
    
    static Value float32(int paramInt, float paramFloat)
    {
      return new Value(paramInt, 3, 2, paramFloat);
    }
    
    static Value float64(int paramInt, double paramDouble)
    {
      return new Value(paramInt, 3, 3, paramDouble);
    }
    
    static Value int16(int paramInt1, int paramInt2)
    {
      return new Value(paramInt1, 1, 1, paramInt2);
    }
    
    static Value int32(int paramInt1, int paramInt2)
    {
      return new Value(paramInt1, 1, 2, paramInt2);
    }
    
    static Value int64(int paramInt, long paramLong)
    {
      return new Value(paramInt, 1, 3, paramLong);
    }
    
    static Value int8(int paramInt1, int paramInt2)
    {
      return new Value(paramInt1, 1, 0, paramInt2);
    }
    
    private static byte packedType(int paramInt1, int paramInt2)
    {
      return (byte)(paramInt2 << 2 | paramInt1);
    }
    
    private static int paddingBytes(int paramInt1, int paramInt2)
    {
      return (paramInt1 ^ 0xFFFFFFFF) + 1 & paramInt2 - 1;
    }
    
    private byte storedPackedType()
    {
      return storedPackedType(0);
    }
    
    private byte storedPackedType(int paramInt)
    {
      return packedType(storedWidth(paramInt), this.type);
    }
    
    private int storedWidth(int paramInt)
    {
      if (FlexBuffers.isTypeInline(this.type)) {
        return Math.max(this.minBitWidth, paramInt);
      }
      return this.minBitWidth;
    }
    
    static Value uInt16(int paramInt1, int paramInt2)
    {
      return new Value(paramInt1, 2, 1, paramInt2);
    }
    
    static Value uInt32(int paramInt1, int paramInt2)
    {
      return new Value(paramInt1, 2, 2, paramInt2);
    }
    
    static Value uInt64(int paramInt, long paramLong)
    {
      return new Value(paramInt, 2, 3, paramLong);
    }
    
    static Value uInt8(int paramInt1, int paramInt2)
    {
      return new Value(paramInt1, 2, 0, paramInt2);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/emoji2/text/flatbuffer/FlexBuffersBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */