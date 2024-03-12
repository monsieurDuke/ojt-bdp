package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.ranges.RangesKt;
import kotlinx.coroutines.AbstractTimeSource;
import kotlinx.coroutines.AbstractTimeSourceKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.EventLoop_commonKt;
import kotlinx.coroutines.GlobalScope;

@Metadata(d1={"\000*\n\000\n\002\020\002\n\000\n\002\020\t\n\002\b\002\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\032/\020\000\032\0020\0012\006\020\002\032\0020\0032\006\020\004\032\0020\0032\f\020\005\032\b\022\004\022\0020\0010\006H@ø\001\000¢\006\002\020\007\032/\020\b\032\0020\0012\006\020\002\032\0020\0032\006\020\004\032\0020\0032\f\020\005\032\b\022\004\022\0020\0010\006H@ø\001\000¢\006\002\020\007\0324\020\t\032\b\022\004\022\0020\0010\n2\006\020\002\032\0020\0032\b\b\002\020\004\032\0020\0032\b\b\002\020\013\032\0020\f2\b\b\002\020\r\032\0020\016H\007\002\004\n\002\b\031¨\006\017"}, d2={"fixedDelayTicker", "", "delayMillis", "", "initialDelayMillis", "channel", "Lkotlinx/coroutines/channels/SendChannel;", "(JJLkotlinx/coroutines/channels/SendChannel;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "fixedPeriodTicker", "ticker", "Lkotlinx/coroutines/channels/ReceiveChannel;", "context", "Lkotlin/coroutines/CoroutineContext;", "mode", "Lkotlinx/coroutines/channels/TickerMode;", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class TickerChannelsKt
{
  private static final Object fixedDelayTicker(long paramLong1, long paramLong2, SendChannel<? super Unit> paramSendChannel, Continuation<? super Unit> paramContinuation)
  {
    if ((paramContinuation instanceof fixedDelayTicker.1))
    {
      local1 = (fixedDelayTicker.1)paramContinuation;
      if ((local1.label & 0x80000000) != 0)
      {
        local1.label += Integer.MIN_VALUE;
        break label53;
      }
    }
    ContinuationImpl local1 = new ContinuationImpl(paramContinuation)
    {
      long J$0;
      Object L$0;
      int label;
      Object result;
      
      public final Object invokeSuspend(Object paramAnonymousObject)
      {
        this.result = paramAnonymousObject;
        this.label |= 0x80000000;
        return TickerChannelsKt.access$fixedDelayTicker(0L, 0L, null, (Continuation)this);
      }
    };
    label53:
    Object localObject2 = local1.result;
    Object localObject1 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
    switch (local1.label)
    {
    default: 
      throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    case 3: 
      paramLong2 = local1.J$0;
      paramSendChannel = (SendChannel)local1.L$0;
      ResultKt.throwOnFailure(localObject2);
      break;
    case 2: 
      paramLong1 = local1.J$0;
      paramSendChannel = (SendChannel)local1.L$0;
      ResultKt.throwOnFailure(localObject2);
      break;
    case 1: 
      paramLong1 = local1.J$0;
      paramContinuation = (SendChannel)local1.L$0;
      ResultKt.throwOnFailure(localObject2);
      break;
    case 0: 
      ResultKt.throwOnFailure(localObject2);
      local1.L$0 = paramSendChannel;
      local1.J$0 = paramLong1;
      local1.label = 1;
      if (DelayKt.delay(paramLong2, local1) == localObject1) {
        return localObject1;
      }
      break;
    }
    for (paramContinuation = paramSendChannel;; paramContinuation = paramSendChannel)
    {
      localObject2 = Unit.INSTANCE;
      local1.L$0 = paramContinuation;
      local1.J$0 = paramLong1;
      local1.label = 2;
      paramSendChannel = paramContinuation;
      if (paramContinuation.send(localObject2, local1) == localObject1) {
        return localObject1;
      }
      local1.L$0 = paramSendChannel;
      local1.J$0 = paramLong1;
      local1.label = 3;
      paramLong2 = paramLong1;
      if (DelayKt.delay(paramLong1, local1) == localObject1) {
        return localObject1;
      }
      paramLong1 = paramLong2;
    }
  }
  
  private static final Object fixedPeriodTicker(long paramLong1, long paramLong2, SendChannel<? super Unit> paramSendChannel, Continuation<? super Unit> paramContinuation)
  {
    if ((paramContinuation instanceof fixedPeriodTicker.1))
    {
      localObject1 = (fixedPeriodTicker.1)paramContinuation;
      if ((((fixedPeriodTicker.1)localObject1).label & 0x80000000) != 0)
      {
        ((fixedPeriodTicker.1)localObject1).label += Integer.MIN_VALUE;
        paramContinuation = (Continuation<? super Unit>)localObject1;
        break label57;
      }
    }
    paramContinuation = new ContinuationImpl(paramContinuation)
    {
      long J$0;
      long J$1;
      Object L$0;
      int label;
      Object result;
      
      public final Object invokeSuspend(Object paramAnonymousObject)
      {
        this.result = paramAnonymousObject;
        this.label |= 0x80000000;
        return TickerChannelsKt.access$fixedPeriodTicker(0L, 0L, null, (Continuation)this);
      }
    };
    label57:
    Object localObject1 = paramContinuation.result;
    Object localObject2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
    long l1;
    switch (paramContinuation.label)
    {
    default: 
      throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    case 4: 
      paramLong1 = paramContinuation.J$1;
      paramLong2 = paramContinuation.J$0;
      paramSendChannel = (SendChannel)paramContinuation.L$0;
      ResultKt.throwOnFailure(localObject1);
      break;
    case 3: 
      paramLong1 = paramContinuation.J$1;
      paramLong2 = paramContinuation.J$0;
      paramSendChannel = (SendChannel)paramContinuation.L$0;
      ResultKt.throwOnFailure(localObject1);
      break;
    case 2: 
      paramLong1 = paramContinuation.J$1;
      paramLong2 = paramContinuation.J$0;
      paramSendChannel = (SendChannel)paramContinuation.L$0;
      ResultKt.throwOnFailure(localObject1);
      break;
    case 1: 
      paramLong2 = paramContinuation.J$1;
      paramLong1 = paramContinuation.J$0;
      paramSendChannel = (SendChannel)paramContinuation.L$0;
      ResultKt.throwOnFailure(localObject1);
      break;
    case 0: 
      ResultKt.throwOnFailure(localObject1);
      localObject1 = AbstractTimeSourceKt.getTimeSource();
      if (localObject1 == null) {
        localObject1 = null;
      } else {
        localObject1 = Boxing.boxLong(((AbstractTimeSource)localObject1).nanoTime());
      }
      if (localObject1 == null) {
        l1 = System.nanoTime();
      } else {
        l1 = ((Long)localObject1).longValue();
      }
      l1 += EventLoop_commonKt.delayToNanos(paramLong2);
      paramContinuation.L$0 = paramSendChannel;
      paramContinuation.J$0 = paramLong1;
      paramContinuation.J$1 = l1;
      paramContinuation.label = 1;
      if (DelayKt.delay(paramLong2, paramContinuation) == localObject2) {
        return localObject2;
      }
      paramLong2 = l1;
    }
    paramLong1 = EventLoop_commonKt.delayToNanos(paramLong1);
    for (;;)
    {
      paramLong2 += paramLong1;
      localObject1 = Unit.INSTANCE;
      paramContinuation.L$0 = paramSendChannel;
      paramContinuation.J$0 = paramLong2;
      paramContinuation.J$1 = paramLong1;
      paramContinuation.label = 2;
      if (paramSendChannel.send(localObject1, paramContinuation) == localObject2) {
        return localObject2;
      }
      localObject1 = AbstractTimeSourceKt.getTimeSource();
      if (localObject1 == null) {
        localObject1 = null;
      } else {
        localObject1 = Boxing.boxLong(((AbstractTimeSource)localObject1).nanoTime());
      }
      if (localObject1 == null) {
        l1 = System.nanoTime();
      } else {
        l1 = ((Long)localObject1).longValue();
      }
      long l2 = RangesKt.coerceAtLeast(paramLong2 - l1, 0L);
      if ((l2 == 0L) && (paramLong1 != 0L))
      {
        l2 = paramLong1 - (l1 - paramLong2) % paramLong1;
        paramLong2 = l1 + l2;
        l1 = EventLoop_commonKt.delayNanosToMillis(l2);
        paramContinuation.L$0 = paramSendChannel;
        paramContinuation.J$0 = paramLong2;
        paramContinuation.J$1 = paramLong1;
        paramContinuation.label = 3;
        if (DelayKt.delay(l1, paramContinuation) == localObject2) {
          return localObject2;
        }
      }
      else
      {
        l1 = EventLoop_commonKt.delayNanosToMillis(l2);
        paramContinuation.L$0 = paramSendChannel;
        paramContinuation.J$0 = paramLong2;
        paramContinuation.J$1 = paramLong1;
        paramContinuation.label = 4;
        if (DelayKt.delay(l1, paramContinuation) == localObject2) {
          return localObject2;
        }
      }
    }
  }
  
  public static final ReceiveChannel<Unit> ticker(final long paramLong1, long paramLong2, CoroutineContext paramCoroutineContext, TickerMode paramTickerMode)
  {
    int j = 1;
    int i;
    if (paramLong1 >= 0L) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      if (paramLong2 >= 0L) {
        i = j;
      } else {
        i = 0;
      }
      if (i != 0) {
        ProduceKt.produce((CoroutineScope)GlobalScope.INSTANCE, Dispatchers.getUnconfined().plus(paramCoroutineContext), 0, (Function2)new SuspendLambda(paramTickerMode, paramLong1)
        {
          final TickerMode $mode;
          private Object L$0;
          int label;
          
          public final Continuation<Unit> create(Object paramAnonymousObject, Continuation<?> paramAnonymousContinuation)
          {
            paramAnonymousContinuation = new 3(this.$mode, paramLong1, this.$initialDelayMillis, paramAnonymousContinuation);
            paramAnonymousContinuation.L$0 = paramAnonymousObject;
            return (Continuation)paramAnonymousContinuation;
          }
          
          public final Object invoke(ProducerScope<? super Unit> paramAnonymousProducerScope, Continuation<? super Unit> paramAnonymousContinuation)
          {
            return ((3)create(paramAnonymousProducerScope, paramAnonymousContinuation)).invokeSuspend(Unit.INSTANCE);
          }
          
          public final Object invokeSuspend(Object paramAnonymousObject)
          {
            Object localObject1 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label)
            {
            default: 
              throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            case 2: 
              ResultKt.throwOnFailure(paramAnonymousObject);
              break;
            case 1: 
              ResultKt.throwOnFailure(paramAnonymousObject);
              break;
            case 0: 
              ResultKt.throwOnFailure(paramAnonymousObject);
              localObject2 = (ProducerScope)this.L$0;
              paramAnonymousObject = this.$mode;
              switch (WhenMappings.$EnumSwitchMapping$0[paramAnonymousObject.ordinal()])
              {
              default: 
                break;
              case 2: 
                l1 = paramLong1;
                l2 = this.$initialDelayMillis;
                paramAnonymousObject = ((ProducerScope)localObject2).getChannel();
                localObject2 = (Continuation)this;
                this.label = 2;
                if (TickerChannelsKt.access$fixedDelayTicker(l1, l2, (SendChannel)paramAnonymousObject, (Continuation)localObject2) == localObject1) {
                  return localObject1;
                }
                break;
              }
              break;
            }
            break label208;
            long l1 = paramLong1;
            long l2 = this.$initialDelayMillis;
            Object localObject2 = ((ProducerScope)localObject2).getChannel();
            paramAnonymousObject = (Continuation)this;
            this.label = 1;
            if (TickerChannelsKt.access$fixedPeriodTicker(l1, l2, (SendChannel)localObject2, (Continuation)paramAnonymousObject) == localObject1) {
              return localObject1;
            }
            label208:
            return Unit.INSTANCE;
          }
        });
      }
      throw new IllegalArgumentException(("Expected non-negative initial delay, but has " + paramLong2 + " ms").toString());
    }
    throw new IllegalArgumentException(("Expected non-negative delay, but has " + paramLong1 + " ms").toString());
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/channels/TickerChannelsKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */