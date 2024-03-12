package kotlin;

import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function3;

@Metadata(d1={"\000\"\n\002\030\002\n\002\b\002\n\002\020\000\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\005\b\007\030\000*\004\b\000\020\001*\004\b\001\020\0022\0020\003BC\0229\020\004\0325\b\001\022\020\022\016\022\004\022\0028\000\022\004\022\0028\0010\006\022\004\022\0028\000\022\n\022\b\022\004\022\0028\0010\007\022\006\022\004\030\0010\0030\005¢\006\002\b\bø\001\000¢\006\002\020\tRL\020\004\0325\b\001\022\020\022\016\022\004\022\0028\000\022\004\022\0028\0010\006\022\004\022\0028\000\022\n\022\b\022\004\022\0028\0010\007\022\006\022\004\030\0010\0030\005¢\006\002\b\bX\004ø\001\000¢\006\n\n\002\020\f\032\004\b\n\020\013\002\004\n\002\b\031¨\006\r"}, d2={"Lkotlin/DeepRecursiveFunction;", "T", "R", "", "block", "Lkotlin/Function3;", "Lkotlin/DeepRecursiveScope;", "Lkotlin/coroutines/Continuation;", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/jvm/functions/Function3;)V", "getBlock$kotlin_stdlib", "()Lkotlin/jvm/functions/Function3;", "Lkotlin/jvm/functions/Function3;", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public final class DeepRecursiveFunction<T, R>
{
  private final Function3<DeepRecursiveScope<T, R>, T, Continuation<? super R>, Object> block;
  
  public DeepRecursiveFunction(Function3<? super DeepRecursiveScope<T, R>, ? super T, ? super Continuation<? super R>, ? extends Object> paramFunction3)
  {
    this.block = paramFunction3;
  }
  
  public final Function3<DeepRecursiveScope<T, R>, T, Continuation<? super R>, Object> getBlock$kotlin_stdlib()
  {
    return this.block;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/DeepRecursiveFunction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */