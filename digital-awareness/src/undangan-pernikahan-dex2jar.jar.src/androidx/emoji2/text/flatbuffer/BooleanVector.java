package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;

public final class BooleanVector
  extends BaseVector
{
  public BooleanVector __assign(int paramInt, ByteBuffer paramByteBuffer)
  {
    __reset(paramInt, 1, paramByteBuffer);
    return this;
  }
  
  public boolean get(int paramInt)
  {
    boolean bool;
    if (this.bb.get(__element(paramInt)) != 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/emoji2/text/flatbuffer/BooleanVector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */