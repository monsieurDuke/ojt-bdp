package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;

public final class FloatVector
  extends BaseVector
{
  public FloatVector __assign(int paramInt, ByteBuffer paramByteBuffer)
  {
    __reset(paramInt, 4, paramByteBuffer);
    return this;
  }
  
  public float get(int paramInt)
  {
    return this.bb.getFloat(__element(paramInt));
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/emoji2/text/flatbuffer/FloatVector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */