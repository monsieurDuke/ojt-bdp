package androidx.core.provider;

import android.util.Base64;
import androidx.core.util.Preconditions;
import java.util.List;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public final class FontRequest
{
  private final List<List<byte[]>> mCertificates;
  private final int mCertificatesArray;
  private final String mIdentifier;
  private final String mProviderAuthority;
  private final String mProviderPackage;
  private final String mQuery;
  
  public FontRequest(String paramString1, String paramString2, String paramString3, int paramInt)
  {
    this.mProviderAuthority = ((String)Preconditions.checkNotNull(paramString1));
    this.mProviderPackage = ((String)Preconditions.checkNotNull(paramString2));
    this.mQuery = ((String)Preconditions.checkNotNull(paramString3));
    this.mCertificates = null;
    boolean bool;
    if (paramInt != 0) {
      bool = true;
    } else {
      bool = false;
    }
    Preconditions.checkArgument(bool);
    this.mCertificatesArray = paramInt;
    this.mIdentifier = createIdentifier(paramString1, paramString2, paramString3);
  }
  
  public FontRequest(String paramString1, String paramString2, String paramString3, List<List<byte[]>> paramList)
  {
    this.mProviderAuthority = ((String)Preconditions.checkNotNull(paramString1));
    this.mProviderPackage = ((String)Preconditions.checkNotNull(paramString2));
    this.mQuery = ((String)Preconditions.checkNotNull(paramString3));
    this.mCertificates = ((List)Preconditions.checkNotNull(paramList));
    this.mCertificatesArray = 0;
    this.mIdentifier = createIdentifier(paramString1, paramString2, paramString3);
  }
  
  private String createIdentifier(String paramString1, String paramString2, String paramString3)
  {
    return paramString1 + "-" + paramString2 + "-" + paramString3;
  }
  
  public List<List<byte[]>> getCertificates()
  {
    return this.mCertificates;
  }
  
  public int getCertificatesArrayResId()
  {
    return this.mCertificatesArray;
  }
  
  String getId()
  {
    return this.mIdentifier;
  }
  
  @Deprecated
  public String getIdentifier()
  {
    return this.mIdentifier;
  }
  
  public String getProviderAuthority()
  {
    return this.mProviderAuthority;
  }
  
  public String getProviderPackage()
  {
    return this.mProviderPackage;
  }
  
  public String getQuery()
  {
    return this.mQuery;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("FontRequest {mProviderAuthority: " + this.mProviderAuthority + ", mProviderPackage: " + this.mProviderPackage + ", mQuery: " + this.mQuery + ", mCertificates:");
    for (int i = 0; i < this.mCertificates.size(); i++)
    {
      localStringBuilder.append(" [");
      List localList = (List)this.mCertificates.get(i);
      for (int j = 0; j < localList.size(); j++)
      {
        localStringBuilder.append(" \"");
        String str = Base64.encodeToString((byte[])localList.get(j), 0);
        Log5ECF72.a(str);
        LogE84000.a(str);
        Log229316.a(str);
        localStringBuilder.append(str);
        localStringBuilder.append("\"");
      }
      localStringBuilder.append(" ]");
    }
    localStringBuilder.append("}");
    localStringBuilder.append("mCertificatesArray: " + this.mCertificatesArray);
    return localStringBuilder.toString();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/provider/FontRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */