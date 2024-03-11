package androidx.documentfile.provider;

import android.content.Context;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.text.TextUtils;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

class DocumentsContractApi19
{
  private static final int FLAG_VIRTUAL_DOCUMENT = 512;
  private static final String TAG = "DocumentFile";
  
  public static boolean canRead(Context paramContext, Uri paramUri)
  {
    if (paramContext.checkCallingOrSelfUriPermission(paramUri, 1) != 0) {
      return false;
    }
    paramContext = getRawType(paramContext, paramUri);
    Log5ECF72.a(paramContext);
    LogE84000.a(paramContext);
    Log229316.a(paramContext);
    return !TextUtils.isEmpty(paramContext);
  }
  
  public static boolean canWrite(Context paramContext, Uri paramUri)
  {
    if (paramContext.checkCallingOrSelfUriPermission(paramUri, 2) != 0) {
      return false;
    }
    String str = getRawType(paramContext, paramUri);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    int i = queryForInt(paramContext, paramUri, "flags", 0);
    if (TextUtils.isEmpty(str)) {
      return false;
    }
    if ((i & 0x4) != 0) {
      return true;
    }
    if (("vnd.android.document/directory".equals(str)) && ((i & 0x8) != 0)) {
      return true;
    }
    return (!TextUtils.isEmpty(str)) && ((i & 0x2) != 0);
  }
  
  private static void closeQuietly(AutoCloseable paramAutoCloseable)
  {
    if (paramAutoCloseable != null) {
      try
      {
        paramAutoCloseable.close();
      }
      catch (Exception paramAutoCloseable) {}catch (RuntimeException paramAutoCloseable)
      {
        throw paramAutoCloseable;
      }
    }
  }
  
  /* Error */
  public static boolean exists(Context paramContext, Uri paramUri)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 77	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   4: astore 5
    //   6: aconst_null
    //   7: astore 4
    //   9: aconst_null
    //   10: astore_0
    //   11: iconst_0
    //   12: istore_3
    //   13: aload 5
    //   15: aload_1
    //   16: iconst_1
    //   17: anewarray 57	java/lang/String
    //   20: dup
    //   21: iconst_0
    //   22: ldc 79
    //   24: aastore
    //   25: aconst_null
    //   26: aconst_null
    //   27: aconst_null
    //   28: invokevirtual 85	android/content/ContentResolver:query	(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   31: astore_1
    //   32: aload_1
    //   33: astore_0
    //   34: aload_1
    //   35: astore 4
    //   37: aload_1
    //   38: invokeinterface 91 1 0
    //   43: istore_2
    //   44: iload_2
    //   45: ifle +5 -> 50
    //   48: iconst_1
    //   49: istore_3
    //   50: aload_1
    //   51: invokestatic 93	androidx/documentfile/provider/DocumentsContractApi19:closeQuietly	(Ljava/lang/AutoCloseable;)V
    //   54: iload_3
    //   55: ireturn
    //   56: astore_1
    //   57: goto +49 -> 106
    //   60: astore 5
    //   62: aload 4
    //   64: astore_0
    //   65: new 95	java/lang/StringBuilder
    //   68: astore_1
    //   69: aload 4
    //   71: astore_0
    //   72: aload_1
    //   73: invokespecial 96	java/lang/StringBuilder:<init>	()V
    //   76: aload 4
    //   78: astore_0
    //   79: ldc 11
    //   81: aload_1
    //   82: ldc 98
    //   84: invokevirtual 102	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   87: aload 5
    //   89: invokevirtual 105	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   92: invokevirtual 109	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   95: invokestatic 115	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   98: pop
    //   99: aload 4
    //   101: invokestatic 93	androidx/documentfile/provider/DocumentsContractApi19:closeQuietly	(Ljava/lang/AutoCloseable;)V
    //   104: iconst_0
    //   105: ireturn
    //   106: aload_0
    //   107: invokestatic 93	androidx/documentfile/provider/DocumentsContractApi19:closeQuietly	(Ljava/lang/AutoCloseable;)V
    //   110: aload_1
    //   111: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	112	0	paramContext	Context
    //   0	112	1	paramUri	Uri
    //   43	2	2	i	int
    //   12	43	3	bool	boolean
    //   7	93	4	localUri	Uri
    //   4	10	5	localContentResolver	android.content.ContentResolver
    //   60	28	5	localException	Exception
    // Exception table:
    //   from	to	target	type
    //   13	32	56	finally
    //   37	44	56	finally
    //   65	69	56	finally
    //   72	76	56	finally
    //   79	99	56	finally
    //   13	32	60	java/lang/Exception
    //   37	44	60	java/lang/Exception
  }
  
  public static long getFlags(Context paramContext, Uri paramUri)
  {
    return queryForLong(paramContext, paramUri, "flags", 0L);
  }
  
  public static String getName(Context paramContext, Uri paramUri)
  {
    paramContext = queryForString(paramContext, paramUri, "_display_name", null);
    Log5ECF72.a(paramContext);
    LogE84000.a(paramContext);
    Log229316.a(paramContext);
    return paramContext;
  }
  
  private static String getRawType(Context paramContext, Uri paramUri)
  {
    paramContext = queryForString(paramContext, paramUri, "mime_type", null);
    Log5ECF72.a(paramContext);
    LogE84000.a(paramContext);
    Log229316.a(paramContext);
    return paramContext;
  }
  
  public static String getType(Context paramContext, Uri paramUri)
  {
    paramContext = getRawType(paramContext, paramUri);
    Log5ECF72.a(paramContext);
    LogE84000.a(paramContext);
    Log229316.a(paramContext);
    if ("vnd.android.document/directory".equals(paramContext)) {
      return null;
    }
    return paramContext;
  }
  
  public static boolean isDirectory(Context paramContext, Uri paramUri)
  {
    paramContext = getRawType(paramContext, paramUri);
    Log5ECF72.a(paramContext);
    LogE84000.a(paramContext);
    Log229316.a(paramContext);
    return "vnd.android.document/directory".equals(paramContext);
  }
  
  public static boolean isFile(Context paramContext, Uri paramUri)
  {
    paramContext = getRawType(paramContext, paramUri);
    Log5ECF72.a(paramContext);
    LogE84000.a(paramContext);
    Log229316.a(paramContext);
    return (!"vnd.android.document/directory".equals(paramContext)) && (!TextUtils.isEmpty(paramContext));
  }
  
  public static boolean isVirtual(Context paramContext, Uri paramUri)
  {
    boolean bool2 = DocumentsContract.isDocumentUri(paramContext, paramUri);
    boolean bool1 = false;
    if (!bool2) {
      return false;
    }
    if ((getFlags(paramContext, paramUri) & 0x200) != 0L) {
      bool1 = true;
    }
    return bool1;
  }
  
  public static long lastModified(Context paramContext, Uri paramUri)
  {
    return queryForLong(paramContext, paramUri, "last_modified", 0L);
  }
  
  public static long length(Context paramContext, Uri paramUri)
  {
    return queryForLong(paramContext, paramUri, "_size", 0L);
  }
  
  private static int queryForInt(Context paramContext, Uri paramUri, String paramString, int paramInt)
  {
    return (int)queryForLong(paramContext, paramUri, paramString, paramInt);
  }
  
  /* Error */
  private static long queryForLong(Context paramContext, Uri paramUri, String paramString, long paramLong)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 77	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   4: astore 8
    //   6: aconst_null
    //   7: astore 7
    //   9: aconst_null
    //   10: astore_0
    //   11: aload 8
    //   13: aload_1
    //   14: iconst_1
    //   15: anewarray 57	java/lang/String
    //   18: dup
    //   19: iconst_0
    //   20: aload_2
    //   21: aastore
    //   22: aconst_null
    //   23: aconst_null
    //   24: aconst_null
    //   25: invokevirtual 85	android/content/ContentResolver:query	(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   28: astore_1
    //   29: aload_1
    //   30: astore_0
    //   31: aload_1
    //   32: astore 7
    //   34: aload_1
    //   35: invokeinterface 153 1 0
    //   40: ifeq +39 -> 79
    //   43: aload_1
    //   44: astore_0
    //   45: aload_1
    //   46: astore 7
    //   48: aload_1
    //   49: iconst_0
    //   50: invokeinterface 157 2 0
    //   55: ifne +24 -> 79
    //   58: aload_1
    //   59: astore_0
    //   60: aload_1
    //   61: astore 7
    //   63: aload_1
    //   64: iconst_0
    //   65: invokeinterface 161 2 0
    //   70: lstore 5
    //   72: aload_1
    //   73: invokestatic 93	androidx/documentfile/provider/DocumentsContractApi19:closeQuietly	(Ljava/lang/AutoCloseable;)V
    //   76: lload 5
    //   78: lreturn
    //   79: aload_1
    //   80: invokestatic 93	androidx/documentfile/provider/DocumentsContractApi19:closeQuietly	(Ljava/lang/AutoCloseable;)V
    //   83: lload_3
    //   84: lreturn
    //   85: astore_1
    //   86: goto +47 -> 133
    //   89: astore_2
    //   90: aload 7
    //   92: astore_0
    //   93: new 95	java/lang/StringBuilder
    //   96: astore_1
    //   97: aload 7
    //   99: astore_0
    //   100: aload_1
    //   101: invokespecial 96	java/lang/StringBuilder:<init>	()V
    //   104: aload 7
    //   106: astore_0
    //   107: ldc 11
    //   109: aload_1
    //   110: ldc 98
    //   112: invokevirtual 102	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   115: aload_2
    //   116: invokevirtual 105	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   119: invokevirtual 109	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   122: invokestatic 115	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   125: pop
    //   126: aload 7
    //   128: invokestatic 93	androidx/documentfile/provider/DocumentsContractApi19:closeQuietly	(Ljava/lang/AutoCloseable;)V
    //   131: lload_3
    //   132: lreturn
    //   133: aload_0
    //   134: invokestatic 93	androidx/documentfile/provider/DocumentsContractApi19:closeQuietly	(Ljava/lang/AutoCloseable;)V
    //   137: aload_1
    //   138: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	139	0	paramContext	Context
    //   0	139	1	paramUri	Uri
    //   0	139	2	paramString	String
    //   0	139	3	paramLong	long
    //   70	7	5	l	long
    //   7	120	7	localUri	Uri
    //   4	8	8	localContentResolver	android.content.ContentResolver
    // Exception table:
    //   from	to	target	type
    //   11	29	85	finally
    //   34	43	85	finally
    //   48	58	85	finally
    //   63	72	85	finally
    //   93	97	85	finally
    //   100	104	85	finally
    //   107	126	85	finally
    //   11	29	89	java/lang/Exception
    //   34	43	89	java/lang/Exception
    //   48	58	89	java/lang/Exception
    //   63	72	89	java/lang/Exception
  }
  
  /* Error */
  private static String queryForString(Context paramContext, Uri paramUri, String paramString1, String paramString2)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 77	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   4: astore 5
    //   6: aconst_null
    //   7: astore 4
    //   9: aconst_null
    //   10: astore_0
    //   11: aload 5
    //   13: aload_1
    //   14: iconst_1
    //   15: anewarray 57	java/lang/String
    //   18: dup
    //   19: iconst_0
    //   20: aload_2
    //   21: aastore
    //   22: aconst_null
    //   23: aconst_null
    //   24: aconst_null
    //   25: invokevirtual 85	android/content/ContentResolver:query	(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   28: astore_1
    //   29: aload_1
    //   30: astore_0
    //   31: aload_1
    //   32: astore 4
    //   34: aload_1
    //   35: invokeinterface 153 1 0
    //   40: ifeq +37 -> 77
    //   43: aload_1
    //   44: astore_0
    //   45: aload_1
    //   46: astore 4
    //   48: aload_1
    //   49: iconst_0
    //   50: invokeinterface 157 2 0
    //   55: ifne +22 -> 77
    //   58: aload_1
    //   59: astore_0
    //   60: aload_1
    //   61: astore 4
    //   63: aload_1
    //   64: iconst_0
    //   65: invokeinterface 165 2 0
    //   70: astore_2
    //   71: aload_1
    //   72: invokestatic 93	androidx/documentfile/provider/DocumentsContractApi19:closeQuietly	(Ljava/lang/AutoCloseable;)V
    //   75: aload_2
    //   76: areturn
    //   77: aload_1
    //   78: invokestatic 93	androidx/documentfile/provider/DocumentsContractApi19:closeQuietly	(Ljava/lang/AutoCloseable;)V
    //   81: aload_3
    //   82: areturn
    //   83: astore_1
    //   84: goto +47 -> 131
    //   87: astore_2
    //   88: aload 4
    //   90: astore_0
    //   91: new 95	java/lang/StringBuilder
    //   94: astore_1
    //   95: aload 4
    //   97: astore_0
    //   98: aload_1
    //   99: invokespecial 96	java/lang/StringBuilder:<init>	()V
    //   102: aload 4
    //   104: astore_0
    //   105: ldc 11
    //   107: aload_1
    //   108: ldc 98
    //   110: invokevirtual 102	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   113: aload_2
    //   114: invokevirtual 105	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   117: invokevirtual 109	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   120: invokestatic 115	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   123: pop
    //   124: aload 4
    //   126: invokestatic 93	androidx/documentfile/provider/DocumentsContractApi19:closeQuietly	(Ljava/lang/AutoCloseable;)V
    //   129: aload_3
    //   130: areturn
    //   131: aload_0
    //   132: invokestatic 93	androidx/documentfile/provider/DocumentsContractApi19:closeQuietly	(Ljava/lang/AutoCloseable;)V
    //   135: aload_1
    //   136: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	137	0	paramContext	Context
    //   0	137	1	paramUri	Uri
    //   0	137	2	paramString1	String
    //   0	137	3	paramString2	String
    //   7	118	4	localUri	Uri
    //   4	8	5	localContentResolver	android.content.ContentResolver
    // Exception table:
    //   from	to	target	type
    //   11	29	83	finally
    //   34	43	83	finally
    //   48	58	83	finally
    //   63	71	83	finally
    //   91	95	83	finally
    //   98	102	83	finally
    //   105	124	83	finally
    //   11	29	87	java/lang/Exception
    //   34	43	87	java/lang/Exception
    //   48	58	87	java/lang/Exception
    //   63	71	87	java/lang/Exception
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/documentfile/provider/DocumentsContractApi19.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */