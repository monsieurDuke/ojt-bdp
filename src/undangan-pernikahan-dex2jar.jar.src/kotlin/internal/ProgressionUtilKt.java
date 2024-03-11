package kotlin.internal;

import kotlin.Metadata;

@Metadata(d1={"\000\022\n\000\n\002\020\b\n\002\b\003\n\002\020\t\n\002\b\006\032 \020\000\032\0020\0012\006\020\002\032\0020\0012\006\020\003\032\0020\0012\006\020\004\032\0020\001H\002\032 \020\000\032\0020\0052\006\020\002\032\0020\0052\006\020\003\032\0020\0052\006\020\004\032\0020\005H\002\032 \020\006\032\0020\0012\006\020\007\032\0020\0012\006\020\b\032\0020\0012\006\020\t\032\0020\001H\001\032 \020\006\032\0020\0052\006\020\007\032\0020\0052\006\020\b\032\0020\0052\006\020\t\032\0020\005H\001\032\030\020\n\032\0020\0012\006\020\002\032\0020\0012\006\020\003\032\0020\001H\002\032\030\020\n\032\0020\0052\006\020\002\032\0020\0052\006\020\003\032\0020\005H\002¨\006\013"}, d2={"differenceModulo", "", "a", "b", "c", "", "getProgressionLastElement", "start", "end", "step", "mod", "kotlin-stdlib"}, k=2, mv={1, 7, 1}, xi=48)
public final class ProgressionUtilKt
{
  private static final int differenceModulo(int paramInt1, int paramInt2, int paramInt3)
  {
    return mod(mod(paramInt1, paramInt3) - mod(paramInt2, paramInt3), paramInt3);
  }
  
  private static final long differenceModulo(long paramLong1, long paramLong2, long paramLong3)
  {
    return mod(mod(paramLong1, paramLong3) - mod(paramLong2, paramLong3), paramLong3);
  }
  
  public static final int getProgressionLastElement(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt3 > 0)
    {
      if (paramInt1 < paramInt2)
      {
        paramInt1 = paramInt2 - differenceModulo(paramInt2, paramInt1, paramInt3);
        break label48;
      }
    }
    else
    {
      if (paramInt3 >= 0) {
        break label50;
      }
      if (paramInt1 > paramInt2) {
        break label38;
      }
    }
    paramInt1 = paramInt2;
    break label48;
    label38:
    paramInt1 = differenceModulo(paramInt1, paramInt2, -paramInt3) + paramInt2;
    label48:
    return paramInt1;
    label50:
    throw new IllegalArgumentException("Step is zero.");
  }
  
  public static final long getProgressionLastElement(long paramLong1, long paramLong2, long paramLong3)
  {
    if (paramLong3 > 0L)
    {
      if (paramLong1 < paramLong2)
      {
        paramLong1 = paramLong2 - differenceModulo(paramLong2, paramLong1, paramLong3);
        break label58;
      }
    }
    else
    {
      if (paramLong3 >= 0L) {
        break label60;
      }
      if (paramLong1 > paramLong2) {
        break label47;
      }
    }
    paramLong1 = paramLong2;
    break label58;
    label47:
    paramLong1 = differenceModulo(paramLong1, paramLong2, -paramLong3) + paramLong2;
    label58:
    return paramLong1;
    label60:
    throw new IllegalArgumentException("Step is zero.");
  }
  
  private static final int mod(int paramInt1, int paramInt2)
  {
    paramInt1 %= paramInt2;
    if (paramInt1 < 0) {
      paramInt1 += paramInt2;
    }
    return paramInt1;
  }
  
  private static final long mod(long paramLong1, long paramLong2)
  {
    paramLong1 %= paramLong2;
    if (paramLong1 < 0L) {
      paramLong1 += paramLong2;
    }
    return paramLong1;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/internal/ProgressionUtilKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */