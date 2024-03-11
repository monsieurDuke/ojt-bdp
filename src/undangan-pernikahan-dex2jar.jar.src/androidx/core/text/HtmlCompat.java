package androidx.core.text;

import android.os.Build.VERSION;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Html.TagHandler;
import android.text.Spanned;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public final class HtmlCompat
{
  public static final int FROM_HTML_MODE_COMPACT = 63;
  public static final int FROM_HTML_MODE_LEGACY = 0;
  public static final int FROM_HTML_OPTION_USE_CSS_COLORS = 256;
  public static final int FROM_HTML_SEPARATOR_LINE_BREAK_BLOCKQUOTE = 32;
  public static final int FROM_HTML_SEPARATOR_LINE_BREAK_DIV = 16;
  public static final int FROM_HTML_SEPARATOR_LINE_BREAK_HEADING = 2;
  public static final int FROM_HTML_SEPARATOR_LINE_BREAK_LIST = 8;
  public static final int FROM_HTML_SEPARATOR_LINE_BREAK_LIST_ITEM = 4;
  public static final int FROM_HTML_SEPARATOR_LINE_BREAK_PARAGRAPH = 1;
  public static final int TO_HTML_PARAGRAPH_LINES_CONSECUTIVE = 0;
  public static final int TO_HTML_PARAGRAPH_LINES_INDIVIDUAL = 1;
  
  public static Spanned fromHtml(String paramString, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 24) {
      return Api24Impl.fromHtml(paramString, paramInt);
    }
    return Html.fromHtml(paramString);
  }
  
  public static Spanned fromHtml(String paramString, int paramInt, Html.ImageGetter paramImageGetter, Html.TagHandler paramTagHandler)
  {
    if (Build.VERSION.SDK_INT >= 24) {
      return Api24Impl.fromHtml(paramString, paramInt, paramImageGetter, paramTagHandler);
    }
    return Html.fromHtml(paramString, paramImageGetter, paramTagHandler);
  }
  
  public static String toHtml(Spanned paramSpanned, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 24)
    {
      paramSpanned = Api24Impl.toHtml(paramSpanned, paramInt);
      Log5ECF72.a(paramSpanned);
      LogE84000.a(paramSpanned);
      Log229316.a(paramSpanned);
      return paramSpanned;
    }
    paramSpanned = Html.toHtml(paramSpanned);
    Log5ECF72.a(paramSpanned);
    LogE84000.a(paramSpanned);
    Log229316.a(paramSpanned);
    return paramSpanned;
  }
  
  static class Api24Impl
  {
    static Spanned fromHtml(String paramString, int paramInt)
    {
      return Html.fromHtml(paramString, paramInt);
    }
    
    static Spanned fromHtml(String paramString, int paramInt, Html.ImageGetter paramImageGetter, Html.TagHandler paramTagHandler)
    {
      return Html.fromHtml(paramString, paramInt, paramImageGetter, paramTagHandler);
    }
    
    static String toHtml(Spanned paramSpanned, int paramInt)
    {
      paramSpanned = Html.toHtml(paramSpanned, paramInt);
      Log5ECF72.a(paramSpanned);
      LogE84000.a(paramSpanned);
      Log229316.a(paramSpanned);
      return paramSpanned;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/text/HtmlCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */