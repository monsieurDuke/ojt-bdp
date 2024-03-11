package androidx.core.provider;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Build.VERSION;
import android.provider.DocumentsContract;
import java.io.FileNotFoundException;
import java.util.List;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public final class DocumentsContractCompat
{
  private static final String PATH_TREE = "tree";
  
  public static Uri buildChildDocumentsUri(String paramString1, String paramString2)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return DocumentsContractApi21Impl.buildChildDocumentsUri(paramString1, paramString2);
    }
    return null;
  }
  
  public static Uri buildChildDocumentsUriUsingTree(Uri paramUri, String paramString)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return DocumentsContractApi21Impl.buildChildDocumentsUriUsingTree(paramUri, paramString);
    }
    return null;
  }
  
  public static Uri buildDocumentUri(String paramString1, String paramString2)
  {
    if (Build.VERSION.SDK_INT >= 19) {
      return DocumentsContractApi19Impl.buildDocumentUri(paramString1, paramString2);
    }
    return null;
  }
  
  public static Uri buildDocumentUriUsingTree(Uri paramUri, String paramString)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return DocumentsContractApi21Impl.buildDocumentUriUsingTree(paramUri, paramString);
    }
    return null;
  }
  
  public static Uri buildTreeDocumentUri(String paramString1, String paramString2)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return DocumentsContractApi21Impl.buildTreeDocumentUri(paramString1, paramString2);
    }
    return null;
  }
  
  public static Uri createDocument(ContentResolver paramContentResolver, Uri paramUri, String paramString1, String paramString2)
    throws FileNotFoundException
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return DocumentsContractApi21Impl.createDocument(paramContentResolver, paramUri, paramString1, paramString2);
    }
    return null;
  }
  
  public static String getDocumentId(Uri paramUri)
  {
    if (Build.VERSION.SDK_INT >= 19)
    {
      paramUri = DocumentsContractApi19Impl.getDocumentId(paramUri);
      Log5ECF72.a(paramUri);
      LogE84000.a(paramUri);
      Log229316.a(paramUri);
      return paramUri;
    }
    return null;
  }
  
  public static String getTreeDocumentId(Uri paramUri)
  {
    if (Build.VERSION.SDK_INT >= 21)
    {
      paramUri = DocumentsContractApi21Impl.getTreeDocumentId(paramUri);
      Log5ECF72.a(paramUri);
      LogE84000.a(paramUri);
      Log229316.a(paramUri);
      return paramUri;
    }
    return null;
  }
  
  public static boolean isDocumentUri(Context paramContext, Uri paramUri)
  {
    if (Build.VERSION.SDK_INT >= 19) {
      return DocumentsContractApi19Impl.isDocumentUri(paramContext, paramUri);
    }
    return false;
  }
  
  public static boolean isTreeUri(Uri paramUri)
  {
    int i = Build.VERSION.SDK_INT;
    boolean bool2 = false;
    if (i < 21) {
      return false;
    }
    if (Build.VERSION.SDK_INT < 24)
    {
      paramUri = paramUri.getPathSegments();
      boolean bool1 = bool2;
      if (paramUri.size() >= 2)
      {
        bool1 = bool2;
        if ("tree".equals(paramUri.get(0))) {
          bool1 = true;
        }
      }
      return bool1;
    }
    return DocumentsContractApi24Impl.isTreeUri(paramUri);
  }
  
  public static boolean removeDocument(ContentResolver paramContentResolver, Uri paramUri1, Uri paramUri2)
    throws FileNotFoundException
  {
    if (Build.VERSION.SDK_INT >= 24) {
      return DocumentsContractApi24Impl.removeDocument(paramContentResolver, paramUri1, paramUri2);
    }
    if (Build.VERSION.SDK_INT >= 19) {
      return DocumentsContractApi19Impl.deleteDocument(paramContentResolver, paramUri1);
    }
    return false;
  }
  
  public static Uri renameDocument(ContentResolver paramContentResolver, Uri paramUri, String paramString)
    throws FileNotFoundException
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return DocumentsContractApi21Impl.renameDocument(paramContentResolver, paramUri, paramString);
    }
    return null;
  }
  
  public static final class DocumentCompat
  {
    public static final int FLAG_VIRTUAL_DOCUMENT = 512;
  }
  
  private static class DocumentsContractApi19Impl
  {
    public static Uri buildDocumentUri(String paramString1, String paramString2)
    {
      return DocumentsContract.buildDocumentUri(paramString1, paramString2);
    }
    
    static boolean deleteDocument(ContentResolver paramContentResolver, Uri paramUri)
      throws FileNotFoundException
    {
      return DocumentsContract.deleteDocument(paramContentResolver, paramUri);
    }
    
    static String getDocumentId(Uri paramUri)
    {
      paramUri = DocumentsContract.getDocumentId(paramUri);
      Log5ECF72.a(paramUri);
      LogE84000.a(paramUri);
      Log229316.a(paramUri);
      return paramUri;
    }
    
    static boolean isDocumentUri(Context paramContext, Uri paramUri)
    {
      return DocumentsContract.isDocumentUri(paramContext, paramUri);
    }
  }
  
  private static class DocumentsContractApi21Impl
  {
    static Uri buildChildDocumentsUri(String paramString1, String paramString2)
    {
      return DocumentsContract.buildChildDocumentsUri(paramString1, paramString2);
    }
    
    static Uri buildChildDocumentsUriUsingTree(Uri paramUri, String paramString)
    {
      return DocumentsContract.buildChildDocumentsUriUsingTree(paramUri, paramString);
    }
    
    static Uri buildDocumentUriUsingTree(Uri paramUri, String paramString)
    {
      return DocumentsContract.buildDocumentUriUsingTree(paramUri, paramString);
    }
    
    public static Uri buildTreeDocumentUri(String paramString1, String paramString2)
    {
      return DocumentsContract.buildTreeDocumentUri(paramString1, paramString2);
    }
    
    static Uri createDocument(ContentResolver paramContentResolver, Uri paramUri, String paramString1, String paramString2)
      throws FileNotFoundException
    {
      return DocumentsContract.createDocument(paramContentResolver, paramUri, paramString1, paramString2);
    }
    
    static String getTreeDocumentId(Uri paramUri)
    {
      paramUri = DocumentsContract.getTreeDocumentId(paramUri);
      Log5ECF72.a(paramUri);
      LogE84000.a(paramUri);
      Log229316.a(paramUri);
      return paramUri;
    }
    
    static Uri renameDocument(ContentResolver paramContentResolver, Uri paramUri, String paramString)
      throws FileNotFoundException
    {
      return DocumentsContract.renameDocument(paramContentResolver, paramUri, paramString);
    }
  }
  
  private static class DocumentsContractApi24Impl
  {
    static boolean isTreeUri(Uri paramUri)
    {
      return DocumentsContract.isTreeUri(paramUri);
    }
    
    static boolean removeDocument(ContentResolver paramContentResolver, Uri paramUri1, Uri paramUri2)
      throws FileNotFoundException
    {
      return DocumentsContract.removeDocument(paramContentResolver, paramUri1, paramUri2);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/provider/DocumentsContractCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */