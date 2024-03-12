package androidx.cardview.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import androidx.cardview.R.attr;
import androidx.cardview.R.color;
import androidx.cardview.R.style;
import androidx.cardview.R.styleable;

public class CardView
  extends FrameLayout
{
  private static final int[] COLOR_BACKGROUND_ATTR = { 16842801 };
  private static final CardViewImpl IMPL;
  private final CardViewDelegate mCardViewDelegate;
  private boolean mCompatPadding;
  final Rect mContentPadding;
  private boolean mPreventCornerOverlap;
  final Rect mShadowBounds;
  int mUserSetMinHeight;
  int mUserSetMinWidth;
  
  static
  {
    if (Build.VERSION.SDK_INT >= 21) {
      IMPL = new CardViewApi21Impl();
    } else if (Build.VERSION.SDK_INT >= 17) {
      IMPL = new CardViewApi17Impl();
    } else {
      IMPL = new CardViewBaseImpl();
    }
    IMPL.initStatic();
  }
  
  public CardView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public CardView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.cardViewStyle);
  }
  
  public CardView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    Rect localRect = new Rect();
    this.mContentPadding = localRect;
    this.mShadowBounds = new Rect();
    CardViewDelegate local1 = new CardViewDelegate()
    {
      private Drawable mCardBackground;
      
      public Drawable getCardBackground()
      {
        return this.mCardBackground;
      }
      
      public View getCardView()
      {
        return CardView.this;
      }
      
      public boolean getPreventCornerOverlap()
      {
        return CardView.this.getPreventCornerOverlap();
      }
      
      public boolean getUseCompatPadding()
      {
        return CardView.this.getUseCompatPadding();
      }
      
      public void setCardBackground(Drawable paramAnonymousDrawable)
      {
        this.mCardBackground = paramAnonymousDrawable;
        CardView.this.setBackgroundDrawable(paramAnonymousDrawable);
      }
      
      public void setMinWidthHeightInternal(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        if (paramAnonymousInt1 > CardView.this.mUserSetMinWidth) {
          CardView.this.setMinimumWidth(paramAnonymousInt1);
        }
        if (paramAnonymousInt2 > CardView.this.mUserSetMinHeight) {
          CardView.this.setMinimumHeight(paramAnonymousInt2);
        }
      }
      
      public void setShadowPadding(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4)
      {
        CardView.this.mShadowBounds.set(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3, paramAnonymousInt4);
        CardView localCardView = CardView.this;
        localCardView.setPadding(localCardView.mContentPadding.left + paramAnonymousInt1, CardView.this.mContentPadding.top + paramAnonymousInt2, CardView.this.mContentPadding.right + paramAnonymousInt3, CardView.this.mContentPadding.bottom + paramAnonymousInt4);
      }
    };
    this.mCardViewDelegate = local1;
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.CardView, paramInt, R.style.CardView);
    if (localTypedArray.hasValue(R.styleable.CardView_cardBackgroundColor))
    {
      paramAttributeSet = localTypedArray.getColorStateList(R.styleable.CardView_cardBackgroundColor);
    }
    else
    {
      paramAttributeSet = getContext().obtainStyledAttributes(COLOR_BACKGROUND_ATTR);
      paramInt = paramAttributeSet.getColor(0, 0);
      paramAttributeSet.recycle();
      paramAttributeSet = new float[3];
      Color.colorToHSV(paramInt, paramAttributeSet);
      if (paramAttributeSet[2] > 0.5F) {
        paramInt = getResources().getColor(R.color.cardview_light_background);
      } else {
        paramInt = getResources().getColor(R.color.cardview_dark_background);
      }
      paramAttributeSet = ColorStateList.valueOf(paramInt);
    }
    float f3 = localTypedArray.getDimension(R.styleable.CardView_cardCornerRadius, 0.0F);
    float f2 = localTypedArray.getDimension(R.styleable.CardView_cardElevation, 0.0F);
    float f1 = localTypedArray.getDimension(R.styleable.CardView_cardMaxElevation, 0.0F);
    this.mCompatPadding = localTypedArray.getBoolean(R.styleable.CardView_cardUseCompatPadding, false);
    this.mPreventCornerOverlap = localTypedArray.getBoolean(R.styleable.CardView_cardPreventCornerOverlap, true);
    paramInt = localTypedArray.getDimensionPixelSize(R.styleable.CardView_contentPadding, 0);
    localRect.left = localTypedArray.getDimensionPixelSize(R.styleable.CardView_contentPaddingLeft, paramInt);
    localRect.top = localTypedArray.getDimensionPixelSize(R.styleable.CardView_contentPaddingTop, paramInt);
    localRect.right = localTypedArray.getDimensionPixelSize(R.styleable.CardView_contentPaddingRight, paramInt);
    localRect.bottom = localTypedArray.getDimensionPixelSize(R.styleable.CardView_contentPaddingBottom, paramInt);
    if (f2 > f1) {
      f1 = f2;
    }
    this.mUserSetMinWidth = localTypedArray.getDimensionPixelSize(R.styleable.CardView_android_minWidth, 0);
    this.mUserSetMinHeight = localTypedArray.getDimensionPixelSize(R.styleable.CardView_android_minHeight, 0);
    localTypedArray.recycle();
    IMPL.initialize(local1, paramContext, paramAttributeSet, f3, f2, f1);
  }
  
  public ColorStateList getCardBackgroundColor()
  {
    return IMPL.getBackgroundColor(this.mCardViewDelegate);
  }
  
  public float getCardElevation()
  {
    return IMPL.getElevation(this.mCardViewDelegate);
  }
  
  public int getContentPaddingBottom()
  {
    return this.mContentPadding.bottom;
  }
  
  public int getContentPaddingLeft()
  {
    return this.mContentPadding.left;
  }
  
  public int getContentPaddingRight()
  {
    return this.mContentPadding.right;
  }
  
  public int getContentPaddingTop()
  {
    return this.mContentPadding.top;
  }
  
  public float getMaxCardElevation()
  {
    return IMPL.getMaxElevation(this.mCardViewDelegate);
  }
  
  public boolean getPreventCornerOverlap()
  {
    return this.mPreventCornerOverlap;
  }
  
  public float getRadius()
  {
    return IMPL.getRadius(this.mCardViewDelegate);
  }
  
  public boolean getUseCompatPadding()
  {
    return this.mCompatPadding;
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    CardViewImpl localCardViewImpl = IMPL;
    if (!(localCardViewImpl instanceof CardViewApi21Impl))
    {
      int i = View.MeasureSpec.getMode(paramInt1);
      int j;
      switch (i)
      {
      default: 
        break;
      case 1073741824: 
      case -2147483648: 
        j = (int)Math.ceil(localCardViewImpl.getMinWidth(this.mCardViewDelegate));
        paramInt1 = View.MeasureSpec.makeMeasureSpec(Math.max(j, View.MeasureSpec.getSize(paramInt1)), i);
      }
      i = View.MeasureSpec.getMode(paramInt2);
      switch (i)
      {
      default: 
        break;
      case 1073741824: 
      case -2147483648: 
        j = (int)Math.ceil(localCardViewImpl.getMinHeight(this.mCardViewDelegate));
        paramInt2 = View.MeasureSpec.makeMeasureSpec(Math.max(j, View.MeasureSpec.getSize(paramInt2)), i);
      }
      super.onMeasure(paramInt1, paramInt2);
    }
    else
    {
      super.onMeasure(paramInt1, paramInt2);
    }
  }
  
  public void setCardBackgroundColor(int paramInt)
  {
    IMPL.setBackgroundColor(this.mCardViewDelegate, ColorStateList.valueOf(paramInt));
  }
  
  public void setCardBackgroundColor(ColorStateList paramColorStateList)
  {
    IMPL.setBackgroundColor(this.mCardViewDelegate, paramColorStateList);
  }
  
  public void setCardElevation(float paramFloat)
  {
    IMPL.setElevation(this.mCardViewDelegate, paramFloat);
  }
  
  public void setContentPadding(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mContentPadding.set(paramInt1, paramInt2, paramInt3, paramInt4);
    IMPL.updatePadding(this.mCardViewDelegate);
  }
  
  public void setMaxCardElevation(float paramFloat)
  {
    IMPL.setMaxElevation(this.mCardViewDelegate, paramFloat);
  }
  
  public void setMinimumHeight(int paramInt)
  {
    this.mUserSetMinHeight = paramInt;
    super.setMinimumHeight(paramInt);
  }
  
  public void setMinimumWidth(int paramInt)
  {
    this.mUserSetMinWidth = paramInt;
    super.setMinimumWidth(paramInt);
  }
  
  public void setPadding(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
  
  public void setPaddingRelative(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
  
  public void setPreventCornerOverlap(boolean paramBoolean)
  {
    if (paramBoolean != this.mPreventCornerOverlap)
    {
      this.mPreventCornerOverlap = paramBoolean;
      IMPL.onPreventCornerOverlapChanged(this.mCardViewDelegate);
    }
  }
  
  public void setRadius(float paramFloat)
  {
    IMPL.setRadius(this.mCardViewDelegate, paramFloat);
  }
  
  public void setUseCompatPadding(boolean paramBoolean)
  {
    if (this.mCompatPadding != paramBoolean)
    {
      this.mCompatPadding = paramBoolean;
      IMPL.onCompatPaddingChanged(this.mCardViewDelegate);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/cardview/widget/CardView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */