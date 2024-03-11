package okio;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(bv={1, 0, 3}, d1={"\000\022\n\000\n\002\020\022\n\002\b\005\n\002\020\016\n\002\b\003\032\016\020\006\032\004\030\0010\001*\0020\007H\000\032\026\020\b\032\0020\007*\0020\0012\b\b\002\020\t\032\0020\001H\000\"\024\020\000\032\0020\001X\004¢\006\b\n\000\032\004\b\002\020\003\"\024\020\004\032\0020\001X\004¢\006\b\n\000\032\004\b\005\020\003¨\006\n"}, d2={"BASE64", "", "getBASE64", "()[B", "BASE64_URL_SAFE", "getBASE64_URL_SAFE", "decodeBase64ToArray", "", "encodeBase64", "map", "okio"}, k=2, mv={1, 4, 0})
public final class _Base64
{
  private static final byte[] BASE64 = ByteString.Companion.encodeUtf8("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/").getData$okio();
  private static final byte[] BASE64_URL_SAFE = ByteString.Companion.encodeUtf8("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_").getData$okio();
  
  public static final byte[] decodeBase64ToArray(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "$this$decodeBase64ToArray");
    int i;
    for (int k = paramString.length(); k > 0; k--)
    {
      i = paramString.charAt(k - 1);
      if ((i != 61) && (i != 10) && (i != 13) && (i != 32) && (i != 9)) {
        break;
      }
    }
    byte[] arrayOfByte = new byte[(int)(k * 6L / 8L)];
    int j = 0;
    int i1 = 0;
    int n = 0;
    int m = 0;
    while (m < k)
    {
      i = paramString.charAt(m);
      if ((65 <= i) && (90 >= i))
      {
        i -= 65;
      }
      else if ((97 <= i) && (122 >= i))
      {
        i -= 71;
      }
      else if ((48 <= i) && (57 >= i))
      {
        i += 4;
      }
      else if ((i != 43) && (i != 45))
      {
        if ((i != 47) && (i != 95))
        {
          if ((i != 10) && (i != 13) && (i != 32) && (i != 9)) {
            return null;
          }
          i = j;
          break label316;
        }
        i = 63;
      }
      else
      {
        i = 62;
      }
      int i2 = n << 6 | i;
      int i3 = i1 + 1;
      i = j;
      i1 = i3;
      n = i2;
      if (i3 % 4 == 0)
      {
        i = j + 1;
        arrayOfByte[j] = ((byte)(i2 >> 16));
        j = i + 1;
        arrayOfByte[i] = ((byte)(i2 >> 8));
        arrayOfByte[j] = ((byte)i2);
        i = j + 1;
        n = i2;
        i1 = i3;
      }
      label316:
      m++;
      j = i;
    }
    switch (i1 % 4)
    {
    default: 
      break;
    case 3: 
      k = n << 6;
      i = j + 1;
      arrayOfByte[j] = ((byte)(k >> 16));
      j = i + 1;
      arrayOfByte[i] = ((byte)(k >> 8));
      break;
    case 2: 
      arrayOfByte[j] = ((byte)(n << 12 >> 16));
      j++;
      break;
    case 1: 
      return null;
    }
    if (j == arrayOfByte.length) {
      return arrayOfByte;
    }
    paramString = Arrays.copyOf(arrayOfByte, j);
    Intrinsics.checkNotNullExpressionValue(paramString, "java.util.Arrays.copyOf(this, newSize)");
    return paramString;
  }
  
  public static final String encodeBase64(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfByte1, "$this$encodeBase64");
    Intrinsics.checkNotNullParameter(paramArrayOfByte2, "map");
    byte[] arrayOfByte = new byte[(paramArrayOfByte1.length + 2) / 3 * 4];
    int j = 0;
    int m = paramArrayOfByte1.length - paramArrayOfByte1.length % 3;
    int n;
    for (int k = 0; k < m; k++)
    {
      n = k + 1;
      int i2 = paramArrayOfByte1[k];
      k = n + 1;
      int i1 = paramArrayOfByte1[n];
      n = paramArrayOfByte1[k];
      int i3 = j + 1;
      arrayOfByte[j] = paramArrayOfByte2[((i2 & 0xFF) >> 2)];
      j = i3 + 1;
      arrayOfByte[i3] = paramArrayOfByte2[((i2 & 0x3) << 4 | (i1 & 0xFF) >> 4)];
      i2 = j + 1;
      arrayOfByte[j] = paramArrayOfByte2[((i1 & 0xF) << 2 | (n & 0xFF) >> 6)];
      j = i2 + 1;
      arrayOfByte[i2] = paramArrayOfByte2[(n & 0x3F)];
    }
    switch (paramArrayOfByte1.length - m)
    {
    default: 
      break;
    case 2: 
      m = paramArrayOfByte1[k];
      n = paramArrayOfByte1[(k + 1)];
      k = j + 1;
      arrayOfByte[j] = paramArrayOfByte2[((m & 0xFF) >> 2)];
      j = k + 1;
      arrayOfByte[k] = paramArrayOfByte2[((m & 0x3) << 4 | (n & 0xFF) >> 4)];
      arrayOfByte[j] = paramArrayOfByte2[((n & 0xF) << 2)];
      arrayOfByte[(j + 1)] = ((byte)61);
      break;
    case 1: 
      k = paramArrayOfByte1[k];
      m = j + 1;
      arrayOfByte[j] = paramArrayOfByte2[((k & 0xFF) >> 2)];
      j = m + 1;
      arrayOfByte[m] = paramArrayOfByte2[((k & 0x3) << 4)];
      int i = (byte)61;
      arrayOfByte[j] = i;
      arrayOfByte[(j + 1)] = i;
    }
    paramArrayOfByte1 = _Platform.toUtf8String(arrayOfByte);
    Log5ECF72.a(paramArrayOfByte1);
    LogE84000.a(paramArrayOfByte1);
    Log229316.a(paramArrayOfByte1);
    return paramArrayOfByte1;
  }
  
  public static final byte[] getBASE64()
  {
    return BASE64;
  }
  
  public static final byte[] getBASE64_URL_SAFE()
  {
    return BASE64_URL_SAFE;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okio/_Base64.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */