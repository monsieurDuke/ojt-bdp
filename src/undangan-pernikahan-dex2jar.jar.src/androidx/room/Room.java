package androidx.room;

import android.content.Context;

public class Room
{
  private static final String CURSOR_CONV_SUFFIX = "_CursorConverter";
  static final String LOG_TAG = "ROOM";
  public static final String MASTER_TABLE_NAME = "room_master_table";
  
  public static <T extends RoomDatabase> RoomDatabase.Builder<T> databaseBuilder(Context paramContext, Class<T> paramClass, String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() != 0)) {
      return new RoomDatabase.Builder(paramContext, paramClass, paramString);
    }
    throw new IllegalArgumentException("Cannot build a database with null or empty name. If you are trying to create an in memory database, use Room.inMemoryDatabaseBuilder");
  }
  
  static <T, C> T getGeneratedImplementation(Class<C> paramClass, String paramString)
  {
    String str2 = paramClass.getPackage().getName();
    String str1 = paramClass.getCanonicalName();
    if (!str2.isEmpty()) {
      str1 = str1.substring(str2.length() + 1);
    }
    str1 = str1.replace('.', '_') + paramString;
    try
    {
      if (str2.isEmpty())
      {
        paramString = str1;
      }
      else
      {
        paramString = new java/lang/StringBuilder;
        paramString.<init>();
        paramString = str2 + "." + str1;
      }
      paramString = Class.forName(paramString).newInstance();
      return paramString;
    }
    catch (InstantiationException paramString)
    {
      throw new RuntimeException("Failed to create an instance of " + paramClass.getCanonicalName());
    }
    catch (IllegalAccessException paramString)
    {
      throw new RuntimeException("Cannot access the constructor" + paramClass.getCanonicalName());
    }
    catch (ClassNotFoundException paramString)
    {
      throw new RuntimeException("cannot find implementation for " + paramClass.getCanonicalName() + ". " + str1 + " does not exist");
    }
  }
  
  public static <T extends RoomDatabase> RoomDatabase.Builder<T> inMemoryDatabaseBuilder(Context paramContext, Class<T> paramClass)
  {
    return new RoomDatabase.Builder(paramContext, paramClass, null);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/room/Room.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */