package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.XmlResourceParser;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.graphics.drawable.LayerDrawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.util.Xml;
import androidx.appcompat.graphics.drawable.AnimatedStateListDrawableCompat;
import androidx.appcompat.resources.Compatibility.Api21Impl;
import androidx.appcompat.resources.R.drawable;
import androidx.collection.LongSparseArray;
import androidx.collection.LruCache;
import androidx.collection.SimpleArrayMap;
import androidx.collection.SparseArrayCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.util.WeakHashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class ResourceManagerInternal
{
  private static final ColorFilterLruCache COLOR_FILTER_CACHE = new ColorFilterLruCache(6);
  private static final boolean DEBUG = false;
  private static final PorterDuff.Mode DEFAULT_MODE = PorterDuff.Mode.SRC_IN;
  private static ResourceManagerInternal INSTANCE;
  private static final String PLATFORM_VD_CLAZZ = "android.graphics.drawable.VectorDrawable";
  private static final String SKIP_DRAWABLE_TAG = "appcompat_skip_skip";
  private static final String TAG = "ResourceManagerInternal";
  private SimpleArrayMap<String, InflateDelegate> mDelegates;
  private final WeakHashMap<Context, LongSparseArray<WeakReference<Drawable.ConstantState>>> mDrawableCaches = new WeakHashMap(0);
  private boolean mHasCheckedVectorDrawableSetup;
  private ResourceManagerHooks mHooks;
  private SparseArrayCompat<String> mKnownDrawableIdTags;
  private WeakHashMap<Context, SparseArrayCompat<ColorStateList>> mTintLists;
  private TypedValue mTypedValue;
  
  private void addDelegate(String paramString, InflateDelegate paramInflateDelegate)
  {
    if (this.mDelegates == null) {
      this.mDelegates = new SimpleArrayMap();
    }
    this.mDelegates.put(paramString, paramInflateDelegate);
  }
  
  private boolean addDrawableToCache(Context paramContext, long paramLong, Drawable paramDrawable)
  {
    try
    {
      Drawable.ConstantState localConstantState = paramDrawable.getConstantState();
      if (localConstantState != null)
      {
        LongSparseArray localLongSparseArray = (LongSparseArray)this.mDrawableCaches.get(paramContext);
        paramDrawable = localLongSparseArray;
        if (localLongSparseArray == null)
        {
          paramDrawable = new androidx/collection/LongSparseArray;
          paramDrawable.<init>();
          this.mDrawableCaches.put(paramContext, paramDrawable);
        }
        paramContext = new java/lang/ref/WeakReference;
        paramContext.<init>(localConstantState);
        paramDrawable.put(paramLong, paramContext);
        return true;
      }
      return false;
    }
    finally {}
  }
  
  private void addTintListToCache(Context paramContext, int paramInt, ColorStateList paramColorStateList)
  {
    if (this.mTintLists == null) {
      this.mTintLists = new WeakHashMap();
    }
    SparseArrayCompat localSparseArrayCompat2 = (SparseArrayCompat)this.mTintLists.get(paramContext);
    SparseArrayCompat localSparseArrayCompat1 = localSparseArrayCompat2;
    if (localSparseArrayCompat2 == null)
    {
      localSparseArrayCompat1 = new SparseArrayCompat();
      this.mTintLists.put(paramContext, localSparseArrayCompat1);
    }
    localSparseArrayCompat1.append(paramInt, paramColorStateList);
  }
  
  private void checkVectorDrawableSetup(Context paramContext)
  {
    if (this.mHasCheckedVectorDrawableSetup) {
      return;
    }
    this.mHasCheckedVectorDrawableSetup = true;
    paramContext = getDrawable(paramContext, R.drawable.abc_vector_test);
    if ((paramContext != null) && (isVectorDrawable(paramContext))) {
      return;
    }
    this.mHasCheckedVectorDrawableSetup = false;
    throw new IllegalStateException("This app has been built with an incorrect configuration. Please configure your build for VectorDrawableCompat.");
  }
  
  private static long createCacheKey(TypedValue paramTypedValue)
  {
    return paramTypedValue.assetCookie << 32 | paramTypedValue.data;
  }
  
  private Drawable createDrawableIfNeeded(Context paramContext, int paramInt)
  {
    if (this.mTypedValue == null) {
      this.mTypedValue = new TypedValue();
    }
    TypedValue localTypedValue = this.mTypedValue;
    paramContext.getResources().getValue(paramInt, localTypedValue, true);
    long l = createCacheKey(localTypedValue);
    Object localObject = getCachedDrawable(paramContext, l);
    if (localObject != null) {
      return (Drawable)localObject;
    }
    localObject = this.mHooks;
    if (localObject == null) {
      localObject = null;
    } else {
      localObject = ((ResourceManagerHooks)localObject).createDrawableFor(this, paramContext, paramInt);
    }
    if (localObject != null)
    {
      ((Drawable)localObject).setChangingConfigurations(localTypedValue.changingConfigurations);
      addDrawableToCache(paramContext, l, (Drawable)localObject);
    }
    return (Drawable)localObject;
  }
  
  private static PorterDuffColorFilter createTintFilter(ColorStateList paramColorStateList, PorterDuff.Mode paramMode, int[] paramArrayOfInt)
  {
    if ((paramColorStateList != null) && (paramMode != null)) {
      return getPorterDuffColorFilter(paramColorStateList.getColorForState(paramArrayOfInt, 0), paramMode);
    }
    return null;
  }
  
  public static ResourceManagerInternal get()
  {
    try
    {
      if (INSTANCE == null)
      {
        localResourceManagerInternal = new androidx/appcompat/widget/ResourceManagerInternal;
        localResourceManagerInternal.<init>();
        INSTANCE = localResourceManagerInternal;
        installDefaultInflateDelegates(localResourceManagerInternal);
      }
      ResourceManagerInternal localResourceManagerInternal = INSTANCE;
      return localResourceManagerInternal;
    }
    finally {}
  }
  
  private Drawable getCachedDrawable(Context paramContext, long paramLong)
  {
    try
    {
      LongSparseArray localLongSparseArray = (LongSparseArray)this.mDrawableCaches.get(paramContext);
      if (localLongSparseArray == null) {
        return null;
      }
      Object localObject = (WeakReference)localLongSparseArray.get(paramLong);
      if (localObject != null)
      {
        localObject = (Drawable.ConstantState)((WeakReference)localObject).get();
        if (localObject != null)
        {
          paramContext = ((Drawable.ConstantState)localObject).newDrawable(paramContext.getResources());
          return paramContext;
        }
        localLongSparseArray.remove(paramLong);
      }
      return null;
    }
    finally {}
  }
  
  public static PorterDuffColorFilter getPorterDuffColorFilter(int paramInt, PorterDuff.Mode paramMode)
  {
    try
    {
      ColorFilterLruCache localColorFilterLruCache = COLOR_FILTER_CACHE;
      PorterDuffColorFilter localPorterDuffColorFilter2 = localColorFilterLruCache.get(paramInt, paramMode);
      PorterDuffColorFilter localPorterDuffColorFilter1 = localPorterDuffColorFilter2;
      if (localPorterDuffColorFilter2 == null)
      {
        localPorterDuffColorFilter1 = new android/graphics/PorterDuffColorFilter;
        localPorterDuffColorFilter1.<init>(paramInt, paramMode);
        localColorFilterLruCache.put(paramInt, paramMode, localPorterDuffColorFilter1);
      }
      return localPorterDuffColorFilter1;
    }
    finally {}
  }
  
  private ColorStateList getTintListFromCache(Context paramContext, int paramInt)
  {
    Object localObject2 = this.mTintLists;
    Object localObject1 = null;
    if (localObject2 != null)
    {
      localObject2 = (SparseArrayCompat)((WeakHashMap)localObject2).get(paramContext);
      paramContext = (Context)localObject1;
      if (localObject2 != null) {
        paramContext = (ColorStateList)((SparseArrayCompat)localObject2).get(paramInt);
      }
      return paramContext;
    }
    return null;
  }
  
  private static void installDefaultInflateDelegates(ResourceManagerInternal paramResourceManagerInternal)
  {
    if (Build.VERSION.SDK_INT < 24)
    {
      paramResourceManagerInternal.addDelegate("vector", new VdcInflateDelegate());
      paramResourceManagerInternal.addDelegate("animated-vector", new AvdcInflateDelegate());
      paramResourceManagerInternal.addDelegate("animated-selector", new AsldcInflateDelegate());
      paramResourceManagerInternal.addDelegate("drawable", new DrawableDelegate());
    }
  }
  
  private static boolean isVectorDrawable(Drawable paramDrawable)
  {
    boolean bool;
    if ((!(paramDrawable instanceof VectorDrawableCompat)) && (!"android.graphics.drawable.VectorDrawable".equals(paramDrawable.getClass().getName()))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  private Drawable loadDrawableFromDelegates(Context paramContext, int paramInt)
  {
    Object localObject1 = this.mDelegates;
    if ((localObject1 != null) && (!((SimpleArrayMap)localObject1).isEmpty()))
    {
      localObject1 = this.mKnownDrawableIdTags;
      if (localObject1 != null)
      {
        localObject1 = (String)((SparseArrayCompat)localObject1).get(paramInt);
        if ((!"appcompat_skip_skip".equals(localObject1)) && ((localObject1 == null) || (this.mDelegates.get(localObject1) != null))) {
          break label87;
        }
        return null;
      }
      else
      {
        this.mKnownDrawableIdTags = new SparseArrayCompat();
      }
      label87:
      if (this.mTypedValue == null) {
        this.mTypedValue = new TypedValue();
      }
      TypedValue localTypedValue = this.mTypedValue;
      Object localObject3 = paramContext.getResources();
      ((Resources)localObject3).getValue(paramInt, localTypedValue, true);
      long l = createCacheKey(localTypedValue);
      Drawable localDrawable = getCachedDrawable(paramContext, l);
      if (localDrawable != null) {
        return localDrawable;
      }
      localObject1 = localDrawable;
      if (localTypedValue.string != null)
      {
        localObject1 = localDrawable;
        if (localTypedValue.string.toString().endsWith(".xml"))
        {
          Object localObject2 = localDrawable;
          try
          {
            XmlResourceParser localXmlResourceParser = ((Resources)localObject3).getXml(paramInt);
            localObject2 = localDrawable;
            localObject3 = Xml.asAttributeSet(localXmlResourceParser);
            int i;
            do
            {
              localObject2 = localDrawable;
              i = localXmlResourceParser.next();
            } while ((i != 2) && (i != 1));
            if (i == 2)
            {
              localObject2 = localDrawable;
              localObject1 = localXmlResourceParser.getName();
              localObject2 = localDrawable;
              this.mKnownDrawableIdTags.append(paramInt, localObject1);
              localObject2 = localDrawable;
              InflateDelegate localInflateDelegate = (InflateDelegate)this.mDelegates.get(localObject1);
              localObject1 = localDrawable;
              if (localInflateDelegate != null)
              {
                localObject2 = localDrawable;
                localObject1 = localInflateDelegate.createFromXmlInner(paramContext, localXmlResourceParser, (AttributeSet)localObject3, paramContext.getTheme());
              }
              if (localObject1 != null)
              {
                localObject2 = localObject1;
                ((Drawable)localObject1).setChangingConfigurations(localTypedValue.changingConfigurations);
                localObject2 = localObject1;
                addDrawableToCache(paramContext, l, (Drawable)localObject1);
              }
            }
            else
            {
              localObject2 = localDrawable;
              paramContext = new org/xmlpull/v1/XmlPullParserException;
              localObject2 = localDrawable;
              paramContext.<init>("No start tag found");
              localObject2 = localDrawable;
              throw paramContext;
            }
          }
          catch (Exception paramContext)
          {
            Log.e("ResourceManagerInternal", "Exception while inflating drawable", paramContext);
            localObject1 = localObject2;
          }
        }
      }
      if (localObject1 == null) {
        this.mKnownDrawableIdTags.append(paramInt, "appcompat_skip_skip");
      }
      return (Drawable)localObject1;
    }
    return null;
  }
  
  private Drawable tintDrawable(Context paramContext, int paramInt, boolean paramBoolean, Drawable paramDrawable)
  {
    Object localObject = getTintList(paramContext, paramInt);
    if (localObject != null)
    {
      paramContext = paramDrawable;
      if (DrawableUtils.canSafelyMutateDrawable(paramDrawable)) {
        paramContext = paramDrawable.mutate();
      }
      paramContext = DrawableCompat.wrap(paramContext);
      DrawableCompat.setTintList(paramContext, (ColorStateList)localObject);
      localObject = getTintMode(paramInt);
      paramDrawable = paramContext;
      if (localObject != null)
      {
        DrawableCompat.setTintMode(paramContext, (PorterDuff.Mode)localObject);
        paramDrawable = paramContext;
      }
    }
    do
    {
      localObject = paramDrawable;
      break;
      localObject = this.mHooks;
    } while ((localObject != null) && (((ResourceManagerHooks)localObject).tintDrawable(paramContext, paramInt, paramDrawable)));
    localObject = paramDrawable;
    if (!tintDrawableUsingColorFilter(paramContext, paramInt, paramDrawable))
    {
      localObject = paramDrawable;
      if (paramBoolean) {
        localObject = null;
      }
    }
    return (Drawable)localObject;
  }
  
  static void tintDrawable(Drawable paramDrawable, TintInfo paramTintInfo, int[] paramArrayOfInt)
  {
    Object localObject = paramDrawable.getState();
    if (DrawableUtils.canSafelyMutateDrawable(paramDrawable))
    {
      int i;
      if (paramDrawable.mutate() == paramDrawable) {
        i = 1;
      } else {
        i = 0;
      }
      if (i == 0)
      {
        Log.d("ResourceManagerInternal", "Mutated drawable is not the same instance as the input.");
        return;
      }
    }
    if (((paramDrawable instanceof LayerDrawable)) && (paramDrawable.isStateful()))
    {
      paramDrawable.setState(new int[0]);
      paramDrawable.setState((int[])localObject);
    }
    if ((!paramTintInfo.mHasTintList) && (!paramTintInfo.mHasTintMode))
    {
      paramDrawable.clearColorFilter();
    }
    else
    {
      if (paramTintInfo.mHasTintList) {
        localObject = paramTintInfo.mTintList;
      } else {
        localObject = null;
      }
      if (paramTintInfo.mHasTintMode) {
        paramTintInfo = paramTintInfo.mTintMode;
      } else {
        paramTintInfo = DEFAULT_MODE;
      }
      paramDrawable.setColorFilter(createTintFilter((ColorStateList)localObject, paramTintInfo, paramArrayOfInt));
    }
    if (Build.VERSION.SDK_INT <= 23) {
      paramDrawable.invalidateSelf();
    }
  }
  
  public Drawable getDrawable(Context paramContext, int paramInt)
  {
    try
    {
      paramContext = getDrawable(paramContext, paramInt, false);
      return paramContext;
    }
    finally
    {
      paramContext = finally;
      throw paramContext;
    }
  }
  
  Drawable getDrawable(Context paramContext, int paramInt, boolean paramBoolean)
  {
    try
    {
      checkVectorDrawableSetup(paramContext);
      Object localObject1 = loadDrawableFromDelegates(paramContext, paramInt);
      Object localObject2 = localObject1;
      if (localObject1 == null) {
        localObject2 = createDrawableIfNeeded(paramContext, paramInt);
      }
      localObject1 = localObject2;
      if (localObject2 == null) {
        localObject1 = ContextCompat.getDrawable(paramContext, paramInt);
      }
      localObject2 = localObject1;
      if (localObject1 != null) {
        localObject2 = tintDrawable(paramContext, paramInt, paramBoolean, (Drawable)localObject1);
      }
      if (localObject2 != null) {
        DrawableUtils.fixDrawable((Drawable)localObject2);
      }
      return (Drawable)localObject2;
    }
    finally {}
  }
  
  ColorStateList getTintList(Context paramContext, int paramInt)
  {
    try
    {
      Object localObject2 = getTintListFromCache(paramContext, paramInt);
      Object localObject1 = localObject2;
      if (localObject2 == null)
      {
        localObject1 = this.mHooks;
        if (localObject1 == null) {
          localObject1 = null;
        } else {
          localObject1 = ((ResourceManagerHooks)localObject1).getTintListForDrawableRes(paramContext, paramInt);
        }
        localObject2 = localObject1;
        localObject1 = localObject2;
        if (localObject2 != null)
        {
          addTintListToCache(paramContext, paramInt, (ColorStateList)localObject2);
          localObject1 = localObject2;
        }
      }
      return (ColorStateList)localObject1;
    }
    finally {}
  }
  
  PorterDuff.Mode getTintMode(int paramInt)
  {
    Object localObject = this.mHooks;
    if (localObject == null) {
      localObject = null;
    } else {
      localObject = ((ResourceManagerHooks)localObject).getTintModeForDrawableRes(paramInt);
    }
    return (PorterDuff.Mode)localObject;
  }
  
  public void onConfigurationChanged(Context paramContext)
  {
    try
    {
      paramContext = (LongSparseArray)this.mDrawableCaches.get(paramContext);
      if (paramContext != null) {
        paramContext.clear();
      }
      return;
    }
    finally {}
  }
  
  Drawable onDrawableLoadedFromResources(Context paramContext, VectorEnabledTintResources paramVectorEnabledTintResources, int paramInt)
  {
    try
    {
      Drawable localDrawable2 = loadDrawableFromDelegates(paramContext, paramInt);
      Drawable localDrawable1 = localDrawable2;
      if (localDrawable2 == null) {
        localDrawable1 = paramVectorEnabledTintResources.getDrawableCanonical(paramInt);
      }
      if (localDrawable1 != null)
      {
        paramContext = tintDrawable(paramContext, paramInt, false, localDrawable1);
        return paramContext;
      }
      return null;
    }
    finally {}
  }
  
  public void setHooks(ResourceManagerHooks paramResourceManagerHooks)
  {
    try
    {
      this.mHooks = paramResourceManagerHooks;
      return;
    }
    finally
    {
      paramResourceManagerHooks = finally;
      throw paramResourceManagerHooks;
    }
  }
  
  boolean tintDrawableUsingColorFilter(Context paramContext, int paramInt, Drawable paramDrawable)
  {
    ResourceManagerHooks localResourceManagerHooks = this.mHooks;
    boolean bool;
    if ((localResourceManagerHooks != null) && (localResourceManagerHooks.tintDrawableUsingColorFilter(paramContext, paramInt, paramDrawable))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  static class AsldcInflateDelegate
    implements ResourceManagerInternal.InflateDelegate
  {
    public Drawable createFromXmlInner(Context paramContext, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, Resources.Theme paramTheme)
    {
      try
      {
        paramContext = AnimatedStateListDrawableCompat.createFromXmlInner(paramContext, paramContext.getResources(), paramXmlPullParser, paramAttributeSet, paramTheme);
        return paramContext;
      }
      catch (Exception paramContext)
      {
        Log.e("AsldcInflateDelegate", "Exception while inflating <animated-selector>", paramContext);
      }
      return null;
    }
  }
  
  private static class AvdcInflateDelegate
    implements ResourceManagerInternal.InflateDelegate
  {
    public Drawable createFromXmlInner(Context paramContext, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, Resources.Theme paramTheme)
    {
      try
      {
        paramContext = AnimatedVectorDrawableCompat.createFromXmlInner(paramContext, paramContext.getResources(), paramXmlPullParser, paramAttributeSet, paramTheme);
        return paramContext;
      }
      catch (Exception paramContext)
      {
        Log.e("AvdcInflateDelegate", "Exception while inflating <animated-vector>", paramContext);
      }
      return null;
    }
  }
  
  private static class ColorFilterLruCache
    extends LruCache<Integer, PorterDuffColorFilter>
  {
    public ColorFilterLruCache(int paramInt)
    {
      super();
    }
    
    private static int generateCacheKey(int paramInt, PorterDuff.Mode paramMode)
    {
      return (1 * 31 + paramInt) * 31 + paramMode.hashCode();
    }
    
    PorterDuffColorFilter get(int paramInt, PorterDuff.Mode paramMode)
    {
      return (PorterDuffColorFilter)get(Integer.valueOf(generateCacheKey(paramInt, paramMode)));
    }
    
    PorterDuffColorFilter put(int paramInt, PorterDuff.Mode paramMode, PorterDuffColorFilter paramPorterDuffColorFilter)
    {
      return (PorterDuffColorFilter)put(Integer.valueOf(generateCacheKey(paramInt, paramMode)), paramPorterDuffColorFilter);
    }
  }
  
  static class DrawableDelegate
    implements ResourceManagerInternal.InflateDelegate
  {
    public Drawable createFromXmlInner(Context paramContext, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, Resources.Theme paramTheme)
    {
      Object localObject = paramAttributeSet.getClassAttribute();
      if (localObject != null) {
        try
        {
          localObject = (Drawable)DrawableDelegate.class.getClassLoader().loadClass((String)localObject).asSubclass(Drawable.class).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
          if (Build.VERSION.SDK_INT >= 21) {
            Compatibility.Api21Impl.inflate((Drawable)localObject, paramContext.getResources(), paramXmlPullParser, paramAttributeSet, paramTheme);
          } else {
            ((Drawable)localObject).inflate(paramContext.getResources(), paramXmlPullParser, paramAttributeSet);
          }
          return (Drawable)localObject;
        }
        catch (Exception paramContext)
        {
          Log.e("DrawableDelegate", "Exception while inflating <drawable>", paramContext);
          return null;
        }
      }
      return null;
    }
  }
  
  private static abstract interface InflateDelegate
  {
    public abstract Drawable createFromXmlInner(Context paramContext, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, Resources.Theme paramTheme);
  }
  
  public static abstract interface ResourceManagerHooks
  {
    public abstract Drawable createDrawableFor(ResourceManagerInternal paramResourceManagerInternal, Context paramContext, int paramInt);
    
    public abstract ColorStateList getTintListForDrawableRes(Context paramContext, int paramInt);
    
    public abstract PorterDuff.Mode getTintModeForDrawableRes(int paramInt);
    
    public abstract boolean tintDrawable(Context paramContext, int paramInt, Drawable paramDrawable);
    
    public abstract boolean tintDrawableUsingColorFilter(Context paramContext, int paramInt, Drawable paramDrawable);
  }
  
  private static class VdcInflateDelegate
    implements ResourceManagerInternal.InflateDelegate
  {
    public Drawable createFromXmlInner(Context paramContext, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, Resources.Theme paramTheme)
    {
      try
      {
        paramContext = VectorDrawableCompat.createFromXmlInner(paramContext.getResources(), paramXmlPullParser, paramAttributeSet, paramTheme);
        return paramContext;
      }
      catch (Exception paramContext)
      {
        Log.e("VdcInflateDelegate", "Exception while inflating <vector>", paramContext);
      }
      return null;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/appcompat/widget/ResourceManagerInternal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */