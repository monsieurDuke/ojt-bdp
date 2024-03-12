package kotlin.collections.builders;

import java.io.Externalizable;
import java.io.InvalidObjectException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\0006\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\036\n\000\n\002\020\b\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\000\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\002\b\000\030\000 \0212\0020\001:\001\021B\007\b\026¢\006\002\020\002B\031\022\n\020\003\032\006\022\002\b\0030\004\022\006\020\005\032\0020\006¢\006\002\020\007J\020\020\b\032\0020\t2\006\020\n\032\0020\013H\026J\b\020\f\032\0020\rH\002J\020\020\016\032\0020\t2\006\020\017\032\0020\020H\026R\022\020\003\032\006\022\002\b\0030\004X\016¢\006\002\n\000R\016\020\005\032\0020\006X\004¢\006\002\n\000¨\006\022"}, d2={"Lkotlin/collections/builders/SerializedCollection;", "Ljava/io/Externalizable;", "()V", "collection", "", "tag", "", "(Ljava/util/Collection;I)V", "readExternal", "", "input", "Ljava/io/ObjectInput;", "readResolve", "", "writeExternal", "output", "Ljava/io/ObjectOutput;", "Companion", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public final class SerializedCollection
  implements Externalizable
{
  public static final Companion Companion = new Companion(null);
  private static final long serialVersionUID = 0L;
  public static final int tagList = 0;
  public static final int tagSet = 1;
  private Collection<?> collection;
  private final int tag;
  
  public SerializedCollection()
  {
    this((Collection)CollectionsKt.emptyList(), 0);
  }
  
  public SerializedCollection(Collection<?> paramCollection, int paramInt)
  {
    this.collection = paramCollection;
    this.tag = paramInt;
  }
  
  private final Object readResolve()
  {
    return this.collection;
  }
  
  public void readExternal(ObjectInput paramObjectInput)
  {
    Intrinsics.checkNotNullParameter(paramObjectInput, "input");
    int i = paramObjectInput.readByte();
    int m = i & 0x1;
    if ((i & 0xFFFFFFFE) == 0)
    {
      int k = paramObjectInput.readInt();
      if (k >= 0)
      {
        i = 0;
        int j = 0;
        Object localObject;
        switch (m)
        {
        default: 
          throw new InvalidObjectException("Unsupported collection type tag: " + m + '.');
        case 1: 
          localObject = SetsKt.createSetBuilder(k);
          for (i = j; i < k; i++) {
            ((Set)localObject).add(paramObjectInput.readObject());
          }
          paramObjectInput = (Collection)SetsKt.build((Set)localObject);
          break;
        case 0: 
          localObject = CollectionsKt.createListBuilder(k);
          while (i < k)
          {
            ((List)localObject).add(paramObjectInput.readObject());
            i++;
          }
          paramObjectInput = (Collection)CollectionsKt.build((List)localObject);
        }
        this.collection = paramObjectInput;
        return;
      }
      throw new InvalidObjectException("Illegal size value: " + k + '.');
    }
    throw new InvalidObjectException("Unsupported flags value: " + i + '.');
  }
  
  public void writeExternal(ObjectOutput paramObjectOutput)
  {
    Intrinsics.checkNotNullParameter(paramObjectOutput, "output");
    paramObjectOutput.writeByte(this.tag);
    paramObjectOutput.writeInt(this.collection.size());
    Iterator localIterator = this.collection.iterator();
    while (localIterator.hasNext()) {
      paramObjectOutput.writeObject(localIterator.next());
    }
  }
  
  @Metadata(d1={"\000\032\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\t\n\000\n\002\020\b\n\002\b\002\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002R\016\020\003\032\0020\004XT¢\006\002\n\000R\016\020\005\032\0020\006XT¢\006\002\n\000R\016\020\007\032\0020\006XT¢\006\002\n\000¨\006\b"}, d2={"Lkotlin/collections/builders/SerializedCollection$Companion;", "", "()V", "serialVersionUID", "", "tagList", "", "tagSet", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
  public static final class Companion {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/collections/builders/SerializedCollection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */