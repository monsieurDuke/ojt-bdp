package androidx.core.util;

import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class DebugUtils
{
  public static void buildShortClassTag(Object paramObject, StringBuilder paramStringBuilder)
  {
    if (paramObject == null)
    {
      paramStringBuilder.append("null");
    }
    else
    {
      String str2 = paramObject.getClass().getSimpleName();
      String str1;
      if (str2 != null)
      {
        str1 = str2;
        if (str2.length() > 0) {}
      }
      else
      {
        str2 = paramObject.getClass().getName();
        int i = str2.lastIndexOf('.');
        str1 = str2;
        if (i > 0) {
          str1 = str2.substring(i + 1);
        }
      }
      paramStringBuilder.append(str1);
      paramStringBuilder.append('{');
      paramObject = Integer.toHexString(System.identityHashCode(paramObject));
      Log5ECF72.a(paramObject);
      LogE84000.a(paramObject);
      Log229316.a(paramObject);
      paramStringBuilder.append((String)paramObject);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/util/DebugUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */