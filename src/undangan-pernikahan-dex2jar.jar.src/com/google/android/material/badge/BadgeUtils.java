package com.google.android.material.badge;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.View.AccessibilityDelegate;
import android.view.ViewOverlay;
import android.widget.FrameLayout;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.material.R.dimen;
import com.google.android.material.internal.ParcelableSparseArray;
import com.google.android.material.internal.ToolbarUtils;

public class BadgeUtils
{
  private static final String LOG_TAG = "BadgeUtils";
  public static final boolean USE_COMPAT_PARENT;
  
  static
  {
    boolean bool;
    if (Build.VERSION.SDK_INT < 18) {
      bool = true;
    } else {
      bool = false;
    }
    USE_COMPAT_PARENT = bool;
  }
  
  private static void attachBadgeContentDescription(final BadgeDrawable paramBadgeDrawable, View paramView)
  {
    if ((Build.VERSION.SDK_INT >= 29) && (ViewCompat.hasAccessibilityDelegate(paramView))) {
      ViewCompat.setAccessibilityDelegate(paramView, new AccessibilityDelegateCompat(paramView.getAccessibilityDelegate())
      {
        public void onInitializeAccessibilityNodeInfo(View paramAnonymousView, AccessibilityNodeInfoCompat paramAnonymousAccessibilityNodeInfoCompat)
        {
          super.onInitializeAccessibilityNodeInfo(paramAnonymousView, paramAnonymousAccessibilityNodeInfoCompat);
          paramAnonymousAccessibilityNodeInfoCompat.setContentDescription(paramBadgeDrawable.getContentDescription());
        }
      });
    } else {
      ViewCompat.setAccessibilityDelegate(paramView, new AccessibilityDelegateCompat()
      {
        public void onInitializeAccessibilityNodeInfo(View paramAnonymousView, AccessibilityNodeInfoCompat paramAnonymousAccessibilityNodeInfoCompat)
        {
          super.onInitializeAccessibilityNodeInfo(paramAnonymousView, paramAnonymousAccessibilityNodeInfoCompat);
          paramAnonymousAccessibilityNodeInfoCompat.setContentDescription(BadgeUtils.this.getContentDescription());
        }
      });
    }
  }
  
  public static void attachBadgeDrawable(BadgeDrawable paramBadgeDrawable, View paramView)
  {
    attachBadgeDrawable(paramBadgeDrawable, paramView, null);
  }
  
  public static void attachBadgeDrawable(BadgeDrawable paramBadgeDrawable, View paramView, FrameLayout paramFrameLayout)
  {
    setBadgeDrawableBounds(paramBadgeDrawable, paramView, paramFrameLayout);
    if (paramBadgeDrawable.getCustomBadgeParent() != null)
    {
      paramBadgeDrawable.getCustomBadgeParent().setForeground(paramBadgeDrawable);
    }
    else
    {
      if (USE_COMPAT_PARENT) {
        break label39;
      }
      paramView.getOverlay().add(paramBadgeDrawable);
    }
    return;
    label39:
    throw new IllegalArgumentException("Trying to reference null customBadgeParent");
  }
  
  public static void attachBadgeDrawable(BadgeDrawable paramBadgeDrawable, Toolbar paramToolbar, int paramInt)
  {
    attachBadgeDrawable(paramBadgeDrawable, paramToolbar, paramInt, null);
  }
  
  public static void attachBadgeDrawable(final BadgeDrawable paramBadgeDrawable, Toolbar paramToolbar, final int paramInt, final FrameLayout paramFrameLayout)
  {
    paramToolbar.post(new Runnable()
    {
      public void run()
      {
        ActionMenuItemView localActionMenuItemView = ToolbarUtils.getActionMenuItemView(BadgeUtils.this, paramInt);
        if (localActionMenuItemView != null)
        {
          BadgeUtils.setToolbarOffset(paramBadgeDrawable, BadgeUtils.this.getResources());
          BadgeUtils.attachBadgeDrawable(paramBadgeDrawable, localActionMenuItemView, paramFrameLayout);
          BadgeUtils.attachBadgeContentDescription(paramBadgeDrawable, localActionMenuItemView);
        }
      }
    });
  }
  
  public static SparseArray<BadgeDrawable> createBadgeDrawablesFromSavedStates(Context paramContext, ParcelableSparseArray paramParcelableSparseArray)
  {
    SparseArray localSparseArray = new SparseArray(paramParcelableSparseArray.size());
    int i = 0;
    while (i < paramParcelableSparseArray.size())
    {
      int j = paramParcelableSparseArray.keyAt(i);
      BadgeState.State localState = (BadgeState.State)paramParcelableSparseArray.valueAt(i);
      if (localState != null)
      {
        localSparseArray.put(j, BadgeDrawable.createFromSavedState(paramContext, localState));
        i++;
      }
      else
      {
        throw new IllegalArgumentException("BadgeDrawable's savedState cannot be null");
      }
    }
    return localSparseArray;
  }
  
  public static ParcelableSparseArray createParcelableBadgeStates(SparseArray<BadgeDrawable> paramSparseArray)
  {
    ParcelableSparseArray localParcelableSparseArray = new ParcelableSparseArray();
    int i = 0;
    while (i < paramSparseArray.size())
    {
      int j = paramSparseArray.keyAt(i);
      BadgeDrawable localBadgeDrawable = (BadgeDrawable)paramSparseArray.valueAt(i);
      if (localBadgeDrawable != null)
      {
        localParcelableSparseArray.put(j, localBadgeDrawable.getSavedState());
        i++;
      }
      else
      {
        throw new IllegalArgumentException("badgeDrawable cannot be null");
      }
    }
    return localParcelableSparseArray;
  }
  
  private static void detachBadgeContentDescription(View paramView)
  {
    if ((Build.VERSION.SDK_INT >= 29) && (ViewCompat.hasAccessibilityDelegate(paramView))) {
      ViewCompat.setAccessibilityDelegate(paramView, new AccessibilityDelegateCompat(paramView.getAccessibilityDelegate())
      {
        public void onInitializeAccessibilityNodeInfo(View paramAnonymousView, AccessibilityNodeInfoCompat paramAnonymousAccessibilityNodeInfoCompat)
        {
          super.onInitializeAccessibilityNodeInfo(paramAnonymousView, paramAnonymousAccessibilityNodeInfoCompat);
          paramAnonymousAccessibilityNodeInfoCompat.setContentDescription(null);
        }
      });
    } else {
      ViewCompat.setAccessibilityDelegate(paramView, null);
    }
  }
  
  public static void detachBadgeDrawable(BadgeDrawable paramBadgeDrawable, View paramView)
  {
    if (paramBadgeDrawable == null) {
      return;
    }
    if ((!USE_COMPAT_PARENT) && (paramBadgeDrawable.getCustomBadgeParent() == null)) {
      paramView.getOverlay().remove(paramBadgeDrawable);
    } else {
      paramBadgeDrawable.getCustomBadgeParent().setForeground(null);
    }
  }
  
  public static void detachBadgeDrawable(BadgeDrawable paramBadgeDrawable, Toolbar paramToolbar, int paramInt)
  {
    if (paramBadgeDrawable == null) {
      return;
    }
    paramToolbar = ToolbarUtils.getActionMenuItemView(paramToolbar, paramInt);
    if (paramToolbar != null)
    {
      removeToolbarOffset(paramBadgeDrawable);
      detachBadgeDrawable(paramBadgeDrawable, paramToolbar);
      detachBadgeContentDescription(paramToolbar);
    }
    else
    {
      Log.w("BadgeUtils", "Trying to remove badge from a null menuItemView: " + paramInt);
    }
  }
  
  static void removeToolbarOffset(BadgeDrawable paramBadgeDrawable)
  {
    paramBadgeDrawable.setAdditionalHorizontalOffset(0);
    paramBadgeDrawable.setAdditionalVerticalOffset(0);
  }
  
  public static void setBadgeDrawableBounds(BadgeDrawable paramBadgeDrawable, View paramView, FrameLayout paramFrameLayout)
  {
    Rect localRect = new Rect();
    paramView.getDrawingRect(localRect);
    paramBadgeDrawable.setBounds(localRect);
    paramBadgeDrawable.updateBadgeCoordinates(paramView, paramFrameLayout);
  }
  
  static void setToolbarOffset(BadgeDrawable paramBadgeDrawable, Resources paramResources)
  {
    paramBadgeDrawable.setAdditionalHorizontalOffset(paramResources.getDimensionPixelOffset(R.dimen.mtrl_badge_toolbar_action_menu_item_horizontal_offset));
    paramBadgeDrawable.setAdditionalVerticalOffset(paramResources.getDimensionPixelOffset(R.dimen.mtrl_badge_toolbar_action_menu_item_vertical_offset));
  }
  
  public static void updateBadgeBounds(Rect paramRect, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    paramRect.set((int)(paramFloat1 - paramFloat3), (int)(paramFloat2 - paramFloat4), (int)(paramFloat1 + paramFloat3), (int)(paramFloat2 + paramFloat4));
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/badge/BadgeUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */