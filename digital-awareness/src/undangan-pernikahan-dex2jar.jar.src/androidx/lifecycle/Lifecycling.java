package androidx.lifecycle;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class Lifecycling
{
  private static final int GENERATED_CALLBACK = 2;
  private static final int REFLECTIVE_CALLBACK = 1;
  private static Map<Class<?>, Integer> sCallbackCache = new HashMap();
  private static Map<Class<?>, List<Constructor<? extends GeneratedAdapter>>> sClassToAdapters = new HashMap();
  
  private static GeneratedAdapter createGeneratedAdapter(Constructor<? extends GeneratedAdapter> paramConstructor, Object paramObject)
  {
    try
    {
      paramConstructor = (GeneratedAdapter)paramConstructor.newInstance(new Object[] { paramObject });
      return paramConstructor;
    }
    catch (InvocationTargetException paramConstructor)
    {
      throw new RuntimeException(paramConstructor);
    }
    catch (InstantiationException paramConstructor)
    {
      throw new RuntimeException(paramConstructor);
    }
    catch (IllegalAccessException paramConstructor)
    {
      throw new RuntimeException(paramConstructor);
    }
  }
  
  private static Constructor<? extends GeneratedAdapter> generatedConstructor(Class<?> paramClass)
  {
    try
    {
      Object localObject = paramClass.getPackage();
      String str = paramClass.getCanonicalName();
      if (localObject != null) {
        localObject = ((Package)localObject).getName();
      } else {
        localObject = "";
      }
      if (!((String)localObject).isEmpty()) {
        str = str.substring(((String)localObject).length() + 1);
      }
      str = getAdapterName(str);
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      if (((String)localObject).isEmpty())
      {
        localObject = str;
      }
      else
      {
        StringBuilder localStringBuilder = new java/lang/StringBuilder;
        localStringBuilder.<init>();
        localObject = (String)localObject + "." + str;
      }
      paramClass = Class.forName((String)localObject).getDeclaredConstructor(new Class[] { paramClass });
      if (!paramClass.isAccessible()) {
        paramClass.setAccessible(true);
      }
      return paramClass;
    }
    catch (NoSuchMethodException paramClass)
    {
      throw new RuntimeException(paramClass);
    }
    catch (ClassNotFoundException paramClass) {}
    return null;
  }
  
  public static String getAdapterName(String paramString)
  {
    return paramString.replace(".", "_") + "_LifecycleAdapter";
  }
  
  @Deprecated
  static GenericLifecycleObserver getCallback(Object paramObject)
  {
    new GenericLifecycleObserver()
    {
      public void onStateChanged(LifecycleOwner paramAnonymousLifecycleOwner, Lifecycle.Event paramAnonymousEvent)
      {
        Lifecycling.this.onStateChanged(paramAnonymousLifecycleOwner, paramAnonymousEvent);
      }
    };
  }
  
  private static int getObserverConstructorType(Class<?> paramClass)
  {
    Integer localInteger = (Integer)sCallbackCache.get(paramClass);
    if (localInteger != null) {
      return localInteger.intValue();
    }
    int i = resolveObserverCallbackType(paramClass);
    sCallbackCache.put(paramClass, Integer.valueOf(i));
    return i;
  }
  
  private static boolean isLifecycleParent(Class<?> paramClass)
  {
    boolean bool;
    if ((paramClass != null) && (LifecycleObserver.class.isAssignableFrom(paramClass))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  static LifecycleEventObserver lifecycleEventObserver(Object paramObject)
  {
    boolean bool1 = paramObject instanceof LifecycleEventObserver;
    boolean bool2 = paramObject instanceof FullLifecycleObserver;
    if ((bool1) && (bool2)) {
      return new FullLifecycleObserverAdapter((FullLifecycleObserver)paramObject, (LifecycleEventObserver)paramObject);
    }
    if (bool2) {
      return new FullLifecycleObserverAdapter((FullLifecycleObserver)paramObject, null);
    }
    if (bool1) {
      return (LifecycleEventObserver)paramObject;
    }
    Object localObject = paramObject.getClass();
    if (getObserverConstructorType((Class)localObject) == 2)
    {
      localObject = (List)sClassToAdapters.get(localObject);
      if (((List)localObject).size() == 1) {
        return new SingleGeneratedAdapterObserver(createGeneratedAdapter((Constructor)((List)localObject).get(0), paramObject));
      }
      GeneratedAdapter[] arrayOfGeneratedAdapter = new GeneratedAdapter[((List)localObject).size()];
      for (int i = 0; i < ((List)localObject).size(); i++) {
        arrayOfGeneratedAdapter[i] = createGeneratedAdapter((Constructor)((List)localObject).get(i), paramObject);
      }
      return new CompositeGeneratedAdaptersObserver(arrayOfGeneratedAdapter);
    }
    return new ReflectiveGenericLifecycleObserver(paramObject);
  }
  
  private static int resolveObserverCallbackType(Class<?> paramClass)
  {
    if (paramClass.getCanonicalName() == null) {
      return 1;
    }
    Object localObject1 = generatedConstructor(paramClass);
    if (localObject1 != null)
    {
      sClassToAdapters.put(paramClass, Collections.singletonList(localObject1));
      return 2;
    }
    if (ClassesInfoCache.sInstance.hasLifecycleMethods(paramClass)) {
      return 1;
    }
    localObject1 = paramClass.getSuperclass();
    Object localObject2 = null;
    if (isLifecycleParent((Class)localObject1))
    {
      if (getObserverConstructorType((Class)localObject1) == 1) {
        return 1;
      }
      localObject2 = new ArrayList((Collection)sClassToAdapters.get(localObject1));
    }
    Class[] arrayOfClass = paramClass.getInterfaces();
    int j = arrayOfClass.length;
    int i = 0;
    while (i < j)
    {
      Class localClass = arrayOfClass[i];
      if (!isLifecycleParent(localClass))
      {
        localObject1 = localObject2;
      }
      else
      {
        if (getObserverConstructorType(localClass) == 1) {
          return 1;
        }
        localObject1 = localObject2;
        if (localObject2 == null) {
          localObject1 = new ArrayList();
        }
        ((List)localObject1).addAll((Collection)sClassToAdapters.get(localClass));
      }
      i++;
      localObject2 = localObject1;
    }
    if (localObject2 != null)
    {
      sClassToAdapters.put(paramClass, localObject2);
      return 2;
    }
    return 1;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/lifecycle/Lifecycling.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */