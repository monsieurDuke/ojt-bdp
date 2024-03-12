package androidx.constraintlayout.core.state.helpers;

import androidx.constraintlayout.core.state.ConstraintReference;
import androidx.constraintlayout.core.state.State;
import androidx.constraintlayout.core.state.State.Helper;
import java.util.ArrayList;
import java.util.Iterator;

public class VerticalChainReference
  extends ChainReference
{
  public VerticalChainReference(State paramState)
  {
    super(paramState, State.Helper.VERTICAL_CHAIN);
  }
  
  public void apply()
  {
    Object localObject2 = null;
    Object localObject1 = null;
    Object localObject4 = this.mReferences.iterator();
    Object localObject3;
    while (((Iterator)localObject4).hasNext())
    {
      localObject3 = ((Iterator)localObject4).next();
      this.mState.constraints(localObject3).clearVertical();
    }
    Iterator localIterator = this.mReferences.iterator();
    while (localIterator.hasNext())
    {
      localObject3 = localIterator.next();
      localObject4 = this.mState.constraints(localObject3);
      localObject3 = localObject2;
      if (localObject2 == null)
      {
        localObject3 = localObject4;
        if (this.mTopToTop != null) {
          ((ConstraintReference)localObject3).topToTop(this.mTopToTop).margin(this.mMarginTop).marginGone(this.mMarginTopGone);
        } else if (this.mTopToBottom != null) {
          ((ConstraintReference)localObject3).topToBottom(this.mTopToBottom).margin(this.mMarginTop).marginGone(this.mMarginTopGone);
        } else {
          ((ConstraintReference)localObject3).topToTop(State.PARENT);
        }
      }
      if (localObject1 != null)
      {
        ((ConstraintReference)localObject1).bottomToTop(((ConstraintReference)localObject4).getKey());
        ((ConstraintReference)localObject4).topToBottom(((ConstraintReference)localObject1).getKey());
      }
      localObject1 = localObject4;
      localObject2 = localObject3;
    }
    if (localObject1 != null) {
      if (this.mBottomToTop != null) {
        ((ConstraintReference)localObject1).bottomToTop(this.mBottomToTop).margin(this.mMarginBottom).marginGone(this.mMarginBottomGone);
      } else if (this.mBottomToBottom != null) {
        ((ConstraintReference)localObject1).bottomToBottom(this.mBottomToBottom).margin(this.mMarginBottom).marginGone(this.mMarginBottomGone);
      } else {
        ((ConstraintReference)localObject1).bottomToBottom(State.PARENT);
      }
    }
    if (localObject2 == null) {
      return;
    }
    if (this.mBias != 0.5F) {
      ((ConstraintReference)localObject2).verticalBias(this.mBias);
    }
    switch (1.$SwitchMap$androidx$constraintlayout$core$state$State$Chain[this.mStyle.ordinal()])
    {
    default: 
      break;
    case 3: 
      ((ConstraintReference)localObject2).setVerticalChainStyle(2);
      break;
    case 2: 
      ((ConstraintReference)localObject2).setVerticalChainStyle(1);
      break;
    case 1: 
      ((ConstraintReference)localObject2).setVerticalChainStyle(0);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/state/helpers/VerticalChainReference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */