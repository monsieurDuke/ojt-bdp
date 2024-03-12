package androidx.appcompat.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.Region.Op;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.text.InputFilter;
import android.text.Layout;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Property;
import android.view.ActionMode.Callback;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.CompoundButton;
import androidx.appcompat.R.attr;
import androidx.appcompat.R.string;
import androidx.appcompat.R.styleable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.text.AllCapsTransformationMethod;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.TextViewCompat;
import androidx.emoji2.text.EmojiCompat;
import androidx.emoji2.text.EmojiCompat.InitCallback;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.List;

public class SwitchCompat
  extends CompoundButton
  implements EmojiCompatConfigurationView
{
  private static final String ACCESSIBILITY_EVENT_CLASS_NAME = "android.widget.Switch";
  private static final int[] CHECKED_STATE_SET = { 16842912 };
  private static final int MONOSPACE = 3;
  private static final int SANS = 1;
  private static final int SERIF = 2;
  private static final int THUMB_ANIMATION_DURATION = 250;
  private static final Property<SwitchCompat, Float> THUMB_POS = new Property(Float.class, "thumbPos")
  {
    public Float get(SwitchCompat paramAnonymousSwitchCompat)
    {
      return Float.valueOf(paramAnonymousSwitchCompat.mThumbPosition);
    }
    
    public void set(SwitchCompat paramAnonymousSwitchCompat, Float paramAnonymousFloat)
    {
      paramAnonymousSwitchCompat.setThumbPosition(paramAnonymousFloat.floatValue());
    }
  };
  private static final int TOUCH_MODE_DOWN = 1;
  private static final int TOUCH_MODE_DRAGGING = 2;
  private static final int TOUCH_MODE_IDLE = 0;
  private AppCompatEmojiTextHelper mAppCompatEmojiTextHelper;
  private EmojiCompatInitCallback mEmojiCompatInitCallback;
  private boolean mEnforceSwitchWidth = true;
  private boolean mHasThumbTint = false;
  private boolean mHasThumbTintMode = false;
  private boolean mHasTrackTint = false;
  private boolean mHasTrackTintMode = false;
  private int mMinFlingVelocity;
  private Layout mOffLayout;
  private Layout mOnLayout;
  ObjectAnimator mPositionAnimator;
  private boolean mShowText;
  private boolean mSplitTrack;
  private int mSwitchBottom;
  private int mSwitchHeight;
  private int mSwitchLeft;
  private int mSwitchMinWidth;
  private int mSwitchPadding;
  private int mSwitchRight;
  private int mSwitchTop;
  private TransformationMethod mSwitchTransformationMethod;
  private int mSwitchWidth;
  private final Rect mTempRect = new Rect();
  private ColorStateList mTextColors;
  private final AppCompatTextHelper mTextHelper;
  private CharSequence mTextOff;
  private CharSequence mTextOffTransformed;
  private CharSequence mTextOn;
  private CharSequence mTextOnTransformed;
  private final TextPaint mTextPaint;
  private Drawable mThumbDrawable;
  float mThumbPosition;
  private int mThumbTextPadding;
  private ColorStateList mThumbTintList = null;
  private PorterDuff.Mode mThumbTintMode = null;
  private int mThumbWidth;
  private int mTouchMode;
  private int mTouchSlop;
  private float mTouchX;
  private float mTouchY;
  private Drawable mTrackDrawable;
  private ColorStateList mTrackTintList = null;
  private PorterDuff.Mode mTrackTintMode = null;
  private VelocityTracker mVelocityTracker = VelocityTracker.obtain();
  
  public SwitchCompat(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public SwitchCompat(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.switchStyle);
  }
  
  public SwitchCompat(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    ThemeUtils.checkAppCompatTheme(this, getContext());
    Object localObject1 = new TextPaint(1);
    this.mTextPaint = ((TextPaint)localObject1);
    ((TextPaint)localObject1).density = getResources().getDisplayMetrics().density;
    localObject1 = TintTypedArray.obtainStyledAttributes(paramContext, paramAttributeSet, R.styleable.SwitchCompat, paramInt, 0);
    ViewCompat.saveAttributeDataForStyleable(this, paramContext, R.styleable.SwitchCompat, paramAttributeSet, ((TintTypedArray)localObject1).getWrappedTypeArray(), paramInt, 0);
    Object localObject2 = ((TintTypedArray)localObject1).getDrawable(R.styleable.SwitchCompat_android_thumb);
    this.mThumbDrawable = ((Drawable)localObject2);
    if (localObject2 != null) {
      ((Drawable)localObject2).setCallback(this);
    }
    localObject2 = ((TintTypedArray)localObject1).getDrawable(R.styleable.SwitchCompat_track);
    this.mTrackDrawable = ((Drawable)localObject2);
    if (localObject2 != null) {
      ((Drawable)localObject2).setCallback(this);
    }
    setTextOnInternal(((TintTypedArray)localObject1).getText(R.styleable.SwitchCompat_android_textOn));
    setTextOffInternal(((TintTypedArray)localObject1).getText(R.styleable.SwitchCompat_android_textOff));
    this.mShowText = ((TintTypedArray)localObject1).getBoolean(R.styleable.SwitchCompat_showText, true);
    this.mThumbTextPadding = ((TintTypedArray)localObject1).getDimensionPixelSize(R.styleable.SwitchCompat_thumbTextPadding, 0);
    this.mSwitchMinWidth = ((TintTypedArray)localObject1).getDimensionPixelSize(R.styleable.SwitchCompat_switchMinWidth, 0);
    this.mSwitchPadding = ((TintTypedArray)localObject1).getDimensionPixelSize(R.styleable.SwitchCompat_switchPadding, 0);
    this.mSplitTrack = ((TintTypedArray)localObject1).getBoolean(R.styleable.SwitchCompat_splitTrack, false);
    localObject2 = ((TintTypedArray)localObject1).getColorStateList(R.styleable.SwitchCompat_thumbTint);
    if (localObject2 != null)
    {
      this.mThumbTintList = ((ColorStateList)localObject2);
      this.mHasThumbTint = true;
    }
    localObject2 = DrawableUtils.parseTintMode(((TintTypedArray)localObject1).getInt(R.styleable.SwitchCompat_thumbTintMode, -1), null);
    if (this.mThumbTintMode != localObject2)
    {
      this.mThumbTintMode = ((PorterDuff.Mode)localObject2);
      this.mHasThumbTintMode = true;
    }
    if ((this.mHasThumbTint) || (this.mHasThumbTintMode)) {
      applyThumbTint();
    }
    localObject2 = ((TintTypedArray)localObject1).getColorStateList(R.styleable.SwitchCompat_trackTint);
    if (localObject2 != null)
    {
      this.mTrackTintList = ((ColorStateList)localObject2);
      this.mHasTrackTint = true;
    }
    localObject2 = DrawableUtils.parseTintMode(((TintTypedArray)localObject1).getInt(R.styleable.SwitchCompat_trackTintMode, -1), null);
    if (this.mTrackTintMode != localObject2)
    {
      this.mTrackTintMode = ((PorterDuff.Mode)localObject2);
      this.mHasTrackTintMode = true;
    }
    if ((this.mHasTrackTint) || (this.mHasTrackTintMode)) {
      applyTrackTint();
    }
    int i = ((TintTypedArray)localObject1).getResourceId(R.styleable.SwitchCompat_switchTextAppearance, 0);
    if (i != 0) {
      setSwitchTextAppearance(paramContext, i);
    }
    localObject2 = new AppCompatTextHelper(this);
    this.mTextHelper = ((AppCompatTextHelper)localObject2);
    ((AppCompatTextHelper)localObject2).loadFromAttributes(paramAttributeSet, paramInt);
    ((TintTypedArray)localObject1).recycle();
    paramContext = ViewConfiguration.get(paramContext);
    this.mTouchSlop = paramContext.getScaledTouchSlop();
    this.mMinFlingVelocity = paramContext.getScaledMinimumFlingVelocity();
    getEmojiTextViewHelper().loadFromAttributes(paramAttributeSet, paramInt);
    refreshDrawableState();
    setChecked(isChecked());
  }
  
  private void animateThumbToCheckedState(boolean paramBoolean)
  {
    float f;
    if (paramBoolean) {
      f = 1.0F;
    } else {
      f = 0.0F;
    }
    ObjectAnimator localObjectAnimator = ObjectAnimator.ofFloat(this, THUMB_POS, new float[] { f });
    this.mPositionAnimator = localObjectAnimator;
    localObjectAnimator.setDuration(250L);
    if (Build.VERSION.SDK_INT >= 18) {
      Api18Impl.setAutoCancel(this.mPositionAnimator, true);
    }
    this.mPositionAnimator.start();
  }
  
  private void applyThumbTint()
  {
    Drawable localDrawable = this.mThumbDrawable;
    if ((localDrawable != null) && ((this.mHasThumbTint) || (this.mHasThumbTintMode)))
    {
      localDrawable = DrawableCompat.wrap(localDrawable).mutate();
      this.mThumbDrawable = localDrawable;
      if (this.mHasThumbTint) {
        DrawableCompat.setTintList(localDrawable, this.mThumbTintList);
      }
      if (this.mHasThumbTintMode) {
        DrawableCompat.setTintMode(this.mThumbDrawable, this.mThumbTintMode);
      }
      if (this.mThumbDrawable.isStateful()) {
        this.mThumbDrawable.setState(getDrawableState());
      }
    }
  }
  
  private void applyTrackTint()
  {
    Drawable localDrawable = this.mTrackDrawable;
    if ((localDrawable != null) && ((this.mHasTrackTint) || (this.mHasTrackTintMode)))
    {
      localDrawable = DrawableCompat.wrap(localDrawable).mutate();
      this.mTrackDrawable = localDrawable;
      if (this.mHasTrackTint) {
        DrawableCompat.setTintList(localDrawable, this.mTrackTintList);
      }
      if (this.mHasTrackTintMode) {
        DrawableCompat.setTintMode(this.mTrackDrawable, this.mTrackTintMode);
      }
      if (this.mTrackDrawable.isStateful()) {
        this.mTrackDrawable.setState(getDrawableState());
      }
    }
  }
  
  private void cancelPositionAnimator()
  {
    ObjectAnimator localObjectAnimator = this.mPositionAnimator;
    if (localObjectAnimator != null) {
      localObjectAnimator.cancel();
    }
  }
  
  private void cancelSuperTouch(MotionEvent paramMotionEvent)
  {
    paramMotionEvent = MotionEvent.obtain(paramMotionEvent);
    paramMotionEvent.setAction(3);
    super.onTouchEvent(paramMotionEvent);
    paramMotionEvent.recycle();
  }
  
  private static float constrain(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (paramFloat1 < paramFloat2) {
      paramFloat1 = paramFloat2;
    } else if (paramFloat1 > paramFloat3) {
      paramFloat1 = paramFloat3;
    }
    return paramFloat1;
  }
  
  private CharSequence doTransformForOnOffText(CharSequence paramCharSequence)
  {
    TransformationMethod localTransformationMethod = getEmojiTextViewHelper().wrapTransformationMethod(this.mSwitchTransformationMethod);
    if (localTransformationMethod != null) {
      paramCharSequence = localTransformationMethod.getTransformation(paramCharSequence, this);
    }
    return paramCharSequence;
  }
  
  private AppCompatEmojiTextHelper getEmojiTextViewHelper()
  {
    if (this.mAppCompatEmojiTextHelper == null) {
      this.mAppCompatEmojiTextHelper = new AppCompatEmojiTextHelper(this);
    }
    return this.mAppCompatEmojiTextHelper;
  }
  
  private boolean getTargetCheckedState()
  {
    boolean bool;
    if (this.mThumbPosition > 0.5F) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private int getThumbOffset()
  {
    float f;
    if (ViewUtils.isLayoutRtl(this)) {
      f = 1.0F - this.mThumbPosition;
    } else {
      f = this.mThumbPosition;
    }
    return (int)(getThumbScrollRange() * f + 0.5F);
  }
  
  private int getThumbScrollRange()
  {
    Object localObject = this.mTrackDrawable;
    if (localObject != null)
    {
      Rect localRect = this.mTempRect;
      ((Drawable)localObject).getPadding(localRect);
      localObject = this.mThumbDrawable;
      if (localObject != null) {
        localObject = DrawableUtils.getOpticalBounds((Drawable)localObject);
      } else {
        localObject = DrawableUtils.INSETS_NONE;
      }
      return this.mSwitchWidth - this.mThumbWidth - localRect.left - localRect.right - ((Rect)localObject).left - ((Rect)localObject).right;
    }
    return 0;
  }
  
  private boolean hitThumb(float paramFloat1, float paramFloat2)
  {
    Drawable localDrawable = this.mThumbDrawable;
    boolean bool2 = false;
    if (localDrawable == null) {
      return false;
    }
    int k = getThumbOffset();
    this.mThumbDrawable.getPadding(this.mTempRect);
    int j = this.mSwitchTop;
    int i = this.mTouchSlop;
    int i1 = this.mSwitchLeft + k - i;
    int i3 = this.mThumbWidth;
    int m = this.mTempRect.left;
    int i2 = this.mTempRect.right;
    k = this.mTouchSlop;
    int n = this.mSwitchBottom;
    boolean bool1 = bool2;
    if (paramFloat1 > i1)
    {
      bool1 = bool2;
      if (paramFloat1 < i3 + i1 + m + i2 + k)
      {
        bool1 = bool2;
        if (paramFloat2 > j - i)
        {
          bool1 = bool2;
          if (paramFloat2 < n + k) {
            bool1 = true;
          }
        }
      }
    }
    return bool1;
  }
  
  private Layout makeLayout(CharSequence paramCharSequence)
  {
    TextPaint localTextPaint = this.mTextPaint;
    int i;
    if (paramCharSequence != null) {
      i = (int)Math.ceil(Layout.getDesiredWidth(paramCharSequence, localTextPaint));
    } else {
      i = 0;
    }
    return new StaticLayout(paramCharSequence, localTextPaint, i, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
  }
  
  private void setOffStateDescriptionOnRAndAbove()
  {
    if (Build.VERSION.SDK_INT >= 30)
    {
      CharSequence localCharSequence = this.mTextOff;
      Object localObject = localCharSequence;
      if (localCharSequence == null) {
        localObject = getResources().getString(R.string.abc_capital_off);
      }
      ViewCompat.setStateDescription(this, (CharSequence)localObject);
    }
  }
  
  private void setOnStateDescriptionOnRAndAbove()
  {
    if (Build.VERSION.SDK_INT >= 30)
    {
      CharSequence localCharSequence = this.mTextOn;
      Object localObject = localCharSequence;
      if (localCharSequence == null) {
        localObject = getResources().getString(R.string.abc_capital_on);
      }
      ViewCompat.setStateDescription(this, (CharSequence)localObject);
    }
  }
  
  private void setSwitchTypefaceByIndex(int paramInt1, int paramInt2)
  {
    Typeface localTypeface = null;
    switch (paramInt1)
    {
    default: 
      break;
    case 3: 
      localTypeface = Typeface.MONOSPACE;
      break;
    case 2: 
      localTypeface = Typeface.SERIF;
      break;
    case 1: 
      localTypeface = Typeface.SANS_SERIF;
    }
    setSwitchTypeface(localTypeface, paramInt2);
  }
  
  private void setTextOffInternal(CharSequence paramCharSequence)
  {
    this.mTextOff = paramCharSequence;
    this.mTextOffTransformed = doTransformForOnOffText(paramCharSequence);
    this.mOffLayout = null;
    if (this.mShowText) {
      setupEmojiCompatLoadCallback();
    }
  }
  
  private void setTextOnInternal(CharSequence paramCharSequence)
  {
    this.mTextOn = paramCharSequence;
    this.mTextOnTransformed = doTransformForOnOffText(paramCharSequence);
    this.mOnLayout = null;
    if (this.mShowText) {
      setupEmojiCompatLoadCallback();
    }
  }
  
  private void setupEmojiCompatLoadCallback()
  {
    if ((this.mEmojiCompatInitCallback == null) && (this.mAppCompatEmojiTextHelper.isEnabled()))
    {
      if (EmojiCompat.isConfigured())
      {
        EmojiCompat localEmojiCompat = EmojiCompat.get();
        int i = localEmojiCompat.getLoadState();
        if ((i == 3) || (i == 0))
        {
          EmojiCompatInitCallback localEmojiCompatInitCallback = new EmojiCompatInitCallback(this);
          this.mEmojiCompatInitCallback = localEmojiCompatInitCallback;
          localEmojiCompat.registerInitCallback(localEmojiCompatInitCallback);
        }
      }
      return;
    }
  }
  
  private void stopDrag(MotionEvent paramMotionEvent)
  {
    this.mTouchMode = 0;
    int i = paramMotionEvent.getAction();
    boolean bool1 = true;
    if ((i == 1) && (isEnabled())) {
      i = 1;
    } else {
      i = 0;
    }
    boolean bool2 = isChecked();
    if (i != 0)
    {
      this.mVelocityTracker.computeCurrentVelocity(1000);
      float f = this.mVelocityTracker.getXVelocity();
      if (Math.abs(f) > this.mMinFlingVelocity)
      {
        if (ViewUtils.isLayoutRtl(this)) {
          if (f >= 0.0F) {
            break label98;
          }
        } else {
          if (f > 0.0F) {
            break label101;
          }
        }
        label98:
        bool1 = false;
      }
      else
      {
        label101:
        bool1 = getTargetCheckedState();
      }
    }
    else
    {
      bool1 = bool2;
    }
    if (bool1 != bool2) {
      playSoundEffect(0);
    }
    setChecked(bool1);
    cancelSuperTouch(paramMotionEvent);
  }
  
  public void draw(Canvas paramCanvas)
  {
    Rect localRect = this.mTempRect;
    int m = this.mSwitchLeft;
    int i5 = this.mSwitchTop;
    int j = this.mSwitchRight;
    int i6 = this.mSwitchBottom;
    int k = getThumbOffset() + m;
    Object localObject = this.mThumbDrawable;
    if (localObject != null) {
      localObject = DrawableUtils.getOpticalBounds((Drawable)localObject);
    } else {
      localObject = DrawableUtils.INSETS_NONE;
    }
    Drawable localDrawable = this.mTrackDrawable;
    int i = k;
    if (localDrawable != null)
    {
      localDrawable.getPadding(localRect);
      int i7 = k + localRect.left;
      k = i5;
      int n = i6;
      int i2 = m;
      int i4 = k;
      int i3 = j;
      int i1 = n;
      if (localObject != null)
      {
        i = m;
        if (((Rect)localObject).left > localRect.left) {
          i = m + (((Rect)localObject).left - localRect.left);
        }
        m = k;
        if (((Rect)localObject).top > localRect.top) {
          m = k + (((Rect)localObject).top - localRect.top);
        }
        k = j;
        if (((Rect)localObject).right > localRect.right) {
          k = j - (((Rect)localObject).right - localRect.right);
        }
        i2 = i;
        i4 = m;
        i3 = k;
        i1 = n;
        if (((Rect)localObject).bottom > localRect.bottom)
        {
          i1 = n - (((Rect)localObject).bottom - localRect.bottom);
          i3 = k;
          i4 = m;
          i2 = i;
        }
      }
      this.mTrackDrawable.setBounds(i2, i4, i3, i1);
      i = i7;
    }
    localObject = this.mThumbDrawable;
    if (localObject != null)
    {
      ((Drawable)localObject).getPadding(localRect);
      j = i - localRect.left;
      i = this.mThumbWidth + i + localRect.right;
      this.mThumbDrawable.setBounds(j, i5, i, i6);
      localObject = getBackground();
      if (localObject != null) {
        DrawableCompat.setHotspotBounds((Drawable)localObject, j, i5, i, i6);
      }
    }
    super.draw(paramCanvas);
  }
  
  public void drawableHotspotChanged(float paramFloat1, float paramFloat2)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      super.drawableHotspotChanged(paramFloat1, paramFloat2);
    }
    Drawable localDrawable = this.mThumbDrawable;
    if (localDrawable != null) {
      DrawableCompat.setHotspot(localDrawable, paramFloat1, paramFloat2);
    }
    localDrawable = this.mTrackDrawable;
    if (localDrawable != null) {
      DrawableCompat.setHotspot(localDrawable, paramFloat1, paramFloat2);
    }
  }
  
  protected void drawableStateChanged()
  {
    super.drawableStateChanged();
    int[] arrayOfInt = getDrawableState();
    boolean bool2 = false;
    Drawable localDrawable = this.mThumbDrawable;
    boolean bool1 = bool2;
    if (localDrawable != null)
    {
      bool1 = bool2;
      if (localDrawable.isStateful()) {
        bool1 = false | localDrawable.setState(arrayOfInt);
      }
    }
    localDrawable = this.mTrackDrawable;
    bool2 = bool1;
    if (localDrawable != null)
    {
      bool2 = bool1;
      if (localDrawable.isStateful()) {
        bool2 = bool1 | localDrawable.setState(arrayOfInt);
      }
    }
    if (bool2) {
      invalidate();
    }
  }
  
  public int getCompoundPaddingLeft()
  {
    if (!ViewUtils.isLayoutRtl(this)) {
      return super.getCompoundPaddingLeft();
    }
    int j = super.getCompoundPaddingLeft() + this.mSwitchWidth;
    int i = j;
    if (!TextUtils.isEmpty(getText())) {
      i = j + this.mSwitchPadding;
    }
    return i;
  }
  
  public int getCompoundPaddingRight()
  {
    if (ViewUtils.isLayoutRtl(this)) {
      return super.getCompoundPaddingRight();
    }
    int j = super.getCompoundPaddingRight() + this.mSwitchWidth;
    int i = j;
    if (!TextUtils.isEmpty(getText())) {
      i = j + this.mSwitchPadding;
    }
    return i;
  }
  
  public ActionMode.Callback getCustomSelectionActionModeCallback()
  {
    return TextViewCompat.unwrapCustomSelectionActionModeCallback(super.getCustomSelectionActionModeCallback());
  }
  
  public boolean getShowText()
  {
    return this.mShowText;
  }
  
  public boolean getSplitTrack()
  {
    return this.mSplitTrack;
  }
  
  public int getSwitchMinWidth()
  {
    return this.mSwitchMinWidth;
  }
  
  public int getSwitchPadding()
  {
    return this.mSwitchPadding;
  }
  
  public CharSequence getTextOff()
  {
    return this.mTextOff;
  }
  
  public CharSequence getTextOn()
  {
    return this.mTextOn;
  }
  
  public Drawable getThumbDrawable()
  {
    return this.mThumbDrawable;
  }
  
  protected final float getThumbPosition()
  {
    return this.mThumbPosition;
  }
  
  public int getThumbTextPadding()
  {
    return this.mThumbTextPadding;
  }
  
  public ColorStateList getThumbTintList()
  {
    return this.mThumbTintList;
  }
  
  public PorterDuff.Mode getThumbTintMode()
  {
    return this.mThumbTintMode;
  }
  
  public Drawable getTrackDrawable()
  {
    return this.mTrackDrawable;
  }
  
  public ColorStateList getTrackTintList()
  {
    return this.mTrackTintList;
  }
  
  public PorterDuff.Mode getTrackTintMode()
  {
    return this.mTrackTintMode;
  }
  
  public boolean isEmojiCompatEnabled()
  {
    return getEmojiTextViewHelper().isEnabled();
  }
  
  public void jumpDrawablesToCurrentState()
  {
    super.jumpDrawablesToCurrentState();
    Object localObject = this.mThumbDrawable;
    if (localObject != null) {
      ((Drawable)localObject).jumpToCurrentState();
    }
    localObject = this.mTrackDrawable;
    if (localObject != null) {
      ((Drawable)localObject).jumpToCurrentState();
    }
    localObject = this.mPositionAnimator;
    if ((localObject != null) && (((ObjectAnimator)localObject).isStarted()))
    {
      this.mPositionAnimator.end();
      this.mPositionAnimator = null;
    }
  }
  
  protected int[] onCreateDrawableState(int paramInt)
  {
    int[] arrayOfInt = super.onCreateDrawableState(paramInt + 1);
    if (isChecked()) {
      mergeDrawableStates(arrayOfInt, CHECKED_STATE_SET);
    }
    return arrayOfInt;
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    Object localObject3 = this.mTempRect;
    Object localObject4 = this.mTrackDrawable;
    if (localObject4 != null) {
      ((Drawable)localObject4).getPadding((Rect)localObject3);
    } else {
      ((Rect)localObject3).setEmpty();
    }
    int k = this.mSwitchTop;
    int m = this.mSwitchBottom;
    int n = ((Rect)localObject3).top;
    int i1 = ((Rect)localObject3).bottom;
    Object localObject2 = this.mThumbDrawable;
    Object localObject1;
    int i;
    if (localObject4 != null) {
      if ((this.mSplitTrack) && (localObject2 != null))
      {
        localObject1 = DrawableUtils.getOpticalBounds((Drawable)localObject2);
        ((Drawable)localObject2).copyBounds((Rect)localObject3);
        ((Rect)localObject3).left += ((Rect)localObject1).left;
        ((Rect)localObject3).right -= ((Rect)localObject1).right;
        i = paramCanvas.save();
        paramCanvas.clipRect((Rect)localObject3, Region.Op.DIFFERENCE);
        ((Drawable)localObject4).draw(paramCanvas);
        paramCanvas.restoreToCount(i);
      }
      else
      {
        ((Drawable)localObject4).draw(paramCanvas);
      }
    }
    int j = paramCanvas.save();
    if (localObject2 != null) {
      ((Drawable)localObject2).draw(paramCanvas);
    }
    if (getTargetCheckedState()) {
      localObject1 = this.mOnLayout;
    } else {
      localObject1 = this.mOffLayout;
    }
    if (localObject1 != null)
    {
      localObject3 = getDrawableState();
      localObject4 = this.mTextColors;
      if (localObject4 != null) {
        this.mTextPaint.setColor(((ColorStateList)localObject4).getColorForState((int[])localObject3, 0));
      }
      this.mTextPaint.drawableState = ((int[])localObject3);
      if (localObject2 != null)
      {
        localObject2 = ((Drawable)localObject2).getBounds();
        i = ((Rect)localObject2).left + ((Rect)localObject2).right;
      }
      else
      {
        i = getWidth();
      }
      int i2 = i / 2;
      i = ((Layout)localObject1).getWidth() / 2;
      m = (n + k + (m - i1)) / 2;
      k = ((Layout)localObject1).getHeight() / 2;
      paramCanvas.translate(i2 - i, m - k);
      ((Layout)localObject1).draw(paramCanvas);
    }
    paramCanvas.restoreToCount(j);
  }
  
  void onEmojiCompatInitializedForSwitchText()
  {
    setTextOnInternal(this.mTextOn);
    setTextOffInternal(this.mTextOff);
    requestLayout();
  }
  
  public void onInitializeAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
  {
    super.onInitializeAccessibilityEvent(paramAccessibilityEvent);
    paramAccessibilityEvent.setClassName("android.widget.Switch");
  }
  
  public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo paramAccessibilityNodeInfo)
  {
    super.onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfo);
    paramAccessibilityNodeInfo.setClassName("android.widget.Switch");
    if (Build.VERSION.SDK_INT < 30)
    {
      CharSequence localCharSequence1;
      if (isChecked()) {
        localCharSequence1 = this.mTextOn;
      } else {
        localCharSequence1 = this.mTextOff;
      }
      if (!TextUtils.isEmpty(localCharSequence1))
      {
        CharSequence localCharSequence2 = paramAccessibilityNodeInfo.getText();
        if (TextUtils.isEmpty(localCharSequence2))
        {
          paramAccessibilityNodeInfo.setText(localCharSequence1);
        }
        else
        {
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append(localCharSequence2).append(' ').append(localCharSequence1);
          paramAccessibilityNodeInfo.setText(localStringBuilder);
        }
      }
    }
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    paramInt1 = 0;
    paramInt2 = 0;
    if (this.mThumbDrawable != null)
    {
      Rect localRect = this.mTempRect;
      Object localObject = this.mTrackDrawable;
      if (localObject != null) {
        ((Drawable)localObject).getPadding(localRect);
      } else {
        localRect.setEmpty();
      }
      localObject = DrawableUtils.getOpticalBounds(this.mThumbDrawable);
      paramInt1 = Math.max(0, ((Rect)localObject).left - localRect.left);
      paramInt2 = Math.max(0, ((Rect)localObject).right - localRect.right);
    }
    if (ViewUtils.isLayoutRtl(this))
    {
      paramInt4 = getPaddingLeft() + paramInt1;
      paramInt3 = this.mSwitchWidth + paramInt4 - paramInt1 - paramInt2;
    }
    else
    {
      paramInt3 = getWidth() - getPaddingRight() - paramInt2;
      paramInt4 = paramInt3 - this.mSwitchWidth + paramInt1 + paramInt2;
    }
    switch (getGravity() & 0x70)
    {
    default: 
      paramInt2 = getPaddingTop();
      paramInt1 = this.mSwitchHeight + paramInt2;
      break;
    case 80: 
      paramInt1 = getHeight() - getPaddingBottom();
      paramInt2 = paramInt1 - this.mSwitchHeight;
      break;
    case 16: 
      paramInt1 = (getPaddingTop() + getHeight() - getPaddingBottom()) / 2;
      int i = this.mSwitchHeight;
      paramInt1 -= i / 2;
      paramInt2 = paramInt1;
      paramInt1 = i + paramInt1;
    }
    this.mSwitchLeft = paramInt4;
    this.mSwitchTop = paramInt2;
    this.mSwitchBottom = paramInt1;
    this.mSwitchRight = paramInt3;
  }
  
  public void onMeasure(int paramInt1, int paramInt2)
  {
    if (this.mShowText)
    {
      if (this.mOnLayout == null) {
        this.mOnLayout = makeLayout(this.mTextOnTransformed);
      }
      if (this.mOffLayout == null) {
        this.mOffLayout = makeLayout(this.mTextOffTransformed);
      }
    }
    Object localObject = this.mTempRect;
    Drawable localDrawable = this.mThumbDrawable;
    int j;
    if (localDrawable != null)
    {
      localDrawable.getPadding((Rect)localObject);
      j = this.mThumbDrawable.getIntrinsicWidth() - ((Rect)localObject).left - ((Rect)localObject).right;
      i = this.mThumbDrawable.getIntrinsicHeight();
    }
    else
    {
      j = 0;
      i = 0;
    }
    if (this.mShowText) {
      k = Math.max(this.mOnLayout.getWidth(), this.mOffLayout.getWidth()) + this.mThumbTextPadding * 2;
    } else {
      k = 0;
    }
    this.mThumbWidth = Math.max(k, j);
    localDrawable = this.mTrackDrawable;
    if (localDrawable != null)
    {
      localDrawable.getPadding((Rect)localObject);
      j = this.mTrackDrawable.getIntrinsicHeight();
    }
    else
    {
      ((Rect)localObject).setEmpty();
      j = 0;
    }
    int i1 = ((Rect)localObject).left;
    int n = ((Rect)localObject).right;
    localObject = this.mThumbDrawable;
    int m = i1;
    int k = n;
    if (localObject != null)
    {
      localObject = DrawableUtils.getOpticalBounds((Drawable)localObject);
      m = Math.max(i1, ((Rect)localObject).left);
      k = Math.max(n, ((Rect)localObject).right);
    }
    if (this.mEnforceSwitchWidth) {
      k = Math.max(this.mSwitchMinWidth, this.mThumbWidth * 2 + m + k);
    } else {
      k = this.mSwitchMinWidth;
    }
    int i = Math.max(j, i);
    this.mSwitchWidth = k;
    this.mSwitchHeight = i;
    super.onMeasure(paramInt1, paramInt2);
    if (getMeasuredHeight() < i) {
      setMeasuredDimension(getMeasuredWidthAndState(), i);
    }
  }
  
  public void onPopulateAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
  {
    super.onPopulateAccessibilityEvent(paramAccessibilityEvent);
    CharSequence localCharSequence;
    if (isChecked()) {
      localCharSequence = this.mTextOn;
    } else {
      localCharSequence = this.mTextOff;
    }
    if (localCharSequence != null) {
      paramAccessibilityEvent.getText().add(localCharSequence);
    }
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    this.mVelocityTracker.addMovement(paramMotionEvent);
    float f1;
    float f2;
    switch (paramMotionEvent.getActionMasked())
    {
    default: 
      break;
    case 2: 
      switch (this.mTouchMode)
      {
      default: 
        break;
      case 2: 
        float f3 = paramMotionEvent.getX();
        int i = getThumbScrollRange();
        f1 = f3 - this.mTouchX;
        if (i != 0) {
          f1 /= i;
        } else if (f1 > 0.0F) {
          f1 = 1.0F;
        } else {
          f1 = -1.0F;
        }
        f2 = f1;
        if (ViewUtils.isLayoutRtl(this)) {
          f2 = -f1;
        }
        f1 = constrain(this.mThumbPosition + f2, 0.0F, 1.0F);
        if (f1 != this.mThumbPosition)
        {
          this.mTouchX = f3;
          setThumbPosition(f1);
        }
        return true;
      case 1: 
        f1 = paramMotionEvent.getX();
        f2 = paramMotionEvent.getY();
        if ((Math.abs(f1 - this.mTouchX) > this.mTouchSlop) || (Math.abs(f2 - this.mTouchY) > this.mTouchSlop))
        {
          this.mTouchMode = 2;
          getParent().requestDisallowInterceptTouchEvent(true);
          this.mTouchX = f1;
          this.mTouchY = f2;
          return true;
        }
        break;
      }
      break;
    case 1: 
    case 3: 
      if (this.mTouchMode == 2)
      {
        stopDrag(paramMotionEvent);
        super.onTouchEvent(paramMotionEvent);
        return true;
      }
      this.mTouchMode = 0;
      this.mVelocityTracker.clear();
      break;
    case 0: 
      f2 = paramMotionEvent.getX();
      f1 = paramMotionEvent.getY();
      if ((isEnabled()) && (hitThumb(f2, f1)))
      {
        this.mTouchMode = 1;
        this.mTouchX = f2;
        this.mTouchY = f1;
      }
      break;
    }
    return super.onTouchEvent(paramMotionEvent);
  }
  
  public void setAllCaps(boolean paramBoolean)
  {
    super.setAllCaps(paramBoolean);
    getEmojiTextViewHelper().setAllCaps(paramBoolean);
  }
  
  public void setChecked(boolean paramBoolean)
  {
    super.setChecked(paramBoolean);
    paramBoolean = isChecked();
    if (paramBoolean) {
      setOnStateDescriptionOnRAndAbove();
    } else {
      setOffStateDescriptionOnRAndAbove();
    }
    if ((getWindowToken() != null) && (ViewCompat.isLaidOut(this)))
    {
      animateThumbToCheckedState(paramBoolean);
    }
    else
    {
      cancelPositionAnimator();
      float f;
      if (paramBoolean) {
        f = 1.0F;
      } else {
        f = 0.0F;
      }
      setThumbPosition(f);
    }
  }
  
  public void setCustomSelectionActionModeCallback(ActionMode.Callback paramCallback)
  {
    super.setCustomSelectionActionModeCallback(TextViewCompat.wrapCustomSelectionActionModeCallback(this, paramCallback));
  }
  
  public void setEmojiCompatEnabled(boolean paramBoolean)
  {
    getEmojiTextViewHelper().setEnabled(paramBoolean);
    setTextOnInternal(this.mTextOn);
    setTextOffInternal(this.mTextOff);
    requestLayout();
  }
  
  protected final void setEnforceSwitchWidth(boolean paramBoolean)
  {
    this.mEnforceSwitchWidth = paramBoolean;
    invalidate();
  }
  
  public void setFilters(InputFilter[] paramArrayOfInputFilter)
  {
    super.setFilters(getEmojiTextViewHelper().getFilters(paramArrayOfInputFilter));
  }
  
  public void setShowText(boolean paramBoolean)
  {
    if (this.mShowText != paramBoolean)
    {
      this.mShowText = paramBoolean;
      requestLayout();
      if (paramBoolean) {
        setupEmojiCompatLoadCallback();
      }
    }
  }
  
  public void setSplitTrack(boolean paramBoolean)
  {
    this.mSplitTrack = paramBoolean;
    invalidate();
  }
  
  public void setSwitchMinWidth(int paramInt)
  {
    this.mSwitchMinWidth = paramInt;
    requestLayout();
  }
  
  public void setSwitchPadding(int paramInt)
  {
    this.mSwitchPadding = paramInt;
    requestLayout();
  }
  
  public void setSwitchTextAppearance(Context paramContext, int paramInt)
  {
    TintTypedArray localTintTypedArray = TintTypedArray.obtainStyledAttributes(paramContext, paramInt, R.styleable.TextAppearance);
    paramContext = localTintTypedArray.getColorStateList(R.styleable.TextAppearance_android_textColor);
    if (paramContext != null) {
      this.mTextColors = paramContext;
    } else {
      this.mTextColors = getTextColors();
    }
    paramInt = localTintTypedArray.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, 0);
    if ((paramInt != 0) && (paramInt != this.mTextPaint.getTextSize()))
    {
      this.mTextPaint.setTextSize(paramInt);
      requestLayout();
    }
    setSwitchTypefaceByIndex(localTintTypedArray.getInt(R.styleable.TextAppearance_android_typeface, -1), localTintTypedArray.getInt(R.styleable.TextAppearance_android_textStyle, -1));
    if (localTintTypedArray.getBoolean(R.styleable.TextAppearance_textAllCaps, false)) {
      this.mSwitchTransformationMethod = new AllCapsTransformationMethod(getContext());
    } else {
      this.mSwitchTransformationMethod = null;
    }
    setTextOnInternal(this.mTextOn);
    setTextOffInternal(this.mTextOff);
    localTintTypedArray.recycle();
  }
  
  public void setSwitchTypeface(Typeface paramTypeface)
  {
    if (((this.mTextPaint.getTypeface() != null) && (!this.mTextPaint.getTypeface().equals(paramTypeface))) || ((this.mTextPaint.getTypeface() == null) && (paramTypeface != null)))
    {
      this.mTextPaint.setTypeface(paramTypeface);
      requestLayout();
      invalidate();
    }
  }
  
  public void setSwitchTypeface(Typeface paramTypeface, int paramInt)
  {
    float f = 0.0F;
    boolean bool = false;
    if (paramInt > 0)
    {
      if (paramTypeface == null) {
        paramTypeface = Typeface.defaultFromStyle(paramInt);
      } else {
        paramTypeface = Typeface.create(paramTypeface, paramInt);
      }
      setSwitchTypeface(paramTypeface);
      int i;
      if (paramTypeface != null) {
        i = paramTypeface.getStyle();
      } else {
        i = 0;
      }
      paramInt = (i ^ 0xFFFFFFFF) & paramInt;
      paramTypeface = this.mTextPaint;
      if ((paramInt & 0x1) != 0) {
        bool = true;
      }
      paramTypeface.setFakeBoldText(bool);
      paramTypeface = this.mTextPaint;
      if ((paramInt & 0x2) != 0) {
        f = -0.25F;
      }
      paramTypeface.setTextSkewX(f);
    }
    else
    {
      this.mTextPaint.setFakeBoldText(false);
      this.mTextPaint.setTextSkewX(0.0F);
      setSwitchTypeface(paramTypeface);
    }
  }
  
  public void setTextOff(CharSequence paramCharSequence)
  {
    setTextOffInternal(paramCharSequence);
    requestLayout();
    if (!isChecked()) {
      setOffStateDescriptionOnRAndAbove();
    }
  }
  
  public void setTextOn(CharSequence paramCharSequence)
  {
    setTextOnInternal(paramCharSequence);
    requestLayout();
    if (isChecked()) {
      setOnStateDescriptionOnRAndAbove();
    }
  }
  
  public void setThumbDrawable(Drawable paramDrawable)
  {
    Drawable localDrawable = this.mThumbDrawable;
    if (localDrawable != null) {
      localDrawable.setCallback(null);
    }
    this.mThumbDrawable = paramDrawable;
    if (paramDrawable != null) {
      paramDrawable.setCallback(this);
    }
    requestLayout();
  }
  
  void setThumbPosition(float paramFloat)
  {
    this.mThumbPosition = paramFloat;
    invalidate();
  }
  
  public void setThumbResource(int paramInt)
  {
    setThumbDrawable(AppCompatResources.getDrawable(getContext(), paramInt));
  }
  
  public void setThumbTextPadding(int paramInt)
  {
    this.mThumbTextPadding = paramInt;
    requestLayout();
  }
  
  public void setThumbTintList(ColorStateList paramColorStateList)
  {
    this.mThumbTintList = paramColorStateList;
    this.mHasThumbTint = true;
    applyThumbTint();
  }
  
  public void setThumbTintMode(PorterDuff.Mode paramMode)
  {
    this.mThumbTintMode = paramMode;
    this.mHasThumbTintMode = true;
    applyThumbTint();
  }
  
  public void setTrackDrawable(Drawable paramDrawable)
  {
    Drawable localDrawable = this.mTrackDrawable;
    if (localDrawable != null) {
      localDrawable.setCallback(null);
    }
    this.mTrackDrawable = paramDrawable;
    if (paramDrawable != null) {
      paramDrawable.setCallback(this);
    }
    requestLayout();
  }
  
  public void setTrackResource(int paramInt)
  {
    setTrackDrawable(AppCompatResources.getDrawable(getContext(), paramInt));
  }
  
  public void setTrackTintList(ColorStateList paramColorStateList)
  {
    this.mTrackTintList = paramColorStateList;
    this.mHasTrackTint = true;
    applyTrackTint();
  }
  
  public void setTrackTintMode(PorterDuff.Mode paramMode)
  {
    this.mTrackTintMode = paramMode;
    this.mHasTrackTintMode = true;
    applyTrackTint();
  }
  
  public void toggle()
  {
    setChecked(isChecked() ^ true);
  }
  
  protected boolean verifyDrawable(Drawable paramDrawable)
  {
    boolean bool;
    if ((!super.verifyDrawable(paramDrawable)) && (paramDrawable != this.mThumbDrawable) && (paramDrawable != this.mTrackDrawable)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  static class Api18Impl
  {
    static void setAutoCancel(ObjectAnimator paramObjectAnimator, boolean paramBoolean)
    {
      paramObjectAnimator.setAutoCancel(paramBoolean);
    }
  }
  
  static class EmojiCompatInitCallback
    extends EmojiCompat.InitCallback
  {
    private final Reference<SwitchCompat> mOuterWeakRef;
    
    EmojiCompatInitCallback(SwitchCompat paramSwitchCompat)
    {
      this.mOuterWeakRef = new WeakReference(paramSwitchCompat);
    }
    
    public void onFailed(Throwable paramThrowable)
    {
      paramThrowable = (SwitchCompat)this.mOuterWeakRef.get();
      if (paramThrowable != null) {
        paramThrowable.onEmojiCompatInitializedForSwitchText();
      }
    }
    
    public void onInitialized()
    {
      SwitchCompat localSwitchCompat = (SwitchCompat)this.mOuterWeakRef.get();
      if (localSwitchCompat != null) {
        localSwitchCompat.onEmojiCompatInitializedForSwitchText();
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/appcompat/widget/SwitchCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */