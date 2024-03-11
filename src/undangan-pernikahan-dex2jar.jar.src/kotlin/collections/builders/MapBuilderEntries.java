package kotlin.collections.builders;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000H\n\002\030\002\n\002\b\002\n\002\030\002\n\002\020'\n\000\n\002\030\002\n\002\b\004\n\002\020\b\n\002\b\003\n\002\020\013\n\002\b\003\n\002\020\036\n\000\n\002\020\002\n\002\b\002\n\002\020&\n\002\b\002\n\002\020)\n\002\b\004\b\000\030\000*\004\b\000\020\001*\004\b\001\020\0022 \022\020\022\016\022\004\022\002H\001\022\004\022\002H\0020\004\022\004\022\002H\001\022\004\022\002H\0020\003B\033\b\000\022\022\020\005\032\016\022\004\022\0028\000\022\004\022\0028\0010\006¢\006\002\020\007J\034\020\016\032\0020\0172\022\020\020\032\016\022\004\022\0028\000\022\004\022\0028\0010\004H\026J\"\020\021\032\0020\0172\030\020\022\032\024\022\020\022\016\022\004\022\0028\000\022\004\022\0028\0010\0040\023H\026J\b\020\024\032\0020\025H\026J\"\020\026\032\0020\0172\030\020\022\032\024\022\020\022\016\022\004\022\0028\000\022\004\022\0028\0010\0040\023H\026J\034\020\027\032\0020\0172\022\020\020\032\016\022\004\022\0028\000\022\004\022\0028\0010\030H\026J\b\020\031\032\0020\017H\026J\033\020\032\032\024\022\020\022\016\022\004\022\0028\000\022\004\022\0028\0010\0040\033H\002J\034\020\034\032\0020\0172\022\020\020\032\016\022\004\022\0028\000\022\004\022\0028\0010\004H\026J\"\020\035\032\0020\0172\030\020\022\032\024\022\020\022\016\022\004\022\0028\000\022\004\022\0028\0010\0040\023H\026J\"\020\036\032\0020\0172\030\020\022\032\024\022\020\022\016\022\004\022\0028\000\022\004\022\0028\0010\0040\023H\026R\035\020\005\032\016\022\004\022\0028\000\022\004\022\0028\0010\006¢\006\b\n\000\032\004\b\b\020\tR\024\020\n\032\0020\0138VX\004¢\006\006\032\004\b\f\020\r¨\006\037"}, d2={"Lkotlin/collections/builders/MapBuilderEntries;", "K", "V", "Lkotlin/collections/builders/AbstractMapBuilderEntrySet;", "", "backing", "Lkotlin/collections/builders/MapBuilder;", "(Lkotlin/collections/builders/MapBuilder;)V", "getBacking", "()Lkotlin/collections/builders/MapBuilder;", "size", "", "getSize", "()I", "add", "", "element", "addAll", "elements", "", "clear", "", "containsAll", "containsEntry", "", "isEmpty", "iterator", "", "remove", "removeAll", "retainAll", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public final class MapBuilderEntries<K, V>
  extends AbstractMapBuilderEntrySet<Map.Entry<K, V>, K, V>
{
  private final MapBuilder<K, V> backing;
  
  public MapBuilderEntries(MapBuilder<K, V> paramMapBuilder)
  {
    this.backing = paramMapBuilder;
  }
  
  public boolean add(Map.Entry<K, V> paramEntry)
  {
    Intrinsics.checkNotNullParameter(paramEntry, "element");
    throw new UnsupportedOperationException();
  }
  
  public boolean addAll(Collection<? extends Map.Entry<K, V>> paramCollection)
  {
    Intrinsics.checkNotNullParameter(paramCollection, "elements");
    throw new UnsupportedOperationException();
  }
  
  public void clear()
  {
    this.backing.clear();
  }
  
  public boolean containsAll(Collection<? extends Object> paramCollection)
  {
    Intrinsics.checkNotNullParameter(paramCollection, "elements");
    return this.backing.containsAllEntries$kotlin_stdlib(paramCollection);
  }
  
  public boolean containsEntry(Map.Entry<? extends K, ? extends V> paramEntry)
  {
    Intrinsics.checkNotNullParameter(paramEntry, "element");
    return this.backing.containsEntry$kotlin_stdlib(paramEntry);
  }
  
  public final MapBuilder<K, V> getBacking()
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
  
  public Iterator<Map.Entry<K, V>> iterator()
  {
    return (Iterator)this.backing.entriesIterator$kotlin_stdlib();
  }
  
  public boolean remove(Map.Entry paramEntry)
  {
    Intrinsics.checkNotNullParameter(paramEntry, "element");
    return this.backing.removeEntry$kotlin_stdlib(paramEntry);
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


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/collections/builders/MapBuilderEntries.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */