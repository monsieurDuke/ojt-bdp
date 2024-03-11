package kotlin.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000\032\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\0326\020\000\032\002H\001\"\004\b\000\020\001*\0020\0022\f\020\003\032\b\022\004\022\002H\0010\004H\bø\001\000\002\n\n\b\b\001\022\002\020\001 \001¢\006\002\020\005\0326\020\006\032\002H\001\"\004\b\000\020\001*\0020\0072\f\020\003\032\b\022\004\022\002H\0010\004H\bø\001\000\002\n\n\b\b\001\022\002\020\001 \001¢\006\002\020\b\0326\020\t\032\002H\001\"\004\b\000\020\001*\0020\0022\f\020\003\032\b\022\004\022\002H\0010\004H\bø\001\000\002\n\n\b\b\001\022\002\020\001 \001¢\006\002\020\005\002\007\n\005\b20\001¨\006\n"}, d2={"read", "T", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "action", "Lkotlin/Function0;", "(Ljava/util/concurrent/locks/ReentrantReadWriteLock;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withLock", "Ljava/util/concurrent/locks/Lock;", "(Ljava/util/concurrent/locks/Lock;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "write", "kotlin-stdlib"}, k=2, mv={1, 7, 1}, xi=48)
public final class LocksKt
{
  private static final <T> T read(ReentrantReadWriteLock paramReentrantReadWriteLock, Function0<? extends T> paramFunction0)
  {
    Intrinsics.checkNotNullParameter(paramReentrantReadWriteLock, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction0, "action");
    paramReentrantReadWriteLock = paramReentrantReadWriteLock.readLock();
    paramReentrantReadWriteLock.lock();
    try
    {
      paramFunction0 = paramFunction0.invoke();
      return paramFunction0;
    }
    finally
    {
      InlineMarker.finallyStart(1);
      paramReentrantReadWriteLock.unlock();
      InlineMarker.finallyEnd(1);
    }
  }
  
  private static final <T> T withLock(Lock paramLock, Function0<? extends T> paramFunction0)
  {
    Intrinsics.checkNotNullParameter(paramLock, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction0, "action");
    paramLock.lock();
    try
    {
      paramFunction0 = paramFunction0.invoke();
      return paramFunction0;
    }
    finally
    {
      InlineMarker.finallyStart(1);
      paramLock.unlock();
      InlineMarker.finallyEnd(1);
    }
  }
  
  private static final <T> T write(ReentrantReadWriteLock paramReentrantReadWriteLock, Function0<? extends T> paramFunction0)
  {
    Intrinsics.checkNotNullParameter(paramReentrantReadWriteLock, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction0, "action");
    ReentrantReadWriteLock.ReadLock localReadLock = paramReentrantReadWriteLock.readLock();
    int i = paramReentrantReadWriteLock.getWriteHoldCount();
    int m = 0;
    int k = 0;
    if (i == 0) {
      i = paramReentrantReadWriteLock.getReadHoldCount();
    } else {
      i = 0;
    }
    for (int j = 0; j < i; j++) {
      localReadLock.unlock();
    }
    paramReentrantReadWriteLock = paramReentrantReadWriteLock.writeLock();
    paramReentrantReadWriteLock.lock();
    try
    {
      paramFunction0 = paramFunction0.invoke();
      return paramFunction0;
    }
    finally
    {
      InlineMarker.finallyStart(1);
      for (j = m; j < i; j++) {
        localReadLock.lock();
      }
      paramReentrantReadWriteLock.unlock();
      InlineMarker.finallyEnd(1);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/concurrent/LocksKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */