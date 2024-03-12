package kotlinx.coroutines.internal;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;

@Metadata(d1={"\000'\n\002\030\002\n\002\030\002\n\002\b\002\n\002\b\003\n\002\030\002\n\002\020\003\n\002\030\002\n\000\n\002\030\002\n\000*\001\004\bÃ\002\030\0002\0020\001B\007\b\002¢\006\002\020\002J*\020\006\032\024\022\004\022\0020\b\022\006\022\004\030\0010\b0\007j\002`\t2\016\020\n\032\n\022\006\b\001\022\0020\b0\013H\026R\020\020\003\032\0020\004X\004¢\006\004\n\002\020\005¨\006\f"}, d2={"Lkotlinx/coroutines/internal/ClassValueCtorCache;", "Lkotlinx/coroutines/internal/CtorCache;", "()V", "cache", "kotlinx/coroutines/internal/ClassValueCtorCache$cache$1", "Lkotlinx/coroutines/internal/ClassValueCtorCache$cache$1;", "get", "Lkotlin/Function1;", "", "Lkotlinx/coroutines/internal/Ctor;", "key", "Ljava/lang/Class;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
final class ClassValueCtorCache
  extends CtorCache
{
  public static final ClassValueCtorCache INSTANCE = new ClassValueCtorCache();
  private static final cache.1 cache = new ClassValue()
  {
    protected Function1<Throwable, Throwable> computeValue(Class<?> paramAnonymousClass)
    {
      if (paramAnonymousClass != null) {
        return ExceptionsConstructorKt.access$createConstructor(paramAnonymousClass);
      }
      throw new NullPointerException("null cannot be cast to non-null type java.lang.Class<out kotlin.Throwable>");
    }
  };
  
  public Function1<Throwable, Throwable> get(Class<? extends Throwable> paramClass)
  {
    return (Function1)cache.get(paramClass);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/internal/ClassValueCtorCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */