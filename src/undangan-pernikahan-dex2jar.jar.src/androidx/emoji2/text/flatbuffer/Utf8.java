package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;

public abstract class Utf8
{
  private static Utf8 DEFAULT;
  
  public static Utf8 getDefault()
  {
    if (DEFAULT == null) {
      DEFAULT = new Utf8Safe();
    }
    return DEFAULT;
  }
  
  public static void setDefault(Utf8 paramUtf8)
  {
    DEFAULT = paramUtf8;
  }
  
  public abstract String decodeUtf8(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2);
  
  public abstract void encodeUtf8(CharSequence paramCharSequence, ByteBuffer paramByteBuffer);
  
  public abstract int encodedLength(CharSequence paramCharSequence);
  
  static class DecodeUtil
  {
    static void handleFourBytes(byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4, char[] paramArrayOfChar, int paramInt)
      throws IllegalArgumentException
    {
      if ((!isNotTrailingByte(paramByte2)) && ((paramByte1 << 28) + (paramByte2 + 112) >> 30 == 0) && (!isNotTrailingByte(paramByte3)) && (!isNotTrailingByte(paramByte4)))
      {
        paramByte1 = (paramByte1 & 0x7) << 18 | trailingByteValue(paramByte2) << 12 | trailingByteValue(paramByte3) << 6 | trailingByteValue(paramByte4);
        paramArrayOfChar[paramInt] = highSurrogate(paramByte1);
        paramArrayOfChar[(paramInt + 1)] = lowSurrogate(paramByte1);
        return;
      }
      throw new IllegalArgumentException("Invalid UTF-8");
    }
    
    static void handleOneByte(byte paramByte, char[] paramArrayOfChar, int paramInt)
    {
      paramArrayOfChar[paramInt] = ((char)paramByte);
    }
    
    static void handleThreeBytes(byte paramByte1, byte paramByte2, byte paramByte3, char[] paramArrayOfChar, int paramInt)
      throws IllegalArgumentException
    {
      if ((!isNotTrailingByte(paramByte2)) && ((paramByte1 != -32) || (paramByte2 >= -96)) && ((paramByte1 != -19) || (paramByte2 < -96)) && (!isNotTrailingByte(paramByte3)))
      {
        paramArrayOfChar[paramInt] = ((char)((paramByte1 & 0xF) << 12 | trailingByteValue(paramByte2) << 6 | trailingByteValue(paramByte3)));
        return;
      }
      throw new IllegalArgumentException("Invalid UTF-8");
    }
    
    static void handleTwoBytes(byte paramByte1, byte paramByte2, char[] paramArrayOfChar, int paramInt)
      throws IllegalArgumentException
    {
      if (paramByte1 >= -62)
      {
        if (!isNotTrailingByte(paramByte2))
        {
          paramArrayOfChar[paramInt] = ((char)((paramByte1 & 0x1F) << 6 | trailingByteValue(paramByte2)));
          return;
        }
        throw new IllegalArgumentException("Invalid UTF-8: Illegal trailing byte in 2 bytes utf");
      }
      throw new IllegalArgumentException("Invalid UTF-8: Illegal leading byte in 2 bytes utf");
    }
    
    private static char highSurrogate(int paramInt)
    {
      return (char)((paramInt >>> 10) + 55232);
    }
    
    private static boolean isNotTrailingByte(byte paramByte)
    {
      boolean bool;
      if (paramByte > -65) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    static boolean isOneByte(byte paramByte)
    {
      boolean bool;
      if (paramByte >= 0) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    static boolean isThreeBytes(byte paramByte)
    {
      boolean bool;
      if (paramByte < -16) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    static boolean isTwoBytes(byte paramByte)
    {
      boolean bool;
      if (paramByte < -32) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    private static char lowSurrogate(int paramInt)
    {
      return (char)((paramInt & 0x3FF) + 56320);
    }
    
    private static int trailingByteValue(byte paramByte)
    {
      return paramByte & 0x3F;
    }
  }
  
  static class UnpairedSurrogateException
    extends IllegalArgumentException
  {
    UnpairedSurrogateException(int paramInt1, int paramInt2)
    {
      super();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/emoji2/text/flatbuffer/Utf8.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */