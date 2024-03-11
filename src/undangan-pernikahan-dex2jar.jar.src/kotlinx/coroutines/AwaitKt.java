package kotlinx.coroutines;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

@Metadata(d1={"\000*\n\000\n\002\020 \n\002\b\002\n\002\020\021\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\000\n\002\020\036\n\002\b\002\032=\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\0022\036\020\003\032\020\022\f\b\001\022\b\022\004\022\002H\0020\0050\004\"\b\022\004\022\002H\0020\005H@ø\001\000¢\006\002\020\006\032%\020\007\032\0020\b2\022\020\t\032\n\022\006\b\001\022\0020\n0\004\"\0020\nH@ø\001\000¢\006\002\020\013\032-\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\016\022\n\022\b\022\004\022\002H\0020\0050\fH@ø\001\000¢\006\002\020\r\032\033\020\007\032\0020\b*\b\022\004\022\0020\n0\fH@ø\001\000¢\006\002\020\r\002\004\n\002\b\031¨\006\016"}, d2={"awaitAll", "", "T", "deferreds", "", "Lkotlinx/coroutines/Deferred;", "([Lkotlinx/coroutines/Deferred;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "joinAll", "", "jobs", "Lkotlinx/coroutines/Job;", "([Lkotlinx/coroutines/Job;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "", "(Ljava/util/Collection;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class AwaitKt
{
  public static final <T> Object awaitAll(Collection<? extends Deferred<? extends T>> paramCollection, Continuation<? super List<? extends T>> paramContinuation)
  {
    if (paramCollection.isEmpty()) {
      return CollectionsKt.emptyList();
    }
    paramCollection = paramCollection.toArray(new Deferred[0]);
    if (paramCollection != null) {
      return new AwaitAll((Deferred[])paramCollection).await(paramContinuation);
    }
    throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
  }
  
  public static final <T> Object awaitAll(Deferred<? extends T>[] paramArrayOfDeferred, Continuation<? super List<? extends T>> paramContinuation)
  {
    int i;
    if (paramArrayOfDeferred.length == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return CollectionsKt.emptyList();
    }
    return new AwaitAll(paramArrayOfDeferred).await(paramContinuation);
  }
  
  public static final Object joinAll(Collection<? extends Job> paramCollection, Continuation<? super Unit> paramContinuation)
  {
    if ((paramContinuation instanceof joinAll.3))
    {
      localObject1 = (joinAll.3)paramContinuation;
      if ((((joinAll.3)localObject1).label & 0x80000000) != 0)
      {
        ((joinAll.3)localObject1).label += Integer.MIN_VALUE;
        paramContinuation = (Continuation<? super Unit>)localObject1;
        break label47;
      }
    }
    paramContinuation = new ContinuationImpl(paramContinuation)
    {
      Object L$0;
      int label;
      Object result;
      
      public final Object invokeSuspend(Object paramAnonymousObject)
      {
        this.result = paramAnonymousObject;
        this.label |= 0x80000000;
        return AwaitKt.joinAll(null, (Continuation)this);
      }
    };
    label47:
    Object localObject2 = paramContinuation.result;
    Object localObject1 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
    switch (paramContinuation.label)
    {
    default: 
      throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    case 1: 
      paramCollection = (Iterator)paramContinuation.L$0;
      ResultKt.throwOnFailure(localObject2);
      break;
    case 0: 
      ResultKt.throwOnFailure(localObject2);
      paramCollection = ((Iterable)paramCollection).iterator();
    }
    while (paramCollection.hasNext())
    {
      localObject2 = (Job)paramCollection.next();
      paramContinuation.L$0 = paramCollection;
      paramContinuation.label = 1;
      if (((Job)localObject2).join(paramContinuation) == localObject1) {
        return localObject1;
      }
    }
    return Unit.INSTANCE;
  }
  
  public static final Object joinAll(Job[] paramArrayOfJob, Continuation<? super Unit> paramContinuation)
  {
    if ((paramContinuation instanceof joinAll.1))
    {
      localObject1 = (joinAll.1)paramContinuation;
      if ((((joinAll.1)localObject1).label & 0x80000000) != 0)
      {
        ((joinAll.1)localObject1).label += Integer.MIN_VALUE;
        paramContinuation = (Continuation<? super Unit>)localObject1;
        break label52;
      }
    }
    paramContinuation = new ContinuationImpl(paramContinuation)
    {
      int I$0;
      int I$1;
      Object L$0;
      int label;
      Object result;
      
      public final Object invokeSuspend(Object paramAnonymousObject)
      {
        this.result = paramAnonymousObject;
        this.label |= 0x80000000;
        return AwaitKt.joinAll(null, (Continuation)this);
      }
    };
    label52:
    Object localObject2 = paramContinuation.result;
    Object localObject1 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
    int j;
    int i;
    switch (paramContinuation.label)
    {
    default: 
      throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    case 1: 
      j = paramContinuation.I$1;
      i = paramContinuation.I$0;
      paramArrayOfJob = (Job[])paramContinuation.L$0;
      ResultKt.throwOnFailure(localObject2);
      break;
    case 0: 
      ResultKt.throwOnFailure(localObject2);
      i = 0;
      j = paramArrayOfJob.length;
    }
    while (i < j)
    {
      localObject2 = paramArrayOfJob[i];
      i++;
      paramContinuation.L$0 = paramArrayOfJob;
      paramContinuation.I$0 = i;
      paramContinuation.I$1 = j;
      paramContinuation.label = 1;
      if (((Job)localObject2).join(paramContinuation) == localObject1) {
        return localObject1;
      }
    }
    return Unit.INSTANCE;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/AwaitKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */