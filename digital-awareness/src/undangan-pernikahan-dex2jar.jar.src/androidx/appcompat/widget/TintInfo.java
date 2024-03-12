package androidx.appcompat.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;

public class TintInfo
{
  public boolean mHasTintList;
  public boolean mHasTintMode;
  public ColorStateList mTintList;
  public PorterDuff.Mode mTintMode;
  
  void clear()
  {
    this.mTintList = null;
    this.mHasTintList = false;
    this.mTintMode = null;
    this.mHasTintMode = false;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/appcompat/widget/TintInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */