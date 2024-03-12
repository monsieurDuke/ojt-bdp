package kotlinx.coroutines;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Metadata;

@Metadata(d1={"\000\026\n\000\n\002\030\002\n\000\n\002\020\b\n\000\n\002\020\016\n\002\b\002\032\030\020\000\032\0020\0012\006\020\002\032\0020\0032\006\020\004\032\0020\005H\007\032\020\020\006\032\0020\0012\006\020\004\032\0020\005H\007¨\006\007"}, d2={"newFixedThreadPoolContext", "Lkotlinx/coroutines/ExecutorCoroutineDispatcher;", "nThreads", "", "name", "", "newSingleThreadContext", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class ThreadPoolDispatcherKt
{
  public static final ExecutorCoroutineDispatcher newFixedThreadPoolContext(int paramInt, String paramString)
  {
    int i = 1;
    if (paramInt < 1) {
      i = 0;
    }
    if (i != 0) {
      return ExecutorsKt.from((ExecutorService)Executors.newScheduledThreadPool(paramInt, new ThreadPoolDispatcherKt..ExternalSyntheticLambda0(paramInt, paramString, new AtomicInteger())));
    }
    throw new IllegalArgumentException(("Expected at least one thread, but " + paramInt + " specified").toString());
  }
  
  private static final Thread newFixedThreadPoolContext$lambda-1(int paramInt, String paramString, AtomicInteger paramAtomicInteger, Runnable paramRunnable)
  {
    if (paramInt != 1) {
      paramString = paramString + '-' + paramAtomicInteger.incrementAndGet();
    }
    paramString = new Thread(paramRunnable, paramString);
    paramString.setDaemon(true);
    return paramString;
  }
  
  public static final ExecutorCoroutineDispatcher newSingleThreadContext(String paramString)
  {
    return newFixedThreadPoolContext(1, paramString);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/ThreadPoolDispatcherKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */