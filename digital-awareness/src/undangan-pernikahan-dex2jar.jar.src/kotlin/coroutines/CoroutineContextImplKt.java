package kotlin.coroutines;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000\030\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\032+\020\000\032\004\030\001H\001\"\b\b\000\020\001*\0020\002*\0020\0022\f\020\003\032\b\022\004\022\002H\0010\004H\007¢\006\002\020\005\032\030\020\006\032\0020\007*\0020\0022\n\020\003\032\006\022\002\b\0030\004H\007¨\006\b"}, d2={"getPolymorphicElement", "E", "Lkotlin/coroutines/CoroutineContext$Element;", "key", "Lkotlin/coroutines/CoroutineContext$Key;", "(Lkotlin/coroutines/CoroutineContext$Element;Lkotlin/coroutines/CoroutineContext$Key;)Lkotlin/coroutines/CoroutineContext$Element;", "minusPolymorphicKey", "Lkotlin/coroutines/CoroutineContext;", "kotlin-stdlib"}, k=2, mv={1, 7, 1}, xi=48)
public final class CoroutineContextImplKt
{
  public static final <E extends CoroutineContext.Element> E getPolymorphicElement(CoroutineContext.Element paramElement, CoroutineContext.Key<E> paramKey)
  {
    Intrinsics.checkNotNullParameter(paramElement, "<this>");
    Intrinsics.checkNotNullParameter(paramKey, "key");
    boolean bool = paramKey instanceof AbstractCoroutineContextKey;
    Object localObject1 = null;
    Object localObject2 = null;
    if (bool)
    {
      localObject1 = localObject2;
      if (((AbstractCoroutineContextKey)paramKey).isSubKey$kotlin_stdlib(paramElement.getKey()))
      {
        paramElement = ((AbstractCoroutineContextKey)paramKey).tryCast$kotlin_stdlib(paramElement);
        localObject1 = localObject2;
        if ((paramElement instanceof CoroutineContext.Element)) {
          localObject1 = paramElement;
        }
      }
      return (E)localObject1;
    }
    if (paramElement.getKey() == paramKey) {
      localObject1 = paramElement;
    }
    return (E)localObject1;
  }
  
  public static final CoroutineContext minusPolymorphicKey(CoroutineContext.Element paramElement, CoroutineContext.Key<?> paramKey)
  {
    Intrinsics.checkNotNullParameter(paramElement, "<this>");
    Intrinsics.checkNotNullParameter(paramKey, "key");
    if ((paramKey instanceof AbstractCoroutineContextKey))
    {
      if ((((AbstractCoroutineContextKey)paramKey).isSubKey$kotlin_stdlib(paramElement.getKey())) && (((AbstractCoroutineContextKey)paramKey).tryCast$kotlin_stdlib(paramElement) != null)) {
        paramElement = (CoroutineContext)EmptyCoroutineContext.INSTANCE;
      } else {
        paramElement = (CoroutineContext)paramElement;
      }
      return paramElement;
    }
    if (paramElement.getKey() == paramKey) {
      paramElement = (CoroutineContext)EmptyCoroutineContext.INSTANCE;
    } else {
      paramElement = (CoroutineContext)paramElement;
    }
    return paramElement;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/coroutines/CoroutineContextImplKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */