package com.google.android.material.internal;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.ColorDrawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.InputMethodManager;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.R.styleable;

public class ViewUtils
{
  public static void addOnGlobalLayoutListener(View paramView, ViewTreeObserver.OnGlobalLayoutListener paramOnGlobalLayoutListener)
  {
    if (paramView != null) {
      paramView.getViewTreeObserver().addOnGlobalLayoutListener(paramOnGlobalLayoutListener);
    }
  }
  
  public static void doOnApplyWindowInsets(View paramView, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    doOnApplyWindowInsets(paramView, paramAttributeSet, paramInt1, paramInt2, null);
  }
  
  public static void doOnApplyWindowInsets(View paramView, AttributeSet paramAttributeSet, int paramInt1, int paramInt2, final OnApplyWindowInsetsListener paramOnApplyWindowInsetsListener)
  {
    paramAttributeSet = paramView.getContext().obtainStyledAttributes(paramAttributeSet, R.styleable.Insets, paramInt1, paramInt2);
    boolean bool1 = paramAttributeSet.getBoolean(R.styleable.Insets_paddingBottomSystemWindowInsets, false);
    final boolean bool3 = paramAttributeSet.getBoolean(R.styleable.Insets_paddingLeftSystemWindowInsets, false);
    final boolean bool2 = paramAttributeSet.getBoolean(R.styleable.Insets_paddingRightSystemWindowInsets, false);
    paramAttributeSet.recycle();
    doOnApplyWindowInsets(paramView, new OnApplyWindowInsetsListener()
    {
      final boolean val$paddingBottomSystemWindowInsets;
      
      public WindowInsetsCompat onApplyWindowInsets(View paramAnonymousView, WindowInsetsCompat paramAnonymousWindowInsetsCompat, ViewUtils.RelativePadding paramAnonymousRelativePadding)
      {
        if (this.val$paddingBottomSystemWindowInsets) {
          paramAnonymousRelativePadding.bottom += paramAnonymousWindowInsetsCompat.getSystemWindowInsetBottom();
        }
        boolean bool = ViewUtils.isLayoutRtl(paramAnonymousView);
        if (bool3) {
          if (bool) {
            paramAnonymousRelativePadding.end += paramAnonymousWindowInsetsCompat.getSystemWindowInsetLeft();
          } else {
            paramAnonymousRelativePadding.start += paramAnonymousWindowInsetsCompat.getSystemWindowInsetLeft();
          }
        }
        if (bool2) {
          if (bool) {
            paramAnonymousRelativePadding.start += paramAnonymousWindowInsetsCompat.getSystemWindowInsetRight();
          } else {
            paramAnonymousRelativePadding.end += paramAnonymousWindowInsetsCompat.getSystemWindowInsetRight();
          }
        }
        paramAnonymousRelativePadding.applyToView(paramAnonymousView);
        ViewUtils.OnApplyWindowInsetsListener localOnApplyWindowInsetsListener = paramOnApplyWindowInsetsListener;
        if (localOnApplyWindowInsetsListener != null) {
          paramAnonymousWindowInsetsCompat = localOnApplyWindowInsetsListener.onApplyWindowInsets(paramAnonymousView, paramAnonymousWindowInsetsCompat, paramAnonymousRelativePadding);
        }
        return paramAnonymousWindowInsetsCompat;
      }
    });
  }
  
  public static void doOnApplyWindowInsets(View paramView, OnApplyWindowInsetsListener paramOnApplyWindowInsetsListener)
  {
    ViewCompat.setOnApplyWindowInsetsListener(paramView, new OnApplyWindowInsetsListener()
    {
      public WindowInsetsCompat onApplyWindowInsets(View paramAnonymousView, WindowInsetsCompat paramAnonymousWindowInsetsCompat)
      {
        return ViewUtils.this.onApplyWindowInsets(paramAnonymousView, paramAnonymousWindowInsetsCompat, new ViewUtils.RelativePadding(this.val$initialPadding));
      }
    });
    requestApplyInsetsWhenAttached(paramView);
  }
  
  public static float dpToPx(Context paramContext, int paramInt)
  {
    paramContext = paramContext.getResources();
    return TypedValue.applyDimension(1, paramInt, paramContext.getDisplayMetrics());
  }
  
  public static Integer getBackgroundColor(View paramView)
  {
    if ((paramView.getBackground() instanceof ColorDrawable)) {
      paramView = Integer.valueOf(((ColorDrawable)paramView.getBackground()).getColor());
    } else {
      paramView = null;
    }
    return paramView;
  }
  
  public static ViewGroup getContentView(View paramView)
  {
    if (paramView == null) {
      return null;
    }
    View localView = paramView.getRootView();
    ViewGroup localViewGroup = (ViewGroup)localView.findViewById(16908290);
    if (localViewGroup != null) {
      return localViewGroup;
    }
    if ((localView != paramView) && ((localView instanceof ViewGroup))) {
      return (ViewGroup)localView;
    }
    return null;
  }
  
  public static ViewOverlayImpl getContentViewOverlay(View paramView)
  {
    return getOverlay(getContentView(paramView));
  }
  
  public static ViewOverlayImpl getOverlay(View paramView)
  {
    if (paramView == null) {
      return null;
    }
    if (Build.VERSION.SDK_INT >= 18) {
      return new ViewOverlayApi18(paramView);
    }
    return ViewOverlayApi14.createFrom(paramView);
  }
  
  public static float getParentAbsoluteElevation(View paramView)
  {
    float f = 0.0F;
    for (paramView = paramView.getParent(); (paramView instanceof View); paramView = paramView.getParent()) {
      f += ViewCompat.getElevation((View)paramView);
    }
    return f;
  }
  
  public static boolean isLayoutRtl(View paramView)
  {
    int i = ViewCompat.getLayoutDirection(paramView);
    boolean bool = true;
    if (i != 1) {
      bool = false;
    }
    return bool;
  }
  
  public static PorterDuff.Mode parseTintMode(int paramInt, PorterDuff.Mode paramMode)
  {
    switch (paramInt)
    {
    default: 
      return paramMode;
    case 16: 
      return PorterDuff.Mode.ADD;
    case 15: 
      return PorterDuff.Mode.SCREEN;
    case 14: 
      return PorterDuff.Mode.MULTIPLY;
    case 9: 
      return PorterDuff.Mode.SRC_ATOP;
    case 5: 
      return PorterDuff.Mode.SRC_IN;
    }
    return PorterDuff.Mode.SRC_OVER;
  }
  
  public static void removeOnGlobalLayoutListener(View paramView, ViewTreeObserver.OnGlobalLayoutListener paramOnGlobalLayoutListener)
  {
    if (paramView != null) {
      removeOnGlobalLayoutListener(paramView.getViewTreeObserver(), paramOnGlobalLayoutListener);
    }
  }
  
  public static void removeOnGlobalLayoutListener(ViewTreeObserver paramViewTreeObserver, ViewTreeObserver.OnGlobalLayoutListener paramOnGlobalLayoutListener)
  {
    if (Build.VERSION.SDK_INT >= 16) {
      paramViewTreeObserver.removeOnGlobalLayoutListener(paramOnGlobalLayoutListener);
    } else {
      paramViewTreeObserver.removeGlobalOnLayoutListener(paramOnGlobalLayoutListener);
    }
  }
  
  public static void requestApplyInsetsWhenAttached(View paramView)
  {
    if (ViewCompat.isAttachedToWindow(paramView)) {
      ViewCompat.requestApplyInsets(paramView);
    } else {
      paramView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener()
      {
        public void onViewAttachedToWindow(View paramAnonymousView)
        {
          paramAnonymousView.removeOnAttachStateChangeListener(this);
          ViewCompat.requestApplyInsets(paramAnonymousView);
        }
        
        public void onViewDetachedFromWindow(View paramAnonymousView) {}
      });
    }
  }
  
  public static void requestFocusAndShowKeyboard(View paramView)
  {
    paramView.requestFocus();
    paramView.post(new Runnable()
    {
      public void run()
      {
        ((InputMethodManager)ViewUtils.this.getContext().getSystemService("input_method")).showSoftInput(ViewUtils.this, 1);
      }
    });
  }
  
  public static abstract interface OnApplyWindowInsetsListener
  {
    public abstract WindowInsetsCompat onApplyWindowInsets(View paramView, WindowInsetsCompat paramWindowInsetsCompat, ViewUtils.RelativePadding paramRelativePadding);
  }
  
  public static class RelativePadding
  {
    public int bottom;
    public int end;
    public int start;
    public int top;
    
    public RelativePadding(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      this.start = paramInt1;
      this.top = paramInt2;
      this.end = paramInt3;
      this.bottom = paramInt4;
    }
    
    public RelativePadding(RelativePadding paramRelativePadding)
    {
      this.start = paramRelativePadding.start;
      this.top = paramRelativePadding.top;
      this.end = paramRelativePadding.end;
      this.bottom = paramRelativePadding.bottom;
    }
    
    public void applyToView(View paramView)
    {
      ViewCompat.setPaddingRelative(paramView, this.start, this.top, this.end, this.bottom);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/internal/ViewUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */