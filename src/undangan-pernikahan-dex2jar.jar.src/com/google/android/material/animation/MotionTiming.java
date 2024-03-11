package com.google.android.material.animation;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class MotionTiming
{
  private long delay = 0L;
  private long duration = 300L;
  private TimeInterpolator interpolator = null;
  private int repeatCount = 0;
  private int repeatMode = 1;
  
  public MotionTiming(long paramLong1, long paramLong2)
  {
    this.delay = paramLong1;
    this.duration = paramLong2;
  }
  
  public MotionTiming(long paramLong1, long paramLong2, TimeInterpolator paramTimeInterpolator)
  {
    this.delay = paramLong1;
    this.duration = paramLong2;
    this.interpolator = paramTimeInterpolator;
  }
  
  static MotionTiming createFromAnimator(ValueAnimator paramValueAnimator)
  {
    MotionTiming localMotionTiming = new MotionTiming(paramValueAnimator.getStartDelay(), paramValueAnimator.getDuration(), getInterpolatorCompat(paramValueAnimator));
    localMotionTiming.repeatCount = paramValueAnimator.getRepeatCount();
    localMotionTiming.repeatMode = paramValueAnimator.getRepeatMode();
    return localMotionTiming;
  }
  
  private static TimeInterpolator getInterpolatorCompat(ValueAnimator paramValueAnimator)
  {
    paramValueAnimator = paramValueAnimator.getInterpolator();
    if ((!(paramValueAnimator instanceof AccelerateDecelerateInterpolator)) && (paramValueAnimator != null))
    {
      if ((paramValueAnimator instanceof AccelerateInterpolator)) {
        return AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR;
      }
      if ((paramValueAnimator instanceof DecelerateInterpolator)) {
        return AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR;
      }
      return paramValueAnimator;
    }
    return AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR;
  }
  
  public void apply(Animator paramAnimator)
  {
    paramAnimator.setStartDelay(getDelay());
    paramAnimator.setDuration(getDuration());
    paramAnimator.setInterpolator(getInterpolator());
    if ((paramAnimator instanceof ValueAnimator))
    {
      ((ValueAnimator)paramAnimator).setRepeatCount(getRepeatCount());
      ((ValueAnimator)paramAnimator).setRepeatMode(getRepeatMode());
    }
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof MotionTiming)) {
      return false;
    }
    paramObject = (MotionTiming)paramObject;
    if (getDelay() != ((MotionTiming)paramObject).getDelay()) {
      return false;
    }
    if (getDuration() != ((MotionTiming)paramObject).getDuration()) {
      return false;
    }
    if (getRepeatCount() != ((MotionTiming)paramObject).getRepeatCount()) {
      return false;
    }
    if (getRepeatMode() != ((MotionTiming)paramObject).getRepeatMode()) {
      return false;
    }
    return getInterpolator().getClass().equals(((MotionTiming)paramObject).getInterpolator().getClass());
  }
  
  public long getDelay()
  {
    return this.delay;
  }
  
  public long getDuration()
  {
    return this.duration;
  }
  
  public TimeInterpolator getInterpolator()
  {
    TimeInterpolator localTimeInterpolator = this.interpolator;
    if (localTimeInterpolator == null) {
      localTimeInterpolator = AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR;
    }
    return localTimeInterpolator;
  }
  
  public int getRepeatCount()
  {
    return this.repeatCount;
  }
  
  public int getRepeatMode()
  {
    return this.repeatMode;
  }
  
  public int hashCode()
  {
    return ((((int)(getDelay() ^ getDelay() >>> 32) * 31 + (int)(getDuration() ^ getDuration() >>> 32)) * 31 + getInterpolator().getClass().hashCode()) * 31 + getRepeatCount()) * 31 + getRepeatMode();
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
    localStringBuilder.append(" delay: ");
    localStringBuilder.append(getDelay());
    localStringBuilder.append(" duration: ");
    localStringBuilder.append(getDuration());
    localStringBuilder.append(" interpolator: ");
    localStringBuilder.append(getInterpolator().getClass());
    localStringBuilder.append(" repeatCount: ");
    localStringBuilder.append(getRepeatCount());
    localStringBuilder.append(" repeatMode: ");
    localStringBuilder.append(getRepeatMode());
    localStringBuilder.append("}\n");
    return localStringBuilder.toString();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/animation/MotionTiming.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */