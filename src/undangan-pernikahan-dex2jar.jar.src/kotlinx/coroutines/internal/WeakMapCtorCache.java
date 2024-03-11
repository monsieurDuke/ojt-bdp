package kotlinx.coroutines.internal;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;

@Metadata(d1={"\000*\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\020\003\n\002\030\002\n\002\030\002\n\002\b\003\bÂ\002\030\0002\0020\001B\007\b\002¢\006\002\020\002J*\020\013\032\024\022\004\022\0020\b\022\006\022\004\030\0010\b0\tj\002`\n2\016\020\f\032\n\022\006\b\001\022\0020\b0\007H\026R\016\020\003\032\0020\004X\004¢\006\002\n\000R4\020\005\032(\022\f\022\n\022\006\b\001\022\0020\b0\007\022\026\022\024\022\004\022\0020\b\022\006\022\004\030\0010\b0\tj\002`\n0\006X\004¢\006\002\n\000¨\006\r"}, d2={"Lkotlinx/coroutines/internal/WeakMapCtorCache;", "Lkotlinx/coroutines/internal/CtorCache;", "()V", "cacheLock", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "exceptionCtors", "Ljava/util/WeakHashMap;", "Ljava/lang/Class;", "", "Lkotlin/Function1;", "Lkotlinx/coroutines/internal/Ctor;", "get", "key", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
final class WeakMapCtorCache
  extends CtorCache
{
  public static final WeakMapCtorCache INSTANCE = new WeakMapCtorCache();
  private static final ReentrantReadWriteLock cacheLock = new ReentrantReadWriteLock();
  private static final WeakHashMap<Class<? extends Throwable>, Function1<Throwable, Throwable>> exceptionCtors = new WeakHashMap();
  
  public Function1<Throwable, Throwable> get(Class<? extends Throwable> paramClass)
  {
    Object localObject1 = cacheLock;
    ReentrantReadWriteLock.ReadLock localReadLock = ((ReentrantReadWriteLock)localObject1).readLock();
    localReadLock.lock();
    try
    {
      Object localObject2 = (Function1)exceptionCtors.get(paramClass);
      if (localObject2 == null)
      {
        localReadLock.unlock();
        localReadLock = ((ReentrantReadWriteLock)localObject1).readLock();
        int i = ((ReentrantReadWriteLock)localObject1).getWriteHoldCount();
        int n = 0;
        int k = 0;
        int m = 0;
        if (i == 0) {
          i = ((ReentrantReadWriteLock)localObject1).getReadHoldCount();
        } else {
          i = 0;
        }
        int j = 0;
        while (j < i)
        {
          j++;
          localReadLock.unlock();
        }
        localObject1 = ((ReentrantReadWriteLock)localObject1).writeLock();
        ((ReentrantReadWriteLock.WriteLock)localObject1).lock();
        try
        {
          localObject2 = exceptionCtors;
          Function1 localFunction1 = (Function1)((WeakHashMap)localObject2).get(paramClass);
          if (localFunction1 == null)
          {
            localFunction1 = ExceptionsConstructorKt.access$createConstructor(paramClass);
            ((Map)localObject2).put(paramClass, localFunction1);
            return localFunction1;
          }
          return localFunction1;
        }
        finally
        {
          j = k;
          while (j < i)
          {
            j++;
            localReadLock.lock();
          }
          ((ReentrantReadWriteLock.WriteLock)localObject1).unlock();
        }
      }
      return (Function1<Throwable, Throwable>)localObject2;
    }
    finally
    {
      localReadLock.unlock();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/internal/WeakMapCtorCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */