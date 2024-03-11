package kotlin.sequences;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(d1={"\000B\n\002\030\002\n\000\n\002\030\002\n\002\020(\n\002\030\002\n\002\020\002\n\002\b\002\n\002\030\002\n\002\b\013\n\002\020\b\n\002\030\002\n\000\n\002\020\003\n\000\n\002\020\013\n\002\b\005\n\002\030\002\n\002\b\b\b\002\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\0022\b\022\004\022\002H\0010\0032\b\022\004\022\0020\0050\004B\005¢\006\002\020\006J\b\020\026\032\0020\027H\002J\t\020\030\032\0020\031H\002J\016\020\032\032\0028\000H\002¢\006\002\020\033J\r\020\034\032\0028\000H\002¢\006\002\020\033J\036\020\035\032\0020\0052\f\020\036\032\b\022\004\022\0020\0050\037H\026ø\001\000¢\006\002\020 J\031\020!\032\0020\0052\006\020\"\032\0028\000H@ø\001\000¢\006\002\020#J\037\020$\032\0020\0052\f\020%\032\b\022\004\022\0028\0000\003H@ø\001\000¢\006\002\020&R\024\020\007\032\0020\b8VX\004¢\006\006\032\004\b\t\020\nR\026\020\013\032\n\022\004\022\0028\000\030\0010\003X\016¢\006\002\n\000R\"\020\f\032\n\022\004\022\0020\005\030\0010\004X\016¢\006\016\n\000\032\004\b\r\020\016\"\004\b\017\020\020R\022\020\021\032\004\030\0018\000X\016¢\006\004\n\002\020\022R\022\020\023\032\0060\024j\002`\025X\016¢\006\002\n\000\002\004\n\002\b\031¨\006'"}, d2={"Lkotlin/sequences/SequenceBuilderIterator;", "T", "Lkotlin/sequences/SequenceScope;", "", "Lkotlin/coroutines/Continuation;", "", "()V", "context", "Lkotlin/coroutines/CoroutineContext;", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "nextIterator", "nextStep", "getNextStep", "()Lkotlin/coroutines/Continuation;", "setNextStep", "(Lkotlin/coroutines/Continuation;)V", "nextValue", "Ljava/lang/Object;", "state", "", "Lkotlin/sequences/State;", "exceptionalState", "", "hasNext", "", "next", "()Ljava/lang/Object;", "nextNotReady", "resumeWith", "result", "Lkotlin/Result;", "(Ljava/lang/Object;)V", "yield", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "yieldAll", "iterator", "(Ljava/util/Iterator;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
final class SequenceBuilderIterator<T>
  extends SequenceScope<T>
  implements Iterator<T>, Continuation<Unit>, KMappedMarker
{
  private Iterator<? extends T> nextIterator;
  private Continuation<? super Unit> nextStep;
  private T nextValue;
  private int state;
  
  private final Throwable exceptionalState()
  {
    Throwable localThrowable;
    switch (this.state)
    {
    default: 
      localThrowable = (Throwable)new IllegalStateException("Unexpected state of the iterator: " + this.state);
      break;
    case 5: 
      localThrowable = (Throwable)new IllegalStateException("Iterator has failed.");
      break;
    case 4: 
      localThrowable = (Throwable)new NoSuchElementException();
    }
    return localThrowable;
  }
  
  private final T nextNotReady()
  {
    if (hasNext()) {
      return (T)next();
    }
    throw new NoSuchElementException();
  }
  
  public CoroutineContext getContext()
  {
    return (CoroutineContext)EmptyCoroutineContext.INSTANCE;
  }
  
  public final Continuation<Unit> getNextStep()
  {
    return this.nextStep;
  }
  
  public boolean hasNext()
  {
    for (;;)
    {
      switch (this.state)
      {
      default: 
        throw exceptionalState();
      case 4: 
        return false;
      case 2: 
      case 3: 
        return true;
      case 1: 
        localObject = this.nextIterator;
        Intrinsics.checkNotNull(localObject);
        if (((Iterator)localObject).hasNext())
        {
          this.state = 2;
          return true;
        }
        this.nextIterator = null;
      }
      this.state = 5;
      Continuation localContinuation = this.nextStep;
      Intrinsics.checkNotNull(localContinuation);
      this.nextStep = null;
      Object localObject = Result.Companion;
      localContinuation.resumeWith(Result.constructor-impl(Unit.INSTANCE));
    }
  }
  
  public T next()
  {
    Object localObject;
    switch (this.state)
    {
    default: 
      throw exceptionalState();
    case 3: 
      this.state = 0;
      localObject = this.nextValue;
      this.nextValue = null;
      return (T)localObject;
    case 2: 
      this.state = 1;
      localObject = this.nextIterator;
      Intrinsics.checkNotNull(localObject);
      return (T)((Iterator)localObject).next();
    }
    return (T)nextNotReady();
  }
  
  public void remove()
  {
    throw new UnsupportedOperationException("Operation is not supported for read-only collection");
  }
  
  public void resumeWith(Object paramObject)
  {
    ResultKt.throwOnFailure(paramObject);
    this.state = 4;
  }
  
  public final void setNextStep(Continuation<? super Unit> paramContinuation)
  {
    this.nextStep = paramContinuation;
  }
  
  public Object yield(T paramT, Continuation<? super Unit> paramContinuation)
  {
    this.nextValue = paramT;
    this.state = 3;
    this.nextStep = paramContinuation;
    paramT = IntrinsicsKt.getCOROUTINE_SUSPENDED();
    if (paramT == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      DebugProbesKt.probeCoroutineSuspended(paramContinuation);
    }
    if (paramT == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      return paramT;
    }
    return Unit.INSTANCE;
  }
  
  public Object yieldAll(Iterator<? extends T> paramIterator, Continuation<? super Unit> paramContinuation)
  {
    if (!paramIterator.hasNext()) {
      return Unit.INSTANCE;
    }
    this.nextIterator = paramIterator;
    this.state = 2;
    this.nextStep = paramContinuation;
    paramIterator = IntrinsicsKt.getCOROUTINE_SUSPENDED();
    if (paramIterator == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      DebugProbesKt.probeCoroutineSuspended(paramContinuation);
    }
    if (paramIterator == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      return paramIterator;
    }
    return Unit.INSTANCE;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/sequences/SequenceBuilderIterator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */