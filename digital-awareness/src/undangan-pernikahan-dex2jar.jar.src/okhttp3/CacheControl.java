package okhttp3;

import java.util.concurrent.TimeUnit;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.internal.Util;

@Metadata(bv={1, 0, 3}, d1={"\000\"\n\002\030\002\n\002\020\000\n\000\n\002\020\013\n\002\b\002\n\002\020\b\n\002\b\n\n\002\020\016\n\002\b\021\030\000 !2\0020\001:\002 !Bq\b\002\022\006\020\002\032\0020\003\022\006\020\004\032\0020\003\022\006\020\005\032\0020\006\022\006\020\007\032\0020\006\022\006\020\b\032\0020\003\022\006\020\t\032\0020\003\022\006\020\n\032\0020\003\022\006\020\013\032\0020\006\022\006\020\f\032\0020\006\022\006\020\r\032\0020\003\022\006\020\016\032\0020\003\022\006\020\017\032\0020\003\022\b\020\020\032\004\030\0010\021¢\006\002\020\022J\r\020\017\032\0020\003H\007¢\006\002\b\025J\r\020\005\032\0020\006H\007¢\006\002\b\026J\r\020\013\032\0020\006H\007¢\006\002\b\027J\r\020\f\032\0020\006H\007¢\006\002\b\030J\r\020\n\032\0020\003H\007¢\006\002\b\031J\r\020\002\032\0020\003H\007¢\006\002\b\032J\r\020\004\032\0020\003H\007¢\006\002\b\033J\r\020\016\032\0020\003H\007¢\006\002\b\034J\r\020\r\032\0020\003H\007¢\006\002\b\035J\r\020\007\032\0020\006H\007¢\006\002\b\036J\b\020\037\032\0020\021H\026R\020\020\020\032\004\030\0010\021X\016¢\006\002\n\000R\023\020\017\032\0020\0038\007¢\006\b\n\000\032\004\b\017\020\023R\021\020\b\032\0020\003¢\006\b\n\000\032\004\b\b\020\023R\021\020\t\032\0020\003¢\006\b\n\000\032\004\b\t\020\023R\023\020\005\032\0020\0068\007¢\006\b\n\000\032\004\b\005\020\024R\023\020\013\032\0020\0068\007¢\006\b\n\000\032\004\b\013\020\024R\023\020\f\032\0020\0068\007¢\006\b\n\000\032\004\b\f\020\024R\023\020\n\032\0020\0038\007¢\006\b\n\000\032\004\b\n\020\023R\023\020\002\032\0020\0038\007¢\006\b\n\000\032\004\b\002\020\023R\023\020\004\032\0020\0038\007¢\006\b\n\000\032\004\b\004\020\023R\023\020\016\032\0020\0038\007¢\006\b\n\000\032\004\b\016\020\023R\023\020\r\032\0020\0038\007¢\006\b\n\000\032\004\b\r\020\023R\023\020\007\032\0020\0068\007¢\006\b\n\000\032\004\b\007\020\024¨\006\""}, d2={"Lokhttp3/CacheControl;", "", "noCache", "", "noStore", "maxAgeSeconds", "", "sMaxAgeSeconds", "isPrivate", "isPublic", "mustRevalidate", "maxStaleSeconds", "minFreshSeconds", "onlyIfCached", "noTransform", "immutable", "headerValue", "", "(ZZIIZZZIIZZZLjava/lang/String;)V", "()Z", "()I", "-deprecated_immutable", "-deprecated_maxAgeSeconds", "-deprecated_maxStaleSeconds", "-deprecated_minFreshSeconds", "-deprecated_mustRevalidate", "-deprecated_noCache", "-deprecated_noStore", "-deprecated_noTransform", "-deprecated_onlyIfCached", "-deprecated_sMaxAgeSeconds", "toString", "Builder", "Companion", "okhttp"}, k=1, mv={1, 4, 0})
public final class CacheControl
{
  public static final Companion Companion = new Companion(null);
  public static final CacheControl FORCE_CACHE = new Builder().onlyIfCached().maxStale(Integer.MAX_VALUE, TimeUnit.SECONDS).build();
  public static final CacheControl FORCE_NETWORK = new Builder().noCache().build();
  private String headerValue;
  private final boolean immutable;
  private final boolean isPrivate;
  private final boolean isPublic;
  private final int maxAgeSeconds;
  private final int maxStaleSeconds;
  private final int minFreshSeconds;
  private final boolean mustRevalidate;
  private final boolean noCache;
  private final boolean noStore;
  private final boolean noTransform;
  private final boolean onlyIfCached;
  private final int sMaxAgeSeconds;
  
  private CacheControl(boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, int paramInt3, int paramInt4, boolean paramBoolean6, boolean paramBoolean7, boolean paramBoolean8, String paramString)
  {
    this.noCache = paramBoolean1;
    this.noStore = paramBoolean2;
    this.maxAgeSeconds = paramInt1;
    this.sMaxAgeSeconds = paramInt2;
    this.isPrivate = paramBoolean3;
    this.isPublic = paramBoolean4;
    this.mustRevalidate = paramBoolean5;
    this.maxStaleSeconds = paramInt3;
    this.minFreshSeconds = paramInt4;
    this.onlyIfCached = paramBoolean6;
    this.noTransform = paramBoolean7;
    this.immutable = paramBoolean8;
    this.headerValue = paramString;
  }
  
  @JvmStatic
  public static final CacheControl parse(Headers paramHeaders)
  {
    return Companion.parse(paramHeaders);
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="immutable", imports={}))
  public final boolean -deprecated_immutable()
  {
    return this.immutable;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="maxAgeSeconds", imports={}))
  public final int -deprecated_maxAgeSeconds()
  {
    return this.maxAgeSeconds;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="maxStaleSeconds", imports={}))
  public final int -deprecated_maxStaleSeconds()
  {
    return this.maxStaleSeconds;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="minFreshSeconds", imports={}))
  public final int -deprecated_minFreshSeconds()
  {
    return this.minFreshSeconds;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="mustRevalidate", imports={}))
  public final boolean -deprecated_mustRevalidate()
  {
    return this.mustRevalidate;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="noCache", imports={}))
  public final boolean -deprecated_noCache()
  {
    return this.noCache;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="noStore", imports={}))
  public final boolean -deprecated_noStore()
  {
    return this.noStore;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="noTransform", imports={}))
  public final boolean -deprecated_noTransform()
  {
    return this.noTransform;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="onlyIfCached", imports={}))
  public final boolean -deprecated_onlyIfCached()
  {
    return this.onlyIfCached;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="sMaxAgeSeconds", imports={}))
  public final int -deprecated_sMaxAgeSeconds()
  {
    return this.sMaxAgeSeconds;
  }
  
  public final boolean immutable()
  {
    return this.immutable;
  }
  
  public final boolean isPrivate()
  {
    return this.isPrivate;
  }
  
  public final boolean isPublic()
  {
    return this.isPublic;
  }
  
  public final int maxAgeSeconds()
  {
    return this.maxAgeSeconds;
  }
  
  public final int maxStaleSeconds()
  {
    return this.maxStaleSeconds;
  }
  
  public final int minFreshSeconds()
  {
    return this.minFreshSeconds;
  }
  
  public final boolean mustRevalidate()
  {
    return this.mustRevalidate;
  }
  
  public final boolean noCache()
  {
    return this.noCache;
  }
  
  public final boolean noStore()
  {
    return this.noStore;
  }
  
  public final boolean noTransform()
  {
    return this.noTransform;
  }
  
  public final boolean onlyIfCached()
  {
    return this.onlyIfCached;
  }
  
  public final int sMaxAgeSeconds()
  {
    return this.sMaxAgeSeconds;
  }
  
  public String toString()
  {
    String str = this.headerValue;
    Object localObject = str;
    if (str == null)
    {
      localObject = new StringBuilder();
      if (this.noCache) {
        ((StringBuilder)localObject).append("no-cache, ");
      }
      if (this.noStore) {
        ((StringBuilder)localObject).append("no-store, ");
      }
      if (this.maxAgeSeconds != -1) {
        ((StringBuilder)localObject).append("max-age=").append(this.maxAgeSeconds).append(", ");
      }
      if (this.sMaxAgeSeconds != -1) {
        ((StringBuilder)localObject).append("s-maxage=").append(this.sMaxAgeSeconds).append(", ");
      }
      if (this.isPrivate) {
        ((StringBuilder)localObject).append("private, ");
      }
      if (this.isPublic) {
        ((StringBuilder)localObject).append("public, ");
      }
      if (this.mustRevalidate) {
        ((StringBuilder)localObject).append("must-revalidate, ");
      }
      if (this.maxStaleSeconds != -1) {
        ((StringBuilder)localObject).append("max-stale=").append(this.maxStaleSeconds).append(", ");
      }
      if (this.minFreshSeconds != -1) {
        ((StringBuilder)localObject).append("min-fresh=").append(this.minFreshSeconds).append(", ");
      }
      if (this.onlyIfCached) {
        ((StringBuilder)localObject).append("only-if-cached, ");
      }
      if (this.noTransform) {
        ((StringBuilder)localObject).append("no-transform, ");
      }
      if (this.immutable) {
        ((StringBuilder)localObject).append("immutable, ");
      }
      int i;
      if (((CharSequence)localObject).length() == 0) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0) {
        return "";
      }
      ((StringBuilder)localObject).delete(((StringBuilder)localObject).length() - 2, ((StringBuilder)localObject).length());
      localObject = ((StringBuilder)localObject).toString();
      Intrinsics.checkNotNullExpressionValue(localObject, "StringBuilder().apply(builderAction).toString()");
      this.headerValue = ((String)localObject);
    }
    return (String)localObject;
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\0000\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\013\n\000\n\002\020\b\n\002\b\007\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\020\t\n\000\030\0002\0020\001B\005¢\006\002\020\002J\006\020\r\032\0020\016J\006\020\003\032\0020\000J\026\020\017\032\0020\0002\006\020\017\032\0020\0062\006\020\020\032\0020\021J\026\020\022\032\0020\0002\006\020\022\032\0020\0062\006\020\020\032\0020\021J\026\020\023\032\0020\0002\006\020\023\032\0020\0062\006\020\020\032\0020\021J\006\020\t\032\0020\000J\006\020\n\032\0020\000J\006\020\013\032\0020\000J\006\020\f\032\0020\000J\f\020\024\032\0020\006*\0020\025H\002R\016\020\003\032\0020\004X\016¢\006\002\n\000R\016\020\005\032\0020\006X\016¢\006\002\n\000R\016\020\007\032\0020\006X\016¢\006\002\n\000R\016\020\b\032\0020\006X\016¢\006\002\n\000R\016\020\t\032\0020\004X\016¢\006\002\n\000R\016\020\n\032\0020\004X\016¢\006\002\n\000R\016\020\013\032\0020\004X\016¢\006\002\n\000R\016\020\f\032\0020\004X\016¢\006\002\n\000¨\006\026"}, d2={"Lokhttp3/CacheControl$Builder;", "", "()V", "immutable", "", "maxAgeSeconds", "", "maxStaleSeconds", "minFreshSeconds", "noCache", "noStore", "noTransform", "onlyIfCached", "build", "Lokhttp3/CacheControl;", "maxAge", "timeUnit", "Ljava/util/concurrent/TimeUnit;", "maxStale", "minFresh", "clampToInt", "", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Builder
  {
    private boolean immutable;
    private int maxAgeSeconds = -1;
    private int maxStaleSeconds = -1;
    private int minFreshSeconds = -1;
    private boolean noCache;
    private boolean noStore;
    private boolean noTransform;
    private boolean onlyIfCached;
    
    private final int clampToInt(long paramLong)
    {
      int i = Integer.MAX_VALUE;
      if (paramLong <= Integer.MAX_VALUE) {
        i = (int)paramLong;
      }
      return i;
    }
    
    public final CacheControl build()
    {
      return new CacheControl(this.noCache, this.noStore, this.maxAgeSeconds, -1, false, false, false, this.maxStaleSeconds, this.minFreshSeconds, this.onlyIfCached, this.noTransform, this.immutable, null, null);
    }
    
    public final Builder immutable()
    {
      ((Builder)this).immutable = true;
      return (Builder)this;
    }
    
    public final Builder maxAge(int paramInt, TimeUnit paramTimeUnit)
    {
      Intrinsics.checkNotNullParameter(paramTimeUnit, "timeUnit");
      Builder localBuilder = (Builder)this;
      int i;
      if (paramInt >= 0) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        localBuilder.maxAgeSeconds = localBuilder.clampToInt(paramTimeUnit.toSeconds(paramInt));
        return (Builder)this;
      }
      throw ((Throwable)new IllegalArgumentException(("maxAge < 0: " + paramInt).toString()));
    }
    
    public final Builder maxStale(int paramInt, TimeUnit paramTimeUnit)
    {
      Intrinsics.checkNotNullParameter(paramTimeUnit, "timeUnit");
      Builder localBuilder = (Builder)this;
      int i;
      if (paramInt >= 0) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        localBuilder.maxStaleSeconds = localBuilder.clampToInt(paramTimeUnit.toSeconds(paramInt));
        return (Builder)this;
      }
      throw ((Throwable)new IllegalArgumentException(("maxStale < 0: " + paramInt).toString()));
    }
    
    public final Builder minFresh(int paramInt, TimeUnit paramTimeUnit)
    {
      Intrinsics.checkNotNullParameter(paramTimeUnit, "timeUnit");
      Builder localBuilder = (Builder)this;
      int i;
      if (paramInt >= 0) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        localBuilder.minFreshSeconds = localBuilder.clampToInt(paramTimeUnit.toSeconds(paramInt));
        return (Builder)this;
      }
      throw ((Throwable)new IllegalArgumentException(("minFresh < 0: " + paramInt).toString()));
    }
    
    public final Builder noCache()
    {
      ((Builder)this).noCache = true;
      return (Builder)this;
    }
    
    public final Builder noStore()
    {
      ((Builder)this).noStore = true;
      return (Builder)this;
    }
    
    public final Builder noTransform()
    {
      ((Builder)this).noTransform = true;
      return (Builder)this;
    }
    
    public final Builder onlyIfCached()
    {
      ((Builder)this).onlyIfCached = true;
      return (Builder)this;
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000&\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\020\b\n\002\020\016\n\002\b\003\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J\020\020\006\032\0020\0042\006\020\007\032\0020\bH\007J\036\020\t\032\0020\n*\0020\0132\006\020\f\032\0020\0132\b\b\002\020\r\032\0020\nH\002R\020\020\003\032\0020\0048\006X\004¢\006\002\n\000R\020\020\005\032\0020\0048\006X\004¢\006\002\n\000¨\006\016"}, d2={"Lokhttp3/CacheControl$Companion;", "", "()V", "FORCE_CACHE", "Lokhttp3/CacheControl;", "FORCE_NETWORK", "parse", "headers", "Lokhttp3/Headers;", "indexOfElement", "", "", "characters", "startIndex", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Companion
  {
    private final int indexOfElement(String paramString1, String paramString2, int paramInt)
    {
      int i = paramString1.length();
      while (paramInt < i)
      {
        if (StringsKt.contains$default((CharSequence)paramString2, paramString1.charAt(paramInt), false, 2, null)) {
          return paramInt;
        }
        paramInt++;
      }
      return paramString1.length();
    }
    
    @JvmStatic
    public final CacheControl parse(Headers paramHeaders)
    {
      Intrinsics.checkNotNullParameter(paramHeaders, "headers");
      boolean bool4 = false;
      boolean bool3 = false;
      int j = -1;
      int i = -1;
      boolean bool5 = false;
      boolean bool2 = false;
      boolean bool1 = false;
      int i1 = -1;
      int n = -1;
      boolean bool7 = false;
      boolean bool8 = false;
      int k = 1;
      Object localObject = (String)null;
      int i2 = paramHeaders.size();
      boolean bool6 = false;
      for (int i3 = 0; i3 < i2; i3++)
      {
        String str1 = paramHeaders.name(i3);
        String str2 = paramHeaders.value(i3);
        if (StringsKt.equals(str1, "Cache-Control", true))
        {
          if (localObject != null) {
            k = 0;
          } else {
            localObject = str2;
          }
        }
        else
        {
          if (!StringsKt.equals(str1, "Pragma", true)) {
            continue;
          }
          k = 0;
        }
        int m = 0;
        while (m < str2.length())
        {
          int i4 = ((Companion)this).indexOfElement(str2, "=,;", m);
          if (str2 != null)
          {
            str1 = str2.substring(m, i4);
            Intrinsics.checkNotNullExpressionValue(str1, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            if (str1 != null)
            {
              String str3 = StringsKt.trim((CharSequence)str1).toString();
              if (i4 != str2.length()) {
                if (str2.charAt(i4) != ',')
                {
                  if (str2.charAt(i4) != ';')
                  {
                    i4 = Util.indexOfNonWhitespace(str2, i4 + 1);
                    if ((i4 < str2.length()) && (str2.charAt(i4) == '"'))
                    {
                      m = i4 + 1;
                      i4 = StringsKt.indexOf$default((CharSequence)str2, '"', m, false, 4, null);
                      if (str2 != null)
                      {
                        str1 = str2.substring(m, i4);
                        Intrinsics.checkNotNullExpressionValue(str1, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                        m = i4 + 1;
                        break label422;
                      }
                      throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                    }
                    m = ((Companion)this).indexOfElement(str2, ",;", i4);
                    if (str2 != null)
                    {
                      str1 = str2.substring(i4, m);
                      Intrinsics.checkNotNullExpressionValue(str1, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                      if (str1 != null)
                      {
                        str1 = StringsKt.trim((CharSequence)str1).toString();
                        break label422;
                      }
                      throw new NullPointerException("null cannot be cast to non-null type kotlin.CharSequence");
                    }
                    throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                  }
                }
                else {}
              }
              m = i4 + 1;
              str1 = (String)null;
              label422:
              if (StringsKt.equals("no-cache", str3, true)) {
                bool4 = true;
              } else if (StringsKt.equals("no-store", str3, true)) {
                bool3 = true;
              } else if (StringsKt.equals("max-age", str3, true)) {
                j = Util.toNonNegativeInt(str1, -1);
              } else if (StringsKt.equals("s-maxage", str3, true)) {
                i = Util.toNonNegativeInt(str1, -1);
              } else if (StringsKt.equals("private", str3, true)) {
                bool5 = true;
              } else if (StringsKt.equals("public", str3, true)) {
                bool2 = true;
              } else if (StringsKt.equals("must-revalidate", str3, true)) {
                bool1 = true;
              } else if (StringsKt.equals("max-stale", str3, true)) {
                i1 = Util.toNonNegativeInt(str1, Integer.MAX_VALUE);
              } else if (StringsKt.equals("min-fresh", str3, true)) {
                n = Util.toNonNegativeInt(str1, -1);
              } else if (StringsKt.equals("only-if-cached", str3, true)) {
                bool7 = true;
              } else if (StringsKt.equals("no-transform", str3, true)) {
                bool8 = true;
              } else if (StringsKt.equals("immutable", str3, true)) {
                bool6 = true;
              }
            }
            else
            {
              throw new NullPointerException("null cannot be cast to non-null type kotlin.CharSequence");
            }
          }
          else
          {
            throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
          }
        }
      }
      if (k == 0) {
        localObject = (String)null;
      }
      return new CacheControl(bool4, bool3, j, i, bool5, bool2, bool1, i1, n, bool7, bool8, bool6, (String)localObject, null);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/CacheControl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */