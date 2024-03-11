package okhttp3;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\0002\n\002\030\002\n\002\020\000\n\000\n\002\020\016\n\002\b\003\n\002\020$\n\002\b\003\n\002\030\002\n\002\b\005\n\002\020\013\n\002\b\002\n\002\020\b\n\002\b\005\030\0002\0020\001B\027\b\026\022\006\020\002\032\0020\003\022\006\020\004\032\0020\003¢\006\002\020\005B#\022\006\020\002\032\0020\003\022\024\020\006\032\020\022\006\022\004\030\0010\003\022\004\022\0020\0030\007¢\006\002\020\bJ\033\020\006\032\020\022\006\022\004\030\0010\003\022\004\022\0020\0030\007H\007¢\006\002\b\016J\r\020\n\032\0020\013H\007¢\006\002\b\017J\023\020\020\032\0020\0212\b\020\022\032\004\030\0010\001H\002J\b\020\023\032\0020\024H\026J\017\020\004\032\004\030\0010\003H\007¢\006\002\b\025J\r\020\002\032\0020\003H\007¢\006\002\b\026J\b\020\027\032\0020\003H\026J\016\020\030\032\0020\0002\006\020\n\032\0020\013R!\020\006\032\020\022\006\022\004\030\0010\003\022\004\022\0020\0030\0078G¢\006\b\n\000\032\004\b\006\020\tR\021\020\n\032\0020\0138G¢\006\006\032\004\b\n\020\fR\023\020\004\032\004\030\0010\0038G¢\006\006\032\004\b\004\020\rR\023\020\002\032\0020\0038\007¢\006\b\n\000\032\004\b\002\020\r¨\006\031"}, d2={"Lokhttp3/Challenge;", "", "scheme", "", "realm", "(Ljava/lang/String;Ljava/lang/String;)V", "authParams", "", "(Ljava/lang/String;Ljava/util/Map;)V", "()Ljava/util/Map;", "charset", "Ljava/nio/charset/Charset;", "()Ljava/nio/charset/Charset;", "()Ljava/lang/String;", "-deprecated_authParams", "-deprecated_charset", "equals", "", "other", "hashCode", "", "-deprecated_realm", "-deprecated_scheme", "toString", "withCharset", "okhttp"}, k=1, mv={1, 4, 0})
public final class Challenge
{
  private final Map<String, String> authParams;
  private final String scheme;
  
  public Challenge(String paramString1, String paramString2)
  {
    this(paramString1, paramString2);
  }
  
  public Challenge(String paramString, Map<String, String> paramMap)
  {
    this.scheme = paramString;
    Map localMap = (Map)new LinkedHashMap();
    paramMap = paramMap.entrySet().iterator();
    while (paramMap.hasNext())
    {
      Object localObject = (Map.Entry)paramMap.next();
      paramString = (String)((Map.Entry)localObject).getKey();
      localObject = (String)((Map.Entry)localObject).getValue();
      if (paramString != null)
      {
        Locale localLocale = Locale.US;
        Intrinsics.checkNotNullExpressionValue(localLocale, "US");
        if (paramString != null)
        {
          paramString = paramString.toLowerCase(localLocale);
          Intrinsics.checkNotNullExpressionValue(paramString, "(this as java.lang.String).toLowerCase(locale)");
        }
        else
        {
          throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
        }
      }
      else
      {
        paramString = null;
      }
      localMap.put(paramString, localObject);
    }
    paramString = Collections.unmodifiableMap(localMap);
    Intrinsics.checkNotNullExpressionValue(paramString, "unmodifiableMap<String?, String>(newAuthParams)");
    this.authParams = paramString;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="authParams", imports={}))
  public final Map<String, String> -deprecated_authParams()
  {
    return this.authParams;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="charset", imports={}))
  public final Charset -deprecated_charset()
  {
    return charset();
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="realm", imports={}))
  public final String -deprecated_realm()
  {
    return realm();
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="scheme", imports={}))
  public final String -deprecated_scheme()
  {
    return this.scheme;
  }
  
  public final Map<String, String> authParams()
  {
    return this.authParams;
  }
  
  public final Charset charset()
  {
    Object localObject = (String)this.authParams.get("charset");
    if (localObject != null) {
      try
      {
        localObject = Charset.forName((String)localObject);
        Intrinsics.checkNotNullExpressionValue(localObject, "Charset.forName(charset)");
        return (Charset)localObject;
      }
      catch (Exception localException) {}
    }
    Charset localCharset = StandardCharsets.ISO_8859_1;
    Intrinsics.checkNotNullExpressionValue(localCharset, "ISO_8859_1");
    return localCharset;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool;
    if (((paramObject instanceof Challenge)) && (Intrinsics.areEqual(((Challenge)paramObject).scheme, this.scheme)) && (Intrinsics.areEqual(((Challenge)paramObject).authParams, this.authParams))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public int hashCode()
  {
    return (29 * 31 + this.scheme.hashCode()) * 31 + this.authParams.hashCode();
  }
  
  public final String realm()
  {
    return (String)this.authParams.get("realm");
  }
  
  public final String scheme()
  {
    return this.scheme;
  }
  
  public String toString()
  {
    return this.scheme + " authParams=" + this.authParams;
  }
  
  public final Challenge withCharset(Charset paramCharset)
  {
    Intrinsics.checkNotNullParameter(paramCharset, "charset");
    Map localMap = MapsKt.toMutableMap(this.authParams);
    paramCharset = paramCharset.name();
    Intrinsics.checkNotNullExpressionValue(paramCharset, "charset.name()");
    localMap.put("charset", paramCharset);
    return new Challenge(this.scheme, localMap);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/Challenge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */