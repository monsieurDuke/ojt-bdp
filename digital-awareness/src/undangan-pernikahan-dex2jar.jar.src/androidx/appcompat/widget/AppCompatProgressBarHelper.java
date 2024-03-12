package androidx.appcompat.widget;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import androidx.core.graphics.drawable.WrappedDrawable;

class AppCompatProgressBarHelper
{
  private static final int[] TINT_ATTRS = { 16843067, 16843068 };
  private Bitmap mSampleTile;
  private final ProgressBar mView;
  
  AppCompatProgressBarHelper(ProgressBar paramProgressBar)
  {
    this.mView = paramProgressBar;
  }
  
  private Shape getDrawableShape()
  {
    return new RoundRectShape(new float[] { 5.0F, 5.0F, 5.0F, 5.0F, 5.0F, 5.0F, 5.0F, 5.0F }, null, null);
  }
  
  private Drawable tileifyIndeterminate(Drawable paramDrawable)
  {
    Object localObject = paramDrawable;
    if ((paramDrawable instanceof AnimationDrawable))
    {
      paramDrawable = (AnimationDrawable)paramDrawable;
      int j = paramDrawable.getNumberOfFrames();
      localObject = new AnimationDrawable();
      ((AnimationDrawable)localObject).setOneShot(paramDrawable.isOneShot());
      for (int i = 0; i < j; i++)
      {
        Drawable localDrawable = tileify(paramDrawable.getFrame(i), true);
        localDrawable.setLevel(10000);
        ((AnimationDrawable)localObject).addFrame(localDrawable, paramDrawable.getDuration(i));
      }
      ((AnimationDrawable)localObject).setLevel(10000);
    }
    return (Drawable)localObject;
  }
  
  Bitmap getSampleTile()
  {
    return this.mSampleTile;
  }
  
  void loadFromAttributes(AttributeSet paramAttributeSet, int paramInt)
  {
    paramAttributeSet = TintTypedArray.obtainStyledAttributes(this.mView.getContext(), paramAttributeSet, TINT_ATTRS, paramInt, 0);
    Drawable localDrawable = paramAttributeSet.getDrawableIfKnown(0);
    if (localDrawable != null) {
      this.mView.setIndeterminateDrawable(tileifyIndeterminate(localDrawable));
    }
    localDrawable = paramAttributeSet.getDrawableIfKnown(1);
    if (localDrawable != null) {
      this.mView.setProgressDrawable(tileify(localDrawable, false));
    }
    paramAttributeSet.recycle();
  }
  
  Drawable tileify(Drawable paramDrawable, boolean paramBoolean)
  {
    Object localObject1;
    if ((paramDrawable instanceof WrappedDrawable))
    {
      localObject1 = ((WrappedDrawable)paramDrawable).getWrappedDrawable();
      if (localObject1 != null)
      {
        localObject1 = tileify((Drawable)localObject1, paramBoolean);
        ((WrappedDrawable)paramDrawable).setWrappedDrawable((Drawable)localObject1);
      }
    }
    else
    {
      Object localObject2;
      if ((paramDrawable instanceof LayerDrawable))
      {
        paramDrawable = (LayerDrawable)paramDrawable;
        int j = paramDrawable.getNumberOfLayers();
        localObject1 = new Drawable[j];
        for (int i = 0; i < j; i++)
        {
          int k = paramDrawable.getId(i);
          localObject2 = paramDrawable.getDrawable(i);
          if ((k != 16908301) && (k != 16908303)) {
            paramBoolean = false;
          } else {
            paramBoolean = true;
          }
          localObject1[i] = tileify((Drawable)localObject2, paramBoolean);
        }
        localObject1 = new LayerDrawable((Drawable[])localObject1);
        for (i = 0; i < j; i++)
        {
          ((LayerDrawable)localObject1).setId(i, paramDrawable.getId(i));
          if (Build.VERSION.SDK_INT >= 23) {
            Api23Impl.transferLayerProperties(paramDrawable, (LayerDrawable)localObject1, i);
          }
        }
        return (Drawable)localObject1;
      }
      if ((paramDrawable instanceof BitmapDrawable))
      {
        localObject1 = (BitmapDrawable)paramDrawable;
        localObject2 = ((BitmapDrawable)localObject1).getBitmap();
        if (this.mSampleTile == null) {
          this.mSampleTile = ((Bitmap)localObject2);
        }
        paramDrawable = new ShapeDrawable(getDrawableShape());
        localObject2 = new BitmapShader((Bitmap)localObject2, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
        paramDrawable.getPaint().setShader((Shader)localObject2);
        paramDrawable.getPaint().setColorFilter(((BitmapDrawable)localObject1).getPaint().getColorFilter());
        if (paramBoolean) {
          paramDrawable = new ClipDrawable(paramDrawable, 3, 1);
        }
        return paramDrawable;
      }
    }
    return paramDrawable;
  }
  
  private static class Api23Impl
  {
    public static void transferLayerProperties(LayerDrawable paramLayerDrawable1, LayerDrawable paramLayerDrawable2, int paramInt)
    {
      paramLayerDrawable2.setLayerGravity(paramInt, paramLayerDrawable1.getLayerGravity(paramInt));
      paramLayerDrawable2.setLayerWidth(paramInt, paramLayerDrawable1.getLayerWidth(paramInt));
      paramLayerDrawable2.setLayerHeight(paramInt, paramLayerDrawable1.getLayerHeight(paramInt));
      paramLayerDrawable2.setLayerInsetLeft(paramInt, paramLayerDrawable1.getLayerInsetLeft(paramInt));
      paramLayerDrawable2.setLayerInsetRight(paramInt, paramLayerDrawable1.getLayerInsetRight(paramInt));
      paramLayerDrawable2.setLayerInsetTop(paramInt, paramLayerDrawable1.getLayerInsetTop(paramInt));
      paramLayerDrawable2.setLayerInsetBottom(paramInt, paramLayerDrawable1.getLayerInsetBottom(paramInt));
      paramLayerDrawable2.setLayerInsetStart(paramInt, paramLayerDrawable1.getLayerInsetStart(paramInt));
      paramLayerDrawable2.setLayerInsetEnd(paramInt, paramLayerDrawable1.getLayerInsetEnd(paramInt));
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/appcompat/widget/AppCompatProgressBarHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */