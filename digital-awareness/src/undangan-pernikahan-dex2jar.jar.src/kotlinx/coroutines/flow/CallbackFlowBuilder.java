package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.internal.ChannelFlow;

@Metadata(d1={"\000@\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\020\002\n\002\020\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\b\n\000\n\002\030\002\n\002\b\006\n\002\030\002\n\000\b\002\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\002BU\022-\020\003\032)\b\001\022\n\022\b\022\004\022\0028\0000\005\022\n\022\b\022\004\022\0020\0070\006\022\006\022\004\030\0010\b0\004¢\006\002\b\t\022\b\b\002\020\n\032\0020\013\022\b\b\002\020\f\032\0020\r\022\b\b\002\020\016\032\0020\017ø\001\000¢\006\002\020\020J\037\020\022\032\0020\0072\f\020\023\032\b\022\004\022\0028\0000\005H@ø\001\000¢\006\002\020\024J&\020\025\032\b\022\004\022\0028\0000\0262\006\020\n\032\0020\0132\006\020\f\032\0020\r2\006\020\016\032\0020\017H\024R:\020\003\032)\b\001\022\n\022\b\022\004\022\0028\0000\005\022\n\022\b\022\004\022\0020\0070\006\022\006\022\004\030\0010\b0\004¢\006\002\b\tX\004ø\001\000¢\006\004\n\002\020\021\002\004\n\002\b\031¨\006\027"}, d2={"Lkotlinx/coroutines/flow/CallbackFlowBuilder;", "T", "Lkotlinx/coroutines/flow/ChannelFlowBuilder;", "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/channels/ProducerScope;", "Lkotlin/coroutines/Continuation;", "", "", "Lkotlin/ExtensionFunctionType;", "context", "Lkotlin/coroutines/CoroutineContext;", "capacity", "", "onBufferOverflow", "Lkotlinx/coroutines/channels/BufferOverflow;", "(Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/CoroutineContext;ILkotlinx/coroutines/channels/BufferOverflow;)V", "Lkotlin/jvm/functions/Function2;", "collectTo", "scope", "(Lkotlinx/coroutines/channels/ProducerScope;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "create", "Lkotlinx/coroutines/flow/internal/ChannelFlow;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
final class CallbackFlowBuilder<T>
  extends ChannelFlowBuilder<T>
{
  private final Function2<ProducerScope<? super T>, Continuation<? super Unit>, Object> block;
  
  public CallbackFlowBuilder(Function2<? super ProducerScope<? super T>, ? super Continuation<? super Unit>, ? extends Object> paramFunction2, CoroutineContext paramCoroutineContext, int paramInt, BufferOverflow paramBufferOverflow)
  {
    super(paramFunction2, paramCoroutineContext, paramInt, paramBufferOverflow);
    this.block = paramFunction2;
  }
  
  protected Object collectTo(ProducerScope<? super T> paramProducerScope, Continuation<? super Unit> paramContinuation)
  {
    Object localObject1;
    if ((paramContinuation instanceof collectTo.1))
    {
      localObject1 = (collectTo.1)paramContinuation;
      if ((((collectTo.1)localObject1).label & 0x80000000) != 0)
      {
        ((collectTo.1)localObject1).label += Integer.MIN_VALUE;
        paramContinuation = (Continuation<? super Unit>)localObject1;
        break label48;
      }
    }
    paramContinuation = new ContinuationImpl(paramContinuation)
    {
      Object L$0;
      int label;
      Object result;
      final CallbackFlowBuilder<T> this$0;
      
      public final Object invokeSuspend(Object paramAnonymousObject)
      {
        this.result = paramAnonymousObject;
        this.label |= 0x80000000;
        return this.this$0.collectTo(null, (Continuation)this);
      }
    };
    label48:
    Object localObject3 = paramContinuation.result;
    Object localObject2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
    switch (paramContinuation.label)
    {
    default: 
      throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    case 1: 
      localObject1 = (ProducerScope)paramContinuation.L$0;
      ResultKt.throwOnFailure(localObject3);
      break;
    case 0: 
      ResultKt.throwOnFailure(localObject3);
      paramContinuation.L$0 = paramProducerScope;
      paramContinuation.label = 1;
      localObject1 = paramProducerScope;
      if (super.collectTo(paramProducerScope, paramContinuation) == localObject2) {
        return localObject2;
      }
      break;
    }
    if (((ProducerScope)localObject1).isClosedForSend()) {
      return Unit.INSTANCE;
    }
    throw new IllegalStateException("'awaitClose { yourCallbackOrListener.cancel() }' should be used in the end of callbackFlow block.\nOtherwise, a callback/listener may leak in case of external cancellation.\nSee callbackFlow API documentation for the details.");
  }
  
  protected ChannelFlow<T> create(CoroutineContext paramCoroutineContext, int paramInt, BufferOverflow paramBufferOverflow)
  {
    return (ChannelFlow)new CallbackFlowBuilder(this.block, paramCoroutineContext, paramInt, paramBufferOverflow);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/flow/CallbackFlowBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */