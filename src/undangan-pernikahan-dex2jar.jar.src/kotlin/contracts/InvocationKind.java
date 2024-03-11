package kotlin.contracts;

import kotlin.Metadata;

@Metadata(d1={"\000\f\n\002\030\002\n\002\020\020\n\002\b\006\b\001\030\0002\b\022\004\022\0020\0000\001B\007\b\002¢\006\002\020\002j\002\b\003j\002\b\004j\002\b\005j\002\b\006¨\006\007"}, d2={"Lkotlin/contracts/InvocationKind;", "", "(Ljava/lang/String;I)V", "AT_MOST_ONCE", "AT_LEAST_ONCE", "EXACTLY_ONCE", "UNKNOWN", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public enum InvocationKind
{
  private static final InvocationKind[] $VALUES = $values();
  
  static
  {
    AT_LEAST_ONCE = new InvocationKind("AT_LEAST_ONCE", 1);
    EXACTLY_ONCE = new InvocationKind("EXACTLY_ONCE", 2);
    UNKNOWN = new InvocationKind("UNKNOWN", 3);
  }
  
  private InvocationKind() {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/contracts/InvocationKind.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */