package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;

public class BaseVector
{
  protected ByteBuffer bb;
  private int element_size;
  private int length;
  private int vector;
  
  protected int __element(int paramInt)
  {
    return this.vector + this.element_size * paramInt;
  }
  
  protected void __reset(int paramInt1, int paramInt2, ByteBuffer paramByteBuffer)
  {
    this.bb = paramByteBuffer;
    if (paramByteBuffer != null)
    {
      this.vector = paramInt1;
      this.length = paramByteBuffer.getInt(paramInt1 - 4);
      this.element_size = paramInt2;
    }
    else
    {
      this.vector = 0;
      this.length = 0;
      this.element_size = 0;
    }
  }
  
  protected int __vector()
  {
    return this.vector;
  }
  
  public int length()
  {
    return this.length;
  }
  
  public void reset()
  {
    __reset(0, 0, null);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/emoji2/text/flatbuffer/BaseVector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */