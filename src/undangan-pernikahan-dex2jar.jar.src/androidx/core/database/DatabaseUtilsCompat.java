package androidx.core.database;

import android.text.TextUtils;

@Deprecated
public final class DatabaseUtilsCompat
{
  @Deprecated
  public static String[] appendSelectionArgs(String[] paramArrayOfString1, String[] paramArrayOfString2)
  {
    if ((paramArrayOfString1 != null) && (paramArrayOfString1.length != 0))
    {
      String[] arrayOfString = new String[paramArrayOfString1.length + paramArrayOfString2.length];
      System.arraycopy(paramArrayOfString1, 0, arrayOfString, 0, paramArrayOfString1.length);
      System.arraycopy(paramArrayOfString2, 0, arrayOfString, paramArrayOfString1.length, paramArrayOfString2.length);
      return arrayOfString;
    }
    return paramArrayOfString2;
  }
  
  @Deprecated
  public static String concatenateWhere(String paramString1, String paramString2)
  {
    if (TextUtils.isEmpty(paramString1)) {
      return paramString2;
    }
    if (TextUtils.isEmpty(paramString2)) {
      return paramString1;
    }
    return "(" + paramString1 + ") AND (" + paramString2 + ")";
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/database/DatabaseUtilsCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */