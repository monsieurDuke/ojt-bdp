package kotlinx.coroutines.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.coroutines.AbstractCoroutineContextElement;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.DefaultImpls;
import kotlin.coroutines.CoroutineContext.Element;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineExceptionHandler;
import kotlinx.coroutines.CoroutineExceptionHandler.Key;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.Delay;
import kotlinx.coroutines.Delay.DefaultImpls;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.EventLoop;
import kotlinx.coroutines.internal.ThreadSafeHeap;
import kotlinx.coroutines.internal.ThreadSafeHeapNode;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Deprecated(level=DeprecationLevel.ERROR, message="This API has been deprecated to integrate with Structured Concurrency.", replaceWith=@ReplaceWith(expression="TestCoroutineScope", imports={"kotlin.coroutines.test"}))
@Metadata(d1={"\000~\n\002\030\002\n\002\030\002\n\000\n\002\020\016\n\002\b\002\n\002\020\t\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020 \n\002\020\003\n\002\b\003\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020!\n\002\b\003\n\002\030\002\n\000\n\002\020\002\n\002\b\004\n\002\030\002\n\002\020\013\n\002\b\006\n\002\030\002\n\002\030\002\n\002\b\004\n\002\030\002\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\t\b\007\030\0002\0020\001:\001<B\021\022\n\b\002\020\002\032\004\030\0010\003¢\006\002\020\004J\030\020\026\032\0020\0062\006\020\027\032\0020\0062\b\b\002\020\030\032\0020\031J\030\020\032\032\0020\0332\006\020\034\032\0020\0062\b\b\002\020\030\032\0020\031J$\020\035\032\0020\0332\b\b\002\020\036\032\0020\0032\022\020\037\032\016\022\004\022\0020\r\022\004\022\0020!0 J$\020\"\032\0020\0332\b\b\002\020\036\032\0020\0032\022\020\037\032\016\022\004\022\0020\r\022\004\022\0020!0 J*\020#\032\0020\0332\b\b\002\020\036\032\0020\0032\030\020\037\032\024\022\n\022\b\022\004\022\0020\r0\f\022\004\022\0020!0 J$\020$\032\0020\0332\b\b\002\020\036\032\0020\0032\022\020\037\032\016\022\004\022\0020\r\022\004\022\0020!0 J\006\020%\032\0020\033J\024\020&\032\0020\0332\n\020'\032\0060(j\002`)H\002J5\020*\032\002H+\"\004\b\000\020+2\006\020,\032\002H+2\030\020-\032\024\022\004\022\002H+\022\004\022\0020/\022\004\022\002H+0.H\026¢\006\002\0200J(\0201\032\004\030\001H2\"\b\b\000\0202*\0020/2\f\0203\032\b\022\004\022\002H204H\002¢\006\002\0205J\024\0206\032\0020\0012\n\0203\032\006\022\002\b\00304H\026J\020\0207\032\0020\0062\b\b\002\020\030\032\0020\031J\034\0208\032\0020\0222\n\020'\032\0060(j\002`)2\006\020\027\032\0020\006H\002J\b\0209\032\0020\006H\002J\b\020:\032\0020\003H\026J\006\020;\032\0020\033J\020\020;\032\0020\0332\006\020\034\032\0020\006H\002R\016\020\005\032\0020\006X\016¢\006\002\n\000R\022\020\007\032\0060\bR\0020\000X\004¢\006\002\n\000R\016\020\t\032\0020\nX\004¢\006\002\n\000R\027\020\013\032\b\022\004\022\0020\r0\f8F¢\006\006\032\004\b\016\020\017R\020\020\002\032\004\030\0010\003X\004¢\006\002\n\000R\024\020\020\032\b\022\004\022\0020\0220\021X\004¢\006\002\n\000R\016\020\023\032\0020\006X\016¢\006\002\n\000R\024\020\024\032\b\022\004\022\0020\r0\025X\004¢\006\002\n\000¨\006="}, d2={"Lkotlinx/coroutines/test/TestCoroutineContext;", "Lkotlin/coroutines/CoroutineContext;", "name", "", "(Ljava/lang/String;)V", "counter", "", "ctxDispatcher", "Lkotlinx/coroutines/test/TestCoroutineContext$Dispatcher;", "ctxHandler", "Lkotlinx/coroutines/CoroutineExceptionHandler;", "exceptions", "", "", "getExceptions", "()Ljava/util/List;", "queue", "Lkotlinx/coroutines/internal/ThreadSafeHeap;", "Lkotlinx/coroutines/test/TimedRunnableObsolete;", "time", "uncaughtExceptions", "", "advanceTimeBy", "delayTime", "unit", "Ljava/util/concurrent/TimeUnit;", "advanceTimeTo", "", "targetTime", "assertAllUnhandledExceptions", "message", "predicate", "Lkotlin/Function1;", "", "assertAnyUnhandledException", "assertExceptions", "assertUnhandledException", "cancelAllActions", "enqueue", "block", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "fold", "R", "initial", "operation", "Lkotlin/Function2;", "Lkotlin/coroutines/CoroutineContext$Element;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "get", "E", "key", "Lkotlin/coroutines/CoroutineContext$Key;", "(Lkotlin/coroutines/CoroutineContext$Key;)Lkotlin/coroutines/CoroutineContext$Element;", "minusKey", "now", "postDelayed", "processNextEvent", "toString", "triggerActions", "Dispatcher", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class TestCoroutineContext
  implements CoroutineContext
{
  private long counter;
  private final Dispatcher ctxDispatcher;
  private final CoroutineExceptionHandler ctxHandler;
  private final String name;
  private final ThreadSafeHeap<TimedRunnableObsolete> queue;
  private long time;
  private final List<Throwable> uncaughtExceptions;
  
  public TestCoroutineContext()
  {
    this(null, 1, null);
  }
  
  public TestCoroutineContext(String paramString)
  {
    this.name = paramString;
    this.uncaughtExceptions = ((List)new ArrayList());
    this.ctxDispatcher = new Dispatcher();
    paramString = (CoroutineExceptionHandler)new AbstractCoroutineContextElement(CoroutineExceptionHandler.Key)
    {
      public void handleException(CoroutineContext paramAnonymousCoroutineContext, Throwable paramAnonymousThrowable)
      {
        ((Collection)TestCoroutineContext.access$getUncaughtExceptions$p(jdField_this)).add(paramAnonymousThrowable);
      }
    };
    this.ctxHandler = paramString;
    this.queue = new ThreadSafeHeap();
  }
  
  private final void enqueue(Runnable paramRunnable)
  {
    ThreadSafeHeap localThreadSafeHeap = this.queue;
    long l = this.counter;
    this.counter = (1L + l);
    localThreadSafeHeap.addLast((ThreadSafeHeapNode)new TimedRunnableObsolete(paramRunnable, l, 0L, 4, null));
  }
  
  private final TimedRunnableObsolete postDelayed(Runnable paramRunnable, long paramLong)
  {
    long l1 = this.counter;
    this.counter = (1L + l1);
    long l2 = this.time;
    paramRunnable = new TimedRunnableObsolete(paramRunnable, l1, TimeUnit.MILLISECONDS.toNanos(paramLong) + l2);
    this.queue.addLast((ThreadSafeHeapNode)paramRunnable);
    return paramRunnable;
  }
  
  private final long processNextEvent()
  {
    TimedRunnableObsolete localTimedRunnableObsolete = (TimedRunnableObsolete)this.queue.peek();
    if (localTimedRunnableObsolete != null) {
      triggerActions(localTimedRunnableObsolete.time);
    }
    long l;
    if (this.queue.isEmpty()) {
      l = Long.MAX_VALUE;
    } else {
      l = 0L;
    }
    return l;
  }
  
  private final void triggerActions(long paramLong)
  {
    synchronized (this.queue)
    {
      ThreadSafeHeapNode localThreadSafeHeapNode2 = ???.firstImpl();
      ThreadSafeHeapNode localThreadSafeHeapNode1 = null;
      Object localObject1 = null;
      if (localThreadSafeHeapNode2 == null)
      {
        localObject1 = localThreadSafeHeapNode1;
      }
      else
      {
        int i;
        if (((TimedRunnableObsolete)localThreadSafeHeapNode2).time <= paramLong) {
          i = 1;
        } else {
          i = 0;
        }
        if (i != 0) {
          localObject1 = ???.removeAtImpl(0);
        } else {
          localThreadSafeHeapNode1 = (ThreadSafeHeapNode)null;
        }
      }
      localObject1 = (TimedRunnableObsolete)localObject1;
      if (localObject1 == null) {
        return;
      }
      if (((TimedRunnableObsolete)localObject1).time != 0L) {
        this.time = ((TimedRunnableObsolete)localObject1).time;
      }
      ((TimedRunnableObsolete)localObject1).run();
    }
  }
  
  public final long advanceTimeBy(long paramLong, TimeUnit paramTimeUnit)
  {
    long l = this.time;
    advanceTimeTo(paramTimeUnit.toNanos(paramLong) + l, TimeUnit.NANOSECONDS);
    return paramTimeUnit.convert(this.time - l, TimeUnit.NANOSECONDS);
  }
  
  public final void advanceTimeTo(long paramLong, TimeUnit paramTimeUnit)
  {
    paramLong = paramTimeUnit.toNanos(paramLong);
    triggerActions(paramLong);
    if (paramLong > this.time) {
      this.time = paramLong;
    }
  }
  
  public final void assertAllUnhandledExceptions(String paramString, Function1<? super Throwable, Boolean> paramFunction1)
  {
    Object localObject = (Iterable)this.uncaughtExceptions;
    boolean bool = localObject instanceof Collection;
    int i = 1;
    if ((!bool) || (!((Collection)localObject).isEmpty()))
    {
      localObject = ((Iterable)localObject).iterator();
      while (((Iterator)localObject).hasNext()) {
        if (!((Boolean)paramFunction1.invoke(((Iterator)localObject).next())).booleanValue()) {
          i = 0;
        }
      }
    }
    if (i != 0)
    {
      this.uncaughtExceptions.clear();
      return;
    }
    throw new AssertionError(paramString);
  }
  
  public final void assertAnyUnhandledException(String paramString, Function1<? super Throwable, Boolean> paramFunction1)
  {
    Object localObject = (Iterable)this.uncaughtExceptions;
    boolean bool = localObject instanceof Collection;
    int i = 0;
    if ((!bool) || (!((Collection)localObject).isEmpty()))
    {
      localObject = ((Iterable)localObject).iterator();
      while (((Iterator)localObject).hasNext()) {
        if (((Boolean)paramFunction1.invoke(((Iterator)localObject).next())).booleanValue()) {
          i = 1;
        }
      }
    }
    if (i != 0)
    {
      this.uncaughtExceptions.clear();
      return;
    }
    throw new AssertionError(paramString);
  }
  
  public final void assertExceptions(String paramString, Function1<? super List<? extends Throwable>, Boolean> paramFunction1)
  {
    if (((Boolean)paramFunction1.invoke(this.uncaughtExceptions)).booleanValue())
    {
      this.uncaughtExceptions.clear();
      return;
    }
    throw new AssertionError(paramString);
  }
  
  public final void assertUnhandledException(String paramString, Function1<? super Throwable, Boolean> paramFunction1)
  {
    if ((this.uncaughtExceptions.size() == 1) && (((Boolean)paramFunction1.invoke(this.uncaughtExceptions.get(0))).booleanValue()))
    {
      this.uncaughtExceptions.clear();
      return;
    }
    throw new AssertionError(paramString);
  }
  
  public final void cancelAllActions()
  {
    if (!this.queue.isEmpty()) {
      this.queue.clear();
    }
  }
  
  public <R> R fold(R paramR, Function2<? super R, ? super CoroutineContext.Element, ? extends R> paramFunction2)
  {
    return (R)paramFunction2.invoke(paramFunction2.invoke(paramR, this.ctxDispatcher), this.ctxHandler);
  }
  
  public <E extends CoroutineContext.Element> E get(CoroutineContext.Key<E> paramKey)
  {
    if (paramKey == ContinuationInterceptor.Key) {
      paramKey = (CoroutineContext.Element)this.ctxDispatcher;
    } else if (paramKey == CoroutineExceptionHandler.Key) {
      paramKey = (CoroutineContext.Element)this.ctxHandler;
    } else {
      paramKey = null;
    }
    return paramKey;
  }
  
  public final List<Throwable> getExceptions()
  {
    return this.uncaughtExceptions;
  }
  
  public CoroutineContext minusKey(CoroutineContext.Key<?> paramKey)
  {
    if (paramKey == ContinuationInterceptor.Key) {
      paramKey = (CoroutineContext)this.ctxHandler;
    } else if (paramKey == CoroutineExceptionHandler.Key) {
      paramKey = (CoroutineContext)this.ctxDispatcher;
    } else {
      paramKey = (CoroutineContext)this;
    }
    return paramKey;
  }
  
  public final long now(TimeUnit paramTimeUnit)
  {
    return paramTimeUnit.convert(this.time, TimeUnit.NANOSECONDS);
  }
  
  public CoroutineContext plus(CoroutineContext paramCoroutineContext)
  {
    return CoroutineContext.DefaultImpls.plus((CoroutineContext)this, paramCoroutineContext);
  }
  
  public String toString()
  {
    String str2 = this.name;
    String str1 = str2;
    if (str2 == null)
    {
      str1 = DebugStringsKt.getHexAddress(this);
      Log5ECF72.a(str1);
      LogE84000.a(str1);
      Log229316.a(str1);
      str1 = Intrinsics.stringPlus("TestCoroutineContext@", str1);
      Log5ECF72.a(str1);
      LogE84000.a(str1);
      Log229316.a(str1);
    }
    return str1;
  }
  
  public final void triggerActions()
  {
    triggerActions(this.time);
  }
  
  @Metadata(d1={"\000F\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\t\n\002\b\003\n\002\030\002\n\000\n\002\020\013\n\000\n\002\020\016\n\000\b\004\030\0002\0020\0012\0020\002B\005¢\006\002\020\003J\034\020\004\032\0020\0052\006\020\006\032\0020\0072\n\020\b\032\0060\tj\002`\nH\026J$\020\013\032\0020\f2\006\020\r\032\0020\0162\n\020\b\032\0060\tj\002`\n2\006\020\006\032\0020\007H\026J\b\020\017\032\0020\016H\026J\036\020\020\032\0020\0052\006\020\r\032\0020\0162\f\020\021\032\b\022\004\022\0020\0050\022H\026J\b\020\023\032\0020\024H\026J\b\020\025\032\0020\026H\026¨\006\027"}, d2={"Lkotlinx/coroutines/test/TestCoroutineContext$Dispatcher;", "Lkotlinx/coroutines/EventLoop;", "Lkotlinx/coroutines/Delay;", "(Lkotlinx/coroutines/test/TestCoroutineContext;)V", "dispatch", "", "context", "Lkotlin/coroutines/CoroutineContext;", "block", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "invokeOnTimeout", "Lkotlinx/coroutines/DisposableHandle;", "timeMillis", "", "processNextEvent", "scheduleResumeAfterDelay", "continuation", "Lkotlinx/coroutines/CancellableContinuation;", "shouldBeProcessedFromContext", "", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  private final class Dispatcher
    extends EventLoop
    implements Delay
  {
    final TestCoroutineContext this$0;
    
    public Dispatcher()
    {
      EventLoop.incrementUseCount$default((EventLoop)this, false, 1, null);
    }
    
    @Deprecated(level=DeprecationLevel.ERROR, message="Deprecated without replacement as an internal method never intended for public use")
    public Object delay(long paramLong, Continuation<? super Unit> paramContinuation)
    {
      return Delay.DefaultImpls.delay((Delay)this, paramLong, paramContinuation);
    }
    
    public void dispatch(CoroutineContext paramCoroutineContext, Runnable paramRunnable)
    {
      TestCoroutineContext.access$enqueue(this.this$0, paramRunnable);
    }
    
    public DisposableHandle invokeOnTimeout(long paramLong, final Runnable paramRunnable, CoroutineContext paramCoroutineContext)
    {
      paramRunnable = TestCoroutineContext.access$postDelayed(this.this$0, paramRunnable, paramLong);
      (DisposableHandle)new DisposableHandle()
      {
        final TestCoroutineContext this$0;
        
        public void dispose()
        {
          TestCoroutineContext.access$getQueue$p(this.this$0).remove((ThreadSafeHeapNode)paramRunnable);
        }
      };
    }
    
    public long processNextEvent()
    {
      return TestCoroutineContext.access$processNextEvent(this.this$0);
    }
    
    public void scheduleResumeAfterDelay(long paramLong, CancellableContinuation<? super Unit> paramCancellableContinuation)
    {
      TestCoroutineContext localTestCoroutineContext = this.this$0;
      paramCancellableContinuation = (Runnable)new Runnable()
      {
        final CancellableContinuation $continuation$inlined;
        
        public final void run()
        {
          this.$continuation$inlined.resumeUndispatched((CoroutineDispatcher)jdField_this, Unit.INSTANCE);
        }
      };
      TestCoroutineContext.access$postDelayed(localTestCoroutineContext, paramCancellableContinuation, paramLong);
    }
    
    public boolean shouldBeProcessedFromContext()
    {
      return true;
    }
    
    public String toString()
    {
      return "Dispatcher(" + this.this$0 + ')';
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/test/TestCoroutineContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */