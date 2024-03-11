package kotlinx.coroutines.flow;

import kotlin.Metadata;

@Metadata(d1={"\000\032\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\006\n\002\020\013\n\002\b\004\bf\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\0022\b\022\004\022\002H\0010\003J\035\020\t\032\0020\n2\006\020\013\032\0028\0002\006\020\f\032\0028\000H&¢\006\002\020\rR\030\020\004\032\0028\000X¦\016¢\006\f\032\004\b\005\020\006\"\004\b\007\020\b¨\006\016"}, d2={"Lkotlinx/coroutines/flow/MutableStateFlow;", "T", "Lkotlinx/coroutines/flow/StateFlow;", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "value", "getValue", "()Ljava/lang/Object;", "setValue", "(Ljava/lang/Object;)V", "compareAndSet", "", "expect", "update", "(Ljava/lang/Object;Ljava/lang/Object;)Z", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public abstract interface MutableStateFlow<T>
  extends StateFlow<T>, MutableSharedFlow<T>
{
  public abstract boolean compareAndSet(T paramT1, T paramT2);
  
  public abstract T getValue();
  
  public abstract void setValue(T paramT);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/flow/MutableStateFlow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */