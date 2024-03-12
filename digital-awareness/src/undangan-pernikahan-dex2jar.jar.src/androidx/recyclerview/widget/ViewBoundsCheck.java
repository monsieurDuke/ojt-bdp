package androidx.recyclerview.widget;

import android.view.View;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

class ViewBoundsCheck
{
  static final int CVE_PVE_POS = 12;
  static final int CVE_PVS_POS = 8;
  static final int CVS_PVE_POS = 4;
  static final int CVS_PVS_POS = 0;
  static final int EQ = 2;
  static final int FLAG_CVE_EQ_PVE = 8192;
  static final int FLAG_CVE_EQ_PVS = 512;
  static final int FLAG_CVE_GT_PVE = 4096;
  static final int FLAG_CVE_GT_PVS = 256;
  static final int FLAG_CVE_LT_PVE = 16384;
  static final int FLAG_CVE_LT_PVS = 1024;
  static final int FLAG_CVS_EQ_PVE = 32;
  static final int FLAG_CVS_EQ_PVS = 2;
  static final int FLAG_CVS_GT_PVE = 16;
  static final int FLAG_CVS_GT_PVS = 1;
  static final int FLAG_CVS_LT_PVE = 64;
  static final int FLAG_CVS_LT_PVS = 4;
  static final int GT = 1;
  static final int LT = 4;
  static final int MASK = 7;
  BoundFlags mBoundFlags;
  final Callback mCallback;
  
  ViewBoundsCheck(Callback paramCallback)
  {
    this.mCallback = paramCallback;
    this.mBoundFlags = new BoundFlags();
  }
  
  View findOneViewWithinBoundFlags(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int k = this.mCallback.getParentStart();
    int j = this.mCallback.getParentEnd();
    int i;
    if (paramInt2 > paramInt1) {
      i = 1;
    } else {
      i = -1;
    }
    Object localObject1;
    for (Object localObject2 = null; paramInt1 != paramInt2; localObject2 = localObject1)
    {
      View localView = this.mCallback.getChildAt(paramInt1);
      int n = this.mCallback.getChildStart(localView);
      int m = this.mCallback.getChildEnd(localView);
      this.mBoundFlags.setBounds(k, j, n, m);
      if (paramInt3 != 0)
      {
        this.mBoundFlags.resetFlags();
        this.mBoundFlags.addFlags(paramInt3);
        if (this.mBoundFlags.boundsMatch()) {
          return localView;
        }
      }
      localObject1 = localObject2;
      if (paramInt4 != 0)
      {
        this.mBoundFlags.resetFlags();
        this.mBoundFlags.addFlags(paramInt4);
        localObject1 = localObject2;
        if (this.mBoundFlags.boundsMatch()) {
          localObject1 = localView;
        }
      }
      paramInt1 += i;
    }
    return (View)localObject2;
  }
  
  boolean isViewWithinBoundFlags(View paramView, int paramInt)
  {
    this.mBoundFlags.setBounds(this.mCallback.getParentStart(), this.mCallback.getParentEnd(), this.mCallback.getChildStart(paramView), this.mCallback.getChildEnd(paramView));
    if (paramInt != 0)
    {
      this.mBoundFlags.resetFlags();
      this.mBoundFlags.addFlags(paramInt);
      return this.mBoundFlags.boundsMatch();
    }
    return false;
  }
  
  static class BoundFlags
  {
    int mBoundFlags = 0;
    int mChildEnd;
    int mChildStart;
    int mRvEnd;
    int mRvStart;
    
    void addFlags(int paramInt)
    {
      this.mBoundFlags |= paramInt;
    }
    
    boolean boundsMatch()
    {
      int i = this.mBoundFlags;
      if (((i & 0x7) != 0) && ((i & compare(this.mChildStart, this.mRvStart) << 0) == 0)) {
        return false;
      }
      i = this.mBoundFlags;
      if (((i & 0x70) != 0) && ((i & compare(this.mChildStart, this.mRvEnd) << 4) == 0)) {
        return false;
      }
      i = this.mBoundFlags;
      if (((i & 0x700) != 0) && ((i & compare(this.mChildEnd, this.mRvStart) << 8) == 0)) {
        return false;
      }
      i = this.mBoundFlags;
      return ((i & 0x7000) == 0) || ((i & compare(this.mChildEnd, this.mRvEnd) << 12) != 0);
    }
    
    int compare(int paramInt1, int paramInt2)
    {
      if (paramInt1 > paramInt2) {
        return 1;
      }
      if (paramInt1 == paramInt2) {
        return 2;
      }
      return 4;
    }
    
    void resetFlags()
    {
      this.mBoundFlags = 0;
    }
    
    void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      this.mRvStart = paramInt1;
      this.mRvEnd = paramInt2;
      this.mChildStart = paramInt3;
      this.mChildEnd = paramInt4;
    }
  }
  
  static abstract interface Callback
  {
    public abstract View getChildAt(int paramInt);
    
    public abstract int getChildEnd(View paramView);
    
    public abstract int getChildStart(View paramView);
    
    public abstract int getParentEnd();
    
    public abstract int getParentStart();
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface ViewBounds {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/recyclerview/widget/ViewBoundsCheck.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */