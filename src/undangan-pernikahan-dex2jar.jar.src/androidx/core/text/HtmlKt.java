package androidx.core.text;

import android.text.Html.ImageGetter;
import android.text.Html.TagHandler;
import android.text.Spanned;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000 \n\000\n\002\030\002\n\002\020\016\n\000\n\002\020\b\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\003\032/\020\000\032\0020\001*\0020\0022\b\b\002\020\003\032\0020\0042\n\b\002\020\005\032\004\030\0010\0062\n\b\002\020\007\032\004\030\0010\bH\b\032\027\020\t\032\0020\002*\0020\0012\b\b\002\020\n\032\0020\004H\b¨\006\013"}, d2={"parseAsHtml", "Landroid/text/Spanned;", "", "flags", "", "imageGetter", "Landroid/text/Html$ImageGetter;", "tagHandler", "Landroid/text/Html$TagHandler;", "toHtml", "option", "core-ktx_release"}, k=2, mv={1, 6, 0}, xi=48)
public final class HtmlKt
{
  public static final Spanned parseAsHtml(String paramString, int paramInt, Html.ImageGetter paramImageGetter, Html.TagHandler paramTagHandler)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    paramString = HtmlCompat.fromHtml(paramString, paramInt, paramImageGetter, paramTagHandler);
    Intrinsics.checkNotNullExpressionValue(paramString, "fromHtml(this, flags, imageGetter, tagHandler)");
    return paramString;
  }
  
  public static final String toHtml(Spanned paramSpanned, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramSpanned, "<this>");
    paramSpanned = HtmlCompat.toHtml(paramSpanned, paramInt);
    Log5ECF72.a(paramSpanned);
    LogE84000.a(paramSpanned);
    Log229316.a(paramSpanned);
    Intrinsics.checkNotNullExpressionValue(paramSpanned, "toHtml(this, option)");
    return paramSpanned;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/text/HtmlKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */