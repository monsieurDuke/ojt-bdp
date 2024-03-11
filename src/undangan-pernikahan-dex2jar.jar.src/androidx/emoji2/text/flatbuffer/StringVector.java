package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public final class StringVector
  extends BaseVector
{
  private Utf8 utf8 = Utf8.getDefault();
  
  public StringVector __assign(int paramInt1, int paramInt2, ByteBuffer paramByteBuffer)
  {
    __reset(paramInt1, paramInt2, paramByteBuffer);
    return this;
  }
  
  public String get(int paramInt)
  {
    String str = Table.__string(__element(paramInt), this.bb, this.utf8);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/emoji2/text/flatbuffer/StringVector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */