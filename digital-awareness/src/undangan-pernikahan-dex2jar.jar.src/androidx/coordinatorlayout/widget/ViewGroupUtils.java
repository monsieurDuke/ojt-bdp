package androidx.coordinatorlayout.widget;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

public class ViewGroupUtils
{
  private static final ThreadLocal<Matrix> sMatrix = new ThreadLocal();
  private static final ThreadLocal<RectF> sRectF = new ThreadLocal();
  
  public static void getDescendantRect(ViewGroup paramViewGroup, View paramView, Rect paramRect)
  {
    paramRect.set(0, 0, paramView.getWidth(), paramView.getHeight());
    offsetDescendantRect(paramViewGroup, paramView, paramRect);
  }
  
  private static void offsetDescendantMatrix(ViewParent paramViewParent, View paramView, Matrix paramMatrix)
  {
    Object localObject = paramView.getParent();
    if (((localObject instanceof View)) && (localObject != paramViewParent))
    {
      localObject = (View)localObject;
      offsetDescendantMatrix(paramViewParent, (View)localObject, paramMatrix);
      paramMatrix.preTranslate(-((View)localObject).getScrollX(), -((View)localObject).getScrollY());
    }
    paramMatrix.preTranslate(paramView.getLeft(), paramView.getTop());
    if (!paramView.getMatrix().isIdentity()) {
      paramMatrix.preConcat(paramView.getMatrix());
    }
  }
  
  static void offsetDescendantRect(ViewGroup paramViewGroup, View paramView, Rect paramRect)
  {
    ThreadLocal localThreadLocal = sMatrix;
    Matrix localMatrix = (Matrix)localThreadLocal.get();
    if (localMatrix == null)
    {
      localMatrix = new Matrix();
      localThreadLocal.set(localMatrix);
    }
    else
    {
      localMatrix.reset();
    }
    offsetDescendantMatrix(paramViewGroup, paramView, localMatrix);
    localThreadLocal = sRectF;
    paramView = (RectF)localThreadLocal.get();
    paramViewGroup = paramView;
    if (paramView == null)
    {
      paramViewGroup = new RectF();
      localThreadLocal.set(paramViewGroup);
    }
    paramViewGroup.set(paramRect);
    localMatrix.mapRect(paramViewGroup);
    paramRect.set((int)(paramViewGroup.left + 0.5F), (int)(paramViewGroup.top + 0.5F), (int)(paramViewGroup.right + 0.5F), (int)(paramViewGroup.bottom + 0.5F));
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/coordinatorlayout/widget/ViewGroupUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */