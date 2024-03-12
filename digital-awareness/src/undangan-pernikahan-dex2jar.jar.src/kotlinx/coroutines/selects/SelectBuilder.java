package kotlinx.coroutines.selects;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

@Metadata(d1={"\000@\n\002\030\002\n\000\n\002\020\000\n\000\n\002\020\002\n\000\n\002\020\t\n\000\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\004\bf\030\000*\006\b\000\020\001 \0002\0020\002J6\020\003\032\0020\0042\006\020\005\032\0020\0062\034\020\007\032\030\b\001\022\n\022\b\022\004\022\0028\0000\t\022\006\022\004\030\0010\0020\bH'ø\001\000¢\006\002\020\nJ3\020\013\032\0020\004*\0020\f2\034\020\007\032\030\b\001\022\n\022\b\022\004\022\0028\0000\t\022\006\022\004\030\0010\0020\bH¦\002ø\001\000¢\006\002\020\rJE\020\013\032\0020\004\"\004\b\001\020\016*\b\022\004\022\002H\0160\0172\"\020\007\032\036\b\001\022\004\022\002H\016\022\n\022\b\022\004\022\0028\0000\t\022\006\022\004\030\0010\0020\020H¦\002ø\001\000¢\006\002\020\021JY\020\013\032\0020\004\"\004\b\001\020\022\"\004\b\002\020\016*\016\022\004\022\002H\022\022\004\022\002H\0160\0232\006\020\024\032\002H\0222\"\020\007\032\036\b\001\022\004\022\002H\016\022\n\022\b\022\004\022\0028\0000\t\022\006\022\004\030\0010\0020\020H¦\002ø\001\000¢\006\002\020\025JS\020\013\032\0020\004\"\004\b\001\020\022\"\004\b\002\020\016*\020\022\006\022\004\030\001H\022\022\004\022\002H\0160\0232\"\020\007\032\036\b\001\022\004\022\002H\016\022\n\022\b\022\004\022\0028\0000\t\022\006\022\004\030\0010\0020\020H\002ø\001\000¢\006\002\020\026\002\004\n\002\b\031¨\006\027"}, d2={"Lkotlinx/coroutines/selects/SelectBuilder;", "R", "", "onTimeout", "", "timeMillis", "", "block", "Lkotlin/Function1;", "Lkotlin/coroutines/Continuation;", "(JLkotlin/jvm/functions/Function1;)V", "invoke", "Lkotlinx/coroutines/selects/SelectClause0;", "(Lkotlinx/coroutines/selects/SelectClause0;Lkotlin/jvm/functions/Function1;)V", "Q", "Lkotlinx/coroutines/selects/SelectClause1;", "Lkotlin/Function2;", "(Lkotlinx/coroutines/selects/SelectClause1;Lkotlin/jvm/functions/Function2;)V", "P", "Lkotlinx/coroutines/selects/SelectClause2;", "param", "(Lkotlinx/coroutines/selects/SelectClause2;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)V", "(Lkotlinx/coroutines/selects/SelectClause2;Lkotlin/jvm/functions/Function2;)V", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public abstract interface SelectBuilder<R>
{
  public abstract void invoke(SelectClause0 paramSelectClause0, Function1<? super Continuation<? super R>, ? extends Object> paramFunction1);
  
  public abstract <Q> void invoke(SelectClause1<? extends Q> paramSelectClause1, Function2<? super Q, ? super Continuation<? super R>, ? extends Object> paramFunction2);
  
  public abstract <P, Q> void invoke(SelectClause2<? super P, ? extends Q> paramSelectClause2, P paramP, Function2<? super Q, ? super Continuation<? super R>, ? extends Object> paramFunction2);
  
  public abstract <P, Q> void invoke(SelectClause2<? super P, ? extends Q> paramSelectClause2, Function2<? super Q, ? super Continuation<? super R>, ? extends Object> paramFunction2);
  
  public abstract void onTimeout(long paramLong, Function1<? super Continuation<? super R>, ? extends Object> paramFunction1);
  
  @Metadata(k=3, mv={1, 6, 0}, xi=48)
  public static final class DefaultImpls
  {
    public static <R, P, Q> void invoke(SelectBuilder<? super R> paramSelectBuilder, SelectClause2<? super P, ? extends Q> paramSelectClause2, Function2<? super Q, ? super Continuation<? super R>, ? extends Object> paramFunction2)
    {
      paramSelectBuilder.invoke(paramSelectClause2, null, paramFunction2);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/selects/SelectBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */