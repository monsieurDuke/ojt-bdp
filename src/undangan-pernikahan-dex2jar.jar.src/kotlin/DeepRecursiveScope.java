package kotlin;

import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000\"\n\002\030\002\n\002\b\002\n\002\020\000\n\002\b\006\n\002\030\002\n\002\b\002\n\002\020\001\n\002\030\002\n\000\b7\030\000*\004\b\000\020\001*\004\b\001\020\0022\0020\003B\007\b\004¢\006\002\020\004J\031\020\005\032\0028\0012\006\020\006\032\0028\000H¦@ø\001\000¢\006\002\020\007J5\020\005\032\002H\b\"\004\b\002\020\t\"\004\b\003\020\b*\016\022\004\022\002H\t\022\004\022\002H\b0\n2\006\020\006\032\002H\tH¦@ø\001\000¢\006\002\020\013J\037\020\f\032\0020\r*\n\022\002\b\003\022\002\b\0030\n2\b\020\006\032\004\030\0010\003H\002\001\001\016\002\004\n\002\b\031¨\006\017"}, d2={"Lkotlin/DeepRecursiveScope;", "T", "R", "", "()V", "callRecursive", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "S", "U", "Lkotlin/DeepRecursiveFunction;", "(Lkotlin/DeepRecursiveFunction;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "invoke", "", "Lkotlin/DeepRecursiveScopeImpl;", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public abstract class DeepRecursiveScope<T, R>
{
  public abstract Object callRecursive(T paramT, Continuation<? super R> paramContinuation);
  
  public abstract <U, S> Object callRecursive(DeepRecursiveFunction<U, S> paramDeepRecursiveFunction, U paramU, Continuation<? super S> paramContinuation);
  
  @Deprecated(level=DeprecationLevel.ERROR, message="'invoke' should not be called from DeepRecursiveScope. Use 'callRecursive' to do recursion in the heap instead of the call stack.", replaceWith=@ReplaceWith(expression="this.callRecursive(value)", imports={}))
  public final Void invoke(DeepRecursiveFunction<?, ?> paramDeepRecursiveFunction, Object paramObject)
  {
    Intrinsics.checkNotNullParameter(paramDeepRecursiveFunction, "<this>");
    throw new UnsupportedOperationException("Should not be called from DeepRecursiveScope");
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/DeepRecursiveScope.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */