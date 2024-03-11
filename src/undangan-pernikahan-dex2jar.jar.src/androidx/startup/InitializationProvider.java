package androidx.startup;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class InitializationProvider
  extends ContentProvider
{
  public final int delete(Uri paramUri, String paramString, String[] paramArrayOfString)
  {
    throw new IllegalStateException("Not allowed.");
  }
  
  public final String getType(Uri paramUri)
  {
    throw new IllegalStateException("Not allowed.");
  }
  
  public final Uri insert(Uri paramUri, ContentValues paramContentValues)
  {
    throw new IllegalStateException("Not allowed.");
  }
  
  public final boolean onCreate()
  {
    Context localContext = getContext();
    if (localContext != null)
    {
      if (localContext.getApplicationContext() != null) {
        AppInitializer.getInstance(localContext).discoverAndInitialize();
      } else {
        StartupLogger.w("Deferring initialization because `applicationContext` is null.");
      }
      return true;
    }
    throw new StartupException("Context cannot be null");
  }
  
  public final Cursor query(Uri paramUri, String[] paramArrayOfString1, String paramString1, String[] paramArrayOfString2, String paramString2)
  {
    throw new IllegalStateException("Not allowed.");
  }
  
  public final int update(Uri paramUri, ContentValues paramContentValues, String paramString, String[] paramArrayOfString)
  {
    throw new IllegalStateException("Not allowed.");
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/startup/InitializationProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */