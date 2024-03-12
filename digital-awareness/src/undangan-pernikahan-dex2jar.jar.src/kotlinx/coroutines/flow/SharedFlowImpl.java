package kotlinx.coroutines.flow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.Result.Companion;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CancellableContinuationKt;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.internal.AbstractSharedFlow;
import kotlinx.coroutines.flow.internal.AbstractSharedFlowKt;
import kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot;
import kotlinx.coroutines.flow.internal.FusibleFlow;

@Metadata(d1={"\000\001\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\020\b\n\002\b\002\n\002\030\002\n\002\b\002\n\002\020\021\n\002\020\000\n\002\b\002\n\002\020\t\n\002\b\017\n\002\020 \n\002\b\t\n\002\020\002\n\002\b\004\n\002\030\002\n\002\b\002\n\002\020\001\n\000\n\002\030\002\n\002\b\017\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\030\002\n\002\b\n\n\002\020\013\n\002\b\022\b\020\030\000*\004\b\000\020\0012\b\022\004\022\0020\0030\0022\b\022\004\022\002H\0010\0042\b\022\004\022\002H\0010\0052\b\022\004\022\002H\0010\006:\001hB\035\022\006\020\007\032\0020\b\022\006\020\t\032\0020\b\022\006\020\n\032\0020\013¢\006\002\020\fJ\031\020+\032\0020,2\006\020-\032\0020\003H@ø\001\000¢\006\002\020.J\020\020/\032\0020,2\006\0200\032\00201H\002J\b\0202\032\0020,H\002J\037\0203\032\002042\f\0205\032\b\022\004\022\0028\00006H@ø\001\000¢\006\002\0207J\020\0208\032\0020,2\006\0209\032\0020\022H\002J\b\020:\032\0020\003H\024J\035\020;\032\n\022\006\022\004\030\0010\0030\0162\006\020<\032\0020\bH\024¢\006\002\020=J\b\020>\032\0020,H\002J\031\020?\032\0020,2\006\020@\032\0028\000H@ø\001\000¢\006\002\020AJ\031\020B\032\0020,2\006\020@\032\0028\000H@ø\001\000¢\006\002\020AJ\022\020C\032\0020,2\b\020D\032\004\030\0010\017H\002J1\020E\032\020\022\f\022\n\022\004\022\0020,\030\0010F0\0162\024\020G\032\020\022\f\022\n\022\004\022\0020,\030\0010F0\016H\002¢\006\002\020HJ&\020I\032\b\022\004\022\0028\0000J2\006\020K\032\0020L2\006\020M\032\0020\b2\006\020\n\032\0020\013H\026J\022\020N\032\004\030\0010\0172\006\020O\032\0020\022H\002J7\020P\032\n\022\006\022\004\030\0010\0170\0162\020\020Q\032\f\022\006\022\004\030\0010\017\030\0010\0162\006\020R\032\0020\b2\006\020S\032\0020\bH\002¢\006\002\020TJ\b\020U\032\0020,H\026J\025\020V\032\0020W2\006\020@\032\0028\000H\026¢\006\002\020XJ\025\020Y\032\0020W2\006\020@\032\0028\000H\002¢\006\002\020XJ\025\020Z\032\0020W2\006\020@\032\0028\000H\002¢\006\002\020XJ\020\020[\032\0020\0222\006\020-\032\0020\003H\002J\022\020\\\032\004\030\0010\0172\006\020-\032\0020\003H\002J(\020]\032\0020,2\006\020^\032\0020\0222\006\020_\032\0020\0222\006\020`\032\0020\0222\006\020a\032\0020\022H\002J%\020b\032\020\022\f\022\n\022\004\022\0020,\030\0010F0\0162\006\020c\032\0020\022H\000¢\006\004\bd\020eJ\r\020f\032\0020\022H\000¢\006\002\bgR\032\020\r\032\f\022\006\022\004\030\0010\017\030\0010\016X\016¢\006\004\n\002\020\020R\016\020\t\032\0020\bX\004¢\006\002\n\000R\024\020\021\032\0020\0228BX\004¢\006\006\032\004\b\023\020\024R\016\020\025\032\0020\bX\016¢\006\002\n\000R\024\020\026\032\0020\0228BX\004¢\006\006\032\004\b\027\020\024R\032\020\030\032\0028\0008DX\004¢\006\f\022\004\b\031\020\032\032\004\b\033\020\034R\016\020\035\032\0020\022X\016¢\006\002\n\000R\016\020\n\032\0020\013X\004¢\006\002\n\000R\024\020\036\032\0020\0228BX\004¢\006\006\032\004\b\037\020\024R\016\020 \032\0020\bX\016¢\006\002\n\000R\016\020\007\032\0020\bX\004¢\006\002\n\000R\032\020!\032\b\022\004\022\0028\0000\"8VX\004¢\006\006\032\004\b#\020$R\016\020%\032\0020\022X\016¢\006\002\n\000R\024\020&\032\0020\b8BX\004¢\006\006\032\004\b'\020(R\024\020)\032\0020\b8BX\004¢\006\006\032\004\b*\020(\002\004\n\002\b\031¨\006i"}, d2={"Lkotlinx/coroutines/flow/SharedFlowImpl;", "T", "Lkotlinx/coroutines/flow/internal/AbstractSharedFlow;", "Lkotlinx/coroutines/flow/SharedFlowSlot;", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "Lkotlinx/coroutines/flow/CancellableFlow;", "Lkotlinx/coroutines/flow/internal/FusibleFlow;", "replay", "", "bufferCapacity", "onBufferOverflow", "Lkotlinx/coroutines/channels/BufferOverflow;", "(IILkotlinx/coroutines/channels/BufferOverflow;)V", "buffer", "", "", "[Ljava/lang/Object;", "bufferEndIndex", "", "getBufferEndIndex", "()J", "bufferSize", "head", "getHead", "lastReplayedLocked", "getLastReplayedLocked$annotations", "()V", "getLastReplayedLocked", "()Ljava/lang/Object;", "minCollectorIndex", "queueEndIndex", "getQueueEndIndex", "queueSize", "replayCache", "", "getReplayCache", "()Ljava/util/List;", "replayIndex", "replaySize", "getReplaySize", "()I", "totalSize", "getTotalSize", "awaitValue", "", "slot", "(Lkotlinx/coroutines/flow/SharedFlowSlot;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "cancelEmitter", "emitter", "Lkotlinx/coroutines/flow/SharedFlowImpl$Emitter;", "cleanupTailLocked", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "correctCollectorIndexesOnDropOldest", "newHead", "createSlot", "createSlotArray", "size", "(I)[Lkotlinx/coroutines/flow/SharedFlowSlot;", "dropOldestLocked", "emit", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "emitSuspend", "enqueueLocked", "item", "findSlotsToResumeLocked", "Lkotlin/coroutines/Continuation;", "resumesIn", "([Lkotlin/coroutines/Continuation;)[Lkotlin/coroutines/Continuation;", "fuse", "Lkotlinx/coroutines/flow/Flow;", "context", "Lkotlin/coroutines/CoroutineContext;", "capacity", "getPeekedValueLockedAt", "index", "growBuffer", "curBuffer", "curSize", "newSize", "([Ljava/lang/Object;II)[Ljava/lang/Object;", "resetReplayCache", "tryEmit", "", "(Ljava/lang/Object;)Z", "tryEmitLocked", "tryEmitNoCollectorsLocked", "tryPeekLocked", "tryTakeValue", "updateBufferLocked", "newReplayIndex", "newMinCollectorIndex", "newBufferEndIndex", "newQueueEndIndex", "updateCollectorIndexLocked", "oldIndex", "updateCollectorIndexLocked$kotlinx_coroutines_core", "(J)[Lkotlin/coroutines/Continuation;", "updateNewCollectorIndexLocked", "updateNewCollectorIndexLocked$kotlinx_coroutines_core", "Emitter", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public class SharedFlowImpl<T>
  extends AbstractSharedFlow<SharedFlowSlot>
  implements MutableSharedFlow<T>, CancellableFlow<T>, FusibleFlow<T>
{
  private Object[] buffer;
  private final int bufferCapacity;
  private int bufferSize;
  private long minCollectorIndex;
  private final BufferOverflow onBufferOverflow;
  private int queueSize;
  private final int replay;
  private long replayIndex;
  
  public SharedFlowImpl(int paramInt1, int paramInt2, BufferOverflow paramBufferOverflow)
  {
    this.replay = paramInt1;
    this.bufferCapacity = paramInt2;
    this.onBufferOverflow = paramBufferOverflow;
  }
  
  private final Object awaitValue(SharedFlowSlot paramSharedFlowSlot, Continuation<? super Unit> paramContinuation)
  {
    CancellableContinuationImpl localCancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(paramContinuation), 1);
    localCancellableContinuationImpl.initCancellability();
    Object localObject = (CancellableContinuation)localCancellableContinuationImpl;
    try
    {
      if (access$tryPeekLocked(this, paramSharedFlowSlot) < 0L)
      {
        paramSharedFlowSlot.cont = ((Continuation)localObject);
        paramSharedFlowSlot.cont = ((Continuation)localObject);
      }
      else
      {
        localObject = (Continuation)localObject;
        paramSharedFlowSlot = Result.Companion;
        ((Continuation)localObject).resumeWith(Result.constructor-impl(Unit.INSTANCE));
      }
      paramSharedFlowSlot = Unit.INSTANCE;
      paramSharedFlowSlot = localCancellableContinuationImpl.getResult();
      if (paramSharedFlowSlot == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
        DebugProbesKt.probeCoroutineSuspended(paramContinuation);
      }
      if (paramSharedFlowSlot == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
        return paramSharedFlowSlot;
      }
      return Unit.INSTANCE;
    }
    finally {}
  }
  
  private final void cancelEmitter(Emitter paramEmitter)
  {
    try
    {
      long l1 = paramEmitter.index;
      long l2 = getHead();
      if (l1 < l2) {
        return;
      }
      Object[] arrayOfObject = this.buffer;
      Intrinsics.checkNotNull(arrayOfObject);
      Object localObject = SharedFlowKt.access$getBufferAt(arrayOfObject, paramEmitter.index);
      if (localObject != paramEmitter) {
        return;
      }
      SharedFlowKt.access$setBufferAt(arrayOfObject, paramEmitter.index, SharedFlowKt.NO_VALUE);
      cleanupTailLocked();
      paramEmitter = Unit.INSTANCE;
      return;
    }
    finally {}
  }
  
  private final void cleanupTailLocked()
  {
    if ((this.bufferCapacity == 0) && (this.queueSize <= 1)) {
      return;
    }
    Object[] arrayOfObject = this.buffer;
    Intrinsics.checkNotNull(arrayOfObject);
    while ((this.queueSize > 0) && (SharedFlowKt.access$getBufferAt(arrayOfObject, getHead() + getTotalSize() - 1L) == SharedFlowKt.NO_VALUE))
    {
      this.queueSize -= 1;
      SharedFlowKt.access$setBufferAt(arrayOfObject, getHead() + getTotalSize(), null);
    }
  }
  
  private final void correctCollectorIndexesOnDropOldest(long paramLong)
  {
    Object localObject = (AbstractSharedFlow)this;
    if (AbstractSharedFlow.access$getNCollectors((AbstractSharedFlow)localObject) != 0)
    {
      localObject = AbstractSharedFlow.access$getSlots((AbstractSharedFlow)localObject);
      if (localObject != null)
      {
        int i = 0;
        int j = localObject.length;
        while (i < j)
        {
          SharedFlowSlot localSharedFlowSlot = localObject[i];
          i++;
          if (localSharedFlowSlot != null)
          {
            localSharedFlowSlot = (SharedFlowSlot)localSharedFlowSlot;
            if ((localSharedFlowSlot.index >= 0L) && (localSharedFlowSlot.index < paramLong)) {
              localSharedFlowSlot.index = paramLong;
            }
          }
        }
      }
    }
    this.minCollectorIndex = paramLong;
  }
  
  private final void dropOldestLocked()
  {
    Object[] arrayOfObject = this.buffer;
    Intrinsics.checkNotNull(arrayOfObject);
    SharedFlowKt.access$setBufferAt(arrayOfObject, getHead(), null);
    this.bufferSize -= 1;
    long l = getHead() + 1L;
    if (this.replayIndex < l) {
      this.replayIndex = l;
    }
    if (this.minCollectorIndex < l) {
      correctCollectorIndexesOnDropOldest(l);
    }
    if (DebugKt.getASSERTIONS_ENABLED())
    {
      int i;
      if (getHead() == l) {
        i = 1;
      } else {
        i = 0;
      }
      if (i == 0) {
        throw new AssertionError();
      }
    }
  }
  
  private final Object emitSuspend(T paramT, Continuation<? super Unit> paramContinuation)
  {
    CancellableContinuationImpl localCancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(paramContinuation), 1);
    localCancellableContinuationImpl.initCancellability();
    CancellableContinuation localCancellableContinuation = (CancellableContinuation)localCancellableContinuationImpl;
    Continuation[] arrayOfContinuation = AbstractSharedFlowKt.EMPTY_RESUMES;
    try
    {
      Object localObject;
      if (access$tryEmitLocked(this, paramT))
      {
        localObject = (Continuation)localCancellableContinuation;
        paramT = Result.Companion;
        ((Continuation)localObject).resumeWith(Result.constructor-impl(Unit.INSTANCE));
        paramT = access$findSlotsToResumeLocked(this, arrayOfContinuation);
        localObject = null;
      }
      else
      {
        localObject = new kotlinx/coroutines/flow/SharedFlowImpl$Emitter;
        long l = access$getHead(this);
        ((Emitter)localObject).<init>(this, access$getTotalSize(this) + l, paramT, (Continuation)localCancellableContinuation);
        access$enqueueLocked(this, localObject);
        access$setQueueSize$p(this, access$getQueueSize$p(this) + 1);
        paramT = arrayOfContinuation;
        if (access$getBufferCapacity$p(this) == 0) {
          paramT = access$findSlotsToResumeLocked(this, arrayOfContinuation);
        }
      }
      if (localObject != null) {
        CancellableContinuationKt.disposeOnCancellation(localCancellableContinuation, (DisposableHandle)localObject);
      }
      int i = 0;
      int j = paramT.length;
      while (i < j)
      {
        arrayOfContinuation = paramT[i];
        i++;
        if (arrayOfContinuation != null)
        {
          localObject = Result.Companion;
          arrayOfContinuation.resumeWith(Result.constructor-impl(Unit.INSTANCE));
        }
      }
      paramT = localCancellableContinuationImpl.getResult();
      if (paramT == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
        DebugProbesKt.probeCoroutineSuspended(paramContinuation);
      }
      if (paramT == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
        return paramT;
      }
      return Unit.INSTANCE;
    }
    finally {}
  }
  
  private final void enqueueLocked(Object paramObject)
  {
    int i = getTotalSize();
    Object[] arrayOfObject2 = this.buffer;
    Object[] arrayOfObject1;
    if (arrayOfObject2 == null)
    {
      arrayOfObject1 = growBuffer(null, 0, 2);
    }
    else
    {
      arrayOfObject1 = arrayOfObject2;
      if (i >= arrayOfObject2.length) {
        arrayOfObject1 = growBuffer(arrayOfObject2, i, arrayOfObject2.length * 2);
      }
    }
    SharedFlowKt.access$setBufferAt(arrayOfObject1, getHead() + i, paramObject);
  }
  
  private final Continuation<Unit>[] findSlotsToResumeLocked(Continuation<Unit>[] paramArrayOfContinuation)
  {
    Object localObject = paramArrayOfContinuation;
    int j = paramArrayOfContinuation.length;
    paramArrayOfContinuation = (AbstractSharedFlow)this;
    if (AbstractSharedFlow.access$getNCollectors(paramArrayOfContinuation) == 0)
    {
      paramArrayOfContinuation = (Continuation<Unit>[])localObject;
    }
    else
    {
      AbstractSharedFlowSlot[] arrayOfAbstractSharedFlowSlot = AbstractSharedFlow.access$getSlots(paramArrayOfContinuation);
      if (arrayOfAbstractSharedFlowSlot == null)
      {
        paramArrayOfContinuation = (Continuation<Unit>[])localObject;
      }
      else
      {
        int i = 0;
        int k = arrayOfAbstractSharedFlowSlot.length;
        paramArrayOfContinuation = (Continuation<Unit>[])localObject;
        while (i < k)
        {
          localObject = arrayOfAbstractSharedFlowSlot[i];
          i++;
          if (localObject != null)
          {
            localObject = (SharedFlowSlot)localObject;
            Continuation localContinuation = ((SharedFlowSlot)localObject).cont;
            if ((localContinuation != null) && (tryPeekLocked((SharedFlowSlot)localObject) >= 0L))
            {
              if (j >= ((Object[])paramArrayOfContinuation).length)
              {
                paramArrayOfContinuation = Arrays.copyOf((Object[])paramArrayOfContinuation, Math.max(2, ((Object[])paramArrayOfContinuation).length * 2));
                Intrinsics.checkNotNullExpressionValue(paramArrayOfContinuation, "copyOf(this, newSize)");
              }
              ((Continuation[])paramArrayOfContinuation)[j] = localContinuation;
              ((SharedFlowSlot)localObject).cont = null;
              j++;
            }
          }
        }
      }
    }
    return (Continuation[])paramArrayOfContinuation;
  }
  
  private final long getBufferEndIndex()
  {
    return getHead() + this.bufferSize;
  }
  
  private final long getHead()
  {
    return Math.min(this.minCollectorIndex, this.replayIndex);
  }
  
  private final Object getPeekedValueLockedAt(long paramLong)
  {
    Object localObject = this.buffer;
    Intrinsics.checkNotNull(localObject);
    localObject = SharedFlowKt.access$getBufferAt((Object[])localObject, paramLong);
    if ((localObject instanceof Emitter)) {
      localObject = ((Emitter)localObject).value;
    }
    return localObject;
  }
  
  private final long getQueueEndIndex()
  {
    return getHead() + this.bufferSize + this.queueSize;
  }
  
  private final int getReplaySize()
  {
    return (int)(getHead() + this.bufferSize - this.replayIndex);
  }
  
  private final int getTotalSize()
  {
    return this.bufferSize + this.queueSize;
  }
  
  private final Object[] growBuffer(Object[] paramArrayOfObject, int paramInt1, int paramInt2)
  {
    int j = 0;
    int i;
    if (paramInt2 > 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      Object[] arrayOfObject = new Object[paramInt2];
      this.buffer = arrayOfObject;
      if (paramArrayOfObject == null) {
        return arrayOfObject;
      }
      long l = getHead();
      for (paramInt2 = j; paramInt2 < paramInt1; paramInt2 = i)
      {
        i = paramInt2 + 1;
        SharedFlowKt.access$setBufferAt(arrayOfObject, paramInt2 + l, SharedFlowKt.access$getBufferAt(paramArrayOfObject, paramInt2 + l));
      }
      return arrayOfObject;
    }
    throw new IllegalStateException("Buffer size overflow".toString());
  }
  
  private final boolean tryEmitLocked(T paramT)
  {
    if (getNCollectors() == 0) {
      return tryEmitNoCollectorsLocked(paramT);
    }
    if ((this.bufferSize >= this.bufferCapacity) && (this.minCollectorIndex <= this.replayIndex))
    {
      BufferOverflow localBufferOverflow = this.onBufferOverflow;
      switch (WhenMappings.$EnumSwitchMapping$0[localBufferOverflow.ordinal()])
      {
      default: 
        break;
      case 2: 
        return true;
      case 1: 
        return false;
      }
    }
    enqueueLocked(paramT);
    int i = this.bufferSize + 1;
    this.bufferSize = i;
    if (i > this.bufferCapacity) {
      dropOldestLocked();
    }
    if (getReplaySize() > this.replay) {
      updateBufferLocked(this.replayIndex + 1L, this.minCollectorIndex, getBufferEndIndex(), getQueueEndIndex());
    }
    return true;
  }
  
  private final boolean tryEmitNoCollectorsLocked(T paramT)
  {
    if (DebugKt.getASSERTIONS_ENABLED())
    {
      if (getNCollectors() == 0) {
        i = 1;
      } else {
        i = 0;
      }
      if (i == 0) {
        throw new AssertionError();
      }
    }
    if (this.replay == 0) {
      return true;
    }
    enqueueLocked(paramT);
    int i = this.bufferSize + 1;
    this.bufferSize = i;
    if (i > this.replay) {
      dropOldestLocked();
    }
    this.minCollectorIndex = (getHead() + this.bufferSize);
    return true;
  }
  
  private final long tryPeekLocked(SharedFlowSlot paramSharedFlowSlot)
  {
    long l = paramSharedFlowSlot.index;
    if (l < getBufferEndIndex()) {
      return l;
    }
    if (this.bufferCapacity > 0) {
      return -1L;
    }
    if (l > getHead()) {
      return -1L;
    }
    if (this.queueSize == 0) {
      return -1L;
    }
    return l;
  }
  
  private final Object tryTakeValue(SharedFlowSlot paramSharedFlowSlot)
  {
    Object localObject2 = AbstractSharedFlowKt.EMPTY_RESUMES;
    try
    {
      long l1 = tryPeekLocked(paramSharedFlowSlot);
      Object localObject1;
      if (l1 < 0L)
      {
        localObject1 = SharedFlowKt.NO_VALUE;
        paramSharedFlowSlot = (SharedFlowSlot)localObject2;
      }
      else
      {
        long l2 = paramSharedFlowSlot.index;
        localObject1 = getPeekedValueLockedAt(l1);
        paramSharedFlowSlot.index = (1L + l1);
        paramSharedFlowSlot = updateCollectorIndexLocked$kotlinx_coroutines_core(l2);
      }
      int i = 0;
      int j = paramSharedFlowSlot.length;
      while (i < j)
      {
        Object localObject3 = paramSharedFlowSlot[i];
        i++;
        if (localObject3 != null)
        {
          localObject2 = Result.Companion;
          ((Continuation)localObject3).resumeWith(Result.constructor-impl(Unit.INSTANCE));
        }
      }
      return localObject1;
    }
    finally {}
  }
  
  private final void updateBufferLocked(long paramLong1, long paramLong2, long paramLong3, long paramLong4)
  {
    long l3 = Math.min(paramLong2, paramLong1);
    boolean bool = DebugKt.getASSERTIONS_ENABLED();
    int j = 1;
    int i;
    if (bool)
    {
      if (l3 >= getHead()) {
        i = 1;
      } else {
        i = 0;
      }
      if (i == 0) {
        throw new AssertionError();
      }
    }
    long l1 = getHead();
    for (;;)
    {
      long l2 = l1;
      if (l2 >= l3) {
        break;
      }
      l1 = l2 + 1L;
      Object[] arrayOfObject = this.buffer;
      Intrinsics.checkNotNull(arrayOfObject);
      SharedFlowKt.access$setBufferAt(arrayOfObject, l2, null);
    }
    this.replayIndex = paramLong1;
    this.minCollectorIndex = paramLong2;
    this.bufferSize = ((int)(paramLong3 - l3));
    this.queueSize = ((int)(paramLong4 - paramLong3));
    if (DebugKt.getASSERTIONS_ENABLED())
    {
      if (this.bufferSize >= 0) {
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
      if (this.queueSize >= 0) {
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
      if (this.replayIndex <= getHead() + this.bufferSize) {
        i = j;
      } else {
        i = 0;
      }
      if (i == 0) {
        throw new AssertionError();
      }
    }
  }
  
  public Object collect(FlowCollector<? super T> paramFlowCollector, Continuation<?> paramContinuation)
  {
    return collect$suspendImpl(this, paramFlowCollector, paramContinuation);
  }
  
  protected SharedFlowSlot createSlot()
  {
    return new SharedFlowSlot();
  }
  
  protected SharedFlowSlot[] createSlotArray(int paramInt)
  {
    return new SharedFlowSlot[paramInt];
  }
  
  public Object emit(T paramT, Continuation<? super Unit> paramContinuation)
  {
    return emit$suspendImpl(this, paramT, paramContinuation);
  }
  
  public Flow<T> fuse(CoroutineContext paramCoroutineContext, int paramInt, BufferOverflow paramBufferOverflow)
  {
    return SharedFlowKt.fuseSharedFlow((SharedFlow)this, paramCoroutineContext, paramInt, paramBufferOverflow);
  }
  
  protected final T getLastReplayedLocked()
  {
    Object[] arrayOfObject = this.buffer;
    Intrinsics.checkNotNull(arrayOfObject);
    return (T)SharedFlowKt.access$getBufferAt(arrayOfObject, this.replayIndex + getReplaySize() - 1L);
  }
  
  public List<T> getReplayCache()
  {
    try
    {
      int k = getReplaySize();
      if (k == 0)
      {
        localObject1 = CollectionsKt.emptyList();
        return (List<T>)localObject1;
      }
      Object localObject1 = new java/util/ArrayList;
      ((ArrayList)localObject1).<init>(k);
      Object[] arrayOfObject = this.buffer;
      Intrinsics.checkNotNull(arrayOfObject);
      int i = 0;
      for (;;)
      {
        int j = i;
        if (j >= k) {
          break;
        }
        i = j + 1;
        ((Collection)localObject1).add(SharedFlowKt.access$getBufferAt(arrayOfObject, this.replayIndex + j));
      }
      return (List)localObject1;
    }
    finally {}
  }
  
  public void resetReplayCache()
  {
    try
    {
      updateBufferLocked(getBufferEndIndex(), this.minCollectorIndex, getBufferEndIndex(), getQueueEndIndex());
      Unit localUnit = Unit.INSTANCE;
      return;
    }
    finally {}
  }
  
  public boolean tryEmit(T paramT)
  {
    Continuation[] arrayOfContinuation = AbstractSharedFlowKt.EMPTY_RESUMES;
    try
    {
      boolean bool = tryEmitLocked(paramT);
      int i = 0;
      if (bool)
      {
        paramT = findSlotsToResumeLocked(arrayOfContinuation);
        bool = true;
      }
      else
      {
        bool = false;
        paramT = arrayOfContinuation;
      }
      int j = paramT.length;
      while (i < j)
      {
        arrayOfContinuation = paramT[i];
        i++;
        if (arrayOfContinuation != null)
        {
          Result.Companion localCompanion = Result.Companion;
          arrayOfContinuation.resumeWith(Result.constructor-impl(Unit.INSTANCE));
        }
      }
      return bool;
    }
    finally {}
  }
  
  public final Continuation<Unit>[] updateCollectorIndexLocked$kotlinx_coroutines_core(long paramLong)
  {
    if (DebugKt.getASSERTIONS_ENABLED())
    {
      if (paramLong >= this.minCollectorIndex) {
        i = 1;
      } else {
        i = 0;
      }
      if (i == 0) {
        throw new AssertionError();
      }
    }
    if (paramLong > this.minCollectorIndex) {
      return AbstractSharedFlowKt.EMPTY_RESUMES;
    }
    long l1 = getHead();
    long l2 = this.bufferSize + l1;
    paramLong = l2;
    if (this.bufferCapacity == 0)
    {
      paramLong = l2;
      if (this.queueSize > 0) {
        paramLong = l2 + 1L;
      }
    }
    Object localObject1 = (AbstractSharedFlow)this;
    long l3;
    int j;
    if (AbstractSharedFlow.access$getNCollectors((AbstractSharedFlow)localObject1) == 0)
    {
      l3 = l1;
      l1 = paramLong;
    }
    else
    {
      localObject1 = AbstractSharedFlow.access$getSlots((AbstractSharedFlow)localObject1);
      if (localObject1 != null)
      {
        j = localObject1.length;
        i = 0;
        while (i < j)
        {
          localObject2 = localObject1[i];
          i++;
          if (localObject2 != null)
          {
            localObject2 = (SharedFlowSlot)localObject2;
            l2 = paramLong;
            if (((SharedFlowSlot)localObject2).index >= 0L)
            {
              l2 = paramLong;
              if (((SharedFlowSlot)localObject2).index < paramLong) {
                l2 = ((SharedFlowSlot)localObject2).index;
              }
            }
            paramLong = l2;
          }
        }
      }
      l3 = l1;
      l1 = paramLong;
    }
    if (DebugKt.getASSERTIONS_ENABLED())
    {
      if (l1 >= this.minCollectorIndex) {
        i = 1;
      } else {
        i = 0;
      }
      if (i == 0) {
        throw new AssertionError();
      }
    }
    if (l1 <= this.minCollectorIndex) {
      return AbstractSharedFlowKt.EMPTY_RESUMES;
    }
    l2 = getBufferEndIndex();
    if (getNCollectors() > 0)
    {
      i = (int)(l2 - l1);
      i = Math.min(this.queueSize, this.bufferCapacity - i);
    }
    else
    {
      i = this.queueSize;
    }
    localObject1 = AbstractSharedFlowKt.EMPTY_RESUMES;
    long l5 = l2 + this.queueSize;
    if (i > 0)
    {
      localObject1 = new Continuation[i];
      j = 0;
      localObject2 = this.buffer;
      Intrinsics.checkNotNull(localObject2);
      paramLong = l2;
      while (l2 < l5)
      {
        long l4 = l2 + 1L;
        Object localObject3 = SharedFlowKt.access$getBufferAt((Object[])localObject2, l2);
        if (localObject3 != SharedFlowKt.NO_VALUE)
        {
          if (localObject3 != null)
          {
            Emitter localEmitter = (Emitter)localObject3;
            int k = j + 1;
            localObject1[j] = ((Emitter)localObject3).cont;
            SharedFlowKt.access$setBufferAt((Object[])localObject2, l2, SharedFlowKt.NO_VALUE);
            SharedFlowKt.access$setBufferAt((Object[])localObject2, paramLong, ((Emitter)localObject3).value);
            paramLong += 1L;
            if (k >= i) {
              break;
            }
            j = k;
            l2 = l4;
          }
          else
          {
            throw new NullPointerException("null cannot be cast to non-null type kotlinx.coroutines.flow.SharedFlowImpl.Emitter");
          }
        }
        else {
          l2 = l4;
        }
      }
    }
    else
    {
      paramLong = l2;
    }
    int i = (int)(paramLong - l3);
    if (getNCollectors() == 0) {
      l1 = paramLong;
    }
    l2 = Math.max(this.replayIndex, paramLong - Math.min(this.replay, i));
    if ((this.bufferCapacity == 0) && (l2 < l5))
    {
      localObject2 = this.buffer;
      Intrinsics.checkNotNull(localObject2);
      if (Intrinsics.areEqual(SharedFlowKt.access$getBufferAt((Object[])localObject2, l2), SharedFlowKt.NO_VALUE))
      {
        paramLong += 1L;
        l2 += 1L;
      }
    }
    updateBufferLocked(l2, l1, paramLong, l5);
    cleanupTailLocked();
    if (localObject1.length == 0) {
      i = 1;
    } else {
      i = 0;
    }
    Object localObject2 = localObject1;
    if ((i ^ 0x1) != 0) {
      localObject2 = findSlotsToResumeLocked((Continuation[])localObject1);
    }
    return (Continuation<Unit>[])localObject2;
  }
  
  public final long updateNewCollectorIndexLocked$kotlinx_coroutines_core()
  {
    long l = this.replayIndex;
    if (l < this.minCollectorIndex) {
      this.minCollectorIndex = l;
    }
    return l;
  }
  
  @Metadata(d1={"\000(\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\t\n\000\n\002\020\000\n\000\n\002\030\002\n\002\020\002\n\002\b\003\b\002\030\0002\0020\001B1\022\n\020\002\032\006\022\002\b\0030\003\022\006\020\004\032\0020\005\022\b\020\006\032\004\030\0010\007\022\f\020\b\032\b\022\004\022\0020\n0\t¢\006\002\020\013J\b\020\f\032\0020\nH\026R\026\020\b\032\b\022\004\022\0020\n0\t8\006X\004¢\006\002\n\000R\024\020\002\032\006\022\002\b\0030\0038\006X\004¢\006\002\n\000R\022\020\004\032\0020\0058\006@\006X\016¢\006\002\n\000R\022\020\006\032\004\030\0010\0078\006X\004¢\006\002\n\000¨\006\r"}, d2={"Lkotlinx/coroutines/flow/SharedFlowImpl$Emitter;", "Lkotlinx/coroutines/DisposableHandle;", "flow", "Lkotlinx/coroutines/flow/SharedFlowImpl;", "index", "", "value", "", "cont", "Lkotlin/coroutines/Continuation;", "", "(Lkotlinx/coroutines/flow/SharedFlowImpl;JLjava/lang/Object;Lkotlin/coroutines/Continuation;)V", "dispose", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  private static final class Emitter
    implements DisposableHandle
  {
    public final Continuation<Unit> cont;
    public final SharedFlowImpl<?> flow;
    public long index;
    public final Object value;
    
    public Emitter(SharedFlowImpl<?> paramSharedFlowImpl, long paramLong, Object paramObject, Continuation<? super Unit> paramContinuation)
    {
      this.flow = paramSharedFlowImpl;
      this.index = paramLong;
      this.value = paramObject;
      this.cont = paramContinuation;
    }
    
    public void dispose()
    {
      SharedFlowImpl.access$cancelEmitter(this.flow, this);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/flow/SharedFlowImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */