package okhttp3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import okhttp3.internal.HostnamesKt;
import okhttp3.internal.Util;
import okhttp3.internal.http.DatesKt;
import okhttp3.internal.publicsuffix.PublicSuffixDatabase;
import okhttp3.internal.publicsuffix.PublicSuffixDatabase.Companion;

@Metadata(bv={1, 0, 3}, d1={"\0002\n\002\030\002\n\002\020\000\n\000\n\002\020\016\n\002\b\002\n\002\020\t\n\002\b\003\n\002\020\013\n\002\b\f\n\002\020\b\n\002\b\004\n\002\030\002\n\002\b\013\030\000 &2\0020\001:\002%&BO\b\002\022\006\020\002\032\0020\003\022\006\020\004\032\0020\003\022\006\020\005\032\0020\006\022\006\020\007\032\0020\003\022\006\020\b\032\0020\003\022\006\020\t\032\0020\n\022\006\020\013\032\0020\n\022\006\020\f\032\0020\n\022\006\020\r\032\0020\n¢\006\002\020\016J\r\020\007\032\0020\003H\007¢\006\002\b\022J\023\020\023\032\0020\n2\b\020\024\032\004\030\0010\001H\002J\r\020\005\032\0020\006H\007¢\006\002\b\025J\b\020\026\032\0020\027H\027J\r\020\r\032\0020\nH\007¢\006\002\b\030J\r\020\013\032\0020\nH\007¢\006\002\b\031J\016\020\032\032\0020\n2\006\020\033\032\0020\034J\r\020\002\032\0020\003H\007¢\006\002\b\035J\r\020\b\032\0020\003H\007¢\006\002\b\036J\r\020\f\032\0020\nH\007¢\006\002\b\037J\r\020\t\032\0020\nH\007¢\006\002\b J\b\020!\032\0020\003H\026J\025\020!\032\0020\0032\006\020\"\032\0020\nH\000¢\006\002\b#J\r\020\004\032\0020\003H\007¢\006\002\b$R\023\020\007\032\0020\0038\007¢\006\b\n\000\032\004\b\007\020\017R\023\020\005\032\0020\0068\007¢\006\b\n\000\032\004\b\005\020\020R\023\020\r\032\0020\n8\007¢\006\b\n\000\032\004\b\r\020\021R\023\020\013\032\0020\n8\007¢\006\b\n\000\032\004\b\013\020\021R\023\020\002\032\0020\0038\007¢\006\b\n\000\032\004\b\002\020\017R\023\020\b\032\0020\0038\007¢\006\b\n\000\032\004\b\b\020\017R\023\020\f\032\0020\n8\007¢\006\b\n\000\032\004\b\f\020\021R\023\020\t\032\0020\n8\007¢\006\b\n\000\032\004\b\t\020\021R\023\020\004\032\0020\0038\007¢\006\b\n\000\032\004\b\004\020\017¨\006'"}, d2={"Lokhttp3/Cookie;", "", "name", "", "value", "expiresAt", "", "domain", "path", "secure", "", "httpOnly", "persistent", "hostOnly", "(Ljava/lang/String;Ljava/lang/String;JLjava/lang/String;Ljava/lang/String;ZZZZ)V", "()Ljava/lang/String;", "()J", "()Z", "-deprecated_domain", "equals", "other", "-deprecated_expiresAt", "hashCode", "", "-deprecated_hostOnly", "-deprecated_httpOnly", "matches", "url", "Lokhttp3/HttpUrl;", "-deprecated_name", "-deprecated_path", "-deprecated_persistent", "-deprecated_secure", "toString", "forObsoleteRfc2965", "toString$okhttp", "-deprecated_value", "Builder", "Companion", "okhttp"}, k=1, mv={1, 4, 0})
public final class Cookie
{
  public static final Companion Companion = new Companion(null);
  private static final Pattern DAY_OF_MONTH_PATTERN = Pattern.compile("(\\d{1,2})[^\\d]*");
  private static final Pattern MONTH_PATTERN;
  private static final Pattern TIME_PATTERN = Pattern.compile("(\\d{1,2}):(\\d{1,2}):(\\d{1,2})[^\\d]*");
  private static final Pattern YEAR_PATTERN = Pattern.compile("(\\d{2,4})[^\\d]*");
  private final String domain;
  private final long expiresAt;
  private final boolean hostOnly;
  private final boolean httpOnly;
  private final String name;
  private final String path;
  private final boolean persistent;
  private final boolean secure;
  private final String value;
  
  static
  {
    MONTH_PATTERN = Pattern.compile("(?i)(jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec).*");
  }
  
  private Cookie(String paramString1, String paramString2, long paramLong, String paramString3, String paramString4, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
  {
    this.name = paramString1;
    this.value = paramString2;
    this.expiresAt = paramLong;
    this.domain = paramString3;
    this.path = paramString4;
    this.secure = paramBoolean1;
    this.httpOnly = paramBoolean2;
    this.persistent = paramBoolean3;
    this.hostOnly = paramBoolean4;
  }
  
  @JvmStatic
  public static final Cookie parse(HttpUrl paramHttpUrl, String paramString)
  {
    return Companion.parse(paramHttpUrl, paramString);
  }
  
  @JvmStatic
  public static final List<Cookie> parseAll(HttpUrl paramHttpUrl, Headers paramHeaders)
  {
    return Companion.parseAll(paramHttpUrl, paramHeaders);
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="domain", imports={}))
  public final String -deprecated_domain()
  {
    return this.domain;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="expiresAt", imports={}))
  public final long -deprecated_expiresAt()
  {
    return this.expiresAt;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="hostOnly", imports={}))
  public final boolean -deprecated_hostOnly()
  {
    return this.hostOnly;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="httpOnly", imports={}))
  public final boolean -deprecated_httpOnly()
  {
    return this.httpOnly;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="name", imports={}))
  public final String -deprecated_name()
  {
    return this.name;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="path", imports={}))
  public final String -deprecated_path()
  {
    return this.path;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="persistent", imports={}))
  public final boolean -deprecated_persistent()
  {
    return this.persistent;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="secure", imports={}))
  public final boolean -deprecated_secure()
  {
    return this.secure;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="value", imports={}))
  public final String -deprecated_value()
  {
    return this.value;
  }
  
  public final String domain()
  {
    return this.domain;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool;
    if (((paramObject instanceof Cookie)) && (Intrinsics.areEqual(((Cookie)paramObject).name, this.name)) && (Intrinsics.areEqual(((Cookie)paramObject).value, this.value)) && (((Cookie)paramObject).expiresAt == this.expiresAt) && (Intrinsics.areEqual(((Cookie)paramObject).domain, this.domain)) && (Intrinsics.areEqual(((Cookie)paramObject).path, this.path)) && (((Cookie)paramObject).secure == this.secure) && (((Cookie)paramObject).httpOnly == this.httpOnly) && (((Cookie)paramObject).persistent == this.persistent) && (((Cookie)paramObject).hostOnly == this.hostOnly)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final long expiresAt()
  {
    return this.expiresAt;
  }
  
  public int hashCode()
  {
    return ((((((((17 * 31 + this.name.hashCode()) * 31 + this.value.hashCode()) * 31 + Long.hashCode(this.expiresAt)) * 31 + this.domain.hashCode()) * 31 + this.path.hashCode()) * 31 + Boolean.hashCode(this.secure)) * 31 + Boolean.hashCode(this.httpOnly)) * 31 + Boolean.hashCode(this.persistent)) * 31 + Boolean.hashCode(this.hostOnly);
  }
  
  public final boolean hostOnly()
  {
    return this.hostOnly;
  }
  
  public final boolean httpOnly()
  {
    return this.httpOnly;
  }
  
  public final boolean matches(HttpUrl paramHttpUrl)
  {
    Intrinsics.checkNotNullParameter(paramHttpUrl, "url");
    boolean bool1;
    if (this.hostOnly) {
      bool1 = Intrinsics.areEqual(paramHttpUrl.host(), this.domain);
    } else {
      bool1 = Companion.access$domainMatch(Companion, paramHttpUrl.host(), this.domain);
    }
    boolean bool2 = false;
    if (!bool1) {
      return false;
    }
    if (!Companion.access$pathMatch(Companion, paramHttpUrl, this.path)) {
      return false;
    }
    if (this.secure)
    {
      bool1 = bool2;
      if (!paramHttpUrl.isHttps()) {}
    }
    else
    {
      bool1 = true;
    }
    return bool1;
  }
  
  public final String name()
  {
    return this.name;
  }
  
  public final String path()
  {
    return this.path;
  }
  
  public final boolean persistent()
  {
    return this.persistent;
  }
  
  public final boolean secure()
  {
    return this.secure;
  }
  
  public String toString()
  {
    return toString$okhttp(false);
  }
  
  public final String toString$okhttp(boolean paramBoolean)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.name);
    localStringBuilder.append('=');
    localStringBuilder.append(this.value);
    if (this.persistent) {
      if (this.expiresAt == Long.MIN_VALUE)
      {
        localStringBuilder.append("; max-age=0");
      }
      else
      {
        localObject = localStringBuilder.append("; expires=");
        String str = DatesKt.toHttpDateString(new Date(this.expiresAt));
        Log5ECF72.a(str);
        LogE84000.a(str);
        Log229316.a(str);
        ((StringBuilder)localObject).append(str);
      }
    }
    if (!this.hostOnly)
    {
      localStringBuilder.append("; domain=");
      if (paramBoolean) {
        localStringBuilder.append(".");
      }
      localStringBuilder.append(this.domain);
    }
    localStringBuilder.append("; path=").append(this.path);
    if (this.secure) {
      localStringBuilder.append("; secure");
    }
    if (this.httpOnly) {
      localStringBuilder.append("; httponly");
    }
    Object localObject = localStringBuilder.toString();
    Intrinsics.checkNotNullExpressionValue(localObject, "toString()");
    return (String)localObject;
  }
  
  public final String value()
  {
    return this.value;
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000(\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\016\n\000\n\002\020\t\n\000\n\002\020\013\n\002\b\007\n\002\030\002\n\002\b\002\030\0002\0020\001B\005¢\006\002\020\002J\006\020\017\032\0020\020J\016\020\003\032\0020\0002\006\020\003\032\0020\004J\030\020\003\032\0020\0002\006\020\003\032\0020\0042\006\020\007\032\0020\bH\002J\016\020\005\032\0020\0002\006\020\005\032\0020\006J\016\020\021\032\0020\0002\006\020\003\032\0020\004J\006\020\t\032\0020\000J\016\020\n\032\0020\0002\006\020\n\032\0020\004J\016\020\013\032\0020\0002\006\020\013\032\0020\004J\006\020\r\032\0020\000J\016\020\016\032\0020\0002\006\020\016\032\0020\004R\020\020\003\032\004\030\0010\004X\016¢\006\002\n\000R\016\020\005\032\0020\006X\016¢\006\002\n\000R\016\020\007\032\0020\bX\016¢\006\002\n\000R\016\020\t\032\0020\bX\016¢\006\002\n\000R\020\020\n\032\004\030\0010\004X\016¢\006\002\n\000R\016\020\013\032\0020\004X\016¢\006\002\n\000R\016\020\f\032\0020\bX\016¢\006\002\n\000R\016\020\r\032\0020\bX\016¢\006\002\n\000R\020\020\016\032\004\030\0010\004X\016¢\006\002\n\000¨\006\022"}, d2={"Lokhttp3/Cookie$Builder;", "", "()V", "domain", "", "expiresAt", "", "hostOnly", "", "httpOnly", "name", "path", "persistent", "secure", "value", "build", "Lokhttp3/Cookie;", "hostOnlyDomain", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Builder
  {
    private String domain;
    private long expiresAt = 253402300799999L;
    private boolean hostOnly;
    private boolean httpOnly;
    private String name;
    private String path = "/";
    private boolean persistent;
    private boolean secure;
    private String value;
    
    private final Builder domain(String paramString, boolean paramBoolean)
    {
      Builder localBuilder = (Builder)this;
      String str = HostnamesKt.toCanonicalHost(paramString);
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      if (str != null)
      {
        localBuilder.domain = str;
        localBuilder.hostOnly = paramBoolean;
        return (Builder)this;
      }
      throw ((Throwable)new IllegalArgumentException("unexpected domain: " + paramString));
    }
    
    public final Cookie build()
    {
      String str2 = this.name;
      if (str2 != null)
      {
        String str3 = this.value;
        if (str3 != null)
        {
          long l = this.expiresAt;
          String str1 = this.domain;
          if (str1 != null) {
            return new Cookie(str2, str3, l, str1, this.path, this.secure, this.httpOnly, this.persistent, this.hostOnly, null);
          }
          throw ((Throwable)new NullPointerException("builder.domain == null"));
        }
        throw ((Throwable)new NullPointerException("builder.value == null"));
      }
      throw ((Throwable)new NullPointerException("builder.name == null"));
    }
    
    public final Builder domain(String paramString)
    {
      Intrinsics.checkNotNullParameter(paramString, "domain");
      return domain(paramString, false);
    }
    
    public final Builder expiresAt(long paramLong)
    {
      Builder localBuilder = (Builder)this;
      long l = paramLong;
      paramLong = l;
      if (l <= 0L) {
        paramLong = Long.MIN_VALUE;
      }
      l = paramLong;
      if (paramLong > 253402300799999L) {
        l = 253402300799999L;
      }
      localBuilder.expiresAt = l;
      localBuilder.persistent = true;
      return (Builder)this;
    }
    
    public final Builder hostOnlyDomain(String paramString)
    {
      Intrinsics.checkNotNullParameter(paramString, "domain");
      return domain(paramString, true);
    }
    
    public final Builder httpOnly()
    {
      ((Builder)this).httpOnly = true;
      return (Builder)this;
    }
    
    public final Builder name(String paramString)
    {
      Intrinsics.checkNotNullParameter(paramString, "name");
      Builder localBuilder = (Builder)this;
      if (Intrinsics.areEqual(StringsKt.trim((CharSequence)paramString).toString(), paramString))
      {
        localBuilder.name = paramString;
        return (Builder)this;
      }
      throw ((Throwable)new IllegalArgumentException("name is not trimmed".toString()));
    }
    
    public final Builder path(String paramString)
    {
      Intrinsics.checkNotNullParameter(paramString, "path");
      Builder localBuilder = (Builder)this;
      if (StringsKt.startsWith$default(paramString, "/", false, 2, null))
      {
        localBuilder.path = paramString;
        return (Builder)this;
      }
      throw ((Throwable)new IllegalArgumentException("path must start with '/'".toString()));
    }
    
    public final Builder secure()
    {
      ((Builder)this).secure = true;
      return (Builder)this;
    }
    
    public final Builder value(String paramString)
    {
      Intrinsics.checkNotNullParameter(paramString, "value");
      Builder localBuilder = (Builder)this;
      if (Intrinsics.areEqual(StringsKt.trim((CharSequence)paramString).toString(), paramString))
      {
        localBuilder.value = paramString;
        return (Builder)this;
      }
      throw ((Throwable)new IllegalArgumentException("value is not trimmed".toString()));
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000L\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\005\n\002\020\b\n\000\n\002\020\016\n\002\b\003\n\002\020\013\n\002\b\004\n\002\030\002\n\000\n\002\020\t\n\000\n\002\030\002\n\002\b\003\n\002\020 \n\000\n\002\030\002\n\002\b\007\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J(\020\t\032\0020\n2\006\020\013\032\0020\f2\006\020\r\032\0020\n2\006\020\016\032\0020\n2\006\020\017\032\0020\020H\002J\030\020\021\032\0020\0202\006\020\022\032\0020\f2\006\020\023\032\0020\fH\002J'\020\024\032\004\030\0010\0252\006\020\026\032\0020\0272\006\020\030\032\0020\0312\006\020\032\032\0020\fH\000¢\006\002\b\033J\032\020\024\032\004\030\0010\0252\006\020\030\032\0020\0312\006\020\032\032\0020\fH\007J\036\020\034\032\b\022\004\022\0020\0250\0352\006\020\030\032\0020\0312\006\020\036\032\0020\037H\007J\020\020 \032\0020\f2\006\020!\032\0020\fH\002J \020\"\032\0020\0272\006\020!\032\0020\f2\006\020\r\032\0020\n2\006\020\016\032\0020\nH\002J\020\020#\032\0020\0272\006\020!\032\0020\fH\002J\030\020$\032\0020\0202\006\020\030\032\0020\0312\006\020%\032\0020\fH\002R\026\020\003\032\n \005*\004\030\0010\0040\004X\004¢\006\002\n\000R\026\020\006\032\n \005*\004\030\0010\0040\004X\004¢\006\002\n\000R\026\020\007\032\n \005*\004\030\0010\0040\004X\004¢\006\002\n\000R\026\020\b\032\n \005*\004\030\0010\0040\004X\004¢\006\002\n\000¨\006&"}, d2={"Lokhttp3/Cookie$Companion;", "", "()V", "DAY_OF_MONTH_PATTERN", "Ljava/util/regex/Pattern;", "kotlin.jvm.PlatformType", "MONTH_PATTERN", "TIME_PATTERN", "YEAR_PATTERN", "dateCharacterOffset", "", "input", "", "pos", "limit", "invert", "", "domainMatch", "urlHost", "domain", "parse", "Lokhttp3/Cookie;", "currentTimeMillis", "", "url", "Lokhttp3/HttpUrl;", "setCookie", "parse$okhttp", "parseAll", "", "headers", "Lokhttp3/Headers;", "parseDomain", "s", "parseExpires", "parseMaxAge", "pathMatch", "path", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Companion
  {
    private final int dateCharacterOffset(String paramString, int paramInt1, int paramInt2, boolean paramBoolean)
    {
      while (paramInt1 < paramInt2)
      {
        int i = paramString.charAt(paramInt1);
        if (((i >= 32) || (i == 9)) && (i < 127) && ((48 > i) || (57 < i)) && ((97 > i) || (122 < i)) && ((65 > i) || (90 < i)) && (i != 58)) {
          i = 0;
        } else {
          i = 1;
        }
        if (i == (paramBoolean ^ true)) {
          return paramInt1;
        }
        paramInt1++;
      }
      return paramInt2;
    }
    
    private final boolean domainMatch(String paramString1, String paramString2)
    {
      boolean bool2 = Intrinsics.areEqual(paramString1, paramString2);
      boolean bool1 = true;
      if (bool2) {
        return true;
      }
      if ((!StringsKt.endsWith$default(paramString1, paramString2, false, 2, null)) || (paramString1.charAt(paramString1.length() - paramString2.length() - 1) != '.') || (Util.canParseAsIpAddress(paramString1))) {
        bool1 = false;
      }
      return bool1;
    }
    
    private final String parseDomain(String paramString)
    {
      if ((StringsKt.endsWith$default(paramString, ".", false, 2, null) ^ true))
      {
        paramString = StringsKt.removePrefix(paramString, (CharSequence)".");
        Log5ECF72.a(paramString);
        LogE84000.a(paramString);
        Log229316.a(paramString);
        paramString = HostnamesKt.toCanonicalHost(paramString);
        Log5ECF72.a(paramString);
        LogE84000.a(paramString);
        if (paramString != null) {
          return paramString;
        }
        throw ((Throwable)new IllegalArgumentException());
      }
      throw ((Throwable)new IllegalArgumentException("Failed requirement.".toString()));
    }
    
    private final long parseExpires(String paramString, int paramInt1, int paramInt2)
    {
      int i7 = ((Companion)this).dateCharacterOffset(paramString, paramInt1, paramInt2, false);
      int n = -1;
      int m = -1;
      int k = -1;
      int j = -1;
      int i = -1;
      paramInt1 = -1;
      Matcher localMatcher = Cookie.access$getTIME_PATTERN$cp().matcher((CharSequence)paramString);
      while (i7 < paramInt2)
      {
        int i8 = ((Companion)this).dateCharacterOffset(paramString, i7 + 1, paramInt2, true);
        localMatcher.region(i7, i8);
        String str;
        int i1;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        if ((n == -1) && (localMatcher.usePattern(Cookie.access$getTIME_PATTERN$cp()).matches()))
        {
          str = localMatcher.group(1);
          Intrinsics.checkNotNullExpressionValue(str, "matcher.group(1)");
          i1 = Integer.parseInt(str);
          str = localMatcher.group(2);
          Intrinsics.checkNotNullExpressionValue(str, "matcher.group(2)");
          i2 = Integer.parseInt(str);
          str = localMatcher.group(3);
          Intrinsics.checkNotNullExpressionValue(str, "matcher.group(3)");
          i3 = Integer.parseInt(str);
          i4 = j;
          i5 = i;
          i6 = paramInt1;
        }
        else if ((j == -1) && (localMatcher.usePattern(Cookie.access$getDAY_OF_MONTH_PATTERN$cp()).matches()))
        {
          str = localMatcher.group(1);
          Intrinsics.checkNotNullExpressionValue(str, "matcher.group(1)");
          i4 = Integer.parseInt(str);
          i1 = n;
          i2 = m;
          i3 = k;
          i5 = i;
          i6 = paramInt1;
        }
        else if ((i == -1) && (localMatcher.usePattern(Cookie.access$getMONTH_PATTERN$cp()).matches()))
        {
          str = localMatcher.group(1);
          Intrinsics.checkNotNullExpressionValue(str, "matcher.group(1)");
          Object localObject = Locale.US;
          Intrinsics.checkNotNullExpressionValue(localObject, "Locale.US");
          if (str != null)
          {
            localObject = str.toLowerCase((Locale)localObject);
            Intrinsics.checkNotNullExpressionValue(localObject, "(this as java.lang.String).toLowerCase(locale)");
            str = Cookie.access$getMONTH_PATTERN$cp().pattern();
            Intrinsics.checkNotNullExpressionValue(str, "MONTH_PATTERN.pattern()");
            i5 = StringsKt.indexOf$default((CharSequence)str, (String)localObject, 0, false, 6, null) / 4;
            i1 = n;
            i2 = m;
            i3 = k;
            i4 = j;
            i6 = paramInt1;
          }
          else
          {
            throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
          }
        }
        else
        {
          i1 = n;
          i2 = m;
          i3 = k;
          i4 = j;
          i5 = i;
          i6 = paramInt1;
          if (paramInt1 == -1)
          {
            i1 = n;
            i2 = m;
            i3 = k;
            i4 = j;
            i5 = i;
            i6 = paramInt1;
            if (localMatcher.usePattern(Cookie.access$getYEAR_PATTERN$cp()).matches())
            {
              str = localMatcher.group(1);
              Intrinsics.checkNotNullExpressionValue(str, "matcher.group(1)");
              i6 = Integer.parseInt(str);
              i5 = i;
              i4 = j;
              i3 = k;
              i2 = m;
              i1 = n;
            }
          }
        }
        i7 = ((Companion)this).dateCharacterOffset(paramString, i8 + 1, paramInt2, false);
        n = i1;
        m = i2;
        k = i3;
        j = i4;
        i = i5;
        paramInt1 = i6;
      }
      if (70 > paramInt1)
      {
        paramInt2 = paramInt1;
      }
      else
      {
        paramInt2 = paramInt1;
        if (99 >= paramInt1) {
          paramInt2 = paramInt1 + 1900;
        }
      }
      if (paramInt2 < 0)
      {
        paramInt1 = paramInt2;
      }
      else
      {
        paramInt1 = paramInt2;
        if (69 >= paramInt2) {
          paramInt1 = paramInt2 + 2000;
        }
      }
      if (paramInt1 >= 1601) {
        paramInt2 = 1;
      } else {
        paramInt2 = 0;
      }
      if (paramInt2 != 0)
      {
        if (i != -1) {
          paramInt2 = 1;
        } else {
          paramInt2 = 0;
        }
        if (paramInt2 != 0)
        {
          if ((1 <= j) && (31 >= j)) {
            paramInt2 = 1;
          } else {
            paramInt2 = 0;
          }
          if (paramInt2 != 0)
          {
            if ((n >= 0) && (23 >= n)) {
              paramInt2 = 1;
            } else {
              paramInt2 = 0;
            }
            if (paramInt2 != 0)
            {
              if ((m >= 0) && (59 >= m)) {
                paramInt2 = 1;
              } else {
                paramInt2 = 0;
              }
              if (paramInt2 != 0)
              {
                if ((k >= 0) && (59 >= k)) {
                  paramInt2 = 1;
                } else {
                  paramInt2 = 0;
                }
                if (paramInt2 != 0)
                {
                  paramString = new GregorianCalendar(Util.UTC);
                  paramString.setLenient(false);
                  paramString.set(1, paramInt1);
                  paramString.set(2, i - 1);
                  paramString.set(5, j);
                  paramString.set(11, n);
                  paramString.set(12, m);
                  paramString.set(13, k);
                  paramString.set(14, 0);
                  return paramString.getTimeInMillis();
                }
                throw ((Throwable)new IllegalArgumentException("Failed requirement.".toString()));
              }
              throw ((Throwable)new IllegalArgumentException("Failed requirement.".toString()));
            }
            throw ((Throwable)new IllegalArgumentException("Failed requirement.".toString()));
          }
          throw ((Throwable)new IllegalArgumentException("Failed requirement.".toString()));
        }
        throw ((Throwable)new IllegalArgumentException("Failed requirement.".toString()));
      }
      throw ((Throwable)new IllegalArgumentException("Failed requirement.".toString()));
    }
    
    private final long parseMaxAge(String paramString)
    {
      long l1 = Long.MIN_VALUE;
      try
      {
        long l2 = Long.parseLong(paramString);
        if (l2 > 0L) {
          l1 = l2;
        }
        return l1;
      }
      catch (NumberFormatException localNumberFormatException)
      {
        CharSequence localCharSequence = (CharSequence)paramString;
        if (new Regex("-?\\d+").matches(localCharSequence))
        {
          if (!StringsKt.startsWith$default(paramString, "-", false, 2, null)) {
            l1 = Long.MAX_VALUE;
          }
          return l1;
        }
        throw ((Throwable)localNumberFormatException);
      }
    }
    
    private final boolean pathMatch(HttpUrl paramHttpUrl, String paramString)
    {
      paramHttpUrl = paramHttpUrl.encodedPath();
      if (Intrinsics.areEqual(paramHttpUrl, paramString)) {
        return true;
      }
      if (StringsKt.startsWith$default(paramHttpUrl, paramString, false, 2, null))
      {
        if (StringsKt.endsWith$default(paramString, "/", false, 2, null)) {
          return true;
        }
        if (paramHttpUrl.charAt(paramString.length()) == '/') {
          return true;
        }
      }
      return false;
    }
    
    @JvmStatic
    public final Cookie parse(HttpUrl paramHttpUrl, String paramString)
    {
      Intrinsics.checkNotNullParameter(paramHttpUrl, "url");
      Intrinsics.checkNotNullParameter(paramString, "setCookie");
      return ((Companion)this).parse$okhttp(System.currentTimeMillis(), paramHttpUrl, paramString);
    }
    
    public final Cookie parse$okhttp(long paramLong, HttpUrl paramHttpUrl, String paramString)
    {
      Intrinsics.checkNotNullParameter(paramHttpUrl, "url");
      Intrinsics.checkNotNullParameter(paramString, "setCookie");
      int k = Util.delimiterOffset$default(paramString, ';', 0, 0, 6, null);
      int j = Util.delimiterOffset$default(paramString, '=', 0, k, 2, null);
      if (j == k) {
        return null;
      }
      String str2 = Util.trimSubstring$default(paramString, 0, j, 1, null);
      Log5ECF72.a(str2);
      LogE84000.a(str2);
      Log229316.a(str2);
      int i;
      if (((CharSequence)str2).length() == 0) {
        i = 1;
      } else {
        i = 0;
      }
      if ((i == 0) && (Util.indexOfControlOrNonAscii(str2) == -1))
      {
        String str1 = Util.trimSubstring(paramString, j + 1, k);
        Log5ECF72.a(str1);
        LogE84000.a(str1);
        Log229316.a(str1);
        if (Util.indexOfControlOrNonAscii(str1) != -1) {
          return null;
        }
        Object localObject2 = (String)null;
        Object localObject1 = (String)null;
        i = paramString.length();
        long l2 = -1L;
        boolean bool5 = false;
        boolean bool4 = false;
        boolean bool3 = true;
        boolean bool2 = false;
        k++;
        long l1 = 253402300799999L;
        while (k < i)
        {
          int m = Util.delimiterOffset(paramString, ';', k, i);
          int n = Util.delimiterOffset(paramString, '=', k, m);
          String str3 = Util.trimSubstring(paramString, k, n);
          Log5ECF72.a(str3);
          LogE84000.a(str3);
          Log229316.a(str3);
          Object localObject3;
          if (n < m)
          {
            localObject3 = Util.trimSubstring(paramString, n + 1, m);
            Log5ECF72.a(localObject3);
            LogE84000.a(localObject3);
            Log229316.a(localObject3);
          }
          else
          {
            localObject3 = "";
          }
          Object localObject7;
          long l3;
          boolean bool1;
          long l4;
          boolean bool7;
          boolean bool6;
          Object localObject4;
          if (StringsKt.equals(str3, "expires", true))
          {
            try
            {
              localObject7 = (Companion)this;
              k = ((String)localObject3).length();
              try
              {
                l3 = ((Companion)localObject7).parseExpires((String)localObject3, 0, k);
                bool1 = true;
                localObject3 = localObject2;
                localObject7 = localObject1;
                l4 = l2;
                bool7 = bool5;
                bool6 = bool3;
              }
              catch (IllegalArgumentException localIllegalArgumentException1) {}
              localObject4 = localObject2;
            }
            catch (IllegalArgumentException localIllegalArgumentException2) {}
            localObject7 = localObject1;
            l3 = l1;
            l4 = l2;
            bool7 = bool5;
            bool6 = bool3;
            bool1 = bool2;
          }
          else
          {
            Object localObject5;
            if (StringsKt.equals(str3, "max-age", true))
            {
              try
              {
                l4 = ((Companion)this).parseMaxAge((String)localObject4);
                bool1 = true;
                localObject4 = localObject2;
                localObject7 = localObject1;
                l3 = l1;
                bool7 = bool5;
                bool6 = bool3;
              }
              catch (NumberFormatException localNumberFormatException)
              {
                localObject5 = localObject2;
                localObject7 = localObject1;
                l3 = l1;
                l4 = l2;
                bool7 = bool5;
                bool6 = bool3;
                bool1 = bool2;
              }
            }
            else if (StringsKt.equals(str3, "domain", true))
            {
              try
              {
                localObject5 = ((Companion)this).parseDomain((String)localObject5);
                bool6 = false;
                localObject7 = localObject1;
                l3 = l1;
                l4 = l2;
                bool7 = bool5;
                bool1 = bool2;
              }
              catch (IllegalArgumentException localIllegalArgumentException3)
              {
                localObject6 = localObject2;
                localObject7 = localObject1;
                l3 = l1;
                l4 = l2;
                bool7 = bool5;
                bool6 = bool3;
                bool1 = bool2;
              }
            }
            else if (StringsKt.equals(str3, "path", true))
            {
              localObject7 = localObject6;
              localObject6 = localObject2;
              l3 = l1;
              l4 = l2;
              bool7 = bool5;
              bool6 = bool3;
              bool1 = bool2;
            }
            else if (StringsKt.equals(str3, "secure", true))
            {
              bool7 = true;
              localObject6 = localObject2;
              localObject7 = localObject1;
              l3 = l1;
              l4 = l2;
              bool6 = bool3;
              bool1 = bool2;
            }
            else
            {
              localObject6 = localObject2;
              localObject7 = localObject1;
              l3 = l1;
              l4 = l2;
              bool7 = bool5;
              bool6 = bool3;
              bool1 = bool2;
              if (StringsKt.equals(str3, "httponly", true))
              {
                bool4 = true;
                bool1 = bool2;
                bool6 = bool3;
                bool7 = bool5;
                l4 = l2;
                l3 = l1;
                localObject7 = localObject1;
                localObject6 = localObject2;
              }
            }
          }
          k = m + 1;
          localObject2 = localObject6;
          localObject1 = localObject7;
          l1 = l3;
          l2 = l4;
          bool5 = bool7;
          bool3 = bool6;
          bool2 = bool1;
        }
        if (l2 == Long.MIN_VALUE)
        {
          paramLong = Long.MIN_VALUE;
        }
        else if (l2 != -1L)
        {
          if (l2 <= 9223372036854775L) {
            l1 = 'Ϩ' * l2;
          } else {
            l1 = Long.MAX_VALUE;
          }
          l1 = paramLong + l1;
          if ((l1 >= paramLong) && (l1 <= 253402300799999L)) {
            paramLong = l1;
          } else {
            paramLong = 253402300799999L;
          }
        }
        else
        {
          paramLong = l1;
        }
        Object localObject6 = paramHttpUrl.host();
        if (localObject2 == null)
        {
          paramString = (String)localObject6;
        }
        else
        {
          paramString = (String)localObject2;
          if (!((Companion)this).domainMatch((String)localObject6, (String)localObject2)) {
            return null;
          }
        }
        if ((((String)localObject6).length() != paramString.length()) && (PublicSuffixDatabase.Companion.get().getEffectiveTldPlusOne(paramString) == null)) {
          return null;
        }
        localObject2 = "/";
        if ((localObject1 != null) && (StringsKt.startsWith$default((String)localObject1, "/", false, 2, null)))
        {
          paramHttpUrl = (HttpUrl)localObject1;
        }
        else
        {
          localObject1 = paramHttpUrl.encodedPath();
          i = StringsKt.lastIndexOf$default((CharSequence)localObject1, '/', 0, false, 6, null);
          paramHttpUrl = (HttpUrl)localObject2;
          if (i != 0) {
            if (localObject1 != null)
            {
              paramHttpUrl = ((String)localObject1).substring(0, i);
              Intrinsics.checkNotNullExpressionValue(paramHttpUrl, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            }
            else
            {
              throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
            }
          }
        }
        return new Cookie(str2, str1, paramLong, paramString, paramHttpUrl, bool5, bool4, bool2, bool3, null);
      }
      return null;
    }
    
    @JvmStatic
    public final List<Cookie> parseAll(HttpUrl paramHttpUrl, Headers paramHeaders)
    {
      Intrinsics.checkNotNullParameter(paramHttpUrl, "url");
      Intrinsics.checkNotNullParameter(paramHeaders, "headers");
      List localList = paramHeaders.values("Set-Cookie");
      paramHeaders = (List)null;
      int j = localList.size();
      int i = 0;
      while (i < j)
      {
        Cookie localCookie = ((Companion)this).parse(paramHttpUrl, (String)localList.get(i));
        Object localObject = paramHeaders;
        if (localCookie != null)
        {
          localObject = paramHeaders;
          if (paramHeaders == null) {
            localObject = (List)new ArrayList();
          }
          ((List)localObject).add(localCookie);
        }
        i++;
        paramHeaders = (Headers)localObject;
      }
      if (paramHeaders != null)
      {
        paramHttpUrl = Collections.unmodifiableList(paramHeaders);
        Intrinsics.checkNotNullExpressionValue(paramHttpUrl, "Collections.unmodifiableList(cookies)");
      }
      else
      {
        paramHttpUrl = CollectionsKt.emptyList();
      }
      return paramHttpUrl;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/Cookie.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */