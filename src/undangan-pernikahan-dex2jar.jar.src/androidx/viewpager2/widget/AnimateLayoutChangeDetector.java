package androidx.viewpager2.widget;

import android.animation.LayoutTransition;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.Arrays;
import java.util.Comparator;

final class AnimateLayoutChangeDetector
{
  private static final ViewGroup.MarginLayoutParams ZERO_MARGIN_LAYOUT_PARAMS;
  private LinearLayoutManager mLayoutManager;
  
  static
  {
    ViewGroup.MarginLayoutParams localMarginLayoutParams = new ViewGroup.MarginLayoutParams(-1, -1);
    ZERO_MARGIN_LAYOUT_PARAMS = localMarginLayoutParams;
    localMarginLayoutParams.setMargins(0, 0, 0, 0);
  }
  
  AnimateLayoutChangeDetector(LinearLayoutManager paramLinearLayoutManager)
  {
    this.mLayoutManager = paramLinearLayoutManager;
  }
  
  private boolean arePagesLaidOutContiguously()
  {
    int m = this.mLayoutManager.getChildCount();
    if (m == 0) {
      return true;
    }
    if (this.mLayoutManager.getOrientation() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    int[][] arrayOfInt = new int[m][2];
    int j = 0;
    while (j < m)
    {
      View localView = this.mLayoutManager.getChildAt(j);
      if (localView != null)
      {
        Object localObject = localView.getLayoutParams();
        if ((localObject instanceof ViewGroup.MarginLayoutParams)) {
          localObject = (ViewGroup.MarginLayoutParams)localObject;
        } else {
          localObject = ZERO_MARGIN_LAYOUT_PARAMS;
        }
        int[] arrayOfInt1 = arrayOfInt[j];
        int k;
        if (i != 0) {
          k = localView.getLeft() - ((ViewGroup.MarginLayoutParams)localObject).leftMargin;
        } else {
          k = localView.getTop() - ((ViewGroup.MarginLayoutParams)localObject).topMargin;
        }
        arrayOfInt1[0] = k;
        arrayOfInt1 = arrayOfInt[j];
        if (i != 0) {
          k = localView.getRight() + ((ViewGroup.MarginLayoutParams)localObject).rightMargin;
        } else {
          k = localView.getBottom() + ((ViewGroup.MarginLayoutParams)localObject).bottomMargin;
        }
        arrayOfInt1[1] = k;
        j++;
      }
      else
      {
        throw new IllegalStateException("null view contained in the view hierarchy");
      }
    }
    Arrays.sort(arrayOfInt, new Comparator()
    {
      public int compare(int[] paramAnonymousArrayOfInt1, int[] paramAnonymousArrayOfInt2)
      {
        return paramAnonymousArrayOfInt1[0] - paramAnonymousArrayOfInt2[0];
      }
    });
    for (int i = 1; i < m; i++) {
      if (arrayOfInt[(i - 1)][1] != arrayOfInt[i][0]) {
        return false;
      }
    }
    j = arrayOfInt[0][1];
    i = arrayOfInt[0][0];
    return (arrayOfInt[0][0] <= 0) && (arrayOfInt[(m - 1)][1] >= j - i);
  }
  
  private boolean hasRunningChangingLayoutTransition()
  {
    int j = this.mLayoutManager.getChildCount();
    for (int i = 0; i < j; i++) {
      if (hasRunningChangingLayoutTransition(this.mLayoutManager.getChildAt(i))) {
        return true;
      }
    }
    return false;
  }
  
  private static boolean hasRunningChangingLayoutTransition(View paramView)
  {
    if ((paramView instanceof ViewGroup))
    {
      paramView = (ViewGroup)paramView;
      LayoutTransition localLayoutTransition = paramView.getLayoutTransition();
      if ((localLayoutTransition != null) && (localLayoutTransition.isChangingLayout())) {
        return true;
      }
      int j = paramView.getChildCount();
      for (int i = 0; i < j; i++) {
        if (hasRunningChangingLayoutTransition(paramView.getChildAt(i))) {
          return true;
        }
      }
    }
    return false;
  }
  
  boolean mayHaveInterferingAnimations()
  {
    boolean bool2 = arePagesLaidOutContiguously();
    boolean bool1 = true;
    if (((bool2) && (this.mLayoutManager.getChildCount() > 1)) || (!hasRunningChangingLayoutTransition())) {
      bool1 = false;
    }
    return bool1;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/viewpager2/widget/AnimateLayoutChangeDetector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */