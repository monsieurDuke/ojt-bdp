package androidx.core.app;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.View;

public class ActivityOptionsCompat
{
  public static final String EXTRA_USAGE_TIME_REPORT = "android.activity.usage_time";
  public static final String EXTRA_USAGE_TIME_REPORT_PACKAGES = "android.usage_time_packages";
  
  public static ActivityOptionsCompat makeBasic()
  {
    if (Build.VERSION.SDK_INT >= 23) {
      return new ActivityOptionsCompatImpl(Api23Impl.makeBasic());
    }
    return new ActivityOptionsCompat();
  }
  
  public static ActivityOptionsCompat makeClipRevealAnimation(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (Build.VERSION.SDK_INT >= 23) {
      return new ActivityOptionsCompatImpl(Api23Impl.makeClipRevealAnimation(paramView, paramInt1, paramInt2, paramInt3, paramInt4));
    }
    return new ActivityOptionsCompat();
  }
  
  public static ActivityOptionsCompat makeCustomAnimation(Context paramContext, int paramInt1, int paramInt2)
  {
    if (Build.VERSION.SDK_INT >= 16) {
      return new ActivityOptionsCompatImpl(Api16Impl.makeCustomAnimation(paramContext, paramInt1, paramInt2));
    }
    return new ActivityOptionsCompat();
  }
  
  public static ActivityOptionsCompat makeScaleUpAnimation(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (Build.VERSION.SDK_INT >= 16) {
      return new ActivityOptionsCompatImpl(Api16Impl.makeScaleUpAnimation(paramView, paramInt1, paramInt2, paramInt3, paramInt4));
    }
    return new ActivityOptionsCompat();
  }
  
  public static ActivityOptionsCompat makeSceneTransitionAnimation(Activity paramActivity, View paramView, String paramString)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return new ActivityOptionsCompatImpl(Api21Impl.makeSceneTransitionAnimation(paramActivity, paramView, paramString));
    }
    return new ActivityOptionsCompat();
  }
  
  public static ActivityOptionsCompat makeSceneTransitionAnimation(Activity paramActivity, androidx.core.util.Pair<View, String>... paramVarArgs)
  {
    if (Build.VERSION.SDK_INT >= 21)
    {
      Object localObject = null;
      if (paramVarArgs != null)
      {
        android.util.Pair[] arrayOfPair = new android.util.Pair[paramVarArgs.length];
        for (int i = 0;; i++)
        {
          localObject = arrayOfPair;
          if (i >= paramVarArgs.length) {
            break;
          }
          arrayOfPair[i] = android.util.Pair.create((View)paramVarArgs[i].first, (String)paramVarArgs[i].second);
        }
      }
      return new ActivityOptionsCompatImpl(Api21Impl.makeSceneTransitionAnimation(paramActivity, (android.util.Pair[])localObject));
    }
    return new ActivityOptionsCompat();
  }
  
  public static ActivityOptionsCompat makeTaskLaunchBehind()
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return new ActivityOptionsCompatImpl(Api21Impl.makeTaskLaunchBehind());
    }
    return new ActivityOptionsCompat();
  }
  
  public static ActivityOptionsCompat makeThumbnailScaleUpAnimation(View paramView, Bitmap paramBitmap, int paramInt1, int paramInt2)
  {
    if (Build.VERSION.SDK_INT >= 16) {
      return new ActivityOptionsCompatImpl(Api16Impl.makeThumbnailScaleUpAnimation(paramView, paramBitmap, paramInt1, paramInt2));
    }
    return new ActivityOptionsCompat();
  }
  
  public Rect getLaunchBounds()
  {
    return null;
  }
  
  public void requestUsageTimeReport(PendingIntent paramPendingIntent) {}
  
  public ActivityOptionsCompat setLaunchBounds(Rect paramRect)
  {
    return this;
  }
  
  public Bundle toBundle()
  {
    return null;
  }
  
  public void update(ActivityOptionsCompat paramActivityOptionsCompat) {}
  
  private static class ActivityOptionsCompatImpl
    extends ActivityOptionsCompat
  {
    private final ActivityOptions mActivityOptions;
    
    ActivityOptionsCompatImpl(ActivityOptions paramActivityOptions)
    {
      this.mActivityOptions = paramActivityOptions;
    }
    
    public Rect getLaunchBounds()
    {
      if (Build.VERSION.SDK_INT < 24) {
        return null;
      }
      return ActivityOptionsCompat.Api24Impl.getLaunchBounds(this.mActivityOptions);
    }
    
    public void requestUsageTimeReport(PendingIntent paramPendingIntent)
    {
      if (Build.VERSION.SDK_INT >= 23) {
        ActivityOptionsCompat.Api23Impl.requestUsageTimeReport(this.mActivityOptions, paramPendingIntent);
      }
    }
    
    public ActivityOptionsCompat setLaunchBounds(Rect paramRect)
    {
      if (Build.VERSION.SDK_INT < 24) {
        return this;
      }
      return new ActivityOptionsCompatImpl(ActivityOptionsCompat.Api24Impl.setLaunchBounds(this.mActivityOptions, paramRect));
    }
    
    public Bundle toBundle()
    {
      return this.mActivityOptions.toBundle();
    }
    
    public void update(ActivityOptionsCompat paramActivityOptionsCompat)
    {
      if ((paramActivityOptionsCompat instanceof ActivityOptionsCompatImpl))
      {
        paramActivityOptionsCompat = (ActivityOptionsCompatImpl)paramActivityOptionsCompat;
        this.mActivityOptions.update(paramActivityOptionsCompat.mActivityOptions);
      }
    }
  }
  
  static class Api16Impl
  {
    static ActivityOptions makeCustomAnimation(Context paramContext, int paramInt1, int paramInt2)
    {
      return ActivityOptions.makeCustomAnimation(paramContext, paramInt1, paramInt2);
    }
    
    static ActivityOptions makeScaleUpAnimation(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      return ActivityOptions.makeScaleUpAnimation(paramView, paramInt1, paramInt2, paramInt3, paramInt4);
    }
    
    static ActivityOptions makeThumbnailScaleUpAnimation(View paramView, Bitmap paramBitmap, int paramInt1, int paramInt2)
    {
      return ActivityOptions.makeThumbnailScaleUpAnimation(paramView, paramBitmap, paramInt1, paramInt2);
    }
  }
  
  static class Api21Impl
  {
    static ActivityOptions makeSceneTransitionAnimation(Activity paramActivity, View paramView, String paramString)
    {
      return ActivityOptions.makeSceneTransitionAnimation(paramActivity, paramView, paramString);
    }
    
    @SafeVarargs
    static ActivityOptions makeSceneTransitionAnimation(Activity paramActivity, android.util.Pair<View, String>... paramVarArgs)
    {
      return ActivityOptions.makeSceneTransitionAnimation(paramActivity, paramVarArgs);
    }
    
    static ActivityOptions makeTaskLaunchBehind()
    {
      return ActivityOptions.makeTaskLaunchBehind();
    }
  }
  
  static class Api23Impl
  {
    static ActivityOptions makeBasic()
    {
      return ActivityOptions.makeBasic();
    }
    
    static ActivityOptions makeClipRevealAnimation(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      return ActivityOptions.makeClipRevealAnimation(paramView, paramInt1, paramInt2, paramInt3, paramInt4);
    }
    
    static void requestUsageTimeReport(ActivityOptions paramActivityOptions, PendingIntent paramPendingIntent)
    {
      paramActivityOptions.requestUsageTimeReport(paramPendingIntent);
    }
  }
  
  static class Api24Impl
  {
    static Rect getLaunchBounds(ActivityOptions paramActivityOptions)
    {
      return paramActivityOptions.getLaunchBounds();
    }
    
    static ActivityOptions setLaunchBounds(ActivityOptions paramActivityOptions, Rect paramRect)
    {
      return paramActivityOptions.setLaunchBounds(paramRect);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/app/ActivityOptionsCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */