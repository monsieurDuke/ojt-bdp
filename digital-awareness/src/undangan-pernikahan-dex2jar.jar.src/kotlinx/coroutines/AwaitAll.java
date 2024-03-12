package kotlinx.coroutines;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000\036\n\002\030\002\n\000\n\002\020\021\n\002\030\002\n\002\b\003\n\002\020 \n\002\b\006\n\002\020\000\b\002\030\000*\004\b\000\020\0012\0020\016:\002\013\fB\035\022\024\020\004\032\020\022\f\b\001\022\b\022\004\022\0028\0000\0030\002¢\006\004\b\005\020\006J\031\020\b\032\b\022\004\022\0028\0000\007H@ø\001\000¢\006\004\b\b\020\tR\"\020\004\032\020\022\f\b\001\022\b\022\004\022\0028\0000\0030\0028\002X\004¢\006\006\n\004\b\004\020\n\002\004\n\002\b\031¨\006\r"}, d2={"Lkotlinx/coroutines/AwaitAll;", "T", "", "Lkotlinx/coroutines/Deferred;", "deferreds", "<init>", "([Lkotlinx/coroutines/Deferred;)V", "", "await", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "[Lkotlinx/coroutines/Deferred;", "AwaitAllNode", "DisposeHandlersOnCancel", "kotlinx-coroutines-core", ""}, k=1, mv={1, 6, 0}, xi=48)
final class AwaitAll<T>
{
  static final AtomicIntegerFieldUpdater notCompletedCount$FU = AtomicIntegerFieldUpdater.newUpdater(AwaitAll.class, "notCompletedCount");
  private final Deferred<T>[] deferreds;
  volatile int notCompletedCount;
  
  public AwaitAll(Deferred<? extends T>[] paramArrayOfDeferred)
  {
    this.deferreds = paramArrayOfDeferred;
    this.notCompletedCount = paramArrayOfDeferred.length;
  }
  
  public final Object await(Continuation<? super List<? extends T>> paramContinuation)
  {
    Object localObject1 = new CancellableContinuationImpl(IntrinsicsKt.intercepted(paramContinuation), 1);
    ((CancellableContinuationImpl)localObject1).initCancellability();
    CancellableContinuation localCancellableContinuation = (CancellableContinuation)localObject1;
    int k = access$getDeferreds$p(this).length;
    AwaitAllNode[] arrayOfAwaitAllNode = new AwaitAllNode[k];
    int j = 0;
    Object localObject3;
    for (int i = 0; i < k; i++)
    {
      localObject3 = access$getDeferreds$p(this)[i];
      ((Deferred)localObject3).start();
      localObject2 = new AwaitAllNode(localCancellableContinuation);
      ((AwaitAllNode)localObject2).setHandle(((Deferred)localObject3).invokeOnCompletion((Function1)localObject2));
      localObject3 = Unit.INSTANCE;
      arrayOfAwaitAllNode[i] = localObject2;
    }
    Object localObject2 = new DisposeHandlersOnCancel(arrayOfAwaitAllNode);
    k = arrayOfAwaitAllNode.length;
    i = j;
    while (i < k)
    {
      localObject3 = arrayOfAwaitAllNode[i];
      i++;
      ((AwaitAllNode)localObject3).setDisposer((DisposeHandlersOnCancel)localObject2);
    }
    if (localCancellableContinuation.isCompleted()) {
      ((DisposeHandlersOnCancel)localObject2).disposeAll();
    } else {
      localCancellableContinuation.invokeOnCancellation((Function1)localObject2);
    }
    localObject1 = ((CancellableContinuationImpl)localObject1).getResult();
    if (localObject1 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      DebugProbesKt.probeCoroutineSuspended(paramContinuation);
    }
    return localObject1;
  }
  
  @Metadata(d1={"\0006\n\002\030\002\n\002\030\002\n\002\020 \n\002\b\003\n\002\020\003\n\000\n\002\020\002\n\002\b\003\n\002\030\002\n\002\030\002\n\002\b\006\n\002\030\002\n\002\b\007\n\002\030\002\b\004\030\0002\0020\034B\033\022\022\020\003\032\016\022\n\022\b\022\004\022\0028\0000\0020\001¢\006\004\b\004\020\005J\032\020\t\032\0020\b2\b\020\007\032\004\030\0010\006H\002¢\006\004\b\t\020\nR \020\003\032\016\022\n\022\b\022\004\022\0028\0000\0020\0018\002X\004¢\006\006\n\004\b\003\020\013R<\020\023\032\016\030\0010\fR\b\022\004\022\0028\0000\r2\022\020\016\032\016\030\0010\fR\b\022\004\022\0028\0000\r8F@FX\016¢\006\f\032\004\b\017\020\020\"\004\b\021\020\022R\"\020\025\032\0020\0248\006@\006X.¢\006\022\n\004\b\025\020\026\032\004\b\027\020\030\"\004\b\031\020\032¨\006\033"}, d2={"Lkotlinx/coroutines/AwaitAll$AwaitAllNode;", "Lkotlinx/coroutines/CancellableContinuation;", "", "continuation", "<init>", "(Lkotlinx/coroutines/AwaitAll;Lkotlinx/coroutines/CancellableContinuation;)V", "", "cause", "", "invoke", "(Ljava/lang/Throwable;)V", "Lkotlinx/coroutines/CancellableContinuation;", "Lkotlinx/coroutines/AwaitAll$DisposeHandlersOnCancel;", "Lkotlinx/coroutines/AwaitAll;", "value", "getDisposer", "()Lkotlinx/coroutines/AwaitAll$DisposeHandlersOnCancel;", "setDisposer", "(Lkotlinx/coroutines/AwaitAll$DisposeHandlersOnCancel;)V", "disposer", "Lkotlinx/coroutines/DisposableHandle;", "handle", "Lkotlinx/coroutines/DisposableHandle;", "getHandle", "()Lkotlinx/coroutines/DisposableHandle;", "setHandle", "(Lkotlinx/coroutines/DisposableHandle;)V", "kotlinx-coroutines-core", "Lkotlinx/coroutines/JobNode;"}, k=1, mv={1, 6, 0}, xi=48)
  private final class AwaitAllNode
    extends JobNode
  {
    private volatile Object _disposer;
    private final CancellableContinuation<List<? extends T>> continuation;
    public DisposableHandle handle;
    
    public AwaitAllNode()
    {
      CancellableContinuation localCancellableContinuation;
      this.continuation = localCancellableContinuation;
      this._disposer = null;
    }
    
    public final AwaitAll<T>.DisposeHandlersOnCancel getDisposer()
    {
      return (AwaitAll.DisposeHandlersOnCancel)this._disposer;
    }
    
    public final DisposableHandle getHandle()
    {
      DisposableHandle localDisposableHandle = this.handle;
      if (localDisposableHandle != null) {
        return localDisposableHandle;
      }
      Intrinsics.throwUninitializedPropertyAccessException("handle");
      return null;
    }
    
    public void invoke(Throwable paramThrowable)
    {
      if (paramThrowable != null)
      {
        paramThrowable = this.continuation.tryResumeWithException(paramThrowable);
        if (paramThrowable != null)
        {
          this.continuation.completeResume(paramThrowable);
          paramThrowable = getDisposer();
          if (paramThrowable != null) {
            paramThrowable.disposeAll();
          }
        }
      }
      else
      {
        paramThrowable = AwaitAll.this;
        if (AwaitAll.notCompletedCount$FU.decrementAndGet(paramThrowable) == 0)
        {
          paramThrowable = (Continuation)this.continuation;
          Object localObject = Result.Companion;
          Deferred[] arrayOfDeferred = AwaitAll.access$getDeferreds$p(AwaitAll.this);
          localObject = (Collection)new ArrayList(arrayOfDeferred.length);
          int i = 0;
          int j = arrayOfDeferred.length;
          while (i < j)
          {
            Deferred localDeferred = arrayOfDeferred[i];
            i++;
            ((Collection)localObject).add(localDeferred.getCompleted());
          }
          localObject = (List)localObject;
          paramThrowable.resumeWith(Result.constructor-impl(localObject));
        }
      }
    }
    
    public final void setDisposer(AwaitAll<T>.DisposeHandlersOnCancel paramAwaitAll)
    {
      this._disposer = paramAwaitAll;
    }
    
    public final void setHandle(DisposableHandle paramDisposableHandle)
    {
      this.handle = paramDisposableHandle;
    }
  }
  
  @Metadata(d1={"\000.\n\002\030\002\n\002\030\002\n\000\n\002\020\021\n\002\030\002\n\002\030\002\n\002\b\003\n\002\020\002\n\002\b\002\n\002\020\003\n\000\n\002\020\016\n\000\b\004\030\0002\0020\001B\035\022\026\020\002\032\022\022\016\022\f0\004R\b\022\004\022\0028\0000\0050\003¢\006\002\020\006J\006\020\b\032\0020\tJ\023\020\n\032\0020\t2\b\020\013\032\004\030\0010\fH\002J\b\020\r\032\0020\016H\026R \020\002\032\022\022\016\022\f0\004R\b\022\004\022\0028\0000\0050\003X\004¢\006\004\n\002\020\007¨\006\017"}, d2={"Lkotlinx/coroutines/AwaitAll$DisposeHandlersOnCancel;", "Lkotlinx/coroutines/CancelHandler;", "nodes", "", "Lkotlinx/coroutines/AwaitAll$AwaitAllNode;", "Lkotlinx/coroutines/AwaitAll;", "(Lkotlinx/coroutines/AwaitAll;[Lkotlinx/coroutines/AwaitAll$AwaitAllNode;)V", "[Lkotlinx/coroutines/AwaitAll$AwaitAllNode;", "disposeAll", "", "invoke", "cause", "", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  private final class DisposeHandlersOnCancel
    extends CancelHandler
  {
    private final AwaitAll<T>[].AwaitAllNode nodes;
    
    public DisposeHandlersOnCancel()
    {
      AwaitAll.AwaitAllNode[] arrayOfAwaitAllNode;
      this.nodes = arrayOfAwaitAllNode;
    }
    
    public final void disposeAll()
    {
      AwaitAll.AwaitAllNode[] arrayOfAwaitAllNode = this.nodes;
      int j = arrayOfAwaitAllNode.length;
      int i = 0;
      while (i < j)
      {
        AwaitAll.AwaitAllNode localAwaitAllNode = arrayOfAwaitAllNode[i];
        i++;
        localAwaitAllNode.getHandle().dispose();
      }
    }
    
    public void invoke(Throwable paramThrowable)
    {
      disposeAll();
    }
    
    public String toString()
    {
      return "DisposeHandlersOnCancel[" + this.nodes + ']';
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/AwaitAll.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */