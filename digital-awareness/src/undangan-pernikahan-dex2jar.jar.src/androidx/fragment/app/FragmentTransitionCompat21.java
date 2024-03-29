package androidx.fragment.app;

import android.graphics.Rect;
import android.transition.Transition;
import android.transition.Transition.EpicenterCallback;
import android.transition.Transition.TransitionListener;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.os.CancellationSignal;
import java.util.ArrayList;
import java.util.List;

class FragmentTransitionCompat21
  extends FragmentTransitionImpl
{
  private static boolean hasSimpleTarget(Transition paramTransition)
  {
    boolean bool;
    if ((isNullOrEmpty(paramTransition.getTargetIds())) && (isNullOrEmpty(paramTransition.getTargetNames())) && (isNullOrEmpty(paramTransition.getTargetTypes()))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public void addTarget(Object paramObject, View paramView)
  {
    if (paramObject != null) {
      ((Transition)paramObject).addTarget(paramView);
    }
  }
  
  public void addTargets(Object paramObject, ArrayList<View> paramArrayList)
  {
    paramObject = (Transition)paramObject;
    if (paramObject == null) {
      return;
    }
    int j;
    int i;
    if ((paramObject instanceof TransitionSet))
    {
      paramObject = (TransitionSet)paramObject;
      j = ((TransitionSet)paramObject).getTransitionCount();
      for (i = 0; i < j; i++) {
        addTargets(((TransitionSet)paramObject).getTransitionAt(i), paramArrayList);
      }
    }
    else if ((!hasSimpleTarget((Transition)paramObject)) && (isNullOrEmpty(((Transition)paramObject).getTargets())))
    {
      j = paramArrayList.size();
      for (i = 0; i < j; i++) {
        ((Transition)paramObject).addTarget((View)paramArrayList.get(i));
      }
    }
  }
  
  public void beginDelayedTransition(ViewGroup paramViewGroup, Object paramObject)
  {
    TransitionManager.beginDelayedTransition(paramViewGroup, (Transition)paramObject);
  }
  
  public boolean canHandle(Object paramObject)
  {
    return paramObject instanceof Transition;
  }
  
  public Object cloneTransition(Object paramObject)
  {
    Transition localTransition = null;
    if (paramObject != null) {
      localTransition = ((Transition)paramObject).clone();
    }
    return localTransition;
  }
  
  public Object mergeTransitionsInSequence(Object paramObject1, Object paramObject2, Object paramObject3)
  {
    Object localObject = null;
    paramObject1 = (Transition)paramObject1;
    paramObject2 = (Transition)paramObject2;
    paramObject3 = (Transition)paramObject3;
    if ((paramObject1 != null) && (paramObject2 != null))
    {
      paramObject1 = new TransitionSet().addTransition((Transition)paramObject1).addTransition((Transition)paramObject2).setOrdering(1);
    }
    else if (paramObject1 == null)
    {
      paramObject1 = localObject;
      if (paramObject2 != null) {
        paramObject1 = paramObject2;
      }
    }
    if (paramObject3 != null)
    {
      paramObject2 = new TransitionSet();
      if (paramObject1 != null) {
        ((TransitionSet)paramObject2).addTransition((Transition)paramObject1);
      }
      ((TransitionSet)paramObject2).addTransition((Transition)paramObject3);
      return paramObject2;
    }
    return paramObject1;
  }
  
  public Object mergeTransitionsTogether(Object paramObject1, Object paramObject2, Object paramObject3)
  {
    TransitionSet localTransitionSet = new TransitionSet();
    if (paramObject1 != null) {
      localTransitionSet.addTransition((Transition)paramObject1);
    }
    if (paramObject2 != null) {
      localTransitionSet.addTransition((Transition)paramObject2);
    }
    if (paramObject3 != null) {
      localTransitionSet.addTransition((Transition)paramObject3);
    }
    return localTransitionSet;
  }
  
  public void removeTarget(Object paramObject, View paramView)
  {
    if (paramObject != null) {
      ((Transition)paramObject).removeTarget(paramView);
    }
  }
  
  public void replaceTargets(Object paramObject, ArrayList<View> paramArrayList1, ArrayList<View> paramArrayList2)
  {
    paramObject = (Transition)paramObject;
    int j;
    int i;
    if ((paramObject instanceof TransitionSet))
    {
      paramObject = (TransitionSet)paramObject;
      j = ((TransitionSet)paramObject).getTransitionCount();
      for (i = 0; i < j; i++) {
        replaceTargets(((TransitionSet)paramObject).getTransitionAt(i), paramArrayList1, paramArrayList2);
      }
    }
    else if (!hasSimpleTarget((Transition)paramObject))
    {
      List localList = ((Transition)paramObject).getTargets();
      if ((localList != null) && (localList.size() == paramArrayList1.size()) && (localList.containsAll(paramArrayList1)))
      {
        if (paramArrayList2 == null) {
          i = 0;
        } else {
          i = paramArrayList2.size();
        }
        for (j = 0; j < i; j++) {
          ((Transition)paramObject).addTarget((View)paramArrayList2.get(j));
        }
        for (i = paramArrayList1.size() - 1; i >= 0; i--) {
          ((Transition)paramObject).removeTarget((View)paramArrayList1.get(i));
        }
      }
    }
  }
  
  public void scheduleHideFragmentView(Object paramObject, final View paramView, final ArrayList<View> paramArrayList)
  {
    ((Transition)paramObject).addListener(new Transition.TransitionListener()
    {
      public void onTransitionCancel(Transition paramAnonymousTransition) {}
      
      public void onTransitionEnd(Transition paramAnonymousTransition)
      {
        paramAnonymousTransition.removeListener(this);
        paramView.setVisibility(8);
        int j = paramArrayList.size();
        for (int i = 0; i < j; i++) {
          ((View)paramArrayList.get(i)).setVisibility(0);
        }
      }
      
      public void onTransitionPause(Transition paramAnonymousTransition) {}
      
      public void onTransitionResume(Transition paramAnonymousTransition) {}
      
      public void onTransitionStart(Transition paramAnonymousTransition)
      {
        paramAnonymousTransition.removeListener(this);
        paramAnonymousTransition.addListener(this);
      }
    });
  }
  
  public void scheduleRemoveTargets(Object paramObject1, final Object paramObject2, final ArrayList<View> paramArrayList1, final Object paramObject3, final ArrayList<View> paramArrayList2, final Object paramObject4, final ArrayList<View> paramArrayList3)
  {
    ((Transition)paramObject1).addListener(new Transition.TransitionListener()
    {
      public void onTransitionCancel(Transition paramAnonymousTransition) {}
      
      public void onTransitionEnd(Transition paramAnonymousTransition)
      {
        paramAnonymousTransition.removeListener(this);
      }
      
      public void onTransitionPause(Transition paramAnonymousTransition) {}
      
      public void onTransitionResume(Transition paramAnonymousTransition) {}
      
      public void onTransitionStart(Transition paramAnonymousTransition)
      {
        paramAnonymousTransition = paramObject2;
        if (paramAnonymousTransition != null) {
          FragmentTransitionCompat21.this.replaceTargets(paramAnonymousTransition, paramArrayList1, null);
        }
        paramAnonymousTransition = paramObject3;
        if (paramAnonymousTransition != null) {
          FragmentTransitionCompat21.this.replaceTargets(paramAnonymousTransition, paramArrayList2, null);
        }
        paramAnonymousTransition = paramObject4;
        if (paramAnonymousTransition != null) {
          FragmentTransitionCompat21.this.replaceTargets(paramAnonymousTransition, paramArrayList3, null);
        }
      }
    });
  }
  
  public void setEpicenter(Object paramObject, final Rect paramRect)
  {
    if (paramObject != null) {
      ((Transition)paramObject).setEpicenterCallback(new Transition.EpicenterCallback()
      {
        public Rect onGetEpicenter(Transition paramAnonymousTransition)
        {
          paramAnonymousTransition = paramRect;
          if ((paramAnonymousTransition != null) && (!paramAnonymousTransition.isEmpty())) {
            return paramRect;
          }
          return null;
        }
      });
    }
  }
  
  public void setEpicenter(Object paramObject, View paramView)
  {
    if (paramView != null)
    {
      paramObject = (Transition)paramObject;
      final Rect localRect = new Rect();
      getBoundsOnScreen(paramView, localRect);
      ((Transition)paramObject).setEpicenterCallback(new Transition.EpicenterCallback()
      {
        public Rect onGetEpicenter(Transition paramAnonymousTransition)
        {
          return localRect;
        }
      });
    }
  }
  
  public void setListenerForTransitionEnd(Fragment paramFragment, Object paramObject, CancellationSignal paramCancellationSignal, final Runnable paramRunnable)
  {
    ((Transition)paramObject).addListener(new Transition.TransitionListener()
    {
      public void onTransitionCancel(Transition paramAnonymousTransition) {}
      
      public void onTransitionEnd(Transition paramAnonymousTransition)
      {
        paramRunnable.run();
      }
      
      public void onTransitionPause(Transition paramAnonymousTransition) {}
      
      public void onTransitionResume(Transition paramAnonymousTransition) {}
      
      public void onTransitionStart(Transition paramAnonymousTransition) {}
    });
  }
  
  public void setSharedElementTargets(Object paramObject, View paramView, ArrayList<View> paramArrayList)
  {
    TransitionSet localTransitionSet = (TransitionSet)paramObject;
    paramObject = localTransitionSet.getTargets();
    ((List)paramObject).clear();
    int j = paramArrayList.size();
    for (int i = 0; i < j; i++) {
      bfsAddViewChildren((List)paramObject, (View)paramArrayList.get(i));
    }
    ((List)paramObject).add(paramView);
    paramArrayList.add(paramView);
    addTargets(localTransitionSet, paramArrayList);
  }
  
  public void swapSharedElementTargets(Object paramObject, ArrayList<View> paramArrayList1, ArrayList<View> paramArrayList2)
  {
    paramObject = (TransitionSet)paramObject;
    if (paramObject != null)
    {
      ((TransitionSet)paramObject).getTargets().clear();
      ((TransitionSet)paramObject).getTargets().addAll(paramArrayList2);
      replaceTargets(paramObject, paramArrayList1, paramArrayList2);
    }
  }
  
  public Object wrapTransitionInSet(Object paramObject)
  {
    if (paramObject == null) {
      return null;
    }
    TransitionSet localTransitionSet = new TransitionSet();
    localTransitionSet.addTransition((Transition)paramObject);
    return localTransitionSet;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/fragment/app/FragmentTransitionCompat21.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */