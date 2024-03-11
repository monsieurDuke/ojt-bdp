package kotlinx.coroutines.internal;

import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.Delay;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.MainCoroutineDispatcher;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000b\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\020\003\n\000\n\002\020\016\n\002\b\005\n\002\020\001\n\000\n\002\020\t\n\002\b\003\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\013\n\000\n\002\030\002\n\000\n\002\020\b\n\002\b\003\n\002\030\002\n\002\020\002\n\002\b\002\b\002\030\0002\0020\0012\0020\002B\033\022\b\020\003\032\004\030\0010\004\022\n\b\002\020\005\032\004\030\0010\006¢\006\002\020\007J\031\020\013\032\0020\f2\006\020\r\032\0020\016H@ø\001\000¢\006\002\020\017J\034\020\020\032\0020\f2\006\020\021\032\0020\0222\n\020\023\032\0060\024j\002`\025H\026J$\020\026\032\0020\0272\006\020\030\032\0020\0162\n\020\023\032\0060\024j\002`\0252\006\020\021\032\0020\022H\026J\020\020\031\032\0020\0322\006\020\021\032\0020\022H\026J\020\020\033\032\0020\0342\006\020\035\032\0020\036H\026J\b\020\037\032\0020\fH\002J\036\020 \032\0020\f2\006\020\030\032\0020\0162\f\020!\032\b\022\004\022\0020#0\"H\026J\b\020$\032\0020\006H\026R\020\020\003\032\004\030\0010\004X\004¢\006\002\n\000R\020\020\005\032\004\030\0010\006X\004¢\006\002\n\000R\024\020\b\032\0020\0018VX\004¢\006\006\032\004\b\t\020\n\002\004\n\002\b\031¨\006%"}, d2={"Lkotlinx/coroutines/internal/MissingMainCoroutineDispatcher;", "Lkotlinx/coroutines/MainCoroutineDispatcher;", "Lkotlinx/coroutines/Delay;", "cause", "", "errorHint", "", "(Ljava/lang/Throwable;Ljava/lang/String;)V", "immediate", "getImmediate", "()Lkotlinx/coroutines/MainCoroutineDispatcher;", "delay", "", "time", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "dispatch", "context", "Lkotlin/coroutines/CoroutineContext;", "block", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "invokeOnTimeout", "Lkotlinx/coroutines/DisposableHandle;", "timeMillis", "isDispatchNeeded", "", "limitedParallelism", "Lkotlinx/coroutines/CoroutineDispatcher;", "parallelism", "", "missing", "scheduleResumeAfterDelay", "continuation", "Lkotlinx/coroutines/CancellableContinuation;", "", "toString", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
final class MissingMainCoroutineDispatcher
  extends MainCoroutineDispatcher
  implements Delay
{
  private final Throwable cause;
  private final String errorHint;
  
  public MissingMainCoroutineDispatcher(Throwable paramThrowable, String paramString)
  {
    this.cause = paramThrowable;
    this.errorHint = paramString;
  }
  
  private final Void missing()
  {
    if (this.cause != null)
    {
      String str3 = this.errorHint;
      String str2 = "";
      String str1 = str2;
      if (str3 != null)
      {
        str1 = Intrinsics.stringPlus(". ", str3);
        Log5ECF72.a(str1);
        LogE84000.a(str1);
        Log229316.a(str1);
        if (str1 == null) {
          str1 = str2;
        }
      }
      str1 = Intrinsics.stringPlus("Module with the Main dispatcher had failed to initialize", str1);
      Log5ECF72.a(str1);
      LogE84000.a(str1);
      Log229316.a(str1);
      throw new IllegalStateException(str1, this.cause);
    }
    MainDispatchersKt.throwMissingMainDispatcherException();
    throw new KotlinNothingValueException();
  }
  
  public Object delay(long paramLong, Continuation<?> paramContinuation)
  {
    missing();
    throw new KotlinNothingValueException();
  }
  
  public Void dispatch(CoroutineContext paramCoroutineContext, Runnable paramRunnable)
  {
    missing();
    throw new KotlinNothingValueException();
  }
  
  public MainCoroutineDispatcher getImmediate()
  {
    return (MainCoroutineDispatcher)this;
  }
  
  public DisposableHandle invokeOnTimeout(long paramLong, Runnable paramRunnable, CoroutineContext paramCoroutineContext)
  {
    missing();
    throw new KotlinNothingValueException();
  }
  
  public boolean isDispatchNeeded(CoroutineContext paramCoroutineContext)
  {
    missing();
    throw new KotlinNothingValueException();
  }
  
  public CoroutineDispatcher limitedParallelism(int paramInt)
  {
    missing();
    throw new KotlinNothingValueException();
  }
  
  public Void scheduleResumeAfterDelay(long paramLong, CancellableContinuation<? super Unit> paramCancellableContinuation)
  {
    missing();
    throw new KotlinNothingValueException();
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder().append("Dispatchers.Main[missing");
    Object localObject = this.cause;
    if (localObject != null)
    {
      localObject = Intrinsics.stringPlus(", cause=", localObject);
      Log5ECF72.a(localObject);
      LogE84000.a(localObject);
      Log229316.a(localObject);
    }
    else
    {
      localObject = "";
    }
    return (String)localObject + ']';
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/internal/MissingMainCoroutineDispatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */