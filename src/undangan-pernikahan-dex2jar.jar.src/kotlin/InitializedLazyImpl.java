package kotlin;

import java.io.Serializable;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000\"\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\006\n\002\020\013\n\000\n\002\020\016\n\000\b\000\030\000*\006\b\000\020\001 \0012\b\022\004\022\002H\0010\0022\0060\003j\002`\004B\r\022\006\020\005\032\0028\000¢\006\002\020\006J\b\020\n\032\0020\013H\026J\b\020\f\032\0020\rH\026R\026\020\005\032\0028\000X\004¢\006\n\n\002\020\t\032\004\b\007\020\b¨\006\016"}, d2={"Lkotlin/InitializedLazyImpl;", "T", "Lkotlin/Lazy;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "value", "(Ljava/lang/Object;)V", "getValue", "()Ljava/lang/Object;", "Ljava/lang/Object;", "isInitialized", "", "toString", "", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public final class InitializedLazyImpl<T>
  implements Lazy<T>, Serializable
{
  private final T value;
  
  public InitializedLazyImpl(T paramT)
  {
    this.value = paramT;
  }
  
  public T getValue()
  {
    return (T)this.value;
  }
  
  public boolean isInitialized()
  {
    return true;
  }
  
  public String toString()
  {
    String str = String.valueOf(getValue());
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/InitializedLazyImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */