package kotlinx.coroutines.selects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Lambda;

@Metadata(d1={"\000h\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\020\002\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\004\n\002\020\003\n\000\n\002\020\000\n\002\b\002\n\002\020\t\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\b\001\030\000*\006\b\000\020\001 \0002\b\022\004\022\002H\0010\002B\023\022\f\020\003\032\b\022\004\022\0028\0000\004¢\006\002\020\005J\020\020\021\032\0020\t2\006\020\022\032\0020\023H\001J\n\020\024\032\004\030\0010\025H\001J6\020\026\032\0020\t2\006\020\027\032\0020\0302\034\020\031\032\030\b\001\022\n\022\b\022\004\022\0028\0000\004\022\006\022\004\030\0010\0250\032H\026ø\001\000¢\006\002\020\033J3\020\034\032\0020\t*\0020\0352\034\020\031\032\030\b\001\022\n\022\b\022\004\022\0028\0000\004\022\006\022\004\030\0010\0250\032H\002ø\001\000¢\006\002\020\036JE\020\034\032\0020\t\"\004\b\001\020\037*\b\022\004\022\002H\0370 2\"\020\031\032\036\b\001\022\004\022\002H\037\022\n\022\b\022\004\022\0028\0000\004\022\006\022\004\030\0010\0250!H\002ø\001\000¢\006\002\020\"JY\020\034\032\0020\t\"\004\b\001\020#\"\004\b\002\020\037*\016\022\004\022\002H#\022\004\022\002H\0370$2\006\020%\032\002H#2\"\020\031\032\036\b\001\022\004\022\002H\037\022\n\022\b\022\004\022\0028\0000\004\022\006\022\004\030\0010\0250!H\002ø\001\000¢\006\002\020&R-\020\006\032\036\022\n\022\b\022\004\022\0020\t0\b0\007j\016\022\n\022\b\022\004\022\0020\t0\b`\n¢\006\b\n\000\032\004\b\013\020\fR\027\020\r\032\b\022\004\022\0028\0000\016¢\006\b\n\000\032\004\b\017\020\020\002\004\n\002\b\031¨\006'"}, d2={"Lkotlinx/coroutines/selects/UnbiasedSelectBuilderImpl;", "R", "Lkotlinx/coroutines/selects/SelectBuilder;", "uCont", "Lkotlin/coroutines/Continuation;", "(Lkotlin/coroutines/Continuation;)V", "clauses", "Ljava/util/ArrayList;", "Lkotlin/Function0;", "", "Lkotlin/collections/ArrayList;", "getClauses", "()Ljava/util/ArrayList;", "instance", "Lkotlinx/coroutines/selects/SelectBuilderImpl;", "getInstance", "()Lkotlinx/coroutines/selects/SelectBuilderImpl;", "handleBuilderException", "e", "", "initSelectResult", "", "onTimeout", "timeMillis", "", "block", "Lkotlin/Function1;", "(JLkotlin/jvm/functions/Function1;)V", "invoke", "Lkotlinx/coroutines/selects/SelectClause0;", "(Lkotlinx/coroutines/selects/SelectClause0;Lkotlin/jvm/functions/Function1;)V", "Q", "Lkotlinx/coroutines/selects/SelectClause1;", "Lkotlin/Function2;", "(Lkotlinx/coroutines/selects/SelectClause1;Lkotlin/jvm/functions/Function2;)V", "P", "Lkotlinx/coroutines/selects/SelectClause2;", "param", "(Lkotlinx/coroutines/selects/SelectClause2;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)V", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class UnbiasedSelectBuilderImpl<R>
  implements SelectBuilder<R>
{
  private final ArrayList<Function0<Unit>> clauses;
  private final SelectBuilderImpl<R> instance;
  
  public UnbiasedSelectBuilderImpl(Continuation<? super R> paramContinuation)
  {
    this.instance = new SelectBuilderImpl(paramContinuation);
    this.clauses = new ArrayList();
  }
  
  public final ArrayList<Function0<Unit>> getClauses()
  {
    return this.clauses;
  }
  
  public final SelectBuilderImpl<R> getInstance()
  {
    return this.instance;
  }
  
  public final void handleBuilderException(Throwable paramThrowable)
  {
    this.instance.handleBuilderException(paramThrowable);
  }
  
  public final Object initSelectResult()
  {
    if (!this.instance.isSelected()) {
      try
      {
        Collections.shuffle((List)this.clauses);
        Iterator localIterator = ((Iterable)this.clauses).iterator();
        while (localIterator.hasNext()) {
          ((Function0)localIterator.next()).invoke();
        }
      }
      finally
      {
        this.instance.handleBuilderException(localThrowable);
      }
    }
    return this.instance.getResult();
  }
  
  public void invoke(SelectClause0 paramSelectClause0, final Function1<? super Continuation<? super R>, ? extends Object> paramFunction1)
  {
    ((Collection)this.clauses).add(new Lambda(paramSelectClause0)
    {
      final SelectClause0 $this_invoke;
      
      public final void invoke()
      {
        this.$this_invoke.registerSelectClause0((SelectInstance)jdField_this.getInstance(), paramFunction1);
      }
    });
  }
  
  public <Q> void invoke(SelectClause1<? extends Q> paramSelectClause1, final Function2<? super Q, ? super Continuation<? super R>, ? extends Object> paramFunction2)
  {
    ((Collection)this.clauses).add(new Lambda(paramSelectClause1)
    {
      final SelectClause1<Q> $this_invoke;
      
      public final void invoke()
      {
        this.$this_invoke.registerSelectClause1((SelectInstance)jdField_this.getInstance(), paramFunction2);
      }
    });
  }
  
  public <P, Q> void invoke(SelectClause2<? super P, ? extends Q> paramSelectClause2, final P paramP, final Function2<? super Q, ? super Continuation<? super R>, ? extends Object> paramFunction2)
  {
    ((Collection)this.clauses).add(new Lambda(paramSelectClause2)
    {
      final SelectClause2<P, Q> $this_invoke;
      
      public final void invoke()
      {
        this.$this_invoke.registerSelectClause2((SelectInstance)jdField_this.getInstance(), paramP, paramFunction2);
      }
    });
  }
  
  public <P, Q> void invoke(SelectClause2<? super P, ? extends Q> paramSelectClause2, Function2<? super Q, ? super Continuation<? super R>, ? extends Object> paramFunction2)
  {
    SelectBuilder.DefaultImpls.invoke((SelectBuilder)this, paramSelectClause2, paramFunction2);
  }
  
  public void onTimeout(final long paramLong, Function1<? super Continuation<? super R>, ? extends Object> paramFunction1)
  {
    ((Collection)this.clauses).add(new Lambda(paramLong)
    {
      final UnbiasedSelectBuilderImpl<R> this$0;
      
      public final void invoke()
      {
        this.this$0.getInstance().onTimeout(paramLong, this.$block);
      }
    });
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/selects/UnbiasedSelectBuilderImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */