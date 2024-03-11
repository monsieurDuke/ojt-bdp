package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.LinearSystem;

public class Placeholder
  extends VirtualLayout
{
  public void addToSolver(LinearSystem paramLinearSystem, boolean paramBoolean)
  {
    super.addToSolver(paramLinearSystem, paramBoolean);
    if (this.mWidgetsCount > 0)
    {
      paramLinearSystem = this.mWidgets[0];
      paramLinearSystem.resetAllConstraints();
      paramLinearSystem.connect(ConstraintAnchor.Type.LEFT, this, ConstraintAnchor.Type.LEFT);
      paramLinearSystem.connect(ConstraintAnchor.Type.RIGHT, this, ConstraintAnchor.Type.RIGHT);
      paramLinearSystem.connect(ConstraintAnchor.Type.TOP, this, ConstraintAnchor.Type.TOP);
      paramLinearSystem.connect(ConstraintAnchor.Type.BOTTOM, this, ConstraintAnchor.Type.BOTTOM);
    }
  }
  
  public void measure(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int k = getPaddingLeft();
    int m = getPaddingRight();
    int i = getPaddingTop();
    int j = getPaddingBottom();
    k = 0 + (k + m);
    m = 0 + (i + j);
    int n = this.mWidgetsCount;
    boolean bool = false;
    j = k;
    i = m;
    if (n > 0)
    {
      j = k + this.mWidgets[0].getWidth();
      i = m + this.mWidgets[0].getHeight();
    }
    k = Math.max(getMinWidth(), j);
    i = Math.max(getMinHeight(), i);
    m = 0;
    j = 0;
    if (paramInt1 != 1073741824) {
      if (paramInt1 == Integer.MIN_VALUE)
      {
        paramInt2 = Math.min(k, paramInt2);
      }
      else
      {
        paramInt2 = m;
        if (paramInt1 == 0) {
          paramInt2 = k;
        }
      }
    }
    if (paramInt3 == 1073741824)
    {
      paramInt1 = paramInt4;
    }
    else if (paramInt3 == Integer.MIN_VALUE)
    {
      paramInt1 = Math.min(i, paramInt4);
    }
    else
    {
      paramInt1 = j;
      if (paramInt3 == 0) {
        paramInt1 = i;
      }
    }
    setMeasure(paramInt2, paramInt1);
    setWidth(paramInt2);
    setHeight(paramInt1);
    if (this.mWidgetsCount > 0) {
      bool = true;
    }
    needsCallbackFromSolver(bool);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/widgets/Placeholder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */