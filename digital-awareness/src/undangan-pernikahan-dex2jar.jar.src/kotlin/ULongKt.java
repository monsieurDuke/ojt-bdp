package kotlin;

@Metadata(d1={"\000,\n\000\n\002\030\002\n\002\020\005\n\000\n\002\020\006\n\000\n\002\020\007\n\000\n\002\020\b\n\000\n\002\020\t\n\000\n\002\020\n\n\002\b\002\032\025\020\000\032\0020\001*\0020\002H\bø\001\000¢\006\002\020\003\032\025\020\000\032\0020\001*\0020\004H\bø\001\000¢\006\002\020\005\032\025\020\000\032\0020\001*\0020\006H\bø\001\000¢\006\002\020\007\032\025\020\000\032\0020\001*\0020\bH\bø\001\000¢\006\002\020\t\032\025\020\000\032\0020\001*\0020\nH\bø\001\000¢\006\002\020\013\032\025\020\000\032\0020\001*\0020\fH\bø\001\000¢\006\002\020\r\002\004\n\002\b\031¨\006\016"}, d2={"toULong", "Lkotlin/ULong;", "", "(B)J", "", "(D)J", "", "(F)J", "", "(I)J", "", "(J)J", "", "(S)J", "kotlin-stdlib"}, k=2, mv={1, 7, 1}, xi=48)
public final class ULongKt
{
  private static final long toULong(byte paramByte)
  {
    return ULong.constructor-impl(paramByte);
  }
  
  private static final long toULong(double paramDouble)
  {
    return UnsignedKt.doubleToULong(paramDouble);
  }
  
  private static final long toULong(float paramFloat)
  {
    return UnsignedKt.doubleToULong(paramFloat);
  }
  
  private static final long toULong(int paramInt)
  {
    return ULong.constructor-impl(paramInt);
  }
  
  private static final long toULong(long paramLong)
  {
    return ULong.constructor-impl(paramLong);
  }
  
  private static final long toULong(short paramShort)
  {
    return ULong.constructor-impl(paramShort);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/ULongKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */