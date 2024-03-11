package com.google.android.material.transition.platform;

import android.app.Activity;
import android.app.SharedElementCallback;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.transition.Transition;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.Window;
import androidx.core.graphics.BlendModeColorFilterCompat;
import androidx.core.graphics.BlendModeCompat;
import com.google.android.material.R.id;
import com.google.android.material.internal.ContextUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

public class MaterialContainerTransformSharedElementCallback
  extends SharedElementCallback
{
  private static WeakReference<View> capturedSharedElement;
  private boolean entering = true;
  private Rect returnEndBounds;
  private ShapeProvider shapeProvider = new ShapeableViewShapeProvider();
  private boolean sharedElementReenterTransitionEnabled = false;
  private boolean transparentWindowBackgroundEnabled = true;
  
  private static Drawable getWindowBackground(Window paramWindow)
  {
    return paramWindow.getDecorView().getBackground();
  }
  
  private static void removeWindowBackground(Window paramWindow)
  {
    paramWindow = getWindowBackground(paramWindow);
    if (paramWindow == null) {
      return;
    }
    paramWindow.mutate().setColorFilter(BlendModeColorFilterCompat.createBlendModeColorFilterCompat(0, BlendModeCompat.CLEAR));
  }
  
  private static void restoreWindowBackground(Window paramWindow)
  {
    paramWindow = getWindowBackground(paramWindow);
    if (paramWindow == null) {
      return;
    }
    paramWindow.mutate().clearColorFilter();
  }
  
  private void setUpEnterTransform(final Window paramWindow)
  {
    Object localObject = paramWindow.getSharedElementEnterTransition();
    if ((localObject instanceof MaterialContainerTransform))
    {
      localObject = (MaterialContainerTransform)localObject;
      if (!this.sharedElementReenterTransitionEnabled) {
        paramWindow.setSharedElementReenterTransition(null);
      }
      if (this.transparentWindowBackgroundEnabled)
      {
        updateBackgroundFadeDuration(paramWindow, (MaterialContainerTransform)localObject);
        ((MaterialContainerTransform)localObject).addListener(new TransitionListenerAdapter()
        {
          public void onTransitionEnd(Transition paramAnonymousTransition)
          {
            MaterialContainerTransformSharedElementCallback.restoreWindowBackground(paramWindow);
          }
          
          public void onTransitionStart(Transition paramAnonymousTransition)
          {
            MaterialContainerTransformSharedElementCallback.removeWindowBackground(paramWindow);
          }
        });
      }
    }
  }
  
  private void setUpReturnTransform(final Activity paramActivity, final Window paramWindow)
  {
    Object localObject = paramWindow.getSharedElementReturnTransition();
    if ((localObject instanceof MaterialContainerTransform))
    {
      localObject = (MaterialContainerTransform)localObject;
      ((MaterialContainerTransform)localObject).setHoldAtEndEnabled(true);
      ((MaterialContainerTransform)localObject).addListener(new TransitionListenerAdapter()
      {
        public void onTransitionEnd(Transition paramAnonymousTransition)
        {
          if (MaterialContainerTransformSharedElementCallback.capturedSharedElement != null)
          {
            paramAnonymousTransition = (View)MaterialContainerTransformSharedElementCallback.capturedSharedElement.get();
            if (paramAnonymousTransition != null)
            {
              paramAnonymousTransition.setAlpha(1.0F);
              MaterialContainerTransformSharedElementCallback.access$202(null);
            }
          }
          paramActivity.finish();
          paramActivity.overridePendingTransition(0, 0);
        }
      });
      if (this.transparentWindowBackgroundEnabled)
      {
        updateBackgroundFadeDuration(paramWindow, (MaterialContainerTransform)localObject);
        ((MaterialContainerTransform)localObject).addListener(new TransitionListenerAdapter()
        {
          public void onTransitionStart(Transition paramAnonymousTransition)
          {
            MaterialContainerTransformSharedElementCallback.removeWindowBackground(paramWindow);
          }
        });
      }
    }
  }
  
  private static void updateBackgroundFadeDuration(Window paramWindow, MaterialContainerTransform paramMaterialContainerTransform)
  {
    if (paramMaterialContainerTransform.getDuration() >= 0L) {
      paramWindow.setTransitionBackgroundFadeDuration(paramMaterialContainerTransform.getDuration());
    }
  }
  
  public ShapeProvider getShapeProvider()
  {
    return this.shapeProvider;
  }
  
  public boolean isSharedElementReenterTransitionEnabled()
  {
    return this.sharedElementReenterTransitionEnabled;
  }
  
  public boolean isTransparentWindowBackgroundEnabled()
  {
    return this.transparentWindowBackgroundEnabled;
  }
  
  public Parcelable onCaptureSharedElementSnapshot(View paramView, Matrix paramMatrix, RectF paramRectF)
  {
    capturedSharedElement = new WeakReference(paramView);
    return super.onCaptureSharedElementSnapshot(paramView, paramMatrix, paramRectF);
  }
  
  public View onCreateSnapshotView(Context paramContext, Parcelable paramParcelable)
  {
    paramContext = super.onCreateSnapshotView(paramContext, paramParcelable);
    if (paramContext != null)
    {
      paramParcelable = capturedSharedElement;
      if ((paramParcelable != null) && (this.shapeProvider != null))
      {
        paramParcelable = (View)paramParcelable.get();
        if (paramParcelable != null)
        {
          paramParcelable = this.shapeProvider.provideShape(paramParcelable);
          if (paramParcelable != null) {
            paramContext.setTag(R.id.mtrl_motion_snapshot_view, paramParcelable);
          }
        }
      }
    }
    return paramContext;
  }
  
  public void onMapSharedElements(List<String> paramList, Map<String, View> paramMap)
  {
    if ((!paramList.isEmpty()) && (!paramMap.isEmpty()))
    {
      paramList = (View)paramMap.get(paramList.get(0));
      if (paramList != null)
      {
        paramList = ContextUtils.getActivity(paramList.getContext());
        if (paramList != null)
        {
          paramMap = paramList.getWindow();
          if (this.entering) {
            setUpEnterTransform(paramMap);
          } else {
            setUpReturnTransform(paramList, paramMap);
          }
        }
      }
    }
  }
  
  public void onSharedElementEnd(List<String> paramList, List<View> paramList1, List<View> paramList2)
  {
    if ((!paramList1.isEmpty()) && ((((View)paramList1.get(0)).getTag(R.id.mtrl_motion_snapshot_view) instanceof View))) {
      ((View)paramList1.get(0)).setTag(R.id.mtrl_motion_snapshot_view, null);
    }
    if ((!this.entering) && (!paramList1.isEmpty())) {
      this.returnEndBounds = TransitionUtils.getRelativeBoundsRect((View)paramList1.get(0));
    }
    this.entering = false;
  }
  
  public void onSharedElementStart(List<String> paramList, List<View> paramList1, List<View> paramList2)
  {
    if ((!paramList1.isEmpty()) && (!paramList2.isEmpty())) {
      ((View)paramList1.get(0)).setTag(R.id.mtrl_motion_snapshot_view, paramList2.get(0));
    }
    if ((!this.entering) && (!paramList1.isEmpty()) && (this.returnEndBounds != null))
    {
      paramList = (View)paramList1.get(0);
      paramList.measure(View.MeasureSpec.makeMeasureSpec(this.returnEndBounds.width(), 1073741824), View.MeasureSpec.makeMeasureSpec(this.returnEndBounds.height(), 1073741824));
      paramList.layout(this.returnEndBounds.left, this.returnEndBounds.top, this.returnEndBounds.right, this.returnEndBounds.bottom);
    }
  }
  
  public void setShapeProvider(ShapeProvider paramShapeProvider)
  {
    this.shapeProvider = paramShapeProvider;
  }
  
  public void setSharedElementReenterTransitionEnabled(boolean paramBoolean)
  {
    this.sharedElementReenterTransitionEnabled = paramBoolean;
  }
  
  public void setTransparentWindowBackgroundEnabled(boolean paramBoolean)
  {
    this.transparentWindowBackgroundEnabled = paramBoolean;
  }
  
  public static abstract interface ShapeProvider
  {
    public abstract ShapeAppearanceModel provideShape(View paramView);
  }
  
  public static class ShapeableViewShapeProvider
    implements MaterialContainerTransformSharedElementCallback.ShapeProvider
  {
    public ShapeAppearanceModel provideShape(View paramView)
    {
      if ((paramView instanceof Shapeable)) {
        paramView = ((Shapeable)paramView).getShapeAppearanceModel();
      } else {
        paramView = null;
      }
      return paramView;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/transition/platform/MaterialContainerTransformSharedElementCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */