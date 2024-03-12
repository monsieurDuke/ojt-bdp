package androidx.core.util;

import android.os.Build.VERSION;
import java.util.Arrays;
import java.util.Objects;

public class ObjectsCompat
{
  public static boolean equals(Object paramObject1, Object paramObject2)
  {
    if (Build.VERSION.SDK_INT >= 19) {
      return Api19Impl.equals(paramObject1, paramObject2);
    }
    boolean bool;
    if ((paramObject1 != paramObject2) && ((paramObject1 == null) || (!paramObject1.equals(paramObject2)))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public static int hash(Object... paramVarArgs)
  {
    if (Build.VERSION.SDK_INT >= 19) {
      return Api19Impl.hash(paramVarArgs);
    }
    return Arrays.hashCode(paramVarArgs);
  }
  
  public static int hashCode(Object paramObject)
  {
    int i;
    if (paramObject != null) {
      i = paramObject.hashCode();
    } else {
      i = 0;
    }
    return i;
  }
  
  public static <T> T requireNonNull(T paramT)
  {
    if (paramT != null) {
      return paramT;
    }
    throw new NullPointerException();
  }
  
  public static <T> T requireNonNull(T paramT, String paramString)
  {
    if (paramT != null) {
      return paramT;
    }
    throw new NullPointerException(paramString);
  }
  
  public static String toString(Object paramObject, String paramString)
  {
    if (paramObject != null) {
      paramObject = paramObject.toString();
    } else {
      paramObject = paramString;
    }
    return (String)paramObject;
  }
  
  static class Api19Impl
  {
    static boolean equals(Object paramObject1, Object paramObject2)
    {
      return Objects.equals(paramObject1, paramObject2);
    }
    
    static int hash(Object... paramVarArgs)
    {
      return Objects.hash(paramVarArgs);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/util/ObjectsCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */