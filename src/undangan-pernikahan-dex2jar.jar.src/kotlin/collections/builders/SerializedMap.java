package kotlin.collections.builders;

import java.io.Externalizable;
import java.io.InvalidObjectException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\0000\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020$\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\000\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\002\b\002\030\000 \0172\0020\001:\001\017B\007\b\026¢\006\002\020\002B\025\022\016\020\003\032\n\022\002\b\003\022\002\b\0030\004¢\006\002\020\005J\020\020\006\032\0020\0072\006\020\b\032\0020\tH\026J\b\020\n\032\0020\013H\002J\020\020\f\032\0020\0072\006\020\r\032\0020\016H\026R\026\020\003\032\n\022\002\b\003\022\002\b\0030\004X\016¢\006\002\n\000¨\006\020"}, d2={"Lkotlin/collections/builders/SerializedMap;", "Ljava/io/Externalizable;", "()V", "map", "", "(Ljava/util/Map;)V", "readExternal", "", "input", "Ljava/io/ObjectInput;", "readResolve", "", "writeExternal", "output", "Ljava/io/ObjectOutput;", "Companion", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
final class SerializedMap
  implements Externalizable
{
  public static final Companion Companion = new Companion(null);
  private static final long serialVersionUID = 0L;
  private Map<?, ?> map;
  
  public SerializedMap()
  {
    this(MapsKt.emptyMap());
  }
  
  public SerializedMap(Map<?, ?> paramMap)
  {
    this.map = paramMap;
  }
  
  private final Object readResolve()
  {
    return this.map;
  }
  
  public void readExternal(ObjectInput paramObjectInput)
  {
    Intrinsics.checkNotNullParameter(paramObjectInput, "input");
    int i = paramObjectInput.readByte();
    if (i == 0)
    {
      int j = paramObjectInput.readInt();
      if (j >= 0)
      {
        Map localMap = MapsKt.createMapBuilder(j);
        for (i = 0; i < j; i++) {
          localMap.put(paramObjectInput.readObject(), paramObjectInput.readObject());
        }
        this.map = MapsKt.build(localMap);
        return;
      }
      throw new InvalidObjectException("Illegal size value: " + j + '.');
    }
    throw new InvalidObjectException("Unsupported flags value: " + i);
  }
  
  public void writeExternal(ObjectOutput paramObjectOutput)
  {
    Intrinsics.checkNotNullParameter(paramObjectOutput, "output");
    paramObjectOutput.writeByte(0);
    paramObjectOutput.writeInt(this.map.size());
    Iterator localIterator = this.map.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      paramObjectOutput.writeObject(localEntry.getKey());
      paramObjectOutput.writeObject(localEntry.getValue());
    }
  }
  
  @Metadata(d1={"\000\022\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\t\n\000\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002R\016\020\003\032\0020\004XT¢\006\002\n\000¨\006\005"}, d2={"Lkotlin/collections/builders/SerializedMap$Companion;", "", "()V", "serialVersionUID", "", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
  public static final class Companion {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/collections/builders/SerializedMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */