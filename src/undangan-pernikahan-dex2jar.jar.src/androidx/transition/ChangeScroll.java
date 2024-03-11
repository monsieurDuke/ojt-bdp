package androidx.transition;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import java.util.Map;

public class ChangeScroll
  extends Transition
{
  private static final String[] PROPERTIES = { "android:changeScroll:x", "android:changeScroll:y" };
  private static final String PROPNAME_SCROLL_X = "android:changeScroll:x";
  private static final String PROPNAME_SCROLL_Y = "android:changeScroll:y";
  
  public ChangeScroll() {}
  
  public ChangeScroll(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  private void captureValues(TransitionValues paramTransitionValues)
  {
    paramTransitionValues.values.put("android:changeScroll:x", Integer.valueOf(paramTransitionValues.view.getScrollX()));
    paramTransitionValues.values.put("android:changeScroll:y", Integer.valueOf(paramTransitionValues.view.getScrollY()));
  }
  
  public void captureEndValues(TransitionValues paramTransitionValues)
  {
    captureValues(paramTransitionValues);
  }
  
  public void captureStartValues(TransitionValues paramTransitionValues)
  {
    captureValues(paramTransitionValues);
  }
  
  public Animator createAnimator(ViewGroup paramViewGroup, TransitionValues paramTransitionValues1, TransitionValues paramTransitionValues2)
  {
    if ((paramTransitionValues1 != null) && (paramTransitionValues2 != null))
    {
      View localView = paramTransitionValues2.view;
      int m = ((Integer)paramTransitionValues1.values.get("android:changeScroll:x")).intValue();
      int j = ((Integer)paramTransitionValues2.values.get("android:changeScroll:x")).intValue();
      int k = ((Integer)paramTransitionValues1.values.get("android:changeScroll:y")).intValue();
      int i = ((Integer)paramTransitionValues2.values.get("android:changeScroll:y")).intValue();
      paramViewGroup = null;
      paramTransitionValues1 = null;
      if (m != j)
      {
        localView.setScrollX(m);
        paramViewGroup = ObjectAnimator.ofInt(localView, "scrollX", new int[] { m, j });
      }
      if (k != i)
      {
        localView.setScrollY(k);
        paramTransitionValues1 = ObjectAnimator.ofInt(localView, "scrollY", new int[] { k, i });
      }
      return TransitionUtils.mergeAnimators(paramViewGroup, paramTransitionValues1);
    }
    return null;
  }
  
  public String[] getTransitionProperties()
  {
    return PROPERTIES;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/transition/ChangeScroll.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */