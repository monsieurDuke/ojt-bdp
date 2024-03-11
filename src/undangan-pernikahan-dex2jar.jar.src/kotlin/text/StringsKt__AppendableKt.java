package kotlin.text;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000:\n\002\b\002\n\002\030\002\n\002\030\002\n\000\n\002\020\021\n\002\020\r\n\002\b\002\n\002\020\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\020\f\n\002\b\002\n\002\020\b\n\002\b\003\0325\020\000\032\002H\001\"\f\b\000\020\001*\0060\002j\002`\003*\002H\0012\026\020\004\032\f\022\b\b\001\022\004\030\0010\0060\005\"\004\030\0010\006¢\006\002\020\007\0329\020\b\032\0020\t\"\004\b\000\020\001*\0060\002j\002`\0032\006\020\n\032\002H\0012\024\020\013\032\020\022\004\022\002H\001\022\004\022\0020\006\030\0010\fH\000¢\006\002\020\r\032\025\020\016\032\0060\002j\002`\003*\0060\002j\002`\003H\b\032\035\020\016\032\0060\002j\002`\003*\0060\002j\002`\0032\006\020\004\032\0020\017H\b\032\037\020\016\032\0060\002j\002`\003*\0060\002j\002`\0032\b\020\004\032\004\030\0010\006H\b\0327\020\020\032\002H\001\"\f\b\000\020\001*\0060\002j\002`\003*\002H\0012\006\020\004\032\0020\0062\006\020\021\032\0020\0222\006\020\023\032\0020\022H\007¢\006\002\020\024¨\006\025"}, d2={"append", "T", "Ljava/lang/Appendable;", "Lkotlin/text/Appendable;", "value", "", "", "(Ljava/lang/Appendable;[Ljava/lang/CharSequence;)Ljava/lang/Appendable;", "appendElement", "", "element", "transform", "Lkotlin/Function1;", "(Ljava/lang/Appendable;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)V", "appendLine", "", "appendRange", "startIndex", "", "endIndex", "(Ljava/lang/Appendable;Ljava/lang/CharSequence;II)Ljava/lang/Appendable;", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/text/StringsKt")
class StringsKt__AppendableKt
{
  public static final <T extends Appendable> T append(T paramT, CharSequence... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramT, "<this>");
    Intrinsics.checkNotNullParameter(paramVarArgs, "value");
    int j = paramVarArgs.length;
    for (int i = 0; i < j; i++) {
      paramT.append(paramVarArgs[i]);
    }
    return paramT;
  }
  
  public static final <T> void appendElement(Appendable paramAppendable, T paramT, Function1<? super T, ? extends CharSequence> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramAppendable, "<this>");
    if (paramFunction1 != null)
    {
      paramAppendable.append((CharSequence)paramFunction1.invoke(paramT));
    }
    else
    {
      boolean bool;
      if (paramT == null) {
        bool = true;
      } else {
        bool = paramT instanceof CharSequence;
      }
      if (bool)
      {
        paramAppendable.append((CharSequence)paramT);
      }
      else if ((paramT instanceof Character))
      {
        paramAppendable.append(((Character)paramT).charValue());
      }
      else
      {
        paramT = String.valueOf(paramT);
        Log5ECF72.a(paramT);
        LogE84000.a(paramT);
        Log229316.a(paramT);
        paramAppendable.append((CharSequence)paramT);
      }
    }
  }
  
  private static final Appendable appendLine(Appendable paramAppendable)
  {
    Intrinsics.checkNotNullParameter(paramAppendable, "<this>");
    paramAppendable = paramAppendable.append('\n');
    Intrinsics.checkNotNullExpressionValue(paramAppendable, "append('\\n')");
    return paramAppendable;
  }
  
  private static final Appendable appendLine(Appendable paramAppendable, char paramChar)
  {
    Intrinsics.checkNotNullParameter(paramAppendable, "<this>");
    paramAppendable = paramAppendable.append(paramChar);
    Intrinsics.checkNotNullExpressionValue(paramAppendable, "append(value)");
    paramAppendable = paramAppendable.append('\n');
    Intrinsics.checkNotNullExpressionValue(paramAppendable, "append('\\n')");
    return paramAppendable;
  }
  
  private static final Appendable appendLine(Appendable paramAppendable, CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramAppendable, "<this>");
    paramAppendable = paramAppendable.append(paramCharSequence);
    Intrinsics.checkNotNullExpressionValue(paramAppendable, "append(value)");
    paramAppendable = paramAppendable.append('\n');
    Intrinsics.checkNotNullExpressionValue(paramAppendable, "append('\\n')");
    return paramAppendable;
  }
  
  public static final <T extends Appendable> T appendRange(T paramT, CharSequence paramCharSequence, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramT, "<this>");
    Intrinsics.checkNotNullParameter(paramCharSequence, "value");
    paramT = paramT.append(paramCharSequence, paramInt1, paramInt2);
    Intrinsics.checkNotNull(paramT, "null cannot be cast to non-null type T of kotlin.text.StringsKt__AppendableKt.appendRange");
    return paramT;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/text/StringsKt__AppendableKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */