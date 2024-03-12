package androidx.constraintlayout.core.state.helpers;

import androidx.constraintlayout.core.state.ConstraintReference;
import androidx.constraintlayout.core.state.HelperReference;
import androidx.constraintlayout.core.state.State;
import androidx.constraintlayout.core.state.State.Direction;
import androidx.constraintlayout.core.state.State.Helper;
import androidx.constraintlayout.core.widgets.Barrier;
import androidx.constraintlayout.core.widgets.HelperWidget;

public class BarrierReference
  extends HelperReference
{
  private Barrier mBarrierWidget;
  private State.Direction mDirection;
  private int mMargin;
  
  public BarrierReference(State paramState)
  {
    super(paramState, State.Helper.BARRIER);
  }
  
  public void apply()
  {
    getHelperWidget();
    int i = 0;
    switch (1.$SwitchMap$androidx$constraintlayout$core$state$State$Direction[this.mDirection.ordinal()])
    {
    default: 
      break;
    case 6: 
      i = 3;
      break;
    case 5: 
      i = 2;
      break;
    case 3: 
    case 4: 
      i = 1;
      break;
    }
    this.mBarrierWidget.setBarrierType(i);
    this.mBarrierWidget.setMargin(this.mMargin);
  }
  
  public HelperWidget getHelperWidget()
  {
    if (this.mBarrierWidget == null) {
      this.mBarrierWidget = new Barrier();
    }
    return this.mBarrierWidget;
  }
  
  public ConstraintReference margin(int paramInt)
  {
    this.mMargin = paramInt;
    return this;
  }
  
  public ConstraintReference margin(Object paramObject)
  {
    margin(this.mState.convertDimension(paramObject));
    return this;
  }
  
  public void setBarrierDirection(State.Direction paramDirection)
  {
    this.mDirection = paramDirection;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/state/helpers/BarrierReference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */