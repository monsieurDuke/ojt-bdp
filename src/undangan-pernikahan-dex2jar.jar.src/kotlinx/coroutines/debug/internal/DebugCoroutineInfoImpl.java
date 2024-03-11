package kotlinx.coroutines.debug.internal;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequenceScope;
import kotlin.sequences.SequencesKt;

@Metadata(d1={"\000\\\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\t\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\016\n\002\b\005\n\002\020 \n\002\030\002\n\002\b\t\n\002\030\002\n\002\b\006\n\002\020\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\b\000\030\0002\0020\001B!\022\b\020\002\032\004\030\0010\003\022\b\020\004\032\004\030\0010\005\022\006\020\006\032\0020\007¢\006\002\020\bJ\016\020\023\032\b\022\004\022\0020\0250\024H\002J\f\020#\032\b\022\004\022\0020\0250\024J\b\020$\032\0020\016H\026J!\020%\032\0020&2\006\020 \032\0020\0162\n\020'\032\006\022\002\b\0030(H\000¢\006\002\b)J%\020*\032\0020&*\b\022\004\022\0020\0250+2\b\020'\032\004\030\0010\fHPø\001\000¢\006\002\020,R\026\020\t\032\n\022\006\022\004\030\0010\0030\nX\004¢\006\002\n\000R\026\020\013\032\n\022\004\022\0020\f\030\0010\nX\016¢\006\002\n\000R\016\020\r\032\0020\016X\016¢\006\002\n\000R\023\020\002\032\004\030\0010\0038F¢\006\006\032\004\b\017\020\020R\023\020\004\032\004\030\0010\005¢\006\b\n\000\032\004\b\021\020\022R\027\020\023\032\b\022\004\022\0020\0250\0248F¢\006\006\032\004\b\026\020\027R(\020\031\032\004\030\0010\f2\b\020\030\032\004\030\0010\f8@@@X\016¢\006\f\032\004\b\032\020\033\"\004\b\034\020\035R\024\020\036\032\004\030\0010\0378\000@\000X\016¢\006\002\n\000R\020\020\006\032\0020\0078\000X\004¢\006\002\n\000R\021\020 \032\0020\0168F¢\006\006\032\004\b!\020\"\002\004\n\002\b\031¨\006-"}, d2={"Lkotlinx/coroutines/debug/internal/DebugCoroutineInfoImpl;", "", "context", "Lkotlin/coroutines/CoroutineContext;", "creationStackBottom", "Lkotlinx/coroutines/debug/internal/StackTraceFrame;", "sequenceNumber", "", "(Lkotlin/coroutines/CoroutineContext;Lkotlinx/coroutines/debug/internal/StackTraceFrame;J)V", "_context", "Ljava/lang/ref/WeakReference;", "_lastObservedFrame", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "_state", "", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "getCreationStackBottom", "()Lkotlinx/coroutines/debug/internal/StackTraceFrame;", "creationStackTrace", "", "Ljava/lang/StackTraceElement;", "getCreationStackTrace", "()Ljava/util/List;", "value", "lastObservedFrame", "getLastObservedFrame$kotlinx_coroutines_core", "()Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "setLastObservedFrame$kotlinx_coroutines_core", "(Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;)V", "lastObservedThread", "Ljava/lang/Thread;", "state", "getState", "()Ljava/lang/String;", "lastObservedStackTrace", "toString", "updateState", "", "frame", "Lkotlin/coroutines/Continuation;", "updateState$kotlinx_coroutines_core", "yieldFrames", "Lkotlin/sequences/SequenceScope;", "(Lkotlin/sequences/SequenceScope;Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class DebugCoroutineInfoImpl
{
  private final WeakReference<CoroutineContext> _context;
  private WeakReference<CoroutineStackFrame> _lastObservedFrame;
  private String _state;
  private final StackTraceFrame creationStackBottom;
  public Thread lastObservedThread;
  public final long sequenceNumber;
  
  public DebugCoroutineInfoImpl(CoroutineContext paramCoroutineContext, StackTraceFrame paramStackTraceFrame, long paramLong)
  {
    this.creationStackBottom = paramStackTraceFrame;
    this.sequenceNumber = paramLong;
    this._context = new WeakReference(paramCoroutineContext);
    this._state = "CREATED";
  }
  
  private final List<StackTraceElement> creationStackTrace()
  {
    final StackTraceFrame localStackTraceFrame = this.creationStackBottom;
    if (localStackTraceFrame == null) {
      return CollectionsKt.emptyList();
    }
    SequencesKt.toList(SequencesKt.sequence((Function2)new RestrictedSuspendLambda(localStackTraceFrame, null)
    {
      private Object L$0;
      int label;
      final DebugCoroutineInfoImpl this$0;
      
      public final Continuation<Unit> create(Object paramAnonymousObject, Continuation<?> paramAnonymousContinuation)
      {
        paramAnonymousContinuation = new 1(this.this$0, localStackTraceFrame, paramAnonymousContinuation);
        paramAnonymousContinuation.L$0 = paramAnonymousObject;
        return (Continuation)paramAnonymousContinuation;
      }
      
      public final Object invoke(SequenceScope<? super StackTraceElement> paramAnonymousSequenceScope, Continuation<? super Unit> paramAnonymousContinuation)
      {
        return ((1)create(paramAnonymousSequenceScope, paramAnonymousContinuation)).invokeSuspend(Unit.INSTANCE);
      }
      
      public final Object invokeSuspend(Object paramAnonymousObject)
      {
        Object localObject = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label)
        {
        default: 
          throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        case 1: 
          ResultKt.throwOnFailure(paramAnonymousObject);
          break;
        case 0: 
          ResultKt.throwOnFailure(paramAnonymousObject);
          SequenceScope localSequenceScope = (SequenceScope)this.L$0;
          paramAnonymousObject = this.this$0;
          CoroutineStackFrame localCoroutineStackFrame = localStackTraceFrame.getCallerFrame();
          Continuation localContinuation = (Continuation)this;
          this.label = 1;
          if (DebugCoroutineInfoImpl.access$yieldFrames((DebugCoroutineInfoImpl)paramAnonymousObject, localSequenceScope, localCoroutineStackFrame, localContinuation) == localObject) {
            return localObject;
          }
          break;
        }
        return Unit.INSTANCE;
      }
    }));
  }
  
  private final Object yieldFrames(SequenceScope<? super StackTraceElement> paramSequenceScope, CoroutineStackFrame paramCoroutineStackFrame, Continuation<? super Unit> paramContinuation)
  {
    if ((paramContinuation instanceof yieldFrames.1))
    {
      local1 = (yieldFrames.1)paramContinuation;
      if ((local1.label & 0x80000000) != 0)
      {
        local1.label += Integer.MIN_VALUE;
        break label51;
      }
    }
    ContinuationImpl local1 = new ContinuationImpl(paramContinuation)
    {
      Object L$0;
      Object L$1;
      Object L$2;
      int label;
      Object result;
      final DebugCoroutineInfoImpl this$0;
      
      public final Object invokeSuspend(Object paramAnonymousObject)
      {
        this.result = paramAnonymousObject;
        this.label |= 0x80000000;
        return DebugCoroutineInfoImpl.access$yieldFrames(this.this$0, null, null, (Continuation)this);
      }
    };
    label51:
    Object localObject1 = local1.result;
    Object localObject2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
    switch (local1.label)
    {
    default: 
      throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    case 1: 
      paramCoroutineStackFrame = (CoroutineStackFrame)local1.L$2;
      paramContinuation = (SequenceScope)local1.L$1;
      paramSequenceScope = (DebugCoroutineInfoImpl)local1.L$0;
      ResultKt.throwOnFailure(localObject1);
      break;
    case 0: 
      ResultKt.throwOnFailure(localObject1);
      localObject1 = this;
      paramContinuation = paramSequenceScope;
      paramSequenceScope = (SequenceScope<? super StackTraceElement>)localObject1;
    }
    do
    {
      if (paramCoroutineStackFrame == null) {
        return Unit.INSTANCE;
      }
      localObject1 = paramCoroutineStackFrame.getStackTraceElement();
      if (localObject1 != null)
      {
        local1.L$0 = paramSequenceScope;
        local1.L$1 = paramContinuation;
        local1.L$2 = paramCoroutineStackFrame;
        local1.label = 1;
        if (paramContinuation.yield(localObject1, local1) == localObject2) {
          return localObject2;
        }
      }
      paramCoroutineStackFrame = paramCoroutineStackFrame.getCallerFrame();
    } while (paramCoroutineStackFrame != null);
    return Unit.INSTANCE;
  }
  
  public final CoroutineContext getContext()
  {
    return (CoroutineContext)this._context.get();
  }
  
  public final StackTraceFrame getCreationStackBottom()
  {
    return this.creationStackBottom;
  }
  
  public final List<StackTraceElement> getCreationStackTrace()
  {
    return creationStackTrace();
  }
  
  public final CoroutineStackFrame getLastObservedFrame$kotlinx_coroutines_core()
  {
    Object localObject = this._lastObservedFrame;
    if (localObject == null) {
      localObject = null;
    } else {
      localObject = (CoroutineStackFrame)((WeakReference)localObject).get();
    }
    return (CoroutineStackFrame)localObject;
  }
  
  public final String getState()
  {
    return this._state;
  }
  
  public final List<StackTraceElement> lastObservedStackTrace()
  {
    CoroutineStackFrame localCoroutineStackFrame = getLastObservedFrame$kotlinx_coroutines_core();
    if (localCoroutineStackFrame == null) {
      return CollectionsKt.emptyList();
    }
    ArrayList localArrayList = new ArrayList();
    while (localCoroutineStackFrame != null)
    {
      StackTraceElement localStackTraceElement = localCoroutineStackFrame.getStackTraceElement();
      if (localStackTraceElement != null) {
        localArrayList.add(localStackTraceElement);
      }
      localCoroutineStackFrame = localCoroutineStackFrame.getCallerFrame();
    }
    return (List)localArrayList;
  }
  
  public final void setLastObservedFrame$kotlinx_coroutines_core(CoroutineStackFrame paramCoroutineStackFrame)
  {
    if (paramCoroutineStackFrame == null) {
      paramCoroutineStackFrame = null;
    } else {
      paramCoroutineStackFrame = new WeakReference(paramCoroutineStackFrame);
    }
    this._lastObservedFrame = paramCoroutineStackFrame;
  }
  
  public String toString()
  {
    return "DebugCoroutineInfo(state=" + getState() + ",context=" + getContext() + ')';
  }
  
  public final void updateState$kotlinx_coroutines_core(String paramString, Continuation<?> paramContinuation)
  {
    if ((Intrinsics.areEqual(this._state, paramString)) && (Intrinsics.areEqual(paramString, "SUSPENDED")) && (getLastObservedFrame$kotlinx_coroutines_core() != null)) {
      return;
    }
    this._state = paramString;
    boolean bool = paramContinuation instanceof CoroutineStackFrame;
    Object localObject = null;
    if (bool) {
      paramContinuation = (CoroutineStackFrame)paramContinuation;
    } else {
      paramContinuation = null;
    }
    setLastObservedFrame$kotlinx_coroutines_core(paramContinuation);
    if (Intrinsics.areEqual(paramString, "RUNNING"))
    {
      paramString = Thread.currentThread();
    }
    else
    {
      paramString = (Thread)null;
      paramString = (String)localObject;
    }
    this.lastObservedThread = paramString;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/debug/internal/DebugCoroutineInfoImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */