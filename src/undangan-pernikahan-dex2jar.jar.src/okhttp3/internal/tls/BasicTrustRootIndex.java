package okhttp3.internal.tls;

import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\0008\n\002\030\002\n\002\030\002\n\000\n\002\020\021\n\002\030\002\n\002\b\002\n\002\020$\n\002\030\002\n\002\020\"\n\000\n\002\020\013\n\000\n\002\020\000\n\002\b\003\n\002\020\b\n\000\030\0002\0020\001B\031\022\022\020\002\032\n\022\006\b\001\022\0020\0040\003\"\0020\004¢\006\002\020\005J\023\020\n\032\0020\0132\b\020\f\032\004\030\0010\rH\002J\022\020\016\032\004\030\0010\0042\006\020\017\032\0020\004H\026J\b\020\020\032\0020\021H\026R \020\006\032\024\022\004\022\0020\b\022\n\022\b\022\004\022\0020\0040\t0\007X\004¢\006\002\n\000¨\006\022"}, d2={"Lokhttp3/internal/tls/BasicTrustRootIndex;", "Lokhttp3/internal/tls/TrustRootIndex;", "caCerts", "", "Ljava/security/cert/X509Certificate;", "([Ljava/security/cert/X509Certificate;)V", "subjectToCaCerts", "", "Ljavax/security/auth/x500/X500Principal;", "", "equals", "", "other", "", "findByIssuerAndSignature", "cert", "hashCode", "", "okhttp"}, k=1, mv={1, 4, 0})
public final class BasicTrustRootIndex
  implements TrustRootIndex
{
  private final Map<X500Principal, Set<X509Certificate>> subjectToCaCerts;
  
  public BasicTrustRootIndex(X509Certificate... paramVarArgs)
  {
    Map localMap = (Map)new LinkedHashMap();
    int j = paramVarArgs.length;
    for (int i = 0; i < j; i++)
    {
      X509Certificate localX509Certificate = paramVarArgs[i];
      X500Principal localX500Principal = localX509Certificate.getSubjectX500Principal();
      Intrinsics.checkNotNullExpressionValue(localX500Principal, "caCert.subjectX500Principal");
      Object localObject = localMap.get(localX500Principal);
      if (localObject == null)
      {
        localObject = (Set)new LinkedHashSet();
        localMap.put(localX500Principal, localObject);
      }
      ((Set)localObject).add(localX509Certificate);
    }
    this.subjectToCaCerts = localMap;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool;
    if ((paramObject != (BasicTrustRootIndex)this) && ((!(paramObject instanceof BasicTrustRootIndex)) || (!Intrinsics.areEqual(((BasicTrustRootIndex)paramObject).subjectToCaCerts, this.subjectToCaCerts)))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public X509Certificate findByIssuerAndSignature(X509Certificate paramX509Certificate)
  {
    Intrinsics.checkNotNullParameter(paramX509Certificate, "cert");
    X500Principal localX500Principal = paramX509Certificate.getIssuerX500Principal();
    Object localObject = (Set)this.subjectToCaCerts.get(localX500Principal);
    localX500Principal = null;
    if (localObject != null)
    {
      Iterator localIterator = ((Iterable)localObject).iterator();
      while (localIterator.hasNext())
      {
        localObject = localIterator.next();
        X509Certificate localX509Certificate = (X509Certificate)localObject;
        int i;
        try
        {
          paramX509Certificate.verify(localX509Certificate.getPublicKey());
          i = 1;
        }
        catch (Exception localException)
        {
          i = 0;
        }
        if (i != 0)
        {
          paramX509Certificate = (X509Certificate)localObject;
          break label101;
        }
      }
      paramX509Certificate = localX500Principal;
      label101:
      return (X509Certificate)paramX509Certificate;
    }
    return null;
  }
  
  public int hashCode()
  {
    return this.subjectToCaCerts.hashCode();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/tls/BasicTrustRootIndex.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */