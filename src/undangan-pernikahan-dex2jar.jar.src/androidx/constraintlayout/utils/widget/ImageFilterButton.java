package androidx.constraintlayout.utils.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView.ScaleType;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.constraintlayout.widget.R.styleable;

public class ImageFilterButton
  extends AppCompatImageButton
{
  private Drawable mAltDrawable = null;
  private float mCrossfade = 0.0F;
  private Drawable mDrawable = null;
  private ImageFilterView.ImageMatrix mImageMatrix = new ImageFilterView.ImageMatrix();
  LayerDrawable mLayer;
  Drawable[] mLayers = new Drawable[2];
  private boolean mOverlay = true;
  private float mPanX = NaN.0F;
  private float mPanY = NaN.0F;
  private Path mPath;
  RectF mRect;
  private float mRotate = NaN.0F;
  private float mRound = NaN.0F;
  private float mRoundPercent = 0.0F;
  ViewOutlineProvider mViewOutlineProvider;
  private float mZoom = NaN.0F;
  
  public ImageFilterButton(Context paramContext)
  {
    super(paramContext);
    init(paramContext, null);
  }
  
  public ImageFilterButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramContext, paramAttributeSet);
  }
  
  public ImageFilterButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext, paramAttributeSet);
  }
  
  private void init(Context paramContext, AttributeSet paramAttributeSet)
  {
    setPadding(0, 0, 0, 0);
    if (paramAttributeSet != null)
    {
      paramContext = getContext().obtainStyledAttributes(paramAttributeSet, R.styleable.ImageFilterView);
      int j = paramContext.getIndexCount();
      this.mAltDrawable = paramContext.getDrawable(R.styleable.ImageFilterView_altSrc);
      for (int i = 0; i < j; i++)
      {
        int k = paramContext.getIndex(i);
        if (k == R.styleable.ImageFilterView_crossfade) {
          this.mCrossfade = paramContext.getFloat(k, 0.0F);
        } else if (k == R.styleable.ImageFilterView_warmth) {
          setWarmth(paramContext.getFloat(k, 0.0F));
        } else if (k == R.styleable.ImageFilterView_saturation) {
          setSaturation(paramContext.getFloat(k, 0.0F));
        } else if (k == R.styleable.ImageFilterView_contrast) {
          setContrast(paramContext.getFloat(k, 0.0F));
        } else if (k == R.styleable.ImageFilterView_round)
        {
          if (Build.VERSION.SDK_INT >= 21) {
            setRound(paramContext.getDimension(k, 0.0F));
          }
        }
        else if (k == R.styleable.ImageFilterView_roundPercent)
        {
          if (Build.VERSION.SDK_INT >= 21) {
            setRoundPercent(paramContext.getFloat(k, 0.0F));
          }
        }
        else if (k == R.styleable.ImageFilterView_overlay) {
          setOverlay(paramContext.getBoolean(k, this.mOverlay));
        } else if (k == R.styleable.ImageFilterView_imagePanX) {
          setImagePanX(paramContext.getFloat(k, this.mPanX));
        } else if (k == R.styleable.ImageFilterView_imagePanY) {
          setImagePanY(paramContext.getFloat(k, this.mPanY));
        } else if (k == R.styleable.ImageFilterView_imageRotate) {
          setImageRotate(paramContext.getFloat(k, this.mRotate));
        } else if (k == R.styleable.ImageFilterView_imageZoom) {
          setImageZoom(paramContext.getFloat(k, this.mZoom));
        }
      }
      paramContext.recycle();
      paramContext = getDrawable();
      this.mDrawable = paramContext;
      if ((this.mAltDrawable != null) && (paramContext != null))
      {
        paramContext = this.mLayers;
        paramAttributeSet = getDrawable().mutate();
        this.mDrawable = paramAttributeSet;
        paramContext[0] = paramAttributeSet;
        this.mLayers[1] = this.mAltDrawable.mutate();
        paramContext = new LayerDrawable(this.mLayers);
        this.mLayer = paramContext;
        paramContext.getDrawable(1).setAlpha((int)(this.mCrossfade * 255.0F));
        if (!this.mOverlay) {
          this.mLayer.getDrawable(0).setAlpha((int)((1.0F - this.mCrossfade) * 255.0F));
        }
        super.setImageDrawable(this.mLayer);
      }
      else
      {
        paramAttributeSet = getDrawable();
        this.mDrawable = paramAttributeSet;
        if (paramAttributeSet != null)
        {
          paramContext = this.mLayers;
          paramAttributeSet = paramAttributeSet.mutate();
          this.mDrawable = paramAttributeSet;
          paramContext[0] = paramAttributeSet;
        }
      }
    }
  }
  
  private void setMatrix()
  {
    if ((Float.isNaN(this.mPanX)) && (Float.isNaN(this.mPanY)) && (Float.isNaN(this.mZoom)) && (Float.isNaN(this.mRotate))) {
      return;
    }
    boolean bool = Float.isNaN(this.mPanX);
    float f4 = 0.0F;
    float f1;
    if (bool) {
      f1 = 0.0F;
    } else {
      f1 = this.mPanX;
    }
    float f2;
    if (Float.isNaN(this.mPanY)) {
      f2 = 0.0F;
    } else {
      f2 = this.mPanY;
    }
    if (Float.isNaN(this.mZoom)) {
      f3 = 1.0F;
    } else {
      f3 = this.mZoom;
    }
    if (!Float.isNaN(this.mRotate)) {
      f4 = this.mRotate;
    }
    Matrix localMatrix = new Matrix();
    localMatrix.reset();
    float f8 = getDrawable().getIntrinsicWidth();
    float f9 = getDrawable().getIntrinsicHeight();
    float f6 = getWidth();
    float f7 = getHeight();
    float f5;
    if (f8 * f7 < f9 * f6) {
      f5 = f6 / f8;
    } else {
      f5 = f7 / f9;
    }
    float f3 = f5 * f3;
    localMatrix.postScale(f3, f3);
    localMatrix.postTranslate(((f6 - f3 * f8) * f1 + f6 - f3 * f8) * 0.5F, ((f7 - f3 * f9) * f2 + f7 - f3 * f9) * 0.5F);
    localMatrix.postRotate(f4, f6 / 2.0F, f7 / 2.0F);
    setImageMatrix(localMatrix);
    setScaleType(ImageView.ScaleType.MATRIX);
  }
  
  private void setOverlay(boolean paramBoolean)
  {
    this.mOverlay = paramBoolean;
  }
  
  private void updateViewMatrix()
  {
    if ((Float.isNaN(this.mPanX)) && (Float.isNaN(this.mPanY)) && (Float.isNaN(this.mZoom)) && (Float.isNaN(this.mRotate)))
    {
      setScaleType(ImageView.ScaleType.FIT_CENTER);
      return;
    }
    setMatrix();
  }
  
  public void draw(Canvas paramCanvas)
  {
    int j = 0;
    int i = j;
    if (Build.VERSION.SDK_INT < 21)
    {
      i = j;
      if (this.mRound != 0.0F)
      {
        i = j;
        if (this.mPath != null)
        {
          i = 1;
          paramCanvas.save();
          paramCanvas.clipPath(this.mPath);
        }
      }
    }
    super.draw(paramCanvas);
    if (i != 0) {
      paramCanvas.restore();
    }
  }
  
  public float getContrast()
  {
    return this.mImageMatrix.mContrast;
  }
  
  public float getCrossfade()
  {
    return this.mCrossfade;
  }
  
  public float getImagePanX()
  {
    return this.mPanX;
  }
  
  public float getImagePanY()
  {
    return this.mPanY;
  }
  
  public float getImageRotate()
  {
    return this.mRotate;
  }
  
  public float getImageZoom()
  {
    return this.mZoom;
  }
  
  public float getRound()
  {
    return this.mRound;
  }
  
  public float getRoundPercent()
  {
    return this.mRoundPercent;
  }
  
  public float getSaturation()
  {
    return this.mImageMatrix.mSaturation;
  }
  
  public float getWarmth()
  {
    return this.mImageMatrix.mWarmth;
  }
  
  public void layout(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.layout(paramInt1, paramInt2, paramInt3, paramInt4);
    setMatrix();
  }
  
  public void setAltImageResource(int paramInt)
  {
    Drawable localDrawable = AppCompatResources.getDrawable(getContext(), paramInt).mutate();
    this.mAltDrawable = localDrawable;
    Object localObject = this.mLayers;
    localObject[0] = this.mDrawable;
    localObject[1] = localDrawable;
    localObject = new LayerDrawable(this.mLayers);
    this.mLayer = ((LayerDrawable)localObject);
    super.setImageDrawable((Drawable)localObject);
    setCrossfade(this.mCrossfade);
  }
  
  public void setBrightness(float paramFloat)
  {
    this.mImageMatrix.mBrightness = paramFloat;
    this.mImageMatrix.updateMatrix(this);
  }
  
  public void setContrast(float paramFloat)
  {
    this.mImageMatrix.mContrast = paramFloat;
    this.mImageMatrix.updateMatrix(this);
  }
  
  public void setCrossfade(float paramFloat)
  {
    this.mCrossfade = paramFloat;
    if (this.mLayers != null)
    {
      if (!this.mOverlay) {
        this.mLayer.getDrawable(0).setAlpha((int)((1.0F - this.mCrossfade) * 255.0F));
      }
      this.mLayer.getDrawable(1).setAlpha((int)(this.mCrossfade * 255.0F));
      super.setImageDrawable(this.mLayer);
    }
  }
  
  public void setImageDrawable(Drawable paramDrawable)
  {
    if ((this.mAltDrawable != null) && (paramDrawable != null))
    {
      paramDrawable = paramDrawable.mutate();
      this.mDrawable = paramDrawable;
      Drawable[] arrayOfDrawable = this.mLayers;
      arrayOfDrawable[0] = paramDrawable;
      arrayOfDrawable[1] = this.mAltDrawable;
      paramDrawable = new LayerDrawable(this.mLayers);
      this.mLayer = paramDrawable;
      super.setImageDrawable(paramDrawable);
      setCrossfade(this.mCrossfade);
    }
    else
    {
      super.setImageDrawable(paramDrawable);
    }
  }
  
  public void setImagePanX(float paramFloat)
  {
    this.mPanX = paramFloat;
    updateViewMatrix();
  }
  
  public void setImagePanY(float paramFloat)
  {
    this.mPanY = paramFloat;
    updateViewMatrix();
  }
  
  public void setImageResource(int paramInt)
  {
    if (this.mAltDrawable != null)
    {
      Drawable localDrawable = AppCompatResources.getDrawable(getContext(), paramInt).mutate();
      this.mDrawable = localDrawable;
      Object localObject = this.mLayers;
      localObject[0] = localDrawable;
      localObject[1] = this.mAltDrawable;
      localObject = new LayerDrawable(this.mLayers);
      this.mLayer = ((LayerDrawable)localObject);
      super.setImageDrawable((Drawable)localObject);
      setCrossfade(this.mCrossfade);
    }
    else
    {
      super.setImageResource(paramInt);
    }
  }
  
  public void setImageRotate(float paramFloat)
  {
    this.mRotate = paramFloat;
    updateViewMatrix();
  }
  
  public void setImageZoom(float paramFloat)
  {
    this.mZoom = paramFloat;
    updateViewMatrix();
  }
  
  public void setRound(float paramFloat)
  {
    if (Float.isNaN(paramFloat))
    {
      this.mRound = paramFloat;
      paramFloat = this.mRoundPercent;
      this.mRoundPercent = -1.0F;
      setRoundPercent(paramFloat);
      return;
    }
    int i;
    if (this.mRound != paramFloat) {
      i = 1;
    } else {
      i = 0;
    }
    this.mRound = paramFloat;
    if (paramFloat != 0.0F)
    {
      if (this.mPath == null) {
        this.mPath = new Path();
      }
      if (this.mRect == null) {
        this.mRect = new RectF();
      }
      if (Build.VERSION.SDK_INT >= 21)
      {
        if (this.mViewOutlineProvider == null)
        {
          localObject = new ViewOutlineProvider()
          {
            public void getOutline(View paramAnonymousView, Outline paramAnonymousOutline)
            {
              paramAnonymousOutline.setRoundRect(0, 0, ImageFilterButton.this.getWidth(), ImageFilterButton.this.getHeight(), ImageFilterButton.this.mRound);
            }
          };
          this.mViewOutlineProvider = ((ViewOutlineProvider)localObject);
          setOutlineProvider((ViewOutlineProvider)localObject);
        }
        setClipToOutline(true);
      }
      int j = getWidth();
      int k = getHeight();
      this.mRect.set(0.0F, 0.0F, j, k);
      this.mPath.reset();
      Path localPath = this.mPath;
      Object localObject = this.mRect;
      paramFloat = this.mRound;
      localPath.addRoundRect((RectF)localObject, paramFloat, paramFloat, Path.Direction.CW);
    }
    else if (Build.VERSION.SDK_INT >= 21)
    {
      setClipToOutline(false);
    }
    if ((i != 0) && (Build.VERSION.SDK_INT >= 21)) {
      invalidateOutline();
    }
  }
  
  public void setRoundPercent(float paramFloat)
  {
    int i;
    if (this.mRoundPercent != paramFloat) {
      i = 1;
    } else {
      i = 0;
    }
    this.mRoundPercent = paramFloat;
    if (paramFloat != 0.0F)
    {
      if (this.mPath == null) {
        this.mPath = new Path();
      }
      if (this.mRect == null) {
        this.mRect = new RectF();
      }
      if (Build.VERSION.SDK_INT >= 21)
      {
        if (this.mViewOutlineProvider == null)
        {
          ViewOutlineProvider local1 = new ViewOutlineProvider()
          {
            public void getOutline(View paramAnonymousView, Outline paramAnonymousOutline)
            {
              int i = ImageFilterButton.this.getWidth();
              int j = ImageFilterButton.this.getHeight();
              paramAnonymousOutline.setRoundRect(0, 0, i, j, Math.min(i, j) * ImageFilterButton.this.mRoundPercent / 2.0F);
            }
          };
          this.mViewOutlineProvider = local1;
          setOutlineProvider(local1);
        }
        setClipToOutline(true);
      }
      int j = getWidth();
      int k = getHeight();
      paramFloat = Math.min(j, k) * this.mRoundPercent / 2.0F;
      this.mRect.set(0.0F, 0.0F, j, k);
      this.mPath.reset();
      this.mPath.addRoundRect(this.mRect, paramFloat, paramFloat, Path.Direction.CW);
    }
    else if (Build.VERSION.SDK_INT >= 21)
    {
      setClipToOutline(false);
    }
    if ((i != 0) && (Build.VERSION.SDK_INT >= 21)) {
      invalidateOutline();
    }
  }
  
  public void setSaturation(float paramFloat)
  {
    this.mImageMatrix.mSaturation = paramFloat;
    this.mImageMatrix.updateMatrix(this);
  }
  
  public void setWarmth(float paramFloat)
  {
    this.mImageMatrix.mWarmth = paramFloat;
    this.mImageMatrix.updateMatrix(this);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/utils/widget/ImageFilterButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */