package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;

public final class ByteVector
  extends BaseVector
{
  public ByteVector __assign(int paramInt, ByteBuffer paramByteBuffer)
  {
    __reset(paramInt, 1, paramByteBuffer);
    return this;
  }
  
  public byte get(int paramInt)
  {
    return this.bb.get(__element(paramInt));
  }
  
  public int getAsUnsigned(int paramInt)
  {
    return get(paramInt) & 0xFF;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/emoji2/text/flatbuffer/ByteVector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */