package androidx.transition;

import android.view.ViewGroup;

public abstract class TransitionPropagation
{
  public abstract void captureValues(TransitionValues paramTransitionValues);
  
  public abstract String[] getPropagationProperties();
  
  public abstract long getStartDelay(ViewGroup paramViewGroup, Transition paramTransition, TransitionValues paramTransitionValues1, TransitionValues paramTransitionValues2);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/transition/TransitionPropagation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */