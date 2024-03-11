package androidx.constraintlayout.core.state.helpers;

import androidx.constraintlayout.core.state.ConstraintReference;
import androidx.constraintlayout.core.state.State;
import androidx.constraintlayout.core.state.State.Helper;
import java.util.ArrayList;
import java.util.Iterator;

public class HorizontalChainReference
  extends ChainReference
{
  public HorizontalChainReference(State paramState)
  {
    super(paramState, State.Helper.HORIZONTAL_CHAIN);
  }
  
  public void apply()
  {
    Object localObject2 = null;
    Object localObject1 = null;
    Object localObject3 = this.mReferences.iterator();
    Object localObject4;
    while (((Iterator)localObject3).hasNext())
    {
      localObject4 = ((Iterator)localObject3).next();
      this.mState.constraints(localObject4).clearHorizontal();
    }
    Iterator localIterator = this.mReferences.iterator();
    while (localIterator.hasNext())
    {
      localObject3 = localIterator.next();
      localObject3 = this.mState.constraints(localObject3);
      localObject4 = localObject2;
      if (localObject2 == null)
      {
        localObject4 = localObject3;
        if (this.mStartToStart != null) {
          ((ConstraintReference)localObject4).startToStart(this.mStartToStart).margin(this.mMarginStart).marginGone(this.mMarginStartGone);
        } else if (this.mStartToEnd != null) {
          ((ConstraintReference)localObject4).startToEnd(this.mStartToEnd).margin(this.mMarginStart).marginGone(this.mMarginStartGone);
        } else if (this.mLeftToLeft != null) {
          ((ConstraintReference)localObject4).startToStart(this.mLeftToLeft).margin(this.mMarginLeft).marginGone(this.mMarginLeftGone);
        } else if (this.mLeftToRight != null) {
          ((ConstraintReference)localObject4).startToEnd(this.mLeftToRight).margin(this.mMarginLeft).marginGone(this.mMarginLeftGone);
        } else {
          ((ConstraintReference)localObject4).startToStart(State.PARENT);
        }
      }
      if (localObject1 != null)
      {
        ((ConstraintReference)localObject1).endToStart(((ConstraintReference)localObject3).getKey());
        ((ConstraintReference)localObject3).startToEnd(((ConstraintReference)localObject1).getKey());
      }
      localObject1 = localObject3;
      localObject2 = localObject4;
    }
    if (localObject1 != null) {
      if (this.mEndToStart != null) {
        ((ConstraintReference)localObject1).endToStart(this.mEndToStart).margin(this.mMarginEnd).marginGone(this.mMarginEndGone);
      } else if (this.mEndToEnd != null) {
        ((ConstraintReference)localObject1).endToEnd(this.mEndToEnd).margin(this.mMarginEnd).marginGone(this.mMarginEndGone);
      } else if (this.mRightToLeft != null) {
        ((ConstraintReference)localObject1).endToStart(this.mRightToLeft).margin(this.mMarginRight).marginGone(this.mMarginRightGone);
      } else if (this.mRightToRight != null) {
        ((ConstraintReference)localObject1).endToEnd(this.mRightToRight).margin(this.mMarginRight).marginGone(this.mMarginRightGone);
      } else {
        ((ConstraintReference)localObject1).endToEnd(State.PARENT);
      }
    }
    if (localObject2 == null) {
      return;
    }
    if (this.mBias != 0.5F) {
      ((ConstraintReference)localObject2).horizontalBias(this.mBias);
    }
    switch (1.$SwitchMap$androidx$constraintlayout$core$state$State$Chain[this.mStyle.ordinal()])
    {
    default: 
      break;
    case 3: 
      ((ConstraintReference)localObject2).setHorizontalChainStyle(2);
      break;
    case 2: 
      ((ConstraintReference)localObject2).setHorizontalChainStyle(1);
      break;
    case 1: 
      ((ConstraintReference)localObject2).setHorizontalChainStyle(0);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/state/helpers/HorizontalChainReference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */