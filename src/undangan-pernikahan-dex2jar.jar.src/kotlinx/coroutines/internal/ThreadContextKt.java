package kotlinx.coroutines.internal;

import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Element;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlinx.coroutines.ThreadContextElement;

@Metadata(d1={"\0000\n\000\n\002\030\002\n\000\n\002\030\002\n\002\020\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\005\032\032\020\n\032\0020\0132\006\020\f\032\0020\r2\b\020\016\032\004\030\0010\004H\000\032\020\020\017\032\0020\0042\006\020\f\032\0020\rH\000\032\034\020\020\032\004\030\0010\0042\006\020\f\032\0020\r2\b\020\021\032\004\030\0010\004H\000\"\020\020\000\032\0020\0018\000X\004¢\006\002\n\000\"$\020\002\032\030\022\006\022\004\030\0010\004\022\004\022\0020\005\022\006\022\004\030\0010\0040\003X\004¢\006\002\n\000\",\020\006\032 \022\n\022\b\022\002\b\003\030\0010\007\022\004\022\0020\005\022\n\022\b\022\002\b\003\030\0010\0070\003X\004¢\006\002\n\000\" \020\b\032\024\022\004\022\0020\t\022\004\022\0020\005\022\004\022\0020\t0\003X\004¢\006\002\n\000¨\006\022"}, d2={"NO_THREAD_ELEMENTS", "Lkotlinx/coroutines/internal/Symbol;", "countAll", "Lkotlin/Function2;", "", "Lkotlin/coroutines/CoroutineContext$Element;", "findOne", "Lkotlinx/coroutines/ThreadContextElement;", "updateState", "Lkotlinx/coroutines/internal/ThreadState;", "restoreThreadContext", "", "context", "Lkotlin/coroutines/CoroutineContext;", "oldState", "threadContextElements", "updateThreadContext", "countOrElement", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class ThreadContextKt
{
  public static final Symbol NO_THREAD_ELEMENTS = new Symbol("NO_THREAD_ELEMENTS");
  private static final Function2<Object, CoroutineContext.Element, Object> countAll = (Function2)countAll.1.INSTANCE;
  private static final Function2<ThreadContextElement<?>, CoroutineContext.Element, ThreadContextElement<?>> findOne = (Function2)findOne.1.INSTANCE;
  private static final Function2<ThreadState, CoroutineContext.Element, ThreadState> updateState = (Function2)updateState.1.INSTANCE;
  
  public static final void restoreThreadContext(CoroutineContext paramCoroutineContext, Object paramObject)
  {
    if (paramObject == NO_THREAD_ELEMENTS) {
      return;
    }
    if ((paramObject instanceof ThreadState))
    {
      ((ThreadState)paramObject).restore(paramCoroutineContext);
    }
    else
    {
      Object localObject = paramCoroutineContext.fold(null, findOne);
      if (localObject == null) {
        break label55;
      }
      localObject = (ThreadContextElement)localObject;
      ((ThreadContextElement)localObject).restoreThreadContext(paramCoroutineContext, paramObject);
    }
    return;
    label55:
    throw new NullPointerException("null cannot be cast to non-null type kotlinx.coroutines.ThreadContextElement<kotlin.Any?>");
  }
  
  public static final Object threadContextElements(CoroutineContext paramCoroutineContext)
  {
    paramCoroutineContext = paramCoroutineContext.fold(Integer.valueOf(0), countAll);
    Intrinsics.checkNotNull(paramCoroutineContext);
    return paramCoroutineContext;
  }
  
  public static final Object updateThreadContext(CoroutineContext paramCoroutineContext, Object paramObject)
  {
    if (paramObject == null) {
      paramObject = threadContextElements(paramCoroutineContext);
    }
    if (paramObject == Integer.valueOf(0))
    {
      paramCoroutineContext = NO_THREAD_ELEMENTS;
    }
    else if ((paramObject instanceof Integer))
    {
      paramCoroutineContext = paramCoroutineContext.fold(new ThreadState(paramCoroutineContext, ((Number)paramObject).intValue()), updateState);
    }
    else
    {
      paramObject = (ThreadContextElement)paramObject;
      paramCoroutineContext = ((ThreadContextElement)paramObject).updateThreadContext(paramCoroutineContext);
    }
    return paramCoroutineContext;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/internal/ThreadContextKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */