package com.google.android.material.textfield;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build.VERSION;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.AutoCompleteTextView;
import android.widget.AutoCompleteTextView.OnDismissListener;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityManagerCompat;
import androidx.core.view.accessibility.AccessibilityManagerCompat.TouchExplorationStateChangeListener;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.material.R.attr;
import com.google.android.material.R.dimen;
import com.google.android.material.R.drawable;
import com.google.android.material.R.string;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.internal.TextWatcherAdapter;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.ShapeAppearanceModel.Builder;

class DropdownMenuEndIconDelegate
  extends EndIconDelegate
{
  private static final int ANIMATION_FADE_IN_DURATION = 67;
  private static final int ANIMATION_FADE_OUT_DURATION = 50;
  private static final boolean IS_LOLLIPOP;
  private final TextInputLayout.AccessibilityDelegate accessibilityDelegate = new TextInputLayout.AccessibilityDelegate(this.textInputLayout)
  {
    public void onInitializeAccessibilityNodeInfo(View paramAnonymousView, AccessibilityNodeInfoCompat paramAnonymousAccessibilityNodeInfoCompat)
    {
      super.onInitializeAccessibilityNodeInfo(paramAnonymousView, paramAnonymousAccessibilityNodeInfoCompat);
      if (!DropdownMenuEndIconDelegate.isEditable(DropdownMenuEndIconDelegate.this.textInputLayout.getEditText())) {
        paramAnonymousAccessibilityNodeInfoCompat.setClassName(Spinner.class.getName());
      }
      if (paramAnonymousAccessibilityNodeInfoCompat.isShowingHintText()) {
        paramAnonymousAccessibilityNodeInfoCompat.setHintText(null);
      }
    }
    
    public void onPopulateAccessibilityEvent(View paramAnonymousView, AccessibilityEvent paramAnonymousAccessibilityEvent)
    {
      super.onPopulateAccessibilityEvent(paramAnonymousView, paramAnonymousAccessibilityEvent);
      paramAnonymousView = DropdownMenuEndIconDelegate.castAutoCompleteTextViewOrThrow(DropdownMenuEndIconDelegate.this.textInputLayout.getEditText());
      if ((paramAnonymousAccessibilityEvent.getEventType() == 1) && (DropdownMenuEndIconDelegate.this.accessibilityManager.isEnabled()) && (!DropdownMenuEndIconDelegate.isEditable(DropdownMenuEndIconDelegate.this.textInputLayout.getEditText())))
      {
        DropdownMenuEndIconDelegate.this.showHideDropdown(paramAnonymousView);
        DropdownMenuEndIconDelegate.this.updateDropdownPopupDirty();
      }
    }
  };
  private AccessibilityManager accessibilityManager;
  private final TextInputLayout.OnEditTextAttachedListener dropdownMenuOnEditTextAttachedListener = new TextInputLayout.OnEditTextAttachedListener()
  {
    public void onEditTextAttached(TextInputLayout paramAnonymousTextInputLayout)
    {
      AutoCompleteTextView localAutoCompleteTextView = DropdownMenuEndIconDelegate.castAutoCompleteTextViewOrThrow(paramAnonymousTextInputLayout.getEditText());
      DropdownMenuEndIconDelegate.this.setPopupBackground(localAutoCompleteTextView);
      DropdownMenuEndIconDelegate.this.addRippleEffect(localAutoCompleteTextView);
      DropdownMenuEndIconDelegate.this.setUpDropdownShowHideBehavior(localAutoCompleteTextView);
      localAutoCompleteTextView.setThreshold(0);
      localAutoCompleteTextView.removeTextChangedListener(DropdownMenuEndIconDelegate.this.exposedDropdownEndIconTextWatcher);
      localAutoCompleteTextView.addTextChangedListener(DropdownMenuEndIconDelegate.this.exposedDropdownEndIconTextWatcher);
      paramAnonymousTextInputLayout.setEndIconCheckable(true);
      paramAnonymousTextInputLayout.setErrorIconDrawable(null);
      if ((!DropdownMenuEndIconDelegate.isEditable(localAutoCompleteTextView)) && (DropdownMenuEndIconDelegate.this.accessibilityManager.isTouchExplorationEnabled())) {
        ViewCompat.setImportantForAccessibility(DropdownMenuEndIconDelegate.this.endIconView, 2);
      }
      paramAnonymousTextInputLayout.setTextInputAccessibilityDelegate(DropdownMenuEndIconDelegate.this.accessibilityDelegate);
      paramAnonymousTextInputLayout.setEndIconVisible(true);
    }
  };
  private long dropdownPopupActivatedAt = Long.MAX_VALUE;
  private boolean dropdownPopupDirty = false;
  private final TextInputLayout.OnEndIconChangedListener endIconChangedListener = new TextInputLayout.OnEndIconChangedListener()
  {
    public void onEndIconChanged(TextInputLayout paramAnonymousTextInputLayout, int paramAnonymousInt)
    {
      final AutoCompleteTextView localAutoCompleteTextView = (AutoCompleteTextView)paramAnonymousTextInputLayout.getEditText();
      if ((localAutoCompleteTextView != null) && (paramAnonymousInt == 3))
      {
        localAutoCompleteTextView.post(new Runnable()
        {
          public void run()
          {
            localAutoCompleteTextView.removeTextChangedListener(DropdownMenuEndIconDelegate.this.exposedDropdownEndIconTextWatcher);
          }
        });
        if (localAutoCompleteTextView.getOnFocusChangeListener() == DropdownMenuEndIconDelegate.this.onFocusChangeListener) {
          localAutoCompleteTextView.setOnFocusChangeListener(null);
        }
        localAutoCompleteTextView.setOnTouchListener(null);
        if (DropdownMenuEndIconDelegate.IS_LOLLIPOP) {
          localAutoCompleteTextView.setOnDismissListener(null);
        }
      }
      if (paramAnonymousInt == 3)
      {
        paramAnonymousTextInputLayout.removeOnAttachStateChangeListener(DropdownMenuEndIconDelegate.this.onAttachStateChangeListener);
        DropdownMenuEndIconDelegate.this.removeTouchExplorationStateChangeListenerIfNeeded();
      }
    }
  };
  private final TextWatcher exposedDropdownEndIconTextWatcher = new TextWatcherAdapter()
  {
    public void afterTextChanged(final Editable paramAnonymousEditable)
    {
      paramAnonymousEditable = DropdownMenuEndIconDelegate.castAutoCompleteTextViewOrThrow(DropdownMenuEndIconDelegate.this.textInputLayout.getEditText());
      if ((DropdownMenuEndIconDelegate.this.accessibilityManager.isTouchExplorationEnabled()) && (DropdownMenuEndIconDelegate.isEditable(paramAnonymousEditable)) && (!DropdownMenuEndIconDelegate.this.endIconView.hasFocus())) {
        paramAnonymousEditable.dismissDropDown();
      }
      paramAnonymousEditable.post(new Runnable()
      {
        public void run()
        {
          boolean bool = paramAnonymousEditable.isPopupShowing();
          DropdownMenuEndIconDelegate.this.setEndIconChecked(bool);
          DropdownMenuEndIconDelegate.access$402(DropdownMenuEndIconDelegate.this, bool);
        }
      });
    }
  };
  private ValueAnimator fadeInAnim;
  private ValueAnimator fadeOutAnim;
  private StateListDrawable filledPopupBackground;
  private boolean isEndIconChecked = false;
  private final View.OnAttachStateChangeListener onAttachStateChangeListener = new View.OnAttachStateChangeListener()
  {
    public void onViewAttachedToWindow(View paramAnonymousView)
    {
      DropdownMenuEndIconDelegate.this.addTouchExplorationStateChangeListenerIfNeeded();
    }
    
    public void onViewDetachedFromWindow(View paramAnonymousView)
    {
      DropdownMenuEndIconDelegate.this.removeTouchExplorationStateChangeListenerIfNeeded();
    }
  };
  private final View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener()
  {
    public void onFocusChange(View paramAnonymousView, boolean paramAnonymousBoolean)
    {
      DropdownMenuEndIconDelegate.this.textInputLayout.setEndIconActivated(paramAnonymousBoolean);
      if (!paramAnonymousBoolean)
      {
        DropdownMenuEndIconDelegate.this.setEndIconChecked(false);
        DropdownMenuEndIconDelegate.access$402(DropdownMenuEndIconDelegate.this, false);
      }
    }
  };
  private MaterialShapeDrawable outlinedPopupBackground;
  private final AccessibilityManagerCompat.TouchExplorationStateChangeListener touchExplorationStateChangeListener = new AccessibilityManagerCompat.TouchExplorationStateChangeListener()
  {
    public void onTouchExplorationStateChanged(boolean paramAnonymousBoolean)
    {
      if (DropdownMenuEndIconDelegate.this.textInputLayout != null)
      {
        Object localObject = (AutoCompleteTextView)DropdownMenuEndIconDelegate.this.textInputLayout.getEditText();
        if ((localObject != null) && (!DropdownMenuEndIconDelegate.isEditable((EditText)localObject)))
        {
          localObject = DropdownMenuEndIconDelegate.this.endIconView;
          int i;
          if (paramAnonymousBoolean) {
            i = 2;
          } else {
            i = 1;
          }
          ViewCompat.setImportantForAccessibility((View)localObject, i);
        }
      }
    }
  };
  
  static
  {
    boolean bool;
    if (Build.VERSION.SDK_INT >= 21) {
      bool = true;
    } else {
      bool = false;
    }
    IS_LOLLIPOP = bool;
  }
  
  DropdownMenuEndIconDelegate(TextInputLayout paramTextInputLayout, int paramInt)
  {
    super(paramTextInputLayout, paramInt);
  }
  
  private void addRippleEffect(AutoCompleteTextView paramAutoCompleteTextView)
  {
    if (isEditable(paramAutoCompleteTextView)) {
      return;
    }
    int i = this.textInputLayout.getBoxBackgroundMode();
    MaterialShapeDrawable localMaterialShapeDrawable = this.textInputLayout.getBoxBackground();
    int j = MaterialColors.getColor(paramAutoCompleteTextView, R.attr.colorControlHighlight);
    int[][] arrayOfInt = new int[2][];
    arrayOfInt[0] = { 16842919 };
    arrayOfInt[1] = new int[0];
    if (i == 2) {
      addRippleEffectOnOutlinedLayout(paramAutoCompleteTextView, j, arrayOfInt, localMaterialShapeDrawable);
    } else if (i == 1) {
      addRippleEffectOnFilledLayout(paramAutoCompleteTextView, j, arrayOfInt, localMaterialShapeDrawable);
    }
  }
  
  private void addRippleEffectOnFilledLayout(AutoCompleteTextView paramAutoCompleteTextView, int paramInt, int[][] paramArrayOfInt, MaterialShapeDrawable paramMaterialShapeDrawable)
  {
    int i = this.textInputLayout.getBoxBackgroundColor();
    paramInt = MaterialColors.layer(paramInt, i, 0.1F);
    int[] arrayOfInt = new int[2];
    arrayOfInt[0] = paramInt;
    arrayOfInt[1] = i;
    if (IS_LOLLIPOP)
    {
      ViewCompat.setBackground(paramAutoCompleteTextView, new RippleDrawable(new ColorStateList(paramArrayOfInt, arrayOfInt), paramMaterialShapeDrawable, paramMaterialShapeDrawable));
    }
    else
    {
      MaterialShapeDrawable localMaterialShapeDrawable = new MaterialShapeDrawable(paramMaterialShapeDrawable.getShapeAppearanceModel());
      localMaterialShapeDrawable.setFillColor(new ColorStateList(paramArrayOfInt, arrayOfInt));
      paramArrayOfInt = new LayerDrawable(new Drawable[] { paramMaterialShapeDrawable, localMaterialShapeDrawable });
      int j = ViewCompat.getPaddingStart(paramAutoCompleteTextView);
      int k = paramAutoCompleteTextView.getPaddingTop();
      i = ViewCompat.getPaddingEnd(paramAutoCompleteTextView);
      paramInt = paramAutoCompleteTextView.getPaddingBottom();
      ViewCompat.setBackground(paramAutoCompleteTextView, paramArrayOfInt);
      ViewCompat.setPaddingRelative(paramAutoCompleteTextView, j, k, i, paramInt);
    }
  }
  
  private void addRippleEffectOnOutlinedLayout(AutoCompleteTextView paramAutoCompleteTextView, int paramInt, int[][] paramArrayOfInt, MaterialShapeDrawable paramMaterialShapeDrawable)
  {
    int i = MaterialColors.getColor(paramAutoCompleteTextView, R.attr.colorSurface);
    MaterialShapeDrawable localMaterialShapeDrawable1 = new MaterialShapeDrawable(paramMaterialShapeDrawable.getShapeAppearanceModel());
    paramInt = MaterialColors.layer(paramInt, i, 0.1F);
    localMaterialShapeDrawable1.setFillColor(new ColorStateList(paramArrayOfInt, new int[] { paramInt, 0 }));
    if (IS_LOLLIPOP)
    {
      localMaterialShapeDrawable1.setTint(i);
      paramArrayOfInt = new ColorStateList(paramArrayOfInt, new int[] { paramInt, i });
      MaterialShapeDrawable localMaterialShapeDrawable2 = new MaterialShapeDrawable(paramMaterialShapeDrawable.getShapeAppearanceModel());
      localMaterialShapeDrawable2.setTint(-1);
      paramArrayOfInt = new LayerDrawable(new Drawable[] { new RippleDrawable(paramArrayOfInt, localMaterialShapeDrawable1, localMaterialShapeDrawable2), paramMaterialShapeDrawable });
    }
    else
    {
      paramArrayOfInt = new LayerDrawable(new Drawable[] { localMaterialShapeDrawable1, paramMaterialShapeDrawable });
    }
    ViewCompat.setBackground(paramAutoCompleteTextView, paramArrayOfInt);
  }
  
  private void addTouchExplorationStateChangeListenerIfNeeded()
  {
    if ((this.accessibilityManager != null) && (this.textInputLayout != null) && (ViewCompat.isAttachedToWindow(this.textInputLayout))) {
      AccessibilityManagerCompat.addTouchExplorationStateChangeListener(this.accessibilityManager, this.touchExplorationStateChangeListener);
    }
  }
  
  private static AutoCompleteTextView castAutoCompleteTextViewOrThrow(EditText paramEditText)
  {
    if ((paramEditText instanceof AutoCompleteTextView)) {
      return (AutoCompleteTextView)paramEditText;
    }
    throw new RuntimeException("EditText needs to be an AutoCompleteTextView if an Exposed Dropdown Menu is being used.");
  }
  
  private ValueAnimator getAlphaAnimator(int paramInt, float... paramVarArgs)
  {
    paramVarArgs = ValueAnimator.ofFloat(paramVarArgs);
    paramVarArgs.setInterpolator(AnimationUtils.LINEAR_INTERPOLATOR);
    paramVarArgs.setDuration(paramInt);
    paramVarArgs.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
    {
      public void onAnimationUpdate(ValueAnimator paramAnonymousValueAnimator)
      {
        float f = ((Float)paramAnonymousValueAnimator.getAnimatedValue()).floatValue();
        DropdownMenuEndIconDelegate.this.endIconView.setAlpha(f);
      }
    });
    return paramVarArgs;
  }
  
  private MaterialShapeDrawable getPopUpMaterialShapeDrawable(float paramFloat1, float paramFloat2, float paramFloat3, int paramInt)
  {
    ShapeAppearanceModel localShapeAppearanceModel = ShapeAppearanceModel.builder().setTopLeftCornerSize(paramFloat1).setTopRightCornerSize(paramFloat1).setBottomLeftCornerSize(paramFloat2).setBottomRightCornerSize(paramFloat2).build();
    MaterialShapeDrawable localMaterialShapeDrawable = MaterialShapeDrawable.createWithElevationOverlay(this.context, paramFloat3);
    localMaterialShapeDrawable.setShapeAppearanceModel(localShapeAppearanceModel);
    localMaterialShapeDrawable.setPadding(0, paramInt, 0, paramInt);
    return localMaterialShapeDrawable;
  }
  
  private void initAnimators()
  {
    this.fadeInAnim = getAlphaAnimator(67, new float[] { 0.0F, 1.0F });
    ValueAnimator localValueAnimator = getAlphaAnimator(50, new float[] { 1.0F, 0.0F });
    this.fadeOutAnim = localValueAnimator;
    localValueAnimator.addListener(new AnimatorListenerAdapter()
    {
      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        DropdownMenuEndIconDelegate.this.endIconView.setChecked(DropdownMenuEndIconDelegate.this.isEndIconChecked);
        DropdownMenuEndIconDelegate.this.fadeInAnim.start();
      }
    });
  }
  
  private boolean isDropdownPopupActive()
  {
    long l = System.currentTimeMillis() - this.dropdownPopupActivatedAt;
    boolean bool;
    if ((l >= 0L) && (l <= 300L)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  private static boolean isEditable(EditText paramEditText)
  {
    boolean bool;
    if (paramEditText.getKeyListener() != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private void removeTouchExplorationStateChangeListenerIfNeeded()
  {
    AccessibilityManager localAccessibilityManager = this.accessibilityManager;
    if (localAccessibilityManager != null) {
      AccessibilityManagerCompat.removeTouchExplorationStateChangeListener(localAccessibilityManager, this.touchExplorationStateChangeListener);
    }
  }
  
  private void setEndIconChecked(boolean paramBoolean)
  {
    if (this.isEndIconChecked != paramBoolean)
    {
      this.isEndIconChecked = paramBoolean;
      this.fadeInAnim.cancel();
      this.fadeOutAnim.start();
    }
  }
  
  private void setPopupBackground(AutoCompleteTextView paramAutoCompleteTextView)
  {
    if (IS_LOLLIPOP)
    {
      int i = this.textInputLayout.getBoxBackgroundMode();
      if (i == 2) {
        paramAutoCompleteTextView.setDropDownBackgroundDrawable(this.outlinedPopupBackground);
      } else if (i == 1) {
        paramAutoCompleteTextView.setDropDownBackgroundDrawable(this.filledPopupBackground);
      }
    }
  }
  
  private void setUpDropdownShowHideBehavior(final AutoCompleteTextView paramAutoCompleteTextView)
  {
    paramAutoCompleteTextView.setOnTouchListener(new View.OnTouchListener()
    {
      public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
      {
        if (paramAnonymousMotionEvent.getAction() == 1)
        {
          if (DropdownMenuEndIconDelegate.this.isDropdownPopupActive()) {
            DropdownMenuEndIconDelegate.access$402(DropdownMenuEndIconDelegate.this, false);
          }
          DropdownMenuEndIconDelegate.this.showHideDropdown(paramAutoCompleteTextView);
          DropdownMenuEndIconDelegate.this.updateDropdownPopupDirty();
        }
        return false;
      }
    });
    paramAutoCompleteTextView.setOnFocusChangeListener(this.onFocusChangeListener);
    if (IS_LOLLIPOP) {
      paramAutoCompleteTextView.setOnDismissListener(new AutoCompleteTextView.OnDismissListener()
      {
        public void onDismiss()
        {
          DropdownMenuEndIconDelegate.this.updateDropdownPopupDirty();
          DropdownMenuEndIconDelegate.this.setEndIconChecked(false);
        }
      });
    }
  }
  
  private void showHideDropdown(AutoCompleteTextView paramAutoCompleteTextView)
  {
    if (paramAutoCompleteTextView == null) {
      return;
    }
    if (isDropdownPopupActive()) {
      this.dropdownPopupDirty = false;
    }
    if (!this.dropdownPopupDirty)
    {
      if (IS_LOLLIPOP)
      {
        setEndIconChecked(this.isEndIconChecked ^ true);
      }
      else
      {
        this.isEndIconChecked ^= true;
        this.endIconView.toggle();
      }
      if (this.isEndIconChecked)
      {
        paramAutoCompleteTextView.requestFocus();
        paramAutoCompleteTextView.showDropDown();
      }
      else
      {
        paramAutoCompleteTextView.dismissDropDown();
      }
    }
    else
    {
      this.dropdownPopupDirty = false;
    }
  }
  
  private void updateDropdownPopupDirty()
  {
    this.dropdownPopupDirty = true;
    this.dropdownPopupActivatedAt = System.currentTimeMillis();
  }
  
  void initialize()
  {
    float f1 = this.context.getResources().getDimensionPixelOffset(R.dimen.mtrl_shape_corner_size_small_component);
    float f2 = this.context.getResources().getDimensionPixelOffset(R.dimen.mtrl_exposed_dropdown_menu_popup_elevation);
    int i = this.context.getResources().getDimensionPixelOffset(R.dimen.mtrl_exposed_dropdown_menu_popup_vertical_padding);
    MaterialShapeDrawable localMaterialShapeDrawable2 = getPopUpMaterialShapeDrawable(f1, f1, f2, i);
    MaterialShapeDrawable localMaterialShapeDrawable1 = getPopUpMaterialShapeDrawable(0.0F, f1, f2, i);
    this.outlinedPopupBackground = localMaterialShapeDrawable2;
    StateListDrawable localStateListDrawable = new StateListDrawable();
    this.filledPopupBackground = localStateListDrawable;
    localStateListDrawable.addState(new int[] { 16842922 }, localMaterialShapeDrawable2);
    this.filledPopupBackground.addState(new int[0], localMaterialShapeDrawable1);
    if (this.customEndIcon == 0)
    {
      if (IS_LOLLIPOP) {
        i = R.drawable.mtrl_dropdown_arrow;
      } else {
        i = R.drawable.mtrl_ic_arrow_drop_down;
      }
    }
    else {
      i = this.customEndIcon;
    }
    this.textInputLayout.setEndIconDrawable(i);
    this.textInputLayout.setEndIconContentDescription(this.textInputLayout.getResources().getText(R.string.exposed_dropdown_menu_content_description));
    this.textInputLayout.setEndIconOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        paramAnonymousView = (AutoCompleteTextView)DropdownMenuEndIconDelegate.this.textInputLayout.getEditText();
        DropdownMenuEndIconDelegate.this.showHideDropdown(paramAnonymousView);
      }
    });
    this.textInputLayout.addOnEditTextAttachedListener(this.dropdownMenuOnEditTextAttachedListener);
    this.textInputLayout.addOnEndIconChangedListener(this.endIconChangedListener);
    initAnimators();
    this.accessibilityManager = ((AccessibilityManager)this.context.getSystemService("accessibility"));
    this.textInputLayout.addOnAttachStateChangeListener(this.onAttachStateChangeListener);
    addTouchExplorationStateChangeListenerIfNeeded();
  }
  
  boolean isBoxBackgroundModeSupported(int paramInt)
  {
    boolean bool;
    if (paramInt != 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  boolean shouldTintIconOnError()
  {
    return true;
  }
  
  void updateOutlinedRippleEffect(AutoCompleteTextView paramAutoCompleteTextView)
  {
    if ((!isEditable(paramAutoCompleteTextView)) && (this.textInputLayout.getBoxBackgroundMode() == 2) && ((paramAutoCompleteTextView.getBackground() instanceof LayerDrawable)))
    {
      addRippleEffect(paramAutoCompleteTextView);
      return;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/textfield/DropdownMenuEndIconDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */