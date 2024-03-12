package androidx.core.text;

import android.text.Spanned;
import android.text.SpannedString;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000 \n\000\n\002\020\021\n\000\n\002\020\000\n\002\030\002\n\000\n\002\020\b\n\002\b\003\n\002\020\r\n\000\032:\020\000\032\n\022\006\b\001\022\002H\0020\001\"\n\b\000\020\002\030\001*\0020\003*\0020\0042\b\b\002\020\005\032\0020\0062\b\b\002\020\007\032\0020\006H\b¢\006\002\020\b\032\r\020\t\032\0020\004*\0020\nH\b¨\006\013"}, d2={"getSpans", "", "T", "", "Landroid/text/Spanned;", "start", "", "end", "(Landroid/text/Spanned;II)[Ljava/lang/Object;", "toSpanned", "", "core-ktx_release"}, k=2, mv={1, 6, 0}, xi=48)
public final class SpannedStringKt
{
  public static final Spanned toSpanned(CharSequence paramCharSequence)
  {
    Intrinsics.checkNotNullParameter(paramCharSequence, "<this>");
    paramCharSequence = SpannedString.valueOf(paramCharSequence);
    Intrinsics.checkNotNullExpressionValue(paramCharSequence, "valueOf(this)");
    return (Spanned)paramCharSequence;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/text/SpannedStringKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */