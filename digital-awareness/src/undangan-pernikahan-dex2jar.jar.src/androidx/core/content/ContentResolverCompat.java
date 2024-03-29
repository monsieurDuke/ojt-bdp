package androidx.core.content;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;

public final class ContentResolverCompat
{
  public static Cursor query(ContentResolver paramContentResolver, Uri paramUri, String[] paramArrayOfString1, String paramString1, String[] paramArrayOfString2, String paramString2, androidx.core.os.CancellationSignal paramCancellationSignal)
  {
    if (Build.VERSION.SDK_INT >= 16)
    {
      if (paramCancellationSignal != null) {
        try
        {
          paramCancellationSignal = paramCancellationSignal.getCancellationSignalObject();
        }
        catch (Exception paramContentResolver)
        {
          break label49;
        }
      } else {
        paramCancellationSignal = null;
      }
      paramContentResolver = Api16Impl.query(paramContentResolver, paramUri, paramArrayOfString1, paramString1, paramArrayOfString2, paramString2, (android.os.CancellationSignal)paramCancellationSignal);
      return paramContentResolver;
      label49:
      if ((paramContentResolver instanceof android.os.OperationCanceledException)) {
        throw new androidx.core.os.OperationCanceledException();
      }
      throw paramContentResolver;
    }
    if (paramCancellationSignal != null) {
      paramCancellationSignal.throwIfCanceled();
    }
    return paramContentResolver.query(paramUri, paramArrayOfString1, paramString1, paramArrayOfString2, paramString2);
  }
  
  static class Api16Impl
  {
    static Cursor query(ContentResolver paramContentResolver, Uri paramUri, String[] paramArrayOfString1, String paramString1, String[] paramArrayOfString2, String paramString2, android.os.CancellationSignal paramCancellationSignal)
    {
      return paramContentResolver.query(paramUri, paramArrayOfString1, paramString1, paramArrayOfString2, paramString2, paramCancellationSignal);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/content/ContentResolverCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */