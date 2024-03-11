package com.google.android.material.snackbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff.Mode;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.R.attr;
import com.google.android.material.R.layout;

public class Snackbar
  extends BaseTransientBottomBar<Snackbar>
{
  private static final int[] SNACKBAR_BUTTON_STYLE_ATTR = { R.attr.snackbarButtonStyle };
  private static final int[] SNACKBAR_CONTENT_STYLE_ATTRS = { R.attr.snackbarButtonStyle, R.attr.snackbarTextViewStyle };
  private final AccessibilityManager accessibilityManager;
  private BaseTransientBottomBar.BaseCallback<Snackbar> callback;
  private boolean hasAction;
  
  private Snackbar(Context paramContext, ViewGroup paramViewGroup, View paramView, ContentViewCallback paramContentViewCallback)
  {
    super(paramContext, paramViewGroup, paramView, paramContentViewCallback);
    this.accessibilityManager = ((AccessibilityManager)paramViewGroup.getContext().getSystemService("accessibility"));
  }
  
  private static ViewGroup findSuitableParent(View paramView)
  {
    ViewGroup localViewGroup = null;
    for (;;)
    {
      if ((paramView instanceof CoordinatorLayout)) {
        return (ViewGroup)paramView;
      }
      if ((paramView instanceof FrameLayout))
      {
        if (paramView.getId() == 16908290) {
          return (ViewGroup)paramView;
        }
        localViewGroup = (ViewGroup)paramView;
      }
      View localView = paramView;
      if (paramView != null)
      {
        paramView = paramView.getParent();
        if ((paramView instanceof View)) {
          paramView = (View)paramView;
        } else {
          paramView = null;
        }
        localView = paramView;
      }
      if (localView == null) {
        return localViewGroup;
      }
      paramView = localView;
    }
  }
  
  private Button getActionView()
  {
    return getContentLayout().getActionView();
  }
  
  private SnackbarContentLayout getContentLayout()
  {
    return (SnackbarContentLayout)this.view.getChildAt(0);
  }
  
  private TextView getMessageView()
  {
    return getContentLayout().getMessageView();
  }
  
  @Deprecated
  protected static boolean hasSnackbarButtonStyleAttr(Context paramContext)
  {
    paramContext = paramContext.obtainStyledAttributes(SNACKBAR_BUTTON_STYLE_ATTR);
    boolean bool = false;
    int i = paramContext.getResourceId(0, -1);
    paramContext.recycle();
    if (i != -1) {
      bool = true;
    }
    return bool;
  }
  
  private static boolean hasSnackbarContentStyleAttrs(Context paramContext)
  {
    paramContext = paramContext.obtainStyledAttributes(SNACKBAR_CONTENT_STYLE_ATTRS);
    boolean bool2 = false;
    int j = paramContext.getResourceId(0, -1);
    int i = paramContext.getResourceId(1, -1);
    paramContext.recycle();
    boolean bool1 = bool2;
    if (j != -1)
    {
      bool1 = bool2;
      if (i != -1) {
        bool1 = true;
      }
    }
    return bool1;
  }
  
  public static Snackbar make(Context paramContext, View paramView, CharSequence paramCharSequence, int paramInt)
  {
    return makeInternal(paramContext, paramView, paramCharSequence, paramInt);
  }
  
  public static Snackbar make(View paramView, int paramInt1, int paramInt2)
  {
    return make(paramView, paramView.getResources().getText(paramInt1), paramInt2);
  }
  
  public static Snackbar make(View paramView, CharSequence paramCharSequence, int paramInt)
  {
    return makeInternal(null, paramView, paramCharSequence, paramInt);
  }
  
  private static Snackbar makeInternal(Context paramContext, View paramView, CharSequence paramCharSequence, int paramInt)
  {
    ViewGroup localViewGroup = findSuitableParent(paramView);
    if (localViewGroup != null)
    {
      paramView = paramContext;
      if (paramContext == null) {
        paramView = localViewGroup.getContext();
      }
      paramContext = LayoutInflater.from(paramView);
      int i;
      if (hasSnackbarContentStyleAttrs(paramView)) {
        i = R.layout.mtrl_layout_snackbar_include;
      } else {
        i = R.layout.design_layout_snackbar_include;
      }
      paramContext = (SnackbarContentLayout)paramContext.inflate(i, localViewGroup, false);
      paramContext = new Snackbar(paramView, localViewGroup, paramContext, paramContext);
      paramContext.setText(paramCharSequence);
      paramContext.setDuration(paramInt);
      return paramContext;
    }
    throw new IllegalArgumentException("No suitable parent found from the given view. Please provide a valid view.");
  }
  
  public void dismiss()
  {
    super.dismiss();
  }
  
  public int getDuration()
  {
    int j = super.getDuration();
    int i = -2;
    if (j == -2) {
      return -2;
    }
    if (Build.VERSION.SDK_INT >= 29)
    {
      if (this.hasAction) {
        i = 4;
      } else {
        i = 0;
      }
      return this.accessibilityManager.getRecommendedTimeoutMillis(j, i | 0x1 | 0x2);
    }
    if ((!this.hasAction) || (!this.accessibilityManager.isTouchExplorationEnabled())) {
      i = j;
    }
    return i;
  }
  
  public boolean isShown()
  {
    return super.isShown();
  }
  
  public Snackbar setAction(int paramInt, View.OnClickListener paramOnClickListener)
  {
    return setAction(getContext().getText(paramInt), paramOnClickListener);
  }
  
  public Snackbar setAction(CharSequence paramCharSequence, final View.OnClickListener paramOnClickListener)
  {
    Button localButton = getActionView();
    if ((!TextUtils.isEmpty(paramCharSequence)) && (paramOnClickListener != null))
    {
      this.hasAction = true;
      localButton.setVisibility(0);
      localButton.setText(paramCharSequence);
      localButton.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          paramOnClickListener.onClick(paramAnonymousView);
          Snackbar.this.dispatchDismiss(1);
        }
      });
    }
    else
    {
      localButton.setVisibility(8);
      localButton.setOnClickListener(null);
      this.hasAction = false;
    }
    return this;
  }
  
  public Snackbar setActionTextColor(int paramInt)
  {
    getActionView().setTextColor(paramInt);
    return this;
  }
  
  public Snackbar setActionTextColor(ColorStateList paramColorStateList)
  {
    getActionView().setTextColor(paramColorStateList);
    return this;
  }
  
  public Snackbar setBackgroundTint(int paramInt)
  {
    return setBackgroundTintList(ColorStateList.valueOf(paramInt));
  }
  
  public Snackbar setBackgroundTintList(ColorStateList paramColorStateList)
  {
    this.view.setBackgroundTintList(paramColorStateList);
    return this;
  }
  
  public Snackbar setBackgroundTintMode(PorterDuff.Mode paramMode)
  {
    this.view.setBackgroundTintMode(paramMode);
    return this;
  }
  
  @Deprecated
  public Snackbar setCallback(Callback paramCallback)
  {
    BaseTransientBottomBar.BaseCallback localBaseCallback = this.callback;
    if (localBaseCallback != null) {
      removeCallback(localBaseCallback);
    }
    if (paramCallback != null) {
      addCallback(paramCallback);
    }
    this.callback = paramCallback;
    return this;
  }
  
  public Snackbar setMaxInlineActionWidth(int paramInt)
  {
    getContentLayout().setMaxInlineActionWidth(paramInt);
    return this;
  }
  
  public Snackbar setText(int paramInt)
  {
    return setText(getContext().getText(paramInt));
  }
  
  public Snackbar setText(CharSequence paramCharSequence)
  {
    getMessageView().setText(paramCharSequence);
    return this;
  }
  
  public Snackbar setTextColor(int paramInt)
  {
    getMessageView().setTextColor(paramInt);
    return this;
  }
  
  public Snackbar setTextColor(ColorStateList paramColorStateList)
  {
    getMessageView().setTextColor(paramColorStateList);
    return this;
  }
  
  public Snackbar setTextMaxLines(int paramInt)
  {
    getMessageView().setMaxLines(paramInt);
    return this;
  }
  
  public void show()
  {
    super.show();
  }
  
  public static class Callback
    extends BaseTransientBottomBar.BaseCallback<Snackbar>
  {
    public static final int DISMISS_EVENT_ACTION = 1;
    public static final int DISMISS_EVENT_CONSECUTIVE = 4;
    public static final int DISMISS_EVENT_MANUAL = 3;
    public static final int DISMISS_EVENT_SWIPE = 0;
    public static final int DISMISS_EVENT_TIMEOUT = 2;
    
    public void onDismissed(Snackbar paramSnackbar, int paramInt) {}
    
    public void onShown(Snackbar paramSnackbar) {}
  }
  
  public static final class SnackbarLayout
    extends BaseTransientBottomBar.SnackbarBaseLayout
  {
    public SnackbarLayout(Context paramContext)
    {
      super();
    }
    
    public SnackbarLayout(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
    }
    
    protected void onMeasure(int paramInt1, int paramInt2)
    {
      super.onMeasure(paramInt1, paramInt2);
      int k = getChildCount();
      paramInt2 = getMeasuredWidth();
      int i = getPaddingLeft();
      int j = getPaddingRight();
      for (paramInt1 = 0; paramInt1 < k; paramInt1++)
      {
        View localView = getChildAt(paramInt1);
        if (localView.getLayoutParams().width == -1) {
          localView.measure(View.MeasureSpec.makeMeasureSpec(paramInt2 - i - j, 1073741824), View.MeasureSpec.makeMeasureSpec(localView.getMeasuredHeight(), 1073741824));
        }
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/snackbar/Snackbar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */