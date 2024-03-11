package androidx.transition;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.content.res.TypedArrayUtils;
import java.util.ArrayList;
import java.util.Iterator;

public class TransitionSet
  extends Transition
{
  private static final int FLAG_CHANGE_EPICENTER = 8;
  private static final int FLAG_CHANGE_INTERPOLATOR = 1;
  private static final int FLAG_CHANGE_PATH_MOTION = 4;
  private static final int FLAG_CHANGE_PROPAGATION = 2;
  public static final int ORDERING_SEQUENTIAL = 1;
  public static final int ORDERING_TOGETHER = 0;
  private int mChangeFlags = 0;
  int mCurrentListeners;
  private boolean mPlayTogether = true;
  boolean mStarted = false;
  private ArrayList<Transition> mTransitions = new ArrayList();
  
  public TransitionSet() {}
  
  public TransitionSet(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, Styleable.TRANSITION_SET);
    setOrdering(TypedArrayUtils.getNamedInt(paramContext, (XmlResourceParser)paramAttributeSet, "transitionOrdering", 0, 0));
    paramContext.recycle();
  }
  
  private void addTransitionInternal(Transition paramTransition)
  {
    this.mTransitions.add(paramTransition);
    paramTransition.mParent = this;
  }
  
  private void setupStartEndListeners()
  {
    TransitionSetListener localTransitionSetListener = new TransitionSetListener(this);
    Iterator localIterator = this.mTransitions.iterator();
    while (localIterator.hasNext()) {
      ((Transition)localIterator.next()).addListener(localTransitionSetListener);
    }
    this.mCurrentListeners = this.mTransitions.size();
  }
  
  public TransitionSet addListener(Transition.TransitionListener paramTransitionListener)
  {
    return (TransitionSet)super.addListener(paramTransitionListener);
  }
  
  public TransitionSet addTarget(int paramInt)
  {
    for (int i = 0; i < this.mTransitions.size(); i++) {
      ((Transition)this.mTransitions.get(i)).addTarget(paramInt);
    }
    return (TransitionSet)super.addTarget(paramInt);
  }
  
  public TransitionSet addTarget(View paramView)
  {
    for (int i = 0; i < this.mTransitions.size(); i++) {
      ((Transition)this.mTransitions.get(i)).addTarget(paramView);
    }
    return (TransitionSet)super.addTarget(paramView);
  }
  
  public TransitionSet addTarget(Class<?> paramClass)
  {
    for (int i = 0; i < this.mTransitions.size(); i++) {
      ((Transition)this.mTransitions.get(i)).addTarget(paramClass);
    }
    return (TransitionSet)super.addTarget(paramClass);
  }
  
  public TransitionSet addTarget(String paramString)
  {
    for (int i = 0; i < this.mTransitions.size(); i++) {
      ((Transition)this.mTransitions.get(i)).addTarget(paramString);
    }
    return (TransitionSet)super.addTarget(paramString);
  }
  
  public TransitionSet addTransition(Transition paramTransition)
  {
    addTransitionInternal(paramTransition);
    if (this.mDuration >= 0L) {
      paramTransition.setDuration(this.mDuration);
    }
    if ((this.mChangeFlags & 0x1) != 0) {
      paramTransition.setInterpolator(getInterpolator());
    }
    if ((this.mChangeFlags & 0x2) != 0) {
      paramTransition.setPropagation(getPropagation());
    }
    if ((this.mChangeFlags & 0x4) != 0) {
      paramTransition.setPathMotion(getPathMotion());
    }
    if ((this.mChangeFlags & 0x8) != 0) {
      paramTransition.setEpicenterCallback(getEpicenterCallback());
    }
    return this;
  }
  
  protected void cancel()
  {
    super.cancel();
    int j = this.mTransitions.size();
    for (int i = 0; i < j; i++) {
      ((Transition)this.mTransitions.get(i)).cancel();
    }
  }
  
  public void captureEndValues(TransitionValues paramTransitionValues)
  {
    if (isValidTarget(paramTransitionValues.view))
    {
      Iterator localIterator = this.mTransitions.iterator();
      while (localIterator.hasNext())
      {
        Transition localTransition = (Transition)localIterator.next();
        if (localTransition.isValidTarget(paramTransitionValues.view))
        {
          localTransition.captureEndValues(paramTransitionValues);
          paramTransitionValues.mTargetedTransitions.add(localTransition);
        }
      }
    }
  }
  
  void capturePropagationValues(TransitionValues paramTransitionValues)
  {
    super.capturePropagationValues(paramTransitionValues);
    int j = this.mTransitions.size();
    for (int i = 0; i < j; i++) {
      ((Transition)this.mTransitions.get(i)).capturePropagationValues(paramTransitionValues);
    }
  }
  
  public void captureStartValues(TransitionValues paramTransitionValues)
  {
    if (isValidTarget(paramTransitionValues.view))
    {
      Iterator localIterator = this.mTransitions.iterator();
      while (localIterator.hasNext())
      {
        Transition localTransition = (Transition)localIterator.next();
        if (localTransition.isValidTarget(paramTransitionValues.view))
        {
          localTransition.captureStartValues(paramTransitionValues);
          paramTransitionValues.mTargetedTransitions.add(localTransition);
        }
      }
    }
  }
  
  public Transition clone()
  {
    TransitionSet localTransitionSet = (TransitionSet)super.clone();
    localTransitionSet.mTransitions = new ArrayList();
    int j = this.mTransitions.size();
    for (int i = 0; i < j; i++) {
      localTransitionSet.addTransitionInternal(((Transition)this.mTransitions.get(i)).clone());
    }
    return localTransitionSet;
  }
  
  protected void createAnimators(ViewGroup paramViewGroup, TransitionValuesMaps paramTransitionValuesMaps1, TransitionValuesMaps paramTransitionValuesMaps2, ArrayList<TransitionValues> paramArrayList1, ArrayList<TransitionValues> paramArrayList2)
  {
    long l2 = getStartDelay();
    int j = this.mTransitions.size();
    for (int i = 0; i < j; i++)
    {
      Transition localTransition = (Transition)this.mTransitions.get(i);
      if ((l2 > 0L) && ((this.mPlayTogether) || (i == 0)))
      {
        long l1 = localTransition.getStartDelay();
        if (l1 > 0L) {
          localTransition.setStartDelay(l2 + l1);
        } else {
          localTransition.setStartDelay(l2);
        }
      }
      localTransition.createAnimators(paramViewGroup, paramTransitionValuesMaps1, paramTransitionValuesMaps2, paramArrayList1, paramArrayList2);
    }
  }
  
  public Transition excludeTarget(int paramInt, boolean paramBoolean)
  {
    for (int i = 0; i < this.mTransitions.size(); i++) {
      ((Transition)this.mTransitions.get(i)).excludeTarget(paramInt, paramBoolean);
    }
    return super.excludeTarget(paramInt, paramBoolean);
  }
  
  public Transition excludeTarget(View paramView, boolean paramBoolean)
  {
    for (int i = 0; i < this.mTransitions.size(); i++) {
      ((Transition)this.mTransitions.get(i)).excludeTarget(paramView, paramBoolean);
    }
    return super.excludeTarget(paramView, paramBoolean);
  }
  
  public Transition excludeTarget(Class<?> paramClass, boolean paramBoolean)
  {
    for (int i = 0; i < this.mTransitions.size(); i++) {
      ((Transition)this.mTransitions.get(i)).excludeTarget(paramClass, paramBoolean);
    }
    return super.excludeTarget(paramClass, paramBoolean);
  }
  
  public Transition excludeTarget(String paramString, boolean paramBoolean)
  {
    for (int i = 0; i < this.mTransitions.size(); i++) {
      ((Transition)this.mTransitions.get(i)).excludeTarget(paramString, paramBoolean);
    }
    return super.excludeTarget(paramString, paramBoolean);
  }
  
  void forceToEnd(ViewGroup paramViewGroup)
  {
    super.forceToEnd(paramViewGroup);
    int j = this.mTransitions.size();
    for (int i = 0; i < j; i++) {
      ((Transition)this.mTransitions.get(i)).forceToEnd(paramViewGroup);
    }
  }
  
  public int getOrdering()
  {
    return this.mPlayTogether ^ true;
  }
  
  public Transition getTransitionAt(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt < this.mTransitions.size())) {
      return (Transition)this.mTransitions.get(paramInt);
    }
    return null;
  }
  
  public int getTransitionCount()
  {
    return this.mTransitions.size();
  }
  
  public void pause(View paramView)
  {
    super.pause(paramView);
    int j = this.mTransitions.size();
    for (int i = 0; i < j; i++) {
      ((Transition)this.mTransitions.get(i)).pause(paramView);
    }
  }
  
  public TransitionSet removeListener(Transition.TransitionListener paramTransitionListener)
  {
    return (TransitionSet)super.removeListener(paramTransitionListener);
  }
  
  public TransitionSet removeTarget(int paramInt)
  {
    for (int i = 0; i < this.mTransitions.size(); i++) {
      ((Transition)this.mTransitions.get(i)).removeTarget(paramInt);
    }
    return (TransitionSet)super.removeTarget(paramInt);
  }
  
  public TransitionSet removeTarget(View paramView)
  {
    for (int i = 0; i < this.mTransitions.size(); i++) {
      ((Transition)this.mTransitions.get(i)).removeTarget(paramView);
    }
    return (TransitionSet)super.removeTarget(paramView);
  }
  
  public TransitionSet removeTarget(Class<?> paramClass)
  {
    for (int i = 0; i < this.mTransitions.size(); i++) {
      ((Transition)this.mTransitions.get(i)).removeTarget(paramClass);
    }
    return (TransitionSet)super.removeTarget(paramClass);
  }
  
  public TransitionSet removeTarget(String paramString)
  {
    for (int i = 0; i < this.mTransitions.size(); i++) {
      ((Transition)this.mTransitions.get(i)).removeTarget(paramString);
    }
    return (TransitionSet)super.removeTarget(paramString);
  }
  
  public TransitionSet removeTransition(Transition paramTransition)
  {
    this.mTransitions.remove(paramTransition);
    paramTransition.mParent = null;
    return this;
  }
  
  public void resume(View paramView)
  {
    super.resume(paramView);
    int j = this.mTransitions.size();
    for (int i = 0; i < j; i++) {
      ((Transition)this.mTransitions.get(i)).resume(paramView);
    }
  }
  
  protected void runAnimators()
  {
    if (this.mTransitions.isEmpty())
    {
      start();
      end();
      return;
    }
    setupStartEndListeners();
    Object localObject;
    if (!this.mPlayTogether)
    {
      for (int i = 1; i < this.mTransitions.size(); i++) {
        ((Transition)this.mTransitions.get(i - 1)).addListener(new TransitionListenerAdapter()
        {
          public void onTransitionEnd(Transition paramAnonymousTransition)
          {
            this.val$nextTransition.runAnimators();
            paramAnonymousTransition.removeListener(this);
          }
        });
      }
      localObject = (Transition)this.mTransitions.get(0);
      if (localObject != null) {
        ((Transition)localObject).runAnimators();
      }
    }
    else
    {
      localObject = this.mTransitions.iterator();
      while (((Iterator)localObject).hasNext()) {
        ((Transition)((Iterator)localObject).next()).runAnimators();
      }
    }
  }
  
  void setCanRemoveViews(boolean paramBoolean)
  {
    super.setCanRemoveViews(paramBoolean);
    int j = this.mTransitions.size();
    for (int i = 0; i < j; i++) {
      ((Transition)this.mTransitions.get(i)).setCanRemoveViews(paramBoolean);
    }
  }
  
  public TransitionSet setDuration(long paramLong)
  {
    super.setDuration(paramLong);
    if (this.mDuration >= 0L)
    {
      ArrayList localArrayList = this.mTransitions;
      if (localArrayList != null)
      {
        int j = localArrayList.size();
        for (int i = 0; i < j; i++) {
          ((Transition)this.mTransitions.get(i)).setDuration(paramLong);
        }
      }
    }
    return this;
  }
  
  public void setEpicenterCallback(Transition.EpicenterCallback paramEpicenterCallback)
  {
    super.setEpicenterCallback(paramEpicenterCallback);
    this.mChangeFlags |= 0x8;
    int j = this.mTransitions.size();
    for (int i = 0; i < j; i++) {
      ((Transition)this.mTransitions.get(i)).setEpicenterCallback(paramEpicenterCallback);
    }
  }
  
  public TransitionSet setInterpolator(TimeInterpolator paramTimeInterpolator)
  {
    this.mChangeFlags |= 0x1;
    ArrayList localArrayList = this.mTransitions;
    if (localArrayList != null)
    {
      int j = localArrayList.size();
      for (int i = 0; i < j; i++) {
        ((Transition)this.mTransitions.get(i)).setInterpolator(paramTimeInterpolator);
      }
    }
    return (TransitionSet)super.setInterpolator(paramTimeInterpolator);
  }
  
  public TransitionSet setOrdering(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      throw new AndroidRuntimeException("Invalid parameter for TransitionSet ordering: " + paramInt);
    case 1: 
      this.mPlayTogether = false;
      break;
    case 0: 
      this.mPlayTogether = true;
    }
    return this;
  }
  
  public void setPathMotion(PathMotion paramPathMotion)
  {
    super.setPathMotion(paramPathMotion);
    this.mChangeFlags |= 0x4;
    if (this.mTransitions != null) {
      for (int i = 0; i < this.mTransitions.size(); i++) {
        ((Transition)this.mTransitions.get(i)).setPathMotion(paramPathMotion);
      }
    }
  }
  
  public void setPropagation(TransitionPropagation paramTransitionPropagation)
  {
    super.setPropagation(paramTransitionPropagation);
    this.mChangeFlags |= 0x2;
    int j = this.mTransitions.size();
    for (int i = 0; i < j; i++) {
      ((Transition)this.mTransitions.get(i)).setPropagation(paramTransitionPropagation);
    }
  }
  
  TransitionSet setSceneRoot(ViewGroup paramViewGroup)
  {
    super.setSceneRoot(paramViewGroup);
    int j = this.mTransitions.size();
    for (int i = 0; i < j; i++) {
      ((Transition)this.mTransitions.get(i)).setSceneRoot(paramViewGroup);
    }
    return this;
  }
  
  public TransitionSet setStartDelay(long paramLong)
  {
    return (TransitionSet)super.setStartDelay(paramLong);
  }
  
  String toString(String paramString)
  {
    String str = super.toString(paramString);
    for (int i = 0; i < this.mTransitions.size(); i++) {
      str = str + "\n" + ((Transition)this.mTransitions.get(i)).toString(new StringBuilder().append(paramString).append("  ").toString());
    }
    return str;
  }
  
  static class TransitionSetListener
    extends TransitionListenerAdapter
  {
    TransitionSet mTransitionSet;
    
    TransitionSetListener(TransitionSet paramTransitionSet)
    {
      this.mTransitionSet = paramTransitionSet;
    }
    
    public void onTransitionEnd(Transition paramTransition)
    {
      TransitionSet localTransitionSet = this.mTransitionSet;
      localTransitionSet.mCurrentListeners -= 1;
      if (this.mTransitionSet.mCurrentListeners == 0)
      {
        this.mTransitionSet.mStarted = false;
        this.mTransitionSet.end();
      }
      paramTransition.removeListener(this);
    }
    
    public void onTransitionStart(Transition paramTransition)
    {
      if (!this.mTransitionSet.mStarted)
      {
        this.mTransitionSet.start();
        this.mTransitionSet.mStarted = true;
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/transition/TransitionSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */