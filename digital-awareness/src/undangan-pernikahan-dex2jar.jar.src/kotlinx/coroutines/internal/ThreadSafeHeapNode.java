package kotlinx.coroutines.internal;

import kotlin.Metadata;

@Metadata(d1={"\000\032\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\002\b\005\n\002\020\b\n\002\b\005\bg\030\0002\0020\001R\036\020\002\032\b\022\002\b\003\030\0010\003X¦\016¢\006\f\032\004\b\004\020\005\"\004\b\006\020\007R\030\020\b\032\0020\tX¦\016¢\006\f\032\004\b\n\020\013\"\004\b\f\020\r¨\006\016"}, d2={"Lkotlinx/coroutines/internal/ThreadSafeHeapNode;", "", "heap", "Lkotlinx/coroutines/internal/ThreadSafeHeap;", "getHeap", "()Lkotlinx/coroutines/internal/ThreadSafeHeap;", "setHeap", "(Lkotlinx/coroutines/internal/ThreadSafeHeap;)V", "index", "", "getIndex", "()I", "setIndex", "(I)V", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public abstract interface ThreadSafeHeapNode
{
  public abstract ThreadSafeHeap<?> getHeap();
  
  public abstract int getIndex();
  
  public abstract void setHeap(ThreadSafeHeap<?> paramThreadSafeHeap);
  
  public abstract void setIndex(int paramInt);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/internal/ThreadSafeHeapNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */