package kotlin;

@Metadata(d1={"\000\022\n\000\n\002\020\b\n\002\020\005\n\002\020\n\n\002\b\b\032\r\020\000\032\0020\001*\0020\002H\b\032\r\020\000\032\0020\001*\0020\003H\b\032\r\020\004\032\0020\001*\0020\002H\b\032\r\020\004\032\0020\001*\0020\003H\b\032\r\020\005\032\0020\001*\0020\002H\b\032\r\020\005\032\0020\001*\0020\003H\b\032\024\020\006\032\0020\002*\0020\0022\006\020\007\032\0020\001H\007\032\024\020\006\032\0020\003*\0020\0032\006\020\007\032\0020\001H\007\032\024\020\b\032\0020\002*\0020\0022\006\020\007\032\0020\001H\007\032\024\020\b\032\0020\003*\0020\0032\006\020\007\032\0020\001H\007\032\r\020\t\032\0020\002*\0020\002H\b\032\r\020\t\032\0020\003*\0020\003H\b\032\r\020\n\032\0020\002*\0020\002H\b\032\r\020\n\032\0020\003*\0020\003H\b¨\006\013"}, d2={"countLeadingZeroBits", "", "", "", "countOneBits", "countTrailingZeroBits", "rotateLeft", "bitCount", "rotateRight", "takeHighestOneBit", "takeLowestOneBit", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/NumbersKt")
class NumbersKt__NumbersKt
  extends NumbersKt__NumbersJVMKt
{
  private static final int countLeadingZeroBits(byte paramByte)
  {
    return Integer.numberOfLeadingZeros(paramByte & 0xFF) - 24;
  }
  
  private static final int countLeadingZeroBits(short paramShort)
  {
    return Integer.numberOfLeadingZeros(0xFFFF & paramShort) - 16;
  }
  
  private static final int countOneBits(byte paramByte)
  {
    return Integer.bitCount(paramByte & 0xFF);
  }
  
  private static final int countOneBits(short paramShort)
  {
    return Integer.bitCount(0xFFFF & paramShort);
  }
  
  private static final int countTrailingZeroBits(byte paramByte)
  {
    return Integer.numberOfTrailingZeros(paramByte | 0x100);
  }
  
  private static final int countTrailingZeroBits(short paramShort)
  {
    return Integer.numberOfTrailingZeros(0x10000 | paramShort);
  }
  
  public static final byte rotateLeft(byte paramByte, int paramInt)
  {
    return (byte)(paramByte << (paramInt & 0x7) | (paramByte & 0xFF) >>> 8 - (paramInt & 0x7));
  }
  
  public static final short rotateLeft(short paramShort, int paramInt)
  {
    return (short)(paramShort << (paramInt & 0xF) | (0xFFFF & paramShort) >>> 16 - (paramInt & 0xF));
  }
  
  public static final byte rotateRight(byte paramByte, int paramInt)
  {
    return (byte)(paramByte << 8 - (paramInt & 0x7) | (paramByte & 0xFF) >>> (paramInt & 0x7));
  }
  
  public static final short rotateRight(short paramShort, int paramInt)
  {
    return (short)(paramShort << 16 - (paramInt & 0xF) | (0xFFFF & paramShort) >>> (paramInt & 0xF));
  }
  
  private static final byte takeHighestOneBit(byte paramByte)
  {
    return (byte)Integer.highestOneBit(paramByte & 0xFF);
  }
  
  private static final short takeHighestOneBit(short paramShort)
  {
    return (short)Integer.highestOneBit(0xFFFF & paramShort);
  }
  
  private static final byte takeLowestOneBit(byte paramByte)
  {
    return (byte)Integer.lowestOneBit(paramByte);
  }
  
  private static final short takeLowestOneBit(short paramShort)
  {
    return (short)Integer.lowestOneBit(paramShort);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/NumbersKt__NumbersKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */