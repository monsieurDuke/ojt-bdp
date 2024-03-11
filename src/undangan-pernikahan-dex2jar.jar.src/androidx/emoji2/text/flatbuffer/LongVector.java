package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;

public final class LongVector
  extends BaseVector
{
  public LongVector __assign(int paramInt, ByteBuffer paramByteBuffer)
  {
    __reset(paramInt, 8, paramByteBuffer);
    return this;
  }
  
  public long get(int paramInt)
  {
    return this.bb.getLong(__element(paramInt));
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/emoji2/text/flatbuffer/LongVector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */