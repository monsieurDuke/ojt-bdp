package androidx.constraintlayout.utils.widget;

import android.content.Context;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.text.Layout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewOutlineProvider;
import androidx.appcompat.R.attr;
import androidx.constraintlayout.motion.widget.Debug;
import androidx.constraintlayout.motion.widget.FloatLayout;
import androidx.constraintlayout.widget.R.styleable;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class MotionLabel
  extends View
  implements FloatLayout
{
  private static final int MONOSPACE = 3;
  private static final int SANS = 1;
  private static final int SERIF = 2;
  static String TAG = "MotionLabel";
  private boolean mAutoSize = false;
  private int mAutoSizeTextType = 0;
  float mBackgroundPanX = NaN.0F;
  float mBackgroundPanY = NaN.0F;
  private float mBaseTextSize = NaN.0F;
  private float mDeltaLeft;
  private float mFloatHeight;
  private float mFloatWidth;
  private String mFontFamily;
  private int mGravity = 8388659;
  private Layout mLayout;
  boolean mNotBuilt = true;
  Matrix mOutlinePositionMatrix;
  private int mPaddingBottom = 1;
  private int mPaddingLeft = 1;
  private int mPaddingRight = 1;
  private int mPaddingTop = 1;
  TextPaint mPaint = new TextPaint();
  Path mPath = new Path();
  RectF mRect;
  float mRotate = NaN.0F;
  private float mRound = NaN.0F;
  private float mRoundPercent = 0.0F;
  private int mStyleIndex;
  Paint mTempPaint;
  Rect mTempRect;
  private String mText = "Hello World";
  private Drawable mTextBackground;
  private Bitmap mTextBackgroundBitmap;
  private Rect mTextBounds = new Rect();
  private int mTextFillColor = 65535;
  private int mTextOutlineColor = 65535;
  private float mTextOutlineThickness = 0.0F;
  private float mTextPanX = 0.0F;
  private float mTextPanY = 0.0F;
  private BitmapShader mTextShader;
  private Matrix mTextShaderMatrix;
  private float mTextSize = 48.0F;
  private int mTextureEffect = 0;
  private float mTextureHeight = NaN.0F;
  private float mTextureWidth = NaN.0F;
  private CharSequence mTransformed;
  private int mTypefaceIndex;
  private boolean mUseOutline = false;
  ViewOutlineProvider mViewOutlineProvider;
  float mZoom = NaN.0F;
  Paint paintCache = new Paint();
  float paintTextSize;
  
  public MotionLabel(Context paramContext)
  {
    super(paramContext);
    init(paramContext, null);
  }
  
  public MotionLabel(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramContext, paramAttributeSet);
  }
  
  public MotionLabel(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext, paramAttributeSet);
  }
  
  private void adjustTexture(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    if (this.mTextShaderMatrix == null) {
      return;
    }
    this.mFloatWidth = (paramFloat3 - paramFloat1);
    this.mFloatHeight = (paramFloat4 - paramFloat2);
    updateShaderMatrix();
  }
  
  private float getHorizontalOffset()
  {
    float f1;
    if (Float.isNaN(this.mBaseTextSize)) {
      f1 = 1.0F;
    } else {
      f1 = this.mTextSize / this.mBaseTextSize;
    }
    TextPaint localTextPaint = this.mPaint;
    String str = this.mText;
    float f3 = localTextPaint.measureText(str, 0, str.length());
    float f2;
    if (Float.isNaN(this.mFloatWidth)) {
      f2 = getMeasuredWidth();
    } else {
      f2 = this.mFloatWidth;
    }
    return (f2 - getPaddingLeft() - getPaddingRight() - f3 * f1) * (this.mTextPanX + 1.0F) / 2.0F;
  }
  
  private float getVerticalOffset()
  {
    float f1;
    if (Float.isNaN(this.mBaseTextSize)) {
      f1 = 1.0F;
    } else {
      f1 = this.mTextSize / this.mBaseTextSize;
    }
    Paint.FontMetrics localFontMetrics = this.mPaint.getFontMetrics();
    float f2;
    if (Float.isNaN(this.mFloatHeight)) {
      f2 = getMeasuredHeight();
    } else {
      f2 = this.mFloatHeight;
    }
    return (f2 - getPaddingTop() - getPaddingBottom() - (localFontMetrics.descent - localFontMetrics.ascent) * f1) * (1.0F - this.mTextPanY) / 2.0F - localFontMetrics.ascent * f1;
  }
  
  private void init(Context paramContext, AttributeSet paramAttributeSet)
  {
    setUpTheme(paramContext, paramAttributeSet);
    if (paramAttributeSet != null)
    {
      paramContext = getContext().obtainStyledAttributes(paramAttributeSet, R.styleable.MotionLabel);
      int j = paramContext.getIndexCount();
      for (int i = 0; i < j; i++)
      {
        int k = paramContext.getIndex(i);
        if (k == R.styleable.MotionLabel_android_text)
        {
          setText(paramContext.getText(k));
        }
        else if (k == R.styleable.MotionLabel_android_fontFamily)
        {
          this.mFontFamily = paramContext.getString(k);
        }
        else if (k == R.styleable.MotionLabel_scaleFromTextSize)
        {
          this.mBaseTextSize = paramContext.getDimensionPixelSize(k, (int)this.mBaseTextSize);
        }
        else if (k == R.styleable.MotionLabel_android_textSize)
        {
          this.mTextSize = paramContext.getDimensionPixelSize(k, (int)this.mTextSize);
        }
        else if (k == R.styleable.MotionLabel_android_textStyle)
        {
          this.mStyleIndex = paramContext.getInt(k, this.mStyleIndex);
        }
        else if (k == R.styleable.MotionLabel_android_typeface)
        {
          this.mTypefaceIndex = paramContext.getInt(k, this.mTypefaceIndex);
        }
        else if (k == R.styleable.MotionLabel_android_textColor)
        {
          this.mTextFillColor = paramContext.getColor(k, this.mTextFillColor);
        }
        else if (k == R.styleable.MotionLabel_borderRound)
        {
          this.mRound = paramContext.getDimension(k, this.mRound);
          if (Build.VERSION.SDK_INT >= 21) {
            setRound(this.mRound);
          }
        }
        else if (k == R.styleable.MotionLabel_borderRoundPercent)
        {
          this.mRoundPercent = paramContext.getFloat(k, this.mRoundPercent);
          if (Build.VERSION.SDK_INT >= 21) {
            setRoundPercent(this.mRoundPercent);
          }
        }
        else if (k == R.styleable.MotionLabel_android_gravity)
        {
          setGravity(paramContext.getInt(k, -1));
        }
        else if (k == R.styleable.MotionLabel_android_autoSizeTextType)
        {
          this.mAutoSizeTextType = paramContext.getInt(k, 0);
        }
        else if (k == R.styleable.MotionLabel_textOutlineColor)
        {
          this.mTextOutlineColor = paramContext.getInt(k, this.mTextOutlineColor);
          this.mUseOutline = true;
        }
        else if (k == R.styleable.MotionLabel_textOutlineThickness)
        {
          this.mTextOutlineThickness = paramContext.getDimension(k, this.mTextOutlineThickness);
          this.mUseOutline = true;
        }
        else if (k == R.styleable.MotionLabel_textBackground)
        {
          this.mTextBackground = paramContext.getDrawable(k);
          this.mUseOutline = true;
        }
        else if (k == R.styleable.MotionLabel_textBackgroundPanX)
        {
          this.mBackgroundPanX = paramContext.getFloat(k, this.mBackgroundPanX);
        }
        else if (k == R.styleable.MotionLabel_textBackgroundPanY)
        {
          this.mBackgroundPanY = paramContext.getFloat(k, this.mBackgroundPanY);
        }
        else if (k == R.styleable.MotionLabel_textPanX)
        {
          this.mTextPanX = paramContext.getFloat(k, this.mTextPanX);
        }
        else if (k == R.styleable.MotionLabel_textPanY)
        {
          this.mTextPanY = paramContext.getFloat(k, this.mTextPanY);
        }
        else if (k == R.styleable.MotionLabel_textBackgroundRotate)
        {
          this.mRotate = paramContext.getFloat(k, this.mRotate);
        }
        else if (k == R.styleable.MotionLabel_textBackgroundZoom)
        {
          this.mZoom = paramContext.getFloat(k, this.mZoom);
        }
        else if (k == R.styleable.MotionLabel_textureHeight)
        {
          this.mTextureHeight = paramContext.getDimension(k, this.mTextureHeight);
        }
        else if (k == R.styleable.MotionLabel_textureWidth)
        {
          this.mTextureWidth = paramContext.getDimension(k, this.mTextureWidth);
        }
        else if (k == R.styleable.MotionLabel_textureEffect)
        {
          this.mTextureEffect = paramContext.getInt(k, this.mTextureEffect);
        }
      }
      paramContext.recycle();
    }
    setupTexture();
    setupPath();
  }
  
  private void setTypefaceFromAttrs(String paramString, int paramInt1, int paramInt2)
  {
    String str = null;
    if (paramString != null)
    {
      paramString = Typeface.create(paramString, paramInt2);
      str = paramString;
      if (paramString != null)
      {
        setTypeface(paramString);
        return;
      }
    }
    switch (paramInt1)
    {
    default: 
      paramString = str;
      break;
    case 3: 
      paramString = Typeface.MONOSPACE;
      break;
    case 2: 
      paramString = Typeface.SERIF;
      break;
    case 1: 
      paramString = Typeface.SANS_SERIF;
    }
    float f = 0.0F;
    boolean bool = false;
    if (paramInt2 > 0)
    {
      if (paramString == null) {
        paramString = Typeface.defaultFromStyle(paramInt2);
      } else {
        paramString = Typeface.create(paramString, paramInt2);
      }
      setTypeface(paramString);
      if (paramString != null) {
        paramInt1 = paramString.getStyle();
      } else {
        paramInt1 = 0;
      }
      paramInt1 = (paramInt1 ^ 0xFFFFFFFF) & paramInt2;
      paramString = this.mPaint;
      if ((paramInt1 & 0x1) != 0) {
        bool = true;
      }
      paramString.setFakeBoldText(bool);
      paramString = this.mPaint;
      if ((paramInt1 & 0x2) != 0) {
        f = -0.25F;
      }
      paramString.setTextSkewX(f);
    }
    else
    {
      this.mPaint.setFakeBoldText(false);
      this.mPaint.setTextSkewX(0.0F);
      setTypeface(paramString);
    }
  }
  
  private void setUpTheme(Context paramContext, AttributeSet paramAttributeSet)
  {
    paramAttributeSet = new TypedValue();
    paramContext.getTheme().resolveAttribute(R.attr.colorPrimary, paramAttributeSet, true);
    paramContext = this.mPaint;
    int i = paramAttributeSet.data;
    this.mTextFillColor = i;
    paramContext.setColor(i);
  }
  
  private void setupTexture()
  {
    if (this.mTextBackground != null)
    {
      this.mTextShaderMatrix = new Matrix();
      int j = this.mTextBackground.getIntrinsicWidth();
      int m = this.mTextBackground.getIntrinsicHeight();
      int k = 128;
      int i = j;
      if (j <= 0)
      {
        j = getWidth();
        i = j;
        if (j == 0) {
          if (Float.isNaN(this.mTextureWidth)) {
            i = 128;
          } else {
            i = (int)this.mTextureWidth;
          }
        }
      }
      j = m;
      if (m <= 0)
      {
        m = getHeight();
        j = m;
        if (m == 0) {
          if (Float.isNaN(this.mTextureHeight)) {
            j = k;
          } else {
            j = (int)this.mTextureHeight;
          }
        }
      }
      m = i;
      k = j;
      if (this.mTextureEffect != 0)
      {
        m = i / 2;
        k = j / 2;
      }
      this.mTextBackgroundBitmap = Bitmap.createBitmap(m, k, Bitmap.Config.ARGB_8888);
      Canvas localCanvas = new Canvas(this.mTextBackgroundBitmap);
      this.mTextBackground.setBounds(0, 0, localCanvas.getWidth(), localCanvas.getHeight());
      this.mTextBackground.setFilterBitmap(true);
      this.mTextBackground.draw(localCanvas);
      if (this.mTextureEffect != 0) {
        this.mTextBackgroundBitmap = blur(this.mTextBackgroundBitmap, 4);
      }
      this.mTextShader = new BitmapShader(this.mTextBackgroundBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
    }
  }
  
  private void updateShaderMatrix()
  {
    boolean bool = Float.isNaN(this.mBackgroundPanX);
    float f3 = 0.0F;
    float f1;
    if (bool) {
      f1 = 0.0F;
    } else {
      f1 = this.mBackgroundPanX;
    }
    float f2;
    if (Float.isNaN(this.mBackgroundPanY)) {
      f2 = 0.0F;
    } else {
      f2 = this.mBackgroundPanY;
    }
    if (Float.isNaN(this.mZoom)) {
      f6 = 1.0F;
    } else {
      f6 = this.mZoom;
    }
    if (!Float.isNaN(this.mRotate)) {
      f3 = this.mRotate;
    }
    this.mTextShaderMatrix.reset();
    float f8 = this.mTextBackgroundBitmap.getWidth();
    float f9 = this.mTextBackgroundBitmap.getHeight();
    float f4;
    if (Float.isNaN(this.mTextureWidth)) {
      f4 = this.mFloatWidth;
    } else {
      f4 = this.mTextureWidth;
    }
    float f5;
    if (Float.isNaN(this.mTextureHeight)) {
      f5 = this.mFloatHeight;
    } else {
      f5 = this.mTextureHeight;
    }
    if (f8 * f5 < f9 * f4) {
      f7 = f4 / f8;
    } else {
      f7 = f5 / f9;
    }
    float f10 = f7 * f6;
    this.mTextShaderMatrix.postScale(f10, f10);
    float f7 = f4 - f10 * f8;
    float f6 = f5 - f10 * f9;
    if (!Float.isNaN(this.mTextureHeight)) {
      f6 = this.mTextureHeight / 2.0F;
    }
    if (!Float.isNaN(this.mTextureWidth)) {
      f7 = this.mTextureWidth / 2.0F;
    }
    this.mTextShaderMatrix.postTranslate((f1 * f7 + f4 - f10 * f8) * 0.5F, (f2 * f6 + f5 - f10 * f9) * 0.5F);
    this.mTextShaderMatrix.postRotate(f3, f4 / 2.0F, f5 / 2.0F);
    this.mTextShader.setLocalMatrix(this.mTextShaderMatrix);
  }
  
  Bitmap blur(Bitmap paramBitmap, int paramInt)
  {
    System.nanoTime();
    int j = paramBitmap.getWidth();
    int i = paramBitmap.getHeight();
    int k = j / 2;
    j = i / 2;
    paramBitmap = Bitmap.createScaledBitmap(paramBitmap, k, j, true);
    for (i = 0; (i < paramInt) && (k >= 32) && (j >= 32); i++)
    {
      k /= 2;
      j /= 2;
      paramBitmap = Bitmap.createScaledBitmap(paramBitmap, k, j, true);
    }
    return paramBitmap;
  }
  
  void buildShape(float paramFloat)
  {
    if ((!this.mUseOutline) && (paramFloat == 1.0F)) {
      return;
    }
    this.mPath.reset();
    Object localObject = this.mText;
    int i = ((String)localObject).length();
    this.mPaint.getTextBounds((String)localObject, 0, i, this.mTextBounds);
    this.mPaint.getTextPath((String)localObject, 0, i, 0.0F, 0.0F, this.mPath);
    if (paramFloat != 1.0F)
    {
      String str1 = TAG;
      localObject = new StringBuilder();
      String str2 = Debug.getLoc();
      Log5ECF72.a(str2);
      LogE84000.a(str2);
      Log229316.a(str2);
      Log.v(str1, str2 + " scale " + paramFloat);
      localObject = new Matrix();
      ((Matrix)localObject).postScale(paramFloat, paramFloat);
      this.mPath.transform((Matrix)localObject);
    }
    localObject = this.mTextBounds;
    ((Rect)localObject).right -= 1;
    localObject = this.mTextBounds;
    ((Rect)localObject).left += 1;
    localObject = this.mTextBounds;
    ((Rect)localObject).bottom += 1;
    localObject = this.mTextBounds;
    ((Rect)localObject).top -= 1;
    localObject = new RectF();
    ((RectF)localObject).bottom = getHeight();
    ((RectF)localObject).right = getWidth();
    this.mNotBuilt = false;
  }
  
  public float getRound()
  {
    return this.mRound;
  }
  
  public float getRoundPercent()
  {
    return this.mRoundPercent;
  }
  
  public float getScaleFromTextSize()
  {
    return this.mBaseTextSize;
  }
  
  public float getTextBackgroundPanX()
  {
    return this.mBackgroundPanX;
  }
  
  public float getTextBackgroundPanY()
  {
    return this.mBackgroundPanY;
  }
  
  public float getTextBackgroundRotate()
  {
    return this.mRotate;
  }
  
  public float getTextBackgroundZoom()
  {
    return this.mZoom;
  }
  
  public int getTextOutlineColor()
  {
    return this.mTextOutlineColor;
  }
  
  public float getTextPanX()
  {
    return this.mTextPanX;
  }
  
  public float getTextPanY()
  {
    return this.mTextPanY;
  }
  
  public float getTextureHeight()
  {
    return this.mTextureHeight;
  }
  
  public float getTextureWidth()
  {
    return this.mTextureWidth;
  }
  
  public Typeface getTypeface()
  {
    return this.mPaint.getTypeface();
  }
  
  public void layout(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    this.mDeltaLeft = (paramFloat1 - (int)(paramFloat1 + 0.5F));
    int j = (int)(paramFloat3 + 0.5F) - (int)(paramFloat1 + 0.5F);
    int i = (int)(paramFloat4 + 0.5F) - (int)(paramFloat2 + 0.5F);
    this.mFloatWidth = (paramFloat3 - paramFloat1);
    this.mFloatHeight = (paramFloat4 - paramFloat2);
    adjustTexture(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
    if ((getMeasuredHeight() == i) && (getMeasuredWidth() == j))
    {
      super.layout((int)(paramFloat1 + 0.5F), (int)(paramFloat2 + 0.5F), (int)(paramFloat3 + 0.5F), (int)(0.5F + paramFloat4));
    }
    else
    {
      measure(View.MeasureSpec.makeMeasureSpec(j, 1073741824), View.MeasureSpec.makeMeasureSpec(i, 1073741824));
      super.layout((int)(paramFloat1 + 0.5F), (int)(paramFloat2 + 0.5F), (int)(paramFloat3 + 0.5F), (int)(0.5F + paramFloat4));
    }
    if (this.mAutoSize)
    {
      if (this.mTempRect == null)
      {
        this.mTempPaint = new Paint();
        this.mTempRect = new Rect();
        this.mTempPaint.set(this.mPaint);
        this.paintTextSize = this.mTempPaint.getTextSize();
      }
      this.mFloatWidth = (paramFloat3 - paramFloat1);
      this.mFloatHeight = (paramFloat4 - paramFloat2);
      Paint localPaint = this.mTempPaint;
      String str = this.mText;
      localPaint.getTextBounds(str, 0, str.length(), this.mTempRect);
      i = this.mTempRect.width();
      float f = this.mTempRect.height() * 1.3F;
      paramFloat1 = paramFloat3 - paramFloat1 - this.mPaddingRight - this.mPaddingLeft;
      paramFloat2 = paramFloat4 - paramFloat2 - this.mPaddingBottom - this.mPaddingTop;
      if (i * paramFloat2 > f * paramFloat1) {
        this.mPaint.setTextSize(this.paintTextSize * paramFloat1 / i);
      } else {
        this.mPaint.setTextSize(this.paintTextSize * paramFloat2 / f);
      }
      if ((this.mUseOutline) || (!Float.isNaN(this.mBaseTextSize)))
      {
        if (Float.isNaN(this.mBaseTextSize)) {
          paramFloat1 = 1.0F;
        } else {
          paramFloat1 = this.mTextSize / this.mBaseTextSize;
        }
        buildShape(paramFloat1);
      }
    }
  }
  
  public void layout(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.layout(paramInt1, paramInt2, paramInt3, paramInt4);
    boolean bool = Float.isNaN(this.mBaseTextSize);
    float f1;
    if (bool) {
      f1 = 1.0F;
    } else {
      f1 = this.mTextSize / this.mBaseTextSize;
    }
    this.mFloatWidth = (paramInt3 - paramInt1);
    this.mFloatHeight = (paramInt4 - paramInt2);
    float f2 = f1;
    if (this.mAutoSize)
    {
      if (this.mTempRect == null)
      {
        this.mTempPaint = new Paint();
        this.mTempRect = new Rect();
        this.mTempPaint.set(this.mPaint);
        this.paintTextSize = this.mTempPaint.getTextSize();
      }
      Paint localPaint = this.mTempPaint;
      String str = this.mText;
      localPaint.getTextBounds(str, 0, str.length(), this.mTempRect);
      int i = this.mTempRect.width();
      int j = (int)(this.mTempRect.height() * 1.3F);
      float f3 = this.mFloatWidth - this.mPaddingRight - this.mPaddingLeft;
      f2 = this.mFloatHeight - this.mPaddingBottom - this.mPaddingTop;
      if (bool)
      {
        if (i * f2 > j * f3)
        {
          this.mPaint.setTextSize(this.paintTextSize * f3 / i);
          f2 = f1;
        }
        else
        {
          this.mPaint.setTextSize(this.paintTextSize * f2 / j);
          f2 = f1;
        }
      }
      else
      {
        if (i * f2 > j * f3) {
          f1 = f3 / i;
        } else {
          f1 = f2 / j;
        }
        f2 = f1;
      }
    }
    if ((this.mUseOutline) || (!bool))
    {
      adjustTexture(paramInt1, paramInt2, paramInt3, paramInt4);
      buildShape(f2);
    }
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    float f1;
    if (Float.isNaN(this.mBaseTextSize)) {
      f1 = 1.0F;
    } else {
      f1 = this.mTextSize / this.mBaseTextSize;
    }
    super.onDraw(paramCanvas);
    float f2;
    float f3;
    if ((!this.mUseOutline) && (f1 == 1.0F))
    {
      f1 = this.mPaddingLeft;
      float f4 = getHorizontalOffset();
      f2 = this.mPaddingTop;
      f3 = getVerticalOffset();
      paramCanvas.drawText(this.mText, this.mDeltaLeft + (f1 + f4), f2 + f3, this.mPaint);
      return;
    }
    if (this.mNotBuilt) {
      buildShape(f1);
    }
    if (this.mOutlinePositionMatrix == null) {
      this.mOutlinePositionMatrix = new Matrix();
    }
    if (this.mUseOutline)
    {
      this.paintCache.set(this.mPaint);
      this.mOutlinePositionMatrix.reset();
      f3 = this.mPaddingLeft + getHorizontalOffset();
      f2 = this.mPaddingTop + getVerticalOffset();
      this.mOutlinePositionMatrix.postTranslate(f3, f2);
      this.mOutlinePositionMatrix.preScale(f1, f1);
      this.mPath.transform(this.mOutlinePositionMatrix);
      if (this.mTextShader != null)
      {
        this.mPaint.setFilterBitmap(true);
        this.mPaint.setShader(this.mTextShader);
      }
      else
      {
        this.mPaint.setColor(this.mTextFillColor);
      }
      this.mPaint.setStyle(Paint.Style.FILL);
      this.mPaint.setStrokeWidth(this.mTextOutlineThickness);
      paramCanvas.drawPath(this.mPath, this.mPaint);
      if (this.mTextShader != null) {
        this.mPaint.setShader(null);
      }
      this.mPaint.setColor(this.mTextOutlineColor);
      this.mPaint.setStyle(Paint.Style.STROKE);
      this.mPaint.setStrokeWidth(this.mTextOutlineThickness);
      paramCanvas.drawPath(this.mPath, this.mPaint);
      this.mOutlinePositionMatrix.reset();
      this.mOutlinePositionMatrix.postTranslate(-f3, -f2);
      this.mPath.transform(this.mOutlinePositionMatrix);
      this.mPaint.set(this.paintCache);
    }
    else
    {
      f1 = this.mPaddingLeft + getHorizontalOffset();
      f2 = this.mPaddingTop + getVerticalOffset();
      this.mOutlinePositionMatrix.reset();
      this.mOutlinePositionMatrix.preTranslate(f1, f2);
      this.mPath.transform(this.mOutlinePositionMatrix);
      this.mPaint.setColor(this.mTextFillColor);
      this.mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
      this.mPaint.setStrokeWidth(this.mTextOutlineThickness);
      paramCanvas.drawPath(this.mPath, this.mPaint);
      this.mOutlinePositionMatrix.reset();
      this.mOutlinePositionMatrix.preTranslate(-f1, -f2);
      this.mPath.transform(this.mOutlinePositionMatrix);
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getMode(paramInt1);
    int m = View.MeasureSpec.getMode(paramInt2);
    paramInt1 = View.MeasureSpec.getSize(paramInt1);
    paramInt2 = View.MeasureSpec.getSize(paramInt2);
    this.mAutoSize = false;
    this.mPaddingLeft = getPaddingLeft();
    this.mPaddingRight = getPaddingRight();
    this.mPaddingTop = getPaddingTop();
    this.mPaddingBottom = getPaddingBottom();
    int j;
    if ((i == 1073741824) && (m == 1073741824))
    {
      i = paramInt1;
      j = paramInt2;
      if (this.mAutoSizeTextType != 0)
      {
        this.mAutoSize = true;
        i = paramInt1;
        j = paramInt2;
      }
    }
    else
    {
      TextPaint localTextPaint = this.mPaint;
      String str = this.mText;
      localTextPaint.getTextBounds(str, 0, str.length(), this.mTextBounds);
      if (i != 1073741824) {
        paramInt1 = (int)(this.mTextBounds.width() + 0.99999F);
      }
      int k = paramInt1 + (this.mPaddingLeft + this.mPaddingRight);
      i = k;
      j = paramInt2;
      if (m != 1073741824)
      {
        paramInt1 = (int)(this.mPaint.getFontMetricsInt(null) + 0.99999F);
        if (m == Integer.MIN_VALUE) {
          paramInt1 = Math.min(paramInt2, paramInt1);
        }
        j = paramInt1 + (this.mPaddingTop + this.mPaddingBottom);
        i = k;
      }
    }
    setMeasuredDimension(i, j);
  }
  
  public void setGravity(int paramInt)
  {
    int i = paramInt;
    if ((paramInt & 0x800007) == 0) {
      i = paramInt | 0x800003;
    }
    paramInt = i;
    if ((i & 0x70) == 0) {
      paramInt = i | 0x30;
    }
    i = this.mGravity;
    if (((paramInt & 0x800007) == (i & 0x800007)) || (paramInt != i)) {
      invalidate();
    }
    this.mGravity = paramInt;
    switch (paramInt & 0x70)
    {
    default: 
      this.mTextPanY = 0.0F;
      break;
    case 80: 
      this.mTextPanY = 1.0F;
      break;
    case 48: 
      this.mTextPanY = -1.0F;
    }
    switch (0x800007 & paramInt)
    {
    default: 
      this.mTextPanX = 0.0F;
      break;
    case 5: 
    case 8388613: 
      this.mTextPanX = 1.0F;
      break;
    case 3: 
    case 8388611: 
      this.mTextPanX = -1.0F;
    }
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
              paramAnonymousOutline.setRoundRect(0, 0, MotionLabel.this.getWidth(), MotionLabel.this.getHeight(), MotionLabel.this.mRound);
            }
          };
          this.mViewOutlineProvider = ((ViewOutlineProvider)localObject);
          setOutlineProvider((ViewOutlineProvider)localObject);
        }
        setClipToOutline(true);
      }
      int k = getWidth();
      int j = getHeight();
      this.mRect.set(0.0F, 0.0F, k, j);
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
              int i = MotionLabel.this.getWidth();
              int j = MotionLabel.this.getHeight();
              paramAnonymousOutline.setRoundRect(0, 0, i, j, Math.min(i, j) * MotionLabel.this.mRoundPercent / 2.0F);
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
  
  public void setScaleFromTextSize(float paramFloat)
  {
    this.mBaseTextSize = paramFloat;
  }
  
  public void setText(CharSequence paramCharSequence)
  {
    this.mText = paramCharSequence.toString();
    invalidate();
  }
  
  public void setTextBackgroundPanX(float paramFloat)
  {
    this.mBackgroundPanX = paramFloat;
    updateShaderMatrix();
    invalidate();
  }
  
  public void setTextBackgroundPanY(float paramFloat)
  {
    this.mBackgroundPanY = paramFloat;
    updateShaderMatrix();
    invalidate();
  }
  
  public void setTextBackgroundRotate(float paramFloat)
  {
    this.mRotate = paramFloat;
    updateShaderMatrix();
    invalidate();
  }
  
  public void setTextBackgroundZoom(float paramFloat)
  {
    this.mZoom = paramFloat;
    updateShaderMatrix();
    invalidate();
  }
  
  public void setTextFillColor(int paramInt)
  {
    this.mTextFillColor = paramInt;
    invalidate();
  }
  
  public void setTextOutlineColor(int paramInt)
  {
    this.mTextOutlineColor = paramInt;
    this.mUseOutline = true;
    invalidate();
  }
  
  public void setTextOutlineThickness(float paramFloat)
  {
    this.mTextOutlineThickness = paramFloat;
    this.mUseOutline = true;
    if (Float.isNaN(paramFloat))
    {
      this.mTextOutlineThickness = 1.0F;
      this.mUseOutline = false;
    }
    invalidate();
  }
  
  public void setTextPanX(float paramFloat)
  {
    this.mTextPanX = paramFloat;
    invalidate();
  }
  
  public void setTextPanY(float paramFloat)
  {
    this.mTextPanY = paramFloat;
    invalidate();
  }
  
  public void setTextSize(float paramFloat)
  {
    this.mTextSize = paramFloat;
    Object localObject = TAG;
    StringBuilder localStringBuilder = new StringBuilder();
    String str = Debug.getLoc();
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    Log.v((String)localObject, str + "  " + paramFloat + " / " + this.mBaseTextSize);
    localObject = this.mPaint;
    if (!Float.isNaN(this.mBaseTextSize)) {
      paramFloat = this.mBaseTextSize;
    }
    ((TextPaint)localObject).setTextSize(paramFloat);
    if (Float.isNaN(this.mBaseTextSize)) {
      paramFloat = 1.0F;
    } else {
      paramFloat = this.mTextSize / this.mBaseTextSize;
    }
    buildShape(paramFloat);
    requestLayout();
    invalidate();
  }
  
  public void setTextureHeight(float paramFloat)
  {
    this.mTextureHeight = paramFloat;
    updateShaderMatrix();
    invalidate();
  }
  
  public void setTextureWidth(float paramFloat)
  {
    this.mTextureWidth = paramFloat;
    updateShaderMatrix();
    invalidate();
  }
  
  public void setTypeface(Typeface paramTypeface)
  {
    if (this.mPaint.getTypeface() != paramTypeface)
    {
      this.mPaint.setTypeface(paramTypeface);
      if (this.mLayout != null)
      {
        this.mLayout = null;
        requestLayout();
        invalidate();
      }
    }
  }
  
  void setupPath()
  {
    this.mPaddingLeft = getPaddingLeft();
    this.mPaddingRight = getPaddingRight();
    this.mPaddingTop = getPaddingTop();
    this.mPaddingBottom = getPaddingBottom();
    setTypefaceFromAttrs(this.mFontFamily, this.mTypefaceIndex, this.mStyleIndex);
    this.mPaint.setColor(this.mTextFillColor);
    this.mPaint.setStrokeWidth(this.mTextOutlineThickness);
    this.mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    this.mPaint.setFlags(128);
    setTextSize(this.mTextSize);
    this.mPaint.setAntiAlias(true);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/utils/widget/MotionLabel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */