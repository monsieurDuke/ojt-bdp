package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Element;
import kotlin.coroutines.CoroutineContext.Element.DefaultImpls;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.jvm.functions.Function2;

@Metadata(d1={"\000\020\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\005\bÂ\002\030\0002\0020\0012\b\022\004\022\0020\0000\002B\007\b\002¢\006\002\020\003R\030\020\004\032\006\022\002\b\0030\0028VX\004¢\006\006\032\004\b\005\020\006¨\006\007"}, d2={"Lkotlinx/coroutines/UndispatchedMarker;", "Lkotlin/coroutines/CoroutineContext$Element;", "Lkotlin/coroutines/CoroutineContext$Key;", "()V", "key", "getKey", "()Lkotlin/coroutines/CoroutineContext$Key;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
final class UndispatchedMarker
  implements CoroutineContext.Element, CoroutineContext.Key<UndispatchedMarker>
{
  public static final UndispatchedMarker INSTANCE = new UndispatchedMarker();
  
  public <R> R fold(R paramR, Function2<? super R, ? super CoroutineContext.Element, ? extends R> paramFunction2)
  {
    return (R)CoroutineContext.Element.DefaultImpls.fold((CoroutineContext.Element)this, paramR, paramFunction2);
  }
  
  public <E extends CoroutineContext.Element> E get(CoroutineContext.Key<E> paramKey)
  {
    return CoroutineContext.Element.DefaultImpls.get((CoroutineContext.Element)this, paramKey);
  }
  
  public CoroutineContext.Key<?> getKey()
  {
    return (CoroutineContext.Key)this;
  }
  
  public CoroutineContext minusKey(CoroutineContext.Key<?> paramKey)
  {
    return CoroutineContext.Element.DefaultImpls.minusKey((CoroutineContext.Element)this, paramKey);
  }
  
  public CoroutineContext plus(CoroutineContext paramCoroutineContext)
  {
    return CoroutineContext.Element.DefaultImpls.plus((CoroutineContext.Element)this, paramCoroutineContext);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/UndispatchedMarker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */