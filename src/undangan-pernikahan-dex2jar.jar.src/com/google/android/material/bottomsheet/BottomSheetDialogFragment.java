package com.google.android.material.bottomsheet;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatDialogFragment;

public class BottomSheetDialogFragment
  extends AppCompatDialogFragment
{
  private boolean waitingForDismissAllowingStateLoss;
  
  private void dismissAfterAnimation()
  {
    if (this.waitingForDismissAllowingStateLoss) {
      super.dismissAllowingStateLoss();
    } else {
      super.dismiss();
    }
  }
  
  private void dismissWithAnimation(BottomSheetBehavior<?> paramBottomSheetBehavior, boolean paramBoolean)
  {
    this.waitingForDismissAllowingStateLoss = paramBoolean;
    if (paramBottomSheetBehavior.getState() == 5)
    {
      dismissAfterAnimation();
    }
    else
    {
      if ((getDialog() instanceof BottomSheetDialog)) {
        ((BottomSheetDialog)getDialog()).removeDefaultCallback();
      }
      paramBottomSheetBehavior.addBottomSheetCallback(new BottomSheetDismissCallback(null));
      paramBottomSheetBehavior.setState(5);
    }
  }
  
  private boolean tryDismissWithAnimation(boolean paramBoolean)
  {
    Object localObject = getDialog();
    if ((localObject instanceof BottomSheetDialog))
    {
      BottomSheetDialog localBottomSheetDialog = (BottomSheetDialog)localObject;
      localObject = localBottomSheetDialog.getBehavior();
      if ((((BottomSheetBehavior)localObject).isHideable()) && (localBottomSheetDialog.getDismissWithAnimation()))
      {
        dismissWithAnimation((BottomSheetBehavior)localObject, paramBoolean);
        return true;
      }
    }
    return false;
  }
  
  public void dismiss()
  {
    if (!tryDismissWithAnimation(false)) {
      super.dismiss();
    }
  }
  
  public void dismissAllowingStateLoss()
  {
    if (!tryDismissWithAnimation(true)) {
      super.dismissAllowingStateLoss();
    }
  }
  
  public Dialog onCreateDialog(Bundle paramBundle)
  {
    return new BottomSheetDialog(getContext(), getTheme());
  }
  
  private class BottomSheetDismissCallback
    extends BottomSheetBehavior.BottomSheetCallback
  {
    private BottomSheetDismissCallback() {}
    
    public void onSlide(View paramView, float paramFloat) {}
    
    public void onStateChanged(View paramView, int paramInt)
    {
      if (paramInt == 5) {
        BottomSheetDialogFragment.this.dismissAfterAnimation();
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/bottomsheet/BottomSheetDialogFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */