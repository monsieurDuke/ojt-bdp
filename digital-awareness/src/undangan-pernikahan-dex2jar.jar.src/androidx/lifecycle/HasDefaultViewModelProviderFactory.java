package androidx.lifecycle;

import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.CreationExtras.Empty;

public abstract interface HasDefaultViewModelProviderFactory
{
  public CreationExtras getDefaultViewModelCreationExtras()
  {
    return CreationExtras.Empty.INSTANCE;
  }
  
  public abstract ViewModelProvider.Factory getDefaultViewModelProviderFactory();
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/lifecycle/HasDefaultViewModelProviderFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */