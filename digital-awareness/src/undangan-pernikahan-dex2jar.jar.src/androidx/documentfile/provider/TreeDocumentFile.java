package androidx.documentfile.provider;

import android.content.Context;
import android.net.Uri;
import android.provider.DocumentsContract;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

class TreeDocumentFile
  extends DocumentFile
{
  private Context mContext;
  private Uri mUri;
  
  TreeDocumentFile(DocumentFile paramDocumentFile, Context paramContext, Uri paramUri)
  {
    super(paramDocumentFile);
    this.mContext = paramContext;
    this.mUri = paramUri;
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
  
  private static Uri createFile(Context paramContext, Uri paramUri, String paramString1, String paramString2)
  {
    try
    {
      paramContext = DocumentsContract.createDocument(paramContext.getContentResolver(), paramUri, paramString1, paramString2);
      return paramContext;
    }
    catch (Exception paramContext) {}
    return null;
  }
  
  public boolean canRead()
  {
    return DocumentsContractApi19.canRead(this.mContext, this.mUri);
  }
  
  public boolean canWrite()
  {
    return DocumentsContractApi19.canWrite(this.mContext, this.mUri);
  }
  
  public DocumentFile createDirectory(String paramString)
  {
    paramString = createFile(this.mContext, this.mUri, "vnd.android.document/directory", paramString);
    if (paramString != null) {
      paramString = new TreeDocumentFile(this, this.mContext, paramString);
    } else {
      paramString = null;
    }
    return paramString;
  }
  
  public DocumentFile createFile(String paramString1, String paramString2)
  {
    paramString1 = createFile(this.mContext, this.mUri, paramString1, paramString2);
    if (paramString1 != null) {
      paramString1 = new TreeDocumentFile(this, this.mContext, paramString1);
    } else {
      paramString1 = null;
    }
    return paramString1;
  }
  
  public boolean delete()
  {
    try
    {
      boolean bool = DocumentsContract.deleteDocument(this.mContext.getContentResolver(), this.mUri);
      return bool;
    }
    catch (Exception localException) {}
    return false;
  }
  
  public boolean exists()
  {
    return DocumentsContractApi19.exists(this.mContext, this.mUri);
  }
  
  public String getName()
  {
    String str = DocumentsContractApi19.getName(this.mContext, this.mUri);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str;
  }
  
  public String getType()
  {
    String str = DocumentsContractApi19.getType(this.mContext, this.mUri);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str;
  }
  
  public Uri getUri()
  {
    return this.mUri;
  }
  
  public boolean isDirectory()
  {
    return DocumentsContractApi19.isDirectory(this.mContext, this.mUri);
  }
  
  public boolean isFile()
  {
    return DocumentsContractApi19.isFile(this.mContext, this.mUri);
  }
  
  public boolean isVirtual()
  {
    return DocumentsContractApi19.isVirtual(this.mContext, this.mUri);
  }
  
  public long lastModified()
  {
    return DocumentsContractApi19.lastModified(this.mContext, this.mUri);
  }
  
  public long length()
  {
    return DocumentsContractApi19.length(this.mContext, this.mUri);
  }
  
  /* Error */
  public DocumentFile[] listFiles()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 15	androidx/documentfile/provider/TreeDocumentFile:mContext	Landroid/content/Context;
    //   4: invokevirtual 38	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   7: astore 4
    //   9: aload_0
    //   10: getfield 17	androidx/documentfile/provider/TreeDocumentFile:mUri	Landroid/net/Uri;
    //   13: astore_2
    //   14: aload_2
    //   15: invokestatic 116	android/provider/DocumentsContract:getDocumentId	(Landroid/net/Uri;)Ljava/lang/String;
    //   18: astore_3
    //   19: aload_3
    //   20: invokestatic 82	mt/Log5ECF72:a	(Ljava/lang/Object;)V
    //   23: aload_3
    //   24: invokestatic 85	mt/LogE84000:a	(Ljava/lang/Object;)V
    //   27: aload_3
    //   28: invokestatic 88	mt/Log229316:a	(Ljava/lang/Object;)V
    //   31: aload_2
    //   32: aload_3
    //   33: invokestatic 120	android/provider/DocumentsContract:buildChildDocumentsUriUsingTree	(Landroid/net/Uri;Ljava/lang/String;)Landroid/net/Uri;
    //   36: astore 6
    //   38: new 122	java/util/ArrayList
    //   41: dup
    //   42: invokespecial 124	java/util/ArrayList:<init>	()V
    //   45: astore 5
    //   47: aconst_null
    //   48: astore_2
    //   49: aconst_null
    //   50: astore_3
    //   51: aload 4
    //   53: aload 6
    //   55: iconst_1
    //   56: anewarray 126	java/lang/String
    //   59: dup
    //   60: iconst_0
    //   61: ldc -128
    //   63: aastore
    //   64: aconst_null
    //   65: aconst_null
    //   66: aconst_null
    //   67: invokevirtual 134	android/content/ContentResolver:query	(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   70: astore 4
    //   72: aload 4
    //   74: astore_3
    //   75: aload 4
    //   77: astore_2
    //   78: aload 4
    //   80: invokeinterface 139 1 0
    //   85: ifeq +43 -> 128
    //   88: aload 4
    //   90: astore_3
    //   91: aload 4
    //   93: astore_2
    //   94: aload 4
    //   96: iconst_0
    //   97: invokeinterface 143 2 0
    //   102: astore 6
    //   104: aload 4
    //   106: astore_3
    //   107: aload 4
    //   109: astore_2
    //   110: aload 5
    //   112: aload_0
    //   113: getfield 17	androidx/documentfile/provider/TreeDocumentFile:mUri	Landroid/net/Uri;
    //   116: aload 6
    //   118: invokestatic 146	android/provider/DocumentsContract:buildDocumentUriUsingTree	(Landroid/net/Uri;Ljava/lang/String;)Landroid/net/Uri;
    //   121: invokevirtual 150	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   124: pop
    //   125: goto -53 -> 72
    //   128: aload 4
    //   130: astore_2
    //   131: aload_2
    //   132: invokestatic 152	androidx/documentfile/provider/TreeDocumentFile:closeQuietly	(Ljava/lang/AutoCloseable;)V
    //   135: goto +49 -> 184
    //   138: astore_2
    //   139: goto +102 -> 241
    //   142: astore 4
    //   144: aload_2
    //   145: astore_3
    //   146: new 154	java/lang/StringBuilder
    //   149: astore 6
    //   151: aload_2
    //   152: astore_3
    //   153: aload 6
    //   155: invokespecial 155	java/lang/StringBuilder:<init>	()V
    //   158: aload_2
    //   159: astore_3
    //   160: ldc -99
    //   162: aload 6
    //   164: ldc -97
    //   166: invokevirtual 163	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   169: aload 4
    //   171: invokevirtual 166	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   174: invokevirtual 169	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   177: invokestatic 175	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   180: pop
    //   181: goto -50 -> 131
    //   184: aload 5
    //   186: aload 5
    //   188: invokevirtual 179	java/util/ArrayList:size	()I
    //   191: anewarray 181	android/net/Uri
    //   194: invokevirtual 185	java/util/ArrayList:toArray	([Ljava/lang/Object;)[Ljava/lang/Object;
    //   197: checkcast 187	[Landroid/net/Uri;
    //   200: astore_3
    //   201: aload_3
    //   202: arraylength
    //   203: anewarray 4	androidx/documentfile/provider/DocumentFile
    //   206: astore_2
    //   207: iconst_0
    //   208: istore_1
    //   209: iload_1
    //   210: aload_3
    //   211: arraylength
    //   212: if_icmpge +27 -> 239
    //   215: aload_2
    //   216: iload_1
    //   217: new 2	androidx/documentfile/provider/TreeDocumentFile
    //   220: dup
    //   221: aload_0
    //   222: aload_0
    //   223: getfield 15	androidx/documentfile/provider/TreeDocumentFile:mContext	Landroid/content/Context;
    //   226: aload_3
    //   227: iload_1
    //   228: aaload
    //   229: invokespecial 62	androidx/documentfile/provider/TreeDocumentFile:<init>	(Landroidx/documentfile/provider/DocumentFile;Landroid/content/Context;Landroid/net/Uri;)V
    //   232: aastore
    //   233: iinc 1 1
    //   236: goto -27 -> 209
    //   239: aload_2
    //   240: areturn
    //   241: aload_3
    //   242: invokestatic 152	androidx/documentfile/provider/TreeDocumentFile:closeQuietly	(Ljava/lang/AutoCloseable;)V
    //   245: aload_2
    //   246: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	247	0	this	TreeDocumentFile
    //   208	26	1	i	int
    //   13	119	2	localObject1	Object
    //   138	21	2	localObject2	Object
    //   206	40	2	arrayOfDocumentFile	DocumentFile[]
    //   18	224	3	localObject3	Object
    //   7	122	4	localObject4	Object
    //   142	28	4	localException	Exception
    //   45	142	5	localArrayList	java.util.ArrayList
    //   36	127	6	localObject5	Object
    // Exception table:
    //   from	to	target	type
    //   51	72	138	finally
    //   78	88	138	finally
    //   94	104	138	finally
    //   110	125	138	finally
    //   146	151	138	finally
    //   153	158	138	finally
    //   160	181	138	finally
    //   51	72	142	java/lang/Exception
    //   78	88	142	java/lang/Exception
    //   94	104	142	java/lang/Exception
    //   110	125	142	java/lang/Exception
  }
  
  public boolean renameTo(String paramString)
  {
    try
    {
      paramString = DocumentsContract.renameDocument(this.mContext.getContentResolver(), this.mUri, paramString);
      if (paramString != null)
      {
        this.mUri = paramString;
        return true;
      }
      return false;
    }
    catch (Exception paramString) {}
    return false;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/documentfile/provider/TreeDocumentFile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */