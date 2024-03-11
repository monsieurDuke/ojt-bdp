package androidx.constraintlayout.core.state.helpers;

import androidx.constraintlayout.core.state.ConstraintReference;
import androidx.constraintlayout.core.state.HelperReference;
import androidx.constraintlayout.core.state.State;
import androidx.constraintlayout.core.state.State.Helper;
import java.util.ArrayList;
import java.util.Iterator;

public class AlignVerticallyReference
  extends HelperReference
{
  private float mBias = 0.5F;
  
  public AlignVerticallyReference(State paramState)
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
      ((ConstraintReference)localObject).clearVertical();
      if (this.mTopToTop != null) {
        ((ConstraintReference)localObject).topToTop(this.mTopToTop);
      } else if (this.mTopToBottom != null) {
        ((ConstraintReference)localObject).topToBottom(this.mTopToBottom);
      } else {
        ((ConstraintReference)localObject).topToTop(State.PARENT);
      }
      if (this.mBottomToTop != null) {
        ((ConstraintReference)localObject).bottomToTop(this.mBottomToTop);
      } else if (this.mBottomToBottom != null) {
        ((ConstraintReference)localObject).bottomToBottom(this.mBottomToBottom);
      } else {
        ((ConstraintReference)localObject).bottomToBottom(State.PARENT);
      }
      float f = this.mBias;
      if (f != 0.5F) {
        ((ConstraintReference)localObject).verticalBias(f);
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/state/helpers/AlignVerticallyReference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */