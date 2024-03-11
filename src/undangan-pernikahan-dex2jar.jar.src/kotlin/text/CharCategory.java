package kotlin.text;

import kotlin.Metadata;
import kotlin.ranges.IntRange;

@Metadata(d1={"\000&\n\002\030\002\n\002\020\020\n\000\n\002\020\b\n\000\n\002\020\016\n\002\b\006\n\002\020\013\n\000\n\002\020\f\n\002\b \b\001\030\000 -2\b\022\004\022\0020\0000\001:\001-B\027\b\002\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005¢\006\002\020\006J\021\020\013\032\0020\f2\006\020\r\032\0020\016H\002R\021\020\004\032\0020\005¢\006\b\n\000\032\004\b\007\020\bR\021\020\002\032\0020\003¢\006\b\n\000\032\004\b\t\020\nj\002\b\017j\002\b\020j\002\b\021j\002\b\022j\002\b\023j\002\b\024j\002\b\025j\002\b\026j\002\b\027j\002\b\030j\002\b\031j\002\b\032j\002\b\033j\002\b\034j\002\b\035j\002\b\036j\002\b\037j\002\b j\002\b!j\002\b\"j\002\b#j\002\b$j\002\b%j\002\b&j\002\b'j\002\b(j\002\b)j\002\b*j\002\b+j\002\b,¨\006."}, d2={"Lkotlin/text/CharCategory;", "", "value", "", "code", "", "(Ljava/lang/String;IILjava/lang/String;)V", "getCode", "()Ljava/lang/String;", "getValue", "()I", "contains", "", "char", "", "UNASSIGNED", "UPPERCASE_LETTER", "LOWERCASE_LETTER", "TITLECASE_LETTER", "MODIFIER_LETTER", "OTHER_LETTER", "NON_SPACING_MARK", "ENCLOSING_MARK", "COMBINING_SPACING_MARK", "DECIMAL_DIGIT_NUMBER", "LETTER_NUMBER", "OTHER_NUMBER", "SPACE_SEPARATOR", "LINE_SEPARATOR", "PARAGRAPH_SEPARATOR", "CONTROL", "FORMAT", "PRIVATE_USE", "SURROGATE", "DASH_PUNCTUATION", "START_PUNCTUATION", "END_PUNCTUATION", "CONNECTOR_PUNCTUATION", "OTHER_PUNCTUATION", "MATH_SYMBOL", "CURRENCY_SYMBOL", "MODIFIER_SYMBOL", "OTHER_SYMBOL", "INITIAL_QUOTE_PUNCTUATION", "FINAL_QUOTE_PUNCTUATION", "Companion", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public enum CharCategory
{
  private static final CharCategory[] $VALUES = $values();
  public static final Companion Companion = new Companion(null);
  private final String code;
  private final int value;
  
  static
  {
    LOWERCASE_LETTER = new CharCategory("LOWERCASE_LETTER", 2, 2, "Ll");
    TITLECASE_LETTER = new CharCategory("TITLECASE_LETTER", 3, 3, "Lt");
    MODIFIER_LETTER = new CharCategory("MODIFIER_LETTER", 4, 4, "Lm");
    OTHER_LETTER = new CharCategory("OTHER_LETTER", 5, 5, "Lo");
    NON_SPACING_MARK = new CharCategory("NON_SPACING_MARK", 6, 6, "Mn");
    ENCLOSING_MARK = new CharCategory("ENCLOSING_MARK", 7, 7, "Me");
    COMBINING_SPACING_MARK = new CharCategory("COMBINING_SPACING_MARK", 8, 8, "Mc");
    DECIMAL_DIGIT_NUMBER = new CharCategory("DECIMAL_DIGIT_NUMBER", 9, 9, "Nd");
    LETTER_NUMBER = new CharCategory("LETTER_NUMBER", 10, 10, "Nl");
    OTHER_NUMBER = new CharCategory("OTHER_NUMBER", 11, 11, "No");
    SPACE_SEPARATOR = new CharCategory("SPACE_SEPARATOR", 12, 12, "Zs");
    LINE_SEPARATOR = new CharCategory("LINE_SEPARATOR", 13, 13, "Zl");
    PARAGRAPH_SEPARATOR = new CharCategory("PARAGRAPH_SEPARATOR", 14, 14, "Zp");
    CONTROL = new CharCategory("CONTROL", 15, 15, "Cc");
    FORMAT = new CharCategory("FORMAT", 16, 16, "Cf");
    PRIVATE_USE = new CharCategory("PRIVATE_USE", 17, 18, "Co");
    SURROGATE = new CharCategory("SURROGATE", 18, 19, "Cs");
    DASH_PUNCTUATION = new CharCategory("DASH_PUNCTUATION", 19, 20, "Pd");
    START_PUNCTUATION = new CharCategory("START_PUNCTUATION", 20, 21, "Ps");
    END_PUNCTUATION = new CharCategory("END_PUNCTUATION", 21, 22, "Pe");
    CONNECTOR_PUNCTUATION = new CharCategory("CONNECTOR_PUNCTUATION", 22, 23, "Pc");
    OTHER_PUNCTUATION = new CharCategory("OTHER_PUNCTUATION", 23, 24, "Po");
    MATH_SYMBOL = new CharCategory("MATH_SYMBOL", 24, 25, "Sm");
    CURRENCY_SYMBOL = new CharCategory("CURRENCY_SYMBOL", 25, 26, "Sc");
    MODIFIER_SYMBOL = new CharCategory("MODIFIER_SYMBOL", 26, 27, "Sk");
    OTHER_SYMBOL = new CharCategory("OTHER_SYMBOL", 27, 28, "So");
    INITIAL_QUOTE_PUNCTUATION = new CharCategory("INITIAL_QUOTE_PUNCTUATION", 28, 29, "Pi");
    FINAL_QUOTE_PUNCTUATION = new CharCategory("FINAL_QUOTE_PUNCTUATION", 29, 30, "Pf");
  }
  
  private CharCategory(int paramInt, String paramString)
  {
    this.value = paramInt;
    this.code = paramString;
  }
  
  public final boolean contains(char paramChar)
  {
    boolean bool;
    if (Character.getType(paramChar) == this.value) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final String getCode()
  {
    return this.code;
  }
  
  public final int getValue()
  {
    return this.value;
  }
  
  @Metadata(d1={"\000\030\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\n\002\020\b\n\000\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J\016\020\003\032\0020\0042\006\020\005\032\0020\006¨\006\007"}, d2={"Lkotlin/text/CharCategory$Companion;", "", "()V", "valueOf", "Lkotlin/text/CharCategory;", "category", "", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
  public static final class Companion
  {
    public final CharCategory valueOf(int paramInt)
    {
      CharCategory localCharCategory;
      if (new IntRange(0, 16).contains(paramInt))
      {
        localCharCategory = CharCategory.values()[paramInt];
      }
      else
      {
        if (!new IntRange(18, 30).contains(paramInt)) {
          break label54;
        }
        localCharCategory = CharCategory.values()[(paramInt - 1)];
      }
      return localCharCategory;
      label54:
      throw new IllegalArgumentException("Category #" + paramInt + " is not defined.");
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/text/CharCategory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */