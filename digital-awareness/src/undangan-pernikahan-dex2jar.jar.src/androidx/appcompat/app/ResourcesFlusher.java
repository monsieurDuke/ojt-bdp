package androidx.appcompat.app;

import android.content.res.Resources;
import android.os.Build.VERSION;
import android.util.Log;
import android.util.LongSparseArray;
import java.lang.reflect.Field;
import java.util.Map;

class ResourcesFlusher
{
  private static final String TAG = "ResourcesFlusher";
  private static Field sDrawableCacheField;
  private static boolean sDrawableCacheFieldFetched;
  private static Field sResourcesImplField;
  private static boolean sResourcesImplFieldFetched;
  private static Class<?> sThemedResourceCacheClazz;
  private static boolean sThemedResourceCacheClazzFetched;
  private static Field sThemedResourceCache_mUnthemedEntriesField;
  private static boolean sThemedResourceCache_mUnthemedEntriesFieldFetched;
  
  static void flush(Resources paramResources)
  {
    if (Build.VERSION.SDK_INT >= 28) {
      return;
    }
    if (Build.VERSION.SDK_INT >= 24) {
      flushNougats(paramResources);
    } else if (Build.VERSION.SDK_INT >= 23) {
      flushMarshmallows(paramResources);
    } else if (Build.VERSION.SDK_INT >= 21) {
      flushLollipops(paramResources);
    }
  }
  
  private static void flushLollipops(Resources paramResources)
  {
    if (!sDrawableCacheFieldFetched)
    {
      try
      {
        Field localField1 = Resources.class.getDeclaredField("mDrawableCache");
        sDrawableCacheField = localField1;
        localField1.setAccessible(true);
      }
      catch (NoSuchFieldException localNoSuchFieldException)
      {
        Log.e("ResourcesFlusher", "Could not retrieve Resources#mDrawableCache field", localNoSuchFieldException);
      }
      sDrawableCacheFieldFetched = true;
    }
    Field localField2 = sDrawableCacheField;
    if (localField2 != null)
    {
      Object localObject = null;
      try
      {
        paramResources = (Map)localField2.get(paramResources);
      }
      catch (IllegalAccessException paramResources)
      {
        Log.e("ResourcesFlusher", "Could not retrieve value from Resources#mDrawableCache", paramResources);
        paramResources = (Resources)localObject;
      }
      if (paramResources != null) {
        paramResources.clear();
      }
    }
  }
  
  private static void flushMarshmallows(Resources paramResources)
  {
    if (!sDrawableCacheFieldFetched)
    {
      try
      {
        Field localField1 = Resources.class.getDeclaredField("mDrawableCache");
        sDrawableCacheField = localField1;
        localField1.setAccessible(true);
      }
      catch (NoSuchFieldException localNoSuchFieldException)
      {
        Log.e("ResourcesFlusher", "Could not retrieve Resources#mDrawableCache field", localNoSuchFieldException);
      }
      sDrawableCacheFieldFetched = true;
    }
    Object localObject2 = null;
    Field localField2 = sDrawableCacheField;
    Object localObject1 = localObject2;
    if (localField2 != null) {
      try
      {
        localObject1 = localField2.get(paramResources);
      }
      catch (IllegalAccessException paramResources)
      {
        Log.e("ResourcesFlusher", "Could not retrieve value from Resources#mDrawableCache", paramResources);
        localObject1 = localObject2;
      }
    }
    if (localObject1 == null) {
      return;
    }
    flushThemedResourcesCache(localObject1);
  }
  
  private static void flushNougats(Resources paramResources)
  {
    if (!sResourcesImplFieldFetched)
    {
      try
      {
        Field localField1 = Resources.class.getDeclaredField("mResourcesImpl");
        sResourcesImplField = localField1;
        localField1.setAccessible(true);
      }
      catch (NoSuchFieldException localNoSuchFieldException1)
      {
        Log.e("ResourcesFlusher", "Could not retrieve Resources#mResourcesImpl field", localNoSuchFieldException1);
      }
      sResourcesImplFieldFetched = true;
    }
    Field localField3 = sResourcesImplField;
    if (localField3 == null) {
      return;
    }
    Field localField2 = null;
    try
    {
      paramResources = localField3.get(paramResources);
    }
    catch (IllegalAccessException paramResources)
    {
      Log.e("ResourcesFlusher", "Could not retrieve value from Resources#mResourcesImpl", paramResources);
      paramResources = localField2;
    }
    if (paramResources == null) {
      return;
    }
    if (!sDrawableCacheFieldFetched)
    {
      try
      {
        localField2 = paramResources.getClass().getDeclaredField("mDrawableCache");
        sDrawableCacheField = localField2;
        localField2.setAccessible(true);
      }
      catch (NoSuchFieldException localNoSuchFieldException2)
      {
        Log.e("ResourcesFlusher", "Could not retrieve ResourcesImpl#mDrawableCache field", localNoSuchFieldException2);
      }
      sDrawableCacheFieldFetched = true;
    }
    localField3 = null;
    Field localField4 = sDrawableCacheField;
    Object localObject = localField3;
    if (localField4 != null) {
      try
      {
        localObject = localField4.get(paramResources);
      }
      catch (IllegalAccessException paramResources)
      {
        Log.e("ResourcesFlusher", "Could not retrieve value from ResourcesImpl#mDrawableCache", paramResources);
        localObject = localField3;
      }
    }
    if (localObject != null) {
      flushThemedResourcesCache(localObject);
    }
  }
  
  private static void flushThemedResourcesCache(Object paramObject)
  {
    if (!sThemedResourceCacheClazzFetched)
    {
      try
      {
        sThemedResourceCacheClazz = Class.forName("android.content.res.ThemedResourceCache");
      }
      catch (ClassNotFoundException localClassNotFoundException)
      {
        Log.e("ResourcesFlusher", "Could not find ThemedResourceCache class", localClassNotFoundException);
      }
      sThemedResourceCacheClazzFetched = true;
    }
    Object localObject1 = sThemedResourceCacheClazz;
    if (localObject1 == null) {
      return;
    }
    if (!sThemedResourceCache_mUnthemedEntriesFieldFetched)
    {
      try
      {
        localObject1 = ((Class)localObject1).getDeclaredField("mUnthemedEntries");
        sThemedResourceCache_mUnthemedEntriesField = (Field)localObject1;
        ((Field)localObject1).setAccessible(true);
      }
      catch (NoSuchFieldException localNoSuchFieldException)
      {
        Log.e("ResourcesFlusher", "Could not retrieve ThemedResourceCache#mUnthemedEntries field", localNoSuchFieldException);
      }
      sThemedResourceCache_mUnthemedEntriesFieldFetched = true;
    }
    Field localField = sThemedResourceCache_mUnthemedEntriesField;
    if (localField == null) {
      return;
    }
    Object localObject2 = null;
    try
    {
      paramObject = (LongSparseArray)localField.get(paramObject);
    }
    catch (IllegalAccessException paramObject)
    {
      Log.e("ResourcesFlusher", "Could not retrieve value from ThemedResourceCache#mUnthemedEntries", (Throwable)paramObject);
      paramObject = localObject2;
    }
    if (paramObject != null) {
      Api16Impl.clear((LongSparseArray)paramObject);
    }
  }
  
  static class Api16Impl
  {
    static void clear(LongSparseArray paramLongSparseArray)
    {
      paramLongSparseArray.clear();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/appcompat/app/ResourcesFlusher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */