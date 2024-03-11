package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000<\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\020\016\n\000\n\002\020\003\n\000\n\002\030\002\n\002\b\003\n\002\020\013\n\000\n\002\020\000\n\002\b\002\n\002\020\b\n\002\b\002\b\000\030\0002\0060\001j\002`\0022\b\022\004\022\0020\0000\003B\037\022\006\020\004\032\0020\005\022\b\020\006\032\004\030\0010\007\022\006\020\b\032\0020\t¢\006\002\020\nJ\n\020\013\032\004\030\0010\000H\026J\023\020\f\032\0020\r2\b\020\016\032\004\030\0010\017H\002J\b\020\020\032\0020\007H\026J\b\020\021\032\0020\022H\026J\b\020\023\032\0020\005H\026R\020\020\b\032\0020\t8\000X\004¢\006\002\n\000¨\006\024"}, d2={"Lkotlinx/coroutines/JobCancellationException;", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "Lkotlinx/coroutines/CopyableThrowable;", "message", "", "cause", "", "job", "Lkotlinx/coroutines/Job;", "(Ljava/lang/String;Ljava/lang/Throwable;Lkotlinx/coroutines/Job;)V", "createCopy", "equals", "", "other", "", "fillInStackTrace", "hashCode", "", "toString", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class JobCancellationException
  extends CancellationException
  implements CopyableThrowable<JobCancellationException>
{
  public final Job job;
  
  public JobCancellationException(String paramString, Throwable paramThrowable, Job paramJob)
  {
    super(paramString);
    this.job = paramJob;
    if (paramThrowable != null) {
      initCause(paramThrowable);
    }
  }
  
  public JobCancellationException createCopy()
  {
    if (DebugKt.getDEBUG())
    {
      String str = getMessage();
      Intrinsics.checkNotNull(str);
      return new JobCancellationException(str, (Throwable)this, this.job);
    }
    return null;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool;
    if ((paramObject != this) && ((!(paramObject instanceof JobCancellationException)) || (!Intrinsics.areEqual(((JobCancellationException)paramObject).getMessage(), getMessage())) || (!Intrinsics.areEqual(((JobCancellationException)paramObject).job, this.job)) || (!Intrinsics.areEqual(((JobCancellationException)paramObject).getCause(), getCause())))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public Throwable fillInStackTrace()
  {
    if (DebugKt.getDEBUG()) {
      return super.fillInStackTrace();
    }
    setStackTrace(new StackTraceElement[0]);
    return (Throwable)this;
  }
  
  public int hashCode()
  {
    Object localObject = getMessage();
    Intrinsics.checkNotNull(localObject);
    int j = ((String)localObject).hashCode();
    int k = this.job.hashCode();
    localObject = getCause();
    int i;
    if (localObject == null) {
      i = 0;
    } else {
      i = ((Throwable)localObject).hashCode();
    }
    return (j * 31 + k) * 31 + i;
  }
  
  public String toString()
  {
    return super.toString() + "; job=" + this.job;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/JobCancellationException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */