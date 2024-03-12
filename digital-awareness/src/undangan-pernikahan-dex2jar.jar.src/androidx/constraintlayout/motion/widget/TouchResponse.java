package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.R.styleable;
import androidx.core.widget.NestedScrollView;
import androidx.core.widget.NestedScrollView.OnScrollChangeListener;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import org.xmlpull.v1.XmlPullParser;

class TouchResponse
{
  public static final int COMPLETE_MODE_CONTINUOUS_VELOCITY = 0;
  public static final int COMPLETE_MODE_SPRING = 1;
  private static final boolean DEBUG = false;
  private static final float EPSILON = 1.0E-7F;
  static final int FLAG_DISABLE_POST_SCROLL = 1;
  static final int FLAG_DISABLE_SCROLL = 2;
  static final int FLAG_SUPPORT_SCROLL_UP = 4;
  private static final int SEC_TO_MILLISECONDS = 1000;
  private static final int SIDE_BOTTOM = 3;
  private static final int SIDE_END = 6;
  private static final int SIDE_LEFT = 1;
  private static final int SIDE_MIDDLE = 4;
  private static final int SIDE_RIGHT = 2;
  private static final int SIDE_START = 5;
  private static final int SIDE_TOP = 0;
  private static final String TAG = "TouchResponse";
  private static final float[][] TOUCH_DIRECTION;
  private static final int TOUCH_DOWN = 1;
  private static final int TOUCH_END = 5;
  private static final int TOUCH_LEFT = 2;
  private static final int TOUCH_RIGHT = 3;
  private static final float[][] TOUCH_SIDES = { { 0.5F, 0.0F }, { 0.0F, 0.5F }, { 1.0F, 0.5F }, { 0.5F, 1.0F }, { 0.5F, 0.5F }, { 0.0F, 0.5F }, { 1.0F, 0.5F } };
  private static final int TOUCH_START = 4;
  private static final int TOUCH_UP = 0;
  private float[] mAnchorDpDt = new float[2];
  private int mAutoCompleteMode = 0;
  private float mDragScale = 1.0F;
  private boolean mDragStarted = false;
  private float mDragThreshold = 10.0F;
  private int mFlags = 0;
  boolean mIsRotateMode = false;
  private float mLastTouchX;
  private float mLastTouchY;
  private int mLimitBoundsTo = -1;
  private float mMaxAcceleration = 1.2F;
  private float mMaxVelocity = 4.0F;
  private final MotionLayout mMotionLayout;
  private boolean mMoveWhenScrollAtTop = true;
  private int mOnTouchUp = 0;
  float mRotateCenterX = 0.5F;
  float mRotateCenterY = 0.5F;
  private int mRotationCenterId = -1;
  private int mSpringBoundary = 0;
  private float mSpringDamping = 10.0F;
  private float mSpringMass = 1.0F;
  private float mSpringStiffness = NaN.0F;
  private float mSpringStopThreshold = NaN.0F;
  private int[] mTempLoc = new int[2];
  private int mTouchAnchorId = -1;
  private int mTouchAnchorSide = 0;
  private float mTouchAnchorX = 0.5F;
  private float mTouchAnchorY = 0.5F;
  private float mTouchDirectionX = 0.0F;
  private float mTouchDirectionY = 1.0F;
  private int mTouchRegionId = -1;
  private int mTouchSide = 0;
  
  static
  {
    float[] arrayOfFloat1 = { 0.0F, -1.0F };
    float[] arrayOfFloat2 = { 0.0F, 1.0F };
    float[] arrayOfFloat3 = { 1.0F, 0.0F };
    float[] arrayOfFloat5 = { -1.0F, 0.0F };
    float[] arrayOfFloat4 = { 1.0F, 0.0F };
    TOUCH_DIRECTION = new float[][] { arrayOfFloat1, arrayOfFloat2, { -1.0F, 0.0F }, arrayOfFloat3, arrayOfFloat5, arrayOfFloat4 };
  }
  
  TouchResponse(Context paramContext, MotionLayout paramMotionLayout, XmlPullParser paramXmlPullParser)
  {
    this.mMotionLayout = paramMotionLayout;
    fillFromAttributeList(paramContext, Xml.asAttributeSet(paramXmlPullParser));
  }
  
  public TouchResponse(MotionLayout paramMotionLayout, OnSwipe paramOnSwipe)
  {
    this.mMotionLayout = paramMotionLayout;
    this.mTouchAnchorId = paramOnSwipe.getTouchAnchorId();
    int i = paramOnSwipe.getTouchAnchorSide();
    this.mTouchAnchorSide = i;
    if (i != -1)
    {
      paramMotionLayout = TOUCH_SIDES[i];
      this.mTouchAnchorX = paramMotionLayout[0];
      this.mTouchAnchorY = paramMotionLayout[1];
    }
    i = paramOnSwipe.getDragDirection();
    this.mTouchSide = i;
    paramMotionLayout = TOUCH_DIRECTION;
    if (i < paramMotionLayout.length)
    {
      paramMotionLayout = paramMotionLayout[i];
      this.mTouchDirectionX = paramMotionLayout[0];
      this.mTouchDirectionY = paramMotionLayout[1];
    }
    else
    {
      this.mTouchDirectionY = NaN.0F;
      this.mTouchDirectionX = NaN.0F;
      this.mIsRotateMode = true;
    }
    this.mMaxVelocity = paramOnSwipe.getMaxVelocity();
    this.mMaxAcceleration = paramOnSwipe.getMaxAcceleration();
    this.mMoveWhenScrollAtTop = paramOnSwipe.getMoveWhenScrollAtTop();
    this.mDragScale = paramOnSwipe.getDragScale();
    this.mDragThreshold = paramOnSwipe.getDragThreshold();
    this.mTouchRegionId = paramOnSwipe.getTouchRegionId();
    this.mOnTouchUp = paramOnSwipe.getOnTouchUp();
    this.mFlags = paramOnSwipe.getNestedScrollFlags();
    this.mLimitBoundsTo = paramOnSwipe.getLimitBoundsTo();
    this.mRotationCenterId = paramOnSwipe.getRotationCenterId();
    this.mSpringBoundary = paramOnSwipe.getSpringBoundary();
    this.mSpringDamping = paramOnSwipe.getSpringDamping();
    this.mSpringMass = paramOnSwipe.getSpringMass();
    this.mSpringStiffness = paramOnSwipe.getSpringStiffness();
    this.mSpringStopThreshold = paramOnSwipe.getSpringStopThreshold();
    this.mAutoCompleteMode = paramOnSwipe.getAutoCompleteMode();
  }
  
  private void fill(TypedArray paramTypedArray)
  {
    int j = paramTypedArray.getIndexCount();
    for (int i = 0; i < j; i++)
    {
      int k = paramTypedArray.getIndex(i);
      if (k == R.styleable.OnSwipe_touchAnchorId)
      {
        this.mTouchAnchorId = paramTypedArray.getResourceId(k, this.mTouchAnchorId);
      }
      else
      {
        Object localObject;
        if (k == R.styleable.OnSwipe_touchAnchorSide)
        {
          k = paramTypedArray.getInt(k, this.mTouchAnchorSide);
          this.mTouchAnchorSide = k;
          localObject = TOUCH_SIDES[k];
          this.mTouchAnchorX = localObject[0];
          this.mTouchAnchorY = localObject[1];
        }
        else if (k == R.styleable.OnSwipe_dragDirection)
        {
          k = paramTypedArray.getInt(k, this.mTouchSide);
          this.mTouchSide = k;
          localObject = TOUCH_DIRECTION;
          if (k < localObject.length)
          {
            localObject = localObject[k];
            this.mTouchDirectionX = localObject[0];
            this.mTouchDirectionY = localObject[1];
          }
          else
          {
            this.mTouchDirectionY = NaN.0F;
            this.mTouchDirectionX = NaN.0F;
            this.mIsRotateMode = true;
          }
        }
        else if (k == R.styleable.OnSwipe_maxVelocity)
        {
          this.mMaxVelocity = paramTypedArray.getFloat(k, this.mMaxVelocity);
        }
        else if (k == R.styleable.OnSwipe_maxAcceleration)
        {
          this.mMaxAcceleration = paramTypedArray.getFloat(k, this.mMaxAcceleration);
        }
        else if (k == R.styleable.OnSwipe_moveWhenScrollAtTop)
        {
          this.mMoveWhenScrollAtTop = paramTypedArray.getBoolean(k, this.mMoveWhenScrollAtTop);
        }
        else if (k == R.styleable.OnSwipe_dragScale)
        {
          this.mDragScale = paramTypedArray.getFloat(k, this.mDragScale);
        }
        else if (k == R.styleable.OnSwipe_dragThreshold)
        {
          this.mDragThreshold = paramTypedArray.getFloat(k, this.mDragThreshold);
        }
        else if (k == R.styleable.OnSwipe_touchRegionId)
        {
          this.mTouchRegionId = paramTypedArray.getResourceId(k, this.mTouchRegionId);
        }
        else if (k == R.styleable.OnSwipe_onTouchUp)
        {
          this.mOnTouchUp = paramTypedArray.getInt(k, this.mOnTouchUp);
        }
        else if (k == R.styleable.OnSwipe_nestedScrollFlags)
        {
          this.mFlags = paramTypedArray.getInteger(k, 0);
        }
        else if (k == R.styleable.OnSwipe_limitBoundsTo)
        {
          this.mLimitBoundsTo = paramTypedArray.getResourceId(k, 0);
        }
        else if (k == R.styleable.OnSwipe_rotationCenterId)
        {
          this.mRotationCenterId = paramTypedArray.getResourceId(k, this.mRotationCenterId);
        }
        else if (k == R.styleable.OnSwipe_springDamping)
        {
          this.mSpringDamping = paramTypedArray.getFloat(k, this.mSpringDamping);
        }
        else if (k == R.styleable.OnSwipe_springMass)
        {
          this.mSpringMass = paramTypedArray.getFloat(k, this.mSpringMass);
        }
        else if (k == R.styleable.OnSwipe_springStiffness)
        {
          this.mSpringStiffness = paramTypedArray.getFloat(k, this.mSpringStiffness);
        }
        else if (k == R.styleable.OnSwipe_springStopThreshold)
        {
          this.mSpringStopThreshold = paramTypedArray.getFloat(k, this.mSpringStopThreshold);
        }
        else if (k == R.styleable.OnSwipe_springBoundary)
        {
          this.mSpringBoundary = paramTypedArray.getInt(k, this.mSpringBoundary);
        }
        else if (k == R.styleable.OnSwipe_autoCompleteMode)
        {
          this.mAutoCompleteMode = paramTypedArray.getInt(k, this.mAutoCompleteMode);
        }
      }
    }
  }
  
  private void fillFromAttributeList(Context paramContext, AttributeSet paramAttributeSet)
  {
    paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.OnSwipe);
    fill(paramContext);
    paramContext.recycle();
  }
  
  float dot(float paramFloat1, float paramFloat2)
  {
    return this.mTouchDirectionX * paramFloat1 + this.mTouchDirectionY * paramFloat2;
  }
  
  public int getAnchorId()
  {
    return this.mTouchAnchorId;
  }
  
  public int getAutoCompleteMode()
  {
    return this.mAutoCompleteMode;
  }
  
  public int getFlags()
  {
    return this.mFlags;
  }
  
  RectF getLimitBoundsTo(ViewGroup paramViewGroup, RectF paramRectF)
  {
    int i = this.mLimitBoundsTo;
    if (i == -1) {
      return null;
    }
    paramViewGroup = paramViewGroup.findViewById(i);
    if (paramViewGroup == null) {
      return null;
    }
    paramRectF.set(paramViewGroup.getLeft(), paramViewGroup.getTop(), paramViewGroup.getRight(), paramViewGroup.getBottom());
    return paramRectF;
  }
  
  int getLimitBoundsToId()
  {
    return this.mLimitBoundsTo;
  }
  
  float getMaxAcceleration()
  {
    return this.mMaxAcceleration;
  }
  
  public float getMaxVelocity()
  {
    return this.mMaxVelocity;
  }
  
  boolean getMoveWhenScrollAtTop()
  {
    return this.mMoveWhenScrollAtTop;
  }
  
  float getProgressDirection(float paramFloat1, float paramFloat2)
  {
    float f = this.mMotionLayout.getProgress();
    this.mMotionLayout.getAnchorDpDt(this.mTouchAnchorId, f, this.mTouchAnchorX, this.mTouchAnchorY, this.mAnchorDpDt);
    f = this.mTouchDirectionX;
    float[] arrayOfFloat;
    if (f != 0.0F)
    {
      arrayOfFloat = this.mAnchorDpDt;
      if (arrayOfFloat[0] == 0.0F) {
        arrayOfFloat[0] = 1.0E-7F;
      }
      paramFloat1 = f * paramFloat1 / arrayOfFloat[0];
    }
    else
    {
      arrayOfFloat = this.mAnchorDpDt;
      if (arrayOfFloat[1] == 0.0F) {
        arrayOfFloat[1] = 1.0E-7F;
      }
      paramFloat1 = this.mTouchDirectionY * paramFloat2 / arrayOfFloat[1];
    }
    return paramFloat1;
  }
  
  public int getSpringBoundary()
  {
    return this.mSpringBoundary;
  }
  
  public float getSpringDamping()
  {
    return this.mSpringDamping;
  }
  
  public float getSpringMass()
  {
    return this.mSpringMass;
  }
  
  public float getSpringStiffness()
  {
    return this.mSpringStiffness;
  }
  
  public float getSpringStopThreshold()
  {
    return this.mSpringStopThreshold;
  }
  
  RectF getTouchRegion(ViewGroup paramViewGroup, RectF paramRectF)
  {
    int i = this.mTouchRegionId;
    if (i == -1) {
      return null;
    }
    paramViewGroup = paramViewGroup.findViewById(i);
    if (paramViewGroup == null) {
      return null;
    }
    paramRectF.set(paramViewGroup.getLeft(), paramViewGroup.getTop(), paramViewGroup.getRight(), paramViewGroup.getBottom());
    return paramRectF;
  }
  
  int getTouchRegionId()
  {
    return this.mTouchRegionId;
  }
  
  boolean isDragStarted()
  {
    return this.mDragStarted;
  }
  
  void processTouchEvent(MotionEvent paramMotionEvent, MotionLayout.MotionTracker paramMotionTracker, int paramInt, MotionScene paramMotionScene)
  {
    if (this.mIsRotateMode)
    {
      processTouchRotateEvent(paramMotionEvent, paramMotionTracker, paramInt, paramMotionScene);
      return;
    }
    paramMotionTracker.addMovement(paramMotionEvent);
    float f1;
    float f3;
    float f2;
    float f4;
    switch (paramMotionEvent.getAction())
    {
    default: 
      break;
    case 2: 
      f1 = paramMotionEvent.getRawY() - this.mLastTouchY;
      f3 = paramMotionEvent.getRawX() - this.mLastTouchX;
      if ((Math.abs(this.mTouchDirectionX * f3 + this.mTouchDirectionY * f1) > this.mDragThreshold) || (this.mDragStarted))
      {
        f2 = this.mMotionLayout.getProgress();
        if (!this.mDragStarted)
        {
          this.mDragStarted = true;
          this.mMotionLayout.setProgress(f2);
        }
        paramInt = this.mTouchAnchorId;
        if (paramInt != -1)
        {
          this.mMotionLayout.getAnchorDpDt(paramInt, f2, this.mTouchAnchorX, this.mTouchAnchorY, this.mAnchorDpDt);
        }
        else
        {
          f4 = Math.min(this.mMotionLayout.getWidth(), this.mMotionLayout.getHeight());
          paramMotionScene = this.mAnchorDpDt;
          paramMotionScene[1] = (this.mTouchDirectionY * f4);
          paramMotionScene[0] = (this.mTouchDirectionX * f4);
        }
        f4 = this.mTouchDirectionX;
        paramMotionScene = this.mAnchorDpDt;
        if (Math.abs((f4 * paramMotionScene[0] + this.mTouchDirectionY * paramMotionScene[1]) * this.mDragScale) < 0.01D)
        {
          paramMotionScene = this.mAnchorDpDt;
          paramMotionScene[0] = 0.01F;
          paramMotionScene[1] = 0.01F;
        }
        if (this.mTouchDirectionX != 0.0F) {
          f1 = f3 / this.mAnchorDpDt[0];
        } else {
          f1 /= this.mAnchorDpDt[1];
        }
        f2 = Math.max(Math.min(f2 + f1, 1.0F), 0.0F);
        f1 = f2;
        if (this.mOnTouchUp == 6) {
          f1 = Math.max(f2, 0.01F);
        }
        f2 = f1;
        if (this.mOnTouchUp == 7) {
          f2 = Math.min(f1, 0.99F);
        }
        f1 = this.mMotionLayout.getProgress();
        if (f2 != f1)
        {
          if ((f1 == 0.0F) || (f1 == 1.0F))
          {
            paramMotionScene = this.mMotionLayout;
            boolean bool;
            if (f1 == 0.0F) {
              bool = true;
            } else {
              bool = false;
            }
            paramMotionScene.endTrigger(bool);
          }
          this.mMotionLayout.setProgress(f2);
          paramMotionTracker.computeCurrentVelocity(1000);
          f1 = paramMotionTracker.getXVelocity();
          f2 = paramMotionTracker.getYVelocity();
          if (this.mTouchDirectionX != 0.0F) {
            f1 /= this.mAnchorDpDt[0];
          } else {
            f1 = f2 / this.mAnchorDpDt[1];
          }
          this.mMotionLayout.mLastVelocity = f1;
        }
        else
        {
          this.mMotionLayout.mLastVelocity = 0.0F;
        }
        this.mLastTouchX = paramMotionEvent.getRawX();
        this.mLastTouchY = paramMotionEvent.getRawY();
      }
      break;
    case 1: 
      this.mDragStarted = false;
      paramMotionTracker.computeCurrentVelocity(1000);
      f3 = paramMotionTracker.getXVelocity();
      f1 = paramMotionTracker.getYVelocity();
      f4 = this.mMotionLayout.getProgress();
      f2 = f4;
      paramInt = this.mTouchAnchorId;
      if (paramInt != -1)
      {
        this.mMotionLayout.getAnchorDpDt(paramInt, f2, this.mTouchAnchorX, this.mTouchAnchorY, this.mAnchorDpDt);
      }
      else
      {
        f5 = Math.min(this.mMotionLayout.getWidth(), this.mMotionLayout.getHeight());
        paramMotionEvent = this.mAnchorDpDt;
        paramMotionEvent[1] = (this.mTouchDirectionY * f5);
        paramMotionEvent[0] = (this.mTouchDirectionX * f5);
      }
      float f5 = this.mTouchDirectionX;
      paramMotionEvent = this.mAnchorDpDt;
      float f6 = paramMotionEvent[0];
      float f7 = this.mTouchDirectionY;
      f7 = paramMotionEvent[1];
      if (f5 != 0.0F) {
        f1 = f3 / f6;
      } else {
        f1 /= f7;
      }
      f3 = f2;
      if (!Float.isNaN(f1)) {
        f3 = f2 + f1 / 3.0F;
      }
      if ((f3 != 0.0F) && (f3 != 1.0F))
      {
        paramInt = this.mOnTouchUp;
        if (paramInt != 3)
        {
          if (f3 < 0.5D) {
            f3 = 0.0F;
          } else {
            f3 = 1.0F;
          }
          f2 = f1;
          if (paramInt == 6)
          {
            f2 = f1;
            if (f4 + f1 < 0.0F) {
              f2 = Math.abs(f1);
            }
            f3 = 1.0F;
          }
          f1 = f2;
          if (this.mOnTouchUp == 7)
          {
            f1 = f2;
            if (f4 + f2 > 1.0F) {
              f1 = -Math.abs(f2);
            }
            f3 = 0.0F;
          }
          this.mMotionLayout.touchAnimateTo(this.mOnTouchUp, f3, f1);
          if ((0.0F < f4) && (1.0F > f4)) {
            return;
          }
          this.mMotionLayout.setState(MotionLayout.TransitionState.FINISHED);
          return;
        }
      }
      if ((0.0F < f3) && (1.0F > f3)) {
        break;
      }
      this.mMotionLayout.setState(MotionLayout.TransitionState.FINISHED);
      break;
    case 0: 
      this.mLastTouchX = paramMotionEvent.getRawX();
      this.mLastTouchY = paramMotionEvent.getRawY();
      this.mDragStarted = false;
    }
  }
  
  void processTouchRotateEvent(MotionEvent paramMotionEvent, MotionLayout.MotionTracker paramMotionTracker, int paramInt, MotionScene paramMotionScene)
  {
    paramMotionTracker.addMovement(paramMotionEvent);
    float f1;
    float f4;
    float f3;
    float f2;
    float f5;
    float f6;
    double d;
    switch (paramMotionEvent.getAction())
    {
    default: 
      break;
    case 2: 
      paramMotionEvent.getRawY();
      f1 = this.mLastTouchY;
      paramMotionEvent.getRawX();
      f1 = this.mLastTouchX;
      f4 = this.mMotionLayout.getWidth() / 2.0F;
      f3 = this.mMotionLayout.getHeight() / 2.0F;
      paramInt = this.mRotationCenterId;
      if (paramInt != -1)
      {
        paramMotionScene = this.mMotionLayout.findViewById(paramInt);
        this.mMotionLayout.getLocationOnScreen(this.mTempLoc);
        f1 = this.mTempLoc[0] + (paramMotionScene.getLeft() + paramMotionScene.getRight()) / 2.0F;
        f2 = this.mTempLoc[1] + (paramMotionScene.getTop() + paramMotionScene.getBottom()) / 2.0F;
      }
      for (;;)
      {
        break;
        paramInt = this.mTouchAnchorId;
        f1 = f4;
        f2 = f3;
        if (paramInt != -1)
        {
          paramMotionScene = this.mMotionLayout.getMotionController(paramInt);
          paramMotionScene = this.mMotionLayout.findViewById(paramMotionScene.getAnimateRelativeTo());
          if (paramMotionScene == null)
          {
            Log.e("TouchResponse", "could not find view to animate to");
            f1 = f4;
            f2 = f3;
          }
          else
          {
            this.mMotionLayout.getLocationOnScreen(this.mTempLoc);
            f1 = this.mTempLoc[0] + (paramMotionScene.getLeft() + paramMotionScene.getRight()) / 2.0F;
            f2 = this.mTempLoc[1] + (paramMotionScene.getTop() + paramMotionScene.getBottom()) / 2.0F;
          }
        }
      }
      f5 = paramMotionEvent.getRawX();
      f6 = paramMotionEvent.getRawY();
      d = Math.atan2(paramMotionEvent.getRawY() - f2, paramMotionEvent.getRawX() - f1);
      f4 = (float)((d - Math.atan2(this.mLastTouchY - f2, this.mLastTouchX - f1)) * 180.0D / 3.141592653589793D);
      if (f4 > 330.0F)
      {
        f3 = f4 - 360.0F;
      }
      else
      {
        f3 = f4;
        if (f4 < -330.0F) {
          f3 = f4 + 360.0F;
        }
      }
      if ((Math.abs(f3) > 0.01D) || (this.mDragStarted))
      {
        f4 = this.mMotionLayout.getProgress();
        if (!this.mDragStarted)
        {
          this.mDragStarted = true;
          this.mMotionLayout.setProgress(f4);
        }
        paramInt = this.mTouchAnchorId;
        if (paramInt != -1)
        {
          this.mMotionLayout.getAnchorDpDt(paramInt, f4, this.mTouchAnchorX, this.mTouchAnchorY, this.mAnchorDpDt);
          paramMotionScene = this.mAnchorDpDt;
          paramMotionScene[1] = ((float)Math.toDegrees(paramMotionScene[1]));
        }
        else
        {
          this.mAnchorDpDt[1] = 360.0F;
        }
        f4 = Math.max(Math.min(f4 + this.mDragScale * f3 / this.mAnchorDpDt[1], 1.0F), 0.0F);
        f3 = this.mMotionLayout.getProgress();
        if (f4 != f3)
        {
          if ((f3 == 0.0F) || (f3 == 1.0F))
          {
            paramMotionScene = this.mMotionLayout;
            boolean bool;
            if (f3 == 0.0F) {
              bool = true;
            } else {
              bool = false;
            }
            paramMotionScene.endTrigger(bool);
          }
          this.mMotionLayout.setProgress(f4);
          paramMotionTracker.computeCurrentVelocity(1000);
          f4 = paramMotionTracker.getXVelocity();
          f3 = paramMotionTracker.getYVelocity();
          f1 = (float)(Math.hypot(f3, f4) * Math.sin(Math.atan2(f3, f4) - d) / Math.hypot(f5 - f1, f6 - f2));
          this.mMotionLayout.mLastVelocity = ((float)Math.toDegrees(f1));
        }
        else
        {
          this.mMotionLayout.mLastVelocity = 0.0F;
        }
        this.mLastTouchX = paramMotionEvent.getRawX();
        this.mLastTouchY = paramMotionEvent.getRawY();
      }
      break;
    case 1: 
      this.mDragStarted = false;
      paramMotionTracker.computeCurrentVelocity(16);
      f6 = paramMotionTracker.getXVelocity();
      f5 = paramMotionTracker.getYVelocity();
      f4 = this.mMotionLayout.getProgress();
      f3 = f4;
      f1 = this.mMotionLayout.getWidth() / 2.0F;
      f2 = this.mMotionLayout.getHeight() / 2.0F;
      paramInt = this.mRotationCenterId;
      if (paramInt != -1)
      {
        paramMotionTracker = this.mMotionLayout.findViewById(paramInt);
        this.mMotionLayout.getLocationOnScreen(this.mTempLoc);
        f1 = this.mTempLoc[0] + (paramMotionTracker.getLeft() + paramMotionTracker.getRight()) / 2.0F;
        f2 = this.mTempLoc[1] + (paramMotionTracker.getTop() + paramMotionTracker.getBottom()) / 2.0F;
      }
      else
      {
        paramInt = this.mTouchAnchorId;
        if (paramInt != -1)
        {
          paramMotionTracker = this.mMotionLayout.getMotionController(paramInt);
          paramMotionTracker = this.mMotionLayout.findViewById(paramMotionTracker.getAnimateRelativeTo());
          this.mMotionLayout.getLocationOnScreen(this.mTempLoc);
          f1 = this.mTempLoc[0] + (paramMotionTracker.getLeft() + paramMotionTracker.getRight()) / 2.0F;
          f2 = this.mTempLoc[1] + (paramMotionTracker.getTop() + paramMotionTracker.getBottom()) / 2.0F;
        }
      }
      f1 = paramMotionEvent.getRawX() - f1;
      f2 = paramMotionEvent.getRawY() - f2;
      d = Math.toDegrees(Math.atan2(f2, f1));
      paramInt = this.mTouchAnchorId;
      if (paramInt != -1)
      {
        this.mMotionLayout.getAnchorDpDt(paramInt, f3, this.mTouchAnchorX, this.mTouchAnchorY, this.mAnchorDpDt);
        paramMotionEvent = this.mAnchorDpDt;
        paramMotionEvent[1] = ((float)Math.toDegrees(paramMotionEvent[1]));
      }
      else
      {
        this.mAnchorDpDt[1] = 360.0F;
      }
      f2 = (float)(Math.toDegrees(Math.atan2(f5 + f2, f6 + f1)) - d) * 62.5F;
      f1 = f3;
      if (!Float.isNaN(f2)) {
        f1 = f3 + f2 * 3.0F * this.mDragScale / this.mAnchorDpDt[1];
      }
      if ((f1 != 0.0F) && (f1 != 1.0F))
      {
        paramInt = this.mOnTouchUp;
        if (paramInt != 3)
        {
          f3 = this.mDragScale * f2 / this.mAnchorDpDt[1];
          if (f1 < 0.5D) {
            f2 = 0.0F;
          } else {
            f2 = 1.0F;
          }
          f1 = f3;
          if (paramInt == 6)
          {
            f1 = f3;
            if (f4 + f3 < 0.0F) {
              f1 = Math.abs(f3);
            }
            f2 = 1.0F;
          }
          f3 = f2;
          f2 = f1;
          if (this.mOnTouchUp == 7)
          {
            f2 = f1;
            if (f4 + f1 > 1.0F) {
              f2 = -Math.abs(f1);
            }
            f3 = 0.0F;
          }
          this.mMotionLayout.touchAnimateTo(this.mOnTouchUp, f3, f2 * 3.0F);
          if ((0.0F >= f4) || (1.0F <= f4)) {
            this.mMotionLayout.setState(MotionLayout.TransitionState.FINISHED);
          }
          break;
        }
      }
      if ((0.0F >= f1) || (1.0F <= f1)) {
        this.mMotionLayout.setState(MotionLayout.TransitionState.FINISHED);
      }
      break;
    case 0: 
      this.mLastTouchX = paramMotionEvent.getRawX();
      this.mLastTouchY = paramMotionEvent.getRawY();
      this.mDragStarted = false;
    }
  }
  
  void scrollMove(float paramFloat1, float paramFloat2)
  {
    float f1 = this.mTouchDirectionX;
    f1 = this.mTouchDirectionY;
    f1 = this.mMotionLayout.getProgress();
    if (!this.mDragStarted)
    {
      this.mDragStarted = true;
      this.mMotionLayout.setProgress(f1);
    }
    this.mMotionLayout.getAnchorDpDt(this.mTouchAnchorId, f1, this.mTouchAnchorX, this.mTouchAnchorY, this.mAnchorDpDt);
    float f2 = this.mTouchDirectionX;
    float[] arrayOfFloat = this.mAnchorDpDt;
    if (Math.abs(f2 * arrayOfFloat[0] + this.mTouchDirectionY * arrayOfFloat[1]) < 0.01D)
    {
      arrayOfFloat = this.mAnchorDpDt;
      arrayOfFloat[0] = 0.01F;
      arrayOfFloat[1] = 0.01F;
    }
    f2 = this.mTouchDirectionX;
    if (f2 != 0.0F) {
      paramFloat1 = f2 * paramFloat1 / this.mAnchorDpDt[0];
    } else {
      paramFloat1 = this.mTouchDirectionY * paramFloat2 / this.mAnchorDpDt[1];
    }
    paramFloat1 = Math.max(Math.min(f1 + paramFloat1, 1.0F), 0.0F);
    if (paramFloat1 != this.mMotionLayout.getProgress()) {
      this.mMotionLayout.setProgress(paramFloat1);
    }
  }
  
  void scrollUp(float paramFloat1, float paramFloat2)
  {
    int j = 0;
    this.mDragStarted = false;
    float f2 = this.mMotionLayout.getProgress();
    this.mMotionLayout.getAnchorDpDt(this.mTouchAnchorId, f2, this.mTouchAnchorX, this.mTouchAnchorY, this.mAnchorDpDt);
    float f6 = this.mTouchDirectionX;
    Object localObject = this.mAnchorDpDt;
    float f3 = localObject[0];
    float f4 = this.mTouchDirectionY;
    float f5 = localObject[1];
    float f1 = 0.0F;
    if (f6 != 0.0F) {
      paramFloat1 = f6 * paramFloat1 / f3;
    } else {
      paramFloat1 = f4 * paramFloat2 / f5;
    }
    paramFloat2 = f2;
    if (!Float.isNaN(paramFloat1)) {
      paramFloat2 = f2 + paramFloat1 / 3.0F;
    }
    if (paramFloat2 != 0.0F)
    {
      int i;
      if (paramFloat2 != 1.0F) {
        i = 1;
      } else {
        i = 0;
      }
      int k = this.mOnTouchUp;
      if (k != 3) {
        j = 1;
      }
      if ((j & i) != 0)
      {
        localObject = this.mMotionLayout;
        if (paramFloat2 < 0.5D) {
          paramFloat2 = f1;
        } else {
          paramFloat2 = 1.0F;
        }
        ((MotionLayout)localObject).touchAnimateTo(k, paramFloat2, paramFloat1);
      }
    }
  }
  
  public void setAnchorId(int paramInt)
  {
    this.mTouchAnchorId = paramInt;
  }
  
  void setAutoCompleteMode(int paramInt)
  {
    this.mAutoCompleteMode = paramInt;
  }
  
  void setDown(float paramFloat1, float paramFloat2)
  {
    this.mLastTouchX = paramFloat1;
    this.mLastTouchY = paramFloat2;
  }
  
  public void setMaxAcceleration(float paramFloat)
  {
    this.mMaxAcceleration = paramFloat;
  }
  
  public void setMaxVelocity(float paramFloat)
  {
    this.mMaxVelocity = paramFloat;
  }
  
  public void setRTL(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      localObject = TOUCH_DIRECTION;
      localObject[4] = localObject[3];
      localObject[5] = localObject[2];
      localObject = TOUCH_SIDES;
      localObject[5] = localObject[2];
      localObject[6] = localObject[1];
    }
    else
    {
      localObject = TOUCH_DIRECTION;
      localObject[4] = localObject[2];
      localObject[5] = localObject[3];
      localObject = TOUCH_SIDES;
      localObject[5] = localObject[1];
      localObject[6] = localObject[2];
    }
    Object localObject = TOUCH_SIDES[this.mTouchAnchorSide];
    this.mTouchAnchorX = localObject[0];
    this.mTouchAnchorY = localObject[1];
    int i = this.mTouchSide;
    localObject = TOUCH_DIRECTION;
    if (i >= localObject.length) {
      return;
    }
    localObject = localObject[i];
    this.mTouchDirectionX = localObject[0];
    this.mTouchDirectionY = localObject[1];
  }
  
  public void setTouchAnchorLocation(float paramFloat1, float paramFloat2)
  {
    this.mTouchAnchorX = paramFloat1;
    this.mTouchAnchorY = paramFloat2;
  }
  
  public void setTouchUpMode(int paramInt)
  {
    this.mOnTouchUp = paramInt;
  }
  
  void setUpTouchEvent(float paramFloat1, float paramFloat2)
  {
    this.mLastTouchX = paramFloat1;
    this.mLastTouchY = paramFloat2;
    this.mDragStarted = false;
  }
  
  void setupTouch()
  {
    Object localObject = null;
    int i = this.mTouchAnchorId;
    if (i != -1)
    {
      View localView = this.mMotionLayout.findViewById(i);
      localObject = localView;
      if (localView == null)
      {
        StringBuilder localStringBuilder = new StringBuilder().append("cannot find TouchAnchorId @id/");
        localObject = Debug.getName(this.mMotionLayout.getContext(), this.mTouchAnchorId);
        Log5ECF72.a(localObject);
        LogE84000.a(localObject);
        Log229316.a(localObject);
        Log.e("TouchResponse", (String)localObject);
        localObject = localView;
      }
    }
    if ((localObject instanceof NestedScrollView))
    {
      localObject = (NestedScrollView)localObject;
      ((NestedScrollView)localObject).setOnTouchListener(new View.OnTouchListener()
      {
        public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
        {
          return false;
        }
      });
      ((NestedScrollView)localObject).setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener()
      {
        public void onScrollChange(NestedScrollView paramAnonymousNestedScrollView, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4) {}
      });
    }
  }
  
  public String toString()
  {
    String str;
    if (Float.isNaN(this.mTouchDirectionX)) {
      str = "rotation";
    } else {
      str = this.mTouchDirectionX + " , " + this.mTouchDirectionY;
    }
    return str;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/motion/widget/TouchResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */