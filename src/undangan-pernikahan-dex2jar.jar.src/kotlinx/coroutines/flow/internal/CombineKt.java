package kotlinx.coroutines.flow.internal;

import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.IndexedValue;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendFunction;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Lambda;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CompletableJob;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.YieldKt;
import kotlinx.coroutines.channels.Channel;
import kotlinx.coroutines.channels.ChannelKt;
import kotlinx.coroutines.channels.ChannelResult;
import kotlinx.coroutines.channels.ChannelResult.Failed;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.ReceiveChannel;
import kotlinx.coroutines.channels.SendChannel;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

@Metadata(d1={"\000>\n\000\n\002\030\002\n\002\b\006\n\002\030\002\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\000\n\002\020\021\n\000\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\032n\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\003\"\004\b\001\020\004\"\004\b\002\020\0022\f\020\005\032\b\022\004\022\002H\0030\0012\f\020\006\032\b\022\004\022\002H\0040\0012(\020\007\032$\b\001\022\004\022\002H\003\022\004\022\002H\004\022\n\022\b\022\004\022\002H\0020\t\022\006\022\004\030\0010\n0\bH\000ø\001\000¢\006\002\020\013\032\001\020\f\032\0020\r\"\004\b\000\020\002\"\004\b\001\020\016*\b\022\004\022\002H\0020\0172\024\020\020\032\020\022\f\b\001\022\b\022\004\022\002H\0160\0010\0212\026\020\022\032\022\022\016\022\f\022\006\022\004\030\001H\016\030\0010\0210\02329\020\007\0325\b\001\022\n\022\b\022\004\022\002H\0020\017\022\n\022\b\022\004\022\002H\0160\021\022\n\022\b\022\004\022\0020\r0\t\022\006\022\004\030\0010\n0\b¢\006\002\b\024H@ø\001\000¢\006\002\020\025*\034\b\002\020\026\"\n\022\006\022\004\030\0010\n0\0272\n\022\006\022\004\030\0010\n0\027\002\004\n\002\b\031¨\006\030"}, d2={"zipImpl", "Lkotlinx/coroutines/flow/Flow;", "R", "T1", "T2", "flow", "flow2", "transform", "Lkotlin/Function3;", "Lkotlin/coroutines/Continuation;", "", "(Lkotlinx/coroutines/flow/Flow;Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function3;)Lkotlinx/coroutines/flow/Flow;", "combineInternal", "", "T", "Lkotlinx/coroutines/flow/FlowCollector;", "flows", "", "arrayFactory", "Lkotlin/Function0;", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/flow/FlowCollector;[Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function3;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Update", "Lkotlin/collections/IndexedValue;", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class CombineKt
{
  public static final <R, T> Object combineInternal(final FlowCollector<? super R> paramFlowCollector, Flow<? extends T>[] paramArrayOfFlow, final Function0<T[]> paramFunction0, final Function3<? super FlowCollector<? super R>, ? super T[], ? super Continuation<? super Unit>, ? extends Object> paramFunction3, Continuation<? super Unit> paramContinuation)
  {
    paramFlowCollector = FlowCoroutineKt.flowScope((Function2)new SuspendLambda(paramArrayOfFlow, paramFunction0)
    {
      final Flow<T>[] $flows;
      int I$0;
      int I$1;
      private Object L$0;
      Object L$1;
      Object L$2;
      int label;
      
      public final Continuation<Unit> create(Object paramAnonymousObject, Continuation<?> paramAnonymousContinuation)
      {
        paramAnonymousContinuation = new 2(this.$flows, paramFunction0, paramFunction3, paramFlowCollector, paramAnonymousContinuation);
        paramAnonymousContinuation.L$0 = paramAnonymousObject;
        return (Continuation)paramAnonymousContinuation;
      }
      
      public final Object invoke(CoroutineScope paramAnonymousCoroutineScope, Continuation<? super Unit> paramAnonymousContinuation)
      {
        return ((2)create(paramAnonymousCoroutineScope, paramAnonymousContinuation)).invokeSuspend(Unit.INSTANCE);
      }
      
      public final Object invokeSuspend(Object paramAnonymousObject)
      {
        Object localObject4 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        2 local2;
        final int i;
        int j;
        final Object localObject1;
        final Channel localChannel;
        switch (this.label)
        {
        default: 
          throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        case 3: 
          local2 = this;
          i = local2.I$1;
          j = local2.I$0;
          localObject1 = (byte[])local2.L$2;
          localChannel = (Channel)local2.L$1;
          localObject2 = (Object[])local2.L$0;
          ResultKt.throwOnFailure(paramAnonymousObject);
          paramAnonymousObject = localObject2;
          break;
        case 2: 
          local2 = this;
          i = local2.I$1;
          j = local2.I$0;
          localObject1 = (byte[])local2.L$2;
          localChannel = (Channel)local2.L$1;
          localObject2 = (Object[])local2.L$0;
          ResultKt.throwOnFailure(paramAnonymousObject);
          paramAnonymousObject = localObject2;
          break;
        case 1: 
          local2 = this;
          i = local2.I$1;
          j = local2.I$0;
          localObject1 = (byte[])local2.L$2;
          localChannel = (Channel)local2.L$1;
          localObject2 = (Object[])local2.L$0;
          ResultKt.throwOnFailure(paramAnonymousObject);
          localObject3 = ((ChannelResult)paramAnonymousObject).unbox-impl();
          paramAnonymousObject = localObject2;
          localObject2 = localObject3;
          break;
        case 0: 
          ResultKt.throwOnFailure(paramAnonymousObject);
          local2 = this;
          localObject2 = (CoroutineScope)local2.L$0;
          j = local2.$flows.length;
          if (j == 0) {
            return Unit.INSTANCE;
          }
          paramAnonymousObject = new Object[j];
          ArraysKt.fill$default((Object[])paramAnonymousObject, NullSurrogateKt.UNINITIALIZED, 0, 0, 6, null);
          localChannel = ChannelKt.Channel$default(j, null, null, 6, null);
          localObject1 = new AtomicInteger(j);
          for (i = 0; i < j; i++) {
            BuildersKt.launch$default((CoroutineScope)localObject2, null, null, (Function2)new SuspendLambda(local2.$flows, i)
            {
              final Flow<T>[] $flows;
              int label;
              
              public final Continuation<Unit> create(Object paramAnonymous2Object, Continuation<?> paramAnonymous2Continuation)
              {
                return (Continuation)new 1(this.$flows, i, localObject1, localChannel, paramAnonymous2Continuation);
              }
              
              public final Object invoke(CoroutineScope paramAnonymous2CoroutineScope, Continuation<? super Unit> paramAnonymous2Continuation)
              {
                return ((1)create(paramAnonymous2CoroutineScope, paramAnonymous2Continuation)).invokeSuspend(Unit.INSTANCE);
              }
              
              /* Error */
              public final Object invokeSuspend(Object paramAnonymous2Object)
              {
                // Byte code:
                //   0: invokestatic 101	kotlin/coroutines/intrinsics/IntrinsicsKt:getCOROUTINE_SUSPENDED	()Ljava/lang/Object;
                //   3: astore_2
                //   4: aload_0
                //   5: getfield 103	kotlinx/coroutines/flow/internal/CombineKt$combineInternal$2$1:label	I
                //   8: tableswitch	default:+24->32, 0:+45->53, 1:+34->42
                //   32: new 105	java/lang/IllegalStateException
                //   35: dup
                //   36: ldc 107
                //   38: invokespecial 110	java/lang/IllegalStateException:<init>	(Ljava/lang/String;)V
                //   41: athrow
                //   42: aload_1
                //   43: invokestatic 116	kotlin/ResultKt:throwOnFailure	(Ljava/lang/Object;)V
                //   46: goto +70 -> 116
                //   49: astore_1
                //   50: goto +95 -> 145
                //   53: aload_1
                //   54: invokestatic 116	kotlin/ResultKt:throwOnFailure	(Ljava/lang/Object;)V
                //   57: aload_0
                //   58: getfield 60	kotlinx/coroutines/flow/internal/CombineKt$combineInternal$2$1:$flows	[Lkotlinx/coroutines/flow/Flow;
                //   61: aload_0
                //   62: getfield 62	kotlinx/coroutines/flow/internal/CombineKt$combineInternal$2$1:$i	I
                //   65: aaload
                //   66: astore_1
                //   67: new 14	kotlinx/coroutines/flow/internal/CombineKt$combineInternal$2$1$1
                //   70: astore_3
                //   71: aload_3
                //   72: aload_0
                //   73: getfield 66	kotlinx/coroutines/flow/internal/CombineKt$combineInternal$2$1:$resultChannel	Lkotlinx/coroutines/channels/Channel;
                //   76: aload_0
                //   77: getfield 62	kotlinx/coroutines/flow/internal/CombineKt$combineInternal$2$1:$i	I
                //   80: invokespecial 119	kotlinx/coroutines/flow/internal/CombineKt$combineInternal$2$1$1:<init>	(Lkotlinx/coroutines/channels/Channel;I)V
                //   83: aload_3
                //   84: checkcast 121	kotlinx/coroutines/flow/FlowCollector
                //   87: astore 4
                //   89: aload_0
                //   90: checkcast 78	kotlin/coroutines/Continuation
                //   93: astore_3
                //   94: aload_0
                //   95: iconst_1
                //   96: putfield 103	kotlinx/coroutines/flow/internal/CombineKt$combineInternal$2$1:label	I
                //   99: aload_1
                //   100: aload 4
                //   102: aload_3
                //   103: invokeinterface 127 3 0
                //   108: astore_1
                //   109: aload_1
                //   110: aload_2
                //   111: if_acmpne +5 -> 116
                //   114: aload_2
                //   115: areturn
                //   116: aload_0
                //   117: getfield 64	kotlinx/coroutines/flow/internal/CombineKt$combineInternal$2$1:$nonClosed	Ljava/util/concurrent/atomic/AtomicInteger;
                //   120: invokevirtual 133	java/util/concurrent/atomic/AtomicInteger:decrementAndGet	()I
                //   123: ifne +17 -> 140
                //   126: aload_0
                //   127: getfield 66	kotlinx/coroutines/flow/internal/CombineKt$combineInternal$2$1:$resultChannel	Lkotlinx/coroutines/channels/Channel;
                //   130: checkcast 135	kotlinx/coroutines/channels/SendChannel
                //   133: aconst_null
                //   134: iconst_1
                //   135: aconst_null
                //   136: invokestatic 141	kotlinx/coroutines/channels/SendChannel$DefaultImpls:close$default	(Lkotlinx/coroutines/channels/SendChannel;Ljava/lang/Throwable;ILjava/lang/Object;)Z
                //   139: pop
                //   140: getstatic 94	kotlin/Unit:INSTANCE	Lkotlin/Unit;
                //   143: areturn
                //   144: astore_1
                //   145: aload_0
                //   146: getfield 64	kotlinx/coroutines/flow/internal/CombineKt$combineInternal$2$1:$nonClosed	Ljava/util/concurrent/atomic/AtomicInteger;
                //   149: invokevirtual 133	java/util/concurrent/atomic/AtomicInteger:decrementAndGet	()I
                //   152: ifne +17 -> 169
                //   155: aload_0
                //   156: getfield 66	kotlinx/coroutines/flow/internal/CombineKt$combineInternal$2$1:$resultChannel	Lkotlinx/coroutines/channels/Channel;
                //   159: checkcast 135	kotlinx/coroutines/channels/SendChannel
                //   162: aconst_null
                //   163: iconst_1
                //   164: aconst_null
                //   165: invokestatic 141	kotlinx/coroutines/channels/SendChannel$DefaultImpls:close$default	(Lkotlinx/coroutines/channels/SendChannel;Ljava/lang/Throwable;ILjava/lang/Object;)Z
                //   168: pop
                //   169: aload_1
                //   170: athrow
                // Local variable table:
                //   start	length	slot	name	signature
                //   0	171	0	this	1
                //   0	171	1	paramAnonymous2Object	Object
                //   3	112	2	localObject1	Object
                //   70	33	3	localObject2	Object
                //   87	14	4	localFlowCollector	FlowCollector
                // Exception table:
                //   from	to	target	type
                //   42	46	49	finally
                //   57	109	144	finally
              }
            }, 3, null);
          }
          localObject1 = new byte[j];
          i = 0;
          i = (byte)(i + 1);
          localObject2 = (Continuation)local2;
          local2.L$0 = paramAnonymousObject;
          local2.L$1 = localChannel;
          local2.L$2 = localObject1;
          local2.I$0 = j;
          local2.I$1 = i;
          local2.label = 1;
          localObject2 = localChannel.receiveCatching-JP2dKIU((Continuation)localObject2);
          if (localObject2 == localObject4) {
            return localObject4;
          }
          break;
        }
        Object localObject3 = (IndexedValue)ChannelResult.getOrNull-impl(localObject2);
        int k = j;
        Object localObject2 = localObject3;
        if (localObject3 == null) {
          return Unit.INSTANCE;
        }
        for (;;)
        {
          int m = ((IndexedValue)localObject2).getIndex();
          localObject3 = paramAnonymousObject[m];
          paramAnonymousObject[m] = ((IndexedValue)localObject2).getValue();
          j = k;
          if (localObject3 == NullSurrogateKt.UNINITIALIZED) {
            j = k - 1;
          }
          if (localObject1[m] != i)
          {
            localObject1[m] = ((byte)i);
            localObject2 = (IndexedValue)ChannelResult.getOrNull-impl(localChannel.tryReceive-PtdJZtk());
            if (localObject2 != null) {}
          }
          else
          {
            if (j == 0)
            {
              Object[] arrayOfObject = (Object[])paramFunction0.invoke();
              if (arrayOfObject == null)
              {
                localObject2 = paramFunction3;
                localObject3 = paramFlowCollector;
                local2.L$0 = paramAnonymousObject;
                local2.L$1 = localChannel;
                local2.L$2 = localObject1;
                local2.I$0 = j;
                local2.I$1 = i;
                local2.label = 2;
                if (((Function3)localObject2).invoke(localObject3, paramAnonymousObject, local2) == localObject4) {
                  return localObject4;
                }
                break;
              }
              ArraysKt.copyInto$default((Object[])paramAnonymousObject, arrayOfObject, 0, 0, 0, 14, null);
              localObject3 = paramFunction3;
              localObject2 = paramFlowCollector;
              local2.L$0 = paramAnonymousObject;
              local2.L$1 = localChannel;
              local2.L$2 = localObject1;
              local2.I$0 = j;
              local2.I$1 = i;
              local2.label = 3;
              if (((Function3)localObject3).invoke(localObject2, arrayOfObject, local2) == localObject4) {
                return localObject4;
              }
              break;
            }
            break;
          }
          k = j;
        }
      }
    }, paramContinuation);
    if (paramFlowCollector == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      return paramFlowCollector;
    }
    return Unit.INSTANCE;
  }
  
  public static final <T1, T2, R> Flow<R> zipImpl(final Flow<? extends T1> paramFlow, Flow<? extends T2> paramFlow1, final Function3<? super T1, ? super T2, ? super Continuation<? super R>, ? extends Object> paramFunction3)
  {
    (Flow)new Flow()
    {
      final Flow $flow2$inlined;
      
      public Object collect(FlowCollector<? super R> paramAnonymousFlowCollector, Continuation<? super Unit> paramAnonymousContinuation)
      {
        paramAnonymousFlowCollector = CoroutineScopeKt.coroutineScope((Function2)new CombineKt.zipImpl.1.1(paramAnonymousFlowCollector, this.$flow2$inlined, paramFlow, paramFunction3, null), paramAnonymousContinuation);
        if (paramAnonymousFlowCollector == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
          return paramAnonymousFlowCollector;
        }
        return Unit.INSTANCE;
      }
    };
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/flow/internal/CombineKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */