package kotlinx.coroutines.internal;

import androidx.concurrent.futures.AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0;
import java.util.List;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;

@Metadata(d1={"\0000\n\002\030\002\n\002\020\000\n\000\n\002\020\013\n\002\b\006\n\002\020\002\n\002\b\005\n\002\030\002\n\000\n\002\020 \n\002\b\005\n\002\020\b\n\002\b\004\b\020\030\000*\b\b\000\020\002*\0020\0012\0020\001B\017\022\006\020\004\032\0020\003¢\006\004\b\005\020\006J\025\020\b\032\0020\0032\006\020\007\032\0028\000¢\006\004\b\b\020\tJ\r\020\013\032\0020\n¢\006\004\b\013\020\fJ\r\020\r\032\0020\003¢\006\004\b\r\020\016J-\020\023\032\b\022\004\022\0028\0010\022\"\004\b\001\020\0172\022\020\021\032\016\022\004\022\0028\000\022\004\022\0028\0010\020¢\006\004\b\023\020\024J\017\020\025\032\004\030\0018\000¢\006\004\b\025\020\026R\021\020\027\032\0020\0038F¢\006\006\032\004\b\027\020\016R\021\020\033\032\0020\0308F¢\006\006\032\004\b\031\020\032¨\006\034"}, d2={"Lkotlinx/coroutines/internal/LockFreeTaskQueue;", "", "E", "", "singleConsumer", "<init>", "(Z)V", "element", "addLast", "(Ljava/lang/Object;)Z", "", "close", "()V", "isClosed", "()Z", "R", "Lkotlin/Function1;", "transform", "", "map", "(Lkotlin/jvm/functions/Function1;)Ljava/util/List;", "removeFirstOrNull", "()Ljava/lang/Object;", "isEmpty", "", "getSize", "()I", "size", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public class LockFreeTaskQueue<E>
{
  private static final AtomicReferenceFieldUpdater _cur$FU = AtomicReferenceFieldUpdater.newUpdater(LockFreeTaskQueue.class, Object.class, "_cur");
  private volatile Object _cur;
  
  public LockFreeTaskQueue(boolean paramBoolean)
  {
    this._cur = new LockFreeTaskQueueCore(8, paramBoolean);
  }
  
  public final boolean addLast(E paramE)
  {
    for (;;)
    {
      LockFreeTaskQueueCore localLockFreeTaskQueueCore = (LockFreeTaskQueueCore)this._cur;
      switch (localLockFreeTaskQueueCore.addLast(paramE))
      {
      default: 
        break;
      case 2: 
        return false;
      case 1: 
        AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_cur$FU, this, localLockFreeTaskQueueCore, localLockFreeTaskQueueCore.next());
        break;
      case 0: 
        return true;
      }
    }
  }
  
  public final void close()
  {
    for (;;)
    {
      LockFreeTaskQueueCore localLockFreeTaskQueueCore = (LockFreeTaskQueueCore)this._cur;
      if (localLockFreeTaskQueueCore.close()) {
        return;
      }
      AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_cur$FU, this, localLockFreeTaskQueueCore, localLockFreeTaskQueueCore.next());
    }
  }
  
  public final int getSize()
  {
    return ((LockFreeTaskQueueCore)this._cur).getSize();
  }
  
  public final boolean isClosed()
  {
    return ((LockFreeTaskQueueCore)this._cur).isClosed();
  }
  
  public final boolean isEmpty()
  {
    return ((LockFreeTaskQueueCore)this._cur).isEmpty();
  }
  
  public final <R> List<R> map(Function1<? super E, ? extends R> paramFunction1)
  {
    return ((LockFreeTaskQueueCore)this._cur).map(paramFunction1);
  }
  
  public final E removeFirstOrNull()
  {
    for (;;)
    {
      LockFreeTaskQueueCore localLockFreeTaskQueueCore = (LockFreeTaskQueueCore)this._cur;
      Object localObject = localLockFreeTaskQueueCore.removeFirstOrNull();
      if (localObject != LockFreeTaskQueueCore.REMOVE_FROZEN) {
        return (E)localObject;
      }
      AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0.m(_cur$FU, this, localLockFreeTaskQueueCore, localLockFreeTaskQueueCore.next());
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/internal/LockFreeTaskQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */