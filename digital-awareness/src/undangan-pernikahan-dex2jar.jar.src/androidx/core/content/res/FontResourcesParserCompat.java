package androidx.core.content.res;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.util.Base64;
import android.util.TypedValue;
import android.util.Xml;
import androidx.core.R.styleable;
import androidx.core.provider.FontRequest;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class FontResourcesParserCompat
{
  private static final int DEFAULT_TIMEOUT_MILLIS = 500;
  public static final int FETCH_STRATEGY_ASYNC = 1;
  public static final int FETCH_STRATEGY_BLOCKING = 0;
  public static final int INFINITE_TIMEOUT_VALUE = -1;
  private static final int ITALIC = 1;
  private static final int NORMAL_WEIGHT = 400;
  
  private static int getType(TypedArray paramTypedArray, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return Api21Impl.getType(paramTypedArray, paramInt);
    }
    TypedValue localTypedValue = new TypedValue();
    paramTypedArray.getValue(paramInt, localTypedValue);
    return localTypedValue.type;
  }
  
  public static FamilyResourceEntry parse(XmlPullParser paramXmlPullParser, Resources paramResources)
    throws XmlPullParserException, IOException
  {
    int i;
    do
    {
      i = paramXmlPullParser.next();
    } while ((i != 2) && (i != 1));
    if (i == 2) {
      return readFamilies(paramXmlPullParser, paramResources);
    }
    throw new XmlPullParserException("No start tag found");
  }
  
  public static List<List<byte[]>> readCerts(Resources paramResources, int paramInt)
  {
    if (paramInt == 0) {
      return Collections.emptyList();
    }
    TypedArray localTypedArray = paramResources.obtainTypedArray(paramInt);
    try
    {
      if (localTypedArray.length() == 0)
      {
        paramResources = Collections.emptyList();
        return paramResources;
      }
      ArrayList localArrayList = new java/util/ArrayList;
      localArrayList.<init>();
      if (getType(localTypedArray, 0) == 1) {
        for (paramInt = 0; paramInt < localTypedArray.length(); paramInt++)
        {
          int i = localTypedArray.getResourceId(paramInt, 0);
          if (i != 0) {
            localArrayList.add(toByteArrayList(paramResources.getStringArray(i)));
          }
        }
      } else {
        localArrayList.add(toByteArrayList(paramResources.getStringArray(paramInt)));
      }
      return localArrayList;
    }
    finally
    {
      localTypedArray.recycle();
    }
  }
  
  private static FamilyResourceEntry readFamilies(XmlPullParser paramXmlPullParser, Resources paramResources)
    throws XmlPullParserException, IOException
  {
    paramXmlPullParser.require(2, null, "font-family");
    if (paramXmlPullParser.getName().equals("font-family")) {
      return readFamily(paramXmlPullParser, paramResources);
    }
    skip(paramXmlPullParser);
    return null;
  }
  
  private static FamilyResourceEntry readFamily(XmlPullParser paramXmlPullParser, Resources paramResources)
    throws XmlPullParserException, IOException
  {
    TypedArray localTypedArray = paramResources.obtainAttributes(Xml.asAttributeSet(paramXmlPullParser), R.styleable.FontFamily);
    String str2 = localTypedArray.getString(R.styleable.FontFamily_fontProviderAuthority);
    Object localObject = localTypedArray.getString(R.styleable.FontFamily_fontProviderPackage);
    String str3 = localTypedArray.getString(R.styleable.FontFamily_fontProviderQuery);
    int j = localTypedArray.getResourceId(R.styleable.FontFamily_fontProviderCerts, 0);
    int i = localTypedArray.getInteger(R.styleable.FontFamily_fontProviderFetchStrategy, 1);
    int k = localTypedArray.getInteger(R.styleable.FontFamily_fontProviderFetchTimeout, 500);
    String str1 = localTypedArray.getString(R.styleable.FontFamily_fontProviderSystemFontFamily);
    localTypedArray.recycle();
    if ((str2 != null) && (localObject != null) && (str3 != null))
    {
      while (paramXmlPullParser.next() != 3) {
        skip(paramXmlPullParser);
      }
      return new ProviderResourceEntry(new FontRequest(str2, (String)localObject, str3, readCerts(paramResources, j)), i, k, str1);
    }
    localObject = new ArrayList();
    while (paramXmlPullParser.next() != 3) {
      if (paramXmlPullParser.getEventType() == 2) {
        if (paramXmlPullParser.getName().equals("font")) {
          ((List)localObject).add(readFont(paramXmlPullParser, paramResources));
        } else {
          skip(paramXmlPullParser);
        }
      }
    }
    if (((List)localObject).isEmpty()) {
      return null;
    }
    return new FontFamilyFilesResourceEntry((FontFileResourceEntry[])((List)localObject).toArray(new FontFileResourceEntry[0]));
  }
  
  private static FontFileResourceEntry readFont(XmlPullParser paramXmlPullParser, Resources paramResources)
    throws XmlPullParserException, IOException
  {
    paramResources = paramResources.obtainAttributes(Xml.asAttributeSet(paramXmlPullParser), R.styleable.FontFamilyFont);
    int i;
    if (paramResources.hasValue(R.styleable.FontFamilyFont_fontWeight)) {
      i = R.styleable.FontFamilyFont_fontWeight;
    } else {
      i = R.styleable.FontFamilyFont_android_fontWeight;
    }
    int k = paramResources.getInt(i, 400);
    if (paramResources.hasValue(R.styleable.FontFamilyFont_fontStyle)) {
      i = R.styleable.FontFamilyFont_fontStyle;
    } else {
      i = R.styleable.FontFamilyFont_android_fontStyle;
    }
    boolean bool;
    if (1 == paramResources.getInt(i, 0)) {
      bool = true;
    } else {
      bool = false;
    }
    if (paramResources.hasValue(R.styleable.FontFamilyFont_ttcIndex)) {
      i = R.styleable.FontFamilyFont_ttcIndex;
    } else {
      i = R.styleable.FontFamilyFont_android_ttcIndex;
    }
    if (paramResources.hasValue(R.styleable.FontFamilyFont_fontVariationSettings)) {
      j = R.styleable.FontFamilyFont_fontVariationSettings;
    } else {
      j = R.styleable.FontFamilyFont_android_fontVariationSettings;
    }
    String str2 = paramResources.getString(j);
    int j = paramResources.getInt(i, 0);
    if (paramResources.hasValue(R.styleable.FontFamilyFont_font)) {
      i = R.styleable.FontFamilyFont_font;
    } else {
      i = R.styleable.FontFamilyFont_android_font;
    }
    int m = paramResources.getResourceId(i, 0);
    String str1 = paramResources.getString(i);
    paramResources.recycle();
    while (paramXmlPullParser.next() != 3) {
      skip(paramXmlPullParser);
    }
    return new FontFileResourceEntry(str1, k, bool, str2, j, m);
  }
  
  private static void skip(XmlPullParser paramXmlPullParser)
    throws XmlPullParserException, IOException
  {
    int i = 1;
    while (i > 0) {
      switch (paramXmlPullParser.next())
      {
      default: 
        break;
      case 3: 
        i--;
        break;
      case 2: 
        i++;
      }
    }
  }
  
  private static List<byte[]> toByteArrayList(String[] paramArrayOfString)
  {
    ArrayList localArrayList = new ArrayList();
    int j = paramArrayOfString.length;
    for (int i = 0; i < j; i++) {
      localArrayList.add(Base64.decode(paramArrayOfString[i], 0));
    }
    return localArrayList;
  }
  
  static class Api21Impl
  {
    static int getType(TypedArray paramTypedArray, int paramInt)
    {
      return paramTypedArray.getType(paramInt);
    }
  }
  
  public static abstract interface FamilyResourceEntry {}
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface FetchStrategy {}
  
  public static final class FontFamilyFilesResourceEntry
    implements FontResourcesParserCompat.FamilyResourceEntry
  {
    private final FontResourcesParserCompat.FontFileResourceEntry[] mEntries;
    
    public FontFamilyFilesResourceEntry(FontResourcesParserCompat.FontFileResourceEntry[] paramArrayOfFontFileResourceEntry)
    {
      this.mEntries = paramArrayOfFontFileResourceEntry;
    }
    
    public FontResourcesParserCompat.FontFileResourceEntry[] getEntries()
    {
      return this.mEntries;
    }
  }
  
  public static final class FontFileResourceEntry
  {
    private final String mFileName;
    private final boolean mItalic;
    private final int mResourceId;
    private final int mTtcIndex;
    private final String mVariationSettings;
    private final int mWeight;
    
    public FontFileResourceEntry(String paramString1, int paramInt1, boolean paramBoolean, String paramString2, int paramInt2, int paramInt3)
    {
      this.mFileName = paramString1;
      this.mWeight = paramInt1;
      this.mItalic = paramBoolean;
      this.mVariationSettings = paramString2;
      this.mTtcIndex = paramInt2;
      this.mResourceId = paramInt3;
    }
    
    public String getFileName()
    {
      return this.mFileName;
    }
    
    public int getResourceId()
    {
      return this.mResourceId;
    }
    
    public int getTtcIndex()
    {
      return this.mTtcIndex;
    }
    
    public String getVariationSettings()
    {
      return this.mVariationSettings;
    }
    
    public int getWeight()
    {
      return this.mWeight;
    }
    
    public boolean isItalic()
    {
      return this.mItalic;
    }
  }
  
  public static final class ProviderResourceEntry
    implements FontResourcesParserCompat.FamilyResourceEntry
  {
    private final FontRequest mRequest;
    private final int mStrategy;
    private final String mSystemFontFamilyName;
    private final int mTimeoutMs;
    
    public ProviderResourceEntry(FontRequest paramFontRequest, int paramInt1, int paramInt2)
    {
      this(paramFontRequest, paramInt1, paramInt2, null);
    }
    
    public ProviderResourceEntry(FontRequest paramFontRequest, int paramInt1, int paramInt2, String paramString)
    {
      this.mRequest = paramFontRequest;
      this.mStrategy = paramInt1;
      this.mTimeoutMs = paramInt2;
      this.mSystemFontFamilyName = paramString;
    }
    
    public int getFetchStrategy()
    {
      return this.mStrategy;
    }
    
    public FontRequest getRequest()
    {
      return this.mRequest;
    }
    
    public String getSystemFontFamilyName()
    {
      return this.mSystemFontFamilyName;
    }
    
    public int getTimeout()
    {
      return this.mTimeoutMs;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/content/res/FontResourcesParserCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */