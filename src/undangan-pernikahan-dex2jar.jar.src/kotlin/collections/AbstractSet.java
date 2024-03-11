package kotlin.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(d1={"\000&\n\002\030\002\n\000\n\002\030\002\n\002\020\"\n\002\b\002\n\002\020\013\n\000\n\002\020\000\n\000\n\002\020\b\n\002\b\002\b'\030\000 \013*\006\b\000\020\001 \0012\b\022\004\022\002H\0010\0022\b\022\004\022\002H\0010\003:\001\013B\007\b\004¢\006\002\020\004J\023\020\005\032\0020\0062\b\020\007\032\004\030\0010\bH\002J\b\020\t\032\0020\nH\026¨\006\f"}, d2={"Lkotlin/collections/AbstractSet;", "E", "Lkotlin/collections/AbstractCollection;", "", "()V", "equals", "", "other", "", "hashCode", "", "Companion", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public abstract class AbstractSet<E>
  extends AbstractCollection<E>
  implements Set<E>, KMappedMarker
{
  public static final Companion Companion = new Companion(null);
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (!(paramObject instanceof Set)) {
      return false;
    }
    return Companion.setEquals$kotlin_stdlib((Set)this, (Set)paramObject);
  }
  
  public int hashCode()
  {
    return Companion.unorderedHashCode$kotlin_stdlib((Collection)this);
  }
  
  public Iterator<E> iterator()
  {
    throw new UnsupportedOperationException("Operation is not supported for read-only collection");
  }
  
  @Metadata(d1={"\000&\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\013\n\000\n\002\020\"\n\002\b\003\n\002\020\b\n\002\020\036\n\002\b\002\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J%\020\003\032\0020\0042\n\020\005\032\006\022\002\b\0030\0062\n\020\007\032\006\022\002\b\0030\006H\000¢\006\002\b\bJ\031\020\t\032\0020\n2\n\020\005\032\006\022\002\b\0030\013H\000¢\006\002\b\f¨\006\r"}, d2={"Lkotlin/collections/AbstractSet$Companion;", "", "()V", "setEquals", "", "c", "", "other", "setEquals$kotlin_stdlib", "unorderedHashCode", "", "", "unorderedHashCode$kotlin_stdlib", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
  public static final class Companion
  {
    public final boolean setEquals$kotlin_stdlib(Set<?> paramSet1, Set<?> paramSet2)
    {
      Intrinsics.checkNotNullParameter(paramSet1, "c");
      Intrinsics.checkNotNullParameter(paramSet2, "other");
      if (paramSet1.size() != paramSet2.size()) {
        return false;
      }
      return ((Collection)paramSet1).containsAll((Collection)paramSet2);
    }
    
    public final int unorderedHashCode$kotlin_stdlib(Collection<?> paramCollection)
    {
      Intrinsics.checkNotNullParameter(paramCollection, "c");
      int i = 0;
      paramCollection = paramCollection.iterator();
      while (paramCollection.hasNext())
      {
        Object localObject = paramCollection.next();
        int j;
        if (localObject != null) {
          j = localObject.hashCode();
        } else {
          j = 0;
        }
        i += j;
      }
      return i;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/collections/AbstractSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */