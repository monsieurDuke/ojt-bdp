package androidx.constraintlayout.utils.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.R.styleable;

public class MotionTelltales
  extends MockView
{
  private static final String TAG = "MotionTelltales";
  Matrix mInvertMatrix = new Matrix();
  MotionLayout mMotionLayout;
  private Paint mPaintTelltales = new Paint();
  int mTailColor = -65281;
  float mTailScale = 0.25F;
  int mVelocityMode = 0;
  float[] velocity = new float[2];
  
  public MotionTelltales(Context paramContext)
  {
    super(paramContext);
    init(paramContext, null);
  }
  
  public MotionTelltales(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramContext, paramAttributeSet);
  }
  
  public MotionTelltales(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext, paramAttributeSet);
  }
  
  private void init(Context paramContext, AttributeSet paramAttributeSet)
  {
    if (paramAttributeSet != null)
    {
      paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.MotionTelltales);
      int j = paramContext.getIndexCount();
      for (int i = 0; i < j; i++)
      {
        int k = paramContext.getIndex(i);
        if (k == R.styleable.MotionTelltales_telltales_tailColor) {
          this.mTailColor = paramContext.getColor(k, this.mTailColor);
        } else if (k == R.styleable.MotionTelltales_telltales_velocityMode) {
          this.mVelocityMode = paramContext.getInt(k, this.mVelocityMode);
        } else if (k == R.styleable.MotionTelltales_telltales_tailScale) {
          this.mTailScale = paramContext.getFloat(k, this.mTailScale);
        }
      }
      paramContext.recycle();
    }
    this.mPaintTelltales.setColor(this.mTailColor);
    this.mPaintTelltales.setStrokeWidth(5.0F);
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
  }
  
  public void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    getMatrix().invert(this.mInvertMatrix);
    if (this.mMotionLayout == null)
    {
      paramCanvas = getParent();
      if ((paramCanvas instanceof MotionLayout)) {
        this.mMotionLayout = ((MotionLayout)paramCanvas);
      }
      return;
    }
    int m = getWidth();
    int k = getHeight();
    float[] arrayOfFloat2 = new float[5];
    float[] tmp64_62 = arrayOfFloat2;
    tmp64_62[0] = 0.1F;
    float[] tmp69_64 = tmp64_62;
    tmp69_64[1] = 0.25F;
    float[] tmp74_69 = tmp69_64;
    tmp74_69[2] = 0.5F;
    float[] tmp79_74 = tmp74_69;
    tmp79_74[3] = 0.75F;
    float[] tmp84_79 = tmp79_74;
    tmp84_79[4] = 0.9F;
    tmp84_79;
    for (int i = 0; i < arrayOfFloat2.length; i++)
    {
      float f1 = arrayOfFloat2[i];
      for (int j = 0; j < arrayOfFloat2.length; j++)
      {
        float f2 = arrayOfFloat2[j];
        this.mMotionLayout.getViewVelocity(this, f2, f1, this.velocity, this.mVelocityMode);
        this.mInvertMatrix.mapVectors(this.velocity);
        float f3 = m * f2;
        float f5 = k * f1;
        float[] arrayOfFloat1 = this.velocity;
        f2 = arrayOfFloat1[0];
        float f4 = this.mTailScale;
        float f6 = arrayOfFloat1[1];
        this.mInvertMatrix.mapVectors(arrayOfFloat1);
        paramCanvas.drawLine(f3, f5, f3 - f2 * f4, f5 - f6 * f4, this.mPaintTelltales);
      }
    }
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    postInvalidate();
  }
  
  public void setText(CharSequence paramCharSequence)
  {
    this.mText = paramCharSequence.toString();
    requestLayout();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/utils/widget/MotionTelltales.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */