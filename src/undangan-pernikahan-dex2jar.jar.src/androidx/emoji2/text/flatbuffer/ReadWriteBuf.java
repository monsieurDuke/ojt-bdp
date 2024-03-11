package androidx.emoji2.text.flatbuffer;

abstract interface ReadWriteBuf
  extends ReadBuf
{
  public abstract int limit();
  
  public abstract void put(byte paramByte);
  
  public abstract void put(byte[] paramArrayOfByte, int paramInt1, int paramInt2);
  
  public abstract void putBoolean(boolean paramBoolean);
  
  public abstract void putDouble(double paramDouble);
  
  public abstract void putFloat(float paramFloat);
  
  public abstract void putInt(int paramInt);
  
  public abstract void putLong(long paramLong);
  
  public abstract void putShort(short paramShort);
  
  public abstract boolean requestCapacity(int paramInt);
  
  public abstract void set(int paramInt, byte paramByte);
  
  public abstract void set(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3);
  
  public abstract void setBoolean(int paramInt, boolean paramBoolean);
  
  public abstract void setDouble(int paramInt, double paramDouble);
  
  public abstract void setFloat(int paramInt, float paramFloat);
  
  public abstract void setInt(int paramInt1, int paramInt2);
  
  public abstract void setLong(int paramInt, long paramLong);
  
  public abstract void setShort(int paramInt, short paramShort);
  
  public abstract int writePosition();
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/emoji2/text/flatbuffer/ReadWriteBuf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */