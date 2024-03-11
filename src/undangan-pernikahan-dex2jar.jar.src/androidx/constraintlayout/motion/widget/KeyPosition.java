package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.motion.utils.ViewSpline;
import androidx.constraintlayout.widget.R.styleable;
import java.io.PrintStream;
import java.util.HashMap;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class KeyPosition
  extends KeyPositionBase
{
  public static final String DRAWPATH = "drawPath";
  static final int KEY_TYPE = 2;
  static final String NAME = "KeyPosition";
  public static final String PERCENT_HEIGHT = "percentHeight";
  public static final String PERCENT_WIDTH = "percentWidth";
  public static final String PERCENT_X = "percentX";
  public static final String PERCENT_Y = "percentY";
  public static final String SIZE_PERCENT = "sizePercent";
  private static final String TAG = "KeyPosition";
  public static final String TRANSITION_EASING = "transitionEasing";
  public static final int TYPE_CARTESIAN = 0;
  public static final int TYPE_PATH = 1;
  public static final int TYPE_SCREEN = 2;
  float mAltPercentX = NaN.0F;
  float mAltPercentY = NaN.0F;
  private float mCalculatedPositionX = NaN.0F;
  private float mCalculatedPositionY = NaN.0F;
  int mDrawPath = 0;
  int mPathMotionArc = UNSET;
  float mPercentHeight = NaN.0F;
  float mPercentWidth = NaN.0F;
  float mPercentX = NaN.0F;
  float mPercentY = NaN.0F;
  int mPositionType = 0;
  String mTransitionEasing = null;
  
  public KeyPosition()
  {
    this.mType = 2;
  }
  
  private void calcCartesianPosition(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    float f3 = paramFloat3 - paramFloat1;
    float f4 = paramFloat4 - paramFloat2;
    boolean bool = Float.isNaN(this.mPercentX);
    float f2 = 0.0F;
    if (bool) {
      paramFloat3 = 0.0F;
    } else {
      paramFloat3 = this.mPercentX;
    }
    if (Float.isNaN(this.mAltPercentY)) {
      paramFloat4 = 0.0F;
    } else {
      paramFloat4 = this.mAltPercentY;
    }
    float f1;
    if (Float.isNaN(this.mPercentY)) {
      f1 = 0.0F;
    } else {
      f1 = this.mPercentY;
    }
    if (!Float.isNaN(this.mAltPercentX)) {
      f2 = this.mAltPercentX;
    }
    this.mCalculatedPositionX = ((int)(f3 * paramFloat3 + paramFloat1 + f4 * f2));
    this.mCalculatedPositionY = ((int)(f3 * paramFloat4 + paramFloat2 + f4 * f1));
  }
  
  private void calcPathPosition(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    paramFloat3 -= paramFloat1;
    float f1 = paramFloat4 - paramFloat2;
    paramFloat4 = -f1;
    float f2 = this.mPercentX;
    float f3 = this.mPercentY;
    this.mCalculatedPositionX = (paramFloat3 * f2 + paramFloat1 + paramFloat4 * f3);
    this.mCalculatedPositionY = (f2 * f1 + paramFloat2 + f3 * paramFloat3);
  }
  
  private void calcScreenPosition(int paramInt1, int paramInt2)
  {
    float f1 = paramInt1 - 0;
    float f2 = this.mPercentX;
    this.mCalculatedPositionX = (f1 * f2 + 0 / 2);
    this.mCalculatedPositionY = ((paramInt2 - 0) * f2 + 0 / 2);
  }
  
  public void addValues(HashMap<String, ViewSpline> paramHashMap) {}
  
  void calcPosition(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    switch (this.mPositionType)
    {
    default: 
      calcCartesianPosition(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
      return;
    case 2: 
      calcScreenPosition(paramInt1, paramInt2);
      return;
    }
    calcPathPosition(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }
  
  public Key clone()
  {
    return new KeyPosition().copy(this);
  }
  
  public Key copy(Key paramKey)
  {
    super.copy(paramKey);
    paramKey = (KeyPosition)paramKey;
    this.mTransitionEasing = paramKey.mTransitionEasing;
    this.mPathMotionArc = paramKey.mPathMotionArc;
    this.mDrawPath = paramKey.mDrawPath;
    this.mPercentWidth = paramKey.mPercentWidth;
    this.mPercentHeight = NaN.0F;
    this.mPercentX = paramKey.mPercentX;
    this.mPercentY = paramKey.mPercentY;
    this.mAltPercentX = paramKey.mAltPercentX;
    this.mAltPercentY = paramKey.mAltPercentY;
    this.mCalculatedPositionX = paramKey.mCalculatedPositionX;
    this.mCalculatedPositionY = paramKey.mCalculatedPositionY;
    return this;
  }
  
  float getPositionX()
  {
    return this.mCalculatedPositionX;
  }
  
  float getPositionY()
  {
    return this.mCalculatedPositionY;
  }
  
  public boolean intersects(int paramInt1, int paramInt2, RectF paramRectF1, RectF paramRectF2, float paramFloat1, float paramFloat2)
  {
    calcPosition(paramInt1, paramInt2, paramRectF1.centerX(), paramRectF1.centerY(), paramRectF2.centerX(), paramRectF2.centerY());
    return (Math.abs(paramFloat1 - this.mCalculatedPositionX) < 20.0F) && (Math.abs(paramFloat2 - this.mCalculatedPositionY) < 20.0F);
  }
  
  public void load(Context paramContext, AttributeSet paramAttributeSet)
  {
    Loader.read(this, paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.KeyPosition));
  }
  
  public void positionAttributes(View paramView, RectF paramRectF1, RectF paramRectF2, float paramFloat1, float paramFloat2, String[] paramArrayOfString, float[] paramArrayOfFloat)
  {
    switch (this.mPositionType)
    {
    default: 
      positionCartAttributes(paramRectF1, paramRectF2, paramFloat1, paramFloat2, paramArrayOfString, paramArrayOfFloat);
      return;
    case 2: 
      positionScreenAttributes(paramView, paramRectF1, paramRectF2, paramFloat1, paramFloat2, paramArrayOfString, paramArrayOfFloat);
      return;
    }
    positionPathAttributes(paramRectF1, paramRectF2, paramFloat1, paramFloat2, paramArrayOfString, paramArrayOfFloat);
  }
  
  void positionCartAttributes(RectF paramRectF1, RectF paramRectF2, float paramFloat1, float paramFloat2, String[] paramArrayOfString, float[] paramArrayOfFloat)
  {
    float f2 = paramRectF1.centerX();
    float f1 = paramRectF1.centerY();
    float f4 = paramRectF2.centerX();
    float f3 = paramRectF2.centerY();
    f4 -= f2;
    f3 -= f1;
    if (paramArrayOfString[0] != null)
    {
      if ("percentX".equals(paramArrayOfString[0]))
      {
        paramArrayOfFloat[0] = ((paramFloat1 - f2) / f4);
        paramArrayOfFloat[1] = ((paramFloat2 - f1) / f3);
      }
      else
      {
        paramArrayOfFloat[1] = ((paramFloat1 - f2) / f4);
        paramArrayOfFloat[0] = ((paramFloat2 - f1) / f3);
      }
    }
    else
    {
      paramArrayOfString[0] = "percentX";
      paramArrayOfFloat[0] = ((paramFloat1 - f2) / f4);
      paramArrayOfString[1] = "percentY";
      paramArrayOfFloat[1] = ((paramFloat2 - f1) / f3);
    }
  }
  
  void positionPathAttributes(RectF paramRectF1, RectF paramRectF2, float paramFloat1, float paramFloat2, String[] paramArrayOfString, float[] paramArrayOfFloat)
  {
    float f1 = paramRectF1.centerX();
    float f2 = paramRectF1.centerY();
    float f4 = paramRectF2.centerX();
    float f3 = paramRectF2.centerY();
    f4 -= f1;
    float f5 = f3 - f2;
    f3 = (float)Math.hypot(f4, f5);
    if (f3 < 1.0E-4D)
    {
      System.out.println("distance ~ 0");
      paramArrayOfFloat[0] = 0.0F;
      paramArrayOfFloat[1] = 0.0F;
      return;
    }
    f4 /= f3;
    float f6 = f5 / f3;
    f5 = ((paramFloat2 - f2) * f4 - (paramFloat1 - f1) * f6) / f3;
    paramFloat1 = ((paramFloat1 - f1) * f4 + (paramFloat2 - f2) * f6) / f3;
    if (paramArrayOfString[0] != null)
    {
      if ("percentX".equals(paramArrayOfString[0]))
      {
        paramArrayOfFloat[0] = paramFloat1;
        paramArrayOfFloat[1] = f5;
      }
    }
    else
    {
      paramArrayOfString[0] = "percentX";
      paramArrayOfString[1] = "percentY";
      paramArrayOfFloat[0] = paramFloat1;
      paramArrayOfFloat[1] = f5;
    }
  }
  
  void positionScreenAttributes(View paramView, RectF paramRectF1, RectF paramRectF2, float paramFloat1, float paramFloat2, String[] paramArrayOfString, float[] paramArrayOfFloat)
  {
    paramRectF1.centerX();
    paramRectF1.centerY();
    paramRectF2.centerX();
    paramRectF2.centerY();
    paramView = (ViewGroup)paramView.getParent();
    int j = paramView.getWidth();
    int i = paramView.getHeight();
    if (paramArrayOfString[0] != null)
    {
      if ("percentX".equals(paramArrayOfString[0]))
      {
        paramArrayOfFloat[0] = (paramFloat1 / j);
        paramArrayOfFloat[1] = (paramFloat2 / i);
      }
      else
      {
        paramArrayOfFloat[1] = (paramFloat1 / j);
        paramArrayOfFloat[0] = (paramFloat2 / i);
      }
    }
    else
    {
      paramArrayOfString[0] = "percentX";
      paramArrayOfFloat[0] = (paramFloat1 / j);
      paramArrayOfString[1] = "percentY";
      paramArrayOfFloat[1] = (paramFloat2 / i);
    }
  }
  
  public void setType(int paramInt)
  {
    this.mPositionType = paramInt;
  }
  
  public void setValue(String paramString, Object paramObject)
  {
    switch (paramString.hashCode())
    {
    }
    for (;;)
    {
      break;
      if (paramString.equals("percentY"))
      {
        i = 6;
        break label184;
        if (paramString.equals("percentX"))
        {
          i = 5;
          break label184;
          if (paramString.equals("sizePercent"))
          {
            i = 4;
            break label184;
            if (paramString.equals("drawPath"))
            {
              i = 1;
              break label184;
              if (paramString.equals("percentHeight"))
              {
                i = 3;
                break label184;
                if (paramString.equals("percentWidth"))
                {
                  i = 2;
                  break label184;
                  if (paramString.equals("transitionEasing"))
                  {
                    i = 0;
                    break label184;
                  }
                }
              }
            }
          }
        }
      }
    }
    int i = -1;
    switch (i)
    {
    default: 
      break;
    case 6: 
      this.mPercentY = toFloat(paramObject);
      break;
    case 5: 
      this.mPercentX = toFloat(paramObject);
      break;
    case 4: 
      float f = toFloat(paramObject);
      this.mPercentWidth = f;
      this.mPercentHeight = f;
      break;
    case 3: 
      this.mPercentHeight = toFloat(paramObject);
      break;
    case 2: 
      this.mPercentWidth = toFloat(paramObject);
      break;
    case 1: 
      this.mDrawPath = toInt(paramObject);
      break;
    case 0: 
      label184:
      this.mTransitionEasing = paramObject.toString();
    }
  }
  
  private static class Loader
  {
    private static final int CURVE_FIT = 4;
    private static final int DRAW_PATH = 5;
    private static final int FRAME_POSITION = 2;
    private static final int PATH_MOTION_ARC = 10;
    private static final int PERCENT_HEIGHT = 12;
    private static final int PERCENT_WIDTH = 11;
    private static final int PERCENT_X = 6;
    private static final int PERCENT_Y = 7;
    private static final int SIZE_PERCENT = 8;
    private static final int TARGET_ID = 1;
    private static final int TRANSITION_EASING = 3;
    private static final int TYPE = 9;
    private static SparseIntArray mAttrMap;
    
    static
    {
      SparseIntArray localSparseIntArray = new SparseIntArray();
      mAttrMap = localSparseIntArray;
      localSparseIntArray.append(R.styleable.KeyPosition_motionTarget, 1);
      mAttrMap.append(R.styleable.KeyPosition_framePosition, 2);
      mAttrMap.append(R.styleable.KeyPosition_transitionEasing, 3);
      mAttrMap.append(R.styleable.KeyPosition_curveFit, 4);
      mAttrMap.append(R.styleable.KeyPosition_drawPath, 5);
      mAttrMap.append(R.styleable.KeyPosition_percentX, 6);
      mAttrMap.append(R.styleable.KeyPosition_percentY, 7);
      mAttrMap.append(R.styleable.KeyPosition_keyPositionType, 9);
      mAttrMap.append(R.styleable.KeyPosition_sizePercent, 8);
      mAttrMap.append(R.styleable.KeyPosition_percentWidth, 11);
      mAttrMap.append(R.styleable.KeyPosition_percentHeight, 12);
      mAttrMap.append(R.styleable.KeyPosition_pathMotionArc, 10);
    }
    
    private static void read(KeyPosition paramKeyPosition, TypedArray paramTypedArray)
    {
      int j = paramTypedArray.getIndexCount();
      for (int i = 0; i < j; i++)
      {
        int k = paramTypedArray.getIndex(i);
        switch (mAttrMap.get(k))
        {
        default: 
          StringBuilder localStringBuilder = new StringBuilder().append("unused attribute 0x");
          String str = Integer.toHexString(k);
          Log5ECF72.a(str);
          LogE84000.a(str);
          Log229316.a(str);
          Log.e("KeyPosition", str + "   " + mAttrMap.get(k));
          break;
        case 12: 
          paramKeyPosition.mPercentHeight = paramTypedArray.getFloat(k, paramKeyPosition.mPercentHeight);
          break;
        case 11: 
          paramKeyPosition.mPercentWidth = paramTypedArray.getFloat(k, paramKeyPosition.mPercentWidth);
          break;
        case 10: 
          paramKeyPosition.mPathMotionArc = paramTypedArray.getInt(k, paramKeyPosition.mPathMotionArc);
          break;
        case 9: 
          paramKeyPosition.mPositionType = paramTypedArray.getInt(k, paramKeyPosition.mPositionType);
          break;
        case 8: 
          float f = paramTypedArray.getFloat(k, paramKeyPosition.mPercentHeight);
          paramKeyPosition.mPercentWidth = f;
          paramKeyPosition.mPercentHeight = f;
          break;
        case 7: 
          paramKeyPosition.mPercentY = paramTypedArray.getFloat(k, paramKeyPosition.mPercentY);
          break;
        case 6: 
          paramKeyPosition.mPercentX = paramTypedArray.getFloat(k, paramKeyPosition.mPercentX);
          break;
        case 5: 
          paramKeyPosition.mDrawPath = paramTypedArray.getInt(k, paramKeyPosition.mDrawPath);
          break;
        case 4: 
          paramKeyPosition.mCurveFit = paramTypedArray.getInteger(k, paramKeyPosition.mCurveFit);
          break;
        case 3: 
          if (paramTypedArray.peekValue(k).type == 3) {
            paramKeyPosition.mTransitionEasing = paramTypedArray.getString(k);
          } else {
            paramKeyPosition.mTransitionEasing = androidx.constraintlayout.core.motion.utils.Easing.NAMED_EASING[paramTypedArray.getInteger(k, 0)];
          }
          break;
        case 2: 
          paramKeyPosition.mFramePosition = paramTypedArray.getInt(k, paramKeyPosition.mFramePosition);
          break;
        case 1: 
          if (MotionLayout.IS_IN_EDIT_MODE)
          {
            paramKeyPosition.mTargetId = paramTypedArray.getResourceId(k, paramKeyPosition.mTargetId);
            if (paramKeyPosition.mTargetId == -1) {
              paramKeyPosition.mTargetString = paramTypedArray.getString(k);
            }
          }
          else if (paramTypedArray.peekValue(k).type == 3)
          {
            paramKeyPosition.mTargetString = paramTypedArray.getString(k);
          }
          else
          {
            paramKeyPosition.mTargetId = paramTypedArray.getResourceId(k, paramKeyPosition.mTargetId);
          }
          break;
        }
      }
      if (paramKeyPosition.mFramePosition == -1) {
        Log.e("KeyPosition", "no frame position");
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/motion/widget/KeyPosition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */