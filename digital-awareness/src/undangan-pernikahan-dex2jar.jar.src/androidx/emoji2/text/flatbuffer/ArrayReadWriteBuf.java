package androidx.emoji2.text.flatbuffer;

import java.util.Arrays;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class ArrayReadWriteBuf
  implements ReadWriteBuf
{
  private byte[] buffer;
  private int writePos;
  
  public ArrayReadWriteBuf()
  {
    this(10);
  }
  
  public ArrayReadWriteBuf(int paramInt)
  {
    this(new byte[paramInt]);
  }
  
  public ArrayReadWriteBuf(byte[] paramArrayOfByte)
  {
    this.buffer = paramArrayOfByte;
    this.writePos = 0;
  }
  
  public ArrayReadWriteBuf(byte[] paramArrayOfByte, int paramInt)
  {
    this.buffer = paramArrayOfByte;
    this.writePos = paramInt;
  }
  
  public byte[] data()
  {
    return this.buffer;
  }
  
  public byte get(int paramInt)
  {
    return this.buffer[paramInt];
  }
  
  public boolean getBoolean(int paramInt)
  {
    boolean bool;
    if (this.buffer[paramInt] != 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public double getDouble(int paramInt)
  {
    return Double.longBitsToDouble(getLong(paramInt));
  }
  
  public float getFloat(int paramInt)
  {
    return Float.intBitsToFloat(getInt(paramInt));
  }
  
  public int getInt(int paramInt)
  {
    byte[] arrayOfByte = this.buffer;
    int k = arrayOfByte[(paramInt + 3)];
    int j = arrayOfByte[(paramInt + 2)];
    int i = arrayOfByte[(paramInt + 1)];
    return arrayOfByte[paramInt] & 0xFF | k << 24 | (j & 0xFF) << 16 | (i & 0xFF) << 8;
  }
  
  public long getLong(int paramInt)
  {
    byte[] arrayOfByte = this.buffer;
    int i = paramInt + 1;
    long l4 = arrayOfByte[paramInt];
    paramInt = i + 1;
    long l2 = arrayOfByte[i];
    i = paramInt + 1;
    long l3 = arrayOfByte[paramInt];
    paramInt = i + 1;
    long l5 = arrayOfByte[i];
    i = paramInt + 1;
    long l1 = arrayOfByte[paramInt];
    paramInt = i + 1;
    return l4 & 0xFF | (l2 & 0xFF) << 8 | (l3 & 0xFF) << 16 | (l5 & 0xFF) << 24 | (l1 & 0xFF) << 32 | (arrayOfByte[i] & 0xFF) << 40 | (0xFF & arrayOfByte[paramInt]) << 48 | arrayOfByte[(paramInt + 1)] << 56;
  }
  
  public short getShort(int paramInt)
  {
    byte[] arrayOfByte = this.buffer;
    int i = arrayOfByte[(paramInt + 1)];
    return (short)(arrayOfByte[paramInt] & 0xFF | i << 8);
  }
  
  public String getString(int paramInt1, int paramInt2)
  {
    String str = Utf8Safe.decodeUtf8Array(this.buffer, paramInt1, paramInt2);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str;
  }
  
  public int limit()
  {
    return this.writePos;
  }
  
  public void put(byte paramByte)
  {
    set(this.writePos, paramByte);
    this.writePos += 1;
  }
  
  public void put(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    set(this.writePos, paramArrayOfByte, paramInt1, paramInt2);
    this.writePos += paramInt2;
  }
  
  public void putBoolean(boolean paramBoolean)
  {
    setBoolean(this.writePos, paramBoolean);
    this.writePos += 1;
  }
  
  public void putDouble(double paramDouble)
  {
    setDouble(this.writePos, paramDouble);
    this.writePos += 8;
  }
  
  public void putFloat(float paramFloat)
  {
    setFloat(this.writePos, paramFloat);
    this.writePos += 4;
  }
  
  public void putInt(int paramInt)
  {
    setInt(this.writePos, paramInt);
    this.writePos += 4;
  }
  
  public void putLong(long paramLong)
  {
    setLong(this.writePos, paramLong);
    this.writePos += 8;
  }
  
  public void putShort(short paramShort)
  {
    setShort(this.writePos, paramShort);
    this.writePos += 2;
  }
  
  public boolean requestCapacity(int paramInt)
  {
    byte[] arrayOfByte = this.buffer;
    if (arrayOfByte.length > paramInt) {
      return true;
    }
    paramInt = arrayOfByte.length;
    this.buffer = Arrays.copyOf(arrayOfByte, (paramInt >> 1) + paramInt);
    return true;
  }
  
  public void set(int paramInt, byte paramByte)
  {
    requestCapacity(paramInt + 1);
    this.buffer[paramInt] = paramByte;
  }
  
  public void set(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
  {
    requestCapacity(paramInt3 - paramInt2 + paramInt1);
    System.arraycopy(paramArrayOfByte, paramInt2, this.buffer, paramInt1, paramInt3);
  }
  
  public void setBoolean(int paramInt, boolean paramBoolean)
  {
    set(paramInt, paramBoolean);
  }
  
  public void setDouble(int paramInt, double paramDouble)
  {
    requestCapacity(paramInt + 8);
    long l = Double.doubleToRawLongBits(paramDouble);
    int i = (int)l;
    byte[] arrayOfByte = this.buffer;
    int j = paramInt + 1;
    arrayOfByte[paramInt] = ((byte)(i & 0xFF));
    paramInt = j + 1;
    arrayOfByte[j] = ((byte)(i >> 8 & 0xFF));
    j = paramInt + 1;
    arrayOfByte[paramInt] = ((byte)(i >> 16 & 0xFF));
    paramInt = j + 1;
    arrayOfByte[j] = ((byte)(i >> 24 & 0xFF));
    i = (int)(l >> 32);
    j = paramInt + 1;
    arrayOfByte[paramInt] = ((byte)(i & 0xFF));
    paramInt = j + 1;
    arrayOfByte[j] = ((byte)(i >> 8 & 0xFF));
    arrayOfByte[paramInt] = ((byte)(i >> 16 & 0xFF));
    arrayOfByte[(paramInt + 1)] = ((byte)(i >> 24 & 0xFF));
  }
  
  public void setFloat(int paramInt, float paramFloat)
  {
    requestCapacity(paramInt + 4);
    int i = Float.floatToRawIntBits(paramFloat);
    byte[] arrayOfByte = this.buffer;
    int j = paramInt + 1;
    arrayOfByte[paramInt] = ((byte)(i & 0xFF));
    paramInt = j + 1;
    arrayOfByte[j] = ((byte)(i >> 8 & 0xFF));
    arrayOfByte[paramInt] = ((byte)(i >> 16 & 0xFF));
    arrayOfByte[(paramInt + 1)] = ((byte)(i >> 24 & 0xFF));
  }
  
  public void setInt(int paramInt1, int paramInt2)
  {
    requestCapacity(paramInt1 + 4);
    byte[] arrayOfByte = this.buffer;
    int i = paramInt1 + 1;
    arrayOfByte[paramInt1] = ((byte)(paramInt2 & 0xFF));
    paramInt1 = i + 1;
    arrayOfByte[i] = ((byte)(paramInt2 >> 8 & 0xFF));
    arrayOfByte[paramInt1] = ((byte)(paramInt2 >> 16 & 0xFF));
    arrayOfByte[(paramInt1 + 1)] = ((byte)(paramInt2 >> 24 & 0xFF));
  }
  
  public void setLong(int paramInt, long paramLong)
  {
    requestCapacity(paramInt + 8);
    int i = (int)paramLong;
    byte[] arrayOfByte = this.buffer;
    int j = paramInt + 1;
    arrayOfByte[paramInt] = ((byte)(i & 0xFF));
    paramInt = j + 1;
    arrayOfByte[j] = ((byte)(i >> 8 & 0xFF));
    j = paramInt + 1;
    arrayOfByte[paramInt] = ((byte)(i >> 16 & 0xFF));
    paramInt = j + 1;
    arrayOfByte[j] = ((byte)(i >> 24 & 0xFF));
    j = (int)(paramLong >> 32);
    i = paramInt + 1;
    arrayOfByte[paramInt] = ((byte)(j & 0xFF));
    paramInt = i + 1;
    arrayOfByte[i] = ((byte)(j >> 8 & 0xFF));
    arrayOfByte[paramInt] = ((byte)(j >> 16 & 0xFF));
    arrayOfByte[(paramInt + 1)] = ((byte)(j >> 24 & 0xFF));
  }
  
  public void setShort(int paramInt, short paramShort)
  {
    requestCapacity(paramInt + 2);
    byte[] arrayOfByte = this.buffer;
    arrayOfByte[paramInt] = ((byte)(paramShort & 0xFF));
    arrayOfByte[(paramInt + 1)] = ((byte)(paramShort >> 8 & 0xFF));
  }
  
  public int writePosition()
  {
    return this.writePos;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/emoji2/text/flatbuffer/ArrayReadWriteBuf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */