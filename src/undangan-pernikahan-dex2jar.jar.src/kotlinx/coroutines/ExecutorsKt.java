package kotlinx.coroutines;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import kotlin.Metadata;

@Metadata(d1={"\000\036\n\000\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\032\021\020\000\032\0020\001*\0020\002H\007¢\006\002\b\003\032\021\020\000\032\0020\004*\0020\005H\007¢\006\002\b\003\032\n\020\006\032\0020\002*\0020\001*\020\b\007\020\007\"\0020\0042\0020\004B\002\b\b¨\006\t"}, d2={"asCoroutineDispatcher", "Lkotlinx/coroutines/CoroutineDispatcher;", "Ljava/util/concurrent/Executor;", "from", "Lkotlinx/coroutines/ExecutorCoroutineDispatcher;", "Ljava/util/concurrent/ExecutorService;", "asExecutor", "CloseableCoroutineDispatcher", "Lkotlinx/coroutines/ExperimentalCoroutinesApi;", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class ExecutorsKt
{
  public static final Executor asExecutor(CoroutineDispatcher paramCoroutineDispatcher)
  {
    boolean bool = paramCoroutineDispatcher instanceof ExecutorCoroutineDispatcher;
    Object localObject2 = null;
    Object localObject1;
    if (bool) {
      localObject1 = (ExecutorCoroutineDispatcher)paramCoroutineDispatcher;
    } else {
      localObject1 = null;
    }
    if (localObject1 == null) {
      localObject1 = localObject2;
    } else {
      localObject1 = ((ExecutorCoroutineDispatcher)localObject1).getExecutor();
    }
    localObject2 = localObject1;
    if (localObject1 == null) {
      localObject2 = (Executor)new DispatcherExecutor(paramCoroutineDispatcher);
    }
    return (Executor)localObject2;
  }
  
  public static final CoroutineDispatcher from(Executor paramExecutor)
  {
    boolean bool = paramExecutor instanceof DispatcherExecutor;
    Object localObject2 = null;
    Object localObject1;
    if (bool) {
      localObject1 = (DispatcherExecutor)paramExecutor;
    } else {
      localObject1 = null;
    }
    if (localObject1 == null) {
      localObject1 = localObject2;
    } else {
      localObject1 = ((DispatcherExecutor)localObject1).dispatcher;
    }
    localObject2 = localObject1;
    if (localObject1 == null) {
      localObject2 = (CoroutineDispatcher)new ExecutorCoroutineDispatcherImpl(paramExecutor);
    }
    return (CoroutineDispatcher)localObject2;
  }
  
  public static final ExecutorCoroutineDispatcher from(ExecutorService paramExecutorService)
  {
    return (ExecutorCoroutineDispatcher)new ExecutorCoroutineDispatcherImpl((Executor)paramExecutorService);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/ExecutorsKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */