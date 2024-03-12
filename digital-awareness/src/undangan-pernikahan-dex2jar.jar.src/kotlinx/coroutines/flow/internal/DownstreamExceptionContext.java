package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Element;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.jvm.functions.Function2;

@Metadata(d1={"\000&\n\002\030\002\n\002\030\002\n\000\n\002\020\003\n\002\b\006\n\002\030\002\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\005\b\000\030\0002\0020\001B\025\022\006\020\002\032\0020\003\022\006\020\004\032\0020\001¢\006\002\020\005J6\020\006\032\002H\007\"\004\b\000\020\0072\006\020\b\032\002H\0072\030\020\t\032\024\022\004\022\002H\007\022\004\022\0020\013\022\004\022\002H\0070\nH\001¢\006\002\020\fJ(\020\r\032\004\030\001H\016\"\b\b\000\020\016*\0020\0132\f\020\017\032\b\022\004\022\002H\0160\020H\003¢\006\002\020\021J\025\020\022\032\0020\0012\n\020\017\032\006\022\002\b\0030\020H\001J\021\020\023\032\0020\0012\006\020\024\032\0020\001H\003R\020\020\002\032\0020\0038\006X\004¢\006\002\n\000¨\006\025"}, d2={"Lkotlinx/coroutines/flow/internal/DownstreamExceptionContext;", "Lkotlin/coroutines/CoroutineContext;", "e", "", "originalContext", "(Ljava/lang/Throwable;Lkotlin/coroutines/CoroutineContext;)V", "fold", "R", "initial", "operation", "Lkotlin/Function2;", "Lkotlin/coroutines/CoroutineContext$Element;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "get", "E", "key", "Lkotlin/coroutines/CoroutineContext$Key;", "(Lkotlin/coroutines/CoroutineContext$Key;)Lkotlin/coroutines/CoroutineContext$Element;", "minusKey", "plus", "context", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class DownstreamExceptionContext
  implements CoroutineContext
{
  private final CoroutineContext $$delegate_0;
  public final Throwable e;
  
  public DownstreamExceptionContext(Throwable paramThrowable, CoroutineContext paramCoroutineContext)
  {
    this.e = paramThrowable;
    this.$$delegate_0 = paramCoroutineContext;
  }
  
  public <R> R fold(R paramR, Function2<? super R, ? super CoroutineContext.Element, ? extends R> paramFunction2)
  {
    return (R)this.$$delegate_0.fold(paramR, paramFunction2);
  }
  
  public <E extends CoroutineContext.Element> E get(CoroutineContext.Key<E> paramKey)
  {
    return this.$$delegate_0.get(paramKey);
  }
  
  public CoroutineContext minusKey(CoroutineContext.Key<?> paramKey)
  {
    return this.$$delegate_0.minusKey(paramKey);
  }
  
  public CoroutineContext plus(CoroutineContext paramCoroutineContext)
  {
    return this.$$delegate_0.plus(paramCoroutineContext);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/flow/internal/DownstreamExceptionContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */