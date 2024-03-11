package kotlin.random;

import kotlin.Metadata;
import kotlin.internal.PlatformImplementations;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000\036\n\000\n\002\030\002\n\000\n\002\020\006\n\000\n\002\020\b\n\002\b\002\n\002\030\002\n\002\b\002\032\t\020\000\032\0020\001H\b\032\030\020\002\032\0020\0032\006\020\004\032\0020\0052\006\020\006\032\0020\005H\000\032\f\020\007\032\0020\b*\0020\001H\007\032\f\020\t\032\0020\001*\0020\bH\007¨\006\n"}, d2={"defaultPlatformRandom", "Lkotlin/random/Random;", "doubleFromParts", "", "hi26", "", "low27", "asJavaRandom", "Ljava/util/Random;", "asKotlinRandom", "kotlin-stdlib"}, k=2, mv={1, 7, 1}, xi=48)
public final class PlatformRandomKt
{
  public static final java.util.Random asJavaRandom(Random paramRandom)
  {
    Intrinsics.checkNotNullParameter(paramRandom, "<this>");
    Object localObject;
    if ((paramRandom instanceof AbstractPlatformRandom)) {
      localObject = (AbstractPlatformRandom)paramRandom;
    } else {
      localObject = null;
    }
    if (localObject != null)
    {
      java.util.Random localRandom = ((AbstractPlatformRandom)localObject).getImpl();
      localObject = localRandom;
      if (localRandom != null) {}
    }
    else
    {
      localObject = (java.util.Random)new KotlinRandom(paramRandom);
    }
    return (java.util.Random)localObject;
  }
  
  public static final Random asKotlinRandom(java.util.Random paramRandom)
  {
    Intrinsics.checkNotNullParameter(paramRandom, "<this>");
    Object localObject;
    if ((paramRandom instanceof KotlinRandom)) {
      localObject = (KotlinRandom)paramRandom;
    } else {
      localObject = null;
    }
    if (localObject != null)
    {
      Random localRandom = ((KotlinRandom)localObject).getImpl();
      localObject = localRandom;
      if (localRandom != null) {}
    }
    else
    {
      localObject = (Random)new PlatformRandom(paramRandom);
    }
    return (Random)localObject;
  }
  
  private static final Random defaultPlatformRandom()
  {
    return PlatformImplementationsKt.IMPLEMENTATIONS.defaultPlatformRandom();
  }
  
  public static final double doubleFromParts(int paramInt1, int paramInt2)
  {
    return ((paramInt1 << 27) + paramInt2) / 9.007199254740992E15D;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/random/PlatformRandomKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */