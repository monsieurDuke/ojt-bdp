package kotlin.text;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import kotlin.Deprecated;
import kotlin.DeprecatedSinceKotlin;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.collections.AbstractList;
import kotlin.collections.AbstractList.Companion;
import kotlin.collections.ArraysKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000~\n\000\n\002\030\002\n\002\020\016\n\002\030\002\n\002\030\002\n\002\b\004\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\022\n\000\n\002\030\002\n\000\n\002\020\b\n\002\b\002\n\002\020\031\n\000\n\002\020\025\n\002\b\002\n\002\030\002\n\002\b\t\n\002\020\013\n\002\b\003\n\002\020\r\n\002\b\n\n\002\020\021\n\002\020\000\n\002\b\n\n\002\020\f\n\002\b\021\n\002\020 \n\000\n\002\030\002\n\002\b\016\032\021\020\007\032\0020\0022\006\020\b\032\0020\tH\b\032\021\020\007\032\0020\0022\006\020\n\032\0020\013H\b\032\021\020\007\032\0020\0022\006\020\f\032\0020\rH\b\032\031\020\007\032\0020\0022\006\020\f\032\0020\r2\006\020\016\032\0020\017H\b\032!\020\007\032\0020\0022\006\020\f\032\0020\r2\006\020\020\032\0020\0212\006\020\022\032\0020\021H\b\032)\020\007\032\0020\0022\006\020\f\032\0020\r2\006\020\020\032\0020\0212\006\020\022\032\0020\0212\006\020\016\032\0020\017H\b\032\021\020\007\032\0020\0022\006\020\023\032\0020\024H\b\032!\020\007\032\0020\0022\006\020\023\032\0020\0242\006\020\020\032\0020\0212\006\020\022\032\0020\021H\b\032!\020\007\032\0020\0022\006\020\025\032\0020\0262\006\020\020\032\0020\0212\006\020\022\032\0020\021H\b\032\f\020\027\032\0020\002*\0020\002H\007\032\024\020\027\032\0020\002*\0020\0022\006\020\030\032\0020\031H\007\032\025\020\032\032\0020\021*\0020\0022\006\020\033\032\0020\021H\b\032\025\020\034\032\0020\021*\0020\0022\006\020\033\032\0020\021H\b\032\035\020\035\032\0020\021*\0020\0022\006\020\036\032\0020\0212\006\020\037\032\0020\021H\b\032\034\020 \032\0020\021*\0020\0022\006\020!\032\0020\0022\b\b\002\020\"\032\0020#\032\f\020$\032\0020\002*\0020\024H\007\032 \020$\032\0020\002*\0020\0242\b\b\002\020%\032\0020\0212\b\b\002\020\037\032\0020\021H\007\032\031\020&\032\0020#*\004\030\0010'2\b\020!\032\004\030\0010'H\004\032 \020&\032\0020#*\004\030\0010'2\b\020!\032\004\030\0010'2\006\020\"\032\0020#H\007\032\025\020&\032\0020#*\0020\0022\006\020\n\032\0020\tH\b\032\025\020&\032\0020#*\0020\0022\006\020(\032\0020'H\b\032\f\020)\032\0020\002*\0020\002H\007\032\024\020)\032\0020\002*\0020\0022\006\020\030\032\0020\031H\007\032\f\020*\032\0020\002*\0020\rH\007\032*\020*\032\0020\002*\0020\r2\b\b\002\020%\032\0020\0212\b\b\002\020\037\032\0020\0212\b\b\002\020+\032\0020#H\007\032\f\020,\032\0020\r*\0020\002H\007\032*\020,\032\0020\r*\0020\0022\b\b\002\020%\032\0020\0212\b\b\002\020\037\032\0020\0212\b\b\002\020+\032\0020#H\007\032\034\020-\032\0020#*\0020\0022\006\020.\032\0020\0022\b\b\002\020\"\032\0020#\032 \020/\032\0020#*\004\030\0010\0022\b\020!\032\004\030\0010\0022\b\b\002\020\"\032\0020#\0322\0200\032\0020\002*\0020\0022\006\020\030\032\0020\0312\026\0201\032\f\022\b\b\001\022\004\030\0010302\"\004\030\00103H\b¢\006\002\0204\0326\0200\032\0020\002*\0020\0022\b\020\030\032\004\030\0010\0312\026\0201\032\f\022\b\b\001\022\004\030\0010302\"\004\030\00103H\b¢\006\004\b5\0204\032*\0200\032\0020\002*\0020\0022\026\0201\032\f\022\b\b\001\022\004\030\0010302\"\004\030\00103H\b¢\006\002\0206\032:\0200\032\0020\002*\0020\0042\006\020\030\032\0020\0312\006\0200\032\0020\0022\026\0201\032\f\022\b\b\001\022\004\030\0010302\"\004\030\00103H\b¢\006\002\0207\032>\0200\032\0020\002*\0020\0042\b\020\030\032\004\030\0010\0312\006\0200\032\0020\0022\026\0201\032\f\022\b\b\001\022\004\030\0010302\"\004\030\00103H\b¢\006\004\b5\0207\0322\0200\032\0020\002*\0020\0042\006\0200\032\0020\0022\026\0201\032\f\022\b\b\001\022\004\030\0010302\"\004\030\00103H\b¢\006\002\0208\032\r\0209\032\0020\002*\0020\002H\b\032\n\020:\032\0020#*\0020'\032\r\020;\032\0020\002*\0020\002H\b\032\025\020;\032\0020\002*\0020\0022\006\020\030\032\0020\031H\b\032\035\020<\032\0020\021*\0020\0022\006\020=\032\0020>2\006\020?\032\0020\021H\b\032\035\020<\032\0020\021*\0020\0022\006\020@\032\0020\0022\006\020?\032\0020\021H\b\032\035\020A\032\0020\021*\0020\0022\006\020=\032\0020>2\006\020?\032\0020\021H\b\032\035\020A\032\0020\021*\0020\0022\006\020@\032\0020\0022\006\020?\032\0020\021H\b\032\035\020B\032\0020\021*\0020\0022\006\020\033\032\0020\0212\006\020C\032\0020\021H\b\0324\020D\032\0020#*\0020'2\006\020E\032\0020\0212\006\020!\032\0020'2\006\020F\032\0020\0212\006\020\022\032\0020\0212\b\b\002\020\"\032\0020#\0324\020D\032\0020#*\0020\0022\006\020E\032\0020\0212\006\020!\032\0020\0022\006\020F\032\0020\0212\006\020\022\032\0020\0212\b\b\002\020\"\032\0020#\032\022\020G\032\0020\002*\0020'2\006\020H\032\0020\021\032$\020I\032\0020\002*\0020\0022\006\020J\032\0020>2\006\020K\032\0020>2\b\b\002\020\"\032\0020#\032$\020I\032\0020\002*\0020\0022\006\020L\032\0020\0022\006\020M\032\0020\0022\b\b\002\020\"\032\0020#\032$\020N\032\0020\002*\0020\0022\006\020J\032\0020>2\006\020K\032\0020>2\b\b\002\020\"\032\0020#\032$\020N\032\0020\002*\0020\0022\006\020L\032\0020\0022\006\020M\032\0020\0022\b\b\002\020\"\032\0020#\032\"\020O\032\b\022\004\022\0020\0020P*\0020'2\006\020Q\032\0020R2\b\b\002\020S\032\0020\021\032\034\020T\032\0020#*\0020\0022\006\020U\032\0020\0022\b\b\002\020\"\032\0020#\032$\020T\032\0020#*\0020\0022\006\020U\032\0020\0022\006\020%\032\0020\0212\b\b\002\020\"\032\0020#\032\025\020V\032\0020\002*\0020\0022\006\020%\032\0020\021H\b\032\035\020V\032\0020\002*\0020\0022\006\020%\032\0020\0212\006\020\037\032\0020\021H\b\032\027\020W\032\0020\r*\0020\0022\b\b\002\020\016\032\0020\017H\b\032\r\020X\032\0020\024*\0020\002H\b\0323\020X\032\0020\024*\0020\0022\006\020Y\032\0020\0242\b\b\002\020Z\032\0020\0212\b\b\002\020%\032\0020\0212\b\b\002\020\037\032\0020\021H\b\032 \020X\032\0020\024*\0020\0022\b\b\002\020%\032\0020\0212\b\b\002\020\037\032\0020\021H\007\032\r\020[\032\0020\002*\0020\002H\b\032\025\020[\032\0020\002*\0020\0022\006\020\030\032\0020\031H\b\032\027\020\\\032\0020R*\0020\0022\b\b\002\020]\032\0020\021H\b\032\r\020^\032\0020\002*\0020\002H\b\032\025\020^\032\0020\002*\0020\0022\006\020\030\032\0020\031H\b\032\r\020_\032\0020\002*\0020\002H\b\032\025\020_\032\0020\002*\0020\0022\006\020\030\032\0020\031H\b\"%\020\000\032\022\022\004\022\0020\0020\001j\b\022\004\022\0020\002`\003*\0020\0048F¢\006\006\032\004\b\005\020\006¨\006`"}, d2={"CASE_INSENSITIVE_ORDER", "Ljava/util/Comparator;", "", "Lkotlin/Comparator;", "Lkotlin/String$Companion;", "getCASE_INSENSITIVE_ORDER", "(Lkotlin/jvm/internal/StringCompanionObject;)Ljava/util/Comparator;", "String", "stringBuffer", "Ljava/lang/StringBuffer;", "stringBuilder", "Ljava/lang/StringBuilder;", "bytes", "", "charset", "Ljava/nio/charset/Charset;", "offset", "", "length", "chars", "", "codePoints", "", "capitalize", "locale", "Ljava/util/Locale;", "codePointAt", "index", "codePointBefore", "codePointCount", "beginIndex", "endIndex", "compareTo", "other", "ignoreCase", "", "concatToString", "startIndex", "contentEquals", "", "charSequence", "decapitalize", "decodeToString", "throwOnInvalidSequence", "encodeToByteArray", "endsWith", "suffix", "equals", "format", "args", "", "", "(Ljava/lang/String;Ljava/util/Locale;[Ljava/lang/Object;)Ljava/lang/String;", "formatNullable", "(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", "(Lkotlin/jvm/internal/StringCompanionObject;Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", "(Lkotlin/jvm/internal/StringCompanionObject;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", "intern", "isBlank", "lowercase", "nativeIndexOf", "ch", "", "fromIndex", "str", "nativeLastIndexOf", "offsetByCodePoints", "codePointOffset", "regionMatches", "thisOffset", "otherOffset", "repeat", "n", "replace", "oldChar", "newChar", "oldValue", "newValue", "replaceFirst", "split", "", "regex", "Ljava/util/regex/Pattern;", "limit", "startsWith", "prefix", "substring", "toByteArray", "toCharArray", "destination", "destinationOffset", "toLowerCase", "toPattern", "flags", "toUpperCase", "uppercase", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/text/StringsKt")
class StringsKt__StringsJVMKt
  extends StringsKt__StringNumberConversionsKt
{
  private static final String String(StringBuffer paramStringBuffer)
  {
    Intrinsics.checkNotNullParameter(paramStringBuffer, "stringBuffer");
    paramStringBuffer = new String(paramStringBuffer);
    Log5ECF72.a(paramStringBuffer);
    LogE84000.a(paramStringBuffer);
    Log229316.a(paramStringBuffer);
    return paramStringBuffer;
  }
  
  private static final String String(StringBuilder paramStringBuilder)
  {
    Intrinsics.checkNotNullParameter(paramStringBuilder, "stringBuilder");
    paramStringBuilder = new String(paramStringBuilder);
    Log5ECF72.a(paramStringBuilder);
    LogE84000.a(paramStringBuilder);
    Log229316.a(paramStringBuilder);
    return paramStringBuilder;
  }
  
  private static final String String(byte[] paramArrayOfByte)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "bytes");
    paramArrayOfByte = new String(paramArrayOfByte, Charsets.UTF_8);
    Log5ECF72.a(paramArrayOfByte);
    LogE84000.a(paramArrayOfByte);
    Log229316.a(paramArrayOfByte);
    return paramArrayOfByte;
  }
  
  private static final String String(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "bytes");
    paramArrayOfByte = new String(paramArrayOfByte, paramInt1, paramInt2, Charsets.UTF_8);
    Log5ECF72.a(paramArrayOfByte);
    LogE84000.a(paramArrayOfByte);
    Log229316.a(paramArrayOfByte);
    return paramArrayOfByte;
  }
  
  private static final String String(byte[] paramArrayOfByte, int paramInt1, int paramInt2, Charset paramCharset)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "bytes");
    Intrinsics.checkNotNullParameter(paramCharset, "charset");
    paramArrayOfByte = new String(paramArrayOfByte, paramInt1, paramInt2, paramCharset);
    Log5ECF72.a(paramArrayOfByte);
    LogE84000.a(paramArrayOfByte);
    Log229316.a(paramArrayOfByte);
    return paramArrayOfByte;
  }
  
  private static final String String(byte[] paramArrayOfByte, Charset paramCharset)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "bytes");
    Intrinsics.checkNotNullParameter(paramCharset, "charset");
    paramArrayOfByte = new String(paramArrayOfByte, paramCharset);
    Log5ECF72.a(paramArrayOfByte);
    LogE84000.a(paramArrayOfByte);
    Log229316.a(paramArrayOfByte);
    return paramArrayOfByte;
  }
  
  private static final String String(char[] paramArrayOfChar)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfChar, "chars");
    paramArrayOfChar = new String(paramArrayOfChar);
    Log5ECF72.a(paramArrayOfChar);
    LogE84000.a(paramArrayOfChar);
    Log229316.a(paramArrayOfChar);
    return paramArrayOfChar;
  }
  
  private static final String String(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfChar, "chars");
    paramArrayOfChar = new String(paramArrayOfChar, paramInt1, paramInt2);
    Log5ECF72.a(paramArrayOfChar);
    LogE84000.a(paramArrayOfChar);
    Log229316.a(paramArrayOfChar);
    return paramArrayOfChar;
  }
  
  private static final String String(int[] paramArrayOfInt, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfInt, "codePoints");
    paramArrayOfInt = new String(paramArrayOfInt, paramInt1, paramInt2);
    Log5ECF72.a(paramArrayOfInt);
    LogE84000.a(paramArrayOfInt);
    Log229316.a(paramArrayOfInt);
    return paramArrayOfInt;
  }
  
  @Deprecated(message="Use replaceFirstChar instead.", replaceWith=@ReplaceWith(expression="replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }", imports={"java.util.Locale"}))
  @DeprecatedSinceKotlin(warningSince="1.5")
  public static final String capitalize(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    Locale localLocale = Locale.getDefault();
    Intrinsics.checkNotNullExpressionValue(localLocale, "getDefault()");
    paramString = StringsKt.capitalize(paramString, localLocale);
    Log5ECF72.a(paramString);
    LogE84000.a(paramString);
    Log229316.a(paramString);
    return paramString;
  }
  
  @Deprecated(message="Use replaceFirstChar instead.", replaceWith=@ReplaceWith(expression="replaceFirstChar { if (it.isLowerCase()) it.titlecase(locale) else it.toString() }", imports={}))
  @DeprecatedSinceKotlin(warningSince="1.5")
  public static final String capitalize(String paramString, Locale paramLocale)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    Intrinsics.checkNotNullParameter(paramLocale, "locale");
    int i;
    if (((CharSequence)paramString).length() > 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      char c1 = paramString.charAt(0);
      if (Character.isLowerCase(c1))
      {
        StringBuilder localStringBuilder = new StringBuilder();
        char c2 = Character.toTitleCase(c1);
        if (c2 != Character.toUpperCase(c1))
        {
          localStringBuilder.append(c2);
        }
        else
        {
          String str = paramString.substring(0, 1);
          Intrinsics.checkNotNullExpressionValue(str, "this as java.lang.String…ing(startIndex, endIndex)");
          Intrinsics.checkNotNull(str, "null cannot be cast to non-null type java.lang.String");
          paramLocale = str.toUpperCase(paramLocale);
          Intrinsics.checkNotNullExpressionValue(paramLocale, "this as java.lang.String).toUpperCase(locale)");
          localStringBuilder.append(paramLocale);
        }
        paramString = paramString.substring(1);
        Intrinsics.checkNotNullExpressionValue(paramString, "this as java.lang.String).substring(startIndex)");
        localStringBuilder.append(paramString);
        paramString = localStringBuilder.toString();
        Intrinsics.checkNotNullExpressionValue(paramString, "StringBuilder().apply(builderAction).toString()");
        return paramString;
      }
    }
    return paramString;
  }
  
  private static final int codePointAt(String paramString, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    return paramString.codePointAt(paramInt);
  }
  
  private static final int codePointBefore(String paramString, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    return paramString.codePointBefore(paramInt);
  }
  
  private static final int codePointCount(String paramString, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    return paramString.codePointCount(paramInt1, paramInt2);
  }
  
  public static final int compareTo(String paramString1, String paramString2, boolean paramBoolean)
  {
    Intrinsics.checkNotNullParameter(paramString1, "<this>");
    Intrinsics.checkNotNullParameter(paramString2, "other");
    if (paramBoolean) {
      return paramString1.compareToIgnoreCase(paramString2);
    }
    return paramString1.compareTo(paramString2);
  }
  
  public static final String concatToString(char[] paramArrayOfChar)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfChar, "<this>");
    paramArrayOfChar = new String(paramArrayOfChar);
    Log5ECF72.a(paramArrayOfChar);
    LogE84000.a(paramArrayOfChar);
    Log229316.a(paramArrayOfChar);
    return paramArrayOfChar;
  }
  
  public static final String concatToString(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfChar, "<this>");
    AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(paramInt1, paramInt2, paramArrayOfChar.length);
    paramArrayOfChar = new String(paramArrayOfChar, paramInt1, paramInt2 - paramInt1);
    Log5ECF72.a(paramArrayOfChar);
    LogE84000.a(paramArrayOfChar);
    Log229316.a(paramArrayOfChar);
    return paramArrayOfChar;
  }
  
  public static final boolean contentEquals(CharSequence paramCharSequence1, CharSequence paramCharSequence2)
  {
    boolean bool;
    if (((paramCharSequence1 instanceof String)) && (paramCharSequence2 != null)) {
      bool = ((String)paramCharSequence1).contentEquals(paramCharSequence2);
    } else {
      bool = StringsKt.contentEqualsImpl(paramCharSequence1, paramCharSequence2);
    }
    return bool;
  }
  
  public static final boolean contentEquals(CharSequence paramCharSequence1, CharSequence paramCharSequence2, boolean paramBoolean)
  {
    if (paramBoolean) {
      paramBoolean = StringsKt.contentEqualsIgnoreCaseImpl(paramCharSequence1, paramCharSequence2);
    } else {
      paramBoolean = StringsKt.contentEquals(paramCharSequence1, paramCharSequence2);
    }
    return paramBoolean;
  }
  
  private static final boolean contentEquals(String paramString, CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    Intrinsics.checkNotNullParameter(paramCharSequence, "charSequence");
    return paramString.contentEquals(paramCharSequence);
  }
  
  private static final boolean contentEquals(String paramString, StringBuffer paramStringBuffer)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    Intrinsics.checkNotNullParameter(paramStringBuffer, "stringBuilder");
    return paramString.contentEquals(paramStringBuffer);
  }
  
  @Deprecated(message="Use replaceFirstChar instead.", replaceWith=@ReplaceWith(expression="replaceFirstChar { it.lowercase(Locale.getDefault()) }", imports={"java.util.Locale"}))
  @DeprecatedSinceKotlin(warningSince="1.5")
  public static final String decapitalize(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    int i;
    if (((CharSequence)paramString).length() > 0) {
      i = 1;
    } else {
      i = 0;
    }
    if ((i != 0) && (!Character.isLowerCase(paramString.charAt(0))))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      String str = paramString.substring(0, 1);
      Intrinsics.checkNotNullExpressionValue(str, "this as java.lang.String…ing(startIndex, endIndex)");
      Intrinsics.checkNotNull(str, "null cannot be cast to non-null type java.lang.String");
      str = str.toLowerCase();
      Intrinsics.checkNotNullExpressionValue(str, "this as java.lang.String).toLowerCase()");
      localStringBuilder = localStringBuilder.append(str);
      paramString = paramString.substring(1);
      Intrinsics.checkNotNullExpressionValue(paramString, "this as java.lang.String).substring(startIndex)");
      paramString = paramString;
    }
    return paramString;
  }
  
  @Deprecated(message="Use replaceFirstChar instead.", replaceWith=@ReplaceWith(expression="replaceFirstChar { it.lowercase(locale) }", imports={}))
  @DeprecatedSinceKotlin(warningSince="1.5")
  public static final String decapitalize(String paramString, Locale paramLocale)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    Intrinsics.checkNotNullParameter(paramLocale, "locale");
    int i;
    if (((CharSequence)paramString).length() > 0) {
      i = 1;
    } else {
      i = 0;
    }
    if ((i != 0) && (!Character.isLowerCase(paramString.charAt(0))))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      String str = paramString.substring(0, 1);
      Intrinsics.checkNotNullExpressionValue(str, "this as java.lang.String…ing(startIndex, endIndex)");
      Intrinsics.checkNotNull(str, "null cannot be cast to non-null type java.lang.String");
      paramLocale = str.toLowerCase(paramLocale);
      Intrinsics.checkNotNullExpressionValue(paramLocale, "this as java.lang.String).toLowerCase(locale)");
      paramLocale = localStringBuilder.append(paramLocale);
      paramString = paramString.substring(1);
      Intrinsics.checkNotNullExpressionValue(paramString, "this as java.lang.String).substring(startIndex)");
      paramString = paramString;
    }
    return paramString;
  }
  
  public static final String decodeToString(byte[] paramArrayOfByte)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "<this>");
    paramArrayOfByte = new String(paramArrayOfByte, Charsets.UTF_8);
    Log5ECF72.a(paramArrayOfByte);
    LogE84000.a(paramArrayOfByte);
    Log229316.a(paramArrayOfByte);
    return paramArrayOfByte;
  }
  
  public static final String decodeToString(byte[] paramArrayOfByte, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "<this>");
    AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(paramInt1, paramInt2, paramArrayOfByte.length);
    if (!paramBoolean)
    {
      paramArrayOfByte = new String(paramArrayOfByte, paramInt1, paramInt2 - paramInt1, Charsets.UTF_8);
      Log5ECF72.a(paramArrayOfByte);
      LogE84000.a(paramArrayOfByte);
      Log229316.a(paramArrayOfByte);
      return paramArrayOfByte;
    }
    CharsetDecoder localCharsetDecoder = Charsets.UTF_8.newDecoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT);
    paramArrayOfByte = localCharsetDecoder.decode(ByteBuffer.wrap(paramArrayOfByte, paramInt1, paramInt2 - paramInt1)).toString();
    Intrinsics.checkNotNullExpressionValue(paramArrayOfByte, "decoder.decode(ByteBuffe…- startIndex)).toString()");
    return paramArrayOfByte;
  }
  
  public static final byte[] encodeToByteArray(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    paramString = paramString.getBytes(Charsets.UTF_8);
    Intrinsics.checkNotNullExpressionValue(paramString, "this as java.lang.String).getBytes(charset)");
    return paramString;
  }
  
  public static final byte[] encodeToByteArray(String paramString, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(paramInt1, paramInt2, paramString.length());
    if (!paramBoolean)
    {
      localObject = paramString.substring(paramInt1, paramInt2);
      Intrinsics.checkNotNullExpressionValue(localObject, "this as java.lang.String…ing(startIndex, endIndex)");
      paramString = Charsets.UTF_8;
      Intrinsics.checkNotNull(localObject, "null cannot be cast to non-null type java.lang.String");
      paramString = ((String)localObject).getBytes(paramString);
      Intrinsics.checkNotNullExpressionValue(paramString, "this as java.lang.String).getBytes(charset)");
      return paramString;
    }
    Object localObject = Charsets.UTF_8.newEncoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT);
    localObject = ((CharsetEncoder)localObject).encode(CharBuffer.wrap((CharSequence)paramString, paramInt1, paramInt2));
    if ((((ByteBuffer)localObject).hasArray()) && (((ByteBuffer)localObject).arrayOffset() == 0))
    {
      paramInt1 = ((ByteBuffer)localObject).remaining();
      paramString = ((ByteBuffer)localObject).array();
      Intrinsics.checkNotNull(paramString);
      if (paramInt1 == paramString.length)
      {
        paramString = ((ByteBuffer)localObject).array();
        Intrinsics.checkNotNullExpressionValue(paramString, "{\n        byteBuffer.array()\n    }");
        return paramString;
      }
    }
    paramString = new byte[((ByteBuffer)localObject).remaining()];
    ((ByteBuffer)localObject).get(paramString);
    return paramString;
  }
  
  public static final boolean endsWith(String paramString1, String paramString2, boolean paramBoolean)
  {
    Intrinsics.checkNotNullParameter(paramString1, "<this>");
    Intrinsics.checkNotNullParameter(paramString2, "suffix");
    if (!paramBoolean) {
      return paramString1.endsWith(paramString2);
    }
    return StringsKt.regionMatches(paramString1, paramString1.length() - paramString2.length(), paramString2, 0, paramString2.length(), true);
  }
  
  public static final boolean equals(String paramString1, String paramString2, boolean paramBoolean)
  {
    if (paramString1 == null)
    {
      if (paramString2 == null) {
        paramBoolean = true;
      } else {
        paramBoolean = false;
      }
      return paramBoolean;
    }
    if (!paramBoolean) {
      paramBoolean = paramString1.equals(paramString2);
    } else {
      paramBoolean = paramString1.equalsIgnoreCase(paramString2);
    }
    return paramBoolean;
  }
  
  private static final String format(String paramString, Object... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    Intrinsics.checkNotNullParameter(paramVarArgs, "args");
    paramString = String.format(paramString, Arrays.copyOf(paramVarArgs, paramVarArgs.length));
    Log5ECF72.a(paramString);
    LogE84000.a(paramString);
    Log229316.a(paramString);
    Intrinsics.checkNotNullExpressionValue(paramString, "format(this, *args)");
    return paramString;
  }
  
  private static final String format(StringCompanionObject paramStringCompanionObject, String paramString, Object... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramStringCompanionObject, "<this>");
    Intrinsics.checkNotNullParameter(paramString, "format");
    Intrinsics.checkNotNullParameter(paramVarArgs, "args");
    paramStringCompanionObject = String.format(paramString, Arrays.copyOf(paramVarArgs, paramVarArgs.length));
    Log5ECF72.a(paramStringCompanionObject);
    LogE84000.a(paramStringCompanionObject);
    Log229316.a(paramStringCompanionObject);
    Intrinsics.checkNotNullExpressionValue(paramStringCompanionObject, "format(format, *args)");
    return paramStringCompanionObject;
  }
  
  private static final String formatNullable(String paramString, Locale paramLocale, Object... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    Intrinsics.checkNotNullParameter(paramVarArgs, "args");
    paramString = String.format(paramLocale, paramString, Arrays.copyOf(paramVarArgs, paramVarArgs.length));
    Log5ECF72.a(paramString);
    LogE84000.a(paramString);
    Log229316.a(paramString);
    Intrinsics.checkNotNullExpressionValue(paramString, "format(locale, this, *args)");
    return paramString;
  }
  
  private static final String formatNullable(StringCompanionObject paramStringCompanionObject, Locale paramLocale, String paramString, Object... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramStringCompanionObject, "<this>");
    Intrinsics.checkNotNullParameter(paramString, "format");
    Intrinsics.checkNotNullParameter(paramVarArgs, "args");
    paramStringCompanionObject = String.format(paramLocale, paramString, Arrays.copyOf(paramVarArgs, paramVarArgs.length));
    Log5ECF72.a(paramStringCompanionObject);
    LogE84000.a(paramStringCompanionObject);
    Log229316.a(paramStringCompanionObject);
    Intrinsics.checkNotNullExpressionValue(paramStringCompanionObject, "format(locale, format, *args)");
    return paramStringCompanionObject;
  }
  
  public static final Comparator<String> getCASE_INSENSITIVE_ORDER(StringCompanionObject paramStringCompanionObject)
  {
    Intrinsics.checkNotNullParameter(paramStringCompanionObject, "<this>");
    paramStringCompanionObject = String.CASE_INSENSITIVE_ORDER;
    Intrinsics.checkNotNullExpressionValue(paramStringCompanionObject, "CASE_INSENSITIVE_ORDER");
    return paramStringCompanionObject;
  }
  
  private static final String intern(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    paramString = paramString.intern();
    Intrinsics.checkNotNullExpressionValue(paramString, "this as java.lang.String).intern()");
    return paramString;
  }
  
  public static final boolean isBlank(CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    int i = paramCharSequence.length();
    boolean bool = false;
    if (i != 0)
    {
      Object localObject = (Iterable)StringsKt.getIndices(paramCharSequence);
      if (((localObject instanceof Collection)) && (((Collection)localObject).isEmpty()))
      {
        i = 1;
      }
      else
      {
        localObject = ((Iterable)localObject).iterator();
        while (((Iterator)localObject).hasNext()) {
          if (!CharsKt.isWhitespace(paramCharSequence.charAt(((IntIterator)localObject).nextInt())))
          {
            i = 0;
            break label93;
          }
        }
        i = 1;
      }
      label93:
      if (i == 0) {}
    }
    else
    {
      bool = true;
    }
    return bool;
  }
  
  private static final String lowercase(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    paramString = paramString.toLowerCase(Locale.ROOT);
    Intrinsics.checkNotNullExpressionValue(paramString, "this as java.lang.String).toLowerCase(Locale.ROOT)");
    return paramString;
  }
  
  private static final String lowercase(String paramString, Locale paramLocale)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    Intrinsics.checkNotNullParameter(paramLocale, "locale");
    paramString = paramString.toLowerCase(paramLocale);
    Intrinsics.checkNotNullExpressionValue(paramString, "this as java.lang.String).toLowerCase(locale)");
    return paramString;
  }
  
  private static final int nativeIndexOf(String paramString, char paramChar, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    return paramString.indexOf(paramChar, paramInt);
  }
  
  private static final int nativeIndexOf(String paramString1, String paramString2, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramString1, "<this>");
    Intrinsics.checkNotNullParameter(paramString2, "str");
    return paramString1.indexOf(paramString2, paramInt);
  }
  
  private static final int nativeLastIndexOf(String paramString, char paramChar, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    return paramString.lastIndexOf(paramChar, paramInt);
  }
  
  private static final int nativeLastIndexOf(String paramString1, String paramString2, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramString1, "<this>");
    Intrinsics.checkNotNullParameter(paramString2, "str");
    return paramString1.lastIndexOf(paramString2, paramInt);
  }
  
  private static final int offsetByCodePoints(String paramString, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    return paramString.offsetByCodePoints(paramInt1, paramInt2);
  }
  
  public static final boolean regionMatches(CharSequence paramCharSequence1, int paramInt1, CharSequence paramCharSequence2, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence1, "<this>");
    Intrinsics.checkNotNullParameter(paramCharSequence2, "other");
    if (((paramCharSequence1 instanceof String)) && ((paramCharSequence2 instanceof String))) {
      return StringsKt.regionMatches((String)paramCharSequence1, paramInt1, (String)paramCharSequence2, paramInt2, paramInt3, paramBoolean);
    }
    return StringsKt.regionMatchesImpl(paramCharSequence1, paramInt1, paramCharSequence2, paramInt2, paramInt3, paramBoolean);
  }
  
  public static final boolean regionMatches(String paramString1, int paramInt1, String paramString2, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    Intrinsics.checkNotNullParameter(paramString1, "<this>");
    Intrinsics.checkNotNullParameter(paramString2, "other");
    if (!paramBoolean) {
      paramBoolean = paramString1.regionMatches(paramInt1, paramString2, paramInt2, paramInt3);
    } else {
      paramBoolean = paramString1.regionMatches(paramBoolean, paramInt1, paramString2, paramInt2, paramInt3);
    }
    return paramBoolean;
  }
  
  public static final String repeat(CharSequence paramCharSequence, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    int k = 0;
    int j;
    if (paramInt >= 0) {
      j = 1;
    } else {
      j = 0;
    }
    if (j != 0)
    {
      Object localObject = "";
      IntIterator localIntIterator;
      switch (paramInt)
      {
      default: 
        switch (paramCharSequence.length())
        {
        default: 
          localObject = new StringBuilder(paramCharSequence.length() * paramInt);
          localIntIterator = new IntRange(1, paramInt).iterator();
        }
        break;
      case 1: 
        paramCharSequence = paramCharSequence.toString();
        break;
      case 0: 
        paramCharSequence = (CharSequence)localObject;
        break label225;
        int i = paramCharSequence.charAt(0);
        paramCharSequence = new char[paramInt];
        for (j = k; j < paramInt; j++) {
          paramCharSequence[j] = i;
        }
        paramCharSequence = new String(paramCharSequence);
        Log5ECF72.a(paramCharSequence);
        LogE84000.a(paramCharSequence);
        Log229316.a(paramCharSequence);
        break label225;
        paramCharSequence = (CharSequence)localObject;
        break;
      }
      while (localIntIterator.hasNext())
      {
        localIntIterator.nextInt();
        ((StringBuilder)localObject).append(paramCharSequence);
      }
      paramCharSequence = ((StringBuilder)localObject).toString();
      Intrinsics.checkNotNullExpressionValue(paramCharSequence, "{\n                    va…tring()\n                }");
      label225:
      return paramCharSequence;
    }
    throw new IllegalArgumentException(("Count 'n' must be non-negative, but was " + paramInt + '.').toString());
  }
  
  public static final String replace(String paramString, char paramChar1, char paramChar2, boolean paramBoolean)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    if (!paramBoolean)
    {
      paramString = paramString.replace(paramChar1, paramChar2);
      Intrinsics.checkNotNullExpressionValue(paramString, "this as java.lang.String…replace(oldChar, newChar)");
      return paramString;
    }
    StringBuilder localStringBuilder = new StringBuilder(paramString.length());
    paramString = (CharSequence)paramString;
    for (int i = 0; i < paramString.length(); i++)
    {
      char c = paramString.charAt(i);
      if (CharsKt.equals(c, paramChar1, paramBoolean)) {
        c = paramChar2;
      }
      localStringBuilder.append(c);
    }
    paramString = localStringBuilder.toString();
    Intrinsics.checkNotNullExpressionValue(paramString, "StringBuilder(capacity).…builderAction).toString()");
    return paramString;
  }
  
  public static final String replace(String paramString1, String paramString2, String paramString3, boolean paramBoolean)
  {
    Intrinsics.checkNotNullParameter(paramString1, "<this>");
    Intrinsics.checkNotNullParameter(paramString2, "oldValue");
    Intrinsics.checkNotNullParameter(paramString3, "newValue");
    int j = StringsKt.indexOf((CharSequence)paramString1, paramString2, 0, paramBoolean);
    if (j < 0) {
      return paramString1;
    }
    int i1 = paramString2.length();
    int n = RangesKt.coerceAtLeast(i1, 1);
    int i = paramString1.length() - i1 + paramString3.length();
    if (i >= 0)
    {
      StringBuilder localStringBuilder = new StringBuilder(i);
      i = 0;
      int m;
      int k;
      do
      {
        localStringBuilder.append((CharSequence)paramString1, i, j).append(paramString3);
        m = j + i1;
        if (j >= paramString1.length()) {
          break;
        }
        k = StringsKt.indexOf((CharSequence)paramString1, paramString2, j + n, paramBoolean);
        j = k;
        i = m;
      } while (k > 0);
      paramString1 = localStringBuilder.append((CharSequence)paramString1, m, paramString1.length()).toString();
      Intrinsics.checkNotNullExpressionValue(paramString1, "stringBuilder.append(this, i, length).toString()");
      return paramString1;
    }
    throw new OutOfMemoryError();
  }
  
  public static final String replaceFirst(String paramString, char paramChar1, char paramChar2, boolean paramBoolean)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    int i = StringsKt.indexOf$default((CharSequence)paramString, paramChar1, 0, paramBoolean, 2, null);
    if (i >= 0)
    {
      Object localObject = String.valueOf(paramChar2);
      Log5ECF72.a(localObject);
      LogE84000.a(localObject);
      Log229316.a(localObject);
      localObject = (CharSequence)localObject;
      paramString = StringsKt.replaceRange((CharSequence)paramString, i, i + 1, (CharSequence)localObject).toString();
    }
    return paramString;
  }
  
  public static final String replaceFirst(String paramString1, String paramString2, String paramString3, boolean paramBoolean)
  {
    Intrinsics.checkNotNullParameter(paramString1, "<this>");
    Intrinsics.checkNotNullParameter(paramString2, "oldValue");
    Intrinsics.checkNotNullParameter(paramString3, "newValue");
    int j = StringsKt.indexOf$default((CharSequence)paramString1, paramString2, 0, paramBoolean, 2, null);
    if (j >= 0)
    {
      int i = paramString2.length();
      paramString1 = StringsKt.replaceRange((CharSequence)paramString1, j, i + j, (CharSequence)paramString3).toString();
    }
    return paramString1;
  }
  
  public static final List<String> split(CharSequence paramCharSequence, Pattern paramPattern, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramPattern, "regex");
    StringsKt.requireNonNegativeLimit(paramInt);
    if (paramInt == 0) {
      paramInt = -1;
    }
    paramCharSequence = paramPattern.split(paramCharSequence, paramInt);
    Intrinsics.checkNotNullExpressionValue(paramCharSequence, "regex.split(this, if (limit == 0) -1 else limit)");
    return ArraysKt.asList((Object[])paramCharSequence);
  }
  
  public static final boolean startsWith(String paramString1, String paramString2, int paramInt, boolean paramBoolean)
  {
    Intrinsics.checkNotNullParameter(paramString1, "<this>");
    Intrinsics.checkNotNullParameter(paramString2, "prefix");
    if (!paramBoolean) {
      return paramString1.startsWith(paramString2, paramInt);
    }
    return StringsKt.regionMatches(paramString1, paramInt, paramString2, 0, paramString2.length(), paramBoolean);
  }
  
  public static final boolean startsWith(String paramString1, String paramString2, boolean paramBoolean)
  {
    Intrinsics.checkNotNullParameter(paramString1, "<this>");
    Intrinsics.checkNotNullParameter(paramString2, "prefix");
    if (!paramBoolean) {
      return paramString1.startsWith(paramString2);
    }
    return StringsKt.regionMatches(paramString1, 0, paramString2, 0, paramString2.length(), paramBoolean);
  }
  
  private static final String substring(String paramString, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    paramString = paramString.substring(paramInt);
    Intrinsics.checkNotNullExpressionValue(paramString, "this as java.lang.String).substring(startIndex)");
    return paramString;
  }
  
  private static final String substring(String paramString, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    paramString = paramString.substring(paramInt1, paramInt2);
    Intrinsics.checkNotNullExpressionValue(paramString, "this as java.lang.String…ing(startIndex, endIndex)");
    return paramString;
  }
  
  private static final byte[] toByteArray(String paramString, Charset paramCharset)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    Intrinsics.checkNotNullParameter(paramCharset, "charset");
    paramString = paramString.getBytes(paramCharset);
    Intrinsics.checkNotNullExpressionValue(paramString, "this as java.lang.String).getBytes(charset)");
    return paramString;
  }
  
  private static final char[] toCharArray(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    paramString = paramString.toCharArray();
    Intrinsics.checkNotNullExpressionValue(paramString, "this as java.lang.String).toCharArray()");
    return paramString;
  }
  
  public static final char[] toCharArray(String paramString, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(paramInt1, paramInt2, paramString.length());
    char[] arrayOfChar = new char[paramInt2 - paramInt1];
    paramString.getChars(paramInt1, paramInt2, arrayOfChar, 0);
    return arrayOfChar;
  }
  
  private static final char[] toCharArray(String paramString, char[] paramArrayOfChar, int paramInt1, int paramInt2, int paramInt3)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    Intrinsics.checkNotNullParameter(paramArrayOfChar, "destination");
    paramString.getChars(paramInt2, paramInt3, paramArrayOfChar, paramInt1);
    return paramArrayOfChar;
  }
  
  @Deprecated(message="Use lowercase() instead.", replaceWith=@ReplaceWith(expression="lowercase(Locale.getDefault())", imports={"java.util.Locale"}))
  @DeprecatedSinceKotlin(warningSince="1.5")
  private static final String toLowerCase(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    paramString = paramString.toLowerCase();
    Intrinsics.checkNotNullExpressionValue(paramString, "this as java.lang.String).toLowerCase()");
    return paramString;
  }
  
  @Deprecated(message="Use lowercase() instead.", replaceWith=@ReplaceWith(expression="lowercase(locale)", imports={}))
  @DeprecatedSinceKotlin(warningSince="1.5")
  private static final String toLowerCase(String paramString, Locale paramLocale)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    Intrinsics.checkNotNullParameter(paramLocale, "locale");
    paramString = paramString.toLowerCase(paramLocale);
    Intrinsics.checkNotNullExpressionValue(paramString, "this as java.lang.String).toLowerCase(locale)");
    return paramString;
  }
  
  private static final Pattern toPattern(String paramString, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    paramString = Pattern.compile(paramString, paramInt);
    Intrinsics.checkNotNullExpressionValue(paramString, "compile(this, flags)");
    return paramString;
  }
  
  @Deprecated(message="Use uppercase() instead.", replaceWith=@ReplaceWith(expression="uppercase(Locale.getDefault())", imports={"java.util.Locale"}))
  @DeprecatedSinceKotlin(warningSince="1.5")
  private static final String toUpperCase(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    paramString = paramString.toUpperCase();
    Intrinsics.checkNotNullExpressionValue(paramString, "this as java.lang.String).toUpperCase()");
    return paramString;
  }
  
  @Deprecated(message="Use uppercase() instead.", replaceWith=@ReplaceWith(expression="uppercase(locale)", imports={}))
  @DeprecatedSinceKotlin(warningSince="1.5")
  private static final String toUpperCase(String paramString, Locale paramLocale)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    Intrinsics.checkNotNullParameter(paramLocale, "locale");
    paramString = paramString.toUpperCase(paramLocale);
    Intrinsics.checkNotNullExpressionValue(paramString, "this as java.lang.String).toUpperCase(locale)");
    return paramString;
  }
  
  private static final String uppercase(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    paramString = paramString.toUpperCase(Locale.ROOT);
    Intrinsics.checkNotNullExpressionValue(paramString, "this as java.lang.String).toUpperCase(Locale.ROOT)");
    return paramString;
  }
  
  private static final String uppercase(String paramString, Locale paramLocale)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    Intrinsics.checkNotNullParameter(paramLocale, "locale");
    paramString = paramString.toUpperCase(paramLocale);
    Intrinsics.checkNotNullExpressionValue(paramString, "this as java.lang.String).toUpperCase(locale)");
    return paramString;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/text/StringsKt__StringsJVMKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */