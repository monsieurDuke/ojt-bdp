package mt;

import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;

public class Log5ECF72
  extends Thread
{
  private static final ThreadLocal PARAMETER_BUFFER;
  private static final LinkedBlockingQueue QUEUE;
  private static final SimpleDateFormat TIME_FORMAT1 = new SimpleDateFormat("HH:mm:ss.SSS");
  private static final SimpleDateFormat TIME_FORMAT2 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
  
  static
  {
    QUEUE = new LinkedBlockingQueue();
    PARAMETER_BUFFER = new ThreadLocal();
    Log5ECF72 localLog5ECF72 = new Log5ECF72();
    localLog5ECF72.setDaemon(true);
    localLog5ECF72.start();
  }
  
  public static void a(char paramChar)
  {
    z(String.valueOf(paramChar));
  }
  
  public static void a(double paramDouble)
  {
    z(String.valueOf(paramDouble));
  }
  
  public static void a(float paramFloat)
  {
    z(String.valueOf(paramFloat));
  }
  
  public static void a(int paramInt)
  {
    z(String.valueOf(paramInt));
  }
  
  public static void a(long paramLong)
  {
    z(String.valueOf(paramLong));
  }
  
  public static void a(Object paramObject)
  {
    z(y(paramObject));
  }
  
  public static void a(boolean paramBoolean)
  {
    z(String.valueOf(paramBoolean));
  }
  
  public static void b()
  {
    ThreadLocal localThreadLocal = PARAMETER_BUFFER;
    z(((StringBuilder)localThreadLocal.get()).toString());
    localThreadLocal.remove();
  }
  
  public static void b1(char paramChar)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Parameter1: ");
    localStringBuilder.append(paramChar);
    x(localStringBuilder.toString());
  }
  
  public static void b1(double paramDouble)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Parameter1: ");
    localStringBuilder.append(paramDouble);
    x(localStringBuilder.toString());
  }
  
  public static void b1(float paramFloat)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Parameter1: ");
    localStringBuilder.append(paramFloat);
    x(localStringBuilder.toString());
  }
  
  public static void b1(int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Parameter1: ");
    localStringBuilder.append(paramInt);
    x(localStringBuilder.toString());
  }
  
  public static void b1(long paramLong)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Parameter1: ");
    localStringBuilder.append(paramLong);
    x(localStringBuilder.toString());
  }
  
  public static void b1(Object paramObject)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Parameter1: ");
    localStringBuilder.append(y(paramObject));
    x(localStringBuilder.toString());
  }
  
  public static void b1(boolean paramBoolean)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Parameter1: ");
    localStringBuilder.append(paramBoolean);
    x(localStringBuilder.toString());
  }
  
  public static void b2(char paramChar)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Parameter2: ");
    localStringBuilder.append(paramChar);
    x(localStringBuilder.toString());
  }
  
  public static void b2(double paramDouble)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Parameter2: ");
    localStringBuilder.append(paramDouble);
    x(localStringBuilder.toString());
  }
  
  public static void b2(float paramFloat)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Parameter2: ");
    localStringBuilder.append(paramFloat);
    x(localStringBuilder.toString());
  }
  
  public static void b2(int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Parameter2: ");
    localStringBuilder.append(paramInt);
    x(localStringBuilder.toString());
  }
  
  public static void b2(long paramLong)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Parameter2: ");
    localStringBuilder.append(paramLong);
    x(localStringBuilder.toString());
  }
  
  public static void b2(Object paramObject)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Parameter2: ");
    localStringBuilder.append(y(paramObject));
    x(localStringBuilder.toString());
  }
  
  public static void b2(boolean paramBoolean)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Parameter2: ");
    localStringBuilder.append(paramBoolean);
    x(localStringBuilder.toString());
  }
  
  public static void b3(char paramChar)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Parameter3: ");
    localStringBuilder.append(paramChar);
    x(localStringBuilder.toString());
  }
  
  public static void b3(double paramDouble)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Parameter3: ");
    localStringBuilder.append(paramDouble);
    x(localStringBuilder.toString());
  }
  
  public static void b3(float paramFloat)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Parameter3: ");
    localStringBuilder.append(paramFloat);
    x(localStringBuilder.toString());
  }
  
  public static void b3(int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Parameter3: ");
    localStringBuilder.append(paramInt);
    x(localStringBuilder.toString());
  }
  
  public static void b3(long paramLong)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Parameter3: ");
    localStringBuilder.append(paramLong);
    x(localStringBuilder.toString());
  }
  
  public static void b3(Object paramObject)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Parameter3: ");
    localStringBuilder.append(y(paramObject));
    x(localStringBuilder.toString());
  }
  
  public static void b3(boolean paramBoolean)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Parameter3: ");
    localStringBuilder.append(paramBoolean);
    x(localStringBuilder.toString());
  }
  
  public static void b4(char paramChar)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Parameter4: ");
    localStringBuilder.append(paramChar);
    x(localStringBuilder.toString());
  }
  
  public static void b4(double paramDouble)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Parameter4: ");
    localStringBuilder.append(paramDouble);
    x(localStringBuilder.toString());
  }
  
  public static void b4(float paramFloat)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Parameter4: ");
    localStringBuilder.append(paramFloat);
    x(localStringBuilder.toString());
  }
  
  public static void b4(int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Parameter4: ");
    localStringBuilder.append(paramInt);
    x(localStringBuilder.toString());
  }
  
  public static void b4(long paramLong)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Parameter4: ");
    localStringBuilder.append(paramLong);
    x(localStringBuilder.toString());
  }
  
  public static void b4(Object paramObject)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Parameter4: ");
    localStringBuilder.append(y(paramObject));
    x(localStringBuilder.toString());
  }
  
  public static void b4(boolean paramBoolean)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Parameter4: ");
    localStringBuilder.append(paramBoolean);
    x(localStringBuilder.toString());
  }
  
  public static void b5(char paramChar)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Parameter5: ");
    localStringBuilder.append(paramChar);
    x(localStringBuilder.toString());
  }
  
  public static void b5(double paramDouble)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Parameter5: ");
    localStringBuilder.append(paramDouble);
    x(localStringBuilder.toString());
  }
  
  public static void b5(float paramFloat)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Parameter5: ");
    localStringBuilder.append(paramFloat);
    x(localStringBuilder.toString());
  }
  
  public static void b5(int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Parameter5: ");
    localStringBuilder.append(paramInt);
    x(localStringBuilder.toString());
  }
  
  public static void b5(long paramLong)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Parameter5: ");
    localStringBuilder.append(paramLong);
    x(localStringBuilder.toString());
  }
  
  public static void b5(Object paramObject)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Parameter5: ");
    localStringBuilder.append(y(paramObject));
    x(localStringBuilder.toString());
  }
  
  public static void b5(boolean paramBoolean)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Parameter5: ");
    localStringBuilder.append(paramBoolean);
    x(localStringBuilder.toString());
  }
  
  public static void b6(char paramChar)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Parameter6: ");
    localStringBuilder.append(paramChar);
    x(localStringBuilder.toString());
  }
  
  public static void b6(double paramDouble)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Parameter6: ");
    localStringBuilder.append(paramDouble);
    x(localStringBuilder.toString());
  }
  
  public static void b6(float paramFloat)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Parameter6: ");
    localStringBuilder.append(paramFloat);
    x(localStringBuilder.toString());
  }
  
  public static void b6(int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Parameter6: ");
    localStringBuilder.append(paramInt);
    x(localStringBuilder.toString());
  }
  
  public static void b6(long paramLong)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Parameter6: ");
    localStringBuilder.append(paramLong);
    x(localStringBuilder.toString());
  }
  
  public static void b6(Object paramObject)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Parameter6: ");
    localStringBuilder.append(y(paramObject));
    x(localStringBuilder.toString());
  }
  
  public static void b6(boolean paramBoolean)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Parameter6: ");
    localStringBuilder.append(paramBoolean);
    x(localStringBuilder.toString());
  }
  
  public static void printStackTrace()
  {
    z(Log.getStackTraceString(new Exception("InjectedLog.printStackTrace")));
  }
  
  private static void x(String paramString)
  {
    ThreadLocal localThreadLocal = PARAMETER_BUFFER;
    StringBuilder localStringBuilder2 = (StringBuilder)localThreadLocal.get();
    StringBuilder localStringBuilder1 = localStringBuilder2;
    if (localStringBuilder2 == null)
    {
      localStringBuilder1 = new StringBuilder();
      localThreadLocal.set(localStringBuilder1);
    }
    if (localStringBuilder1.length() > 0) {
      localStringBuilder1.append('\n');
    }
    localStringBuilder1.append(paramString);
  }
  
  private static String y(Object paramObject)
  {
    if (paramObject == null) {
      return "null";
    }
    Class localClass = paramObject.getClass();
    if (localClass.isArray())
    {
      if (localClass == byte[].class) {
        return Arrays.toString((byte[])paramObject);
      }
      if (localClass == short[].class) {
        return Arrays.toString((short[])paramObject);
      }
      if (localClass == int[].class) {
        return Arrays.toString((int[])paramObject);
      }
      if (localClass == long[].class) {
        return Arrays.toString((long[])paramObject);
      }
      if (localClass == char[].class) {
        return Arrays.toString((char[])paramObject);
      }
      if (localClass == float[].class) {
        return Arrays.toString((float[])paramObject);
      }
      if (localClass == double[].class) {
        return Arrays.toString((double[])paramObject);
      }
      if (localClass == boolean[].class) {
        return Arrays.toString((boolean[])paramObject);
      }
      return Arrays.deepToString((Object[])paramObject);
    }
    return paramObject.toString();
  }
  
  private static void z(String paramString)
  {
    String str = "[TIME] [CLASS]\n->[METHOD]([LOCATION])\n[RESULT]\n--------------------\n";
    if ("[TIME] [CLASS]\n->[METHOD]([LOCATION])\n[RESULT]\n--------------------\n".contains("[TIME]")) {
      str = "[TIME] [CLASS]\n->[METHOD]([LOCATION])\n[RESULT]\n--------------------\n".replace("[TIME]", TIME_FORMAT1.format(Long.valueOf(System.currentTimeMillis())));
    }
    StackTraceElement localStackTraceElement = new Throwable().getStackTrace()[2];
    Object localObject2 = localStackTraceElement.getFileName();
    Object localObject1 = localObject2;
    if (localObject2 == null) {
      localObject1 = "Unknown Source";
    }
    int i = localStackTraceElement.getLineNumber();
    localObject2 = localObject1;
    if (i >= 0)
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append(":");
      ((StringBuilder)localObject2).append(i);
      localObject2 = ((StringBuilder)localObject2).toString();
    }
    paramString = str.replace("[RESULT]", paramString).replace("[CLASS]", localStackTraceElement.getClassName()).replace("[METHOD]", localStackTraceElement.getMethodName()).replace("[LOCATION]", (CharSequence)localObject2);
    QUEUE.offer(paramString);
  }
  
  /* Error */
  public void run()
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: ldc_w 291
    //   5: ldc_w 293
    //   8: invokestatic 299	android/os/Environment:getExternalStorageDirectory	()Ljava/io/File;
    //   11: invokevirtual 304	java/io/File:getPath	()Ljava/lang/String;
    //   14: invokevirtual 249	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   17: ldc_w 306
    //   20: ldc_w 308
    //   23: invokevirtual 249	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   26: ldc -29
    //   28: getstatic 27	mt/Log5ECF72:TIME_FORMAT2	Ljava/text/SimpleDateFormat;
    //   31: invokestatic 237	java/lang/System:currentTimeMillis	()J
    //   34: invokestatic 242	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   37: invokevirtual 245	java/text/SimpleDateFormat:format	(Ljava/lang/Object;)Ljava/lang/String;
    //   40: invokevirtual 249	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   43: bipush 92
    //   45: bipush 47
    //   47: invokevirtual 311	java/lang/String:replace	(CC)Ljava/lang/String;
    //   50: ldc_w 313
    //   53: ldc_w 315
    //   56: invokevirtual 249	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   59: astore_3
    //   60: aconst_null
    //   61: astore_2
    //   62: new 301	java/io/File
    //   65: astore 4
    //   67: aload 4
    //   69: aload_3
    //   70: invokespecial 316	java/io/File:<init>	(Ljava/lang/String;)V
    //   73: aload 4
    //   75: invokevirtual 319	java/io/File:getParentFile	()Ljava/io/File;
    //   78: astore_3
    //   79: aload_3
    //   80: ifnull +8 -> 88
    //   83: aload_3
    //   84: invokevirtual 322	java/io/File:mkdirs	()Z
    //   87: pop
    //   88: new 324	java/io/FileOutputStream
    //   91: astore_3
    //   92: aload_3
    //   93: aload 4
    //   95: iconst_1
    //   96: invokespecial 327	java/io/FileOutputStream:<init>	(Ljava/io/File;Z)V
    //   99: aload_3
    //   100: astore_1
    //   101: goto +8 -> 109
    //   104: astore_2
    //   105: aload_2
    //   106: invokevirtual 329	java/io/IOException:printStackTrace	()V
    //   109: aload_1
    //   110: astore_3
    //   111: aload_1
    //   112: ifnonnull +95 -> 207
    //   115: new 301	java/io/File
    //   118: astore 4
    //   120: aload 4
    //   122: ldc_w 331
    //   125: invokespecial 316	java/io/File:<init>	(Ljava/lang/String;)V
    //   128: new 301	java/io/File
    //   131: astore_3
    //   132: new 90	java/lang/StringBuilder
    //   135: astore_1
    //   136: aload_1
    //   137: invokespecial 99	java/lang/StringBuilder:<init>	()V
    //   140: aload_1
    //   141: getstatic 27	mt/Log5ECF72:TIME_FORMAT2	Ljava/text/SimpleDateFormat;
    //   144: invokestatic 237	java/lang/System:currentTimeMillis	()J
    //   147: invokestatic 242	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   150: invokevirtual 245	java/text/SimpleDateFormat:format	(Ljava/lang/Object;)Ljava/lang/String;
    //   153: invokevirtual 105	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   156: pop
    //   157: aload_1
    //   158: ldc_w 333
    //   161: invokevirtual 105	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   164: pop
    //   165: aload_3
    //   166: aload 4
    //   168: aload_1
    //   169: invokevirtual 94	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   172: invokespecial 336	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   175: aload 4
    //   177: invokevirtual 322	java/io/File:mkdirs	()Z
    //   180: pop
    //   181: new 324	java/io/FileOutputStream
    //   184: dup
    //   185: aload_3
    //   186: invokespecial 339	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   189: astore_3
    //   190: goto +17 -> 207
    //   193: astore_1
    //   194: aload_1
    //   195: invokevirtual 329	java/io/IOException:printStackTrace	()V
    //   198: new 341	java/lang/RuntimeException
    //   201: dup
    //   202: aload_2
    //   203: invokespecial 344	java/lang/RuntimeException:<init>	(Ljava/lang/Throwable;)V
    //   206: athrow
    //   207: invokestatic 350	java/nio/charset/Charset:defaultCharset	()Ljava/nio/charset/Charset;
    //   210: astore_2
    //   211: getstatic 33	mt/Log5ECF72:QUEUE	Ljava/util/concurrent/LinkedBlockingQueue;
    //   214: astore_1
    //   215: aload_3
    //   216: aload_1
    //   217: invokevirtual 353	java/util/concurrent/LinkedBlockingQueue:take	()Ljava/lang/Object;
    //   220: checkcast 52	java/lang/String
    //   223: aload_2
    //   224: invokevirtual 357	java/lang/String:getBytes	(Ljava/nio/charset/Charset;)[B
    //   227: invokevirtual 361	java/io/FileOutputStream:write	([B)V
    //   230: aload_1
    //   231: invokevirtual 364	java/util/concurrent/LinkedBlockingQueue:isEmpty	()Z
    //   234: ifeq +7 -> 241
    //   237: aload_3
    //   238: invokevirtual 367	java/io/FileOutputStream:flush	()V
    //   241: goto -30 -> 211
    //   244: astore_1
    //   245: new 341	java/lang/RuntimeException
    //   248: dup
    //   249: aload_1
    //   250: invokespecial 344	java/lang/RuntimeException:<init>	(Ljava/lang/Throwable;)V
    //   253: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	254	0	this	Log5ECF72
    //   1	168	1	localObject1	Object
    //   193	2	1	localIOException1	java.io.IOException
    //   214	17	1	localLinkedBlockingQueue	LinkedBlockingQueue
    //   244	6	1	localException	Exception
    //   61	1	2	localObject2	Object
    //   104	99	2	localIOException2	java.io.IOException
    //   210	14	2	localCharset	java.nio.charset.Charset
    //   59	179	3	localObject3	Object
    //   65	111	4	localFile	java.io.File
    // Exception table:
    //   from	to	target	type
    //   62	79	104	java/io/IOException
    //   83	88	104	java/io/IOException
    //   88	99	104	java/io/IOException
    //   115	190	193	java/io/IOException
    //   207	211	244	java/lang/Exception
    //   211	241	244	java/lang/Exception
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/mt/Log5ECF72.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */