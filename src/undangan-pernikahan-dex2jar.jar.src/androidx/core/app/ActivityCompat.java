package androidx.core.app;

import android.app.Activity;
import android.app.SharedElementCallback.OnSharedElementsReadyListener;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentSender;
import android.content.IntentSender.SendIntentException;
import android.content.pm.PackageManager;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.Display;
import android.view.DragEvent;
import android.view.View;
import android.view.WindowManager;
import androidx.core.content.ContextCompat;
import androidx.core.content.LocusIdCompat;
import androidx.core.view.DragAndDropPermissionsCompat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class ActivityCompat
  extends ContextCompat
{
  private static PermissionCompatDelegate sDelegate;
  
  public static void finishAffinity(Activity paramActivity)
  {
    if (Build.VERSION.SDK_INT >= 16) {
      Api16Impl.finishAffinity(paramActivity);
    } else {
      paramActivity.finish();
    }
  }
  
  public static void finishAfterTransition(Activity paramActivity)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      Api21Impl.finishAfterTransition(paramActivity);
    } else {
      paramActivity.finish();
    }
  }
  
  public static PermissionCompatDelegate getPermissionCompatDelegate()
  {
    return sDelegate;
  }
  
  public static Uri getReferrer(Activity paramActivity)
  {
    if (Build.VERSION.SDK_INT >= 22) {
      return Api22Impl.getReferrer(paramActivity);
    }
    Intent localIntent = paramActivity.getIntent();
    paramActivity = (Uri)localIntent.getParcelableExtra("android.intent.extra.REFERRER");
    if (paramActivity != null) {
      return paramActivity;
    }
    paramActivity = localIntent.getStringExtra("android.intent.extra.REFERRER_NAME");
    if (paramActivity != null) {
      return Uri.parse(paramActivity);
    }
    return null;
  }
  
  @Deprecated
  public static boolean invalidateOptionsMenu(Activity paramActivity)
  {
    paramActivity.invalidateOptionsMenu();
    return true;
  }
  
  public static boolean isLaunchedFromBubble(Activity paramActivity)
  {
    if (Build.VERSION.SDK_INT >= 31) {
      return Api31Impl.isLaunchedFromBubble(paramActivity);
    }
    int i = Build.VERSION.SDK_INT;
    boolean bool2 = true;
    boolean bool1 = true;
    if (i == 30)
    {
      if ((Api30Impl.getDisplay(paramActivity) == null) || (Api30Impl.getDisplay(paramActivity).getDisplayId() == 0)) {
        bool1 = false;
      }
      return bool1;
    }
    if (Build.VERSION.SDK_INT == 29)
    {
      if ((paramActivity.getWindowManager().getDefaultDisplay() != null) && (paramActivity.getWindowManager().getDefaultDisplay().getDisplayId() != 0)) {
        bool1 = bool2;
      } else {
        bool1 = false;
      }
      return bool1;
    }
    return false;
  }
  
  public static void postponeEnterTransition(Activity paramActivity)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      Api21Impl.postponeEnterTransition(paramActivity);
    }
  }
  
  public static void recreate(Activity paramActivity)
  {
    if (Build.VERSION.SDK_INT >= 28) {
      paramActivity.recreate();
    } else {
      new Handler(paramActivity.getMainLooper()).post(new ActivityCompat..ExternalSyntheticLambda0(paramActivity));
    }
  }
  
  public static DragAndDropPermissionsCompat requestDragAndDropPermissions(Activity paramActivity, DragEvent paramDragEvent)
  {
    return DragAndDropPermissionsCompat.request(paramActivity, paramDragEvent);
  }
  
  public static void requestPermissions(final Activity paramActivity, String[] paramArrayOfString, final int paramInt)
  {
    PermissionCompatDelegate localPermissionCompatDelegate = sDelegate;
    if ((localPermissionCompatDelegate != null) && (localPermissionCompatDelegate.requestPermissions(paramActivity, paramArrayOfString, paramInt))) {
      return;
    }
    int j = paramArrayOfString.length;
    int i = 0;
    while (i < j) {
      if (!TextUtils.isEmpty(paramArrayOfString[i]))
      {
        i++;
      }
      else
      {
        paramActivity = new StringBuilder().append("Permission request for permissions ");
        paramArrayOfString = Arrays.toString(paramArrayOfString);
        Log5ECF72.a(paramArrayOfString);
        LogE84000.a(paramArrayOfString);
        Log229316.a(paramArrayOfString);
        throw new IllegalArgumentException(paramArrayOfString + " must not contain null or empty values");
      }
    }
    if (Build.VERSION.SDK_INT >= 23)
    {
      if ((paramActivity instanceof RequestPermissionsRequestCodeValidator)) {
        ((RequestPermissionsRequestCodeValidator)paramActivity).validateRequestPermissionsRequestCode(paramInt);
      }
      Api23Impl.requestPermissions(paramActivity, paramArrayOfString, paramInt);
    }
    else if ((paramActivity instanceof OnRequestPermissionsResultCallback))
    {
      new Handler(Looper.getMainLooper()).post(new Runnable()
      {
        public void run()
        {
          int[] arrayOfInt = new int[ActivityCompat.this.length];
          PackageManager localPackageManager = paramActivity.getPackageManager();
          String str = paramActivity.getPackageName();
          int j = ActivityCompat.this.length;
          for (int i = 0; i < j; i++) {
            arrayOfInt[i] = localPackageManager.checkPermission(ActivityCompat.this[i], str);
          }
          ((ActivityCompat.OnRequestPermissionsResultCallback)paramActivity).onRequestPermissionsResult(paramInt, ActivityCompat.this, arrayOfInt);
        }
      });
    }
  }
  
  public static <T extends View> T requireViewById(Activity paramActivity, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 28) {
      return (View)Api28Impl.requireViewById(paramActivity, paramInt);
    }
    paramActivity = paramActivity.findViewById(paramInt);
    if (paramActivity != null) {
      return paramActivity;
    }
    throw new IllegalArgumentException("ID does not reference a View inside this Activity");
  }
  
  public static void setEnterSharedElementCallback(Activity paramActivity, SharedElementCallback paramSharedElementCallback)
  {
    if (Build.VERSION.SDK_INT >= 21)
    {
      if (paramSharedElementCallback != null) {
        paramSharedElementCallback = new SharedElementCallback21Impl(paramSharedElementCallback);
      } else {
        paramSharedElementCallback = null;
      }
      Api21Impl.setEnterSharedElementCallback(paramActivity, paramSharedElementCallback);
    }
  }
  
  public static void setExitSharedElementCallback(Activity paramActivity, SharedElementCallback paramSharedElementCallback)
  {
    if (Build.VERSION.SDK_INT >= 21)
    {
      if (paramSharedElementCallback != null) {
        paramSharedElementCallback = new SharedElementCallback21Impl(paramSharedElementCallback);
      } else {
        paramSharedElementCallback = null;
      }
      Api21Impl.setExitSharedElementCallback(paramActivity, paramSharedElementCallback);
    }
  }
  
  public static void setLocusContext(Activity paramActivity, LocusIdCompat paramLocusIdCompat, Bundle paramBundle)
  {
    if (Build.VERSION.SDK_INT >= 30) {
      Api30Impl.setLocusContext(paramActivity, paramLocusIdCompat, paramBundle);
    }
  }
  
  public static void setPermissionCompatDelegate(PermissionCompatDelegate paramPermissionCompatDelegate)
  {
    sDelegate = paramPermissionCompatDelegate;
  }
  
  public static boolean shouldShowRequestPermissionRationale(Activity paramActivity, String paramString)
  {
    if (Build.VERSION.SDK_INT >= 23) {
      return Api23Impl.shouldShowRequestPermissionRationale(paramActivity, paramString);
    }
    return false;
  }
  
  public static void startActivityForResult(Activity paramActivity, Intent paramIntent, int paramInt, Bundle paramBundle)
  {
    if (Build.VERSION.SDK_INT >= 16) {
      Api16Impl.startActivityForResult(paramActivity, paramIntent, paramInt, paramBundle);
    } else {
      paramActivity.startActivityForResult(paramIntent, paramInt);
    }
  }
  
  public static void startIntentSenderForResult(Activity paramActivity, IntentSender paramIntentSender, int paramInt1, Intent paramIntent, int paramInt2, int paramInt3, int paramInt4, Bundle paramBundle)
    throws IntentSender.SendIntentException
  {
    if (Build.VERSION.SDK_INT >= 16) {
      Api16Impl.startIntentSenderForResult(paramActivity, paramIntentSender, paramInt1, paramIntent, paramInt2, paramInt3, paramInt4, paramBundle);
    } else {
      paramActivity.startIntentSenderForResult(paramIntentSender, paramInt1, paramIntent, paramInt2, paramInt3, paramInt4);
    }
  }
  
  public static void startPostponedEnterTransition(Activity paramActivity)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      Api21Impl.startPostponedEnterTransition(paramActivity);
    }
  }
  
  static class Api16Impl
  {
    static void finishAffinity(Activity paramActivity)
    {
      paramActivity.finishAffinity();
    }
    
    static void startActivityForResult(Activity paramActivity, Intent paramIntent, int paramInt, Bundle paramBundle)
    {
      paramActivity.startActivityForResult(paramIntent, paramInt, paramBundle);
    }
    
    static void startIntentSenderForResult(Activity paramActivity, IntentSender paramIntentSender, int paramInt1, Intent paramIntent, int paramInt2, int paramInt3, int paramInt4, Bundle paramBundle)
      throws IntentSender.SendIntentException
    {
      paramActivity.startIntentSenderForResult(paramIntentSender, paramInt1, paramIntent, paramInt2, paramInt3, paramInt4, paramBundle);
    }
  }
  
  static class Api21Impl
  {
    static void finishAfterTransition(Activity paramActivity)
    {
      paramActivity.finishAfterTransition();
    }
    
    static void postponeEnterTransition(Activity paramActivity)
    {
      paramActivity.postponeEnterTransition();
    }
    
    static void setEnterSharedElementCallback(Activity paramActivity, android.app.SharedElementCallback paramSharedElementCallback)
    {
      paramActivity.setEnterSharedElementCallback(paramSharedElementCallback);
    }
    
    static void setExitSharedElementCallback(Activity paramActivity, android.app.SharedElementCallback paramSharedElementCallback)
    {
      paramActivity.setExitSharedElementCallback(paramSharedElementCallback);
    }
    
    static void startPostponedEnterTransition(Activity paramActivity)
    {
      paramActivity.startPostponedEnterTransition();
    }
  }
  
  static class Api22Impl
  {
    static Uri getReferrer(Activity paramActivity)
    {
      return paramActivity.getReferrer();
    }
  }
  
  static class Api23Impl
  {
    static void onSharedElementsReady(Object paramObject)
    {
      ((SharedElementCallback.OnSharedElementsReadyListener)paramObject).onSharedElementsReady();
    }
    
    static void requestPermissions(Activity paramActivity, String[] paramArrayOfString, int paramInt)
    {
      paramActivity.requestPermissions(paramArrayOfString, paramInt);
    }
    
    static boolean shouldShowRequestPermissionRationale(Activity paramActivity, String paramString)
    {
      return paramActivity.shouldShowRequestPermissionRationale(paramString);
    }
  }
  
  static class Api28Impl
  {
    static <T> T requireViewById(Activity paramActivity, int paramInt)
    {
      return paramActivity.requireViewById(paramInt);
    }
  }
  
  static class Api30Impl
  {
    static Display getDisplay(ContextWrapper paramContextWrapper)
    {
      return paramContextWrapper.getDisplay();
    }
    
    static void setLocusContext(Activity paramActivity, LocusIdCompat paramLocusIdCompat, Bundle paramBundle)
    {
      if (paramLocusIdCompat == null) {
        paramLocusIdCompat = null;
      } else {
        paramLocusIdCompat = paramLocusIdCompat.toLocusId();
      }
      paramActivity.setLocusContext(paramLocusIdCompat, paramBundle);
    }
  }
  
  static class Api31Impl
  {
    static boolean isLaunchedFromBubble(Activity paramActivity)
    {
      return paramActivity.isLaunchedFromBubble();
    }
  }
  
  public static abstract interface OnRequestPermissionsResultCallback
  {
    public abstract void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfInt);
  }
  
  public static abstract interface PermissionCompatDelegate
  {
    public abstract boolean onActivityResult(Activity paramActivity, int paramInt1, int paramInt2, Intent paramIntent);
    
    public abstract boolean requestPermissions(Activity paramActivity, String[] paramArrayOfString, int paramInt);
  }
  
  public static abstract interface RequestPermissionsRequestCodeValidator
  {
    public abstract void validateRequestPermissionsRequestCode(int paramInt);
  }
  
  static class SharedElementCallback21Impl
    extends android.app.SharedElementCallback
  {
    private final SharedElementCallback mCallback;
    
    SharedElementCallback21Impl(SharedElementCallback paramSharedElementCallback)
    {
      this.mCallback = paramSharedElementCallback;
    }
    
    public Parcelable onCaptureSharedElementSnapshot(View paramView, Matrix paramMatrix, RectF paramRectF)
    {
      return this.mCallback.onCaptureSharedElementSnapshot(paramView, paramMatrix, paramRectF);
    }
    
    public View onCreateSnapshotView(Context paramContext, Parcelable paramParcelable)
    {
      return this.mCallback.onCreateSnapshotView(paramContext, paramParcelable);
    }
    
    public void onMapSharedElements(List<String> paramList, Map<String, View> paramMap)
    {
      this.mCallback.onMapSharedElements(paramList, paramMap);
    }
    
    public void onRejectSharedElements(List<View> paramList)
    {
      this.mCallback.onRejectSharedElements(paramList);
    }
    
    public void onSharedElementEnd(List<String> paramList, List<View> paramList1, List<View> paramList2)
    {
      this.mCallback.onSharedElementEnd(paramList, paramList1, paramList2);
    }
    
    public void onSharedElementStart(List<String> paramList, List<View> paramList1, List<View> paramList2)
    {
      this.mCallback.onSharedElementStart(paramList, paramList1, paramList2);
    }
    
    public void onSharedElementsArrived(List<String> paramList, List<View> paramList1, SharedElementCallback.OnSharedElementsReadyListener paramOnSharedElementsReadyListener)
    {
      this.mCallback.onSharedElementsArrived(paramList, paramList1, new ActivityCompat.SharedElementCallback21Impl..ExternalSyntheticLambda0(paramOnSharedElementsReadyListener));
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/app/ActivityCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */