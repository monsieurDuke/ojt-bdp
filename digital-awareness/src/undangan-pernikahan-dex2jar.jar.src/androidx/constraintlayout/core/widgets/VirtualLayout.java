package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure.Measure;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure.Measurer;
import java.util.HashSet;

public class VirtualLayout
  extends HelperWidget
{
  protected BasicMeasure.Measure mMeasure = new BasicMeasure.Measure();
  private int mMeasuredHeight = 0;
  private int mMeasuredWidth = 0;
  BasicMeasure.Measurer mMeasurer = null;
  private boolean mNeedsCallFromSolver = false;
  private int mPaddingBottom = 0;
  private int mPaddingEnd = 0;
  private int mPaddingLeft = 0;
  private int mPaddingRight = 0;
  private int mPaddingStart = 0;
  private int mPaddingTop = 0;
  private int mResolvedPaddingLeft = 0;
  private int mResolvedPaddingRight = 0;
  
  public void applyRtl(boolean paramBoolean)
  {
    int i = this.mPaddingStart;
    if ((i > 0) || (this.mPaddingEnd > 0)) {
      if (paramBoolean)
      {
        this.mResolvedPaddingLeft = this.mPaddingEnd;
        this.mResolvedPaddingRight = i;
      }
      else
      {
        this.mResolvedPaddingLeft = i;
        this.mResolvedPaddingRight = this.mPaddingEnd;
      }
    }
  }
  
  public void captureWidgets()
  {
    for (int i = 0; i < this.mWidgetsCount; i++)
    {
      ConstraintWidget localConstraintWidget = this.mWidgets[i];
      if (localConstraintWidget != null) {
        localConstraintWidget.setInVirtualLayout(true);
      }
    }
  }
  
  public boolean contains(HashSet<ConstraintWidget> paramHashSet)
  {
    for (int i = 0; i < this.mWidgetsCount; i++) {
      if (paramHashSet.contains(this.mWidgets[i])) {
        return true;
      }
    }
    return false;
  }
  
  public int getMeasuredHeight()
  {
    return this.mMeasuredHeight;
  }
  
  public int getMeasuredWidth()
  {
    return this.mMeasuredWidth;
  }
  
  public int getPaddingBottom()
  {
    return this.mPaddingBottom;
  }
  
  public int getPaddingLeft()
  {
    return this.mResolvedPaddingLeft;
  }
  
  public int getPaddingRight()
  {
    return this.mResolvedPaddingRight;
  }
  
  public int getPaddingTop()
  {
    return this.mPaddingTop;
  }
  
  public void measure(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
  
  protected void measure(ConstraintWidget paramConstraintWidget, ConstraintWidget.DimensionBehaviour paramDimensionBehaviour1, int paramInt1, ConstraintWidget.DimensionBehaviour paramDimensionBehaviour2, int paramInt2)
  {
    while ((this.mMeasurer == null) && (getParent() != null)) {
      this.mMeasurer = ((ConstraintWidgetContainer)getParent()).getMeasurer();
    }
    this.mMeasure.horizontalBehavior = paramDimensionBehaviour1;
    this.mMeasure.verticalBehavior = paramDimensionBehaviour2;
    this.mMeasure.horizontalDimension = paramInt1;
    this.mMeasure.verticalDimension = paramInt2;
    this.mMeasurer.measure(paramConstraintWidget, this.mMeasure);
    paramConstraintWidget.setWidth(this.mMeasure.measuredWidth);
    paramConstraintWidget.setHeight(this.mMeasure.measuredHeight);
    paramConstraintWidget.setHasBaseline(this.mMeasure.measuredHasBaseline);
    paramConstraintWidget.setBaselineDistance(this.mMeasure.measuredBaseline);
  }
  
  protected boolean measureChildren()
  {
    BasicMeasure.Measurer localMeasurer = null;
    if (this.mParent != null) {
      localMeasurer = ((ConstraintWidgetContainer)this.mParent).getMeasurer();
    }
    if (localMeasurer == null) {
      return false;
    }
    for (int i = 0;; i++)
    {
      int k = this.mWidgetsCount;
      int j = 1;
      if (i >= k) {
        break;
      }
      ConstraintWidget localConstraintWidget = this.mWidgets[i];
      if ((localConstraintWidget != null) && (!(localConstraintWidget instanceof Guideline)))
      {
        Object localObject2 = localConstraintWidget.getDimensionBehaviour(0);
        ConstraintWidget.DimensionBehaviour localDimensionBehaviour = localConstraintWidget.getDimensionBehaviour(1);
        if ((localObject2 != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) || (localConstraintWidget.mMatchConstraintDefaultWidth == 1) || (localDimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) || (localConstraintWidget.mMatchConstraintDefaultHeight == 1)) {
          j = 0;
        }
        if (j == 0)
        {
          Object localObject1 = localObject2;
          if (localObject2 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            localObject1 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
          }
          localObject2 = localDimensionBehaviour;
          if (localDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            localObject2 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
          }
          this.mMeasure.horizontalBehavior = ((ConstraintWidget.DimensionBehaviour)localObject1);
          this.mMeasure.verticalBehavior = ((ConstraintWidget.DimensionBehaviour)localObject2);
          this.mMeasure.horizontalDimension = localConstraintWidget.getWidth();
          this.mMeasure.verticalDimension = localConstraintWidget.getHeight();
          localMeasurer.measure(localConstraintWidget, this.mMeasure);
          localConstraintWidget.setWidth(this.mMeasure.measuredWidth);
          localConstraintWidget.setHeight(this.mMeasure.measuredHeight);
          localConstraintWidget.setBaselineDistance(this.mMeasure.measuredBaseline);
        }
      }
    }
    return true;
  }
  
  public boolean needSolverPass()
  {
    return this.mNeedsCallFromSolver;
  }
  
  protected void needsCallbackFromSolver(boolean paramBoolean)
  {
    this.mNeedsCallFromSolver = paramBoolean;
  }
  
  public void setMeasure(int paramInt1, int paramInt2)
  {
    this.mMeasuredWidth = paramInt1;
    this.mMeasuredHeight = paramInt2;
  }
  
  public void setPadding(int paramInt)
  {
    this.mPaddingLeft = paramInt;
    this.mPaddingTop = paramInt;
    this.mPaddingRight = paramInt;
    this.mPaddingBottom = paramInt;
    this.mPaddingStart = paramInt;
    this.mPaddingEnd = paramInt;
  }
  
  public void setPaddingBottom(int paramInt)
  {
    this.mPaddingBottom = paramInt;
  }
  
  public void setPaddingEnd(int paramInt)
  {
    this.mPaddingEnd = paramInt;
  }
  
  public void setPaddingLeft(int paramInt)
  {
    this.mPaddingLeft = paramInt;
    this.mResolvedPaddingLeft = paramInt;
  }
  
  public void setPaddingRight(int paramInt)
  {
    this.mPaddingRight = paramInt;
    this.mResolvedPaddingRight = paramInt;
  }
  
  public void setPaddingStart(int paramInt)
  {
    this.mPaddingStart = paramInt;
    this.mResolvedPaddingLeft = paramInt;
    this.mResolvedPaddingRight = paramInt;
  }
  
  public void setPaddingTop(int paramInt)
  {
    this.mPaddingTop = paramInt;
  }
  
  public void updateConstraints(ConstraintWidgetContainer paramConstraintWidgetContainer)
  {
    captureWidgets();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/widgets/VirtualLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */