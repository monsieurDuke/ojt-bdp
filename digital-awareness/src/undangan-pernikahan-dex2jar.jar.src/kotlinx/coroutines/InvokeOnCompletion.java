package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

@Metadata(d1={"\000&\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\020\003\n\002\030\002\n\002\b\002\n\002\020\002\n\002\030\002\n\002\b\003\b\002\030\0002\0020\001B.\022'\020\002\032#\022\025\022\023\030\0010\004¢\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\003j\002`\t¢\006\002\020\nJ\023\020\013\032\0020\b2\b\020\007\032\004\030\0010\004H\002R/\020\002\032#\022\025\022\023\030\0010\004¢\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\003j\002`\tX\004¢\006\002\n\000¨\006\f"}, d2={"Lkotlinx/coroutines/InvokeOnCompletion;", "Lkotlinx/coroutines/JobNode;", "handler", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "cause", "", "Lkotlinx/coroutines/CompletionHandler;", "(Lkotlin/jvm/functions/Function1;)V", "invoke", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
final class InvokeOnCompletion
  extends JobNode
{
  private final Function1<Throwable, Unit> handler;
  
  public InvokeOnCompletion(Function1<? super Throwable, Unit> paramFunction1)
  {
    this.handler = paramFunction1;
  }
  
  public void invoke(Throwable paramThrowable)
  {
    this.handler.invoke(paramThrowable);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/InvokeOnCompletion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */