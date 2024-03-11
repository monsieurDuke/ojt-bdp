package androidx.work;

import android.content.Context;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public abstract class WorkerFactory
{
  private static final String TAG;
  
  static
  {
    String str = Logger.tagWithPrefix("WorkerFactory");
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    TAG = str;
  }
  
  public static WorkerFactory getDefaultWorkerFactory()
  {
    new WorkerFactory()
    {
      public ListenableWorker createWorker(Context paramAnonymousContext, String paramAnonymousString, WorkerParameters paramAnonymousWorkerParameters)
      {
        return null;
      }
    };
  }
  
  public abstract ListenableWorker createWorker(Context paramContext, String paramString, WorkerParameters paramWorkerParameters);
  
  /* Error */
  public final ListenableWorker createWorkerWithDefaultFallback(Context paramContext, String paramString, WorkerParameters paramWorkerParameters)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: aload_2
    //   3: aload_3
    //   4: invokevirtual 44	androidx/work/WorkerFactory:createWorker	(Landroid/content/Context;Ljava/lang/String;Landroidx/work/WorkerParameters;)Landroidx/work/ListenableWorker;
    //   7: astore 6
    //   9: aload 6
    //   11: astore 5
    //   13: aload 6
    //   15: ifnonnull +155 -> 170
    //   18: aconst_null
    //   19: astore 4
    //   21: aload_2
    //   22: invokestatic 50	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   25: ldc 52
    //   27: invokevirtual 56	java/lang/Class:asSubclass	(Ljava/lang/Class;)Ljava/lang/Class;
    //   30: astore 5
    //   32: aload 5
    //   34: astore 4
    //   36: goto +42 -> 78
    //   39: astore 5
    //   41: invokestatic 60	androidx/work/Logger:get	()Landroidx/work/Logger;
    //   44: getstatic 32	androidx/work/WorkerFactory:TAG	Ljava/lang/String;
    //   47: new 62	java/lang/StringBuilder
    //   50: dup
    //   51: invokespecial 63	java/lang/StringBuilder:<init>	()V
    //   54: ldc 65
    //   56: invokevirtual 69	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   59: aload_2
    //   60: invokevirtual 69	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   63: invokevirtual 73	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   66: iconst_1
    //   67: anewarray 75	java/lang/Throwable
    //   70: dup
    //   71: iconst_0
    //   72: aload 5
    //   74: aastore
    //   75: invokevirtual 79	androidx/work/Logger:error	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Throwable;)V
    //   78: aload 6
    //   80: astore 5
    //   82: aload 4
    //   84: ifnull +86 -> 170
    //   87: aload 4
    //   89: iconst_2
    //   90: anewarray 46	java/lang/Class
    //   93: dup
    //   94: iconst_0
    //   95: ldc 81
    //   97: aastore
    //   98: dup
    //   99: iconst_1
    //   100: ldc 83
    //   102: aastore
    //   103: invokevirtual 87	java/lang/Class:getDeclaredConstructor	([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;
    //   106: iconst_2
    //   107: anewarray 4	java/lang/Object
    //   110: dup
    //   111: iconst_0
    //   112: aload_1
    //   113: aastore
    //   114: dup
    //   115: iconst_1
    //   116: aload_3
    //   117: aastore
    //   118: invokevirtual 93	java/lang/reflect/Constructor:newInstance	([Ljava/lang/Object;)Ljava/lang/Object;
    //   121: checkcast 52	androidx/work/ListenableWorker
    //   124: astore 5
    //   126: goto +44 -> 170
    //   129: astore_1
    //   130: invokestatic 60	androidx/work/Logger:get	()Landroidx/work/Logger;
    //   133: getstatic 32	androidx/work/WorkerFactory:TAG	Ljava/lang/String;
    //   136: new 62	java/lang/StringBuilder
    //   139: dup
    //   140: invokespecial 63	java/lang/StringBuilder:<init>	()V
    //   143: ldc 95
    //   145: invokevirtual 69	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   148: aload_2
    //   149: invokevirtual 69	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   152: invokevirtual 73	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   155: iconst_1
    //   156: anewarray 75	java/lang/Throwable
    //   159: dup
    //   160: iconst_0
    //   161: aload_1
    //   162: aastore
    //   163: invokevirtual 79	androidx/work/Logger:error	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Throwable;)V
    //   166: aload 6
    //   168: astore 5
    //   170: aload 5
    //   172: ifnull +59 -> 231
    //   175: aload 5
    //   177: invokevirtual 99	androidx/work/ListenableWorker:isUsed	()Z
    //   180: ifne +6 -> 186
    //   183: goto +48 -> 231
    //   186: ldc 101
    //   188: iconst_2
    //   189: anewarray 4	java/lang/Object
    //   192: dup
    //   193: iconst_0
    //   194: aload_0
    //   195: invokevirtual 105	java/lang/Object:getClass	()Ljava/lang/Class;
    //   198: invokevirtual 108	java/lang/Class:getName	()Ljava/lang/String;
    //   201: aastore
    //   202: dup
    //   203: iconst_1
    //   204: aload_2
    //   205: aastore
    //   206: invokestatic 114	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   209: astore_1
    //   210: aload_1
    //   211: invokestatic 24	mt/Log5ECF72:a	(Ljava/lang/Object;)V
    //   214: aload_1
    //   215: invokestatic 27	mt/LogE84000:a	(Ljava/lang/Object;)V
    //   218: aload_1
    //   219: invokestatic 30	mt/Log229316:a	(Ljava/lang/Object;)V
    //   222: new 116	java/lang/IllegalStateException
    //   225: dup
    //   226: aload_1
    //   227: invokespecial 119	java/lang/IllegalStateException:<init>	(Ljava/lang/String;)V
    //   230: athrow
    //   231: aload 5
    //   233: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	234	0	this	WorkerFactory
    //   0	234	1	paramContext	Context
    //   0	234	2	paramString	String
    //   0	234	3	paramWorkerParameters	WorkerParameters
    //   19	69	4	localObject1	Object
    //   11	22	5	localObject2	Object
    //   39	34	5	localObject3	Object
    //   80	152	5	localListenableWorker1	ListenableWorker
    //   7	160	6	localListenableWorker2	ListenableWorker
    // Exception table:
    //   from	to	target	type
    //   21	32	39	finally
    //   87	126	129	finally
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/WorkerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */