package okhttp3.internal.tls;

import java.security.GeneralSecurityException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000@\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020 \n\002\030\002\n\002\b\002\n\002\020\016\n\000\n\002\020\013\n\000\n\002\020\000\n\000\n\002\020\b\n\002\b\002\n\002\030\002\n\002\b\003\030\000 \0252\0020\001:\001\025B\r\022\006\020\002\032\0020\003¢\006\002\020\004J$\020\005\032\b\022\004\022\0020\0070\0062\f\020\b\032\b\022\004\022\0020\0070\0062\006\020\t\032\0020\nH\026J\023\020\013\032\0020\f2\b\020\r\032\004\030\0010\016H\002J\b\020\017\032\0020\020H\026J\030\020\021\032\0020\f2\006\020\022\032\0020\0232\006\020\024\032\0020\023H\002R\016\020\002\032\0020\003X\004¢\006\002\n\000¨\006\026"}, d2={"Lokhttp3/internal/tls/BasicCertificateChainCleaner;", "Lokhttp3/internal/tls/CertificateChainCleaner;", "trustRootIndex", "Lokhttp3/internal/tls/TrustRootIndex;", "(Lokhttp3/internal/tls/TrustRootIndex;)V", "clean", "", "Ljava/security/cert/Certificate;", "chain", "hostname", "", "equals", "", "other", "", "hashCode", "", "verifySignature", "toVerify", "Ljava/security/cert/X509Certificate;", "signingCert", "Companion", "okhttp"}, k=1, mv={1, 4, 0})
public final class BasicCertificateChainCleaner
  extends CertificateChainCleaner
{
  public static final Companion Companion = new Companion(null);
  private static final int MAX_SIGNERS = 9;
  private final TrustRootIndex trustRootIndex;
  
  public BasicCertificateChainCleaner(TrustRootIndex paramTrustRootIndex)
  {
    this.trustRootIndex = paramTrustRootIndex;
  }
  
  private final boolean verifySignature(X509Certificate paramX509Certificate1, X509Certificate paramX509Certificate2)
  {
    boolean bool2 = Intrinsics.areEqual(paramX509Certificate1.getIssuerDN(), paramX509Certificate2.getSubjectDN());
    boolean bool1 = true;
    if ((bool2 ^ true)) {
      return false;
    }
    try
    {
      paramX509Certificate1.verify(paramX509Certificate2.getPublicKey());
    }
    catch (GeneralSecurityException paramX509Certificate1)
    {
      bool1 = false;
    }
    return bool1;
  }
  
  public List<Certificate> clean(List<? extends Certificate> paramList, String paramString)
    throws SSLPeerUnverifiedException
  {
    Intrinsics.checkNotNullParameter(paramList, "chain");
    Intrinsics.checkNotNullParameter(paramString, "hostname");
    paramList = (Deque)new ArrayDeque((Collection)paramList);
    paramString = (List)new ArrayList();
    Object localObject1 = paramList.removeFirst();
    Intrinsics.checkNotNullExpressionValue(localObject1, "queue.removeFirst()");
    paramString.add(localObject1);
    int j = 0;
    int i = 0;
    if (i < 9)
    {
      localObject1 = paramString.get(paramString.size() - 1);
      if (localObject1 != null)
      {
        localObject1 = (X509Certificate)localObject1;
        Object localObject2 = this.trustRootIndex.findByIssuerAndSignature((X509Certificate)localObject1);
        if (localObject2 != null)
        {
          if ((paramString.size() > 1) || ((true ^ Intrinsics.areEqual(localObject1, localObject2)))) {
            paramString.add(localObject2);
          }
          if (verifySignature((X509Certificate)localObject2, (X509Certificate)localObject2)) {
            return paramString;
          }
          j = 1;
        }
        else
        {
          localObject2 = paramList.iterator();
          Intrinsics.checkNotNullExpressionValue(localObject2, "queue.iterator()");
        }
        for (;;)
        {
          if (((Iterator)localObject2).hasNext())
          {
            Object localObject3 = ((Iterator)localObject2).next();
            if (localObject3 != null)
            {
              localObject3 = (X509Certificate)localObject3;
              if (verifySignature((X509Certificate)localObject1, (X509Certificate)localObject3))
              {
                ((Iterator)localObject2).remove();
                paramString.add(localObject3);
                i++;
                break;
              }
              continue;
            }
            throw new NullPointerException("null cannot be cast to non-null type java.security.cert.X509Certificate");
          }
        }
        if (j != 0) {
          return paramString;
        }
        throw ((Throwable)new SSLPeerUnverifiedException("Failed to find a trusted cert that signed " + localObject1));
      }
      throw new NullPointerException("null cannot be cast to non-null type java.security.cert.X509Certificate");
    }
    throw ((Throwable)new SSLPeerUnverifiedException("Certificate chain too long: " + paramString));
  }
  
  public boolean equals(Object paramObject)
  {
    BasicCertificateChainCleaner localBasicCertificateChainCleaner = (BasicCertificateChainCleaner)this;
    boolean bool = true;
    if ((paramObject != localBasicCertificateChainCleaner) && ((!(paramObject instanceof BasicCertificateChainCleaner)) || (!Intrinsics.areEqual(((BasicCertificateChainCleaner)paramObject).trustRootIndex, this.trustRootIndex)))) {
      bool = false;
    }
    return bool;
  }
  
  public int hashCode()
  {
    return this.trustRootIndex.hashCode();
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\022\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\b\n\000\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002R\016\020\003\032\0020\004XT¢\006\002\n\000¨\006\005"}, d2={"Lokhttp3/internal/tls/BasicCertificateChainCleaner$Companion;", "", "()V", "MAX_SIGNERS", "", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Companion {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/tls/BasicCertificateChainCleaner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */