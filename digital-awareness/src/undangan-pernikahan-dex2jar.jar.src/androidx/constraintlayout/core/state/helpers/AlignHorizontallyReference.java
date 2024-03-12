package androidx.constraintlayout.core.state.helpers;

import androidx.constraintlayout.core.state.ConstraintReference;
import androidx.constraintlayout.core.state.HelperReference;
import androidx.constraintlayout.core.state.State;
import androidx.constraintlayout.core.state.State.Helper;
import java.util.ArrayList;
import java.util.Iterator;

public class AlignHorizontallyReference
  extends HelperReference
{
  private float mBias = 0.5F;
  
  public AlignHorizontallyReference(State paramState)
  {
    super(paramState, State.Helper.ALIGN_VERTICALLY);
  }
  
  public void apply()
  {
    Iterator localIterator = this.mReferences.iterator();
    while (localIterator.hasNext())
    {
      Object localObject = localIterator.next();
      localObject = this.mState.constraints(localObject);
      ((ConstraintReference)localObject).clearHorizontal();
      if (this.mStartToStart != null) {
        ((ConstraintReference)localObject).startToStart(this.mStartToStart);
      } else if (this.mStartToEnd != null) {
        ((ConstraintReference)localObject).startToEnd(this.mStartToEnd);
      } else {
        ((ConstraintReference)localObject).startToStart(State.PARENT);
      }
      if (this.mEndToStart != null) {
        ((ConstraintReference)localObject).endToStart(this.mEndToStart);
      } else if (this.mEndToEnd != null) {
        ((ConstraintReference)localObject).endToEnd(this.mEndToEnd);
      } else {
        ((ConstraintReference)localObject).endToEnd(State.PARENT);
      }
      float f = this.mBias;
      if (f != 0.5F) {
        ((ConstraintReference)localObject).horizontalBias(f);
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/state/helpers/AlignHorizontallyReference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */