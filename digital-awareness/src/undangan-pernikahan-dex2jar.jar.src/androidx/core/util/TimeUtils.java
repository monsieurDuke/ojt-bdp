package androidx.core.util;

import java.io.PrintWriter;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public final class TimeUtils
{
  public static final int HUNDRED_DAY_FIELD_LEN = 19;
  private static final int SECONDS_PER_DAY = 86400;
  private static final int SECONDS_PER_HOUR = 3600;
  private static final int SECONDS_PER_MINUTE = 60;
  private static char[] sFormatStr = new char[24];
  private static final Object sFormatSync = new Object();
  
  private static int accumField(int paramInt1, int paramInt2, boolean paramBoolean, int paramInt3)
  {
    if ((paramInt1 <= 99) && ((!paramBoolean) || (paramInt3 < 3)))
    {
      if ((paramInt1 <= 9) && ((!paramBoolean) || (paramInt3 < 2)))
      {
        if ((!paramBoolean) && (paramInt1 <= 0)) {
          return 0;
        }
        return paramInt2 + 1;
      }
      return paramInt2 + 2;
    }
    return paramInt2 + 3;
  }
  
  public static void formatDuration(long paramLong1, long paramLong2, PrintWriter paramPrintWriter)
  {
    if (paramLong1 == 0L)
    {
      paramPrintWriter.print("--");
      return;
    }
    formatDuration(paramLong1 - paramLong2, paramPrintWriter, 0);
  }
  
  public static void formatDuration(long paramLong, PrintWriter paramPrintWriter)
  {
    formatDuration(paramLong, paramPrintWriter, 0);
  }
  
  public static void formatDuration(long paramLong, PrintWriter paramPrintWriter, int paramInt)
  {
    synchronized (sFormatSync)
    {
      paramInt = formatDurationLocked(paramLong, paramInt);
      String str = new java/lang/String;
      str.<init>(sFormatStr, 0, paramInt);
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      paramPrintWriter.print(str);
      return;
    }
  }
  
  public static void formatDuration(long paramLong, StringBuilder paramStringBuilder)
  {
    synchronized (sFormatSync)
    {
      int i = formatDurationLocked(paramLong, 0);
      paramStringBuilder.append(sFormatStr, 0, i);
      return;
    }
  }
  
  private static int formatDurationLocked(long paramLong, int paramInt)
  {
    if (sFormatStr.length < paramInt) {
      sFormatStr = new char[paramInt];
    }
    char[] arrayOfChar = sFormatStr;
    if (paramLong == 0L)
    {
      while (paramInt - 1 < 0) {
        arrayOfChar[0] = ' ';
      }
      arrayOfChar[0] = '0';
      return 0 + 1;
    }
    int i;
    if (paramLong > 0L)
    {
      i = 43;
    }
    else
    {
      paramLong = -paramLong;
      i = 45;
    }
    int i6 = (int)(paramLong % 1000L);
    int j = (int)Math.floor(paramLong / 1000L);
    if (j > 86400)
    {
      m = j / 86400;
      j -= 86400 * m;
    }
    else
    {
      m = 0;
    }
    int n;
    if (j > 3600)
    {
      n = j / 3600;
      j -= n * 3600;
    }
    else
    {
      n = 0;
    }
    int i1;
    int k;
    if (j > 60)
    {
      i1 = j / 60;
      k = j - i1 * 60;
    }
    else
    {
      i1 = 0;
      k = j;
    }
    int i3 = 0;
    int i5 = 0;
    int i4 = 3;
    boolean bool1 = false;
    if (paramInt != 0)
    {
      j = accumField(m, 1, false, 0);
      if (j > 0) {
        bool1 = true;
      }
      j += accumField(n, 1, bool1, 2);
      if (j > 0) {
        bool1 = true;
      } else {
        bool1 = false;
      }
      j += accumField(i1, 1, bool1, 2);
      if (j > 0) {
        bool1 = true;
      } else {
        bool1 = false;
      }
      i2 = j + accumField(k, 1, bool1, 2);
      if (i2 > 0) {
        j = 3;
      } else {
        j = 0;
      }
      i2 += accumField(i6, 2, true, j) + 1;
      j = i5;
      for (;;)
      {
        i3 = j;
        if (i2 >= paramInt) {
          break;
        }
        arrayOfChar[j] = ' ';
        j++;
        i2++;
      }
    }
    arrayOfChar[i3] = i;
    i3++;
    if (paramInt != 0) {
      paramInt = 1;
    } else {
      paramInt = 0;
    }
    boolean bool2 = true;
    int i2 = 2;
    int m = printField(arrayOfChar, m, 'd', i3, false, 0);
    if (m != i3) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    if (paramInt != 0) {
      j = 2;
    } else {
      j = 0;
    }
    m = printField(arrayOfChar, n, 'h', m, bool1, j);
    if (m != i3) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    if (paramInt != 0) {
      j = 2;
    } else {
      j = 0;
    }
    m = printField(arrayOfChar, i1, 'm', m, bool1, j);
    if (m != i3) {
      bool1 = bool2;
    } else {
      bool1 = false;
    }
    if (paramInt != 0) {
      j = i2;
    } else {
      j = 0;
    }
    j = printField(arrayOfChar, k, 's', m, bool1, j);
    if ((paramInt != 0) && (j != i3)) {
      paramInt = i4;
    } else {
      paramInt = 0;
    }
    paramInt = printField(arrayOfChar, i6, 'm', j, true, paramInt);
    arrayOfChar[paramInt] = 's';
    return paramInt + 1;
  }
  
  private static int printField(char[] paramArrayOfChar, int paramInt1, char paramChar, int paramInt2, boolean paramBoolean, int paramInt3)
  {
    int i;
    if (!paramBoolean)
    {
      i = paramInt2;
      if (paramInt1 <= 0) {}
    }
    else
    {
      int j;
      if ((!paramBoolean) || (paramInt3 < 3))
      {
        i = paramInt1;
        j = paramInt2;
        if (paramInt1 <= 99) {}
      }
      else
      {
        i = paramInt1 / 100;
        paramArrayOfChar[paramInt2] = ((char)(i + 48));
        j = paramInt2 + 1;
        i = paramInt1 - i * 100;
      }
      if (((!paramBoolean) || (paramInt3 < 2)) && (i <= 9))
      {
        paramInt3 = i;
        paramInt1 = j;
        if (paramInt2 == j) {}
      }
      else
      {
        paramInt2 = i / 10;
        paramArrayOfChar[j] = ((char)(paramInt2 + 48));
        paramInt1 = j + 1;
        paramInt3 = i - paramInt2 * 10;
      }
      paramArrayOfChar[paramInt1] = ((char)(paramInt3 + 48));
      paramInt1++;
      paramArrayOfChar[paramInt1] = paramChar;
      i = paramInt1 + 1;
    }
    return i;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/util/TimeUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */