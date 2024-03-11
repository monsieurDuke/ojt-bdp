package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000F\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\020\003\n\002\030\002\n\002\b\002\n\002\020\002\n\002\b\004\n\002\020\013\n\002\b\013\n\002\020\b\n\002\b\002\n\002\030\002\n\000\n\002\020\016\n\000\b\b\030\0002\0020\001BZ\022\b\020\002\032\004\030\0010\001\022\n\b\002\020\003\032\004\030\0010\004\022%\b\002\020\005\032\037\022\023\022\0210\007¢\006\f\b\b\022\b\b\t\022\004\b\b(\n\022\004\022\0020\013\030\0010\006\022\n\b\002\020\f\032\004\030\0010\001\022\n\b\002\020\r\032\004\030\0010\007¢\006\002\020\016J\013\020\023\032\004\030\0010\001HÆ\003J\013\020\024\032\004\030\0010\004HÆ\003J&\020\025\032\037\022\023\022\0210\007¢\006\f\b\b\022\b\b\t\022\004\b\b(\n\022\004\022\0020\013\030\0010\006HÆ\003J\013\020\026\032\004\030\0010\001HÆ\003J\013\020\027\032\004\030\0010\007HÆ\003J`\020\030\032\0020\0002\n\b\002\020\002\032\004\030\0010\0012\n\b\002\020\003\032\004\030\0010\0042%\b\002\020\005\032\037\022\023\022\0210\007¢\006\f\b\b\022\b\b\t\022\004\b\b(\n\022\004\022\0020\013\030\0010\0062\n\b\002\020\f\032\004\030\0010\0012\n\b\002\020\r\032\004\030\0010\007HÆ\001J\023\020\031\032\0020\0202\b\020\032\032\004\030\0010\001HÖ\003J\t\020\033\032\0020\034HÖ\001J\032\020\035\032\0020\0132\n\020\036\032\006\022\002\b\0030\0372\006\020\n\032\0020\007J\t\020 \032\0020!HÖ\001R\022\020\r\032\004\030\0010\0078\006X\004¢\006\002\n\000R\022\020\003\032\004\030\0010\0048\006X\004¢\006\002\n\000R\021\020\017\032\0020\0208F¢\006\006\032\004\b\021\020\022R\022\020\f\032\004\030\0010\0018\006X\004¢\006\002\n\000R-\020\005\032\037\022\023\022\0210\007¢\006\f\b\b\022\b\b\t\022\004\b\b(\n\022\004\022\0020\013\030\0010\0068\006X\004¢\006\002\n\000R\022\020\002\032\004\030\0010\0018\006X\004¢\006\002\n\000¨\006\""}, d2={"Lkotlinx/coroutines/CompletedContinuation;", "", "result", "cancelHandler", "Lkotlinx/coroutines/CancelHandler;", "onCancellation", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "cause", "", "idempotentResume", "cancelCause", "(Ljava/lang/Object;Lkotlinx/coroutines/CancelHandler;Lkotlin/jvm/functions/Function1;Ljava/lang/Object;Ljava/lang/Throwable;)V", "cancelled", "", "getCancelled", "()Z", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "other", "hashCode", "", "invokeHandlers", "cont", "Lkotlinx/coroutines/CancellableContinuationImpl;", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
final class CompletedContinuation
{
  public final Throwable cancelCause;
  public final CancelHandler cancelHandler;
  public final Object idempotentResume;
  public final Function1<Throwable, Unit> onCancellation;
  public final Object result;
  
  public CompletedContinuation(Object paramObject1, CancelHandler paramCancelHandler, Function1<? super Throwable, Unit> paramFunction1, Object paramObject2, Throwable paramThrowable)
  {
    this.result = paramObject1;
    this.cancelHandler = paramCancelHandler;
    this.onCancellation = paramFunction1;
    this.idempotentResume = paramObject2;
    this.cancelCause = paramThrowable;
  }
  
  public final Object component1()
  {
    return this.result;
  }
  
  public final CancelHandler component2()
  {
    return this.cancelHandler;
  }
  
  public final Function1<Throwable, Unit> component3()
  {
    return this.onCancellation;
  }
  
  public final Object component4()
  {
    return this.idempotentResume;
  }
  
  public final Throwable component5()
  {
    return this.cancelCause;
  }
  
  public final CompletedContinuation copy(Object paramObject1, CancelHandler paramCancelHandler, Function1<? super Throwable, Unit> paramFunction1, Object paramObject2, Throwable paramThrowable)
  {
    return new CompletedContinuation(paramObject1, paramCancelHandler, paramFunction1, paramObject2, paramThrowable);
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof CompletedContinuation)) {
      return false;
    }
    paramObject = (CompletedContinuation)paramObject;
    if (!Intrinsics.areEqual(this.result, ((CompletedContinuation)paramObject).result)) {
      return false;
    }
    if (!Intrinsics.areEqual(this.cancelHandler, ((CompletedContinuation)paramObject).cancelHandler)) {
      return false;
    }
    if (!Intrinsics.areEqual(this.onCancellation, ((CompletedContinuation)paramObject).onCancellation)) {
      return false;
    }
    if (!Intrinsics.areEqual(this.idempotentResume, ((CompletedContinuation)paramObject).idempotentResume)) {
      return false;
    }
    return Intrinsics.areEqual(this.cancelCause, ((CompletedContinuation)paramObject).cancelCause);
  }
  
  public final boolean getCancelled()
  {
    boolean bool;
    if (this.cancelCause != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public int hashCode()
  {
    Object localObject = this.result;
    int n = 0;
    int i;
    if (localObject == null) {
      i = 0;
    } else {
      i = localObject.hashCode();
    }
    localObject = this.cancelHandler;
    int j;
    if (localObject == null) {
      j = 0;
    } else {
      j = ((CancelHandler)localObject).hashCode();
    }
    localObject = this.onCancellation;
    int k;
    if (localObject == null) {
      k = 0;
    } else {
      k = localObject.hashCode();
    }
    localObject = this.idempotentResume;
    int m;
    if (localObject == null) {
      m = 0;
    } else {
      m = localObject.hashCode();
    }
    localObject = this.cancelCause;
    if (localObject != null) {
      n = ((Throwable)localObject).hashCode();
    }
    return (((i * 31 + j) * 31 + k) * 31 + m) * 31 + n;
  }
  
  public final void invokeHandlers(CancellableContinuationImpl<?> paramCancellableContinuationImpl, Throwable paramThrowable)
  {
    Object localObject = this.cancelHandler;
    if (localObject != null) {
      paramCancellableContinuationImpl.callCancelHandler((CancelHandler)localObject, paramThrowable);
    }
    localObject = this.onCancellation;
    if (localObject != null) {
      paramCancellableContinuationImpl.callOnCancellation((Function1)localObject, paramThrowable);
    }
  }
  
  public String toString()
  {
    return "CompletedContinuation(result=" + this.result + ", cancelHandler=" + this.cancelHandler + ", onCancellation=" + this.onCancellation + ", idempotentResume=" + this.idempotentResume + ", cancelCause=" + this.cancelCause + ')';
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/CompletedContinuation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */