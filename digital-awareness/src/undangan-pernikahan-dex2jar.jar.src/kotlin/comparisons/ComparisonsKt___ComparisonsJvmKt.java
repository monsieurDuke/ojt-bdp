package kotlin.comparisons;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000F\n\002\b\002\n\002\020\017\n\002\b\006\n\002\020\021\n\000\n\002\020\005\n\002\020\022\n\002\020\006\n\002\020\023\n\002\020\007\n\002\020\024\n\002\020\b\n\002\020\025\n\002\020\t\n\002\020\026\n\002\020\n\n\002\020\027\n\002\b\002\032-\020\000\032\002H\001\"\016\b\000\020\001*\b\022\004\022\002H\0010\0022\006\020\003\032\002H\0012\006\020\004\032\002H\001H\007¢\006\002\020\005\0325\020\000\032\002H\001\"\016\b\000\020\001*\b\022\004\022\002H\0010\0022\006\020\003\032\002H\0012\006\020\004\032\002H\0012\006\020\006\032\002H\001H\007¢\006\002\020\007\0329\020\000\032\002H\001\"\016\b\000\020\001*\b\022\004\022\002H\0010\0022\006\020\003\032\002H\0012\022\020\b\032\n\022\006\b\001\022\002H\0010\t\"\002H\001H\007¢\006\002\020\n\032\031\020\000\032\0020\0132\006\020\003\032\0020\0132\006\020\004\032\0020\013H\b\032!\020\000\032\0020\0132\006\020\003\032\0020\0132\006\020\004\032\0020\0132\006\020\006\032\0020\013H\b\032\034\020\000\032\0020\0132\006\020\003\032\0020\0132\n\020\b\032\0020\f\"\0020\013H\007\032\031\020\000\032\0020\r2\006\020\003\032\0020\r2\006\020\004\032\0020\rH\b\032!\020\000\032\0020\r2\006\020\003\032\0020\r2\006\020\004\032\0020\r2\006\020\006\032\0020\rH\b\032\034\020\000\032\0020\r2\006\020\003\032\0020\r2\n\020\b\032\0020\016\"\0020\rH\007\032\031\020\000\032\0020\0172\006\020\003\032\0020\0172\006\020\004\032\0020\017H\b\032!\020\000\032\0020\0172\006\020\003\032\0020\0172\006\020\004\032\0020\0172\006\020\006\032\0020\017H\b\032\034\020\000\032\0020\0172\006\020\003\032\0020\0172\n\020\b\032\0020\020\"\0020\017H\007\032\031\020\000\032\0020\0212\006\020\003\032\0020\0212\006\020\004\032\0020\021H\b\032!\020\000\032\0020\0212\006\020\003\032\0020\0212\006\020\004\032\0020\0212\006\020\006\032\0020\021H\b\032\034\020\000\032\0020\0212\006\020\003\032\0020\0212\n\020\b\032\0020\022\"\0020\021H\007\032\031\020\000\032\0020\0232\006\020\003\032\0020\0232\006\020\004\032\0020\023H\b\032!\020\000\032\0020\0232\006\020\003\032\0020\0232\006\020\004\032\0020\0232\006\020\006\032\0020\023H\b\032\034\020\000\032\0020\0232\006\020\003\032\0020\0232\n\020\b\032\0020\024\"\0020\023H\007\032\031\020\000\032\0020\0252\006\020\003\032\0020\0252\006\020\004\032\0020\025H\b\032!\020\000\032\0020\0252\006\020\003\032\0020\0252\006\020\004\032\0020\0252\006\020\006\032\0020\025H\b\032\034\020\000\032\0020\0252\006\020\003\032\0020\0252\n\020\b\032\0020\026\"\0020\025H\007\032-\020\027\032\002H\001\"\016\b\000\020\001*\b\022\004\022\002H\0010\0022\006\020\003\032\002H\0012\006\020\004\032\002H\001H\007¢\006\002\020\005\0325\020\027\032\002H\001\"\016\b\000\020\001*\b\022\004\022\002H\0010\0022\006\020\003\032\002H\0012\006\020\004\032\002H\0012\006\020\006\032\002H\001H\007¢\006\002\020\007\0329\020\027\032\002H\001\"\016\b\000\020\001*\b\022\004\022\002H\0010\0022\006\020\003\032\002H\0012\022\020\b\032\n\022\006\b\001\022\002H\0010\t\"\002H\001H\007¢\006\002\020\n\032\031\020\027\032\0020\0132\006\020\003\032\0020\0132\006\020\004\032\0020\013H\b\032!\020\027\032\0020\0132\006\020\003\032\0020\0132\006\020\004\032\0020\0132\006\020\006\032\0020\013H\b\032\034\020\027\032\0020\0132\006\020\003\032\0020\0132\n\020\b\032\0020\f\"\0020\013H\007\032\031\020\027\032\0020\r2\006\020\003\032\0020\r2\006\020\004\032\0020\rH\b\032!\020\027\032\0020\r2\006\020\003\032\0020\r2\006\020\004\032\0020\r2\006\020\006\032\0020\rH\b\032\034\020\027\032\0020\r2\006\020\003\032\0020\r2\n\020\b\032\0020\016\"\0020\rH\007\032\031\020\027\032\0020\0172\006\020\003\032\0020\0172\006\020\004\032\0020\017H\b\032!\020\027\032\0020\0172\006\020\003\032\0020\0172\006\020\004\032\0020\0172\006\020\006\032\0020\017H\b\032\034\020\027\032\0020\0172\006\020\003\032\0020\0172\n\020\b\032\0020\020\"\0020\017H\007\032\031\020\027\032\0020\0212\006\020\003\032\0020\0212\006\020\004\032\0020\021H\b\032!\020\027\032\0020\0212\006\020\003\032\0020\0212\006\020\004\032\0020\0212\006\020\006\032\0020\021H\b\032\034\020\027\032\0020\0212\006\020\003\032\0020\0212\n\020\b\032\0020\022\"\0020\021H\007\032\031\020\027\032\0020\0232\006\020\003\032\0020\0232\006\020\004\032\0020\023H\b\032!\020\027\032\0020\0232\006\020\003\032\0020\0232\006\020\004\032\0020\0232\006\020\006\032\0020\023H\b\032\034\020\027\032\0020\0232\006\020\003\032\0020\0232\n\020\b\032\0020\024\"\0020\023H\007\032\031\020\027\032\0020\0252\006\020\003\032\0020\0252\006\020\004\032\0020\025H\b\032!\020\027\032\0020\0252\006\020\003\032\0020\0252\006\020\004\032\0020\0252\006\020\006\032\0020\025H\b\032\034\020\027\032\0020\0252\006\020\003\032\0020\0252\n\020\b\032\0020\026\"\0020\025H\007¨\006\030"}, d2={"maxOf", "T", "", "a", "b", "(Ljava/lang/Comparable;Ljava/lang/Comparable;)Ljava/lang/Comparable;", "c", "(Ljava/lang/Comparable;Ljava/lang/Comparable;Ljava/lang/Comparable;)Ljava/lang/Comparable;", "other", "", "(Ljava/lang/Comparable;[Ljava/lang/Comparable;)Ljava/lang/Comparable;", "", "", "", "", "", "", "", "", "", "", "", "", "minOf", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/comparisons/ComparisonsKt")
class ComparisonsKt___ComparisonsJvmKt
  extends ComparisonsKt__ComparisonsKt
{
  private static final byte maxOf(byte paramByte1, byte paramByte2)
  {
    return (byte)Math.max(paramByte1, paramByte2);
  }
  
  private static final byte maxOf(byte paramByte1, byte paramByte2, byte paramByte3)
  {
    return (byte)Math.max(paramByte1, Math.max(paramByte2, paramByte3));
  }
  
  public static final byte maxOf(byte paramByte, byte... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramVarArgs, "other");
    int j = paramVarArgs.length;
    for (int i = 0; i < j; i++) {
      paramByte = (byte)Math.max(paramByte, paramVarArgs[i]);
    }
    return paramByte;
  }
  
  private static final double maxOf(double paramDouble1, double paramDouble2)
  {
    return Math.max(paramDouble1, paramDouble2);
  }
  
  private static final double maxOf(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    return Math.max(paramDouble1, Math.max(paramDouble2, paramDouble3));
  }
  
  public static final double maxOf(double paramDouble, double... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramVarArgs, "other");
    int j = paramVarArgs.length;
    for (int i = 0; i < j; i++) {
      paramDouble = Math.max(paramDouble, paramVarArgs[i]);
    }
    return paramDouble;
  }
  
  private static final float maxOf(float paramFloat1, float paramFloat2)
  {
    return Math.max(paramFloat1, paramFloat2);
  }
  
  private static final float maxOf(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return Math.max(paramFloat1, Math.max(paramFloat2, paramFloat3));
  }
  
  public static final float maxOf(float paramFloat, float... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramVarArgs, "other");
    int j = paramVarArgs.length;
    for (int i = 0; i < j; i++) {
      paramFloat = Math.max(paramFloat, paramVarArgs[i]);
    }
    return paramFloat;
  }
  
  private static final int maxOf(int paramInt1, int paramInt2)
  {
    return Math.max(paramInt1, paramInt2);
  }
  
  private static final int maxOf(int paramInt1, int paramInt2, int paramInt3)
  {
    return Math.max(paramInt1, Math.max(paramInt2, paramInt3));
  }
  
  public static final int maxOf(int paramInt, int... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramVarArgs, "other");
    int i = paramInt;
    int j = paramVarArgs.length;
    for (paramInt = 0; paramInt < j; paramInt++) {
      i = Math.max(i, paramVarArgs[paramInt]);
    }
    return i;
  }
  
  private static final long maxOf(long paramLong1, long paramLong2)
  {
    return Math.max(paramLong1, paramLong2);
  }
  
  private static final long maxOf(long paramLong1, long paramLong2, long paramLong3)
  {
    return Math.max(paramLong1, Math.max(paramLong2, paramLong3));
  }
  
  public static final long maxOf(long paramLong, long... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramVarArgs, "other");
    int j = paramVarArgs.length;
    for (int i = 0; i < j; i++) {
      paramLong = Math.max(paramLong, paramVarArgs[i]);
    }
    return paramLong;
  }
  
  public static final <T extends Comparable<? super T>> T maxOf(T paramT1, T paramT2)
  {
    Intrinsics.checkNotNullParameter(paramT1, "a");
    Intrinsics.checkNotNullParameter(paramT2, "b");
    if (paramT1.compareTo(paramT2) >= 0) {
      paramT2 = paramT1;
    }
    return paramT2;
  }
  
  public static final <T extends Comparable<? super T>> T maxOf(T paramT1, T paramT2, T paramT3)
  {
    Intrinsics.checkNotNullParameter(paramT1, "a");
    Intrinsics.checkNotNullParameter(paramT2, "b");
    Intrinsics.checkNotNullParameter(paramT3, "c");
    return ComparisonsKt.maxOf(paramT1, ComparisonsKt.maxOf(paramT2, paramT3));
  }
  
  public static final <T extends Comparable<? super T>> T maxOf(T paramT, T... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramT, "a");
    Intrinsics.checkNotNullParameter(paramVarArgs, "other");
    int j = paramVarArgs.length;
    for (int i = 0; i < j; i++) {
      paramT = ComparisonsKt.maxOf(paramT, paramVarArgs[i]);
    }
    return paramT;
  }
  
  private static final short maxOf(short paramShort1, short paramShort2)
  {
    return (short)Math.max(paramShort1, paramShort2);
  }
  
  private static final short maxOf(short paramShort1, short paramShort2, short paramShort3)
  {
    return (short)Math.max(paramShort1, Math.max(paramShort2, paramShort3));
  }
  
  public static final short maxOf(short paramShort, short... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramVarArgs, "other");
    int j = paramVarArgs.length;
    for (int i = 0; i < j; i++) {
      paramShort = (short)Math.max(paramShort, paramVarArgs[i]);
    }
    return paramShort;
  }
  
  private static final byte minOf(byte paramByte1, byte paramByte2)
  {
    return (byte)Math.min(paramByte1, paramByte2);
  }
  
  private static final byte minOf(byte paramByte1, byte paramByte2, byte paramByte3)
  {
    return (byte)Math.min(paramByte1, Math.min(paramByte2, paramByte3));
  }
  
  public static final byte minOf(byte paramByte, byte... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramVarArgs, "other");
    int j = paramVarArgs.length;
    for (int i = 0; i < j; i++) {
      paramByte = (byte)Math.min(paramByte, paramVarArgs[i]);
    }
    return paramByte;
  }
  
  private static final double minOf(double paramDouble1, double paramDouble2)
  {
    return Math.min(paramDouble1, paramDouble2);
  }
  
  private static final double minOf(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    return Math.min(paramDouble1, Math.min(paramDouble2, paramDouble3));
  }
  
  public static final double minOf(double paramDouble, double... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramVarArgs, "other");
    int j = paramVarArgs.length;
    for (int i = 0; i < j; i++) {
      paramDouble = Math.min(paramDouble, paramVarArgs[i]);
    }
    return paramDouble;
  }
  
  private static final float minOf(float paramFloat1, float paramFloat2)
  {
    return Math.min(paramFloat1, paramFloat2);
  }
  
  private static final float minOf(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return Math.min(paramFloat1, Math.min(paramFloat2, paramFloat3));
  }
  
  public static final float minOf(float paramFloat, float... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramVarArgs, "other");
    int j = paramVarArgs.length;
    for (int i = 0; i < j; i++) {
      paramFloat = Math.min(paramFloat, paramVarArgs[i]);
    }
    return paramFloat;
  }
  
  private static final int minOf(int paramInt1, int paramInt2)
  {
    return Math.min(paramInt1, paramInt2);
  }
  
  private static final int minOf(int paramInt1, int paramInt2, int paramInt3)
  {
    return Math.min(paramInt1, Math.min(paramInt2, paramInt3));
  }
  
  public static final int minOf(int paramInt, int... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramVarArgs, "other");
    int i = paramInt;
    int j = paramVarArgs.length;
    for (paramInt = 0; paramInt < j; paramInt++) {
      i = Math.min(i, paramVarArgs[paramInt]);
    }
    return i;
  }
  
  private static final long minOf(long paramLong1, long paramLong2)
  {
    return Math.min(paramLong1, paramLong2);
  }
  
  private static final long minOf(long paramLong1, long paramLong2, long paramLong3)
  {
    return Math.min(paramLong1, Math.min(paramLong2, paramLong3));
  }
  
  public static final long minOf(long paramLong, long... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramVarArgs, "other");
    int j = paramVarArgs.length;
    for (int i = 0; i < j; i++) {
      paramLong = Math.min(paramLong, paramVarArgs[i]);
    }
    return paramLong;
  }
  
  public static final <T extends Comparable<? super T>> T minOf(T paramT1, T paramT2)
  {
    Intrinsics.checkNotNullParameter(paramT1, "a");
    Intrinsics.checkNotNullParameter(paramT2, "b");
    if (paramT1.compareTo(paramT2) > 0) {
      paramT1 = paramT2;
    }
    return paramT1;
  }
  
  public static final <T extends Comparable<? super T>> T minOf(T paramT1, T paramT2, T paramT3)
  {
    Intrinsics.checkNotNullParameter(paramT1, "a");
    Intrinsics.checkNotNullParameter(paramT2, "b");
    Intrinsics.checkNotNullParameter(paramT3, "c");
    return ComparisonsKt.minOf(paramT1, ComparisonsKt.minOf(paramT2, paramT3));
  }
  
  public static final <T extends Comparable<? super T>> T minOf(T paramT, T... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramT, "a");
    Intrinsics.checkNotNullParameter(paramVarArgs, "other");
    int j = paramVarArgs.length;
    for (int i = 0; i < j; i++) {
      paramT = ComparisonsKt.minOf(paramT, paramVarArgs[i]);
    }
    return paramT;
  }
  
  private static final short minOf(short paramShort1, short paramShort2)
  {
    return (short)Math.min(paramShort1, paramShort2);
  }
  
  private static final short minOf(short paramShort1, short paramShort2, short paramShort3)
  {
    return (short)Math.min(paramShort1, Math.min(paramShort2, paramShort3));
  }
  
  public static final short minOf(short paramShort, short... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramVarArgs, "other");
    int j = paramVarArgs.length;
    for (int i = 0; i < j; i++) {
      paramShort = (short)Math.min(paramShort, paramVarArgs[i]);
    }
    return paramShort;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/comparisons/ComparisonsKt___ComparisonsJvmKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */