package androidx.core.app;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.PendingIntent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.util.SparseArray;
import androidx.core.graphics.drawable.IconCompat;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

class NotificationCompatJellybean
{
  static final String EXTRA_ALLOW_GENERATED_REPLIES = "android.support.allowGeneratedReplies";
  static final String EXTRA_DATA_ONLY_REMOTE_INPUTS = "android.support.dataRemoteInputs";
  private static final String KEY_ACTION_INTENT = "actionIntent";
  private static final String KEY_ALLOWED_DATA_TYPES = "allowedDataTypes";
  private static final String KEY_ALLOW_FREE_FORM_INPUT = "allowFreeFormInput";
  private static final String KEY_CHOICES = "choices";
  private static final String KEY_DATA_ONLY_REMOTE_INPUTS = "dataOnlyRemoteInputs";
  private static final String KEY_EXTRAS = "extras";
  private static final String KEY_ICON = "icon";
  private static final String KEY_LABEL = "label";
  private static final String KEY_REMOTE_INPUTS = "remoteInputs";
  private static final String KEY_RESULT_KEY = "resultKey";
  private static final String KEY_SEMANTIC_ACTION = "semanticAction";
  private static final String KEY_SHOWS_USER_INTERFACE = "showsUserInterface";
  private static final String KEY_TITLE = "title";
  public static final String TAG = "NotificationCompat";
  private static Field sActionIconField;
  private static Field sActionIntentField;
  private static Field sActionTitleField;
  private static boolean sActionsAccessFailed;
  private static Field sActionsField;
  private static final Object sActionsLock = new Object();
  private static Field sExtrasField;
  private static boolean sExtrasFieldAccessFailed;
  private static final Object sExtrasLock = new Object();
  
  public static SparseArray<Bundle> buildActionExtrasMap(List<Bundle> paramList)
  {
    Object localObject1 = null;
    int i = 0;
    int j = paramList.size();
    while (i < j)
    {
      Bundle localBundle = (Bundle)paramList.get(i);
      Object localObject2 = localObject1;
      if (localBundle != null)
      {
        localObject2 = localObject1;
        if (localObject1 == null) {
          localObject2 = new SparseArray();
        }
        ((SparseArray)localObject2).put(i, localBundle);
      }
      i++;
      localObject1 = localObject2;
    }
    return (SparseArray<Bundle>)localObject1;
  }
  
  private static boolean ensureActionReflectionReadyLocked()
  {
    if (sActionsAccessFailed) {
      return false;
    }
    try
    {
      if (sActionsField == null)
      {
        Object localObject = Class.forName("android.app.Notification$Action");
        sActionIconField = ((Class)localObject).getDeclaredField("icon");
        sActionTitleField = ((Class)localObject).getDeclaredField("title");
        sActionIntentField = ((Class)localObject).getDeclaredField("actionIntent");
        localObject = Notification.class.getDeclaredField("actions");
        sActionsField = (Field)localObject;
        ((Field)localObject).setAccessible(true);
      }
    }
    catch (NoSuchFieldException localNoSuchFieldException)
    {
      Log.e("NotificationCompat", "Unable to access notification actions", localNoSuchFieldException);
      sActionsAccessFailed = true;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      Log.e("NotificationCompat", "Unable to access notification actions", localClassNotFoundException);
      sActionsAccessFailed = true;
    }
    return sActionsAccessFailed ^ true;
  }
  
  private static RemoteInput fromBundle(Bundle paramBundle)
  {
    Object localObject = paramBundle.getStringArrayList("allowedDataTypes");
    HashSet localHashSet = new HashSet();
    if (localObject != null)
    {
      localObject = ((ArrayList)localObject).iterator();
      while (((Iterator)localObject).hasNext()) {
        localHashSet.add((String)((Iterator)localObject).next());
      }
    }
    return new RemoteInput(paramBundle.getString("resultKey"), paramBundle.getCharSequence("label"), paramBundle.getCharSequenceArray("choices"), paramBundle.getBoolean("allowFreeFormInput"), 0, paramBundle.getBundle("extras"), localHashSet);
  }
  
  private static RemoteInput[] fromBundleArray(Bundle[] paramArrayOfBundle)
  {
    if (paramArrayOfBundle == null) {
      return null;
    }
    RemoteInput[] arrayOfRemoteInput = new RemoteInput[paramArrayOfBundle.length];
    for (int i = 0; i < paramArrayOfBundle.length; i++) {
      arrayOfRemoteInput[i] = fromBundle(paramArrayOfBundle[i]);
    }
    return arrayOfRemoteInput;
  }
  
  public static NotificationCompat.Action getAction(Notification paramNotification, int paramInt)
  {
    try
    {
      synchronized (sActionsLock)
      {
        Object[] arrayOfObject = getActionObjectsLocked(paramNotification);
        if (arrayOfObject != null)
        {
          Object localObject2 = arrayOfObject[paramInt];
          arrayOfObject = null;
          Object localObject3 = getExtras(paramNotification);
          paramNotification = arrayOfObject;
          if (localObject3 != null)
          {
            localObject3 = ((Bundle)localObject3).getSparseParcelableArray("android.support.actionExtras");
            paramNotification = arrayOfObject;
            if (localObject3 != null) {
              paramNotification = (Bundle)((SparseArray)localObject3).get(paramInt);
            }
          }
          paramNotification = readAction(sActionIconField.getInt(localObject2), (CharSequence)sActionTitleField.get(localObject2), (PendingIntent)sActionIntentField.get(localObject2), paramNotification);
          return paramNotification;
        }
      }
    }
    catch (IllegalAccessException paramNotification)
    {
      Log.e("NotificationCompat", "Unable to access notification actions", paramNotification);
      sActionsAccessFailed = true;
      return null;
    }
  }
  
  public static int getActionCount(Notification paramNotification)
  {
    synchronized (sActionsLock)
    {
      paramNotification = getActionObjectsLocked(paramNotification);
      int i;
      if (paramNotification != null) {
        i = paramNotification.length;
      } else {
        i = 0;
      }
      return i;
    }
  }
  
  static NotificationCompat.Action getActionFromBundle(Bundle paramBundle)
  {
    Bundle localBundle = paramBundle.getBundle("extras");
    boolean bool = false;
    if (localBundle != null) {
      bool = localBundle.getBoolean("android.support.allowGeneratedReplies", false);
    }
    return new NotificationCompat.Action(paramBundle.getInt("icon"), paramBundle.getCharSequence("title"), (PendingIntent)paramBundle.getParcelable("actionIntent"), paramBundle.getBundle("extras"), fromBundleArray(getBundleArrayFromBundle(paramBundle, "remoteInputs")), fromBundleArray(getBundleArrayFromBundle(paramBundle, "dataOnlyRemoteInputs")), bool, paramBundle.getInt("semanticAction"), paramBundle.getBoolean("showsUserInterface"), false, false);
  }
  
  private static Object[] getActionObjectsLocked(Notification paramNotification)
  {
    synchronized (sActionsLock)
    {
      if (!ensureActionReflectionReadyLocked()) {
        return null;
      }
      try
      {
        paramNotification = (Object[])sActionsField.get(paramNotification);
        return paramNotification;
      }
      catch (IllegalAccessException paramNotification)
      {
        Log.e("NotificationCompat", "Unable to access notification actions", paramNotification);
        sActionsAccessFailed = true;
        return null;
      }
    }
  }
  
  private static Bundle[] getBundleArrayFromBundle(Bundle paramBundle, String paramString)
  {
    Object localObject = paramBundle.getParcelableArray(paramString);
    if ((!(localObject instanceof Bundle[])) && (localObject != null))
    {
      localObject = (Bundle[])Arrays.copyOf((Object[])localObject, localObject.length, Bundle[].class);
      paramBundle.putParcelableArray(paramString, (Parcelable[])localObject);
      return (Bundle[])localObject;
    }
    return (Bundle[])localObject;
  }
  
  static Bundle getBundleForAction(NotificationCompat.Action paramAction)
  {
    Bundle localBundle = new Bundle();
    Object localObject = paramAction.getIconCompat();
    int i;
    if (localObject != null) {
      i = ((IconCompat)localObject).getResId();
    } else {
      i = 0;
    }
    localBundle.putInt("icon", i);
    localBundle.putCharSequence("title", paramAction.getTitle());
    localBundle.putParcelable("actionIntent", paramAction.getActionIntent());
    if (paramAction.getExtras() != null) {
      localObject = new Bundle(paramAction.getExtras());
    } else {
      localObject = new Bundle();
    }
    ((Bundle)localObject).putBoolean("android.support.allowGeneratedReplies", paramAction.getAllowGeneratedReplies());
    localBundle.putBundle("extras", (Bundle)localObject);
    localBundle.putParcelableArray("remoteInputs", toBundleArray(paramAction.getRemoteInputs()));
    localBundle.putBoolean("showsUserInterface", paramAction.getShowsUserInterface());
    localBundle.putInt("semanticAction", paramAction.getSemanticAction());
    return localBundle;
  }
  
  public static Bundle getExtras(Notification paramNotification)
  {
    synchronized (sExtrasLock)
    {
      if (sExtrasFieldAccessFailed) {
        return null;
      }
      try
      {
        if (sExtrasField == null)
        {
          localObject1 = Notification.class.getDeclaredField("extras");
          if (!Bundle.class.isAssignableFrom(((Field)localObject1).getType()))
          {
            Log.e("NotificationCompat", "Notification.extras field is not of type Bundle");
            sExtrasFieldAccessFailed = true;
            return null;
          }
          ((Field)localObject1).setAccessible(true);
          sExtrasField = (Field)localObject1;
        }
        Bundle localBundle = (Bundle)sExtrasField.get(paramNotification);
        Object localObject1 = localBundle;
        if (localBundle == null)
        {
          localObject1 = new android/os/Bundle;
          ((Bundle)localObject1).<init>();
          sExtrasField.set(paramNotification, localObject1);
        }
        return (Bundle)localObject1;
      }
      catch (NoSuchFieldException paramNotification)
      {
        Log.e("NotificationCompat", "Unable to access notification extras", paramNotification);
      }
      catch (IllegalAccessException paramNotification)
      {
        Log.e("NotificationCompat", "Unable to access notification extras", paramNotification);
      }
      sExtrasFieldAccessFailed = true;
      return null;
    }
  }
  
  public static NotificationCompat.Action readAction(int paramInt, CharSequence paramCharSequence, PendingIntent paramPendingIntent, Bundle paramBundle)
  {
    RemoteInput[] arrayOfRemoteInput1;
    RemoteInput[] arrayOfRemoteInput2;
    boolean bool;
    if (paramBundle != null)
    {
      arrayOfRemoteInput1 = fromBundleArray(getBundleArrayFromBundle(paramBundle, "android.support.remoteInputs"));
      arrayOfRemoteInput2 = fromBundleArray(getBundleArrayFromBundle(paramBundle, "android.support.dataRemoteInputs"));
      bool = paramBundle.getBoolean("android.support.allowGeneratedReplies");
    }
    else
    {
      arrayOfRemoteInput1 = null;
      arrayOfRemoteInput2 = null;
      bool = false;
    }
    return new NotificationCompat.Action(paramInt, paramCharSequence, paramPendingIntent, paramBundle, arrayOfRemoteInput1, arrayOfRemoteInput2, bool, 0, true, false, false);
  }
  
  private static Bundle toBundle(RemoteInput paramRemoteInput)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("resultKey", paramRemoteInput.getResultKey());
    localBundle.putCharSequence("label", paramRemoteInput.getLabel());
    localBundle.putCharSequenceArray("choices", paramRemoteInput.getChoices());
    localBundle.putBoolean("allowFreeFormInput", paramRemoteInput.getAllowFreeFormInput());
    localBundle.putBundle("extras", paramRemoteInput.getExtras());
    Object localObject = paramRemoteInput.getAllowedDataTypes();
    if ((localObject != null) && (!((Set)localObject).isEmpty()))
    {
      paramRemoteInput = new ArrayList(((Set)localObject).size());
      localObject = ((Set)localObject).iterator();
      while (((Iterator)localObject).hasNext()) {
        paramRemoteInput.add((String)((Iterator)localObject).next());
      }
      localBundle.putStringArrayList("allowedDataTypes", paramRemoteInput);
    }
    return localBundle;
  }
  
  private static Bundle[] toBundleArray(RemoteInput[] paramArrayOfRemoteInput)
  {
    if (paramArrayOfRemoteInput == null) {
      return null;
    }
    Bundle[] arrayOfBundle = new Bundle[paramArrayOfRemoteInput.length];
    for (int i = 0; i < paramArrayOfRemoteInput.length; i++) {
      arrayOfBundle[i] = toBundle(paramArrayOfRemoteInput[i]);
    }
    return arrayOfBundle;
  }
  
  public static Bundle writeActionAndGetExtras(Notification.Builder paramBuilder, NotificationCompat.Action paramAction)
  {
    IconCompat localIconCompat = paramAction.getIconCompat();
    int i;
    if (localIconCompat != null) {
      i = localIconCompat.getResId();
    } else {
      i = 0;
    }
    paramBuilder.addAction(i, paramAction.getTitle(), paramAction.getActionIntent());
    paramBuilder = new Bundle(paramAction.getExtras());
    if (paramAction.getRemoteInputs() != null) {
      paramBuilder.putParcelableArray("android.support.remoteInputs", toBundleArray(paramAction.getRemoteInputs()));
    }
    if (paramAction.getDataOnlyRemoteInputs() != null) {
      paramBuilder.putParcelableArray("android.support.dataRemoteInputs", toBundleArray(paramAction.getDataOnlyRemoteInputs()));
    }
    paramBuilder.putBoolean("android.support.allowGeneratedReplies", paramAction.getAllowGeneratedReplies());
    return paramBuilder;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/app/NotificationCompatJellybean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */