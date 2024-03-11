package kotlin.coroutines;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

@Metadata(d1={"\000 \n\002\030\002\n\002\020\000\n\002\b\004\n\002\030\002\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\007\bg\030\0002\0020\001:\002\021\022J5\020\002\032\002H\003\"\004\b\000\020\0032\006\020\004\032\002H\0032\030\020\005\032\024\022\004\022\002H\003\022\004\022\0020\007\022\004\022\002H\0030\006H&¢\006\002\020\bJ(\020\t\032\004\030\001H\n\"\b\b\000\020\n*\0020\0072\f\020\013\032\b\022\004\022\002H\n0\fH¦\002¢\006\002\020\rJ\024\020\016\032\0020\0002\n\020\013\032\006\022\002\b\0030\fH&J\021\020\017\032\0020\0002\006\020\020\032\0020\000H\002¨\006\023"}, d2={"Lkotlin/coroutines/CoroutineContext;", "", "fold", "R", "initial", "operation", "Lkotlin/Function2;", "Lkotlin/coroutines/CoroutineContext$Element;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "get", "E", "key", "Lkotlin/coroutines/CoroutineContext$Key;", "(Lkotlin/coroutines/CoroutineContext$Key;)Lkotlin/coroutines/CoroutineContext$Element;", "minusKey", "plus", "context", "Element", "Key", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public abstract interface CoroutineContext
{
  public abstract <R> R fold(R paramR, Function2<? super R, ? super Element, ? extends R> paramFunction2);
  
  public abstract <E extends Element> E get(Key<E> paramKey);
  
  public abstract CoroutineContext minusKey(Key<?> paramKey);
  
  public abstract CoroutineContext plus(CoroutineContext paramCoroutineContext);
  
  @Metadata(k=3, mv={1, 7, 1}, xi=48)
  public static final class DefaultImpls
  {
    public static CoroutineContext plus(CoroutineContext paramCoroutineContext1, CoroutineContext paramCoroutineContext2)
    {
      Intrinsics.checkNotNullParameter(paramCoroutineContext2, "context");
      if (paramCoroutineContext2 != EmptyCoroutineContext.INSTANCE) {
        paramCoroutineContext1 = (CoroutineContext)paramCoroutineContext2.fold(paramCoroutineContext1, (Function2)CoroutineContext.plus.1.INSTANCE);
      }
      return paramCoroutineContext1;
    }
  }
  
  @Metadata(d1={"\000\032\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\006\n\002\030\002\n\002\b\006\bf\030\0002\0020\001J5\020\006\032\002H\007\"\004\b\000\020\0072\006\020\b\032\002H\0072\030\020\t\032\024\022\004\022\002H\007\022\004\022\0020\000\022\004\022\002H\0070\nH\026¢\006\002\020\013J(\020\f\032\004\030\001H\r\"\b\b\000\020\r*\0020\0002\f\020\002\032\b\022\004\022\002H\r0\003H\002¢\006\002\020\016J\024\020\017\032\0020\0012\n\020\002\032\006\022\002\b\0030\003H\026R\026\020\002\032\006\022\002\b\0030\003X¦\004¢\006\006\032\004\b\004\020\005¨\006\020"}, d2={"Lkotlin/coroutines/CoroutineContext$Element;", "Lkotlin/coroutines/CoroutineContext;", "key", "Lkotlin/coroutines/CoroutineContext$Key;", "getKey", "()Lkotlin/coroutines/CoroutineContext$Key;", "fold", "R", "initial", "operation", "Lkotlin/Function2;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "get", "E", "(Lkotlin/coroutines/CoroutineContext$Key;)Lkotlin/coroutines/CoroutineContext$Element;", "minusKey", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
  public static abstract interface Element
    extends CoroutineContext
  {
    public abstract <R> R fold(R paramR, Function2<? super R, ? super Element, ? extends R> paramFunction2);
    
    public abstract <E extends Element> E get(CoroutineContext.Key<E> paramKey);
    
    public abstract CoroutineContext.Key<?> getKey();
    
    public abstract CoroutineContext minusKey(CoroutineContext.Key<?> paramKey);
    
    @Metadata(k=3, mv={1, 7, 1}, xi=48)
    public static final class DefaultImpls
    {
      public static <R> R fold(CoroutineContext.Element paramElement, R paramR, Function2<? super R, ? super CoroutineContext.Element, ? extends R> paramFunction2)
      {
        Intrinsics.checkNotNullParameter(paramFunction2, "operation");
        return (R)paramFunction2.invoke(paramR, paramElement);
      }
      
      public static <E extends CoroutineContext.Element> E get(CoroutineContext.Element paramElement, CoroutineContext.Key<E> paramKey)
      {
        Intrinsics.checkNotNullParameter(paramKey, "key");
        if (Intrinsics.areEqual(paramElement.getKey(), paramKey)) {
          Intrinsics.checkNotNull(paramElement, "null cannot be cast to non-null type E of kotlin.coroutines.CoroutineContext.Element.get");
        } else {
          paramElement = null;
        }
        return paramElement;
      }
      
      public static CoroutineContext minusKey(CoroutineContext.Element paramElement, CoroutineContext.Key<?> paramKey)
      {
        Intrinsics.checkNotNullParameter(paramKey, "key");
        if (Intrinsics.areEqual(paramElement.getKey(), paramKey)) {
          paramElement = (CoroutineContext)EmptyCoroutineContext.INSTANCE;
        } else {
          paramElement = (CoroutineContext)paramElement;
        }
        return paramElement;
      }
      
      public static CoroutineContext plus(CoroutineContext.Element paramElement, CoroutineContext paramCoroutineContext)
      {
        Intrinsics.checkNotNullParameter(paramCoroutineContext, "context");
        return CoroutineContext.DefaultImpls.plus((CoroutineContext)paramElement, paramCoroutineContext);
      }
    }
  }
  
  @Metadata(d1={"\000\020\n\002\030\002\n\000\n\002\030\002\n\002\020\000\n\000\bf\030\000*\b\b\000\020\001*\0020\0022\0020\003¨\006\004"}, d2={"Lkotlin/coroutines/CoroutineContext$Key;", "E", "Lkotlin/coroutines/CoroutineContext$Element;", "", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
  public static abstract interface Key<E extends CoroutineContext.Element> {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/coroutines/CoroutineContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */