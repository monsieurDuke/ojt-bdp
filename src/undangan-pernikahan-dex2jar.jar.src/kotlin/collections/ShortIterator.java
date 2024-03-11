package kotlin.collections;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(d1={"\000\020\n\002\030\002\n\002\020(\n\002\020\n\n\002\b\005\b&\030\0002\b\022\004\022\0020\0020\001B\005¢\006\002\020\003J\016\020\004\032\0020\002H\002¢\006\002\020\005J\b\020\006\032\0020\002H&¨\006\007"}, d2={"Lkotlin/collections/ShortIterator;", "", "", "()V", "next", "()Ljava/lang/Short;", "nextShort", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public abstract class ShortIterator
  implements Iterator<Short>, KMappedMarker
{
  public final Short next()
  {
    return Short.valueOf(nextShort());
  }
  
  public abstract short nextShort();
  
  public void remove()
  {
    throw new UnsupportedOperationException("Operation is not supported for read-only collection");
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/collections/ShortIterator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */