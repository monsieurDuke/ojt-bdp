package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.intrinsics.CancellableKt;
import kotlinx.coroutines.selects.SelectInstance;

@Metadata(d1={"\000.\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\020\000\n\002\b\003\n\002\020\002\n\000\n\002\020\003\n\000\b\002\030\000*\004\b\000\020\0012\0020\002B4\022\f\020\003\032\b\022\004\022\0028\0000\004\022\034\020\005\032\030\b\001\022\n\022\b\022\004\022\0028\0000\007\022\006\022\004\030\0010\b0\006ø\001\000¢\006\002\020\tJ\023\020\013\032\0020\f2\b\020\r\032\004\030\0010\016H\002R)\020\005\032\030\b\001\022\n\022\b\022\004\022\0028\0000\007\022\006\022\004\030\0010\b0\006X\004ø\001\000¢\006\004\n\002\020\nR\024\020\003\032\b\022\004\022\0028\0000\004X\004¢\006\002\n\000\002\004\n\002\b\031¨\006\017"}, d2={"Lkotlinx/coroutines/SelectJoinOnCompletion;", "R", "Lkotlinx/coroutines/JobNode;", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "block", "Lkotlin/Function1;", "Lkotlin/coroutines/Continuation;", "", "(Lkotlinx/coroutines/selects/SelectInstance;Lkotlin/jvm/functions/Function1;)V", "Lkotlin/jvm/functions/Function1;", "invoke", "", "cause", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
final class SelectJoinOnCompletion<R>
  extends JobNode
{
  private final Function1<Continuation<? super R>, Object> block;
  private final SelectInstance<R> select;
  
  public SelectJoinOnCompletion(SelectInstance<? super R> paramSelectInstance, Function1<? super Continuation<? super R>, ? extends Object> paramFunction1)
  {
    this.select = paramSelectInstance;
    this.block = paramFunction1;
  }
  
  public void invoke(Throwable paramThrowable)
  {
    if (this.select.trySelect()) {
      CancellableKt.startCoroutineCancellable(this.block, this.select.getCompletion());
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/SelectJoinOnCompletion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */