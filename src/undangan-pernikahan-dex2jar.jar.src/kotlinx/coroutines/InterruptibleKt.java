package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;

@Metadata(d1={"\000\030\n\000\n\002\020\b\n\002\b\006\n\002\030\002\n\000\n\002\030\002\n\002\b\005\032/\020\005\032\002H\006\"\004\b\000\020\0062\b\b\002\020\007\032\0020\b2\f\020\t\032\b\022\004\022\002H\0060\nH@ø\001\000¢\006\002\020\013\032)\020\f\032\002H\006\"\004\b\000\020\0062\006\020\r\032\0020\b2\f\020\t\032\b\022\004\022\002H\0060\nH\002¢\006\002\020\016\"\016\020\000\032\0020\001XT¢\006\002\n\000\"\016\020\002\032\0020\001XT¢\006\002\n\000\"\016\020\003\032\0020\001XT¢\006\002\n\000\"\016\020\004\032\0020\001XT¢\006\002\n\000\002\004\n\002\b\031¨\006\017"}, d2={"FINISHED", "", "INTERRUPTED", "INTERRUPTING", "WORKING", "runInterruptible", "T", "context", "Lkotlin/coroutines/CoroutineContext;", "block", "Lkotlin/Function0;", "(Lkotlin/coroutines/CoroutineContext;Lkotlin/jvm/functions/Function0;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "runInterruptibleInExpectedContext", "coroutineContext", "(Lkotlin/coroutines/CoroutineContext;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class InterruptibleKt
{
  private static final int FINISHED = 1;
  private static final int INTERRUPTED = 3;
  private static final int INTERRUPTING = 2;
  private static final int WORKING = 0;
  
  public static final <T> Object runInterruptible(CoroutineContext paramCoroutineContext, Function0<? extends T> paramFunction0, Continuation<? super T> paramContinuation)
  {
    BuildersKt.withContext(paramCoroutineContext, (Function2)new SuspendLambda(paramFunction0, null)
    {
      final Function0<T> $block;
      private Object L$0;
      int label;
      
      public final Continuation<Unit> create(Object paramAnonymousObject, Continuation<?> paramAnonymousContinuation)
      {
        paramAnonymousContinuation = new 2(this.$block, paramAnonymousContinuation);
        paramAnonymousContinuation.L$0 = paramAnonymousObject;
        return (Continuation)paramAnonymousContinuation;
      }
      
      public final Object invoke(CoroutineScope paramAnonymousCoroutineScope, Continuation<? super T> paramAnonymousContinuation)
      {
        return ((2)create(paramAnonymousCoroutineScope, paramAnonymousContinuation)).invokeSuspend(Unit.INSTANCE);
      }
      
      public final Object invokeSuspend(Object paramAnonymousObject)
      {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label)
        {
        default: 
          throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(paramAnonymousObject);
        return InterruptibleKt.access$runInterruptibleInExpectedContext(((CoroutineScope)this.L$0).getCoroutineContext(), this.$block);
      }
    }, paramContinuation);
  }
  
  /* Error */
  private static final <T> T runInterruptibleInExpectedContext(CoroutineContext paramCoroutineContext, Function0<? extends T> paramFunction0)
  {
    // Byte code:
    //   0: new 71	kotlinx/coroutines/ThreadState
    //   3: astore_2
    //   4: aload_2
    //   5: aload_0
    //   6: invokestatic 77	kotlinx/coroutines/JobKt:getJob	(Lkotlin/coroutines/CoroutineContext;)Lkotlinx/coroutines/Job;
    //   9: invokespecial 80	kotlinx/coroutines/ThreadState:<init>	(Lkotlinx/coroutines/Job;)V
    //   12: aload_2
    //   13: invokevirtual 84	kotlinx/coroutines/ThreadState:setup	()V
    //   16: aload_1
    //   17: invokeinterface 90 1 0
    //   22: astore_0
    //   23: aload_2
    //   24: invokevirtual 93	kotlinx/coroutines/ThreadState:clearInterrupt	()V
    //   27: aload_0
    //   28: areturn
    //   29: astore_0
    //   30: aload_2
    //   31: invokevirtual 93	kotlinx/coroutines/ThreadState:clearInterrupt	()V
    //   34: aload_0
    //   35: athrow
    //   36: astore_0
    //   37: new 95	java/util/concurrent/CancellationException
    //   40: dup
    //   41: ldc 97
    //   43: invokespecial 100	java/util/concurrent/CancellationException:<init>	(Ljava/lang/String;)V
    //   46: aload_0
    //   47: checkcast 102	java/lang/Throwable
    //   50: invokevirtual 106	java/util/concurrent/CancellationException:initCause	(Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   53: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	54	0	paramCoroutineContext	CoroutineContext
    //   0	54	1	paramFunction0	Function0<? extends T>
    //   3	28	2	localThreadState	ThreadState
    // Exception table:
    //   from	to	target	type
    //   16	23	29	finally
    //   0	16	36	java/lang/InterruptedException
    //   23	27	36	java/lang/InterruptedException
    //   30	36	36	java/lang/InterruptedException
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/InterruptibleKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */