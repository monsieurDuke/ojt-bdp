package kotlin;

@Metadata(d1={"\000 \n\000\n\002\020\b\n\002\020\005\n\000\n\002\020\t\n\002\020\n\n\000\n\002\020\006\n\002\020\007\n\000\032\025\020\000\032\0020\001*\0020\0022\006\020\003\032\0020\002H\b\032\025\020\000\032\0020\001*\0020\0022\006\020\003\032\0020\001H\b\032\025\020\000\032\0020\004*\0020\0022\006\020\003\032\0020\004H\b\032\025\020\000\032\0020\001*\0020\0022\006\020\003\032\0020\005H\b\032\025\020\000\032\0020\001*\0020\0012\006\020\003\032\0020\002H\b\032\025\020\000\032\0020\001*\0020\0012\006\020\003\032\0020\001H\b\032\025\020\000\032\0020\004*\0020\0012\006\020\003\032\0020\004H\b\032\025\020\000\032\0020\001*\0020\0012\006\020\003\032\0020\005H\b\032\025\020\000\032\0020\004*\0020\0042\006\020\003\032\0020\002H\b\032\025\020\000\032\0020\004*\0020\0042\006\020\003\032\0020\001H\b\032\025\020\000\032\0020\004*\0020\0042\006\020\003\032\0020\004H\b\032\025\020\000\032\0020\004*\0020\0042\006\020\003\032\0020\005H\b\032\025\020\000\032\0020\001*\0020\0052\006\020\003\032\0020\002H\b\032\025\020\000\032\0020\001*\0020\0052\006\020\003\032\0020\001H\b\032\025\020\000\032\0020\004*\0020\0052\006\020\003\032\0020\004H\b\032\025\020\000\032\0020\001*\0020\0052\006\020\003\032\0020\005H\b\032\025\020\006\032\0020\002*\0020\0022\006\020\003\032\0020\002H\b\032\025\020\006\032\0020\001*\0020\0022\006\020\003\032\0020\001H\b\032\025\020\006\032\0020\004*\0020\0022\006\020\003\032\0020\004H\b\032\025\020\006\032\0020\005*\0020\0022\006\020\003\032\0020\005H\b\032\025\020\006\032\0020\007*\0020\0072\006\020\003\032\0020\007H\b\032\025\020\006\032\0020\007*\0020\0072\006\020\003\032\0020\bH\b\032\025\020\006\032\0020\007*\0020\b2\006\020\003\032\0020\007H\b\032\025\020\006\032\0020\b*\0020\b2\006\020\003\032\0020\bH\b\032\025\020\006\032\0020\002*\0020\0012\006\020\003\032\0020\002H\b\032\025\020\006\032\0020\001*\0020\0012\006\020\003\032\0020\001H\b\032\025\020\006\032\0020\004*\0020\0012\006\020\003\032\0020\004H\b\032\025\020\006\032\0020\005*\0020\0012\006\020\003\032\0020\005H\b\032\025\020\006\032\0020\002*\0020\0042\006\020\003\032\0020\002H\b\032\025\020\006\032\0020\001*\0020\0042\006\020\003\032\0020\001H\b\032\025\020\006\032\0020\004*\0020\0042\006\020\003\032\0020\004H\b\032\025\020\006\032\0020\005*\0020\0042\006\020\003\032\0020\005H\b\032\025\020\006\032\0020\002*\0020\0052\006\020\003\032\0020\002H\b\032\025\020\006\032\0020\001*\0020\0052\006\020\003\032\0020\001H\b\032\025\020\006\032\0020\004*\0020\0052\006\020\003\032\0020\004H\b\032\025\020\006\032\0020\005*\0020\0052\006\020\003\032\0020\005H\b¨\006\t"}, d2={"floorDiv", "", "", "other", "", "", "mod", "", "", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/NumbersKt")
class NumbersKt__FloorDivModKt
  extends NumbersKt__BigIntegersKt
{
  private static final int floorDiv(byte paramByte1, byte paramByte2)
  {
    byte b = paramByte1 / paramByte2;
    int i = b;
    if ((paramByte1 ^ paramByte2) < 0)
    {
      i = b;
      if (b * paramByte2 != paramByte1) {
        i = b - 1;
      }
    }
    return i;
  }
  
  private static final int floorDiv(byte paramByte, int paramInt)
  {
    int j = paramByte / paramInt;
    int i = j;
    if ((paramByte ^ paramInt) < 0)
    {
      i = j;
      if (j * paramInt != paramByte) {
        i = j - 1;
      }
    }
    return i;
  }
  
  private static final int floorDiv(byte paramByte, short paramShort)
  {
    short s = paramByte / paramShort;
    int i = s;
    if ((paramByte ^ paramShort) < 0)
    {
      i = s;
      if (s * paramShort != paramByte) {
        i = s - 1;
      }
    }
    return i;
  }
  
  private static final int floorDiv(int paramInt, byte paramByte)
  {
    byte b = paramInt / paramByte;
    int i = b;
    if ((paramInt ^ paramByte) < 0)
    {
      i = b;
      if (b * paramByte != paramInt) {
        i = b - 1;
      }
    }
    return i;
  }
  
  private static final int floorDiv(int paramInt1, int paramInt2)
  {
    int j = paramInt1 / paramInt2;
    int i = j;
    if ((paramInt1 ^ paramInt2) < 0)
    {
      i = j;
      if (j * paramInt2 != paramInt1) {
        i = j - 1;
      }
    }
    return i;
  }
  
  private static final int floorDiv(int paramInt, short paramShort)
  {
    short s = paramInt / paramShort;
    int i = s;
    if ((paramInt ^ paramShort) < 0)
    {
      i = s;
      if (s * paramShort != paramInt) {
        i = s - 1;
      }
    }
    return i;
  }
  
  private static final int floorDiv(short paramShort, byte paramByte)
  {
    byte b = paramShort / paramByte;
    int i = b;
    if ((paramShort ^ paramByte) < 0)
    {
      i = b;
      if (b * paramByte != paramShort) {
        i = b - 1;
      }
    }
    return i;
  }
  
  private static final int floorDiv(short paramShort, int paramInt)
  {
    int j = paramShort / paramInt;
    int i = j;
    if ((paramShort ^ paramInt) < 0)
    {
      i = j;
      if (j * paramInt != paramShort) {
        i = j - 1;
      }
    }
    return i;
  }
  
  private static final int floorDiv(short paramShort1, short paramShort2)
  {
    short s = paramShort1 / paramShort2;
    int i = s;
    if ((paramShort1 ^ paramShort2) < 0)
    {
      i = s;
      if (s * paramShort2 != paramShort1) {
        i = s - 1;
      }
    }
    return i;
  }
  
  private static final long floorDiv(byte paramByte, long paramLong)
  {
    long l3 = paramByte;
    long l2 = l3 / paramLong;
    long l1 = l2;
    if ((l3 ^ paramLong) < 0L)
    {
      l1 = l2;
      if (l2 * paramLong != l3) {
        l1 = l2 - 1L;
      }
    }
    return l1;
  }
  
  private static final long floorDiv(int paramInt, long paramLong)
  {
    long l3 = paramInt;
    long l2 = l3 / paramLong;
    long l1 = l2;
    if ((l3 ^ paramLong) < 0L)
    {
      l1 = l2;
      if (l2 * paramLong != l3) {
        l1 = l2 - 1L;
      }
    }
    return l1;
  }
  
  private static final long floorDiv(long paramLong, byte paramByte)
  {
    long l3 = paramByte;
    long l2 = paramLong / l3;
    long l1 = l2;
    if ((paramLong ^ l3) < 0L)
    {
      l1 = l2;
      if (l3 * l2 != paramLong) {
        l1 = l2 - 1L;
      }
    }
    return l1;
  }
  
  private static final long floorDiv(long paramLong, int paramInt)
  {
    long l3 = paramInt;
    long l2 = paramLong / l3;
    long l1 = l2;
    if ((paramLong ^ l3) < 0L)
    {
      l1 = l2;
      if (l3 * l2 != paramLong) {
        l1 = l2 - 1L;
      }
    }
    return l1;
  }
  
  private static final long floorDiv(long paramLong1, long paramLong2)
  {
    long l2 = paramLong1 / paramLong2;
    long l1 = l2;
    if ((paramLong1 ^ paramLong2) < 0L)
    {
      l1 = l2;
      if (l2 * paramLong2 != paramLong1) {
        l1 = l2 - 1L;
      }
    }
    return l1;
  }
  
  private static final long floorDiv(long paramLong, short paramShort)
  {
    long l3 = paramShort;
    long l2 = paramLong / l3;
    long l1 = l2;
    if ((paramLong ^ l3) < 0L)
    {
      l1 = l2;
      if (l3 * l2 != paramLong) {
        l1 = l2 - 1L;
      }
    }
    return l1;
  }
  
  private static final long floorDiv(short paramShort, long paramLong)
  {
    long l3 = paramShort;
    long l2 = l3 / paramLong;
    long l1 = l2;
    if ((l3 ^ paramLong) < 0L)
    {
      l1 = l2;
      if (l2 * paramLong != l3) {
        l1 = l2 - 1L;
      }
    }
    return l1;
  }
  
  private static final byte mod(byte paramByte1, byte paramByte2)
  {
    paramByte1 %= paramByte2;
    return (byte)(paramByte1 + (((paramByte1 ^ paramByte2) & (-paramByte1 | paramByte1)) >> 31 & paramByte2));
  }
  
  private static final byte mod(int paramInt, byte paramByte)
  {
    paramInt %= paramByte;
    return (byte)(paramInt + (((paramInt ^ paramByte) & (-paramInt | paramInt)) >> 31 & paramByte));
  }
  
  private static final byte mod(long paramLong, byte paramByte)
  {
    long l = paramByte;
    paramLong %= l;
    return (byte)(int)(paramLong + (l & ((paramLong ^ l) & (-paramLong | paramLong)) >> 63));
  }
  
  private static final byte mod(short paramShort, byte paramByte)
  {
    paramShort %= paramByte;
    return (byte)(paramShort + (((paramShort ^ paramByte) & (-paramShort | paramShort)) >> 31 & paramByte));
  }
  
  private static final double mod(double paramDouble1, double paramDouble2)
  {
    paramDouble1 %= paramDouble2;
    int j = 1;
    int i;
    if (paramDouble1 == 0.0D) {
      i = 1;
    } else {
      i = 0;
    }
    if (i == 0)
    {
      if (Math.signum(paramDouble1) == Math.signum(paramDouble2)) {
        i = j;
      } else {
        i = 0;
      }
      if (i == 0) {
        paramDouble1 += paramDouble2;
      }
    }
    return paramDouble1;
  }
  
  private static final double mod(double paramDouble, float paramFloat)
  {
    double d2 = paramFloat;
    double d1 = paramDouble % d2;
    int j = 1;
    int i;
    if (d1 == 0.0D) {
      i = 1;
    } else {
      i = 0;
    }
    paramDouble = d1;
    if (i == 0)
    {
      if (Math.signum(d1) == Math.signum(d2)) {
        i = j;
      } else {
        i = 0;
      }
      paramDouble = d1;
      if (i == 0) {
        paramDouble = d1 + d2;
      }
    }
    return paramDouble;
  }
  
  private static final double mod(float paramFloat, double paramDouble)
  {
    double d2 = paramFloat % paramDouble;
    int j = 1;
    int i;
    if (d2 == 0.0D) {
      i = 1;
    } else {
      i = 0;
    }
    double d1 = d2;
    if (i == 0)
    {
      if (Math.signum(d2) == Math.signum(paramDouble)) {
        i = j;
      } else {
        i = 0;
      }
      d1 = d2;
      if (i == 0) {
        d1 = d2 + paramDouble;
      }
    }
    return d1;
  }
  
  private static final float mod(float paramFloat1, float paramFloat2)
  {
    paramFloat1 %= paramFloat2;
    int j = 1;
    int i;
    if (paramFloat1 == 0.0F) {
      i = 1;
    } else {
      i = 0;
    }
    if (i == 0)
    {
      if (Math.signum(paramFloat1) == Math.signum(paramFloat2)) {
        i = j;
      } else {
        i = 0;
      }
      if (i == 0) {
        paramFloat1 += paramFloat2;
      }
    }
    return paramFloat1;
  }
  
  private static final int mod(byte paramByte, int paramInt)
  {
    paramByte %= paramInt;
    return paramByte + (((paramByte ^ paramInt) & (-paramByte | paramByte)) >> 31 & paramInt);
  }
  
  private static final int mod(int paramInt1, int paramInt2)
  {
    paramInt1 %= paramInt2;
    return (((paramInt1 ^ paramInt2) & (-paramInt1 | paramInt1)) >> 31 & paramInt2) + paramInt1;
  }
  
  private static final int mod(long paramLong, int paramInt)
  {
    long l = paramInt;
    paramLong %= l;
    return (int)(paramLong + (l & ((paramLong ^ l) & (-paramLong | paramLong)) >> 63));
  }
  
  private static final int mod(short paramShort, int paramInt)
  {
    paramShort %= paramInt;
    return paramShort + (((paramShort ^ paramInt) & (-paramShort | paramShort)) >> 31 & paramInt);
  }
  
  private static final long mod(byte paramByte, long paramLong)
  {
    long l = paramByte % paramLong;
    return l + (((l ^ paramLong) & (-l | l)) >> 63 & paramLong);
  }
  
  private static final long mod(int paramInt, long paramLong)
  {
    long l = paramInt % paramLong;
    return l + (((l ^ paramLong) & (-l | l)) >> 63 & paramLong);
  }
  
  private static final long mod(long paramLong1, long paramLong2)
  {
    paramLong1 %= paramLong2;
    return (((paramLong1 ^ paramLong2) & (-paramLong1 | paramLong1)) >> 63 & paramLong2) + paramLong1;
  }
  
  private static final long mod(short paramShort, long paramLong)
  {
    long l = paramShort % paramLong;
    return l + (((l ^ paramLong) & (-l | l)) >> 63 & paramLong);
  }
  
  private static final short mod(byte paramByte, short paramShort)
  {
    paramByte %= paramShort;
    return (short)(paramByte + (((paramByte ^ paramShort) & (-paramByte | paramByte)) >> 31 & paramShort));
  }
  
  private static final short mod(int paramInt, short paramShort)
  {
    paramInt %= paramShort;
    return (short)(paramInt + (((paramInt ^ paramShort) & (-paramInt | paramInt)) >> 31 & paramShort));
  }
  
  private static final short mod(long paramLong, short paramShort)
  {
    long l = paramShort;
    paramLong %= l;
    return (short)(int)(paramLong + (l & ((paramLong ^ l) & (-paramLong | paramLong)) >> 63));
  }
  
  private static final short mod(short paramShort1, short paramShort2)
  {
    paramShort1 %= paramShort2;
    return (short)(paramShort1 + (((paramShort1 ^ paramShort2) & (-paramShort1 | paramShort1)) >> 31 & paramShort2));
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/NumbersKt__FloorDivModKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */