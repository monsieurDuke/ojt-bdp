package androidx.recyclerview.widget;

import android.graphics.PointF;
import android.view.View;

public class LinearSnapHelper
  extends SnapHelper
{
  private static final float INVALID_DISTANCE = 1.0F;
  private OrientationHelper mHorizontalHelper;
  private OrientationHelper mVerticalHelper;
  
  private float computeDistancePerChild(RecyclerView.LayoutManager paramLayoutManager, OrientationHelper paramOrientationHelper)
  {
    Object localObject1 = null;
    Object localObject2 = null;
    int i = Integer.MAX_VALUE;
    int k = Integer.MIN_VALUE;
    int i2 = paramLayoutManager.getChildCount();
    if (i2 == 0) {
      return 1.0F;
    }
    int m = 0;
    int j;
    while (m < i2)
    {
      View localView = paramLayoutManager.getChildAt(m);
      int n = paramLayoutManager.getPosition(localView);
      Object localObject3;
      int i1;
      if (n == -1)
      {
        localObject3 = localObject1;
        i1 = k;
      }
      else
      {
        j = i;
        if (n < i)
        {
          j = n;
          localObject1 = localView;
        }
        localObject3 = localObject1;
        i = j;
        i1 = k;
        if (n > k)
        {
          i1 = n;
          i = j;
          localObject2 = localView;
          localObject3 = localObject1;
        }
      }
      m++;
      localObject1 = localObject3;
      k = i1;
    }
    if ((localObject1 != null) && (localObject2 != null))
    {
      j = Math.min(paramOrientationHelper.getDecoratedStart((View)localObject1), paramOrientationHelper.getDecoratedStart((View)localObject2));
      j = Math.max(paramOrientationHelper.getDecoratedEnd((View)localObject1), paramOrientationHelper.getDecoratedEnd((View)localObject2)) - j;
      if (j == 0) {
        return 1.0F;
      }
      return j * 1.0F / (k - i + 1);
    }
    return 1.0F;
  }
  
  private int distanceToCenter(RecyclerView.LayoutManager paramLayoutManager, View paramView, OrientationHelper paramOrientationHelper)
  {
    return paramOrientationHelper.getDecoratedStart(paramView) + paramOrientationHelper.getDecoratedMeasurement(paramView) / 2 - (paramOrientationHelper.getStartAfterPadding() + paramOrientationHelper.getTotalSpace() / 2);
  }
  
  private int estimateNextPositionDiffForFling(RecyclerView.LayoutManager paramLayoutManager, OrientationHelper paramOrientationHelper, int paramInt1, int paramInt2)
  {
    int[] arrayOfInt = calculateScrollDistance(paramInt1, paramInt2);
    float f = computeDistancePerChild(paramLayoutManager, paramOrientationHelper);
    if (f <= 0.0F) {
      return 0;
    }
    if (Math.abs(arrayOfInt[0]) > Math.abs(arrayOfInt[1])) {
      paramInt1 = arrayOfInt[0];
    } else {
      paramInt1 = arrayOfInt[1];
    }
    return Math.round(paramInt1 / f);
  }
  
  private View findCenterView(RecyclerView.LayoutManager paramLayoutManager, OrientationHelper paramOrientationHelper)
  {
    int i2 = paramLayoutManager.getChildCount();
    if (i2 == 0) {
      return null;
    }
    Object localObject = null;
    int i1 = paramOrientationHelper.getStartAfterPadding();
    int n = paramOrientationHelper.getTotalSpace() / 2;
    int j = Integer.MAX_VALUE;
    int i = 0;
    while (i < i2)
    {
      View localView = paramLayoutManager.getChildAt(i);
      int m = Math.abs(paramOrientationHelper.getDecoratedStart(localView) + paramOrientationHelper.getDecoratedMeasurement(localView) / 2 - (i1 + n));
      int k = j;
      if (m < j)
      {
        k = m;
        localObject = localView;
      }
      i++;
      j = k;
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
  
  private OrientationHelper getVerticalHelper(RecyclerView.LayoutManager paramLayoutManager)
  {
    OrientationHelper localOrientationHelper = this.mVerticalHelper;
    if ((localOrientationHelper == null) || (localOrientationHelper.mLayoutManager != paramLayoutManager)) {
      this.mVerticalHelper = OrientationHelper.createVerticalHelper(paramLayoutManager);
    }
    return this.mVerticalHelper;
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
    if (!(paramLayoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)) {
      return -1;
    }
    int j = paramLayoutManager.getItemCount();
    if (j == 0) {
      return -1;
    }
    Object localObject = findSnapView(paramLayoutManager);
    if (localObject == null) {
      return -1;
    }
    int k = paramLayoutManager.getPosition((View)localObject);
    if (k == -1) {
      return -1;
    }
    localObject = ((RecyclerView.SmoothScroller.ScrollVectorProvider)paramLayoutManager).computeScrollVectorForPosition(j - 1);
    if (localObject == null) {
      return -1;
    }
    int i;
    if (paramLayoutManager.canScrollHorizontally())
    {
      i = estimateNextPositionDiffForFling(paramLayoutManager, getHorizontalHelper(paramLayoutManager), paramInt1, 0);
      paramInt1 = i;
      if (((PointF)localObject).x < 0.0F) {
        paramInt1 = -i;
      }
    }
    else
    {
      paramInt1 = 0;
    }
    if (paramLayoutManager.canScrollVertically())
    {
      i = estimateNextPositionDiffForFling(paramLayoutManager, getVerticalHelper(paramLayoutManager), 0, paramInt2);
      paramInt2 = i;
      if (((PointF)localObject).y < 0.0F) {
        paramInt2 = -i;
      }
    }
    else
    {
      paramInt2 = 0;
    }
    if (paramLayoutManager.canScrollVertically()) {
      paramInt1 = paramInt2;
    }
    if (paramInt1 == 0) {
      return -1;
    }
    paramInt2 = k + paramInt1;
    paramInt1 = paramInt2;
    if (paramInt2 < 0) {
      paramInt1 = 0;
    }
    paramInt2 = paramInt1;
    if (paramInt1 >= j) {
      paramInt2 = j - 1;
    }
    return paramInt2;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/recyclerview/widget/LinearSnapHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */