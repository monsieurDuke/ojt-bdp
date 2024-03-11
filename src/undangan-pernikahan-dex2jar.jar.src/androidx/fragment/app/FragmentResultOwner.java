package androidx.fragment.app;

import android.os.Bundle;
import androidx.lifecycle.LifecycleOwner;

public abstract interface FragmentResultOwner
{
  public abstract void clearFragmentResult(String paramString);
  
  public abstract void clearFragmentResultListener(String paramString);
  
  public abstract void setFragmentResult(String paramString, Bundle paramBundle);
  
  public abstract void setFragmentResultListener(String paramString, LifecycleOwner paramLifecycleOwner, FragmentResultListener paramFragmentResultListener);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/fragment/app/FragmentResultOwner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */