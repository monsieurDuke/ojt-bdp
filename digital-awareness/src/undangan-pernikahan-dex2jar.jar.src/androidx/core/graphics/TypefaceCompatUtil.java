package androidx.core.graphics;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import androidx.core.provider.FontsContractCompat.FontInfo;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TypefaceCompatUtil
{
  private static final String CACHE_FILE_PREFIX = ".font";
  private static final String TAG = "TypefaceCompatUtil";
  
  public static void closeQuietly(Closeable paramCloseable)
  {
    if (paramCloseable != null) {
      try
      {
        paramCloseable.close();
      }
      catch (IOException paramCloseable) {}
    }
  }
  
  public static ByteBuffer copyToDirectBuffer(Context paramContext, Resources paramResources, int paramInt)
  {
    paramContext = getTempFile(paramContext);
    if (paramContext == null) {
      return null;
    }
    try
    {
      boolean bool = copyToFile(paramContext, paramResources, paramInt);
      if (!bool) {
        return null;
      }
      paramResources = mmap(paramContext);
      return paramResources;
    }
    finally
    {
      paramContext.delete();
    }
  }
  
  public static boolean copyToFile(File paramFile, Resources paramResources, int paramInt)
  {
    Resources localResources = null;
    try
    {
      paramResources = paramResources.openRawResource(paramInt);
      localResources = paramResources;
      boolean bool = copyToFile(paramFile, paramResources);
      return bool;
    }
    finally
    {
      closeQuietly(localResources);
    }
  }
  
  /* Error */
  public static boolean copyToFile(File paramFile, java.io.InputStream paramInputStream)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 6
    //   3: aconst_null
    //   4: astore 7
    //   6: invokestatic 65	android/os/StrictMode:allowThreadDiskWrites	()Landroid/os/StrictMode$ThreadPolicy;
    //   9: astore 8
    //   11: aload 7
    //   13: astore_3
    //   14: aload 6
    //   16: astore 4
    //   18: new 67	java/io/FileOutputStream
    //   21: astore 5
    //   23: aload 7
    //   25: astore_3
    //   26: aload 6
    //   28: astore 4
    //   30: aload 5
    //   32: aload_0
    //   33: iconst_0
    //   34: invokespecial 70	java/io/FileOutputStream:<init>	(Ljava/io/File;Z)V
    //   37: aload 5
    //   39: astore_0
    //   40: aload_0
    //   41: astore_3
    //   42: aload_0
    //   43: astore 4
    //   45: sipush 1024
    //   48: newarray <illegal type>
    //   50: astore 5
    //   52: aload_0
    //   53: astore_3
    //   54: aload_0
    //   55: astore 4
    //   57: aload_1
    //   58: aload 5
    //   60: invokevirtual 76	java/io/InputStream:read	([B)I
    //   63: istore_2
    //   64: iload_2
    //   65: iconst_m1
    //   66: if_icmpeq +19 -> 85
    //   69: aload_0
    //   70: astore_3
    //   71: aload_0
    //   72: astore 4
    //   74: aload_0
    //   75: aload 5
    //   77: iconst_0
    //   78: iload_2
    //   79: invokevirtual 80	java/io/FileOutputStream:write	([BII)V
    //   82: goto -30 -> 52
    //   85: aload_0
    //   86: invokestatic 59	androidx/core/graphics/TypefaceCompatUtil:closeQuietly	(Ljava/io/Closeable;)V
    //   89: aload 8
    //   91: invokestatic 84	android/os/StrictMode:setThreadPolicy	(Landroid/os/StrictMode$ThreadPolicy;)V
    //   94: iconst_1
    //   95: ireturn
    //   96: astore_0
    //   97: goto +55 -> 152
    //   100: astore_1
    //   101: aload 4
    //   103: astore_3
    //   104: new 86	java/lang/StringBuilder
    //   107: astore_0
    //   108: aload 4
    //   110: astore_3
    //   111: aload_0
    //   112: invokespecial 87	java/lang/StringBuilder:<init>	()V
    //   115: aload 4
    //   117: astore_3
    //   118: ldc 14
    //   120: aload_0
    //   121: ldc 89
    //   123: invokevirtual 93	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   126: aload_1
    //   127: invokevirtual 97	java/io/IOException:getMessage	()Ljava/lang/String;
    //   130: invokevirtual 93	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   133: invokevirtual 100	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   136: invokestatic 106	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   139: pop
    //   140: aload 4
    //   142: invokestatic 59	androidx/core/graphics/TypefaceCompatUtil:closeQuietly	(Ljava/io/Closeable;)V
    //   145: aload 8
    //   147: invokestatic 84	android/os/StrictMode:setThreadPolicy	(Landroid/os/StrictMode$ThreadPolicy;)V
    //   150: iconst_0
    //   151: ireturn
    //   152: aload_3
    //   153: invokestatic 59	androidx/core/graphics/TypefaceCompatUtil:closeQuietly	(Ljava/io/Closeable;)V
    //   156: aload 8
    //   158: invokestatic 84	android/os/StrictMode:setThreadPolicy	(Landroid/os/StrictMode$ThreadPolicy;)V
    //   161: aload_0
    //   162: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	163	0	paramFile	File
    //   0	163	1	paramInputStream	java.io.InputStream
    //   63	16	2	i	int
    //   13	140	3	localObject1	Object
    //   16	125	4	localObject2	Object
    //   21	55	5	localObject3	Object
    //   1	26	6	localObject4	Object
    //   4	20	7	localObject5	Object
    //   9	148	8	localThreadPolicy	android.os.StrictMode.ThreadPolicy
    // Exception table:
    //   from	to	target	type
    //   18	23	96	finally
    //   30	37	96	finally
    //   45	52	96	finally
    //   57	64	96	finally
    //   74	82	96	finally
    //   104	108	96	finally
    //   111	115	96	finally
    //   118	140	96	finally
    //   18	23	100	java/io/IOException
    //   30	37	100	java/io/IOException
    //   45	52	100	java/io/IOException
    //   57	64	100	java/io/IOException
    //   74	82	100	java/io/IOException
  }
  
  public static File getTempFile(Context paramContext)
  {
    File localFile1 = paramContext.getCacheDir();
    if (localFile1 == null) {
      return null;
    }
    paramContext = ".font" + Process.myPid() + "-" + Process.myTid() + "-";
    for (int i = 0; i < 100; i++)
    {
      File localFile2 = new File(localFile1, paramContext + i);
      try
      {
        boolean bool = localFile2.createNewFile();
        if (bool) {
          return localFile2;
        }
      }
      catch (IOException localIOException) {}
    }
    return null;
  }
  
  /* Error */
  public static ByteBuffer mmap(Context paramContext, CancellationSignal paramCancellationSignal, Uri paramUri)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 137	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   4: astore_0
    //   5: aload_0
    //   6: aload_2
    //   7: ldc -117
    //   9: aload_1
    //   10: invokestatic 143	androidx/core/graphics/TypefaceCompatUtil$Api19Impl:openFileDescriptor	(Landroid/content/ContentResolver;Landroid/net/Uri;Ljava/lang/String;Landroid/os/CancellationSignal;)Landroid/os/ParcelFileDescriptor;
    //   13: astore_0
    //   14: aload_0
    //   15: ifnonnull +13 -> 28
    //   18: aload_0
    //   19: ifnull +7 -> 26
    //   22: aload_0
    //   23: invokevirtual 146	android/os/ParcelFileDescriptor:close	()V
    //   26: aconst_null
    //   27: areturn
    //   28: new 148	java/io/FileInputStream
    //   31: astore_1
    //   32: aload_1
    //   33: aload_0
    //   34: invokevirtual 152	android/os/ParcelFileDescriptor:getFileDescriptor	()Ljava/io/FileDescriptor;
    //   37: invokespecial 155	java/io/FileInputStream:<init>	(Ljava/io/FileDescriptor;)V
    //   40: aload_1
    //   41: invokevirtual 159	java/io/FileInputStream:getChannel	()Ljava/nio/channels/FileChannel;
    //   44: astore_2
    //   45: aload_2
    //   46: invokevirtual 165	java/nio/channels/FileChannel:size	()J
    //   49: lstore_3
    //   50: aload_2
    //   51: getstatic 171	java/nio/channels/FileChannel$MapMode:READ_ONLY	Ljava/nio/channels/FileChannel$MapMode;
    //   54: lconst_0
    //   55: lload_3
    //   56: invokevirtual 175	java/nio/channels/FileChannel:map	(Ljava/nio/channels/FileChannel$MapMode;JJ)Ljava/nio/MappedByteBuffer;
    //   59: astore_2
    //   60: aload_1
    //   61: invokevirtual 176	java/io/FileInputStream:close	()V
    //   64: aload_0
    //   65: ifnull +7 -> 72
    //   68: aload_0
    //   69: invokevirtual 146	android/os/ParcelFileDescriptor:close	()V
    //   72: aload_2
    //   73: areturn
    //   74: astore_2
    //   75: aload_1
    //   76: invokevirtual 176	java/io/FileInputStream:close	()V
    //   79: goto +9 -> 88
    //   82: astore_1
    //   83: aload_2
    //   84: aload_1
    //   85: invokevirtual 182	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   88: aload_2
    //   89: athrow
    //   90: astore_1
    //   91: aload_0
    //   92: ifnull +16 -> 108
    //   95: aload_0
    //   96: invokevirtual 146	android/os/ParcelFileDescriptor:close	()V
    //   99: goto +9 -> 108
    //   102: astore_0
    //   103: aload_1
    //   104: aload_0
    //   105: invokevirtual 182	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   108: aload_1
    //   109: athrow
    //   110: astore_0
    //   111: aconst_null
    //   112: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	113	0	paramContext	Context
    //   0	113	1	paramCancellationSignal	CancellationSignal
    //   0	113	2	paramUri	Uri
    //   49	7	3	l	long
    // Exception table:
    //   from	to	target	type
    //   40	60	74	finally
    //   75	79	82	finally
    //   28	40	90	finally
    //   60	64	90	finally
    //   83	88	90	finally
    //   88	90	90	finally
    //   95	99	102	finally
    //   5	14	110	java/io/IOException
    //   22	26	110	java/io/IOException
    //   68	72	110	java/io/IOException
    //   103	108	110	java/io/IOException
    //   108	110	110	java/io/IOException
  }
  
  /* Error */
  private static ByteBuffer mmap(File paramFile)
  {
    // Byte code:
    //   0: new 148	java/io/FileInputStream
    //   3: astore_3
    //   4: aload_3
    //   5: aload_0
    //   6: invokespecial 185	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   9: aload_3
    //   10: invokevirtual 159	java/io/FileInputStream:getChannel	()Ljava/nio/channels/FileChannel;
    //   13: astore_0
    //   14: aload_0
    //   15: invokevirtual 165	java/nio/channels/FileChannel:size	()J
    //   18: lstore_1
    //   19: aload_0
    //   20: getstatic 171	java/nio/channels/FileChannel$MapMode:READ_ONLY	Ljava/nio/channels/FileChannel$MapMode;
    //   23: lconst_0
    //   24: lload_1
    //   25: invokevirtual 175	java/nio/channels/FileChannel:map	(Ljava/nio/channels/FileChannel$MapMode;JJ)Ljava/nio/MappedByteBuffer;
    //   28: astore_0
    //   29: aload_3
    //   30: invokevirtual 176	java/io/FileInputStream:close	()V
    //   33: aload_0
    //   34: areturn
    //   35: astore_0
    //   36: aload_3
    //   37: invokevirtual 176	java/io/FileInputStream:close	()V
    //   40: goto +9 -> 49
    //   43: astore_3
    //   44: aload_0
    //   45: aload_3
    //   46: invokevirtual 182	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   49: aload_0
    //   50: athrow
    //   51: astore_0
    //   52: aconst_null
    //   53: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	54	0	paramFile	File
    //   18	7	1	l	long
    //   3	34	3	localFileInputStream	java.io.FileInputStream
    //   43	3	3	localThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   9	29	35	finally
    //   36	40	43	finally
    //   0	9	51	java/io/IOException
    //   29	33	51	java/io/IOException
    //   44	49	51	java/io/IOException
    //   49	51	51	java/io/IOException
  }
  
  public static Map<Uri, ByteBuffer> readFontInfoIntoByteBuffer(Context paramContext, FontsContractCompat.FontInfo[] paramArrayOfFontInfo, CancellationSignal paramCancellationSignal)
  {
    HashMap localHashMap = new HashMap();
    int j = paramArrayOfFontInfo.length;
    for (int i = 0; i < j; i++)
    {
      Object localObject = paramArrayOfFontInfo[i];
      if (((FontsContractCompat.FontInfo)localObject).getResultCode() == 0)
      {
        localObject = ((FontsContractCompat.FontInfo)localObject).getUri();
        if (!localHashMap.containsKey(localObject)) {
          localHashMap.put(localObject, mmap(paramContext, paramCancellationSignal, (Uri)localObject));
        }
      }
    }
    return Collections.unmodifiableMap(localHashMap);
  }
  
  static class Api19Impl
  {
    static ParcelFileDescriptor openFileDescriptor(ContentResolver paramContentResolver, Uri paramUri, String paramString, CancellationSignal paramCancellationSignal)
      throws FileNotFoundException
    {
      return paramContentResolver.openFileDescriptor(paramUri, paramString, paramCancellationSignal);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/graphics/TypefaceCompatUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */