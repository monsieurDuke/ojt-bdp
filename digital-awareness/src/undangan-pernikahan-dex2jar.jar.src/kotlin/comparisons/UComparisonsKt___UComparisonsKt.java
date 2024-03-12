package kotlin.comparisons;

import kotlin.Metadata;
import kotlin.UByteArray;
import kotlin.UIntArray;
import kotlin.ULongArray;
import kotlin.UShortArray;
import kotlin.UnsignedKt;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000B\n\000\n\002\030\002\n\002\b\b\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\020\032\"\020\000\032\0020\0012\006\020\002\032\0020\0012\006\020\003\032\0020\001H\007ø\001\000¢\006\004\b\004\020\005\032+\020\000\032\0020\0012\006\020\002\032\0020\0012\006\020\003\032\0020\0012\006\020\006\032\0020\001H\bø\001\000¢\006\004\b\007\020\b\032&\020\000\032\0020\0012\006\020\002\032\0020\0012\n\020\t\032\0020\n\"\0020\001H\007ø\001\000¢\006\004\b\013\020\f\032\"\020\000\032\0020\r2\006\020\002\032\0020\r2\006\020\003\032\0020\rH\007ø\001\000¢\006\004\b\016\020\017\032+\020\000\032\0020\r2\006\020\002\032\0020\r2\006\020\003\032\0020\r2\006\020\006\032\0020\rH\bø\001\000¢\006\004\b\020\020\021\032&\020\000\032\0020\r2\006\020\002\032\0020\r2\n\020\t\032\0020\022\"\0020\rH\007ø\001\000¢\006\004\b\023\020\024\032\"\020\000\032\0020\0252\006\020\002\032\0020\0252\006\020\003\032\0020\025H\007ø\001\000¢\006\004\b\026\020\027\032+\020\000\032\0020\0252\006\020\002\032\0020\0252\006\020\003\032\0020\0252\006\020\006\032\0020\025H\bø\001\000¢\006\004\b\030\020\031\032&\020\000\032\0020\0252\006\020\002\032\0020\0252\n\020\t\032\0020\032\"\0020\025H\007ø\001\000¢\006\004\b\033\020\034\032\"\020\000\032\0020\0352\006\020\002\032\0020\0352\006\020\003\032\0020\035H\007ø\001\000¢\006\004\b\036\020\037\032+\020\000\032\0020\0352\006\020\002\032\0020\0352\006\020\003\032\0020\0352\006\020\006\032\0020\035H\bø\001\000¢\006\004\b \020!\032&\020\000\032\0020\0352\006\020\002\032\0020\0352\n\020\t\032\0020\"\"\0020\035H\007ø\001\000¢\006\004\b#\020$\032\"\020%\032\0020\0012\006\020\002\032\0020\0012\006\020\003\032\0020\001H\007ø\001\000¢\006\004\b&\020\005\032+\020%\032\0020\0012\006\020\002\032\0020\0012\006\020\003\032\0020\0012\006\020\006\032\0020\001H\bø\001\000¢\006\004\b'\020\b\032&\020%\032\0020\0012\006\020\002\032\0020\0012\n\020\t\032\0020\n\"\0020\001H\007ø\001\000¢\006\004\b(\020\f\032\"\020%\032\0020\r2\006\020\002\032\0020\r2\006\020\003\032\0020\rH\007ø\001\000¢\006\004\b)\020\017\032+\020%\032\0020\r2\006\020\002\032\0020\r2\006\020\003\032\0020\r2\006\020\006\032\0020\rH\bø\001\000¢\006\004\b*\020\021\032&\020%\032\0020\r2\006\020\002\032\0020\r2\n\020\t\032\0020\022\"\0020\rH\007ø\001\000¢\006\004\b+\020\024\032\"\020%\032\0020\0252\006\020\002\032\0020\0252\006\020\003\032\0020\025H\007ø\001\000¢\006\004\b,\020\027\032+\020%\032\0020\0252\006\020\002\032\0020\0252\006\020\003\032\0020\0252\006\020\006\032\0020\025H\bø\001\000¢\006\004\b-\020\031\032&\020%\032\0020\0252\006\020\002\032\0020\0252\n\020\t\032\0020\032\"\0020\025H\007ø\001\000¢\006\004\b.\020\034\032\"\020%\032\0020\0352\006\020\002\032\0020\0352\006\020\003\032\0020\035H\007ø\001\000¢\006\004\b/\020\037\032+\020%\032\0020\0352\006\020\002\032\0020\0352\006\020\003\032\0020\0352\006\020\006\032\0020\035H\bø\001\000¢\006\004\b0\020!\032&\020%\032\0020\0352\006\020\002\032\0020\0352\n\020\t\032\0020\"\"\0020\035H\007ø\001\000¢\006\004\b1\020$\002\004\n\002\b\031¨\0062"}, d2={"maxOf", "Lkotlin/UByte;", "a", "b", "maxOf-Kr8caGY", "(BB)B", "c", "maxOf-b33U2AM", "(BBB)B", "other", "Lkotlin/UByteArray;", "maxOf-Wr6uiD8", "(B[B)B", "Lkotlin/UInt;", "maxOf-J1ME1BU", "(II)I", "maxOf-WZ9TVnA", "(III)I", "Lkotlin/UIntArray;", "maxOf-Md2H83M", "(I[I)I", "Lkotlin/ULong;", "maxOf-eb3DHEI", "(JJ)J", "maxOf-sambcqE", "(JJJ)J", "Lkotlin/ULongArray;", "maxOf-R03FKyM", "(J[J)J", "Lkotlin/UShort;", "maxOf-5PvTz6A", "(SS)S", "maxOf-VKSA0NQ", "(SSS)S", "Lkotlin/UShortArray;", "maxOf-t1qELG4", "(S[S)S", "minOf", "minOf-Kr8caGY", "minOf-b33U2AM", "minOf-Wr6uiD8", "minOf-J1ME1BU", "minOf-WZ9TVnA", "minOf-Md2H83M", "minOf-eb3DHEI", "minOf-sambcqE", "minOf-R03FKyM", "minOf-5PvTz6A", "minOf-VKSA0NQ", "minOf-t1qELG4", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/comparisons/UComparisonsKt")
class UComparisonsKt___UComparisonsKt
{
  public static final short maxOf-5PvTz6A(short paramShort1, short paramShort2)
  {
    if (Intrinsics.compare(paramShort1 & 0xFFFF, 0xFFFF & paramShort2) < 0) {
      paramShort1 = paramShort2;
    }
    return paramShort1;
  }
  
  public static final int maxOf-J1ME1BU(int paramInt1, int paramInt2)
  {
    if (UnsignedKt.uintCompare(paramInt1, paramInt2) < 0) {
      paramInt1 = paramInt2;
    }
    return paramInt1;
  }
  
  public static final byte maxOf-Kr8caGY(byte paramByte1, byte paramByte2)
  {
    if (Intrinsics.compare(paramByte1 & 0xFF, paramByte2 & 0xFF) >= 0) {
      paramByte2 = paramByte1;
    }
    return paramByte2;
  }
  
  public static final int maxOf-Md2H83M(int paramInt, int... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramVarArgs, "other");
    int i = paramInt;
    int j = UIntArray.getSize-impl(paramVarArgs);
    for (paramInt = 0; paramInt < j; paramInt++) {
      i = UComparisonsKt.maxOf-J1ME1BU(i, UIntArray.get-pVg5ArA(paramVarArgs, paramInt));
    }
    return i;
  }
  
  public static final long maxOf-R03FKyM(long paramLong, long... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramVarArgs, "other");
    int j = ULongArray.getSize-impl(paramVarArgs);
    for (int i = 0; i < j; i++) {
      paramLong = UComparisonsKt.maxOf-eb3DHEI(paramLong, ULongArray.get-s-VKNKU(paramVarArgs, i));
    }
    return paramLong;
  }
  
  private static final short maxOf-VKSA0NQ(short paramShort1, short paramShort2, short paramShort3)
  {
    return UComparisonsKt.maxOf-5PvTz6A(paramShort1, UComparisonsKt.maxOf-5PvTz6A(paramShort2, paramShort3));
  }
  
  private static final int maxOf-WZ9TVnA(int paramInt1, int paramInt2, int paramInt3)
  {
    return UComparisonsKt.maxOf-J1ME1BU(paramInt1, UComparisonsKt.maxOf-J1ME1BU(paramInt2, paramInt3));
  }
  
  public static final byte maxOf-Wr6uiD8(byte paramByte, byte... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramVarArgs, "other");
    int j = UByteArray.getSize-impl(paramVarArgs);
    for (int i = 0; i < j; i++) {
      paramByte = UComparisonsKt.maxOf-Kr8caGY(paramByte, UByteArray.get-w2LRezQ(paramVarArgs, i));
    }
    return paramByte;
  }
  
  private static final byte maxOf-b33U2AM(byte paramByte1, byte paramByte2, byte paramByte3)
  {
    return UComparisonsKt.maxOf-Kr8caGY(paramByte1, UComparisonsKt.maxOf-Kr8caGY(paramByte2, paramByte3));
  }
  
  public static final long maxOf-eb3DHEI(long paramLong1, long paramLong2)
  {
    if (UnsignedKt.ulongCompare(paramLong1, paramLong2) < 0) {
      paramLong1 = paramLong2;
    }
    return paramLong1;
  }
  
  private static final long maxOf-sambcqE(long paramLong1, long paramLong2, long paramLong3)
  {
    return UComparisonsKt.maxOf-eb3DHEI(paramLong1, UComparisonsKt.maxOf-eb3DHEI(paramLong2, paramLong3));
  }
  
  public static final short maxOf-t1qELG4(short paramShort, short... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramVarArgs, "other");
    int j = UShortArray.getSize-impl(paramVarArgs);
    for (int i = 0; i < j; i++) {
      paramShort = UComparisonsKt.maxOf-5PvTz6A(paramShort, UShortArray.get-Mh2AYeg(paramVarArgs, i));
    }
    return paramShort;
  }
  
  public static final short minOf-5PvTz6A(short paramShort1, short paramShort2)
  {
    if (Intrinsics.compare(paramShort1 & 0xFFFF, 0xFFFF & paramShort2) > 0) {
      paramShort1 = paramShort2;
    }
    return paramShort1;
  }
  
  public static final int minOf-J1ME1BU(int paramInt1, int paramInt2)
  {
    if (UnsignedKt.uintCompare(paramInt1, paramInt2) > 0) {
      paramInt1 = paramInt2;
    }
    return paramInt1;
  }
  
  public static final byte minOf-Kr8caGY(byte paramByte1, byte paramByte2)
  {
    if (Intrinsics.compare(paramByte1 & 0xFF, paramByte2 & 0xFF) <= 0) {
      paramByte2 = paramByte1;
    }
    return paramByte2;
  }
  
  public static final int minOf-Md2H83M(int paramInt, int... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramVarArgs, "other");
    int i = paramInt;
    int j = UIntArray.getSize-impl(paramVarArgs);
    for (paramInt = 0; paramInt < j; paramInt++) {
      i = UComparisonsKt.minOf-J1ME1BU(i, UIntArray.get-pVg5ArA(paramVarArgs, paramInt));
    }
    return i;
  }
  
  public static final long minOf-R03FKyM(long paramLong, long... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramVarArgs, "other");
    int j = ULongArray.getSize-impl(paramVarArgs);
    for (int i = 0; i < j; i++) {
      paramLong = UComparisonsKt.minOf-eb3DHEI(paramLong, ULongArray.get-s-VKNKU(paramVarArgs, i));
    }
    return paramLong;
  }
  
  private static final short minOf-VKSA0NQ(short paramShort1, short paramShort2, short paramShort3)
  {
    return UComparisonsKt.minOf-5PvTz6A(paramShort1, UComparisonsKt.minOf-5PvTz6A(paramShort2, paramShort3));
  }
  
  private static final int minOf-WZ9TVnA(int paramInt1, int paramInt2, int paramInt3)
  {
    return UComparisonsKt.minOf-J1ME1BU(paramInt1, UComparisonsKt.minOf-J1ME1BU(paramInt2, paramInt3));
  }
  
  public static final byte minOf-Wr6uiD8(byte paramByte, byte... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramVarArgs, "other");
    int j = UByteArray.getSize-impl(paramVarArgs);
    for (int i = 0; i < j; i++) {
      paramByte = UComparisonsKt.minOf-Kr8caGY(paramByte, UByteArray.get-w2LRezQ(paramVarArgs, i));
    }
    return paramByte;
  }
  
  private static final byte minOf-b33U2AM(byte paramByte1, byte paramByte2, byte paramByte3)
  {
    return UComparisonsKt.minOf-Kr8caGY(paramByte1, UComparisonsKt.minOf-Kr8caGY(paramByte2, paramByte3));
  }
  
  public static final long minOf-eb3DHEI(long paramLong1, long paramLong2)
  {
    if (UnsignedKt.ulongCompare(paramLong1, paramLong2) > 0) {
      paramLong1 = paramLong2;
    }
    return paramLong1;
  }
  
  private static final long minOf-sambcqE(long paramLong1, long paramLong2, long paramLong3)
  {
    return UComparisonsKt.minOf-eb3DHEI(paramLong1, UComparisonsKt.minOf-eb3DHEI(paramLong2, paramLong3));
  }
  
  public static final short minOf-t1qELG4(short paramShort, short... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramVarArgs, "other");
    int j = UShortArray.getSize-impl(paramVarArgs);
    for (int i = 0; i < j; i++) {
      paramShort = UComparisonsKt.minOf-5PvTz6A(paramShort, UShortArray.get-Mh2AYeg(paramVarArgs, i));
    }
    return paramShort;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/comparisons/UComparisonsKt___UComparisonsKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */