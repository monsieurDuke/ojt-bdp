package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;

public class ByteBufferUtil
{
  public static int getSizePrefix(ByteBuffer paramByteBuffer)
  {
    return paramByteBuffer.getInt(paramByteBuffer.position());
  }
  
  public static ByteBuffer removeSizePrefix(ByteBuffer paramByteBuffer)
  {
    paramByteBuffer = paramByteBuffer.duplicate();
    paramByteBuffer.position(paramByteBuffer.position() + 4);
    return paramByteBuffer;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/emoji2/text/flatbuffer/ByteBufferUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */