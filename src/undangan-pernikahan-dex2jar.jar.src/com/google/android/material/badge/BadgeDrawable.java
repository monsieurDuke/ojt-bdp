package com.google.android.material.badge;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import androidx.core.view.ViewCompat;
import com.google.android.material.R.attr;
import com.google.android.material.R.dimen;
import com.google.android.material.R.id;
import com.google.android.material.R.string;
import com.google.android.material.R.style;
import com.google.android.material.internal.TextDrawableHelper;
import com.google.android.material.internal.TextDrawableHelper.TextDrawableDelegate;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.TextAppearance;
import com.google.android.material.shape.MaterialShapeDrawable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.text.NumberFormat;
import java.util.Locale;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class BadgeDrawable
  extends Drawable
  implements TextDrawableHelper.TextDrawableDelegate
{
  public static final int BOTTOM_END = 8388693;
  public static final int BOTTOM_START = 8388691;
  static final String DEFAULT_EXCEED_MAX_BADGE_NUMBER_SUFFIX = "+";
  private static final int DEFAULT_STYLE = R.style.Widget_MaterialComponents_Badge;
  private static final int DEFAULT_THEME_ATTR = R.attr.badgeStyle;
  private static final int MAX_CIRCULAR_BADGE_NUMBER_COUNT = 9;
  public static final int TOP_END = 8388661;
  public static final int TOP_START = 8388659;
  private WeakReference<View> anchorViewRef;
  private final Rect badgeBounds;
  private float badgeCenterX;
  private float badgeCenterY;
  private final WeakReference<Context> contextRef;
  private float cornerRadius;
  private WeakReference<FrameLayout> customBadgeParentRef;
  private float halfBadgeHeight;
  private float halfBadgeWidth;
  private int maxBadgeNumber;
  private final MaterialShapeDrawable shapeDrawable;
  private final BadgeState state;
  private final TextDrawableHelper textDrawableHelper;
  
  private BadgeDrawable(Context paramContext, int paramInt1, int paramInt2, int paramInt3, BadgeState.State paramState)
  {
    this.contextRef = new WeakReference(paramContext);
    ThemeEnforcement.checkMaterialTheme(paramContext);
    this.badgeBounds = new Rect();
    this.shapeDrawable = new MaterialShapeDrawable();
    TextDrawableHelper localTextDrawableHelper = new TextDrawableHelper(this);
    this.textDrawableHelper = localTextDrawableHelper;
    localTextDrawableHelper.getTextPaint().setTextAlign(Paint.Align.CENTER);
    setTextAppearanceResource(R.style.TextAppearance_MaterialComponents_Badge);
    this.state = new BadgeState(paramContext, paramInt1, paramInt2, paramInt3, paramState);
    restoreState();
  }
  
  private void calculateCenterAndBounds(Context paramContext, Rect paramRect, View paramView)
  {
    int i = getTotalVerticalOffsetForState();
    switch (this.state.getBadgeGravity())
    {
    case 8388692: 
    default: 
      this.badgeCenterY = (paramRect.top + i);
      break;
    case 8388691: 
    case 8388693: 
      this.badgeCenterY = (paramRect.bottom - i);
    }
    if (getNumber() <= 9)
    {
      if (!hasNumber()) {
        f = this.state.badgeRadius;
      } else {
        f = this.state.badgeWithTextRadius;
      }
      this.cornerRadius = f;
      this.halfBadgeHeight = f;
      this.halfBadgeWidth = f;
    }
    else
    {
      f = this.state.badgeWithTextRadius;
      this.cornerRadius = f;
      this.halfBadgeHeight = f;
      String str = getBadgeText();
      this.halfBadgeWidth = (this.textDrawableHelper.getTextWidth(str) / 2.0F + this.state.badgeWidePadding);
    }
    paramContext = paramContext.getResources();
    if (hasNumber()) {
      i = R.dimen.mtrl_badge_text_horizontal_edge_offset;
    } else {
      i = R.dimen.mtrl_badge_horizontal_edge_offset;
    }
    int j = paramContext.getDimensionPixelSize(i);
    i = getTotalHorizontalOffsetForState();
    switch (this.state.getBadgeGravity())
    {
    default: 
      if (ViewCompat.getLayoutDirection(paramView) == 0) {
        f = paramRect.right + this.halfBadgeWidth - j - i;
      }
      break;
    case 8388659: 
    case 8388691: 
      if (ViewCompat.getLayoutDirection(paramView) == 0) {
        f = paramRect.left - this.halfBadgeWidth + j + i;
      } else {
        f = paramRect.right + this.halfBadgeWidth - j - i;
      }
      this.badgeCenterX = f;
      break;
    }
    float f = paramRect.left - this.halfBadgeWidth + j + i;
    this.badgeCenterX = f;
  }
  
  public static BadgeDrawable create(Context paramContext)
  {
    return new BadgeDrawable(paramContext, 0, DEFAULT_THEME_ATTR, DEFAULT_STYLE, null);
  }
  
  public static BadgeDrawable createFromResource(Context paramContext, int paramInt)
  {
    return new BadgeDrawable(paramContext, paramInt, DEFAULT_THEME_ATTR, DEFAULT_STYLE, null);
  }
  
  static BadgeDrawable createFromSavedState(Context paramContext, BadgeState.State paramState)
  {
    return new BadgeDrawable(paramContext, 0, DEFAULT_THEME_ATTR, DEFAULT_STYLE, paramState);
  }
  
  private void drawText(Canvas paramCanvas)
  {
    Rect localRect = new Rect();
    String str = getBadgeText();
    this.textDrawableHelper.getTextPaint().getTextBounds(str, 0, str.length(), localRect);
    paramCanvas.drawText(str, this.badgeCenterX, this.badgeCenterY + localRect.height() / 2, this.textDrawableHelper.getTextPaint());
  }
  
  private String getBadgeText()
  {
    if (getNumber() <= this.maxBadgeNumber) {
      return NumberFormat.getInstance(this.state.getNumberLocale()).format(getNumber());
    }
    Object localObject = (Context)this.contextRef.get();
    if (localObject == null) {
      return "";
    }
    localObject = String.format(this.state.getNumberLocale(), ((Context)localObject).getString(R.string.mtrl_exceed_max_badge_number_suffix), new Object[] { Integer.valueOf(this.maxBadgeNumber), "+" });
    Log5ECF72.a(localObject);
    LogE84000.a(localObject);
    Log229316.a(localObject);
    return (String)localObject;
  }
  
  private int getTotalHorizontalOffsetForState()
  {
    int i;
    if (hasNumber()) {
      i = this.state.getHorizontalOffsetWithText();
    } else {
      i = this.state.getHorizontalOffsetWithoutText();
    }
    return this.state.getAdditionalHorizontalOffset() + i;
  }
  
  private int getTotalVerticalOffsetForState()
  {
    int i;
    if (hasNumber()) {
      i = this.state.getVerticalOffsetWithText();
    } else {
      i = this.state.getVerticalOffsetWithoutText();
    }
    return this.state.getAdditionalVerticalOffset() + i;
  }
  
  private void onAlphaUpdated()
  {
    this.textDrawableHelper.getTextPaint().setAlpha(getAlpha());
    invalidateSelf();
  }
  
  private void onBackgroundColorUpdated()
  {
    ColorStateList localColorStateList = ColorStateList.valueOf(this.state.getBackgroundColor());
    if (this.shapeDrawable.getFillColor() != localColorStateList)
    {
      this.shapeDrawable.setFillColor(localColorStateList);
      invalidateSelf();
    }
  }
  
  private void onBadgeGravityUpdated()
  {
    Object localObject = this.anchorViewRef;
    if ((localObject != null) && (((WeakReference)localObject).get() != null))
    {
      View localView = (View)this.anchorViewRef.get();
      localObject = this.customBadgeParentRef;
      if (localObject != null) {
        localObject = (FrameLayout)((WeakReference)localObject).get();
      } else {
        localObject = null;
      }
      updateBadgeCoordinates(localView, (FrameLayout)localObject);
    }
  }
  
  private void onBadgeTextColorUpdated()
  {
    this.textDrawableHelper.getTextPaint().setColor(this.state.getBadgeTextColor());
    invalidateSelf();
  }
  
  private void onMaxCharacterCountUpdated()
  {
    updateMaxBadgeNumber();
    this.textDrawableHelper.setTextWidthDirty(true);
    updateCenterAndBounds();
    invalidateSelf();
  }
  
  private void onNumberUpdated()
  {
    this.textDrawableHelper.setTextWidthDirty(true);
    updateCenterAndBounds();
    invalidateSelf();
  }
  
  private void onVisibilityUpdated()
  {
    boolean bool = this.state.isVisible();
    setVisible(bool, false);
    if ((BadgeUtils.USE_COMPAT_PARENT) && (getCustomBadgeParent() != null) && (!bool)) {
      ((ViewGroup)getCustomBadgeParent().getParent()).invalidate();
    }
  }
  
  private void restoreState()
  {
    onMaxCharacterCountUpdated();
    onNumberUpdated();
    onAlphaUpdated();
    onBackgroundColorUpdated();
    onBadgeTextColorUpdated();
    onBadgeGravityUpdated();
    updateCenterAndBounds();
    onVisibilityUpdated();
  }
  
  private void setTextAppearance(TextAppearance paramTextAppearance)
  {
    if (this.textDrawableHelper.getTextAppearance() == paramTextAppearance) {
      return;
    }
    Context localContext = (Context)this.contextRef.get();
    if (localContext == null) {
      return;
    }
    this.textDrawableHelper.setTextAppearance(paramTextAppearance, localContext);
    updateCenterAndBounds();
  }
  
  private void setTextAppearanceResource(int paramInt)
  {
    Context localContext = (Context)this.contextRef.get();
    if (localContext == null) {
      return;
    }
    setTextAppearance(new TextAppearance(localContext, paramInt));
  }
  
  private void tryWrapAnchorInCompatParent(final View paramView)
  {
    ViewGroup localViewGroup = (ViewGroup)paramView.getParent();
    if ((localViewGroup == null) || (localViewGroup.getId() != R.id.mtrl_anchor_parent))
    {
      localObject = this.customBadgeParentRef;
      if ((localObject == null) || (((WeakReference)localObject).get() != localViewGroup)) {}
    }
    else
    {
      return;
    }
    updateAnchorParentToNotClip(paramView);
    final Object localObject = new FrameLayout(paramView.getContext());
    ((FrameLayout)localObject).setId(R.id.mtrl_anchor_parent);
    ((FrameLayout)localObject).setClipChildren(false);
    ((FrameLayout)localObject).setClipToPadding(false);
    ((FrameLayout)localObject).setLayoutParams(paramView.getLayoutParams());
    ((FrameLayout)localObject).setMinimumWidth(paramView.getWidth());
    ((FrameLayout)localObject).setMinimumHeight(paramView.getHeight());
    int i = localViewGroup.indexOfChild(paramView);
    localViewGroup.removeViewAt(i);
    paramView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
    ((FrameLayout)localObject).addView(paramView);
    localViewGroup.addView((View)localObject, i);
    this.customBadgeParentRef = new WeakReference(localObject);
    ((FrameLayout)localObject).post(new Runnable()
    {
      public void run()
      {
        BadgeDrawable.this.updateBadgeCoordinates(paramView, localObject);
      }
    });
  }
  
  private static void updateAnchorParentToNotClip(View paramView)
  {
    paramView = (ViewGroup)paramView.getParent();
    paramView.setClipChildren(false);
    paramView.setClipToPadding(false);
  }
  
  private void updateCenterAndBounds()
  {
    Context localContext = (Context)this.contextRef.get();
    Object localObject2 = this.anchorViewRef;
    Object localObject1 = null;
    if (localObject2 != null) {
      localObject2 = (View)((WeakReference)localObject2).get();
    } else {
      localObject2 = null;
    }
    if ((localContext != null) && (localObject2 != null))
    {
      Rect localRect1 = new Rect();
      localRect1.set(this.badgeBounds);
      Rect localRect2 = new Rect();
      ((View)localObject2).getDrawingRect(localRect2);
      WeakReference localWeakReference = this.customBadgeParentRef;
      if (localWeakReference != null) {
        localObject1 = (FrameLayout)localWeakReference.get();
      }
      if ((localObject1 != null) || (BadgeUtils.USE_COMPAT_PARENT))
      {
        if (localObject1 == null) {
          localObject1 = (ViewGroup)((View)localObject2).getParent();
        }
        ((ViewGroup)localObject1).offsetDescendantRectToMyCoords((View)localObject2, localRect2);
      }
      calculateCenterAndBounds(localContext, localRect2, (View)localObject2);
      BadgeUtils.updateBadgeBounds(this.badgeBounds, this.badgeCenterX, this.badgeCenterY, this.halfBadgeWidth, this.halfBadgeHeight);
      this.shapeDrawable.setCornerSize(this.cornerRadius);
      if (!localRect1.equals(this.badgeBounds)) {
        this.shapeDrawable.setBounds(this.badgeBounds);
      }
      return;
    }
  }
  
  private void updateMaxBadgeNumber()
  {
    this.maxBadgeNumber = ((int)Math.pow(10.0D, getMaxCharacterCount() - 1.0D) - 1);
  }
  
  public void clearNumber()
  {
    if (hasNumber())
    {
      this.state.clearNumber();
      onNumberUpdated();
    }
  }
  
  public void draw(Canvas paramCanvas)
  {
    if ((!getBounds().isEmpty()) && (getAlpha() != 0) && (isVisible()))
    {
      this.shapeDrawable.draw(paramCanvas);
      if (hasNumber()) {
        drawText(paramCanvas);
      }
      return;
    }
  }
  
  int getAdditionalHorizontalOffset()
  {
    return this.state.getAdditionalHorizontalOffset();
  }
  
  int getAdditionalVerticalOffset()
  {
    return this.state.getAdditionalVerticalOffset();
  }
  
  public int getAlpha()
  {
    return this.state.getAlpha();
  }
  
  public int getBackgroundColor()
  {
    return this.shapeDrawable.getFillColor().getDefaultColor();
  }
  
  public int getBadgeGravity()
  {
    return this.state.getBadgeGravity();
  }
  
  public Locale getBadgeNumberLocale()
  {
    return this.state.getNumberLocale();
  }
  
  public int getBadgeTextColor()
  {
    return this.textDrawableHelper.getTextPaint().getColor();
  }
  
  public CharSequence getContentDescription()
  {
    if (!isVisible()) {
      return null;
    }
    if (hasNumber())
    {
      if (this.state.getContentDescriptionQuantityStrings() != 0)
      {
        Context localContext = (Context)this.contextRef.get();
        if (localContext == null) {
          return null;
        }
        if (getNumber() <= this.maxBadgeNumber) {
          return localContext.getResources().getQuantityString(this.state.getContentDescriptionQuantityStrings(), getNumber(), new Object[] { Integer.valueOf(getNumber()) });
        }
        return localContext.getString(this.state.getContentDescriptionExceedsMaxBadgeNumberStringResource(), new Object[] { Integer.valueOf(this.maxBadgeNumber) });
      }
      return null;
    }
    return this.state.getContentDescriptionNumberless();
  }
  
  public FrameLayout getCustomBadgeParent()
  {
    Object localObject = this.customBadgeParentRef;
    if (localObject != null) {
      localObject = (FrameLayout)((WeakReference)localObject).get();
    } else {
      localObject = null;
    }
    return (FrameLayout)localObject;
  }
  
  public int getHorizontalOffset()
  {
    return this.state.getHorizontalOffsetWithoutText();
  }
  
  public int getHorizontalOffsetWithText()
  {
    return this.state.getHorizontalOffsetWithText();
  }
  
  public int getHorizontalOffsetWithoutText()
  {
    return this.state.getHorizontalOffsetWithoutText();
  }
  
  public int getIntrinsicHeight()
  {
    return this.badgeBounds.height();
  }
  
  public int getIntrinsicWidth()
  {
    return this.badgeBounds.width();
  }
  
  public int getMaxCharacterCount()
  {
    return this.state.getMaxCharacterCount();
  }
  
  public int getNumber()
  {
    int i;
    if (hasNumber()) {
      i = this.state.getNumber();
    } else {
      i = 0;
    }
    return i;
  }
  
  public int getOpacity()
  {
    return -3;
  }
  
  BadgeState.State getSavedState()
  {
    return this.state.getOverridingState();
  }
  
  public int getVerticalOffset()
  {
    return this.state.getVerticalOffsetWithoutText();
  }
  
  public int getVerticalOffsetWithText()
  {
    return this.state.getVerticalOffsetWithText();
  }
  
  public int getVerticalOffsetWithoutText()
  {
    return this.state.getVerticalOffsetWithoutText();
  }
  
  public boolean hasNumber()
  {
    return this.state.hasNumber();
  }
  
  public boolean isStateful()
  {
    return false;
  }
  
  public boolean onStateChange(int[] paramArrayOfInt)
  {
    return super.onStateChange(paramArrayOfInt);
  }
  
  public void onTextSizeChange()
  {
    invalidateSelf();
  }
  
  void setAdditionalHorizontalOffset(int paramInt)
  {
    this.state.setAdditionalHorizontalOffset(paramInt);
    updateCenterAndBounds();
  }
  
  void setAdditionalVerticalOffset(int paramInt)
  {
    this.state.setAdditionalVerticalOffset(paramInt);
    updateCenterAndBounds();
  }
  
  public void setAlpha(int paramInt)
  {
    this.state.setAlpha(paramInt);
    onAlphaUpdated();
  }
  
  public void setBackgroundColor(int paramInt)
  {
    this.state.setBackgroundColor(paramInt);
    onBackgroundColorUpdated();
  }
  
  public void setBadgeGravity(int paramInt)
  {
    if (this.state.getBadgeGravity() != paramInt)
    {
      this.state.setBadgeGravity(paramInt);
      onBadgeGravityUpdated();
    }
  }
  
  public void setBadgeNumberLocale(Locale paramLocale)
  {
    if (!paramLocale.equals(this.state.getNumberLocale()))
    {
      this.state.setNumberLocale(paramLocale);
      invalidateSelf();
    }
  }
  
  public void setBadgeTextColor(int paramInt)
  {
    if (this.textDrawableHelper.getTextPaint().getColor() != paramInt)
    {
      this.state.setBadgeTextColor(paramInt);
      onBadgeTextColorUpdated();
    }
  }
  
  public void setColorFilter(ColorFilter paramColorFilter) {}
  
  public void setContentDescriptionExceedsMaxBadgeNumberStringResource(int paramInt)
  {
    this.state.setContentDescriptionExceedsMaxBadgeNumberStringResource(paramInt);
  }
  
  public void setContentDescriptionNumberless(CharSequence paramCharSequence)
  {
    this.state.setContentDescriptionNumberless(paramCharSequence);
  }
  
  public void setContentDescriptionQuantityStringsResource(int paramInt)
  {
    this.state.setContentDescriptionQuantityStringsResource(paramInt);
  }
  
  public void setHorizontalOffset(int paramInt)
  {
    setHorizontalOffsetWithoutText(paramInt);
    setHorizontalOffsetWithText(paramInt);
  }
  
  public void setHorizontalOffsetWithText(int paramInt)
  {
    this.state.setHorizontalOffsetWithText(paramInt);
    updateCenterAndBounds();
  }
  
  public void setHorizontalOffsetWithoutText(int paramInt)
  {
    this.state.setHorizontalOffsetWithoutText(paramInt);
    updateCenterAndBounds();
  }
  
  public void setMaxCharacterCount(int paramInt)
  {
    if (this.state.getMaxCharacterCount() != paramInt)
    {
      this.state.setMaxCharacterCount(paramInt);
      onMaxCharacterCountUpdated();
    }
  }
  
  public void setNumber(int paramInt)
  {
    paramInt = Math.max(0, paramInt);
    if (this.state.getNumber() != paramInt)
    {
      this.state.setNumber(paramInt);
      onNumberUpdated();
    }
  }
  
  public void setVerticalOffset(int paramInt)
  {
    setVerticalOffsetWithoutText(paramInt);
    setVerticalOffsetWithText(paramInt);
  }
  
  public void setVerticalOffsetWithText(int paramInt)
  {
    this.state.setVerticalOffsetWithText(paramInt);
    updateCenterAndBounds();
  }
  
  public void setVerticalOffsetWithoutText(int paramInt)
  {
    this.state.setVerticalOffsetWithoutText(paramInt);
    updateCenterAndBounds();
  }
  
  public void setVisible(boolean paramBoolean)
  {
    this.state.setVisible(paramBoolean);
    onVisibilityUpdated();
  }
  
  public void updateBadgeCoordinates(View paramView)
  {
    updateBadgeCoordinates(paramView, null);
  }
  
  @Deprecated
  public void updateBadgeCoordinates(View paramView, ViewGroup paramViewGroup)
  {
    if ((paramViewGroup instanceof FrameLayout))
    {
      updateBadgeCoordinates(paramView, (FrameLayout)paramViewGroup);
      return;
    }
    throw new IllegalArgumentException("customBadgeParent must be a FrameLayout");
  }
  
  public void updateBadgeCoordinates(View paramView, FrameLayout paramFrameLayout)
  {
    this.anchorViewRef = new WeakReference(paramView);
    if ((BadgeUtils.USE_COMPAT_PARENT) && (paramFrameLayout == null)) {
      tryWrapAnchorInCompatParent(paramView);
    } else {
      this.customBadgeParentRef = new WeakReference(paramFrameLayout);
    }
    if (!BadgeUtils.USE_COMPAT_PARENT) {
      updateAnchorParentToNotClip(paramView);
    }
    updateCenterAndBounds();
    invalidateSelf();
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface BadgeGravity {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/badge/BadgeDrawable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */