package kotlinx.coroutines.internal;

import androidx.concurrent.futures.AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.Result.Companion;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CompletedWithCancellation;
import kotlinx.coroutines.CompletionStateKt;
import kotlinx.coroutines.CoroutineContextKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.DispatchedTask;
import kotlinx.coroutines.EventLoop;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.ThreadLocalEventLoop;
import kotlinx.coroutines.UndispatchedCoroutine;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000\001\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\003\n\002\020\002\n\002\b\002\n\002\020\000\n\000\n\002\020\003\n\002\b\004\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\005\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\005\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\r\n\002\020\016\n\002\b\002\n\002\030\002\n\002\b\005\n\002\030\002\n\002\030\002\n\002\b\016\n\002\030\002\b\000\030\000*\006\b\000\020\001 \0002\b\022\004\022\0028\0000O2\0060?j\002`@2\b\022\004\022\0028\0000\004B\035\022\006\020\003\032\0020\002\022\f\020\005\032\b\022\004\022\0028\0000\004¢\006\004\b\006\020\007J\r\020\t\032\0020\b¢\006\004\b\t\020\nJ!\020\021\032\0020\b2\b\020\f\032\004\030\0010\0132\006\020\016\032\0020\rH\020¢\006\004\b\017\020\020J\025\020\023\032\n\022\004\022\0028\000\030\0010\022¢\006\004\b\023\020\024J\037\020\032\032\0020\b2\006\020\026\032\0020\0252\006\020\027\032\0028\000H\000¢\006\004\b\030\020\031J\027\020\035\032\n\030\0010\033j\004\030\001`\034H\026¢\006\004\b\035\020\036J\r\020 \032\0020\037¢\006\004\b \020!J\025\020\"\032\0020\0372\006\020\016\032\0020\r¢\006\004\b\"\020#J\r\020$\032\0020\b¢\006\004\b$\020\nJH\020+\032\0020\b2\f\020&\032\b\022\004\022\0028\0000%2%\b\b\020*\032\037\022\023\022\0210\r¢\006\f\b(\022\b\b)\022\004\b\b(\016\022\004\022\0020\b\030\0010'H\bø\001\000¢\006\004\b+\020,J\032\020.\032\0020\0372\b\020-\032\004\030\0010\013H\b¢\006\004\b.\020/J!\0200\032\0020\b2\f\020&\032\b\022\004\022\0028\0000%H\bø\001\000¢\006\004\b0\0201J \0202\032\0020\b2\f\020&\032\b\022\004\022\0028\0000%H\026ø\001\000¢\006\004\b2\0201J\021\0205\032\004\030\0010\013H\020¢\006\004\b3\0204J\017\0207\032\00206H\026¢\006\004\b7\0208J\033\020:\032\004\030\0010\r2\n\020\005\032\006\022\002\b\00309¢\006\004\b:\020;R\036\020<\032\004\030\0010\0138\000@\000X\016¢\006\f\n\004\b<\020=\022\004\b>\020\nR\034\020C\032\n\030\0010?j\004\030\001`@8VX\004¢\006\006\032\004\bA\020BR\024\020\026\032\0020\0258\026X\005¢\006\006\032\004\bD\020ER\032\020\005\032\b\022\004\022\0028\0000\0048\006X\004¢\006\006\n\004\b\005\020FR\024\020G\032\0020\0138\000X\004¢\006\006\n\004\bG\020=R\032\020J\032\b\022\004\022\0028\0000\0048PX\004¢\006\006\032\004\bH\020IR\024\020\003\032\0020\0028\006X\004¢\006\006\n\004\b\003\020KR\032\020M\032\b\022\002\b\003\030\0010\0228BX\004¢\006\006\032\004\bL\020\024\002\004\n\002\b\031¨\006N"}, d2={"Lkotlinx/coroutines/internal/DispatchedContinuation;", "T", "Lkotlinx/coroutines/CoroutineDispatcher;", "dispatcher", "Lkotlin/coroutines/Continuation;", "continuation", "<init>", "(Lkotlinx/coroutines/CoroutineDispatcher;Lkotlin/coroutines/Continuation;)V", "", "awaitReusability", "()V", "", "takenState", "", "cause", "cancelCompletedResult$kotlinx_coroutines_core", "(Ljava/lang/Object;Ljava/lang/Throwable;)V", "cancelCompletedResult", "Lkotlinx/coroutines/CancellableContinuationImpl;", "claimReusableCancellableContinuation", "()Lkotlinx/coroutines/CancellableContinuationImpl;", "Lkotlin/coroutines/CoroutineContext;", "context", "value", "dispatchYield$kotlinx_coroutines_core", "(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;)V", "dispatchYield", "Ljava/lang/StackTraceElement;", "Lkotlinx/coroutines/internal/StackTraceElement;", "getStackTraceElement", "()Ljava/lang/StackTraceElement;", "", "isReusable", "()Z", "postponeCancellation", "(Ljava/lang/Throwable;)Z", "release", "Lkotlin/Result;", "result", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "onCancellation", "resumeCancellableWith", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)V", "state", "resumeCancelled", "(Ljava/lang/Object;)Z", "resumeUndispatchedWith", "(Ljava/lang/Object;)V", "resumeWith", "takeState$kotlinx_coroutines_core", "()Ljava/lang/Object;", "takeState", "", "toString", "()Ljava/lang/String;", "Lkotlinx/coroutines/CancellableContinuation;", "tryReleaseClaimedContinuation", "(Lkotlinx/coroutines/CancellableContinuation;)Ljava/lang/Throwable;", "_state", "Ljava/lang/Object;", "get_state$kotlinx_coroutines_core$annotations", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "Lkotlinx/coroutines/internal/CoroutineStackFrame;", "getCallerFrame", "()Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "callerFrame", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "Lkotlin/coroutines/Continuation;", "countOrElement", "getDelegate$kotlinx_coroutines_core", "()Lkotlin/coroutines/Continuation;", "delegate", "Lkotlinx/coroutines/CoroutineDispatcher;", "getReusableCancellableContinuation", "reusableCancellableContinuation", "kotlinx-coroutines-core", "Lkotlinx/coroutines/DispatchedTask;"}, k=1, mv={1, 6, 0}, xi=48)
public final class DispatchedContinuation<T>
  extends DispatchedTask<T>
  implements CoroutineStackFrame, Continuation<T>
{
  private static final AtomicReferenceFieldUpdater _reusableCancellableContinuation$FU = AtomicReferenceFieldUpdater.newUpdater(DispatchedContinuation.class, Object.class, "_reusableCancellableContinuation");
  private volatile Object _reusableCancellableContinuation;
  public Object _state;
  public final Continuation<T> continuation;
  public final Object countOrElement;
  public final CoroutineDispatcher dispatcher;
  
  public DispatchedContinuation(CoroutineDispatcher paramCoroutineDispatcher, Continuation<? super T> paramContinuation)
  {
    super(-1);
    this.dispatcher = paramCoroutineDispatcher;
    this.continuation = paramContinuation;
    this._state = DispatchedContinuationKt.access$getUNDEFINED$p();
    this.countOrElement = ThreadContextKt.threadContextElements(getContext());
    this._reusableCancellableContinuation = null;
  }
  
  private final CancellableContinuationImpl<?> getReusableCancellableContinuation()
  {
    Object localObject = this._reusableCancellableContinuation;
    if ((localObject instanceof CancellableContinuationImpl)) {
      localObject = (CancellableContinuationImpl)localObject;
    } else {
      localObject = null;
    }
    return (CancellableContinuationImpl<?>)localObject;
  }
  
  public final void awaitReusability()
  {
    for (;;)
    {
      if (this._reusableCancellableContinuation != DispatchedContinuationKt.REUSABLE_CLAIMED) {
        return;
      }
    }
  }
  
  public void cancelCompletedResult$kotlinx_coroutines_core(Object paramObject, Throwable paramThrowable)
  {
    if ((paramObject instanceof CompletedWithCancellation)) {
      ((CompletedWithCancellation)paramObject).onCancellation.invoke(paramThrowable);
    }
  }
  
  public final CancellableContinuationImpl<T> claimReusableCancellableContinuation()
  {
    do
    {
      localObject = this._reusableCancellableContinuation;
      if (localObject == null)
      {
        this._reusableCancellableContinuation = DispatchedContinuationKt.REUSABLE_CLAIMED;
        return null;
      }
      if ((localObject instanceof CancellableContinuationImpl))
      {
        if (!AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_reusableCancellableContinuation$FU, this, localObject, DispatchedContinuationKt.REUSABLE_CLAIMED)) {
          break;
        }
        return (CancellableContinuationImpl)localObject;
      }
    } while ((localObject != DispatchedContinuationKt.REUSABLE_CLAIMED) && ((localObject instanceof Throwable)));
    Object localObject = Intrinsics.stringPlus("Inconsistent state ", localObject);
    Log5ECF72.a(localObject);
    LogE84000.a(localObject);
    Log229316.a(localObject);
    throw new IllegalStateException(localObject.toString());
  }
  
  public final void dispatchYield$kotlinx_coroutines_core(CoroutineContext paramCoroutineContext, T paramT)
  {
    this._state = paramT;
    this.resumeMode = 1;
    this.dispatcher.dispatchYield(paramCoroutineContext, (Runnable)this);
  }
  
  public CoroutineStackFrame getCallerFrame()
  {
    Object localObject = this.continuation;
    if ((localObject instanceof CoroutineStackFrame)) {
      localObject = (CoroutineStackFrame)localObject;
    } else {
      localObject = null;
    }
    return (CoroutineStackFrame)localObject;
  }
  
  public CoroutineContext getContext()
  {
    return this.continuation.getContext();
  }
  
  public Continuation<T> getDelegate$kotlinx_coroutines_core()
  {
    return (Continuation)this;
  }
  
  public StackTraceElement getStackTraceElement()
  {
    return null;
  }
  
  public final boolean isReusable()
  {
    boolean bool;
    if (this._reusableCancellableContinuation != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final boolean postponeCancellation(Throwable paramThrowable)
  {
    for (;;)
    {
      Object localObject = this._reusableCancellableContinuation;
      if (Intrinsics.areEqual(localObject, DispatchedContinuationKt.REUSABLE_CLAIMED))
      {
        if (AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_reusableCancellableContinuation$FU, this, DispatchedContinuationKt.REUSABLE_CLAIMED, paramThrowable)) {
          return true;
        }
      }
      else
      {
        if ((localObject instanceof Throwable)) {
          return true;
        }
        if (AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_reusableCancellableContinuation$FU, this, localObject, null)) {
          return false;
        }
      }
    }
  }
  
  public final void release()
  {
    awaitReusability();
    CancellableContinuationImpl localCancellableContinuationImpl = getReusableCancellableContinuation();
    if (localCancellableContinuationImpl != null) {
      localCancellableContinuationImpl.detachChild$kotlinx_coroutines_core();
    }
  }
  
  /* Error */
  public final void resumeCancellableWith(Object paramObject, Function1<? super Throwable, Unit> paramFunction1)
  {
    // Byte code:
    //   0: aload_1
    //   1: aload_2
    //   2: invokestatic 225	kotlinx/coroutines/CompletionStateKt:toState	(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;
    //   5: astore 7
    //   7: aload_0
    //   8: getfield 117	kotlinx/coroutines/internal/DispatchedContinuation:dispatcher	Lkotlinx/coroutines/CoroutineDispatcher;
    //   11: aload_0
    //   12: invokevirtual 129	kotlinx/coroutines/internal/DispatchedContinuation:getContext	()Lkotlin/coroutines/CoroutineContext;
    //   15: invokevirtual 229	kotlinx/coroutines/CoroutineDispatcher:isDispatchNeeded	(Lkotlin/coroutines/CoroutineContext;)Z
    //   18: ifeq +32 -> 50
    //   21: aload_0
    //   22: aload 7
    //   24: putfield 127	kotlinx/coroutines/internal/DispatchedContinuation:_state	Ljava/lang/Object;
    //   27: aload_0
    //   28: iconst_1
    //   29: putfield 198	kotlinx/coroutines/internal/DispatchedContinuation:resumeMode	I
    //   32: aload_0
    //   33: getfield 117	kotlinx/coroutines/internal/DispatchedContinuation:dispatcher	Lkotlinx/coroutines/CoroutineDispatcher;
    //   36: aload_0
    //   37: invokevirtual 129	kotlinx/coroutines/internal/DispatchedContinuation:getContext	()Lkotlin/coroutines/CoroutineContext;
    //   40: aload_0
    //   41: checkcast 200	java/lang/Runnable
    //   44: invokevirtual 232	kotlinx/coroutines/CoroutineDispatcher:dispatch	(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Runnable;)V
    //   47: goto +344 -> 391
    //   50: invokestatic 237	kotlinx/coroutines/DebugKt:getASSERTIONS_ENABLED	()Z
    //   53: ifeq +3 -> 56
    //   56: getstatic 243	kotlinx/coroutines/ThreadLocalEventLoop:INSTANCE	Lkotlinx/coroutines/ThreadLocalEventLoop;
    //   59: invokevirtual 247	kotlinx/coroutines/ThreadLocalEventLoop:getEventLoop$kotlinx_coroutines_core	()Lkotlinx/coroutines/EventLoop;
    //   62: astore 5
    //   64: aload 5
    //   66: invokevirtual 252	kotlinx/coroutines/EventLoop:isUnconfinedLoopActive	()Z
    //   69: ifeq +26 -> 95
    //   72: aload_0
    //   73: aload 7
    //   75: putfield 127	kotlinx/coroutines/internal/DispatchedContinuation:_state	Ljava/lang/Object;
    //   78: aload_0
    //   79: iconst_1
    //   80: putfield 198	kotlinx/coroutines/internal/DispatchedContinuation:resumeMode	I
    //   83: aload 5
    //   85: aload_0
    //   86: checkcast 5	kotlinx/coroutines/DispatchedTask
    //   89: invokevirtual 256	kotlinx/coroutines/EventLoop:dispatchUnconfined	(Lkotlinx/coroutines/DispatchedTask;)V
    //   92: goto +299 -> 391
    //   95: aload_0
    //   96: checkcast 5	kotlinx/coroutines/DispatchedTask
    //   99: astore 6
    //   101: aload 5
    //   103: iconst_1
    //   104: invokevirtual 260	kotlinx/coroutines/EventLoop:incrementUseCount	(Z)V
    //   107: aload_0
    //   108: invokevirtual 129	kotlinx/coroutines/internal/DispatchedContinuation:getContext	()Lkotlin/coroutines/CoroutineContext;
    //   111: getstatic 266	kotlinx/coroutines/Job:Key	Lkotlinx/coroutines/Job$Key;
    //   114: checkcast 268	kotlin/coroutines/CoroutineContext$Key
    //   117: invokeinterface 274 2 0
    //   122: checkcast 262	kotlinx/coroutines/Job
    //   125: astore_2
    //   126: aload_2
    //   127: ifnull +66 -> 193
    //   130: aload_2
    //   131: invokeinterface 277 1 0
    //   136: ifne +57 -> 193
    //   139: aload_2
    //   140: invokeinterface 281 1 0
    //   145: astore_2
    //   146: aload_0
    //   147: aload 7
    //   149: aload_2
    //   150: checkcast 167	java/lang/Throwable
    //   153: invokevirtual 283	kotlinx/coroutines/internal/DispatchedContinuation:cancelCompletedResult$kotlinx_coroutines_core	(Ljava/lang/Object;Ljava/lang/Throwable;)V
    //   156: aload_0
    //   157: checkcast 9	kotlin/coroutines/Continuation
    //   160: astore 8
    //   162: getstatic 289	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   165: astore 7
    //   167: aload 8
    //   169: aload_2
    //   170: checkcast 167	java/lang/Throwable
    //   173: invokestatic 295	kotlin/ResultKt:createFailure	(Ljava/lang/Throwable;)Ljava/lang/Object;
    //   176: invokestatic 298	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   179: invokeinterface 300 2 0
    //   184: iconst_1
    //   185: istore_3
    //   186: goto +9 -> 195
    //   189: astore_1
    //   190: goto +180 -> 370
    //   193: iconst_0
    //   194: istore_3
    //   195: iload_3
    //   196: ifne +150 -> 346
    //   199: aload_0
    //   200: getfield 119	kotlinx/coroutines/internal/DispatchedContinuation:continuation	Lkotlin/coroutines/Continuation;
    //   203: astore_2
    //   204: aload_0
    //   205: getfield 137	kotlinx/coroutines/internal/DispatchedContinuation:countOrElement	Ljava/lang/Object;
    //   208: astore 8
    //   210: aload_2
    //   211: invokeinterface 206 1 0
    //   216: astore 7
    //   218: aload 7
    //   220: aload 8
    //   222: invokestatic 304	kotlinx/coroutines/internal/ThreadContextKt:updateThreadContext	(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;)Ljava/lang/Object;
    //   225: astore 8
    //   227: getstatic 307	kotlinx/coroutines/internal/ThreadContextKt:NO_THREAD_ELEMENTS	Lkotlinx/coroutines/internal/Symbol;
    //   230: astore 9
    //   232: aload 8
    //   234: aload 9
    //   236: if_acmpeq +19 -> 255
    //   239: aload_2
    //   240: aload 7
    //   242: aload 8
    //   244: invokestatic 313	kotlinx/coroutines/CoroutineContextKt:updateUndispatchedCompletion	(Lkotlin/coroutines/Continuation;Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;)Lkotlinx/coroutines/UndispatchedCoroutine;
    //   247: astore_2
    //   248: goto +14 -> 262
    //   251: astore_1
    //   252: goto +118 -> 370
    //   255: aconst_null
    //   256: checkcast 315	kotlinx/coroutines/UndispatchedCoroutine
    //   259: astore_2
    //   260: aconst_null
    //   261: astore_2
    //   262: aload_0
    //   263: getfield 119	kotlinx/coroutines/internal/DispatchedContinuation:continuation	Lkotlin/coroutines/Continuation;
    //   266: astore 9
    //   268: aload 9
    //   270: aload_1
    //   271: invokeinterface 300 2 0
    //   276: getstatic 320	kotlin/Unit:INSTANCE	Lkotlin/Unit;
    //   279: astore_1
    //   280: iconst_1
    //   281: invokestatic 325	kotlin/jvm/internal/InlineMarker:finallyStart	(I)V
    //   284: aload_2
    //   285: ifnull +10 -> 295
    //   288: aload_2
    //   289: invokevirtual 328	kotlinx/coroutines/UndispatchedCoroutine:clearThreadContext	()Z
    //   292: ifeq +10 -> 302
    //   295: aload 7
    //   297: aload 8
    //   299: invokestatic 331	kotlinx/coroutines/internal/ThreadContextKt:restoreThreadContext	(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;)V
    //   302: iconst_1
    //   303: invokestatic 334	kotlin/jvm/internal/InlineMarker:finallyEnd	(I)V
    //   306: goto +40 -> 346
    //   309: astore_1
    //   310: goto +4 -> 314
    //   313: astore_1
    //   314: iconst_1
    //   315: invokestatic 325	kotlin/jvm/internal/InlineMarker:finallyStart	(I)V
    //   318: aload_2
    //   319: ifnull +10 -> 329
    //   322: aload_2
    //   323: invokevirtual 328	kotlinx/coroutines/UndispatchedCoroutine:clearThreadContext	()Z
    //   326: ifeq +10 -> 336
    //   329: aload 7
    //   331: aload 8
    //   333: invokestatic 331	kotlinx/coroutines/internal/ThreadContextKt:restoreThreadContext	(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;)V
    //   336: iconst_1
    //   337: invokestatic 334	kotlin/jvm/internal/InlineMarker:finallyEnd	(I)V
    //   340: aload_1
    //   341: athrow
    //   342: astore_1
    //   343: goto +27 -> 370
    //   346: aload 5
    //   348: invokevirtual 337	kotlinx/coroutines/EventLoop:processUnconfinedEvent	()Z
    //   351: istore 4
    //   353: iload 4
    //   355: ifne -9 -> 346
    //   358: iconst_1
    //   359: invokestatic 325	kotlin/jvm/internal/InlineMarker:finallyStart	(I)V
    //   362: goto +19 -> 381
    //   365: astore_1
    //   366: goto +4 -> 370
    //   369: astore_1
    //   370: aload 6
    //   372: aload_1
    //   373: aconst_null
    //   374: invokevirtual 341	kotlinx/coroutines/DispatchedTask:handleFatalException	(Ljava/lang/Throwable;Ljava/lang/Throwable;)V
    //   377: iconst_1
    //   378: invokestatic 325	kotlin/jvm/internal/InlineMarker:finallyStart	(I)V
    //   381: aload 5
    //   383: iconst_1
    //   384: invokevirtual 344	kotlinx/coroutines/EventLoop:decrementUseCount	(Z)V
    //   387: iconst_1
    //   388: invokestatic 334	kotlin/jvm/internal/InlineMarker:finallyEnd	(I)V
    //   391: return
    //   392: astore_1
    //   393: iconst_1
    //   394: invokestatic 325	kotlin/jvm/internal/InlineMarker:finallyStart	(I)V
    //   397: aload 5
    //   399: iconst_1
    //   400: invokevirtual 344	kotlinx/coroutines/EventLoop:decrementUseCount	(Z)V
    //   403: iconst_1
    //   404: invokestatic 334	kotlin/jvm/internal/InlineMarker:finallyEnd	(I)V
    //   407: aload_1
    //   408: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	409	0	this	DispatchedContinuation
    //   0	409	1	paramObject	Object
    //   0	409	2	paramFunction1	Function1<? super Throwable, Unit>
    //   185	11	3	i	int
    //   351	3	4	bool	boolean
    //   62	336	5	localEventLoop	EventLoop
    //   99	272	6	localDispatchedTask	DispatchedTask
    //   5	325	7	localObject1	Object
    //   160	172	8	localObject2	Object
    //   230	39	9	localObject3	Object
    // Exception table:
    //   from	to	target	type
    //   130	184	189	finally
    //   239	248	251	finally
    //   268	276	309	finally
    //   276	280	309	finally
    //   262	268	313	finally
    //   255	260	342	finally
    //   280	284	365	finally
    //   288	295	365	finally
    //   295	302	365	finally
    //   302	306	365	finally
    //   314	318	365	finally
    //   322	329	365	finally
    //   329	336	365	finally
    //   336	342	365	finally
    //   346	353	365	finally
    //   107	126	369	finally
    //   199	232	369	finally
    //   370	377	392	finally
  }
  
  public final boolean resumeCancelled(Object paramObject)
  {
    Object localObject = (Job)getContext().get((CoroutineContext.Key)Job.Key);
    if ((localObject != null) && (!((Job)localObject).isActive()))
    {
      localObject = ((Job)localObject).getCancellationException();
      cancelCompletedResult$kotlinx_coroutines_core(paramObject, (Throwable)localObject);
      paramObject = (Continuation)this;
      Result.Companion localCompanion = Result.Companion;
      ((Continuation)paramObject).resumeWith(Result.constructor-impl(ResultKt.createFailure((Throwable)localObject)));
      return true;
    }
    return false;
  }
  
  public final void resumeUndispatchedWith(Object paramObject)
  {
    Object localObject1 = this.continuation;
    Object localObject2 = this.countOrElement;
    CoroutineContext localCoroutineContext = ((Continuation)localObject1).getContext();
    localObject2 = ThreadContextKt.updateThreadContext(localCoroutineContext, localObject2);
    if (localObject2 != ThreadContextKt.NO_THREAD_ELEMENTS)
    {
      localObject1 = CoroutineContextKt.updateUndispatchedCompletion((Continuation)localObject1, localCoroutineContext, localObject2);
    }
    else
    {
      localObject1 = null;
      UndispatchedCoroutine localUndispatchedCoroutine = (UndispatchedCoroutine)null;
    }
    try
    {
      this.continuation.resumeWith(paramObject);
      paramObject = Unit.INSTANCE;
      return;
    }
    finally
    {
      InlineMarker.finallyStart(1);
      if ((localObject1 == null) || (((UndispatchedCoroutine)localObject1).clearThreadContext())) {
        ThreadContextKt.restoreThreadContext(localCoroutineContext, localObject2);
      }
      InlineMarker.finallyEnd(1);
    }
  }
  
  public void resumeWith(Object paramObject)
  {
    localObject1 = this.continuation.getContext();
    localObject2 = CompletionStateKt.toState$default(paramObject, null, 1, null);
    if (this.dispatcher.isDispatchNeeded((CoroutineContext)localObject1))
    {
      this._state = localObject2;
      this.resumeMode = 0;
      this.dispatcher.dispatch((CoroutineContext)localObject1, (Runnable)this);
    }
    else
    {
      if (DebugKt.getASSERTIONS_ENABLED()) {}
      localObject1 = ThreadLocalEventLoop.INSTANCE.getEventLoop$kotlinx_coroutines_core();
      if (((EventLoop)localObject1).isUnconfinedLoopActive())
      {
        this._state = localObject2;
        this.resumeMode = 0;
        ((EventLoop)localObject1).dispatchUnconfined((DispatchedTask)this);
      }
      else
      {
        localObject2 = (DispatchedTask)this;
        ((EventLoop)localObject1).incrementUseCount(true);
        try
        {
          CoroutineContext localCoroutineContext = getContext();
          Object localObject3 = ThreadContextKt.updateThreadContext(localCoroutineContext, this.countOrElement);
          try
          {
            this.continuation.resumeWith(paramObject);
            paramObject = Unit.INSTANCE;
            ThreadContextKt.restoreThreadContext(localCoroutineContext, localObject3);
            while (((EventLoop)localObject1).processUnconfinedEvent()) {}
          }
          finally
          {
            ThreadContextKt.restoreThreadContext(localCoroutineContext, localObject3);
          }
          try
          {
            ((DispatchedTask)localObject2).handleFatalException((Throwable)paramObject, null);
            return;
          }
          finally
          {
            ((EventLoop)localObject1).decrementUseCount(true);
          }
        }
        finally {}
      }
    }
  }
  
  public Object takeState$kotlinx_coroutines_core()
  {
    Object localObject = this._state;
    if (DebugKt.getASSERTIONS_ENABLED())
    {
      int i;
      if (localObject != DispatchedContinuationKt.access$getUNDEFINED$p()) {
        i = 1;
      } else {
        i = 0;
      }
      if (i == 0) {
        throw new AssertionError();
      }
    }
    this._state = DispatchedContinuationKt.access$getUNDEFINED$p();
    return localObject;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder().append("DispatchedContinuation[").append(this.dispatcher).append(", ");
    String str = DebugStringsKt.toDebugString(this.continuation);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str + ']';
  }
  
  public final Throwable tryReleaseClaimedContinuation(CancellableContinuation<?> paramCancellableContinuation)
  {
    Object localObject;
    for (;;)
    {
      localObject = this._reusableCancellableContinuation;
      if (localObject != DispatchedContinuationKt.REUSABLE_CLAIMED) {
        break;
      }
      if (AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_reusableCancellableContinuation$FU, this, DispatchedContinuationKt.REUSABLE_CLAIMED, paramCancellableContinuation)) {
        return null;
      }
    }
    if ((localObject instanceof Throwable))
    {
      if (AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_reusableCancellableContinuation$FU, this, localObject, null)) {
        return (Throwable)localObject;
      }
      throw new IllegalArgumentException("Failed requirement.".toString());
    }
    paramCancellableContinuation = Intrinsics.stringPlus("Inconsistent state ", localObject);
    Log5ECF72.a(paramCancellableContinuation);
    LogE84000.a(paramCancellableContinuation);
    Log229316.a(paramCancellableContinuation);
    throw new IllegalStateException(paramCancellableContinuation.toString());
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/internal/DispatchedContinuation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */