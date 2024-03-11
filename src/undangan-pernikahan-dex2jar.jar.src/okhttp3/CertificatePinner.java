package okhttp3;

import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.net.ssl.SSLPeerUnverifiedException;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.text.StringsKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import okhttp3.internal.HostnamesKt;
import okhttp3.internal.tls.CertificateChainCleaner;
import okio.ByteString;
import okio.ByteString.Companion;

@Metadata(bv={1, 0, 3}, d1={"\000T\n\002\030\002\n\002\020\000\n\000\n\002\020\"\n\002\030\002\n\000\n\002\030\002\n\002\b\006\n\002\020\002\n\000\n\002\020\016\n\000\n\002\030\002\n\002\020 \n\002\030\002\n\002\b\002\n\002\020\021\n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\003\n\002\020\b\n\002\b\006\030\000 \"2\0020\001:\003!\"#B!\b\000\022\f\020\002\032\b\022\004\022\0020\0040\003\022\n\b\002\020\005\032\004\030\0010\006¢\006\002\020\007J)\020\f\032\0020\r2\006\020\016\032\0020\0172\022\020\020\032\016\022\n\022\b\022\004\022\0020\0230\0220\021H\000¢\006\002\b\024J)\020\f\032\0020\r2\006\020\016\032\0020\0172\022\020\025\032\n\022\006\b\001\022\0020\0270\026\"\0020\027H\007¢\006\002\020\030J\034\020\f\032\0020\r2\006\020\016\032\0020\0172\f\020\025\032\b\022\004\022\0020\0270\022J\023\020\031\032\0020\0322\b\020\033\032\004\030\0010\001H\002J\024\020\034\032\b\022\004\022\0020\0040\0222\006\020\016\032\0020\017J\b\020\035\032\0020\036H\026J\025\020\037\032\0020\0002\006\020\005\032\0020\006H\000¢\006\002\b R\026\020\005\032\004\030\0010\006X\004¢\006\b\n\000\032\004\b\b\020\tR\027\020\002\032\b\022\004\022\0020\0040\003¢\006\b\n\000\032\004\b\n\020\013¨\006$"}, d2={"Lokhttp3/CertificatePinner;", "", "pins", "", "Lokhttp3/CertificatePinner$Pin;", "certificateChainCleaner", "Lokhttp3/internal/tls/CertificateChainCleaner;", "(Ljava/util/Set;Lokhttp3/internal/tls/CertificateChainCleaner;)V", "getCertificateChainCleaner$okhttp", "()Lokhttp3/internal/tls/CertificateChainCleaner;", "getPins", "()Ljava/util/Set;", "check", "", "hostname", "", "cleanedPeerCertificatesFn", "Lkotlin/Function0;", "", "Ljava/security/cert/X509Certificate;", "check$okhttp", "peerCertificates", "", "Ljava/security/cert/Certificate;", "(Ljava/lang/String;[Ljava/security/cert/Certificate;)V", "equals", "", "other", "findMatchingPins", "hashCode", "", "withCertificateChainCleaner", "withCertificateChainCleaner$okhttp", "Builder", "Companion", "Pin", "okhttp"}, k=1, mv={1, 4, 0})
public final class CertificatePinner
{
  public static final Companion Companion = new Companion(null);
  public static final CertificatePinner DEFAULT = new Builder().build();
  private final CertificateChainCleaner certificateChainCleaner;
  private final Set<Pin> pins;
  
  public CertificatePinner(Set<Pin> paramSet, CertificateChainCleaner paramCertificateChainCleaner)
  {
    this.pins = paramSet;
    this.certificateChainCleaner = paramCertificateChainCleaner;
  }
  
  @JvmStatic
  public static final String pin(Certificate paramCertificate)
  {
    return Companion.pin(paramCertificate);
  }
  
  @JvmStatic
  public static final ByteString sha1Hash(X509Certificate paramX509Certificate)
  {
    return Companion.sha1Hash(paramX509Certificate);
  }
  
  @JvmStatic
  public static final ByteString sha256Hash(X509Certificate paramX509Certificate)
  {
    return Companion.sha256Hash(paramX509Certificate);
  }
  
  public final void check(final String paramString, final List<? extends Certificate> paramList)
    throws SSLPeerUnverifiedException
  {
    Intrinsics.checkNotNullParameter(paramString, "hostname");
    Intrinsics.checkNotNullParameter(paramList, "peerCertificates");
    check$okhttp(paramString, (Function0)new Lambda(paramList)
    {
      final CertificatePinner this$0;
      
      public final List<X509Certificate> invoke()
      {
        Object localObject1 = this.this$0.getCertificateChainCleaner$okhttp();
        if (localObject1 != null)
        {
          localObject1 = ((CertificateChainCleaner)localObject1).clean(paramList, paramString);
          if (localObject1 != null) {}
        }
        else
        {
          localObject1 = paramList;
        }
        Object localObject2 = (Iterable)localObject1;
        localObject1 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject2, 10));
        localObject2 = ((Iterable)localObject2).iterator();
        while (((Iterator)localObject2).hasNext())
        {
          Certificate localCertificate = (Certificate)((Iterator)localObject2).next();
          if (localCertificate != null) {
            ((Collection)localObject1).add((X509Certificate)localCertificate);
          } else {
            throw new NullPointerException("null cannot be cast to non-null type java.security.cert.X509Certificate");
          }
        }
        return (List)localObject1;
      }
    });
  }
  
  @Deprecated(message="replaced with {@link #check(String, List)}.", replaceWith=@ReplaceWith(expression="check(hostname, peerCertificates.toList())", imports={}))
  public final void check(String paramString, Certificate... paramVarArgs)
    throws SSLPeerUnverifiedException
  {
    Intrinsics.checkNotNullParameter(paramString, "hostname");
    Intrinsics.checkNotNullParameter(paramVarArgs, "peerCertificates");
    check(paramString, ArraysKt.toList(paramVarArgs));
  }
  
  public final void check$okhttp(String paramString, Function0<? extends List<? extends X509Certificate>> paramFunction0)
  {
    Intrinsics.checkNotNullParameter(paramString, "hostname");
    Intrinsics.checkNotNullParameter(paramFunction0, "cleanedPeerCertificatesFn");
    List localList1 = findMatchingPins(paramString);
    if (localList1.isEmpty()) {
      return;
    }
    List localList2 = (List)paramFunction0.invoke();
    Iterator localIterator2 = localList2.iterator();
    Object localObject2;
    while (localIterator2.hasNext())
    {
      X509Certificate localX509Certificate = (X509Certificate)localIterator2.next();
      paramFunction0 = (ByteString)null;
      localObject1 = (ByteString)null;
      Iterator localIterator1 = localList1.iterator();
      while (localIterator1.hasNext())
      {
        Pin localPin = (Pin)localIterator1.next();
        localObject2 = localPin.getHashAlgorithm();
        switch (((String)localObject2).hashCode())
        {
        default: 
          break;
        case 3528965: 
          if (!((String)localObject2).equals("sha1")) {
            break label246;
          }
          localObject2 = paramFunction0;
          if (paramFunction0 == null) {
            localObject2 = Companion.sha1Hash(localX509Certificate);
          }
          paramFunction0 = (Function0<? extends List<? extends X509Certificate>>)localObject2;
          if (Intrinsics.areEqual(localPin.getHash(), localObject2)) {
            return;
          }
          break;
        case -903629273: 
          if (!((String)localObject2).equals("sha256")) {
            break label246;
          }
          localObject2 = localObject1;
          if (localObject1 == null) {
            localObject2 = Companion.sha256Hash(localX509Certificate);
          }
          localObject1 = localObject2;
          if (Intrinsics.areEqual(localPin.getHash(), localObject2)) {
            return;
          }
          break;
        }
        continue;
        label246:
        throw ((Throwable)new AssertionError("unsupported hashAlgorithm: " + localPin.getHashAlgorithm()));
      }
    }
    paramFunction0 = new StringBuilder();
    paramFunction0.append("Certificate pinning failure!");
    paramFunction0.append("\n  Peer certificate chain:");
    Object localObject1 = localList2.iterator();
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (X509Certificate)((Iterator)localObject1).next();
      paramFunction0.append("\n    ");
      paramFunction0.append(Companion.pin((Certificate)localObject2));
      paramFunction0.append(": ");
      localObject2 = ((X509Certificate)localObject2).getSubjectDN();
      Intrinsics.checkNotNullExpressionValue(localObject2, "element.subjectDN");
      paramFunction0.append(((Principal)localObject2).getName());
    }
    paramFunction0.append("\n  Pinned certificates for ");
    paramFunction0.append(paramString);
    paramFunction0.append(":");
    paramString = localList1.iterator();
    while (paramString.hasNext())
    {
      localObject1 = (Pin)paramString.next();
      paramFunction0.append("\n    ");
      paramFunction0.append(localObject1);
    }
    paramString = paramFunction0.toString();
    Intrinsics.checkNotNullExpressionValue(paramString, "StringBuilder().apply(builderAction).toString()");
    throw ((Throwable)new SSLPeerUnverifiedException(paramString));
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool;
    if (((paramObject instanceof CertificatePinner)) && (Intrinsics.areEqual(((CertificatePinner)paramObject).pins, this.pins)) && (Intrinsics.areEqual(((CertificatePinner)paramObject).certificateChainCleaner, this.certificateChainCleaner))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final List<Pin> findMatchingPins(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "hostname");
    Object localObject2 = (Iterable)this.pins;
    Object localObject1 = CollectionsKt.emptyList();
    Iterator localIterator = ((Iterable)localObject2).iterator();
    while (localIterator.hasNext())
    {
      Object localObject3 = localIterator.next();
      localObject2 = localObject1;
      if (((Pin)localObject3).matchesHostname(paramString))
      {
        localObject2 = localObject1;
        if (((List)localObject1).isEmpty()) {
          localObject2 = (List)new ArrayList();
        }
        if (localObject2 != null) {
          TypeIntrinsics.asMutableList(localObject2).add(localObject3);
        } else {
          throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.MutableList<T>");
        }
      }
      localObject1 = localObject2;
    }
    return (List<Pin>)localObject1;
  }
  
  public final CertificateChainCleaner getCertificateChainCleaner$okhttp()
  {
    return this.certificateChainCleaner;
  }
  
  public final Set<Pin> getPins()
  {
    return this.pins;
  }
  
  public int hashCode()
  {
    int j = this.pins.hashCode();
    CertificateChainCleaner localCertificateChainCleaner = this.certificateChainCleaner;
    int i;
    if (localCertificateChainCleaner != null) {
      i = localCertificateChainCleaner.hashCode();
    } else {
      i = 0;
    }
    return (37 * 41 + j) * 41 + i;
  }
  
  public final CertificatePinner withCertificateChainCleaner$okhttp(CertificateChainCleaner paramCertificateChainCleaner)
  {
    Intrinsics.checkNotNullParameter(paramCertificateChainCleaner, "certificateChainCleaner");
    if (Intrinsics.areEqual(this.certificateChainCleaner, paramCertificateChainCleaner)) {
      paramCertificateChainCleaner = this;
    } else {
      paramCertificateChainCleaner = new CertificatePinner(this.pins, paramCertificateChainCleaner);
    }
    return paramCertificateChainCleaner;
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000*\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020!\n\002\030\002\n\002\b\004\n\002\020\016\n\002\020\021\n\002\b\002\n\002\030\002\n\000\030\0002\0020\001B\005¢\006\002\020\002J'\020\b\032\0020\0002\006\020\t\032\0020\n2\022\020\003\032\n\022\006\b\001\022\0020\n0\013\"\0020\n¢\006\002\020\fJ\006\020\r\032\0020\016R\027\020\003\032\b\022\004\022\0020\0050\004¢\006\b\n\000\032\004\b\006\020\007¨\006\017"}, d2={"Lokhttp3/CertificatePinner$Builder;", "", "()V", "pins", "", "Lokhttp3/CertificatePinner$Pin;", "getPins", "()Ljava/util/List;", "add", "pattern", "", "", "(Ljava/lang/String;[Ljava/lang/String;)Lokhttp3/CertificatePinner$Builder;", "build", "Lokhttp3/CertificatePinner;", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Builder
  {
    private final List<CertificatePinner.Pin> pins = (List)new ArrayList();
    
    public final Builder add(String paramString, String... paramVarArgs)
    {
      Intrinsics.checkNotNullParameter(paramString, "pattern");
      Intrinsics.checkNotNullParameter(paramVarArgs, "pins");
      Builder localBuilder = (Builder)this;
      int j = paramVarArgs.length;
      for (int i = 0; i < j; i++)
      {
        String str = paramVarArgs[i];
        localBuilder.pins.add(new CertificatePinner.Pin(paramString, str));
      }
      return (Builder)this;
    }
    
    public final CertificatePinner build()
    {
      return new CertificatePinner(CollectionsKt.toSet((Iterable)this.pins), null, 2, null);
    }
    
    public final List<CertificatePinner.Pin> getPins()
    {
      return this.pins;
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000*\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\n\002\020\016\n\000\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\002\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J\020\020\005\032\0020\0062\006\020\007\032\0020\bH\007J\f\020\t\032\0020\n*\0020\013H\007J\f\020\f\032\0020\n*\0020\013H\007R\020\020\003\032\0020\0048\006X\004¢\006\002\n\000¨\006\r"}, d2={"Lokhttp3/CertificatePinner$Companion;", "", "()V", "DEFAULT", "Lokhttp3/CertificatePinner;", "pin", "", "certificate", "Ljava/security/cert/Certificate;", "sha1Hash", "Lokio/ByteString;", "Ljava/security/cert/X509Certificate;", "sha256Hash", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Companion
  {
    @JvmStatic
    public final String pin(Certificate paramCertificate)
    {
      Intrinsics.checkNotNullParameter(paramCertificate, "certificate");
      if ((paramCertificate instanceof X509Certificate)) {
        return "sha256/" + ((Companion)this).sha256Hash((X509Certificate)paramCertificate).base64();
      }
      throw ((Throwable)new IllegalArgumentException("Certificate pinning requires X509 certificates".toString()));
    }
    
    @JvmStatic
    public final ByteString sha1Hash(X509Certificate paramX509Certificate)
    {
      Intrinsics.checkNotNullParameter(paramX509Certificate, "$this$sha1Hash");
      ByteString.Companion localCompanion = ByteString.Companion;
      paramX509Certificate = paramX509Certificate.getPublicKey();
      Intrinsics.checkNotNullExpressionValue(paramX509Certificate, "publicKey");
      paramX509Certificate = paramX509Certificate.getEncoded();
      Intrinsics.checkNotNullExpressionValue(paramX509Certificate, "publicKey.encoded");
      return ByteString.Companion.of$default(localCompanion, paramX509Certificate, 0, 0, 3, null).sha1();
    }
    
    @JvmStatic
    public final ByteString sha256Hash(X509Certificate paramX509Certificate)
    {
      Intrinsics.checkNotNullParameter(paramX509Certificate, "$this$sha256Hash");
      ByteString.Companion localCompanion = ByteString.Companion;
      paramX509Certificate = paramX509Certificate.getPublicKey();
      Intrinsics.checkNotNullExpressionValue(paramX509Certificate, "publicKey");
      paramX509Certificate = paramX509Certificate.getEncoded();
      Intrinsics.checkNotNullExpressionValue(paramX509Certificate, "publicKey.encoded");
      return ByteString.Companion.of$default(localCompanion, paramX509Certificate, 0, 0, 3, null).sha256();
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\0002\n\002\030\002\n\002\020\000\n\000\n\002\020\016\n\002\b\003\n\002\030\002\n\002\b\007\n\002\020\013\n\002\b\002\n\002\020\b\n\002\b\002\n\002\030\002\n\002\b\004\030\0002\0020\001B\025\022\006\020\002\032\0020\003\022\006\020\004\032\0020\003¢\006\002\020\005J\023\020\016\032\0020\0172\b\020\020\032\004\030\0010\001H\002J\b\020\021\032\0020\022H\026J\016\020\023\032\0020\0172\006\020\024\032\0020\025J\016\020\026\032\0020\0172\006\020\027\032\0020\003J\b\020\030\032\0020\003H\026R\021\020\006\032\0020\007¢\006\b\n\000\032\004\b\b\020\tR\021\020\n\032\0020\003¢\006\b\n\000\032\004\b\013\020\fR\021\020\002\032\0020\003¢\006\b\n\000\032\004\b\r\020\f¨\006\031"}, d2={"Lokhttp3/CertificatePinner$Pin;", "", "pattern", "", "pin", "(Ljava/lang/String;Ljava/lang/String;)V", "hash", "Lokio/ByteString;", "getHash", "()Lokio/ByteString;", "hashAlgorithm", "getHashAlgorithm", "()Ljava/lang/String;", "getPattern", "equals", "", "other", "hashCode", "", "matchesCertificate", "certificate", "Ljava/security/cert/X509Certificate;", "matchesHostname", "hostname", "toString", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Pin
  {
    private final ByteString hash;
    private final String hashAlgorithm;
    private final String pattern;
    
    public Pin(String paramString1, String paramString2)
    {
      int i;
      if (((StringsKt.startsWith$default(paramString1, "*.", false, 2, null)) && (StringsKt.indexOf$default((CharSequence)paramString1, "*", 1, false, 4, null) == -1)) || ((StringsKt.startsWith$default(paramString1, "**.", false, 2, null)) && (StringsKt.indexOf$default((CharSequence)paramString1, "*", 2, false, 4, null) == -1)) || (StringsKt.indexOf$default((CharSequence)paramString1, "*", 0, false, 6, null) == -1)) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        Object localObject = HostnamesKt.toCanonicalHost(paramString1);
        Log5ECF72.a(localObject);
        LogE84000.a(localObject);
        Log229316.a(localObject);
        if (localObject != null)
        {
          this.pattern = ((String)localObject);
          if (StringsKt.startsWith$default(paramString2, "sha1/", false, 2, null))
          {
            this.hashAlgorithm = "sha1";
            localObject = ByteString.Companion;
            paramString1 = paramString2.substring("sha1/".length());
            Intrinsics.checkNotNullExpressionValue(paramString1, "(this as java.lang.String).substring(startIndex)");
            paramString1 = ((ByteString.Companion)localObject).decodeBase64(paramString1);
            if (paramString1 != null) {
              this.hash = paramString1;
            } else {
              throw ((Throwable)new IllegalArgumentException("Invalid pin hash: " + paramString2));
            }
          }
          else
          {
            if (!StringsKt.startsWith$default(paramString2, "sha256/", false, 2, null)) {
              break label309;
            }
            this.hashAlgorithm = "sha256";
            localObject = ByteString.Companion;
            paramString1 = paramString2.substring("sha256/".length());
            Intrinsics.checkNotNullExpressionValue(paramString1, "(this as java.lang.String).substring(startIndex)");
            paramString1 = ((ByteString.Companion)localObject).decodeBase64(paramString1);
            if (paramString1 == null) {
              break label279;
            }
            this.hash = paramString1;
          }
          return;
          label279:
          throw ((Throwable)new IllegalArgumentException("Invalid pin hash: " + paramString2));
          label309:
          throw ((Throwable)new IllegalArgumentException("pins must start with 'sha256/' or 'sha1/': " + paramString2));
        }
        throw ((Throwable)new IllegalArgumentException("Invalid pattern: " + paramString1));
      }
      throw ((Throwable)new IllegalArgumentException(("Unexpected pattern: " + paramString1).toString()));
    }
    
    public boolean equals(Object paramObject)
    {
      if ((Pin)this == paramObject) {
        return true;
      }
      if (!(paramObject instanceof Pin)) {
        return false;
      }
      if ((Intrinsics.areEqual(this.pattern, ((Pin)paramObject).pattern) ^ true)) {
        return false;
      }
      if ((Intrinsics.areEqual(this.hashAlgorithm, ((Pin)paramObject).hashAlgorithm) ^ true)) {
        return false;
      }
      return !(Intrinsics.areEqual(this.hash, ((Pin)paramObject).hash) ^ true);
    }
    
    public final ByteString getHash()
    {
      return this.hash;
    }
    
    public final String getHashAlgorithm()
    {
      return this.hashAlgorithm;
    }
    
    public final String getPattern()
    {
      return this.pattern;
    }
    
    public int hashCode()
    {
      return (this.pattern.hashCode() * 31 + this.hashAlgorithm.hashCode()) * 31 + this.hash.hashCode();
    }
    
    public final boolean matchesCertificate(X509Certificate paramX509Certificate)
    {
      Intrinsics.checkNotNullParameter(paramX509Certificate, "certificate");
      String str = this.hashAlgorithm;
      switch (str.hashCode())
      {
      default: 
        break;
      case 3528965: 
        if (str.equals("sha1")) {
          bool = Intrinsics.areEqual(this.hash, CertificatePinner.Companion.sha1Hash(paramX509Certificate));
        }
        break;
      case -903629273: 
        if (str.equals("sha256")) {
          bool = Intrinsics.areEqual(this.hash, CertificatePinner.Companion.sha256Hash(paramX509Certificate));
        }
        break;
      }
      boolean bool = false;
      return bool;
    }
    
    public final boolean matchesHostname(String paramString)
    {
      Intrinsics.checkNotNullParameter(paramString, "hostname");
      String str = this.pattern;
      boolean bool = false;
      int j;
      int i;
      if (StringsKt.startsWith$default(str, "**.", false, 2, null))
      {
        j = this.pattern.length() - 3;
        i = paramString.length() - j;
        if ((StringsKt.regionMatches$default(paramString, paramString.length() - j, this.pattern, 3, j, false, 16, null)) && ((i == 0) || (paramString.charAt(i - 1) == '.'))) {
          bool = true;
        }
      }
      else if (StringsKt.startsWith$default(this.pattern, "*.", false, 2, null))
      {
        j = this.pattern.length() - 1;
        i = paramString.length();
        if ((StringsKt.regionMatches$default(paramString, paramString.length() - j, this.pattern, 1, j, false, 16, null)) && (StringsKt.lastIndexOf$default((CharSequence)paramString, '.', i - j - 1, false, 4, null) == -1)) {
          bool = true;
        }
      }
      else
      {
        bool = Intrinsics.areEqual(paramString, this.pattern);
      }
      return bool;
    }
    
    public String toString()
    {
      return this.hashAlgorithm + '/' + this.hash.base64();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/CertificatePinner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */