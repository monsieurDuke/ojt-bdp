package androidx.work.impl;

import android.content.Context;
import android.os.Build.VERSION;
import androidx.work.Logger;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class WorkDatabasePathHelper
{
  private static final String[] DATABASE_EXTRA_FILES = { "-journal", "-shm", "-wal" };
  private static final String TAG;
  private static final String WORK_DATABASE_NAME = "androidx.work.workdb";
  
  static
  {
    String str = Logger.tagWithPrefix("WrkDbPathHelper");
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    TAG = str;
  }
  
  public static File getDatabasePath(Context paramContext)
  {
    if (Build.VERSION.SDK_INT < 23) {
      return getDefaultDatabasePath(paramContext);
    }
    return getNoBackupPath(paramContext, "androidx.work.workdb");
  }
  
  public static File getDefaultDatabasePath(Context paramContext)
  {
    return paramContext.getDatabasePath("androidx.work.workdb");
  }
  
  private static File getNoBackupPath(Context paramContext, String paramString)
  {
    return new File(paramContext.getNoBackupFilesDir(), paramString);
  }
  
  public static String getWorkDatabaseName()
  {
    return "androidx.work.workdb";
  }
  
  public static void migrateDatabase(Context paramContext)
  {
    Object localObject = getDefaultDatabasePath(paramContext);
    if ((Build.VERSION.SDK_INT >= 23) && (((File)localObject).exists()))
    {
      Logger.get().debug(TAG, "Migrating WorkDatabase to the no-backup directory", new Throwable[0]);
      localObject = migrationPaths(paramContext);
      Iterator localIterator = ((Map)localObject).keySet().iterator();
      while (localIterator.hasNext())
      {
        File localFile2 = (File)localIterator.next();
        File localFile1 = (File)((Map)localObject).get(localFile2);
        if ((localFile2.exists()) && (localFile1 != null))
        {
          if (localFile1.exists())
          {
            paramContext = String.format("Over-writing contents of %s", new Object[] { localFile1 });
            Log5ECF72.a(paramContext);
            LogE84000.a(paramContext);
            Log229316.a(paramContext);
            Logger.get().warning(TAG, paramContext, new Throwable[0]);
          }
          if (localFile2.renameTo(localFile1))
          {
            paramContext = String.format("Migrated %s to %s", new Object[] { localFile2, localFile1 });
            Log5ECF72.a(paramContext);
            LogE84000.a(paramContext);
            Log229316.a(paramContext);
          }
          else
          {
            paramContext = String.format("Renaming %s to %s failed", new Object[] { localFile2, localFile1 });
            Log5ECF72.a(paramContext);
            LogE84000.a(paramContext);
            Log229316.a(paramContext);
          }
          Logger.get().debug(TAG, paramContext, new Throwable[0]);
        }
      }
    }
  }
  
  public static Map<File, File> migrationPaths(Context paramContext)
  {
    HashMap localHashMap = new HashMap();
    if (Build.VERSION.SDK_INT >= 23)
    {
      File localFile = getDefaultDatabasePath(paramContext);
      paramContext = getDatabasePath(paramContext);
      localHashMap.put(localFile, paramContext);
      for (String str : DATABASE_EXTRA_FILES) {
        localHashMap.put(new File(localFile.getPath() + str), new File(paramContext.getPath() + str));
      }
    }
    return localHashMap;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/WorkDatabasePathHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */