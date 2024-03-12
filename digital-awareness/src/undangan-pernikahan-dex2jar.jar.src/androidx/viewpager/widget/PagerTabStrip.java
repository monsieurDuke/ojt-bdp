package androidx.viewpager.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.widget.TextView;
import androidx.core.content.ContextCompat;

public class PagerTabStrip
  extends PagerTitleStrip
{
  private static final int FULL_UNDERLINE_HEIGHT = 1;
  private static final int INDICATOR_HEIGHT = 3;
  private static final int MIN_PADDING_BOTTOM = 6;
  private static final int MIN_STRIP_HEIGHT = 32;
  private static final int MIN_TEXT_SPACING = 64;
  private static final int TAB_PADDING = 16;
  private static final int TAB_SPACING = 32;
  private static final String TAG = "PagerTabStrip";
  private boolean mDrawFullUnderline;
  private boolean mDrawFullUnderlineSet;
  private int mFullUnderlineHeight;
  private boolean mIgnoreTap;
  private int mIndicatorColor;
  private int mIndicatorHeight;
  private float mInitialMotionX;
  private float mInitialMotionY;
  private int mMinPaddingBottom;
  private int mMinStripHeight;
  private int mMinTextSpacing;
  private int mTabAlpha;
  private int mTabPadding;
  private final Paint mTabPaint;
  private final Rect mTempRect;
  private int mTouchSlop;
  
  public PagerTabStrip(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PagerTabStrip(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    paramAttributeSet = new Paint();
    this.mTabPaint = paramAttributeSet;
    this.mTempRect = new Rect();
    this.mTabAlpha = 255;
    this.mDrawFullUnderline = false;
    this.mDrawFullUnderlineSet = false;
    int i = this.mTextColor;
    this.mIndicatorColor = i;
    paramAttributeSet.setColor(i);
    float f = paramContext.getResources().getDisplayMetrics().density;
    this.mIndicatorHeight = ((int)(3.0F * f + 0.5F));
    this.mMinPaddingBottom = ((int)(6.0F * f + 0.5F));
    this.mMinTextSpacing = ((int)(64.0F * f));
    this.mTabPadding = ((int)(16.0F * f + 0.5F));
    this.mFullUnderlineHeight = ((int)(1.0F * f + 0.5F));
    this.mMinStripHeight = ((int)(32.0F * f + 0.5F));
    this.mTouchSlop = ViewConfiguration.get(paramContext).getScaledTouchSlop();
    setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom());
    setTextSpacing(getTextSpacing());
    setWillNotDraw(false);
    this.mPrevText.setFocusable(true);
    this.mPrevText.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        PagerTabStrip.this.mPager.setCurrentItem(PagerTabStrip.this.mPager.getCurrentItem() - 1);
      }
    });
    this.mNextText.setFocusable(true);
    this.mNextText.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        PagerTabStrip.this.mPager.setCurrentItem(PagerTabStrip.this.mPager.getCurrentItem() + 1);
      }
    });
    if (getBackground() == null) {
      this.mDrawFullUnderline = true;
    }
  }
  
  public boolean getDrawFullUnderline()
  {
    return this.mDrawFullUnderline;
  }
  
  int getMinHeight()
  {
    return Math.max(super.getMinHeight(), this.mMinStripHeight);
  }
  
  public int getTabIndicatorColor()
  {
    return this.mIndicatorColor;
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    int j = getHeight();
    int n = this.mCurrText.getLeft();
    int k = this.mTabPadding;
    int m = this.mCurrText.getRight();
    int i1 = this.mTabPadding;
    int i = this.mIndicatorHeight;
    this.mTabPaint.setColor(this.mTabAlpha << 24 | this.mIndicatorColor & 0xFFFFFF);
    paramCanvas.drawRect(n - k, j - i, m + i1, j, this.mTabPaint);
    if (this.mDrawFullUnderline)
    {
      this.mTabPaint.setColor(0xFF000000 | this.mIndicatorColor & 0xFFFFFF);
      paramCanvas.drawRect(getPaddingLeft(), j - this.mFullUnderlineHeight, getWidth() - getPaddingRight(), j, this.mTabPaint);
    }
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    int i = paramMotionEvent.getAction();
    if ((i != 0) && (this.mIgnoreTap)) {
      return false;
    }
    float f2 = paramMotionEvent.getX();
    float f1 = paramMotionEvent.getY();
    switch (i)
    {
    default: 
      break;
    case 2: 
      if ((Math.abs(f2 - this.mInitialMotionX) > this.mTouchSlop) || (Math.abs(f1 - this.mInitialMotionY) > this.mTouchSlop)) {
        this.mIgnoreTap = true;
      }
      break;
    case 1: 
      if (f2 < this.mCurrText.getLeft() - this.mTabPadding) {
        this.mPager.setCurrentItem(this.mPager.getCurrentItem() - 1);
      } else if (f2 > this.mCurrText.getRight() + this.mTabPadding) {
        this.mPager.setCurrentItem(this.mPager.getCurrentItem() + 1);
      }
      break;
    case 0: 
      this.mInitialMotionX = f2;
      this.mInitialMotionY = f1;
      this.mIgnoreTap = false;
    }
    return true;
  }
  
  public void setBackgroundColor(int paramInt)
  {
    super.setBackgroundColor(paramInt);
    if (!this.mDrawFullUnderlineSet)
    {
      boolean bool;
      if ((0xFF000000 & paramInt) == 0) {
        bool = true;
      } else {
        bool = false;
      }
      this.mDrawFullUnderline = bool;
    }
  }
  
  public void setBackgroundDrawable(Drawable paramDrawable)
  {
    super.setBackgroundDrawable(paramDrawable);
    if (!this.mDrawFullUnderlineSet)
    {
      boolean bool;
      if (paramDrawable == null) {
        bool = true;
      } else {
        bool = false;
      }
      this.mDrawFullUnderline = bool;
    }
  }
  
  public void setBackgroundResource(int paramInt)
  {
    super.setBackgroundResource(paramInt);
    if (!this.mDrawFullUnderlineSet)
    {
      boolean bool;
      if (paramInt == 0) {
        bool = true;
      } else {
        bool = false;
      }
      this.mDrawFullUnderline = bool;
    }
  }
  
  public void setDrawFullUnderline(boolean paramBoolean)
  {
    this.mDrawFullUnderline = paramBoolean;
    this.mDrawFullUnderlineSet = true;
    invalidate();
  }
  
  public void setPadding(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = paramInt4;
    if (paramInt4 < this.mMinPaddingBottom) {
      i = this.mMinPaddingBottom;
    }
    super.setPadding(paramInt1, paramInt2, paramInt3, i);
  }
  
  public void setTabIndicatorColor(int paramInt)
  {
    this.mIndicatorColor = paramInt;
    this.mTabPaint.setColor(paramInt);
    invalidate();
  }
  
  public void setTabIndicatorColorResource(int paramInt)
  {
    setTabIndicatorColor(ContextCompat.getColor(getContext(), paramInt));
  }
  
  public void setTextSpacing(int paramInt)
  {
    int i = paramInt;
    if (paramInt < this.mMinTextSpacing) {
      i = this.mMinTextSpacing;
    }
    super.setTextSpacing(i);
  }
  
  void updateTextPositions(int paramInt, float paramFloat, boolean paramBoolean)
  {
    Rect localRect = this.mTempRect;
    int n = getHeight();
    int i = this.mCurrText.getLeft();
    int i1 = this.mTabPadding;
    int m = this.mCurrText.getRight();
    int j = this.mTabPadding;
    int k = n - this.mIndicatorHeight;
    localRect.set(i - i1, k, m + j, n);
    super.updateTextPositions(paramInt, paramFloat, paramBoolean);
    this.mTabAlpha = ((int)(Math.abs(paramFloat - 0.5F) * 2.0F * 255.0F));
    localRect.union(this.mCurrText.getLeft() - this.mTabPadding, k, this.mCurrText.getRight() + this.mTabPadding, n);
    invalidate(localRect);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/viewpager/widget/PagerTabStrip.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */