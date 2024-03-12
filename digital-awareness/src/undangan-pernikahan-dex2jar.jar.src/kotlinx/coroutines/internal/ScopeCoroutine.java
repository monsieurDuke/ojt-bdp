package kotlinx.coroutines.internal;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlinx.coroutines.AbstractCoroutine;
import kotlinx.coroutines.ChildHandle;
import kotlinx.coroutines.CompletionStateKt;
import kotlinx.coroutines.Job;

@Metadata(d1={"\000J\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\005\n\002\020\013\n\002\b\002\n\002\030\002\n\002\b\003\n\002\020\002\n\000\n\002\020\000\n\002\b\002\n\002\030\002\n\002\030\002\n\000\b\020\030\000*\006\b\000\020\001 \0002\b\022\004\022\002H\0010\0022\0060\003j\002`\004B\033\022\006\020\005\032\0020\006\022\f\020\007\032\b\022\004\022\0028\0000\b¢\006\002\020\tJ\022\020\024\032\0020\0252\b\020\026\032\004\030\0010\027H\024J\022\020\030\032\0020\0252\b\020\026\032\004\030\0010\027H\024J\016\020\031\032\n\030\0010\032j\004\030\001`\033R\031\020\n\032\n\030\0010\003j\004\030\001`\0048F¢\006\006\032\004\b\013\020\fR\024\020\r\032\0020\0168DX\004¢\006\006\032\004\b\r\020\017R\026\020\020\032\004\030\0010\0218@X\004¢\006\006\032\004\b\022\020\023R\026\020\007\032\b\022\004\022\0028\0000\b8\006X\004¢\006\002\n\000¨\006\034"}, d2={"Lkotlinx/coroutines/internal/ScopeCoroutine;", "T", "Lkotlinx/coroutines/AbstractCoroutine;", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "Lkotlinx/coroutines/internal/CoroutineStackFrame;", "context", "Lkotlin/coroutines/CoroutineContext;", "uCont", "Lkotlin/coroutines/Continuation;", "(Lkotlin/coroutines/CoroutineContext;Lkotlin/coroutines/Continuation;)V", "callerFrame", "getCallerFrame", "()Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "isScopedCoroutine", "", "()Z", "parent", "Lkotlinx/coroutines/Job;", "getParent$kotlinx_coroutines_core", "()Lkotlinx/coroutines/Job;", "afterCompletion", "", "state", "", "afterResume", "getStackTraceElement", "Ljava/lang/StackTraceElement;", "Lkotlinx/coroutines/internal/StackTraceElement;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public class ScopeCoroutine<T>
  extends AbstractCoroutine<T>
  implements CoroutineStackFrame
{
  public final Continuation<T> uCont;
  
  public ScopeCoroutine(CoroutineContext paramCoroutineContext, Continuation<? super T> paramContinuation)
  {
    super(paramCoroutineContext, true, true);
    this.uCont = paramContinuation;
  }
  
  protected void afterCompletion(Object paramObject)
  {
    DispatchedContinuationKt.resumeCancellableWith$default(IntrinsicsKt.intercepted(this.uCont), CompletionStateKt.recoverResult(paramObject, this.uCont), null, 2, null);
  }
  
  protected void afterResume(Object paramObject)
  {
    Continuation localContinuation = this.uCont;
    localContinuation.resumeWith(CompletionStateKt.recoverResult(paramObject, localContinuation));
  }
  
  public final CoroutineStackFrame getCallerFrame()
  {
    Object localObject = this.uCont;
    if ((localObject instanceof CoroutineStackFrame)) {
      localObject = (CoroutineStackFrame)localObject;
    } else {
      localObject = null;
    }
    return (CoroutineStackFrame)localObject;
  }
  
  public final Job getParent$kotlinx_coroutines_core()
  {
    Object localObject = getParentHandle$kotlinx_coroutines_core();
    if (localObject == null) {
      localObject = null;
    } else {
      localObject = ((ChildHandle)localObject).getParent();
    }
    return (Job)localObject;
  }
  
  public final StackTraceElement getStackTraceElement()
  {
    return null;
  }
  
  protected final boolean isScopedCoroutine()
  {
    return true;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/internal/ScopeCoroutine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */