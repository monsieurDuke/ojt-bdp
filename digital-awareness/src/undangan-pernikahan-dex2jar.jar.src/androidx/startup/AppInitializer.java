package androidx.startup;

import android.content.Context;
import android.os.Bundle;
import androidx.tracing.Trace;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public final class AppInitializer
{
  private static final String SECTION_NAME = "Startup";
  private static volatile AppInitializer sInstance;
  private static final Object sLock = new Object();
  final Context mContext;
  final Set<Class<? extends Initializer<?>>> mDiscovered;
  final Map<Class<?>, Object> mInitialized;
  
  AppInitializer(Context paramContext)
  {
    this.mContext = paramContext.getApplicationContext();
    this.mDiscovered = new HashSet();
    this.mInitialized = new HashMap();
  }
  
  private <T> T doInitialize(Class<? extends Initializer<?>> paramClass, Set<Class<?>> paramSet)
  {
    if (Trace.isEnabled()) {}
    try
    {
      Trace.beginSection(paramClass.getSimpleName());
      if (!paramSet.contains(paramClass))
      {
        if (!this.mInitialized.containsKey(paramClass))
        {
          paramSet.add(paramClass);
          try
          {
            Object localObject1 = (Initializer)paramClass.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            Object localObject2 = ((Initializer)localObject1).dependencies();
            if (!((List)localObject2).isEmpty())
            {
              Iterator localIterator = ((List)localObject2).iterator();
              while (localIterator.hasNext())
              {
                localObject2 = (Class)localIterator.next();
                if (!this.mInitialized.containsKey(localObject2)) {
                  doInitialize((Class)localObject2, paramSet);
                }
              }
            }
            localObject1 = ((Initializer)localObject1).create(this.mContext);
            paramSet.remove(paramClass);
            this.mInitialized.put(paramClass, localObject1);
            paramClass = (Class<? extends Initializer<?>>)localObject1;
          }
          finally
          {
            paramClass = new androidx/startup/StartupException;
            paramClass.<init>(paramSet);
          }
        }
        paramClass = this.mInitialized.get(paramClass);
        return paramClass;
      }
      paramClass = String.format("Cannot initialize %s. Cycle detected.", new Object[] { paramClass.getName() });
      Log5ECF72.a(paramClass);
      LogE84000.a(paramClass);
      Log229316.a(paramClass);
      paramSet = new java/lang/IllegalStateException;
      paramSet.<init>(paramClass);
      throw paramSet;
    }
    finally
    {
      Trace.endSection();
    }
  }
  
  public static AppInitializer getInstance(Context paramContext)
  {
    if (sInstance == null) {
      synchronized (sLock)
      {
        if (sInstance == null)
        {
          AppInitializer localAppInitializer = new androidx/startup/AppInitializer;
          localAppInitializer.<init>(paramContext);
          sInstance = localAppInitializer;
        }
      }
    }
    return sInstance;
  }
  
  static void setDelegate(AppInitializer paramAppInitializer)
  {
    synchronized (sLock)
    {
      sInstance = paramAppInitializer;
      return;
    }
  }
  
  /* Error */
  void discoverAndInitialize()
  {
    // Byte code:
    //   0: ldc 8
    //   2: invokestatic 66	androidx/tracing/Trace:beginSection	(Ljava/lang/String;)V
    //   5: new 180	android/content/ComponentName
    //   8: astore_1
    //   9: aload_1
    //   10: aload_0
    //   11: getfield 37	androidx/startup/AppInitializer:mContext	Landroid/content/Context;
    //   14: invokevirtual 183	android/content/Context:getPackageName	()Ljava/lang/String;
    //   17: ldc -71
    //   19: invokevirtual 144	java/lang/Class:getName	()Ljava/lang/String;
    //   22: invokespecial 188	android/content/ComponentName:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   25: aload_0
    //   26: aload_0
    //   27: getfield 37	androidx/startup/AppInitializer:mContext	Landroid/content/Context;
    //   30: invokevirtual 192	android/content/Context:getPackageManager	()Landroid/content/pm/PackageManager;
    //   33: aload_1
    //   34: sipush 128
    //   37: invokevirtual 198	android/content/pm/PackageManager:getProviderInfo	(Landroid/content/ComponentName;I)Landroid/content/pm/ProviderInfo;
    //   40: getfield 204	android/content/pm/ProviderInfo:metaData	Landroid/os/Bundle;
    //   43: invokevirtual 207	androidx/startup/AppInitializer:discoverAndInitialize	(Landroid/os/Bundle;)V
    //   46: invokestatic 139	androidx/tracing/Trace:endSection	()V
    //   49: return
    //   50: astore_1
    //   51: goto +15 -> 66
    //   54: astore_1
    //   55: new 129	androidx/startup/StartupException
    //   58: astore_2
    //   59: aload_2
    //   60: aload_1
    //   61: invokespecial 132	androidx/startup/StartupException:<init>	(Ljava/lang/Throwable;)V
    //   64: aload_2
    //   65: athrow
    //   66: invokestatic 139	androidx/tracing/Trace:endSection	()V
    //   69: aload_1
    //   70: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	71	0	this	AppInitializer
    //   8	26	1	localComponentName	android.content.ComponentName
    //   50	1	1	localObject	Object
    //   54	16	1	localNameNotFoundException	android.content.pm.PackageManager.NameNotFoundException
    //   58	7	2	localStartupException	StartupException
    // Exception table:
    //   from	to	target	type
    //   0	46	50	finally
    //   55	66	50	finally
    //   0	46	54	android/content/pm/PackageManager$NameNotFoundException
  }
  
  void discoverAndInitialize(Bundle paramBundle)
  {
    String str = this.mContext.getString(R.string.androidx_startup);
    if (paramBundle != null) {
      try
      {
        HashSet localHashSet = new java/util/HashSet;
        localHashSet.<init>();
        Iterator localIterator = paramBundle.keySet().iterator();
        while (localIterator.hasNext())
        {
          Object localObject = (String)localIterator.next();
          if (str.equals(paramBundle.getString((String)localObject, null)))
          {
            localObject = Class.forName((String)localObject);
            if (Initializer.class.isAssignableFrom((Class)localObject)) {
              this.mDiscovered.add(localObject);
            }
          }
        }
        paramBundle = this.mDiscovered.iterator();
        while (paramBundle.hasNext()) {
          doInitialize((Class)paramBundle.next(), localHashSet);
        }
        return;
      }
      catch (ClassNotFoundException paramBundle)
      {
        throw new StartupException(paramBundle);
      }
    }
  }
  
  <T> T doInitialize(Class<? extends Initializer<?>> paramClass)
  {
    synchronized (sLock)
    {
      Object localObject2 = this.mInitialized.get(paramClass);
      Object localObject1 = localObject2;
      if (localObject2 == null)
      {
        localObject1 = new java/util/HashSet;
        ((HashSet)localObject1).<init>();
        localObject1 = doInitialize(paramClass, (Set)localObject1);
      }
      return (T)localObject1;
    }
  }
  
  public <T> T initializeComponent(Class<? extends Initializer<T>> paramClass)
  {
    return (T)doInitialize(paramClass);
  }
  
  public boolean isEagerlyInitialized(Class<? extends Initializer<?>> paramClass)
  {
    return this.mDiscovered.contains(paramClass);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/startup/AppInitializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */