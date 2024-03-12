package androidx.activity.result;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.core.app.ActivityOptionsCompat;

public abstract class ActivityResultLauncher<I>
{
  public abstract ActivityResultContract<I, ?> getContract();
  
  public void launch(I paramI)
  {
    launch(paramI, null);
  }
  
  public abstract void launch(I paramI, ActivityOptionsCompat paramActivityOptionsCompat);
  
  public abstract void unregister();
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/activity/result/ActivityResultLauncher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */