package com.google.android.material.color;

final class ColorResourcesLoaderCreator
{
  private static final String TAG = ColorResourcesLoaderCreator.class.getSimpleName();
  
  /* Error */
  static android.content.res.loader.ResourcesLoader create(android.content.Context paramContext, java.util.Map<Integer, Integer> paramMap)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: invokestatic 30	com/google/android/material/color/ColorResourcesTableCreator:create	(Landroid/content/Context;Ljava/util/Map;)[B
    //   5: astore 4
    //   7: getstatic 16	com/google/android/material/color/ColorResourcesLoaderCreator:TAG	Ljava/lang/String;
    //   10: astore_0
    //   11: new 32	java/lang/StringBuilder
    //   14: astore_1
    //   15: aload_1
    //   16: invokespecial 33	java/lang/StringBuilder:<init>	()V
    //   19: aload_0
    //   20: aload_1
    //   21: ldc 35
    //   23: invokevirtual 39	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   26: aload 4
    //   28: arraylength
    //   29: invokevirtual 42	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   32: invokevirtual 45	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   35: invokestatic 51	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   38: pop
    //   39: aload 4
    //   41: arraylength
    //   42: istore_2
    //   43: iload_2
    //   44: ifne +5 -> 49
    //   47: aconst_null
    //   48: areturn
    //   49: aconst_null
    //   50: astore_0
    //   51: ldc 53
    //   53: iconst_0
    //   54: invokestatic 59	android/system/Os:memfd_create	(Ljava/lang/String;I)Ljava/io/FileDescriptor;
    //   57: astore_1
    //   58: aload_1
    //   59: astore_0
    //   60: new 61	java/io/FileOutputStream
    //   63: astore_3
    //   64: aload_1
    //   65: astore_0
    //   66: aload_3
    //   67: aload_1
    //   68: invokespecial 64	java/io/FileOutputStream:<init>	(Ljava/io/FileDescriptor;)V
    //   71: aload_3
    //   72: aload 4
    //   74: invokevirtual 70	java/io/OutputStream:write	([B)V
    //   77: aload_1
    //   78: invokestatic 76	android/os/ParcelFileDescriptor:dup	(Ljava/io/FileDescriptor;)Landroid/os/ParcelFileDescriptor;
    //   81: astore_0
    //   82: new 78	android/content/res/loader/ResourcesLoader
    //   85: astore 4
    //   87: aload 4
    //   89: invokespecial 79	android/content/res/loader/ResourcesLoader:<init>	()V
    //   92: aload 4
    //   94: aload_0
    //   95: aconst_null
    //   96: invokestatic 85	android/content/res/loader/ResourcesProvider:loadFromTable	(Landroid/os/ParcelFileDescriptor;Landroid/content/res/loader/AssetsProvider;)Landroid/content/res/loader/ResourcesProvider;
    //   99: invokevirtual 89	android/content/res/loader/ResourcesLoader:addProvider	(Landroid/content/res/loader/ResourcesProvider;)V
    //   102: aload_0
    //   103: ifnull +7 -> 110
    //   106: aload_0
    //   107: invokevirtual 92	android/os/ParcelFileDescriptor:close	()V
    //   110: aload_1
    //   111: astore_0
    //   112: aload_3
    //   113: invokevirtual 93	java/io/OutputStream:close	()V
    //   116: aload_1
    //   117: ifnull +7 -> 124
    //   120: aload_1
    //   121: invokestatic 95	android/system/Os:close	(Ljava/io/FileDescriptor;)V
    //   124: aload 4
    //   126: areturn
    //   127: astore 4
    //   129: aload_0
    //   130: ifnull +17 -> 147
    //   133: aload_0
    //   134: invokevirtual 92	android/os/ParcelFileDescriptor:close	()V
    //   137: goto +10 -> 147
    //   140: astore_0
    //   141: aload 4
    //   143: aload_0
    //   144: invokevirtual 101	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   147: aload 4
    //   149: athrow
    //   150: astore 4
    //   152: aload_3
    //   153: invokevirtual 93	java/io/OutputStream:close	()V
    //   156: goto +12 -> 168
    //   159: astore_3
    //   160: aload_1
    //   161: astore_0
    //   162: aload 4
    //   164: aload_3
    //   165: invokevirtual 101	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   168: aload_1
    //   169: astore_0
    //   170: aload 4
    //   172: athrow
    //   173: astore_1
    //   174: aload_0
    //   175: ifnull +7 -> 182
    //   178: aload_0
    //   179: invokestatic 95	android/system/Os:close	(Ljava/io/FileDescriptor;)V
    //   182: aload_1
    //   183: athrow
    //   184: astore_0
    //   185: getstatic 16	com/google/android/material/color/ColorResourcesLoaderCreator:TAG	Ljava/lang/String;
    //   188: ldc 103
    //   190: aload_0
    //   191: invokestatic 107	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   194: pop
    //   195: aconst_null
    //   196: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	197	0	paramContext	android.content.Context
    //   0	197	1	paramMap	java.util.Map<Integer, Integer>
    //   42	2	2	i	int
    //   63	90	3	localFileOutputStream	java.io.FileOutputStream
    //   159	6	3	localThrowable	Throwable
    //   5	120	4	localObject1	Object
    //   127	21	4	localObject2	Object
    //   150	21	4	localObject3	Object
    // Exception table:
    //   from	to	target	type
    //   82	92	127	finally
    //   92	102	127	finally
    //   133	137	140	finally
    //   71	82	150	finally
    //   106	110	150	finally
    //   141	147	150	finally
    //   147	150	150	finally
    //   152	156	159	finally
    //   51	58	173	finally
    //   60	64	173	finally
    //   66	71	173	finally
    //   112	116	173	finally
    //   162	168	173	finally
    //   170	173	173	finally
    //   0	43	184	java/lang/Exception
    //   120	124	184	java/lang/Exception
    //   178	182	184	java/lang/Exception
    //   182	184	184	java/lang/Exception
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/color/ColorResourcesLoaderCreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */