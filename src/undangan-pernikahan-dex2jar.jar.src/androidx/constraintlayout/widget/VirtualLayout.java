package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;

public abstract class VirtualLayout
  extends ConstraintHelper
{
  private boolean mApplyElevationOnAttach;
  private boolean mApplyVisibilityOnAttach;
  
  public VirtualLayout(Context paramContext)
  {
    super(paramContext);
  }
  
  public VirtualLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public VirtualLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  protected void applyLayoutFeaturesInConstraintSet(ConstraintLayout paramConstraintLayout)
  {
    applyLayoutFeatures(paramConstraintLayout);
  }
  
  protected void init(AttributeSet paramAttributeSet)
  {
    super.init(paramAttributeSet);
    if (paramAttributeSet != null)
    {
      paramAttributeSet = getContext().obtainStyledAttributes(paramAttributeSet, R.styleable.ConstraintLayout_Layout);
      int j = paramAttributeSet.getIndexCount();
      for (int i = 0; i < j; i++)
      {
        int k = paramAttributeSet.getIndex(i);
        if (k == R.styleable.ConstraintLayout_Layout_android_visibility) {
          this.mApplyVisibilityOnAttach = true;
        } else if (k == R.styleable.ConstraintLayout_Layout_android_elevation) {
          this.mApplyElevationOnAttach = true;
        }
      }
      paramAttributeSet.recycle();
    }
  }
  
  public void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    if ((this.mApplyVisibilityOnAttach) || (this.mApplyElevationOnAttach))
    {
      Object localObject = getParent();
      if ((localObject instanceof ConstraintLayout))
      {
        ConstraintLayout localConstraintLayout = (ConstraintLayout)localObject;
        int j = getVisibility();
        float f = 0.0F;
        if (Build.VERSION.SDK_INT >= 21) {
          f = getElevation();
        }
        for (int i = 0; i < this.mCount; i++)
        {
          localObject = localConstraintLayout.getViewById(this.mIds[i]);
          if (localObject != null)
          {
            if (this.mApplyVisibilityOnAttach) {
              ((View)localObject).setVisibility(j);
            }
            if ((this.mApplyElevationOnAttach) && (f > 0.0F) && (Build.VERSION.SDK_INT >= 21)) {
              ((View)localObject).setTranslationZ(((View)localObject).getTranslationZ() + f);
            }
          }
        }
      }
    }
  }
  
  public void onMeasure(androidx.constraintlayout.core.widgets.VirtualLayout paramVirtualLayout, int paramInt1, int paramInt2) {}
  
  public void setElevation(float paramFloat)
  {
    super.setElevation(paramFloat);
    applyLayoutFeatures();
  }
  
  public void setVisibility(int paramInt)
  {
    super.setVisibility(paramInt);
    applyLayoutFeatures();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/widget/VirtualLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */