package androidx.room.util;

import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class StringUtil
{
  public static final String[] EMPTY_STRING_ARRAY = new String[0];
  
  public static void appendPlaceholders(StringBuilder paramStringBuilder, int paramInt)
  {
    for (int i = 0; i < paramInt; i++)
    {
      paramStringBuilder.append("?");
      if (i < paramInt - 1) {
        paramStringBuilder.append(",");
      }
    }
  }
  
  public static String joinIntoString(List<Integer> paramList)
  {
    if (paramList == null) {
      return null;
    }
    int j = paramList.size();
    if (j == 0) {
      return "";
    }
    StringBuilder localStringBuilder = new StringBuilder();
    for (int i = 0; i < j; i++)
    {
      String str = Integer.toString(((Integer)paramList.get(i)).intValue());
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      localStringBuilder.append(str);
      if (i < j - 1) {
        localStringBuilder.append(",");
      }
    }
    return localStringBuilder.toString();
  }
  
  public static StringBuilder newStringBuilder()
  {
    return new StringBuilder();
  }
  
  public static List<Integer> splitToIntList(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    ArrayList localArrayList = new ArrayList();
    paramString = new StringTokenizer(paramString, ",");
    while (paramString.hasMoreElements())
    {
      String str = paramString.nextToken();
      try
      {
        localArrayList.add(Integer.valueOf(Integer.parseInt(str)));
      }
      catch (NumberFormatException localNumberFormatException)
      {
        Log.e("ROOM", "Malformed integer list", localNumberFormatException);
      }
    }
    return localArrayList;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/room/util/StringUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */