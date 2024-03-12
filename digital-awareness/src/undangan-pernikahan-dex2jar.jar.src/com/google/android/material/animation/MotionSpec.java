package com.google.android.material.animation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;
import android.util.Property;
import androidx.collection.SimpleArrayMap;
import java.util.ArrayList;
import java.util.List;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class MotionSpec
{
  private static final String TAG = "MotionSpec";
  private final SimpleArrayMap<String, PropertyValuesHolder[]> propertyValues = new SimpleArrayMap();
  private final SimpleArrayMap<String, MotionTiming> timings = new SimpleArrayMap();
  
  private static void addInfoFromAnimator(MotionSpec paramMotionSpec, Animator paramAnimator)
  {
    if ((paramAnimator instanceof ObjectAnimator))
    {
      paramAnimator = (ObjectAnimator)paramAnimator;
      paramMotionSpec.setPropertyValues(paramAnimator.getPropertyName(), paramAnimator.getValues());
      paramMotionSpec.setTiming(paramAnimator.getPropertyName(), MotionTiming.createFromAnimator(paramAnimator));
      return;
    }
    throw new IllegalArgumentException("Animator must be an ObjectAnimator: " + paramAnimator);
  }
  
  private PropertyValuesHolder[] clonePropertyValuesHolder(PropertyValuesHolder[] paramArrayOfPropertyValuesHolder)
  {
    PropertyValuesHolder[] arrayOfPropertyValuesHolder = new PropertyValuesHolder[paramArrayOfPropertyValuesHolder.length];
    for (int i = 0; i < paramArrayOfPropertyValuesHolder.length; i++) {
      arrayOfPropertyValuesHolder[i] = paramArrayOfPropertyValuesHolder[i].clone();
    }
    return arrayOfPropertyValuesHolder;
  }
  
  public static MotionSpec createFromAttribute(Context paramContext, TypedArray paramTypedArray, int paramInt)
  {
    if (paramTypedArray.hasValue(paramInt))
    {
      paramInt = paramTypedArray.getResourceId(paramInt, 0);
      if (paramInt != 0) {
        return createFromResource(paramContext, paramInt);
      }
    }
    return null;
  }
  
  public static MotionSpec createFromResource(Context paramContext, int paramInt)
  {
    try
    {
      paramContext = AnimatorInflater.loadAnimator(paramContext, paramInt);
      if ((paramContext instanceof AnimatorSet)) {
        return createSpecFromAnimators(((AnimatorSet)paramContext).getChildAnimations());
      }
      if (paramContext != null)
      {
        localObject = new java/util/ArrayList;
        ((ArrayList)localObject).<init>();
        ((List)localObject).add(paramContext);
        paramContext = createSpecFromAnimators((List)localObject);
        return paramContext;
      }
      return null;
    }
    catch (Exception localException)
    {
      Object localObject = new StringBuilder().append("Can't load animation resource ID #0x");
      paramContext = Integer.toHexString(paramInt);
      Log5ECF72.a(paramContext);
      LogE84000.a(paramContext);
      Log229316.a(paramContext);
      Log.w("MotionSpec", paramContext, localException);
    }
    return null;
  }
  
  private static MotionSpec createSpecFromAnimators(List<Animator> paramList)
  {
    MotionSpec localMotionSpec = new MotionSpec();
    int i = 0;
    int j = paramList.size();
    while (i < j)
    {
      addInfoFromAnimator(localMotionSpec, (Animator)paramList.get(i));
      i++;
    }
    return localMotionSpec;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof MotionSpec)) {
      return false;
    }
    paramObject = (MotionSpec)paramObject;
    return this.timings.equals(((MotionSpec)paramObject).timings);
  }
  
  public <T> ObjectAnimator getAnimator(String paramString, T paramT, Property<T, ?> paramProperty)
  {
    paramT = ObjectAnimator.ofPropertyValuesHolder(paramT, getPropertyValues(paramString));
    paramT.setProperty(paramProperty);
    getTiming(paramString).apply(paramT);
    return paramT;
  }
  
  public PropertyValuesHolder[] getPropertyValues(String paramString)
  {
    if (hasPropertyValues(paramString)) {
      return clonePropertyValuesHolder((PropertyValuesHolder[])this.propertyValues.get(paramString));
    }
    throw new IllegalArgumentException();
  }
  
  public MotionTiming getTiming(String paramString)
  {
    if (hasTiming(paramString)) {
      return (MotionTiming)this.timings.get(paramString);
    }
    throw new IllegalArgumentException();
  }
  
  public long getTotalDuration()
  {
    long l = 0L;
    int i = 0;
    int j = this.timings.size();
    while (i < j)
    {
      MotionTiming localMotionTiming = (MotionTiming)this.timings.valueAt(i);
      l = Math.max(l, localMotionTiming.getDelay() + localMotionTiming.getDuration());
      i++;
    }
    return l;
  }
  
  public boolean hasPropertyValues(String paramString)
  {
    boolean bool;
    if (this.propertyValues.get(paramString) != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean hasTiming(String paramString)
  {
    boolean bool;
    if (this.timings.get(paramString) != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public int hashCode()
  {
    return this.timings.hashCode();
  }
  
  public void setPropertyValues(String paramString, PropertyValuesHolder[] paramArrayOfPropertyValuesHolder)
  {
    this.propertyValues.put(paramString, paramArrayOfPropertyValuesHolder);
  }
  
  public void setTiming(String paramString, MotionTiming paramMotionTiming)
  {
    this.timings.put(paramString, paramMotionTiming);
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append('\n');
    localStringBuilder.append(getClass().getName());
    localStringBuilder.append('{');
    String str = Integer.toHexString(System.identityHashCode(this));
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    localStringBuilder.append(str);
    localStringBuilder.append(" timings: ");
    localStringBuilder.append(this.timings);
    localStringBuilder.append("}\n");
    return localStringBuilder.toString();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/animation/MotionSpec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */