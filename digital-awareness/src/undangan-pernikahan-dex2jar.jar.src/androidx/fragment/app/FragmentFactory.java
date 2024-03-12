package androidx.fragment.app;

import androidx.collection.SimpleArrayMap;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class FragmentFactory
{
  private static final SimpleArrayMap<ClassLoader, SimpleArrayMap<String, Class<?>>> sClassCacheMap = new SimpleArrayMap();
  
  static boolean isFragmentClass(ClassLoader paramClassLoader, String paramString)
  {
    try
    {
      boolean bool = Fragment.class.isAssignableFrom(loadClass(paramClassLoader, paramString));
      return bool;
    }
    catch (ClassNotFoundException paramClassLoader) {}
    return false;
  }
  
  private static Class<?> loadClass(ClassLoader paramClassLoader, String paramString)
    throws ClassNotFoundException
  {
    Object localObject3 = sClassCacheMap;
    Object localObject2 = (SimpleArrayMap)((SimpleArrayMap)localObject3).get(paramClassLoader);
    Object localObject1 = localObject2;
    if (localObject2 == null)
    {
      localObject1 = new SimpleArrayMap();
      ((SimpleArrayMap)localObject3).put(paramClassLoader, localObject1);
    }
    localObject3 = (Class)((SimpleArrayMap)localObject1).get(paramString);
    localObject2 = localObject3;
    if (localObject3 == null)
    {
      localObject2 = Class.forName(paramString, false, paramClassLoader);
      ((SimpleArrayMap)localObject1).put(paramString, localObject2);
    }
    return (Class<?>)localObject2;
  }
  
  public static Class<? extends Fragment> loadFragmentClass(ClassLoader paramClassLoader, String paramString)
  {
    try
    {
      paramClassLoader = loadClass(paramClassLoader, paramString);
      return paramClassLoader;
    }
    catch (ClassCastException paramClassLoader)
    {
      throw new Fragment.InstantiationException("Unable to instantiate fragment " + paramString + ": make sure class is a valid subclass of Fragment", paramClassLoader);
    }
    catch (ClassNotFoundException paramClassLoader)
    {
      throw new Fragment.InstantiationException("Unable to instantiate fragment " + paramString + ": make sure class name exists", paramClassLoader);
    }
  }
  
  public Fragment instantiate(ClassLoader paramClassLoader, String paramString)
  {
    try
    {
      paramClassLoader = (Fragment)loadFragmentClass(paramClassLoader, paramString).getConstructor(new Class[0]).newInstance(new Object[0]);
      return paramClassLoader;
    }
    catch (InvocationTargetException paramClassLoader)
    {
      throw new Fragment.InstantiationException("Unable to instantiate fragment " + paramString + ": calling Fragment constructor caused an exception", paramClassLoader);
    }
    catch (NoSuchMethodException paramClassLoader)
    {
      throw new Fragment.InstantiationException("Unable to instantiate fragment " + paramString + ": could not find Fragment constructor", paramClassLoader);
    }
    catch (IllegalAccessException paramClassLoader)
    {
      throw new Fragment.InstantiationException("Unable to instantiate fragment " + paramString + ": make sure class name exists, is public, and has an empty constructor that is public", paramClassLoader);
    }
    catch (InstantiationException paramClassLoader)
    {
      throw new Fragment.InstantiationException("Unable to instantiate fragment " + paramString + ": make sure class name exists, is public, and has an empty constructor that is public", paramClassLoader);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/fragment/app/FragmentFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */