package androidx.core.provider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ProviderInfo;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Build.VERSION;
import android.os.CancellationSignal;
import androidx.core.content.res.FontResourcesParserCompat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class FontProvider
{
  private static final Comparator<byte[]> sByteArrayComparator = new FontProvider..ExternalSyntheticLambda0();
  
  private static List<byte[]> convertToByteArrayList(Signature[] paramArrayOfSignature)
  {
    ArrayList localArrayList = new ArrayList();
    int j = paramArrayOfSignature.length;
    for (int i = 0; i < j; i++) {
      localArrayList.add(paramArrayOfSignature[i].toByteArray());
    }
    return localArrayList;
  }
  
  private static boolean equalsByteArrayList(List<byte[]> paramList1, List<byte[]> paramList2)
  {
    if (paramList1.size() != paramList2.size()) {
      return false;
    }
    for (int i = 0; i < paramList1.size(); i++) {
      if (!Arrays.equals((byte[])paramList1.get(i), (byte[])paramList2.get(i))) {
        return false;
      }
    }
    return true;
  }
  
  private static List<List<byte[]>> getCertificates(FontRequest paramFontRequest, Resources paramResources)
  {
    if (paramFontRequest.getCertificates() != null) {
      return paramFontRequest.getCertificates();
    }
    return FontResourcesParserCompat.readCerts(paramResources, paramFontRequest.getCertificatesArrayResId());
  }
  
  static FontsContractCompat.FontFamilyResult getFontFamilyResult(Context paramContext, FontRequest paramFontRequest, CancellationSignal paramCancellationSignal)
    throws PackageManager.NameNotFoundException
  {
    ProviderInfo localProviderInfo = getProvider(paramContext.getPackageManager(), paramFontRequest, paramContext.getResources());
    if (localProviderInfo == null) {
      return FontsContractCompat.FontFamilyResult.create(1, null);
    }
    return FontsContractCompat.FontFamilyResult.create(0, query(paramContext, paramFontRequest, localProviderInfo.authority, paramCancellationSignal));
  }
  
  static ProviderInfo getProvider(PackageManager paramPackageManager, FontRequest paramFontRequest, Resources paramResources)
    throws PackageManager.NameNotFoundException
  {
    String str = paramFontRequest.getProviderAuthority();
    ProviderInfo localProviderInfo = paramPackageManager.resolveContentProvider(str, 0);
    if (localProviderInfo != null)
    {
      if (localProviderInfo.packageName.equals(paramFontRequest.getProviderPackage()))
      {
        paramPackageManager = convertToByteArrayList(paramPackageManager.getPackageInfo(localProviderInfo.packageName, 64).signatures);
        Collections.sort(paramPackageManager, sByteArrayComparator);
        paramFontRequest = getCertificates(paramFontRequest, paramResources);
        for (int i = 0; i < paramFontRequest.size(); i++)
        {
          paramResources = new ArrayList((Collection)paramFontRequest.get(i));
          Collections.sort(paramResources, sByteArrayComparator);
          if (equalsByteArrayList(paramPackageManager, paramResources)) {
            return localProviderInfo;
          }
        }
        return null;
      }
      throw new PackageManager.NameNotFoundException("Found content provider " + str + ", but package was not " + paramFontRequest.getProviderPackage());
    }
    throw new PackageManager.NameNotFoundException("No package found for authority: " + str);
  }
  
  static FontsContractCompat.FontInfo[] query(Context paramContext, FontRequest paramFontRequest, String paramString, CancellationSignal paramCancellationSignal)
  {
    ArrayList localArrayList = new ArrayList();
    Uri localUri1 = new Uri.Builder().scheme("content").authority(paramString).build();
    Uri localUri2 = new Uri.Builder().scheme("content").authority(paramString).appendPath("file").build();
    Object localObject2 = null;
    paramString = (String)localObject2;
    try
    {
      Object localObject1 = { "_id", "file_id", "font_ttc_index", "font_variation_settings", "font_weight", "font_italic", "result_code" };
      paramString = (String)localObject2;
      paramContext = paramContext.getContentResolver();
      paramString = (String)localObject2;
      if (Build.VERSION.SDK_INT > 16)
      {
        paramString = (String)localObject2;
        paramContext = Api16Impl.query(paramContext, localUri1, (String[])localObject1, "query = ?", new String[] { paramFontRequest.getQuery() }, null, paramCancellationSignal);
      }
      else
      {
        paramString = (String)localObject2;
        paramContext = paramContext.query(localUri1, (String[])localObject1, "query = ?", new String[] { paramFontRequest.getQuery() }, null);
      }
      if (paramContext != null)
      {
        paramString = paramContext;
        if (paramContext.getCount() > 0)
        {
          paramString = paramContext;
          int i = paramContext.getColumnIndex("result_code");
          paramString = paramContext;
          paramFontRequest = new java/util/ArrayList;
          paramString = paramContext;
          paramFontRequest.<init>();
          paramString = paramContext;
          int i2 = paramContext.getColumnIndex("_id");
          paramString = paramContext;
          int n = paramContext.getColumnIndex("file_id");
          paramString = paramContext;
          int i3 = paramContext.getColumnIndex("font_ttc_index");
          paramString = paramContext;
          int i1 = paramContext.getColumnIndex("font_weight");
          paramString = paramContext;
          int i4 = paramContext.getColumnIndex("font_italic");
          paramCancellationSignal = (CancellationSignal)localObject1;
          for (;;)
          {
            paramString = paramContext;
            if (!paramContext.moveToNext()) {
              break;
            }
            int j;
            if (i != -1)
            {
              paramString = paramContext;
              j = paramContext.getInt(i);
            }
            else
            {
              j = 0;
            }
            int k;
            if (i3 != -1)
            {
              paramString = paramContext;
              k = paramContext.getInt(i3);
            }
            else
            {
              k = 0;
            }
            if (n == -1)
            {
              paramString = paramContext;
              localObject1 = ContentUris.withAppendedId(localUri1, paramContext.getLong(i2));
            }
            else
            {
              paramString = paramContext;
              localObject1 = ContentUris.withAppendedId(localUri2, paramContext.getLong(n));
            }
            int m;
            if (i1 != -1)
            {
              paramString = paramContext;
              m = paramContext.getInt(i1);
            }
            else
            {
              m = 400;
            }
            if (i4 != -1)
            {
              paramString = paramContext;
              if (paramContext.getInt(i4) == 1)
              {
                bool = true;
                break label439;
              }
            }
            boolean bool = false;
            label439:
            paramString = paramContext;
            paramFontRequest.add(FontsContractCompat.FontInfo.create((Uri)localObject1, k, m, bool, j));
          }
          break label468;
        }
      }
      paramFontRequest = localArrayList;
      label468:
      return (FontsContractCompat.FontInfo[])paramFontRequest.toArray(new FontsContractCompat.FontInfo[0]);
    }
    finally
    {
      if (paramString != null) {
        paramString.close();
      }
    }
  }
  
  static class Api16Impl
  {
    static Cursor query(ContentResolver paramContentResolver, Uri paramUri, String[] paramArrayOfString1, String paramString1, String[] paramArrayOfString2, String paramString2, Object paramObject)
    {
      return paramContentResolver.query(paramUri, paramArrayOfString1, paramString1, paramArrayOfString2, paramString2, (CancellationSignal)paramObject);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/provider/FontProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */