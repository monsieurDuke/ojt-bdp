package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.selects.SelectInstance;

@Metadata(d1={"\0000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\020\000\n\002\b\003\n\002\020\002\n\000\n\002\020\003\n\000\b\002\030\000*\004\b\000\020\001*\004\b\001\020\0022\0020\003B:\022\f\020\004\032\b\022\004\022\0028\0010\005\022\"\020\006\032\036\b\001\022\004\022\0028\000\022\n\022\b\022\004\022\0028\0010\b\022\006\022\004\030\0010\t0\007ø\001\000¢\006\002\020\nJ\023\020\f\032\0020\r2\b\020\016\032\004\030\0010\017H\002R/\020\006\032\036\b\001\022\004\022\0028\000\022\n\022\b\022\004\022\0028\0010\b\022\006\022\004\030\0010\t0\007X\004ø\001\000¢\006\004\n\002\020\013R\024\020\004\032\b\022\004\022\0028\0010\005X\004¢\006\002\n\000\002\004\n\002\b\031¨\006\020"}, d2={"Lkotlinx/coroutines/SelectAwaitOnCompletion;", "T", "R", "Lkotlinx/coroutines/JobNode;", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "block", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "", "(Lkotlinx/coroutines/selects/SelectInstance;Lkotlin/jvm/functions/Function2;)V", "Lkotlin/jvm/functions/Function2;", "invoke", "", "cause", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
final class SelectAwaitOnCompletion<T, R>
  extends JobNode
{
  private final Function2<T, Continuation<? super R>, Object> block;
  private final SelectInstance<R> select;
  
  public SelectAwaitOnCompletion(SelectInstance<? super R> paramSelectInstance, Function2<? super T, ? super Continuation<? super R>, ? extends Object> paramFunction2)
  {
    this.select = paramSelectInstance;
    this.block = paramFunction2;
  }
  
  public void invoke(Throwable paramThrowable)
  {
    if (this.select.trySelect()) {
      getJob().selectAwaitCompletion$kotlinx_coroutines_core(this.select, this.block);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/SelectAwaitOnCompletion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */