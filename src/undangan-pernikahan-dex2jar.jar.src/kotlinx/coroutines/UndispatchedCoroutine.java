package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.internal.ScopeCoroutine;
import kotlinx.coroutines.internal.ThreadContextKt;

@Metadata(d1={"\0008\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\020\000\n\000\n\002\020\002\n\002\b\002\n\002\020\013\n\002\b\003\b\000\030\000*\006\b\000\020\001 \0002\b\022\004\022\002H\0010\002B\033\022\006\020\003\032\0020\004\022\f\020\005\032\b\022\004\022\0028\0000\006¢\006\002\020\007J\022\020\f\032\0020\r2\b\020\016\032\004\030\0010\013H\024J\006\020\017\032\0020\020J\030\020\021\032\0020\r2\006\020\003\032\0020\0042\b\020\022\032\004\030\0010\013R\"\020\b\032\026\022\022\022\020\022\004\022\0020\004\022\006\022\004\030\0010\0130\n0\tX\016¢\006\002\n\000¨\006\023"}, d2={"Lkotlinx/coroutines/UndispatchedCoroutine;", "T", "Lkotlinx/coroutines/internal/ScopeCoroutine;", "context", "Lkotlin/coroutines/CoroutineContext;", "uCont", "Lkotlin/coroutines/Continuation;", "(Lkotlin/coroutines/CoroutineContext;Lkotlin/coroutines/Continuation;)V", "threadStateToRecover", "Ljava/lang/ThreadLocal;", "Lkotlin/Pair;", "", "afterResume", "", "state", "clearThreadContext", "", "saveThreadContext", "oldValue", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class UndispatchedCoroutine<T>
  extends ScopeCoroutine<T>
{
  private ThreadLocal<Pair<CoroutineContext, Object>> threadStateToRecover = new ThreadLocal();
  
  public UndispatchedCoroutine(CoroutineContext paramCoroutineContext, Continuation<? super T> paramContinuation)
  {
    super(paramCoroutineContext, paramContinuation);
  }
  
  protected void afterResume(Object paramObject)
  {
    Object localObject2 = (Pair)this.threadStateToRecover.get();
    Unit localUnit = null;
    if (localObject2 != null)
    {
      ThreadContextKt.restoreThreadContext((CoroutineContext)((Pair)localObject2).component1(), ((Pair)localObject2).component2());
      this.threadStateToRecover.set(null);
    }
    Object localObject4 = CompletionStateKt.recoverResult(paramObject, this.uCont);
    paramObject = this.uCont;
    localObject2 = ((Continuation)paramObject).getContext();
    Object localObject3 = ThreadContextKt.updateThreadContext((CoroutineContext)localObject2, null);
    if (localObject3 != ThreadContextKt.NO_THREAD_ELEMENTS)
    {
      paramObject = CoroutineContextKt.updateUndispatchedCompletion((Continuation)paramObject, (CoroutineContext)localObject2, localObject3);
    }
    else
    {
      paramObject = (UndispatchedCoroutine)null;
      paramObject = localUnit;
    }
    try
    {
      this.uCont.resumeWith(localObject4);
      localUnit = Unit.INSTANCE;
      return;
    }
    finally
    {
      if ((paramObject == null) || (((UndispatchedCoroutine)paramObject).clearThreadContext())) {
        ThreadContextKt.restoreThreadContext((CoroutineContext)localObject2, localObject3);
      }
    }
  }
  
  public final boolean clearThreadContext()
  {
    if (this.threadStateToRecover.get() == null) {
      return false;
    }
    this.threadStateToRecover.set(null);
    return true;
  }
  
  public final void saveThreadContext(CoroutineContext paramCoroutineContext, Object paramObject)
  {
    this.threadStateToRecover.set(TuplesKt.to(paramCoroutineContext, paramObject));
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/UndispatchedCoroutine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */