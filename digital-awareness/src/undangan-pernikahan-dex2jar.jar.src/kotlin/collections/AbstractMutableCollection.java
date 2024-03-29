package kotlin.collections;

import java.util.AbstractCollection;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.markers.KMutableCollection;

@Metadata(d1={"\000\032\n\002\030\002\n\000\n\002\020\037\n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\003\b'\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\0022\b\022\004\022\002H\0010\003B\007\b\004¢\006\002\020\004J\025\020\005\032\0020\0062\006\020\007\032\0028\000H&¢\006\002\020\b¨\006\t"}, d2={"Lkotlin/collections/AbstractMutableCollection;", "E", "", "Ljava/util/AbstractCollection;", "()V", "add", "", "element", "(Ljava/lang/Object;)Z", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public abstract class AbstractMutableCollection<E>
  extends AbstractCollection<E>
  implements Collection<E>, KMutableCollection
{
  public abstract boolean add(E paramE);
  
  public abstract int getSize();
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/collections/AbstractMutableCollection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */