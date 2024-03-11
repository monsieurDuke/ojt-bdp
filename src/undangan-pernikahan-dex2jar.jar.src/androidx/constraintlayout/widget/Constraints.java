package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

public class Constraints
  extends ViewGroup
{
  public static final String TAG = "Constraints";
  ConstraintSet myConstraintSet;
  
  public Constraints(Context paramContext)
  {
    super(paramContext);
    super.setVisibility(8);
  }
  
  public Constraints(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramAttributeSet);
    super.setVisibility(8);
  }
  
  public Constraints(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramAttributeSet);
    super.setVisibility(8);
  }
  
  private void init(AttributeSet paramAttributeSet)
  {
    Log.v("Constraints", " ################# init");
  }
  
  protected LayoutParams generateDefaultLayoutParams()
  {
    return new LayoutParams(-2, -2);
  }
  
  protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    return new ConstraintLayout.LayoutParams(paramLayoutParams);
  }
  
  public LayoutParams generateLayoutParams(AttributeSet paramAttributeSet)
  {
    return new LayoutParams(getContext(), paramAttributeSet);
  }
  
  public ConstraintSet getConstraintSet()
  {
    if (this.myConstraintSet == null) {
      this.myConstraintSet = new ConstraintSet();
    }
    this.myConstraintSet.clone(this);
    return this.myConstraintSet;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
  
  public static class LayoutParams
    extends ConstraintLayout.LayoutParams
  {
    public float alpha = 1.0F;
    public boolean applyElevation = false;
    public float elevation = 0.0F;
    public float rotation = 0.0F;
    public float rotationX = 0.0F;
    public float rotationY = 0.0F;
    public float scaleX = 1.0F;
    public float scaleY = 1.0F;
    public float transformPivotX = 0.0F;
    public float transformPivotY = 0.0F;
    public float translationX = 0.0F;
    public float translationY = 0.0F;
    public float translationZ = 0.0F;
    
    public LayoutParams(int paramInt1, int paramInt2)
    {
      super(paramInt2);
    }
    
    public LayoutParams(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
      paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.ConstraintSet);
      int j = paramContext.getIndexCount();
      for (int i = 0; i < j; i++)
      {
        int k = paramContext.getIndex(i);
        if (k == R.styleable.ConstraintSet_android_alpha) {
          this.alpha = paramContext.getFloat(k, this.alpha);
        } else if (k == R.styleable.ConstraintSet_android_elevation)
        {
          if (Build.VERSION.SDK_INT >= 21)
          {
            this.elevation = paramContext.getFloat(k, this.elevation);
            this.applyElevation = true;
          }
        }
        else if (k == R.styleable.ConstraintSet_android_rotationX) {
          this.rotationX = paramContext.getFloat(k, this.rotationX);
        } else if (k == R.styleable.ConstraintSet_android_rotationY) {
          this.rotationY = paramContext.getFloat(k, this.rotationY);
        } else if (k == R.styleable.ConstraintSet_android_rotation) {
          this.rotation = paramContext.getFloat(k, this.rotation);
        } else if (k == R.styleable.ConstraintSet_android_scaleX) {
          this.scaleX = paramContext.getFloat(k, this.scaleX);
        } else if (k == R.styleable.ConstraintSet_android_scaleY) {
          this.scaleY = paramContext.getFloat(k, this.scaleY);
        } else if (k == R.styleable.ConstraintSet_android_transformPivotX) {
          this.transformPivotX = paramContext.getFloat(k, this.transformPivotX);
        } else if (k == R.styleable.ConstraintSet_android_transformPivotY) {
          this.transformPivotY = paramContext.getFloat(k, this.transformPivotY);
        } else if (k == R.styleable.ConstraintSet_android_translationX) {
          this.translationX = paramContext.getFloat(k, this.translationX);
        } else if (k == R.styleable.ConstraintSet_android_translationY) {
          this.translationY = paramContext.getFloat(k, this.translationY);
        } else if ((k == R.styleable.ConstraintSet_android_translationZ) && (Build.VERSION.SDK_INT >= 21)) {
          this.translationZ = paramContext.getFloat(k, this.translationZ);
        }
      }
      paramContext.recycle();
    }
    
    public LayoutParams(LayoutParams paramLayoutParams)
    {
      super();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/widget/Constraints.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */