package androidx.fragment.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import androidx.collection.ArrayMap;
import androidx.core.app.SharedElementCallback;
import androidx.core.os.CancellationSignal;
import androidx.core.os.CancellationSignal.OnCancelListener;
import androidx.core.util.Preconditions;
import androidx.core.view.OneShotPreDrawListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewGroupCompat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

class DefaultSpecialEffectsController
  extends SpecialEffectsController
{
  DefaultSpecialEffectsController(ViewGroup paramViewGroup)
  {
    super(paramViewGroup);
  }
  
  private void startAnimations(List<AnimationInfo> paramList, final List<SpecialEffectsController.Operation> paramList1, boolean paramBoolean, final Map<SpecialEffectsController.Operation, Boolean> paramMap)
  {
    final ViewGroup localViewGroup = getContainer();
    Context localContext = localViewGroup.getContext();
    Object localObject1 = new ArrayList();
    int i = 0;
    paramList = paramList.iterator();
    final Object localObject2;
    while (paramList.hasNext())
    {
      localObject2 = (AnimationInfo)paramList.next();
      if (((AnimationInfo)localObject2).isVisibilityUnchanged())
      {
        ((AnimationInfo)localObject2).completeSpecialEffect();
      }
      else
      {
        final Object localObject3 = ((AnimationInfo)localObject2).getAnimation(localContext);
        if (localObject3 == null)
        {
          ((AnimationInfo)localObject2).completeSpecialEffect();
        }
        else
        {
          localObject3 = ((FragmentAnim.AnimationOrAnimator)localObject3).animator;
          if (localObject3 == null)
          {
            ((ArrayList)localObject1).add(localObject2);
          }
          else
          {
            final SpecialEffectsController.Operation localOperation = ((AnimationInfo)localObject2).getOperation();
            final Object localObject4 = localOperation.getFragment();
            if (Boolean.TRUE.equals(paramMap.get(localOperation)))
            {
              if (FragmentManager.isLoggingEnabled(2)) {
                Log.v("FragmentManager", "Ignoring Animator set on " + localObject4 + " as this Fragment was involved in a Transition.");
              }
              ((AnimationInfo)localObject2).completeSpecialEffect();
            }
            else
            {
              final boolean bool;
              if (localOperation.getFinalState() == SpecialEffectsController.Operation.State.GONE) {
                bool = true;
              } else {
                bool = false;
              }
              if (bool) {
                paramList1.remove(localOperation);
              }
              localObject4 = ((Fragment)localObject4).mView;
              localViewGroup.startViewTransition((View)localObject4);
              ((Animator)localObject3).addListener(new AnimatorListenerAdapter()
              {
                public void onAnimationEnd(Animator paramAnonymousAnimator)
                {
                  localViewGroup.endViewTransition(localObject4);
                  if (bool) {
                    localOperation.getFinalState().applyState(localObject4);
                  }
                  localObject2.completeSpecialEffect();
                }
              });
              ((Animator)localObject3).setTarget(localObject4);
              ((Animator)localObject3).start();
              ((AnimationInfo)localObject2).getSignal().setOnCancelListener(new CancellationSignal.OnCancelListener()
              {
                public void onCancel()
                {
                  localObject3.end();
                }
              });
              i = 1;
            }
          }
        }
      }
    }
    paramList = ((ArrayList)localObject1).iterator();
    while (paramList.hasNext())
    {
      paramList1 = (AnimationInfo)paramList.next();
      localObject1 = paramList1.getOperation();
      paramMap = ((SpecialEffectsController.Operation)localObject1).getFragment();
      if (paramBoolean)
      {
        if (FragmentManager.isLoggingEnabled(2)) {
          Log.v("FragmentManager", "Ignoring Animation set on " + paramMap + " as Animations cannot run alongside Transitions.");
        }
        paramList1.completeSpecialEffect();
      }
      else if (i != 0)
      {
        if (FragmentManager.isLoggingEnabled(2)) {
          Log.v("FragmentManager", "Ignoring Animation set on " + paramMap + " as Animations cannot run alongside Animators.");
        }
        paramList1.completeSpecialEffect();
      }
      else
      {
        paramMap = paramMap.mView;
        localObject2 = (Animation)Preconditions.checkNotNull(((FragmentAnim.AnimationOrAnimator)Preconditions.checkNotNull(paramList1.getAnimation(localContext))).animation);
        if (((SpecialEffectsController.Operation)localObject1).getFinalState() != SpecialEffectsController.Operation.State.REMOVED)
        {
          paramMap.startAnimation((Animation)localObject2);
          paramList1.completeSpecialEffect();
        }
        else
        {
          localViewGroup.startViewTransition(paramMap);
          localObject1 = new FragmentAnim.EndViewTransitionAnimation((Animation)localObject2, localViewGroup, paramMap);
          ((Animation)localObject1).setAnimationListener(new Animation.AnimationListener()
          {
            public void onAnimationEnd(Animation paramAnonymousAnimation)
            {
              localViewGroup.post(new Runnable()
              {
                public void run()
                {
                  DefaultSpecialEffectsController.4.this.val$container.endViewTransition(DefaultSpecialEffectsController.4.this.val$viewToAnimate);
                  DefaultSpecialEffectsController.4.this.val$animationInfo.completeSpecialEffect();
                }
              });
            }
            
            public void onAnimationRepeat(Animation paramAnonymousAnimation) {}
            
            public void onAnimationStart(Animation paramAnonymousAnimation) {}
          });
          paramMap.startAnimation((Animation)localObject1);
        }
        paramList1.getSignal().setOnCancelListener(new CancellationSignal.OnCancelListener()
        {
          public void onCancel()
          {
            paramMap.clearAnimation();
            localViewGroup.endViewTransition(paramMap);
            paramList1.completeSpecialEffect();
          }
        });
      }
    }
  }
  
  private Map<SpecialEffectsController.Operation, Boolean> startTransitions(List<TransitionInfo> paramList, List<SpecialEffectsController.Operation> paramList1, final boolean paramBoolean, final SpecialEffectsController.Operation paramOperation1, final SpecialEffectsController.Operation paramOperation2)
  {
    Object localObject3 = paramOperation1;
    final Object localObject2 = paramOperation2;
    Object localObject6 = new HashMap();
    final Object localObject7 = paramList.iterator();
    final Object localObject4 = null;
    while (((Iterator)localObject7).hasNext())
    {
      localObject8 = (TransitionInfo)((Iterator)localObject7).next();
      if (!((TransitionInfo)localObject8).isVisibilityUnchanged())
      {
        localObject5 = ((TransitionInfo)localObject8).getHandlingImpl();
        if (localObject4 == null)
        {
          localObject1 = localObject5;
        }
        else
        {
          localObject1 = localObject4;
          if (localObject5 != null) {
            if (localObject4 == localObject5) {
              localObject1 = localObject4;
            } else {
              throw new IllegalArgumentException("Mixing framework transitions and AndroidX transitions is not allowed. Fragment " + ((TransitionInfo)localObject8).getOperation().getFragment() + " returned Transition " + ((TransitionInfo)localObject8).getTransition() + " which uses a different Transition  type than other Fragments.");
            }
          }
        }
        localObject4 = localObject1;
      }
    }
    boolean bool = false;
    if (localObject4 == null)
    {
      paramList = paramList.iterator();
      while (paramList.hasNext())
      {
        paramList1 = (TransitionInfo)paramList.next();
        ((Map)localObject6).put(paramList1.getOperation(), Boolean.valueOf(false));
        paramList1.completeSpecialEffect();
      }
      return (Map<SpecialEffectsController.Operation, Boolean>)localObject6;
    }
    Object localObject5 = new View(getContainer().getContext());
    final Object localObject10 = null;
    final Object localObject8 = new Rect();
    localObject7 = new ArrayList();
    Object localObject9 = new ArrayList();
    ArrayMap localArrayMap = new ArrayMap();
    Object localObject13 = paramList.iterator();
    Object localObject1 = null;
    int i = 0;
    while (((Iterator)localObject13).hasNext())
    {
      localObject11 = (TransitionInfo)((Iterator)localObject13).next();
      if ((((TransitionInfo)localObject11).hasSharedElementTransition()) && (localObject3 != null) && (localObject2 != null))
      {
        localObject12 = ((FragmentTransitionImpl)localObject4).wrapTransitionInSet(((FragmentTransitionImpl)localObject4).cloneTransition(((TransitionInfo)localObject11).getSharedElementTransition()));
        localObject10 = paramOperation2.getFragment().getSharedElementSourceNames();
        localObject3 = paramOperation1.getFragment().getSharedElementSourceNames();
        localObject2 = paramOperation1.getFragment().getSharedElementTargetNames();
        for (int j = 0; j < ((ArrayList)localObject2).size(); j++)
        {
          k = ((ArrayList)localObject10).indexOf(((ArrayList)localObject2).get(j));
          if (k != -1) {
            ((ArrayList)localObject10).set(k, ((ArrayList)localObject3).get(j));
          }
        }
        localObject14 = paramOperation2.getFragment().getSharedElementTargetNames();
        if (!paramBoolean)
        {
          localObject3 = paramOperation1.getFragment().getExitTransitionCallback();
          localObject2 = paramOperation2.getFragment().getEnterTransitionCallback();
        }
        else
        {
          localObject3 = paramOperation1.getFragment().getEnterTransitionCallback();
          localObject2 = paramOperation2.getFragment().getExitTransitionCallback();
        }
        j = ((ArrayList)localObject10).size();
        for (int k = 0; k < j; k++) {
          localArrayMap.put((String)((ArrayList)localObject10).get(k), (String)((ArrayList)localObject14).get(k));
        }
        localObject15 = new ArrayMap();
        findNamedViews((Map)localObject15, paramOperation1.getFragment().mView);
        ((ArrayMap)localObject15).retainAll((Collection)localObject10);
        if (localObject3 != null)
        {
          ((SharedElementCallback)localObject3).onMapSharedElements((List)localObject10, (Map)localObject15);
          for (j = ((ArrayList)localObject10).size() - 1; j >= 0; j--)
          {
            localObject17 = (String)((ArrayList)localObject10).get(j);
            localObject11 = (View)((ArrayMap)localObject15).get(localObject17);
            if (localObject11 == null)
            {
              localArrayMap.remove(localObject17);
            }
            else
            {
              localObject16 = ViewCompat.getTransitionName((View)localObject11);
              Log5ECF72.a(localObject16);
              LogE84000.a(localObject16);
              Log229316.a(localObject16);
              if (!((String)localObject17).equals(localObject16))
              {
                localObject16 = (String)localArrayMap.remove(localObject17);
                localObject11 = ViewCompat.getTransitionName((View)localObject11);
                Log5ECF72.a(localObject11);
                LogE84000.a(localObject11);
                Log229316.a(localObject11);
                localArrayMap.put(localObject11, localObject16);
              }
            }
          }
          localObject11 = localObject10;
          localObject10 = localObject3;
          localObject3 = localObject11;
          localObject11 = localObject10;
        }
        else
        {
          localArrayMap.retainAll(((ArrayMap)localObject15).keySet());
          localObject11 = localObject3;
          localObject3 = localObject10;
        }
        localObject10 = new ArrayMap();
        findNamedViews((Map)localObject10, paramOperation2.getFragment().mView);
        ((ArrayMap)localObject10).retainAll((Collection)localObject14);
        ((ArrayMap)localObject10).retainAll(localArrayMap.values());
        if (localObject2 != null)
        {
          ((SharedElementCallback)localObject2).onMapSharedElements((List)localObject14, (Map)localObject10);
          for (j = ((ArrayList)localObject14).size() - 1; j >= 0; j--)
          {
            localObject17 = (String)((ArrayList)localObject14).get(j);
            localObject11 = (View)((ArrayMap)localObject10).get(localObject17);
            if (localObject11 == null)
            {
              localObject11 = FragmentTransition.findKeyForValue(localArrayMap, (String)localObject17);
              Log5ECF72.a(localObject11);
              LogE84000.a(localObject11);
              Log229316.a(localObject11);
              if (localObject11 != null) {
                localArrayMap.remove(localObject11);
              }
            }
            else
            {
              localObject16 = ViewCompat.getTransitionName((View)localObject11);
              Log5ECF72.a(localObject16);
              LogE84000.a(localObject16);
              Log229316.a(localObject16);
              if (!((String)localObject17).equals(localObject16))
              {
                localObject16 = FragmentTransition.findKeyForValue(localArrayMap, (String)localObject17);
                Log5ECF72.a(localObject16);
                LogE84000.a(localObject16);
                Log229316.a(localObject16);
                if (localObject16 != null)
                {
                  localObject11 = ViewCompat.getTransitionName((View)localObject11);
                  Log5ECF72.a(localObject11);
                  LogE84000.a(localObject11);
                  Log229316.a(localObject11);
                  localArrayMap.put(localObject16, localObject11);
                }
                else {}
              }
            }
          }
        }
        else
        {
          FragmentTransition.retainValues(localArrayMap, (ArrayMap)localObject10);
        }
        retainMatchingViews((ArrayMap)localObject15, localArrayMap.keySet());
        retainMatchingViews((ArrayMap)localObject10, localArrayMap.values());
        if (localArrayMap.isEmpty())
        {
          localObject10 = null;
          ((ArrayList)localObject7).clear();
          ((ArrayList)localObject9).clear();
          bool = false;
          localObject2 = paramOperation1;
          localObject3 = paramOperation2;
        }
        else
        {
          FragmentTransition.callSharedElementStartEnd(paramOperation2.getFragment(), paramOperation1.getFragment(), paramBoolean, (ArrayMap)localObject15, true);
          OneShotPreDrawListener.add(getContainer(), new Runnable()
          {
            public void run()
            {
              FragmentTransition.callSharedElementStartEnd(paramOperation2.getFragment(), paramOperation1.getFragment(), paramBoolean, localObject10, false);
            }
          });
          ((ArrayList)localObject7).addAll(((ArrayMap)localObject15).values());
          if (!((ArrayList)localObject3).isEmpty())
          {
            localObject1 = (View)((ArrayMap)localObject15).get((String)((ArrayList)localObject3).get(0));
            ((FragmentTransitionImpl)localObject4).setEpicenter(localObject12, (View)localObject1);
          }
          ((ArrayList)localObject9).addAll(((ArrayMap)localObject10).values());
          if (!((ArrayList)localObject14).isEmpty())
          {
            localObject2 = (View)((ArrayMap)localObject10).get((String)((ArrayList)localObject14).get(0));
            if (localObject2 != null)
            {
              i = 1;
              OneShotPreDrawListener.add(getContainer(), new Runnable()
              {
                public void run()
                {
                  localObject4.getBoundsOnScreen(localObject2, localObject8);
                }
              });
            }
          }
          ((FragmentTransitionImpl)localObject4).setSharedElementTargets(localObject12, (View)localObject5, (ArrayList)localObject7);
          bool = false;
          ((FragmentTransitionImpl)localObject4).scheduleRemoveTargets(localObject12, null, null, null, null, localObject12, (ArrayList)localObject9);
          localObject2 = paramOperation1;
          ((Map)localObject6).put(localObject2, Boolean.valueOf(true));
          localObject3 = paramOperation2;
          ((Map)localObject6).put(localObject3, Boolean.valueOf(true));
          localObject10 = localObject12;
        }
      }
      else
      {
        localObject11 = localObject3;
        localObject3 = localObject2;
        localObject2 = localObject11;
      }
      localObject11 = localObject7;
      localObject7 = localObject3;
      localObject3 = localObject2;
      localObject2 = localObject7;
      localObject7 = localObject11;
    }
    paramOperation1 = (SpecialEffectsController.Operation)localObject1;
    Object localObject11 = localObject9;
    localObject9 = localObject5;
    paramBoolean = bool;
    localObject1 = localObject4;
    final Object localObject12 = localObject2;
    localObject2 = localObject6;
    localObject13 = localObject7;
    Object localObject17 = new ArrayList();
    Object localObject14 = null;
    Object localObject15 = null;
    Object localObject16 = paramList.iterator();
    localObject6 = localObject8;
    localObject7 = paramOperation1;
    localObject4 = localObject11;
    localObject5 = localObject3;
    localObject3 = localObject13;
    localObject8 = localObject16;
    paramOperation1 = (SpecialEffectsController.Operation)localObject15;
    localObject11 = localObject14;
    while (((Iterator)localObject8).hasNext())
    {
      localObject15 = (TransitionInfo)((Iterator)localObject8).next();
      if (((TransitionInfo)localObject15).isVisibilityUnchanged())
      {
        ((Map)localObject2).put(((TransitionInfo)localObject15).getOperation(), Boolean.valueOf(paramBoolean));
        ((TransitionInfo)localObject15).completeSpecialEffect();
      }
      else
      {
        localObject13 = ((FragmentTransitionImpl)localObject1).cloneTransition(((TransitionInfo)localObject15).getTransition());
        localObject14 = ((TransitionInfo)localObject15).getOperation();
        if ((localObject10 != null) && ((localObject14 == localObject5) || (localObject14 == localObject12))) {
          bool = true;
        } else {
          bool = paramBoolean;
        }
        if (localObject13 == null)
        {
          if (!bool)
          {
            ((Map)localObject2).put(localObject14, Boolean.valueOf(paramBoolean));
            ((TransitionInfo)localObject15).completeSpecialEffect();
          }
        }
        else
        {
          localObject12 = new ArrayList();
          captureTransitioningViews((ArrayList)localObject12, ((SpecialEffectsController.Operation)localObject14).getFragment().mView);
          if (bool) {
            if (localObject14 == localObject5) {
              ((ArrayList)localObject12).removeAll((Collection)localObject3);
            } else {
              ((ArrayList)localObject12).removeAll((Collection)localObject4);
            }
          }
          if (((ArrayList)localObject12).isEmpty())
          {
            ((FragmentTransitionImpl)localObject1).addTarget(localObject13, (View)localObject9);
          }
          else
          {
            ((FragmentTransitionImpl)localObject1).addTargets(localObject13, (ArrayList)localObject12);
            ((FragmentTransitionImpl)localObject1).scheduleRemoveTargets(localObject13, localObject13, (ArrayList)localObject12, null, null, null, null);
            if (((SpecialEffectsController.Operation)localObject14).getFinalState() == SpecialEffectsController.Operation.State.GONE)
            {
              paramList1.remove(localObject14);
              localObject16 = new ArrayList((Collection)localObject12);
              ((ArrayList)localObject16).remove(((SpecialEffectsController.Operation)localObject14).getFragment().mView);
              ((FragmentTransitionImpl)localObject1).scheduleHideFragmentView(localObject13, ((SpecialEffectsController.Operation)localObject14).getFragment().mView, (ArrayList)localObject16);
              OneShotPreDrawListener.add(getContainer(), new Runnable()
              {
                public void run()
                {
                  FragmentTransition.setViewVisibility(localObject12, 4);
                }
              });
            }
          }
          if (((SpecialEffectsController.Operation)localObject14).getFinalState() == SpecialEffectsController.Operation.State.VISIBLE)
          {
            ((ArrayList)localObject17).addAll((Collection)localObject12);
            if (i != 0) {
              ((FragmentTransitionImpl)localObject1).setEpicenter(localObject13, (Rect)localObject6);
            }
          }
          else
          {
            ((FragmentTransitionImpl)localObject1).setEpicenter(localObject13, (View)localObject7);
          }
          ((Map)localObject2).put(localObject14, Boolean.valueOf(true));
          if (((TransitionInfo)localObject15).isOverlapAllowed()) {
            localObject11 = ((FragmentTransitionImpl)localObject1).mergeTransitionsTogether(localObject11, localObject13, null);
          } else {
            paramOperation1 = ((FragmentTransitionImpl)localObject1).mergeTransitionsTogether(paramOperation1, localObject13, null);
          }
        }
        paramBoolean = false;
        localObject12 = paramOperation2;
      }
    }
    paramList1 = ((FragmentTransitionImpl)localObject1).mergeTransitionsInSequence(localObject11, paramOperation1, localObject10);
    paramList = paramList.iterator();
    label1947:
    label1950:
    label2063:
    while (paramList.hasNext())
    {
      localObject7 = (TransitionInfo)paramList.next();
      if (!((TransitionInfo)localObject7).isVisibilityUnchanged())
      {
        localObject8 = ((TransitionInfo)localObject7).getTransition();
        localObject6 = ((TransitionInfo)localObject7).getOperation();
        if (localObject10 != null)
        {
          if (localObject6 != localObject5) {
            if (localObject6 != paramOperation2) {
              break label1947;
            }
          }
          i = 1;
          break label1950;
        }
        i = 0;
        if ((localObject8 == null) && (i == 0)) {
          break label2063;
        }
        if (!ViewCompat.isLaidOut(getContainer()))
        {
          if (FragmentManager.isLoggingEnabled(2)) {
            Log.v("FragmentManager", "SpecialEffectsController: Container " + getContainer() + " has not been laid out. Completing operation " + localObject6);
          }
          ((TransitionInfo)localObject7).completeSpecialEffect();
        }
        else
        {
          ((FragmentTransitionImpl)localObject1).setListenerForTransitionEnd(((TransitionInfo)localObject7).getOperation().getFragment(), paramList1, ((TransitionInfo)localObject7).getSignal(), new Runnable()
          {
            public void run()
            {
              localObject7.completeSpecialEffect();
            }
          });
        }
      }
    }
    if (!ViewCompat.isLaidOut(getContainer())) {
      return (Map<SpecialEffectsController.Operation, Boolean>)localObject2;
    }
    FragmentTransition.setViewVisibility((ArrayList)localObject17, 4);
    paramList = ((FragmentTransitionImpl)localObject1).prepareSetNameOverridesReordered((ArrayList)localObject4);
    ((FragmentTransitionImpl)localObject1).beginDelayedTransition(getContainer(), paramList1);
    ((FragmentTransitionImpl)localObject1).setNameOverridesReordered(getContainer(), (ArrayList)localObject3, (ArrayList)localObject4, paramList, localArrayMap);
    FragmentTransition.setViewVisibility((ArrayList)localObject17, 0);
    ((FragmentTransitionImpl)localObject1).swapSharedElementTargets(localObject10, (ArrayList)localObject3, (ArrayList)localObject4);
    return (Map<SpecialEffectsController.Operation, Boolean>)localObject2;
  }
  
  void applyContainerChanges(SpecialEffectsController.Operation paramOperation)
  {
    View localView = paramOperation.getFragment().mView;
    paramOperation.getFinalState().applyState(localView);
  }
  
  void captureTransitioningViews(ArrayList<View> paramArrayList, View paramView)
  {
    if ((paramView instanceof ViewGroup))
    {
      ViewGroup localViewGroup = (ViewGroup)paramView;
      if (ViewGroupCompat.isTransitionGroup(localViewGroup))
      {
        if (!paramArrayList.contains(paramView)) {
          paramArrayList.add(localViewGroup);
        }
      }
      else
      {
        int j = localViewGroup.getChildCount();
        for (int i = 0; i < j; i++)
        {
          paramView = localViewGroup.getChildAt(i);
          if (paramView.getVisibility() == 0) {
            captureTransitioningViews(paramArrayList, paramView);
          }
        }
      }
    }
    else if (!paramArrayList.contains(paramView))
    {
      paramArrayList.add(paramView);
    }
  }
  
  void executeOperations(List<SpecialEffectsController.Operation> paramList, boolean paramBoolean)
  {
    Object localObject2 = null;
    Object localObject1 = null;
    final Object localObject6 = paramList.iterator();
    Object localObject7;
    while (((Iterator)localObject6).hasNext())
    {
      localObject5 = (SpecialEffectsController.Operation)((Iterator)localObject6).next();
      localObject7 = SpecialEffectsController.Operation.State.from(((SpecialEffectsController.Operation)localObject5).getFragment().mView);
      switch (10.$SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State[localObject5.getFinalState().ordinal()])
      {
      default: 
        localObject4 = localObject2;
        localObject3 = localObject1;
        break;
      case 4: 
        localObject4 = localObject2;
        localObject3 = localObject1;
        if (localObject7 != SpecialEffectsController.Operation.State.VISIBLE)
        {
          localObject3 = localObject5;
          localObject4 = localObject2;
        }
        break;
      case 1: 
      case 2: 
      case 3: 
        localObject4 = localObject2;
        localObject3 = localObject1;
        if (localObject7 == SpecialEffectsController.Operation.State.VISIBLE)
        {
          localObject4 = localObject2;
          localObject3 = localObject1;
          if (localObject2 == null)
          {
            localObject4 = localObject5;
            localObject3 = localObject1;
          }
        }
        break;
      }
      localObject2 = localObject4;
      localObject1 = localObject3;
    }
    Object localObject4 = new ArrayList();
    Object localObject5 = new ArrayList();
    final Object localObject3 = new ArrayList(paramList);
    paramList = paramList.iterator();
    for (;;)
    {
      boolean bool2 = paramList.hasNext();
      boolean bool1 = true;
      if (!bool2) {
        break;
      }
      localObject6 = (SpecialEffectsController.Operation)paramList.next();
      localObject7 = new CancellationSignal();
      ((SpecialEffectsController.Operation)localObject6).markStartedSpecialEffect((CancellationSignal)localObject7);
      ((List)localObject4).add(new AnimationInfo((SpecialEffectsController.Operation)localObject6, (CancellationSignal)localObject7, paramBoolean));
      localObject7 = new CancellationSignal();
      ((SpecialEffectsController.Operation)localObject6).markStartedSpecialEffect((CancellationSignal)localObject7);
      if (paramBoolean) {
        if (localObject6 != localObject2) {
          break label315;
        }
      } else {
        if (localObject6 == localObject1) {
          break label317;
        }
      }
      label315:
      bool1 = false;
      label317:
      ((List)localObject5).add(new TransitionInfo((SpecialEffectsController.Operation)localObject6, (CancellationSignal)localObject7, paramBoolean, bool1));
      ((SpecialEffectsController.Operation)localObject6).addCompletionListener(new Runnable()
      {
        public void run()
        {
          if (localObject3.contains(localObject6))
          {
            localObject3.remove(localObject6);
            DefaultSpecialEffectsController.this.applyContainerChanges(localObject6);
          }
        }
      });
    }
    paramList = startTransitions((List)localObject5, (List)localObject3, paramBoolean, (SpecialEffectsController.Operation)localObject2, (SpecialEffectsController.Operation)localObject1);
    startAnimations((List)localObject4, (List)localObject3, paramList.containsValue(Boolean.valueOf(true)), paramList);
    paramList = ((List)localObject3).iterator();
    while (paramList.hasNext()) {
      applyContainerChanges((SpecialEffectsController.Operation)paramList.next());
    }
    ((List)localObject3).clear();
  }
  
  void findNamedViews(Map<String, View> paramMap, View paramView)
  {
    Object localObject = ViewCompat.getTransitionName(paramView);
    Log5ECF72.a(localObject);
    LogE84000.a(localObject);
    Log229316.a(localObject);
    if (localObject != null) {
      paramMap.put(localObject, paramView);
    }
    if ((paramView instanceof ViewGroup))
    {
      paramView = (ViewGroup)paramView;
      int j = paramView.getChildCount();
      for (int i = 0; i < j; i++)
      {
        localObject = paramView.getChildAt(i);
        if (((View)localObject).getVisibility() == 0) {
          findNamedViews(paramMap, (View)localObject);
        }
      }
    }
  }
  
  void retainMatchingViews(ArrayMap<String, View> paramArrayMap, Collection<String> paramCollection)
  {
    paramArrayMap = paramArrayMap.entrySet().iterator();
    while (paramArrayMap.hasNext())
    {
      String str = ViewCompat.getTransitionName((View)((Map.Entry)paramArrayMap.next()).getValue());
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      if (!paramCollection.contains(str)) {
        paramArrayMap.remove();
      }
    }
  }
  
  private static class AnimationInfo
    extends DefaultSpecialEffectsController.SpecialEffectsInfo
  {
    private FragmentAnim.AnimationOrAnimator mAnimation;
    private boolean mIsPop;
    private boolean mLoadedAnim = false;
    
    AnimationInfo(SpecialEffectsController.Operation paramOperation, CancellationSignal paramCancellationSignal, boolean paramBoolean)
    {
      super(paramCancellationSignal);
      this.mIsPop = paramBoolean;
    }
    
    FragmentAnim.AnimationOrAnimator getAnimation(Context paramContext)
    {
      if (this.mLoadedAnim) {
        return this.mAnimation;
      }
      Fragment localFragment = getOperation().getFragment();
      boolean bool;
      if (getOperation().getFinalState() == SpecialEffectsController.Operation.State.VISIBLE) {
        bool = true;
      } else {
        bool = false;
      }
      paramContext = FragmentAnim.loadAnimation(paramContext, localFragment, bool, this.mIsPop);
      this.mAnimation = paramContext;
      this.mLoadedAnim = true;
      return paramContext;
    }
  }
  
  private static class SpecialEffectsInfo
  {
    private final SpecialEffectsController.Operation mOperation;
    private final CancellationSignal mSignal;
    
    SpecialEffectsInfo(SpecialEffectsController.Operation paramOperation, CancellationSignal paramCancellationSignal)
    {
      this.mOperation = paramOperation;
      this.mSignal = paramCancellationSignal;
    }
    
    void completeSpecialEffect()
    {
      this.mOperation.completeSpecialEffect(this.mSignal);
    }
    
    SpecialEffectsController.Operation getOperation()
    {
      return this.mOperation;
    }
    
    CancellationSignal getSignal()
    {
      return this.mSignal;
    }
    
    boolean isVisibilityUnchanged()
    {
      SpecialEffectsController.Operation.State localState2 = SpecialEffectsController.Operation.State.from(this.mOperation.getFragment().mView);
      SpecialEffectsController.Operation.State localState1 = this.mOperation.getFinalState();
      boolean bool;
      if ((localState2 != localState1) && ((localState2 == SpecialEffectsController.Operation.State.VISIBLE) || (localState1 == SpecialEffectsController.Operation.State.VISIBLE))) {
        bool = false;
      } else {
        bool = true;
      }
      return bool;
    }
  }
  
  private static class TransitionInfo
    extends DefaultSpecialEffectsController.SpecialEffectsInfo
  {
    private final boolean mOverlapAllowed;
    private final Object mSharedElementTransition;
    private final Object mTransition;
    
    TransitionInfo(SpecialEffectsController.Operation paramOperation, CancellationSignal paramCancellationSignal, boolean paramBoolean1, boolean paramBoolean2)
    {
      super(paramCancellationSignal);
      if (paramOperation.getFinalState() == SpecialEffectsController.Operation.State.VISIBLE)
      {
        if (paramBoolean1) {
          paramCancellationSignal = paramOperation.getFragment().getReenterTransition();
        } else {
          paramCancellationSignal = paramOperation.getFragment().getEnterTransition();
        }
        this.mTransition = paramCancellationSignal;
        boolean bool;
        if (paramBoolean1) {
          bool = paramOperation.getFragment().getAllowReturnTransitionOverlap();
        } else {
          bool = paramOperation.getFragment().getAllowEnterTransitionOverlap();
        }
        this.mOverlapAllowed = bool;
      }
      else
      {
        if (paramBoolean1) {
          paramCancellationSignal = paramOperation.getFragment().getReturnTransition();
        } else {
          paramCancellationSignal = paramOperation.getFragment().getExitTransition();
        }
        this.mTransition = paramCancellationSignal;
        this.mOverlapAllowed = true;
      }
      if (paramBoolean2)
      {
        if (paramBoolean1) {
          this.mSharedElementTransition = paramOperation.getFragment().getSharedElementReturnTransition();
        } else {
          this.mSharedElementTransition = paramOperation.getFragment().getSharedElementEnterTransition();
        }
      }
      else {
        this.mSharedElementTransition = null;
      }
    }
    
    private FragmentTransitionImpl getHandlingImpl(Object paramObject)
    {
      if (paramObject == null) {
        return null;
      }
      if ((FragmentTransition.PLATFORM_IMPL != null) && (FragmentTransition.PLATFORM_IMPL.canHandle(paramObject))) {
        return FragmentTransition.PLATFORM_IMPL;
      }
      if ((FragmentTransition.SUPPORT_IMPL != null) && (FragmentTransition.SUPPORT_IMPL.canHandle(paramObject))) {
        return FragmentTransition.SUPPORT_IMPL;
      }
      throw new IllegalArgumentException("Transition " + paramObject + " for fragment " + getOperation().getFragment() + " is not a valid framework Transition or AndroidX Transition");
    }
    
    FragmentTransitionImpl getHandlingImpl()
    {
      Object localObject = getHandlingImpl(this.mTransition);
      FragmentTransitionImpl localFragmentTransitionImpl = getHandlingImpl(this.mSharedElementTransition);
      if ((localObject != null) && (localFragmentTransitionImpl != null) && (localObject != localFragmentTransitionImpl)) {
        throw new IllegalArgumentException("Mixing framework transitions and AndroidX transitions is not allowed. Fragment " + getOperation().getFragment() + " returned Transition " + this.mTransition + " which uses a different Transition  type than its shared element transition " + this.mSharedElementTransition);
      }
      if (localObject == null) {
        localObject = localFragmentTransitionImpl;
      }
      return (FragmentTransitionImpl)localObject;
    }
    
    public Object getSharedElementTransition()
    {
      return this.mSharedElementTransition;
    }
    
    Object getTransition()
    {
      return this.mTransition;
    }
    
    public boolean hasSharedElementTransition()
    {
      boolean bool;
      if (this.mSharedElementTransition != null) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    boolean isOverlapAllowed()
    {
      return this.mOverlapAllowed;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/fragment/app/DefaultSpecialEffectsController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */