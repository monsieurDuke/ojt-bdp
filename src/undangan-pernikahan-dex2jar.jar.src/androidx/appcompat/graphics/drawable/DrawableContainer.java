package androidx.appcompat.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.graphics.drawable.Drawable.ConstantState;
import android.os.Build.VERSION;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import androidx.core.graphics.drawable.DrawableCompat;

class DrawableContainer
  extends Drawable
  implements Drawable.Callback
{
  private static final boolean DEBUG = false;
  private static final boolean DEFAULT_DITHER = true;
  private static final String TAG = "DrawableContainer";
  private int mAlpha = 255;
  private Runnable mAnimationRunnable;
  private BlockInvalidateCallback mBlockInvalidateCallback;
  private int mCurIndex = -1;
  private Drawable mCurrDrawable;
  private DrawableContainerState mDrawableContainerState;
  private long mEnterAnimationEnd;
  private long mExitAnimationEnd;
  private boolean mHasAlpha;
  private Rect mHotspotBounds;
  private Drawable mLastDrawable;
  private boolean mMutated;
  
  private void initializeDrawableForDisplay(Drawable paramDrawable)
  {
    if (this.mBlockInvalidateCallback == null) {
      this.mBlockInvalidateCallback = new BlockInvalidateCallback();
    }
    paramDrawable.setCallback(this.mBlockInvalidateCallback.wrap(paramDrawable.getCallback()));
    try
    {
      if ((this.mDrawableContainerState.mEnterFadeDuration <= 0) && (this.mHasAlpha)) {
        paramDrawable.setAlpha(this.mAlpha);
      }
      if (this.mDrawableContainerState.mHasColorFilter)
      {
        paramDrawable.setColorFilter(this.mDrawableContainerState.mColorFilter);
      }
      else
      {
        if (this.mDrawableContainerState.mHasTintList) {
          DrawableCompat.setTintList(paramDrawable, this.mDrawableContainerState.mTintList);
        }
        if (this.mDrawableContainerState.mHasTintMode) {
          DrawableCompat.setTintMode(paramDrawable, this.mDrawableContainerState.mTintMode);
        }
      }
      paramDrawable.setVisible(isVisible(), true);
      paramDrawable.setDither(this.mDrawableContainerState.mDither);
      paramDrawable.setState(getState());
      paramDrawable.setLevel(getLevel());
      paramDrawable.setBounds(getBounds());
      if (Build.VERSION.SDK_INT >= 23) {
        DrawableCompat.setLayoutDirection(paramDrawable, DrawableCompat.getLayoutDirection(this));
      }
      if (Build.VERSION.SDK_INT >= 19) {
        DrawableCompat.setAutoMirrored(paramDrawable, this.mDrawableContainerState.mAutoMirrored);
      }
      Rect localRect = this.mHotspotBounds;
      if ((Build.VERSION.SDK_INT >= 21) && (localRect != null)) {
        DrawableCompat.setHotspotBounds(paramDrawable, localRect.left, localRect.top, localRect.right, localRect.bottom);
      }
      return;
    }
    finally
    {
      paramDrawable.setCallback(this.mBlockInvalidateCallback.unwrap());
    }
  }
  
  private boolean needsMirroring()
  {
    boolean bool2 = isAutoMirrored();
    boolean bool1 = true;
    if ((!bool2) || (DrawableCompat.getLayoutDirection(this) != 1)) {
      bool1 = false;
    }
    return bool1;
  }
  
  static int resolveDensity(Resources paramResources, int paramInt)
  {
    if (paramResources != null) {
      paramInt = paramResources.getDisplayMetrics().densityDpi;
    }
    if (paramInt == 0) {
      paramInt = 160;
    }
    return paramInt;
  }
  
  void animate(boolean paramBoolean)
  {
    this.mHasAlpha = true;
    long l1 = SystemClock.uptimeMillis();
    int j = 0;
    Drawable localDrawable = this.mCurrDrawable;
    long l2;
    int i;
    if (localDrawable != null)
    {
      l2 = this.mEnterAnimationEnd;
      i = j;
      if (l2 != 0L) {
        if (l2 <= l1)
        {
          localDrawable.setAlpha(this.mAlpha);
          this.mEnterAnimationEnd = 0L;
          i = j;
        }
        else
        {
          i = (int)((l2 - l1) * 255L) / this.mDrawableContainerState.mEnterFadeDuration;
          this.mCurrDrawable.setAlpha((255 - i) * this.mAlpha / 255);
          i = 1;
        }
      }
    }
    else
    {
      this.mEnterAnimationEnd = 0L;
      i = j;
    }
    localDrawable = this.mLastDrawable;
    if (localDrawable != null)
    {
      l2 = this.mExitAnimationEnd;
      j = i;
      if (l2 != 0L) {
        if (l2 <= l1)
        {
          localDrawable.setVisible(false, false);
          this.mLastDrawable = null;
          this.mExitAnimationEnd = 0L;
          j = i;
        }
        else
        {
          i = (int)((l2 - l1) * 255L) / this.mDrawableContainerState.mExitFadeDuration;
          this.mLastDrawable.setAlpha(this.mAlpha * i / 255);
          j = 1;
        }
      }
    }
    else
    {
      this.mExitAnimationEnd = 0L;
      j = i;
    }
    if ((paramBoolean) && (j != 0)) {
      scheduleSelf(this.mAnimationRunnable, 16L + l1);
    }
  }
  
  public void applyTheme(Resources.Theme paramTheme)
  {
    this.mDrawableContainerState.applyTheme(paramTheme);
  }
  
  public boolean canApplyTheme()
  {
    return this.mDrawableContainerState.canApplyTheme();
  }
  
  void clearMutated()
  {
    this.mDrawableContainerState.clearMutated();
    this.mMutated = false;
  }
  
  DrawableContainerState cloneConstantState()
  {
    return this.mDrawableContainerState;
  }
  
  public void draw(Canvas paramCanvas)
  {
    Drawable localDrawable = this.mCurrDrawable;
    if (localDrawable != null) {
      localDrawable.draw(paramCanvas);
    }
    localDrawable = this.mLastDrawable;
    if (localDrawable != null) {
      localDrawable.draw(paramCanvas);
    }
  }
  
  public int getAlpha()
  {
    return this.mAlpha;
  }
  
  public int getChangingConfigurations()
  {
    return super.getChangingConfigurations() | this.mDrawableContainerState.getChangingConfigurations();
  }
  
  public final Drawable.ConstantState getConstantState()
  {
    if (this.mDrawableContainerState.canConstantState())
    {
      this.mDrawableContainerState.mChangingConfigurations = getChangingConfigurations();
      return this.mDrawableContainerState;
    }
    return null;
  }
  
  public Drawable getCurrent()
  {
    return this.mCurrDrawable;
  }
  
  int getCurrentIndex()
  {
    return this.mCurIndex;
  }
  
  public void getHotspotBounds(Rect paramRect)
  {
    Rect localRect = this.mHotspotBounds;
    if (localRect != null) {
      paramRect.set(localRect);
    } else {
      super.getHotspotBounds(paramRect);
    }
  }
  
  public int getIntrinsicHeight()
  {
    if (this.mDrawableContainerState.isConstantSize()) {
      return this.mDrawableContainerState.getConstantHeight();
    }
    Drawable localDrawable = this.mCurrDrawable;
    int i;
    if (localDrawable != null) {
      i = localDrawable.getIntrinsicHeight();
    } else {
      i = -1;
    }
    return i;
  }
  
  public int getIntrinsicWidth()
  {
    if (this.mDrawableContainerState.isConstantSize()) {
      return this.mDrawableContainerState.getConstantWidth();
    }
    Drawable localDrawable = this.mCurrDrawable;
    int i;
    if (localDrawable != null) {
      i = localDrawable.getIntrinsicWidth();
    } else {
      i = -1;
    }
    return i;
  }
  
  public int getMinimumHeight()
  {
    if (this.mDrawableContainerState.isConstantSize()) {
      return this.mDrawableContainerState.getConstantMinimumHeight();
    }
    Drawable localDrawable = this.mCurrDrawable;
    int i;
    if (localDrawable != null) {
      i = localDrawable.getMinimumHeight();
    } else {
      i = 0;
    }
    return i;
  }
  
  public int getMinimumWidth()
  {
    if (this.mDrawableContainerState.isConstantSize()) {
      return this.mDrawableContainerState.getConstantMinimumWidth();
    }
    Drawable localDrawable = this.mCurrDrawable;
    int i;
    if (localDrawable != null) {
      i = localDrawable.getMinimumWidth();
    } else {
      i = 0;
    }
    return i;
  }
  
  public int getOpacity()
  {
    Drawable localDrawable = this.mCurrDrawable;
    int i;
    if ((localDrawable != null) && (localDrawable.isVisible())) {
      i = this.mDrawableContainerState.getOpacity();
    } else {
      i = -2;
    }
    return i;
  }
  
  public void getOutline(Outline paramOutline)
  {
    Drawable localDrawable = this.mCurrDrawable;
    if (localDrawable != null) {
      Api21Impl.getOutline(localDrawable, paramOutline);
    }
  }
  
  public boolean getPadding(Rect paramRect)
  {
    Object localObject = this.mDrawableContainerState.getConstantPadding();
    boolean bool;
    if (localObject != null)
    {
      paramRect.set((Rect)localObject);
      if ((((Rect)localObject).left | ((Rect)localObject).top | ((Rect)localObject).bottom | ((Rect)localObject).right) != 0) {
        bool = true;
      } else {
        bool = false;
      }
    }
    else
    {
      localObject = this.mCurrDrawable;
      if (localObject != null) {
        bool = ((Drawable)localObject).getPadding(paramRect);
      } else {
        bool = super.getPadding(paramRect);
      }
    }
    if (needsMirroring())
    {
      int i = paramRect.left;
      paramRect.left = paramRect.right;
      paramRect.right = i;
    }
    return bool;
  }
  
  public void invalidateDrawable(Drawable paramDrawable)
  {
    DrawableContainerState localDrawableContainerState = this.mDrawableContainerState;
    if (localDrawableContainerState != null) {
      localDrawableContainerState.invalidateCache();
    }
    if ((paramDrawable == this.mCurrDrawable) && (getCallback() != null)) {
      getCallback().invalidateDrawable(this);
    }
  }
  
  public boolean isAutoMirrored()
  {
    return this.mDrawableContainerState.mAutoMirrored;
  }
  
  public boolean isStateful()
  {
    return this.mDrawableContainerState.isStateful();
  }
  
  public void jumpToCurrentState()
  {
    int i = 0;
    Drawable localDrawable = this.mLastDrawable;
    if (localDrawable != null)
    {
      localDrawable.jumpToCurrentState();
      this.mLastDrawable = null;
      i = 1;
    }
    localDrawable = this.mCurrDrawable;
    if (localDrawable != null)
    {
      localDrawable.jumpToCurrentState();
      if (this.mHasAlpha) {
        this.mCurrDrawable.setAlpha(this.mAlpha);
      }
    }
    if (this.mExitAnimationEnd != 0L)
    {
      this.mExitAnimationEnd = 0L;
      i = 1;
    }
    if (this.mEnterAnimationEnd != 0L)
    {
      this.mEnterAnimationEnd = 0L;
      i = 1;
    }
    if (i != 0) {
      invalidateSelf();
    }
  }
  
  public Drawable mutate()
  {
    if ((!this.mMutated) && (super.mutate() == this))
    {
      DrawableContainerState localDrawableContainerState = cloneConstantState();
      localDrawableContainerState.mutate();
      setConstantState(localDrawableContainerState);
      this.mMutated = true;
    }
    return this;
  }
  
  protected void onBoundsChange(Rect paramRect)
  {
    Drawable localDrawable = this.mLastDrawable;
    if (localDrawable != null) {
      localDrawable.setBounds(paramRect);
    }
    localDrawable = this.mCurrDrawable;
    if (localDrawable != null) {
      localDrawable.setBounds(paramRect);
    }
  }
  
  public boolean onLayoutDirectionChanged(int paramInt)
  {
    return this.mDrawableContainerState.setLayoutDirection(paramInt, getCurrentIndex());
  }
  
  protected boolean onLevelChange(int paramInt)
  {
    Drawable localDrawable = this.mLastDrawable;
    if (localDrawable != null) {
      return localDrawable.setLevel(paramInt);
    }
    localDrawable = this.mCurrDrawable;
    if (localDrawable != null) {
      return localDrawable.setLevel(paramInt);
    }
    return false;
  }
  
  protected boolean onStateChange(int[] paramArrayOfInt)
  {
    Drawable localDrawable = this.mLastDrawable;
    if (localDrawable != null) {
      return localDrawable.setState(paramArrayOfInt);
    }
    localDrawable = this.mCurrDrawable;
    if (localDrawable != null) {
      return localDrawable.setState(paramArrayOfInt);
    }
    return false;
  }
  
  public void scheduleDrawable(Drawable paramDrawable, Runnable paramRunnable, long paramLong)
  {
    if ((paramDrawable == this.mCurrDrawable) && (getCallback() != null)) {
      getCallback().scheduleDrawable(this, paramRunnable, paramLong);
    }
  }
  
  boolean selectDrawable(int paramInt)
  {
    if (paramInt == this.mCurIndex) {
      return false;
    }
    long l = SystemClock.uptimeMillis();
    Object localObject;
    if (this.mDrawableContainerState.mExitFadeDuration > 0)
    {
      localObject = this.mLastDrawable;
      if (localObject != null) {
        ((Drawable)localObject).setVisible(false, false);
      }
      localObject = this.mCurrDrawable;
      if (localObject != null)
      {
        this.mLastDrawable = ((Drawable)localObject);
        this.mExitAnimationEnd = (this.mDrawableContainerState.mExitFadeDuration + l);
      }
      else
      {
        this.mLastDrawable = null;
        this.mExitAnimationEnd = 0L;
      }
    }
    else
    {
      localObject = this.mCurrDrawable;
      if (localObject != null) {
        ((Drawable)localObject).setVisible(false, false);
      }
    }
    if ((paramInt >= 0) && (paramInt < this.mDrawableContainerState.mNumChildren))
    {
      localObject = this.mDrawableContainerState.getChild(paramInt);
      this.mCurrDrawable = ((Drawable)localObject);
      this.mCurIndex = paramInt;
      if (localObject != null)
      {
        if (this.mDrawableContainerState.mEnterFadeDuration > 0) {
          this.mEnterAnimationEnd = (this.mDrawableContainerState.mEnterFadeDuration + l);
        }
        initializeDrawableForDisplay((Drawable)localObject);
      }
    }
    else
    {
      this.mCurrDrawable = null;
      this.mCurIndex = -1;
    }
    if ((this.mEnterAnimationEnd != 0L) || (this.mExitAnimationEnd != 0L))
    {
      localObject = this.mAnimationRunnable;
      if (localObject == null) {
        this.mAnimationRunnable = new Runnable()
        {
          public void run()
          {
            DrawableContainer.this.animate(true);
            DrawableContainer.this.invalidateSelf();
          }
        };
      } else {
        unscheduleSelf((Runnable)localObject);
      }
      animate(true);
    }
    invalidateSelf();
    return true;
  }
  
  public void setAlpha(int paramInt)
  {
    if ((!this.mHasAlpha) || (this.mAlpha != paramInt))
    {
      this.mHasAlpha = true;
      this.mAlpha = paramInt;
      Drawable localDrawable = this.mCurrDrawable;
      if (localDrawable != null) {
        if (this.mEnterAnimationEnd == 0L) {
          localDrawable.setAlpha(paramInt);
        } else {
          animate(false);
        }
      }
    }
  }
  
  public void setAutoMirrored(boolean paramBoolean)
  {
    if (this.mDrawableContainerState.mAutoMirrored != paramBoolean)
    {
      this.mDrawableContainerState.mAutoMirrored = paramBoolean;
      Drawable localDrawable = this.mCurrDrawable;
      if (localDrawable != null) {
        DrawableCompat.setAutoMirrored(localDrawable, this.mDrawableContainerState.mAutoMirrored);
      }
    }
  }
  
  public void setColorFilter(ColorFilter paramColorFilter)
  {
    this.mDrawableContainerState.mHasColorFilter = true;
    if (this.mDrawableContainerState.mColorFilter != paramColorFilter)
    {
      this.mDrawableContainerState.mColorFilter = paramColorFilter;
      Drawable localDrawable = this.mCurrDrawable;
      if (localDrawable != null) {
        localDrawable.setColorFilter(paramColorFilter);
      }
    }
  }
  
  void setConstantState(DrawableContainerState paramDrawableContainerState)
  {
    this.mDrawableContainerState = paramDrawableContainerState;
    int i = this.mCurIndex;
    if (i >= 0)
    {
      paramDrawableContainerState = paramDrawableContainerState.getChild(i);
      this.mCurrDrawable = paramDrawableContainerState;
      if (paramDrawableContainerState != null) {
        initializeDrawableForDisplay(paramDrawableContainerState);
      }
    }
    this.mLastDrawable = null;
  }
  
  void setCurrentIndex(int paramInt)
  {
    selectDrawable(paramInt);
  }
  
  public void setDither(boolean paramBoolean)
  {
    if (this.mDrawableContainerState.mDither != paramBoolean)
    {
      this.mDrawableContainerState.mDither = paramBoolean;
      Drawable localDrawable = this.mCurrDrawable;
      if (localDrawable != null) {
        localDrawable.setDither(this.mDrawableContainerState.mDither);
      }
    }
  }
  
  public void setEnterFadeDuration(int paramInt)
  {
    this.mDrawableContainerState.mEnterFadeDuration = paramInt;
  }
  
  public void setExitFadeDuration(int paramInt)
  {
    this.mDrawableContainerState.mExitFadeDuration = paramInt;
  }
  
  public void setHotspot(float paramFloat1, float paramFloat2)
  {
    Drawable localDrawable = this.mCurrDrawable;
    if (localDrawable != null) {
      DrawableCompat.setHotspot(localDrawable, paramFloat1, paramFloat2);
    }
  }
  
  public void setHotspotBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    Object localObject = this.mHotspotBounds;
    if (localObject == null) {
      this.mHotspotBounds = new Rect(paramInt1, paramInt2, paramInt3, paramInt4);
    } else {
      ((Rect)localObject).set(paramInt1, paramInt2, paramInt3, paramInt4);
    }
    localObject = this.mCurrDrawable;
    if (localObject != null) {
      DrawableCompat.setHotspotBounds((Drawable)localObject, paramInt1, paramInt2, paramInt3, paramInt4);
    }
  }
  
  public void setTint(int paramInt)
  {
    setTintList(ColorStateList.valueOf(paramInt));
  }
  
  public void setTintList(ColorStateList paramColorStateList)
  {
    this.mDrawableContainerState.mHasTintList = true;
    if (this.mDrawableContainerState.mTintList != paramColorStateList)
    {
      this.mDrawableContainerState.mTintList = paramColorStateList;
      DrawableCompat.setTintList(this.mCurrDrawable, paramColorStateList);
    }
  }
  
  public void setTintMode(PorterDuff.Mode paramMode)
  {
    this.mDrawableContainerState.mHasTintMode = true;
    if (this.mDrawableContainerState.mTintMode != paramMode)
    {
      this.mDrawableContainerState.mTintMode = paramMode;
      DrawableCompat.setTintMode(this.mCurrDrawable, paramMode);
    }
  }
  
  public boolean setVisible(boolean paramBoolean1, boolean paramBoolean2)
  {
    boolean bool = super.setVisible(paramBoolean1, paramBoolean2);
    Drawable localDrawable = this.mLastDrawable;
    if (localDrawable != null) {
      localDrawable.setVisible(paramBoolean1, paramBoolean2);
    }
    localDrawable = this.mCurrDrawable;
    if (localDrawable != null) {
      localDrawable.setVisible(paramBoolean1, paramBoolean2);
    }
    return bool;
  }
  
  public void unscheduleDrawable(Drawable paramDrawable, Runnable paramRunnable)
  {
    if ((paramDrawable == this.mCurrDrawable) && (getCallback() != null)) {
      getCallback().unscheduleDrawable(this, paramRunnable);
    }
  }
  
  final void updateDensity(Resources paramResources)
  {
    this.mDrawableContainerState.updateDensity(paramResources);
  }
  
  private static class Api21Impl
  {
    public static boolean canApplyTheme(Drawable.ConstantState paramConstantState)
    {
      return paramConstantState.canApplyTheme();
    }
    
    public static void getOutline(Drawable paramDrawable, Outline paramOutline)
    {
      paramDrawable.getOutline(paramOutline);
    }
    
    public static Resources getResources(Resources.Theme paramTheme)
    {
      return paramTheme.getResources();
    }
  }
  
  static class BlockInvalidateCallback
    implements Drawable.Callback
  {
    private Drawable.Callback mCallback;
    
    public void invalidateDrawable(Drawable paramDrawable) {}
    
    public void scheduleDrawable(Drawable paramDrawable, Runnable paramRunnable, long paramLong)
    {
      Drawable.Callback localCallback = this.mCallback;
      if (localCallback != null) {
        localCallback.scheduleDrawable(paramDrawable, paramRunnable, paramLong);
      }
    }
    
    public void unscheduleDrawable(Drawable paramDrawable, Runnable paramRunnable)
    {
      Drawable.Callback localCallback = this.mCallback;
      if (localCallback != null) {
        localCallback.unscheduleDrawable(paramDrawable, paramRunnable);
      }
    }
    
    public Drawable.Callback unwrap()
    {
      Drawable.Callback localCallback = this.mCallback;
      this.mCallback = null;
      return localCallback;
    }
    
    public BlockInvalidateCallback wrap(Drawable.Callback paramCallback)
    {
      this.mCallback = paramCallback;
      return this;
    }
  }
  
  static abstract class DrawableContainerState
    extends Drawable.ConstantState
  {
    boolean mAutoMirrored;
    boolean mCanConstantState;
    int mChangingConfigurations;
    boolean mCheckedConstantSize;
    boolean mCheckedConstantState;
    boolean mCheckedOpacity;
    boolean mCheckedPadding;
    boolean mCheckedStateful;
    int mChildrenChangingConfigurations;
    ColorFilter mColorFilter;
    int mConstantHeight;
    int mConstantMinimumHeight;
    int mConstantMinimumWidth;
    Rect mConstantPadding;
    boolean mConstantSize = false;
    int mConstantWidth;
    int mDensity;
    boolean mDither = true;
    SparseArray<Drawable.ConstantState> mDrawableFutures;
    Drawable[] mDrawables;
    int mEnterFadeDuration = 0;
    int mExitFadeDuration = 0;
    boolean mHasColorFilter;
    boolean mHasTintList;
    boolean mHasTintMode;
    int mLayoutDirection;
    boolean mMutated;
    int mNumChildren;
    int mOpacity;
    final DrawableContainer mOwner;
    Resources mSourceRes;
    boolean mStateful;
    ColorStateList mTintList;
    PorterDuff.Mode mTintMode;
    boolean mVariablePadding = false;
    
    DrawableContainerState(DrawableContainerState paramDrawableContainerState, DrawableContainer paramDrawableContainer, Resources paramResources)
    {
      this.mOwner = paramDrawableContainer;
      Object localObject = null;
      if (paramResources != null) {
        paramDrawableContainer = paramResources;
      } else if (paramDrawableContainerState != null) {
        paramDrawableContainer = paramDrawableContainerState.mSourceRes;
      } else {
        paramDrawableContainer = null;
      }
      this.mSourceRes = paramDrawableContainer;
      if (paramDrawableContainerState != null) {
        i = paramDrawableContainerState.mDensity;
      } else {
        i = 0;
      }
      int i = DrawableContainer.resolveDensity(paramResources, i);
      this.mDensity = i;
      if (paramDrawableContainerState != null)
      {
        this.mChangingConfigurations = paramDrawableContainerState.mChangingConfigurations;
        this.mChildrenChangingConfigurations = paramDrawableContainerState.mChildrenChangingConfigurations;
        this.mCheckedConstantState = true;
        this.mCanConstantState = true;
        this.mVariablePadding = paramDrawableContainerState.mVariablePadding;
        this.mConstantSize = paramDrawableContainerState.mConstantSize;
        this.mDither = paramDrawableContainerState.mDither;
        this.mMutated = paramDrawableContainerState.mMutated;
        this.mLayoutDirection = paramDrawableContainerState.mLayoutDirection;
        this.mEnterFadeDuration = paramDrawableContainerState.mEnterFadeDuration;
        this.mExitFadeDuration = paramDrawableContainerState.mExitFadeDuration;
        this.mAutoMirrored = paramDrawableContainerState.mAutoMirrored;
        this.mColorFilter = paramDrawableContainerState.mColorFilter;
        this.mHasColorFilter = paramDrawableContainerState.mHasColorFilter;
        this.mTintList = paramDrawableContainerState.mTintList;
        this.mTintMode = paramDrawableContainerState.mTintMode;
        this.mHasTintList = paramDrawableContainerState.mHasTintList;
        this.mHasTintMode = paramDrawableContainerState.mHasTintMode;
        if (paramDrawableContainerState.mDensity == i)
        {
          if (paramDrawableContainerState.mCheckedPadding)
          {
            if (paramDrawableContainerState.mConstantPadding != null) {
              paramDrawableContainer = new Rect(paramDrawableContainerState.mConstantPadding);
            } else {
              paramDrawableContainer = (DrawableContainer)localObject;
            }
            this.mConstantPadding = paramDrawableContainer;
            this.mCheckedPadding = true;
          }
          if (paramDrawableContainerState.mCheckedConstantSize)
          {
            this.mConstantWidth = paramDrawableContainerState.mConstantWidth;
            this.mConstantHeight = paramDrawableContainerState.mConstantHeight;
            this.mConstantMinimumWidth = paramDrawableContainerState.mConstantMinimumWidth;
            this.mConstantMinimumHeight = paramDrawableContainerState.mConstantMinimumHeight;
            this.mCheckedConstantSize = true;
          }
        }
        if (paramDrawableContainerState.mCheckedOpacity)
        {
          this.mOpacity = paramDrawableContainerState.mOpacity;
          this.mCheckedOpacity = true;
        }
        if (paramDrawableContainerState.mCheckedStateful)
        {
          this.mStateful = paramDrawableContainerState.mStateful;
          this.mCheckedStateful = true;
        }
        paramDrawableContainer = paramDrawableContainerState.mDrawables;
        this.mDrawables = new Drawable[paramDrawableContainer.length];
        this.mNumChildren = paramDrawableContainerState.mNumChildren;
        paramDrawableContainerState = paramDrawableContainerState.mDrawableFutures;
        if (paramDrawableContainerState != null) {
          this.mDrawableFutures = paramDrawableContainerState.clone();
        } else {
          this.mDrawableFutures = new SparseArray(this.mNumChildren);
        }
        int j = this.mNumChildren;
        for (i = 0; i < j; i++) {
          if (paramDrawableContainer[i] != null)
          {
            paramDrawableContainerState = paramDrawableContainer[i].getConstantState();
            if (paramDrawableContainerState != null) {
              this.mDrawableFutures.put(i, paramDrawableContainerState);
            } else {
              this.mDrawables[i] = paramDrawableContainer[i];
            }
          }
        }
      }
      else
      {
        this.mDrawables = new Drawable[10];
        this.mNumChildren = 0;
      }
    }
    
    private void createAllFutures()
    {
      Object localObject = this.mDrawableFutures;
      if (localObject != null)
      {
        int j = ((SparseArray)localObject).size();
        for (int i = 0; i < j; i++)
        {
          int k = this.mDrawableFutures.keyAt(i);
          localObject = (Drawable.ConstantState)this.mDrawableFutures.valueAt(i);
          this.mDrawables[k] = prepareDrawable(((Drawable.ConstantState)localObject).newDrawable(this.mSourceRes));
        }
        this.mDrawableFutures = null;
      }
    }
    
    private Drawable prepareDrawable(Drawable paramDrawable)
    {
      if (Build.VERSION.SDK_INT >= 23) {
        DrawableCompat.setLayoutDirection(paramDrawable, this.mLayoutDirection);
      }
      paramDrawable = paramDrawable.mutate();
      paramDrawable.setCallback(this.mOwner);
      return paramDrawable;
    }
    
    public final int addChild(Drawable paramDrawable)
    {
      int i = this.mNumChildren;
      if (i >= this.mDrawables.length) {
        growArray(i, i + 10);
      }
      paramDrawable.mutate();
      paramDrawable.setVisible(false, true);
      paramDrawable.setCallback(this.mOwner);
      this.mDrawables[i] = paramDrawable;
      this.mNumChildren += 1;
      this.mChildrenChangingConfigurations |= paramDrawable.getChangingConfigurations();
      invalidateCache();
      this.mConstantPadding = null;
      this.mCheckedPadding = false;
      this.mCheckedConstantSize = false;
      this.mCheckedConstantState = false;
      return i;
    }
    
    final void applyTheme(Resources.Theme paramTheme)
    {
      if (paramTheme != null)
      {
        createAllFutures();
        int j = this.mNumChildren;
        Drawable[] arrayOfDrawable = this.mDrawables;
        for (int i = 0; i < j; i++) {
          if ((arrayOfDrawable[i] != null) && (DrawableCompat.canApplyTheme(arrayOfDrawable[i])))
          {
            DrawableCompat.applyTheme(arrayOfDrawable[i], paramTheme);
            this.mChildrenChangingConfigurations |= arrayOfDrawable[i].getChangingConfigurations();
          }
        }
        updateDensity(DrawableContainer.Api21Impl.getResources(paramTheme));
      }
    }
    
    public boolean canApplyTheme()
    {
      int j = this.mNumChildren;
      Drawable[] arrayOfDrawable = this.mDrawables;
      for (int i = 0; i < j; i++)
      {
        Object localObject = arrayOfDrawable[i];
        if (localObject != null)
        {
          if (DrawableCompat.canApplyTheme((Drawable)localObject)) {
            return true;
          }
        }
        else
        {
          localObject = (Drawable.ConstantState)this.mDrawableFutures.get(i);
          if ((localObject != null) && (DrawableContainer.Api21Impl.canApplyTheme((Drawable.ConstantState)localObject))) {
            return true;
          }
        }
      }
      return false;
    }
    
    public boolean canConstantState()
    {
      if (this.mCheckedConstantState) {
        return this.mCanConstantState;
      }
      createAllFutures();
      this.mCheckedConstantState = true;
      int j = this.mNumChildren;
      Drawable[] arrayOfDrawable = this.mDrawables;
      for (int i = 0; i < j; i++) {
        if (arrayOfDrawable[i].getConstantState() == null)
        {
          this.mCanConstantState = false;
          return false;
        }
      }
      this.mCanConstantState = true;
      return true;
    }
    
    final void clearMutated()
    {
      this.mMutated = false;
    }
    
    protected void computeConstantSize()
    {
      this.mCheckedConstantSize = true;
      createAllFutures();
      int j = this.mNumChildren;
      Drawable[] arrayOfDrawable = this.mDrawables;
      this.mConstantHeight = -1;
      this.mConstantWidth = -1;
      this.mConstantMinimumHeight = 0;
      this.mConstantMinimumWidth = 0;
      for (int i = 0; i < j; i++)
      {
        Drawable localDrawable = arrayOfDrawable[i];
        int k = localDrawable.getIntrinsicWidth();
        if (k > this.mConstantWidth) {
          this.mConstantWidth = k;
        }
        k = localDrawable.getIntrinsicHeight();
        if (k > this.mConstantHeight) {
          this.mConstantHeight = k;
        }
        k = localDrawable.getMinimumWidth();
        if (k > this.mConstantMinimumWidth) {
          this.mConstantMinimumWidth = k;
        }
        k = localDrawable.getMinimumHeight();
        if (k > this.mConstantMinimumHeight) {
          this.mConstantMinimumHeight = k;
        }
      }
    }
    
    final int getCapacity()
    {
      return this.mDrawables.length;
    }
    
    public int getChangingConfigurations()
    {
      return this.mChangingConfigurations | this.mChildrenChangingConfigurations;
    }
    
    public final Drawable getChild(int paramInt)
    {
      Object localObject = this.mDrawables[paramInt];
      if (localObject != null) {
        return (Drawable)localObject;
      }
      localObject = this.mDrawableFutures;
      if (localObject != null)
      {
        int i = ((SparseArray)localObject).indexOfKey(paramInt);
        if (i >= 0)
        {
          localObject = prepareDrawable(((Drawable.ConstantState)this.mDrawableFutures.valueAt(i)).newDrawable(this.mSourceRes));
          this.mDrawables[paramInt] = localObject;
          this.mDrawableFutures.removeAt(i);
          if (this.mDrawableFutures.size() == 0) {
            this.mDrawableFutures = null;
          }
          return (Drawable)localObject;
        }
      }
      return null;
    }
    
    public final int getChildCount()
    {
      return this.mNumChildren;
    }
    
    public final int getConstantHeight()
    {
      if (!this.mCheckedConstantSize) {
        computeConstantSize();
      }
      return this.mConstantHeight;
    }
    
    public final int getConstantMinimumHeight()
    {
      if (!this.mCheckedConstantSize) {
        computeConstantSize();
      }
      return this.mConstantMinimumHeight;
    }
    
    public final int getConstantMinimumWidth()
    {
      if (!this.mCheckedConstantSize) {
        computeConstantSize();
      }
      return this.mConstantMinimumWidth;
    }
    
    public final Rect getConstantPadding()
    {
      if (this.mVariablePadding) {
        return null;
      }
      Object localObject1 = this.mConstantPadding;
      if ((localObject1 == null) && (!this.mCheckedPadding))
      {
        createAllFutures();
        localObject1 = null;
        Rect localRect = new Rect();
        int j = this.mNumChildren;
        Drawable[] arrayOfDrawable = this.mDrawables;
        int i = 0;
        while (i < j)
        {
          Object localObject3 = localObject1;
          if (arrayOfDrawable[i].getPadding(localRect))
          {
            Object localObject2 = localObject1;
            if (localObject1 == null) {
              localObject2 = new Rect(0, 0, 0, 0);
            }
            if (localRect.left > ((Rect)localObject2).left) {
              ((Rect)localObject2).left = localRect.left;
            }
            if (localRect.top > ((Rect)localObject2).top) {
              ((Rect)localObject2).top = localRect.top;
            }
            if (localRect.right > ((Rect)localObject2).right) {
              ((Rect)localObject2).right = localRect.right;
            }
            localObject3 = localObject2;
            if (localRect.bottom > ((Rect)localObject2).bottom)
            {
              ((Rect)localObject2).bottom = localRect.bottom;
              localObject3 = localObject2;
            }
          }
          i++;
          localObject1 = localObject3;
        }
        this.mCheckedPadding = true;
        this.mConstantPadding = ((Rect)localObject1);
        return (Rect)localObject1;
      }
      return (Rect)localObject1;
    }
    
    public final int getConstantWidth()
    {
      if (!this.mCheckedConstantSize) {
        computeConstantSize();
      }
      return this.mConstantWidth;
    }
    
    public final int getEnterFadeDuration()
    {
      return this.mEnterFadeDuration;
    }
    
    public final int getExitFadeDuration()
    {
      return this.mExitFadeDuration;
    }
    
    public final int getOpacity()
    {
      if (this.mCheckedOpacity) {
        return this.mOpacity;
      }
      createAllFutures();
      int k = this.mNumChildren;
      Drawable[] arrayOfDrawable = this.mDrawables;
      int i;
      if (k > 0) {
        i = arrayOfDrawable[0].getOpacity();
      } else {
        i = -2;
      }
      for (int j = 1; j < k; j++) {
        i = Drawable.resolveOpacity(i, arrayOfDrawable[j].getOpacity());
      }
      this.mOpacity = i;
      this.mCheckedOpacity = true;
      return i;
    }
    
    public void growArray(int paramInt1, int paramInt2)
    {
      Drawable[] arrayOfDrawable1 = new Drawable[paramInt2];
      Drawable[] arrayOfDrawable2 = this.mDrawables;
      if (arrayOfDrawable2 != null) {
        System.arraycopy(arrayOfDrawable2, 0, arrayOfDrawable1, 0, paramInt1);
      }
      this.mDrawables = arrayOfDrawable1;
    }
    
    void invalidateCache()
    {
      this.mCheckedOpacity = false;
      this.mCheckedStateful = false;
    }
    
    public final boolean isConstantSize()
    {
      return this.mConstantSize;
    }
    
    public final boolean isStateful()
    {
      if (this.mCheckedStateful) {
        return this.mStateful;
      }
      createAllFutures();
      int j = this.mNumChildren;
      Drawable[] arrayOfDrawable = this.mDrawables;
      boolean bool2 = false;
      boolean bool1;
      for (int i = 0;; i++)
      {
        bool1 = bool2;
        if (i >= j) {
          break;
        }
        if (arrayOfDrawable[i].isStateful())
        {
          bool1 = true;
          break;
        }
      }
      this.mStateful = bool1;
      this.mCheckedStateful = true;
      return bool1;
    }
    
    void mutate()
    {
      int j = this.mNumChildren;
      Drawable[] arrayOfDrawable = this.mDrawables;
      for (int i = 0; i < j; i++) {
        if (arrayOfDrawable[i] != null) {
          arrayOfDrawable[i].mutate();
        }
      }
      this.mMutated = true;
    }
    
    public final void setConstantSize(boolean paramBoolean)
    {
      this.mConstantSize = paramBoolean;
    }
    
    public final void setEnterFadeDuration(int paramInt)
    {
      this.mEnterFadeDuration = paramInt;
    }
    
    public final void setExitFadeDuration(int paramInt)
    {
      this.mExitFadeDuration = paramInt;
    }
    
    final boolean setLayoutDirection(int paramInt1, int paramInt2)
    {
      boolean bool2 = false;
      int j = this.mNumChildren;
      Drawable[] arrayOfDrawable = this.mDrawables;
      int i = 0;
      while (i < j)
      {
        boolean bool3 = bool2;
        if (arrayOfDrawable[i] != null)
        {
          boolean bool1 = false;
          if (Build.VERSION.SDK_INT >= 23) {
            bool1 = DrawableCompat.setLayoutDirection(arrayOfDrawable[i], paramInt1);
          }
          bool3 = bool2;
          if (i == paramInt2) {
            bool3 = bool1;
          }
        }
        i++;
        bool2 = bool3;
      }
      this.mLayoutDirection = paramInt1;
      return bool2;
    }
    
    public final void setVariablePadding(boolean paramBoolean)
    {
      this.mVariablePadding = paramBoolean;
    }
    
    final void updateDensity(Resources paramResources)
    {
      if (paramResources != null)
      {
        this.mSourceRes = paramResources;
        int j = DrawableContainer.resolveDensity(paramResources, this.mDensity);
        int i = this.mDensity;
        this.mDensity = j;
        if (i != j)
        {
          this.mCheckedConstantSize = false;
          this.mCheckedPadding = false;
        }
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/appcompat/graphics/drawable/DrawableContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */