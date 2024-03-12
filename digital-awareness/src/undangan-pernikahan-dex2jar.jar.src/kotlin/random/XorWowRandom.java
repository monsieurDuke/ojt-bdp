package kotlin.random;

import java.io.Serializable;
import kotlin.Metadata;

@Metadata(d1={"\000\032\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\020\b\n\002\b\016\b\000\030\000 \0222\0020\0012\0060\002j\002`\003:\001\022B\027\b\020\022\006\020\004\032\0020\005\022\006\020\006\032\0020\005¢\006\002\020\007B7\b\000\022\006\020\b\032\0020\005\022\006\020\t\032\0020\005\022\006\020\n\032\0020\005\022\006\020\013\032\0020\005\022\006\020\f\032\0020\005\022\006\020\r\032\0020\005¢\006\002\020\016J\020\020\017\032\0020\0052\006\020\020\032\0020\005H\026J\b\020\021\032\0020\005H\026R\016\020\r\032\0020\005X\016¢\006\002\n\000R\016\020\f\032\0020\005X\016¢\006\002\n\000R\016\020\013\032\0020\005X\016¢\006\002\n\000R\016\020\b\032\0020\005X\016¢\006\002\n\000R\016\020\t\032\0020\005X\016¢\006\002\n\000R\016\020\n\032\0020\005X\016¢\006\002\n\000¨\006\023"}, d2={"Lkotlin/random/XorWowRandom;", "Lkotlin/random/Random;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "seed1", "", "seed2", "(II)V", "x", "y", "z", "w", "v", "addend", "(IIIIII)V", "nextBits", "bitCount", "nextInt", "Companion", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public final class XorWowRandom
  extends Random
  implements Serializable
{
  private static final Companion Companion = new Companion(null);
  @Deprecated
  private static final long serialVersionUID = 0L;
  private int addend;
  private int v;
  private int w;
  private int x;
  private int y;
  private int z;
  
  public XorWowRandom(int paramInt1, int paramInt2)
  {
    this(paramInt1, paramInt2, 0, 0, paramInt1 ^ 0xFFFFFFFF, paramInt1 << 10 ^ paramInt2 >>> 4);
  }
  
  public XorWowRandom(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    this.x = paramInt1;
    this.y = paramInt2;
    this.z = paramInt3;
    this.w = paramInt4;
    this.v = paramInt5;
    this.addend = paramInt6;
    paramInt6 = 0;
    if ((paramInt1 | paramInt2 | paramInt3 | paramInt4 | paramInt5) != 0) {
      paramInt1 = 1;
    } else {
      paramInt1 = 0;
    }
    if (paramInt1 != 0)
    {
      for (paramInt1 = paramInt6; paramInt1 < 64; paramInt1++) {
        nextInt();
      }
      return;
    }
    throw new IllegalArgumentException("Initial state must have at least one non-zero element.".toString());
  }
  
  public int nextBits(int paramInt)
  {
    return RandomKt.takeUpperBits(nextInt(), paramInt);
  }
  
  public int nextInt()
  {
    int i = this.x;
    int j = i ^ i >>> 2;
    this.x = this.y;
    this.y = this.z;
    this.z = this.w;
    i = this.v;
    this.w = i;
    j = j << 1 ^ j ^ i ^ i << 4;
    this.v = j;
    i = this.addend + 362437;
    this.addend = i;
    return i + j;
  }
  
  @Metadata(d1={"\000\022\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\t\n\000\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002R\016\020\003\032\0020\004XT¢\006\002\n\000¨\006\005"}, d2={"Lkotlin/random/XorWowRandom$Companion;", "", "()V", "serialVersionUID", "", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
  private static final class Companion {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/random/XorWowRandom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */