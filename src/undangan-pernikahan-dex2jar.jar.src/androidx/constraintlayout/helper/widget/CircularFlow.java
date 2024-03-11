package androidx.constraintlayout.helper.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.R.styleable;
import androidx.constraintlayout.widget.VirtualLayout;
import java.util.Arrays;
import java.util.HashMap;

public class CircularFlow
  extends VirtualLayout
{
  private static float DEFAULT_ANGLE = 0.0F;
  private static int DEFAULT_RADIUS = 0;
  private static final String TAG = "CircularFlow";
  private float[] mAngles;
  ConstraintLayout mContainer;
  private int mCountAngle;
  private int mCountRadius;
  private int[] mRadius;
  private String mReferenceAngles;
  private Float mReferenceDefaultAngle;
  private Integer mReferenceDefaultRadius;
  private String mReferenceRadius;
  int mViewCenter;
  
  public CircularFlow(Context paramContext)
  {
    super(paramContext);
  }
  
  public CircularFlow(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public CircularFlow(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  private void addAngle(String paramString)
  {
    if ((paramString != null) && (paramString.length() != 0))
    {
      if (this.myContext == null) {
        return;
      }
      float[] arrayOfFloat = this.mAngles;
      if (arrayOfFloat == null) {
        return;
      }
      if (this.mCountAngle + 1 > arrayOfFloat.length) {
        this.mAngles = Arrays.copyOf(arrayOfFloat, arrayOfFloat.length + 1);
      }
      this.mAngles[this.mCountAngle] = Integer.parseInt(paramString);
      this.mCountAngle += 1;
      return;
    }
  }
  
  private void addRadius(String paramString)
  {
    if ((paramString != null) && (paramString.length() != 0))
    {
      if (this.myContext == null) {
        return;
      }
      int[] arrayOfInt = this.mRadius;
      if (arrayOfInt == null) {
        return;
      }
      if (this.mCountRadius + 1 > arrayOfInt.length) {
        this.mRadius = Arrays.copyOf(arrayOfInt, arrayOfInt.length + 1);
      }
      this.mRadius[this.mCountRadius] = ((int)(Integer.parseInt(paramString) * this.myContext.getResources().getDisplayMetrics().density));
      this.mCountRadius += 1;
      return;
    }
  }
  
  private void anchorReferences()
  {
    this.mContainer = ((ConstraintLayout)getParent());
    for (int i = 0; i < this.mCount; i++)
    {
      View localView = this.mContainer.getViewById(this.mIds[i]);
      if (localView != null)
      {
        int j = DEFAULT_RADIUS;
        float f = DEFAULT_ANGLE;
        Object localObject = this.mRadius;
        if ((localObject != null) && (i < localObject.length))
        {
          j = localObject[i];
        }
        else
        {
          localObject = this.mReferenceDefaultRadius;
          if ((localObject != null) && (((Integer)localObject).intValue() != -1))
          {
            this.mCountRadius += 1;
            if (this.mRadius == null) {
              this.mRadius = new int[1];
            }
            localObject = getRadius();
            this.mRadius = ((int[])localObject);
            localObject[(this.mCountRadius - 1)] = j;
          }
          else
          {
            Log.e("CircularFlow", "Added radius to view with id: " + (String)this.mMap.get(Integer.valueOf(localView.getId())));
          }
        }
        localObject = this.mAngles;
        if ((localObject != null) && (i < localObject.length))
        {
          f = localObject[i];
        }
        else
        {
          localObject = this.mReferenceDefaultAngle;
          if ((localObject != null) && (((Float)localObject).floatValue() != -1.0F))
          {
            this.mCountAngle += 1;
            if (this.mAngles == null) {
              this.mAngles = new float[1];
            }
            localObject = getAngles();
            this.mAngles = ((float[])localObject);
            localObject[(this.mCountAngle - 1)] = f;
          }
          else
          {
            Log.e("CircularFlow", "Added angle to view with id: " + (String)this.mMap.get(Integer.valueOf(localView.getId())));
          }
        }
        localObject = (ConstraintLayout.LayoutParams)localView.getLayoutParams();
        ((ConstraintLayout.LayoutParams)localObject).circleAngle = f;
        ((ConstraintLayout.LayoutParams)localObject).circleConstraint = this.mViewCenter;
        ((ConstraintLayout.LayoutParams)localObject).circleRadius = j;
        localView.setLayoutParams((ViewGroup.LayoutParams)localObject);
      }
    }
    applyLayoutFeatures();
  }
  
  private float[] removeAngle(float[] paramArrayOfFloat, int paramInt)
  {
    if ((paramArrayOfFloat != null) && (paramInt >= 0) && (paramInt < this.mCountAngle)) {
      return removeElementFromArray(paramArrayOfFloat, paramInt);
    }
    return paramArrayOfFloat;
  }
  
  public static float[] removeElementFromArray(float[] paramArrayOfFloat, int paramInt)
  {
    float[] arrayOfFloat = new float[paramArrayOfFloat.length - 1];
    int i = 0;
    int j = 0;
    while (i < paramArrayOfFloat.length)
    {
      if (i != paramInt)
      {
        arrayOfFloat[j] = paramArrayOfFloat[i];
        j++;
      }
      i++;
    }
    return arrayOfFloat;
  }
  
  public static int[] removeElementFromArray(int[] paramArrayOfInt, int paramInt)
  {
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    int i = 0;
    int j = 0;
    while (i < paramArrayOfInt.length)
    {
      if (i != paramInt)
      {
        arrayOfInt[j] = paramArrayOfInt[i];
        j++;
      }
      i++;
    }
    return arrayOfInt;
  }
  
  private int[] removeRadius(int[] paramArrayOfInt, int paramInt)
  {
    if ((paramArrayOfInt != null) && (paramInt >= 0) && (paramInt < this.mCountRadius)) {
      return removeElementFromArray(paramArrayOfInt, paramInt);
    }
    return paramArrayOfInt;
  }
  
  private void setAngles(String paramString)
  {
    if (paramString == null) {
      return;
    }
    int i = 0;
    this.mCountAngle = 0;
    for (;;)
    {
      int j = paramString.indexOf(',', i);
      if (j == -1)
      {
        addAngle(paramString.substring(i).trim());
        return;
      }
      addAngle(paramString.substring(i, j).trim());
      i = j + 1;
    }
  }
  
  private void setRadius(String paramString)
  {
    if (paramString == null) {
      return;
    }
    int i = 0;
    this.mCountRadius = 0;
    for (;;)
    {
      int j = paramString.indexOf(',', i);
      if (j == -1)
      {
        addRadius(paramString.substring(i).trim());
        return;
      }
      addRadius(paramString.substring(i, j).trim());
      i = j + 1;
    }
  }
  
  public void addViewToCircularFlow(View paramView, int paramInt, float paramFloat)
  {
    if (containsId(paramView.getId())) {
      return;
    }
    addView(paramView);
    this.mCountAngle += 1;
    paramView = getAngles();
    this.mAngles = paramView;
    paramView[(this.mCountAngle - 1)] = paramFloat;
    this.mCountRadius += 1;
    paramView = getRadius();
    this.mRadius = paramView;
    paramView[(this.mCountRadius - 1)] = ((int)(paramInt * this.myContext.getResources().getDisplayMetrics().density));
    anchorReferences();
  }
  
  public float[] getAngles()
  {
    return Arrays.copyOf(this.mAngles, this.mCountAngle);
  }
  
  public int[] getRadius()
  {
    return Arrays.copyOf(this.mRadius, this.mCountRadius);
  }
  
  protected void init(AttributeSet paramAttributeSet)
  {
    super.init(paramAttributeSet);
    if (paramAttributeSet != null)
    {
      paramAttributeSet = getContext().obtainStyledAttributes(paramAttributeSet, R.styleable.ConstraintLayout_Layout);
      int j = paramAttributeSet.getIndexCount();
      for (int i = 0; i < j; i++)
      {
        int k = paramAttributeSet.getIndex(i);
        if (k == R.styleable.ConstraintLayout_Layout_circularflow_viewCenter)
        {
          this.mViewCenter = paramAttributeSet.getResourceId(k, 0);
        }
        else
        {
          Object localObject;
          if (k == R.styleable.ConstraintLayout_Layout_circularflow_angles)
          {
            localObject = paramAttributeSet.getString(k);
            this.mReferenceAngles = ((String)localObject);
            setAngles((String)localObject);
          }
          else if (k == R.styleable.ConstraintLayout_Layout_circularflow_radiusInDP)
          {
            localObject = paramAttributeSet.getString(k);
            this.mReferenceRadius = ((String)localObject);
            setRadius((String)localObject);
          }
          else if (k == R.styleable.ConstraintLayout_Layout_circularflow_defaultAngle)
          {
            localObject = Float.valueOf(paramAttributeSet.getFloat(k, DEFAULT_ANGLE));
            this.mReferenceDefaultAngle = ((Float)localObject);
            setDefaultAngle(((Float)localObject).floatValue());
          }
          else if (k == R.styleable.ConstraintLayout_Layout_circularflow_defaultRadius)
          {
            localObject = Integer.valueOf(paramAttributeSet.getDimensionPixelSize(k, DEFAULT_RADIUS));
            this.mReferenceDefaultRadius = ((Integer)localObject);
            setDefaultRadius(((Integer)localObject).intValue());
          }
        }
      }
      paramAttributeSet.recycle();
    }
  }
  
  public boolean isUpdatable(View paramView)
  {
    boolean bool2 = containsId(paramView.getId());
    boolean bool1 = false;
    if (!bool2) {
      return false;
    }
    if (indexFromId(paramView.getId()) != -1) {
      bool1 = true;
    }
    return bool1;
  }
  
  public void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    Object localObject = this.mReferenceAngles;
    if (localObject != null)
    {
      this.mAngles = new float[1];
      setAngles((String)localObject);
    }
    localObject = this.mReferenceRadius;
    if (localObject != null)
    {
      this.mRadius = new int[1];
      setRadius((String)localObject);
    }
    localObject = this.mReferenceDefaultAngle;
    if (localObject != null) {
      setDefaultAngle(((Float)localObject).floatValue());
    }
    localObject = this.mReferenceDefaultRadius;
    if (localObject != null) {
      setDefaultRadius(((Integer)localObject).intValue());
    }
    anchorReferences();
  }
  
  public int removeView(View paramView)
  {
    int i = super.removeView(paramView);
    if (i == -1) {
      return i;
    }
    ConstraintSet localConstraintSet = new ConstraintSet();
    localConstraintSet.clone(this.mContainer);
    localConstraintSet.clear(paramView.getId(), 8);
    localConstraintSet.applyTo(this.mContainer);
    paramView = this.mAngles;
    if (i < paramView.length)
    {
      this.mAngles = removeAngle(paramView, i);
      this.mCountAngle -= 1;
    }
    paramView = this.mRadius;
    if (i < paramView.length)
    {
      this.mRadius = removeRadius(paramView, i);
      this.mCountRadius -= 1;
    }
    anchorReferences();
    return i;
  }
  
  public void setDefaultAngle(float paramFloat)
  {
    DEFAULT_ANGLE = paramFloat;
  }
  
  public void setDefaultRadius(int paramInt)
  {
    DEFAULT_RADIUS = paramInt;
  }
  
  public void updateAngle(View paramView, float paramFloat)
  {
    if (!isUpdatable(paramView))
    {
      Log.e("CircularFlow", "It was not possible to update angle to view with id: " + paramView.getId());
      return;
    }
    int i = indexFromId(paramView.getId());
    if (i > this.mAngles.length) {
      return;
    }
    paramView = getAngles();
    this.mAngles = paramView;
    paramView[i] = paramFloat;
    anchorReferences();
  }
  
  public void updateRadius(View paramView, int paramInt)
  {
    if (!isUpdatable(paramView))
    {
      Log.e("CircularFlow", "It was not possible to update radius to view with id: " + paramView.getId());
      return;
    }
    int i = indexFromId(paramView.getId());
    if (i > this.mRadius.length) {
      return;
    }
    paramView = getRadius();
    this.mRadius = paramView;
    paramView[i] = ((int)(paramInt * this.myContext.getResources().getDisplayMetrics().density));
    anchorReferences();
  }
  
  public void updateReference(View paramView, int paramInt, float paramFloat)
  {
    if (!isUpdatable(paramView))
    {
      Log.e("CircularFlow", "It was not possible to update radius and angle to view with id: " + paramView.getId());
      return;
    }
    int i = indexFromId(paramView.getId());
    if (getAngles().length > i)
    {
      paramView = getAngles();
      this.mAngles = paramView;
      paramView[i] = paramFloat;
    }
    if (getRadius().length > i)
    {
      paramView = getRadius();
      this.mRadius = paramView;
      paramView[i] = ((int)(paramInt * this.myContext.getResources().getDisplayMetrics().density));
    }
    anchorReferences();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/helper/widget/CircularFlow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */