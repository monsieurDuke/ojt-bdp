package kotlinx.coroutines.sync;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.selects.SelectClause2;

@Metadata(d1={"\000\"\n\002\030\002\n\002\020\000\n\000\n\002\020\013\n\002\b\002\n\002\030\002\n\002\b\007\n\002\020\002\n\002\b\004\bf\030\0002\0020\001J\020\020\013\032\0020\0032\006\020\f\032\0020\001H&J\035\020\r\032\0020\0162\n\b\002\020\f\032\004\030\0010\001H¦@ø\001\000¢\006\002\020\017J\024\020\020\032\0020\0032\n\b\002\020\f\032\004\030\0010\001H&J\024\020\021\032\0020\0162\n\b\002\020\f\032\004\030\0010\001H&R\022\020\002\032\0020\003X¦\004¢\006\006\032\004\b\002\020\004R(\020\005\032\020\022\006\022\004\030\0010\001\022\004\022\0020\0000\0068&X§\004¢\006\f\022\004\b\007\020\b\032\004\b\t\020\n\002\004\n\002\b\031¨\006\022"}, d2={"Lkotlinx/coroutines/sync/Mutex;", "", "isLocked", "", "()Z", "onLock", "Lkotlinx/coroutines/selects/SelectClause2;", "getOnLock$annotations", "()V", "getOnLock", "()Lkotlinx/coroutines/selects/SelectClause2;", "holdsLock", "owner", "lock", "", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "tryLock", "unlock", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public abstract interface Mutex
{
  public abstract SelectClause2<Object, Mutex> getOnLock();
  
  public abstract boolean holdsLock(Object paramObject);
  
  public abstract boolean isLocked();
  
  public abstract Object lock(Object paramObject, Continuation<? super Unit> paramContinuation);
  
  public abstract boolean tryLock(Object paramObject);
  
  public abstract void unlock(Object paramObject);
  
  @Metadata(k=3, mv={1, 6, 0}, xi=48)
  public static final class DefaultImpls {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/sync/Mutex.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */