package androidx.core.view;

import android.graphics.Rect;
import android.os.Build.VERSION;
import android.view.DisplayCutout;
import androidx.core.util.ObjectsCompat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class DisplayCutoutCompat
{
  private final DisplayCutout mDisplayCutout;
  
  public DisplayCutoutCompat(Rect paramRect, List<Rect> paramList)
  {
    this(paramRect);
  }
  
  private DisplayCutoutCompat(DisplayCutout paramDisplayCutout)
  {
    this.mDisplayCutout = paramDisplayCutout;
  }
  
  public DisplayCutoutCompat(androidx.core.graphics.Insets paramInsets1, Rect paramRect1, Rect paramRect2, Rect paramRect3, Rect paramRect4, androidx.core.graphics.Insets paramInsets2)
  {
    this(constructDisplayCutout(paramInsets1, paramRect1, paramRect2, paramRect3, paramRect4, paramInsets2));
  }
  
  private static DisplayCutout constructDisplayCutout(androidx.core.graphics.Insets paramInsets1, Rect paramRect1, Rect paramRect2, Rect paramRect3, Rect paramRect4, androidx.core.graphics.Insets paramInsets2)
  {
    if (Build.VERSION.SDK_INT >= 30) {
      return Api30Impl.createDisplayCutout(paramInsets1.toPlatformInsets(), paramRect1, paramRect2, paramRect3, paramRect4, paramInsets2.toPlatformInsets());
    }
    if (Build.VERSION.SDK_INT >= 29) {
      return Api29Impl.createDisplayCutout(paramInsets1.toPlatformInsets(), paramRect1, paramRect2, paramRect3, paramRect4);
    }
    if (Build.VERSION.SDK_INT >= 28)
    {
      paramInsets1 = new Rect(paramInsets1.left, paramInsets1.top, paramInsets1.right, paramInsets1.bottom);
      paramInsets2 = new ArrayList();
      if (paramRect1 != null) {
        paramInsets2.add(paramRect1);
      }
      if (paramRect2 != null) {
        paramInsets2.add(paramRect2);
      }
      if (paramRect3 != null) {
        paramInsets2.add(paramRect3);
      }
      if (paramRect4 != null) {
        paramInsets2.add(paramRect4);
      }
      return Api28Impl.createDisplayCutout(paramInsets1, paramInsets2);
    }
    return null;
  }
  
  static DisplayCutoutCompat wrap(DisplayCutout paramDisplayCutout)
  {
    if (paramDisplayCutout == null) {
      paramDisplayCutout = null;
    } else {
      paramDisplayCutout = new DisplayCutoutCompat(paramDisplayCutout);
    }
    return paramDisplayCutout;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if ((paramObject != null) && (getClass() == paramObject.getClass()))
    {
      paramObject = (DisplayCutoutCompat)paramObject;
      return ObjectsCompat.equals(this.mDisplayCutout, ((DisplayCutoutCompat)paramObject).mDisplayCutout);
    }
    return false;
  }
  
  public List<Rect> getBoundingRects()
  {
    if (Build.VERSION.SDK_INT >= 28) {
      return Api28Impl.getBoundingRects(this.mDisplayCutout);
    }
    return Collections.emptyList();
  }
  
  public int getSafeInsetBottom()
  {
    if (Build.VERSION.SDK_INT >= 28) {
      return Api28Impl.getSafeInsetBottom(this.mDisplayCutout);
    }
    return 0;
  }
  
  public int getSafeInsetLeft()
  {
    if (Build.VERSION.SDK_INT >= 28) {
      return Api28Impl.getSafeInsetLeft(this.mDisplayCutout);
    }
    return 0;
  }
  
  public int getSafeInsetRight()
  {
    if (Build.VERSION.SDK_INT >= 28) {
      return Api28Impl.getSafeInsetRight(this.mDisplayCutout);
    }
    return 0;
  }
  
  public int getSafeInsetTop()
  {
    if (Build.VERSION.SDK_INT >= 28) {
      return Api28Impl.getSafeInsetTop(this.mDisplayCutout);
    }
    return 0;
  }
  
  public androidx.core.graphics.Insets getWaterfallInsets()
  {
    if (Build.VERSION.SDK_INT >= 30) {
      return androidx.core.graphics.Insets.toCompatInsets(Api30Impl.getWaterfallInsets(this.mDisplayCutout));
    }
    return androidx.core.graphics.Insets.NONE;
  }
  
  public int hashCode()
  {
    DisplayCutout localDisplayCutout = this.mDisplayCutout;
    int i;
    if (localDisplayCutout == null) {
      i = 0;
    } else {
      i = localDisplayCutout.hashCode();
    }
    return i;
  }
  
  public String toString()
  {
    return "DisplayCutoutCompat{" + this.mDisplayCutout + "}";
  }
  
  DisplayCutout unwrap()
  {
    return this.mDisplayCutout;
  }
  
  static class Api28Impl
  {
    static DisplayCutout createDisplayCutout(Rect paramRect, List<Rect> paramList)
    {
      return new DisplayCutout(paramRect, paramList);
    }
    
    static List<Rect> getBoundingRects(DisplayCutout paramDisplayCutout)
    {
      return paramDisplayCutout.getBoundingRects();
    }
    
    static int getSafeInsetBottom(DisplayCutout paramDisplayCutout)
    {
      return paramDisplayCutout.getSafeInsetBottom();
    }
    
    static int getSafeInsetLeft(DisplayCutout paramDisplayCutout)
    {
      return paramDisplayCutout.getSafeInsetLeft();
    }
    
    static int getSafeInsetRight(DisplayCutout paramDisplayCutout)
    {
      return paramDisplayCutout.getSafeInsetRight();
    }
    
    static int getSafeInsetTop(DisplayCutout paramDisplayCutout)
    {
      return paramDisplayCutout.getSafeInsetTop();
    }
  }
  
  static class Api29Impl
  {
    static DisplayCutout createDisplayCutout(android.graphics.Insets paramInsets, Rect paramRect1, Rect paramRect2, Rect paramRect3, Rect paramRect4)
    {
      return new DisplayCutout(paramInsets, paramRect1, paramRect2, paramRect3, paramRect4);
    }
  }
  
  static class Api30Impl
  {
    static DisplayCutout createDisplayCutout(android.graphics.Insets paramInsets1, Rect paramRect1, Rect paramRect2, Rect paramRect3, Rect paramRect4, android.graphics.Insets paramInsets2)
    {
      return new DisplayCutout(paramInsets1, paramRect1, paramRect2, paramRect3, paramRect4, paramInsets2);
    }
    
    static android.graphics.Insets getWaterfallInsets(DisplayCutout paramDisplayCutout)
    {
      return paramDisplayCutout.getWaterfallInsets();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/view/DisplayCutoutCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */