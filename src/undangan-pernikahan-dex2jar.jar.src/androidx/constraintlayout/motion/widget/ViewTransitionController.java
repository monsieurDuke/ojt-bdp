package androidx.constraintlayout.motion.widget;

import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.SharedValues;
import androidx.constraintlayout.widget.SharedValues.SharedValuesListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class ViewTransitionController
{
  private String TAG = "ViewTransitionController";
  ArrayList<ViewTransition.Animate> animations;
  private final MotionLayout mMotionLayout;
  private HashSet<View> mRelatedViews;
  ArrayList<ViewTransition.Animate> removeList = new ArrayList();
  private ArrayList<ViewTransition> viewTransitions = new ArrayList();
  
  public ViewTransitionController(MotionLayout paramMotionLayout)
  {
    this.mMotionLayout = paramMotionLayout;
  }
  
  private void listenForSharedVariable(final ViewTransition paramViewTransition, final boolean paramBoolean)
  {
    final int i = paramViewTransition.getSharedValueID();
    final int j = paramViewTransition.getSharedValue();
    ConstraintLayout.getSharedValues().addListener(paramViewTransition.getSharedValueID(), new SharedValues.SharedValuesListener()
    {
      public void onNewValue(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
        paramAnonymousInt3 = paramViewTransition.getSharedValueCurrent();
        paramViewTransition.setSharedValueCurrent(paramAnonymousInt2);
        if ((i == paramAnonymousInt1) && (paramAnonymousInt3 != paramAnonymousInt2))
        {
          Object localObject1;
          Object localObject4;
          Object localObject2;
          Object localObject3;
          if (paramBoolean)
          {
            if (j == paramAnonymousInt2)
            {
              paramAnonymousInt2 = ViewTransitionController.this.mMotionLayout.getChildCount();
              for (paramAnonymousInt1 = 0; paramAnonymousInt1 < paramAnonymousInt2; paramAnonymousInt1++)
              {
                localObject1 = ViewTransitionController.this.mMotionLayout.getChildAt(paramAnonymousInt1);
                if (paramViewTransition.matchesView((View)localObject1))
                {
                  paramAnonymousInt3 = ViewTransitionController.this.mMotionLayout.getCurrentState();
                  localObject4 = ViewTransitionController.this.mMotionLayout.getConstraintSet(paramAnonymousInt3);
                  localObject2 = paramViewTransition;
                  localObject3 = ViewTransitionController.this;
                  ((ViewTransition)localObject2).applyTransition((ViewTransitionController)localObject3, ((ViewTransitionController)localObject3).mMotionLayout, paramAnonymousInt3, (ConstraintSet)localObject4, new View[] { localObject1 });
                }
              }
            }
          }
          else if (j != paramAnonymousInt2)
          {
            paramAnonymousInt2 = ViewTransitionController.this.mMotionLayout.getChildCount();
            for (paramAnonymousInt1 = 0; paramAnonymousInt1 < paramAnonymousInt2; paramAnonymousInt1++)
            {
              localObject2 = ViewTransitionController.this.mMotionLayout.getChildAt(paramAnonymousInt1);
              if (paramViewTransition.matchesView((View)localObject2))
              {
                paramAnonymousInt3 = ViewTransitionController.this.mMotionLayout.getCurrentState();
                localObject1 = ViewTransitionController.this.mMotionLayout.getConstraintSet(paramAnonymousInt3);
                localObject3 = paramViewTransition;
                localObject4 = ViewTransitionController.this;
                ((ViewTransition)localObject3).applyTransition((ViewTransitionController)localObject4, ((ViewTransitionController)localObject4).mMotionLayout, paramAnonymousInt3, (ConstraintSet)localObject1, new View[] { localObject2 });
              }
            }
          }
        }
      }
    });
  }
  
  private void viewTransition(ViewTransition paramViewTransition, View... paramVarArgs)
  {
    int i = this.mMotionLayout.getCurrentState();
    if (paramViewTransition.mViewTransitionMode != 2)
    {
      if (i == -1)
      {
        Log.w(this.TAG, "No support for ViewTransition within transition yet. Currently: " + this.mMotionLayout.toString());
        return;
      }
      ConstraintSet localConstraintSet = this.mMotionLayout.getConstraintSet(i);
      if (localConstraintSet == null) {
        return;
      }
      paramViewTransition.applyTransition(this, this.mMotionLayout, i, localConstraintSet, paramVarArgs);
    }
    else
    {
      paramViewTransition.applyTransition(this, this.mMotionLayout, i, null, paramVarArgs);
    }
  }
  
  public void add(ViewTransition paramViewTransition)
  {
    this.viewTransitions.add(paramViewTransition);
    this.mRelatedViews = null;
    if (paramViewTransition.getStateTransition() == 4) {
      listenForSharedVariable(paramViewTransition, true);
    } else if (paramViewTransition.getStateTransition() == 5) {
      listenForSharedVariable(paramViewTransition, false);
    }
  }
  
  void addAnimation(ViewTransition.Animate paramAnimate)
  {
    if (this.animations == null) {
      this.animations = new ArrayList();
    }
    this.animations.add(paramAnimate);
  }
  
  void animate()
  {
    Object localObject = this.animations;
    if (localObject == null) {
      return;
    }
    localObject = ((ArrayList)localObject).iterator();
    while (((Iterator)localObject).hasNext()) {
      ((ViewTransition.Animate)((Iterator)localObject).next()).mutate();
    }
    this.animations.removeAll(this.removeList);
    this.removeList.clear();
    if (this.animations.isEmpty()) {
      this.animations = null;
    }
  }
  
  boolean applyViewTransition(int paramInt, MotionController paramMotionController)
  {
    Iterator localIterator = this.viewTransitions.iterator();
    while (localIterator.hasNext())
    {
      ViewTransition localViewTransition = (ViewTransition)localIterator.next();
      if (localViewTransition.getId() == paramInt)
      {
        localViewTransition.mKeyFrames.addAllFrames(paramMotionController);
        return true;
      }
    }
    return false;
  }
  
  void enableViewTransition(int paramInt, boolean paramBoolean)
  {
    Iterator localIterator = this.viewTransitions.iterator();
    while (localIterator.hasNext())
    {
      ViewTransition localViewTransition = (ViewTransition)localIterator.next();
      if (localViewTransition.getId() == paramInt)
      {
        localViewTransition.setEnabled(paramBoolean);
        break;
      }
    }
  }
  
  void invalidate()
  {
    this.mMotionLayout.invalidate();
  }
  
  boolean isViewTransitionEnabled(int paramInt)
  {
    Iterator localIterator = this.viewTransitions.iterator();
    while (localIterator.hasNext())
    {
      ViewTransition localViewTransition = (ViewTransition)localIterator.next();
      if (localViewTransition.getId() == paramInt) {
        return localViewTransition.isEnabled();
      }
    }
    return false;
  }
  
  void remove(int paramInt)
  {
    Object localObject2 = null;
    Iterator localIterator = this.viewTransitions.iterator();
    Object localObject1;
    for (;;)
    {
      localObject1 = localObject2;
      if (!localIterator.hasNext()) {
        break;
      }
      localObject1 = (ViewTransition)localIterator.next();
      if (((ViewTransition)localObject1).getId() == paramInt) {
        break;
      }
    }
    if (localObject1 != null)
    {
      this.mRelatedViews = null;
      this.viewTransitions.remove(localObject1);
    }
  }
  
  void removeAnimation(ViewTransition.Animate paramAnimate)
  {
    this.removeList.add(paramAnimate);
  }
  
  void touchEvent(MotionEvent paramMotionEvent)
  {
    int j = this.mMotionLayout.getCurrentState();
    if (j == -1) {
      return;
    }
    Object localObject3;
    Object localObject2;
    if (this.mRelatedViews == null)
    {
      this.mRelatedViews = new HashSet();
      localObject3 = this.viewTransitions.iterator();
      while (((Iterator)localObject3).hasNext())
      {
        localObject2 = (ViewTransition)((Iterator)localObject3).next();
        int k = this.mMotionLayout.getChildCount();
        for (i = 0; i < k; i++)
        {
          localObject1 = this.mMotionLayout.getChildAt(i);
          if (((ViewTransition)localObject2).matchesView((View)localObject1))
          {
            ((View)localObject1).getId();
            this.mRelatedViews.add(localObject1);
          }
        }
      }
    }
    float f2 = paramMotionEvent.getX();
    float f1 = paramMotionEvent.getY();
    Object localObject1 = new Rect();
    int i = paramMotionEvent.getAction();
    paramMotionEvent = this.animations;
    if ((paramMotionEvent != null) && (!paramMotionEvent.isEmpty()))
    {
      paramMotionEvent = this.animations.iterator();
      while (paramMotionEvent.hasNext()) {
        ((ViewTransition.Animate)paramMotionEvent.next()).reactTo(i, f2, f1);
      }
    }
    switch (i)
    {
    default: 
      break;
    case 0: 
    case 1: 
      ConstraintSet localConstraintSet = this.mMotionLayout.getConstraintSet(j);
      Iterator localIterator = this.viewTransitions.iterator();
      while (localIterator.hasNext())
      {
        localObject3 = (ViewTransition)localIterator.next();
        if (((ViewTransition)localObject3).supports(i))
        {
          localObject2 = this.mRelatedViews.iterator();
          while (((Iterator)localObject2).hasNext())
          {
            paramMotionEvent = (View)((Iterator)localObject2).next();
            if (((ViewTransition)localObject3).matchesView(paramMotionEvent))
            {
              paramMotionEvent.getHitRect((Rect)localObject1);
              if (((Rect)localObject1).contains((int)f2, (int)f1)) {
                ((ViewTransition)localObject3).applyTransition(this, this.mMotionLayout, j, localConstraintSet, new View[] { paramMotionEvent });
              }
            }
          }
        }
      }
    }
  }
  
  void viewTransition(int paramInt, View... paramVarArgs)
  {
    Object localObject = null;
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.viewTransitions.iterator();
    while (localIterator.hasNext())
    {
      ViewTransition localViewTransition2 = (ViewTransition)localIterator.next();
      if (localViewTransition2.getId() == paramInt)
      {
        ViewTransition localViewTransition1 = localViewTransition2;
        int j = paramVarArgs.length;
        for (int i = 0; i < j; i++)
        {
          localObject = paramVarArgs[i];
          if (localViewTransition2.checkTags((View)localObject)) {
            localArrayList.add(localObject);
          }
        }
        localObject = localViewTransition1;
        if (!localArrayList.isEmpty())
        {
          viewTransition(localViewTransition1, (View[])localArrayList.toArray(new View[0]));
          localArrayList.clear();
          localObject = localViewTransition1;
        }
      }
    }
    if (localObject == null)
    {
      Log.e(this.TAG, " Could not find ViewTransition");
      return;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/motion/widget/ViewTransitionController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */