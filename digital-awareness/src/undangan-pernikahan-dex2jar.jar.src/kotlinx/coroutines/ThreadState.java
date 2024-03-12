package kotlinx.coroutines;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000H\n\002\030\002\n\002\030\002\n\002\b\003\n\002\020\002\n\002\b\002\n\002\020\b\n\000\n\002\020\001\n\002\b\002\n\002\020\003\n\002\b\004\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\004\n\002\030\002\n\002\030\002\n\000\n\002\030\002\b\002\030\0002#\022\025\022\023\030\0010\r¢\006\f\b\034\022\b\b\035\022\004\b\b(\016\022\004\022\0020\0050\033j\002`\036B\017\022\006\020\002\032\0020\001¢\006\004\b\003\020\004J\r\020\006\032\0020\005¢\006\004\b\006\020\007J\027\020\013\032\0020\n2\006\020\t\032\0020\bH\002¢\006\004\b\013\020\fJ\032\020\017\032\0020\0052\b\020\016\032\004\030\0010\rH\002¢\006\004\b\017\020\020J\r\020\021\032\0020\005¢\006\004\b\021\020\007R\030\020\023\032\004\030\0010\0228\002@\002X\016¢\006\006\n\004\b\023\020\024R\024\020\002\032\0020\0018\002X\004¢\006\006\n\004\b\002\020\025R\034\020\030\032\n \027*\004\030\0010\0260\0268\002X\004¢\006\006\n\004\b\030\020\031¨\006\032"}, d2={"Lkotlinx/coroutines/ThreadState;", "Lkotlinx/coroutines/Job;", "job", "<init>", "(Lkotlinx/coroutines/Job;)V", "", "clearInterrupt", "()V", "", "state", "", "invalidState", "(I)Ljava/lang/Void;", "", "cause", "invoke", "(Ljava/lang/Throwable;)V", "setup", "Lkotlinx/coroutines/DisposableHandle;", "cancelHandle", "Lkotlinx/coroutines/DisposableHandle;", "Lkotlinx/coroutines/Job;", "Ljava/lang/Thread;", "kotlin.jvm.PlatformType", "targetThread", "Ljava/lang/Thread;", "kotlinx-coroutines-core", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "Lkotlinx/coroutines/CompletionHandler;"}, k=1, mv={1, 6, 0}, xi=48)
final class ThreadState
  implements Function1<Throwable, Unit>
{
  private static final AtomicIntegerFieldUpdater _state$FU = AtomicIntegerFieldUpdater.newUpdater(ThreadState.class, "_state");
  private volatile int _state;
  private DisposableHandle cancelHandle;
  private final Job job;
  private final Thread targetThread;
  
  public ThreadState(Job paramJob)
  {
    this.job = paramJob;
    this._state = 0;
    this.targetThread = Thread.currentThread();
  }
  
  private final Void invalidState(int paramInt)
  {
    String str = Intrinsics.stringPlus("Illegal state ", Integer.valueOf(paramInt));
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    throw new IllegalStateException(str.toString());
  }
  
  public final void clearInterrupt()
  {
    for (;;)
    {
      int i = this._state;
      switch (i)
      {
      case 1: 
      default: 
        invalidState(i);
        throw new KotlinNothingValueException();
      case 3: 
        Thread.interrupted();
        return;
      case 2: 
        break;
      case 0: 
        if (_state$FU.compareAndSet(this, i, 1))
        {
          DisposableHandle localDisposableHandle = this.cancelHandle;
          if (localDisposableHandle != null) {
            localDisposableHandle.dispose();
          }
          return;
        }
        break;
      }
    }
  }
  
  public void invoke(Throwable paramThrowable)
  {
    for (;;)
    {
      int i = this._state;
      switch (i)
      {
      default: 
        invalidState(i);
        throw new KotlinNothingValueException();
      case 1: 
      case 2: 
      case 3: 
        return;
      }
      if (_state$FU.compareAndSet(this, i, 2))
      {
        this.targetThread.interrupt();
        this._state = 3;
        return;
      }
    }
  }
  
  public final void setup()
  {
    this.cancelHandle = this.job.invokeOnCompletion(true, true, (Function1)this);
    for (;;)
    {
      int i = this._state;
      switch (i)
      {
      case 1: 
      default: 
        invalidState(i);
        throw new KotlinNothingValueException();
      case 2: 
      case 3: 
        return;
      }
      if (_state$FU.compareAndSet(this, i, 0)) {
        return;
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/ThreadState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */