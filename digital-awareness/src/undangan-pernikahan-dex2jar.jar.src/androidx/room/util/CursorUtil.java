package androidx.room.util;

import android.database.Cursor;
import android.database.MatrixCursor;

public class CursorUtil
{
  public static Cursor copyAndClose(Cursor paramCursor)
  {
    try
    {
      MatrixCursor localMatrixCursor = new android/database/MatrixCursor;
      localMatrixCursor.<init>(paramCursor.getColumnNames(), paramCursor.getCount());
      while (paramCursor.moveToNext())
      {
        Object localObject1 = new Object[paramCursor.getColumnCount()];
        int i = 0;
        while (i < paramCursor.getColumnCount())
        {
          switch (paramCursor.getType(i))
          {
          default: 
            localObject1 = new java/lang/IllegalStateException;
            break;
          case 4: 
            localObject1[i] = paramCursor.getBlob(i);
            break;
          case 3: 
            localObject1[i] = paramCursor.getString(i);
            break;
          case 2: 
            localObject1[i] = Double.valueOf(paramCursor.getDouble(i));
            break;
          case 1: 
            localObject1[i] = Long.valueOf(paramCursor.getLong(i));
            break;
          case 0: 
            localObject1[i] = null;
          }
          i++;
          continue;
          ((IllegalStateException)localObject1).<init>();
          throw ((Throwable)localObject1);
        }
        localMatrixCursor.addRow((Object[])localObject1);
      }
      return localMatrixCursor;
    }
    finally
    {
      paramCursor.close();
    }
  }
  
  public static int getColumnIndex(Cursor paramCursor, String paramString)
  {
    int i = paramCursor.getColumnIndex(paramString);
    if (i >= 0) {
      return i;
    }
    return paramCursor.getColumnIndex("`" + paramString + "`");
  }
  
  public static int getColumnIndexOrThrow(Cursor paramCursor, String paramString)
  {
    int i = paramCursor.getColumnIndex(paramString);
    if (i >= 0) {
      return i;
    }
    return paramCursor.getColumnIndexOrThrow("`" + paramString + "`");
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/room/util/CursorUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */