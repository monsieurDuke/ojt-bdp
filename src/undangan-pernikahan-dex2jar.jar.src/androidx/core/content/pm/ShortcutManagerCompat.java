package androidx.core.content.pm;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.IntentSender.SendIntentException;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.IconCompat;
import androidx.core.util.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ShortcutManagerCompat
{
  static final String ACTION_INSTALL_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";
  private static final int DEFAULT_MAX_ICON_DIMENSION_DP = 96;
  private static final int DEFAULT_MAX_ICON_DIMENSION_LOWRAM_DP = 48;
  public static final String EXTRA_SHORTCUT_ID = "android.intent.extra.shortcut.ID";
  public static final int FLAG_MATCH_CACHED = 8;
  public static final int FLAG_MATCH_DYNAMIC = 2;
  public static final int FLAG_MATCH_MANIFEST = 1;
  public static final int FLAG_MATCH_PINNED = 4;
  static final String INSTALL_SHORTCUT_PERMISSION = "com.android.launcher.permission.INSTALL_SHORTCUT";
  private static final String SHORTCUT_LISTENER_INTENT_FILTER_ACTION = "androidx.core.content.pm.SHORTCUT_LISTENER";
  private static final String SHORTCUT_LISTENER_META_DATA_KEY = "androidx.core.content.pm.shortcut_listener_impl";
  private static volatile List<ShortcutInfoChangeListener> sShortcutInfoChangeListeners = null;
  private static volatile ShortcutInfoCompatSaver<?> sShortcutInfoCompatSaver = null;
  
  public static boolean addDynamicShortcuts(Context paramContext, List<ShortcutInfoCompat> paramList)
  {
    List localList = removeShortcutsExcludedFromSurface(paramList, 1);
    if (Build.VERSION.SDK_INT <= 29) {
      convertUriIconsToBitmapIcons(paramContext, localList);
    }
    if (Build.VERSION.SDK_INT >= 25)
    {
      ArrayList localArrayList = new ArrayList();
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext()) {
        localArrayList.add(((ShortcutInfoCompat)localIterator.next()).toShortcutInfo());
      }
      if (!((ShortcutManager)paramContext.getSystemService(ShortcutManager.class)).addDynamicShortcuts(localArrayList)) {
        return false;
      }
    }
    getShortcutInfoSaverInstance(paramContext).addShortcuts(localList);
    paramContext = getShortcutInfoListeners(paramContext).iterator();
    while (paramContext.hasNext()) {
      ((ShortcutInfoChangeListener)paramContext.next()).onShortcutAdded(paramList);
    }
    return true;
  }
  
  static boolean convertUriIconToBitmapIcon(Context paramContext, ShortcutInfoCompat paramShortcutInfoCompat)
  {
    if (paramShortcutInfoCompat.mIcon == null) {
      return false;
    }
    int i = paramShortcutInfoCompat.mIcon.mType;
    if ((i != 6) && (i != 4)) {
      return true;
    }
    paramContext = paramShortcutInfoCompat.mIcon.getUriInputStream(paramContext);
    if (paramContext == null) {
      return false;
    }
    paramContext = BitmapFactory.decodeStream(paramContext);
    if (paramContext == null) {
      return false;
    }
    if (i == 6) {
      paramContext = IconCompat.createWithAdaptiveBitmap(paramContext);
    } else {
      paramContext = IconCompat.createWithBitmap(paramContext);
    }
    paramShortcutInfoCompat.mIcon = paramContext;
    return true;
  }
  
  static void convertUriIconsToBitmapIcons(Context paramContext, List<ShortcutInfoCompat> paramList)
  {
    Iterator localIterator = new ArrayList(paramList).iterator();
    while (localIterator.hasNext())
    {
      ShortcutInfoCompat localShortcutInfoCompat = (ShortcutInfoCompat)localIterator.next();
      if (!convertUriIconToBitmapIcon(paramContext, localShortcutInfoCompat)) {
        paramList.remove(localShortcutInfoCompat);
      }
    }
  }
  
  public static Intent createShortcutResultIntent(Context paramContext, ShortcutInfoCompat paramShortcutInfoCompat)
  {
    Intent localIntent = null;
    if (Build.VERSION.SDK_INT >= 26) {
      localIntent = ((ShortcutManager)paramContext.getSystemService(ShortcutManager.class)).createShortcutResultIntent(paramShortcutInfoCompat.toShortcutInfo());
    }
    paramContext = localIntent;
    if (localIntent == null) {
      paramContext = new Intent();
    }
    return paramShortcutInfoCompat.addToIntent(paramContext);
  }
  
  public static void disableShortcuts(Context paramContext, List<String> paramList, CharSequence paramCharSequence)
  {
    if (Build.VERSION.SDK_INT >= 25) {
      ((ShortcutManager)paramContext.getSystemService(ShortcutManager.class)).disableShortcuts(paramList, paramCharSequence);
    }
    getShortcutInfoSaverInstance(paramContext).removeShortcuts(paramList);
    paramContext = getShortcutInfoListeners(paramContext).iterator();
    while (paramContext.hasNext()) {
      ((ShortcutInfoChangeListener)paramContext.next()).onShortcutRemoved(paramList);
    }
  }
  
  public static void enableShortcuts(Context paramContext, List<ShortcutInfoCompat> paramList)
  {
    List localList = removeShortcutsExcludedFromSurface(paramList, 1);
    if (Build.VERSION.SDK_INT >= 25)
    {
      ArrayList localArrayList = new ArrayList(paramList.size());
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext()) {
        localArrayList.add(((ShortcutInfoCompat)localIterator.next()).mId);
      }
      ((ShortcutManager)paramContext.getSystemService(ShortcutManager.class)).enableShortcuts(localArrayList);
    }
    getShortcutInfoSaverInstance(paramContext).addShortcuts(localList);
    paramContext = getShortcutInfoListeners(paramContext).iterator();
    while (paramContext.hasNext()) {
      ((ShortcutInfoChangeListener)paramContext.next()).onShortcutAdded(paramList);
    }
  }
  
  public static List<ShortcutInfoCompat> getDynamicShortcuts(Context paramContext)
  {
    if (Build.VERSION.SDK_INT >= 25)
    {
      Object localObject = ((ShortcutManager)paramContext.getSystemService(ShortcutManager.class)).getDynamicShortcuts();
      ArrayList localArrayList = new ArrayList(((List)localObject).size());
      localObject = ((List)localObject).iterator();
      while (((Iterator)localObject).hasNext()) {
        localArrayList.add(new ShortcutInfoCompat.Builder(paramContext, (ShortcutInfo)((Iterator)localObject).next()).build());
      }
      return localArrayList;
    }
    try
    {
      paramContext = getShortcutInfoSaverInstance(paramContext).getShortcuts();
      return paramContext;
    }
    catch (Exception paramContext) {}
    return new ArrayList();
  }
  
  private static int getIconDimensionInternal(Context paramContext, boolean paramBoolean)
  {
    ActivityManager localActivityManager = (ActivityManager)paramContext.getSystemService("activity");
    if ((Build.VERSION.SDK_INT >= 19) && (localActivityManager != null) && (!localActivityManager.isLowRamDevice())) {
      i = 0;
    } else {
      i = 1;
    }
    if (i != 0) {
      i = 48;
    } else {
      i = 96;
    }
    int i = Math.max(1, i);
    paramContext = paramContext.getResources().getDisplayMetrics();
    float f;
    if (paramBoolean) {
      f = paramContext.xdpi;
    } else {
      f = paramContext.ydpi;
    }
    f /= 160.0F;
    return (int)(i * f);
  }
  
  public static int getIconMaxHeight(Context paramContext)
  {
    Preconditions.checkNotNull(paramContext);
    if (Build.VERSION.SDK_INT >= 25) {
      return ((ShortcutManager)paramContext.getSystemService(ShortcutManager.class)).getIconMaxHeight();
    }
    return getIconDimensionInternal(paramContext, false);
  }
  
  public static int getIconMaxWidth(Context paramContext)
  {
    Preconditions.checkNotNull(paramContext);
    if (Build.VERSION.SDK_INT >= 25) {
      return ((ShortcutManager)paramContext.getSystemService(ShortcutManager.class)).getIconMaxWidth();
    }
    return getIconDimensionInternal(paramContext, true);
  }
  
  public static int getMaxShortcutCountPerActivity(Context paramContext)
  {
    Preconditions.checkNotNull(paramContext);
    if (Build.VERSION.SDK_INT >= 25) {
      return ((ShortcutManager)paramContext.getSystemService(ShortcutManager.class)).getMaxShortcutCountPerActivity();
    }
    return 5;
  }
  
  static List<ShortcutInfoChangeListener> getShortcutInfoChangeListeners()
  {
    return sShortcutInfoChangeListeners;
  }
  
  private static String getShortcutInfoCompatWithLowestRank(List<ShortcutInfoCompat> paramList)
  {
    int i = -1;
    ShortcutInfoCompat localShortcutInfoCompat = null;
    Iterator localIterator = paramList.iterator();
    paramList = localShortcutInfoCompat;
    while (localIterator.hasNext())
    {
      localShortcutInfoCompat = (ShortcutInfoCompat)localIterator.next();
      int j = i;
      if (localShortcutInfoCompat.getRank() > i)
      {
        paramList = localShortcutInfoCompat.getId();
        j = localShortcutInfoCompat.getRank();
      }
      i = j;
    }
    return paramList;
  }
  
  private static List<ShortcutInfoChangeListener> getShortcutInfoListeners(Context paramContext)
  {
    if (sShortcutInfoChangeListeners == null)
    {
      ArrayList localArrayList = new ArrayList();
      if (Build.VERSION.SDK_INT >= 21)
      {
        Object localObject1 = paramContext.getPackageManager();
        Object localObject2 = new Intent("androidx.core.content.pm.SHORTCUT_LISTENER");
        ((Intent)localObject2).setPackage(paramContext.getPackageName());
        localObject1 = ((PackageManager)localObject1).queryIntentActivities((Intent)localObject2, 128).iterator();
        while (((Iterator)localObject1).hasNext())
        {
          localObject2 = ((ResolveInfo)((Iterator)localObject1).next()).activityInfo;
          if (localObject2 != null)
          {
            localObject2 = ((ActivityInfo)localObject2).metaData;
            if (localObject2 != null)
            {
              localObject2 = ((Bundle)localObject2).getString("androidx.core.content.pm.shortcut_listener_impl");
              if (localObject2 != null) {
                try
                {
                  localArrayList.add((ShortcutInfoChangeListener)Class.forName((String)localObject2, false, ShortcutManagerCompat.class.getClassLoader()).getMethod("getInstance", new Class[] { Context.class }).invoke(null, new Object[] { paramContext }));
                }
                catch (Exception localException) {}
              }
            }
          }
        }
      }
      if (sShortcutInfoChangeListeners == null) {
        sShortcutInfoChangeListeners = localArrayList;
      }
    }
    return sShortcutInfoChangeListeners;
  }
  
  private static ShortcutInfoCompatSaver<?> getShortcutInfoSaverInstance(Context paramContext)
  {
    if (sShortcutInfoCompatSaver == null)
    {
      if (Build.VERSION.SDK_INT >= 23) {
        try
        {
          sShortcutInfoCompatSaver = (ShortcutInfoCompatSaver)Class.forName("androidx.sharetarget.ShortcutInfoCompatSaverImpl", false, ShortcutManagerCompat.class.getClassLoader()).getMethod("getInstance", new Class[] { Context.class }).invoke(null, new Object[] { paramContext });
        }
        catch (Exception paramContext) {}
      }
      if (sShortcutInfoCompatSaver == null) {
        sShortcutInfoCompatSaver = new ShortcutInfoCompatSaver.NoopImpl();
      }
    }
    return sShortcutInfoCompatSaver;
  }
  
  public static List<ShortcutInfoCompat> getShortcuts(Context paramContext, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 30) {
      return ShortcutInfoCompat.fromShortcuts(paramContext, ((ShortcutManager)paramContext.getSystemService(ShortcutManager.class)).getShortcuts(paramInt));
    }
    if (Build.VERSION.SDK_INT >= 25)
    {
      ShortcutManager localShortcutManager = (ShortcutManager)paramContext.getSystemService(ShortcutManager.class);
      ArrayList localArrayList = new ArrayList();
      if ((paramInt & 0x1) != 0) {
        localArrayList.addAll(localShortcutManager.getManifestShortcuts());
      }
      if ((paramInt & 0x2) != 0) {
        localArrayList.addAll(localShortcutManager.getDynamicShortcuts());
      }
      if ((paramInt & 0x4) != 0) {
        localArrayList.addAll(localShortcutManager.getPinnedShortcuts());
      }
      return ShortcutInfoCompat.fromShortcuts(paramContext, localArrayList);
    }
    if ((paramInt & 0x2) != 0) {
      try
      {
        paramContext = getShortcutInfoSaverInstance(paramContext).getShortcuts();
        return paramContext;
      }
      catch (Exception paramContext) {}
    }
    return Collections.emptyList();
  }
  
  public static boolean isRateLimitingActive(Context paramContext)
  {
    Preconditions.checkNotNull(paramContext);
    if (Build.VERSION.SDK_INT >= 25) {
      return ((ShortcutManager)paramContext.getSystemService(ShortcutManager.class)).isRateLimitingActive();
    }
    boolean bool;
    if (getShortcuts(paramContext, 3).size() == getMaxShortcutCountPerActivity(paramContext)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static boolean isRequestPinShortcutSupported(Context paramContext)
  {
    if (Build.VERSION.SDK_INT >= 26) {
      return ((ShortcutManager)paramContext.getSystemService(ShortcutManager.class)).isRequestPinShortcutSupported();
    }
    if (ContextCompat.checkSelfPermission(paramContext, "com.android.launcher.permission.INSTALL_SHORTCUT") != 0) {
      return false;
    }
    Iterator localIterator = paramContext.getPackageManager().queryBroadcastReceivers(new Intent("com.android.launcher.action.INSTALL_SHORTCUT"), 0).iterator();
    while (localIterator.hasNext())
    {
      paramContext = ((ResolveInfo)localIterator.next()).activityInfo.permission;
      if ((!TextUtils.isEmpty(paramContext)) && (!"com.android.launcher.permission.INSTALL_SHORTCUT".equals(paramContext))) {}
      return true;
    }
    return false;
  }
  
  /* Error */
  public static boolean pushDynamicShortcut(Context paramContext, ShortcutInfoCompat paramShortcutInfoCompat)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic 276	androidx/core/util/Preconditions:checkNotNull	(Ljava/lang/Object;)Ljava/lang/Object;
    //   4: pop
    //   5: aload_1
    //   6: invokestatic 276	androidx/core/util/Preconditions:checkNotNull	(Ljava/lang/Object;)Ljava/lang/Object;
    //   9: pop
    //   10: getstatic 69	android/os/Build$VERSION:SDK_INT	I
    //   13: bipush 31
    //   15: if_icmpgt +51 -> 66
    //   18: aload_1
    //   19: iconst_1
    //   20: invokevirtual 428	androidx/core/content/pm/ShortcutInfoCompat:isExcludedFromSurfaces	(I)Z
    //   23: ifeq +43 -> 66
    //   26: aload_0
    //   27: invokestatic 127	androidx/core/content/pm/ShortcutManagerCompat:getShortcutInfoListeners	(Landroid/content/Context;)Ljava/util/List;
    //   30: invokeinterface 82 1 0
    //   35: astore_0
    //   36: aload_0
    //   37: invokeinterface 88 1 0
    //   42: ifeq +22 -> 64
    //   45: aload_0
    //   46: invokeinterface 92 1 0
    //   51: checkcast 129	androidx/core/content/pm/ShortcutInfoChangeListener
    //   54: aload_1
    //   55: invokestatic 432	java/util/Collections:singletonList	(Ljava/lang/Object;)Ljava/util/List;
    //   58: invokevirtual 133	androidx/core/content/pm/ShortcutInfoChangeListener:onShortcutAdded	(Ljava/util/List;)V
    //   61: goto -25 -> 36
    //   64: iconst_1
    //   65: ireturn
    //   66: aload_0
    //   67: invokestatic 397	androidx/core/content/pm/ShortcutManagerCompat:getMaxShortcutCountPerActivity	(Landroid/content/Context;)I
    //   70: istore_2
    //   71: iload_2
    //   72: ifne +5 -> 77
    //   75: iconst_0
    //   76: ireturn
    //   77: getstatic 69	android/os/Build$VERSION:SDK_INT	I
    //   80: bipush 29
    //   82: if_icmpgt +9 -> 91
    //   85: aload_0
    //   86: aload_1
    //   87: invokestatic 168	androidx/core/content/pm/ShortcutManagerCompat:convertUriIconToBitmapIcon	(Landroid/content/Context;Landroidx/core/content/pm/ShortcutInfoCompat;)Z
    //   90: pop
    //   91: getstatic 69	android/os/Build$VERSION:SDK_INT	I
    //   94: bipush 30
    //   96: if_icmplt +22 -> 118
    //   99: aload_0
    //   100: ldc 104
    //   102: invokevirtual 110	android/content/Context:getSystemService	(Ljava/lang/Class;)Ljava/lang/Object;
    //   105: checkcast 104	android/content/pm/ShortcutManager
    //   108: aload_1
    //   109: invokevirtual 98	androidx/core/content/pm/ShortcutInfoCompat:toShortcutInfo	()Landroid/content/pm/ShortcutInfo;
    //   112: invokevirtual 435	android/content/pm/ShortcutManager:pushDynamicShortcut	(Landroid/content/pm/ShortcutInfo;)V
    //   115: goto +104 -> 219
    //   118: getstatic 69	android/os/Build$VERSION:SDK_INT	I
    //   121: bipush 25
    //   123: if_icmplt +96 -> 219
    //   126: aload_0
    //   127: ldc 104
    //   129: invokevirtual 110	android/content/Context:getSystemService	(Ljava/lang/Class;)Ljava/lang/Object;
    //   132: checkcast 104	android/content/pm/ShortcutManager
    //   135: astore_3
    //   136: aload_3
    //   137: invokevirtual 393	android/content/pm/ShortcutManager:isRateLimitingActive	()Z
    //   140: ifeq +5 -> 145
    //   143: iconst_0
    //   144: ireturn
    //   145: aload_3
    //   146: invokevirtual 215	android/content/pm/ShortcutManager:getDynamicShortcuts	()Ljava/util/List;
    //   149: astore 4
    //   151: aload 4
    //   153: invokeinterface 200 1 0
    //   158: iload_2
    //   159: if_icmplt +41 -> 200
    //   162: aload 4
    //   164: invokestatic 438	androidx/core/content/pm/ShortcutManagerCompat$Api25Impl:getShortcutInfoWithLowestRank	(Ljava/util/List;)Ljava/lang/String;
    //   167: astore 4
    //   169: aload 4
    //   171: invokestatic 444	mt/Log5ECF72:a	(Ljava/lang/Object;)V
    //   174: aload 4
    //   176: invokestatic 447	mt/LogE84000:a	(Ljava/lang/Object;)V
    //   179: aload 4
    //   181: invokestatic 450	mt/Log229316:a	(Ljava/lang/Object;)V
    //   184: aload_3
    //   185: iconst_1
    //   186: anewarray 420	java/lang/String
    //   189: dup
    //   190: iconst_0
    //   191: aload 4
    //   193: aastore
    //   194: invokestatic 456	java/util/Arrays:asList	([Ljava/lang/Object;)Ljava/util/List;
    //   197: invokevirtual 459	android/content/pm/ShortcutManager:removeDynamicShortcuts	(Ljava/util/List;)V
    //   200: aload_3
    //   201: iconst_1
    //   202: anewarray 219	android/content/pm/ShortcutInfo
    //   205: dup
    //   206: iconst_0
    //   207: aload_1
    //   208: invokevirtual 98	androidx/core/content/pm/ShortcutInfoCompat:toShortcutInfo	()Landroid/content/pm/ShortcutInfo;
    //   211: aastore
    //   212: invokestatic 456	java/util/Arrays:asList	([Ljava/lang/Object;)Ljava/util/List;
    //   215: invokevirtual 113	android/content/pm/ShortcutManager:addDynamicShortcuts	(Ljava/util/List;)Z
    //   218: pop
    //   219: aload_0
    //   220: invokestatic 117	androidx/core/content/pm/ShortcutManagerCompat:getShortcutInfoSaverInstance	(Landroid/content/Context;)Landroidx/core/content/pm/ShortcutInfoCompatSaver;
    //   223: astore_3
    //   224: aload_3
    //   225: invokevirtual 230	androidx/core/content/pm/ShortcutInfoCompatSaver:getShortcuts	()Ljava/util/List;
    //   228: astore 4
    //   230: aload 4
    //   232: invokeinterface 200 1 0
    //   237: iload_2
    //   238: if_icmplt +42 -> 280
    //   241: aload 4
    //   243: invokestatic 461	androidx/core/content/pm/ShortcutManagerCompat:getShortcutInfoCompatWithLowestRank	(Ljava/util/List;)Ljava/lang/String;
    //   246: astore 4
    //   248: aload 4
    //   250: invokestatic 444	mt/Log5ECF72:a	(Ljava/lang/Object;)V
    //   253: aload 4
    //   255: invokestatic 447	mt/LogE84000:a	(Ljava/lang/Object;)V
    //   258: aload 4
    //   260: invokestatic 450	mt/Log229316:a	(Ljava/lang/Object;)V
    //   263: aload_3
    //   264: iconst_1
    //   265: anewarray 420	java/lang/String
    //   268: dup
    //   269: iconst_0
    //   270: aload 4
    //   272: aastore
    //   273: invokestatic 456	java/util/Arrays:asList	([Ljava/lang/Object;)Ljava/util/List;
    //   276: invokevirtual 192	androidx/core/content/pm/ShortcutInfoCompatSaver:removeShortcuts	(Ljava/util/List;)Ljava/lang/Object;
    //   279: pop
    //   280: aload_3
    //   281: iconst_1
    //   282: anewarray 94	androidx/core/content/pm/ShortcutInfoCompat
    //   285: dup
    //   286: iconst_0
    //   287: aload_1
    //   288: aastore
    //   289: invokestatic 456	java/util/Arrays:asList	([Ljava/lang/Object;)Ljava/util/List;
    //   292: invokevirtual 123	androidx/core/content/pm/ShortcutInfoCompatSaver:addShortcuts	(Ljava/util/List;)Ljava/lang/Object;
    //   295: pop
    //   296: aload_0
    //   297: invokestatic 127	androidx/core/content/pm/ShortcutManagerCompat:getShortcutInfoListeners	(Landroid/content/Context;)Ljava/util/List;
    //   300: invokeinterface 82 1 0
    //   305: astore_3
    //   306: aload_3
    //   307: invokeinterface 88 1 0
    //   312: ifeq +22 -> 334
    //   315: aload_3
    //   316: invokeinterface 92 1 0
    //   321: checkcast 129	androidx/core/content/pm/ShortcutInfoChangeListener
    //   324: aload_1
    //   325: invokestatic 432	java/util/Collections:singletonList	(Ljava/lang/Object;)Ljava/util/List;
    //   328: invokevirtual 133	androidx/core/content/pm/ShortcutInfoChangeListener:onShortcutAdded	(Ljava/util/List;)V
    //   331: goto -25 -> 306
    //   334: aload_0
    //   335: aload_1
    //   336: invokevirtual 298	androidx/core/content/pm/ShortcutInfoCompat:getId	()Ljava/lang/String;
    //   339: invokestatic 465	androidx/core/content/pm/ShortcutManagerCompat:reportShortcutUsed	(Landroid/content/Context;Ljava/lang/String;)V
    //   342: iconst_1
    //   343: ireturn
    //   344: astore 4
    //   346: aload_0
    //   347: invokestatic 127	androidx/core/content/pm/ShortcutManagerCompat:getShortcutInfoListeners	(Landroid/content/Context;)Ljava/util/List;
    //   350: invokeinterface 82 1 0
    //   355: astore_3
    //   356: aload_3
    //   357: invokeinterface 88 1 0
    //   362: ifeq +22 -> 384
    //   365: aload_3
    //   366: invokeinterface 92 1 0
    //   371: checkcast 129	androidx/core/content/pm/ShortcutInfoChangeListener
    //   374: aload_1
    //   375: invokestatic 432	java/util/Collections:singletonList	(Ljava/lang/Object;)Ljava/util/List;
    //   378: invokevirtual 133	androidx/core/content/pm/ShortcutInfoChangeListener:onShortcutAdded	(Ljava/util/List;)V
    //   381: goto -25 -> 356
    //   384: aload_0
    //   385: aload_1
    //   386: invokevirtual 298	androidx/core/content/pm/ShortcutInfoCompat:getId	()Ljava/lang/String;
    //   389: invokestatic 465	androidx/core/content/pm/ShortcutManagerCompat:reportShortcutUsed	(Landroid/content/Context;Ljava/lang/String;)V
    //   392: aload 4
    //   394: athrow
    //   395: astore_3
    //   396: aload_0
    //   397: invokestatic 127	androidx/core/content/pm/ShortcutManagerCompat:getShortcutInfoListeners	(Landroid/content/Context;)Ljava/util/List;
    //   400: invokeinterface 82 1 0
    //   405: astore_3
    //   406: aload_3
    //   407: invokeinterface 88 1 0
    //   412: ifeq +22 -> 434
    //   415: aload_3
    //   416: invokeinterface 92 1 0
    //   421: checkcast 129	androidx/core/content/pm/ShortcutInfoChangeListener
    //   424: aload_1
    //   425: invokestatic 432	java/util/Collections:singletonList	(Ljava/lang/Object;)Ljava/util/List;
    //   428: invokevirtual 133	androidx/core/content/pm/ShortcutInfoChangeListener:onShortcutAdded	(Ljava/util/List;)V
    //   431: goto -25 -> 406
    //   434: aload_0
    //   435: aload_1
    //   436: invokevirtual 298	androidx/core/content/pm/ShortcutInfoCompat:getId	()Ljava/lang/String;
    //   439: invokestatic 465	androidx/core/content/pm/ShortcutManagerCompat:reportShortcutUsed	(Landroid/content/Context;Ljava/lang/String;)V
    //   442: iconst_0
    //   443: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	444	0	paramContext	Context
    //   0	444	1	paramShortcutInfoCompat	ShortcutInfoCompat
    //   70	169	2	i	int
    //   135	231	3	localObject1	Object
    //   395	1	3	localException	Exception
    //   405	11	3	localIterator	Iterator
    //   149	122	4	localObject2	Object
    //   344	49	4	localObject3	Object
    // Exception table:
    //   from	to	target	type
    //   224	248	344	finally
    //   263	280	344	finally
    //   280	296	344	finally
    //   224	248	395	java/lang/Exception
    //   263	280	395	java/lang/Exception
    //   280	296	395	java/lang/Exception
  }
  
  public static void removeAllDynamicShortcuts(Context paramContext)
  {
    if (Build.VERSION.SDK_INT >= 25) {
      ((ShortcutManager)paramContext.getSystemService(ShortcutManager.class)).removeAllDynamicShortcuts();
    }
    getShortcutInfoSaverInstance(paramContext).removeAllShortcuts();
    paramContext = getShortcutInfoListeners(paramContext).iterator();
    while (paramContext.hasNext()) {
      ((ShortcutInfoChangeListener)paramContext.next()).onAllShortcutsRemoved();
    }
  }
  
  public static void removeDynamicShortcuts(Context paramContext, List<String> paramList)
  {
    if (Build.VERSION.SDK_INT >= 25) {
      ((ShortcutManager)paramContext.getSystemService(ShortcutManager.class)).removeDynamicShortcuts(paramList);
    }
    getShortcutInfoSaverInstance(paramContext).removeShortcuts(paramList);
    paramContext = getShortcutInfoListeners(paramContext).iterator();
    while (paramContext.hasNext()) {
      ((ShortcutInfoChangeListener)paramContext.next()).onShortcutRemoved(paramList);
    }
  }
  
  public static void removeLongLivedShortcuts(Context paramContext, List<String> paramList)
  {
    if (Build.VERSION.SDK_INT < 30)
    {
      removeDynamicShortcuts(paramContext, paramList);
      return;
    }
    ((ShortcutManager)paramContext.getSystemService(ShortcutManager.class)).removeLongLivedShortcuts(paramList);
    getShortcutInfoSaverInstance(paramContext).removeShortcuts(paramList);
    paramContext = getShortcutInfoListeners(paramContext).iterator();
    while (paramContext.hasNext()) {
      ((ShortcutInfoChangeListener)paramContext.next()).onShortcutRemoved(paramList);
    }
  }
  
  private static List<ShortcutInfoCompat> removeShortcutsExcludedFromSurface(List<ShortcutInfoCompat> paramList, int paramInt)
  {
    Objects.requireNonNull(paramList);
    if (Build.VERSION.SDK_INT > 31) {
      return paramList;
    }
    ArrayList localArrayList = new ArrayList(paramList);
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      ShortcutInfoCompat localShortcutInfoCompat = (ShortcutInfoCompat)paramList.next();
      if (localShortcutInfoCompat.isExcludedFromSurfaces(paramInt)) {
        localArrayList.remove(localShortcutInfoCompat);
      }
    }
    return localArrayList;
  }
  
  public static void reportShortcutUsed(Context paramContext, String paramString)
  {
    Preconditions.checkNotNull(paramContext);
    Preconditions.checkNotNull(paramString);
    if (Build.VERSION.SDK_INT >= 25) {
      ((ShortcutManager)paramContext.getSystemService(ShortcutManager.class)).reportShortcutUsed(paramString);
    }
    paramContext = getShortcutInfoListeners(paramContext).iterator();
    while (paramContext.hasNext()) {
      ((ShortcutInfoChangeListener)paramContext.next()).onShortcutUsageReported(Collections.singletonList(paramString));
    }
  }
  
  public static boolean requestPinShortcut(Context paramContext, ShortcutInfoCompat paramShortcutInfoCompat, IntentSender paramIntentSender)
  {
    if ((Build.VERSION.SDK_INT <= 31) && (paramShortcutInfoCompat.isExcludedFromSurfaces(1))) {
      return false;
    }
    if (Build.VERSION.SDK_INT >= 26) {
      return ((ShortcutManager)paramContext.getSystemService(ShortcutManager.class)).requestPinShortcut(paramShortcutInfoCompat.toShortcutInfo(), paramIntentSender);
    }
    if (!isRequestPinShortcutSupported(paramContext)) {
      return false;
    }
    paramShortcutInfoCompat = paramShortcutInfoCompat.addToIntent(new Intent("com.android.launcher.action.INSTALL_SHORTCUT"));
    if (paramIntentSender == null)
    {
      paramContext.sendBroadcast(paramShortcutInfoCompat);
      return true;
    }
    paramContext.sendOrderedBroadcast(paramShortcutInfoCompat, null, new BroadcastReceiver()
    {
      public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
      {
        try
        {
          ShortcutManagerCompat.this.sendIntent(paramAnonymousContext, 0, null, null, null);
        }
        catch (IntentSender.SendIntentException paramAnonymousContext) {}
      }
    }, null, -1, null, null);
    return true;
  }
  
  public static boolean setDynamicShortcuts(Context paramContext, List<ShortcutInfoCompat> paramList)
  {
    Preconditions.checkNotNull(paramContext);
    Preconditions.checkNotNull(paramList);
    List localList = removeShortcutsExcludedFromSurface(paramList, 1);
    Object localObject;
    if (Build.VERSION.SDK_INT >= 25)
    {
      ArrayList localArrayList = new ArrayList(localList.size());
      localObject = localList.iterator();
      while (((Iterator)localObject).hasNext()) {
        localArrayList.add(((ShortcutInfoCompat)((Iterator)localObject).next()).toShortcutInfo());
      }
      if (!((ShortcutManager)paramContext.getSystemService(ShortcutManager.class)).setDynamicShortcuts(localArrayList)) {
        return false;
      }
    }
    getShortcutInfoSaverInstance(paramContext).removeAllShortcuts();
    getShortcutInfoSaverInstance(paramContext).addShortcuts(localList);
    paramContext = getShortcutInfoListeners(paramContext).iterator();
    while (paramContext.hasNext())
    {
      localObject = (ShortcutInfoChangeListener)paramContext.next();
      ((ShortcutInfoChangeListener)localObject).onAllShortcutsRemoved();
      ((ShortcutInfoChangeListener)localObject).onShortcutAdded(paramList);
    }
    return true;
  }
  
  static void setShortcutInfoChangeListeners(List<ShortcutInfoChangeListener> paramList)
  {
    sShortcutInfoChangeListeners = paramList;
  }
  
  static void setShortcutInfoCompatSaver(ShortcutInfoCompatSaver<Void> paramShortcutInfoCompatSaver)
  {
    sShortcutInfoCompatSaver = paramShortcutInfoCompatSaver;
  }
  
  public static boolean updateShortcuts(Context paramContext, List<ShortcutInfoCompat> paramList)
  {
    List localList = removeShortcutsExcludedFromSurface(paramList, 1);
    if (Build.VERSION.SDK_INT <= 29) {
      convertUriIconsToBitmapIcons(paramContext, localList);
    }
    if (Build.VERSION.SDK_INT >= 25)
    {
      ArrayList localArrayList = new ArrayList();
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext()) {
        localArrayList.add(((ShortcutInfoCompat)localIterator.next()).toShortcutInfo());
      }
      if (!((ShortcutManager)paramContext.getSystemService(ShortcutManager.class)).updateShortcuts(localArrayList)) {
        return false;
      }
    }
    getShortcutInfoSaverInstance(paramContext).addShortcuts(localList);
    paramContext = getShortcutInfoListeners(paramContext).iterator();
    while (paramContext.hasNext()) {
      ((ShortcutInfoChangeListener)paramContext.next()).onShortcutUpdated(paramList);
    }
    return true;
  }
  
  private static class Api25Impl
  {
    static String getShortcutInfoWithLowestRank(List<ShortcutInfo> paramList)
    {
      int j = -1;
      ShortcutInfo localShortcutInfo = null;
      Iterator localIterator = paramList.iterator();
      paramList = localShortcutInfo;
      while (localIterator.hasNext())
      {
        localShortcutInfo = (ShortcutInfo)localIterator.next();
        int i = j;
        if (localShortcutInfo.getRank() > j)
        {
          paramList = localShortcutInfo.getId();
          i = localShortcutInfo.getRank();
        }
        j = i;
      }
      return paramList;
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface ShortcutMatchFlags {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/content/pm/ShortcutManagerCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */