package androidx.appcompat.view;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.os.Build.VERSION;
import android.view.LayoutInflater;
import androidx.appcompat.R.style;

public class ContextThemeWrapper
  extends ContextWrapper
{
  private static Configuration sEmptyConfig;
  private LayoutInflater mInflater;
  private Configuration mOverrideConfiguration;
  private Resources mResources;
  private Resources.Theme mTheme;
  private int mThemeResource;
  
  public ContextThemeWrapper()
  {
    super(null);
  }
  
  public ContextThemeWrapper(Context paramContext, int paramInt)
  {
    super(paramContext);
    this.mThemeResource = paramInt;
  }
  
  public ContextThemeWrapper(Context paramContext, Resources.Theme paramTheme)
  {
    super(paramContext);
    this.mTheme = paramTheme;
  }
  
  private Resources getResourcesInternal()
  {
    if (this.mResources == null) {
      if ((this.mOverrideConfiguration != null) && ((Build.VERSION.SDK_INT < 26) || (!isEmptyConfiguration(this.mOverrideConfiguration))))
      {
        if (Build.VERSION.SDK_INT >= 17)
        {
          this.mResources = Api17Impl.createConfigurationContext(this, this.mOverrideConfiguration).getResources();
        }
        else
        {
          Resources localResources = super.getResources();
          Configuration localConfiguration = new Configuration(localResources.getConfiguration());
          localConfiguration.updateFrom(this.mOverrideConfiguration);
          this.mResources = new Resources(localResources.getAssets(), localResources.getDisplayMetrics(), localConfiguration);
        }
      }
      else {
        this.mResources = super.getResources();
      }
    }
    return this.mResources;
  }
  
  private void initializeTheme()
  {
    boolean bool;
    if (this.mTheme == null) {
      bool = true;
    } else {
      bool = false;
    }
    if (bool)
    {
      this.mTheme = getResources().newTheme();
      Resources.Theme localTheme = getBaseContext().getTheme();
      if (localTheme != null) {
        this.mTheme.setTo(localTheme);
      }
    }
    onApplyThemeResource(this.mTheme, this.mThemeResource, bool);
  }
  
  private static boolean isEmptyConfiguration(Configuration paramConfiguration)
  {
    if (paramConfiguration == null) {
      return true;
    }
    if (sEmptyConfig == null)
    {
      Configuration localConfiguration = new Configuration();
      localConfiguration.fontScale = 0.0F;
      sEmptyConfig = localConfiguration;
    }
    return paramConfiguration.equals(sEmptyConfig);
  }
  
  public void applyOverrideConfiguration(Configuration paramConfiguration)
  {
    if (this.mResources == null)
    {
      if (this.mOverrideConfiguration == null)
      {
        this.mOverrideConfiguration = new Configuration(paramConfiguration);
        return;
      }
      throw new IllegalStateException("Override configuration has already been set");
    }
    throw new IllegalStateException("getResources() or getAssets() has already been called");
  }
  
  protected void attachBaseContext(Context paramContext)
  {
    super.attachBaseContext(paramContext);
  }
  
  public AssetManager getAssets()
  {
    return getResources().getAssets();
  }
  
  public Resources getResources()
  {
    return getResourcesInternal();
  }
  
  public Object getSystemService(String paramString)
  {
    if ("layout_inflater".equals(paramString))
    {
      if (this.mInflater == null) {
        this.mInflater = LayoutInflater.from(getBaseContext()).cloneInContext(this);
      }
      return this.mInflater;
    }
    return getBaseContext().getSystemService(paramString);
  }
  
  public Resources.Theme getTheme()
  {
    Resources.Theme localTheme = this.mTheme;
    if (localTheme != null) {
      return localTheme;
    }
    if (this.mThemeResource == 0) {
      this.mThemeResource = R.style.Theme_AppCompat_Light;
    }
    initializeTheme();
    return this.mTheme;
  }
  
  public int getThemeResId()
  {
    return this.mThemeResource;
  }
  
  protected void onApplyThemeResource(Resources.Theme paramTheme, int paramInt, boolean paramBoolean)
  {
    paramTheme.applyStyle(paramInt, true);
  }
  
  public void setTheme(int paramInt)
  {
    if (this.mThemeResource != paramInt)
    {
      this.mThemeResource = paramInt;
      initializeTheme();
    }
  }
  
  static class Api17Impl
  {
    static Context createConfigurationContext(ContextThemeWrapper paramContextThemeWrapper, Configuration paramConfiguration)
    {
      return paramContextThemeWrapper.createConfigurationContext(paramConfiguration);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/appcompat/view/ContextThemeWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */