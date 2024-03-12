package androidx.emoji2.text.flatbuffer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class FlatBufferBuilder
{
  static final boolean $assertionsDisabled = false;
  ByteBuffer bb;
  ByteBufferFactory bb_factory;
  boolean finished = false;
  boolean force_defaults = false;
  int minalign = 1;
  boolean nested = false;
  int num_vtables = 0;
  int object_start;
  int space;
  final Utf8 utf8;
  int vector_num_elems = 0;
  int[] vtable = null;
  int vtable_in_use = 0;
  int[] vtables = new int[16];
  
  public FlatBufferBuilder()
  {
    this(1024);
  }
  
  public FlatBufferBuilder(int paramInt)
  {
    this(paramInt, HeapByteBufferFactory.INSTANCE, null, Utf8.getDefault());
  }
  
  public FlatBufferBuilder(int paramInt, ByteBufferFactory paramByteBufferFactory)
  {
    this(paramInt, paramByteBufferFactory, null, Utf8.getDefault());
  }
  
  public FlatBufferBuilder(int paramInt, ByteBufferFactory paramByteBufferFactory, ByteBuffer paramByteBuffer, Utf8 paramUtf8)
  {
    int i = paramInt;
    if (paramInt <= 0) {
      i = 1;
    }
    this.bb_factory = paramByteBufferFactory;
    if (paramByteBuffer != null)
    {
      this.bb = paramByteBuffer;
      paramByteBuffer.clear();
      this.bb.order(ByteOrder.LITTLE_ENDIAN);
    }
    else
    {
      this.bb = paramByteBufferFactory.newByteBuffer(i);
    }
    this.utf8 = paramUtf8;
    this.space = this.bb.capacity();
  }
  
  public FlatBufferBuilder(ByteBuffer paramByteBuffer)
  {
    this(paramByteBuffer, new HeapByteBufferFactory());
  }
  
  public FlatBufferBuilder(ByteBuffer paramByteBuffer, ByteBufferFactory paramByteBufferFactory)
  {
    this(paramByteBuffer.capacity(), paramByteBufferFactory, paramByteBuffer, Utf8.getDefault());
  }
  
  @Deprecated
  private int dataStart()
  {
    finished();
    return this.space;
  }
  
  static ByteBuffer growByteBuffer(ByteBuffer paramByteBuffer, ByteBufferFactory paramByteBufferFactory)
  {
    int j = paramByteBuffer.capacity();
    if ((0xC0000000 & j) == 0)
    {
      int i;
      if (j == 0) {
        i = 1;
      } else {
        i = j << 1;
      }
      paramByteBuffer.position(0);
      paramByteBufferFactory = paramByteBufferFactory.newByteBuffer(i);
      paramByteBufferFactory.position(paramByteBufferFactory.clear().capacity() - j);
      paramByteBufferFactory.put(paramByteBuffer);
      return paramByteBufferFactory;
    }
    throw new AssertionError("FlatBuffers: cannot grow buffer beyond 2 gigabytes.");
  }
  
  public static boolean isFieldPresent(Table paramTable, int paramInt)
  {
    boolean bool;
    if (paramTable.__offset(paramInt) != 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void Nested(int paramInt)
  {
    if (paramInt == offset()) {
      return;
    }
    throw new AssertionError("FlatBuffers: struct must be serialized inline.");
  }
  
  public void addBoolean(int paramInt, boolean paramBoolean1, boolean paramBoolean2)
  {
    if ((this.force_defaults) || (paramBoolean1 != paramBoolean2))
    {
      addBoolean(paramBoolean1);
      slot(paramInt);
    }
  }
  
  public void addBoolean(boolean paramBoolean)
  {
    prep(1, 0);
    putBoolean(paramBoolean);
  }
  
  public void addByte(byte paramByte)
  {
    prep(1, 0);
    putByte(paramByte);
  }
  
  public void addByte(int paramInt1, byte paramByte, int paramInt2)
  {
    if ((this.force_defaults) || (paramByte != paramInt2))
    {
      addByte(paramByte);
      slot(paramInt1);
    }
  }
  
  public void addDouble(double paramDouble)
  {
    prep(8, 0);
    putDouble(paramDouble);
  }
  
  public void addDouble(int paramInt, double paramDouble1, double paramDouble2)
  {
    if ((this.force_defaults) || (paramDouble1 != paramDouble2))
    {
      addDouble(paramDouble1);
      slot(paramInt);
    }
  }
  
  public void addFloat(float paramFloat)
  {
    prep(4, 0);
    putFloat(paramFloat);
  }
  
  public void addFloat(int paramInt, float paramFloat, double paramDouble)
  {
    if ((this.force_defaults) || (paramFloat != paramDouble))
    {
      addFloat(paramFloat);
      slot(paramInt);
    }
  }
  
  public void addInt(int paramInt)
  {
    prep(4, 0);
    putInt(paramInt);
  }
  
  public void addInt(int paramInt1, int paramInt2, int paramInt3)
  {
    if ((this.force_defaults) || (paramInt2 != paramInt3))
    {
      addInt(paramInt2);
      slot(paramInt1);
    }
  }
  
  public void addLong(int paramInt, long paramLong1, long paramLong2)
  {
    if ((this.force_defaults) || (paramLong1 != paramLong2))
    {
      addLong(paramLong1);
      slot(paramInt);
    }
  }
  
  public void addLong(long paramLong)
  {
    prep(8, 0);
    putLong(paramLong);
  }
  
  public void addOffset(int paramInt)
  {
    prep(4, 0);
    if (paramInt <= offset())
    {
      putInt(offset() - paramInt + 4);
      return;
    }
    throw new AssertionError();
  }
  
  public void addOffset(int paramInt1, int paramInt2, int paramInt3)
  {
    if ((this.force_defaults) || (paramInt2 != paramInt3))
    {
      addOffset(paramInt2);
      slot(paramInt1);
    }
  }
  
  public void addShort(int paramInt1, short paramShort, int paramInt2)
  {
    if ((this.force_defaults) || (paramShort != paramInt2))
    {
      addShort(paramShort);
      slot(paramInt1);
    }
  }
  
  public void addShort(short paramShort)
  {
    prep(2, 0);
    putShort(paramShort);
  }
  
  public void addStruct(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt2 != paramInt3)
    {
      Nested(paramInt2);
      slot(paramInt1);
    }
  }
  
  public void clear()
  {
    this.space = this.bb.capacity();
    this.bb.clear();
    this.minalign = 1;
    for (;;)
    {
      int i = this.vtable_in_use;
      if (i <= 0) {
        break;
      }
      int[] arrayOfInt = this.vtable;
      i--;
      this.vtable_in_use = i;
      arrayOfInt[i] = 0;
    }
    this.vtable_in_use = 0;
    this.nested = false;
    this.finished = false;
    this.object_start = 0;
    this.num_vtables = 0;
    this.vector_num_elems = 0;
  }
  
  public int createByteVector(ByteBuffer paramByteBuffer)
  {
    int i = paramByteBuffer.remaining();
    startVector(1, i, 1);
    ByteBuffer localByteBuffer = this.bb;
    i = this.space - i;
    this.space = i;
    localByteBuffer.position(i);
    this.bb.put(paramByteBuffer);
    return endVector();
  }
  
  public int createByteVector(byte[] paramArrayOfByte)
  {
    int i = paramArrayOfByte.length;
    startVector(1, i, 1);
    ByteBuffer localByteBuffer = this.bb;
    i = this.space - i;
    this.space = i;
    localByteBuffer.position(i);
    this.bb.put(paramArrayOfByte);
    return endVector();
  }
  
  public int createByteVector(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    startVector(1, paramInt2, 1);
    ByteBuffer localByteBuffer = this.bb;
    int i = this.space - paramInt2;
    this.space = i;
    localByteBuffer.position(i);
    this.bb.put(paramArrayOfByte, paramInt1, paramInt2);
    return endVector();
  }
  
  public <T extends Table> int createSortedVectorOfTables(T paramT, int[] paramArrayOfInt)
  {
    paramT.sortTables(paramArrayOfInt, this.bb);
    return createVectorOfTables(paramArrayOfInt);
  }
  
  public int createString(CharSequence paramCharSequence)
  {
    int i = this.utf8.encodedLength(paramCharSequence);
    addByte((byte)0);
    startVector(1, i, 1);
    ByteBuffer localByteBuffer = this.bb;
    i = this.space - i;
    this.space = i;
    localByteBuffer.position(i);
    this.utf8.encodeUtf8(paramCharSequence, this.bb);
    return endVector();
  }
  
  public int createString(ByteBuffer paramByteBuffer)
  {
    int i = paramByteBuffer.remaining();
    addByte((byte)0);
    startVector(1, i, 1);
    ByteBuffer localByteBuffer = this.bb;
    i = this.space - i;
    this.space = i;
    localByteBuffer.position(i);
    this.bb.put(paramByteBuffer);
    return endVector();
  }
  
  public ByteBuffer createUnintializedVector(int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramInt1 * paramInt2;
    startVector(paramInt1, paramInt2, paramInt3);
    ByteBuffer localByteBuffer = this.bb;
    paramInt1 = this.space - i;
    this.space = paramInt1;
    localByteBuffer.position(paramInt1);
    localByteBuffer = this.bb.slice().order(ByteOrder.LITTLE_ENDIAN);
    localByteBuffer.limit(i);
    return localByteBuffer;
  }
  
  public int createVectorOfTables(int[] paramArrayOfInt)
  {
    notNested();
    startVector(4, paramArrayOfInt.length, 4);
    for (int i = paramArrayOfInt.length - 1; i >= 0; i--) {
      addOffset(paramArrayOfInt[i]);
    }
    return endVector();
  }
  
  public ByteBuffer dataBuffer()
  {
    finished();
    return this.bb;
  }
  
  public int endTable()
  {
    if ((this.vtable != null) && (this.nested))
    {
      addInt(0);
      int m = offset();
      for (int i = this.vtable_in_use - 1; (i >= 0) && (this.vtable[i] == 0); i--) {}
      for (int j = i;; j = k - 1)
      {
        k = j;
        if (k < 0) {
          break;
        }
        j = this.vtable[k];
        if (j != 0) {
          j = m - j;
        } else {
          j = 0;
        }
        addShort((short)j);
      }
      addShort((short)(m - this.object_start));
      addShort((short)((i + 1 + 2) * 2));
      int k = 0;
      label230:
      for (i = 0;; i++)
      {
        j = k;
        if (i >= this.num_vtables) {
          break;
        }
        int n = this.bb.capacity() - this.vtables[i];
        int i2 = this.space;
        int i1 = this.bb.getShort(n);
        if (i1 == this.bb.getShort(i2))
        {
          for (j = 2; j < i1; j += 2) {
            if (this.bb.getShort(n + j) != this.bb.getShort(i2 + j)) {
              break label230;
            }
          }
          j = this.vtables[i];
          break;
        }
      }
      if (j != 0)
      {
        i = this.bb.capacity() - m;
        this.space = i;
        this.bb.putInt(i, j - m);
      }
      else
      {
        i = this.num_vtables;
        Object localObject = this.vtables;
        if (i == localObject.length) {
          this.vtables = Arrays.copyOf((int[])localObject, i * 2);
        }
        localObject = this.vtables;
        i = this.num_vtables;
        this.num_vtables = (i + 1);
        localObject[i] = offset();
        localObject = this.bb;
        ((ByteBuffer)localObject).putInt(((ByteBuffer)localObject).capacity() - m, offset() - m);
      }
      this.nested = false;
      return m;
    }
    throw new AssertionError("FlatBuffers: endTable called without startTable");
  }
  
  public int endVector()
  {
    if (this.nested)
    {
      this.nested = false;
      putInt(this.vector_num_elems);
      return offset();
    }
    throw new AssertionError("FlatBuffers: endVector called without startVector");
  }
  
  public void finish(int paramInt)
  {
    finish(paramInt, false);
  }
  
  public void finish(int paramInt, String paramString)
  {
    finish(paramInt, paramString, false);
  }
  
  protected void finish(int paramInt, String paramString, boolean paramBoolean)
  {
    int j = this.minalign;
    int i;
    if (paramBoolean) {
      i = 4;
    } else {
      i = 0;
    }
    prep(j, i + 8);
    if (paramString.length() == 4)
    {
      for (i = 3; i >= 0; i--) {
        addByte((byte)paramString.charAt(i));
      }
      finish(paramInt, paramBoolean);
      return;
    }
    throw new AssertionError("FlatBuffers: file identifier must be length 4");
  }
  
  protected void finish(int paramInt, boolean paramBoolean)
  {
    int j = this.minalign;
    int i;
    if (paramBoolean) {
      i = 4;
    } else {
      i = 0;
    }
    prep(j, i + 4);
    addOffset(paramInt);
    if (paramBoolean) {
      addInt(this.bb.capacity() - this.space);
    }
    this.bb.position(this.space);
    this.finished = true;
  }
  
  public void finishSizePrefixed(int paramInt)
  {
    finish(paramInt, true);
  }
  
  public void finishSizePrefixed(int paramInt, String paramString)
  {
    finish(paramInt, paramString, true);
  }
  
  public void finished()
  {
    if (this.finished) {
      return;
    }
    throw new AssertionError("FlatBuffers: you can only access the serialized buffer after it has been finished by FlatBufferBuilder.finish().");
  }
  
  public FlatBufferBuilder forceDefaults(boolean paramBoolean)
  {
    this.force_defaults = paramBoolean;
    return this;
  }
  
  public FlatBufferBuilder init(ByteBuffer paramByteBuffer, ByteBufferFactory paramByteBufferFactory)
  {
    this.bb_factory = paramByteBufferFactory;
    this.bb = paramByteBuffer;
    paramByteBuffer.clear();
    this.bb.order(ByteOrder.LITTLE_ENDIAN);
    this.minalign = 1;
    this.space = this.bb.capacity();
    this.vtable_in_use = 0;
    this.nested = false;
    this.finished = false;
    this.object_start = 0;
    this.num_vtables = 0;
    this.vector_num_elems = 0;
    return this;
  }
  
  public void notNested()
  {
    if (!this.nested) {
      return;
    }
    throw new AssertionError("FlatBuffers: object serialization must not be nested.");
  }
  
  public int offset()
  {
    return this.bb.capacity() - this.space;
  }
  
  public void pad(int paramInt)
  {
    for (int i = 0; i < paramInt; i++)
    {
      ByteBuffer localByteBuffer = this.bb;
      int j = this.space - 1;
      this.space = j;
      localByteBuffer.put(j, (byte)0);
    }
  }
  
  public void prep(int paramInt1, int paramInt2)
  {
    if (paramInt1 > this.minalign) {
      this.minalign = paramInt1;
    }
    int i = (this.bb.capacity() - this.space + paramInt2 ^ 0xFFFFFFFF) + 1 & paramInt1 - 1;
    while (this.space < i + paramInt1 + paramInt2)
    {
      int j = this.bb.capacity();
      ByteBuffer localByteBuffer1 = this.bb;
      ByteBuffer localByteBuffer2 = growByteBuffer(localByteBuffer1, this.bb_factory);
      this.bb = localByteBuffer2;
      if (localByteBuffer1 != localByteBuffer2) {
        this.bb_factory.releaseByteBuffer(localByteBuffer1);
      }
      this.space += this.bb.capacity() - j;
    }
    pad(i);
  }
  
  public void putBoolean(boolean paramBoolean)
  {
    ByteBuffer localByteBuffer = this.bb;
    int i = this.space - 1;
    this.space = i;
    localByteBuffer.put(i, (byte)paramBoolean);
  }
  
  public void putByte(byte paramByte)
  {
    ByteBuffer localByteBuffer = this.bb;
    int i = this.space - 1;
    this.space = i;
    localByteBuffer.put(i, paramByte);
  }
  
  public void putDouble(double paramDouble)
  {
    ByteBuffer localByteBuffer = this.bb;
    int i = this.space - 8;
    this.space = i;
    localByteBuffer.putDouble(i, paramDouble);
  }
  
  public void putFloat(float paramFloat)
  {
    ByteBuffer localByteBuffer = this.bb;
    int i = this.space - 4;
    this.space = i;
    localByteBuffer.putFloat(i, paramFloat);
  }
  
  public void putInt(int paramInt)
  {
    ByteBuffer localByteBuffer = this.bb;
    int i = this.space - 4;
    this.space = i;
    localByteBuffer.putInt(i, paramInt);
  }
  
  public void putLong(long paramLong)
  {
    ByteBuffer localByteBuffer = this.bb;
    int i = this.space - 8;
    this.space = i;
    localByteBuffer.putLong(i, paramLong);
  }
  
  public void putShort(short paramShort)
  {
    ByteBuffer localByteBuffer = this.bb;
    int i = this.space - 2;
    this.space = i;
    localByteBuffer.putShort(i, paramShort);
  }
  
  public void required(int paramInt1, int paramInt2)
  {
    paramInt1 = this.bb.capacity() - paramInt1;
    int i = this.bb.getInt(paramInt1);
    if (this.bb.getShort(paramInt1 - i + paramInt2) != 0) {
      paramInt1 = 1;
    } else {
      paramInt1 = 0;
    }
    if (paramInt1 != 0) {
      return;
    }
    throw new AssertionError("FlatBuffers: field " + paramInt2 + " must be set");
  }
  
  public byte[] sizedByteArray()
  {
    return sizedByteArray(this.space, this.bb.capacity() - this.space);
  }
  
  public byte[] sizedByteArray(int paramInt1, int paramInt2)
  {
    finished();
    byte[] arrayOfByte = new byte[paramInt2];
    this.bb.position(paramInt1);
    this.bb.get(arrayOfByte);
    return arrayOfByte;
  }
  
  public InputStream sizedInputStream()
  {
    finished();
    ByteBuffer localByteBuffer = this.bb.duplicate();
    localByteBuffer.position(this.space);
    localByteBuffer.limit(this.bb.capacity());
    return new ByteBufferBackedInputStream(localByteBuffer);
  }
  
  public void slot(int paramInt)
  {
    this.vtable[paramInt] = offset();
  }
  
  public void startTable(int paramInt)
  {
    notNested();
    int[] arrayOfInt = this.vtable;
    if ((arrayOfInt == null) || (arrayOfInt.length < paramInt)) {
      this.vtable = new int[paramInt];
    }
    this.vtable_in_use = paramInt;
    Arrays.fill(this.vtable, 0, paramInt, 0);
    this.nested = true;
    this.object_start = offset();
  }
  
  public void startVector(int paramInt1, int paramInt2, int paramInt3)
  {
    notNested();
    this.vector_num_elems = paramInt2;
    prep(4, paramInt1 * paramInt2);
    prep(paramInt3, paramInt1 * paramInt2);
    this.nested = true;
  }
  
  static class ByteBufferBackedInputStream
    extends InputStream
  {
    ByteBuffer buf;
    
    public ByteBufferBackedInputStream(ByteBuffer paramByteBuffer)
    {
      this.buf = paramByteBuffer;
    }
    
    public int read()
      throws IOException
    {
      try
      {
        int i = this.buf.get();
        return i & 0xFF;
      }
      catch (BufferUnderflowException localBufferUnderflowException) {}
      return -1;
    }
  }
  
  public static abstract class ByteBufferFactory
  {
    public abstract ByteBuffer newByteBuffer(int paramInt);
    
    public void releaseByteBuffer(ByteBuffer paramByteBuffer) {}
  }
  
  public static final class HeapByteBufferFactory
    extends FlatBufferBuilder.ByteBufferFactory
  {
    public static final HeapByteBufferFactory INSTANCE = new HeapByteBufferFactory();
    
    public ByteBuffer newByteBuffer(int paramInt)
    {
      return ByteBuffer.allocate(paramInt).order(ByteOrder.LITTLE_ENDIAN);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/emoji2/text/flatbuffer/FlatBufferBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */