package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Metadata(d1={"\000\036\n\002\030\002\n\002\030\002\n\002\020\000\n\000\n\002\020\003\n\002\b\002\n\002\020\002\n\002\b\003\b\000\030\0002\n\022\006\022\004\030\0010\0020\001B\r\022\006\020\003\032\0020\004¢\006\002\020\005J\033\020\006\032\0020\0072\b\020\b\032\004\030\0010\002H@ø\001\000¢\006\002\020\tR\020\020\003\032\0020\0048\006X\004¢\006\002\n\000\002\004\n\002\b\031¨\006\n"}, d2={"Lkotlinx/coroutines/flow/ThrowingCollector;", "Lkotlinx/coroutines/flow/FlowCollector;", "", "e", "", "(Ljava/lang/Throwable;)V", "emit", "", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class ThrowingCollector
  implements FlowCollector<Object>
{
  public final Throwable e;
  
  public ThrowingCollector(Throwable paramThrowable)
  {
    this.e = paramThrowable;
  }
  
  public Object emit(Object paramObject, Continuation<? super Unit> paramContinuation)
  {
    throw this.e;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/flow/ThrowingCollector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */