package androidx.transition;

import android.graphics.Matrix;
import android.util.Log;
import android.view.View;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class ViewUtilsBase
{
  private static final String TAG = "ViewUtilsBase";
  private static final int VISIBILITY_MASK = 12;
  private static boolean sSetFrameFetched;
  private static Method sSetFrameMethod;
  private static Field sViewFlagsField;
  private static boolean sViewFlagsFieldFetched;
  private float[] mMatrixValues;
  
  private void fetchSetFrame()
  {
    if (!sSetFrameFetched)
    {
      try
      {
        Method localMethod = View.class.getDeclaredMethod("setFrame", new Class[] { Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE });
        sSetFrameMethod = localMethod;
        localMethod.setAccessible(true);
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        Log.i("ViewUtilsBase", "Failed to retrieve setFrame method", localNoSuchMethodException);
      }
      sSetFrameFetched = true;
    }
  }
  
  public void clearNonTransitionAlpha(View paramView)
  {
    if (paramView.getVisibility() == 0) {
      paramView.setTag(R.id.save_non_transition_alpha, null);
    }
  }
  
  public float getTransitionAlpha(View paramView)
  {
    Float localFloat = (Float)paramView.getTag(R.id.save_non_transition_alpha);
    if (localFloat != null) {
      return paramView.getAlpha() / localFloat.floatValue();
    }
    return paramView.getAlpha();
  }
  
  public void saveNonTransitionAlpha(View paramView)
  {
    if (paramView.getTag(R.id.save_non_transition_alpha) == null) {
      paramView.setTag(R.id.save_non_transition_alpha, Float.valueOf(paramView.getAlpha()));
    }
  }
  
  public void setAnimationMatrix(View paramView, Matrix paramMatrix)
  {
    if ((paramMatrix != null) && (!paramMatrix.isIdentity()))
    {
      float[] arrayOfFloat2 = this.mMatrixValues;
      float[] arrayOfFloat1 = arrayOfFloat2;
      if (arrayOfFloat2 == null)
      {
        arrayOfFloat2 = new float[9];
        arrayOfFloat1 = arrayOfFloat2;
        this.mMatrixValues = arrayOfFloat2;
      }
      paramMatrix.getValues(arrayOfFloat1);
      float f1 = arrayOfFloat1[3];
      float f2 = (float)Math.sqrt(1.0F - f1 * f1);
      int i;
      if (arrayOfFloat1[0] < 0.0F) {
        i = -1;
      } else {
        i = 1;
      }
      float f3 = f2 * i;
      f2 = (float)Math.toDegrees(Math.atan2(f1, f3));
      f1 = arrayOfFloat1[0] / f3;
      float f5 = arrayOfFloat1[4] / f3;
      float f4 = arrayOfFloat1[2];
      f3 = arrayOfFloat1[5];
      paramView.setPivotX(0.0F);
      paramView.setPivotY(0.0F);
      paramView.setTranslationX(f4);
      paramView.setTranslationY(f3);
      paramView.setRotation(f2);
      paramView.setScaleX(f1);
      paramView.setScaleY(f5);
    }
    else
    {
      paramView.setPivotX(paramView.getWidth() / 2);
      paramView.setPivotY(paramView.getHeight() / 2);
      paramView.setTranslationX(0.0F);
      paramView.setTranslationY(0.0F);
      paramView.setScaleX(1.0F);
      paramView.setScaleY(1.0F);
      paramView.setRotation(0.0F);
    }
  }
  
  public void setLeftTopRightBottom(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    fetchSetFrame();
    Method localMethod = sSetFrameMethod;
    if (localMethod != null) {
      try
      {
        localMethod.invoke(paramView, new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Integer.valueOf(paramInt3), Integer.valueOf(paramInt4) });
      }
      catch (InvocationTargetException paramView)
      {
        throw new RuntimeException(paramView.getCause());
      }
      catch (IllegalAccessException paramView) {}
    }
  }
  
  public void setTransitionAlpha(View paramView, float paramFloat)
  {
    Float localFloat = (Float)paramView.getTag(R.id.save_non_transition_alpha);
    if (localFloat != null) {
      paramView.setAlpha(localFloat.floatValue() * paramFloat);
    } else {
      paramView.setAlpha(paramFloat);
    }
  }
  
  public void setTransitionVisibility(View paramView, int paramInt)
  {
    if (!sViewFlagsFieldFetched)
    {
      try
      {
        Field localField1 = View.class.getDeclaredField("mViewFlags");
        sViewFlagsField = localField1;
        localField1.setAccessible(true);
      }
      catch (NoSuchFieldException localNoSuchFieldException)
      {
        Log.i("ViewUtilsBase", "fetchViewFlagsField: ");
      }
      sViewFlagsFieldFetched = true;
    }
    Field localField2 = sViewFlagsField;
    if (localField2 != null) {
      try
      {
        int i = localField2.getInt(paramView);
        sViewFlagsField.setInt(paramView, i & 0xFFFFFFF3 | paramInt);
      }
      catch (IllegalAccessException paramView) {}
    }
  }
  
  public void transformMatrixToGlobal(View paramView, Matrix paramMatrix)
  {
    Object localObject = paramView.getParent();
    if ((localObject instanceof View))
    {
      localObject = (View)localObject;
      transformMatrixToGlobal((View)localObject, paramMatrix);
      paramMatrix.preTranslate(-((View)localObject).getScrollX(), -((View)localObject).getScrollY());
    }
    paramMatrix.preTranslate(paramView.getLeft(), paramView.getTop());
    paramView = paramView.getMatrix();
    if (!paramView.isIdentity()) {
      paramMatrix.preConcat(paramView);
    }
  }
  
  public void transformMatrixToLocal(View paramView, Matrix paramMatrix)
  {
    Object localObject = paramView.getParent();
    if ((localObject instanceof View))
    {
      localObject = (View)localObject;
      transformMatrixToLocal((View)localObject, paramMatrix);
      paramMatrix.postTranslate(((View)localObject).getScrollX(), ((View)localObject).getScrollY());
    }
    paramMatrix.postTranslate(-paramView.getLeft(), -paramView.getTop());
    paramView = paramView.getMatrix();
    if (!paramView.isIdentity())
    {
      localObject = new Matrix();
      if (paramView.invert((Matrix)localObject)) {
        paramMatrix.postConcat((Matrix)localObject);
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/transition/ViewUtilsBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */