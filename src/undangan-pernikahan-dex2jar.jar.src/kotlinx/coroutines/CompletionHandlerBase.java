package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;

@Metadata(d1={"\000$\n\002\030\002\n\002\030\002\n\002\030\002\n\002\020\003\n\002\030\002\n\002\b\002\n\002\020\002\n\002\030\002\n\002\b\003\b \030\0002\0020\0012#\022\025\022\023\030\0010\003¢\006\f\b\004\022\b\b\005\022\004\b\b(\006\022\004\022\0020\0070\002j\002`\bB\005¢\006\002\020\tJ\023\020\n\032\0020\0072\b\020\006\032\004\030\0010\003H¦\002¨\006\013"}, d2={"Lkotlinx/coroutines/CompletionHandlerBase;", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "cause", "", "Lkotlinx/coroutines/CompletionHandler;", "()V", "invoke", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public abstract class CompletionHandlerBase
  extends LockFreeLinkedListNode
  implements Function1<Throwable, Unit>
{
  public abstract void invoke(Throwable paramThrowable);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/CompletionHandlerBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */