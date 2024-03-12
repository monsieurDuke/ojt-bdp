package androidx.constraintlayout.utils.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
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
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.R.styleable;

public class ImageFilterView
  extends AppCompatImageView
{
  private Drawable mAltDrawable = null;
  private float mCrossfade = 0.0F;
  private Drawable mDrawable = null;
  private ImageMatrix mImageMatrix = new ImageMatrix();
  LayerDrawable mLayer;
  Drawable[] mLayers = new Drawable[2];
  private boolean mOverlay = true;
  float mPanX = NaN.0F;
  float mPanY = NaN.0F;
  private Path mPath;
  RectF mRect;
  float mRotate = NaN.0F;
  private float mRound = NaN.0F;
  private float mRoundPercent = 0.0F;
  ViewOutlineProvider mViewOutlineProvider;
  float mZoom = NaN.0F;
  
  public ImageFilterView(Context paramContext)
  {
    super(paramContext);
    init(paramContext, null);
  }
  
  public ImageFilterView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramContext, paramAttributeSet);
  }
  
  public ImageFilterView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext, paramAttributeSet);
  }
  
  private void init(Context paramContext, AttributeSet paramAttributeSet)
  {
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
        } else if (k == R.styleable.ImageFilterView_brightness) {
          setBrightness(paramContext.getFloat(k, 0.0F));
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
        paramAttributeSet = this.mLayers;
        paramContext = getDrawable().mutate();
        this.mDrawable = paramContext;
        paramAttributeSet[0] = paramContext;
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
    float f6 = getDrawable().getIntrinsicHeight();
    float f7 = getWidth();
    float f9 = getHeight();
    float f5;
    if (f8 * f9 < f6 * f7) {
      f5 = f7 / f8;
    } else {
      f5 = f9 / f6;
    }
    float f3 = f5 * f3;
    localMatrix.postScale(f3, f3);
    localMatrix.postTranslate(((f7 - f3 * f8) * f1 + f7 - f3 * f8) * 0.5F, ((f9 - f3 * f6) * f2 + f9 - f3 * f6) * 0.5F);
    localMatrix.postRotate(f4, f7 / 2.0F, f9 / 2.0F);
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
      if (this.mRoundPercent != 0.0F)
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
  
  public float getBrightness()
  {
    return this.mImageMatrix.mBrightness;
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
      Object localObject = AppCompatResources.getDrawable(getContext(), paramInt).mutate();
      this.mDrawable = ((Drawable)localObject);
      Drawable[] arrayOfDrawable = this.mLayers;
      arrayOfDrawable[0] = localObject;
      arrayOfDrawable[1] = this.mAltDrawable;
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
              paramAnonymousOutline.setRoundRect(0, 0, ImageFilterView.this.getWidth(), ImageFilterView.this.getHeight(), ImageFilterView.this.mRound);
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
              int i = ImageFilterView.this.getWidth();
              int j = ImageFilterView.this.getHeight();
              paramAnonymousOutline.setRoundRect(0, 0, i, j, Math.min(i, j) * ImageFilterView.this.mRoundPercent / 2.0F);
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
  
  static class ImageMatrix
  {
    float[] m = new float[20];
    float mBrightness = 1.0F;
    ColorMatrix mColorMatrix = new ColorMatrix();
    float mContrast = 1.0F;
    float mSaturation = 1.0F;
    ColorMatrix mTmpColorMatrix = new ColorMatrix();
    float mWarmth = 1.0F;
    
    private void brightness(float paramFloat)
    {
      float[] arrayOfFloat = this.m;
      arrayOfFloat[0] = paramFloat;
      arrayOfFloat[1] = 0.0F;
      arrayOfFloat[2] = 0.0F;
      arrayOfFloat[3] = 0.0F;
      arrayOfFloat[4] = 0.0F;
      arrayOfFloat[5] = 0.0F;
      arrayOfFloat[6] = paramFloat;
      arrayOfFloat[7] = 0.0F;
      arrayOfFloat[8] = 0.0F;
      arrayOfFloat[9] = 0.0F;
      arrayOfFloat[10] = 0.0F;
      arrayOfFloat[11] = 0.0F;
      arrayOfFloat[12] = paramFloat;
      arrayOfFloat[13] = 0.0F;
      arrayOfFloat[14] = 0.0F;
      arrayOfFloat[15] = 0.0F;
      arrayOfFloat[16] = 0.0F;
      arrayOfFloat[17] = 0.0F;
      arrayOfFloat[18] = 1.0F;
      arrayOfFloat[19] = 0.0F;
    }
    
    private void saturation(float paramFloat)
    {
      float f3 = 1.0F - paramFloat;
      float f2 = 0.2999F * f3;
      float f1 = 0.587F * f3;
      f3 = 0.114F * f3;
      float[] arrayOfFloat = this.m;
      arrayOfFloat[0] = (f2 + paramFloat);
      arrayOfFloat[1] = f1;
      arrayOfFloat[2] = f3;
      arrayOfFloat[3] = 0.0F;
      arrayOfFloat[4] = 0.0F;
      arrayOfFloat[5] = f2;
      arrayOfFloat[6] = (f1 + paramFloat);
      arrayOfFloat[7] = f3;
      arrayOfFloat[8] = 0.0F;
      arrayOfFloat[9] = 0.0F;
      arrayOfFloat[10] = f2;
      arrayOfFloat[11] = f1;
      arrayOfFloat[12] = (f3 + paramFloat);
      arrayOfFloat[13] = 0.0F;
      arrayOfFloat[14] = 0.0F;
      arrayOfFloat[15] = 0.0F;
      arrayOfFloat[16] = 0.0F;
      arrayOfFloat[17] = 0.0F;
      arrayOfFloat[18] = 1.0F;
      arrayOfFloat[19] = 0.0F;
    }
    
    private void warmth(float paramFloat)
    {
      if (paramFloat <= 0.0F) {
        paramFloat = 0.01F;
      }
      paramFloat = 5000.0F / paramFloat / 100.0F;
      if (paramFloat > 66.0F)
      {
        f1 = paramFloat - 60.0F;
        f2 = (float)Math.pow(f1, -0.13320475816726685D) * 329.69873F;
        f1 = (float)Math.pow(f1, 0.07551484555006027D) * 288.12216F;
      }
      else
      {
        f1 = (float)Math.log(paramFloat) * 99.4708F - 161.11957F;
        f2 = 255.0F;
      }
      if (paramFloat < 66.0F)
      {
        if (paramFloat > 19.0F) {
          paramFloat = (float)Math.log(paramFloat - 10.0F) * 138.51773F - 305.0448F;
        } else {
          paramFloat = 0.0F;
        }
      }
      else {
        paramFloat = 255.0F;
      }
      float f3 = Math.min(255.0F, Math.max(f2, 0.0F));
      float f4 = Math.min(255.0F, Math.max(f1, 0.0F));
      float f5 = Math.min(255.0F, Math.max(paramFloat, 0.0F));
      paramFloat = 5000.0F / 100.0F;
      if (paramFloat > 66.0F)
      {
        f1 = paramFloat - 60.0F;
        f2 = (float)Math.pow(f1, -0.13320475816726685D) * 329.69873F;
        f1 = (float)Math.pow(f1, 0.07551484555006027D) * 288.12216F;
      }
      else
      {
        f1 = (float)Math.log(paramFloat) * 99.4708F - 161.11957F;
        f2 = 255.0F;
      }
      if (paramFloat < 66.0F)
      {
        if (paramFloat > 19.0F) {
          paramFloat = (float)Math.log(paramFloat - 10.0F) * 138.51773F - 305.0448F;
        } else {
          paramFloat = 0.0F;
        }
      }
      else {
        paramFloat = 255.0F;
      }
      float f2 = Math.min(255.0F, Math.max(f2, 0.0F));
      float f1 = Math.min(255.0F, Math.max(f1, 0.0F));
      paramFloat = Math.min(255.0F, Math.max(paramFloat, 0.0F));
      f2 = f3 / f2;
      f1 = f4 / f1;
      paramFloat = f5 / paramFloat;
      float[] arrayOfFloat = this.m;
      arrayOfFloat[0] = f2;
      arrayOfFloat[1] = 0.0F;
      arrayOfFloat[2] = 0.0F;
      arrayOfFloat[3] = 0.0F;
      arrayOfFloat[4] = 0.0F;
      arrayOfFloat[5] = 0.0F;
      arrayOfFloat[6] = f1;
      arrayOfFloat[7] = 0.0F;
      arrayOfFloat[8] = 0.0F;
      arrayOfFloat[9] = 0.0F;
      arrayOfFloat[10] = 0.0F;
      arrayOfFloat[11] = 0.0F;
      arrayOfFloat[12] = paramFloat;
      arrayOfFloat[13] = 0.0F;
      arrayOfFloat[14] = 0.0F;
      arrayOfFloat[15] = 0.0F;
      arrayOfFloat[16] = 0.0F;
      arrayOfFloat[17] = 0.0F;
      arrayOfFloat[18] = 1.0F;
      arrayOfFloat[19] = 0.0F;
    }
    
    void updateMatrix(ImageView paramImageView)
    {
      this.mColorMatrix.reset();
      int i = 0;
      float f = this.mSaturation;
      if (f != 1.0F)
      {
        saturation(f);
        this.mColorMatrix.set(this.m);
        i = 1;
      }
      f = this.mContrast;
      if (f != 1.0F)
      {
        this.mTmpColorMatrix.setScale(f, f, f, 1.0F);
        this.mColorMatrix.postConcat(this.mTmpColorMatrix);
        i = 1;
      }
      f = this.mWarmth;
      if (f != 1.0F)
      {
        warmth(f);
        this.mTmpColorMatrix.set(this.m);
        this.mColorMatrix.postConcat(this.mTmpColorMatrix);
        i = 1;
      }
      f = this.mBrightness;
      if (f != 1.0F)
      {
        brightness(f);
        this.mTmpColorMatrix.set(this.m);
        this.mColorMatrix.postConcat(this.mTmpColorMatrix);
        i = 1;
      }
      if (i != 0) {
        paramImageView.setColorFilter(new ColorMatrixColorFilter(this.mColorMatrix));
      } else {
        paramImageView.clearColorFilter();
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/utils/widget/ImageFilterView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */