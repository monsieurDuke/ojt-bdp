package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.jvm.internal.InlineMarker;
import kotlinx.coroutines.internal.ThreadLocalElement;
import kotlinx.coroutines.internal.ThreadLocalKey;

@Metadata(d1={"\000\036\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\003\n\002\020\002\n\002\b\002\n\002\020\013\n\000\032+\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\b\b\002\020\004\032\002H\002¢\006\002\020\005\032\031\020\006\032\0020\007*\006\022\002\b\0030\003HHø\001\000¢\006\002\020\b\032\031\020\t\032\0020\n*\006\022\002\b\0030\003HHø\001\000¢\006\002\020\b\002\004\n\002\b\031¨\006\013"}, d2={"asContextElement", "Lkotlinx/coroutines/ThreadContextElement;", "T", "Ljava/lang/ThreadLocal;", "value", "(Ljava/lang/ThreadLocal;Ljava/lang/Object;)Lkotlinx/coroutines/ThreadContextElement;", "ensurePresent", "", "(Ljava/lang/ThreadLocal;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "isPresent", "", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class ThreadContextElementKt
{
  public static final <T> ThreadContextElement<T> asContextElement(ThreadLocal<T> paramThreadLocal, T paramT)
  {
    return (ThreadContextElement)new ThreadLocalElement(paramT, paramThreadLocal);
  }
  
  public static final Object ensurePresent(ThreadLocal<?> paramThreadLocal, Continuation<? super Unit> paramContinuation)
  {
    int i;
    if (paramContinuation.getContext().get((CoroutineContext.Key)new ThreadLocalKey(paramThreadLocal)) != null) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return Unit.INSTANCE;
    }
    throw new IllegalStateException(("ThreadLocal " + paramThreadLocal + " is missing from context " + paramContinuation.getContext()).toString());
  }
  
  private static final Object ensurePresent$$forInline(ThreadLocal<?> paramThreadLocal, Continuation<? super Unit> paramContinuation)
  {
    InlineMarker.mark(3);
    throw new NullPointerException();
  }
  
  public static final Object isPresent(ThreadLocal<?> paramThreadLocal, Continuation<? super Boolean> paramContinuation)
  {
    boolean bool;
    if (paramContinuation.getContext().get((CoroutineContext.Key)new ThreadLocalKey(paramThreadLocal)) != null) {
      bool = true;
    } else {
      bool = false;
    }
    return Boxing.boxBoolean(bool);
  }
  
  private static final Object isPresent$$forInline(ThreadLocal<?> paramThreadLocal, Continuation<? super Boolean> paramContinuation)
  {
    InlineMarker.mark(3);
    throw new NullPointerException();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/ThreadContextElementKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */