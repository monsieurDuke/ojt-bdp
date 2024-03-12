package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;

public final class ShortVector
  extends BaseVector
{
  public ShortVector __assign(int paramInt, ByteBuffer paramByteBuffer)
  {
    __reset(paramInt, 2, paramByteBuffer);
    return this;
  }
  
  public short get(int paramInt)
  {
    return this.bb.getShort(__element(paramInt));
  }
  
  public int getAsUnsigned(int paramInt)
  {
    return get(paramInt) & 0xFFFF;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/emoji2/text/flatbuffer/ShortVector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */