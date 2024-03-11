package kotlinx.coroutines.android;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.ranges.RangesKt;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.Delay;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.JobKt;
import kotlinx.coroutines.NonDisposableHandle;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000^\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\016\n\002\b\002\n\002\020\013\n\002\b\006\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\003\n\002\020\000\n\000\n\002\020\b\n\000\n\002\030\002\n\000\n\002\020\t\n\002\b\003\n\002\030\002\n\002\b\002\b\000\030\0002\0020\0012\0020\002B\033\b\026\022\006\020\003\032\0020\004\022\n\b\002\020\005\032\004\030\0010\006¢\006\002\020\007B!\b\002\022\006\020\003\032\0020\004\022\b\020\005\032\004\030\0010\006\022\006\020\b\032\0020\t¢\006\002\020\nJ\034\020\017\032\0020\0202\006\020\021\032\0020\0222\n\020\023\032\0060\024j\002`\025H\002J\034\020\026\032\0020\0202\006\020\021\032\0020\0222\n\020\023\032\0060\024j\002`\025H\026J\023\020\027\032\0020\t2\b\020\030\032\004\030\0010\031H\002J\b\020\032\032\0020\033H\026J$\020\034\032\0020\0352\006\020\036\032\0020\0372\n\020\023\032\0060\024j\002`\0252\006\020\021\032\0020\022H\026J\020\020 \032\0020\t2\006\020\021\032\0020\022H\026J\036\020!\032\0020\0202\006\020\036\032\0020\0372\f\020\"\032\b\022\004\022\0020\0200#H\026J\b\020$\032\0020\006H\026R\020\020\013\032\004\030\0010\000X\016¢\006\002\n\000R\016\020\003\032\0020\004X\004¢\006\002\n\000R\024\020\f\032\0020\000X\004¢\006\b\n\000\032\004\b\r\020\016R\016\020\b\032\0020\tX\004¢\006\002\n\000R\020\020\005\032\004\030\0010\006X\004¢\006\002\n\000¨\006%"}, d2={"Lkotlinx/coroutines/android/HandlerContext;", "Lkotlinx/coroutines/android/HandlerDispatcher;", "Lkotlinx/coroutines/Delay;", "handler", "Landroid/os/Handler;", "name", "", "(Landroid/os/Handler;Ljava/lang/String;)V", "invokeImmediately", "", "(Landroid/os/Handler;Ljava/lang/String;Z)V", "_immediate", "immediate", "getImmediate", "()Lkotlinx/coroutines/android/HandlerContext;", "cancelOnRejection", "", "context", "Lkotlin/coroutines/CoroutineContext;", "block", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "dispatch", "equals", "other", "", "hashCode", "", "invokeOnTimeout", "Lkotlinx/coroutines/DisposableHandle;", "timeMillis", "", "isDispatchNeeded", "scheduleResumeAfterDelay", "continuation", "Lkotlinx/coroutines/CancellableContinuation;", "toString", "kotlinx-coroutines-android"}, k=1, mv={1, 6, 0}, xi=48)
public final class HandlerContext
  extends HandlerDispatcher
  implements Delay
{
  private volatile HandlerContext _immediate;
  private final Handler handler;
  private final HandlerContext immediate;
  private final boolean invokeImmediately;
  private final String name;
  
  public HandlerContext(Handler paramHandler, String paramString)
  {
    this(paramHandler, paramString, false);
  }
  
  private HandlerContext(Handler paramHandler, String paramString, boolean paramBoolean)
  {
    super(null);
    this.handler = paramHandler;
    this.name = paramString;
    this.invokeImmediately = paramBoolean;
    if (paramBoolean) {
      localObject = this;
    }
    this._immediate = ((HandlerContext)localObject);
    HandlerContext localHandlerContext = this._immediate;
    localObject = localHandlerContext;
    if (localHandlerContext == null)
    {
      localObject = new HandlerContext(paramHandler, paramString, true);
      this._immediate = ((HandlerContext)localObject);
    }
    this.immediate = ((HandlerContext)localObject);
  }
  
  private final void cancelOnRejection(CoroutineContext paramCoroutineContext, Runnable paramRunnable)
  {
    JobKt.cancel(paramCoroutineContext, new CancellationException("The task was rejected, the handler underlying the dispatcher '" + this + "' was closed"));
    Dispatchers.getIO().dispatch(paramCoroutineContext, paramRunnable);
  }
  
  private static final void invokeOnTimeout$lambda-3(HandlerContext paramHandlerContext, Runnable paramRunnable)
  {
    paramHandlerContext.handler.removeCallbacks(paramRunnable);
  }
  
  public void dispatch(CoroutineContext paramCoroutineContext, Runnable paramRunnable)
  {
    if (!this.handler.post(paramRunnable)) {
      cancelOnRejection(paramCoroutineContext, paramRunnable);
    }
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool;
    if (((paramObject instanceof HandlerContext)) && (((HandlerContext)paramObject).handler == this.handler)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public HandlerContext getImmediate()
  {
    return this.immediate;
  }
  
  public int hashCode()
  {
    return System.identityHashCode(this.handler);
  }
  
  public DisposableHandle invokeOnTimeout(long paramLong, Runnable paramRunnable, CoroutineContext paramCoroutineContext)
  {
    if (this.handler.postDelayed(paramRunnable, RangesKt.coerceAtMost(paramLong, 4611686018427387903L))) {
      return new HandlerContext..ExternalSyntheticLambda0(this, paramRunnable);
    }
    cancelOnRejection(paramCoroutineContext, paramRunnable);
    return (DisposableHandle)NonDisposableHandle.INSTANCE;
  }
  
  public boolean isDispatchNeeded(CoroutineContext paramCoroutineContext)
  {
    boolean bool;
    if ((this.invokeImmediately) && (Intrinsics.areEqual(Looper.myLooper(), this.handler.getLooper()))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public void scheduleResumeAfterDelay(long paramLong, CancellableContinuation<? super Unit> paramCancellableContinuation)
  {
    final Runnable localRunnable = (Runnable)new Runnable()
    {
      final CancellableContinuation $continuation$inlined;
      
      public final void run()
      {
        this.$continuation$inlined.resumeUndispatched((CoroutineDispatcher)jdField_this, Unit.INSTANCE);
      }
    };
    if (this.handler.postDelayed(localRunnable, RangesKt.coerceAtMost(paramLong, 4611686018427387903L))) {
      paramCancellableContinuation.invokeOnCancellation((Function1)new Lambda(localRunnable)
      {
        final HandlerContext this$0;
        
        public final void invoke(Throwable paramAnonymousThrowable)
        {
          HandlerContext.access$getHandler$p(this.this$0).removeCallbacks(localRunnable);
        }
      });
    } else {
      cancelOnRejection(paramCancellableContinuation.getContext(), localRunnable);
    }
  }
  
  public String toString()
  {
    String str2 = toStringInternalImpl();
    String str1 = str2;
    if (str2 == null)
    {
      HandlerContext localHandlerContext = (HandlerContext)this;
      str2 = localHandlerContext.name;
      str1 = str2;
      if (str2 == null) {
        str1 = localHandlerContext.handler.toString();
      }
      if (localHandlerContext.invokeImmediately)
      {
        str1 = Intrinsics.stringPlus(str1, ".immediate");
        Log5ECF72.a(str1);
        LogE84000.a(str1);
        Log229316.a(str1);
      }
    }
    return str1;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/android/HandlerContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */