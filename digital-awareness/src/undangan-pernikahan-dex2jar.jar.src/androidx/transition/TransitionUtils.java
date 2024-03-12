package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.TypeEvaluator;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Picture;
import android.graphics.RectF;
import android.os.Build.VERSION;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

class TransitionUtils
{
  private static final boolean HAS_IS_ATTACHED_TO_WINDOW;
  private static final boolean HAS_OVERLAY;
  private static final boolean HAS_PICTURE_BITMAP;
  private static final int MAX_IMAGE_SIZE = 1048576;
  
  static
  {
    int i = Build.VERSION.SDK_INT;
    boolean bool2 = true;
    boolean bool1;
    if (i >= 19) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    HAS_IS_ATTACHED_TO_WINDOW = bool1;
    if (Build.VERSION.SDK_INT >= 18) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    HAS_OVERLAY = bool1;
    if (Build.VERSION.SDK_INT >= 28) {
      bool1 = bool2;
    } else {
      bool1 = false;
    }
    HAS_PICTURE_BITMAP = bool1;
  }
  
  static View copyViewImage(ViewGroup paramViewGroup, View paramView1, View paramView2)
  {
    Matrix localMatrix = new Matrix();
    localMatrix.setTranslate(-paramView2.getScrollX(), -paramView2.getScrollY());
    ViewUtils.transformMatrixToGlobal(paramView1, localMatrix);
    ViewUtils.transformMatrixToLocal(paramViewGroup, localMatrix);
    RectF localRectF = new RectF(0.0F, 0.0F, paramView1.getWidth(), paramView1.getHeight());
    localMatrix.mapRect(localRectF);
    int i = Math.round(localRectF.left);
    int k = Math.round(localRectF.top);
    int j = Math.round(localRectF.right);
    int m = Math.round(localRectF.bottom);
    paramView2 = new ImageView(paramView1.getContext());
    paramView2.setScaleType(ImageView.ScaleType.CENTER_CROP);
    paramViewGroup = createViewBitmap(paramView1, localMatrix, localRectF, paramViewGroup);
    if (paramViewGroup != null) {
      paramView2.setImageBitmap(paramViewGroup);
    }
    paramView2.measure(View.MeasureSpec.makeMeasureSpec(j - i, 1073741824), View.MeasureSpec.makeMeasureSpec(m - k, 1073741824));
    paramView2.layout(i, k, j, m);
    return paramView2;
  }
  
  private static Bitmap createViewBitmap(View paramView, Matrix paramMatrix, RectF paramRectF, ViewGroup paramViewGroup)
  {
    boolean bool1;
    boolean bool2;
    if (HAS_IS_ATTACHED_TO_WINDOW)
    {
      bool1 = paramView.isAttachedToWindow() ^ true;
      if (paramViewGroup == null) {
        bool2 = false;
      } else {
        bool2 = paramViewGroup.isAttachedToWindow();
      }
    }
    else
    {
      bool1 = false;
      bool2 = false;
    }
    Object localObject1 = null;
    int j = 0;
    boolean bool3 = HAS_OVERLAY;
    Object localObject2 = localObject1;
    int i = j;
    if (bool3)
    {
      localObject2 = localObject1;
      i = j;
      if (bool1)
      {
        if (!bool2) {
          return null;
        }
        localObject2 = (ViewGroup)paramView.getParent();
        i = ((ViewGroup)localObject2).indexOfChild(paramView);
        paramViewGroup.getOverlay().add(paramView);
      }
    }
    Object localObject3 = null;
    j = Math.round(paramRectF.width());
    int k = Math.round(paramRectF.height());
    localObject1 = localObject3;
    if (j > 0)
    {
      localObject1 = localObject3;
      if (k > 0)
      {
        float f = Math.min(1.0F, 1048576.0F / (j * k));
        j = Math.round(j * f);
        k = Math.round(k * f);
        paramMatrix.postTranslate(-paramRectF.left, -paramRectF.top);
        paramMatrix.postScale(f, f);
        if (HAS_PICTURE_BITMAP)
        {
          paramRectF = new Picture();
          localObject1 = paramRectF.beginRecording(j, k);
          ((Canvas)localObject1).concat(paramMatrix);
          paramView.draw((Canvas)localObject1);
          paramRectF.endRecording();
          localObject1 = Bitmap.createBitmap(paramRectF);
        }
        else
        {
          localObject1 = Bitmap.createBitmap(j, k, Bitmap.Config.ARGB_8888);
          paramRectF = new Canvas((Bitmap)localObject1);
          paramRectF.concat(paramMatrix);
          paramView.draw(paramRectF);
        }
      }
    }
    if ((bool3) && (bool1))
    {
      paramViewGroup.getOverlay().remove(paramView);
      ((ViewGroup)localObject2).addView(paramView, i);
    }
    return (Bitmap)localObject1;
  }
  
  static Animator mergeAnimators(Animator paramAnimator1, Animator paramAnimator2)
  {
    if (paramAnimator1 == null) {
      return paramAnimator2;
    }
    if (paramAnimator2 == null) {
      return paramAnimator1;
    }
    AnimatorSet localAnimatorSet = new AnimatorSet();
    localAnimatorSet.playTogether(new Animator[] { paramAnimator1, paramAnimator2 });
    return localAnimatorSet;
  }
  
  static class MatrixEvaluator
    implements TypeEvaluator<Matrix>
  {
    final float[] mTempEndValues = new float[9];
    final Matrix mTempMatrix = new Matrix();
    final float[] mTempStartValues = new float[9];
    
    public Matrix evaluate(float paramFloat, Matrix paramMatrix1, Matrix paramMatrix2)
    {
      paramMatrix1.getValues(this.mTempStartValues);
      paramMatrix2.getValues(this.mTempEndValues);
      for (int i = 0; i < 9; i++)
      {
        paramMatrix1 = this.mTempEndValues;
        float f2 = paramMatrix1[i];
        float f1 = this.mTempStartValues[i];
        paramMatrix1[i] = (f1 + paramFloat * (f2 - f1));
      }
      this.mTempMatrix.setValues(this.mTempEndValues);
      return this.mTempMatrix;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/transition/TransitionUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */