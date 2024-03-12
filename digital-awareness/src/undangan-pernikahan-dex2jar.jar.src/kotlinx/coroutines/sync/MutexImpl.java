package kotlinx.coroutines.sync;

import androidx.concurrent.futures.AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CancellableContinuationImplKt;
import kotlinx.coroutines.CancellableContinuationKt;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.internal.AtomicDesc;
import kotlinx.coroutines.internal.AtomicKt;
import kotlinx.coroutines.internal.AtomicOp;
import kotlinx.coroutines.internal.LockFreeLinkedListHead;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;
import kotlinx.coroutines.internal.OpDescriptor;
import kotlinx.coroutines.intrinsics.CancellableKt;
import kotlinx.coroutines.intrinsics.UndispatchedKt;
import kotlinx.coroutines.selects.SelectClause2;
import kotlinx.coroutines.selects.SelectInstance;
import kotlinx.coroutines.selects.SelectKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000B\n\002\030\002\n\002\020\013\n\002\b\003\n\002\020\000\n\002\b\003\n\002\020\002\n\002\b\004\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\003\n\002\020\016\n\002\b\t\n\002\030\002\n\002\b\n\b\000\030\0002\0020\0212\020\022\006\022\004\030\0010\005\022\004\022\0020\0210 :\006$%&'()B\017\022\006\020\002\032\0020\001¢\006\004\b\003\020\004J\027\020\007\032\0020\0012\006\020\006\032\0020\005H\026¢\006\004\b\007\020\bJ\035\020\n\032\0020\t2\b\020\006\032\004\030\0010\005H@ø\001\000¢\006\004\b\n\020\013J\035\020\f\032\0020\t2\b\020\006\032\004\030\0010\005H@ø\001\000¢\006\004\b\f\020\013JT\020\024\032\0020\t\"\004\b\000\020\r2\f\020\017\032\b\022\004\022\0028\0000\0162\b\020\006\032\004\030\0010\0052\"\020\023\032\036\b\001\022\004\022\0020\021\022\n\022\b\022\004\022\0028\0000\022\022\006\022\004\030\0010\0050\020H\026ø\001\000¢\006\004\b\024\020\025J\017\020\027\032\0020\026H\026¢\006\004\b\027\020\030J\031\020\031\032\0020\0012\b\020\006\032\004\030\0010\005H\026¢\006\004\b\031\020\bJ\031\020\032\032\0020\t2\b\020\006\032\004\030\0010\005H\026¢\006\004\b\032\020\033R\024\020\034\032\0020\0018VX\004¢\006\006\032\004\b\034\020\035R\024\020\037\032\0020\0018@X\004¢\006\006\032\004\b\036\020\035R\"\020#\032\020\022\006\022\004\030\0010\005\022\004\022\0020\0210 8VX\004¢\006\006\032\004\b!\020\"\002\004\n\002\b\031¨\006*"}, d2={"Lkotlinx/coroutines/sync/MutexImpl;", "", "locked", "<init>", "(Z)V", "", "owner", "holdsLock", "(Ljava/lang/Object;)Z", "", "lock", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "lockSuspend", "R", "Lkotlinx/coroutines/selects/SelectInstance;", "select", "Lkotlin/Function2;", "Lkotlinx/coroutines/sync/Mutex;", "Lkotlin/coroutines/Continuation;", "block", "registerSelectClause2", "(Lkotlinx/coroutines/selects/SelectInstance;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)V", "", "toString", "()Ljava/lang/String;", "tryLock", "unlock", "(Ljava/lang/Object;)V", "isLocked", "()Z", "isLockedEmptyQueueState$kotlinx_coroutines_core", "isLockedEmptyQueueState", "Lkotlinx/coroutines/selects/SelectClause2;", "getOnLock", "()Lkotlinx/coroutines/selects/SelectClause2;", "onLock", "LockCont", "LockSelect", "LockWaiter", "LockedQueue", "TryLockDesc", "UnlockOp", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class MutexImpl
  implements Mutex, SelectClause2<Object, Mutex>
{
  static final AtomicReferenceFieldUpdater _state$FU = AtomicReferenceFieldUpdater.newUpdater(MutexImpl.class, Object.class, "_state");
  volatile Object _state;
  
  public MutexImpl(boolean paramBoolean)
  {
    Empty localEmpty;
    if (paramBoolean) {
      localEmpty = MutexKt.access$getEMPTY_LOCKED$p();
    } else {
      localEmpty = MutexKt.access$getEMPTY_UNLOCKED$p();
    }
    this._state = localEmpty;
  }
  
  private final Object lockSuspend(final Object paramObject, Continuation<? super Unit> paramContinuation)
  {
    CancellableContinuationImpl localCancellableContinuationImpl = CancellableContinuationKt.getOrCreateCancellableContinuation(IntrinsicsKt.intercepted(paramContinuation));
    CancellableContinuation localCancellableContinuation = (CancellableContinuation)localCancellableContinuationImpl;
    LockCont localLockCont = new LockCont(paramObject, localCancellableContinuation);
    Object localObject;
    label266:
    label297:
    label315:
    for (;;)
    {
      localObject = this._state;
      if ((localObject instanceof Empty))
      {
        if (((Empty)localObject).locked != MutexKt.access$getUNLOCKED$p())
        {
          AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_state$FU, this, localObject, new LockedQueue(((Empty)localObject).locked));
          break label315;
        }
        Empty localEmpty;
        if (paramObject == null) {
          localEmpty = MutexKt.access$getEMPTY_LOCKED$p();
        } else {
          localEmpty = new Empty(paramObject);
        }
        if (!AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_state$FU, this, localObject, localEmpty)) {
          break label315;
        }
        localCancellableContinuation.resume(Unit.INSTANCE, (Function1)new Lambda(paramObject)
        {
          final MutexImpl this$0;
          
          public final void invoke(Throwable paramAnonymousThrowable)
          {
            this.this$0.unlock(paramObject);
          }
        });
      }
      else
      {
        if (!(localObject instanceof LockedQueue)) {
          break label297;
        }
        int i;
        if (((LockedQueue)localObject).owner != paramObject) {
          i = 1;
        } else {
          i = 0;
        }
        if (i == 0) {
          break label266;
        }
        ((LockedQueue)localObject).addLast((LockFreeLinkedListNode)localLockCont);
        if ((this._state != localObject) && (localLockCont.take()))
        {
          localLockCont = new LockCont(paramObject, localCancellableContinuation);
          break label315;
        }
        CancellableContinuationKt.removeOnCancellation(localCancellableContinuation, (LockFreeLinkedListNode)localLockCont);
      }
      paramObject = localCancellableContinuationImpl.getResult();
      if (paramObject == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
        DebugProbesKt.probeCoroutineSuspended(paramContinuation);
      }
      if (paramObject == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
        return paramObject;
      }
      return Unit.INSTANCE;
      paramObject = Intrinsics.stringPlus("Already locked by ", paramObject);
      Log5ECF72.a(paramObject);
      LogE84000.a(paramObject);
      Log229316.a(paramObject);
      throw new IllegalStateException(paramObject.toString());
      if (!(localObject instanceof OpDescriptor)) {
        break;
      }
      ((OpDescriptor)localObject).perform(this);
    }
    paramObject = Intrinsics.stringPlus("Illegal state ", localObject);
    Log5ECF72.a(paramObject);
    LogE84000.a(paramObject);
    Log229316.a(paramObject);
    throw new IllegalStateException(paramObject.toString());
  }
  
  public SelectClause2<Object, Mutex> getOnLock()
  {
    return (SelectClause2)this;
  }
  
  public boolean holdsLock(Object paramObject)
  {
    Object localObject = this._state;
    boolean bool2 = localObject instanceof Empty;
    boolean bool1 = true;
    if (bool2)
    {
      if (((Empty)localObject).locked != paramObject) {
        bool1 = false;
      }
    }
    else if ((localObject instanceof LockedQueue))
    {
      if (((LockedQueue)localObject).owner != paramObject) {
        bool1 = false;
      }
    }
    else {
      bool1 = false;
    }
    return bool1;
  }
  
  public boolean isLocked()
  {
    for (;;)
    {
      localObject = this._state;
      boolean bool2 = localObject instanceof Empty;
      boolean bool1 = true;
      if (bool2)
      {
        if (((Empty)localObject).locked == MutexKt.access$getUNLOCKED$p()) {
          bool1 = false;
        }
        return bool1;
      }
      if ((localObject instanceof LockedQueue)) {
        return true;
      }
      if (!(localObject instanceof OpDescriptor)) {
        break;
      }
      ((OpDescriptor)localObject).perform(this);
    }
    Object localObject = Intrinsics.stringPlus("Illegal state ", localObject);
    Log5ECF72.a(localObject);
    LogE84000.a(localObject);
    Log229316.a(localObject);
    throw new IllegalStateException(localObject.toString());
  }
  
  public final boolean isLockedEmptyQueueState$kotlinx_coroutines_core()
  {
    Object localObject = this._state;
    boolean bool;
    if (((localObject instanceof LockedQueue)) && (((LockedQueue)localObject).isEmpty())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public Object lock(Object paramObject, Continuation<? super Unit> paramContinuation)
  {
    if (tryLock(paramObject)) {
      return Unit.INSTANCE;
    }
    paramObject = lockSuspend(paramObject, paramContinuation);
    if (paramObject == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      return paramObject;
    }
    return Unit.INSTANCE;
  }
  
  public <R> void registerSelectClause2(SelectInstance<? super R> paramSelectInstance, Object paramObject, Function2<? super Mutex, ? super Continuation<? super R>, ? extends Object> paramFunction2)
  {
    Object localObject;
    for (;;)
    {
      if (paramSelectInstance.isSelected()) {
        return;
      }
      localObject = this._state;
      if ((localObject instanceof Empty))
      {
        if (((Empty)localObject).locked != MutexKt.access$getUNLOCKED$p())
        {
          AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_state$FU, this, localObject, new LockedQueue(((Empty)localObject).locked));
        }
        else
        {
          localObject = paramSelectInstance.performAtomicTrySelect((AtomicDesc)new TryLockDesc(this, paramObject));
          if (localObject == null)
          {
            UndispatchedKt.startCoroutineUnintercepted(paramFunction2, this, paramSelectInstance.getCompletion());
            return;
          }
          if (localObject == SelectKt.getALREADY_SELECTED()) {
            return;
          }
          if ((localObject != MutexKt.access$getLOCK_FAIL$p()) && (localObject != AtomicKt.RETRY_ATOMIC))
          {
            paramSelectInstance = Intrinsics.stringPlus("performAtomicTrySelect(TryLockDesc) returned ", localObject);
            Log5ECF72.a(paramSelectInstance);
            LogE84000.a(paramSelectInstance);
            Log229316.a(paramSelectInstance);
            throw new IllegalStateException(paramSelectInstance.toString());
          }
        }
      }
      else if ((localObject instanceof LockedQueue))
      {
        int i;
        if (((LockedQueue)localObject).owner != paramObject) {
          i = 1;
        } else {
          i = 0;
        }
        if (i != 0)
        {
          LockSelect localLockSelect = new LockSelect(paramObject, paramSelectInstance, paramFunction2);
          ((LockedQueue)localObject).addLast((LockFreeLinkedListNode)localLockSelect);
          if ((this._state == localObject) || (!localLockSelect.take())) {
            paramSelectInstance.disposeOnSelect((DisposableHandle)localLockSelect);
          }
        }
        else
        {
          paramSelectInstance = Intrinsics.stringPlus("Already locked by ", paramObject);
          Log5ECF72.a(paramSelectInstance);
          LogE84000.a(paramSelectInstance);
          Log229316.a(paramSelectInstance);
          throw new IllegalStateException(paramSelectInstance.toString());
        }
      }
      else
      {
        if (!(localObject instanceof OpDescriptor)) {
          break;
        }
        ((OpDescriptor)localObject).perform(this);
      }
    }
    paramSelectInstance = Intrinsics.stringPlus("Illegal state ", localObject);
    Log5ECF72.a(paramSelectInstance);
    LogE84000.a(paramSelectInstance);
    Log229316.a(paramSelectInstance);
    throw new IllegalStateException(paramSelectInstance.toString());
  }
  
  public String toString()
  {
    for (;;)
    {
      localObject = this._state;
      if ((localObject instanceof Empty)) {
        return "Mutex[" + ((Empty)localObject).locked + ']';
      }
      if (!(localObject instanceof OpDescriptor)) {
        break;
      }
      ((OpDescriptor)localObject).perform(this);
    }
    if ((localObject instanceof LockedQueue)) {
      return "Mutex[" + ((LockedQueue)localObject).owner + ']';
    }
    Object localObject = Intrinsics.stringPlus("Illegal state ", localObject);
    Log5ECF72.a(localObject);
    LogE84000.a(localObject);
    Log229316.a(localObject);
    throw new IllegalStateException(localObject.toString());
  }
  
  public boolean tryLock(Object paramObject)
  {
    Object localObject;
    for (;;)
    {
      localObject = this._state;
      boolean bool = localObject instanceof Empty;
      int i = 1;
      if (bool)
      {
        if (((Empty)localObject).locked != MutexKt.access$getUNLOCKED$p()) {
          return false;
        }
        Empty localEmpty;
        if (paramObject == null) {
          localEmpty = MutexKt.access$getEMPTY_LOCKED$p();
        } else {
          localEmpty = new Empty(paramObject);
        }
        if (AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_state$FU, this, localObject, localEmpty)) {
          return true;
        }
      }
      else
      {
        if ((localObject instanceof LockedQueue))
        {
          if (((LockedQueue)localObject).owner == paramObject) {
            i = 0;
          }
          if (i != 0) {
            return false;
          }
          paramObject = Intrinsics.stringPlus("Already locked by ", paramObject);
          Log5ECF72.a(paramObject);
          LogE84000.a(paramObject);
          Log229316.a(paramObject);
          throw new IllegalStateException(paramObject.toString());
        }
        if (!(localObject instanceof OpDescriptor)) {
          break;
        }
        ((OpDescriptor)localObject).perform(this);
      }
    }
    paramObject = Intrinsics.stringPlus("Illegal state ", localObject);
    Log5ECF72.a(paramObject);
    LogE84000.a(paramObject);
    Log229316.a(paramObject);
    throw new IllegalStateException(paramObject.toString());
  }
  
  public void unlock(Object paramObject)
  {
    Object localObject1;
    for (;;)
    {
      localObject1 = this._state;
      boolean bool = localObject1 instanceof Empty;
      int i = 1;
      int k = 1;
      int j = 1;
      if (bool)
      {
        if (paramObject == null)
        {
          if (((Empty)localObject1).locked != MutexKt.access$getUNLOCKED$p()) {
            i = j;
          } else {
            i = 0;
          }
          if (i == 0) {
            throw new IllegalStateException("Mutex is not locked".toString());
          }
        }
        else
        {
          if (((Empty)localObject1).locked != paramObject) {
            i = 0;
          }
          if (i == 0) {
            break label108;
          }
        }
        if (AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_state$FU, this, localObject1, MutexKt.access$getEMPTY_UNLOCKED$p()))
        {
          return;
          label108:
          throw new IllegalStateException(("Mutex is locked by " + ((Empty)localObject1).locked + " but expected " + paramObject).toString());
        }
      }
      else if ((localObject1 instanceof OpDescriptor))
      {
        ((OpDescriptor)localObject1).perform(this);
      }
      else
      {
        if (!(localObject1 instanceof LockedQueue)) {
          break;
        }
        if (paramObject != null)
        {
          if (((LockedQueue)localObject1).owner == paramObject) {
            i = k;
          } else {
            i = 0;
          }
          if (i == 0) {
            throw new IllegalStateException(("Mutex is locked by " + ((LockedQueue)localObject1).owner + " but expected " + paramObject).toString());
          }
        }
        Object localObject2 = ((LockedQueue)localObject1).removeFirstOrNull();
        if (localObject2 == null)
        {
          localObject2 = new UnlockOp((LockedQueue)localObject1);
          if ((!AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_state$FU, this, localObject1, localObject2)) || (((UnlockOp)localObject2).perform(this) != null)) {}
        }
        else if (((LockWaiter)localObject2).tryResumeLockWaiter())
        {
          LockedQueue localLockedQueue = (LockedQueue)localObject1;
          localObject1 = ((LockWaiter)localObject2).owner;
          paramObject = localObject1;
          if (localObject1 == null) {
            paramObject = MutexKt.access$getLOCKED$p();
          }
          localLockedQueue.owner = paramObject;
          ((LockWaiter)localObject2).completeResumeLockWaiter();
          return;
        }
      }
    }
    paramObject = Intrinsics.stringPlus("Illegal state ", localObject1);
    Log5ECF72.a(paramObject);
    LogE84000.a(paramObject);
    Log229316.a(paramObject);
    throw new IllegalStateException(paramObject.toString());
  }
  
  @Metadata(d1={"\000,\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\020\000\n\000\n\002\030\002\n\002\020\002\n\002\b\003\n\002\020\016\n\000\n\002\020\013\n\000\b\004\030\0002\0060\001R\0020\002B\035\022\b\020\003\032\004\030\0010\004\022\f\020\005\032\b\022\004\022\0020\0070\006¢\006\002\020\bJ\b\020\t\032\0020\007H\026J\b\020\n\032\0020\013H\026J\b\020\f\032\0020\rH\026R\024\020\005\032\b\022\004\022\0020\0070\006X\004¢\006\002\n\000¨\006\016"}, d2={"Lkotlinx/coroutines/sync/MutexImpl$LockCont;", "Lkotlinx/coroutines/sync/MutexImpl$LockWaiter;", "Lkotlinx/coroutines/sync/MutexImpl;", "owner", "", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "", "(Lkotlinx/coroutines/sync/MutexImpl;Ljava/lang/Object;Lkotlinx/coroutines/CancellableContinuation;)V", "completeResumeLockWaiter", "toString", "", "tryResumeLockWaiter", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  private final class LockCont
    extends MutexImpl.LockWaiter
  {
    private final CancellableContinuation<Unit> cont;
    
    public LockCont(CancellableContinuation<? super Unit> paramCancellableContinuation)
    {
      super(paramCancellableContinuation);
      CancellableContinuation localCancellableContinuation;
      this.cont = localCancellableContinuation;
    }
    
    public void completeResumeLockWaiter()
    {
      this.cont.completeResume(CancellableContinuationImplKt.RESUME_TOKEN);
    }
    
    public String toString()
    {
      return "LockCont[" + this.owner + ", " + this.cont + "] for " + MutexImpl.this;
    }
    
    public boolean tryResumeLockWaiter()
    {
      boolean bool2 = take();
      boolean bool1 = false;
      if (!bool2) {
        return false;
      }
      if (this.cont.tryResume(Unit.INSTANCE, null, (Function1)new Lambda(MutexImpl.this)
      {
        final MutexImpl this$0;
        
        public final void invoke(Throwable paramAnonymousThrowable)
        {
          this.this$0.unlock(jdField_this.owner);
        }
      }) != null) {
        bool1 = true;
      }
      return bool1;
    }
  }
  
  @Metadata(d1={"\000>\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\000\n\002\020\000\n\000\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\003\n\002\020\002\n\000\n\002\020\016\n\000\n\002\020\013\n\000\b\004\030\000*\004\b\000\020\0012\0060\002R\0020\003BD\022\b\020\004\032\004\030\0010\005\022\f\020\006\032\b\022\004\022\0028\0000\007\022\"\020\b\032\036\b\001\022\004\022\0020\n\022\n\022\b\022\004\022\0028\0000\013\022\006\022\004\030\0010\0050\tø\001\000¢\006\002\020\fJ\b\020\016\032\0020\017H\026J\b\020\020\032\0020\021H\026J\b\020\022\032\0020\023H\026R1\020\b\032\036\b\001\022\004\022\0020\n\022\n\022\b\022\004\022\0028\0000\013\022\006\022\004\030\0010\0050\t8\006X\004ø\001\000¢\006\004\n\002\020\rR\026\020\006\032\b\022\004\022\0028\0000\0078\006X\004¢\006\002\n\000\002\004\n\002\b\031¨\006\024"}, d2={"Lkotlinx/coroutines/sync/MutexImpl$LockSelect;", "R", "Lkotlinx/coroutines/sync/MutexImpl$LockWaiter;", "Lkotlinx/coroutines/sync/MutexImpl;", "owner", "", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/sync/Mutex;", "Lkotlin/coroutines/Continuation;", "(Lkotlinx/coroutines/sync/MutexImpl;Ljava/lang/Object;Lkotlinx/coroutines/selects/SelectInstance;Lkotlin/jvm/functions/Function2;)V", "Lkotlin/jvm/functions/Function2;", "completeResumeLockWaiter", "", "toString", "", "tryResumeLockWaiter", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  private final class LockSelect<R>
    extends MutexImpl.LockWaiter
  {
    public final Function2<Mutex, Continuation<? super R>, Object> block;
    public final SelectInstance<R> select;
    
    public LockSelect(SelectInstance<? super R> paramSelectInstance, Function2<? super Mutex, ? super Continuation<? super R>, ? extends Object> paramFunction2)
    {
      super(paramSelectInstance);
      this.select = paramFunction2;
      Function2 localFunction2;
      this.block = localFunction2;
    }
    
    public void completeResumeLockWaiter()
    {
      CancellableKt.startCoroutineCancellable(this.block, MutexImpl.this, this.select.getCompletion(), (Function1)new Lambda(MutexImpl.this)
      {
        final MutexImpl this$0;
        
        public final void invoke(Throwable paramAnonymousThrowable)
        {
          this.this$0.unlock(jdField_this.owner);
        }
      });
    }
    
    public String toString()
    {
      return "LockSelect[" + this.owner + ", " + this.select + "] for " + MutexImpl.this;
    }
    
    public boolean tryResumeLockWaiter()
    {
      boolean bool;
      if ((take()) && (this.select.trySelect())) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
  }
  
  @Metadata(d1={"\000$\n\002\030\002\n\002\020\000\n\002\b\003\n\002\020\002\n\002\b\003\n\002\020\013\n\002\b\005\n\002\030\002\n\002\030\002\b¢\004\030\0002\0020\0172\0020\020B\021\022\b\020\002\032\004\030\0010\001¢\006\004\b\003\020\004J\017\020\006\032\0020\005H&¢\006\004\b\006\020\007J\r\020\b\032\0020\005¢\006\004\b\b\020\007J\r\020\n\032\0020\t¢\006\004\b\n\020\013J\017\020\f\032\0020\tH&¢\006\004\b\f\020\013R\026\020\002\032\004\030\0010\0018\006X\004¢\006\006\n\004\b\002\020\r¨\006\016"}, d2={"Lkotlinx/coroutines/sync/MutexImpl$LockWaiter;", "", "owner", "<init>", "(Lkotlinx/coroutines/sync/MutexImpl;Ljava/lang/Object;)V", "", "completeResumeLockWaiter", "()V", "dispose", "", "take", "()Z", "tryResumeLockWaiter", "Ljava/lang/Object;", "kotlinx-coroutines-core", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "Lkotlinx/coroutines/DisposableHandle;"}, k=1, mv={1, 6, 0}, xi=48)
  private abstract class LockWaiter
    extends LockFreeLinkedListNode
    implements DisposableHandle
  {
    private static final AtomicIntegerFieldUpdater isTaken$FU = AtomicIntegerFieldUpdater.newUpdater(LockWaiter.class, "isTaken");
    private volatile int isTaken;
    public final Object owner;
    
    public LockWaiter()
    {
      Object localObject;
      this.owner = localObject;
      this.isTaken = 0;
    }
    
    public abstract void completeResumeLockWaiter();
    
    public final void dispose()
    {
      remove();
    }
    
    public final boolean take()
    {
      return isTaken$FU.compareAndSet(this, 0, 1);
    }
    
    public abstract boolean tryResumeLockWaiter();
  }
  
  @Metadata(d1={"\000\030\n\002\030\002\n\002\030\002\n\000\n\002\020\000\n\002\b\002\n\002\020\016\n\000\b\002\030\0002\0020\001B\r\022\006\020\002\032\0020\003¢\006\002\020\004J\b\020\005\032\0020\006H\026R\022\020\002\032\0020\0038\006@\006X\016¢\006\002\n\000¨\006\007"}, d2={"Lkotlinx/coroutines/sync/MutexImpl$LockedQueue;", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "owner", "", "(Ljava/lang/Object;)V", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  private static final class LockedQueue
    extends LockFreeLinkedListHead
  {
    public Object owner;
    
    public LockedQueue(Object paramObject)
    {
      this.owner = paramObject;
    }
    
    public String toString()
    {
      return "LockedQueue[" + this.owner + ']';
    }
  }
  
  @Metadata(d1={"\000&\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\000\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\002\b\004\b\002\030\0002\0020\001:\001\rB\027\022\006\020\002\032\0020\003\022\b\020\004\032\004\030\0010\005¢\006\002\020\006J\036\020\007\032\0020\b2\n\020\t\032\006\022\002\b\0030\n2\b\020\013\032\004\030\0010\005H\026J\026\020\f\032\004\030\0010\0052\n\020\t\032\006\022\002\b\0030\nH\026R\020\020\002\032\0020\0038\006X\004¢\006\002\n\000R\022\020\004\032\004\030\0010\0058\006X\004¢\006\002\n\000¨\006\016"}, d2={"Lkotlinx/coroutines/sync/MutexImpl$TryLockDesc;", "Lkotlinx/coroutines/internal/AtomicDesc;", "mutex", "Lkotlinx/coroutines/sync/MutexImpl;", "owner", "", "(Lkotlinx/coroutines/sync/MutexImpl;Ljava/lang/Object;)V", "complete", "", "op", "Lkotlinx/coroutines/internal/AtomicOp;", "failure", "prepare", "PrepareOp", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  private static final class TryLockDesc
    extends AtomicDesc
  {
    public final MutexImpl mutex;
    public final Object owner;
    
    public TryLockDesc(MutexImpl paramMutexImpl, Object paramObject)
    {
      this.mutex = paramMutexImpl;
      this.owner = paramObject;
    }
    
    public void complete(AtomicOp<?> paramAtomicOp, Object paramObject)
    {
      if (paramObject != null) {
        paramObject = MutexKt.access$getEMPTY_UNLOCKED$p();
      } else if (this.owner == null) {
        paramObject = MutexKt.access$getEMPTY_LOCKED$p();
      } else {
        paramObject = new Empty(this.owner);
      }
      MutexImpl localMutexImpl = this.mutex;
      AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(MutexImpl._state$FU, localMutexImpl, paramAtomicOp, paramObject);
    }
    
    public Object prepare(AtomicOp<?> paramAtomicOp)
    {
      PrepareOp localPrepareOp = new PrepareOp(paramAtomicOp);
      paramAtomicOp = this.mutex;
      if (!AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(MutexImpl._state$FU, paramAtomicOp, MutexKt.access$getEMPTY_UNLOCKED$p(), localPrepareOp)) {
        return MutexKt.access$getLOCK_FAIL$p();
      }
      return localPrepareOp.perform(this.mutex);
    }
    
    @Metadata(d1={"\000\032\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\004\n\002\020\000\n\002\b\002\b\004\030\0002\0020\001B\021\022\n\020\002\032\006\022\002\b\0030\003¢\006\002\020\004J\024\020\007\032\004\030\0010\b2\b\020\t\032\004\030\0010\bH\026R\030\020\002\032\006\022\002\b\0030\003X\004¢\006\b\n\000\032\004\b\005\020\006¨\006\n"}, d2={"Lkotlinx/coroutines/sync/MutexImpl$TryLockDesc$PrepareOp;", "Lkotlinx/coroutines/internal/OpDescriptor;", "atomicOp", "Lkotlinx/coroutines/internal/AtomicOp;", "(Lkotlinx/coroutines/sync/MutexImpl$TryLockDesc;Lkotlinx/coroutines/internal/AtomicOp;)V", "getAtomicOp", "()Lkotlinx/coroutines/internal/AtomicOp;", "perform", "", "affected", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
    private final class PrepareOp
      extends OpDescriptor
    {
      private final AtomicOp<?> atomicOp;
      
      public PrepareOp()
      {
        AtomicOp localAtomicOp;
        this.atomicOp = localAtomicOp;
      }
      
      public AtomicOp<?> getAtomicOp()
      {
        return this.atomicOp;
      }
      
      public Object perform(Object paramObject)
      {
        Object localObject;
        if (getAtomicOp().isDecided()) {
          localObject = MutexKt.access$getEMPTY_UNLOCKED$p();
        } else {
          localObject = getAtomicOp();
        }
        if (paramObject != null)
        {
          paramObject = (MutexImpl)paramObject;
          AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(MutexImpl._state$FU, paramObject, this, localObject);
          return null;
        }
        throw new NullPointerException("null cannot be cast to non-null type kotlinx.coroutines.sync.MutexImpl");
      }
    }
  }
  
  @Metadata(d1={"\000&\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\002\n\002\b\002\n\002\020\000\n\002\b\002\b\002\030\0002\b\022\004\022\0020\0020\001B\r\022\006\020\003\032\0020\004¢\006\002\020\005J\032\020\006\032\0020\0072\006\020\b\032\0020\0022\b\020\t\032\004\030\0010\nH\026J\022\020\013\032\004\030\0010\n2\006\020\b\032\0020\002H\026R\020\020\003\032\0020\0048\006X\004¢\006\002\n\000¨\006\f"}, d2={"Lkotlinx/coroutines/sync/MutexImpl$UnlockOp;", "Lkotlinx/coroutines/internal/AtomicOp;", "Lkotlinx/coroutines/sync/MutexImpl;", "queue", "Lkotlinx/coroutines/sync/MutexImpl$LockedQueue;", "(Lkotlinx/coroutines/sync/MutexImpl$LockedQueue;)V", "complete", "", "affected", "failure", "", "prepare", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  private static final class UnlockOp
    extends AtomicOp<MutexImpl>
  {
    public final MutexImpl.LockedQueue queue;
    
    public UnlockOp(MutexImpl.LockedQueue paramLockedQueue)
    {
      this.queue = paramLockedQueue;
    }
    
    public void complete(MutexImpl paramMutexImpl, Object paramObject)
    {
      if (paramObject == null) {
        paramObject = MutexKt.access$getEMPTY_UNLOCKED$p();
      } else {
        paramObject = this.queue;
      }
      AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(MutexImpl._state$FU, paramMutexImpl, this, paramObject);
    }
    
    public Object prepare(MutexImpl paramMutexImpl)
    {
      if (this.queue.isEmpty()) {
        paramMutexImpl = null;
      } else {
        paramMutexImpl = MutexKt.access$getUNLOCK_FAIL$p();
      }
      return paramMutexImpl;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/sync/MutexImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */