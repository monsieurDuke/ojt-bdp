package androidx.transition;

import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import java.util.ArrayList;

class GhostViewHolder
  extends FrameLayout
{
  private boolean mAttached;
  private ViewGroup mParent;
  
  GhostViewHolder(ViewGroup paramViewGroup)
  {
    super(paramViewGroup.getContext());
    setClipChildren(false);
    this.mParent = paramViewGroup;
    paramViewGroup.setTag(R.id.ghost_view_holder, this);
    ViewGroupUtils.getOverlay(this.mParent).add(this);
    this.mAttached = true;
  }
  
  static GhostViewHolder getHolder(ViewGroup paramViewGroup)
  {
    return (GhostViewHolder)paramViewGroup.getTag(R.id.ghost_view_holder);
  }
  
  private int getInsertIndex(ArrayList<View> paramArrayList)
  {
    ArrayList localArrayList = new ArrayList();
    int j = 0;
    int i = getChildCount() - 1;
    while (j <= i)
    {
      int k = (j + i) / 2;
      getParents(((GhostViewPort)getChildAt(k)).mView, localArrayList);
      if (isOnTop(paramArrayList, localArrayList)) {
        j = k + 1;
      } else {
        i = k - 1;
      }
      localArrayList.clear();
    }
    return j;
  }
  
  private static void getParents(View paramView, ArrayList<View> paramArrayList)
  {
    ViewParent localViewParent = paramView.getParent();
    if ((localViewParent instanceof ViewGroup)) {
      getParents((View)localViewParent, paramArrayList);
    }
    paramArrayList.add(paramView);
  }
  
  private static boolean isOnTop(View paramView1, View paramView2)
  {
    ViewGroup localViewGroup = (ViewGroup)paramView1.getParent();
    int j = localViewGroup.getChildCount();
    boolean bool1;
    if ((Build.VERSION.SDK_INT >= 21) && (paramView1.getZ() != paramView2.getZ()))
    {
      if (paramView1.getZ() > paramView2.getZ()) {
        bool1 = true;
      } else {
        bool1 = false;
      }
      return bool1;
    }
    boolean bool2 = true;
    for (int i = 0;; i++)
    {
      bool1 = bool2;
      if (i >= j) {
        break;
      }
      View localView = localViewGroup.getChildAt(ViewGroupUtils.getChildDrawingOrder(localViewGroup, i));
      if (localView == paramView1)
      {
        bool1 = false;
        break;
      }
      if (localView == paramView2)
      {
        bool1 = true;
        break;
      }
    }
    return bool1;
  }
  
  private static boolean isOnTop(ArrayList<View> paramArrayList1, ArrayList<View> paramArrayList2)
  {
    boolean bool2 = paramArrayList1.isEmpty();
    boolean bool1 = true;
    if ((!bool2) && (!paramArrayList2.isEmpty()) && (paramArrayList1.get(0) == paramArrayList2.get(0)))
    {
      int j = Math.min(paramArrayList1.size(), paramArrayList2.size());
      for (int i = 1; i < j; i++)
      {
        View localView2 = (View)paramArrayList1.get(i);
        View localView1 = (View)paramArrayList2.get(i);
        if (localView2 != localView1) {
          return isOnTop(localView2, localView1);
        }
      }
      if (paramArrayList2.size() != j) {
        bool1 = false;
      }
      return bool1;
    }
    return true;
  }
  
  void addGhostView(GhostViewPort paramGhostViewPort)
  {
    ArrayList localArrayList = new ArrayList();
    getParents(paramGhostViewPort.mView, localArrayList);
    int i = getInsertIndex(localArrayList);
    if ((i >= 0) && (i < getChildCount())) {
      addView(paramGhostViewPort, i);
    } else {
      addView(paramGhostViewPort);
    }
  }
  
  public void onViewAdded(View paramView)
  {
    if (this.mAttached)
    {
      super.onViewAdded(paramView);
      return;
    }
    throw new IllegalStateException("This GhostViewHolder is detached!");
  }
  
  public void onViewRemoved(View paramView)
  {
    super.onViewRemoved(paramView);
    if (((getChildCount() == 1) && (getChildAt(0) == paramView)) || (getChildCount() == 0))
    {
      this.mParent.setTag(R.id.ghost_view_holder, null);
      ViewGroupUtils.getOverlay(this.mParent).remove(this);
      this.mAttached = false;
    }
  }
  
  void popToOverlayTop()
  {
    if (this.mAttached)
    {
      ViewGroupUtils.getOverlay(this.mParent).remove(this);
      ViewGroupUtils.getOverlay(this.mParent).add(this);
      return;
    }
    throw new IllegalStateException("This GhostViewHolder is detached!");
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/transition/GhostViewHolder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */