package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.FlowCollector;

@Metadata(d1={"\000\030\n\002\030\002\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\002\n\002\b\003\bÀ\002\030\0002\n\022\006\022\004\030\0010\0020\001B\007\b\002¢\006\002\020\003J\033\020\004\032\0020\0052\b\020\006\032\004\030\0010\002H@ø\001\000¢\006\002\020\007\002\004\n\002\b\031¨\006\b"}, d2={"Lkotlinx/coroutines/flow/internal/NopCollector;", "Lkotlinx/coroutines/flow/FlowCollector;", "", "()V", "emit", "", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class NopCollector
  implements FlowCollector<Object>
{
  public static final NopCollector INSTANCE = new NopCollector();
  
  public Object emit(Object paramObject, Continuation<? super Unit> paramContinuation)
  {
    return Unit.INSTANCE;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/flow/internal/NopCollector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */