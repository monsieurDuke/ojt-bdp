package kotlin.collections.builders;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.AbstractMutableSet;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMutableSet;

@Metadata(d1={"\000>\n\002\030\002\n\000\n\002\020#\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\b\n\002\b\003\n\002\020\013\n\002\b\004\n\002\020\036\n\000\n\002\020\002\n\002\b\003\n\002\020)\n\002\b\004\b\000\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\0022\b\022\004\022\002H\0010\003B\031\b\000\022\020\020\004\032\f\022\004\022\0028\000\022\002\b\0030\005¢\006\002\020\006J\025\020\013\032\0020\f2\006\020\r\032\0028\000H\026¢\006\002\020\016J\026\020\017\032\0020\f2\f\020\020\032\b\022\004\022\0028\0000\021H\026J\b\020\022\032\0020\023H\026J\026\020\024\032\0020\f2\006\020\r\032\0028\000H\002¢\006\002\020\016J\b\020\025\032\0020\fH\026J\017\020\026\032\b\022\004\022\0028\0000\027H\002J\025\020\030\032\0020\f2\006\020\r\032\0028\000H\026¢\006\002\020\016J\026\020\031\032\0020\f2\f\020\020\032\b\022\004\022\0028\0000\021H\026J\026\020\032\032\0020\f2\f\020\020\032\b\022\004\022\0028\0000\021H\026R\030\020\004\032\f\022\004\022\0028\000\022\002\b\0030\005X\004¢\006\002\n\000R\024\020\007\032\0020\b8VX\004¢\006\006\032\004\b\t\020\n¨\006\033"}, d2={"Lkotlin/collections/builders/MapBuilderKeys;", "E", "", "Lkotlin/collections/AbstractMutableSet;", "backing", "Lkotlin/collections/builders/MapBuilder;", "(Lkotlin/collections/builders/MapBuilder;)V", "size", "", "getSize", "()I", "add", "", "element", "(Ljava/lang/Object;)Z", "addAll", "elements", "", "clear", "", "contains", "isEmpty", "iterator", "", "remove", "removeAll", "retainAll", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public final class MapBuilderKeys<E>
  extends AbstractMutableSet<E>
  implements Set<E>, KMutableSet
{
  private final MapBuilder<E, ?> backing;
  
  public MapBuilderKeys(MapBuilder<E, ?> paramMapBuilder)
  {
    this.backing = paramMapBuilder;
  }
  
  public boolean add(E paramE)
  {
    throw new UnsupportedOperationException();
  }
  
  public boolean addAll(Collection<? extends E> paramCollection)
  {
    Intrinsics.checkNotNullParameter(paramCollection, "elements");
    throw new UnsupportedOperationException();
  }
  
  public void clear()
  {
    this.backing.clear();
  }
  
  public boolean contains(Object paramObject)
  {
    return this.backing.containsKey(paramObject);
  }
  
  public int getSize()
  {
    return this.backing.size();
  }
  
  public boolean isEmpty()
  {
    return this.backing.isEmpty();
  }
  
  public Iterator<E> iterator()
  {
    return (Iterator)this.backing.keysIterator$kotlin_stdlib();
  }
  
  public boolean remove(Object paramObject)
  {
    boolean bool;
    if (this.backing.removeKey$kotlin_stdlib(paramObject) >= 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean removeAll(Collection<? extends Object> paramCollection)
  {
    Intrinsics.checkNotNullParameter(paramCollection, "elements");
    this.backing.checkIsMutable$kotlin_stdlib();
    return super.removeAll(paramCollection);
  }
  
  public boolean retainAll(Collection<? extends Object> paramCollection)
  {
    Intrinsics.checkNotNullParameter(paramCollection, "elements");
    this.backing.checkIsMutable$kotlin_stdlib();
    return super.retainAll(paramCollection);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/collections/builders/MapBuilderKeys.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */