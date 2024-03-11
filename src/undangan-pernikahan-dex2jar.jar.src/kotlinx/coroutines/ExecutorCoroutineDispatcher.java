package kotlinx.coroutines;

import java.io.Closeable;
import java.util.concurrent.Executor;
import kotlin.Metadata;
import kotlin.coroutines.AbstractCoroutineContextKey;
import kotlin.coroutines.CoroutineContext.Element;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

@Metadata(d1={"\000 \n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\020\002\n\002\b\002\b&\030\000 \n2\0020\0012\0020\002:\001\nB\005¢\006\002\020\003J\b\020\b\032\0020\tH&R\022\020\004\032\0020\005X¦\004¢\006\006\032\004\b\006\020\007¨\006\013"}, d2={"Lkotlinx/coroutines/ExecutorCoroutineDispatcher;", "Lkotlinx/coroutines/CoroutineDispatcher;", "Ljava/io/Closeable;", "()V", "executor", "Ljava/util/concurrent/Executor;", "getExecutor", "()Ljava/util/concurrent/Executor;", "close", "", "Key", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public abstract class ExecutorCoroutineDispatcher
  extends CoroutineDispatcher
  implements Closeable
{
  public static final Key Key = new Key(null);
  
  public abstract void close();
  
  public abstract Executor getExecutor();
  
  @Metadata(d1={"\000\024\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\b\003\030\0002\016\022\004\022\0020\002\022\004\022\0020\0030\001B\007\b\002¢\006\002\020\004¨\006\005"}, d2={"Lkotlinx/coroutines/ExecutorCoroutineDispatcher$Key;", "Lkotlin/coroutines/AbstractCoroutineContextKey;", "Lkotlinx/coroutines/CoroutineDispatcher;", "Lkotlinx/coroutines/ExecutorCoroutineDispatcher;", "()V", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  public static final class Key
    extends AbstractCoroutineContextKey<CoroutineDispatcher, ExecutorCoroutineDispatcher>
  {
    private Key()
    {
      super((Function1)1.INSTANCE);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/ExecutorCoroutineDispatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */