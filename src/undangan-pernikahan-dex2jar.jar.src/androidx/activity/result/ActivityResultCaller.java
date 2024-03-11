package androidx.activity.result;

import androidx.activity.result.contract.ActivityResultContract;

public abstract interface ActivityResultCaller
{
  public abstract <I, O> ActivityResultLauncher<I> registerForActivityResult(ActivityResultContract<I, O> paramActivityResultContract, ActivityResultCallback<O> paramActivityResultCallback);
  
  public abstract <I, O> ActivityResultLauncher<I> registerForActivityResult(ActivityResultContract<I, O> paramActivityResultContract, ActivityResultRegistry paramActivityResultRegistry, ActivityResultCallback<O> paramActivityResultCallback);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/activity/result/ActivityResultCaller.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */