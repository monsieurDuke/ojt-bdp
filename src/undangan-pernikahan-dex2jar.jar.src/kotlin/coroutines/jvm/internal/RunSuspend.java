package kotlin.coroutines.jvm.internal;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000 \n\002\030\002\n\002\030\002\n\002\020\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\b\b\002\030\0002\b\022\004\022\0020\0020\001B\005¢\006\002\020\003J\006\020\016\032\0020\002J\036\020\017\032\0020\0022\f\020\b\032\b\022\004\022\0020\0020\tH\026ø\001\000¢\006\002\020\020R\024\020\004\032\0020\0058VX\004¢\006\006\032\004\b\006\020\007R(\020\b\032\n\022\004\022\0020\002\030\0010\tX\016ø\001\000ø\001\001¢\006\016\n\000\032\004\b\n\020\013\"\004\b\f\020\r\002\b\n\002\b\031\n\002\b!¨\006\021"}, d2={"Lkotlin/coroutines/jvm/internal/RunSuspend;", "Lkotlin/coroutines/Continuation;", "", "()V", "context", "Lkotlin/coroutines/CoroutineContext;", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "result", "Lkotlin/Result;", "getResult-xLWZpok", "()Lkotlin/Result;", "setResult", "(Lkotlin/Result;)V", "await", "resumeWith", "(Ljava/lang/Object;)V", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
final class RunSuspend
  implements Continuation<Unit>
{
  private Result<Unit> result;
  
  public final void await()
  {
    try
    {
      Result localResult;
      for (;;)
      {
        localResult = this.result;
        if (localResult != null) {
          break;
        }
        Intrinsics.checkNotNull(this, "null cannot be cast to non-null type java.lang.Object");
        ((Object)this).wait();
      }
      ResultKt.throwOnFailure(localResult.unbox-impl());
      return;
    }
    finally {}
  }
  
  public CoroutineContext getContext()
  {
    return (CoroutineContext)EmptyCoroutineContext.INSTANCE;
  }
  
  public final Result<Unit> getResult-xLWZpok()
  {
    return this.result;
  }
  
  public void resumeWith(Object paramObject)
  {
    try
    {
      this.result = Result.box-impl(paramObject);
      Intrinsics.checkNotNull(this, "null cannot be cast to non-null type java.lang.Object");
      ((Object)this).notifyAll();
      paramObject = Unit.INSTANCE;
      return;
    }
    finally {}
  }
  
  public final void setResult(Result<Unit> paramResult)
  {
    this.result = paramResult;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/coroutines/jvm/internal/RunSuspend.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */