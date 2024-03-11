package okhttp3;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntProgression;
import kotlin.ranges.RangesKt;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import okhttp3.internal.HostnamesKt;
import okhttp3.internal.Util;
import okhttp3.internal.publicsuffix.PublicSuffixDatabase;
import okhttp3.internal.publicsuffix.PublicSuffixDatabase.Companion;
import okio.Buffer;

@Metadata(bv={1, 0, 3}, d1={"\000H\n\002\030\002\n\002\020\000\n\000\n\002\020\016\n\002\b\004\n\002\020\b\n\000\n\002\020 \n\002\b\r\n\002\020\013\n\002\b\005\n\002\020\"\n\002\b\016\n\002\030\002\n\002\b\023\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\007\030\000 J2\0020\001:\002IJBa\b\000\022\006\020\002\032\0020\003\022\006\020\004\032\0020\003\022\006\020\005\032\0020\003\022\006\020\006\032\0020\003\022\006\020\007\032\0020\b\022\f\020\t\032\b\022\004\022\0020\0030\n\022\020\020\013\032\f\022\006\022\004\030\0010\003\030\0010\n\022\b\020\f\032\004\030\0010\003\022\006\020\r\032\0020\003¢\006\002\020\016J\017\020\017\032\004\030\0010\003H\007¢\006\002\b!J\r\020\021\032\0020\003H\007¢\006\002\b\"J\r\020\022\032\0020\003H\007¢\006\002\b#J\023\020\023\032\b\022\004\022\0020\0030\nH\007¢\006\002\b$J\017\020\025\032\004\030\0010\003H\007¢\006\002\b%J\r\020\026\032\0020\003H\007¢\006\002\b&J\023\020'\032\0020\0302\b\020(\032\004\030\0010\001H\002J\017\020\f\032\004\030\0010\003H\007¢\006\002\b)J\b\020*\032\0020\bH\026J\r\020\006\032\0020\003H\007¢\006\002\b+J\006\020,\032\0020-J\020\020,\032\004\030\0010-2\006\020.\032\0020\003J\r\020\005\032\0020\003H\007¢\006\002\b/J\023\020\t\032\b\022\004\022\0020\0030\nH\007¢\006\002\b0J\r\020\032\032\0020\bH\007¢\006\002\b1J\r\020\007\032\0020\bH\007¢\006\002\b2J\017\020\034\032\004\030\0010\003H\007¢\006\002\b3J\020\0204\032\004\030\0010\0032\006\0205\032\0020\003J\016\0206\032\0020\0032\006\0207\032\0020\bJ\023\020\035\032\b\022\004\022\0020\0030\036H\007¢\006\002\b8J\020\0209\032\004\030\0010\0032\006\0207\032\0020\bJ\026\020:\032\n\022\006\022\004\030\0010\0030\n2\006\0205\032\0020\003J\r\020 \032\0020\bH\007¢\006\002\b;J\006\020<\032\0020\003J\020\020=\032\004\030\0010\0002\006\020.\032\0020\003J\r\020\002\032\0020\003H\007¢\006\002\b>J\b\020?\032\0020\003H\026J\r\020@\032\0020AH\007¢\006\002\bBJ\r\020C\032\0020DH\007¢\006\002\b\rJ\b\020E\032\004\030\0010\003J\r\020B\032\0020AH\007¢\006\002\bFJ\r\020\r\032\0020DH\007¢\006\002\bGJ\r\020\004\032\0020\003H\007¢\006\002\bHR\023\020\017\032\004\030\0010\0038G¢\006\006\032\004\b\017\020\020R\021\020\021\032\0020\0038G¢\006\006\032\004\b\021\020\020R\021\020\022\032\0020\0038G¢\006\006\032\004\b\022\020\020R\027\020\023\032\b\022\004\022\0020\0030\n8G¢\006\006\032\004\b\023\020\024R\023\020\025\032\004\030\0010\0038G¢\006\006\032\004\b\025\020\020R\021\020\026\032\0020\0038G¢\006\006\032\004\b\026\020\020R\025\020\f\032\004\030\0010\0038\007¢\006\b\n\000\032\004\b\f\020\020R\023\020\006\032\0020\0038\007¢\006\b\n\000\032\004\b\006\020\020R\021\020\027\032\0020\030¢\006\b\n\000\032\004\b\027\020\031R\023\020\005\032\0020\0038\007¢\006\b\n\000\032\004\b\005\020\020R\031\020\t\032\b\022\004\022\0020\0030\n8\007¢\006\b\n\000\032\004\b\t\020\024R\021\020\032\032\0020\b8G¢\006\006\032\004\b\032\020\033R\023\020\007\032\0020\b8\007¢\006\b\n\000\032\004\b\007\020\033R\023\020\034\032\004\030\0010\0038G¢\006\006\032\004\b\034\020\020R\030\020\013\032\f\022\006\022\004\030\0010\003\030\0010\nX\004¢\006\002\n\000R\027\020\035\032\b\022\004\022\0020\0030\0368G¢\006\006\032\004\b\035\020\037R\021\020 \032\0020\b8G¢\006\006\032\004\b \020\033R\023\020\002\032\0020\0038\007¢\006\b\n\000\032\004\b\002\020\020R\016\020\r\032\0020\003X\004¢\006\002\n\000R\023\020\004\032\0020\0038\007¢\006\b\n\000\032\004\b\004\020\020¨\006K"}, d2={"Lokhttp3/HttpUrl;", "", "scheme", "", "username", "password", "host", "port", "", "pathSegments", "", "queryNamesAndValues", "fragment", "url", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/util/List;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;)V", "encodedFragment", "()Ljava/lang/String;", "encodedPassword", "encodedPath", "encodedPathSegments", "()Ljava/util/List;", "encodedQuery", "encodedUsername", "isHttps", "", "()Z", "pathSize", "()I", "query", "queryParameterNames", "", "()Ljava/util/Set;", "querySize", "-deprecated_encodedFragment", "-deprecated_encodedPassword", "-deprecated_encodedPath", "-deprecated_encodedPathSegments", "-deprecated_encodedQuery", "-deprecated_encodedUsername", "equals", "other", "-deprecated_fragment", "hashCode", "-deprecated_host", "newBuilder", "Lokhttp3/HttpUrl$Builder;", "link", "-deprecated_password", "-deprecated_pathSegments", "-deprecated_pathSize", "-deprecated_port", "-deprecated_query", "queryParameter", "name", "queryParameterName", "index", "-deprecated_queryParameterNames", "queryParameterValue", "queryParameterValues", "-deprecated_querySize", "redact", "resolve", "-deprecated_scheme", "toString", "toUri", "Ljava/net/URI;", "uri", "toUrl", "Ljava/net/URL;", "topPrivateDomain", "-deprecated_uri", "-deprecated_url", "-deprecated_username", "Builder", "Companion", "okhttp"}, k=1, mv={1, 4, 0})
public final class HttpUrl
{
  public static final Companion Companion = new Companion(null);
  public static final String FORM_ENCODE_SET = " \"':;<=>@[]^`{}|/\\?#&!$(),~";
  public static final String FRAGMENT_ENCODE_SET = "";
  public static final String FRAGMENT_ENCODE_SET_URI = " \"#<>\\^`{|}";
  private static final char[] HEX_DIGITS = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70 };
  public static final String PASSWORD_ENCODE_SET = " \"':;<=>@[]^`{}|/\\?#";
  public static final String PATH_SEGMENT_ENCODE_SET = " \"<>^`{}|/\\?#";
  public static final String PATH_SEGMENT_ENCODE_SET_URI = "[]";
  public static final String QUERY_COMPONENT_ENCODE_SET = " !\"#$&'(),/:;<=>?@[]\\^`{|}~";
  public static final String QUERY_COMPONENT_ENCODE_SET_URI = "\\^`{|}";
  public static final String QUERY_COMPONENT_REENCODE_SET = " \"'<>#&=";
  public static final String QUERY_ENCODE_SET = " \"'<>#";
  public static final String USERNAME_ENCODE_SET = " \"':;<=>@[]^`{}|/\\?#";
  private final String fragment;
  private final String host;
  private final boolean isHttps;
  private final String password;
  private final List<String> pathSegments;
  private final int port;
  private final List<String> queryNamesAndValues;
  private final String scheme;
  private final String url;
  private final String username;
  
  public HttpUrl(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt, List<String> paramList1, List<String> paramList2, String paramString5, String paramString6)
  {
    this.scheme = paramString1;
    this.username = paramString2;
    this.password = paramString3;
    this.host = paramString4;
    this.port = paramInt;
    this.pathSegments = paramList1;
    this.queryNamesAndValues = paramList2;
    this.fragment = paramString5;
    this.url = paramString6;
    this.isHttps = Intrinsics.areEqual(paramString1, "https");
  }
  
  @JvmStatic
  public static final int defaultPort(String paramString)
  {
    return Companion.defaultPort(paramString);
  }
  
  @JvmStatic
  public static final HttpUrl get(String paramString)
  {
    return Companion.get(paramString);
  }
  
  @JvmStatic
  public static final HttpUrl get(URI paramURI)
  {
    return Companion.get(paramURI);
  }
  
  @JvmStatic
  public static final HttpUrl get(URL paramURL)
  {
    return Companion.get(paramURL);
  }
  
  @JvmStatic
  public static final HttpUrl parse(String paramString)
  {
    return Companion.parse(paramString);
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="encodedFragment", imports={}))
  public final String -deprecated_encodedFragment()
  {
    return encodedFragment();
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="encodedPassword", imports={}))
  public final String -deprecated_encodedPassword()
  {
    return encodedPassword();
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="encodedPath", imports={}))
  public final String -deprecated_encodedPath()
  {
    return encodedPath();
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="encodedPathSegments", imports={}))
  public final List<String> -deprecated_encodedPathSegments()
  {
    return encodedPathSegments();
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="encodedQuery", imports={}))
  public final String -deprecated_encodedQuery()
  {
    return encodedQuery();
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="encodedUsername", imports={}))
  public final String -deprecated_encodedUsername()
  {
    return encodedUsername();
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="fragment", imports={}))
  public final String -deprecated_fragment()
  {
    return this.fragment;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="host", imports={}))
  public final String -deprecated_host()
  {
    return this.host;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="password", imports={}))
  public final String -deprecated_password()
  {
    return this.password;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="pathSegments", imports={}))
  public final List<String> -deprecated_pathSegments()
  {
    return this.pathSegments;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="pathSize", imports={}))
  public final int -deprecated_pathSize()
  {
    return pathSize();
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="port", imports={}))
  public final int -deprecated_port()
  {
    return this.port;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="query", imports={}))
  public final String -deprecated_query()
  {
    return query();
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="queryParameterNames", imports={}))
  public final Set<String> -deprecated_queryParameterNames()
  {
    return queryParameterNames();
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="querySize", imports={}))
  public final int -deprecated_querySize()
  {
    return querySize();
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="scheme", imports={}))
  public final String -deprecated_scheme()
  {
    return this.scheme;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to toUri()", replaceWith=@ReplaceWith(expression="toUri()", imports={}))
  public final URI -deprecated_uri()
  {
    return uri();
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to toUrl()", replaceWith=@ReplaceWith(expression="toUrl()", imports={}))
  public final URL -deprecated_url()
  {
    return url();
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="username", imports={}))
  public final String -deprecated_username()
  {
    return this.username;
  }
  
  public final String encodedFragment()
  {
    if (this.fragment == null) {
      return null;
    }
    int i = StringsKt.indexOf$default((CharSequence)this.url, '#', 0, false, 6, null);
    String str = this.url;
    if (str != null)
    {
      str = str.substring(i + 1);
      Intrinsics.checkNotNullExpressionValue(str, "(this as java.lang.String).substring(startIndex)");
      return str;
    }
    throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
  }
  
  public final String encodedPassword()
  {
    if (((CharSequence)this.password).length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return "";
    }
    int j = StringsKt.indexOf$default((CharSequence)this.url, ':', this.scheme.length() + 3, false, 4, null);
    int i = StringsKt.indexOf$default((CharSequence)this.url, '@', 0, false, 6, null);
    String str = this.url;
    if (str != null)
    {
      str = str.substring(j + 1, i);
      Intrinsics.checkNotNullExpressionValue(str, "(this as java.lang.Strin…ing(startIndex, endIndex)");
      return str;
    }
    throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
  }
  
  public final String encodedPath()
  {
    int j = StringsKt.indexOf$default((CharSequence)this.url, '/', this.scheme.length() + 3, false, 4, null);
    String str = this.url;
    int i = Util.delimiterOffset(str, "?#", j, str.length());
    str = this.url;
    if (str != null)
    {
      str = str.substring(j, i);
      Intrinsics.checkNotNullExpressionValue(str, "(this as java.lang.Strin…ing(startIndex, endIndex)");
      return str;
    }
    throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
  }
  
  public final List<String> encodedPathSegments()
  {
    int i = StringsKt.indexOf$default((CharSequence)this.url, '/', this.scheme.length() + 3, false, 4, null);
    Object localObject = this.url;
    int j = Util.delimiterOffset((String)localObject, "?#", i, ((String)localObject).length());
    localObject = (List)new ArrayList();
    while (i < j)
    {
      int k = i + 1;
      i = Util.delimiterOffset(this.url, '/', k, j);
      String str = this.url;
      if (str != null)
      {
        str = str.substring(k, i);
        Intrinsics.checkNotNullExpressionValue(str, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        ((List)localObject).add(str);
      }
      else
      {
        throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
      }
    }
    return (List<String>)localObject;
  }
  
  public final String encodedQuery()
  {
    if (this.queryNamesAndValues == null) {
      return null;
    }
    int j = StringsKt.indexOf$default((CharSequence)this.url, '?', 0, false, 6, null) + 1;
    String str = this.url;
    int i = Util.delimiterOffset(str, '#', j, str.length());
    str = this.url;
    if (str != null)
    {
      str = str.substring(j, i);
      Intrinsics.checkNotNullExpressionValue(str, "(this as java.lang.Strin…ing(startIndex, endIndex)");
      return str;
    }
    throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
  }
  
  public final String encodedUsername()
  {
    if (((CharSequence)this.username).length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return "";
    }
    int i = this.scheme.length() + 3;
    String str = this.url;
    int j = Util.delimiterOffset(str, ":@", i, str.length());
    str = this.url;
    if (str != null)
    {
      str = str.substring(i, j);
      Intrinsics.checkNotNullExpressionValue(str, "(this as java.lang.Strin…ing(startIndex, endIndex)");
      return str;
    }
    throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool;
    if (((paramObject instanceof HttpUrl)) && (Intrinsics.areEqual(((HttpUrl)paramObject).url, this.url))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final String fragment()
  {
    return this.fragment;
  }
  
  public int hashCode()
  {
    return this.url.hashCode();
  }
  
  public final String host()
  {
    return this.host;
  }
  
  public final boolean isHttps()
  {
    return this.isHttps;
  }
  
  public final Builder newBuilder()
  {
    Builder localBuilder = new Builder();
    localBuilder.setScheme$okhttp(this.scheme);
    localBuilder.setEncodedUsername$okhttp(encodedUsername());
    localBuilder.setEncodedPassword$okhttp(encodedPassword());
    localBuilder.setHost$okhttp(this.host);
    int i;
    if (this.port != Companion.defaultPort(this.scheme)) {
      i = this.port;
    } else {
      i = -1;
    }
    localBuilder.setPort$okhttp(i);
    localBuilder.getEncodedPathSegments$okhttp().clear();
    localBuilder.getEncodedPathSegments$okhttp().addAll((Collection)encodedPathSegments());
    localBuilder.encodedQuery(encodedQuery());
    localBuilder.setEncodedFragment$okhttp(encodedFragment());
    return localBuilder;
  }
  
  public final Builder newBuilder(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "link");
    try
    {
      Builder localBuilder = new okhttp3/HttpUrl$Builder;
      localBuilder.<init>();
      paramString = localBuilder.parse$okhttp(this, paramString);
    }
    catch (IllegalArgumentException paramString)
    {
      paramString = null;
    }
    return paramString;
  }
  
  public final String password()
  {
    return this.password;
  }
  
  public final List<String> pathSegments()
  {
    return this.pathSegments;
  }
  
  public final int pathSize()
  {
    return this.pathSegments.size();
  }
  
  public final int port()
  {
    return this.port;
  }
  
  public final String query()
  {
    if (this.queryNamesAndValues == null) {
      return null;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    Companion.toQueryString$okhttp(this.queryNamesAndValues, localStringBuilder);
    return localStringBuilder.toString();
  }
  
  public final String queryParameter(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "name");
    Object localObject = this.queryNamesAndValues;
    if (localObject == null) {
      return null;
    }
    localObject = RangesKt.step((IntProgression)RangesKt.until(0, ((List)localObject).size()), 2);
    int i = ((IntProgression)localObject).getFirst();
    int j = ((IntProgression)localObject).getLast();
    int k = ((IntProgression)localObject).getStep();
    if (k >= 0 ? i <= j : i >= j) {
      for (;;)
      {
        if (Intrinsics.areEqual(paramString, (String)this.queryNamesAndValues.get(i))) {
          return (String)this.queryNamesAndValues.get(i + 1);
        }
        if (i == j) {
          break;
        }
        i += k;
      }
    }
    return null;
  }
  
  public final String queryParameterName(int paramInt)
  {
    Object localObject = this.queryNamesAndValues;
    if (localObject != null)
    {
      localObject = ((List)localObject).get(paramInt * 2);
      Intrinsics.checkNotNull(localObject);
      return (String)localObject;
    }
    throw ((Throwable)new IndexOutOfBoundsException());
  }
  
  public final Set<String> queryParameterNames()
  {
    if (this.queryNamesAndValues == null) {
      return SetsKt.emptySet();
    }
    Object localObject1 = new LinkedHashSet();
    Object localObject2 = RangesKt.step((IntProgression)RangesKt.until(0, this.queryNamesAndValues.size()), 2);
    int i = ((IntProgression)localObject2).getFirst();
    int j = ((IntProgression)localObject2).getLast();
    int k = ((IntProgression)localObject2).getStep();
    if (k >= 0 ? i <= j : i >= j) {
      for (;;)
      {
        localObject2 = this.queryNamesAndValues.get(i);
        Intrinsics.checkNotNull(localObject2);
        ((LinkedHashSet)localObject1).add(localObject2);
        if (i == j) {
          break;
        }
        i += k;
      }
    }
    localObject1 = Collections.unmodifiableSet((Set)localObject1);
    Intrinsics.checkNotNullExpressionValue(localObject1, "Collections.unmodifiableSet(result)");
    return (Set<String>)localObject1;
  }
  
  public final String queryParameterValue(int paramInt)
  {
    List localList = this.queryNamesAndValues;
    if (localList != null) {
      return (String)localList.get(paramInt * 2 + 1);
    }
    throw ((Throwable)new IndexOutOfBoundsException());
  }
  
  public final List<String> queryParameterValues(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "name");
    if (this.queryNamesAndValues == null) {
      return CollectionsKt.emptyList();
    }
    List localList = (List)new ArrayList();
    IntProgression localIntProgression = RangesKt.step((IntProgression)RangesKt.until(0, this.queryNamesAndValues.size()), 2);
    int i = localIntProgression.getFirst();
    int j = localIntProgression.getLast();
    int k = localIntProgression.getStep();
    if (k >= 0 ? i <= j : i >= j) {
      for (;;)
      {
        if (Intrinsics.areEqual(paramString, (String)this.queryNamesAndValues.get(i))) {
          localList.add(this.queryNamesAndValues.get(i + 1));
        }
        if (i == j) {
          break;
        }
        i += k;
      }
    }
    paramString = Collections.unmodifiableList(localList);
    Intrinsics.checkNotNullExpressionValue(paramString, "Collections.unmodifiableList(result)");
    return paramString;
  }
  
  public final int querySize()
  {
    List localList = this.queryNamesAndValues;
    int i;
    if (localList != null) {
      i = localList.size() / 2;
    } else {
      i = 0;
    }
    return i;
  }
  
  public final String redact()
  {
    Builder localBuilder = newBuilder("/...");
    Intrinsics.checkNotNull(localBuilder);
    return localBuilder.username("").password("").build().toString();
  }
  
  public final HttpUrl resolve(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "link");
    paramString = newBuilder(paramString);
    if (paramString != null) {
      paramString = paramString.build();
    } else {
      paramString = null;
    }
    return paramString;
  }
  
  public final String scheme()
  {
    return this.scheme;
  }
  
  public String toString()
  {
    return this.url;
  }
  
  public final String topPrivateDomain()
  {
    String str;
    if (Util.canParseAsIpAddress(this.host)) {
      str = null;
    } else {
      str = PublicSuffixDatabase.Companion.get().getEffectiveTldPlusOne(this.host);
    }
    return str;
  }
  
  public final URI uri()
  {
    Object localObject2 = newBuilder().reencodeForUri$okhttp().toString();
    Object localObject1;
    try
    {
      localObject1 = new java/net/URI;
      ((URI)localObject1).<init>((String)localObject2);
    }
    catch (URISyntaxException localURISyntaxException) {}
    try
    {
      localObject1 = (CharSequence)localObject2;
      localObject2 = new kotlin/text/Regex;
      ((Regex)localObject2).<init>("[\\u0000-\\u001F\\u007F-\\u009F\\p{javaWhitespace}]");
      localObject1 = URI.create(((Regex)localObject2).replace((CharSequence)localObject1, ""));
      Intrinsics.checkNotNullExpressionValue(localObject1, "try {\n        val stripp…e) // Unexpected!\n      }");
      return (URI)localObject1;
    }
    catch (Exception localException)
    {
      throw ((Throwable)new RuntimeException((Throwable)localURISyntaxException));
    }
  }
  
  public final URL url()
  {
    try
    {
      URL localURL = new URL(this.url);
      return localURL;
    }
    catch (MalformedURLException localMalformedURLException)
    {
      throw ((Throwable)new RuntimeException((Throwable)localMalformedURLException));
    }
  }
  
  public final String username()
  {
    return this.username;
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000<\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\016\n\002\b\b\n\002\020!\n\002\b\r\n\002\020\b\n\002\b\022\n\002\020\013\n\002\b\004\n\002\030\002\n\002\b\f\n\002\020\002\n\002\b\027\030\000 V2\0020\001:\001VB\005¢\006\002\020\002J\016\020#\032\0020\0002\006\020$\032\0020\004J\016\020%\032\0020\0002\006\020\f\032\0020\004J\030\020&\032\0020\0002\006\020'\032\0020\0042\b\020(\032\004\030\0010\004J\016\020)\032\0020\0002\006\020*\032\0020\004J\016\020+\032\0020\0002\006\020,\032\0020\004J\030\020+\032\0020\0002\006\020,\032\0020\0042\006\020-\032\0020.H\002J\030\020/\032\0020\0002\006\0200\032\0020\0042\b\0201\032\004\030\0010\004J\006\0202\032\00203J\b\0204\032\0020\033H\002J\020\020\003\032\0020\0002\b\020\003\032\004\030\0010\004J\016\020\t\032\0020\0002\006\020\t\032\0020\004J\016\0205\032\0020\0002\006\0205\032\0020\004J\020\0206\032\0020\0002\b\0206\032\004\030\0010\004J\016\020\024\032\0020\0002\006\020\024\032\0020\004J\020\0207\032\0020\0002\b\0207\032\004\030\0010\004J\016\020\027\032\0020\0002\006\020\027\032\0020\004J\020\0208\032\0020.2\006\0209\032\0020\004H\002J\020\020:\032\0020.2\006\0209\032\0020\004H\002J\037\020;\032\0020\0002\b\020<\032\004\030\001032\006\0209\032\0020\004H\000¢\006\002\b=J\016\020>\032\0020\0002\006\020>\032\0020\004J\b\020?\032\0020@H\002J\016\020\032\032\0020\0002\006\020\032\032\0020\033J0\020A\032\0020@2\006\0209\032\0020\0042\006\020B\032\0020\0332\006\020C\032\0020\0332\006\020D\032\0020.2\006\020-\032\0020.H\002J\020\020E\032\0020\0002\b\020E\032\004\030\0010\004J\r\020F\032\0020\000H\000¢\006\002\bGJ\020\020H\032\0020@2\006\020I\032\0020\004H\002J\016\020J\032\0020\0002\006\020'\032\0020\004J\016\020K\032\0020\0002\006\0200\032\0020\004J\016\020L\032\0020\0002\006\020M\032\0020\033J \020N\032\0020@2\006\0209\032\0020\0042\006\020O\032\0020\0332\006\020C\032\0020\033H\002J\016\020 \032\0020\0002\006\020 \032\0020\004J\026\020P\032\0020\0002\006\020M\032\0020\0332\006\020$\032\0020\004J\030\020Q\032\0020\0002\006\020'\032\0020\0042\b\020(\032\004\030\0010\004J\026\020R\032\0020\0002\006\020M\032\0020\0332\006\020*\032\0020\004J\030\020S\032\0020\0002\006\0200\032\0020\0042\b\0201\032\004\030\0010\004J\b\020T\032\0020\004H\026J\016\020U\032\0020\0002\006\020U\032\0020\004R\034\020\003\032\004\030\0010\004X\016¢\006\016\n\000\032\004\b\005\020\006\"\004\b\007\020\bR\032\020\t\032\0020\004X\016¢\006\016\n\000\032\004\b\n\020\006\"\004\b\013\020\bR\032\020\f\032\b\022\004\022\0020\0040\rX\004¢\006\b\n\000\032\004\b\016\020\017R$\020\020\032\f\022\006\022\004\030\0010\004\030\0010\rX\016¢\006\016\n\000\032\004\b\021\020\017\"\004\b\022\020\023R\032\020\024\032\0020\004X\016¢\006\016\n\000\032\004\b\025\020\006\"\004\b\026\020\bR\034\020\027\032\004\030\0010\004X\016¢\006\016\n\000\032\004\b\030\020\006\"\004\b\031\020\bR\032\020\032\032\0020\033X\016¢\006\016\n\000\032\004\b\034\020\035\"\004\b\036\020\037R\034\020 \032\004\030\0010\004X\016¢\006\016\n\000\032\004\b!\020\006\"\004\b\"\020\b¨\006W"}, d2={"Lokhttp3/HttpUrl$Builder;", "", "()V", "encodedFragment", "", "getEncodedFragment$okhttp", "()Ljava/lang/String;", "setEncodedFragment$okhttp", "(Ljava/lang/String;)V", "encodedPassword", "getEncodedPassword$okhttp", "setEncodedPassword$okhttp", "encodedPathSegments", "", "getEncodedPathSegments$okhttp", "()Ljava/util/List;", "encodedQueryNamesAndValues", "getEncodedQueryNamesAndValues$okhttp", "setEncodedQueryNamesAndValues$okhttp", "(Ljava/util/List;)V", "encodedUsername", "getEncodedUsername$okhttp", "setEncodedUsername$okhttp", "host", "getHost$okhttp", "setHost$okhttp", "port", "", "getPort$okhttp", "()I", "setPort$okhttp", "(I)V", "scheme", "getScheme$okhttp", "setScheme$okhttp", "addEncodedPathSegment", "encodedPathSegment", "addEncodedPathSegments", "addEncodedQueryParameter", "encodedName", "encodedValue", "addPathSegment", "pathSegment", "addPathSegments", "pathSegments", "alreadyEncoded", "", "addQueryParameter", "name", "value", "build", "Lokhttp3/HttpUrl;", "effectivePort", "encodedPath", "encodedQuery", "fragment", "isDot", "input", "isDotDot", "parse", "base", "parse$okhttp", "password", "pop", "", "push", "pos", "limit", "addTrailingSlash", "query", "reencodeForUri", "reencodeForUri$okhttp", "removeAllCanonicalQueryParameters", "canonicalName", "removeAllEncodedQueryParameters", "removeAllQueryParameters", "removePathSegment", "index", "resolvePath", "startPos", "setEncodedPathSegment", "setEncodedQueryParameter", "setPathSegment", "setQueryParameter", "toString", "username", "Companion", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Builder
  {
    public static final Companion Companion = new Companion(null);
    public static final String INVALID_HOST = "Invalid URL host";
    private String encodedFragment;
    private String encodedPassword = "";
    private final List<String> encodedPathSegments;
    private List<String> encodedQueryNamesAndValues;
    private String encodedUsername = "";
    private String host;
    private int port = -1;
    private String scheme;
    
    public Builder()
    {
      List localList = (List)new ArrayList();
      this.encodedPathSegments = localList;
      localList.add("");
    }
    
    private final Builder addPathSegments(String paramString, boolean paramBoolean)
    {
      Builder localBuilder = (Builder)this;
      int i = 0;
      int j;
      do
      {
        j = Util.delimiterOffset(paramString, "/\\", i, paramString.length());
        boolean bool;
        if (j < paramString.length()) {
          bool = true;
        } else {
          bool = false;
        }
        localBuilder.push(paramString, i, j, bool, paramBoolean);
        j++;
        i = j;
      } while (j <= paramString.length());
      return (Builder)this;
    }
    
    private final int effectivePort()
    {
      int i = this.port;
      if (i == -1)
      {
        HttpUrl.Companion localCompanion = HttpUrl.Companion;
        String str = this.scheme;
        Intrinsics.checkNotNull(str);
        i = localCompanion.defaultPort(str);
      }
      return i;
    }
    
    private final boolean isDot(String paramString)
    {
      boolean bool3 = Intrinsics.areEqual(paramString, ".");
      boolean bool2 = true;
      boolean bool1 = bool2;
      if (!bool3) {
        if (StringsKt.equals(paramString, "%2e", true)) {
          bool1 = bool2;
        } else {
          bool1 = false;
        }
      }
      return bool1;
    }
    
    private final boolean isDotDot(String paramString)
    {
      boolean bool2 = Intrinsics.areEqual(paramString, "..");
      boolean bool1 = true;
      if ((!bool2) && (!StringsKt.equals(paramString, "%2e.", true)) && (!StringsKt.equals(paramString, ".%2e", true)) && (!StringsKt.equals(paramString, "%2e%2e", true))) {
        bool1 = false;
      }
      return bool1;
    }
    
    private final void pop()
    {
      List localList = this.encodedPathSegments;
      int i;
      if (((CharSequence)localList.remove(localList.size() - 1)).length() == 0) {
        i = 1;
      } else {
        i = 0;
      }
      if ((i != 0) && ((((Collection)this.encodedPathSegments).isEmpty() ^ true)))
      {
        localList = this.encodedPathSegments;
        localList.set(localList.size() - 1, "");
      }
      else
      {
        this.encodedPathSegments.add("");
      }
    }
    
    private final void push(String paramString, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
    {
      Object localObject = HttpUrl.Companion;
      paramString = HttpUrl.Companion.canonicalize$okhttp$default((HttpUrl.Companion)localObject, paramString, paramInt1, paramInt2, " \"<>^`{}|/\\?#", paramBoolean2, false, false, false, null, 240, null);
      Log5ECF72.a(paramString);
      LogE84000.a(paramString);
      Log229316.a(paramString);
      if (isDot(paramString)) {
        return;
      }
      if (isDotDot(paramString))
      {
        pop();
        return;
      }
      localObject = this.encodedPathSegments;
      if (((CharSequence)((List)localObject).get(((List)localObject).size() - 1)).length() == 0) {
        paramInt1 = 1;
      } else {
        paramInt1 = 0;
      }
      if (paramInt1 != 0)
      {
        localObject = this.encodedPathSegments;
        ((List)localObject).set(((List)localObject).size() - 1, paramString);
      }
      else
      {
        this.encodedPathSegments.add(paramString);
      }
      if (paramBoolean1) {
        this.encodedPathSegments.add("");
      }
    }
    
    private final void removeAllCanonicalQueryParameters(String paramString)
    {
      Object localObject = this.encodedQueryNamesAndValues;
      Intrinsics.checkNotNull(localObject);
      localObject = RangesKt.step(RangesKt.downTo(((List)localObject).size() - 2, 0), 2);
      int i = ((IntProgression)localObject).getFirst();
      int k = ((IntProgression)localObject).getLast();
      int j = ((IntProgression)localObject).getStep();
      if (j >= 0 ? i <= k : i >= k) {
        for (;;)
        {
          localObject = this.encodedQueryNamesAndValues;
          Intrinsics.checkNotNull(localObject);
          if (Intrinsics.areEqual(paramString, (String)((List)localObject).get(i)))
          {
            localObject = this.encodedQueryNamesAndValues;
            Intrinsics.checkNotNull(localObject);
            ((List)localObject).remove(i + 1);
            localObject = this.encodedQueryNamesAndValues;
            Intrinsics.checkNotNull(localObject);
            ((List)localObject).remove(i);
            localObject = this.encodedQueryNamesAndValues;
            Intrinsics.checkNotNull(localObject);
            if (((List)localObject).isEmpty())
            {
              paramString = (List)null;
              this.encodedQueryNamesAndValues = null;
              return;
            }
          }
          if (i == k) {
            break;
          }
          i += j;
        }
      }
    }
    
    private final void resolvePath(String paramString, int paramInt1, int paramInt2)
    {
      if (paramInt1 == paramInt2) {
        return;
      }
      int i = paramString.charAt(paramInt1);
      if ((i != 47) && (i != 92))
      {
        List localList = this.encodedPathSegments;
        localList.set(localList.size() - 1, "");
      }
      else
      {
        this.encodedPathSegments.clear();
        this.encodedPathSegments.add("");
        paramInt1++;
      }
      while (paramInt1 < paramInt2)
      {
        i = Util.delimiterOffset(paramString, "/\\", paramInt1, paramInt2);
        boolean bool;
        if (i < paramInt2) {
          bool = true;
        } else {
          bool = false;
        }
        push(paramString, paramInt1, i, bool, true);
        paramInt1 = i;
        if (bool) {
          paramInt1 = i + 1;
        }
      }
    }
    
    public final Builder addEncodedPathSegment(String paramString)
    {
      Intrinsics.checkNotNullParameter(paramString, "encodedPathSegment");
      Builder localBuilder = (Builder)this;
      int i = paramString.length();
      localBuilder.push(paramString, 0, i, false, true);
      return (Builder)this;
    }
    
    public final Builder addEncodedPathSegments(String paramString)
    {
      Intrinsics.checkNotNullParameter(paramString, "encodedPathSegments");
      return addPathSegments(paramString, true);
    }
    
    public final Builder addEncodedQueryParameter(String paramString1, String paramString2)
    {
      Intrinsics.checkNotNullParameter(paramString1, "encodedName");
      Builder localBuilder = (Builder)this;
      if (localBuilder.encodedQueryNamesAndValues == null) {
        localBuilder.encodedQueryNamesAndValues = ((List)new ArrayList());
      }
      List localList = localBuilder.encodedQueryNamesAndValues;
      Intrinsics.checkNotNull(localList);
      HttpUrl.Companion localCompanion = HttpUrl.Companion;
      paramString1 = HttpUrl.Companion.canonicalize$okhttp$default(localCompanion, paramString1, 0, 0, " \"'<>#&=", true, false, true, false, null, 211, null);
      Log5ECF72.a(paramString1);
      LogE84000.a(paramString1);
      Log229316.a(paramString1);
      localList.add(paramString1);
      localList = localBuilder.encodedQueryNamesAndValues;
      Intrinsics.checkNotNull(localList);
      if (paramString2 != null)
      {
        paramString1 = HttpUrl.Companion;
        paramString1 = HttpUrl.Companion.canonicalize$okhttp$default(paramString1, paramString2, 0, 0, " \"'<>#&=", true, false, true, false, null, 211, null);
        Log5ECF72.a(paramString1);
        LogE84000.a(paramString1);
        Log229316.a(paramString1);
      }
      else
      {
        paramString1 = null;
      }
      localList.add(paramString1);
      return (Builder)this;
    }
    
    public final Builder addPathSegment(String paramString)
    {
      Intrinsics.checkNotNullParameter(paramString, "pathSegment");
      ((Builder)this).push(paramString, 0, paramString.length(), false, false);
      return (Builder)this;
    }
    
    public final Builder addPathSegments(String paramString)
    {
      Intrinsics.checkNotNullParameter(paramString, "pathSegments");
      return addPathSegments(paramString, false);
    }
    
    public final Builder addQueryParameter(String paramString1, String paramString2)
    {
      Intrinsics.checkNotNullParameter(paramString1, "name");
      Object localObject = (Builder)this;
      if (((Builder)localObject).encodedQueryNamesAndValues == null) {
        ((Builder)localObject).encodedQueryNamesAndValues = ((List)new ArrayList());
      }
      List localList = ((Builder)localObject).encodedQueryNamesAndValues;
      Intrinsics.checkNotNull(localList);
      paramString1 = HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, paramString1, 0, 0, " !\"#$&'(),/:;<=>?@[]\\^`{|}~", false, false, true, false, null, 219, null);
      Log5ECF72.a(paramString1);
      LogE84000.a(paramString1);
      Log229316.a(paramString1);
      localList.add(paramString1);
      localObject = ((Builder)localObject).encodedQueryNamesAndValues;
      Intrinsics.checkNotNull(localObject);
      if (paramString2 != null)
      {
        paramString1 = HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, paramString2, 0, 0, " !\"#$&'(),/:;<=>?@[]\\^`{|}~", false, false, true, false, null, 219, null);
        Log5ECF72.a(paramString1);
        LogE84000.a(paramString1);
        Log229316.a(paramString1);
      }
      else
      {
        paramString1 = null;
      }
      ((List)localObject).add(paramString1);
      return (Builder)this;
    }
    
    public final HttpUrl build()
    {
      String str4 = this.scheme;
      if (str4 != null)
      {
        String str2 = HttpUrl.Companion.percentDecode$okhttp$default(HttpUrl.Companion, this.encodedUsername, 0, 0, false, 7, null);
        Log5ECF72.a(str2);
        LogE84000.a(str2);
        Log229316.a(str2);
        String str1 = HttpUrl.Companion.percentDecode$okhttp$default(HttpUrl.Companion, this.encodedPassword, 0, 0, false, 7, null);
        Log5ECF72.a(str1);
        LogE84000.a(str1);
        Log229316.a(str1);
        String str3 = this.host;
        if (str3 != null)
        {
          int i = effectivePort();
          Object localObject2 = (Iterable)this.encodedPathSegments;
          Object localObject1 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject2, 10));
          localObject2 = ((Iterable)localObject2).iterator();
          while (((Iterator)localObject2).hasNext())
          {
            localObject3 = (String)((Iterator)localObject2).next();
            localObject3 = HttpUrl.Companion.percentDecode$okhttp$default(HttpUrl.Companion, (String)localObject3, 0, 0, false, 7, null);
            Log5ECF72.a(localObject3);
            LogE84000.a(localObject3);
            Log229316.a(localObject3);
            ((Collection)localObject1).add(localObject3);
          }
          Object localObject3 = (List)localObject1;
          localObject1 = this.encodedQueryNamesAndValues;
          if (localObject1 != null)
          {
            localObject1 = (Iterable)localObject1;
            localObject2 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject1, 10));
            Iterator localIterator = ((Iterable)localObject1).iterator();
            while (localIterator.hasNext())
            {
              localObject1 = (String)localIterator.next();
              if (localObject1 != null)
              {
                localObject1 = HttpUrl.Companion.percentDecode$okhttp$default(HttpUrl.Companion, (String)localObject1, 0, 0, true, 3, null);
                Log5ECF72.a(localObject1);
                LogE84000.a(localObject1);
                Log229316.a(localObject1);
              }
              else
              {
                localObject1 = null;
              }
              ((Collection)localObject2).add(localObject1);
            }
            localObject1 = (List)localObject2;
          }
          else
          {
            localObject1 = null;
          }
          localObject2 = this.encodedFragment;
          if (localObject2 != null)
          {
            localObject2 = HttpUrl.Companion.percentDecode$okhttp$default(HttpUrl.Companion, (String)localObject2, 0, 0, false, 7, null);
            Log5ECF72.a(localObject2);
            LogE84000.a(localObject2);
            Log229316.a(localObject2);
          }
          else
          {
            localObject2 = null;
          }
          return new HttpUrl(str4, str2, str1, str3, i, (List)localObject3, (List)localObject1, (String)localObject2, toString());
        }
        throw ((Throwable)new IllegalStateException("host == null"));
      }
      throw ((Throwable)new IllegalStateException("scheme == null"));
    }
    
    public final Builder encodedFragment(String paramString)
    {
      Builder localBuilder = (Builder)this;
      if (paramString != null)
      {
        HttpUrl.Companion localCompanion = HttpUrl.Companion;
        paramString = HttpUrl.Companion.canonicalize$okhttp$default(localCompanion, paramString, 0, 0, "", true, false, false, true, null, 179, null);
        Log5ECF72.a(paramString);
        LogE84000.a(paramString);
        Log229316.a(paramString);
      }
      else
      {
        paramString = null;
      }
      localBuilder.encodedFragment = paramString;
      return (Builder)this;
    }
    
    public final Builder encodedPassword(String paramString)
    {
      Intrinsics.checkNotNullParameter(paramString, "encodedPassword");
      Builder localBuilder = (Builder)this;
      HttpUrl.Companion localCompanion = HttpUrl.Companion;
      paramString = HttpUrl.Companion.canonicalize$okhttp$default(localCompanion, paramString, 0, 0, " \"':;<=>@[]^`{}|/\\?#", true, false, false, false, null, 243, null);
      Log5ECF72.a(paramString);
      LogE84000.a(paramString);
      Log229316.a(paramString);
      localBuilder.encodedPassword = paramString;
      return (Builder)this;
    }
    
    public final Builder encodedPath(String paramString)
    {
      Intrinsics.checkNotNullParameter(paramString, "encodedPath");
      Builder localBuilder = (Builder)this;
      if (StringsKt.startsWith$default(paramString, "/", false, 2, null))
      {
        localBuilder.resolvePath(paramString, 0, paramString.length());
        return (Builder)this;
      }
      throw ((Throwable)new IllegalArgumentException(("unexpected encodedPath: " + paramString).toString()));
    }
    
    public final Builder encodedQuery(String paramString)
    {
      Builder localBuilder = (Builder)this;
      if (paramString != null)
      {
        HttpUrl.Companion localCompanion = HttpUrl.Companion;
        paramString = HttpUrl.Companion.canonicalize$okhttp$default(localCompanion, paramString, 0, 0, " \"'<>#", true, false, true, false, null, 211, null);
        Log5ECF72.a(paramString);
        LogE84000.a(paramString);
        Log229316.a(paramString);
        if (paramString != null)
        {
          paramString = HttpUrl.Companion.toQueryNamesAndValues$okhttp(paramString);
          break label62;
        }
      }
      paramString = null;
      label62:
      localBuilder.encodedQueryNamesAndValues = paramString;
      return (Builder)this;
    }
    
    public final Builder encodedUsername(String paramString)
    {
      Intrinsics.checkNotNullParameter(paramString, "encodedUsername");
      Builder localBuilder = (Builder)this;
      HttpUrl.Companion localCompanion = HttpUrl.Companion;
      paramString = HttpUrl.Companion.canonicalize$okhttp$default(localCompanion, paramString, 0, 0, " \"':;<=>@[]^`{}|/\\?#", true, false, false, false, null, 243, null);
      Log5ECF72.a(paramString);
      LogE84000.a(paramString);
      Log229316.a(paramString);
      localBuilder.encodedUsername = paramString;
      return (Builder)this;
    }
    
    public final Builder fragment(String paramString)
    {
      Builder localBuilder = (Builder)this;
      if (paramString != null)
      {
        paramString = HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, paramString, 0, 0, "", false, false, false, true, null, 187, null);
        Log5ECF72.a(paramString);
        LogE84000.a(paramString);
        Log229316.a(paramString);
      }
      else
      {
        paramString = null;
      }
      localBuilder.encodedFragment = paramString;
      return (Builder)this;
    }
    
    public final String getEncodedFragment$okhttp()
    {
      return this.encodedFragment;
    }
    
    public final String getEncodedPassword$okhttp()
    {
      return this.encodedPassword;
    }
    
    public final List<String> getEncodedPathSegments$okhttp()
    {
      return this.encodedPathSegments;
    }
    
    public final List<String> getEncodedQueryNamesAndValues$okhttp()
    {
      return this.encodedQueryNamesAndValues;
    }
    
    public final String getEncodedUsername$okhttp()
    {
      return this.encodedUsername;
    }
    
    public final String getHost$okhttp()
    {
      return this.host;
    }
    
    public final int getPort$okhttp()
    {
      return this.port;
    }
    
    public final String getScheme$okhttp()
    {
      return this.scheme;
    }
    
    public final Builder host(String paramString)
    {
      Intrinsics.checkNotNullParameter(paramString, "host");
      Builder localBuilder = (Builder)this;
      String str = HttpUrl.Companion.percentDecode$okhttp$default(HttpUrl.Companion, paramString, 0, 0, false, 7, null);
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      str = HostnamesKt.toCanonicalHost(str);
      Log5ECF72.a(str);
      LogE84000.a(str);
      if (str != null)
      {
        localBuilder.host = str;
        return (Builder)this;
      }
      throw ((Throwable)new IllegalArgumentException("unexpected host: " + paramString));
    }
    
    public final Builder parse$okhttp(HttpUrl paramHttpUrl, String paramString)
    {
      Intrinsics.checkNotNullParameter(paramString, "input");
      int i = Util.indexOfFirstNonAsciiWhitespace$default(paramString, 0, 0, 3, null);
      int j = Util.indexOfLastNonAsciiWhitespace$default(paramString, i, 0, 2, null);
      Object localObject2 = Companion;
      int i2 = Companion.access$schemeDelimiterOffset((Companion)localObject2, paramString, i, j);
      Object localObject1 = "(this as java.lang.Strin…ing(startIndex, endIndex)";
      int m = -1;
      int i1 = 1;
      if (i2 != -1)
      {
        if (StringsKt.startsWith(paramString, "https:", i, true))
        {
          this.scheme = "https";
          i += "https:".length();
        }
        else if (StringsKt.startsWith(paramString, "http:", i, true))
        {
          this.scheme = "http";
          i += "http:".length();
        }
        else
        {
          paramHttpUrl = new StringBuilder().append("Expected URL scheme 'http' or 'https' but was '");
          paramString = paramString.substring(0, i2);
          Intrinsics.checkNotNullExpressionValue(paramString, "(this as java.lang.Strin…ing(startIndex, endIndex)");
          throw ((Throwable)new IllegalArgumentException(paramString + "'"));
        }
      }
      else
      {
        if (paramHttpUrl == null) {
          break label1266;
        }
        this.scheme = paramHttpUrl.scheme();
      }
      int i3 = Companion.access$slashCount((Companion)localObject2, paramString, i, j);
      int i4 = 35;
      int k;
      int i5;
      int n;
      if ((i3 < 2) && (paramHttpUrl != null) && (!(Intrinsics.areEqual(paramHttpUrl.scheme(), this.scheme) ^ true)))
      {
        this.encodedUsername = paramHttpUrl.encodedUsername();
        this.encodedPassword = paramHttpUrl.encodedPassword();
        this.host = paramHttpUrl.host();
        this.port = paramHttpUrl.port();
        this.encodedPathSegments.clear();
        this.encodedPathSegments.addAll((Collection)paramHttpUrl.encodedPathSegments());
        if ((i == j) || (paramString.charAt(i) == '#')) {
          encodedQuery(paramHttpUrl.encodedQuery());
        }
        k = j;
        j = i;
        i = k;
      }
      else
      {
        i5 = i + i3;
        n = 0;
        k = 0;
        i = j;
        paramHttpUrl = (HttpUrl)localObject1;
        j = i1;
        i1 = i4;
        i4 = i5;
      }
      for (;;)
      {
        int i6 = Util.delimiterOffset(paramString, "@/\\?#", i4, i);
        if (i6 != i) {
          i5 = paramString.charAt(i6);
        } else {
          i5 = m;
        }
        switch (i5)
        {
        default: 
          localObject1 = paramHttpUrl;
          i5 = i;
          break;
        case 64: 
          if (k == 0)
          {
            m = Util.delimiterOffset(paramString, ':', i4, i6);
            localObject1 = HttpUrl.Companion;
            localObject1 = HttpUrl.Companion.canonicalize$okhttp$default((HttpUrl.Companion)localObject1, paramString, i4, m, " \"':;<=>@[]^`{}|/\\?#", true, false, false, false, null, 240, null);
            Log5ECF72.a(localObject1);
            LogE84000.a(localObject1);
            Log229316.a(localObject1);
            if (n != 0) {
              localObject1 = this.encodedUsername + "%40" + (String)localObject1;
            }
            this.encodedUsername = ((String)localObject1);
            if (m != i6)
            {
              k = 1;
              localObject1 = HttpUrl.Companion;
              localObject1 = HttpUrl.Companion.canonicalize$okhttp$default((HttpUrl.Companion)localObject1, paramString, m + 1, i6, " \"':;<=>@[]^`{}|/\\?#", true, false, false, false, null, 240, null);
              Log5ECF72.a(localObject1);
              LogE84000.a(localObject1);
              Log229316.a(localObject1);
              this.encodedPassword = ((String)localObject1);
            }
            n = 1;
          }
          else
          {
            localObject1 = new StringBuilder().append(this.encodedPassword).append("%40");
            localObject2 = HttpUrl.Companion;
            localObject2 = HttpUrl.Companion.canonicalize$okhttp$default((HttpUrl.Companion)localObject2, paramString, i4, i6, " \"':;<=>@[]^`{}|/\\?#", true, false, false, false, null, 240, null);
            Log5ECF72.a(localObject2);
            LogE84000.a(localObject2);
            Log229316.a(localObject2);
            this.encodedPassword = ((String)localObject2);
          }
          i4 = i6 + 1;
          m = i;
          localObject1 = paramHttpUrl;
          m = -1;
          i1 = 35;
          break;
        case -1: 
        case 35: 
        case 47: 
        case 63: 
        case 92: 
          localObject1 = Companion;
          m = Companion.access$portColonOffset((Companion)localObject1, paramString, i4, i6);
          if (m + 1 < i6)
          {
            localObject2 = HttpUrl.Companion.percentDecode$okhttp$default(HttpUrl.Companion, paramString, i4, m, false, 4, null);
            Log5ECF72.a(localObject2);
            LogE84000.a(localObject2);
            Log229316.a(localObject2);
            localObject2 = HostnamesKt.toCanonicalHost((String)localObject2);
            Log5ECF72.a(localObject2);
            LogE84000.a(localObject2);
            this.host = ((String)localObject2);
            k = Companion.access$parsePort((Companion)localObject1, paramString, m + 1, i6);
            this.port = k;
            if (k != -1) {
              k = j;
            } else {
              k = 0;
            }
            if (k == 0)
            {
              localObject1 = new StringBuilder().append("Invalid URL port: \"");
              paramString = paramString.substring(m + 1, i6);
              Intrinsics.checkNotNullExpressionValue(paramString, paramHttpUrl);
              throw ((Throwable)new IllegalArgumentException((paramString + '"').toString()));
            }
          }
          else
          {
            localObject1 = HttpUrl.Companion.percentDecode$okhttp$default(HttpUrl.Companion, paramString, i4, m, false, 4, null);
            Log5ECF72.a(localObject1);
            LogE84000.a(localObject1);
            Log229316.a(localObject1);
            localObject1 = HostnamesKt.toCanonicalHost((String)localObject1);
            Log5ECF72.a(localObject1);
            LogE84000.a(localObject1);
            this.host = ((String)localObject1);
            localObject2 = HttpUrl.Companion;
            localObject1 = this.scheme;
            Intrinsics.checkNotNull(localObject1);
            this.port = ((HttpUrl.Companion)localObject2).defaultPort((String)localObject1);
          }
          if (this.host == null) {
            j = 0;
          }
          if (j != 0)
          {
            j = i6;
            k = Util.delimiterOffset(paramString, "?#", j, i);
            resolvePath(paramString, j, k);
            if ((k < i) && (paramString.charAt(k) == '?'))
            {
              j = Util.delimiterOffset(paramString, '#', k, i);
              paramHttpUrl = HttpUrl.Companion;
              localObject1 = HttpUrl.Companion;
              localObject1 = HttpUrl.Companion.canonicalize$okhttp$default((HttpUrl.Companion)localObject1, paramString, k + 1, j, " \"'<>#", true, false, true, false, null, 208, null);
              Log5ECF72.a(localObject1);
              LogE84000.a(localObject1);
              Log229316.a(localObject1);
              this.encodedQueryNamesAndValues = paramHttpUrl.toQueryNamesAndValues$okhttp((String)localObject1);
            }
            else
            {
              j = k;
            }
            if ((j < i) && (paramString.charAt(j) == '#'))
            {
              paramHttpUrl = HttpUrl.Companion;
              paramHttpUrl = HttpUrl.Companion.canonicalize$okhttp$default(paramHttpUrl, paramString, j + 1, i, "", true, false, false, true, null, 176, null);
              Log5ECF72.a(paramHttpUrl);
              LogE84000.a(paramHttpUrl);
              Log229316.a(paramHttpUrl);
              this.encodedFragment = paramHttpUrl;
            }
            return this;
          }
          localObject1 = new StringBuilder().append("Invalid URL host: \"");
          paramString = paramString.substring(i4, m);
          Intrinsics.checkNotNullExpressionValue(paramString, paramHttpUrl);
          throw ((Throwable)new IllegalArgumentException((paramString + '"').toString()));
        }
      }
      label1266:
      throw ((Throwable)new IllegalArgumentException("Expected URL scheme 'http' or 'https' but no colon was found"));
    }
    
    public final Builder password(String paramString)
    {
      Intrinsics.checkNotNullParameter(paramString, "password");
      Builder localBuilder = (Builder)this;
      paramString = HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, paramString, 0, 0, " \"':;<=>@[]^`{}|/\\?#", false, false, false, false, null, 251, null);
      Log5ECF72.a(paramString);
      LogE84000.a(paramString);
      Log229316.a(paramString);
      localBuilder.encodedPassword = paramString;
      return (Builder)this;
    }
    
    public final Builder port(int paramInt)
    {
      Builder localBuilder = (Builder)this;
      int i = 1;
      if ((1 > paramInt) || (65535 < paramInt)) {
        i = 0;
      }
      if (i != 0)
      {
        localBuilder.port = paramInt;
        return (Builder)this;
      }
      throw ((Throwable)new IllegalArgumentException(("unexpected port: " + paramInt).toString()));
    }
    
    public final Builder query(String paramString)
    {
      Builder localBuilder = (Builder)this;
      if (paramString != null)
      {
        paramString = HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, paramString, 0, 0, " \"'<>#", false, false, true, false, null, 219, null);
        Log5ECF72.a(paramString);
        LogE84000.a(paramString);
        Log229316.a(paramString);
        if (paramString != null)
        {
          paramString = HttpUrl.Companion.toQueryNamesAndValues$okhttp(paramString);
          break label60;
        }
      }
      paramString = null;
      label60:
      localBuilder.encodedQueryNamesAndValues = paramString;
      return (Builder)this;
    }
    
    public final Builder reencodeForUri$okhttp()
    {
      Builder localBuilder = (Builder)this;
      Object localObject1 = localBuilder.host;
      Object localObject2 = null;
      if (localObject1 != null)
      {
        localObject1 = (CharSequence)localObject1;
        localObject1 = new Regex("[\"<>^`{|}]").replace((CharSequence)localObject1, "");
      }
      else
      {
        localObject1 = null;
      }
      localBuilder.host = ((String)localObject1);
      int k = localBuilder.encodedPathSegments.size();
      int j = 0;
      HttpUrl.Companion localCompanion;
      for (int i = 0; i < k; i++)
      {
        localObject1 = localBuilder.encodedPathSegments;
        localCompanion = HttpUrl.Companion;
        localObject3 = (String)localBuilder.encodedPathSegments.get(i);
        localObject3 = HttpUrl.Companion.canonicalize$okhttp$default(localCompanion, (String)localObject3, 0, 0, "[]", true, true, false, false, null, 227, null);
        Log5ECF72.a(localObject3);
        LogE84000.a(localObject3);
        Log229316.a(localObject3);
        ((List)localObject1).set(i, localObject3);
      }
      Object localObject3 = localBuilder.encodedQueryNamesAndValues;
      if (localObject3 != null)
      {
        k = ((List)localObject3).size();
        for (i = j; i < k; i++)
        {
          localObject1 = (String)((List)localObject3).get(i);
          if (localObject1 != null)
          {
            localCompanion = HttpUrl.Companion;
            localObject1 = HttpUrl.Companion.canonicalize$okhttp$default(localCompanion, (String)localObject1, 0, 0, "\\^`{|}", true, true, true, false, null, 195, null);
            Log5ECF72.a(localObject1);
            LogE84000.a(localObject1);
            Log229316.a(localObject1);
          }
          else
          {
            localObject1 = null;
          }
          ((List)localObject3).set(i, localObject1);
        }
      }
      localObject3 = localBuilder.encodedFragment;
      localObject1 = localObject2;
      if (localObject3 != null)
      {
        localObject1 = HttpUrl.Companion;
        localObject1 = HttpUrl.Companion.canonicalize$okhttp$default((HttpUrl.Companion)localObject1, (String)localObject3, 0, 0, " \"#<>\\^`{|}", true, true, false, true, null, 163, null);
        Log5ECF72.a(localObject1);
        LogE84000.a(localObject1);
        Log229316.a(localObject1);
      }
      localBuilder.encodedFragment = ((String)localObject1);
      return (Builder)this;
    }
    
    public final Builder removeAllEncodedQueryParameters(String paramString)
    {
      Intrinsics.checkNotNullParameter(paramString, "encodedName");
      Builder localBuilder = (Builder)this;
      if (localBuilder.encodedQueryNamesAndValues == null) {
        return localBuilder;
      }
      HttpUrl.Companion localCompanion = HttpUrl.Companion;
      paramString = HttpUrl.Companion.canonicalize$okhttp$default(localCompanion, paramString, 0, 0, " \"'<>#&=", true, false, true, false, null, 211, null);
      Log5ECF72.a(paramString);
      LogE84000.a(paramString);
      Log229316.a(paramString);
      localBuilder.removeAllCanonicalQueryParameters(paramString);
      return (Builder)this;
    }
    
    public final Builder removeAllQueryParameters(String paramString)
    {
      Intrinsics.checkNotNullParameter(paramString, "name");
      Builder localBuilder = (Builder)this;
      if (localBuilder.encodedQueryNamesAndValues == null) {
        return localBuilder;
      }
      paramString = HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, paramString, 0, 0, " !\"#$&'(),/:;<=>?@[]\\^`{|}~", false, false, true, false, null, 219, null);
      Log5ECF72.a(paramString);
      LogE84000.a(paramString);
      Log229316.a(paramString);
      localBuilder.removeAllCanonicalQueryParameters(paramString);
      return (Builder)this;
    }
    
    public final Builder removePathSegment(int paramInt)
    {
      Builder localBuilder = (Builder)this;
      localBuilder.encodedPathSegments.remove(paramInt);
      if (localBuilder.encodedPathSegments.isEmpty()) {
        localBuilder.encodedPathSegments.add("");
      }
      return (Builder)this;
    }
    
    public final Builder scheme(String paramString)
    {
      Intrinsics.checkNotNullParameter(paramString, "scheme");
      Builder localBuilder = (Builder)this;
      if (StringsKt.equals(paramString, "http", true))
      {
        localBuilder.scheme = "http";
      }
      else
      {
        if (!StringsKt.equals(paramString, "https", true)) {
          break label56;
        }
        localBuilder.scheme = "https";
      }
      return (Builder)this;
      label56:
      throw ((Throwable)new IllegalArgumentException("unexpected scheme: " + paramString));
    }
    
    public final void setEncodedFragment$okhttp(String paramString)
    {
      this.encodedFragment = paramString;
    }
    
    public final void setEncodedPassword$okhttp(String paramString)
    {
      Intrinsics.checkNotNullParameter(paramString, "<set-?>");
      this.encodedPassword = paramString;
    }
    
    public final Builder setEncodedPathSegment(int paramInt, String paramString)
    {
      Intrinsics.checkNotNullParameter(paramString, "encodedPathSegment");
      Builder localBuilder = (Builder)this;
      Object localObject = HttpUrl.Companion;
      localObject = HttpUrl.Companion.canonicalize$okhttp$default((HttpUrl.Companion)localObject, paramString, 0, 0, " \"<>^`{}|/\\?#", true, false, false, false, null, 243, null);
      Log5ECF72.a(localObject);
      LogE84000.a(localObject);
      Log229316.a(localObject);
      localBuilder.encodedPathSegments.set(paramInt, localObject);
      if ((!localBuilder.isDot((String)localObject)) && (!localBuilder.isDotDot((String)localObject))) {
        paramInt = 1;
      } else {
        paramInt = 0;
      }
      if (paramInt != 0) {
        return (Builder)this;
      }
      throw ((Throwable)new IllegalArgumentException(("unexpected path segment: " + paramString).toString()));
    }
    
    public final void setEncodedQueryNamesAndValues$okhttp(List<String> paramList)
    {
      this.encodedQueryNamesAndValues = paramList;
    }
    
    public final Builder setEncodedQueryParameter(String paramString1, String paramString2)
    {
      Intrinsics.checkNotNullParameter(paramString1, "encodedName");
      Builder localBuilder = (Builder)this;
      localBuilder.removeAllEncodedQueryParameters(paramString1);
      localBuilder.addEncodedQueryParameter(paramString1, paramString2);
      return (Builder)this;
    }
    
    public final void setEncodedUsername$okhttp(String paramString)
    {
      Intrinsics.checkNotNullParameter(paramString, "<set-?>");
      this.encodedUsername = paramString;
    }
    
    public final void setHost$okhttp(String paramString)
    {
      this.host = paramString;
    }
    
    public final Builder setPathSegment(int paramInt, String paramString)
    {
      Intrinsics.checkNotNullParameter(paramString, "pathSegment");
      Builder localBuilder = (Builder)this;
      String str = HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, paramString, 0, 0, " \"<>^`{}|/\\?#", false, false, false, false, null, 251, null);
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      int i;
      if ((!localBuilder.isDot(str)) && (!localBuilder.isDotDot(str))) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        localBuilder.encodedPathSegments.set(paramInt, str);
        return (Builder)this;
      }
      throw ((Throwable)new IllegalArgumentException(("unexpected path segment: " + paramString).toString()));
    }
    
    public final void setPort$okhttp(int paramInt)
    {
      this.port = paramInt;
    }
    
    public final Builder setQueryParameter(String paramString1, String paramString2)
    {
      Intrinsics.checkNotNullParameter(paramString1, "name");
      Builder localBuilder = (Builder)this;
      localBuilder.removeAllQueryParameters(paramString1);
      localBuilder.addQueryParameter(paramString1, paramString2);
      return (Builder)this;
    }
    
    public final void setScheme$okhttp(String paramString)
    {
      this.scheme = paramString;
    }
    
    public String toString()
    {
      Object localObject1 = new StringBuilder();
      Object localObject2 = this.scheme;
      if (localObject2 != null)
      {
        ((StringBuilder)localObject1).append((String)localObject2);
        ((StringBuilder)localObject1).append("://");
      }
      else
      {
        ((StringBuilder)localObject1).append("//");
      }
      int i = ((CharSequence)this.encodedUsername).length();
      int j = 1;
      if (i > 0) {
        i = 1;
      } else {
        i = 0;
      }
      if (i == 0)
      {
        if (((CharSequence)this.encodedPassword).length() > 0) {
          i = 1;
        } else {
          i = 0;
        }
        if (i == 0) {}
      }
      else
      {
        ((StringBuilder)localObject1).append(this.encodedUsername);
        if (((CharSequence)this.encodedPassword).length() > 0) {
          i = j;
        } else {
          i = 0;
        }
        if (i != 0)
        {
          ((StringBuilder)localObject1).append(':');
          ((StringBuilder)localObject1).append(this.encodedPassword);
        }
        ((StringBuilder)localObject1).append('@');
      }
      localObject2 = this.host;
      if (localObject2 != null)
      {
        Intrinsics.checkNotNull(localObject2);
        if (StringsKt.contains$default((CharSequence)localObject2, ':', false, 2, null))
        {
          ((StringBuilder)localObject1).append('[');
          ((StringBuilder)localObject1).append(this.host);
          ((StringBuilder)localObject1).append(']');
        }
        else
        {
          ((StringBuilder)localObject1).append(this.host);
        }
      }
      Object localObject3;
      if ((this.port != -1) || (this.scheme != null))
      {
        i = effectivePort();
        if (this.scheme != null)
        {
          localObject2 = HttpUrl.Companion;
          localObject3 = this.scheme;
          Intrinsics.checkNotNull(localObject3);
          if (i == ((HttpUrl.Companion)localObject2).defaultPort((String)localObject3)) {}
        }
        else
        {
          ((StringBuilder)localObject1).append(':');
          ((StringBuilder)localObject1).append(i);
        }
      }
      HttpUrl.Companion.toPathString$okhttp(this.encodedPathSegments, (StringBuilder)localObject1);
      if (this.encodedQueryNamesAndValues != null)
      {
        ((StringBuilder)localObject1).append('?');
        localObject3 = HttpUrl.Companion;
        localObject2 = this.encodedQueryNamesAndValues;
        Intrinsics.checkNotNull(localObject2);
        ((HttpUrl.Companion)localObject3).toQueryString$okhttp((List)localObject2, (StringBuilder)localObject1);
      }
      if (this.encodedFragment != null)
      {
        ((StringBuilder)localObject1).append('#');
        ((StringBuilder)localObject1).append(this.encodedFragment);
      }
      localObject1 = ((StringBuilder)localObject1).toString();
      Intrinsics.checkNotNullExpressionValue(localObject1, "StringBuilder().apply(builderAction).toString()");
      return (String)localObject1;
    }
    
    public final Builder username(String paramString)
    {
      Intrinsics.checkNotNullParameter(paramString, "username");
      Builder localBuilder = (Builder)this;
      paramString = HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, paramString, 0, 0, " \"':;<=>@[]^`{}|/\\?#", false, false, false, false, null, 251, null);
      Log5ECF72.a(paramString);
      LogE84000.a(paramString);
      Log229316.a(paramString);
      localBuilder.encodedUsername = paramString;
      return (Builder)this;
    }
    
    @Metadata(bv={1, 0, 3}, d1={"\000\032\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\016\n\000\n\002\020\b\n\002\b\007\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J \020\005\032\0020\0062\006\020\007\032\0020\0042\006\020\b\032\0020\0062\006\020\t\032\0020\006H\002J \020\n\032\0020\0062\006\020\007\032\0020\0042\006\020\b\032\0020\0062\006\020\t\032\0020\006H\002J \020\013\032\0020\0062\006\020\007\032\0020\0042\006\020\b\032\0020\0062\006\020\t\032\0020\006H\002J\034\020\f\032\0020\006*\0020\0042\006\020\b\032\0020\0062\006\020\t\032\0020\006H\002R\016\020\003\032\0020\004XT¢\006\002\n\000¨\006\r"}, d2={"Lokhttp3/HttpUrl$Builder$Companion;", "", "()V", "INVALID_HOST", "", "parsePort", "", "input", "pos", "limit", "portColonOffset", "schemeDelimiterOffset", "slashCount", "okhttp"}, k=1, mv={1, 4, 0})
    public static final class Companion
    {
      private final int parsePort(String paramString, int paramInt1, int paramInt2)
      {
        int i = -1;
        try
        {
          paramString = HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, paramString, paramInt1, paramInt2, "", false, false, false, false, null, 248, null);
          Log5ECF72.a(paramString);
          LogE84000.a(paramString);
          Log229316.a(paramString);
          paramInt2 = Integer.parseInt(paramString);
          if (1 > paramInt2)
          {
            paramInt1 = i;
          }
          else
          {
            paramInt1 = i;
            if (65535 >= paramInt2) {
              paramInt1 = paramInt2;
            }
          }
        }
        catch (NumberFormatException paramString)
        {
          paramInt1 = i;
        }
        return paramInt1;
      }
      
      private final int portColonOffset(String paramString, int paramInt1, int paramInt2)
      {
        while (paramInt1 < paramInt2)
        {
          int i = paramInt1;
          switch (paramString.charAt(paramInt1))
          {
          default: 
            break;
          case '[': 
            for (;;)
            {
              i++;
              paramInt1 = i;
              if (i >= paramInt2) {
                break;
              }
              if (paramString.charAt(i) == ']')
              {
                paramInt1 = i;
                break;
              }
            }
          case ':': 
            return paramInt1;
          }
          paramInt1++;
        }
        return paramInt2;
      }
      
      private final int schemeDelimiterOffset(String paramString, int paramInt1, int paramInt2)
      {
        int i = -1;
        if (paramInt2 - paramInt1 < 2) {
          return -1;
        }
        int j = paramString.charAt(paramInt1);
        if (((Intrinsics.compare(j, 97) >= 0) && (Intrinsics.compare(j, 122) <= 0)) || ((Intrinsics.compare(j, 65) >= 0) && (Intrinsics.compare(j, 90) <= 0)))
        {
          paramInt1++;
          while (paramInt1 < paramInt2)
          {
            j = paramString.charAt(paramInt1);
            if ((97 > j) || (122 < j)) {
              for (;;)
              {
                if (((65 > j) || (90 < j)) && ((48 > j) || (57 < j)) && (j != 43) && (j != 45)) {
                  if (j != 46) {
                    break;
                  }
                }
              }
            }
            paramInt1++;
            continue;
            if (j != 58) {
              paramInt1 = i;
            }
            return paramInt1;
          }
          return -1;
        }
        return -1;
      }
      
      private final int slashCount(String paramString, int paramInt1, int paramInt2)
      {
        int i = 0;
        while (paramInt1 < paramInt2)
        {
          int j = paramString.charAt(paramInt1);
          if ((j != 92) && (j != 47)) {
            break;
          }
          i++;
          paramInt1++;
        }
        return i;
      }
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000p\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\016\n\002\b\003\n\002\020\031\n\002\b\t\n\002\020\b\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\007\n\002\020\013\n\002\b\004\n\002\030\002\n\002\b\007\n\002\020\002\n\002\020 \n\000\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020!\n\002\b\004\n\002\030\002\n\002\b\004\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J\020\020\021\032\0020\0222\006\020\023\032\0020\004H\007J\027\020\024\032\004\030\0010\0252\006\020\026\032\0020\027H\007¢\006\002\b\030J\027\020\024\032\004\030\0010\0252\006\020\031\032\0020\032H\007¢\006\002\b\030J\025\020\024\032\0020\0252\006\020\031\032\0020\004H\007¢\006\002\b\030J\027\020\033\032\004\030\0010\0252\006\020\031\032\0020\004H\007¢\006\002\b\034Ja\020\035\032\0020\004*\0020\0042\b\b\002\020\036\032\0020\0222\b\b\002\020\037\032\0020\0222\006\020 \032\0020\0042\b\b\002\020!\032\0020\"2\b\b\002\020#\032\0020\"2\b\b\002\020$\032\0020\"2\b\b\002\020%\032\0020\"2\n\b\002\020&\032\004\030\0010'H\000¢\006\002\b(J\034\020)\032\0020\"*\0020\0042\006\020\036\032\0020\0222\006\020\037\032\0020\022H\002J/\020*\032\0020\004*\0020\0042\b\b\002\020\036\032\0020\0222\b\b\002\020\037\032\0020\0222\b\b\002\020$\032\0020\"H\000¢\006\002\b+J\021\020,\032\0020\025*\0020\004H\007¢\006\002\b\024J\023\020-\032\004\030\0010\025*\0020\027H\007¢\006\002\b\024J\023\020-\032\004\030\0010\025*\0020\032H\007¢\006\002\b\024J\023\020-\032\004\030\0010\025*\0020\004H\007¢\006\002\b\033J#\020.\032\0020/*\b\022\004\022\0020\004002\n\0201\032\00602j\002`3H\000¢\006\002\b4J\031\0205\032\n\022\006\022\004\030\0010\00406*\0020\004H\000¢\006\002\b7J%\0208\032\0020/*\n\022\006\022\004\030\0010\004002\n\0201\032\00602j\002`3H\000¢\006\002\b9JV\020:\032\0020/*\0020;2\006\020<\032\0020\0042\006\020\036\032\0020\0222\006\020\037\032\0020\0222\006\020 \032\0020\0042\006\020!\032\0020\"2\006\020#\032\0020\"2\006\020$\032\0020\"2\006\020%\032\0020\"2\b\020&\032\004\030\0010'H\002J,\020=\032\0020/*\0020;2\006\020>\032\0020\0042\006\020\036\032\0020\0222\006\020\037\032\0020\0222\006\020$\032\0020\"H\002R\016\020\003\032\0020\004XT¢\006\002\n\000R\016\020\005\032\0020\004XT¢\006\002\n\000R\016\020\006\032\0020\004XT¢\006\002\n\000R\016\020\007\032\0020\bX\004¢\006\002\n\000R\016\020\t\032\0020\004XT¢\006\002\n\000R\016\020\n\032\0020\004XT¢\006\002\n\000R\016\020\013\032\0020\004XT¢\006\002\n\000R\016\020\f\032\0020\004XT¢\006\002\n\000R\016\020\r\032\0020\004XT¢\006\002\n\000R\016\020\016\032\0020\004XT¢\006\002\n\000R\016\020\017\032\0020\004XT¢\006\002\n\000R\016\020\020\032\0020\004XT¢\006\002\n\000¨\006?"}, d2={"Lokhttp3/HttpUrl$Companion;", "", "()V", "FORM_ENCODE_SET", "", "FRAGMENT_ENCODE_SET", "FRAGMENT_ENCODE_SET_URI", "HEX_DIGITS", "", "PASSWORD_ENCODE_SET", "PATH_SEGMENT_ENCODE_SET", "PATH_SEGMENT_ENCODE_SET_URI", "QUERY_COMPONENT_ENCODE_SET", "QUERY_COMPONENT_ENCODE_SET_URI", "QUERY_COMPONENT_REENCODE_SET", "QUERY_ENCODE_SET", "USERNAME_ENCODE_SET", "defaultPort", "", "scheme", "get", "Lokhttp3/HttpUrl;", "uri", "Ljava/net/URI;", "-deprecated_get", "url", "Ljava/net/URL;", "parse", "-deprecated_parse", "canonicalize", "pos", "limit", "encodeSet", "alreadyEncoded", "", "strict", "plusIsSpace", "unicodeAllowed", "charset", "Ljava/nio/charset/Charset;", "canonicalize$okhttp", "isPercentEncoded", "percentDecode", "percentDecode$okhttp", "toHttpUrl", "toHttpUrlOrNull", "toPathString", "", "", "out", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "toPathString$okhttp", "toQueryNamesAndValues", "", "toQueryNamesAndValues$okhttp", "toQueryString", "toQueryString$okhttp", "writeCanonicalized", "Lokio/Buffer;", "input", "writePercentDecoded", "encoded", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Companion
  {
    private final boolean isPercentEncoded(String paramString, int paramInt1, int paramInt2)
    {
      boolean bool;
      if ((paramInt1 + 2 < paramInt2) && (paramString.charAt(paramInt1) == '%') && (Util.parseHexDigit(paramString.charAt(paramInt1 + 1)) != -1) && (Util.parseHexDigit(paramString.charAt(paramInt1 + 2)) != -1)) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    private final void writeCanonicalized(Buffer paramBuffer, String paramString1, int paramInt1, int paramInt2, String paramString2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, Charset paramCharset)
    {
      Object localObject1 = (Buffer)null;
      while (paramInt1 < paramInt2) {
        if (paramString1 != null)
        {
          int i = paramString1.codePointAt(paramInt1);
          Object localObject3;
          if (paramBoolean1)
          {
            localObject3 = localObject1;
            if (i != 9)
            {
              localObject3 = localObject1;
              if (i != 10)
              {
                localObject3 = localObject1;
                if (i != 12)
                {
                  localObject3 = localObject1;
                  if (i == 13) {}
                }
              }
            }
          }
          else
          {
            Object localObject2;
            if ((i == 43) && (paramBoolean3))
            {
              if (paramBoolean1) {
                localObject2 = "+";
              } else {
                localObject2 = "%2B";
              }
              paramBuffer.writeUtf8((String)localObject2);
              localObject3 = localObject1;
            }
            else if ((i >= 32) && (i != 127) && ((i < 128) || (paramBoolean4)) && (!StringsKt.contains$default((CharSequence)paramString2, (char)i, false, 2, null)) && ((i != 37) || ((paramBoolean1) && ((!paramBoolean2) || (((Companion)this).isPercentEncoded(paramString1, paramInt1, paramInt2))))))
            {
              paramBuffer.writeUtf8CodePoint(i);
              localObject3 = localObject1;
            }
            else
            {
              localObject2 = localObject1;
              if (localObject1 == null) {
                localObject2 = new Buffer();
              }
              if ((paramCharset != null) && (!Intrinsics.areEqual(paramCharset, StandardCharsets.UTF_8))) {
                ((Buffer)localObject2).writeString(paramString1, paramInt1, Character.charCount(i) + paramInt1, paramCharset);
              } else {
                ((Buffer)localObject2).writeUtf8CodePoint(i);
              }
              for (;;)
              {
                localObject3 = localObject2;
                if (((Buffer)localObject2).exhausted()) {
                  break;
                }
                int j = ((Buffer)localObject2).readByte() & 0xFF;
                paramBuffer.writeByte(37);
                paramBuffer.writeByte(HttpUrl.access$getHEX_DIGITS$cp()[(j >> 4 & 0xF)]);
                paramBuffer.writeByte(HttpUrl.access$getHEX_DIGITS$cp()[(j & 0xF)]);
              }
            }
          }
          paramInt1 += Character.charCount(i);
          localObject1 = localObject3;
        }
        else
        {
          throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
        }
      }
    }
    
    private final void writePercentDecoded(Buffer paramBuffer, String paramString, int paramInt1, int paramInt2, boolean paramBoolean)
    {
      while (paramInt1 < paramInt2) {
        if (paramString != null)
        {
          int k = paramString.codePointAt(paramInt1);
          if ((k == 37) && (paramInt1 + 2 < paramInt2))
          {
            int j = Util.parseHexDigit(paramString.charAt(paramInt1 + 1));
            int i = Util.parseHexDigit(paramString.charAt(paramInt1 + 2));
            if ((j != -1) && (i != -1))
            {
              paramBuffer.writeByte((j << 4) + i);
              paramInt1 = paramInt1 + 2 + Character.charCount(k);
            }
          }
          else if ((k == 43) && (paramBoolean))
          {
            paramBuffer.writeByte(32);
            paramInt1++;
            continue;
          }
          paramBuffer.writeUtf8CodePoint(k);
          paramInt1 += Character.charCount(k);
        }
        else
        {
          throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
        }
      }
    }
    
    @Deprecated(level=DeprecationLevel.ERROR, message="moved to extension function", replaceWith=@ReplaceWith(expression="url.toHttpUrl()", imports={"okhttp3.HttpUrl.Companion.toHttpUrl"}))
    public final HttpUrl -deprecated_get(String paramString)
    {
      Intrinsics.checkNotNullParameter(paramString, "url");
      return ((Companion)this).get(paramString);
    }
    
    @Deprecated(level=DeprecationLevel.ERROR, message="moved to extension function", replaceWith=@ReplaceWith(expression="uri.toHttpUrlOrNull()", imports={"okhttp3.HttpUrl.Companion.toHttpUrlOrNull"}))
    public final HttpUrl -deprecated_get(URI paramURI)
    {
      Intrinsics.checkNotNullParameter(paramURI, "uri");
      return ((Companion)this).get(paramURI);
    }
    
    @Deprecated(level=DeprecationLevel.ERROR, message="moved to extension function", replaceWith=@ReplaceWith(expression="url.toHttpUrlOrNull()", imports={"okhttp3.HttpUrl.Companion.toHttpUrlOrNull"}))
    public final HttpUrl -deprecated_get(URL paramURL)
    {
      Intrinsics.checkNotNullParameter(paramURL, "url");
      return ((Companion)this).get(paramURL);
    }
    
    @Deprecated(level=DeprecationLevel.ERROR, message="moved to extension function", replaceWith=@ReplaceWith(expression="url.toHttpUrlOrNull()", imports={"okhttp3.HttpUrl.Companion.toHttpUrlOrNull"}))
    public final HttpUrl -deprecated_parse(String paramString)
    {
      Intrinsics.checkNotNullParameter(paramString, "url");
      return ((Companion)this).parse(paramString);
    }
    
    public final String canonicalize$okhttp(String paramString1, int paramInt1, int paramInt2, String paramString2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, Charset paramCharset)
    {
      Intrinsics.checkNotNullParameter(paramString1, "$this$canonicalize");
      Intrinsics.checkNotNullParameter(paramString2, "encodeSet");
      int i = paramInt1;
      while (i < paramInt2)
      {
        int j = paramString1.codePointAt(i);
        if ((j >= 32) && (j != 127) && ((j < 128) || (paramBoolean4)) && (!StringsKt.contains$default((CharSequence)paramString2, (char)j, false, 2, null)) && ((j != 37) || ((paramBoolean1) && ((!paramBoolean2) || (((Companion)this).isPercentEncoded(paramString1, i, paramInt2))))) && ((j != 43) || (!paramBoolean3)))
        {
          i += Character.charCount(j);
        }
        else
        {
          Buffer localBuffer = new Buffer();
          localBuffer.writeUtf8(paramString1, paramInt1, i);
          Companion localCompanion = (Companion)this;
          localCompanion.writeCanonicalized(localBuffer, paramString1, i, paramInt2, paramString2, paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4, paramCharset);
          return localBuffer.readUtf8();
        }
      }
      paramString1 = paramString1.substring(paramInt1, paramInt2);
      Intrinsics.checkNotNullExpressionValue(paramString1, "(this as java.lang.Strin…ing(startIndex, endIndex)");
      return paramString1;
    }
    
    @JvmStatic
    public final int defaultPort(String paramString)
    {
      Intrinsics.checkNotNullParameter(paramString, "scheme");
      switch (paramString.hashCode())
      {
      default: 
        break;
      case 99617003: 
        if (paramString.equals("https")) {
          i = 443;
        }
        break;
      case 3213448: 
        if (paramString.equals("http")) {
          i = 80;
        }
        break;
      }
      int i = -1;
      return i;
    }
    
    @JvmStatic
    public final HttpUrl get(String paramString)
    {
      Intrinsics.checkNotNullParameter(paramString, "$this$toHttpUrl");
      return new HttpUrl.Builder().parse$okhttp(null, paramString).build();
    }
    
    @JvmStatic
    public final HttpUrl get(URI paramURI)
    {
      Intrinsics.checkNotNullParameter(paramURI, "$this$toHttpUrlOrNull");
      Companion localCompanion = (Companion)this;
      paramURI = paramURI.toString();
      Intrinsics.checkNotNullExpressionValue(paramURI, "toString()");
      return localCompanion.parse(paramURI);
    }
    
    @JvmStatic
    public final HttpUrl get(URL paramURL)
    {
      Intrinsics.checkNotNullParameter(paramURL, "$this$toHttpUrlOrNull");
      Companion localCompanion = (Companion)this;
      paramURL = paramURL.toString();
      Intrinsics.checkNotNullExpressionValue(paramURL, "toString()");
      return localCompanion.parse(paramURL);
    }
    
    @JvmStatic
    public final HttpUrl parse(String paramString)
    {
      Intrinsics.checkNotNullParameter(paramString, "$this$toHttpUrlOrNull");
      try
      {
        paramString = ((Companion)this).get(paramString);
      }
      catch (IllegalArgumentException paramString)
      {
        paramString = null;
      }
      return paramString;
    }
    
    public final String percentDecode$okhttp(String paramString, int paramInt1, int paramInt2, boolean paramBoolean)
    {
      Intrinsics.checkNotNullParameter(paramString, "$this$percentDecode");
      int i = paramInt1;
      while (i < paramInt2)
      {
        int j = paramString.charAt(i);
        if ((j != 37) && ((j != 43) || (!paramBoolean)))
        {
          i++;
        }
        else
        {
          Buffer localBuffer = new Buffer();
          localBuffer.writeUtf8(paramString, paramInt1, i);
          ((Companion)this).writePercentDecoded(localBuffer, paramString, i, paramInt2, paramBoolean);
          return localBuffer.readUtf8();
        }
      }
      paramString = paramString.substring(paramInt1, paramInt2);
      Intrinsics.checkNotNullExpressionValue(paramString, "(this as java.lang.Strin…ing(startIndex, endIndex)");
      return paramString;
    }
    
    public final void toPathString$okhttp(List<String> paramList, StringBuilder paramStringBuilder)
    {
      Intrinsics.checkNotNullParameter(paramList, "$this$toPathString");
      Intrinsics.checkNotNullParameter(paramStringBuilder, "out");
      int j = paramList.size();
      for (int i = 0; i < j; i++)
      {
        paramStringBuilder.append('/');
        paramStringBuilder.append((String)paramList.get(i));
      }
    }
    
    public final List<String> toQueryNamesAndValues$okhttp(String paramString)
    {
      Intrinsics.checkNotNullParameter(paramString, "$this$toQueryNamesAndValues");
      List localList = (List)new ArrayList();
      int j;
      for (int i = 0; i <= paramString.length(); i = j + 1)
      {
        int k = StringsKt.indexOf$default((CharSequence)paramString, '&', i, false, 4, null);
        j = k;
        if (k == -1) {
          j = paramString.length();
        }
        k = StringsKt.indexOf$default((CharSequence)paramString, '=', i, false, 4, null);
        String str;
        if ((k != -1) && (k <= j))
        {
          str = paramString.substring(i, k);
          Intrinsics.checkNotNullExpressionValue(str, "(this as java.lang.Strin…ing(startIndex, endIndex)");
          localList.add(str);
          str = paramString.substring(k + 1, j);
          Intrinsics.checkNotNullExpressionValue(str, "(this as java.lang.Strin…ing(startIndex, endIndex)");
          localList.add(str);
        }
        else
        {
          str = paramString.substring(i, j);
          Intrinsics.checkNotNullExpressionValue(str, "(this as java.lang.Strin…ing(startIndex, endIndex)");
          localList.add(str);
          localList.add(null);
        }
      }
      return localList;
    }
    
    public final void toQueryString$okhttp(List<String> paramList, StringBuilder paramStringBuilder)
    {
      Intrinsics.checkNotNullParameter(paramList, "$this$toQueryString");
      Intrinsics.checkNotNullParameter(paramStringBuilder, "out");
      Object localObject = RangesKt.step((IntProgression)RangesKt.until(0, paramList.size()), 2);
      int i = ((IntProgression)localObject).getFirst();
      int k = ((IntProgression)localObject).getLast();
      int j = ((IntProgression)localObject).getStep();
      if (j >= 0 ? i <= k : i >= k) {
        for (;;)
        {
          String str = (String)paramList.get(i);
          localObject = (String)paramList.get(i + 1);
          if (i > 0) {
            paramStringBuilder.append('&');
          }
          paramStringBuilder.append(str);
          if (localObject != null)
          {
            paramStringBuilder.append('=');
            paramStringBuilder.append((String)localObject);
          }
          if (i == k) {
            break;
          }
          i += j;
        }
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/HttpUrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */