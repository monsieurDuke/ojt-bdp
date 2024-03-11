package androidx.core.view;

import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import androidx.core.R.id;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public final class ViewGroupCompat
{
  public static final int LAYOUT_MODE_CLIP_BOUNDS = 0;
  public static final int LAYOUT_MODE_OPTICAL_BOUNDS = 1;
  
  public static int getLayoutMode(ViewGroup paramViewGroup)
  {
    if (Build.VERSION.SDK_INT >= 18) {
      return Api18Impl.getLayoutMode(paramViewGroup);
    }
    return 0;
  }
  
  public static int getNestedScrollAxes(ViewGroup paramViewGroup)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return Api21Impl.getNestedScrollAxes(paramViewGroup);
    }
    if ((paramViewGroup instanceof NestedScrollingParent)) {
      return ((NestedScrollingParent)paramViewGroup).getNestedScrollAxes();
    }
    return 0;
  }
  
  public static boolean isTransitionGroup(ViewGroup paramViewGroup)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return Api21Impl.isTransitionGroup(paramViewGroup);
    }
    Boolean localBoolean = (Boolean)paramViewGroup.getTag(R.id.tag_transition_group);
    if (((localBoolean == null) || (!localBoolean.booleanValue())) && (paramViewGroup.getBackground() == null))
    {
      paramViewGroup = ViewCompat.getTransitionName(paramViewGroup);
      Log5ECF72.a(paramViewGroup);
      LogE84000.a(paramViewGroup);
      Log229316.a(paramViewGroup);
      if (paramViewGroup == null) {
        return false;
      }
    }
    boolean bool = true;
    return bool;
  }
  
  @Deprecated
  public static boolean onRequestSendAccessibilityEvent(ViewGroup paramViewGroup, View paramView, AccessibilityEvent paramAccessibilityEvent)
  {
    return paramViewGroup.onRequestSendAccessibilityEvent(paramView, paramAccessibilityEvent);
  }
  
  public static void setLayoutMode(ViewGroup paramViewGroup, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 18) {
      Api18Impl.setLayoutMode(paramViewGroup, paramInt);
    }
  }
  
  @Deprecated
  public static void setMotionEventSplittingEnabled(ViewGroup paramViewGroup, boolean paramBoolean)
  {
    paramViewGroup.setMotionEventSplittingEnabled(paramBoolean);
  }
  
  public static void setTransitionGroup(ViewGroup paramViewGroup, boolean paramBoolean)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      Api21Impl.setTransitionGroup(paramViewGroup, paramBoolean);
    } else {
      paramViewGroup.setTag(R.id.tag_transition_group, Boolean.valueOf(paramBoolean));
    }
  }
  
  static class Api18Impl
  {
    static int getLayoutMode(ViewGroup paramViewGroup)
    {
      return paramViewGroup.getLayoutMode();
    }
    
    static void setLayoutMode(ViewGroup paramViewGroup, int paramInt)
    {
      paramViewGroup.setLayoutMode(paramInt);
    }
  }
  
  static class Api21Impl
  {
    static int getNestedScrollAxes(ViewGroup paramViewGroup)
    {
      return paramViewGroup.getNestedScrollAxes();
    }
    
    static boolean isTransitionGroup(ViewGroup paramViewGroup)
    {
      return paramViewGroup.isTransitionGroup();
    }
    
    static void setTransitionGroup(ViewGroup paramViewGroup, boolean paramBoolean)
    {
      paramViewGroup.setTransitionGroup(paramBoolean);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/view/ViewGroupCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */