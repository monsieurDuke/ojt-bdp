package kotlin.collections.builders;

import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.collections.AbstractMutableSet;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000\036\n\002\030\002\n\000\n\002\020&\n\002\b\002\n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\004\b \030\000*\024\b\000\020\001*\016\022\004\022\002H\003\022\004\022\002H\0040\002*\004\b\001\020\003*\004\b\002\020\0042\b\022\004\022\002H\0010\005B\005¢\006\002\020\006J\026\020\007\032\0020\b2\006\020\t\032\0028\000H\002¢\006\002\020\nJ\034\020\013\032\0020\b2\022\020\t\032\016\022\004\022\0028\001\022\004\022\0028\0020\002H&¨\006\f"}, d2={"Lkotlin/collections/builders/AbstractMapBuilderEntrySet;", "E", "", "K", "V", "Lkotlin/collections/AbstractMutableSet;", "()V", "contains", "", "element", "(Ljava/util/Map$Entry;)Z", "containsEntry", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public abstract class AbstractMapBuilderEntrySet<E extends Map.Entry<? extends K, ? extends V>, K, V>
  extends AbstractMutableSet<E>
{
  public final boolean contains(E paramE)
  {
    Intrinsics.checkNotNullParameter(paramE, "element");
    return containsEntry(paramE);
  }
  
  public abstract boolean containsEntry(Map.Entry<? extends K, ? extends V> paramEntry);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/collections/builders/AbstractMapBuilderEntrySet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */