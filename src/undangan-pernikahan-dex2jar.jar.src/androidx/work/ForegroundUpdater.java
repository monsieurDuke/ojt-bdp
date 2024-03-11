package androidx.work;

import android.content.Context;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.UUID;

public abstract interface ForegroundUpdater
{
  public abstract ListenableFuture<Void> setForegroundAsync(Context paramContext, UUID paramUUID, ForegroundInfo paramForegroundInfo);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/ForegroundUpdater.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */