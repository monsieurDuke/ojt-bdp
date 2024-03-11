package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintHelper;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.R.styleable;
import java.util.HashMap;

public class MotionHelper
  extends ConstraintHelper
  implements MotionHelperInterface
{
  private float mProgress;
  private boolean mUseOnHide = false;
  private boolean mUseOnShow = false;
  protected View[] views;
  
  public MotionHelper(Context paramContext)
  {
    super(paramContext);
  }
  
  public MotionHelper(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramAttributeSet);
  }
  
  public MotionHelper(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramAttributeSet);
  }
  
  public float getProgress()
  {
    return this.mProgress;
  }
  
  protected void init(AttributeSet paramAttributeSet)
  {
    super.init(paramAttributeSet);
    if (paramAttributeSet != null)
    {
      paramAttributeSet = getContext().obtainStyledAttributes(paramAttributeSet, R.styleable.MotionHelper);
      int j = paramAttributeSet.getIndexCount();
      for (int i = 0; i < j; i++)
      {
        int k = paramAttributeSet.getIndex(i);
        if (k == R.styleable.MotionHelper_onShow) {
          this.mUseOnShow = paramAttributeSet.getBoolean(k, this.mUseOnShow);
        } else if (k == R.styleable.MotionHelper_onHide) {
          this.mUseOnHide = paramAttributeSet.getBoolean(k, this.mUseOnHide);
        }
      }
      paramAttributeSet.recycle();
    }
  }
  
  public boolean isDecorator()
  {
    return false;
  }
  
  public boolean isUseOnHide()
  {
    return this.mUseOnHide;
  }
  
  public boolean isUsedOnShow()
  {
    return this.mUseOnShow;
  }
  
  public void onFinishedMotionScene(MotionLayout paramMotionLayout) {}
  
  public void onPostDraw(Canvas paramCanvas) {}
  
  public void onPreDraw(Canvas paramCanvas) {}
  
  public void onPreSetup(MotionLayout paramMotionLayout, HashMap<View, MotionController> paramHashMap) {}
  
  public void onTransitionChange(MotionLayout paramMotionLayout, int paramInt1, int paramInt2, float paramFloat) {}
  
  public void onTransitionCompleted(MotionLayout paramMotionLayout, int paramInt) {}
  
  public void onTransitionStarted(MotionLayout paramMotionLayout, int paramInt1, int paramInt2) {}
  
  public void onTransitionTrigger(MotionLayout paramMotionLayout, int paramInt, boolean paramBoolean, float paramFloat) {}
  
  public void setProgress(float paramFloat)
  {
    this.mProgress = paramFloat;
    int i;
    if (this.mCount > 0)
    {
      this.views = getViews((ConstraintLayout)getParent());
      for (i = 0; i < this.mCount; i++) {
        setProgress(this.views[i], paramFloat);
      }
    }
    else
    {
      ViewGroup localViewGroup = (ViewGroup)getParent();
      int j = localViewGroup.getChildCount();
      for (i = 0; i < j; i++)
      {
        View localView = localViewGroup.getChildAt(i);
        if (!(localView instanceof MotionHelper)) {
          setProgress(localView, paramFloat);
        }
      }
    }
  }
  
  public void setProgress(View paramView, float paramFloat) {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/motion/widget/MotionHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */