package kotlinx.coroutines;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Element;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.jvm.functions.Function2;

@Metadata(d1={"\000\032\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\013\n\002\b\004\n\002\020\003\n\000\bf\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\002J\025\020\003\032\0020\0042\006\020\005\032\0028\000H&¢\006\002\020\006J\020\020\007\032\0020\0042\006\020\b\032\0020\tH&¨\006\n"}, d2={"Lkotlinx/coroutines/CompletableDeferred;", "T", "Lkotlinx/coroutines/Deferred;", "complete", "", "value", "(Ljava/lang/Object;)Z", "completeExceptionally", "exception", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public abstract interface CompletableDeferred<T>
  extends Deferred<T>
{
  public abstract boolean complete(T paramT);
  
  public abstract boolean completeExceptionally(Throwable paramThrowable);
  
  @Metadata(k=3, mv={1, 6, 0}, xi=48)
  public static final class DefaultImpls
  {
    public static <T, R> R fold(CompletableDeferred<T> paramCompletableDeferred, R paramR, Function2<? super R, ? super CoroutineContext.Element, ? extends R> paramFunction2)
    {
      return (R)Deferred.DefaultImpls.fold((Deferred)paramCompletableDeferred, paramR, paramFunction2);
    }
    
    public static <T, E extends CoroutineContext.Element> E get(CompletableDeferred<T> paramCompletableDeferred, CoroutineContext.Key<E> paramKey)
    {
      return Deferred.DefaultImpls.get((Deferred)paramCompletableDeferred, paramKey);
    }
    
    public static <T> CoroutineContext minusKey(CompletableDeferred<T> paramCompletableDeferred, CoroutineContext.Key<?> paramKey)
    {
      return Deferred.DefaultImpls.minusKey((Deferred)paramCompletableDeferred, paramKey);
    }
    
    public static <T> CoroutineContext plus(CompletableDeferred<T> paramCompletableDeferred, CoroutineContext paramCoroutineContext)
    {
      return Deferred.DefaultImpls.plus((Deferred)paramCompletableDeferred, paramCoroutineContext);
    }
    
    @Deprecated(level=DeprecationLevel.ERROR, message="Operator '+' on two Job objects is meaningless. Job is a coroutine context element and `+` is a set-sum operator for coroutine contexts. The job to the right of `+` just replaces the job the left of `+`.")
    public static <T> Job plus(CompletableDeferred<T> paramCompletableDeferred, Job paramJob)
    {
      return Deferred.DefaultImpls.plus((Deferred)paramCompletableDeferred, paramJob);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/CompletableDeferred.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */