package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000^\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\013\n\002\b\f\n\002\020\002\n\000\n\002\020\000\n\000\n\002\020\016\n\002\b\002\n\002\020\003\n\002\b\f\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\b\002\b'\030\000*\006\b\000\020\001 \0002\0020\0022\0020\0032\b\022\004\022\002H\0010\0042\0020\005B\035\022\006\020\006\032\0020\007\022\006\020\b\032\0020\t\022\006\020\n\032\0020\t¢\006\002\020\013J\022\020\025\032\0020\0262\b\020\027\032\004\030\0010\030H\024J\b\020\031\032\0020\032H\024J\025\020\033\032\0020\0262\006\020\034\032\0020\035H\000¢\006\002\b\036J\r\020\037\032\0020\032H\020¢\006\002\b J\030\020!\032\0020\0262\006\020\"\032\0020\0352\006\020#\032\0020\tH\024J\025\020$\032\0020\0262\006\020%\032\0028\000H\024¢\006\002\020&J\022\020'\032\0020\0262\b\020\027\032\004\030\0010\030H\004J\034\020(\032\0020\0262\f\020)\032\b\022\004\022\0028\0000*ø\001\000¢\006\002\020&JM\020+\032\0020\026\"\004\b\001\020,2\006\020+\032\0020-2\006\020.\032\002H,2'\020/\032#\b\001\022\004\022\002H,\022\n\022\b\022\004\022\0028\0000\004\022\006\022\004\030\0010\03000¢\006\002\b1ø\001\000¢\006\002\0202R\027\020\f\032\0020\007¢\006\016\n\000\022\004\b\r\020\016\032\004\b\017\020\020R\024\020\021\032\0020\0078VX\004¢\006\006\032\004\b\022\020\020R\024\020\023\032\0020\t8VX\004¢\006\006\032\004\b\023\020\024\002\004\n\002\b\031¨\0063"}, d2={"Lkotlinx/coroutines/AbstractCoroutine;", "T", "Lkotlinx/coroutines/JobSupport;", "Lkotlinx/coroutines/Job;", "Lkotlin/coroutines/Continuation;", "Lkotlinx/coroutines/CoroutineScope;", "parentContext", "Lkotlin/coroutines/CoroutineContext;", "initParentJob", "", "active", "(Lkotlin/coroutines/CoroutineContext;ZZ)V", "context", "getContext$annotations", "()V", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "coroutineContext", "getCoroutineContext", "isActive", "()Z", "afterResume", "", "state", "", "cancellationExceptionMessage", "", "handleOnCompletionException", "exception", "", "handleOnCompletionException$kotlinx_coroutines_core", "nameString", "nameString$kotlinx_coroutines_core", "onCancelled", "cause", "handled", "onCompleted", "value", "(Ljava/lang/Object;)V", "onCompletionInternal", "resumeWith", "result", "Lkotlin/Result;", "start", "R", "Lkotlinx/coroutines/CoroutineStart;", "receiver", "block", "Lkotlin/Function2;", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/CoroutineStart;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)V", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public abstract class AbstractCoroutine<T>
  extends JobSupport
  implements Job, Continuation<T>, CoroutineScope
{
  private final CoroutineContext context;
  
  public AbstractCoroutine(CoroutineContext paramCoroutineContext, boolean paramBoolean1, boolean paramBoolean2)
  {
    super(paramBoolean2);
    if (paramBoolean1) {
      initParentJob((Job)paramCoroutineContext.get((CoroutineContext.Key)Job.Key));
    }
    this.context = paramCoroutineContext.plus((CoroutineContext)this);
  }
  
  protected void afterResume(Object paramObject)
  {
    afterCompletion(paramObject);
  }
  
  protected String cancellationExceptionMessage()
  {
    String str = DebugStringsKt.getClassSimpleName(this);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    str = Intrinsics.stringPlus(str, " was cancelled");
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str;
  }
  
  public final CoroutineContext getContext()
  {
    return this.context;
  }
  
  public CoroutineContext getCoroutineContext()
  {
    return this.context;
  }
  
  public final void handleOnCompletionException$kotlinx_coroutines_core(Throwable paramThrowable)
  {
    CoroutineExceptionHandlerKt.handleCoroutineException(this.context, paramThrowable);
  }
  
  public boolean isActive()
  {
    return super.isActive();
  }
  
  public String nameString$kotlinx_coroutines_core()
  {
    String str = CoroutineContextKt.getCoroutineName(this.context);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    if (str == null) {
      return super.nameString$kotlinx_coroutines_core();
    }
    return '"' + str + "\":" + super.nameString$kotlinx_coroutines_core();
  }
  
  protected void onCancelled(Throwable paramThrowable, boolean paramBoolean) {}
  
  protected void onCompleted(T paramT) {}
  
  protected final void onCompletionInternal(Object paramObject)
  {
    if ((paramObject instanceof CompletedExceptionally)) {
      onCancelled(((CompletedExceptionally)paramObject).cause, ((CompletedExceptionally)paramObject).getHandled());
    } else {
      onCompleted(paramObject);
    }
  }
  
  public final void resumeWith(Object paramObject)
  {
    paramObject = makeCompletingOnce$kotlinx_coroutines_core(CompletionStateKt.toState$default(paramObject, null, 1, null));
    if (paramObject == JobSupportKt.COMPLETING_WAITING_CHILDREN) {
      return;
    }
    afterResume(paramObject);
  }
  
  public final <R> void start(CoroutineStart paramCoroutineStart, R paramR, Function2<? super R, ? super Continuation<? super T>, ? extends Object> paramFunction2)
  {
    paramCoroutineStart.invoke(paramFunction2, paramR, (Continuation)this);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/AbstractCoroutine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */