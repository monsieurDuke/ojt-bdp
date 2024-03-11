package kotlinx.coroutines;

import androidx.concurrent.futures.AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.internal.DispatchedContinuation;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;
import kotlinx.coroutines.internal.Symbol;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000¶\001\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\b\n\002\b\003\n\002\020\000\n\000\n\002\020\001\n\002\b\002\n\002\030\002\n\000\n\002\020\003\n\000\n\002\020\002\n\002\b\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\005\n\002\020\013\n\002\b\021\n\002\030\002\n\002\b\t\n\002\030\002\n\002\030\002\n\002\b\006\n\002\030\002\n\002\b\n\n\002\020\016\n\002\b\f\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\t\n\002\030\002\n\002\b\006\n\002\030\002\n\002\b\004\n\002\030\002\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\020\n\002\030\002\n\002\030\002\b\021\030\000*\006\b\000\020\001 \0002\t\022\004\022\0028\0000\0012\t\022\004\022\0028\0000\0012\0060tj\002`uB\035\022\f\020\003\032\b\022\004\022\0028\0000\002\022\006\020\005\032\0020\004¢\006\004\b\006\020\007J\031\020\013\032\0020\n2\b\020\t\032\004\030\0010\bH\002¢\006\004\b\013\020\fJ\037\020\022\032\0020\0212\006\020\016\032\0020\r2\b\020\020\032\004\030\0010\017¢\006\004\b\022\020\023JB\020\022\032\0020\0212'\020\016\032#\022\025\022\023\030\0010\017¢\006\f\b\025\022\b\b\026\022\004\b\b(\020\022\004\022\0020\0210\024j\002`\0272\b\020\020\032\004\030\0010\017H\002¢\006\004\b\022\020\030J\036\020\033\032\0020\0212\f\020\032\032\b\022\004\022\0020\0210\031H\b¢\006\004\b\033\020\034J8\020\036\032\0020\0212!\020\035\032\035\022\023\022\0210\017¢\006\f\b\025\022\b\b\026\022\004\b\b(\020\022\004\022\0020\0210\0242\006\020\020\032\0020\017¢\006\004\b\036\020\030J\031\020 \032\0020\0372\b\020\020\032\004\030\0010\017H\026¢\006\004\b \020!J!\020%\032\0020\0212\b\020\"\032\004\030\0010\b2\006\020\020\032\0020\017H\020¢\006\004\b#\020$J\027\020&\032\0020\0372\006\020\020\032\0020\017H\002¢\006\004\b&\020!J\027\020(\032\0020\0212\006\020'\032\0020\bH\026¢\006\004\b(\020)J\017\020,\032\0020\021H\000¢\006\004\b*\020+J\017\020-\032\0020\021H\002¢\006\004\b-\020+J\027\020/\032\0020\0212\006\020.\032\0020\004H\002¢\006\004\b/\0200J\027\0203\032\0020\0172\006\0202\032\00201H\026¢\006\004\b3\0204J\033\0208\032\004\030\0010\0172\b\0205\032\004\030\0010\bH\020¢\006\004\b6\0207J\021\0209\032\004\030\0010\bH\001¢\006\004\b9\020:J\027\020=\032\n\030\0010;j\004\030\001`<H\026¢\006\004\b=\020>J\037\020A\032\0028\001\"\004\b\001\020\0012\b\0205\032\004\030\0010\bH\020¢\006\004\b?\020@J\017\020B\032\0020\021H\026¢\006\004\bB\020+J\021\020D\032\004\030\0010CH\002¢\006\004\bD\020EJ8\020F\032\0020\0212'\020\016\032#\022\025\022\023\030\0010\017¢\006\f\b\025\022\b\b\026\022\004\b\b(\020\022\004\022\0020\0210\024j\002`\027H\026¢\006\004\bF\020GJ\017\020H\032\0020\037H\002¢\006\004\bH\020IJ8\020J\032\0020\r2'\020\016\032#\022\025\022\023\030\0010\017¢\006\f\b\025\022\b\b\026\022\004\b\b(\020\022\004\022\0020\0210\024j\002`\027H\002¢\006\004\bJ\020KJB\020L\032\0020\0212'\020\016\032#\022\025\022\023\030\0010\017¢\006\f\b\025\022\b\b\026\022\004\b\b(\020\022\004\022\0020\0210\024j\002`\0272\b\0205\032\004\030\0010\bH\002¢\006\004\bL\020MJ\017\020O\032\0020NH\024¢\006\004\bO\020PJ\027\020S\032\0020\0212\006\020\020\032\0020\017H\000¢\006\004\bQ\020RJ\017\020T\032\0020\021H\002¢\006\004\bT\020+J\017\020U\032\0020\037H\001¢\006\004\bU\020IJ<\020W\032\0020\0212\006\020V\032\0028\0002#\020\035\032\037\022\023\022\0210\017¢\006\f\b\025\022\b\b\026\022\004\b\b(\020\022\004\022\0020\021\030\0010\024H\026¢\006\004\bW\020XJH\020Y\032\0020\0212\b\020\t\032\004\030\0010\b2\006\020\005\032\0020\0042%\b\002\020\035\032\037\022\023\022\0210\017¢\006\f\b\025\022\b\b\026\022\004\b\b(\020\022\004\022\0020\021\030\0010\024H\002¢\006\004\bY\020ZJ \020]\032\0020\0212\f\020\\\032\b\022\004\022\0028\0000[H\026ø\001\000¢\006\004\b]\020)JZ\020`\032\004\030\0010\b2\006\0205\032\0020^2\b\020\t\032\004\030\0010\b2\006\020\005\032\0020\0042#\020\035\032\037\022\023\022\0210\017¢\006\f\b\025\022\b\b\026\022\004\b\b(\020\022\004\022\0020\021\030\0010\0242\b\020_\032\004\030\0010\bH\002¢\006\004\b`\020aJ\021\020c\032\004\030\0010\bH\020¢\006\004\bb\020:J\017\020d\032\0020NH\026¢\006\004\bd\020PJ\017\020e\032\0020\037H\002¢\006\004\be\020IJ#\020e\032\004\030\0010\b2\006\020V\032\0028\0002\b\020_\032\004\030\0010\bH\026¢\006\004\be\020fJH\020e\032\004\030\0010\b2\006\020V\032\0028\0002\b\020_\032\004\030\0010\b2#\020\035\032\037\022\023\022\0210\017¢\006\f\b\025\022\b\b\026\022\004\b\b(\020\022\004\022\0020\021\030\0010\024H\026¢\006\004\be\020gJJ\020i\032\004\030\0010h2\b\020\t\032\004\030\0010\b2\b\020_\032\004\030\0010\b2#\020\035\032\037\022\023\022\0210\017¢\006\f\b\025\022\b\b\026\022\004\b\b(\020\022\004\022\0020\021\030\0010\024H\002¢\006\004\bi\020jJ\031\020l\032\004\030\0010\b2\006\020k\032\0020\017H\026¢\006\004\bl\020mJ\017\020n\032\0020\037H\002¢\006\004\bn\020IJ\033\020p\032\0020\021*\0020o2\006\020V\032\0028\000H\026¢\006\004\bp\020qJ\033\020r\032\0020\021*\0020o2\006\020k\032\0020\017H\026¢\006\004\br\020sR\034\020x\032\n\030\0010tj\004\030\001`u8VX\004¢\006\006\032\004\bv\020wR\032\020z\032\0020y8\026X\004¢\006\f\n\004\bz\020{\032\004\b|\020}R!\020\003\032\b\022\004\022\0028\0000\0028\000X\004¢\006\r\n\004\b\003\020~\032\005\b\020\001R\026\020\001\032\0020\0378VX\004¢\006\007\032\005\b\001\020IR\026\020\001\032\0020\0378VX\004¢\006\007\032\005\b\001\020IR\026\020\001\032\0020\0378VX\004¢\006\007\032\005\b\001\020IR\033\020\001\032\004\030\0010C8\002@\002X\016¢\006\b\n\006\b\001\020\001R\027\0205\032\004\030\0010\b8@X\004¢\006\007\032\005\b\001\020:R\026\020\001\032\0020N8BX\004¢\006\007\032\005\b\001\020P\002\004\n\002\b\031¨\006\001"}, d2={"Lkotlinx/coroutines/CancellableContinuationImpl;", "T", "Lkotlin/coroutines/Continuation;", "delegate", "", "resumeMode", "<init>", "(Lkotlin/coroutines/Continuation;I)V", "", "proposedUpdate", "", "alreadyResumedError", "(Ljava/lang/Object;)Ljava/lang/Void;", "Lkotlinx/coroutines/CancelHandler;", "handler", "", "cause", "", "callCancelHandler", "(Lkotlinx/coroutines/CancelHandler;Ljava/lang/Throwable;)V", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "Lkotlinx/coroutines/CompletionHandler;", "(Lkotlin/jvm/functions/Function1;Ljava/lang/Throwable;)V", "Lkotlin/Function0;", "block", "callCancelHandlerSafely", "(Lkotlin/jvm/functions/Function0;)V", "onCancellation", "callOnCancellation", "", "cancel", "(Ljava/lang/Throwable;)Z", "takenState", "cancelCompletedResult$kotlinx_coroutines_core", "(Ljava/lang/Object;Ljava/lang/Throwable;)V", "cancelCompletedResult", "cancelLater", "token", "completeResume", "(Ljava/lang/Object;)V", "detachChild$kotlinx_coroutines_core", "()V", "detachChild", "detachChildIfNonResuable", "mode", "dispatchResume", "(I)V", "Lkotlinx/coroutines/Job;", "parent", "getContinuationCancellationCause", "(Lkotlinx/coroutines/Job;)Ljava/lang/Throwable;", "state", "getExceptionalResult$kotlinx_coroutines_core", "(Ljava/lang/Object;)Ljava/lang/Throwable;", "getExceptionalResult", "getResult", "()Ljava/lang/Object;", "Ljava/lang/StackTraceElement;", "Lkotlinx/coroutines/internal/StackTraceElement;", "getStackTraceElement", "()Ljava/lang/StackTraceElement;", "getSuccessfulResult$kotlinx_coroutines_core", "(Ljava/lang/Object;)Ljava/lang/Object;", "getSuccessfulResult", "initCancellability", "Lkotlinx/coroutines/DisposableHandle;", "installParentHandle", "()Lkotlinx/coroutines/DisposableHandle;", "invokeOnCancellation", "(Lkotlin/jvm/functions/Function1;)V", "isReusable", "()Z", "makeCancelHandler", "(Lkotlin/jvm/functions/Function1;)Lkotlinx/coroutines/CancelHandler;", "multipleHandlersError", "(Lkotlin/jvm/functions/Function1;Ljava/lang/Object;)V", "", "nameString", "()Ljava/lang/String;", "parentCancelled$kotlinx_coroutines_core", "(Ljava/lang/Throwable;)V", "parentCancelled", "releaseClaimedReusableContinuation", "resetStateReusable", "value", "resume", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)V", "resumeImpl", "(Ljava/lang/Object;ILkotlin/jvm/functions/Function1;)V", "Lkotlin/Result;", "result", "resumeWith", "Lkotlinx/coroutines/NotCompleted;", "idempotent", "resumedState", "(Lkotlinx/coroutines/NotCompleted;Ljava/lang/Object;ILkotlin/jvm/functions/Function1;Ljava/lang/Object;)Ljava/lang/Object;", "takeState$kotlinx_coroutines_core", "takeState", "toString", "tryResume", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "(Ljava/lang/Object;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "Lkotlinx/coroutines/internal/Symbol;", "tryResumeImpl", "(Ljava/lang/Object;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Lkotlinx/coroutines/internal/Symbol;", "exception", "tryResumeWithException", "(Ljava/lang/Throwable;)Ljava/lang/Object;", "trySuspend", "Lkotlinx/coroutines/CoroutineDispatcher;", "resumeUndispatched", "(Lkotlinx/coroutines/CoroutineDispatcher;Ljava/lang/Object;)V", "resumeUndispatchedWithException", "(Lkotlinx/coroutines/CoroutineDispatcher;Ljava/lang/Throwable;)V", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "Lkotlinx/coroutines/internal/CoroutineStackFrame;", "getCallerFrame", "()Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "callerFrame", "Lkotlin/coroutines/CoroutineContext;", "context", "Lkotlin/coroutines/CoroutineContext;", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "Lkotlin/coroutines/Continuation;", "getDelegate$kotlinx_coroutines_core", "()Lkotlin/coroutines/Continuation;", "isActive", "isCancelled", "isCompleted", "parentHandle", "Lkotlinx/coroutines/DisposableHandle;", "getState$kotlinx_coroutines_core", "getStateDebugRepresentation", "stateDebugRepresentation", "kotlinx-coroutines-core", "Lkotlinx/coroutines/DispatchedTask;", "Lkotlinx/coroutines/CancellableContinuation;"}, k=1, mv={1, 6, 0}, xi=48)
public class CancellableContinuationImpl<T>
  extends DispatchedTask<T>
  implements CancellableContinuation<T>, CoroutineStackFrame
{
  private static final AtomicIntegerFieldUpdater _decision$FU = AtomicIntegerFieldUpdater.newUpdater(CancellableContinuationImpl.class, "_decision");
  private static final AtomicReferenceFieldUpdater _state$FU = AtomicReferenceFieldUpdater.newUpdater(CancellableContinuationImpl.class, Object.class, "_state");
  private volatile int _decision;
  private volatile Object _state;
  private final CoroutineContext context;
  private final Continuation<T> delegate;
  private DisposableHandle parentHandle;
  
  public CancellableContinuationImpl(Continuation<? super T> paramContinuation, int paramInt)
  {
    super(paramInt);
    this.delegate = paramContinuation;
    if (DebugKt.getASSERTIONS_ENABLED())
    {
      if (paramInt != -1) {
        paramInt = 1;
      } else {
        paramInt = 0;
      }
      if (paramInt == 0) {
        throw new AssertionError();
      }
    }
    this.context = paramContinuation.getContext();
    this._decision = 0;
    this._state = Active.INSTANCE;
  }
  
  private final Void alreadyResumedError(Object paramObject)
  {
    paramObject = Intrinsics.stringPlus("Already resumed, but proposed with update ", paramObject);
    Log5ECF72.a(paramObject);
    LogE84000.a(paramObject);
    Log229316.a(paramObject);
    throw new IllegalStateException(paramObject.toString());
  }
  
  /* Error */
  private final void callCancelHandler(Function1<? super Throwable, Unit> paramFunction1, Throwable paramThrowable)
  {
    // Byte code:
    //   0: aload_1
    //   1: aload_2
    //   2: invokeinterface 244 2 0
    //   7: pop
    //   8: goto +44 -> 52
    //   11: astore_3
    //   12: aload_0
    //   13: invokevirtual 245	kotlinx/coroutines/CancellableContinuationImpl:getContext	()Lkotlin/coroutines/CoroutineContext;
    //   16: astore_2
    //   17: ldc -9
    //   19: aload_0
    //   20: invokestatic 220	kotlin/jvm/internal/Intrinsics:stringPlus	(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/String;
    //   23: astore_1
    //   24: aload_1
    //   25: invokestatic 225	mt/Log5ECF72:a	(Ljava/lang/Object;)V
    //   28: aload_1
    //   29: invokestatic 228	mt/LogE84000:a	(Ljava/lang/Object;)V
    //   32: aload_1
    //   33: invokestatic 231	mt/Log229316:a	(Ljava/lang/Object;)V
    //   36: aload_2
    //   37: new 249	kotlinx/coroutines/CompletionHandlerException
    //   40: dup
    //   41: aload_1
    //   42: aload_3
    //   43: invokespecial 252	kotlinx/coroutines/CompletionHandlerException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   46: checkcast 254	java/lang/Throwable
    //   49: invokestatic 260	kotlinx/coroutines/CoroutineExceptionHandlerKt:handleCoroutineException	(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Throwable;)V
    //   52: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	53	0	this	CancellableContinuationImpl
    //   0	53	1	paramFunction1	Function1<? super Throwable, Unit>
    //   0	53	2	paramThrowable	Throwable
    //   11	32	3	localThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   0	8	11	finally
  }
  
  /* Error */
  private final void callCancelHandlerSafely(kotlin.jvm.functions.Function0<Unit> paramFunction0)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokeinterface 265 1 0
    //   6: pop
    //   7: goto +44 -> 51
    //   10: astore_3
    //   11: aload_0
    //   12: invokevirtual 245	kotlinx/coroutines/CancellableContinuationImpl:getContext	()Lkotlin/coroutines/CoroutineContext;
    //   15: astore_2
    //   16: ldc -9
    //   18: aload_0
    //   19: invokestatic 220	kotlin/jvm/internal/Intrinsics:stringPlus	(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/String;
    //   22: astore_1
    //   23: aload_1
    //   24: invokestatic 225	mt/Log5ECF72:a	(Ljava/lang/Object;)V
    //   27: aload_1
    //   28: invokestatic 228	mt/LogE84000:a	(Ljava/lang/Object;)V
    //   31: aload_1
    //   32: invokestatic 231	mt/Log229316:a	(Ljava/lang/Object;)V
    //   35: aload_2
    //   36: new 249	kotlinx/coroutines/CompletionHandlerException
    //   39: dup
    //   40: aload_1
    //   41: aload_3
    //   42: invokespecial 252	kotlinx/coroutines/CompletionHandlerException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   45: checkcast 254	java/lang/Throwable
    //   48: invokestatic 260	kotlinx/coroutines/CoroutineExceptionHandlerKt:handleCoroutineException	(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Throwable;)V
    //   51: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	52	0	this	CancellableContinuationImpl
    //   0	52	1	paramFunction0	kotlin.jvm.functions.Function0<Unit>
    //   15	21	2	localCoroutineContext	CoroutineContext
    //   10	32	3	localThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   0	7	10	finally
  }
  
  private final boolean cancelLater(Throwable paramThrowable)
  {
    if (!isReusable()) {
      return false;
    }
    return ((DispatchedContinuation)this.delegate).postponeCancellation(paramThrowable);
  }
  
  private final void detachChildIfNonResuable()
  {
    if (!isReusable()) {
      detachChild$kotlinx_coroutines_core();
    }
  }
  
  private final void dispatchResume(int paramInt)
  {
    if (tryResume()) {
      return;
    }
    DispatchedTaskKt.dispatch((DispatchedTask)this, paramInt);
  }
  
  private final String getStateDebugRepresentation()
  {
    Object localObject = getState$kotlinx_coroutines_core();
    if ((localObject instanceof NotCompleted)) {
      localObject = "Active";
    } else if ((localObject instanceof CancelledContinuation)) {
      localObject = "Cancelled";
    } else {
      localObject = "Completed";
    }
    return (String)localObject;
  }
  
  private final DisposableHandle installParentHandle()
  {
    Object localObject = (Job)getContext().get((CoroutineContext.Key)Job.Key);
    if (localObject == null) {
      return null;
    }
    localObject = Job.DefaultImpls.invokeOnCompletion$default((Job)localObject, true, false, (Function1)new ChildContinuation(this), 2, null);
    this.parentHandle = ((DisposableHandle)localObject);
    return (DisposableHandle)localObject;
  }
  
  private final boolean isReusable()
  {
    boolean bool;
    if ((DispatchedTaskKt.isReusableMode(this.resumeMode)) && (((DispatchedContinuation)this.delegate).isReusable())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private final CancelHandler makeCancelHandler(Function1<? super Throwable, Unit> paramFunction1)
  {
    if ((paramFunction1 instanceof CancelHandler)) {
      paramFunction1 = (CancelHandler)paramFunction1;
    } else {
      paramFunction1 = (CancelHandler)new InvokeOnCancel(paramFunction1);
    }
    return paramFunction1;
  }
  
  private final void multipleHandlersError(Function1<? super Throwable, Unit> paramFunction1, Object paramObject)
  {
    throw new IllegalStateException(("It's prohibited to register multiple handlers, tried to register " + paramFunction1 + ", already has " + paramObject).toString());
  }
  
  private final void releaseClaimedReusableContinuation()
  {
    Object localObject1 = this.delegate;
    boolean bool = localObject1 instanceof DispatchedContinuation;
    Object localObject2 = null;
    if (bool) {
      localObject1 = (DispatchedContinuation)localObject1;
    } else {
      localObject1 = null;
    }
    if (localObject1 == null) {
      localObject1 = localObject2;
    } else {
      localObject1 = ((DispatchedContinuation)localObject1).tryReleaseClaimedContinuation((CancellableContinuation)this);
    }
    if (localObject1 == null) {
      return;
    }
    detachChild$kotlinx_coroutines_core();
    cancel((Throwable)localObject1);
  }
  
  private final void resumeImpl(Object paramObject, int paramInt, Function1<? super Throwable, Unit> paramFunction1)
  {
    Object localObject1;
    Object localObject2;
    do
    {
      localObject1 = this._state;
      if (!(localObject1 instanceof NotCompleted)) {
        break;
      }
      localObject2 = resumedState((NotCompleted)localObject1, paramObject, paramInt, paramFunction1, null);
    } while (!AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_state$FU, this, localObject1, localObject2));
    detachChildIfNonResuable();
    dispatchResume(paramInt);
    return;
    if (((localObject1 instanceof CancelledContinuation)) && (((CancelledContinuation)localObject1).makeResumed()))
    {
      if (paramFunction1 != null) {
        callOnCancellation(paramFunction1, ((CancelledContinuation)localObject1).cause);
      }
      return;
    }
    alreadyResumedError(paramObject);
    throw new KotlinNothingValueException();
  }
  
  private final Object resumedState(NotCompleted paramNotCompleted, Object paramObject1, int paramInt, Function1<? super Throwable, Unit> paramFunction1, Object paramObject2)
  {
    if ((paramObject1 instanceof CompletedExceptionally))
    {
      boolean bool = DebugKt.getASSERTIONS_ENABLED();
      int i = 1;
      if (bool)
      {
        if (paramObject2 == null) {
          paramInt = 1;
        } else {
          paramInt = 0;
        }
        if (paramInt == 0) {
          throw new AssertionError();
        }
      }
      if (DebugKt.getASSERTIONS_ENABLED())
      {
        if (paramFunction1 == null) {
          paramInt = i;
        } else {
          paramInt = 0;
        }
        if (paramInt == 0) {
          throw new AssertionError();
        }
      }
    }
    else
    {
      if (((DispatchedTaskKt.isCancellableMode(paramInt)) || (paramObject2 != null)) && ((paramFunction1 != null) || (((paramNotCompleted instanceof CancelHandler)) && (!(paramNotCompleted instanceof BeforeResumeCancelHandler))) || (paramObject2 != null))) {
        break label129;
      }
    }
    return paramObject1;
    label129:
    if ((paramNotCompleted instanceof CancelHandler)) {
      paramNotCompleted = (CancelHandler)paramNotCompleted;
    } else {
      paramNotCompleted = null;
    }
    paramObject1 = new CompletedContinuation(paramObject1, paramNotCompleted, paramFunction1, paramObject2, null, 16, null);
    return paramObject1;
  }
  
  private final boolean tryResume()
  {
    for (;;)
    {
      switch (this._decision)
      {
      default: 
        throw new IllegalStateException("Already resumed".toString());
      case 1: 
        return false;
      }
      if (_decision$FU.compareAndSet(this, 0, 2)) {
        return true;
      }
    }
  }
  
  private final Symbol tryResumeImpl(Object paramObject1, Object paramObject2, Function1<? super Throwable, Unit> paramFunction1)
  {
    Object localObject1;
    Object localObject2;
    do
    {
      localObject1 = this._state;
      if (!(localObject1 instanceof NotCompleted)) {
        break;
      }
      localObject2 = resumedState((NotCompleted)localObject1, paramObject1, this.resumeMode, paramFunction1, paramObject2);
    } while (!AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_state$FU, this, localObject1, localObject2));
    detachChildIfNonResuable();
    return CancellableContinuationImplKt.RESUME_TOKEN;
    boolean bool = localObject1 instanceof CompletedContinuation;
    paramFunction1 = null;
    if (bool)
    {
      if ((paramObject2 != null) && (((CompletedContinuation)localObject1).idempotentResume == paramObject2))
      {
        if ((DebugKt.getASSERTIONS_ENABLED()) && (!Intrinsics.areEqual(((CompletedContinuation)localObject1).result, paramObject1))) {
          throw new AssertionError();
        }
        paramObject1 = CancellableContinuationImplKt.RESUME_TOKEN;
      }
      else
      {
        paramObject1 = (Symbol)null;
        paramObject1 = paramFunction1;
      }
      return (Symbol)paramObject1;
    }
    return null;
  }
  
  private final boolean trySuspend()
  {
    for (;;)
    {
      switch (this._decision)
      {
      case 1: 
      default: 
        throw new IllegalStateException("Already suspended".toString());
      case 2: 
        return false;
      }
      if (_decision$FU.compareAndSet(this, 0, 1)) {
        return true;
      }
    }
  }
  
  /* Error */
  public final void callCancelHandler(CancelHandler paramCancelHandler, Throwable paramThrowable)
  {
    // Byte code:
    //   0: aload_1
    //   1: aload_2
    //   2: invokevirtual 434	kotlinx/coroutines/CancelHandler:invoke	(Ljava/lang/Throwable;)V
    //   5: goto +44 -> 49
    //   8: astore_3
    //   9: aload_0
    //   10: invokevirtual 245	kotlinx/coroutines/CancellableContinuationImpl:getContext	()Lkotlin/coroutines/CoroutineContext;
    //   13: astore_2
    //   14: ldc -9
    //   16: aload_0
    //   17: invokestatic 220	kotlin/jvm/internal/Intrinsics:stringPlus	(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/String;
    //   20: astore_1
    //   21: aload_1
    //   22: invokestatic 225	mt/Log5ECF72:a	(Ljava/lang/Object;)V
    //   25: aload_1
    //   26: invokestatic 228	mt/LogE84000:a	(Ljava/lang/Object;)V
    //   29: aload_1
    //   30: invokestatic 231	mt/Log229316:a	(Ljava/lang/Object;)V
    //   33: aload_2
    //   34: new 249	kotlinx/coroutines/CompletionHandlerException
    //   37: dup
    //   38: aload_1
    //   39: aload_3
    //   40: invokespecial 252	kotlinx/coroutines/CompletionHandlerException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   43: checkcast 254	java/lang/Throwable
    //   46: invokestatic 260	kotlinx/coroutines/CoroutineExceptionHandlerKt:handleCoroutineException	(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Throwable;)V
    //   49: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	50	0	this	CancellableContinuationImpl
    //   0	50	1	paramCancelHandler	CancelHandler
    //   0	50	2	paramThrowable	Throwable
    //   8	32	3	localThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   0	5	8	finally
  }
  
  /* Error */
  public final void callOnCancellation(Function1<? super Throwable, Unit> paramFunction1, Throwable paramThrowable)
  {
    // Byte code:
    //   0: aload_1
    //   1: aload_2
    //   2: invokeinterface 244 2 0
    //   7: pop
    //   8: goto +45 -> 53
    //   11: astore_1
    //   12: aload_0
    //   13: invokevirtual 245	kotlinx/coroutines/CancellableContinuationImpl:getContext	()Lkotlin/coroutines/CoroutineContext;
    //   16: astore_2
    //   17: ldc_w 436
    //   20: aload_0
    //   21: invokestatic 220	kotlin/jvm/internal/Intrinsics:stringPlus	(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/String;
    //   24: astore_3
    //   25: aload_3
    //   26: invokestatic 225	mt/Log5ECF72:a	(Ljava/lang/Object;)V
    //   29: aload_3
    //   30: invokestatic 228	mt/LogE84000:a	(Ljava/lang/Object;)V
    //   33: aload_3
    //   34: invokestatic 231	mt/Log229316:a	(Ljava/lang/Object;)V
    //   37: aload_2
    //   38: new 249	kotlinx/coroutines/CompletionHandlerException
    //   41: dup
    //   42: aload_3
    //   43: aload_1
    //   44: invokespecial 252	kotlinx/coroutines/CompletionHandlerException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   47: checkcast 254	java/lang/Throwable
    //   50: invokestatic 260	kotlinx/coroutines/CoroutineExceptionHandlerKt:handleCoroutineException	(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Throwable;)V
    //   53: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	54	0	this	CancellableContinuationImpl
    //   0	54	1	paramFunction1	Function1<? super Throwable, Unit>
    //   0	54	2	paramThrowable	Throwable
    //   24	19	3	str	String
    // Exception table:
    //   from	to	target	type
    //   0	8	11	finally
  }
  
  public boolean cancel(Throwable paramThrowable)
  {
    Object localObject;
    CancelledContinuation localCancelledContinuation;
    do
    {
      localObject = this._state;
      if (!(localObject instanceof NotCompleted)) {
        return false;
      }
      localCancelledContinuation = new CancelledContinuation((Continuation)this, paramThrowable, localObject instanceof CancelHandler);
    } while (!AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_state$FU, this, localObject, localCancelledContinuation));
    if ((localObject instanceof CancelHandler)) {
      localObject = (CancelHandler)localObject;
    } else {
      localObject = null;
    }
    if (localObject != null) {
      callCancelHandler((CancelHandler)localObject, paramThrowable);
    }
    detachChildIfNonResuable();
    dispatchResume(this.resumeMode);
    return true;
  }
  
  public void cancelCompletedResult$kotlinx_coroutines_core(Object paramObject, Throwable paramThrowable)
  {
    for (;;)
    {
      paramObject = this._state;
      if ((paramObject instanceof NotCompleted)) {
        break;
      }
      if ((paramObject instanceof CompletedExceptionally)) {
        return;
      }
      if ((paramObject instanceof CompletedContinuation))
      {
        if ((((CompletedContinuation)paramObject).getCancelled() ^ true))
        {
          CompletedContinuation localCompletedContinuation = CompletedContinuation.copy$default((CompletedContinuation)paramObject, null, null, null, null, paramThrowable, 15, null);
          if (AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_state$FU, this, paramObject, localCompletedContinuation))
          {
            ((CompletedContinuation)paramObject).invokeHandlers(this, paramThrowable);
            return;
          }
        }
        else
        {
          throw new IllegalStateException("Must be called at most once".toString());
        }
      }
      else if (AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_state$FU, this, paramObject, new CompletedContinuation(paramObject, null, null, null, paramThrowable, 14, null))) {
        return;
      }
    }
    throw new IllegalStateException("Not completed".toString());
  }
  
  public void completeResume(Object paramObject)
  {
    if (DebugKt.getASSERTIONS_ENABLED())
    {
      int i;
      if (paramObject == CancellableContinuationImplKt.RESUME_TOKEN) {
        i = 1;
      } else {
        i = 0;
      }
      if (i == 0) {
        throw new AssertionError();
      }
    }
    dispatchResume(this.resumeMode);
  }
  
  public final void detachChild$kotlinx_coroutines_core()
  {
    DisposableHandle localDisposableHandle = this.parentHandle;
    if (localDisposableHandle == null) {
      return;
    }
    localDisposableHandle.dispose();
    this.parentHandle = ((DisposableHandle)NonDisposableHandle.INSTANCE);
  }
  
  public CoroutineStackFrame getCallerFrame()
  {
    Object localObject = this.delegate;
    if ((localObject instanceof CoroutineStackFrame)) {
      localObject = (CoroutineStackFrame)localObject;
    } else {
      localObject = null;
    }
    return (CoroutineStackFrame)localObject;
  }
  
  public CoroutineContext getContext()
  {
    return this.context;
  }
  
  public Throwable getContinuationCancellationCause(Job paramJob)
  {
    return (Throwable)paramJob.getCancellationException();
  }
  
  public final Continuation<T> getDelegate$kotlinx_coroutines_core()
  {
    return this.delegate;
  }
  
  public Throwable getExceptionalResult$kotlinx_coroutines_core(Object paramObject)
  {
    paramObject = super.getExceptionalResult$kotlinx_coroutines_core(paramObject);
    if (paramObject == null)
    {
      paramObject = null;
    }
    else
    {
      Continuation localContinuation = getDelegate$kotlinx_coroutines_core();
      if ((DebugKt.getRECOVER_STACK_TRACES()) && ((localContinuation instanceof CoroutineStackFrame))) {
        paramObject = StackTraceRecoveryKt.access$recoverFromStackFrame((Throwable)paramObject, (CoroutineStackFrame)localContinuation);
      }
    }
    return (Throwable)paramObject;
  }
  
  public final Object getResult()
  {
    boolean bool = isReusable();
    if (trySuspend())
    {
      if (this.parentHandle == null) {
        installParentHandle();
      }
      if (bool) {
        releaseClaimedReusableContinuation();
      }
      return IntrinsicsKt.getCOROUTINE_SUSPENDED();
    }
    if (bool) {
      releaseClaimedReusableContinuation();
    }
    Object localObject1 = getState$kotlinx_coroutines_core();
    Object localObject2;
    if ((localObject1 instanceof CompletedExceptionally))
    {
      localObject2 = ((CompletedExceptionally)localObject1).cause;
      localObject1 = localObject2;
      if (DebugKt.getRECOVER_STACK_TRACES()) {
        if (!((Continuation)this instanceof CoroutineStackFrame)) {
          localObject1 = localObject2;
        } else {
          localObject1 = StackTraceRecoveryKt.access$recoverFromStackFrame((Throwable)localObject2, (CoroutineStackFrame)this);
        }
      }
      throw ((Throwable)localObject1);
    }
    if (DispatchedTaskKt.isCancellableMode(this.resumeMode))
    {
      localObject2 = (Job)getContext().get((CoroutineContext.Key)Job.Key);
      if ((localObject2 != null) && (!((Job)localObject2).isActive()))
      {
        localObject2 = ((Job)localObject2).getCancellationException();
        cancelCompletedResult$kotlinx_coroutines_core(localObject1, (Throwable)localObject2);
        if ((DebugKt.getRECOVER_STACK_TRACES()) && (((Continuation)this instanceof CoroutineStackFrame))) {
          localObject1 = StackTraceRecoveryKt.access$recoverFromStackFrame((Throwable)localObject2, (CoroutineStackFrame)this);
        } else {
          localObject1 = (Throwable)localObject2;
        }
        throw ((Throwable)localObject1);
      }
    }
    return getSuccessfulResult$kotlinx_coroutines_core(localObject1);
  }
  
  public StackTraceElement getStackTraceElement()
  {
    return null;
  }
  
  public final Object getState$kotlinx_coroutines_core()
  {
    return this._state;
  }
  
  public <T> T getSuccessfulResult$kotlinx_coroutines_core(Object paramObject)
  {
    if ((paramObject instanceof CompletedContinuation)) {
      paramObject = ((CompletedContinuation)paramObject).result;
    }
    return (T)paramObject;
  }
  
  public void initCancellability()
  {
    DisposableHandle localDisposableHandle = installParentHandle();
    if (localDisposableHandle == null) {
      return;
    }
    if (isCompleted())
    {
      localDisposableHandle.dispose();
      this.parentHandle = ((DisposableHandle)NonDisposableHandle.INSTANCE);
    }
  }
  
  public void invokeOnCancellation(Function1<? super Throwable, Unit> paramFunction1)
  {
    CancelHandler localCancelHandler = makeCancelHandler(paramFunction1);
    for (;;)
    {
      Object localObject = this._state;
      if ((localObject instanceof Active))
      {
        if (!AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_state$FU, this, localObject, localCancelHandler)) {}
      }
      else if ((localObject instanceof CancelHandler))
      {
        multipleHandlersError(paramFunction1, localObject);
      }
      else
      {
        if ((localObject instanceof CompletedExceptionally))
        {
          if (!((CompletedExceptionally)localObject).makeHandled()) {
            multipleHandlersError(paramFunction1, localObject);
          }
          if ((localObject instanceof CancelledContinuation))
          {
            boolean bool = localObject instanceof CompletedExceptionally;
            localCancelHandler = null;
            if (bool) {
              localObject = (CompletedExceptionally)localObject;
            } else {
              localObject = null;
            }
            if (localObject == null) {
              localObject = localCancelHandler;
            } else {
              localObject = ((CompletedExceptionally)localObject).cause;
            }
            callCancelHandler(paramFunction1, (Throwable)localObject);
          }
          return;
        }
        CompletedContinuation localCompletedContinuation;
        if ((localObject instanceof CompletedContinuation))
        {
          if (((CompletedContinuation)localObject).cancelHandler != null) {
            multipleHandlersError(paramFunction1, localObject);
          }
          if ((localCancelHandler instanceof BeforeResumeCancelHandler)) {
            return;
          }
          if (((CompletedContinuation)localObject).getCancelled())
          {
            callCancelHandler(paramFunction1, ((CompletedContinuation)localObject).cancelCause);
            return;
          }
          localCompletedContinuation = CompletedContinuation.copy$default((CompletedContinuation)localObject, null, localCancelHandler, null, null, null, 29, null);
          if (!AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_state$FU, this, localObject, localCompletedContinuation)) {}
        }
        else
        {
          if ((localCancelHandler instanceof BeforeResumeCancelHandler)) {
            return;
          }
          localCompletedContinuation = new CompletedContinuation(localObject, localCancelHandler, null, null, null, 28, null);
          if (AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_state$FU, this, localObject, localCompletedContinuation)) {
            return;
          }
        }
      }
    }
  }
  
  public boolean isActive()
  {
    return getState$kotlinx_coroutines_core() instanceof NotCompleted;
  }
  
  public boolean isCancelled()
  {
    return getState$kotlinx_coroutines_core() instanceof CancelledContinuation;
  }
  
  public boolean isCompleted()
  {
    return getState$kotlinx_coroutines_core() instanceof NotCompleted ^ true;
  }
  
  protected String nameString()
  {
    return "CancellableContinuation";
  }
  
  public final void parentCancelled$kotlinx_coroutines_core(Throwable paramThrowable)
  {
    if (cancelLater(paramThrowable)) {
      return;
    }
    cancel(paramThrowable);
    detachChildIfNonResuable();
  }
  
  public final boolean resetStateReusable()
  {
    int i;
    if (DebugKt.getASSERTIONS_ENABLED())
    {
      if (this.resumeMode == 2) {
        i = 1;
      } else {
        i = 0;
      }
      if (i == 0) {
        throw new AssertionError();
      }
    }
    if (DebugKt.getASSERTIONS_ENABLED())
    {
      if (this.parentHandle != NonDisposableHandle.INSTANCE) {
        i = 1;
      } else {
        i = 0;
      }
      if (i == 0) {
        throw new AssertionError();
      }
    }
    Object localObject = this._state;
    if ((DebugKt.getASSERTIONS_ENABLED()) && (!(localObject instanceof NotCompleted ^ true))) {
      throw new AssertionError();
    }
    if (((localObject instanceof CompletedContinuation)) && (((CompletedContinuation)localObject).idempotentResume != null))
    {
      detachChild$kotlinx_coroutines_core();
      return false;
    }
    this._decision = 0;
    this._state = Active.INSTANCE;
    return true;
  }
  
  public void resume(T paramT, Function1<? super Throwable, Unit> paramFunction1)
  {
    resumeImpl(paramT, this.resumeMode, paramFunction1);
  }
  
  public void resumeUndispatched(CoroutineDispatcher paramCoroutineDispatcher, T paramT)
  {
    Object localObject1 = this.delegate;
    boolean bool = localObject1 instanceof DispatchedContinuation;
    Object localObject2 = null;
    if (bool) {
      localObject1 = (DispatchedContinuation)localObject1;
    } else {
      localObject1 = null;
    }
    if (localObject1 == null) {
      localObject1 = localObject2;
    } else {
      localObject1 = ((DispatchedContinuation)localObject1).dispatcher;
    }
    int i;
    if (localObject1 == paramCoroutineDispatcher) {
      i = 4;
    } else {
      i = this.resumeMode;
    }
    resumeImpl$default(this, paramT, i, null, 4, null);
  }
  
  public void resumeUndispatchedWithException(CoroutineDispatcher paramCoroutineDispatcher, Throwable paramThrowable)
  {
    Object localObject1 = this.delegate;
    boolean bool = localObject1 instanceof DispatchedContinuation;
    Object localObject2 = null;
    if (bool) {
      localObject1 = (DispatchedContinuation)localObject1;
    } else {
      localObject1 = null;
    }
    CompletedExceptionally localCompletedExceptionally = new CompletedExceptionally(paramThrowable, false, 2, null);
    if (localObject1 == null) {
      paramThrowable = (Throwable)localObject2;
    } else {
      paramThrowable = ((DispatchedContinuation)localObject1).dispatcher;
    }
    int i;
    if (paramThrowable == paramCoroutineDispatcher) {
      i = 4;
    } else {
      i = this.resumeMode;
    }
    resumeImpl$default(this, localCompletedExceptionally, i, null, 4, null);
  }
  
  public void resumeWith(Object paramObject)
  {
    resumeImpl$default(this, CompletionStateKt.toState(paramObject, (CancellableContinuation)this), this.resumeMode, null, 4, null);
  }
  
  public Object takeState$kotlinx_coroutines_core()
  {
    return getState$kotlinx_coroutines_core();
  }
  
  public String toString()
  {
    Object localObject2 = new StringBuilder().append(nameString()).append('(');
    Object localObject1 = DebugStringsKt.toDebugString(this.delegate);
    Log5ECF72.a(localObject1);
    LogE84000.a(localObject1);
    Log229316.a(localObject1);
    localObject1 = ((StringBuilder)localObject2).append((String)localObject1).append("){").append(getStateDebugRepresentation()).append("}@");
    localObject2 = DebugStringsKt.getHexAddress(this);
    Log5ECF72.a(localObject2);
    LogE84000.a(localObject2);
    Log229316.a(localObject2);
    return (String)localObject2;
  }
  
  public Object tryResume(T paramT, Object paramObject)
  {
    return tryResumeImpl(paramT, paramObject, null);
  }
  
  public Object tryResume(T paramT, Object paramObject, Function1<? super Throwable, Unit> paramFunction1)
  {
    return tryResumeImpl(paramT, paramObject, paramFunction1);
  }
  
  public Object tryResumeWithException(Throwable paramThrowable)
  {
    return tryResumeImpl(new CompletedExceptionally(paramThrowable, false, 2, null), null, null);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/CancellableContinuationImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */