package kotlin.text;

import java.util.Locale;
import kotlin.Deprecated;
import kotlin.DeprecatedSinceKotlin;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\0004\n\000\n\002\030\002\n\002\020\f\n\002\b\003\n\002\030\002\n\002\b\003\n\002\020\b\n\002\b\004\n\002\020\013\n\002\b\016\n\002\020\016\n\000\n\002\030\002\n\002\b\t\032\020\020\t\032\0020\n2\006\020\013\032\0020\nH\001\032\030\020\f\032\0020\n2\006\020\r\032\0020\0022\006\020\013\032\0020\nH\000\032\r\020\016\032\0020\017*\0020\002H\b\032\r\020\020\032\0020\017*\0020\002H\b\032\r\020\021\032\0020\017*\0020\002H\b\032\r\020\022\032\0020\017*\0020\002H\b\032\r\020\023\032\0020\017*\0020\002H\b\032\r\020\024\032\0020\017*\0020\002H\b\032\r\020\025\032\0020\017*\0020\002H\b\032\r\020\026\032\0020\017*\0020\002H\b\032\r\020\027\032\0020\017*\0020\002H\b\032\r\020\030\032\0020\017*\0020\002H\b\032\r\020\031\032\0020\017*\0020\002H\b\032\r\020\032\032\0020\017*\0020\002H\b\032\r\020\033\032\0020\017*\0020\002H\b\032\n\020\034\032\0020\017*\0020\002\032\r\020\035\032\0020\036*\0020\002H\b\032\024\020\035\032\0020\036*\0020\0022\006\020\037\032\0020 H\007\032\r\020!\032\0020\002*\0020\002H\b\032\024\020\"\032\0020\036*\0020\0022\006\020\037\032\0020 H\007\032\r\020#\032\0020\002*\0020\002H\b\032\r\020$\032\0020\002*\0020\002H\b\032\r\020%\032\0020\002*\0020\002H\b\032\r\020&\032\0020\002*\0020\002H\b\032\r\020'\032\0020\036*\0020\002H\b\032\024\020'\032\0020\036*\0020\0022\006\020\037\032\0020 H\007\032\r\020(\032\0020\002*\0020\002H\b\"\025\020\000\032\0020\001*\0020\0028F¢\006\006\032\004\b\003\020\004\"\025\020\005\032\0020\006*\0020\0028F¢\006\006\032\004\b\007\020\b¨\006)"}, d2={"category", "Lkotlin/text/CharCategory;", "", "getCategory", "(C)Lkotlin/text/CharCategory;", "directionality", "Lkotlin/text/CharDirectionality;", "getDirectionality", "(C)Lkotlin/text/CharDirectionality;", "checkRadix", "", "radix", "digitOf", "char", "isDefined", "", "isDigit", "isHighSurrogate", "isISOControl", "isIdentifierIgnorable", "isJavaIdentifierPart", "isJavaIdentifierStart", "isLetter", "isLetterOrDigit", "isLowSurrogate", "isLowerCase", "isTitleCase", "isUpperCase", "isWhitespace", "lowercase", "", "locale", "Ljava/util/Locale;", "lowercaseChar", "titlecase", "titlecaseChar", "toLowerCase", "toTitleCase", "toUpperCase", "uppercase", "uppercaseChar", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/text/CharsKt")
class CharsKt__CharJVMKt
{
  public static final int checkRadix(int paramInt)
  {
    if (new IntRange(2, 36).contains(paramInt)) {
      return paramInt;
    }
    throw new IllegalArgumentException("radix " + paramInt + " was not in valid range " + new IntRange(2, 36));
  }
  
  public static final int digitOf(char paramChar, int paramInt)
  {
    return Character.digit(paramChar, paramInt);
  }
  
  public static final CharCategory getCategory(char paramChar)
  {
    return CharCategory.Companion.valueOf(Character.getType(paramChar));
  }
  
  public static final CharDirectionality getDirectionality(char paramChar)
  {
    return CharDirectionality.Companion.valueOf(Character.getDirectionality(paramChar));
  }
  
  private static final boolean isDefined(char paramChar)
  {
    return Character.isDefined(paramChar);
  }
  
  private static final boolean isDigit(char paramChar)
  {
    return Character.isDigit(paramChar);
  }
  
  private static final boolean isHighSurrogate(char paramChar)
  {
    return Character.isHighSurrogate(paramChar);
  }
  
  private static final boolean isISOControl(char paramChar)
  {
    return Character.isISOControl(paramChar);
  }
  
  private static final boolean isIdentifierIgnorable(char paramChar)
  {
    return Character.isIdentifierIgnorable(paramChar);
  }
  
  private static final boolean isJavaIdentifierPart(char paramChar)
  {
    return Character.isJavaIdentifierPart(paramChar);
  }
  
  private static final boolean isJavaIdentifierStart(char paramChar)
  {
    return Character.isJavaIdentifierStart(paramChar);
  }
  
  private static final boolean isLetter(char paramChar)
  {
    return Character.isLetter(paramChar);
  }
  
  private static final boolean isLetterOrDigit(char paramChar)
  {
    return Character.isLetterOrDigit(paramChar);
  }
  
  private static final boolean isLowSurrogate(char paramChar)
  {
    return Character.isLowSurrogate(paramChar);
  }
  
  private static final boolean isLowerCase(char paramChar)
  {
    return Character.isLowerCase(paramChar);
  }
  
  private static final boolean isTitleCase(char paramChar)
  {
    return Character.isTitleCase(paramChar);
  }
  
  private static final boolean isUpperCase(char paramChar)
  {
    return Character.isUpperCase(paramChar);
  }
  
  public static final boolean isWhitespace(char paramChar)
  {
    boolean bool;
    if ((!Character.isWhitespace(paramChar)) && (!Character.isSpaceChar(paramChar))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  private static final String lowercase(char paramChar)
  {
    String str = String.valueOf(paramChar);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    Intrinsics.checkNotNull(str, "null cannot be cast to non-null type java.lang.String");
    str = str.toLowerCase(Locale.ROOT);
    Intrinsics.checkNotNullExpressionValue(str, "this as java.lang.String).toLowerCase(Locale.ROOT)");
    return str;
  }
  
  public static final String lowercase(char paramChar, Locale paramLocale)
  {
    Intrinsics.checkNotNullParameter(paramLocale, "locale");
    String str = String.valueOf(paramChar);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    Intrinsics.checkNotNull(str, "null cannot be cast to non-null type java.lang.String");
    paramLocale = str.toLowerCase(paramLocale);
    Intrinsics.checkNotNullExpressionValue(paramLocale, "this as java.lang.String).toLowerCase(locale)");
    return paramLocale;
  }
  
  private static final char lowercaseChar(char paramChar)
  {
    return Character.toLowerCase(paramChar);
  }
  
  public static final String titlecase(char paramChar, Locale paramLocale)
  {
    Intrinsics.checkNotNullParameter(paramLocale, "locale");
    paramLocale = CharsKt.uppercase(paramChar, paramLocale);
    Log5ECF72.a(paramLocale);
    LogE84000.a(paramLocale);
    Log229316.a(paramLocale);
    if (paramLocale.length() > 1)
    {
      if (paramChar != 'ŉ')
      {
        paramChar = paramLocale.charAt(0);
        Intrinsics.checkNotNull(paramLocale, "null cannot be cast to non-null type java.lang.String");
        paramLocale = paramLocale.substring(1);
        Intrinsics.checkNotNullExpressionValue(paramLocale, "this as java.lang.String).substring(startIndex)");
        Intrinsics.checkNotNull(paramLocale, "null cannot be cast to non-null type java.lang.String");
        paramLocale = paramLocale.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(paramLocale, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        paramLocale = paramChar + paramLocale;
      }
      return paramLocale;
    }
    String str = String.valueOf(paramChar);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    Intrinsics.checkNotNull(str, "null cannot be cast to non-null type java.lang.String");
    str = str.toUpperCase(Locale.ROOT);
    Intrinsics.checkNotNullExpressionValue(str, "this as java.lang.String).toUpperCase(Locale.ROOT)");
    if (!Intrinsics.areEqual(paramLocale, str)) {
      return paramLocale;
    }
    paramLocale = String.valueOf(Character.toTitleCase(paramChar));
    Log5ECF72.a(paramLocale);
    LogE84000.a(paramLocale);
    Log229316.a(paramLocale);
    return paramLocale;
  }
  
  private static final char titlecaseChar(char paramChar)
  {
    return Character.toTitleCase(paramChar);
  }
  
  @Deprecated(message="Use lowercaseChar() instead.", replaceWith=@ReplaceWith(expression="lowercaseChar()", imports={}))
  @DeprecatedSinceKotlin(warningSince="1.5")
  private static final char toLowerCase(char paramChar)
  {
    return Character.toLowerCase(paramChar);
  }
  
  @Deprecated(message="Use titlecaseChar() instead.", replaceWith=@ReplaceWith(expression="titlecaseChar()", imports={}))
  @DeprecatedSinceKotlin(warningSince="1.5")
  private static final char toTitleCase(char paramChar)
  {
    return Character.toTitleCase(paramChar);
  }
  
  @Deprecated(message="Use uppercaseChar() instead.", replaceWith=@ReplaceWith(expression="uppercaseChar()", imports={}))
  @DeprecatedSinceKotlin(warningSince="1.5")
  private static final char toUpperCase(char paramChar)
  {
    return Character.toUpperCase(paramChar);
  }
  
  private static final String uppercase(char paramChar)
  {
    String str = String.valueOf(paramChar);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    Intrinsics.checkNotNull(str, "null cannot be cast to non-null type java.lang.String");
    str = str.toUpperCase(Locale.ROOT);
    Intrinsics.checkNotNullExpressionValue(str, "this as java.lang.String).toUpperCase(Locale.ROOT)");
    return str;
  }
  
  public static final String uppercase(char paramChar, Locale paramLocale)
  {
    Intrinsics.checkNotNullParameter(paramLocale, "locale");
    String str = String.valueOf(paramChar);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    Intrinsics.checkNotNull(str, "null cannot be cast to non-null type java.lang.String");
    paramLocale = str.toUpperCase(paramLocale);
    Intrinsics.checkNotNullExpressionValue(paramLocale, "this as java.lang.String).toUpperCase(locale)");
    return paramLocale;
  }
  
  private static final char uppercaseChar(char paramChar)
  {
    return Character.toUpperCase(paramChar);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/text/CharsKt__CharJVMKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */