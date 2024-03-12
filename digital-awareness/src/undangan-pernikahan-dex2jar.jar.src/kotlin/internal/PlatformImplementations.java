package kotlin.internal;

import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.MatchResult;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.FallbackThreadLocalRandom;
import kotlin.random.Random;
import kotlin.text.MatchGroup;

@Metadata(d1={"\000:\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\002\n\000\n\002\020\003\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\016\n\000\n\002\020 \n\002\b\002\b\020\030\0002\0020\001:\001\022B\005¢\006\002\020\002J\030\020\003\032\0020\0042\006\020\005\032\0020\0062\006\020\007\032\0020\006H\026J\b\020\b\032\0020\tH\026J\032\020\n\032\004\030\0010\0132\006\020\f\032\0020\r2\006\020\016\032\0020\017H\026J\026\020\020\032\b\022\004\022\0020\0060\0212\006\020\007\032\0020\006H\026¨\006\023"}, d2={"Lkotlin/internal/PlatformImplementations;", "", "()V", "addSuppressed", "", "cause", "", "exception", "defaultPlatformRandom", "Lkotlin/random/Random;", "getMatchResultNamedGroup", "Lkotlin/text/MatchGroup;", "matchResult", "Ljava/util/regex/MatchResult;", "name", "", "getSuppressed", "", "ReflectThrowable", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public class PlatformImplementations
{
  public void addSuppressed(Throwable paramThrowable1, Throwable paramThrowable2)
  {
    Intrinsics.checkNotNullParameter(paramThrowable1, "cause");
    Intrinsics.checkNotNullParameter(paramThrowable2, "exception");
    Method localMethod = ReflectThrowable.addSuppressed;
    if (localMethod != null) {
      localMethod.invoke(paramThrowable1, new Object[] { paramThrowable2 });
    }
  }
  
  public Random defaultPlatformRandom()
  {
    return (Random)new FallbackThreadLocalRandom();
  }
  
  public MatchGroup getMatchResultNamedGroup(MatchResult paramMatchResult, String paramString)
  {
    Intrinsics.checkNotNullParameter(paramMatchResult, "matchResult");
    Intrinsics.checkNotNullParameter(paramString, "name");
    throw new UnsupportedOperationException("Retrieving groups by name is not supported on this platform.");
  }
  
  public List<Throwable> getSuppressed(Throwable paramThrowable)
  {
    Intrinsics.checkNotNullParameter(paramThrowable, "exception");
    Object localObject = ReflectThrowable.getSuppressed;
    if (localObject != null)
    {
      paramThrowable = ((Method)localObject).invoke(paramThrowable, new Object[0]);
      if (paramThrowable != null)
      {
        localObject = ArraysKt.asList((Throwable[])paramThrowable);
        paramThrowable = (Throwable)localObject;
        if (localObject != null) {
          return paramThrowable;
        }
      }
    }
    paramThrowable = CollectionsKt.emptyList();
    return paramThrowable;
  }
  
  @Metadata(d1={"\000\024\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\002\bÂ\002\030\0002\0020\001B\007\b\002¢\006\002\020\002R\022\020\003\032\004\030\0010\0048\006X\004¢\006\002\n\000R\022\020\005\032\004\030\0010\0048\006X\004¢\006\002\n\000¨\006\006"}, d2={"Lkotlin/internal/PlatformImplementations$ReflectThrowable;", "", "()V", "addSuppressed", "Ljava/lang/reflect/Method;", "getSuppressed", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
  private static final class ReflectThrowable
  {
    public static final ReflectThrowable INSTANCE = new ReflectThrowable();
    public static final Method addSuppressed;
    public static final Method getSuppressed;
    
    static
    {
      Method[] arrayOfMethod = Throwable.class.getMethods();
      Intrinsics.checkNotNullExpressionValue(arrayOfMethod, "throwableMethods");
      int m = arrayOfMethod.length;
      int k = 0;
      Object localObject2;
      for (int i = 0;; i++)
      {
        localObject2 = null;
        if (i >= m) {
          break;
        }
        localObject1 = arrayOfMethod[i];
        if (Intrinsics.areEqual(((Method)localObject1).getName(), "addSuppressed"))
        {
          Class[] arrayOfClass = ((Method)localObject1).getParameterTypes();
          Intrinsics.checkNotNullExpressionValue(arrayOfClass, "it.parameterTypes");
          if (Intrinsics.areEqual(ArraysKt.singleOrNull((Object[])arrayOfClass), Throwable.class))
          {
            j = 1;
            break label96;
          }
        }
        j = 0;
        label96:
        if (j != 0) {
          break label112;
        }
      }
      Object localObject1 = null;
      label112:
      addSuppressed = (Method)localObject1;
      int j = arrayOfMethod.length;
      for (i = k;; i++)
      {
        localObject1 = localObject2;
        if (i >= j) {
          break;
        }
        localObject1 = arrayOfMethod[i];
        if (Intrinsics.areEqual(((Method)localObject1).getName(), "getSuppressed")) {
          break;
        }
      }
      getSuppressed = (Method)localObject1;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/internal/PlatformImplementations.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */