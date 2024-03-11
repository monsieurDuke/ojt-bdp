package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;

public final class DoubleVector
  extends BaseVector
{
  public DoubleVector __assign(int paramInt, ByteBuffer paramByteBuffer)
  {
    __reset(paramInt, 8, paramByteBuffer);
    return this;
  }
  
  public double get(int paramInt)
  {
    return this.bb.getDouble(__element(paramInt));
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/emoji2/text/flatbuffer/DoubleVector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */