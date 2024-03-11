package okhttp3.internal.tls;

import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import okhttp3.internal.HostnamesKt;
import okhttp3.internal.Util;

@Metadata(bv={1, 0, 3}, d1={"\0006\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\b\n\002\b\002\n\002\020 \n\002\020\016\n\000\n\002\030\002\n\002\b\003\n\002\020\013\n\002\b\002\n\002\030\002\n\002\b\006\bÆ\002\030\0002\0020\001B\007\b\002¢\006\002\020\002J\024\020\006\032\b\022\004\022\0020\b0\0072\006\020\t\032\0020\nJ\036\020\013\032\b\022\004\022\0020\b0\0072\006\020\t\032\0020\n2\006\020\f\032\0020\004H\002J\026\020\r\032\0020\0162\006\020\017\032\0020\b2\006\020\t\032\0020\nJ\030\020\r\032\0020\0162\006\020\017\032\0020\b2\006\020\020\032\0020\021H\026J\030\020\022\032\0020\0162\006\020\023\032\0020\b2\006\020\t\032\0020\nH\002J\034\020\022\032\0020\0162\b\020\023\032\004\030\0010\b2\b\020\024\032\004\030\0010\bH\002J\030\020\025\032\0020\0162\006\020\026\032\0020\b2\006\020\t\032\0020\nH\002R\016\020\003\032\0020\004XT¢\006\002\n\000R\016\020\005\032\0020\004XT¢\006\002\n\000¨\006\027"}, d2={"Lokhttp3/internal/tls/OkHostnameVerifier;", "Ljavax/net/ssl/HostnameVerifier;", "()V", "ALT_DNS_NAME", "", "ALT_IPA_NAME", "allSubjectAltNames", "", "", "certificate", "Ljava/security/cert/X509Certificate;", "getSubjectAltNames", "type", "verify", "", "host", "session", "Ljavax/net/ssl/SSLSession;", "verifyHostname", "hostname", "pattern", "verifyIpAddress", "ipAddress", "okhttp"}, k=1, mv={1, 4, 0})
public final class OkHostnameVerifier
  implements HostnameVerifier
{
  private static final int ALT_DNS_NAME = 2;
  private static final int ALT_IPA_NAME = 7;
  public static final OkHostnameVerifier INSTANCE = new OkHostnameVerifier();
  
  private final List<String> getSubjectAltNames(X509Certificate paramX509Certificate, int paramInt)
  {
    try
    {
      Object localObject1 = paramX509Certificate.getSubjectAlternativeNames();
      if (localObject1 != null)
      {
        paramX509Certificate = new java/util/ArrayList;
        paramX509Certificate.<init>();
        paramX509Certificate = (List)paramX509Certificate;
        localObject1 = ((Collection)localObject1).iterator();
        while (((Iterator)localObject1).hasNext())
        {
          Object localObject2 = (List)((Iterator)localObject1).next();
          if ((localObject2 != null) && (((List)localObject2).size() >= 2) && (!(Intrinsics.areEqual(((List)localObject2).get(0), Integer.valueOf(paramInt)) ^ true)))
          {
            localObject2 = ((List)localObject2).get(1);
            if (localObject2 != null) {
              if (localObject2 != null)
              {
                paramX509Certificate.add((String)localObject2);
              }
              else
              {
                paramX509Certificate = new java/lang/NullPointerException;
                paramX509Certificate.<init>("null cannot be cast to non-null type kotlin.String");
                throw paramX509Certificate;
              }
            }
          }
        }
        return paramX509Certificate;
      }
      paramX509Certificate = CollectionsKt.emptyList();
      return paramX509Certificate;
    }
    catch (CertificateParsingException paramX509Certificate) {}
    return CollectionsKt.emptyList();
  }
  
  private final boolean verifyHostname(String paramString1, String paramString2)
  {
    String str = paramString1;
    paramString1 = (CharSequence)str;
    int i;
    if ((paramString1 != null) && (paramString1.length() != 0)) {
      i = 0;
    } else {
      i = 1;
    }
    if ((i == 0) && (!StringsKt.startsWith$default(str, ".", false, 2, null)) && (!StringsKt.endsWith$default(str, "..", false, 2, null)))
    {
      paramString1 = (CharSequence)paramString2;
      if ((paramString1 != null) && (paramString1.length() != 0)) {
        i = 0;
      } else {
        i = 1;
      }
      if ((i == 0) && (!StringsKt.startsWith$default(paramString2, ".", false, 2, null)) && (!StringsKt.endsWith$default(paramString2, "..", false, 2, null)))
      {
        paramString1 = str;
        if (!StringsKt.endsWith$default(str, ".", false, 2, null)) {
          paramString1 = str + ".";
        }
        str = paramString2;
        if (!StringsKt.endsWith$default(paramString2, ".", false, 2, null)) {
          str = paramString2 + ".";
        }
        paramString2 = Locale.US;
        Intrinsics.checkNotNullExpressionValue(paramString2, "Locale.US");
        if (str != null)
        {
          paramString2 = str.toLowerCase(paramString2);
          Intrinsics.checkNotNullExpressionValue(paramString2, "(this as java.lang.String).toLowerCase(locale)");
          if (!StringsKt.contains$default((CharSequence)paramString2, (CharSequence)"*", false, 2, null)) {
            return Intrinsics.areEqual(paramString1, paramString2);
          }
          if ((StringsKt.startsWith$default(paramString2, "*.", false, 2, null)) && (StringsKt.indexOf$default((CharSequence)paramString2, '*', 1, false, 4, null) == -1))
          {
            if (paramString1.length() < paramString2.length()) {
              return false;
            }
            if (Intrinsics.areEqual("*.", paramString2)) {
              return false;
            }
            if (paramString2 != null)
            {
              paramString2 = paramString2.substring(1);
              Intrinsics.checkNotNullExpressionValue(paramString2, "(this as java.lang.String).substring(startIndex)");
              if (!StringsKt.endsWith$default(paramString1, paramString2, false, 2, null)) {
                return false;
              }
              i = paramString1.length() - paramString2.length();
              return (i <= 0) || (StringsKt.lastIndexOf$default((CharSequence)paramString1, '.', i - 1, false, 4, null) == -1);
            }
            throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
          }
          return false;
        }
        throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
      }
      return false;
    }
    return false;
  }
  
  private final boolean verifyHostname(String paramString, X509Certificate paramX509Certificate)
  {
    Object localObject = Locale.US;
    Intrinsics.checkNotNullExpressionValue(localObject, "Locale.US");
    if (paramString != null)
    {
      paramString = paramString.toLowerCase((Locale)localObject);
      Intrinsics.checkNotNullExpressionValue(paramString, "(this as java.lang.String).toLowerCase(locale)");
      paramX509Certificate = (Iterable)getSubjectAltNames(paramX509Certificate, 2);
      boolean bool2 = paramX509Certificate instanceof Collection;
      boolean bool1 = false;
      if ((!bool2) || (!((Collection)paramX509Certificate).isEmpty()))
      {
        localObject = paramX509Certificate.iterator();
        while (((Iterator)localObject).hasNext())
        {
          paramX509Certificate = (String)((Iterator)localObject).next();
          if (INSTANCE.verifyHostname(paramString, paramX509Certificate)) {
            bool1 = true;
          }
        }
      }
      return bool1;
    }
    throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
  }
  
  private final boolean verifyIpAddress(String paramString, X509Certificate paramX509Certificate)
  {
    paramString = HostnamesKt.toCanonicalHost(paramString);
    Log5ECF72.a(paramString);
    LogE84000.a(paramString);
    Log229316.a(paramString);
    paramX509Certificate = (Iterable)getSubjectAltNames(paramX509Certificate, 7);
    boolean bool2 = paramX509Certificate instanceof Collection;
    boolean bool1 = false;
    if ((!bool2) || (!((Collection)paramX509Certificate).isEmpty()))
    {
      Iterator localIterator = paramX509Certificate.iterator();
      while (localIterator.hasNext())
      {
        paramX509Certificate = HostnamesKt.toCanonicalHost((String)localIterator.next());
        Log5ECF72.a(paramX509Certificate);
        LogE84000.a(paramX509Certificate);
        Log229316.a(paramX509Certificate);
        if (Intrinsics.areEqual(paramString, paramX509Certificate)) {
          bool1 = true;
        }
      }
    }
    return bool1;
  }
  
  public final List<String> allSubjectAltNames(X509Certificate paramX509Certificate)
  {
    Intrinsics.checkNotNullParameter(paramX509Certificate, "certificate");
    List localList = getSubjectAltNames(paramX509Certificate, 7);
    paramX509Certificate = getSubjectAltNames(paramX509Certificate, 2);
    return CollectionsKt.plus((Collection)localList, (Iterable)paramX509Certificate);
  }
  
  public final boolean verify(String paramString, X509Certificate paramX509Certificate)
  {
    Intrinsics.checkNotNullParameter(paramString, "host");
    Intrinsics.checkNotNullParameter(paramX509Certificate, "certificate");
    boolean bool;
    if (Util.canParseAsIpAddress(paramString)) {
      bool = verifyIpAddress(paramString, paramX509Certificate);
    } else {
      bool = verifyHostname(paramString, paramX509Certificate);
    }
    return bool;
  }
  
  public boolean verify(String paramString, SSLSession paramSSLSession)
  {
    Intrinsics.checkNotNullParameter(paramString, "host");
    Intrinsics.checkNotNullParameter(paramSSLSession, "session");
    boolean bool2 = false;
    boolean bool1;
    try
    {
      paramSSLSession = paramSSLSession.getPeerCertificates()[0];
      if (paramSSLSession != null)
      {
        bool1 = verify(paramString, (X509Certificate)paramSSLSession);
      }
      else
      {
        paramString = new java/lang/NullPointerException;
        paramString.<init>("null cannot be cast to non-null type java.security.cert.X509Certificate");
        throw paramString;
      }
    }
    catch (SSLException paramString)
    {
      bool1 = bool2;
    }
    return bool1;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/tls/OkHostnameVerifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */