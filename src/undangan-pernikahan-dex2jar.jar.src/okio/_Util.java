package okio;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(bv={1, 0, 3}, d1={"\000:\n\000\n\002\020\013\n\000\n\002\020\022\n\000\n\002\020\b\n\002\b\004\n\002\020\002\n\000\n\002\020\t\n\002\b\003\n\002\020\005\n\002\b\002\n\002\020\n\n\002\b\003\n\002\020\016\n\000\0320\020\000\032\0020\0012\006\020\002\032\0020\0032\006\020\004\032\0020\0052\006\020\006\032\0020\0032\006\020\007\032\0020\0052\006\020\b\032\0020\005H\000\032 \020\t\032\0020\n2\006\020\013\032\0020\f2\006\020\r\032\0020\f2\006\020\b\032\0020\fH\000\032\031\020\016\032\0020\f2\006\020\002\032\0020\0052\006\020\006\032\0020\fH\b\032\031\020\016\032\0020\f2\006\020\002\032\0020\f2\006\020\006\032\0020\005H\b\032\025\020\017\032\0020\005*\0020\0202\006\020\021\032\0020\005H\f\032\025\020\017\032\0020\f*\0020\0202\006\020\021\032\0020\fH\f\032\025\020\017\032\0020\f*\0020\0052\006\020\021\032\0020\fH\f\032\f\020\022\032\0020\005*\0020\005H\000\032\f\020\022\032\0020\f*\0020\fH\000\032\f\020\022\032\0020\023*\0020\023H\000\032\025\020\024\032\0020\005*\0020\0202\006\020\021\032\0020\005H\f\032\025\020\025\032\0020\005*\0020\0202\006\020\021\032\0020\005H\f\032\f\020\026\032\0020\027*\0020\020H\000\032\f\020\026\032\0020\027*\0020\005H\000\032\f\020\026\032\0020\027*\0020\fH\000¨\006\030"}, d2={"arrayRangeEquals", "", "a", "", "aOffset", "", "b", "bOffset", "byteCount", "checkOffsetAndCount", "", "size", "", "offset", "minOf", "and", "", "other", "reverseBytes", "", "shl", "shr", "toHexString", "", "okio"}, k=2, mv={1, 4, 0})
public final class _Util
{
  public static final int and(byte paramByte, int paramInt)
  {
    return paramByte & paramInt;
  }
  
  public static final long and(byte paramByte, long paramLong)
  {
    return paramByte & paramLong;
  }
  
  public static final long and(int paramInt, long paramLong)
  {
    return paramInt & paramLong;
  }
  
  public static final boolean arrayRangeEquals(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2, int paramInt3)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfByte1, "a");
    Intrinsics.checkNotNullParameter(paramArrayOfByte2, "b");
    for (int i = 0; i < paramInt3; i++) {
      if (paramArrayOfByte1[(i + paramInt1)] != paramArrayOfByte2[(i + paramInt2)]) {
        return false;
      }
    }
    return true;
  }
  
  public static final void checkOffsetAndCount(long paramLong1, long paramLong2, long paramLong3)
  {
    if (((paramLong2 | paramLong3) >= 0L) && (paramLong2 <= paramLong1) && (paramLong1 - paramLong2 >= paramLong3)) {
      return;
    }
    throw ((Throwable)new ArrayIndexOutOfBoundsException("size=" + paramLong1 + " offset=" + paramLong2 + " byteCount=" + paramLong3));
  }
  
  public static final long minOf(int paramInt, long paramLong)
  {
    return Math.min(paramInt, paramLong);
  }
  
  public static final long minOf(long paramLong, int paramInt)
  {
    return Math.min(paramLong, paramInt);
  }
  
  public static final int reverseBytes(int paramInt)
  {
    return (0xFF000000 & paramInt) >>> 24 | (0xFF0000 & paramInt) >>> 8 | (0xFF00 & paramInt) << 8 | (paramInt & 0xFF) << 24;
  }
  
  public static final long reverseBytes(long paramLong)
  {
    return (0xFF00000000000000 & paramLong) >>> 56 | (0xFF000000000000 & paramLong) >>> 40 | (0xFF0000000000 & paramLong) >>> 24 | (0xFF00000000 & paramLong) >>> 8 | (0xFF000000 & paramLong) << 8 | (0xFF0000 & paramLong) << 24 | (0xFF00 & paramLong) << 40 | (0xFF & paramLong) << 56;
  }
  
  public static final short reverseBytes(short paramShort)
  {
    paramShort = 0xFFFF & paramShort;
    return (short)((0xFF00 & paramShort) >>> 8 | (paramShort & 0xFF) << 8);
  }
  
  public static final int shl(byte paramByte, int paramInt)
  {
    return paramByte << paramInt;
  }
  
  public static final int shr(byte paramByte, int paramInt)
  {
    return paramByte >> paramInt;
  }
  
  public static final String toHexString(byte paramByte)
  {
    String str = new String(new char[] { okio.internal.ByteStringKt.getHEX_DIGIT_CHARS()[(paramByte >> 4 & 0xF)], okio.internal.ByteStringKt.getHEX_DIGIT_CHARS()[(0xF & paramByte)] });
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str;
  }
  
  public static final String toHexString(int paramInt)
  {
    if (paramInt == 0) {
      return "0";
    }
    Object localObject = new char[8];
    localObject[0] = okio.internal.ByteStringKt.getHEX_DIGIT_CHARS()[(paramInt >> 28 & 0xF)];
    localObject[1] = okio.internal.ByteStringKt.getHEX_DIGIT_CHARS()[(paramInt >> 24 & 0xF)];
    localObject[2] = okio.internal.ByteStringKt.getHEX_DIGIT_CHARS()[(paramInt >> 20 & 0xF)];
    localObject[3] = okio.internal.ByteStringKt.getHEX_DIGIT_CHARS()[(paramInt >> 16 & 0xF)];
    localObject[4] = okio.internal.ByteStringKt.getHEX_DIGIT_CHARS()[(paramInt >> 12 & 0xF)];
    localObject[5] = okio.internal.ByteStringKt.getHEX_DIGIT_CHARS()[(paramInt >> 8 & 0xF)];
    localObject[6] = okio.internal.ByteStringKt.getHEX_DIGIT_CHARS()[(paramInt >> 4 & 0xF)];
    localObject[7] = okio.internal.ByteStringKt.getHEX_DIGIT_CHARS()[(paramInt & 0xF)];
    for (paramInt = 0; (paramInt < localObject.length) && (localObject[paramInt] == '0'); paramInt++) {}
    localObject = new String((char[])localObject, paramInt, localObject.length - paramInt);
    Log5ECF72.a(localObject);
    LogE84000.a(localObject);
    Log229316.a(localObject);
    return (String)localObject;
  }
  
  public static final String toHexString(long paramLong)
  {
    if (paramLong == 0L) {
      return "0";
    }
    Object localObject = new char[16];
    localObject[0] = okio.internal.ByteStringKt.getHEX_DIGIT_CHARS()[((int)(paramLong >> 60 & 0xF))];
    localObject[1] = okio.internal.ByteStringKt.getHEX_DIGIT_CHARS()[((int)(paramLong >> 56 & 0xF))];
    localObject[2] = okio.internal.ByteStringKt.getHEX_DIGIT_CHARS()[((int)(paramLong >> 52 & 0xF))];
    localObject[3] = okio.internal.ByteStringKt.getHEX_DIGIT_CHARS()[((int)(paramLong >> 48 & 0xF))];
    localObject[4] = okio.internal.ByteStringKt.getHEX_DIGIT_CHARS()[((int)(paramLong >> 44 & 0xF))];
    localObject[5] = okio.internal.ByteStringKt.getHEX_DIGIT_CHARS()[((int)(paramLong >> 40 & 0xF))];
    localObject[6] = okio.internal.ByteStringKt.getHEX_DIGIT_CHARS()[((int)(paramLong >> 36 & 0xF))];
    localObject[7] = okio.internal.ByteStringKt.getHEX_DIGIT_CHARS()[((int)(paramLong >> 32 & 0xF))];
    localObject[8] = okio.internal.ByteStringKt.getHEX_DIGIT_CHARS()[((int)(paramLong >> 28 & 0xF))];
    localObject[9] = okio.internal.ByteStringKt.getHEX_DIGIT_CHARS()[((int)(paramLong >> 24 & 0xF))];
    localObject[10] = okio.internal.ByteStringKt.getHEX_DIGIT_CHARS()[((int)(paramLong >> 20 & 0xF))];
    localObject[11] = okio.internal.ByteStringKt.getHEX_DIGIT_CHARS()[((int)(paramLong >> 16 & 0xF))];
    localObject[12] = okio.internal.ByteStringKt.getHEX_DIGIT_CHARS()[((int)(paramLong >> 12 & 0xF))];
    localObject[13] = okio.internal.ByteStringKt.getHEX_DIGIT_CHARS()[((int)(paramLong >> 8 & 0xF))];
    localObject[14] = okio.internal.ByteStringKt.getHEX_DIGIT_CHARS()[((int)(paramLong >> 4 & 0xF))];
    localObject[15] = okio.internal.ByteStringKt.getHEX_DIGIT_CHARS()[((int)(paramLong & 0xF))];
    for (int i = 0; (i < localObject.length) && (localObject[i] == '0'); i++) {}
    localObject = new String((char[])localObject, i, localObject.length - i);
    Log5ECF72.a(localObject);
    LogE84000.a(localObject);
    Log229316.a(localObject);
    return (String)localObject;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okio/_Util.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */