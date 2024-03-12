package kotlin.collections.builders;

import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.AbstractMutableCollection;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMutableCollection;

@Metadata(d1={"\000>\n\002\030\002\n\000\n\002\020\037\n\002\030\002\n\000\n\002\030\002\n\002\b\004\n\002\020\b\n\002\b\003\n\002\020\013\n\002\b\004\n\002\020\036\n\000\n\002\020\002\n\002\b\003\n\002\020)\n\002\b\004\b\000\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\0022\b\022\004\022\002H\0010\003B\031\b\000\022\020\020\004\032\f\022\002\b\003\022\004\022\0028\0000\005¢\006\002\020\006J\025\020\r\032\0020\0162\006\020\017\032\0028\000H\026¢\006\002\020\020J\026\020\021\032\0020\0162\f\020\022\032\b\022\004\022\0028\0000\023H\026J\b\020\024\032\0020\025H\026J\026\020\026\032\0020\0162\006\020\017\032\0028\000H\002¢\006\002\020\020J\b\020\027\032\0020\016H\026J\017\020\030\032\b\022\004\022\0028\0000\031H\002J\025\020\032\032\0020\0162\006\020\017\032\0028\000H\026¢\006\002\020\020J\026\020\033\032\0020\0162\f\020\022\032\b\022\004\022\0028\0000\023H\026J\026\020\034\032\0020\0162\f\020\022\032\b\022\004\022\0028\0000\023H\026R\033\020\004\032\f\022\002\b\003\022\004\022\0028\0000\005¢\006\b\n\000\032\004\b\007\020\bR\024\020\t\032\0020\n8VX\004¢\006\006\032\004\b\013\020\f¨\006\035"}, d2={"Lkotlin/collections/builders/MapBuilderValues;", "V", "", "Lkotlin/collections/AbstractMutableCollection;", "backing", "Lkotlin/collections/builders/MapBuilder;", "(Lkotlin/collections/builders/MapBuilder;)V", "getBacking", "()Lkotlin/collections/builders/MapBuilder;", "size", "", "getSize", "()I", "add", "", "element", "(Ljava/lang/Object;)Z", "addAll", "elements", "", "clear", "", "contains", "isEmpty", "iterator", "", "remove", "removeAll", "retainAll", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public final class MapBuilderValues<V>
  extends AbstractMutableCollection<V>
  implements Collection<V>, KMutableCollection
{
  private final MapBuilder<?, V> backing;
  
  public MapBuilderValues(MapBuilder<?, V> paramMapBuilder)
  {
    this.backing = paramMapBuilder;
  }
  
  public boolean add(V paramV)
  {
    throw new UnsupportedOperationException();
  }
  
  public boolean addAll(Collection<? extends V> paramCollection)
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
    return this.backing.containsValue(paramObject);
  }
  
  public final MapBuilder<?, V> getBacking()
  {
    return this.backing;
  }
  
  public int getSize()
  {
    return this.backing.size();
  }
  
  public boolean isEmpty()
  {
    return this.backing.isEmpty();
  }
  
  public Iterator<V> iterator()
  {
    return (Iterator)this.backing.valuesIterator$kotlin_stdlib();
  }
  
  public boolean remove(Object paramObject)
  {
    return this.backing.removeValue$kotlin_stdlib(paramObject);
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


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/collections/builders/MapBuilderValues.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */