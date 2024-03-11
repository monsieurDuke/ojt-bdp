package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.coroutines.AbstractCoroutineContextElement;
import kotlin.coroutines.CoroutineContext.Key;

@Metadata(d1={"\000\024\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\002\b\001\030\000 \0052\0020\001:\001\005B\005¢\006\002\020\002R\022\020\003\032\0020\0048\006@\006X\016¢\006\002\n\000¨\006\006"}, d2={"Lkotlinx/coroutines/YieldContext;", "Lkotlin/coroutines/AbstractCoroutineContextElement;", "()V", "dispatcherWasUnconfined", "", "Key", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class YieldContext
  extends AbstractCoroutineContextElement
{
  public static final Key Key = new Key(null);
  public boolean dispatcherWasUnconfined;
  
  public YieldContext()
  {
    super((CoroutineContext.Key)Key);
  }
  
  @Metadata(d1={"\000\020\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\b\003\030\0002\b\022\004\022\0020\0020\001B\007\b\002¢\006\002\020\003¨\006\004"}, d2={"Lkotlinx/coroutines/YieldContext$Key;", "Lkotlin/coroutines/CoroutineContext$Key;", "Lkotlinx/coroutines/YieldContext;", "()V", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  public static final class Key
    implements CoroutineContext.Key<YieldContext>
  {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/YieldContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */