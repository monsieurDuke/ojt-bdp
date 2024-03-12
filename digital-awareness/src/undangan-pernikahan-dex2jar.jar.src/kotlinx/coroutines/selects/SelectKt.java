package kotlinx.coroutines.selects;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.internal.Symbol;

@Metadata(d1={"\0004\n\000\n\002\020\000\n\002\b\f\n\002\030\002\n\002\b\004\n\002\030\002\n\002\030\002\n\002\020\002\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\030\002\n\002\b\003\032E\020\020\032\002H\021\"\004\b\000\020\0212\037\b\004\020\022\032\031\022\n\022\b\022\004\022\002H\0210\024\022\004\022\0020\0250\023¢\006\002\b\026HHø\001\000\002\n\n\b\b\001\022\002\020\001 \001¢\006\002\020\027\032N\020\030\032\0020\025\"\004\b\000\020\021*\b\022\004\022\002H\0210\0242\006\020\031\032\0020\0322\034\020\033\032\030\b\001\022\n\022\b\022\004\022\002H\0210\034\022\006\022\004\030\0010\0010\023H\007ø\001\000ø\001\000ø\001\001¢\006\004\b\035\020\036\"\034\020\000\032\0020\0018\000X\004¢\006\016\n\000\022\004\b\002\020\003\032\004\b\004\020\005\"\034\020\006\032\0020\0018\000X\004¢\006\016\n\000\022\004\b\007\020\003\032\004\b\b\020\005\"\026\020\t\032\0020\0018\002X\004¢\006\b\n\000\022\004\b\n\020\003\"\026\020\013\032\0020\0018\002X\004¢\006\b\n\000\022\004\b\f\020\003\"\026\020\r\032\0020\0168\002X\004¢\006\b\n\000\022\004\b\017\020\003\002\013\n\002\b\031\n\005\b¡\0360\001¨\006\037"}, d2={"ALREADY_SELECTED", "", "getALREADY_SELECTED$annotations", "()V", "getALREADY_SELECTED", "()Ljava/lang/Object;", "NOT_SELECTED", "getNOT_SELECTED$annotations", "getNOT_SELECTED", "RESUMED", "getRESUMED$annotations", "UNDECIDED", "getUNDECIDED$annotations", "selectOpSequenceNumber", "Lkotlinx/coroutines/selects/SeqNumber;", "getSelectOpSequenceNumber$annotations", "select", "R", "builder", "Lkotlin/Function1;", "Lkotlinx/coroutines/selects/SelectBuilder;", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "onTimeout", "timeout", "Lkotlin/time/Duration;", "block", "Lkotlin/coroutines/Continuation;", "onTimeout-8Mi8wO0", "(Lkotlinx/coroutines/selects/SelectBuilder;JLkotlin/jvm/functions/Function1;)V", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class SelectKt
{
  private static final Object ALREADY_SELECTED;
  private static final Object NOT_SELECTED = new Symbol("NOT_SELECTED");
  private static final Object RESUMED = new Symbol("RESUMED");
  private static final Object UNDECIDED;
  private static final SeqNumber selectOpSequenceNumber = new SeqNumber();
  
  static
  {
    ALREADY_SELECTED = new Symbol("ALREADY_SELECTED");
    UNDECIDED = new Symbol("UNDECIDED");
  }
  
  public static final Object getALREADY_SELECTED()
  {
    return ALREADY_SELECTED;
  }
  
  public static final Object getNOT_SELECTED()
  {
    return NOT_SELECTED;
  }
  
  public static final <R> void onTimeout-8Mi8wO0(SelectBuilder<? super R> paramSelectBuilder, long paramLong, Function1<? super Continuation<? super R>, ? extends Object> paramFunction1)
  {
    paramSelectBuilder.onTimeout(DelayKt.toDelayMillis-LRDsOJo(paramLong), paramFunction1);
  }
  
  /* Error */
  public static final <R> Object select(Function1<? super SelectBuilder<? super R>, kotlin.Unit> paramFunction1, Continuation<? super R> paramContinuation)
  {
    // Byte code:
    //   0: new 94	kotlinx/coroutines/selects/SelectBuilderImpl
    //   3: dup
    //   4: aload_1
    //   5: invokespecial 97	kotlinx/coroutines/selects/SelectBuilderImpl:<init>	(Lkotlin/coroutines/Continuation;)V
    //   8: astore_2
    //   9: aload_0
    //   10: aload_2
    //   11: invokeinterface 103 2 0
    //   16: pop
    //   17: goto +9 -> 26
    //   20: astore_0
    //   21: aload_2
    //   22: aload_0
    //   23: invokevirtual 107	kotlinx/coroutines/selects/SelectBuilderImpl:handleBuilderException	(Ljava/lang/Throwable;)V
    //   26: aload_2
    //   27: invokevirtual 110	kotlinx/coroutines/selects/SelectBuilderImpl:getResult	()Ljava/lang/Object;
    //   30: astore_0
    //   31: aload_0
    //   32: invokestatic 115	kotlin/coroutines/intrinsics/IntrinsicsKt:getCOROUTINE_SUSPENDED	()Ljava/lang/Object;
    //   35: if_acmpne +7 -> 42
    //   38: aload_1
    //   39: invokestatic 120	kotlin/coroutines/jvm/internal/DebugProbesKt:probeCoroutineSuspended	(Lkotlin/coroutines/Continuation;)V
    //   42: aload_0
    //   43: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	44	0	paramFunction1	Function1<? super SelectBuilder<? super R>, kotlin.Unit>
    //   0	44	1	paramContinuation	Continuation<? super R>
    //   8	19	2	localSelectBuilderImpl	SelectBuilderImpl
    // Exception table:
    //   from	to	target	type
    //   9	17	20	finally
  }
  
  /* Error */
  private static final <R> Object select$$forInline(Function1<? super SelectBuilder<? super R>, kotlin.Unit> paramFunction1, Continuation<? super R> paramContinuation)
  {
    // Byte code:
    //   0: iconst_0
    //   1: invokestatic 127	kotlin/jvm/internal/InlineMarker:mark	(I)V
    //   4: new 94	kotlinx/coroutines/selects/SelectBuilderImpl
    //   7: dup
    //   8: aload_1
    //   9: invokespecial 97	kotlinx/coroutines/selects/SelectBuilderImpl:<init>	(Lkotlin/coroutines/Continuation;)V
    //   12: astore_2
    //   13: aload_0
    //   14: aload_2
    //   15: invokeinterface 103 2 0
    //   20: pop
    //   21: goto +9 -> 30
    //   24: astore_0
    //   25: aload_2
    //   26: aload_0
    //   27: invokevirtual 107	kotlinx/coroutines/selects/SelectBuilderImpl:handleBuilderException	(Ljava/lang/Throwable;)V
    //   30: aload_2
    //   31: invokevirtual 110	kotlinx/coroutines/selects/SelectBuilderImpl:getResult	()Ljava/lang/Object;
    //   34: astore_0
    //   35: aload_0
    //   36: invokestatic 115	kotlin/coroutines/intrinsics/IntrinsicsKt:getCOROUTINE_SUSPENDED	()Ljava/lang/Object;
    //   39: if_acmpne +7 -> 46
    //   42: aload_1
    //   43: invokestatic 120	kotlin/coroutines/jvm/internal/DebugProbesKt:probeCoroutineSuspended	(Lkotlin/coroutines/Continuation;)V
    //   46: iconst_1
    //   47: invokestatic 127	kotlin/jvm/internal/InlineMarker:mark	(I)V
    //   50: aload_0
    //   51: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	52	0	paramFunction1	Function1<? super SelectBuilder<? super R>, kotlin.Unit>
    //   0	52	1	paramContinuation	Continuation<? super R>
    //   12	19	2	localSelectBuilderImpl	SelectBuilderImpl
    // Exception table:
    //   from	to	target	type
    //   13	21	24	finally
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/selects/SelectKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */