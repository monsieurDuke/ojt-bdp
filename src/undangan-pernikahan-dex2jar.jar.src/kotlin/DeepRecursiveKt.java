package kotlin;

import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000(\n\000\n\002\030\002\n\002\020\000\n\002\b\006\n\002\030\002\n\002\b\003\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\000\0322\020\006\032\002H\007\"\004\b\000\020\b\"\004\b\001\020\007*\016\022\004\022\002H\b\022\004\022\002H\0070\t2\006\020\n\032\002H\bH\002¢\006\002\020\013\"!\020\000\032\b\022\004\022\0020\0020\0018\002X\004ø\001\000¢\006\n\n\002\020\005\022\004\b\003\020\004*r\b\002\020\f\"5\b\001\022\f\022\n\022\002\b\003\022\002\b\0030\016\022\006\022\004\030\0010\002\022\f\022\n\022\006\022\004\030\0010\0020\017\022\006\022\004\030\0010\0020\r¢\006\002\b\02025\b\001\022\f\022\n\022\002\b\003\022\002\b\0030\016\022\006\022\004\030\0010\002\022\f\022\n\022\006\022\004\030\0010\0020\017\022\006\022\004\030\0010\0020\r¢\006\002\b\020\002\004\n\002\b\031¨\006\021"}, d2={"UNDEFINED_RESULT", "Lkotlin/Result;", "", "getUNDEFINED_RESULT$annotations", "()V", "Ljava/lang/Object;", "invoke", "R", "T", "Lkotlin/DeepRecursiveFunction;", "value", "(Lkotlin/DeepRecursiveFunction;Ljava/lang/Object;)Ljava/lang/Object;", "DeepRecursiveFunctionBlock", "Lkotlin/Function3;", "Lkotlin/DeepRecursiveScope;", "Lkotlin/coroutines/Continuation;", "Lkotlin/ExtensionFunctionType;", "kotlin-stdlib"}, k=2, mv={1, 7, 1}, xi=48)
public final class DeepRecursiveKt
{
  private static final Object UNDEFINED_RESULT = Result.constructor-impl(IntrinsicsKt.getCOROUTINE_SUSPENDED());
  
  static
  {
    Result.Companion localCompanion = Result.Companion;
  }
  
  public static final <T, R> R invoke(DeepRecursiveFunction<T, R> paramDeepRecursiveFunction, T paramT)
  {
    Intrinsics.checkNotNullParameter(paramDeepRecursiveFunction, "<this>");
    return (R)new DeepRecursiveScopeImpl(paramDeepRecursiveFunction.getBlock$kotlin_stdlib(), paramT).runCallLoop();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/DeepRecursiveKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */