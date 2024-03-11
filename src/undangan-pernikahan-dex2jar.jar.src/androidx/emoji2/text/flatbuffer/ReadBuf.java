package androidx.emoji2.text.flatbuffer;

abstract interface ReadBuf
{
  public abstract byte[] data();
  
  public abstract byte get(int paramInt);
  
  public abstract boolean getBoolean(int paramInt);
  
  public abstract double getDouble(int paramInt);
  
  public abstract float getFloat(int paramInt);
  
  public abstract int getInt(int paramInt);
  
  public abstract long getLong(int paramInt);
  
  public abstract short getShort(int paramInt);
  
  public abstract String getString(int paramInt1, int paramInt2);
  
  public abstract int limit();
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/emoji2/text/flatbuffer/ReadBuf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */