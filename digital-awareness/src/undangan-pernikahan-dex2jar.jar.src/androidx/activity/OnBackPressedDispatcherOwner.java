package androidx.activity;

import androidx.lifecycle.LifecycleOwner;

public abstract interface OnBackPressedDispatcherOwner
  extends LifecycleOwner
{
  public abstract OnBackPressedDispatcher getOnBackPressedDispatcher();
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/activity/OnBackPressedDispatcherOwner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */