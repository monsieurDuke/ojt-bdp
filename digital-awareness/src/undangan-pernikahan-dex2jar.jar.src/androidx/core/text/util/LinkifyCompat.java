package androidx.core.text.util;

import android.os.Build.VERSION;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.text.util.Linkify.MatchFilter;
import android.text.util.Linkify.TransformFilter;
import android.webkit.WebView;
import android.widget.TextView;
import androidx.core.util.PatternsCompat;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public final class LinkifyCompat
{
  private static final Comparator<LinkSpec> COMPARATOR = new LinkifyCompat..ExternalSyntheticLambda0();
  private static final String[] EMPTY_STRING = new String[0];
  
  private static void addLinkMovementMethod(TextView paramTextView)
  {
    if ((!(paramTextView.getMovementMethod() instanceof LinkMovementMethod)) && (paramTextView.getLinksClickable())) {
      paramTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }
  }
  
  public static void addLinks(TextView paramTextView, Pattern paramPattern, String paramString)
  {
    if (shouldAddLinksFallbackToFramework())
    {
      Linkify.addLinks(paramTextView, paramPattern, paramString);
      return;
    }
    addLinks(paramTextView, paramPattern, paramString, null, null, null);
  }
  
  public static void addLinks(TextView paramTextView, Pattern paramPattern, String paramString, Linkify.MatchFilter paramMatchFilter, Linkify.TransformFilter paramTransformFilter)
  {
    if (shouldAddLinksFallbackToFramework())
    {
      Linkify.addLinks(paramTextView, paramPattern, paramString, paramMatchFilter, paramTransformFilter);
      return;
    }
    addLinks(paramTextView, paramPattern, paramString, null, paramMatchFilter, paramTransformFilter);
  }
  
  public static void addLinks(TextView paramTextView, Pattern paramPattern, String paramString, String[] paramArrayOfString, Linkify.MatchFilter paramMatchFilter, Linkify.TransformFilter paramTransformFilter)
  {
    if (shouldAddLinksFallbackToFramework())
    {
      Api24Impl.addLinks(paramTextView, paramPattern, paramString, paramArrayOfString, paramMatchFilter, paramTransformFilter);
      return;
    }
    SpannableString localSpannableString = SpannableString.valueOf(paramTextView.getText());
    if (addLinks(localSpannableString, paramPattern, paramString, paramArrayOfString, paramMatchFilter, paramTransformFilter))
    {
      paramTextView.setText(localSpannableString);
      addLinkMovementMethod(paramTextView);
    }
  }
  
  public static boolean addLinks(Spannable paramSpannable, int paramInt)
  {
    if (shouldAddLinksFallbackToFramework()) {
      return Linkify.addLinks(paramSpannable, paramInt);
    }
    if (paramInt == 0) {
      return false;
    }
    Object localObject1 = (URLSpan[])paramSpannable.getSpans(0, paramSpannable.length(), URLSpan.class);
    for (int i = localObject1.length - 1; i >= 0; i--) {
      paramSpannable.removeSpan(localObject1[i]);
    }
    if ((paramInt & 0x4) != 0) {
      Linkify.addLinks(paramSpannable, 4);
    }
    Object localObject2 = new ArrayList();
    if ((paramInt & 0x1) != 0)
    {
      localObject1 = PatternsCompat.AUTOLINK_WEB_URL;
      Linkify.MatchFilter localMatchFilter = Linkify.sUrlMatchFilter;
      gatherLinks((ArrayList)localObject2, paramSpannable, (Pattern)localObject1, new String[] { "http://", "https://", "rtsp://" }, localMatchFilter, null);
    }
    if ((paramInt & 0x2) != 0) {
      gatherLinks((ArrayList)localObject2, paramSpannable, PatternsCompat.AUTOLINK_EMAIL_ADDRESS, new String[] { "mailto:" }, null, null);
    }
    if ((paramInt & 0x8) != 0) {
      gatherMapLinks((ArrayList)localObject2, paramSpannable);
    }
    pruneOverlaps((ArrayList)localObject2, paramSpannable);
    if (((ArrayList)localObject2).size() == 0) {
      return false;
    }
    localObject2 = ((ArrayList)localObject2).iterator();
    while (((Iterator)localObject2).hasNext())
    {
      localObject1 = (LinkSpec)((Iterator)localObject2).next();
      if (((LinkSpec)localObject1).frameworkAddedSpan == null) {
        applyLink(((LinkSpec)localObject1).url, ((LinkSpec)localObject1).start, ((LinkSpec)localObject1).end, paramSpannable);
      }
    }
    return true;
  }
  
  public static boolean addLinks(Spannable paramSpannable, Pattern paramPattern, String paramString)
  {
    if (shouldAddLinksFallbackToFramework()) {
      return Linkify.addLinks(paramSpannable, paramPattern, paramString);
    }
    return addLinks(paramSpannable, paramPattern, paramString, null, null, null);
  }
  
  public static boolean addLinks(Spannable paramSpannable, Pattern paramPattern, String paramString, Linkify.MatchFilter paramMatchFilter, Linkify.TransformFilter paramTransformFilter)
  {
    if (shouldAddLinksFallbackToFramework()) {
      return Linkify.addLinks(paramSpannable, paramPattern, paramString, paramMatchFilter, paramTransformFilter);
    }
    return addLinks(paramSpannable, paramPattern, paramString, null, paramMatchFilter, paramTransformFilter);
  }
  
  public static boolean addLinks(Spannable paramSpannable, Pattern paramPattern, String paramString, String[] paramArrayOfString, Linkify.MatchFilter paramMatchFilter, Linkify.TransformFilter paramTransformFilter)
  {
    if (shouldAddLinksFallbackToFramework()) {
      return Api24Impl.addLinks(paramSpannable, paramPattern, paramString, paramArrayOfString, paramMatchFilter, paramTransformFilter);
    }
    String str = paramString;
    if (paramString == null) {
      str = "";
    }
    if (paramArrayOfString != null)
    {
      paramString = paramArrayOfString;
      if (paramArrayOfString.length >= 1) {}
    }
    else
    {
      paramString = EMPTY_STRING;
    }
    String[] arrayOfString = new String[paramString.length + 1];
    arrayOfString[0] = str.toLowerCase(Locale.ROOT);
    for (int i = 0; i < paramString.length; i++)
    {
      paramArrayOfString = paramString[i];
      if (paramArrayOfString == null) {
        paramArrayOfString = "";
      } else {
        paramArrayOfString = paramArrayOfString.toLowerCase(Locale.ROOT);
      }
      arrayOfString[(i + 1)] = paramArrayOfString;
    }
    boolean bool1 = false;
    paramPattern = paramPattern.matcher(paramSpannable);
    while (paramPattern.find())
    {
      i = paramPattern.start();
      int j = paramPattern.end();
      paramString = paramPattern.group(0);
      boolean bool2 = true;
      if (paramMatchFilter != null) {
        bool2 = paramMatchFilter.acceptMatch(paramSpannable, i, j);
      }
      boolean bool3 = bool1;
      if (bool2)
      {
        bool3 = bool1;
        if (paramString != null)
        {
          paramString = makeUrl(paramString, arrayOfString, paramPattern, paramTransformFilter);
          Log5ECF72.a(paramString);
          LogE84000.a(paramString);
          Log229316.a(paramString);
          applyLink(paramString, i, j, paramSpannable);
          bool3 = true;
        }
      }
      bool1 = bool3;
    }
    return bool1;
  }
  
  public static boolean addLinks(TextView paramTextView, int paramInt)
  {
    if (shouldAddLinksFallbackToFramework()) {
      return Linkify.addLinks(paramTextView, paramInt);
    }
    if (paramInt == 0) {
      return false;
    }
    Object localObject = paramTextView.getText();
    if ((localObject instanceof Spannable))
    {
      if (addLinks((Spannable)localObject, paramInt))
      {
        addLinkMovementMethod(paramTextView);
        return true;
      }
    }
    else
    {
      localObject = SpannableString.valueOf((CharSequence)localObject);
      if (addLinks((Spannable)localObject, paramInt))
      {
        addLinkMovementMethod(paramTextView);
        paramTextView.setText((CharSequence)localObject);
        return true;
      }
    }
    return false;
  }
  
  private static void applyLink(String paramString, int paramInt1, int paramInt2, Spannable paramSpannable)
  {
    paramSpannable.setSpan(new URLSpan(paramString), paramInt1, paramInt2, 33);
  }
  
  private static String findAddress(String paramString)
  {
    if (Build.VERSION.SDK_INT >= 28)
    {
      paramString = WebView.findAddress(paramString);
      Log5ECF72.a(paramString);
      LogE84000.a(paramString);
      Log229316.a(paramString);
      return paramString;
    }
    paramString = FindAddress.findAddress(paramString);
    Log5ECF72.a(paramString);
    LogE84000.a(paramString);
    Log229316.a(paramString);
    return paramString;
  }
  
  private static void gatherLinks(ArrayList<LinkSpec> paramArrayList, Spannable paramSpannable, Pattern paramPattern, String[] paramArrayOfString, Linkify.MatchFilter paramMatchFilter, Linkify.TransformFilter paramTransformFilter)
  {
    paramPattern = paramPattern.matcher(paramSpannable);
    while (paramPattern.find())
    {
      int i = paramPattern.start();
      int j = paramPattern.end();
      String str = paramPattern.group(0);
      if (((paramMatchFilter == null) || (paramMatchFilter.acceptMatch(paramSpannable, i, j))) && (str != null))
      {
        LinkSpec localLinkSpec = new LinkSpec();
        str = makeUrl(str, paramArrayOfString, paramPattern, paramTransformFilter);
        Log5ECF72.a(str);
        LogE84000.a(str);
        Log229316.a(str);
        localLinkSpec.url = str;
        localLinkSpec.start = i;
        localLinkSpec.end = j;
        paramArrayList.add(localLinkSpec);
      }
    }
  }
  
  private static void gatherMapLinks(ArrayList<LinkSpec> paramArrayList, Spannable paramSpannable)
  {
    paramSpannable = paramSpannable.toString();
    int i = 0;
    try
    {
      for (;;)
      {
        Object localObject = findAddress(paramSpannable);
        Log5ECF72.a(localObject);
        LogE84000.a(localObject);
        Log229316.a(localObject);
        if (localObject == null) {
          break;
        }
        int j = paramSpannable.indexOf((String)localObject);
        if (j < 0) {
          break;
        }
        LinkSpec localLinkSpec = new androidx/core/text/util/LinkifyCompat$LinkSpec;
        localLinkSpec.<init>();
        int k = j + ((String)localObject).length();
        localLinkSpec.start = (i + j);
        localLinkSpec.end = (i + k);
        paramSpannable = paramSpannable.substring(k);
        i += k;
        try
        {
          String str = URLEncoder.encode((String)localObject, "UTF-8");
          Log5ECF72.a(str);
          LogE84000.a(str);
          Log229316.a(str);
          localObject = new java/lang/StringBuilder;
          ((StringBuilder)localObject).<init>();
          localLinkSpec.url = ("geo:0,0?q=" + str);
          paramArrayList.add(localLinkSpec);
        }
        catch (UnsupportedEncodingException localUnsupportedEncodingException) {}
      }
    }
    catch (UnsupportedOperationException paramArrayList) {}
  }
  
  private static String makeUrl(String paramString, String[] paramArrayOfString, Matcher paramMatcher, Linkify.TransformFilter paramTransformFilter)
  {
    String str = paramString;
    if (paramTransformFilter != null) {
      str = paramTransformFilter.transformUrl(paramMatcher, paramString);
    }
    int k = 0;
    int m = paramArrayOfString.length;
    int i;
    for (int j = 0;; j++)
    {
      i = k;
      paramString = str;
      if (j >= m) {
        break;
      }
      paramMatcher = paramArrayOfString[j];
      if (str.regionMatches(true, 0, paramMatcher, 0, paramMatcher.length()))
      {
        j = 1;
        i = j;
        paramString = str;
        if (str.regionMatches(false, 0, paramMatcher, 0, paramMatcher.length())) {
          break;
        }
        paramString = paramMatcher + str.substring(paramMatcher.length());
        i = j;
        break;
      }
    }
    paramMatcher = paramString;
    if (i == 0)
    {
      paramMatcher = paramString;
      if (paramArrayOfString.length > 0) {
        paramMatcher = paramArrayOfString[0] + paramString;
      }
    }
    return paramMatcher;
  }
  
  private static void pruneOverlaps(ArrayList<LinkSpec> paramArrayList, Spannable paramSpannable)
  {
    int j = paramSpannable.length();
    int i = 0;
    Object localObject1 = (URLSpan[])paramSpannable.getSpans(0, j, URLSpan.class);
    j = localObject1.length;
    Object localObject2;
    while (i < j)
    {
      localObject2 = localObject1[i];
      LinkSpec localLinkSpec = new LinkSpec();
      localLinkSpec.frameworkAddedSpan = ((URLSpan)localObject2);
      localLinkSpec.start = paramSpannable.getSpanStart(localObject2);
      localLinkSpec.end = paramSpannable.getSpanEnd(localObject2);
      paramArrayList.add(localLinkSpec);
      i++;
    }
    Collections.sort(paramArrayList, COMPARATOR);
    int k = paramArrayList.size();
    j = 0;
    while (j < k - 1)
    {
      localObject2 = (LinkSpec)paramArrayList.get(j);
      localObject1 = (LinkSpec)paramArrayList.get(j + 1);
      i = -1;
      if ((((LinkSpec)localObject2).start <= ((LinkSpec)localObject1).start) && (((LinkSpec)localObject2).end > ((LinkSpec)localObject1).start))
      {
        if (((LinkSpec)localObject1).end <= ((LinkSpec)localObject2).end) {
          i = j + 1;
        } else if (((LinkSpec)localObject2).end - ((LinkSpec)localObject2).start > ((LinkSpec)localObject1).end - ((LinkSpec)localObject1).start) {
          i = j + 1;
        } else if (((LinkSpec)localObject2).end - ((LinkSpec)localObject2).start < ((LinkSpec)localObject1).end - ((LinkSpec)localObject1).start) {
          i = j;
        }
        if (i != -1)
        {
          localObject1 = ((LinkSpec)paramArrayList.get(i)).frameworkAddedSpan;
          if (localObject1 != null) {
            paramSpannable.removeSpan(localObject1);
          }
          paramArrayList.remove(i);
          k--;
          continue;
        }
      }
      j++;
    }
  }
  
  private static boolean shouldAddLinksFallbackToFramework()
  {
    boolean bool;
    if (Build.VERSION.SDK_INT >= 28) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  static class Api24Impl
  {
    static void addLinks(TextView paramTextView, Pattern paramPattern, String paramString, String[] paramArrayOfString, Linkify.MatchFilter paramMatchFilter, Linkify.TransformFilter paramTransformFilter)
    {
      Linkify.addLinks(paramTextView, paramPattern, paramString, paramArrayOfString, paramMatchFilter, paramTransformFilter);
    }
    
    static boolean addLinks(Spannable paramSpannable, Pattern paramPattern, String paramString, String[] paramArrayOfString, Linkify.MatchFilter paramMatchFilter, Linkify.TransformFilter paramTransformFilter)
    {
      return Linkify.addLinks(paramSpannable, paramPattern, paramString, paramArrayOfString, paramMatchFilter, paramTransformFilter);
    }
  }
  
  private static class LinkSpec
  {
    int end;
    URLSpan frameworkAddedSpan;
    int start;
    String url;
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface LinkifyMask {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/text/util/LinkifyCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */