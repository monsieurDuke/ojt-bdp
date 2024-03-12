package kotlin.text;

import kotlin.Metadata;

@Metadata(d1={"\000\026\n\002\030\002\n\002\020\020\n\002\030\002\n\000\n\002\020\b\n\002\b\r\b\001\030\0002\b\022\004\022\0020\0000\0012\0020\002B\031\b\002\022\006\020\003\032\0020\004\022\b\b\002\020\005\032\0020\004¢\006\002\020\006R\024\020\005\032\0020\004X\004¢\006\b\n\000\032\004\b\007\020\bR\024\020\003\032\0020\004X\004¢\006\b\n\000\032\004\b\t\020\bj\002\b\nj\002\b\013j\002\b\fj\002\b\rj\002\b\016j\002\b\017j\002\b\020¨\006\021"}, d2={"Lkotlin/text/RegexOption;", "", "Lkotlin/text/FlagEnum;", "value", "", "mask", "(Ljava/lang/String;III)V", "getMask", "()I", "getValue", "IGNORE_CASE", "MULTILINE", "LITERAL", "UNIX_LINES", "COMMENTS", "DOT_MATCHES_ALL", "CANON_EQ", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public enum RegexOption
  implements FlagEnum
{
  private static final RegexOption[] $VALUES = $values();
  private final int mask;
  private final int value;
  
  static
  {
    LITERAL = new RegexOption("LITERAL", 2, 16, 0, 2, null);
    UNIX_LINES = new RegexOption("UNIX_LINES", 3, 1, 0, 2, null);
    COMMENTS = new RegexOption("COMMENTS", 4, 4, 0, 2, null);
    DOT_MATCHES_ALL = new RegexOption("DOT_MATCHES_ALL", 5, 32, 0, 2, null);
    CANON_EQ = new RegexOption("CANON_EQ", 6, 128, 0, 2, null);
  }
  
  private RegexOption(int paramInt1, int paramInt2)
  {
    this.value = paramInt1;
    this.mask = paramInt2;
  }
  
  public int getMask()
  {
    return this.mask;
  }
  
  public int getValue()
  {
    return this.value;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/text/RegexOption.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */