package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Element;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.jvm.functions.Function2;

@Metadata(d1={"\000\032\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\bg\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\002J\016\020\003\032\b\022\004\022\0028\0000\000H&J\020\020\004\032\0020\0052\006\020\006\032\0020\007H&¨\006\b"}, d2={"Lkotlinx/coroutines/CopyableThreadContextElement;", "S", "Lkotlinx/coroutines/ThreadContextElement;", "copyForChild", "mergeForChild", "Lkotlin/coroutines/CoroutineContext;", "overwritingElement", "Lkotlin/coroutines/CoroutineContext$Element;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public abstract interface CopyableThreadContextElement<S>
  extends ThreadContextElement<S>
{
  public abstract CopyableThreadContextElement<S> copyForChild();
  
  public abstract CoroutineContext mergeForChild(CoroutineContext.Element paramElement);
  
  @Metadata(k=3, mv={1, 6, 0}, xi=48)
  public static final class DefaultImpls
  {
    public static <S, R> R fold(CopyableThreadContextElement<S> paramCopyableThreadContextElement, R paramR, Function2<? super R, ? super CoroutineContext.Element, ? extends R> paramFunction2)
    {
      return (R)ThreadContextElement.DefaultImpls.fold((ThreadContextElement)paramCopyableThreadContextElement, paramR, paramFunction2);
    }
    
    public static <S, E extends CoroutineContext.Element> E get(CopyableThreadContextElement<S> paramCopyableThreadContextElement, CoroutineContext.Key<E> paramKey)
    {
      return ThreadContextElement.DefaultImpls.get((ThreadContextElement)paramCopyableThreadContextElement, paramKey);
    }
    
    public static <S> CoroutineContext minusKey(CopyableThreadContextElement<S> paramCopyableThreadContextElement, CoroutineContext.Key<?> paramKey)
    {
      return ThreadContextElement.DefaultImpls.minusKey((ThreadContextElement)paramCopyableThreadContextElement, paramKey);
    }
    
    public static <S> CoroutineContext plus(CopyableThreadContextElement<S> paramCopyableThreadContextElement, CoroutineContext paramCoroutineContext)
    {
      return ThreadContextElement.DefaultImpls.plus((ThreadContextElement)paramCopyableThreadContextElement, paramCoroutineContext);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/CopyableThreadContextElement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */