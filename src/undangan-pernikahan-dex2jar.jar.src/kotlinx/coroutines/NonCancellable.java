package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.AbstractCoroutineContextElement;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlinx.coroutines.selects.SelectClause0;

@Metadata(d1={"\000j\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\004\n\002\020\013\n\002\b\007\n\002\020\016\n\000\n\002\030\002\n\002\b\004\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\003\n\002\020\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\005\bÆ\002\030\0002\0020\0012\0020\002B\007\b\002¢\006\002\020\003J\020\020\030\032\0020\0312\006\020\032\032\0020\033H\027J\022\020\034\032\0020\n2\b\020\035\032\004\030\0010\036H\027J\030\020\034\032\0020\0372\016\020\035\032\n\030\0010 j\004\030\001`!H\027J\f\020\"\032\0060 j\002`!H\027JA\020#\032\0020$2\006\020%\032\0020\n2\006\020&\032\0020\n2'\020'\032#\022\025\022\023\030\0010\036¢\006\f\b)\022\b\b*\022\004\b\b(\035\022\004\022\0020\0370(j\002`+H\027J1\020#\032\0020$2'\020'\032#\022\025\022\023\030\0010\036¢\006\f\b)\022\b\b*\022\004\b\b(\035\022\004\022\0020\0370(j\002`+H\027J\021\020,\032\0020\037H@ø\001\000¢\006\002\020-J\b\020.\032\0020\nH\027J\b\020/\032\0020\022H\026R \020\004\032\b\022\004\022\0020\0020\0058VX\004¢\006\f\022\004\b\006\020\003\032\004\b\007\020\bR\032\020\t\032\0020\n8VX\004¢\006\f\022\004\b\013\020\003\032\004\b\t\020\fR\032\020\r\032\0020\n8VX\004¢\006\f\022\004\b\016\020\003\032\004\b\r\020\fR\032\020\017\032\0020\n8VX\004¢\006\f\022\004\b\020\020\003\032\004\b\017\020\fR\016\020\021\032\0020\022XT¢\006\002\n\000R\032\020\023\032\0020\0248VX\004¢\006\f\022\004\b\025\020\003\032\004\b\026\020\027\002\004\n\002\b\031¨\0060"}, d2={"Lkotlinx/coroutines/NonCancellable;", "Lkotlin/coroutines/AbstractCoroutineContextElement;", "Lkotlinx/coroutines/Job;", "()V", "children", "Lkotlin/sequences/Sequence;", "getChildren$annotations", "getChildren", "()Lkotlin/sequences/Sequence;", "isActive", "", "isActive$annotations", "()Z", "isCancelled", "isCancelled$annotations", "isCompleted", "isCompleted$annotations", "message", "", "onJoin", "Lkotlinx/coroutines/selects/SelectClause0;", "getOnJoin$annotations", "getOnJoin", "()Lkotlinx/coroutines/selects/SelectClause0;", "attachChild", "Lkotlinx/coroutines/ChildHandle;", "child", "Lkotlinx/coroutines/ChildJob;", "cancel", "cause", "", "", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "getCancellationException", "invokeOnCompletion", "Lkotlinx/coroutines/DisposableHandle;", "onCancelling", "invokeImmediately", "handler", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "Lkotlinx/coroutines/CompletionHandler;", "join", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "start", "toString", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class NonCancellable
  extends AbstractCoroutineContextElement
  implements Job
{
  public static final NonCancellable INSTANCE = new NonCancellable();
  private static final String message = "NonCancellable can be used only as an argument for 'withContext', direct usages of its API are prohibited";
  
  private NonCancellable()
  {
    super((CoroutineContext.Key)Job.Key);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="NonCancellable can be used only as an argument for 'withContext', direct usages of its API are prohibited")
  public ChildHandle attachChild(ChildJob paramChildJob)
  {
    return (ChildHandle)NonDisposableHandle.INSTANCE;
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="NonCancellable can be used only as an argument for 'withContext', direct usages of its API are prohibited")
  public void cancel(CancellationException paramCancellationException) {}
  
  @Deprecated(level=DeprecationLevel.WARNING, message="NonCancellable can be used only as an argument for 'withContext', direct usages of its API are prohibited")
  public CancellationException getCancellationException()
  {
    throw new IllegalStateException("This job is always active");
  }
  
  public Sequence<Job> getChildren()
  {
    return SequencesKt.emptySequence();
  }
  
  public SelectClause0 getOnJoin()
  {
    throw new UnsupportedOperationException("This job is always active");
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="NonCancellable can be used only as an argument for 'withContext', direct usages of its API are prohibited")
  public DisposableHandle invokeOnCompletion(Function1<? super Throwable, Unit> paramFunction1)
  {
    return (DisposableHandle)NonDisposableHandle.INSTANCE;
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="NonCancellable can be used only as an argument for 'withContext', direct usages of its API are prohibited")
  public DisposableHandle invokeOnCompletion(boolean paramBoolean1, boolean paramBoolean2, Function1<? super Throwable, Unit> paramFunction1)
  {
    return (DisposableHandle)NonDisposableHandle.INSTANCE;
  }
  
  public boolean isActive()
  {
    return true;
  }
  
  public boolean isCancelled()
  {
    return false;
  }
  
  public boolean isCompleted()
  {
    return false;
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="NonCancellable can be used only as an argument for 'withContext', direct usages of its API are prohibited")
  public Object join(Continuation<? super Unit> paramContinuation)
  {
    throw new UnsupportedOperationException("This job is always active");
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Operator '+' on two Job objects is meaningless. Job is a coroutine context element and `+` is a set-sum operator for coroutine contexts. The job to the right of `+` just replaces the job the left of `+`.")
  public Job plus(Job paramJob)
  {
    return Job.DefaultImpls.plus((Job)this, paramJob);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="NonCancellable can be used only as an argument for 'withContext', direct usages of its API are prohibited")
  public boolean start()
  {
    return false;
  }
  
  public String toString()
  {
    return "NonCancellable";
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/NonCancellable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */