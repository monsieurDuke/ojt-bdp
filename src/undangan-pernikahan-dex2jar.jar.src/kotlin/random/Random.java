package kotlin.random;

import java.io.Serializable;
import kotlin.Metadata;
import kotlin.internal.PlatformImplementations;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;

@Metadata(d1={"\000:\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\b\n\002\b\002\n\002\020\013\n\000\n\002\020\022\n\002\b\005\n\002\020\006\n\002\b\003\n\002\020\007\n\002\b\002\n\002\020\t\n\002\b\002\b'\030\000 \0272\0020\001:\001\027B\005¢\006\002\020\002J\020\020\003\032\0020\0042\006\020\005\032\0020\004H&J\b\020\006\032\0020\007H\026J\020\020\b\032\0020\t2\006\020\n\032\0020\tH\026J$\020\b\032\0020\t2\006\020\n\032\0020\t2\b\b\002\020\013\032\0020\0042\b\b\002\020\f\032\0020\004H\026J\020\020\b\032\0020\t2\006\020\r\032\0020\004H\026J\b\020\016\032\0020\017H\026J\020\020\016\032\0020\0172\006\020\020\032\0020\017H\026J\030\020\016\032\0020\0172\006\020\021\032\0020\0172\006\020\020\032\0020\017H\026J\b\020\022\032\0020\023H\026J\b\020\024\032\0020\004H\026J\020\020\024\032\0020\0042\006\020\020\032\0020\004H\026J\030\020\024\032\0020\0042\006\020\021\032\0020\0042\006\020\020\032\0020\004H\026J\b\020\025\032\0020\026H\026J\020\020\025\032\0020\0262\006\020\020\032\0020\026H\026J\030\020\025\032\0020\0262\006\020\021\032\0020\0262\006\020\020\032\0020\026H\026¨\006\030"}, d2={"Lkotlin/random/Random;", "", "()V", "nextBits", "", "bitCount", "nextBoolean", "", "nextBytes", "", "array", "fromIndex", "toIndex", "size", "nextDouble", "", "until", "from", "nextFloat", "", "nextInt", "nextLong", "", "Default", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public abstract class Random
{
  public static final Default Default = new Default(null);
  private static final Random defaultRandom = PlatformImplementationsKt.IMPLEMENTATIONS.defaultPlatformRandom();
  
  public abstract int nextBits(int paramInt);
  
  public boolean nextBoolean()
  {
    boolean bool = true;
    if (nextBits(1) == 0) {
      bool = false;
    }
    return bool;
  }
  
  public byte[] nextBytes(int paramInt)
  {
    return nextBytes(new byte[paramInt]);
  }
  
  public byte[] nextBytes(byte[] paramArrayOfByte)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "array");
    return nextBytes(paramArrayOfByte, 0, paramArrayOfByte.length);
  }
  
  public byte[] nextBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "array");
    int i = paramArrayOfByte.length;
    int j = 0;
    boolean bool = new IntRange(0, i).contains(paramInt1);
    int k = 1;
    if ((bool) && (new IntRange(0, paramArrayOfByte.length).contains(paramInt2))) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      if (paramInt1 <= paramInt2) {
        i = k;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        k = (paramInt2 - paramInt1) / 4;
        for (i = j; i < k; i++)
        {
          j = nextInt();
          paramArrayOfByte[paramInt1] = ((byte)j);
          paramArrayOfByte[(paramInt1 + 1)] = ((byte)(j >>> 8));
          paramArrayOfByte[(paramInt1 + 2)] = ((byte)(j >>> 16));
          paramArrayOfByte[(paramInt1 + 3)] = ((byte)(j >>> 24));
          paramInt1 += 4;
        }
        i = paramInt2 - paramInt1;
        j = nextBits(i * 8);
        for (paramInt2 = 0; paramInt2 < i; paramInt2++) {
          paramArrayOfByte[(paramInt1 + paramInt2)] = ((byte)(j >>> paramInt2 * 8));
        }
        return paramArrayOfByte;
      }
      throw new IllegalArgumentException(("fromIndex (" + paramInt1 + ") must be not greater than toIndex (" + paramInt2 + ").").toString());
    }
    throw new IllegalArgumentException(("fromIndex (" + paramInt1 + ") or toIndex (" + paramInt2 + ") are out of range: 0.." + paramArrayOfByte.length + '.').toString());
  }
  
  public double nextDouble()
  {
    return PlatformRandomKt.doubleFromParts(nextBits(26), nextBits(27));
  }
  
  public double nextDouble(double paramDouble)
  {
    return nextDouble(0.0D, paramDouble);
  }
  
  public double nextDouble(double paramDouble1, double paramDouble2)
  {
    RandomKt.checkRangeBounds(paramDouble1, paramDouble2);
    double d1 = paramDouble2 - paramDouble1;
    if (Double.isInfinite(d1))
    {
      boolean bool = Double.isInfinite(paramDouble1);
      int j = 1;
      int i;
      if ((!bool) && (!Double.isNaN(paramDouble1))) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        if ((!Double.isInfinite(paramDouble2)) && (!Double.isNaN(paramDouble2))) {
          i = j;
        } else {
          i = 0;
        }
        if (i != 0)
        {
          double d2 = nextDouble();
          d1 = 2;
          d1 = d2 * (paramDouble2 / d1 - paramDouble1 / d1);
          paramDouble1 = paramDouble1 + d1 + d1;
          break label127;
        }
      }
    }
    paramDouble1 += nextDouble() * d1;
    label127:
    if (paramDouble1 >= paramDouble2) {
      paramDouble1 = Math.nextAfter(paramDouble2, Double.NEGATIVE_INFINITY);
    }
    return paramDouble1;
  }
  
  public float nextFloat()
  {
    return nextBits(24) / 1.6777216E7F;
  }
  
  public int nextInt()
  {
    return nextBits(32);
  }
  
  public int nextInt(int paramInt)
  {
    return nextInt(0, paramInt);
  }
  
  public int nextInt(int paramInt1, int paramInt2)
  {
    RandomKt.checkRangeBounds(paramInt1, paramInt2);
    int i = paramInt2 - paramInt1;
    int j;
    if ((i <= 0) && (i != Integer.MIN_VALUE))
    {
      int k;
      do
      {
        k = nextInt();
        j = 0;
        i = j;
        if (paramInt1 <= k)
        {
          i = j;
          if (k < paramInt2) {
            i = 1;
          }
        }
      } while (i == 0);
      return k;
    }
    if ((-i & i) == i) {
      paramInt2 = nextBits(RandomKt.fastLog2(i));
    } else {
      do
      {
        j = nextInt() >>> 1;
        paramInt2 = j % i;
      } while (j - paramInt2 + (i - 1) < 0);
    }
    return paramInt1 + paramInt2;
  }
  
  public long nextLong()
  {
    return (nextInt() << 32) + nextInt();
  }
  
  public long nextLong(long paramLong)
  {
    return nextLong(0L, paramLong);
  }
  
  public long nextLong(long paramLong1, long paramLong2)
  {
    RandomKt.checkRangeBounds(paramLong1, paramLong2);
    long l1 = paramLong2 - paramLong1;
    int j;
    int i;
    if (l1 > 0L)
    {
      if ((-l1 & l1) == l1)
      {
        j = (int)l1;
        i = (int)(l1 >>> 32);
        if (j != 0)
        {
          paramLong2 = 0xFFFFFFFF & nextBits(RandomKt.fastLog2(j));
        }
        else if (i == 1)
        {
          paramLong2 = 0xFFFFFFFF & nextInt();
        }
        else
        {
          paramLong2 = nextBits(RandomKt.fastLog2(i));
          paramLong2 = (0xFFFFFFFF & nextInt()) + (paramLong2 << 32);
        }
      }
      else
      {
        long l2;
        do
        {
          l2 = nextLong() >>> 1;
          paramLong2 = l2 % l1;
        } while (l2 - paramLong2 + (l1 - 1L) < 0L);
      }
      return paramLong1 + paramLong2;
    }
    do
    {
      l1 = nextLong();
      j = 0;
      i = j;
      if (paramLong1 <= l1)
      {
        i = j;
        if (l1 < paramLong2) {
          i = 1;
        }
      }
    } while (i == 0);
    return l1;
  }
  
  @Metadata(d1={"\000H\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\003\n\002\020\b\n\002\b\002\n\002\020\013\n\000\n\002\020\022\n\002\b\005\n\002\020\006\n\002\b\003\n\002\020\007\n\002\b\002\n\002\020\t\n\000\n\002\020\000\n\002\b\002\b\003\030\0002\0020\0012\0060\002j\002`\003:\001\034B\007\b\002¢\006\002\020\004J\020\020\006\032\0020\0072\006\020\b\032\0020\007H\026J\b\020\t\032\0020\nH\026J\020\020\013\032\0020\f2\006\020\r\032\0020\fH\026J \020\013\032\0020\f2\006\020\r\032\0020\f2\006\020\016\032\0020\0072\006\020\017\032\0020\007H\026J\020\020\013\032\0020\f2\006\020\020\032\0020\007H\026J\b\020\021\032\0020\022H\026J\020\020\021\032\0020\0222\006\020\023\032\0020\022H\026J\030\020\021\032\0020\0222\006\020\024\032\0020\0222\006\020\023\032\0020\022H\026J\b\020\025\032\0020\026H\026J\b\020\027\032\0020\007H\026J\020\020\027\032\0020\0072\006\020\023\032\0020\007H\026J\030\020\027\032\0020\0072\006\020\024\032\0020\0072\006\020\023\032\0020\007H\026J\b\020\030\032\0020\031H\026J\020\020\030\032\0020\0312\006\020\023\032\0020\031H\026J\030\020\030\032\0020\0312\006\020\024\032\0020\0312\006\020\023\032\0020\031H\026J\b\020\032\032\0020\033H\002R\016\020\005\032\0020\001X\004¢\006\002\n\000¨\006\035"}, d2={"Lkotlin/random/Random$Default;", "Lkotlin/random/Random;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "()V", "defaultRandom", "nextBits", "", "bitCount", "nextBoolean", "", "nextBytes", "", "array", "fromIndex", "toIndex", "size", "nextDouble", "", "until", "from", "nextFloat", "", "nextInt", "nextLong", "", "writeReplace", "", "Serialized", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
  public static final class Default
    extends Random
    implements Serializable
  {
    private final Object writeReplace()
    {
      return Serialized.INSTANCE;
    }
    
    public int nextBits(int paramInt)
    {
      return Random.access$getDefaultRandom$cp().nextBits(paramInt);
    }
    
    public boolean nextBoolean()
    {
      return Random.access$getDefaultRandom$cp().nextBoolean();
    }
    
    public byte[] nextBytes(int paramInt)
    {
      return Random.access$getDefaultRandom$cp().nextBytes(paramInt);
    }
    
    public byte[] nextBytes(byte[] paramArrayOfByte)
    {
      Intrinsics.checkNotNullParameter(paramArrayOfByte, "array");
      return Random.access$getDefaultRandom$cp().nextBytes(paramArrayOfByte);
    }
    
    public byte[] nextBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    {
      Intrinsics.checkNotNullParameter(paramArrayOfByte, "array");
      return Random.access$getDefaultRandom$cp().nextBytes(paramArrayOfByte, paramInt1, paramInt2);
    }
    
    public double nextDouble()
    {
      return Random.access$getDefaultRandom$cp().nextDouble();
    }
    
    public double nextDouble(double paramDouble)
    {
      return Random.access$getDefaultRandom$cp().nextDouble(paramDouble);
    }
    
    public double nextDouble(double paramDouble1, double paramDouble2)
    {
      return Random.access$getDefaultRandom$cp().nextDouble(paramDouble1, paramDouble2);
    }
    
    public float nextFloat()
    {
      return Random.access$getDefaultRandom$cp().nextFloat();
    }
    
    public int nextInt()
    {
      return Random.access$getDefaultRandom$cp().nextInt();
    }
    
    public int nextInt(int paramInt)
    {
      return Random.access$getDefaultRandom$cp().nextInt(paramInt);
    }
    
    public int nextInt(int paramInt1, int paramInt2)
    {
      return Random.access$getDefaultRandom$cp().nextInt(paramInt1, paramInt2);
    }
    
    public long nextLong()
    {
      return Random.access$getDefaultRandom$cp().nextLong();
    }
    
    public long nextLong(long paramLong)
    {
      return Random.access$getDefaultRandom$cp().nextLong(paramLong);
    }
    
    public long nextLong(long paramLong1, long paramLong2)
    {
      return Random.access$getDefaultRandom$cp().nextLong(paramLong1, paramLong2);
    }
    
    @Metadata(d1={"\000\034\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\t\n\000\n\002\020\000\n\000\bÂ\002\030\0002\0060\001j\002`\002B\007\b\002¢\006\002\020\003J\b\020\006\032\0020\007H\002R\016\020\004\032\0020\005XT¢\006\002\n\000¨\006\b"}, d2={"Lkotlin/random/Random$Default$Serialized;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "()V", "serialVersionUID", "", "readResolve", "", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
    private static final class Serialized
      implements Serializable
    {
      public static final Serialized INSTANCE = new Serialized();
      private static final long serialVersionUID = 0L;
      
      private final Object readResolve()
      {
        return Random.Default;
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/random/Random.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */