package androidx.core.text;

import android.text.SpannableStringBuilder;
import android.text.SpannedString;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.UnderlineSpan;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000:\n\000\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\020\002\n\002\030\002\n\002\b\002\n\002\020\b\n\002\b\003\n\002\020\000\n\000\n\002\020\021\n\002\b\004\n\002\020\007\n\002\b\005\032%\020\000\032\0020\0012\027\020\002\032\023\022\004\022\0020\004\022\004\022\0020\0050\003¢\006\002\b\006H\bø\001\000\0323\020\007\032\0020\004*\0020\0042\b\b\001\020\b\032\0020\t2\027\020\002\032\023\022\004\022\0020\004\022\004\022\0020\0050\003¢\006\002\b\006H\bø\001\000\032)\020\n\032\0020\004*\0020\0042\027\020\002\032\023\022\004\022\0020\004\022\004\022\0020\0050\003¢\006\002\b\006H\bø\001\000\0323\020\b\032\0020\004*\0020\0042\b\b\001\020\b\032\0020\t2\027\020\002\032\023\022\004\022\0020\004\022\004\022\0020\0050\003¢\006\002\b\006H\bø\001\000\0321\020\013\032\0020\004*\0020\0042\006\020\f\032\0020\r2\027\020\002\032\023\022\004\022\0020\004\022\004\022\0020\0050\003¢\006\002\b\006H\bø\001\000\032B\020\013\032\0020\004*\0020\0042\022\020\016\032\n\022\006\b\001\022\0020\r0\017\"\0020\r2\027\020\002\032\023\022\004\022\0020\004\022\004\022\0020\0050\003¢\006\002\b\006H\bø\001\000¢\006\002\020\020\032)\020\021\032\0020\004*\0020\0042\027\020\002\032\023\022\004\022\0020\004\022\004\022\0020\0050\003¢\006\002\b\006H\bø\001\000\0321\020\022\032\0020\004*\0020\0042\006\020\023\032\0020\0242\027\020\002\032\023\022\004\022\0020\004\022\004\022\0020\0050\003¢\006\002\b\006H\bø\001\000\032)\020\025\032\0020\004*\0020\0042\027\020\002\032\023\022\004\022\0020\004\022\004\022\0020\0050\003¢\006\002\b\006H\bø\001\000\032)\020\026\032\0020\004*\0020\0042\027\020\002\032\023\022\004\022\0020\004\022\004\022\0020\0050\003¢\006\002\b\006H\bø\001\000\032)\020\027\032\0020\004*\0020\0042\027\020\002\032\023\022\004\022\0020\004\022\004\022\0020\0050\003¢\006\002\b\006H\bø\001\000\032)\020\030\032\0020\004*\0020\0042\027\020\002\032\023\022\004\022\0020\004\022\004\022\0020\0050\003¢\006\002\b\006H\bø\001\000\002\007\n\005\b20\001¨\006\031"}, d2={"buildSpannedString", "Landroid/text/SpannedString;", "builderAction", "Lkotlin/Function1;", "Landroid/text/SpannableStringBuilder;", "", "Lkotlin/ExtensionFunctionType;", "backgroundColor", "color", "", "bold", "inSpans", "span", "", "spans", "", "(Landroid/text/SpannableStringBuilder;[Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Landroid/text/SpannableStringBuilder;", "italic", "scale", "proportion", "", "strikeThrough", "subscript", "superscript", "underline", "core-ktx_release"}, k=2, mv={1, 6, 0}, xi=48)
public final class SpannableStringBuilderKt
{
  public static final SpannableStringBuilder backgroundColor(SpannableStringBuilder paramSpannableStringBuilder, int paramInt, Function1<? super SpannableStringBuilder, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramSpannableStringBuilder, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "builderAction");
    BackgroundColorSpan localBackgroundColorSpan = new BackgroundColorSpan(paramInt);
    paramInt = paramSpannableStringBuilder.length();
    paramFunction1.invoke(paramSpannableStringBuilder);
    paramSpannableStringBuilder.setSpan(localBackgroundColorSpan, paramInt, paramSpannableStringBuilder.length(), 17);
    return paramSpannableStringBuilder;
  }
  
  public static final SpannableStringBuilder bold(SpannableStringBuilder paramSpannableStringBuilder, Function1<? super SpannableStringBuilder, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramSpannableStringBuilder, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "builderAction");
    StyleSpan localStyleSpan = new StyleSpan(1);
    int i = paramSpannableStringBuilder.length();
    paramFunction1.invoke(paramSpannableStringBuilder);
    paramSpannableStringBuilder.setSpan(localStyleSpan, i, paramSpannableStringBuilder.length(), 17);
    return paramSpannableStringBuilder;
  }
  
  public static final SpannedString buildSpannedString(Function1<? super SpannableStringBuilder, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramFunction1, "builderAction");
    SpannableStringBuilder localSpannableStringBuilder = new SpannableStringBuilder();
    paramFunction1.invoke(localSpannableStringBuilder);
    return new SpannedString((CharSequence)localSpannableStringBuilder);
  }
  
  public static final SpannableStringBuilder color(SpannableStringBuilder paramSpannableStringBuilder, int paramInt, Function1<? super SpannableStringBuilder, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramSpannableStringBuilder, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "builderAction");
    ForegroundColorSpan localForegroundColorSpan = new ForegroundColorSpan(paramInt);
    paramInt = paramSpannableStringBuilder.length();
    paramFunction1.invoke(paramSpannableStringBuilder);
    paramSpannableStringBuilder.setSpan(localForegroundColorSpan, paramInt, paramSpannableStringBuilder.length(), 17);
    return paramSpannableStringBuilder;
  }
  
  public static final SpannableStringBuilder inSpans(SpannableStringBuilder paramSpannableStringBuilder, Object paramObject, Function1<? super SpannableStringBuilder, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramSpannableStringBuilder, "<this>");
    Intrinsics.checkNotNullParameter(paramObject, "span");
    Intrinsics.checkNotNullParameter(paramFunction1, "builderAction");
    int i = paramSpannableStringBuilder.length();
    paramFunction1.invoke(paramSpannableStringBuilder);
    paramSpannableStringBuilder.setSpan(paramObject, i, paramSpannableStringBuilder.length(), 17);
    return paramSpannableStringBuilder;
  }
  
  public static final SpannableStringBuilder inSpans(SpannableStringBuilder paramSpannableStringBuilder, Object[] paramArrayOfObject, Function1<? super SpannableStringBuilder, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramSpannableStringBuilder, "<this>");
    Intrinsics.checkNotNullParameter(paramArrayOfObject, "spans");
    Intrinsics.checkNotNullParameter(paramFunction1, "builderAction");
    int j = paramSpannableStringBuilder.length();
    paramFunction1.invoke(paramSpannableStringBuilder);
    int k = paramArrayOfObject.length;
    for (int i = 0; i < k; i++) {
      paramSpannableStringBuilder.setSpan(paramArrayOfObject[i], j, paramSpannableStringBuilder.length(), 17);
    }
    return paramSpannableStringBuilder;
  }
  
  public static final SpannableStringBuilder italic(SpannableStringBuilder paramSpannableStringBuilder, Function1<? super SpannableStringBuilder, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramSpannableStringBuilder, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "builderAction");
    StyleSpan localStyleSpan = new StyleSpan(2);
    int i = paramSpannableStringBuilder.length();
    paramFunction1.invoke(paramSpannableStringBuilder);
    paramSpannableStringBuilder.setSpan(localStyleSpan, i, paramSpannableStringBuilder.length(), 17);
    return paramSpannableStringBuilder;
  }
  
  public static final SpannableStringBuilder scale(SpannableStringBuilder paramSpannableStringBuilder, float paramFloat, Function1<? super SpannableStringBuilder, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramSpannableStringBuilder, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "builderAction");
    RelativeSizeSpan localRelativeSizeSpan = new RelativeSizeSpan(paramFloat);
    int i = paramSpannableStringBuilder.length();
    paramFunction1.invoke(paramSpannableStringBuilder);
    paramSpannableStringBuilder.setSpan(localRelativeSizeSpan, i, paramSpannableStringBuilder.length(), 17);
    return paramSpannableStringBuilder;
  }
  
  public static final SpannableStringBuilder strikeThrough(SpannableStringBuilder paramSpannableStringBuilder, Function1<? super SpannableStringBuilder, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramSpannableStringBuilder, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "builderAction");
    StrikethroughSpan localStrikethroughSpan = new StrikethroughSpan();
    int i = paramSpannableStringBuilder.length();
    paramFunction1.invoke(paramSpannableStringBuilder);
    paramSpannableStringBuilder.setSpan(localStrikethroughSpan, i, paramSpannableStringBuilder.length(), 17);
    return paramSpannableStringBuilder;
  }
  
  public static final SpannableStringBuilder subscript(SpannableStringBuilder paramSpannableStringBuilder, Function1<? super SpannableStringBuilder, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramSpannableStringBuilder, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "builderAction");
    SubscriptSpan localSubscriptSpan = new SubscriptSpan();
    int i = paramSpannableStringBuilder.length();
    paramFunction1.invoke(paramSpannableStringBuilder);
    paramSpannableStringBuilder.setSpan(localSubscriptSpan, i, paramSpannableStringBuilder.length(), 17);
    return paramSpannableStringBuilder;
  }
  
  public static final SpannableStringBuilder superscript(SpannableStringBuilder paramSpannableStringBuilder, Function1<? super SpannableStringBuilder, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramSpannableStringBuilder, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "builderAction");
    SuperscriptSpan localSuperscriptSpan = new SuperscriptSpan();
    int i = paramSpannableStringBuilder.length();
    paramFunction1.invoke(paramSpannableStringBuilder);
    paramSpannableStringBuilder.setSpan(localSuperscriptSpan, i, paramSpannableStringBuilder.length(), 17);
    return paramSpannableStringBuilder;
  }
  
  public static final SpannableStringBuilder underline(SpannableStringBuilder paramSpannableStringBuilder, Function1<? super SpannableStringBuilder, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramSpannableStringBuilder, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "builderAction");
    UnderlineSpan localUnderlineSpan = new UnderlineSpan();
    int i = paramSpannableStringBuilder.length();
    paramFunction1.invoke(paramSpannableStringBuilder);
    paramSpannableStringBuilder.setSpan(localUnderlineSpan, i, paramSpannableStringBuilder.length(), 17);
    return paramSpannableStringBuilder;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/text/SpannableStringBuilderKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */