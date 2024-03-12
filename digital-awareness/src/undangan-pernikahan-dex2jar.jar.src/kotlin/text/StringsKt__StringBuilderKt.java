package kotlin.text;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000F\n\000\n\002\020\016\n\000\n\002\020\b\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\020\002\n\002\030\002\n\002\b\002\n\002\020\000\n\000\n\002\020\021\n\002\b\003\n\002\020\013\n\002\020\f\n\002\020\031\n\002\020\r\n\000\032>\020\000\032\0020\0012\006\020\002\032\0020\0032\033\020\004\032\027\022\b\022\0060\006j\002`\007\022\004\022\0020\b0\005¢\006\002\b\tH\bø\001\000\002\n\n\b\b\001\022\002\020\002 \001\0326\020\000\032\0020\0012\033\020\004\032\027\022\b\022\0060\006j\002`\007\022\004\022\0020\b0\005¢\006\002\b\tH\bø\001\000\002\n\n\b\b\001\022\002\020\001 \001\032\037\020\n\032\0060\006j\002`\007*\0060\006j\002`\0072\b\020\013\032\004\030\0010\fH\b\032/\020\n\032\0060\006j\002`\007*\0060\006j\002`\0072\026\020\r\032\f\022\b\b\001\022\004\030\0010\f0\016\"\004\030\0010\f¢\006\002\020\017\032/\020\n\032\0060\006j\002`\007*\0060\006j\002`\0072\026\020\r\032\f\022\b\b\001\022\004\030\0010\0010\016\"\004\030\0010\001¢\006\002\020\020\032\025\020\021\032\0060\006j\002`\007*\0060\006j\002`\007H\b\032\037\020\021\032\0060\006j\002`\007*\0060\006j\002`\0072\b\020\r\032\004\030\0010\fH\b\032\035\020\021\032\0060\006j\002`\007*\0060\006j\002`\0072\006\020\r\032\0020\022H\b\032\035\020\021\032\0060\006j\002`\007*\0060\006j\002`\0072\006\020\r\032\0020\023H\b\032\035\020\021\032\0060\006j\002`\007*\0060\006j\002`\0072\006\020\r\032\0020\024H\b\032\037\020\021\032\0060\006j\002`\007*\0060\006j\002`\0072\b\020\r\032\004\030\0010\025H\b\032\037\020\021\032\0060\006j\002`\007*\0060\006j\002`\0072\b\020\r\032\004\030\0010\001H\b\002\007\n\005\b20\001¨\006\026"}, d2={"buildString", "", "capacity", "", "builderAction", "Lkotlin/Function1;", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "", "Lkotlin/ExtensionFunctionType;", "append", "obj", "", "value", "", "(Ljava/lang/StringBuilder;[Ljava/lang/Object;)Ljava/lang/StringBuilder;", "(Ljava/lang/StringBuilder;[Ljava/lang/String;)Ljava/lang/StringBuilder;", "appendLine", "", "", "", "", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/text/StringsKt")
class StringsKt__StringBuilderKt
  extends StringsKt__StringBuilderJVMKt
{
  @Deprecated(level=DeprecationLevel.WARNING, message="Use append(value: Any?) instead", replaceWith=@ReplaceWith(expression="append(value = obj)", imports={}))
  private static final StringBuilder append(StringBuilder paramStringBuilder, Object paramObject)
  {
    Intrinsics.checkNotNullParameter(paramStringBuilder, "<this>");
    paramStringBuilder = paramStringBuilder.append(paramObject);
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder, "this.append(obj)");
    return paramStringBuilder;
  }
  
  public static final StringBuilder append(StringBuilder paramStringBuilder, Object... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramStringBuilder, "<this>");
    Intrinsics.checkNotNullParameter(paramVarArgs, "value");
    int j = paramVarArgs.length;
    for (int i = 0; i < j; i++) {
      paramStringBuilder.append(paramVarArgs[i]);
    }
    return paramStringBuilder;
  }
  
  public static final StringBuilder append(StringBuilder paramStringBuilder, String... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramStringBuilder, "<this>");
    Intrinsics.checkNotNullParameter(paramVarArgs, "value");
    int j = paramVarArgs.length;
    for (int i = 0; i < j; i++) {
      paramStringBuilder.append(paramVarArgs[i]);
    }
    return paramStringBuilder;
  }
  
  private static final StringBuilder appendLine(StringBuilder paramStringBuilder)
  {
    Intrinsics.checkNotNullParameter(paramStringBuilder, "<this>");
    paramStringBuilder = paramStringBuilder.append('\n');
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder, "append('\\n')");
    return paramStringBuilder;
  }
  
  private static final StringBuilder appendLine(StringBuilder paramStringBuilder, char paramChar)
  {
    Intrinsics.checkNotNullParameter(paramStringBuilder, "<this>");
    paramStringBuilder = paramStringBuilder.append(paramChar);
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder, "append(value)");
    paramStringBuilder = paramStringBuilder.append('\n');
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder, "append('\\n')");
    return paramStringBuilder;
  }
  
  private static final StringBuilder appendLine(StringBuilder paramStringBuilder, CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramStringBuilder, "<this>");
    paramStringBuilder = paramStringBuilder.append(paramCharSequence);
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder, "append(value)");
    paramStringBuilder = paramStringBuilder.append('\n');
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder, "append('\\n')");
    return paramStringBuilder;
  }
  
  private static final StringBuilder appendLine(StringBuilder paramStringBuilder, Object paramObject)
  {
    Intrinsics.checkNotNullParameter(paramStringBuilder, "<this>");
    paramStringBuilder = paramStringBuilder.append(paramObject);
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder, "append(value)");
    paramStringBuilder = paramStringBuilder.append('\n');
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder, "append('\\n')");
    return paramStringBuilder;
  }
  
  private static final StringBuilder appendLine(StringBuilder paramStringBuilder, String paramString)
  {
    Intrinsics.checkNotNullParameter(paramStringBuilder, "<this>");
    paramStringBuilder = paramStringBuilder.append(paramString);
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder, "append(value)");
    paramStringBuilder = paramStringBuilder.append('\n');
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder, "append('\\n')");
    return paramStringBuilder;
  }
  
  private static final StringBuilder appendLine(StringBuilder paramStringBuilder, boolean paramBoolean)
  {
    Intrinsics.checkNotNullParameter(paramStringBuilder, "<this>");
    paramStringBuilder = paramStringBuilder.append(paramBoolean);
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder, "append(value)");
    paramStringBuilder = paramStringBuilder.append('\n');
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder, "append('\\n')");
    return paramStringBuilder;
  }
  
  private static final StringBuilder appendLine(StringBuilder paramStringBuilder, char[] paramArrayOfChar)
  {
    Intrinsics.checkNotNullParameter(paramStringBuilder, "<this>");
    Intrinsics.checkNotNullParameter(paramArrayOfChar, "value");
    paramStringBuilder = paramStringBuilder.append(paramArrayOfChar);
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder, "append(value)");
    paramStringBuilder = paramStringBuilder.append('\n');
    Intrinsics.checkNotNullExpressionValue(paramStringBuilder, "append('\\n')");
    return paramStringBuilder;
  }
  
  private static final String buildString(int paramInt, Function1<? super StringBuilder, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramFunction1, "builderAction");
    StringBuilder localStringBuilder = new StringBuilder(paramInt);
    paramFunction1.invoke(localStringBuilder);
    paramFunction1 = localStringBuilder.toString();
    Intrinsics.checkNotNullExpressionValue(paramFunction1, "StringBuilder(capacity).…builderAction).toString()");
    return paramFunction1;
  }
  
  private static final String buildString(Function1<? super StringBuilder, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramFunction1, "builderAction");
    StringBuilder localStringBuilder = new StringBuilder();
    paramFunction1.invoke(localStringBuilder);
    paramFunction1 = localStringBuilder.toString();
    Intrinsics.checkNotNullExpressionValue(paramFunction1, "StringBuilder().apply(builderAction).toString()");
    return paramFunction1;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/text/StringsKt__StringBuilderKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */