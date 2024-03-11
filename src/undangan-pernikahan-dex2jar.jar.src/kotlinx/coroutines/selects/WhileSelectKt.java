package kotlinx.coroutines.selects;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

@Metadata(d1={"\000\034\n\000\n\002\020\002\n\000\n\002\030\002\n\002\030\002\n\002\020\013\n\002\030\002\n\002\b\002\0322\020\000\032\0020\0012\037\b\004\020\002\032\031\022\n\022\b\022\004\022\0020\0050\004\022\004\022\0020\0010\003¢\006\002\b\006HHø\001\000¢\006\002\020\007\002\004\n\002\b\031¨\006\b"}, d2={"whileSelect", "", "builder", "Lkotlin/Function1;", "Lkotlinx/coroutines/selects/SelectBuilder;", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class WhileSelectKt
{
  /* Error */
  public static final Object whileSelect(kotlin.jvm.functions.Function1<? super SelectBuilder<? super Boolean>, kotlin.Unit> paramFunction1, Continuation<? super kotlin.Unit> paramContinuation)
  {
    // Byte code:
    //   0: aload_1
    //   1: instanceof 6
    //   4: ifeq +32 -> 36
    //   7: aload_1
    //   8: checkcast 6	kotlinx/coroutines/selects/WhileSelectKt$whileSelect$1
    //   11: astore_3
    //   12: aload_3
    //   13: getfield 31	kotlinx/coroutines/selects/WhileSelectKt$whileSelect$1:label	I
    //   16: ldc 32
    //   18: iand
    //   19: ifeq +17 -> 36
    //   22: aload_3
    //   23: aload_3
    //   24: getfield 31	kotlinx/coroutines/selects/WhileSelectKt$whileSelect$1:label	I
    //   27: ldc 32
    //   29: iadd
    //   30: putfield 31	kotlinx/coroutines/selects/WhileSelectKt$whileSelect$1:label	I
    //   33: goto +12 -> 45
    //   36: new 6	kotlinx/coroutines/selects/WhileSelectKt$whileSelect$1
    //   39: dup
    //   40: aload_1
    //   41: invokespecial 36	kotlinx/coroutines/selects/WhileSelectKt$whileSelect$1:<init>	(Lkotlin/coroutines/Continuation;)V
    //   44: astore_3
    //   45: aload_3
    //   46: getfield 40	kotlinx/coroutines/selects/WhileSelectKt$whileSelect$1:result	Ljava/lang/Object;
    //   49: astore_2
    //   50: invokestatic 46	kotlin/coroutines/intrinsics/IntrinsicsKt:getCOROUTINE_SUSPENDED	()Ljava/lang/Object;
    //   53: astore 4
    //   55: aload_3
    //   56: getfield 31	kotlinx/coroutines/selects/WhileSelectKt$whileSelect$1:label	I
    //   59: tableswitch	default:+21->80, 0:+58->117, 1:+31->90
    //   80: new 48	java/lang/IllegalStateException
    //   83: dup
    //   84: ldc 50
    //   86: invokespecial 53	java/lang/IllegalStateException:<init>	(Ljava/lang/String;)V
    //   89: athrow
    //   90: aload_3
    //   91: getfield 56	kotlinx/coroutines/selects/WhileSelectKt$whileSelect$1:L$0	Ljava/lang/Object;
    //   94: checkcast 58	kotlin/jvm/functions/Function1
    //   97: astore_0
    //   98: aload_2
    //   99: invokestatic 64	kotlin/ResultKt:throwOnFailure	(Ljava/lang/Object;)V
    //   102: aload 4
    //   104: astore_1
    //   105: aload_2
    //   106: astore 5
    //   108: aload_2
    //   109: astore 4
    //   111: aload 5
    //   113: astore_2
    //   114: goto +94 -> 208
    //   117: aload_2
    //   118: invokestatic 64	kotlin/ResultKt:throwOnFailure	(Ljava/lang/Object;)V
    //   121: aload_0
    //   122: astore_1
    //   123: aload 4
    //   125: astore_0
    //   126: aload_3
    //   127: aload_1
    //   128: putfield 56	kotlinx/coroutines/selects/WhileSelectKt$whileSelect$1:L$0	Ljava/lang/Object;
    //   131: aload_3
    //   132: iconst_1
    //   133: putfield 31	kotlinx/coroutines/selects/WhileSelectKt$whileSelect$1:label	I
    //   136: new 66	kotlinx/coroutines/selects/SelectBuilderImpl
    //   139: dup
    //   140: aload_3
    //   141: checkcast 68	kotlin/coroutines/Continuation
    //   144: invokespecial 69	kotlinx/coroutines/selects/SelectBuilderImpl:<init>	(Lkotlin/coroutines/Continuation;)V
    //   147: astore 5
    //   149: aload_1
    //   150: aload 5
    //   152: invokeinterface 73 2 0
    //   157: pop
    //   158: goto +12 -> 170
    //   161: astore 4
    //   163: aload 5
    //   165: aload 4
    //   167: invokevirtual 77	kotlinx/coroutines/selects/SelectBuilderImpl:handleBuilderException	(Ljava/lang/Throwable;)V
    //   170: aload 5
    //   172: invokevirtual 80	kotlinx/coroutines/selects/SelectBuilderImpl:getResult	()Ljava/lang/Object;
    //   175: astore 4
    //   177: aload 4
    //   179: invokestatic 46	kotlin/coroutines/intrinsics/IntrinsicsKt:getCOROUTINE_SUSPENDED	()Ljava/lang/Object;
    //   182: if_acmpne +10 -> 192
    //   185: aload_3
    //   186: checkcast 68	kotlin/coroutines/Continuation
    //   189: invokestatic 85	kotlin/coroutines/jvm/internal/DebugProbesKt:probeCoroutineSuspended	(Lkotlin/coroutines/Continuation;)V
    //   192: aload 4
    //   194: aload_0
    //   195: if_acmpne +5 -> 200
    //   198: aload_0
    //   199: areturn
    //   200: aload_1
    //   201: astore 5
    //   203: aload_0
    //   204: astore_1
    //   205: aload 5
    //   207: astore_0
    //   208: aload 4
    //   210: checkcast 87	java/lang/Boolean
    //   213: invokevirtual 91	java/lang/Boolean:booleanValue	()Z
    //   216: ifeq +14 -> 230
    //   219: aload_1
    //   220: astore 4
    //   222: aload_0
    //   223: astore_1
    //   224: aload 4
    //   226: astore_0
    //   227: goto -101 -> 126
    //   230: getstatic 97	kotlin/Unit:INSTANCE	Lkotlin/Unit;
    //   233: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	234	0	paramFunction1	kotlin.jvm.functions.Function1<? super SelectBuilder<? super Boolean>, kotlin.Unit>
    //   0	234	1	paramContinuation	Continuation<? super kotlin.Unit>
    //   49	69	2	localObject1	Object
    //   11	175	3	local1	whileSelect.1
    //   53	71	4	localObject2	Object
    //   161	5	4	localThrowable	Throwable
    //   175	50	4	localObject3	Object
    //   106	100	5	localObject4	Object
    // Exception table:
    //   from	to	target	type
    //   149	158	161	finally
  }
  
  /* Error */
  private static final Object whileSelect$$forInline(kotlin.jvm.functions.Function1<? super SelectBuilder<? super Boolean>, kotlin.Unit> paramFunction1, Continuation<? super kotlin.Unit> paramContinuation)
  {
    // Byte code:
    //   0: iconst_0
    //   1: invokestatic 106	kotlin/jvm/internal/InlineMarker:mark	(I)V
    //   4: new 66	kotlinx/coroutines/selects/SelectBuilderImpl
    //   7: dup
    //   8: aload_1
    //   9: invokespecial 69	kotlinx/coroutines/selects/SelectBuilderImpl:<init>	(Lkotlin/coroutines/Continuation;)V
    //   12: astore_2
    //   13: aload_0
    //   14: aload_2
    //   15: invokeinterface 73 2 0
    //   20: pop
    //   21: goto +9 -> 30
    //   24: astore_3
    //   25: aload_2
    //   26: aload_3
    //   27: invokevirtual 77	kotlinx/coroutines/selects/SelectBuilderImpl:handleBuilderException	(Ljava/lang/Throwable;)V
    //   30: aload_2
    //   31: invokevirtual 80	kotlinx/coroutines/selects/SelectBuilderImpl:getResult	()Ljava/lang/Object;
    //   34: astore_2
    //   35: aload_2
    //   36: invokestatic 46	kotlin/coroutines/intrinsics/IntrinsicsKt:getCOROUTINE_SUSPENDED	()Ljava/lang/Object;
    //   39: if_acmpne +7 -> 46
    //   42: aload_1
    //   43: invokestatic 85	kotlin/coroutines/jvm/internal/DebugProbesKt:probeCoroutineSuspended	(Lkotlin/coroutines/Continuation;)V
    //   46: iconst_1
    //   47: invokestatic 106	kotlin/jvm/internal/InlineMarker:mark	(I)V
    //   50: aload_2
    //   51: checkcast 87	java/lang/Boolean
    //   54: invokevirtual 91	java/lang/Boolean:booleanValue	()Z
    //   57: ifeq +6 -> 63
    //   60: goto -60 -> 0
    //   63: getstatic 97	kotlin/Unit:INSTANCE	Lkotlin/Unit;
    //   66: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	67	0	paramFunction1	kotlin.jvm.functions.Function1<? super SelectBuilder<? super Boolean>, kotlin.Unit>
    //   0	67	1	paramContinuation	Continuation<? super kotlin.Unit>
    //   12	39	2	localObject	Object
    //   24	3	3	localThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   13	21	24	finally
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/selects/WhileSelectKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */