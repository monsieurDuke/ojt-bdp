package kotlin.random;

import java.util.Random;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000\033\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\b\003*\001\b\b\000\030\0002\0020\001B\005¢\006\002\020\002R\024\020\003\032\0020\0048VX\004¢\006\006\032\004\b\005\020\006R\020\020\007\032\0020\bX\004¢\006\004\n\002\020\t¨\006\n"}, d2={"Lkotlin/random/FallbackThreadLocalRandom;", "Lkotlin/random/AbstractPlatformRandom;", "()V", "impl", "Ljava/util/Random;", "getImpl", "()Ljava/util/Random;", "implStorage", "kotlin/random/FallbackThreadLocalRandom$implStorage$1", "Lkotlin/random/FallbackThreadLocalRandom$implStorage$1;", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public final class FallbackThreadLocalRandom
  extends AbstractPlatformRandom
{
  private final implStorage.1 implStorage = new ThreadLocal()
  {
    protected Random initialValue()
    {
      return new Random();
    }
  };
  
  public Random getImpl()
  {
    Object localObject = this.implStorage.get();
    Intrinsics.checkNotNullExpressionValue(localObject, "implStorage.get()");
    return (Random)localObject;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/random/FallbackThreadLocalRandom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */