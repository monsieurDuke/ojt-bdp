package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function2;

@Metadata(d1={"\000&\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\020\002\n\002\020\000\n\002\030\002\n\002\b\b\b\000\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\002BE\022\f\020\003\032\b\022\004\022\0028\0000\002\022-\020\004\032)\b\001\022\n\022\b\022\004\022\0028\0000\002\022\n\022\b\022\004\022\0020\0070\006\022\006\022\004\030\0010\b0\005¢\006\002\b\tø\001\000¢\006\002\020\nJ\031\020\f\032\0020\0072\006\020\r\032\0028\000HAø\001\000¢\006\002\020\016J\021\020\017\032\0020\007H@ø\001\000¢\006\002\020\020R:\020\004\032)\b\001\022\n\022\b\022\004\022\0028\0000\002\022\n\022\b\022\004\022\0020\0070\006\022\006\022\004\030\0010\b0\005¢\006\002\b\tX\004ø\001\000¢\006\004\n\002\020\013R\024\020\003\032\b\022\004\022\0028\0000\002X\004¢\006\002\n\000\002\004\n\002\b\031¨\006\021"}, d2={"Lkotlinx/coroutines/flow/SubscribedFlowCollector;", "T", "Lkotlinx/coroutines/flow/FlowCollector;", "collector", "action", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/jvm/functions/Function2;)V", "Lkotlin/jvm/functions/Function2;", "emit", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "onSubscription", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class SubscribedFlowCollector<T>
  implements FlowCollector<T>
{
  private final Function2<FlowCollector<? super T>, Continuation<? super Unit>, Object> action;
  private final FlowCollector<T> collector;
  
  public SubscribedFlowCollector(FlowCollector<? super T> paramFlowCollector, Function2<? super FlowCollector<? super T>, ? super Continuation<? super Unit>, ? extends Object> paramFunction2)
  {
    this.collector = paramFlowCollector;
    this.action = paramFunction2;
  }
  
  public Object emit(T paramT, Continuation<? super Unit> paramContinuation)
  {
    return this.collector.emit(paramT, paramContinuation);
  }
  
  /* Error */
  public final Object onSubscription(Continuation<? super Unit> paramContinuation)
  {
    // Byte code:
    //   0: aload_1
    //   1: instanceof 9
    //   4: ifeq +34 -> 38
    //   7: aload_1
    //   8: checkcast 9	kotlinx/coroutines/flow/SubscribedFlowCollector$onSubscription$1
    //   11: astore_2
    //   12: aload_2
    //   13: getfield 58	kotlinx/coroutines/flow/SubscribedFlowCollector$onSubscription$1:label	I
    //   16: ldc 59
    //   18: iand
    //   19: ifeq +19 -> 38
    //   22: aload_2
    //   23: aload_2
    //   24: getfield 58	kotlinx/coroutines/flow/SubscribedFlowCollector$onSubscription$1:label	I
    //   27: ldc 59
    //   29: iadd
    //   30: putfield 58	kotlinx/coroutines/flow/SubscribedFlowCollector$onSubscription$1:label	I
    //   33: aload_2
    //   34: astore_1
    //   35: goto +13 -> 48
    //   38: new 9	kotlinx/coroutines/flow/SubscribedFlowCollector$onSubscription$1
    //   41: dup
    //   42: aload_0
    //   43: aload_1
    //   44: invokespecial 62	kotlinx/coroutines/flow/SubscribedFlowCollector$onSubscription$1:<init>	(Lkotlinx/coroutines/flow/SubscribedFlowCollector;Lkotlin/coroutines/Continuation;)V
    //   47: astore_1
    //   48: aload_1
    //   49: getfield 66	kotlinx/coroutines/flow/SubscribedFlowCollector$onSubscription$1:result	Ljava/lang/Object;
    //   52: astore 6
    //   54: invokestatic 72	kotlin/coroutines/intrinsics/IntrinsicsKt:getCOROUTINE_SUSPENDED	()Ljava/lang/Object;
    //   57: astore 5
    //   59: aload_1
    //   60: getfield 58	kotlinx/coroutines/flow/SubscribedFlowCollector$onSubscription$1:label	I
    //   63: tableswitch	default:+25->88, 0:+75->138, 1:+43->106, 2:+35->98
    //   88: new 74	java/lang/IllegalStateException
    //   91: dup
    //   92: ldc 76
    //   94: invokespecial 79	java/lang/IllegalStateException:<init>	(Ljava/lang/String;)V
    //   97: athrow
    //   98: aload 6
    //   100: invokestatic 85	kotlin/ResultKt:throwOnFailure	(Ljava/lang/Object;)V
    //   103: goto +172 -> 275
    //   106: aload_1
    //   107: getfield 88	kotlinx/coroutines/flow/SubscribedFlowCollector$onSubscription$1:L$1	Ljava/lang/Object;
    //   110: checkcast 90	kotlinx/coroutines/flow/internal/SafeCollector
    //   113: astore_3
    //   114: aload_1
    //   115: getfield 93	kotlinx/coroutines/flow/SubscribedFlowCollector$onSubscription$1:L$0	Ljava/lang/Object;
    //   118: checkcast 2	kotlinx/coroutines/flow/SubscribedFlowCollector
    //   121: astore 4
    //   123: aload_3
    //   124: astore_2
    //   125: aload 6
    //   127: invokestatic 85	kotlin/ResultKt:throwOnFailure	(Ljava/lang/Object;)V
    //   130: aload_3
    //   131: astore_2
    //   132: aload 4
    //   134: astore_3
    //   135: goto +91 -> 226
    //   138: aload 6
    //   140: invokestatic 85	kotlin/ResultKt:throwOnFailure	(Ljava/lang/Object;)V
    //   143: aload_0
    //   144: astore_3
    //   145: new 90	kotlinx/coroutines/flow/internal/SafeCollector
    //   148: dup
    //   149: aload_3
    //   150: getfield 46	kotlinx/coroutines/flow/SubscribedFlowCollector:collector	Lkotlinx/coroutines/flow/FlowCollector;
    //   153: aload_1
    //   154: invokeinterface 99 1 0
    //   159: invokespecial 102	kotlinx/coroutines/flow/internal/SafeCollector:<init>	(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/CoroutineContext;)V
    //   162: astore 4
    //   164: aload 4
    //   166: astore_2
    //   167: aload_3
    //   168: getfield 48	kotlinx/coroutines/flow/SubscribedFlowCollector:action	Lkotlin/jvm/functions/Function2;
    //   171: astore 6
    //   173: aload 4
    //   175: astore_2
    //   176: aload_1
    //   177: aload_3
    //   178: putfield 93	kotlinx/coroutines/flow/SubscribedFlowCollector$onSubscription$1:L$0	Ljava/lang/Object;
    //   181: aload 4
    //   183: astore_2
    //   184: aload_1
    //   185: aload 4
    //   187: putfield 88	kotlinx/coroutines/flow/SubscribedFlowCollector$onSubscription$1:L$1	Ljava/lang/Object;
    //   190: aload 4
    //   192: astore_2
    //   193: aload_1
    //   194: iconst_1
    //   195: putfield 58	kotlinx/coroutines/flow/SubscribedFlowCollector$onSubscription$1:label	I
    //   198: aload 4
    //   200: astore_2
    //   201: aload 6
    //   203: aload 4
    //   205: aload_1
    //   206: invokeinterface 108 3 0
    //   211: astore 6
    //   213: aload 4
    //   215: astore_2
    //   216: aload 6
    //   218: aload 5
    //   220: if_acmpne +6 -> 226
    //   223: aload 5
    //   225: areturn
    //   226: aload_2
    //   227: invokevirtual 111	kotlinx/coroutines/flow/internal/SafeCollector:releaseIntercepted	()V
    //   230: aload_3
    //   231: getfield 46	kotlinx/coroutines/flow/SubscribedFlowCollector:collector	Lkotlinx/coroutines/flow/FlowCollector;
    //   234: astore_2
    //   235: aload_2
    //   236: instanceof 2
    //   239: ifeq +40 -> 279
    //   242: aload_2
    //   243: checkcast 2	kotlinx/coroutines/flow/SubscribedFlowCollector
    //   246: astore_2
    //   247: aload_1
    //   248: aconst_null
    //   249: putfield 93	kotlinx/coroutines/flow/SubscribedFlowCollector$onSubscription$1:L$0	Ljava/lang/Object;
    //   252: aload_1
    //   253: aconst_null
    //   254: putfield 88	kotlinx/coroutines/flow/SubscribedFlowCollector$onSubscription$1:L$1	Ljava/lang/Object;
    //   257: aload_1
    //   258: iconst_2
    //   259: putfield 58	kotlinx/coroutines/flow/SubscribedFlowCollector$onSubscription$1:label	I
    //   262: aload_2
    //   263: aload_1
    //   264: invokevirtual 113	kotlinx/coroutines/flow/SubscribedFlowCollector:onSubscription	(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;
    //   267: aload 5
    //   269: if_acmpne +6 -> 275
    //   272: aload 5
    //   274: areturn
    //   275: getstatic 119	kotlin/Unit:INSTANCE	Lkotlin/Unit;
    //   278: areturn
    //   279: goto -4 -> 275
    //   282: astore_1
    //   283: aload_2
    //   284: invokevirtual 111	kotlinx/coroutines/flow/internal/SafeCollector:releaseIntercepted	()V
    //   287: aload_1
    //   288: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	289	0	this	SubscribedFlowCollector
    //   0	289	1	paramContinuation	Continuation<? super Unit>
    //   11	273	2	localObject1	Object
    //   113	118	3	localObject2	Object
    //   121	93	4	localObject3	Object
    //   57	216	5	localObject4	Object
    //   52	165	6	localObject5	Object
    // Exception table:
    //   from	to	target	type
    //   125	130	282	finally
    //   167	173	282	finally
    //   176	181	282	finally
    //   184	190	282	finally
    //   193	198	282	finally
    //   201	213	282	finally
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/flow/SubscribedFlowCollector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */