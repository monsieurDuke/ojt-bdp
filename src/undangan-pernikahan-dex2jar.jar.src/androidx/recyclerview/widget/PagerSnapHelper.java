package androidx.recyclerview.widget;

import android.content.Context;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.view.View;

public class PagerSnapHelper
  extends SnapHelper
{
  private static final int MAX_SCROLL_ON_FLING_DURATION = 100;
  private OrientationHelper mHorizontalHelper;
  private OrientationHelper mVerticalHelper;
  
  private int distanceToCenter(RecyclerView.LayoutManager paramLayoutManager, View paramView, OrientationHelper paramOrientationHelper)
  {
    return paramOrientationHelper.getDecoratedStart(paramView) + paramOrientationHelper.getDecoratedMeasurement(paramView) / 2 - (paramOrientationHelper.getStartAfterPadding() + paramOrientationHelper.getTotalSpace() / 2);
  }
  
  private View findCenterView(RecyclerView.LayoutManager paramLayoutManager, OrientationHelper paramOrientationHelper)
  {
    int n = paramLayoutManager.getChildCount();
    if (n == 0) {
      return null;
    }
    Object localObject = null;
    int i1 = paramOrientationHelper.getStartAfterPadding();
    int i2 = paramOrientationHelper.getTotalSpace() / 2;
    int k = Integer.MAX_VALUE;
    int i = 0;
    while (i < n)
    {
      View localView = paramLayoutManager.getChildAt(i);
      int m = Math.abs(paramOrientationHelper.getDecoratedStart(localView) + paramOrientationHelper.getDecoratedMeasurement(localView) / 2 - (i1 + i2));
      int j = k;
      if (m < k)
      {
        j = m;
        localObject = localView;
      }
      i++;
      k = j;
    }
    return (View)localObject;
  }
  
  private OrientationHelper getHorizontalHelper(RecyclerView.LayoutManager paramLayoutManager)
  {
    OrientationHelper localOrientationHelper = this.mHorizontalHelper;
    if ((localOrientationHelper == null) || (localOrientationHelper.mLayoutManager != paramLayoutManager)) {
      this.mHorizontalHelper = OrientationHelper.createHorizontalHelper(paramLayoutManager);
    }
    return this.mHorizontalHelper;
  }
  
  private OrientationHelper getOrientationHelper(RecyclerView.LayoutManager paramLayoutManager)
  {
    if (paramLayoutManager.canScrollVertically()) {
      return getVerticalHelper(paramLayoutManager);
    }
    if (paramLayoutManager.canScrollHorizontally()) {
      return getHorizontalHelper(paramLayoutManager);
    }
    return null;
  }
  
  private OrientationHelper getVerticalHelper(RecyclerView.LayoutManager paramLayoutManager)
  {
    OrientationHelper localOrientationHelper = this.mVerticalHelper;
    if ((localOrientationHelper == null) || (localOrientationHelper.mLayoutManager != paramLayoutManager)) {
      this.mVerticalHelper = OrientationHelper.createVerticalHelper(paramLayoutManager);
    }
    return this.mVerticalHelper;
  }
  
  private boolean isForwardFling(RecyclerView.LayoutManager paramLayoutManager, int paramInt1, int paramInt2)
  {
    boolean bool3 = paramLayoutManager.canScrollHorizontally();
    boolean bool1 = true;
    boolean bool2 = true;
    if (bool3)
    {
      if (paramInt1 > 0) {
        bool1 = bool2;
      } else {
        bool1 = false;
      }
      return bool1;
    }
    if (paramInt2 <= 0) {
      bool1 = false;
    }
    return bool1;
  }
  
  private boolean isReverseLayout(RecyclerView.LayoutManager paramLayoutManager)
  {
    int i = paramLayoutManager.getItemCount();
    boolean bool2 = paramLayoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider;
    boolean bool1 = false;
    if (bool2)
    {
      paramLayoutManager = ((RecyclerView.SmoothScroller.ScrollVectorProvider)paramLayoutManager).computeScrollVectorForPosition(i - 1);
      if (paramLayoutManager != null)
      {
        if ((paramLayoutManager.x < 0.0F) || (paramLayoutManager.y < 0.0F)) {
          bool1 = true;
        }
        return bool1;
      }
    }
    return false;
  }
  
  public int[] calculateDistanceToFinalSnap(RecyclerView.LayoutManager paramLayoutManager, View paramView)
  {
    int[] arrayOfInt = new int[2];
    if (paramLayoutManager.canScrollHorizontally()) {
      arrayOfInt[0] = distanceToCenter(paramLayoutManager, paramView, getHorizontalHelper(paramLayoutManager));
    } else {
      arrayOfInt[0] = 0;
    }
    if (paramLayoutManager.canScrollVertically()) {
      arrayOfInt[1] = distanceToCenter(paramLayoutManager, paramView, getVerticalHelper(paramLayoutManager));
    } else {
      arrayOfInt[1] = 0;
    }
    return arrayOfInt;
  }
  
  protected LinearSmoothScroller createSnapScroller(RecyclerView.LayoutManager paramLayoutManager)
  {
    if (!(paramLayoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)) {
      return null;
    }
    new LinearSmoothScroller(this.mRecyclerView.getContext())
    {
      protected float calculateSpeedPerPixel(DisplayMetrics paramAnonymousDisplayMetrics)
      {
        return 100.0F / paramAnonymousDisplayMetrics.densityDpi;
      }
      
      protected int calculateTimeForScrolling(int paramAnonymousInt)
      {
        return Math.min(100, super.calculateTimeForScrolling(paramAnonymousInt));
      }
      
      protected void onTargetFound(View paramAnonymousView, RecyclerView.State paramAnonymousState, RecyclerView.SmoothScroller.Action paramAnonymousAction)
      {
        paramAnonymousState = PagerSnapHelper.this;
        paramAnonymousView = paramAnonymousState.calculateDistanceToFinalSnap(paramAnonymousState.mRecyclerView.getLayoutManager(), paramAnonymousView);
        int j = paramAnonymousView[0];
        int k = paramAnonymousView[1];
        int i = calculateTimeForDeceleration(Math.max(Math.abs(j), Math.abs(k)));
        if (i > 0) {
          paramAnonymousAction.update(j, k, i, this.mDecelerateInterpolator);
        }
      }
    };
  }
  
  public View findSnapView(RecyclerView.LayoutManager paramLayoutManager)
  {
    if (paramLayoutManager.canScrollVertically()) {
      return findCenterView(paramLayoutManager, getVerticalHelper(paramLayoutManager));
    }
    if (paramLayoutManager.canScrollHorizontally()) {
      return findCenterView(paramLayoutManager, getHorizontalHelper(paramLayoutManager));
    }
    return null;
  }
  
  public int findTargetSnapPosition(RecyclerView.LayoutManager paramLayoutManager, int paramInt1, int paramInt2)
  {
    int i2 = paramLayoutManager.getItemCount();
    if (i2 == 0) {
      return -1;
    }
    OrientationHelper localOrientationHelper = getOrientationHelper(paramLayoutManager);
    if (localOrientationHelper == null) {
      return -1;
    }
    Object localObject1 = null;
    int j = Integer.MIN_VALUE;
    Object localObject2 = null;
    int m = Integer.MAX_VALUE;
    int i3 = paramLayoutManager.getChildCount();
    int k = 0;
    while (k < i3)
    {
      View localView = paramLayoutManager.getChildAt(k);
      Object localObject4;
      int n;
      if (localView == null)
      {
        localObject4 = localObject2;
        n = m;
      }
      else
      {
        int i1 = distanceToCenter(paramLayoutManager, localView, localOrientationHelper);
        Object localObject3 = localObject1;
        int i = j;
        if (i1 <= 0)
        {
          localObject3 = localObject1;
          i = j;
          if (i1 > j)
          {
            i = i1;
            localObject3 = localView;
          }
        }
        localObject1 = localObject3;
        j = i;
        localObject4 = localObject2;
        n = m;
        if (i1 >= 0)
        {
          localObject1 = localObject3;
          j = i;
          localObject4 = localObject2;
          n = m;
          if (i1 < m)
          {
            n = i1;
            localObject4 = localView;
            j = i;
            localObject1 = localObject3;
          }
        }
      }
      k++;
      localObject2 = localObject4;
      m = n;
    }
    boolean bool = isForwardFling(paramLayoutManager, paramInt1, paramInt2);
    if ((bool) && (localObject2 != null)) {
      return paramLayoutManager.getPosition((View)localObject2);
    }
    if ((!bool) && (localObject1 != null)) {
      return paramLayoutManager.getPosition((View)localObject1);
    }
    if (!bool) {
      localObject1 = localObject2;
    }
    if (localObject1 == null) {
      return -1;
    }
    paramInt2 = paramLayoutManager.getPosition((View)localObject1);
    if (isReverseLayout(paramLayoutManager) == bool) {
      paramInt1 = -1;
    } else {
      paramInt1 = 1;
    }
    paramInt1 += paramInt2;
    if ((paramInt1 >= 0) && (paramInt1 < i2)) {
      return paramInt1;
    }
    return -1;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/recyclerview/widget/PagerSnapHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */