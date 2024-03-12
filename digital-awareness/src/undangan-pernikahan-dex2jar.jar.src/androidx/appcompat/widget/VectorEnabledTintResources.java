package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import java.lang.ref.WeakReference;

public class VectorEnabledTintResources
  extends ResourcesWrapper
{
  public static final int MAX_SDK_WHERE_REQUIRED = 20;
  private static boolean sCompatVectorFromResourcesEnabled = false;
  private final WeakReference<Context> mContextRef;
  
  public VectorEnabledTintResources(Context paramContext, Resources paramResources)
  {
    super(paramResources);
    this.mContextRef = new WeakReference(paramContext);
  }
  
  public static boolean isCompatVectorFromResourcesEnabled()
  {
    return sCompatVectorFromResourcesEnabled;
  }
  
  public static void setCompatVectorFromResourcesEnabled(boolean paramBoolean)
  {
    sCompatVectorFromResourcesEnabled = paramBoolean;
  }
  
  public static boolean shouldBeUsed()
  {
    boolean bool;
    if ((isCompatVectorFromResourcesEnabled()) && (Build.VERSION.SDK_INT <= 20)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public Drawable getDrawable(int paramInt)
    throws Resources.NotFoundException
  {
    Context localContext = (Context)this.mContextRef.get();
    if (localContext != null) {
      return ResourceManagerInternal.get().onDrawableLoadedFromResources(localContext, this, paramInt);
    }
    return getDrawableCanonical(paramInt);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/appcompat/widget/VectorEnabledTintResources.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */