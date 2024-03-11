package com.google.android.material.bottomsheet;

import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.res.ColorStateList;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.material.R.attr;
import com.google.android.material.R.id;
import com.google.android.material.R.layout;
import com.google.android.material.R.style;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.shape.MaterialShapeDrawable;

public class BottomSheetDialog
  extends AppCompatDialog
{
  private BottomSheetBehavior<FrameLayout> behavior;
  private FrameLayout bottomSheet;
  private BottomSheetBehavior.BottomSheetCallback bottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback()
  {
    public void onSlide(View paramAnonymousView, float paramAnonymousFloat) {}
    
    public void onStateChanged(View paramAnonymousView, int paramAnonymousInt)
    {
      if (paramAnonymousInt == 5) {
        BottomSheetDialog.this.cancel();
      }
    }
  };
  boolean cancelable = true;
  private boolean canceledOnTouchOutside = true;
  private boolean canceledOnTouchOutsideSet;
  private FrameLayout container;
  private CoordinatorLayout coordinator;
  boolean dismissWithAnimation;
  private BottomSheetBehavior.BottomSheetCallback edgeToEdgeCallback;
  private boolean edgeToEdgeEnabled;
  
  public BottomSheetDialog(Context paramContext)
  {
    this(paramContext, 0);
    this.edgeToEdgeEnabled = getContext().getTheme().obtainStyledAttributes(new int[] { R.attr.enableEdgeToEdge }).getBoolean(0, false);
  }
  
  public BottomSheetDialog(Context paramContext, int paramInt)
  {
    super(paramContext, getThemeResId(paramContext, paramInt));
    supportRequestWindowFeature(1);
    this.edgeToEdgeEnabled = getContext().getTheme().obtainStyledAttributes(new int[] { R.attr.enableEdgeToEdge }).getBoolean(0, false);
  }
  
  protected BottomSheetDialog(Context paramContext, boolean paramBoolean, DialogInterface.OnCancelListener paramOnCancelListener)
  {
    super(paramContext, paramBoolean, paramOnCancelListener);
    supportRequestWindowFeature(1);
    this.cancelable = paramBoolean;
    this.edgeToEdgeEnabled = getContext().getTheme().obtainStyledAttributes(new int[] { R.attr.enableEdgeToEdge }).getBoolean(0, false);
  }
  
  private FrameLayout ensureContainerAndBehavior()
  {
    if (this.container == null)
    {
      Object localObject = (FrameLayout)View.inflate(getContext(), R.layout.design_bottom_sheet_dialog, null);
      this.container = ((FrameLayout)localObject);
      this.coordinator = ((CoordinatorLayout)((FrameLayout)localObject).findViewById(R.id.coordinator));
      localObject = (FrameLayout)this.container.findViewById(R.id.design_bottom_sheet);
      this.bottomSheet = ((FrameLayout)localObject);
      localObject = BottomSheetBehavior.from((View)localObject);
      this.behavior = ((BottomSheetBehavior)localObject);
      ((BottomSheetBehavior)localObject).addBottomSheetCallback(this.bottomSheetCallback);
      this.behavior.setHideable(this.cancelable);
    }
    return this.container;
  }
  
  private static int getThemeResId(Context paramContext, int paramInt)
  {
    int i = paramInt;
    if (paramInt == 0)
    {
      TypedValue localTypedValue = new TypedValue();
      if (paramContext.getTheme().resolveAttribute(R.attr.bottomSheetDialogTheme, localTypedValue, true)) {
        i = localTypedValue.resourceId;
      } else {
        i = R.style.Theme_Design_Light_BottomSheetDialog;
      }
    }
    return i;
  }
  
  public static void setLightStatusBar(View paramView, boolean paramBoolean)
  {
    if (Build.VERSION.SDK_INT >= 23)
    {
      int i = paramView.getSystemUiVisibility();
      if (paramBoolean) {
        i |= 0x2000;
      } else {
        i &= 0xDFFF;
      }
      paramView.setSystemUiVisibility(i);
    }
  }
  
  private View wrapInBottomSheet(int paramInt, View paramView, ViewGroup.LayoutParams paramLayoutParams)
  {
    ensureContainerAndBehavior();
    CoordinatorLayout localCoordinatorLayout = (CoordinatorLayout)this.container.findViewById(R.id.coordinator);
    View localView = paramView;
    if (paramInt != 0)
    {
      localView = paramView;
      if (paramView == null) {
        localView = getLayoutInflater().inflate(paramInt, localCoordinatorLayout, false);
      }
    }
    if (this.edgeToEdgeEnabled) {
      ViewCompat.setOnApplyWindowInsetsListener(this.bottomSheet, new OnApplyWindowInsetsListener()
      {
        public WindowInsetsCompat onApplyWindowInsets(View paramAnonymousView, WindowInsetsCompat paramAnonymousWindowInsetsCompat)
        {
          if (BottomSheetDialog.this.edgeToEdgeCallback != null) {
            BottomSheetDialog.this.behavior.removeBottomSheetCallback(BottomSheetDialog.this.edgeToEdgeCallback);
          }
          if (paramAnonymousWindowInsetsCompat != null)
          {
            BottomSheetDialog.access$002(BottomSheetDialog.this, new BottomSheetDialog.EdgeToEdgeCallback(BottomSheetDialog.this.bottomSheet, paramAnonymousWindowInsetsCompat, null));
            BottomSheetDialog.this.behavior.addBottomSheetCallback(BottomSheetDialog.this.edgeToEdgeCallback);
          }
          return paramAnonymousWindowInsetsCompat;
        }
      });
    }
    this.bottomSheet.removeAllViews();
    if (paramLayoutParams == null) {
      this.bottomSheet.addView(localView);
    } else {
      this.bottomSheet.addView(localView, paramLayoutParams);
    }
    localCoordinatorLayout.findViewById(R.id.touch_outside).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if ((BottomSheetDialog.this.cancelable) && (BottomSheetDialog.this.isShowing()) && (BottomSheetDialog.this.shouldWindowCloseOnTouchOutside())) {
          BottomSheetDialog.this.cancel();
        }
      }
    });
    ViewCompat.setAccessibilityDelegate(this.bottomSheet, new AccessibilityDelegateCompat()
    {
      public void onInitializeAccessibilityNodeInfo(View paramAnonymousView, AccessibilityNodeInfoCompat paramAnonymousAccessibilityNodeInfoCompat)
      {
        super.onInitializeAccessibilityNodeInfo(paramAnonymousView, paramAnonymousAccessibilityNodeInfoCompat);
        if (BottomSheetDialog.this.cancelable)
        {
          paramAnonymousAccessibilityNodeInfoCompat.addAction(1048576);
          paramAnonymousAccessibilityNodeInfoCompat.setDismissable(true);
        }
        else
        {
          paramAnonymousAccessibilityNodeInfoCompat.setDismissable(false);
        }
      }
      
      public boolean performAccessibilityAction(View paramAnonymousView, int paramAnonymousInt, Bundle paramAnonymousBundle)
      {
        if ((paramAnonymousInt == 1048576) && (BottomSheetDialog.this.cancelable))
        {
          BottomSheetDialog.this.cancel();
          return true;
        }
        return super.performAccessibilityAction(paramAnonymousView, paramAnonymousInt, paramAnonymousBundle);
      }
    });
    this.bottomSheet.setOnTouchListener(new View.OnTouchListener()
    {
      public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
      {
        return true;
      }
    });
    return this.container;
  }
  
  public void cancel()
  {
    BottomSheetBehavior localBottomSheetBehavior = getBehavior();
    if ((this.dismissWithAnimation) && (localBottomSheetBehavior.getState() != 5)) {
      localBottomSheetBehavior.setState(5);
    } else {
      super.cancel();
    }
  }
  
  public BottomSheetBehavior<FrameLayout> getBehavior()
  {
    if (this.behavior == null) {
      ensureContainerAndBehavior();
    }
    return this.behavior;
  }
  
  public boolean getDismissWithAnimation()
  {
    return this.dismissWithAnimation;
  }
  
  public boolean getEdgeToEdgeEnabled()
  {
    return this.edgeToEdgeEnabled;
  }
  
  public void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    Window localWindow = getWindow();
    if ((localWindow != null) && (Build.VERSION.SDK_INT >= 21))
    {
      boolean bool1 = this.edgeToEdgeEnabled;
      boolean bool2 = true;
      int i;
      if ((bool1) && (Color.alpha(localWindow.getNavigationBarColor()) < 255)) {
        i = 1;
      } else {
        i = 0;
      }
      Object localObject = this.container;
      if (localObject != null)
      {
        if (i == 0) {
          bool1 = true;
        } else {
          bool1 = false;
        }
        ((FrameLayout)localObject).setFitsSystemWindows(bool1);
      }
      localObject = this.coordinator;
      if (localObject != null)
      {
        if (i == 0) {
          bool1 = bool2;
        } else {
          bool1 = false;
        }
        ((CoordinatorLayout)localObject).setFitsSystemWindows(bool1);
      }
      if (i != 0) {
        localWindow.getDecorView().setSystemUiVisibility(768);
      }
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    paramBundle = getWindow();
    if (paramBundle != null)
    {
      if (Build.VERSION.SDK_INT >= 21)
      {
        paramBundle.setStatusBarColor(0);
        paramBundle.addFlags(Integer.MIN_VALUE);
        if (Build.VERSION.SDK_INT < 23) {
          paramBundle.addFlags(67108864);
        }
      }
      paramBundle.setLayout(-1, -1);
    }
  }
  
  protected void onStart()
  {
    super.onStart();
    BottomSheetBehavior localBottomSheetBehavior = this.behavior;
    if ((localBottomSheetBehavior != null) && (localBottomSheetBehavior.getState() == 5)) {
      this.behavior.setState(4);
    }
  }
  
  void removeDefaultCallback()
  {
    this.behavior.removeBottomSheetCallback(this.bottomSheetCallback);
  }
  
  public void setCancelable(boolean paramBoolean)
  {
    super.setCancelable(paramBoolean);
    if (this.cancelable != paramBoolean)
    {
      this.cancelable = paramBoolean;
      BottomSheetBehavior localBottomSheetBehavior = this.behavior;
      if (localBottomSheetBehavior != null) {
        localBottomSheetBehavior.setHideable(paramBoolean);
      }
    }
  }
  
  public void setCanceledOnTouchOutside(boolean paramBoolean)
  {
    super.setCanceledOnTouchOutside(paramBoolean);
    if ((paramBoolean) && (!this.cancelable)) {
      this.cancelable = true;
    }
    this.canceledOnTouchOutside = paramBoolean;
    this.canceledOnTouchOutsideSet = true;
  }
  
  public void setContentView(int paramInt)
  {
    super.setContentView(wrapInBottomSheet(paramInt, null, null));
  }
  
  public void setContentView(View paramView)
  {
    super.setContentView(wrapInBottomSheet(0, paramView, null));
  }
  
  public void setContentView(View paramView, ViewGroup.LayoutParams paramLayoutParams)
  {
    super.setContentView(wrapInBottomSheet(0, paramView, paramLayoutParams));
  }
  
  public void setDismissWithAnimation(boolean paramBoolean)
  {
    this.dismissWithAnimation = paramBoolean;
  }
  
  boolean shouldWindowCloseOnTouchOutside()
  {
    if (!this.canceledOnTouchOutsideSet)
    {
      TypedArray localTypedArray = getContext().obtainStyledAttributes(new int[] { 16843611 });
      this.canceledOnTouchOutside = localTypedArray.getBoolean(0, true);
      localTypedArray.recycle();
      this.canceledOnTouchOutsideSet = true;
    }
    return this.canceledOnTouchOutside;
  }
  
  private static class EdgeToEdgeCallback
    extends BottomSheetBehavior.BottomSheetCallback
  {
    private final WindowInsetsCompat insetsCompat;
    private final boolean lightBottomSheet;
    private final boolean lightStatusBar;
    
    private EdgeToEdgeCallback(View paramView, WindowInsetsCompat paramWindowInsetsCompat)
    {
      this.insetsCompat = paramWindowInsetsCompat;
      boolean bool;
      if ((Build.VERSION.SDK_INT >= 23) && ((paramView.getSystemUiVisibility() & 0x2000) != 0)) {
        bool = true;
      } else {
        bool = false;
      }
      this.lightStatusBar = bool;
      paramWindowInsetsCompat = BottomSheetBehavior.from(paramView).getMaterialShapeDrawable();
      if (paramWindowInsetsCompat != null) {
        paramWindowInsetsCompat = paramWindowInsetsCompat.getFillColor();
      } else {
        paramWindowInsetsCompat = ViewCompat.getBackgroundTintList(paramView);
      }
      if (paramWindowInsetsCompat != null) {
        this.lightBottomSheet = MaterialColors.isColorLight(paramWindowInsetsCompat.getDefaultColor());
      } else if ((paramView.getBackground() instanceof ColorDrawable)) {
        this.lightBottomSheet = MaterialColors.isColorLight(((ColorDrawable)paramView.getBackground()).getColor());
      } else {
        this.lightBottomSheet = bool;
      }
    }
    
    private void setPaddingForPosition(View paramView)
    {
      if (paramView.getTop() < this.insetsCompat.getSystemWindowInsetTop())
      {
        BottomSheetDialog.setLightStatusBar(paramView, this.lightBottomSheet);
        paramView.setPadding(paramView.getPaddingLeft(), this.insetsCompat.getSystemWindowInsetTop() - paramView.getTop(), paramView.getPaddingRight(), paramView.getPaddingBottom());
      }
      else if (paramView.getTop() != 0)
      {
        BottomSheetDialog.setLightStatusBar(paramView, this.lightStatusBar);
        paramView.setPadding(paramView.getPaddingLeft(), 0, paramView.getPaddingRight(), paramView.getPaddingBottom());
      }
    }
    
    void onLayout(View paramView)
    {
      setPaddingForPosition(paramView);
    }
    
    public void onSlide(View paramView, float paramFloat)
    {
      setPaddingForPosition(paramView);
    }
    
    public void onStateChanged(View paramView, int paramInt)
    {
      setPaddingForPosition(paramView);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/bottomsheet/BottomSheetDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */