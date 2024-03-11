package androidx.core.text;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;

@Metadata(d1={"\000(\n\000\n\002\020\002\n\002\030\002\n\002\b\002\n\002\020\b\n\002\b\002\n\002\020\000\n\000\n\002\030\002\n\000\n\002\020\r\n\000\032\r\020\000\032\0020\001*\0020\002H\b\032%\020\003\032\0020\001*\0020\0022\006\020\004\032\0020\0052\006\020\006\032\0020\0052\006\020\007\032\0020\bH\n\032\035\020\003\032\0020\001*\0020\0022\006\020\t\032\0020\n2\006\020\007\032\0020\bH\n\032\r\020\013\032\0020\002*\0020\fH\b¨\006\r"}, d2={"clearSpans", "", "Landroid/text/Spannable;", "set", "start", "", "end", "span", "", "range", "Lkotlin/ranges/IntRange;", "toSpannable", "", "core-ktx_release"}, k=2, mv={1, 6, 0}, xi=48)
public final class SpannableStringKt
{
  public static final void clearSpans(Spannable paramSpannable)
  {
    Intrinsics.checkNotNullParameter(paramSpannable, "<this>");
    Object localObject = (Spanned)paramSpannable;
    localObject = ((Spanned)localObject).getSpans(0, ((Spanned)localObject).length(), Object.class);
    Intrinsics.checkNotNullExpressionValue(localObject, "getSpans(start, end, T::class.java)");
    int j = localObject.length;
    for (int i = 0; i < j; i++) {
      paramSpannable.removeSpan(localObject[i]);
    }
  }
  
  public static final void set(Spannable paramSpannable, int paramInt1, int paramInt2, Object paramObject)
  {
    Intrinsics.checkNotNullParameter(paramSpannable, "<this>");
    Intrinsics.checkNotNullParameter(paramObject, "span");
    paramSpannable.setSpan(paramObject, paramInt1, paramInt2, 17);
  }
  
  public static final void set(Spannable paramSpannable, IntRange paramIntRange, Object paramObject)
  {
    Intrinsics.checkNotNullParameter(paramSpannable, "<this>");
    Intrinsics.checkNotNullParameter(paramIntRange, "range");
    Intrinsics.checkNotNullParameter(paramObject, "span");
    paramSpannable.setSpan(paramObject, paramIntRange.getStart().intValue(), paramIntRange.getEndInclusive().intValue(), 17);
  }
  
  public static final Spannable toSpannable(CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    paramCharSequence = SpannableString.valueOf(paramCharSequence);
    Intrinsics.checkNotNullExpressionValue(paramCharSequence, "valueOf(this)");
    return (Spannable)paramCharSequence;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/text/SpannableStringKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */