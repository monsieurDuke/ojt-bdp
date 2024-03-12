package androidx.core.content;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import org.xmlpull.v1.XmlPullParserException;

public class FileProvider
  extends ContentProvider
{
  private static final String ATTR_NAME = "name";
  private static final String ATTR_PATH = "path";
  private static final String[] COLUMNS = { "_display_name", "_size" };
  private static final File DEVICE_ROOT = new File("/");
  private static final String DISPLAYNAME_FIELD = "displayName";
  private static final String META_DATA_FILE_PROVIDER_PATHS = "android.support.FILE_PROVIDER_PATHS";
  private static final String TAG_CACHE_PATH = "cache-path";
  private static final String TAG_EXTERNAL = "external-path";
  private static final String TAG_EXTERNAL_CACHE = "external-cache-path";
  private static final String TAG_EXTERNAL_FILES = "external-files-path";
  private static final String TAG_EXTERNAL_MEDIA = "external-media-path";
  private static final String TAG_FILES_PATH = "files-path";
  private static final String TAG_ROOT_PATH = "root-path";
  private static final HashMap<String, PathStrategy> sCache = new HashMap();
  private int mResourceId;
  private PathStrategy mStrategy;
  
  public FileProvider()
  {
    this.mResourceId = 0;
  }
  
  protected FileProvider(int paramInt)
  {
    this.mResourceId = paramInt;
  }
  
  private static File buildPath(File paramFile, String... paramVarArgs)
  {
    int j = paramVarArgs.length;
    int i = 0;
    while (i < j)
    {
      String str = paramVarArgs[i];
      File localFile = paramFile;
      if (str != null) {
        localFile = new File(paramFile, str);
      }
      i++;
      paramFile = localFile;
    }
    return paramFile;
  }
  
  private static Object[] copyOf(Object[] paramArrayOfObject, int paramInt)
  {
    Object[] arrayOfObject = new Object[paramInt];
    System.arraycopy(paramArrayOfObject, 0, arrayOfObject, 0, paramInt);
    return arrayOfObject;
  }
  
  private static String[] copyOf(String[] paramArrayOfString, int paramInt)
  {
    String[] arrayOfString = new String[paramInt];
    System.arraycopy(paramArrayOfString, 0, arrayOfString, 0, paramInt);
    return arrayOfString;
  }
  
  static XmlResourceParser getFileProviderPathsMetaData(Context paramContext, String paramString, ProviderInfo paramProviderInfo, int paramInt)
  {
    if (paramProviderInfo != null)
    {
      if ((paramProviderInfo.metaData == null) && (paramInt != 0))
      {
        paramProviderInfo.metaData = new Bundle(1);
        paramProviderInfo.metaData.putInt("android.support.FILE_PROVIDER_PATHS", paramInt);
      }
      paramContext = paramProviderInfo.loadXmlMetaData(paramContext.getPackageManager(), "android.support.FILE_PROVIDER_PATHS");
      if (paramContext != null) {
        return paramContext;
      }
      throw new IllegalArgumentException("Missing android.support.FILE_PROVIDER_PATHS meta-data");
    }
    throw new IllegalArgumentException("Couldn't find meta-data for provider with authority " + paramString);
  }
  
  private static PathStrategy getPathStrategy(Context paramContext, String paramString, int paramInt)
  {
    synchronized (sCache)
    {
      PathStrategy localPathStrategy2 = (PathStrategy)???.get(paramString);
      PathStrategy localPathStrategy1 = localPathStrategy2;
      if (localPathStrategy2 == null) {
        try
        {
          localPathStrategy1 = parsePathStrategy(paramContext, paramString, paramInt);
          ???.put(paramString, localPathStrategy1);
        }
        catch (XmlPullParserException paramContext)
        {
          paramString = new java/lang/IllegalArgumentException;
          paramString.<init>("Failed to parse android.support.FILE_PROVIDER_PATHS meta-data", paramContext);
          throw paramString;
        }
        catch (IOException paramString)
        {
          paramContext = new java/lang/IllegalArgumentException;
          paramContext.<init>("Failed to parse android.support.FILE_PROVIDER_PATHS meta-data", paramString);
          throw paramContext;
        }
      }
      return localPathStrategy1;
    }
  }
  
  public static Uri getUriForFile(Context paramContext, String paramString, File paramFile)
  {
    return getPathStrategy(paramContext, paramString, 0).getUriForFile(paramFile);
  }
  
  public static Uri getUriForFile(Context paramContext, String paramString1, File paramFile, String paramString2)
  {
    return getUriForFile(paramContext, paramString1, paramFile).buildUpon().appendQueryParameter("displayName", paramString2).build();
  }
  
  private static int modeToMode(String paramString)
  {
    int i;
    if ("r".equals(paramString)) {
      i = 268435456;
    } else if ((!"w".equals(paramString)) && (!"wt".equals(paramString)))
    {
      if ("wa".equals(paramString)) {
        i = 704643072;
      } else if ("rw".equals(paramString)) {
        i = 939524096;
      } else if ("rwt".equals(paramString)) {
        i = 1006632960;
      } else {
        throw new IllegalArgumentException("Invalid mode: " + paramString);
      }
    }
    else {
      i = 738197504;
    }
    return i;
  }
  
  private static PathStrategy parsePathStrategy(Context paramContext, String paramString, int paramInt)
    throws IOException, XmlPullParserException
  {
    SimplePathStrategy localSimplePathStrategy = new SimplePathStrategy(paramString);
    XmlResourceParser localXmlResourceParser = getFileProviderPathsMetaData(paramContext, paramString, paramContext.getPackageManager().resolveContentProvider(paramString, 128), paramInt);
    for (;;)
    {
      paramInt = localXmlResourceParser.next();
      if (paramInt == 1) {
        break;
      }
      if (paramInt == 2)
      {
        String str3 = localXmlResourceParser.getName();
        String str1 = localXmlResourceParser.getAttributeValue(null, "name");
        String str2 = localXmlResourceParser.getAttributeValue(null, "path");
        File[] arrayOfFile2 = null;
        File[] arrayOfFile1 = null;
        paramString = null;
        if ("root-path".equals(str3))
        {
          paramString = DEVICE_ROOT;
        }
        else if ("files-path".equals(str3))
        {
          paramString = paramContext.getFilesDir();
        }
        else if ("cache-path".equals(str3))
        {
          paramString = paramContext.getCacheDir();
        }
        else if ("external-path".equals(str3))
        {
          paramString = Environment.getExternalStorageDirectory();
        }
        else if ("external-files-path".equals(str3))
        {
          arrayOfFile1 = ContextCompat.getExternalFilesDirs(paramContext, null);
          if (arrayOfFile1.length > 0) {
            paramString = arrayOfFile1[0];
          }
        }
        else
        {
          if ("external-cache-path".equals(str3))
          {
            arrayOfFile1 = ContextCompat.getExternalCacheDirs(paramContext);
            paramString = arrayOfFile2;
            if (arrayOfFile1.length > 0) {
              paramString = arrayOfFile1[0];
            }
          }
          for (;;)
          {
            break;
            paramString = arrayOfFile2;
            if (Build.VERSION.SDK_INT >= 21)
            {
              paramString = arrayOfFile1;
              if ("external-media-path".equals(str3))
              {
                arrayOfFile2 = Api21Impl.getExternalMediaDirs(paramContext);
                paramString = arrayOfFile1;
                if (arrayOfFile2.length > 0) {
                  paramString = arrayOfFile2[0];
                }
              }
            }
          }
        }
        if (paramString != null) {
          localSimplePathStrategy.addRoot(str1, buildPath(paramString, new String[] { str2 }));
        }
      }
    }
    return localSimplePathStrategy;
  }
  
  public void attachInfo(Context paramContext, ProviderInfo arg2)
  {
    super.attachInfo(paramContext, ???);
    if (!???.exported)
    {
      if (???.grantUriPermissions)
      {
        String str = ???.authority.split(";")[0];
        synchronized (sCache)
        {
          ???.remove(str);
          this.mStrategy = getPathStrategy(paramContext, str, this.mResourceId);
          return;
        }
      }
      throw new SecurityException("Provider must grant uri permissions");
    }
    throw new SecurityException("Provider must not be exported");
  }
  
  public int delete(Uri paramUri, String paramString, String[] paramArrayOfString)
  {
    return this.mStrategy.getFileForUri(paramUri).delete();
  }
  
  public String getType(Uri paramUri)
  {
    paramUri = this.mStrategy.getFileForUri(paramUri);
    int i = paramUri.getName().lastIndexOf('.');
    if (i >= 0)
    {
      paramUri = paramUri.getName().substring(i + 1);
      paramUri = MimeTypeMap.getSingleton().getMimeTypeFromExtension(paramUri);
      if (paramUri != null) {
        return paramUri;
      }
    }
    return "application/octet-stream";
  }
  
  public Uri insert(Uri paramUri, ContentValues paramContentValues)
  {
    throw new UnsupportedOperationException("No external inserts");
  }
  
  public boolean onCreate()
  {
    return true;
  }
  
  public ParcelFileDescriptor openFile(Uri paramUri, String paramString)
    throws FileNotFoundException
  {
    return ParcelFileDescriptor.open(this.mStrategy.getFileForUri(paramUri), modeToMode(paramString));
  }
  
  public Cursor query(Uri paramUri, String[] paramArrayOfString1, String paramString1, String[] paramArrayOfString2, String paramString2)
  {
    paramArrayOfString2 = this.mStrategy.getFileForUri(paramUri);
    paramString1 = paramUri.getQueryParameter("displayName");
    if (paramArrayOfString1 == null) {
      paramUri = COLUMNS;
    } else {
      paramUri = paramArrayOfString1;
    }
    String[] arrayOfString = new String[paramUri.length];
    paramString2 = new Object[paramUri.length];
    int k = 0;
    int m = paramUri.length;
    int j = 0;
    while (j < m)
    {
      paramArrayOfString1 = paramUri[j];
      int i;
      if ("_display_name".equals(paramArrayOfString1))
      {
        arrayOfString[k] = "_display_name";
        if (paramString1 == null) {
          paramArrayOfString1 = paramArrayOfString2.getName();
        } else {
          paramArrayOfString1 = paramString1;
        }
        paramString2[k] = paramArrayOfString1;
        i = k + 1;
      }
      else
      {
        i = k;
        if ("_size".equals(paramArrayOfString1))
        {
          arrayOfString[k] = "_size";
          paramString2[k] = Long.valueOf(paramArrayOfString2.length());
          i = k + 1;
        }
      }
      j++;
      k = i;
    }
    paramUri = copyOf(arrayOfString, k);
    paramArrayOfString1 = copyOf(paramString2, k);
    paramUri = new MatrixCursor(paramUri, 1);
    paramUri.addRow(paramArrayOfString1);
    return paramUri;
  }
  
  public int update(Uri paramUri, ContentValues paramContentValues, String paramString, String[] paramArrayOfString)
  {
    throw new UnsupportedOperationException("No external updates");
  }
  
  static class Api21Impl
  {
    static File[] getExternalMediaDirs(Context paramContext)
    {
      return paramContext.getExternalMediaDirs();
    }
  }
  
  static abstract interface PathStrategy
  {
    public abstract File getFileForUri(Uri paramUri);
    
    public abstract Uri getUriForFile(File paramFile);
  }
  
  static class SimplePathStrategy
    implements FileProvider.PathStrategy
  {
    private final String mAuthority;
    private final HashMap<String, File> mRoots = new HashMap();
    
    SimplePathStrategy(String paramString)
    {
      this.mAuthority = paramString;
    }
    
    void addRoot(String paramString, File paramFile)
    {
      if (!TextUtils.isEmpty(paramString)) {
        try
        {
          File localFile = paramFile.getCanonicalFile();
          this.mRoots.put(paramString, localFile);
          return;
        }
        catch (IOException paramString)
        {
          throw new IllegalArgumentException("Failed to resolve canonical path for " + paramFile, paramString);
        }
      }
      throw new IllegalArgumentException("Name must not be empty");
    }
    
    public File getFileForUri(Uri paramUri)
    {
      Object localObject2 = paramUri.getEncodedPath();
      int i = ((String)localObject2).indexOf('/', 1);
      Object localObject1 = Uri.decode(((String)localObject2).substring(1, i));
      Log5ECF72.a(localObject1);
      LogE84000.a(localObject1);
      Log229316.a(localObject1);
      localObject2 = Uri.decode(((String)localObject2).substring(i + 1));
      Log5ECF72.a(localObject2);
      LogE84000.a(localObject2);
      Log229316.a(localObject2);
      localObject1 = (File)this.mRoots.get(localObject1);
      if (localObject1 != null)
      {
        paramUri = new File((File)localObject1, (String)localObject2);
        try
        {
          localObject2 = paramUri.getCanonicalFile();
          if (((File)localObject2).getPath().startsWith(((File)localObject1).getPath())) {
            return (File)localObject2;
          }
          throw new SecurityException("Resolved path jumped beyond configured root");
        }
        catch (IOException localIOException)
        {
          throw new IllegalArgumentException("Failed to resolve canonical path for " + paramUri);
        }
      }
      throw new IllegalArgumentException("Unable to find configured root for " + paramUri);
    }
    
    public Uri getUriForFile(File paramFile)
    {
      try
      {
        String str1 = paramFile.getCanonicalPath();
        paramFile = null;
        Iterator localIterator = this.mRoots.entrySet().iterator();
        Object localObject2;
        Object localObject1;
        while (localIterator.hasNext())
        {
          localObject2 = (Map.Entry)localIterator.next();
          String str2 = ((File)((Map.Entry)localObject2).getValue()).getPath();
          localObject1 = paramFile;
          if (str1.startsWith(str2)) {
            if (paramFile != null)
            {
              localObject1 = paramFile;
              if (str2.length() <= ((File)paramFile.getValue()).getPath().length()) {}
            }
            else
            {
              localObject1 = localObject2;
            }
          }
          paramFile = (File)localObject1;
        }
        if (paramFile != null)
        {
          localObject1 = ((File)paramFile.getValue()).getPath();
          if (((String)localObject1).endsWith("/")) {
            localObject1 = str1.substring(((String)localObject1).length());
          } else {
            localObject1 = str1.substring(((String)localObject1).length() + 1);
          }
          localObject2 = new StringBuilder();
          paramFile = Uri.encode((String)paramFile.getKey());
          Log5ECF72.a(paramFile);
          LogE84000.a(paramFile);
          Log229316.a(paramFile);
          paramFile = ((StringBuilder)localObject2).append(paramFile).append('/');
          localObject1 = Uri.encode((String)localObject1, "/");
          Log5ECF72.a(localObject1);
          LogE84000.a(localObject1);
          Log229316.a(localObject1);
          paramFile = (String)localObject1;
          return new Uri.Builder().scheme("content").authority(this.mAuthority).encodedPath(paramFile).build();
        }
        throw new IllegalArgumentException("Failed to find configured root that contains " + str1);
      }
      catch (IOException localIOException)
      {
        throw new IllegalArgumentException("Failed to resolve canonical path for " + paramFile);
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/content/FileProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */