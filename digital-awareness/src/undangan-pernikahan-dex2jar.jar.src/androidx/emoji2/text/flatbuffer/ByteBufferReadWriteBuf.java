package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class ByteBufferReadWriteBuf
  implements ReadWriteBuf
{
  private final ByteBuffer buffer;
  
  public ByteBufferReadWriteBuf(ByteBuffer paramByteBuffer)
  {
    this.buffer = paramByteBuffer;
    paramByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
  }
  
  public byte[] data()
  {
    return this.buffer.array();
  }
  
  public byte get(int paramInt)
  {
    return this.buffer.get(paramInt);
  }
  
  public boolean getBoolean(int paramInt)
  {
    boolean bool;
    if (get(paramInt) != 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public double getDouble(int paramInt)
  {
    return this.buffer.getDouble(paramInt);
  }
  
  public float getFloat(int paramInt)
  {
    return this.buffer.getFloat(paramInt);
  }
  
  public int getInt(int paramInt)
  {
    return this.buffer.getInt(paramInt);
  }
  
  public long getLong(int paramInt)
  {
    return this.buffer.getLong(paramInt);
  }
  
  public short getShort(int paramInt)
  {
    return this.buffer.getShort(paramInt);
  }
  
  public String getString(int paramInt1, int paramInt2)
  {
    String str = Utf8Safe.decodeUtf8Buffer(this.buffer, paramInt1, paramInt2);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str;
  }
  
  public int limit()
  {
    return this.buffer.limit();
  }
  
  public void put(byte paramByte)
  {
    this.buffer.put(paramByte);
  }
  
  public void put(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    this.buffer.put(paramArrayOfByte, paramInt1, paramInt2);
  }
  
  public void putBoolean(boolean paramBoolean)
  {
    this.buffer.put(paramBoolean);
  }
  
  public void putDouble(double paramDouble)
  {
    this.buffer.putDouble(paramDouble);
  }
  
  public void putFloat(float paramFloat)
  {
    this.buffer.putFloat(paramFloat);
  }
  
  public void putInt(int paramInt)
  {
    this.buffer.putInt(paramInt);
  }
  
  public void putLong(long paramLong)
  {
    this.buffer.putLong(paramLong);
  }
  
  public void putShort(short paramShort)
  {
    this.buffer.putShort(paramShort);
  }
  
  public boolean requestCapacity(int paramInt)
  {
    boolean bool;
    if (paramInt <= this.buffer.limit()) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void set(int paramInt, byte paramByte)
  {
    requestCapacity(paramInt + 1);
    this.buffer.put(paramInt, paramByte);
  }
  
  public void set(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
  {
    requestCapacity(paramInt3 - paramInt2 + paramInt1);
    int i = this.buffer.position();
    this.buffer.position(paramInt1);
    this.buffer.put(paramArrayOfByte, paramInt2, paramInt3);
    this.buffer.position(i);
  }
  
  public void setBoolean(int paramInt, boolean paramBoolean)
  {
    set(paramInt, paramBoolean);
  }
  
  public void setDouble(int paramInt, double paramDouble)
  {
    requestCapacity(paramInt + 8);
    this.buffer.putDouble(paramInt, paramDouble);
  }
  
  public void setFloat(int paramInt, float paramFloat)
  {
    requestCapacity(paramInt + 4);
    this.buffer.putFloat(paramInt, paramFloat);
  }
  
  public void setInt(int paramInt1, int paramInt2)
  {
    requestCapacity(paramInt1 + 4);
    this.buffer.putInt(paramInt1, paramInt2);
  }
  
  public void setLong(int paramInt, long paramLong)
  {
    requestCapacity(paramInt + 8);
    this.buffer.putLong(paramInt, paramLong);
  }
  
  public void setShort(int paramInt, short paramShort)
  {
    requestCapacity(paramInt + 2);
    this.buffer.putShort(paramInt, paramShort);
  }
  
  public int writePosition()
  {
    return this.buffer.position();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/emoji2/text/flatbuffer/ByteBufferReadWriteBuf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */