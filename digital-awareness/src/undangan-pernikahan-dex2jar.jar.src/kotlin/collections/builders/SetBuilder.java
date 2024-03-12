package kotlin.collections.builders;

import java.io.NotSerializableException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.AbstractMutableSet;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMutableSet;

@Metadata(d1={"\000T\n\002\030\002\n\000\n\002\020#\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\b\n\002\b\002\n\002\030\002\n\002\b\005\n\002\020\013\n\002\b\004\n\002\020\036\n\000\n\002\020\"\n\000\n\002\020\002\n\002\b\003\n\002\020)\n\002\b\004\n\002\020\000\n\000\b\000\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\0022\b\022\004\022\002H\0010\0032\0060\004j\002`\005B\007\b\026¢\006\002\020\006B\017\b\026\022\006\020\007\032\0020\b¢\006\002\020\tB\031\b\000\022\020\020\n\032\f\022\004\022\0028\000\022\002\b\0030\013¢\006\002\020\fJ\025\020\020\032\0020\0212\006\020\022\032\0028\000H\026¢\006\002\020\023J\026\020\024\032\0020\0212\f\020\025\032\b\022\004\022\0028\0000\026H\026J\f\020\027\032\b\022\004\022\0028\0000\030J\b\020\031\032\0020\032H\026J\026\020\033\032\0020\0212\006\020\022\032\0028\000H\002¢\006\002\020\023J\b\020\034\032\0020\021H\026J\017\020\035\032\b\022\004\022\0028\0000\036H\002J\025\020\037\032\0020\0212\006\020\022\032\0028\000H\026¢\006\002\020\023J\026\020 \032\0020\0212\f\020\025\032\b\022\004\022\0028\0000\026H\026J\026\020!\032\0020\0212\f\020\025\032\b\022\004\022\0028\0000\026H\026J\b\020\"\032\0020#H\002R\030\020\n\032\f\022\004\022\0028\000\022\002\b\0030\013X\004¢\006\002\n\000R\024\020\r\032\0020\b8VX\004¢\006\006\032\004\b\016\020\017¨\006$"}, d2={"Lkotlin/collections/builders/SetBuilder;", "E", "", "Lkotlin/collections/AbstractMutableSet;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "()V", "initialCapacity", "", "(I)V", "backing", "Lkotlin/collections/builders/MapBuilder;", "(Lkotlin/collections/builders/MapBuilder;)V", "size", "getSize", "()I", "add", "", "element", "(Ljava/lang/Object;)Z", "addAll", "elements", "", "build", "", "clear", "", "contains", "isEmpty", "iterator", "", "remove", "removeAll", "retainAll", "writeReplace", "", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public final class SetBuilder<E>
  extends AbstractMutableSet<E>
  implements Set<E>, Serializable, KMutableSet
{
  private final MapBuilder<E, ?> backing;
  
  public SetBuilder()
  {
    this(new MapBuilder());
  }
  
  public SetBuilder(int paramInt)
  {
    this(new MapBuilder(paramInt));
  }
  
  public SetBuilder(MapBuilder<E, ?> paramMapBuilder)
  {
    this.backing = paramMapBuilder;
  }
  
  private final Object writeReplace()
  {
    if (this.backing.isReadOnly$kotlin_stdlib()) {
      return new SerializedCollection((Collection)this, 1);
    }
    throw new NotSerializableException("The set cannot be serialized while it is being built.");
  }
  
  public boolean add(E paramE)
  {
    boolean bool;
    if (this.backing.addKey$kotlin_stdlib(paramE) >= 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean addAll(Collection<? extends E> paramCollection)
  {
    Intrinsics.checkNotNullParameter(paramCollection, "elements");
    this.backing.checkIsMutable$kotlin_stdlib();
    return super.addAll(paramCollection);
  }
  
  public final Set<E> build()
  {
    this.backing.build();
    return (Set)this;
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


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/collections/builders/SetBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */