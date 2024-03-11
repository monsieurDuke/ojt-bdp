package androidx.viewpager2.adapter;

import android.os.Parcelable;

public abstract interface StatefulAdapter
{
  public abstract void restoreState(Parcelable paramParcelable);
  
  public abstract Parcelable saveState();
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/viewpager2/adapter/StatefulAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */