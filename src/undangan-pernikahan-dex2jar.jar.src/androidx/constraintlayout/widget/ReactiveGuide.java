package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import androidx.constraintlayout.motion.widget.MotionLayout;

public class ReactiveGuide
  extends View
  implements SharedValues.SharedValuesListener
{
  private boolean mAnimateChange = false;
  private boolean mApplyToAllConstraintSets = true;
  private int mApplyToConstraintSetId = 0;
  private int mAttributeId = -1;
  
  public ReactiveGuide(Context paramContext)
  {
    super(paramContext);
    super.setVisibility(8);
    init(null);
  }
  
  public ReactiveGuide(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    super.setVisibility(8);
    init(paramAttributeSet);
  }
  
  public ReactiveGuide(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    super.setVisibility(8);
    init(paramAttributeSet);
  }
  
  public ReactiveGuide(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    super(paramContext, paramAttributeSet, paramInt1);
    super.setVisibility(8);
    init(paramAttributeSet);
  }
  
  private void changeValue(int paramInt1, int paramInt2, MotionLayout paramMotionLayout, int paramInt3)
  {
    ConstraintSet localConstraintSet = paramMotionLayout.getConstraintSet(paramInt3);
    localConstraintSet.setGuidelineEnd(paramInt2, paramInt1);
    paramMotionLayout.updateState(paramInt3, localConstraintSet);
  }
  
  private void init(AttributeSet paramAttributeSet)
  {
    if (paramAttributeSet != null)
    {
      paramAttributeSet = getContext().obtainStyledAttributes(paramAttributeSet, R.styleable.ConstraintLayout_ReactiveGuide);
      int j = paramAttributeSet.getIndexCount();
      for (int i = 0; i < j; i++)
      {
        int k = paramAttributeSet.getIndex(i);
        if (k == R.styleable.ConstraintLayout_ReactiveGuide_reactiveGuide_valueId) {
          this.mAttributeId = paramAttributeSet.getResourceId(k, this.mAttributeId);
        } else if (k == R.styleable.ConstraintLayout_ReactiveGuide_reactiveGuide_animateChange) {
          this.mAnimateChange = paramAttributeSet.getBoolean(k, this.mAnimateChange);
        } else if (k == R.styleable.ConstraintLayout_ReactiveGuide_reactiveGuide_applyToConstraintSet) {
          this.mApplyToConstraintSetId = paramAttributeSet.getResourceId(k, this.mApplyToConstraintSetId);
        } else if (k == R.styleable.ConstraintLayout_ReactiveGuide_reactiveGuide_applyToAllConstraintSets) {
          this.mApplyToAllConstraintSets = paramAttributeSet.getBoolean(k, this.mApplyToAllConstraintSets);
        }
      }
      paramAttributeSet.recycle();
    }
    if (this.mAttributeId != -1) {
      ConstraintLayout.getSharedValues().addListener(this.mAttributeId, this);
    }
  }
  
  public void draw(Canvas paramCanvas) {}
  
  public int getApplyToConstraintSetId()
  {
    return this.mApplyToConstraintSetId;
  }
  
  public int getAttributeId()
  {
    return this.mAttributeId;
  }
  
  public boolean isAnimatingChange()
  {
    return this.mAnimateChange;
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    setMeasuredDimension(0, 0);
  }
  
  public void onNewValue(int paramInt1, int paramInt2, int paramInt3)
  {
    setGuidelineBegin(paramInt2);
    int i = getId();
    if (i <= 0) {
      return;
    }
    if ((getParent() instanceof MotionLayout))
    {
      MotionLayout localMotionLayout = (MotionLayout)getParent();
      paramInt1 = localMotionLayout.getCurrentState();
      if (this.mApplyToConstraintSetId != 0) {
        paramInt1 = this.mApplyToConstraintSetId;
      }
      Object localObject;
      if (this.mAnimateChange)
      {
        if (this.mApplyToAllConstraintSets)
        {
          localObject = localMotionLayout.getConstraintSetIds();
          for (paramInt3 = 0; paramInt3 < localObject.length; paramInt3++)
          {
            int j = localObject[paramInt3];
            if (j != paramInt1) {
              changeValue(paramInt2, i, localMotionLayout, j);
            }
          }
        }
        localObject = localMotionLayout.cloneConstraintSet(paramInt1);
        ((ConstraintSet)localObject).setGuidelineEnd(i, paramInt2);
        localMotionLayout.updateStateAnimate(paramInt1, (ConstraintSet)localObject, 1000);
      }
      else if (this.mApplyToAllConstraintSets)
      {
        localObject = localMotionLayout.getConstraintSetIds();
        for (paramInt1 = 0; paramInt1 < localObject.length; paramInt1++) {
          changeValue(paramInt2, i, localMotionLayout, localObject[paramInt1]);
        }
      }
      else
      {
        changeValue(paramInt2, i, localMotionLayout, paramInt1);
      }
    }
  }
  
  public void setAnimateChange(boolean paramBoolean)
  {
    this.mAnimateChange = paramBoolean;
  }
  
  public void setApplyToConstraintSetId(int paramInt)
  {
    this.mApplyToConstraintSetId = paramInt;
  }
  
  public void setAttributeId(int paramInt)
  {
    SharedValues localSharedValues = ConstraintLayout.getSharedValues();
    int i = this.mAttributeId;
    if (i != -1) {
      localSharedValues.removeListener(i, this);
    }
    this.mAttributeId = paramInt;
    if (paramInt != -1) {
      localSharedValues.addListener(paramInt, this);
    }
  }
  
  public void setGuidelineBegin(int paramInt)
  {
    ConstraintLayout.LayoutParams localLayoutParams = (ConstraintLayout.LayoutParams)getLayoutParams();
    localLayoutParams.guideBegin = paramInt;
    setLayoutParams(localLayoutParams);
  }
  
  public void setGuidelineEnd(int paramInt)
  {
    ConstraintLayout.LayoutParams localLayoutParams = (ConstraintLayout.LayoutParams)getLayoutParams();
    localLayoutParams.guideEnd = paramInt;
    setLayoutParams(localLayoutParams);
  }
  
  public void setGuidelinePercent(float paramFloat)
  {
    ConstraintLayout.LayoutParams localLayoutParams = (ConstraintLayout.LayoutParams)getLayoutParams();
    localLayoutParams.guidePercent = paramFloat;
    setLayoutParams(localLayoutParams);
  }
  
  public void setVisibility(int paramInt) {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/widget/ReactiveGuide.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */