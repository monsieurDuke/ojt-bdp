package androidx.room.util;

import android.database.AbstractWindowedCursor;
import android.database.Cursor;
import android.database.CursorWindow;
import android.os.Build.VERSION;
import android.os.CancellationSignal;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteQuery;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DBUtil
{
  public static CancellationSignal createCancellationSignal()
  {
    if (Build.VERSION.SDK_INT >= 16) {
      return new CancellationSignal();
    }
    return null;
  }
  
  public static void dropFtsSyncTriggers(SupportSQLiteDatabase paramSupportSQLiteDatabase)
  {
    Object localObject2 = new ArrayList();
    Object localObject1 = paramSupportSQLiteDatabase.query("SELECT name FROM sqlite_master WHERE type = 'trigger'");
    try
    {
      while (((Cursor)localObject1).moveToNext()) {
        ((List)localObject2).add(((Cursor)localObject1).getString(0));
      }
      ((Cursor)localObject1).close();
      localObject2 = ((List)localObject2).iterator();
      while (((Iterator)localObject2).hasNext())
      {
        localObject1 = (String)((Iterator)localObject2).next();
        if (((String)localObject1).startsWith("room_fts_content_sync_")) {
          paramSupportSQLiteDatabase.execSQL("DROP TRIGGER IF EXISTS " + (String)localObject1);
        }
      }
      return;
    }
    finally
    {
      ((Cursor)localObject1).close();
    }
  }
  
  @Deprecated
  public static Cursor query(RoomDatabase paramRoomDatabase, SupportSQLiteQuery paramSupportSQLiteQuery, boolean paramBoolean)
  {
    return query(paramRoomDatabase, paramSupportSQLiteQuery, paramBoolean, null);
  }
  
  public static Cursor query(RoomDatabase paramRoomDatabase, SupportSQLiteQuery paramSupportSQLiteQuery, boolean paramBoolean, CancellationSignal paramCancellationSignal)
  {
    paramSupportSQLiteQuery = paramRoomDatabase.query(paramSupportSQLiteQuery, paramCancellationSignal);
    if ((paramBoolean) && ((paramSupportSQLiteQuery instanceof AbstractWindowedCursor)))
    {
      paramRoomDatabase = (AbstractWindowedCursor)paramSupportSQLiteQuery;
      int j = paramRoomDatabase.getCount();
      int i;
      if (paramRoomDatabase.hasWindow()) {
        i = paramRoomDatabase.getWindow().getNumRows();
      } else {
        i = j;
      }
      if ((Build.VERSION.SDK_INT < 23) || (i < j)) {
        return CursorUtil.copyAndClose(paramRoomDatabase);
      }
    }
    return paramSupportSQLiteQuery;
  }
  
  public static int readVersion(File paramFile)
    throws IOException
  {
    IOException localIOException = null;
    Object localObject = localIOException;
    try
    {
      ByteBuffer localByteBuffer = ByteBuffer.allocate(4);
      localObject = localIOException;
      FileInputStream localFileInputStream = new java/io/FileInputStream;
      localObject = localIOException;
      localFileInputStream.<init>(paramFile);
      localObject = localIOException;
      paramFile = localFileInputStream.getChannel();
      localObject = paramFile;
      paramFile.tryLock(60L, 4L, true);
      localObject = paramFile;
      paramFile.position(60L);
      localObject = paramFile;
      if (paramFile.read(localByteBuffer) == 4)
      {
        localObject = paramFile;
        localByteBuffer.rewind();
        localObject = paramFile;
        int i = localByteBuffer.getInt();
        if (paramFile != null) {
          paramFile.close();
        }
        return i;
      }
      localObject = paramFile;
      localIOException = new java/io/IOException;
      localObject = paramFile;
      localIOException.<init>("Bad database header, unable to read 4 bytes at offset 60");
      localObject = paramFile;
      throw localIOException;
    }
    finally
    {
      if (localObject != null) {
        ((FileChannel)localObject).close();
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/room/util/DBUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */