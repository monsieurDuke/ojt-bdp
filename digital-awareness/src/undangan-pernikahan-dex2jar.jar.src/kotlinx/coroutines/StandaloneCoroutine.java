package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;

@Metadata(d1={"\000\"\n\002\030\002\n\002\030\002\n\002\020\002\n\000\n\002\030\002\n\000\n\002\020\013\n\002\b\003\n\002\020\003\n\000\b\022\030\0002\b\022\004\022\0020\0020\001B\025\022\006\020\003\032\0020\004\022\006\020\005\032\0020\006¢\006\002\020\007J\020\020\b\032\0020\0062\006\020\t\032\0020\nH\024¨\006\013"}, d2={"Lkotlinx/coroutines/StandaloneCoroutine;", "Lkotlinx/coroutines/AbstractCoroutine;", "", "parentContext", "Lkotlin/coroutines/CoroutineContext;", "active", "", "(Lkotlin/coroutines/CoroutineContext;Z)V", "handleJobException", "exception", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
class StandaloneCoroutine
  extends AbstractCoroutine<Unit>
{
  public StandaloneCoroutine(CoroutineContext paramCoroutineContext, boolean paramBoolean)
  {
    super(paramCoroutineContext, true, paramBoolean);
  }
  
  protected boolean handleJobException(Throwable paramThrowable)
  {
    CoroutineExceptionHandlerKt.handleCoroutineException(getContext(), paramThrowable);
    return true;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/StandaloneCoroutine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */