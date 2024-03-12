package kotlin.random;

import java.io.Serializable;
import java.util.Random;
import kotlin.Metadata;

@Metadata(d1={"\000\032\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\005\b\002\030\000 \t2\0020\0012\0060\002j\002`\003:\001\tB\r\022\006\020\004\032\0020\005¢\006\002\020\006R\024\020\004\032\0020\005X\004¢\006\b\n\000\032\004\b\007\020\b¨\006\n"}, d2={"Lkotlin/random/PlatformRandom;", "Lkotlin/random/AbstractPlatformRandom;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "impl", "Ljava/util/Random;", "(Ljava/util/Random;)V", "getImpl", "()Ljava/util/Random;", "Companion", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
final class PlatformRandom
  extends AbstractPlatformRandom
  implements Serializable
{
  private static final Companion Companion = new Companion(null);
  @Deprecated
  private static final long serialVersionUID = 0L;
  private final Random impl;
  
  public PlatformRandom(Random paramRandom)
  {
    this.impl = paramRandom;
  }
  
  public Random getImpl()
  {
    return this.impl;
  }
  
  @Metadata(d1={"\000\022\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\t\n\000\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002R\016\020\003\032\0020\004XT¢\006\002\n\000¨\006\005"}, d2={"Lkotlin/random/PlatformRandom$Companion;", "", "()V", "serialVersionUID", "", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
  private static final class Companion {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/random/PlatformRandom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */