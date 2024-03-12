package kotlin;

import java.io.Serializable;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000,\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\b\f\n\002\020\013\n\000\n\002\020\000\n\000\n\002\020\b\n\000\n\002\020\016\n\000\b\b\030\000*\006\b\000\020\001 \001*\006\b\001\020\002 \0012\0060\003j\002`\004B\025\022\006\020\005\032\0028\000\022\006\020\006\032\0028\001¢\006\002\020\007J\016\020\f\032\0028\000HÆ\003¢\006\002\020\tJ\016\020\r\032\0028\001HÆ\003¢\006\002\020\tJ.\020\016\032\016\022\004\022\0028\000\022\004\022\0028\0010\0002\b\b\002\020\005\032\0028\0002\b\b\002\020\006\032\0028\001HÆ\001¢\006\002\020\017J\023\020\020\032\0020\0212\b\020\022\032\004\030\0010\023HÖ\003J\t\020\024\032\0020\025HÖ\001J\b\020\026\032\0020\027H\026R\023\020\005\032\0028\000¢\006\n\n\002\020\n\032\004\b\b\020\tR\023\020\006\032\0028\001¢\006\n\n\002\020\n\032\004\b\013\020\t¨\006\030"}, d2={"Lkotlin/Pair;", "A", "B", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "first", "second", "(Ljava/lang/Object;Ljava/lang/Object;)V", "getFirst", "()Ljava/lang/Object;", "Ljava/lang/Object;", "getSecond", "component1", "component2", "copy", "(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;", "equals", "", "other", "", "hashCode", "", "toString", "", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public final class Pair<A, B>
  implements Serializable
{
  private final A first;
  private final B second;
  
  public Pair(A paramA, B paramB)
  {
    this.first = paramA;
    this.second = paramB;
  }
  
  public final A component1()
  {
    return (A)this.first;
  }
  
  public final B component2()
  {
    return (B)this.second;
  }
  
  public final Pair<A, B> copy(A paramA, B paramB)
  {
    return new Pair(paramA, paramB);
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof Pair)) {
      return false;
    }
    paramObject = (Pair)paramObject;
    if (!Intrinsics.areEqual(this.first, ((Pair)paramObject).first)) {
      return false;
    }
    return Intrinsics.areEqual(this.second, ((Pair)paramObject).second);
  }
  
  public final A getFirst()
  {
    return (A)this.first;
  }
  
  public final B getSecond()
  {
    return (B)this.second;
  }
  
  public int hashCode()
  {
    Object localObject = this.first;
    int j = 0;
    int i;
    if (localObject == null) {
      i = 0;
    } else {
      i = localObject.hashCode();
    }
    localObject = this.second;
    if (localObject != null) {
      j = localObject.hashCode();
    }
    return i * 31 + j;
  }
  
  public String toString()
  {
    return '(' + this.first + ", " + this.second + ')';
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/Pair.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */