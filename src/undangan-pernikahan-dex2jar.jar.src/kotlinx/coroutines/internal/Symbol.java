package kotlinx.coroutines.internal;

import kotlin.Metadata;

@Metadata(d1={"\000\022\n\002\030\002\n\002\020\000\n\000\n\002\020\016\n\002\b\007\b\000\030\0002\0020\001B\r\022\006\020\002\032\0020\003¢\006\002\020\004J\b\020\005\032\0020\003H\026J\036\020\006\032\002H\007\"\004\b\000\020\0072\b\020\b\032\004\030\0010\001H\b¢\006\002\020\tR\020\020\002\032\0020\0038\006X\004¢\006\002\n\000¨\006\n"}, d2={"Lkotlinx/coroutines/internal/Symbol;", "", "symbol", "", "(Ljava/lang/String;)V", "toString", "unbox", "T", "value", "(Ljava/lang/Object;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class Symbol
{
  public final String symbol;
  
  public Symbol(String paramString)
  {
    this.symbol = paramString;
  }
  
  public String toString()
  {
    return '<' + this.symbol + '>';
  }
  
  public final <T> T unbox(Object paramObject)
  {
    if (paramObject == this) {
      paramObject = null;
    }
    return (T)paramObject;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/internal/Symbol.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */