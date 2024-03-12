package androidx.core.location;

import android.content.Context;
import android.location.GnssStatus;
import android.location.GnssStatus.Callback;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.Settings.Secure;
import android.text.TextUtils;
import androidx.collection.SimpleArrayMap;
import androidx.core.os.ExecutorCompat;
import androidx.core.util.Consumer;
import androidx.core.util.ObjectsCompat;
import androidx.core.util.Preconditions;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.WeakHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public final class LocationManagerCompat
{
  private static final long GET_CURRENT_LOCATION_TIMEOUT_MS = 30000L;
  private static final long MAX_CURRENT_LOCATION_AGE_MS = 10000L;
  private static final long PRE_N_LOOPER_TIMEOUT_S = 5L;
  private static Field sContextField;
  static final WeakHashMap<LocationListenerKey, WeakReference<LocationListenerTransport>> sLocationListeners = new WeakHashMap();
  
  public static void getCurrentLocation(LocationManager paramLocationManager, String paramString, androidx.core.os.CancellationSignal paramCancellationSignal, Executor paramExecutor, Consumer<Location> paramConsumer)
  {
    if (Build.VERSION.SDK_INT >= 30)
    {
      Api30Impl.getCurrentLocation(paramLocationManager, paramString, paramCancellationSignal, paramExecutor, paramConsumer);
      return;
    }
    if (paramCancellationSignal != null) {
      paramCancellationSignal.throwIfCanceled();
    }
    Location localLocation = paramLocationManager.getLastKnownLocation(paramString);
    if ((localLocation != null) && (SystemClock.elapsedRealtime() - LocationCompat.getElapsedRealtimeMillis(localLocation) < 10000L))
    {
      paramExecutor.execute(new LocationManagerCompat..ExternalSyntheticLambda1(paramConsumer, localLocation));
      return;
    }
    paramExecutor = new CancellableLocationListener(paramLocationManager, paramExecutor, paramConsumer);
    paramLocationManager.requestLocationUpdates(paramString, 0L, 0.0F, paramExecutor, Looper.getMainLooper());
    if (paramCancellationSignal != null)
    {
      Objects.requireNonNull(paramExecutor);
      paramCancellationSignal.setOnCancelListener(new LocationManagerCompat..ExternalSyntheticLambda2(paramExecutor));
    }
    paramExecutor.startTimeout(30000L);
  }
  
  public static String getGnssHardwareModelName(LocationManager paramLocationManager)
  {
    if (Build.VERSION.SDK_INT >= 28)
    {
      paramLocationManager = Api28Impl.getGnssHardwareModelName(paramLocationManager);
      Log5ECF72.a(paramLocationManager);
      LogE84000.a(paramLocationManager);
      Log229316.a(paramLocationManager);
      return paramLocationManager;
    }
    return null;
  }
  
  public static int getGnssYearOfHardware(LocationManager paramLocationManager)
  {
    if (Build.VERSION.SDK_INT >= 28) {
      return Api28Impl.getGnssYearOfHardware(paramLocationManager);
    }
    return 0;
  }
  
  public static boolean hasProvider(LocationManager paramLocationManager, String paramString)
  {
    if (Build.VERSION.SDK_INT >= 31) {
      return Api31Impl.hasProvider(paramLocationManager, paramString);
    }
    boolean bool2 = paramLocationManager.getAllProviders().contains(paramString);
    boolean bool1 = true;
    if (bool2) {
      return true;
    }
    try
    {
      paramLocationManager = paramLocationManager.getProvider(paramString);
      if (paramLocationManager == null) {
        bool1 = false;
      }
      return bool1;
    }
    catch (SecurityException paramLocationManager) {}
    return false;
  }
  
  public static boolean isLocationEnabled(LocationManager paramLocationManager)
  {
    if (Build.VERSION.SDK_INT >= 28) {
      return Api28Impl.isLocationEnabled(paramLocationManager);
    }
    int i = Build.VERSION.SDK_INT;
    boolean bool2 = false;
    boolean bool1 = false;
    if (i <= 19) {
      try
      {
        if (sContextField == null)
        {
          localObject = LocationManager.class.getDeclaredField("mContext");
          sContextField = (Field)localObject;
          ((Field)localObject).setAccessible(true);
        }
        Object localObject = (Context)sContextField.get(paramLocationManager);
        if (localObject != null)
        {
          if (Build.VERSION.SDK_INT == 19)
          {
            if (Settings.Secure.getInt(((Context)localObject).getContentResolver(), "location_mode", 0) != 0) {
              bool1 = true;
            }
            return bool1;
          }
          localObject = Settings.Secure.getString(((Context)localObject).getContentResolver(), "location_providers_allowed");
          Log5ECF72.a(localObject);
          LogE84000.a(localObject);
          Log229316.a(localObject);
          bool1 = TextUtils.isEmpty((CharSequence)localObject);
          return bool1 ^ true;
        }
      }
      catch (IllegalAccessException localIllegalAccessException) {}catch (NoSuchFieldException localNoSuchFieldException) {}catch (SecurityException localSecurityException) {}catch (ClassCastException localClassCastException) {}
    }
    if (!paramLocationManager.isProviderEnabled("network"))
    {
      bool1 = bool2;
      if (!paramLocationManager.isProviderEnabled("gps")) {}
    }
    else
    {
      bool1 = true;
    }
    return bool1;
  }
  
  /* Error */
  private static boolean registerGnssStatusCallback(LocationManager paramLocationManager, Handler paramHandler, Executor paramExecutor, GnssStatusCompat.Callback paramCallback)
  {
    // Byte code:
    //   0: getstatic 78	android/os/Build$VERSION:SDK_INT	I
    //   3: bipush 30
    //   5: if_icmplt +11 -> 16
    //   8: aload_0
    //   9: aload_1
    //   10: aload_2
    //   11: aload_3
    //   12: invokestatic 281	androidx/core/location/LocationManagerCompat$Api30Impl:registerGnssStatusCallback	(Landroid/location/LocationManager;Landroid/os/Handler;Ljava/util/concurrent/Executor;Landroidx/core/location/GnssStatusCompat$Callback;)Z
    //   15: ireturn
    //   16: getstatic 78	android/os/Build$VERSION:SDK_INT	I
    //   19: bipush 24
    //   21: if_icmplt +11 -> 32
    //   24: aload_0
    //   25: aload_1
    //   26: aload_2
    //   27: aload_3
    //   28: invokestatic 282	androidx/core/location/LocationManagerCompat$Api24Impl:registerGnssStatusCallback	(Landroid/location/LocationManager;Landroid/os/Handler;Ljava/util/concurrent/Executor;Landroidx/core/location/GnssStatusCompat$Callback;)Z
    //   31: ireturn
    //   32: aload_1
    //   33: ifnull +9 -> 42
    //   36: iconst_1
    //   37: istore 11
    //   39: goto +6 -> 45
    //   42: iconst_0
    //   43: istore 11
    //   45: iload 11
    //   47: invokestatic 287	androidx/core/util/Preconditions:checkArgument	(Z)V
    //   50: getstatic 291	androidx/core/location/LocationManagerCompat$GnssLazyLoader:sGnssStatusListeners	Landroidx/collection/SimpleArrayMap;
    //   53: astore 19
    //   55: aload 19
    //   57: monitorenter
    //   58: getstatic 291	androidx/core/location/LocationManagerCompat$GnssLazyLoader:sGnssStatusListeners	Landroidx/collection/SimpleArrayMap;
    //   61: aload_3
    //   62: invokevirtual 294	androidx/collection/SimpleArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   65: checkcast 30	androidx/core/location/LocationManagerCompat$GpsStatusTransport
    //   68: astore 18
    //   70: aload 18
    //   72: ifnonnull +18 -> 90
    //   75: new 30	androidx/core/location/LocationManagerCompat$GpsStatusTransport
    //   78: astore 18
    //   80: aload 18
    //   82: aload_0
    //   83: aload_3
    //   84: invokespecial 297	androidx/core/location/LocationManagerCompat$GpsStatusTransport:<init>	(Landroid/location/LocationManager;Landroidx/core/location/GnssStatusCompat$Callback;)V
    //   87: goto +8 -> 95
    //   90: aload 18
    //   92: invokevirtual 300	androidx/core/location/LocationManagerCompat$GpsStatusTransport:unregister	()V
    //   95: aload 18
    //   97: aload_2
    //   98: invokevirtual 304	androidx/core/location/LocationManagerCompat$GpsStatusTransport:register	(Ljava/util/concurrent/Executor;)V
    //   101: new 306	java/util/concurrent/FutureTask
    //   104: astore_2
    //   105: new 308	androidx/core/location/LocationManagerCompat$$ExternalSyntheticLambda0
    //   108: astore 20
    //   110: aload 20
    //   112: aload_0
    //   113: aload 18
    //   115: invokespecial 311	androidx/core/location/LocationManagerCompat$$ExternalSyntheticLambda0:<init>	(Landroid/location/LocationManager;Landroidx/core/location/LocationManagerCompat$GpsStatusTransport;)V
    //   118: aload_2
    //   119: aload 20
    //   121: invokespecial 314	java/util/concurrent/FutureTask:<init>	(Ljava/util/concurrent/Callable;)V
    //   124: invokestatic 317	android/os/Looper:myLooper	()Landroid/os/Looper;
    //   127: aload_1
    //   128: invokevirtual 322	android/os/Handler:getLooper	()Landroid/os/Looper;
    //   131: if_acmpne +10 -> 141
    //   134: aload_2
    //   135: invokevirtual 325	java/util/concurrent/FutureTask:run	()V
    //   138: goto +15 -> 153
    //   141: aload_1
    //   142: aload_2
    //   143: invokevirtual 329	android/os/Handler:post	(Ljava/lang/Runnable;)Z
    //   146: istore 11
    //   148: iload 11
    //   150: ifeq +321 -> 471
    //   153: iconst_0
    //   154: istore 8
    //   156: iconst_0
    //   157: istore 9
    //   159: iconst_0
    //   160: istore 10
    //   162: iconst_0
    //   163: istore 5
    //   165: iload 8
    //   167: istore 4
    //   169: iload 9
    //   171: istore 6
    //   173: iload 10
    //   175: istore 7
    //   177: getstatic 335	java/util/concurrent/TimeUnit:SECONDS	Ljava/util/concurrent/TimeUnit;
    //   180: ldc2_w 52
    //   183: invokevirtual 339	java/util/concurrent/TimeUnit:toNanos	(J)J
    //   186: lstore 14
    //   188: iload 8
    //   190: istore 4
    //   192: iload 9
    //   194: istore 6
    //   196: iload 10
    //   198: istore 7
    //   200: invokestatic 344	java/lang/System:nanoTime	()J
    //   203: lstore 16
    //   205: lload 14
    //   207: lstore 12
    //   209: iload 5
    //   211: istore 4
    //   213: iload 5
    //   215: istore 6
    //   217: iload 5
    //   219: istore 7
    //   221: aload_2
    //   222: lload 12
    //   224: getstatic 347	java/util/concurrent/TimeUnit:NANOSECONDS	Ljava/util/concurrent/TimeUnit;
    //   227: invokevirtual 350	java/util/concurrent/FutureTask:get	(JLjava/util/concurrent/TimeUnit;)Ljava/lang/Object;
    //   230: checkcast 266	java/lang/Boolean
    //   233: invokevirtual 354	java/lang/Boolean:booleanValue	()Z
    //   236: ifeq +41 -> 277
    //   239: iload 5
    //   241: istore 4
    //   243: iload 5
    //   245: istore 6
    //   247: iload 5
    //   249: istore 7
    //   251: getstatic 291	androidx/core/location/LocationManagerCompat$GnssLazyLoader:sGnssStatusListeners	Landroidx/collection/SimpleArrayMap;
    //   254: aload_3
    //   255: aload 18
    //   257: invokevirtual 358	androidx/collection/SimpleArrayMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   260: pop
    //   261: iload 5
    //   263: ifeq +9 -> 272
    //   266: invokestatic 364	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   269: invokevirtual 367	java/lang/Thread:interrupt	()V
    //   272: aload 19
    //   274: monitorexit
    //   275: iconst_1
    //   276: ireturn
    //   277: iload 5
    //   279: ifeq +9 -> 288
    //   282: invokestatic 364	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   285: invokevirtual 367	java/lang/Thread:interrupt	()V
    //   288: aload 19
    //   290: monitorexit
    //   291: iconst_0
    //   292: ireturn
    //   293: astore_0
    //   294: iconst_1
    //   295: istore 4
    //   297: iconst_1
    //   298: istore 6
    //   300: iconst_1
    //   301: istore 7
    //   303: iconst_1
    //   304: istore 5
    //   306: invokestatic 344	java/lang/System:nanoTime	()J
    //   309: lstore 12
    //   311: lload 16
    //   313: lload 14
    //   315: ladd
    //   316: lload 12
    //   318: lsub
    //   319: lstore 12
    //   321: goto -112 -> 209
    //   324: astore_0
    //   325: goto +133 -> 458
    //   328: astore_3
    //   329: iload 6
    //   331: istore 4
    //   333: new 369	java/lang/IllegalStateException
    //   336: astore_0
    //   337: iload 6
    //   339: istore 4
    //   341: new 371	java/lang/StringBuilder
    //   344: astore_2
    //   345: iload 6
    //   347: istore 4
    //   349: aload_2
    //   350: invokespecial 372	java/lang/StringBuilder:<init>	()V
    //   353: iload 6
    //   355: istore 4
    //   357: aload_0
    //   358: aload_2
    //   359: aload_1
    //   360: invokevirtual 376	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   363: ldc_w 378
    //   366: invokevirtual 381	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   369: invokevirtual 385	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   372: aload_3
    //   373: invokespecial 388	java/lang/IllegalStateException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   376: iload 6
    //   378: istore 4
    //   380: aload_0
    //   381: athrow
    //   382: astore_0
    //   383: iload 7
    //   385: istore 4
    //   387: aload_0
    //   388: invokevirtual 392	java/util/concurrent/ExecutionException:getCause	()Ljava/lang/Throwable;
    //   391: instanceof 394
    //   394: ifne +52 -> 446
    //   397: iload 7
    //   399: istore 4
    //   401: aload_0
    //   402: invokevirtual 392	java/util/concurrent/ExecutionException:getCause	()Ljava/lang/Throwable;
    //   405: instanceof 396
    //   408: ifeq +15 -> 423
    //   411: iload 7
    //   413: istore 4
    //   415: aload_0
    //   416: invokevirtual 392	java/util/concurrent/ExecutionException:getCause	()Ljava/lang/Throwable;
    //   419: checkcast 396	java/lang/Error
    //   422: athrow
    //   423: iload 7
    //   425: istore 4
    //   427: new 369	java/lang/IllegalStateException
    //   430: astore_1
    //   431: iload 7
    //   433: istore 4
    //   435: aload_1
    //   436: aload_0
    //   437: invokespecial 399	java/lang/IllegalStateException:<init>	(Ljava/lang/Throwable;)V
    //   440: iload 7
    //   442: istore 4
    //   444: aload_1
    //   445: athrow
    //   446: iload 7
    //   448: istore 4
    //   450: aload_0
    //   451: invokevirtual 392	java/util/concurrent/ExecutionException:getCause	()Ljava/lang/Throwable;
    //   454: checkcast 394	java/lang/RuntimeException
    //   457: athrow
    //   458: iload 4
    //   460: ifeq +9 -> 469
    //   463: invokestatic 364	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   466: invokevirtual 367	java/lang/Thread:interrupt	()V
    //   469: aload_0
    //   470: athrow
    //   471: new 369	java/lang/IllegalStateException
    //   474: astore_0
    //   475: new 371	java/lang/StringBuilder
    //   478: astore_2
    //   479: aload_2
    //   480: invokespecial 372	java/lang/StringBuilder:<init>	()V
    //   483: aload_0
    //   484: aload_2
    //   485: aload_1
    //   486: invokevirtual 376	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   489: ldc_w 401
    //   492: invokevirtual 381	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   495: invokevirtual 385	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   498: invokespecial 404	java/lang/IllegalStateException:<init>	(Ljava/lang/String;)V
    //   501: aload_0
    //   502: athrow
    //   503: astore_0
    //   504: aload 19
    //   506: monitorexit
    //   507: aload_0
    //   508: athrow
    //   509: astore_0
    //   510: goto -6 -> 504
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	513	0	paramLocationManager	LocationManager
    //   0	513	1	paramHandler	Handler
    //   0	513	2	paramExecutor	Executor
    //   0	513	3	paramCallback	GnssStatusCompat.Callback
    //   167	292	4	i	int
    //   163	142	5	j	int
    //   171	206	6	k	int
    //   175	272	7	m	int
    //   154	35	8	n	int
    //   157	36	9	i1	int
    //   160	37	10	i2	int
    //   37	112	11	bool	boolean
    //   207	113	12	l1	long
    //   186	128	14	l2	long
    //   203	109	16	l3	long
    //   68	188	18	localGpsStatusTransport	GpsStatusTransport
    //   53	452	19	localSimpleArrayMap	SimpleArrayMap
    //   108	12	20	localExternalSyntheticLambda0	LocationManagerCompat..ExternalSyntheticLambda0
    // Exception table:
    //   from	to	target	type
    //   221	239	293	java/lang/InterruptedException
    //   251	261	293	java/lang/InterruptedException
    //   177	188	324	finally
    //   200	205	324	finally
    //   221	239	324	finally
    //   251	261	324	finally
    //   306	311	324	finally
    //   333	337	324	finally
    //   341	345	324	finally
    //   349	353	324	finally
    //   357	376	324	finally
    //   380	382	324	finally
    //   387	397	324	finally
    //   401	411	324	finally
    //   415	423	324	finally
    //   427	431	324	finally
    //   435	440	324	finally
    //   444	446	324	finally
    //   450	458	324	finally
    //   177	188	328	java/util/concurrent/TimeoutException
    //   200	205	328	java/util/concurrent/TimeoutException
    //   221	239	328	java/util/concurrent/TimeoutException
    //   251	261	328	java/util/concurrent/TimeoutException
    //   306	311	328	java/util/concurrent/TimeoutException
    //   177	188	382	java/util/concurrent/ExecutionException
    //   200	205	382	java/util/concurrent/ExecutionException
    //   221	239	382	java/util/concurrent/ExecutionException
    //   251	261	382	java/util/concurrent/ExecutionException
    //   306	311	382	java/util/concurrent/ExecutionException
    //   58	70	503	finally
    //   75	87	503	finally
    //   90	95	503	finally
    //   95	138	509	finally
    //   141	148	509	finally
    //   266	272	509	finally
    //   272	275	509	finally
    //   282	288	509	finally
    //   288	291	509	finally
    //   463	469	509	finally
    //   469	471	509	finally
    //   471	503	509	finally
    //   504	507	509	finally
  }
  
  public static boolean registerGnssStatusCallback(LocationManager paramLocationManager, GnssStatusCompat.Callback paramCallback, Handler paramHandler)
  {
    if (Build.VERSION.SDK_INT >= 30) {
      return registerGnssStatusCallback(paramLocationManager, ExecutorCompat.create(paramHandler), paramCallback);
    }
    return registerGnssStatusCallback(paramLocationManager, new InlineHandlerExecutor(paramHandler), paramCallback);
  }
  
  public static boolean registerGnssStatusCallback(LocationManager paramLocationManager, Executor paramExecutor, GnssStatusCompat.Callback paramCallback)
  {
    if (Build.VERSION.SDK_INT >= 30) {
      return registerGnssStatusCallback(paramLocationManager, null, paramExecutor, paramCallback);
    }
    Looper localLooper2 = Looper.myLooper();
    Looper localLooper1 = localLooper2;
    if (localLooper2 == null) {
      localLooper1 = Looper.getMainLooper();
    }
    return registerGnssStatusCallback(paramLocationManager, new Handler(localLooper1), paramExecutor, paramCallback);
  }
  
  static void registerLocationListenerTransport(LocationManager paramLocationManager, LocationListenerTransport paramLocationListenerTransport)
  {
    paramLocationListenerTransport = (WeakReference)sLocationListeners.put(paramLocationListenerTransport.getKey(), new WeakReference(paramLocationListenerTransport));
    if (paramLocationListenerTransport != null) {
      paramLocationListenerTransport = (LocationListenerTransport)paramLocationListenerTransport.get();
    } else {
      paramLocationListenerTransport = null;
    }
    if (paramLocationListenerTransport != null)
    {
      paramLocationListenerTransport.unregister();
      paramLocationManager.removeUpdates(paramLocationListenerTransport);
    }
  }
  
  public static void removeUpdates(LocationManager paramLocationManager, LocationListenerCompat paramLocationListenerCompat)
  {
    WeakHashMap localWeakHashMap = sLocationListeners;
    Object localObject1 = null;
    try
    {
      Iterator localIterator = localWeakHashMap.values().iterator();
      Object localObject2;
      while (localIterator.hasNext())
      {
        LocationListenerTransport localLocationListenerTransport = (LocationListenerTransport)((WeakReference)localIterator.next()).get();
        if (localLocationListenerTransport != null)
        {
          LocationListenerKey localLocationListenerKey = localLocationListenerTransport.getKey();
          localObject2 = localObject1;
          if (localLocationListenerKey.mListener == paramLocationListenerCompat)
          {
            localObject2 = localObject1;
            if (localObject1 == null)
            {
              localObject2 = new java/util/ArrayList;
              ((ArrayList)localObject2).<init>();
            }
            ((ArrayList)localObject2).add(localLocationListenerKey);
            localLocationListenerTransport.unregister();
            paramLocationManager.removeUpdates(localLocationListenerTransport);
          }
          localObject1 = localObject2;
        }
      }
      if (localObject1 != null)
      {
        localObject1 = ((ArrayList)localObject1).iterator();
        while (((Iterator)localObject1).hasNext())
        {
          localObject2 = (LocationListenerKey)((Iterator)localObject1).next();
          sLocationListeners.remove(localObject2);
        }
      }
      paramLocationManager.removeUpdates(paramLocationListenerCompat);
      return;
    }
    finally {}
  }
  
  public static void requestLocationUpdates(LocationManager paramLocationManager, String paramString, LocationRequestCompat paramLocationRequestCompat, LocationListenerCompat paramLocationListenerCompat, Looper paramLooper)
  {
    if (Build.VERSION.SDK_INT >= 31)
    {
      Api31Impl.requestLocationUpdates(paramLocationManager, paramString, paramLocationRequestCompat.toLocationRequest(), ExecutorCompat.create(new Handler(paramLooper)), paramLocationListenerCompat);
      return;
    }
    if ((Build.VERSION.SDK_INT >= 19) && (Api19Impl.tryRequestLocationUpdates(paramLocationManager, paramString, paramLocationRequestCompat, paramLocationListenerCompat, paramLooper))) {
      return;
    }
    paramLocationManager.requestLocationUpdates(paramString, paramLocationRequestCompat.getIntervalMillis(), paramLocationRequestCompat.getMinUpdateDistanceMeters(), paramLocationListenerCompat, paramLooper);
  }
  
  public static void requestLocationUpdates(LocationManager paramLocationManager, String paramString, LocationRequestCompat paramLocationRequestCompat, Executor arg3, LocationListenerCompat paramLocationListenerCompat)
  {
    if (Build.VERSION.SDK_INT >= 31)
    {
      Api31Impl.requestLocationUpdates(paramLocationManager, paramString, paramLocationRequestCompat.toLocationRequest(), ???, paramLocationListenerCompat);
      return;
    }
    if ((Build.VERSION.SDK_INT >= 30) && (Api30Impl.tryRequestLocationUpdates(paramLocationManager, paramString, paramLocationRequestCompat, ???, paramLocationListenerCompat))) {
      return;
    }
    paramLocationListenerCompat = new LocationListenerTransport(new LocationListenerKey(paramString, paramLocationListenerCompat), ???);
    if ((Build.VERSION.SDK_INT >= 19) && (Api19Impl.tryRequestLocationUpdates(paramLocationManager, paramString, paramLocationRequestCompat, paramLocationListenerCompat))) {
      return;
    }
    synchronized (sLocationListeners)
    {
      paramLocationManager.requestLocationUpdates(paramString, paramLocationRequestCompat.getIntervalMillis(), paramLocationRequestCompat.getMinUpdateDistanceMeters(), paramLocationListenerCompat, Looper.getMainLooper());
      registerLocationListenerTransport(paramLocationManager, paramLocationListenerCompat);
      return;
    }
  }
  
  public static void unregisterGnssStatusCallback(LocationManager paramLocationManager, GnssStatusCompat.Callback paramCallback)
  {
    if (Build.VERSION.SDK_INT >= 24) {
      synchronized (GnssLazyLoader.sGnssStatusListeners)
      {
        paramCallback = GnssLazyLoader.sGnssStatusListeners.remove(paramCallback);
        if (paramCallback != null) {
          Api24Impl.unregisterGnssStatusCallback(paramLocationManager, paramCallback);
        }
      }
    }
    synchronized (GnssLazyLoader.sGnssStatusListeners)
    {
      paramCallback = (GpsStatusTransport)GnssLazyLoader.sGnssStatusListeners.remove(paramCallback);
      if (paramCallback != null)
      {
        paramCallback.unregister();
        paramLocationManager.removeGpsStatusListener(paramCallback);
      }
      return;
    }
  }
  
  static class Api19Impl
  {
    private static Class<?> sLocationRequestClass;
    private static Method sRequestLocationUpdatesLooperMethod;
    
    static boolean tryRequestLocationUpdates(LocationManager paramLocationManager, String paramString, LocationRequestCompat paramLocationRequestCompat, LocationListenerCompat paramLocationListenerCompat, Looper paramLooper)
    {
      if (Build.VERSION.SDK_INT >= 19) {
        try
        {
          if (sLocationRequestClass == null) {
            sLocationRequestClass = Class.forName("android.location.LocationRequest");
          }
          if (sRequestLocationUpdatesLooperMethod == null)
          {
            Method localMethod = LocationManager.class.getDeclaredMethod("requestLocationUpdates", new Class[] { sLocationRequestClass, LocationListener.class, Looper.class });
            sRequestLocationUpdatesLooperMethod = localMethod;
            localMethod.setAccessible(true);
          }
          paramString = paramLocationRequestCompat.toLocationRequest(paramString);
          if (paramString != null)
          {
            sRequestLocationUpdatesLooperMethod.invoke(paramLocationManager, new Object[] { paramString, paramLocationListenerCompat, paramLooper });
            return true;
          }
        }
        catch (UnsupportedOperationException paramLocationManager) {}catch (ClassNotFoundException paramLocationManager) {}catch (IllegalAccessException paramLocationManager) {}catch (InvocationTargetException paramLocationManager) {}catch (NoSuchMethodException paramLocationManager) {}
      }
      return false;
    }
    
    static boolean tryRequestLocationUpdates(LocationManager paramLocationManager, String arg1, LocationRequestCompat paramLocationRequestCompat, LocationManagerCompat.LocationListenerTransport paramLocationListenerTransport)
    {
      if (Build.VERSION.SDK_INT >= 19) {
        try
        {
          if (sLocationRequestClass == null) {
            sLocationRequestClass = Class.forName("android.location.LocationRequest");
          }
          if (sRequestLocationUpdatesLooperMethod == null)
          {
            Method localMethod = LocationManager.class.getDeclaredMethod("requestLocationUpdates", new Class[] { sLocationRequestClass, LocationListener.class, Looper.class });
            sRequestLocationUpdatesLooperMethod = localMethod;
            localMethod.setAccessible(true);
          }
          paramLocationRequestCompat = paramLocationRequestCompat.toLocationRequest(???);
          if (paramLocationRequestCompat != null) {
            synchronized (LocationManagerCompat.sLocationListeners)
            {
              sRequestLocationUpdatesLooperMethod.invoke(paramLocationManager, new Object[] { paramLocationRequestCompat, paramLocationListenerTransport, Looper.getMainLooper() });
              LocationManagerCompat.registerLocationListenerTransport(paramLocationManager, paramLocationListenerTransport);
              return true;
            }
          }
        }
        catch (UnsupportedOperationException paramLocationManager) {}catch (ClassNotFoundException paramLocationManager) {}catch (IllegalAccessException paramLocationManager) {}catch (InvocationTargetException paramLocationManager) {}catch (NoSuchMethodException paramLocationManager) {}
      }
      return false;
    }
  }
  
  static class Api24Impl
  {
    static boolean registerGnssStatusCallback(LocationManager paramLocationManager, Handler paramHandler, Executor paramExecutor, GnssStatusCompat.Callback paramCallback)
    {
      boolean bool;
      if (paramHandler != null) {
        bool = true;
      } else {
        bool = false;
      }
      Preconditions.checkArgument(bool);
      synchronized (LocationManagerCompat.GnssLazyLoader.sGnssStatusListeners)
      {
        LocationManagerCompat.PreRGnssStatusTransport localPreRGnssStatusTransport = (LocationManagerCompat.PreRGnssStatusTransport)LocationManagerCompat.GnssLazyLoader.sGnssStatusListeners.get(paramCallback);
        if (localPreRGnssStatusTransport == null)
        {
          localPreRGnssStatusTransport = new androidx/core/location/LocationManagerCompat$PreRGnssStatusTransport;
          localPreRGnssStatusTransport.<init>(paramCallback);
        }
        else
        {
          localPreRGnssStatusTransport.unregister();
        }
        localPreRGnssStatusTransport.register(paramExecutor);
        if (paramLocationManager.registerGnssStatusCallback(localPreRGnssStatusTransport, paramHandler))
        {
          LocationManagerCompat.GnssLazyLoader.sGnssStatusListeners.put(paramCallback, localPreRGnssStatusTransport);
          return true;
        }
        return false;
      }
    }
    
    static void unregisterGnssStatusCallback(LocationManager paramLocationManager, Object paramObject)
    {
      if ((paramObject instanceof LocationManagerCompat.PreRGnssStatusTransport)) {
        ((LocationManagerCompat.PreRGnssStatusTransport)paramObject).unregister();
      }
      paramLocationManager.unregisterGnssStatusCallback((GnssStatus.Callback)paramObject);
    }
  }
  
  private static class Api28Impl
  {
    static String getGnssHardwareModelName(LocationManager paramLocationManager)
    {
      return paramLocationManager.getGnssHardwareModelName();
    }
    
    static int getGnssYearOfHardware(LocationManager paramLocationManager)
    {
      return paramLocationManager.getGnssYearOfHardware();
    }
    
    static boolean isLocationEnabled(LocationManager paramLocationManager)
    {
      return paramLocationManager.isLocationEnabled();
    }
  }
  
  private static class Api30Impl
  {
    private static Class<?> sLocationRequestClass;
    private static Method sRequestLocationUpdatesExecutorMethod;
    
    static void getCurrentLocation(LocationManager paramLocationManager, String paramString, androidx.core.os.CancellationSignal paramCancellationSignal, Executor paramExecutor, Consumer<Location> paramConsumer)
    {
      if (paramCancellationSignal != null) {
        paramCancellationSignal = (android.os.CancellationSignal)paramCancellationSignal.getCancellationSignalObject();
      } else {
        paramCancellationSignal = null;
      }
      Objects.requireNonNull(paramConsumer);
      paramLocationManager.getCurrentLocation(paramString, paramCancellationSignal, paramExecutor, new LocationManagerCompat.Api30Impl..ExternalSyntheticLambda0(paramConsumer));
    }
    
    public static boolean registerGnssStatusCallback(LocationManager paramLocationManager, Handler paramHandler, Executor paramExecutor, GnssStatusCompat.Callback paramCallback)
    {
      synchronized (LocationManagerCompat.GnssLazyLoader.sGnssStatusListeners)
      {
        LocationManagerCompat.GnssStatusTransport localGnssStatusTransport = (LocationManagerCompat.GnssStatusTransport)LocationManagerCompat.GnssLazyLoader.sGnssStatusListeners.get(paramCallback);
        paramHandler = localGnssStatusTransport;
        if (localGnssStatusTransport == null)
        {
          paramHandler = new androidx/core/location/LocationManagerCompat$GnssStatusTransport;
          paramHandler.<init>(paramCallback);
        }
        if (paramLocationManager.registerGnssStatusCallback(paramExecutor, paramHandler))
        {
          LocationManagerCompat.GnssLazyLoader.sGnssStatusListeners.put(paramCallback, paramHandler);
          return true;
        }
        return false;
      }
    }
    
    public static boolean tryRequestLocationUpdates(LocationManager paramLocationManager, String paramString, LocationRequestCompat paramLocationRequestCompat, Executor paramExecutor, LocationListenerCompat paramLocationListenerCompat)
    {
      if (Build.VERSION.SDK_INT >= 30) {
        try
        {
          if (sLocationRequestClass == null) {
            sLocationRequestClass = Class.forName("android.location.LocationRequest");
          }
          if (sRequestLocationUpdatesExecutorMethod == null)
          {
            Method localMethod = LocationManager.class.getDeclaredMethod("requestLocationUpdates", new Class[] { sLocationRequestClass, Executor.class, LocationListener.class });
            sRequestLocationUpdatesExecutorMethod = localMethod;
            localMethod.setAccessible(true);
          }
          paramString = paramLocationRequestCompat.toLocationRequest(paramString);
          if (paramString != null)
          {
            sRequestLocationUpdatesExecutorMethod.invoke(paramLocationManager, new Object[] { paramString, paramExecutor, paramLocationListenerCompat });
            return true;
          }
        }
        catch (UnsupportedOperationException paramLocationManager) {}catch (ClassNotFoundException paramLocationManager) {}catch (IllegalAccessException paramLocationManager) {}catch (InvocationTargetException paramLocationManager) {}catch (NoSuchMethodException paramLocationManager) {}
      }
      return false;
    }
  }
  
  private static class Api31Impl
  {
    static boolean hasProvider(LocationManager paramLocationManager, String paramString)
    {
      return paramLocationManager.hasProvider(paramString);
    }
    
    static void requestLocationUpdates(LocationManager paramLocationManager, String paramString, LocationRequest paramLocationRequest, Executor paramExecutor, LocationListener paramLocationListener)
    {
      paramLocationManager.requestLocationUpdates(paramString, paramLocationRequest, paramExecutor, paramLocationListener);
    }
  }
  
  private static final class CancellableLocationListener
    implements LocationListener
  {
    private Consumer<Location> mConsumer;
    private final Executor mExecutor;
    private final LocationManager mLocationManager;
    private final Handler mTimeoutHandler;
    Runnable mTimeoutRunnable;
    private boolean mTriggered;
    
    CancellableLocationListener(LocationManager paramLocationManager, Executor paramExecutor, Consumer<Location> paramConsumer)
    {
      this.mLocationManager = paramLocationManager;
      this.mExecutor = paramExecutor;
      this.mTimeoutHandler = new Handler(Looper.getMainLooper());
      this.mConsumer = paramConsumer;
    }
    
    private void cleanup()
    {
      this.mConsumer = null;
      this.mLocationManager.removeUpdates(this);
      Runnable localRunnable = this.mTimeoutRunnable;
      if (localRunnable != null)
      {
        this.mTimeoutHandler.removeCallbacks(localRunnable);
        this.mTimeoutRunnable = null;
      }
    }
    
    public void cancel()
    {
      try
      {
        if (this.mTriggered) {
          return;
        }
        this.mTriggered = true;
        cleanup();
        return;
      }
      finally {}
    }
    
    public void onLocationChanged(Location paramLocation)
    {
      try
      {
        if (this.mTriggered) {
          return;
        }
        this.mTriggered = true;
        Consumer localConsumer = this.mConsumer;
        this.mExecutor.execute(new LocationManagerCompat.CancellableLocationListener..ExternalSyntheticLambda1(localConsumer, paramLocation));
        cleanup();
        return;
      }
      finally {}
    }
    
    public void onProviderDisabled(String paramString)
    {
      paramString = (Location)null;
      onLocationChanged(null);
    }
    
    public void onProviderEnabled(String paramString) {}
    
    public void onStatusChanged(String paramString, int paramInt, Bundle paramBundle) {}
    
    public void startTimeout(long paramLong)
    {
      try
      {
        if (this.mTriggered) {
          return;
        }
        LocationManagerCompat.CancellableLocationListener..ExternalSyntheticLambda0 localExternalSyntheticLambda0 = new androidx/core/location/LocationManagerCompat$CancellableLocationListener$$ExternalSyntheticLambda0;
        localExternalSyntheticLambda0.<init>(this);
        this.mTimeoutRunnable = localExternalSyntheticLambda0;
        this.mTimeoutHandler.postDelayed(localExternalSyntheticLambda0, paramLong);
        return;
      }
      finally {}
    }
  }
  
  private static class GnssLazyLoader
  {
    static final SimpleArrayMap<Object, Object> sGnssStatusListeners = new SimpleArrayMap();
  }
  
  private static class GnssStatusTransport
    extends GnssStatus.Callback
  {
    final GnssStatusCompat.Callback mCallback;
    
    GnssStatusTransport(GnssStatusCompat.Callback paramCallback)
    {
      boolean bool;
      if (paramCallback != null) {
        bool = true;
      } else {
        bool = false;
      }
      Preconditions.checkArgument(bool, "invalid null callback");
      this.mCallback = paramCallback;
    }
    
    public void onFirstFix(int paramInt)
    {
      this.mCallback.onFirstFix(paramInt);
    }
    
    public void onSatelliteStatusChanged(GnssStatus paramGnssStatus)
    {
      this.mCallback.onSatelliteStatusChanged(GnssStatusCompat.wrap(paramGnssStatus));
    }
    
    public void onStarted()
    {
      this.mCallback.onStarted();
    }
    
    public void onStopped()
    {
      this.mCallback.onStopped();
    }
  }
  
  private static class GpsStatusTransport
    implements GpsStatus.Listener
  {
    final GnssStatusCompat.Callback mCallback;
    volatile Executor mExecutor;
    private final LocationManager mLocationManager;
    
    GpsStatusTransport(LocationManager paramLocationManager, GnssStatusCompat.Callback paramCallback)
    {
      boolean bool;
      if (paramCallback != null) {
        bool = true;
      } else {
        bool = false;
      }
      Preconditions.checkArgument(bool, "invalid null callback");
      this.mLocationManager = paramLocationManager;
      this.mCallback = paramCallback;
    }
    
    public void onGpsStatusChanged(int paramInt)
    {
      Executor localExecutor = this.mExecutor;
      if (localExecutor == null) {
        return;
      }
      GpsStatus localGpsStatus;
      switch (paramInt)
      {
      default: 
        break;
      case 4: 
        localGpsStatus = this.mLocationManager.getGpsStatus(null);
        if (localGpsStatus != null) {
          localExecutor.execute(new LocationManagerCompat.GpsStatusTransport..ExternalSyntheticLambda3(this, localExecutor, GnssStatusCompat.wrap(localGpsStatus)));
        }
        break;
      case 3: 
        localGpsStatus = this.mLocationManager.getGpsStatus(null);
        if (localGpsStatus != null) {
          localExecutor.execute(new LocationManagerCompat.GpsStatusTransport..ExternalSyntheticLambda2(this, localExecutor, localGpsStatus.getTimeToFirstFix()));
        }
        break;
      case 2: 
        localExecutor.execute(new LocationManagerCompat.GpsStatusTransport..ExternalSyntheticLambda1(this, localExecutor));
        break;
      case 1: 
        localExecutor.execute(new LocationManagerCompat.GpsStatusTransport..ExternalSyntheticLambda0(this, localExecutor));
      }
    }
    
    public void register(Executor paramExecutor)
    {
      boolean bool;
      if (this.mExecutor == null) {
        bool = true;
      } else {
        bool = false;
      }
      Preconditions.checkState(bool);
      this.mExecutor = paramExecutor;
    }
    
    public void unregister()
    {
      this.mExecutor = null;
    }
  }
  
  private static final class InlineHandlerExecutor
    implements Executor
  {
    private final Handler mHandler;
    
    InlineHandlerExecutor(Handler paramHandler)
    {
      this.mHandler = ((Handler)Preconditions.checkNotNull(paramHandler));
    }
    
    public void execute(Runnable paramRunnable)
    {
      if (Looper.myLooper() == this.mHandler.getLooper()) {
        paramRunnable.run();
      } else {
        if (!this.mHandler.post((Runnable)Preconditions.checkNotNull(paramRunnable))) {
          break label40;
        }
      }
      return;
      label40:
      throw new RejectedExecutionException(this.mHandler + " is shutting down");
    }
  }
  
  private static class LocationListenerKey
  {
    final LocationListenerCompat mListener;
    final String mProvider;
    
    LocationListenerKey(String paramString, LocationListenerCompat paramLocationListenerCompat)
    {
      this.mProvider = ((String)ObjectsCompat.requireNonNull(paramString, "invalid null provider"));
      this.mListener = ((LocationListenerCompat)ObjectsCompat.requireNonNull(paramLocationListenerCompat, "invalid null listener"));
    }
    
    public boolean equals(Object paramObject)
    {
      boolean bool1 = paramObject instanceof LocationListenerKey;
      boolean bool2 = false;
      if (!bool1) {
        return false;
      }
      paramObject = (LocationListenerKey)paramObject;
      bool1 = bool2;
      if (this.mProvider.equals(((LocationListenerKey)paramObject).mProvider))
      {
        bool1 = bool2;
        if (this.mListener.equals(((LocationListenerKey)paramObject).mListener)) {
          bool1 = true;
        }
      }
      return bool1;
    }
    
    public int hashCode()
    {
      return ObjectsCompat.hash(new Object[] { this.mProvider, this.mListener });
    }
  }
  
  private static class LocationListenerTransport
    implements LocationListener
  {
    final Executor mExecutor;
    volatile LocationManagerCompat.LocationListenerKey mKey;
    
    LocationListenerTransport(LocationManagerCompat.LocationListenerKey paramLocationListenerKey, Executor paramExecutor)
    {
      this.mKey = paramLocationListenerKey;
      this.mExecutor = paramExecutor;
    }
    
    public LocationManagerCompat.LocationListenerKey getKey()
    {
      return (LocationManagerCompat.LocationListenerKey)ObjectsCompat.requireNonNull(this.mKey);
    }
    
    public void onFlushComplete(int paramInt)
    {
      if (this.mKey == null) {
        return;
      }
      this.mExecutor.execute(new LocationManagerCompat.LocationListenerTransport..ExternalSyntheticLambda1(this, paramInt));
    }
    
    public void onLocationChanged(Location paramLocation)
    {
      if (this.mKey == null) {
        return;
      }
      this.mExecutor.execute(new LocationManagerCompat.LocationListenerTransport..ExternalSyntheticLambda4(this, paramLocation));
    }
    
    public void onLocationChanged(List<Location> paramList)
    {
      if (this.mKey == null) {
        return;
      }
      this.mExecutor.execute(new LocationManagerCompat.LocationListenerTransport..ExternalSyntheticLambda2(this, paramList));
    }
    
    public void onProviderDisabled(String paramString)
    {
      if (this.mKey == null) {
        return;
      }
      this.mExecutor.execute(new LocationManagerCompat.LocationListenerTransport..ExternalSyntheticLambda3(this, paramString));
    }
    
    public void onProviderEnabled(String paramString)
    {
      if (this.mKey == null) {
        return;
      }
      this.mExecutor.execute(new LocationManagerCompat.LocationListenerTransport..ExternalSyntheticLambda0(this, paramString));
    }
    
    public void onStatusChanged(String paramString, int paramInt, Bundle paramBundle)
    {
      if (this.mKey == null) {
        return;
      }
      this.mExecutor.execute(new LocationManagerCompat.LocationListenerTransport..ExternalSyntheticLambda5(this, paramString, paramInt, paramBundle));
    }
    
    public void unregister()
    {
      this.mKey = null;
    }
  }
  
  private static class PreRGnssStatusTransport
    extends GnssStatus.Callback
  {
    final GnssStatusCompat.Callback mCallback;
    volatile Executor mExecutor;
    
    PreRGnssStatusTransport(GnssStatusCompat.Callback paramCallback)
    {
      boolean bool;
      if (paramCallback != null) {
        bool = true;
      } else {
        bool = false;
      }
      Preconditions.checkArgument(bool, "invalid null callback");
      this.mCallback = paramCallback;
    }
    
    public void onFirstFix(int paramInt)
    {
      Executor localExecutor = this.mExecutor;
      if (localExecutor == null) {
        return;
      }
      localExecutor.execute(new LocationManagerCompat.PreRGnssStatusTransport..ExternalSyntheticLambda2(this, localExecutor, paramInt));
    }
    
    public void onSatelliteStatusChanged(GnssStatus paramGnssStatus)
    {
      Executor localExecutor = this.mExecutor;
      if (localExecutor == null) {
        return;
      }
      localExecutor.execute(new LocationManagerCompat.PreRGnssStatusTransport..ExternalSyntheticLambda1(this, localExecutor, paramGnssStatus));
    }
    
    public void onStarted()
    {
      Executor localExecutor = this.mExecutor;
      if (localExecutor == null) {
        return;
      }
      localExecutor.execute(new LocationManagerCompat.PreRGnssStatusTransport..ExternalSyntheticLambda0(this, localExecutor));
    }
    
    public void onStopped()
    {
      Executor localExecutor = this.mExecutor;
      if (localExecutor == null) {
        return;
      }
      localExecutor.execute(new LocationManagerCompat.PreRGnssStatusTransport..ExternalSyntheticLambda3(this, localExecutor));
    }
    
    public void register(Executor paramExecutor)
    {
      boolean bool2 = true;
      boolean bool1;
      if (paramExecutor != null) {
        bool1 = true;
      } else {
        bool1 = false;
      }
      Preconditions.checkArgument(bool1, "invalid null executor");
      if (this.mExecutor == null) {
        bool1 = bool2;
      } else {
        bool1 = false;
      }
      Preconditions.checkState(bool1);
      this.mExecutor = paramExecutor;
    }
    
    public void unregister()
    {
      this.mExecutor = null;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/location/LocationManagerCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */