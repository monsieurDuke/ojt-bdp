package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;

public final class IntVector
  extends BaseVector
{
  public IntVector __assign(int paramInt, ByteBuffer paramByteBuffer)
  {
    __reset(paramInt, 4, paramByteBuffer);
    return this;
  }
  
  public int get(int paramInt)
  {
    return this.bb.getInt(__element(paramInt));
  }
  
  public long getAsUnsigned(int paramInt)
  {
    return get(paramInt) & 0xFFFFFFFF;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/emoji2/text/flatbuffer/IntVector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */