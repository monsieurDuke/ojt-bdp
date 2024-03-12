package androidx.core.os;

import android.os.Build.VERSION;
import android.os.LocaleList;
import java.util.Locale;

public final class LocaleListCompat
{
  private static final LocaleListCompat sEmptyLocaleList = create(new Locale[0]);
  private final LocaleListInterface mImpl;
  
  private LocaleListCompat(LocaleListInterface paramLocaleListInterface)
  {
    this.mImpl = paramLocaleListInterface;
  }
  
  public static LocaleListCompat create(Locale... paramVarArgs)
  {
    if (Build.VERSION.SDK_INT >= 24) {
      return wrap(Api24Impl.createLocaleList(paramVarArgs));
    }
    return new LocaleListCompat(new LocaleListCompatWrapper(paramVarArgs));
  }
  
  static Locale forLanguageTagCompat(String paramString)
  {
    String[] arrayOfString;
    if (paramString.contains("-"))
    {
      arrayOfString = paramString.split("-", -1);
      if (arrayOfString.length > 2) {
        return new Locale(arrayOfString[0], arrayOfString[1], arrayOfString[2]);
      }
      if (arrayOfString.length > 1) {
        return new Locale(arrayOfString[0], arrayOfString[1]);
      }
      if (arrayOfString.length == 1) {
        return new Locale(arrayOfString[0]);
      }
    }
    else
    {
      if (!paramString.contains("_")) {
        break label189;
      }
      arrayOfString = paramString.split("_", -1);
      if (arrayOfString.length > 2) {
        return new Locale(arrayOfString[0], arrayOfString[1], arrayOfString[2]);
      }
      if (arrayOfString.length > 1) {
        return new Locale(arrayOfString[0], arrayOfString[1]);
      }
      if (arrayOfString.length == 1) {
        return new Locale(arrayOfString[0]);
      }
    }
    throw new IllegalArgumentException("Can not parse language tag: [" + paramString + "]");
    label189:
    return new Locale(paramString);
  }
  
  public static LocaleListCompat forLanguageTags(String paramString)
  {
    if ((paramString != null) && (!paramString.isEmpty()))
    {
      String[] arrayOfString = paramString.split(",", -1);
      Locale[] arrayOfLocale = new Locale[arrayOfString.length];
      for (int i = 0; i < arrayOfLocale.length; i++)
      {
        if (Build.VERSION.SDK_INT >= 21) {
          paramString = Api21Impl.forLanguageTag(arrayOfString[i]);
        } else {
          paramString = forLanguageTagCompat(arrayOfString[i]);
        }
        arrayOfLocale[i] = paramString;
      }
      return create(arrayOfLocale);
    }
    return getEmptyLocaleList();
  }
  
  public static LocaleListCompat getAdjustedDefault()
  {
    if (Build.VERSION.SDK_INT >= 24) {
      return wrap(Api24Impl.getAdjustedDefault());
    }
    return create(new Locale[] { Locale.getDefault() });
  }
  
  public static LocaleListCompat getDefault()
  {
    if (Build.VERSION.SDK_INT >= 24) {
      return wrap(Api24Impl.getDefault());
    }
    return create(new Locale[] { Locale.getDefault() });
  }
  
  public static LocaleListCompat getEmptyLocaleList()
  {
    return sEmptyLocaleList;
  }
  
  public static LocaleListCompat wrap(LocaleList paramLocaleList)
  {
    return new LocaleListCompat(new LocaleListPlatformWrapper(paramLocaleList));
  }
  
  @Deprecated
  public static LocaleListCompat wrap(Object paramObject)
  {
    return wrap((LocaleList)paramObject);
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool;
    if (((paramObject instanceof LocaleListCompat)) && (this.mImpl.equals(((LocaleListCompat)paramObject).mImpl))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public Locale get(int paramInt)
  {
    return this.mImpl.get(paramInt);
  }
  
  public Locale getFirstMatch(String[] paramArrayOfString)
  {
    return this.mImpl.getFirstMatch(paramArrayOfString);
  }
  
  public int hashCode()
  {
    return this.mImpl.hashCode();
  }
  
  public int indexOf(Locale paramLocale)
  {
    return this.mImpl.indexOf(paramLocale);
  }
  
  public boolean isEmpty()
  {
    return this.mImpl.isEmpty();
  }
  
  public int size()
  {
    return this.mImpl.size();
  }
  
  public String toLanguageTags()
  {
    return this.mImpl.toLanguageTags();
  }
  
  public String toString()
  {
    return this.mImpl.toString();
  }
  
  public Object unwrap()
  {
    return this.mImpl.getLocaleList();
  }
  
  static class Api21Impl
  {
    static Locale forLanguageTag(String paramString)
    {
      return Locale.forLanguageTag(paramString);
    }
  }
  
  static class Api24Impl
  {
    static LocaleList createLocaleList(Locale... paramVarArgs)
    {
      return new LocaleList(paramVarArgs);
    }
    
    static LocaleList getAdjustedDefault()
    {
      return LocaleList.getAdjustedDefault();
    }
    
    static LocaleList getDefault()
    {
      return LocaleList.getDefault();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/os/LocaleListCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */