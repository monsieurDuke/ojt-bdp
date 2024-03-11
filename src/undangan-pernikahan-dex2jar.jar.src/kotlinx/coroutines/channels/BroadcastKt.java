package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.AbstractCoroutineContextElement;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Lambda;
import kotlinx.coroutines.CoroutineContextKt;
import kotlinx.coroutines.CoroutineExceptionHandler;
import kotlinx.coroutines.CoroutineExceptionHandler.Key;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;

@Metadata(d1={"\000V\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\b\n\000\n\002\030\002\n\000\n\002\030\002\n\002\020\003\n\002\030\002\n\002\b\002\n\002\020\002\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\020\000\n\002\030\002\n\000\n\002\030\002\n\000\032\001\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\0020\0032\b\b\002\020\004\032\0020\0052\b\b\002\020\006\032\0020\0072\b\b\002\020\b\032\0020\t2-\b\002\020\n\032'\022\025\022\023\030\0010\f¢\006\f\b\r\022\b\b\016\022\004\b\b(\017\022\004\022\0020\020\030\0010\013j\004\030\001`\0212/\b\001\020\022\032)\b\001\022\n\022\b\022\004\022\002H\0020\024\022\n\022\b\022\004\022\0020\0200\025\022\006\022\004\030\0010\0260\023¢\006\002\b\027H\007ø\001\000¢\006\002\020\030\0322\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0312\b\b\002\020\006\032\0020\0072\b\b\002\020\b\032\0020\tH\007\002\004\n\002\b\031¨\006\032"}, d2={"broadcast", "Lkotlinx/coroutines/channels/BroadcastChannel;", "E", "Lkotlinx/coroutines/CoroutineScope;", "context", "Lkotlin/coroutines/CoroutineContext;", "capacity", "", "start", "Lkotlinx/coroutines/CoroutineStart;", "onCompletion", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "cause", "", "Lkotlinx/coroutines/CompletionHandler;", "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/channels/ProducerScope;", "Lkotlin/coroutines/Continuation;", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/CoroutineScope;Lkotlin/coroutines/CoroutineContext;ILkotlinx/coroutines/CoroutineStart;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function2;)Lkotlinx/coroutines/channels/BroadcastChannel;", "Lkotlinx/coroutines/channels/ReceiveChannel;", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class BroadcastKt
{
  public static final <E> BroadcastChannel<E> broadcast(CoroutineScope paramCoroutineScope, CoroutineContext paramCoroutineContext, int paramInt, CoroutineStart paramCoroutineStart, Function1<? super Throwable, Unit> paramFunction1, Function2<? super ProducerScope<? super E>, ? super Continuation<? super Unit>, ? extends Object> paramFunction2)
  {
    paramCoroutineContext = CoroutineContextKt.newCoroutineContext(paramCoroutineScope, paramCoroutineContext);
    paramCoroutineScope = BroadcastChannelKt.BroadcastChannel(paramInt);
    if (paramCoroutineStart.isLazy()) {
      paramCoroutineScope = (BroadcastCoroutine)new LazyBroadcastCoroutine(paramCoroutineContext, paramCoroutineScope, paramFunction2);
    } else {
      paramCoroutineScope = new BroadcastCoroutine(paramCoroutineContext, paramCoroutineScope, true);
    }
    if (paramFunction1 != null) {
      paramCoroutineScope.invokeOnCompletion(paramFunction1);
    }
    paramCoroutineScope.start(paramCoroutineStart, paramCoroutineScope, paramFunction2);
    return (BroadcastChannel)paramCoroutineScope;
  }
  
  public static final <E> BroadcastChannel<E> broadcast(ReceiveChannel<? extends E> paramReceiveChannel, int paramInt, CoroutineStart paramCoroutineStart)
  {
    CoroutineScope localCoroutineScope = CoroutineScopeKt.plus((CoroutineScope)GlobalScope.INSTANCE, (CoroutineContext)Dispatchers.getUnconfined());
    CoroutineExceptionHandler localCoroutineExceptionHandler = (CoroutineExceptionHandler)new AbstractCoroutineContextElement(CoroutineExceptionHandler.Key)
    {
      public void handleException(CoroutineContext paramAnonymousCoroutineContext, Throwable paramAnonymousThrowable) {}
    };
    broadcast$default(CoroutineScopeKt.plus(localCoroutineScope, (CoroutineContext)localCoroutineExceptionHandler), null, paramInt, paramCoroutineStart, (Function1)new Lambda(paramReceiveChannel)
    {
      final ReceiveChannel<E> $this_broadcast;
      
      public final void invoke(Throwable paramAnonymousThrowable)
      {
        ChannelsKt.cancelConsumed(this.$this_broadcast, paramAnonymousThrowable);
      }
    }, (Function2)new SuspendLambda(paramReceiveChannel, null)
    {
      final ReceiveChannel<E> $this_broadcast;
      private Object L$0;
      Object L$1;
      int label;
      
      public final Continuation<Unit> create(Object paramAnonymousObject, Continuation<?> paramAnonymousContinuation)
      {
        paramAnonymousContinuation = new 2(this.$this_broadcast, paramAnonymousContinuation);
        paramAnonymousContinuation.L$0 = paramAnonymousObject;
        return (Continuation)paramAnonymousContinuation;
      }
      
      public final Object invoke(ProducerScope<? super E> paramAnonymousProducerScope, Continuation<? super Unit> paramAnonymousContinuation)
      {
        return ((2)create(paramAnonymousProducerScope, paramAnonymousContinuation)).invokeSuspend(Unit.INSTANCE);
      }
      
      public final Object invokeSuspend(Object paramAnonymousObject)
      {
        Object localObject1 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        Object localObject2;
        Object localObject5;
        Object localObject4;
        Object localObject3;
        Object localObject6;
        switch (this.label)
        {
        default: 
          throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        case 2: 
          localObject2 = this;
          localObject5 = (ChannelIterator)((2)localObject2).L$1;
          localObject4 = (ProducerScope)((2)localObject2).L$0;
          ResultKt.throwOnFailure(paramAnonymousObject);
          localObject3 = paramAnonymousObject;
          break;
        case 1: 
          localObject6 = (ChannelIterator)this.L$1;
          localObject2 = (ProducerScope)this.L$0;
          ResultKt.throwOnFailure(paramAnonymousObject);
          localObject3 = this;
          localObject5 = paramAnonymousObject;
          localObject4 = localObject1;
          localObject1 = localObject6;
          localObject6 = paramAnonymousObject;
          break;
        case 0: 
          ResultKt.throwOnFailure(paramAnonymousObject);
          localObject2 = this;
          localObject4 = (ProducerScope)((2)localObject2).L$0;
          localObject5 = ((2)localObject2).$this_broadcast.iterator();
          localObject3 = paramAnonymousObject;
        }
        for (paramAnonymousObject = localObject5;; paramAnonymousObject = localObject5)
        {
          localObject5 = (Continuation)localObject2;
          ((2)localObject2).L$0 = localObject4;
          ((2)localObject2).L$1 = paramAnonymousObject;
          ((2)localObject2).label = 1;
          localObject6 = ((ChannelIterator)paramAnonymousObject).hasNext((Continuation)localObject5);
          if (localObject6 == localObject1) {
            return localObject1;
          }
          localObject5 = localObject3;
          Object localObject7 = localObject4;
          localObject3 = localObject2;
          localObject4 = localObject1;
          localObject2 = localObject7;
          localObject1 = paramAnonymousObject;
          if (!((Boolean)localObject6).booleanValue()) {
            break;
          }
          paramAnonymousObject = ((ChannelIterator)localObject1).next();
          localObject6 = (Continuation)localObject3;
          ((2)localObject3).L$0 = localObject2;
          ((2)localObject3).L$1 = localObject1;
          ((2)localObject3).label = 2;
          if (((ProducerScope)localObject2).send(paramAnonymousObject, (Continuation)localObject6) == localObject4) {
            return localObject4;
          }
          paramAnonymousObject = localObject4;
          localObject6 = localObject3;
          localObject3 = localObject5;
          localObject5 = localObject1;
          localObject4 = localObject2;
          localObject2 = localObject6;
          localObject1 = paramAnonymousObject;
        }
        return Unit.INSTANCE;
      }
    }, 1, null);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/channels/BroadcastKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */