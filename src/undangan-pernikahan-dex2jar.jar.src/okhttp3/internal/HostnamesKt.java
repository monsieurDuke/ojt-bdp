package okhttp3.internal;

import java.net.IDN;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import okio.Buffer;

@Metadata(bv={1, 0, 3}, d1={"\000&\n\000\n\002\020\013\n\000\n\002\020\016\n\000\n\002\020\b\n\002\b\002\n\002\020\022\n\002\b\002\n\002\030\002\n\002\b\004\0320\020\000\032\0020\0012\006\020\002\032\0020\0032\006\020\004\032\0020\0052\006\020\006\032\0020\0052\006\020\007\032\0020\b2\006\020\t\032\0020\005H\002\032\"\020\n\032\004\030\0010\0132\006\020\002\032\0020\0032\006\020\004\032\0020\0052\006\020\006\032\0020\005H\002\032\020\020\f\032\0020\0032\006\020\007\032\0020\bH\002\032\f\020\r\032\0020\001*\0020\003H\002\032\f\020\016\032\004\030\0010\003*\0020\003¨\006\017"}, d2={"decodeIpv4Suffix", "", "input", "", "pos", "", "limit", "address", "", "addressOffset", "decodeIpv6", "Ljava/net/InetAddress;", "inet6AddressToAscii", "containsInvalidHostnameAsciiCodes", "toCanonicalHost", "okhttp"}, k=2, mv={1, 4, 0})
public final class HostnamesKt
{
  private static final boolean containsInvalidHostnameAsciiCodes(String paramString)
  {
    int j = paramString.length();
    int i = 0;
    while (i < j)
    {
      char c = paramString.charAt(i);
      if ((Intrinsics.compare(c, 31) > 0) && (Intrinsics.compare(c, 127) < 0))
      {
        if (StringsKt.indexOf$default((CharSequence)" #%/:?@[\\]", c, 0, false, 6, null) != -1) {
          return true;
        }
        i++;
      }
      else
      {
        return true;
      }
    }
    return false;
  }
  
  private static final boolean decodeIpv4Suffix(String paramString, int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3)
  {
    int j = paramInt3;
    int i = paramInt1;
    boolean bool;
    for (;;)
    {
      bool = false;
      if (i >= paramInt2) {
        break;
      }
      if (j == paramArrayOfByte.length) {
        return false;
      }
      paramInt1 = i;
      if (j != paramInt3)
      {
        if (paramString.charAt(i) != '.') {
          return false;
        }
        paramInt1 = i + 1;
      }
      int k = 0;
      for (i = paramInt1; i < paramInt2; i++)
      {
        int m = paramString.charAt(i);
        if ((Intrinsics.compare(m, 48) < 0) || (Intrinsics.compare(m, 57) > 0)) {
          break;
        }
        if ((k == 0) && (paramInt1 != i)) {
          return false;
        }
        k = k * 10 + m - 48;
        if (k > 255) {
          return false;
        }
      }
      if (i - paramInt1 == 0) {
        return false;
      }
      paramArrayOfByte[j] = ((byte)k);
      j++;
    }
    if (j == paramInt3 + 4) {
      bool = true;
    }
    return bool;
  }
  
  private static final InetAddress decodeIpv6(String paramString, int paramInt1, int paramInt2)
  {
    byte[] arrayOfByte = new byte[16];
    int i = 0;
    int j = -1;
    int i1 = -1;
    int m;
    for (int k = paramInt1;; k = paramInt1)
    {
      paramInt1 = i;
      m = j;
      if (k >= paramInt2) {
        break label309;
      }
      if (i == arrayOfByte.length) {
        return null;
      }
      int n;
      if ((k + 2 <= paramInt2) && (StringsKt.startsWith$default(paramString, "::", k, false, 4, null)))
      {
        if (j != -1) {
          return null;
        }
        k += 2;
        j = i + 2;
        i = j;
        n = j;
        m = i;
        paramInt1 = k;
        if (k == paramInt2)
        {
          paramInt1 = j;
          m = i;
          break label309;
        }
      }
      else
      {
        n = i;
        m = j;
        paramInt1 = k;
        if (i != 0) {
          if (StringsKt.startsWith$default(paramString, ":", k, false, 4, null))
          {
            paramInt1 = k + 1;
            n = i;
            m = j;
          }
          else
          {
            if (StringsKt.startsWith$default(paramString, ".", k, false, 4, null))
            {
              if (!decodeIpv4Suffix(paramString, i1, paramInt2, arrayOfByte, i - 2)) {
                return null;
              }
              paramInt1 = i + 2;
              m = j;
              break label309;
            }
            return null;
          }
        }
      }
      i = 0;
      k = paramInt1;
      while (paramInt1 < paramInt2)
      {
        j = Util.parseHexDigit(paramString.charAt(paramInt1));
        if (j == -1) {
          break;
        }
        i = (i << 4) + j;
        paramInt1++;
      }
      j = paramInt1 - k;
      if ((j == 0) || (j > 4)) {
        break;
      }
      i1 = n + 1;
      arrayOfByte[n] = ((byte)(i >>> 8 & 0xFF));
      j = i1 + 1;
      arrayOfByte[i1] = ((byte)(i & 0xFF));
      i = j;
      j = m;
      i1 = k;
    }
    return null;
    label309:
    if (paramInt1 != arrayOfByte.length)
    {
      if (m == -1) {
        return null;
      }
      System.arraycopy(arrayOfByte, m, arrayOfByte, arrayOfByte.length - (paramInt1 - m), paramInt1 - m);
      Arrays.fill(arrayOfByte, m, arrayOfByte.length - paramInt1 + m, (byte)0);
    }
    return InetAddress.getByAddress(arrayOfByte);
  }
  
  private static final String inet6AddressToAscii(byte[] paramArrayOfByte)
  {
    int m = -1;
    int k = 0;
    int i = 0;
    int j;
    while (i < paramArrayOfByte.length)
    {
      int i1;
      for (j = i;; j = i1 + 2)
      {
        i1 = j;
        if ((i1 >= 16) || (paramArrayOfByte[i1] != 0) || (paramArrayOfByte[(i1 + 1)] != 0)) {
          break;
        }
      }
      int i2 = i1 - i;
      int n = m;
      j = k;
      if (i2 > k)
      {
        n = m;
        j = k;
        if (i2 >= 4)
        {
          j = i2;
          n = i;
        }
      }
      i = i1 + 2;
      m = n;
      k = j;
    }
    Buffer localBuffer = new Buffer();
    i = 0;
    while (i < paramArrayOfByte.length) {
      if (i == m)
      {
        localBuffer.writeByte(58);
        j = i + k;
        i = j;
        if (j == 16)
        {
          localBuffer.writeByte(58);
          i = j;
        }
      }
      else
      {
        if (i > 0) {
          localBuffer.writeByte(58);
        }
        localBuffer.writeHexadecimalUnsignedLong(Util.and(paramArrayOfByte[i], 255) << 8 | Util.and(paramArrayOfByte[(i + 1)], 255));
        i += 2;
      }
    }
    return localBuffer.readUtf8();
  }
  
  public static final String toCanonicalHost(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "$this$toCanonicalHost");
    CharSequence localCharSequence = (CharSequence)paramString;
    Object localObject = (CharSequence)":";
    int i = 0;
    InetAddress localInetAddress = null;
    if (StringsKt.contains$default(localCharSequence, (CharSequence)localObject, false, 2, null))
    {
      if ((StringsKt.startsWith$default(paramString, "[", false, 2, null)) && (StringsKt.endsWith$default(paramString, "]", false, 2, null))) {
        localInetAddress = decodeIpv6(paramString, 1, paramString.length() - 1);
      } else {
        localInetAddress = decodeIpv6(paramString, 0, paramString.length());
      }
      if (localInetAddress != null)
      {
        localObject = localInetAddress.getAddress();
        if (localObject.length == 16)
        {
          Intrinsics.checkNotNullExpressionValue(localObject, "address");
          paramString = inet6AddressToAscii((byte[])localObject);
          Log5ECF72.a(paramString);
          LogE84000.a(paramString);
          Log229316.a(paramString);
          return paramString;
        }
        if (localObject.length == 4) {
          return localInetAddress.getHostAddress();
        }
        throw ((Throwable)new AssertionError("Invalid IPv6 address: '" + paramString + '\''));
      }
      return null;
    }
    try
    {
      paramString = IDN.toASCII(paramString);
      Log5ECF72.a(paramString);
      LogE84000.a(paramString);
      Log229316.a(paramString);
      Intrinsics.checkNotNullExpressionValue(paramString, "IDN.toASCII(host)");
      localObject = Locale.US;
      Intrinsics.checkNotNullExpressionValue(localObject, "Locale.US");
      if (paramString != null)
      {
        paramString = paramString.toLowerCase((Locale)localObject);
        Intrinsics.checkNotNullExpressionValue(paramString, "(this as java.lang.String).toLowerCase(locale)");
        if (((CharSequence)paramString).length() == 0) {
          i = 1;
        }
        if (i != 0) {
          return null;
        }
        if (containsInvalidHostnameAsciiCodes(paramString)) {
          paramString = localInetAddress;
        }
        return paramString;
      }
      paramString = new java/lang/NullPointerException;
      paramString.<init>("null cannot be cast to non-null type java.lang.String");
      throw paramString;
    }
    catch (IllegalArgumentException paramString) {}
    return null;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/HostnamesKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */