package androidx.appcompat.widget;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.os.Build.VERSION;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class TintContextWrapper
  extends ContextWrapper
{
  private static final Object CACHE_LOCK = new Object();
  private static ArrayList<WeakReference<TintContextWrapper>> sCache;
  private final Resources mResources;
  private final Resources.Theme mTheme;
  
  private TintContextWrapper(Context paramContext)
  {
    super(paramContext);
    if (VectorEnabledTintResources.shouldBeUsed())
    {
      Object localObject = new VectorEnabledTintResources(this, paramContext.getResources());
      this.mResources = ((Resources)localObject);
      localObject = ((Resources)localObject).newTheme();
      this.mTheme = ((Resources.Theme)localObject);
      ((Resources.Theme)localObject).setTo(paramContext.getTheme());
    }
    else
    {
      this.mResources = new TintResources(this, paramContext.getResources());
      this.mTheme = null;
    }
  }
  
  private static boolean shouldWrap(Context paramContext)
  {
    boolean bool2 = paramContext instanceof TintContextWrapper;
    boolean bool1 = false;
    if ((!bool2) && (!(paramContext.getResources() instanceof TintResources)) && (!(paramContext.getResources() instanceof VectorEnabledTintResources)))
    {
      if ((Build.VERSION.SDK_INT < 21) || (VectorEnabledTintResources.shouldBeUsed())) {
        bool1 = true;
      }
      return bool1;
    }
    return false;
  }
  
  public static Context wrap(Context paramContext)
  {
    if (shouldWrap(paramContext)) {
      synchronized (CACHE_LOCK)
      {
        Object localObject1 = sCache;
        if (localObject1 == null)
        {
          localObject1 = new java/util/ArrayList;
          ((ArrayList)localObject1).<init>();
          sCache = (ArrayList)localObject1;
        }
        else
        {
          for (int i = ((ArrayList)localObject1).size() - 1; i >= 0; i--)
          {
            localObject1 = (WeakReference)sCache.get(i);
            if ((localObject1 == null) || (((WeakReference)localObject1).get() == null)) {
              sCache.remove(i);
            }
          }
          for (i = sCache.size() - 1; i >= 0; i--)
          {
            localObject1 = (WeakReference)sCache.get(i);
            if (localObject1 != null) {
              localObject1 = (TintContextWrapper)((WeakReference)localObject1).get();
            } else {
              localObject1 = null;
            }
            if ((localObject1 != null) && (((TintContextWrapper)localObject1).getBaseContext() == paramContext)) {
              return (Context)localObject1;
            }
          }
        }
        localObject1 = new androidx/appcompat/widget/TintContextWrapper;
        ((TintContextWrapper)localObject1).<init>(paramContext);
        paramContext = sCache;
        WeakReference localWeakReference = new java/lang/ref/WeakReference;
        localWeakReference.<init>(localObject1);
        paramContext.add(localWeakReference);
        return (Context)localObject1;
      }
    }
    return paramContext;
  }
  
  public AssetManager getAssets()
  {
    return this.mResources.getAssets();
  }
  
  public Resources getResources()
  {
    return this.mResources;
  }
  
  public Resources.Theme getTheme()
  {
    Resources.Theme localTheme2 = this.mTheme;
    Resources.Theme localTheme1 = localTheme2;
    if (localTheme2 == null) {
      localTheme1 = super.getTheme();
    }
    return localTheme1;
  }
  
  public void setTheme(int paramInt)
  {
    Resources.Theme localTheme = this.mTheme;
    if (localTheme == null) {
      super.setTheme(paramInt);
    } else {
      localTheme.applyStyle(paramInt, true);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/appcompat/widget/TintContextWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */