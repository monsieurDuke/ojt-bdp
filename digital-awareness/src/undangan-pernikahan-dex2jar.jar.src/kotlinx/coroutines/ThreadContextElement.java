package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Element;
import kotlin.coroutines.CoroutineContext.Element.DefaultImpls;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.jvm.functions.Function2;

@Metadata(d1={"\000\032\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\005\bf\030\000*\004\b\000\020\0012\0020\002J\035\020\003\032\0020\0042\006\020\005\032\0020\0062\006\020\007\032\0028\000H&¢\006\002\020\bJ\025\020\t\032\0028\0002\006\020\005\032\0020\006H&¢\006\002\020\n¨\006\013"}, d2={"Lkotlinx/coroutines/ThreadContextElement;", "S", "Lkotlin/coroutines/CoroutineContext$Element;", "restoreThreadContext", "", "context", "Lkotlin/coroutines/CoroutineContext;", "oldState", "(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;)V", "updateThreadContext", "(Lkotlin/coroutines/CoroutineContext;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public abstract interface ThreadContextElement<S>
  extends CoroutineContext.Element
{
  public abstract void restoreThreadContext(CoroutineContext paramCoroutineContext, S paramS);
  
  public abstract S updateThreadContext(CoroutineContext paramCoroutineContext);
  
  @Metadata(k=3, mv={1, 6, 0}, xi=48)
  public static final class DefaultImpls
  {
    public static <S, R> R fold(ThreadContextElement<S> paramThreadContextElement, R paramR, Function2<? super R, ? super CoroutineContext.Element, ? extends R> paramFunction2)
    {
      return (R)CoroutineContext.Element.DefaultImpls.fold((CoroutineContext.Element)paramThreadContextElement, paramR, paramFunction2);
    }
    
    public static <S, E extends CoroutineContext.Element> E get(ThreadContextElement<S> paramThreadContextElement, CoroutineContext.Key<E> paramKey)
    {
      return CoroutineContext.Element.DefaultImpls.get((CoroutineContext.Element)paramThreadContextElement, paramKey);
    }
    
    public static <S> CoroutineContext minusKey(ThreadContextElement<S> paramThreadContextElement, CoroutineContext.Key<?> paramKey)
    {
      return CoroutineContext.Element.DefaultImpls.minusKey((CoroutineContext.Element)paramThreadContextElement, paramKey);
    }
    
    public static <S> CoroutineContext plus(ThreadContextElement<S> paramThreadContextElement, CoroutineContext paramCoroutineContext)
    {
      return CoroutineContext.Element.DefaultImpls.plus((CoroutineContext.Element)paramThreadContextElement, paramCoroutineContext);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/ThreadContextElement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */