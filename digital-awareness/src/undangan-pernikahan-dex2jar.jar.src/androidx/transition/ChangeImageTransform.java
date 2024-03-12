package androidx.transition;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.Map;

public class ChangeImageTransform
  extends Transition
{
  private static final Property<ImageView, Matrix> ANIMATED_TRANSFORM_PROPERTY = new Property(Matrix.class, "animatedTransform")
  {
    public Matrix get(ImageView paramAnonymousImageView)
    {
      return null;
    }
    
    public void set(ImageView paramAnonymousImageView, Matrix paramAnonymousMatrix)
    {
      ImageViewUtils.animateTransform(paramAnonymousImageView, paramAnonymousMatrix);
    }
  };
  private static final TypeEvaluator<Matrix> NULL_MATRIX_EVALUATOR;
  private static final String PROPNAME_BOUNDS = "android:changeImageTransform:bounds";
  private static final String PROPNAME_MATRIX = "android:changeImageTransform:matrix";
  private static final String[] sTransitionProperties = { "android:changeImageTransform:matrix", "android:changeImageTransform:bounds" };
  
  static
  {
    NULL_MATRIX_EVALUATOR = new TypeEvaluator()
    {
      public Matrix evaluate(float paramAnonymousFloat, Matrix paramAnonymousMatrix1, Matrix paramAnonymousMatrix2)
      {
        return null;
      }
    };
  }
  
  public ChangeImageTransform() {}
  
  public ChangeImageTransform(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  private void captureValues(TransitionValues paramTransitionValues)
  {
    View localView = paramTransitionValues.view;
    if (((localView instanceof ImageView)) && (localView.getVisibility() == 0))
    {
      ImageView localImageView = (ImageView)localView;
      if (localImageView.getDrawable() == null) {
        return;
      }
      paramTransitionValues = paramTransitionValues.values;
      paramTransitionValues.put("android:changeImageTransform:bounds", new Rect(localView.getLeft(), localView.getTop(), localView.getRight(), localView.getBottom()));
      paramTransitionValues.put("android:changeImageTransform:matrix", copyImageMatrix(localImageView));
      return;
    }
  }
  
  private static Matrix centerCropMatrix(ImageView paramImageView)
  {
    Drawable localDrawable = paramImageView.getDrawable();
    int m = localDrawable.getIntrinsicWidth();
    int j = paramImageView.getWidth();
    float f1 = j / m;
    int k = localDrawable.getIntrinsicHeight();
    int i = paramImageView.getHeight();
    float f2 = Math.max(f1, i / k);
    float f3 = m;
    f1 = k;
    j = Math.round((j - f3 * f2) / 2.0F);
    i = Math.round((i - f1 * f2) / 2.0F);
    paramImageView = new Matrix();
    paramImageView.postScale(f2, f2);
    paramImageView.postTranslate(j, i);
    return paramImageView;
  }
  
  private static Matrix copyImageMatrix(ImageView paramImageView)
  {
    Drawable localDrawable = paramImageView.getDrawable();
    if ((localDrawable.getIntrinsicWidth() > 0) && (localDrawable.getIntrinsicHeight() > 0)) {
      switch (3.$SwitchMap$android$widget$ImageView$ScaleType[paramImageView.getScaleType().ordinal()])
      {
      default: 
        break;
      case 2: 
        return centerCropMatrix(paramImageView);
      case 1: 
        return fitXYMatrix(paramImageView);
      }
    }
    return new Matrix(paramImageView.getImageMatrix());
  }
  
  private ObjectAnimator createMatrixAnimator(ImageView paramImageView, Matrix paramMatrix1, Matrix paramMatrix2)
  {
    return ObjectAnimator.ofObject(paramImageView, ANIMATED_TRANSFORM_PROPERTY, new TransitionUtils.MatrixEvaluator(), new Matrix[] { paramMatrix1, paramMatrix2 });
  }
  
  private ObjectAnimator createNullAnimator(ImageView paramImageView)
  {
    return ObjectAnimator.ofObject(paramImageView, ANIMATED_TRANSFORM_PROPERTY, NULL_MATRIX_EVALUATOR, new Matrix[] { MatrixUtils.IDENTITY_MATRIX, MatrixUtils.IDENTITY_MATRIX });
  }
  
  private static Matrix fitXYMatrix(ImageView paramImageView)
  {
    Drawable localDrawable = paramImageView.getDrawable();
    Matrix localMatrix = new Matrix();
    localMatrix.postScale(paramImageView.getWidth() / localDrawable.getIntrinsicWidth(), paramImageView.getHeight() / localDrawable.getIntrinsicHeight());
    return localMatrix;
  }
  
  public void captureEndValues(TransitionValues paramTransitionValues)
  {
    captureValues(paramTransitionValues);
  }
  
  public void captureStartValues(TransitionValues paramTransitionValues)
  {
    captureValues(paramTransitionValues);
  }
  
  public Animator createAnimator(ViewGroup paramViewGroup, TransitionValues paramTransitionValues1, TransitionValues paramTransitionValues2)
  {
    if ((paramTransitionValues1 != null) && (paramTransitionValues2 != null))
    {
      paramViewGroup = (Rect)paramTransitionValues1.values.get("android:changeImageTransform:bounds");
      Rect localRect = (Rect)paramTransitionValues2.values.get("android:changeImageTransform:bounds");
      if ((paramViewGroup != null) && (localRect != null))
      {
        paramTransitionValues1 = (Matrix)paramTransitionValues1.values.get("android:changeImageTransform:matrix");
        Matrix localMatrix = (Matrix)paramTransitionValues2.values.get("android:changeImageTransform:matrix");
        if (((paramTransitionValues1 == null) && (localMatrix == null)) || ((paramTransitionValues1 != null) && (paramTransitionValues1.equals(localMatrix)))) {
          i = 1;
        } else {
          i = 0;
        }
        if ((paramViewGroup.equals(localRect)) && (i != 0)) {
          return null;
        }
        paramTransitionValues2 = (ImageView)paramTransitionValues2.view;
        paramViewGroup = paramTransitionValues2.getDrawable();
        int j = paramViewGroup.getIntrinsicWidth();
        int i = paramViewGroup.getIntrinsicHeight();
        if ((j > 0) && (i > 0))
        {
          paramViewGroup = paramTransitionValues1;
          if (paramTransitionValues1 == null) {
            paramViewGroup = MatrixUtils.IDENTITY_MATRIX;
          }
          paramTransitionValues1 = localMatrix;
          if (localMatrix == null) {
            paramTransitionValues1 = MatrixUtils.IDENTITY_MATRIX;
          }
          ANIMATED_TRANSFORM_PROPERTY.set(paramTransitionValues2, paramViewGroup);
          paramViewGroup = createMatrixAnimator(paramTransitionValues2, paramViewGroup, paramTransitionValues1);
        }
        else
        {
          paramViewGroup = createNullAnimator(paramTransitionValues2);
        }
        return paramViewGroup;
      }
      return null;
    }
    return null;
  }
  
  public String[] getTransitionProperties()
  {
    return sTransitionProperties;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/transition/ChangeImageTransform.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */