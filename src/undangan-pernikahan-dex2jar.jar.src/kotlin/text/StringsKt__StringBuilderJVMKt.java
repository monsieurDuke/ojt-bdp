package kotlin.text;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000\\\n\000\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\020\005\n\002\020\006\n\002\020\007\n\002\020\b\n\002\020\t\n\002\020\n\n\000\n\002\020\031\n\002\b\002\n\002\020\r\n\000\n\002\030\002\n\002\030\002\n\002\020\f\n\002\020\000\n\002\020\013\n\002\020\016\n\002\b\006\n\002\020\002\n\002\b\005\032\037\020\000\032\0060\001j\002`\002*\0060\001j\002`\0022\b\020\003\032\004\030\0010\004H\b\032\035\020\000\032\0060\001j\002`\002*\0060\001j\002`\0022\006\020\003\032\0020\005H\b\032\035\020\000\032\0060\001j\002`\002*\0060\001j\002`\0022\006\020\003\032\0020\006H\b\032\035\020\000\032\0060\001j\002`\002*\0060\001j\002`\0022\006\020\003\032\0020\007H\b\032\035\020\000\032\0060\001j\002`\002*\0060\001j\002`\0022\006\020\003\032\0020\bH\b\032\035\020\000\032\0060\001j\002`\002*\0060\001j\002`\0022\006\020\003\032\0020\tH\b\032\035\020\000\032\0060\001j\002`\002*\0060\001j\002`\0022\006\020\003\032\0020\nH\b\032%\020\000\032\0060\001j\002`\002*\0060\001j\002`\0022\016\020\003\032\n\030\0010\001j\004\030\001`\002H\b\032-\020\013\032\0060\001j\002`\002*\0060\001j\002`\0022\006\020\003\032\0020\f2\006\020\r\032\0020\b2\006\020\016\032\0020\bH\b\032-\020\013\032\0060\001j\002`\002*\0060\001j\002`\0022\006\020\003\032\0020\0172\006\020\r\032\0020\b2\006\020\016\032\0020\bH\b\032\024\020\020\032\0060\021j\002`\022*\0060\021j\002`\022H\007\032\035\020\020\032\0060\021j\002`\022*\0060\021j\002`\0222\006\020\003\032\0020\023H\b\032\037\020\020\032\0060\021j\002`\022*\0060\021j\002`\0222\b\020\003\032\004\030\0010\017H\b\032\024\020\020\032\0060\001j\002`\002*\0060\001j\002`\002H\007\032\037\020\020\032\0060\001j\002`\002*\0060\001j\002`\0022\b\020\003\032\004\030\0010\004H\b\032\037\020\020\032\0060\001j\002`\002*\0060\001j\002`\0022\b\020\003\032\004\030\0010\024H\b\032\035\020\020\032\0060\001j\002`\002*\0060\001j\002`\0022\006\020\003\032\0020\025H\b\032\035\020\020\032\0060\001j\002`\002*\0060\001j\002`\0022\006\020\003\032\0020\005H\b\032\035\020\020\032\0060\001j\002`\002*\0060\001j\002`\0022\006\020\003\032\0020\023H\b\032\035\020\020\032\0060\001j\002`\002*\0060\001j\002`\0022\006\020\003\032\0020\fH\b\032\037\020\020\032\0060\001j\002`\002*\0060\001j\002`\0022\b\020\003\032\004\030\0010\017H\b\032\035\020\020\032\0060\001j\002`\002*\0060\001j\002`\0022\006\020\003\032\0020\006H\b\032\035\020\020\032\0060\001j\002`\002*\0060\001j\002`\0022\006\020\003\032\0020\007H\b\032\035\020\020\032\0060\001j\002`\002*\0060\001j\002`\0022\006\020\003\032\0020\bH\b\032\035\020\020\032\0060\001j\002`\002*\0060\001j\002`\0022\006\020\003\032\0020\tH\b\032\035\020\020\032\0060\001j\002`\002*\0060\001j\002`\0022\006\020\003\032\0020\nH\b\032\037\020\020\032\0060\001j\002`\002*\0060\001j\002`\0022\b\020\003\032\004\030\0010\026H\b\032%\020\020\032\0060\001j\002`\002*\0060\001j\002`\0022\016\020\003\032\n\030\0010\001j\004\030\001`\002H\b\032\024\020\027\032\0060\001j\002`\002*\0060\001j\002`\002H\007\032\035\020\030\032\0060\001j\002`\002*\0060\001j\002`\0022\006\020\031\032\0020\bH\b\032%\020\032\032\0060\001j\002`\002*\0060\001j\002`\0022\006\020\r\032\0020\b2\006\020\016\032\0020\bH\b\0325\020\033\032\0060\001j\002`\002*\0060\001j\002`\0022\006\020\031\032\0020\b2\006\020\003\032\0020\f2\006\020\r\032\0020\b2\006\020\016\032\0020\bH\b\0325\020\033\032\0060\001j\002`\002*\0060\001j\002`\0022\006\020\031\032\0020\b2\006\020\003\032\0020\0172\006\020\r\032\0020\b2\006\020\016\032\0020\bH\b\032!\020\034\032\0020\035*\0060\001j\002`\0022\006\020\031\032\0020\b2\006\020\003\032\0020\023H\n\032-\020\036\032\0060\001j\002`\002*\0060\001j\002`\0022\006\020\r\032\0020\b2\006\020\016\032\0020\b2\006\020\003\032\0020\026H\b\0327\020\037\032\0020\035*\0060\001j\002`\0022\006\020 \032\0020\f2\b\b\002\020!\032\0020\b2\b\b\002\020\r\032\0020\b2\b\b\002\020\016\032\0020\bH\b¨\006\""}, d2={"appendLine", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "value", "Ljava/lang/StringBuffer;", "", "", "", "", "", "", "appendRange", "", "startIndex", "endIndex", "", "appendln", "Ljava/lang/Appendable;", "Lkotlin/text/Appendable;", "", "", "", "", "clear", "deleteAt", "index", "deleteRange", "insertRange", "set", "", "setRange", "toCharArray", "destination", "destinationOffset", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/text/StringsKt")
class StringsKt__StringBuilderJVMKt
  extends StringsKt__RegexExtensionsKt
{
  private static final StringBuilder appendLine(StringBuilder paramStringBuilder, byte paramByte)
  {
    Intrinsics.checkNotNullParameter(paramStringBuilder, "<this>");
    paramStringBuilder = paramStringBuilder.append(paramByte);
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder, "append(value.toInt())");
    paramStringBuilder = paramStringBuilder.append('\n');
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder, "append('\\n')");
    return paramStringBuilder;
  }
  
  private static final StringBuilder appendLine(StringBuilder paramStringBuilder, double paramDouble)
  {
    Intrinsics.checkNotNullParameter(paramStringBuilder, "<this>");
    paramStringBuilder = paramStringBuilder.append(paramDouble);
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder, "append(value)");
    paramStringBuilder = paramStringBuilder.append('\n');
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder, "append('\\n')");
    return paramStringBuilder;
  }
  
  private static final StringBuilder appendLine(StringBuilder paramStringBuilder, float paramFloat)
  {
    Intrinsics.checkNotNullParameter(paramStringBuilder, "<this>");
    paramStringBuilder = paramStringBuilder.append(paramFloat);
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder, "append(value)");
    paramStringBuilder = paramStringBuilder.append('\n');
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder, "append('\\n')");
    return paramStringBuilder;
  }
  
  private static final StringBuilder appendLine(StringBuilder paramStringBuilder, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramStringBuilder, "<this>");
    paramStringBuilder = paramStringBuilder.append(paramInt);
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder, "append(value)");
    paramStringBuilder = paramStringBuilder.append('\n');
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder, "append('\\n')");
    return paramStringBuilder;
  }
  
  private static final StringBuilder appendLine(StringBuilder paramStringBuilder, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramStringBuilder, "<this>");
    paramStringBuilder = paramStringBuilder.append(paramLong);
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder, "append(value)");
    paramStringBuilder = paramStringBuilder.append('\n');
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder, "append('\\n')");
    return paramStringBuilder;
  }
  
  private static final StringBuilder appendLine(StringBuilder paramStringBuilder, StringBuffer paramStringBuffer)
  {
    Intrinsics.checkNotNullParameter(paramStringBuilder, "<this>");
    paramStringBuilder = paramStringBuilder.append(paramStringBuffer);
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder, "append(value)");
    paramStringBuilder = paramStringBuilder.append('\n');
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder, "append('\\n')");
    return paramStringBuilder;
  }
  
  private static final StringBuilder appendLine(StringBuilder paramStringBuilder1, StringBuilder paramStringBuilder2)
  {
    Intrinsics.checkNotNullParameter(paramStringBuilder1, "<this>");
    paramStringBuilder1 = paramStringBuilder1.append((CharSequence)paramStringBuilder2);
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder1, "append(value)");
    paramStringBuilder1 = paramStringBuilder1.append('\n');
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder1, "append('\\n')");
    return paramStringBuilder1;
  }
  
  private static final StringBuilder appendLine(StringBuilder paramStringBuilder, short paramShort)
  {
    Intrinsics.checkNotNullParameter(paramStringBuilder, "<this>");
    paramStringBuilder = paramStringBuilder.append(paramShort);
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder, "append(value.toInt())");
    paramStringBuilder = paramStringBuilder.append('\n');
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder, "append('\\n')");
    return paramStringBuilder;
  }
  
  private static final StringBuilder appendRange(StringBuilder paramStringBuilder, CharSequence paramCharSequence, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramStringBuilder, "<this>");
    Intrinsics.checkNotNullParameter(paramCharSequence, "value");
    paramStringBuilder = paramStringBuilder.append(paramCharSequence, paramInt1, paramInt2);
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder, "this.append(value, startIndex, endIndex)");
    return paramStringBuilder;
  }
  
  private static final StringBuilder appendRange(StringBuilder paramStringBuilder, char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramStringBuilder, "<this>");
    Intrinsics.checkNotNullParameter(paramArrayOfChar, "value");
    paramStringBuilder = paramStringBuilder.append(paramArrayOfChar, paramInt1, paramInt2 - paramInt1);
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder, "this.append(value, start…x, endIndex - startIndex)");
    return paramStringBuilder;
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith=@ReplaceWith(expression="appendLine()", imports={}))
  public static final Appendable appendln(Appendable paramAppendable)
  {
    Intrinsics.checkNotNullParameter(paramAppendable, "<this>");
    paramAppendable = paramAppendable.append((CharSequence)SystemProperties.LINE_SEPARATOR);
    Intrinsics.checkNotNullExpressionValue(paramAppendable, "append(SystemProperties.LINE_SEPARATOR)");
    return paramAppendable;
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith=@ReplaceWith(expression="appendLine(value)", imports={}))
  private static final Appendable appendln(Appendable paramAppendable, char paramChar)
  {
    Intrinsics.checkNotNullParameter(paramAppendable, "<this>");
    paramAppendable = paramAppendable.append(paramChar);
    Intrinsics.checkNotNullExpressionValue(paramAppendable, "append(value)");
    return StringsKt.appendln(paramAppendable);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith=@ReplaceWith(expression="appendLine(value)", imports={}))
  private static final Appendable appendln(Appendable paramAppendable, CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramAppendable, "<this>");
    paramAppendable = paramAppendable.append(paramCharSequence);
    Intrinsics.checkNotNullExpressionValue(paramAppendable, "append(value)");
    return StringsKt.appendln(paramAppendable);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith=@ReplaceWith(expression="appendLine()", imports={}))
  public static final StringBuilder appendln(StringBuilder paramStringBuilder)
  {
    Intrinsics.checkNotNullParameter(paramStringBuilder, "<this>");
    paramStringBuilder = paramStringBuilder.append(SystemProperties.LINE_SEPARATOR);
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder, "append(SystemProperties.LINE_SEPARATOR)");
    return paramStringBuilder;
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith=@ReplaceWith(expression="appendLine(value)", imports={}))
  private static final StringBuilder appendln(StringBuilder paramStringBuilder, byte paramByte)
  {
    Intrinsics.checkNotNullParameter(paramStringBuilder, "<this>");
    paramStringBuilder = paramStringBuilder.append(paramByte);
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder, "append(value.toInt())");
    return StringsKt.appendln(paramStringBuilder);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith=@ReplaceWith(expression="appendLine(value)", imports={}))
  private static final StringBuilder appendln(StringBuilder paramStringBuilder, char paramChar)
  {
    Intrinsics.checkNotNullParameter(paramStringBuilder, "<this>");
    paramStringBuilder = paramStringBuilder.append(paramChar);
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder, "append(value)");
    return StringsKt.appendln(paramStringBuilder);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith=@ReplaceWith(expression="appendLine(value)", imports={}))
  private static final StringBuilder appendln(StringBuilder paramStringBuilder, double paramDouble)
  {
    Intrinsics.checkNotNullParameter(paramStringBuilder, "<this>");
    paramStringBuilder = paramStringBuilder.append(paramDouble);
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder, "append(value)");
    return StringsKt.appendln(paramStringBuilder);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith=@ReplaceWith(expression="appendLine(value)", imports={}))
  private static final StringBuilder appendln(StringBuilder paramStringBuilder, float paramFloat)
  {
    Intrinsics.checkNotNullParameter(paramStringBuilder, "<this>");
    paramStringBuilder = paramStringBuilder.append(paramFloat);
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder, "append(value)");
    return StringsKt.appendln(paramStringBuilder);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith=@ReplaceWith(expression="appendLine(value)", imports={}))
  private static final StringBuilder appendln(StringBuilder paramStringBuilder, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramStringBuilder, "<this>");
    paramStringBuilder = paramStringBuilder.append(paramInt);
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder, "append(value)");
    return StringsKt.appendln(paramStringBuilder);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith=@ReplaceWith(expression="appendLine(value)", imports={}))
  private static final StringBuilder appendln(StringBuilder paramStringBuilder, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramStringBuilder, "<this>");
    paramStringBuilder = paramStringBuilder.append(paramLong);
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder, "append(value)");
    return StringsKt.appendln(paramStringBuilder);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith=@ReplaceWith(expression="appendLine(value)", imports={}))
  private static final StringBuilder appendln(StringBuilder paramStringBuilder, CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramStringBuilder, "<this>");
    paramStringBuilder = paramStringBuilder.append(paramCharSequence);
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder, "append(value)");
    return StringsKt.appendln(paramStringBuilder);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith=@ReplaceWith(expression="appendLine(value)", imports={}))
  private static final StringBuilder appendln(StringBuilder paramStringBuilder, Object paramObject)
  {
    Intrinsics.checkNotNullParameter(paramStringBuilder, "<this>");
    paramStringBuilder = paramStringBuilder.append(paramObject);
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder, "append(value)");
    return StringsKt.appendln(paramStringBuilder);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith=@ReplaceWith(expression="appendLine(value)", imports={}))
  private static final StringBuilder appendln(StringBuilder paramStringBuilder, String paramString)
  {
    Intrinsics.checkNotNullParameter(paramStringBuilder, "<this>");
    paramStringBuilder = paramStringBuilder.append(paramString);
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder, "append(value)");
    return StringsKt.appendln(paramStringBuilder);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith=@ReplaceWith(expression="appendLine(value)", imports={}))
  private static final StringBuilder appendln(StringBuilder paramStringBuilder, StringBuffer paramStringBuffer)
  {
    Intrinsics.checkNotNullParameter(paramStringBuilder, "<this>");
    paramStringBuilder = paramStringBuilder.append(paramStringBuffer);
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder, "append(value)");
    return StringsKt.appendln(paramStringBuilder);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith=@ReplaceWith(expression="appendLine(value)", imports={}))
  private static final StringBuilder appendln(StringBuilder paramStringBuilder1, StringBuilder paramStringBuilder2)
  {
    Intrinsics.checkNotNullParameter(paramStringBuilder1, "<this>");
    paramStringBuilder1 = paramStringBuilder1.append((CharSequence)paramStringBuilder2);
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder1, "append(value)");
    return StringsKt.appendln(paramStringBuilder1);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith=@ReplaceWith(expression="appendLine(value)", imports={}))
  private static final StringBuilder appendln(StringBuilder paramStringBuilder, short paramShort)
  {
    Intrinsics.checkNotNullParameter(paramStringBuilder, "<this>");
    paramStringBuilder = paramStringBuilder.append(paramShort);
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder, "append(value.toInt())");
    return StringsKt.appendln(paramStringBuilder);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith=@ReplaceWith(expression="appendLine(value)", imports={}))
  private static final StringBuilder appendln(StringBuilder paramStringBuilder, boolean paramBoolean)
  {
    Intrinsics.checkNotNullParameter(paramStringBuilder, "<this>");
    paramStringBuilder = paramStringBuilder.append(paramBoolean);
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder, "append(value)");
    return StringsKt.appendln(paramStringBuilder);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith=@ReplaceWith(expression="appendLine(value)", imports={}))
  private static final StringBuilder appendln(StringBuilder paramStringBuilder, char[] paramArrayOfChar)
  {
    Intrinsics.checkNotNullParameter(paramStringBuilder, "<this>");
    Intrinsics.checkNotNullParameter(paramArrayOfChar, "value");
    paramStringBuilder = paramStringBuilder.append(paramArrayOfChar);
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder, "append(value)");
    return StringsKt.appendln(paramStringBuilder);
  }
  
  public static final StringBuilder clear(StringBuilder paramStringBuilder)
  {
    Intrinsics.checkNotNullParameter(paramStringBuilder, "<this>");
    paramStringBuilder.setLength(0);
    return paramStringBuilder;
  }
  
  private static final StringBuilder deleteAt(StringBuilder paramStringBuilder, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramStringBuilder, "<this>");
    paramStringBuilder = paramStringBuilder.deleteCharAt(paramInt);
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder, "this.deleteCharAt(index)");
    return paramStringBuilder;
  }
  
  private static final StringBuilder deleteRange(StringBuilder paramStringBuilder, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramStringBuilder, "<this>");
    paramStringBuilder = paramStringBuilder.delete(paramInt1, paramInt2);
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder, "this.delete(startIndex, endIndex)");
    return paramStringBuilder;
  }
  
  private static final StringBuilder insertRange(StringBuilder paramStringBuilder, int paramInt1, CharSequence paramCharSequence, int paramInt2, int paramInt3)
  {
    Intrinsics.checkNotNullParameter(paramStringBuilder, "<this>");
    Intrinsics.checkNotNullParameter(paramCharSequence, "value");
    paramStringBuilder = paramStringBuilder.insert(paramInt1, paramCharSequence, paramInt2, paramInt3);
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder, "this.insert(index, value, startIndex, endIndex)");
    return paramStringBuilder;
  }
  
  private static final StringBuilder insertRange(StringBuilder paramStringBuilder, int paramInt1, char[] paramArrayOfChar, int paramInt2, int paramInt3)
  {
    Intrinsics.checkNotNullParameter(paramStringBuilder, "<this>");
    Intrinsics.checkNotNullParameter(paramArrayOfChar, "value");
    paramStringBuilder = paramStringBuilder.insert(paramInt1, paramArrayOfChar, paramInt2, paramInt3 - paramInt2);
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder, "this.insert(index, value…x, endIndex - startIndex)");
    return paramStringBuilder;
  }
  
  private static final void set(StringBuilder paramStringBuilder, int paramInt, char paramChar)
  {
    Intrinsics.checkNotNullParameter(paramStringBuilder, "<this>");
    paramStringBuilder.setCharAt(paramInt, paramChar);
  }
  
  private static final StringBuilder setRange(StringBuilder paramStringBuilder, int paramInt1, int paramInt2, String paramString)
  {
    Intrinsics.checkNotNullParameter(paramStringBuilder, "<this>");
    Intrinsics.checkNotNullParameter(paramString, "value");
    paramStringBuilder = paramStringBuilder.replace(paramInt1, paramInt2, paramString);
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder, "this.replace(startIndex, endIndex, value)");
    return paramStringBuilder;
  }
  
  private static final void toCharArray(StringBuilder paramStringBuilder, char[] paramArrayOfChar, int paramInt1, int paramInt2, int paramInt3)
  {
    Intrinsics.checkNotNullParameter(paramStringBuilder, "<this>");
    Intrinsics.checkNotNullParameter(paramArrayOfChar, "destination");
    paramStringBuilder.getChars(paramInt2, paramInt3, paramArrayOfChar, paramInt1);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/text/StringsKt__StringBuilderJVMKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */