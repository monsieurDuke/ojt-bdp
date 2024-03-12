package kotlin.internal;

import kotlin.Metadata;

@Metadata(d1={"\000\f\n\002\030\002\n\002\020\020\n\002\b\005\b\001\030\0002\b\022\004\022\0020\0000\001B\007\b\002¢\006\002\020\002j\002\b\003j\002\b\004j\002\b\005¨\006\006"}, d2={"Lkotlin/internal/RequireKotlinVersionKind;", "", "(Ljava/lang/String;I)V", "LANGUAGE_VERSION", "COMPILER_VERSION", "API_VERSION", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public enum RequireKotlinVersionKind
{
  private static final RequireKotlinVersionKind[] $VALUES = $values();
  
  static
  {
    COMPILER_VERSION = new RequireKotlinVersionKind("COMPILER_VERSION", 1);
    API_VERSION = new RequireKotlinVersionKind("API_VERSION", 2);
  }
  
  private RequireKotlinVersionKind() {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/internal/RequireKotlinVersionKind.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */