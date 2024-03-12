package kotlin.coroutines;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;

@Metadata(d1={"\000\022\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\004\b'\030\0002\0020\001B\021\022\n\020\002\032\006\022\002\b\0030\003¢\006\002\020\004R\030\020\002\032\006\022\002\b\0030\003X\004¢\006\b\n\000\032\004\b\005\020\006¨\006\007"}, d2={"Lkotlin/coroutines/AbstractCoroutineContextElement;", "Lkotlin/coroutines/CoroutineContext$Element;", "key", "Lkotlin/coroutines/CoroutineContext$Key;", "(Lkotlin/coroutines/CoroutineContext$Key;)V", "getKey", "()Lkotlin/coroutines/CoroutineContext$Key;", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public abstract class AbstractCoroutineContextElement
  implements CoroutineContext.Element
{
  private final CoroutineContext.Key<?> key;
  
  public AbstractCoroutineContextElement(CoroutineContext.Key<?> paramKey)
  {
    this.key = paramKey;
  }
  
  public <R> R fold(R paramR, Function2<? super R, ? super CoroutineContext.Element, ? extends R> paramFunction2)
  {
    return (R)CoroutineContext.Element.DefaultImpls.fold(this, paramR, paramFunction2);
  }
  
  public <E extends CoroutineContext.Element> E get(CoroutineContext.Key<E> paramKey)
  {
    return CoroutineContext.Element.DefaultImpls.get(this, paramKey);
  }
  
  public CoroutineContext.Key<?> getKey()
  {
    return this.key;
  }
  
  public CoroutineContext minusKey(CoroutineContext.Key<?> paramKey)
  {
    return CoroutineContext.Element.DefaultImpls.minusKey(this, paramKey);
  }
  
  public CoroutineContext plus(CoroutineContext paramCoroutineContext)
  {
    return CoroutineContext.Element.DefaultImpls.plus(this, paramCoroutineContext);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/coroutines/AbstractCoroutineContextElement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */