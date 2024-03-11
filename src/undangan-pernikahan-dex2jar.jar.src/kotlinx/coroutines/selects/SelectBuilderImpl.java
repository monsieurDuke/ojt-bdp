package kotlinx.coroutines.selects;

import androidx.concurrent.futures.AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.Result.Companion;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuationImplKt;
import kotlinx.coroutines.CompletedExceptionally;
import kotlinx.coroutines.CompletionStateKt;
import kotlinx.coroutines.CoroutineExceptionHandlerKt;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.Delay;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.Job.DefaultImpls;
import kotlinx.coroutines.JobCancellingNode;
import kotlinx.coroutines.JobSupport;
import kotlinx.coroutines.internal.AtomicDesc;
import kotlinx.coroutines.internal.AtomicKt;
import kotlinx.coroutines.internal.AtomicOp;
import kotlinx.coroutines.internal.LockFreeLinkedListHead;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;
import kotlinx.coroutines.internal.LockFreeLinkedListNode.PrepareOp;
import kotlinx.coroutines.internal.OpDescriptor;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;
import kotlinx.coroutines.intrinsics.CancellableKt;
import kotlinx.coroutines.intrinsics.UndispatchedKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000®\001\n\002\030\002\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\020\002\n\002\b\004\n\002\030\002\n\002\020\000\n\002\b\006\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\003\n\002\b\004\n\002\020\t\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\003\n\002\020\016\n\002\b\002\n\002\020\013\n\002\b\002\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\003\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\b\006\n\002\030\002\n\002\b\016\n\002\030\002\n\002\030\002\n\002\030\002\b\001\030\000*\006\b\000\020\001 \0002\0020Y2\b\022\004\022\0028\0000Z2\b\022\004\022\0028\0000[2\b\022\004\022\0028\0000\0022\0060Bj\002`C:\004TUVWB\025\022\f\020\003\032\b\022\004\022\0028\0000\002¢\006\004\b\004\020\005J\027\020\t\032\0020\b2\006\020\007\032\0020\006H\026¢\006\004\b\t\020\nJ\017\020\013\032\0020\bH\002¢\006\004\b\013\020\fJ.\020\021\032\0020\b2\016\020\017\032\n\022\006\022\004\030\0010\0160\r2\f\020\020\032\b\022\004\022\0020\b0\rH\b¢\006\004\b\021\020\022J\021\020\023\032\004\030\0010\016H\001¢\006\004\b\023\020\024J\027\020\027\032\n\030\0010\025j\004\030\001`\026H\026¢\006\004\b\027\020\030J\027\020\033\032\0020\b2\006\020\032\032\0020\031H\001¢\006\004\b\033\020\034J\017\020\035\032\0020\bH\002¢\006\004\b\035\020\fJ8\020!\032\0020\b2\006\020\037\032\0020\0362\034\020\020\032\030\b\001\022\n\022\b\022\004\022\0028\0000\002\022\006\022\004\030\0010\0160 H\026ø\001\000¢\006\004\b!\020\"J\031\020%\032\004\030\0010\0162\006\020$\032\0020#H\026¢\006\004\b%\020&J\027\020(\032\0020\b2\006\020'\032\0020\031H\026¢\006\004\b(\020\034J \020+\032\0020\b2\f\020*\032\b\022\004\022\0028\0000)H\026ø\001\000¢\006\004\b+\020,J\017\020.\032\0020-H\026¢\006\004\b.\020/J\017\0201\032\00200H\026¢\006\004\b1\0202J\033\0205\032\004\030\0010\0162\b\0204\032\004\030\00103H\026¢\006\004\b5\0206J5\0208\032\0020\b*\002072\034\020\020\032\030\b\001\022\n\022\b\022\004\022\0028\0000\002\022\006\022\004\030\0010\0160 H\002ø\001\000¢\006\004\b8\0209JG\0208\032\0020\b\"\004\b\001\020:*\b\022\004\022\0028\0010;2\"\020\020\032\036\b\001\022\004\022\0028\001\022\n\022\b\022\004\022\0028\0000\002\022\006\022\004\030\0010\0160<H\002ø\001\000¢\006\004\b8\020=J[\0208\032\0020\b\"\004\b\001\020>\"\004\b\002\020:*\016\022\004\022\0028\001\022\004\022\0028\0020?2\006\020@\032\0028\0012\"\020\020\032\036\b\001\022\004\022\0028\002\022\n\022\b\022\004\022\0028\0000\002\022\006\022\004\030\0010\0160<H\002ø\001\000¢\006\004\b8\020AR\034\020F\032\n\030\0010Bj\004\030\001`C8VX\004¢\006\006\032\004\bD\020ER\032\020I\032\b\022\004\022\0028\0000\0028VX\004¢\006\006\032\004\bG\020HR\024\020M\032\0020J8VX\004¢\006\006\032\004\bK\020LR\024\020N\032\002008VX\004¢\006\006\032\004\bN\0202R(\020R\032\004\030\0010\0062\b\020\017\032\004\030\0010\0068B@BX\016¢\006\f\032\004\bO\020P\"\004\bQ\020\nR\032\020\003\032\b\022\004\022\0028\0000\0028\002X\004¢\006\006\n\004\b\003\020S\002\004\n\002\b\031¨\006X"}, d2={"Lkotlinx/coroutines/selects/SelectBuilderImpl;", "R", "Lkotlin/coroutines/Continuation;", "uCont", "<init>", "(Lkotlin/coroutines/Continuation;)V", "Lkotlinx/coroutines/DisposableHandle;", "handle", "", "disposeOnSelect", "(Lkotlinx/coroutines/DisposableHandle;)V", "doAfterSelect", "()V", "Lkotlin/Function0;", "", "value", "block", "doResume", "(Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;)V", "getResult", "()Ljava/lang/Object;", "Ljava/lang/StackTraceElement;", "Lkotlinx/coroutines/internal/StackTraceElement;", "getStackTraceElement", "()Ljava/lang/StackTraceElement;", "", "e", "handleBuilderException", "(Ljava/lang/Throwable;)V", "initCancellability", "", "timeMillis", "Lkotlin/Function1;", "onTimeout", "(JLkotlin/jvm/functions/Function1;)V", "Lkotlinx/coroutines/internal/AtomicDesc;", "desc", "performAtomicTrySelect", "(Lkotlinx/coroutines/internal/AtomicDesc;)Ljava/lang/Object;", "exception", "resumeSelectWithException", "Lkotlin/Result;", "result", "resumeWith", "(Ljava/lang/Object;)V", "", "toString", "()Ljava/lang/String;", "", "trySelect", "()Z", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "otherOp", "trySelectOther", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;)Ljava/lang/Object;", "Lkotlinx/coroutines/selects/SelectClause0;", "invoke", "(Lkotlinx/coroutines/selects/SelectClause0;Lkotlin/jvm/functions/Function1;)V", "Q", "Lkotlinx/coroutines/selects/SelectClause1;", "Lkotlin/Function2;", "(Lkotlinx/coroutines/selects/SelectClause1;Lkotlin/jvm/functions/Function2;)V", "P", "Lkotlinx/coroutines/selects/SelectClause2;", "param", "(Lkotlinx/coroutines/selects/SelectClause2;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)V", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "Lkotlinx/coroutines/internal/CoroutineStackFrame;", "getCallerFrame", "()Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "callerFrame", "getCompletion", "()Lkotlin/coroutines/Continuation;", "completion", "Lkotlin/coroutines/CoroutineContext;", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "context", "isSelected", "getParentHandle", "()Lkotlinx/coroutines/DisposableHandle;", "setParentHandle", "parentHandle", "Lkotlin/coroutines/Continuation;", "AtomicSelectOp", "DisposeNode", "PairSelectOp", "SelectOnCancelling", "kotlinx-coroutines-core", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "Lkotlinx/coroutines/selects/SelectBuilder;", "Lkotlinx/coroutines/selects/SelectInstance;"}, k=1, mv={1, 6, 0}, xi=48)
public final class SelectBuilderImpl<R>
  extends LockFreeLinkedListHead
  implements SelectBuilder<R>, SelectInstance<R>, Continuation<R>, CoroutineStackFrame
{
  private static final AtomicReferenceFieldUpdater _result$FU = AtomicReferenceFieldUpdater.newUpdater(SelectBuilderImpl.class, Object.class, "_result");
  static final AtomicReferenceFieldUpdater _state$FU = AtomicReferenceFieldUpdater.newUpdater(SelectBuilderImpl.class, Object.class, "_state");
  private volatile Object _parentHandle;
  private volatile Object _result;
  volatile Object _state;
  private final Continuation<R> uCont;
  
  public SelectBuilderImpl(Continuation<? super R> paramContinuation)
  {
    this.uCont = paramContinuation;
    this._state = SelectKt.getNOT_SELECTED();
    this._result = SelectKt.access$getUNDECIDED$p();
    this._parentHandle = null;
  }
  
  private final void doAfterSelect()
  {
    Object localObject = getParentHandle();
    if (localObject != null) {
      ((DisposableHandle)localObject).dispose();
    }
    LockFreeLinkedListHead localLockFreeLinkedListHead = (LockFreeLinkedListHead)this;
    for (localObject = (LockFreeLinkedListNode)localLockFreeLinkedListHead.getNext(); !Intrinsics.areEqual(localObject, localLockFreeLinkedListHead); localObject = ((LockFreeLinkedListNode)localObject).getNextNode()) {
      if ((localObject instanceof DisposeNode)) {
        ((DisposeNode)localObject).handle.dispose();
      }
    }
  }
  
  private final void doResume(Function0<? extends Object> paramFunction0, Function0<Unit> paramFunction01)
  {
    if ((DebugKt.getASSERTIONS_ENABLED()) && (!isSelected())) {
      throw new AssertionError();
    }
    for (;;)
    {
      Object localObject = this._result;
      if (localObject == SelectKt.access$getUNDECIDED$p())
      {
        localObject = paramFunction0.invoke();
        if (!AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_result$FU, this, SelectKt.access$getUNDECIDED$p(), localObject)) {}
      }
      else
      {
        if (localObject != IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
          break;
        }
        if (AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_result$FU, this, IntrinsicsKt.getCOROUTINE_SUSPENDED(), SelectKt.access$getRESUMED$p()))
        {
          paramFunction01.invoke();
          return;
        }
      }
    }
    throw new IllegalStateException("Already resumed");
  }
  
  private final DisposableHandle getParentHandle()
  {
    return (DisposableHandle)this._parentHandle;
  }
  
  private final void initCancellability()
  {
    Object localObject = (Job)getContext().get((CoroutineContext.Key)Job.Key);
    if (localObject == null) {
      return;
    }
    localObject = Job.DefaultImpls.invokeOnCompletion$default((Job)localObject, true, false, (Function1)new SelectOnCancelling(), 2, null);
    setParentHandle((DisposableHandle)localObject);
    if (isSelected()) {
      ((DisposableHandle)localObject).dispose();
    }
  }
  
  private final void setParentHandle(DisposableHandle paramDisposableHandle)
  {
    this._parentHandle = paramDisposableHandle;
  }
  
  public void disposeOnSelect(DisposableHandle paramDisposableHandle)
  {
    DisposeNode localDisposeNode = new DisposeNode(paramDisposableHandle);
    if (!isSelected())
    {
      addLast((LockFreeLinkedListNode)localDisposeNode);
      if (!isSelected()) {
        return;
      }
    }
    paramDisposableHandle.dispose();
  }
  
  public CoroutineStackFrame getCallerFrame()
  {
    Object localObject = this.uCont;
    if ((localObject instanceof CoroutineStackFrame)) {
      localObject = (CoroutineStackFrame)localObject;
    } else {
      localObject = null;
    }
    return (CoroutineStackFrame)localObject;
  }
  
  public Continuation<R> getCompletion()
  {
    return (Continuation)this;
  }
  
  public CoroutineContext getContext()
  {
    return this.uCont.getContext();
  }
  
  public final Object getResult()
  {
    if (!isSelected()) {
      initCancellability();
    }
    Object localObject2 = this._result;
    Object localObject1 = localObject2;
    if (localObject2 == SelectKt.access$getUNDECIDED$p())
    {
      if (AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_result$FU, this, SelectKt.access$getUNDECIDED$p(), IntrinsicsKt.getCOROUTINE_SUSPENDED())) {
        return IntrinsicsKt.getCOROUTINE_SUSPENDED();
      }
      localObject1 = this._result;
    }
    if (localObject1 != SelectKt.access$getRESUMED$p())
    {
      if (!(localObject1 instanceof CompletedExceptionally)) {
        return localObject1;
      }
      throw ((CompletedExceptionally)localObject1).cause;
    }
    throw new IllegalStateException("Already resumed");
  }
  
  public StackTraceElement getStackTraceElement()
  {
    return null;
  }
  
  public final void handleBuilderException(Throwable paramThrowable)
  {
    Object localObject2;
    Object localObject1;
    if (trySelect())
    {
      localObject2 = (Continuation)this;
      localObject1 = Result.Companion;
      ((Continuation)localObject2).resumeWith(Result.constructor-impl(ResultKt.createFailure(paramThrowable)));
    }
    else if (!(paramThrowable instanceof CancellationException))
    {
      localObject1 = getResult();
      if ((localObject1 instanceof CompletedExceptionally))
      {
        localObject1 = ((CompletedExceptionally)localObject1).cause;
        if (DebugKt.getRECOVER_STACK_TRACES()) {
          localObject1 = StackTraceRecoveryKt.unwrapImpl((Throwable)localObject1);
        }
        if (!DebugKt.getRECOVER_STACK_TRACES()) {
          localObject2 = paramThrowable;
        } else {
          localObject2 = StackTraceRecoveryKt.unwrapImpl(paramThrowable);
        }
        if (localObject1 == localObject2) {}
      }
      else
      {
        CoroutineExceptionHandlerKt.handleCoroutineException(getContext(), paramThrowable);
      }
    }
  }
  
  public void invoke(SelectClause0 paramSelectClause0, Function1<? super Continuation<? super R>, ? extends Object> paramFunction1)
  {
    paramSelectClause0.registerSelectClause0((SelectInstance)this, paramFunction1);
  }
  
  public <Q> void invoke(SelectClause1<? extends Q> paramSelectClause1, Function2<? super Q, ? super Continuation<? super R>, ? extends Object> paramFunction2)
  {
    paramSelectClause1.registerSelectClause1((SelectInstance)this, paramFunction2);
  }
  
  public <P, Q> void invoke(SelectClause2<? super P, ? extends Q> paramSelectClause2, P paramP, Function2<? super Q, ? super Continuation<? super R>, ? extends Object> paramFunction2)
  {
    paramSelectClause2.registerSelectClause2((SelectInstance)this, paramP, paramFunction2);
  }
  
  public <P, Q> void invoke(SelectClause2<? super P, ? extends Q> paramSelectClause2, Function2<? super Q, ? super Continuation<? super R>, ? extends Object> paramFunction2)
  {
    SelectBuilder.DefaultImpls.invoke((SelectBuilder)this, paramSelectClause2, paramFunction2);
  }
  
  public boolean isSelected()
  {
    for (;;)
    {
      Object localObject = this._state;
      if (localObject == SelectKt.getNOT_SELECTED()) {
        return false;
      }
      if (!(localObject instanceof OpDescriptor)) {
        break;
      }
      ((OpDescriptor)localObject).perform(this);
    }
    return true;
  }
  
  public void onTimeout(long paramLong, final Function1<? super Continuation<? super R>, ? extends Object> paramFunction1)
  {
    if (paramLong <= 0L)
    {
      if (trySelect()) {
        UndispatchedKt.startCoroutineUnintercepted(paramFunction1, getCompletion());
      }
      return;
    }
    paramFunction1 = (Runnable)new Runnable()
    {
      final SelectBuilderImpl this$0;
      
      public final void run()
      {
        if (this.this$0.trySelect()) {
          CancellableKt.startCoroutineCancellable(paramFunction1, this.this$0.getCompletion());
        }
      }
    };
    disposeOnSelect(DelayKt.getDelay(getContext()).invokeOnTimeout(paramLong, paramFunction1, getContext()));
  }
  
  public Object performAtomicTrySelect(AtomicDesc paramAtomicDesc)
  {
    return new AtomicSelectOp(this, paramAtomicDesc).perform(null);
  }
  
  public void resumeSelectWithException(Throwable paramThrowable)
  {
    if ((DebugKt.getASSERTIONS_ENABLED()) && (!isSelected())) {
      throw new AssertionError();
    }
    label149:
    for (;;)
    {
      Object localObject = this._result;
      if (localObject == SelectKt.access$getUNDECIDED$p())
      {
        localObject = this.uCont;
        if ((DebugKt.getRECOVER_STACK_TRACES()) && ((localObject instanceof CoroutineStackFrame))) {
          localObject = StackTraceRecoveryKt.access$recoverFromStackFrame(paramThrowable, (CoroutineStackFrame)localObject);
        } else {
          localObject = paramThrowable;
        }
        localObject = new CompletedExceptionally((Throwable)localObject, false, 2, null);
        if (!AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_result$FU, this, SelectKt.access$getUNDECIDED$p(), localObject)) {
          break label149;
        }
      }
      else
      {
        if (localObject != IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
          break;
        }
        if (!AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_result$FU, this, IntrinsicsKt.getCOROUTINE_SUSPENDED(), SelectKt.access$getRESUMED$p())) {
          break label149;
        }
        localObject = IntrinsicsKt.intercepted(this.uCont);
        Result.Companion localCompanion = Result.Companion;
        ((Continuation)localObject).resumeWith(Result.constructor-impl(ResultKt.createFailure(paramThrowable)));
      }
      return;
    }
    throw new IllegalStateException("Already resumed");
  }
  
  public void resumeWith(Object paramObject)
  {
    if ((DebugKt.getASSERTIONS_ENABLED()) && (!isSelected())) {
      throw new AssertionError();
    }
    label164:
    for (;;)
    {
      Object localObject = this._result;
      if (localObject == SelectKt.access$getUNDECIDED$p())
      {
        localObject = CompletionStateKt.toState$default(paramObject, null, 1, null);
        if (!AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_result$FU, this, SelectKt.access$getUNDECIDED$p(), localObject)) {
          break label164;
        }
      }
      else
      {
        if (localObject != IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
          break;
        }
        if (!AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_result$FU, this, IntrinsicsKt.getCOROUTINE_SUSPENDED(), SelectKt.access$getRESUMED$p())) {
          break label164;
        }
        if (Result.isFailure-impl(paramObject))
        {
          localObject = this.uCont;
          paramObject = Result.exceptionOrNull-impl(paramObject);
          Intrinsics.checkNotNull(paramObject);
          Result.Companion localCompanion = Result.Companion;
          if ((DebugKt.getRECOVER_STACK_TRACES()) && ((localObject instanceof CoroutineStackFrame))) {
            paramObject = StackTraceRecoveryKt.access$recoverFromStackFrame((Throwable)paramObject, (CoroutineStackFrame)localObject);
          }
          ((Continuation)localObject).resumeWith(Result.constructor-impl(ResultKt.createFailure((Throwable)paramObject)));
        }
        else
        {
          this.uCont.resumeWith(paramObject);
        }
      }
      return;
    }
    throw new IllegalStateException("Already resumed");
  }
  
  public String toString()
  {
    return "SelectInstance(state=" + this._state + ", result=" + this._result + ')';
  }
  
  public boolean trySelect()
  {
    Object localObject = trySelectOther(null);
    boolean bool;
    if (localObject == CancellableContinuationImplKt.RESUME_TOKEN)
    {
      bool = true;
    }
    else
    {
      if (localObject != null) {
        break label26;
      }
      bool = false;
    }
    return bool;
    label26:
    localObject = Intrinsics.stringPlus("Unexpected trySelectIdempotent result ", localObject);
    Log5ECF72.a(localObject);
    LogE84000.a(localObject);
    Log229316.a(localObject);
    throw new IllegalStateException(localObject.toString());
  }
  
  public Object trySelectOther(LockFreeLinkedListNode.PrepareOp paramPrepareOp)
  {
    Object localObject;
    label154:
    for (;;)
    {
      localObject = this._state;
      if (localObject == SelectKt.getNOT_SELECTED())
      {
        if (paramPrepareOp == null)
        {
          if (!AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_state$FU, this, SelectKt.getNOT_SELECTED(), null)) {
            break label154;
          }
        }
        else
        {
          localObject = new PairSelectOp(paramPrepareOp);
          if (!AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_state$FU, this, SelectKt.getNOT_SELECTED(), localObject)) {
            break label154;
          }
          paramPrepareOp = ((PairSelectOp)localObject).perform(this);
          if (paramPrepareOp != null) {
            return paramPrepareOp;
          }
        }
        doAfterSelect();
        return CancellableContinuationImplKt.RESUME_TOKEN;
      }
      else
      {
        if (!(localObject instanceof OpDescriptor)) {
          break;
        }
        if (paramPrepareOp != null)
        {
          AtomicOp localAtomicOp = paramPrepareOp.getAtomicOp();
          if (((localAtomicOp instanceof AtomicSelectOp)) && (((AtomicSelectOp)localAtomicOp).impl == this)) {
            throw new IllegalStateException("Cannot use matching select clauses on the same object".toString());
          }
          if (localAtomicOp.isEarlierThan((OpDescriptor)localObject)) {
            return AtomicKt.RETRY_ATOMIC;
          }
        }
        ((OpDescriptor)localObject).perform(this);
      }
    }
    if (paramPrepareOp == null) {
      return null;
    }
    if (localObject == paramPrepareOp.desc) {
      return CancellableContinuationImplKt.RESUME_TOKEN;
    }
    return null;
  }
  
  @Metadata(d1={"\0004\n\002\030\002\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\t\n\002\b\003\n\002\020\002\n\002\b\006\n\002\020\016\n\002\b\002\b\002\030\0002\n\022\006\022\004\030\0010\0020\001B\031\022\n\020\003\032\006\022\002\b\0030\004\022\006\020\005\032\0020\006¢\006\002\020\007J\034\020\f\032\0020\r2\b\020\016\032\004\030\0010\0022\b\020\017\032\004\030\0010\002H\026J\022\020\020\032\0020\r2\b\020\017\032\004\030\0010\002H\002J\024\020\021\032\004\030\0010\0022\b\020\016\032\004\030\0010\002H\026J\n\020\022\032\004\030\0010\002H\002J\b\020\023\032\0020\024H\026J\b\020\025\032\0020\rH\002R\020\020\005\032\0020\0068\006X\004¢\006\002\n\000R\024\020\003\032\006\022\002\b\0030\0048\006X\004¢\006\002\n\000R\024\020\b\032\0020\tX\004¢\006\b\n\000\032\004\b\n\020\013¨\006\026"}, d2={"Lkotlinx/coroutines/selects/SelectBuilderImpl$AtomicSelectOp;", "Lkotlinx/coroutines/internal/AtomicOp;", "", "impl", "Lkotlinx/coroutines/selects/SelectBuilderImpl;", "desc", "Lkotlinx/coroutines/internal/AtomicDesc;", "(Lkotlinx/coroutines/selects/SelectBuilderImpl;Lkotlinx/coroutines/internal/AtomicDesc;)V", "opSequence", "", "getOpSequence", "()J", "complete", "", "affected", "failure", "completeSelect", "prepare", "prepareSelectOp", "toString", "", "undoPrepare", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  private static final class AtomicSelectOp
    extends AtomicOp<Object>
  {
    public final AtomicDesc desc;
    public final SelectBuilderImpl<?> impl;
    private final long opSequence;
    
    public AtomicSelectOp(SelectBuilderImpl<?> paramSelectBuilderImpl, AtomicDesc paramAtomicDesc)
    {
      this.impl = paramSelectBuilderImpl;
      this.desc = paramAtomicDesc;
      this.opSequence = SelectKt.access$getSelectOpSequenceNumber$p().next();
      paramAtomicDesc.setAtomicOp((AtomicOp)this);
    }
    
    private final void completeSelect(Object paramObject)
    {
      int i;
      if (paramObject == null) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0) {
        paramObject = null;
      } else {
        paramObject = SelectKt.getNOT_SELECTED();
      }
      SelectBuilderImpl localSelectBuilderImpl = this.impl;
      if ((AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(SelectBuilderImpl._state$FU, localSelectBuilderImpl, this, paramObject)) && (i != 0)) {
        SelectBuilderImpl.access$doAfterSelect(this.impl);
      }
    }
    
    private final Object prepareSelectOp()
    {
      SelectBuilderImpl localSelectBuilderImpl = this.impl;
      for (;;)
      {
        Object localObject = localSelectBuilderImpl._state;
        if (localObject == this) {
          return null;
        }
        if ((localObject instanceof OpDescriptor))
        {
          ((OpDescriptor)localObject).perform(this.impl);
        }
        else
        {
          if (localObject != SelectKt.getNOT_SELECTED()) {
            break;
          }
          localObject = this.impl;
          if (AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(SelectBuilderImpl._state$FU, localObject, SelectKt.getNOT_SELECTED(), this)) {
            return null;
          }
        }
      }
      return SelectKt.getALREADY_SELECTED();
    }
    
    private final void undoPrepare()
    {
      SelectBuilderImpl localSelectBuilderImpl = this.impl;
      AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(SelectBuilderImpl._state$FU, localSelectBuilderImpl, this, SelectKt.getNOT_SELECTED());
    }
    
    public void complete(Object paramObject1, Object paramObject2)
    {
      completeSelect(paramObject2);
      this.desc.complete((AtomicOp)this, paramObject2);
    }
    
    public long getOpSequence()
    {
      return this.opSequence;
    }
    
    public Object prepare(Object paramObject)
    {
      Object localObject1;
      if (paramObject == null)
      {
        localObject1 = prepareSelectOp();
        if (localObject1 != null) {
          return localObject1;
        }
      }
      try
      {
        localObject1 = this.desc.prepare((AtomicOp)this);
        return localObject1;
      }
      finally
      {
        if (paramObject == null) {
          undoPrepare();
        }
      }
    }
    
    public String toString()
    {
      return "AtomicSelectOp(sequence=" + getOpSequence() + ')';
    }
  }
  
  @Metadata(d1={"\000\022\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\b\002\030\0002\0020\001B\r\022\006\020\002\032\0020\003¢\006\002\020\004R\020\020\002\032\0020\0038\006X\004¢\006\002\n\000¨\006\005"}, d2={"Lkotlinx/coroutines/selects/SelectBuilderImpl$DisposeNode;", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "handle", "Lkotlinx/coroutines/DisposableHandle;", "(Lkotlinx/coroutines/DisposableHandle;)V", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  private static final class DisposeNode
    extends LockFreeLinkedListNode
  {
    public final DisposableHandle handle;
    
    public DisposeNode(DisposableHandle paramDisposableHandle)
    {
      this.handle = paramDisposableHandle;
    }
  }
  
  @Metadata(d1={"\000\"\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\020\000\n\002\b\002\b\002\030\0002\0020\001B\r\022\006\020\002\032\0020\003¢\006\002\020\004J\024\020\t\032\004\030\0010\n2\b\020\013\032\004\030\0010\nH\026R\030\020\005\032\006\022\002\b\0030\0068VX\004¢\006\006\032\004\b\007\020\bR\020\020\002\032\0020\0038\006X\004¢\006\002\n\000¨\006\f"}, d2={"Lkotlinx/coroutines/selects/SelectBuilderImpl$PairSelectOp;", "Lkotlinx/coroutines/internal/OpDescriptor;", "otherOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;)V", "atomicOp", "Lkotlinx/coroutines/internal/AtomicOp;", "getAtomicOp", "()Lkotlinx/coroutines/internal/AtomicOp;", "perform", "", "affected", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  private static final class PairSelectOp
    extends OpDescriptor
  {
    public final LockFreeLinkedListNode.PrepareOp otherOp;
    
    public PairSelectOp(LockFreeLinkedListNode.PrepareOp paramPrepareOp)
    {
      this.otherOp = paramPrepareOp;
    }
    
    public AtomicOp<?> getAtomicOp()
    {
      return this.otherOp.getAtomicOp();
    }
    
    public Object perform(Object paramObject)
    {
      if (paramObject != null)
      {
        SelectBuilderImpl localSelectBuilderImpl = (SelectBuilderImpl)paramObject;
        this.otherOp.finishPrepare();
        Object localObject = this.otherOp.getAtomicOp().decide(null);
        if (localObject == null) {
          paramObject = this.otherOp.desc;
        } else {
          paramObject = SelectKt.getNOT_SELECTED();
        }
        AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(SelectBuilderImpl._state$FU, localSelectBuilderImpl, this, paramObject);
        return localObject;
      }
      throw new NullPointerException("null cannot be cast to non-null type kotlinx.coroutines.selects.SelectBuilderImpl<*>");
    }
  }
  
  @Metadata(d1={"\000\030\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\020\003\n\000\b\004\030\0002\0020\001B\005¢\006\002\020\002J\023\020\003\032\0020\0042\b\020\005\032\004\030\0010\006H\002¨\006\007"}, d2={"Lkotlinx/coroutines/selects/SelectBuilderImpl$SelectOnCancelling;", "Lkotlinx/coroutines/JobCancellingNode;", "(Lkotlinx/coroutines/selects/SelectBuilderImpl;)V", "invoke", "", "cause", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  private final class SelectOnCancelling
    extends JobCancellingNode
  {
    final SelectBuilderImpl<R> this$0;
    
    public void invoke(Throwable paramThrowable)
    {
      if (this.this$0.trySelect()) {
        this.this$0.resumeSelectWithException((Throwable)getJob().getCancellationException());
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/selects/SelectBuilderImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */