package kotlinx.coroutines;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Element;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.jvm.functions.Function2;

@Metadata(d1={"\000\030\n\002\030\002\n\002\030\002\n\000\n\002\020\013\n\002\b\002\n\002\020\003\n\000\bf\030\0002\0020\001J\b\020\002\032\0020\003H&J\020\020\004\032\0020\0032\006\020\005\032\0020\006H&¨\006\007"}, d2={"Lkotlinx/coroutines/CompletableJob;", "Lkotlinx/coroutines/Job;", "complete", "", "completeExceptionally", "exception", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public abstract interface CompletableJob
  extends Job
{
  public abstract boolean complete();
  
  public abstract boolean completeExceptionally(Throwable paramThrowable);
  
  @Metadata(k=3, mv={1, 6, 0}, xi=48)
  public static final class DefaultImpls
  {
    public static <R> R fold(CompletableJob paramCompletableJob, R paramR, Function2<? super R, ? super CoroutineContext.Element, ? extends R> paramFunction2)
    {
      return (R)Job.DefaultImpls.fold((Job)paramCompletableJob, paramR, paramFunction2);
    }
    
    public static <E extends CoroutineContext.Element> E get(CompletableJob paramCompletableJob, CoroutineContext.Key<E> paramKey)
    {
      return Job.DefaultImpls.get((Job)paramCompletableJob, paramKey);
    }
    
    public static CoroutineContext minusKey(CompletableJob paramCompletableJob, CoroutineContext.Key<?> paramKey)
    {
      return Job.DefaultImpls.minusKey((Job)paramCompletableJob, paramKey);
    }
    
    public static CoroutineContext plus(CompletableJob paramCompletableJob, CoroutineContext paramCoroutineContext)
    {
      return Job.DefaultImpls.plus((Job)paramCompletableJob, paramCoroutineContext);
    }
    
    @Deprecated(level=DeprecationLevel.ERROR, message="Operator '+' on two Job objects is meaningless. Job is a coroutine context element and `+` is a set-sum operator for coroutine contexts. The job to the right of `+` just replaces the job the left of `+`.")
    public static Job plus(CompletableJob paramCompletableJob, Job paramJob)
    {
      return Job.DefaultImpls.plus((Job)paramCompletableJob, paramJob);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/CompletableJob.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */