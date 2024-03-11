package androidx.constraintlayout.helper.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import androidx.constraintlayout.motion.widget.MotionHelper;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.motion.widget.MotionScene.Transition;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.ConstraintSet.Constraint;
import androidx.constraintlayout.widget.R.styleable;
import java.util.ArrayList;
import java.util.Iterator;

public class Carousel
  extends MotionHelper
{
  private static final boolean DEBUG = false;
  private static final String TAG = "Carousel";
  public static final int TOUCH_UP_CARRY_ON = 2;
  public static final int TOUCH_UP_IMMEDIATE_STOP = 1;
  private int backwardTransition = -1;
  private float dampening = 0.9F;
  private int emptyViewBehavior = 4;
  private int firstViewReference = -1;
  private int forwardTransition = -1;
  private boolean infiniteCarousel = false;
  private Adapter mAdapter = null;
  private int mAnimateTargetDelay = 200;
  private int mIndex = 0;
  int mLastStartId = -1;
  private final ArrayList<View> mList = new ArrayList();
  private MotionLayout mMotionLayout;
  private int mPreviousIndex = 0;
  private int mTargetIndex = -1;
  Runnable mUpdateRunnable = new Runnable()
  {
    public void run()
    {
      Carousel.this.mMotionLayout.setProgress(0.0F);
      Carousel.this.updateItems();
      Carousel.this.mAdapter.onNewItem(Carousel.this.mIndex);
      float f2 = Carousel.this.mMotionLayout.getVelocity();
      if ((Carousel.this.touchUpMode == 2) && (f2 > Carousel.this.velocityThreshold) && (Carousel.this.mIndex < Carousel.this.mAdapter.count() - 1))
      {
        float f1 = Carousel.this.dampening;
        if ((Carousel.this.mIndex == 0) && (Carousel.this.mPreviousIndex > Carousel.this.mIndex)) {
          return;
        }
        if ((Carousel.this.mIndex == Carousel.this.mAdapter.count() - 1) && (Carousel.this.mPreviousIndex < Carousel.this.mIndex)) {
          return;
        }
        Carousel.this.mMotionLayout.post(new Runnable()
        {
          public void run()
          {
            Carousel.this.mMotionLayout.touchAnimateTo(5, 1.0F, this.val$v);
          }
        });
      }
    }
  };
  private int nextState = -1;
  private int previousState = -1;
  private int startIndex = 0;
  private int touchUpMode = 1;
  private float velocityThreshold = 2.0F;
  
  public Carousel(Context paramContext)
  {
    super(paramContext);
  }
  
  public Carousel(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramContext, paramAttributeSet);
  }
  
  public Carousel(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext, paramAttributeSet);
  }
  
  private void enableAllTransitions(boolean paramBoolean)
  {
    Iterator localIterator = this.mMotionLayout.getDefinedTransitions().iterator();
    while (localIterator.hasNext()) {
      ((MotionScene.Transition)localIterator.next()).setEnabled(paramBoolean);
    }
  }
  
  private boolean enableTransition(int paramInt, boolean paramBoolean)
  {
    if (paramInt == -1) {
      return false;
    }
    Object localObject = this.mMotionLayout;
    if (localObject == null) {
      return false;
    }
    localObject = ((MotionLayout)localObject).getTransition(paramInt);
    if (localObject == null) {
      return false;
    }
    if (paramBoolean == ((MotionScene.Transition)localObject).isEnabled()) {
      return false;
    }
    ((MotionScene.Transition)localObject).setEnabled(paramBoolean);
    return true;
  }
  
  private void init(Context paramContext, AttributeSet paramAttributeSet)
  {
    if (paramAttributeSet != null)
    {
      paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.Carousel);
      int j = paramContext.getIndexCount();
      for (int i = 0; i < j; i++)
      {
        int k = paramContext.getIndex(i);
        if (k == R.styleable.Carousel_carousel_firstView) {
          this.firstViewReference = paramContext.getResourceId(k, this.firstViewReference);
        } else if (k == R.styleable.Carousel_carousel_backwardTransition) {
          this.backwardTransition = paramContext.getResourceId(k, this.backwardTransition);
        } else if (k == R.styleable.Carousel_carousel_forwardTransition) {
          this.forwardTransition = paramContext.getResourceId(k, this.forwardTransition);
        } else if (k == R.styleable.Carousel_carousel_emptyViewsBehavior) {
          this.emptyViewBehavior = paramContext.getInt(k, this.emptyViewBehavior);
        } else if (k == R.styleable.Carousel_carousel_previousState) {
          this.previousState = paramContext.getResourceId(k, this.previousState);
        } else if (k == R.styleable.Carousel_carousel_nextState) {
          this.nextState = paramContext.getResourceId(k, this.nextState);
        } else if (k == R.styleable.Carousel_carousel_touchUp_dampeningFactor) {
          this.dampening = paramContext.getFloat(k, this.dampening);
        } else if (k == R.styleable.Carousel_carousel_touchUpMode) {
          this.touchUpMode = paramContext.getInt(k, this.touchUpMode);
        } else if (k == R.styleable.Carousel_carousel_touchUp_velocityThreshold) {
          this.velocityThreshold = paramContext.getFloat(k, this.velocityThreshold);
        } else if (k == R.styleable.Carousel_carousel_infinite) {
          this.infiniteCarousel = paramContext.getBoolean(k, this.infiniteCarousel);
        }
      }
      paramContext.recycle();
    }
  }
  
  private void updateItems()
  {
    Adapter localAdapter = this.mAdapter;
    if (localAdapter == null) {
      return;
    }
    if (this.mMotionLayout == null) {
      return;
    }
    if (localAdapter.count() == 0) {
      return;
    }
    int m = this.mList.size();
    for (int j = 0; j < m; j++)
    {
      View localView = (View)this.mList.get(j);
      int k = this.mIndex + j - this.startIndex;
      if (this.infiniteCarousel)
      {
        if (k < 0)
        {
          i = this.emptyViewBehavior;
          if (i != 4) {
            updateViewVisibility(localView, i);
          } else {
            updateViewVisibility(localView, 0);
          }
          if (k % this.mAdapter.count() == 0)
          {
            this.mAdapter.populate(localView, 0);
          }
          else
          {
            localAdapter = this.mAdapter;
            localAdapter.populate(localView, localAdapter.count() + k % this.mAdapter.count());
          }
        }
        else if (k >= this.mAdapter.count())
        {
          if (k == this.mAdapter.count())
          {
            i = 0;
          }
          else
          {
            i = k;
            if (k > this.mAdapter.count()) {
              i = k % this.mAdapter.count();
            }
          }
          k = this.emptyViewBehavior;
          if (k != 4) {
            updateViewVisibility(localView, k);
          } else {
            updateViewVisibility(localView, 0);
          }
          this.mAdapter.populate(localView, i);
        }
        else
        {
          updateViewVisibility(localView, 0);
          this.mAdapter.populate(localView, k);
        }
      }
      else if (k < 0)
      {
        updateViewVisibility(localView, this.emptyViewBehavior);
      }
      else if (k >= this.mAdapter.count())
      {
        updateViewVisibility(localView, this.emptyViewBehavior);
      }
      else
      {
        updateViewVisibility(localView, 0);
        this.mAdapter.populate(localView, k);
      }
    }
    int i = this.mTargetIndex;
    if ((i != -1) && (i != this.mIndex)) {
      this.mMotionLayout.post(new Carousel..ExternalSyntheticLambda0(this));
    } else if (i == this.mIndex) {
      this.mTargetIndex = -1;
    }
    if ((this.backwardTransition != -1) && (this.forwardTransition != -1))
    {
      if (this.infiniteCarousel) {
        return;
      }
      i = this.mAdapter.count();
      if (this.mIndex == 0)
      {
        enableTransition(this.backwardTransition, false);
      }
      else
      {
        enableTransition(this.backwardTransition, true);
        this.mMotionLayout.setTransition(this.backwardTransition);
      }
      if (this.mIndex == i - 1)
      {
        enableTransition(this.forwardTransition, false);
      }
      else
      {
        enableTransition(this.forwardTransition, true);
        this.mMotionLayout.setTransition(this.forwardTransition);
      }
      return;
    }
    Log.w("Carousel", "No backward or forward transitions defined for Carousel!");
  }
  
  private boolean updateViewVisibility(int paramInt1, View paramView, int paramInt2)
  {
    Object localObject = this.mMotionLayout.getConstraintSet(paramInt1);
    if (localObject == null) {
      return false;
    }
    localObject = ((ConstraintSet)localObject).getConstraint(paramView.getId());
    if (localObject == null) {
      return false;
    }
    ((ConstraintSet.Constraint)localObject).propertySet.mVisibilityMode = 1;
    paramView.setVisibility(paramInt2);
    return true;
  }
  
  private boolean updateViewVisibility(View paramView, int paramInt)
  {
    Object localObject = this.mMotionLayout;
    if (localObject == null) {
      return false;
    }
    boolean bool = false;
    localObject = ((MotionLayout)localObject).getConstraintSetIds();
    for (int i = 0; i < localObject.length; i++) {
      bool |= updateViewVisibility(localObject[i], paramView, paramInt);
    }
    return bool;
  }
  
  public int getCount()
  {
    Adapter localAdapter = this.mAdapter;
    if (localAdapter != null) {
      return localAdapter.count();
    }
    return 0;
  }
  
  public int getCurrentIndex()
  {
    return this.mIndex;
  }
  
  public void jumpToIndex(int paramInt)
  {
    this.mIndex = Math.max(0, Math.min(getCount() - 1, paramInt));
    refresh();
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    if ((getParent() instanceof MotionLayout))
    {
      MotionLayout localMotionLayout = (MotionLayout)getParent();
      Object localObject;
      for (int i = 0; i < this.mCount; i++)
      {
        int j = this.mIds[i];
        localObject = localMotionLayout.getViewById(j);
        if (this.firstViewReference == j) {
          this.startIndex = i;
        }
        this.mList.add(localObject);
      }
      this.mMotionLayout = localMotionLayout;
      if (this.touchUpMode == 2)
      {
        localObject = localMotionLayout.getTransition(this.forwardTransition);
        if (localObject != null) {
          ((MotionScene.Transition)localObject).setOnTouchUp(5);
        }
        localObject = this.mMotionLayout.getTransition(this.backwardTransition);
        if (localObject != null) {
          ((MotionScene.Transition)localObject).setOnTouchUp(5);
        }
      }
      updateItems();
      return;
    }
  }
  
  public void onTransitionChange(MotionLayout paramMotionLayout, int paramInt1, int paramInt2, float paramFloat)
  {
    this.mLastStartId = paramInt1;
  }
  
  public void onTransitionCompleted(MotionLayout paramMotionLayout, int paramInt)
  {
    int i = this.mIndex;
    this.mPreviousIndex = i;
    if (paramInt == this.nextState) {
      this.mIndex = (i + 1);
    } else if (paramInt == this.previousState) {
      this.mIndex = (i - 1);
    }
    if (this.infiniteCarousel)
    {
      if (this.mIndex >= this.mAdapter.count()) {
        this.mIndex = 0;
      }
      if (this.mIndex < 0) {
        this.mIndex = (this.mAdapter.count() - 1);
      }
    }
    else
    {
      if (this.mIndex >= this.mAdapter.count()) {
        this.mIndex = (this.mAdapter.count() - 1);
      }
      if (this.mIndex < 0) {
        this.mIndex = 0;
      }
    }
    if (this.mPreviousIndex != this.mIndex) {
      this.mMotionLayout.post(this.mUpdateRunnable);
    }
  }
  
  public void refresh()
  {
    int j = this.mList.size();
    for (int i = 0; i < j; i++)
    {
      View localView = (View)this.mList.get(i);
      if (this.mAdapter.count() == 0) {
        updateViewVisibility(localView, this.emptyViewBehavior);
      } else {
        updateViewVisibility(localView, 0);
      }
    }
    this.mMotionLayout.rebuildScene();
    updateItems();
  }
  
  public void setAdapter(Adapter paramAdapter)
  {
    this.mAdapter = paramAdapter;
  }
  
  public void transitionToIndex(int paramInt1, int paramInt2)
  {
    this.mTargetIndex = Math.max(0, Math.min(getCount() - 1, paramInt1));
    paramInt2 = Math.max(0, paramInt2);
    this.mAnimateTargetDelay = paramInt2;
    this.mMotionLayout.setTransitionDuration(paramInt2);
    if (paramInt1 < this.mIndex) {
      this.mMotionLayout.transitionToState(this.previousState, this.mAnimateTargetDelay);
    } else {
      this.mMotionLayout.transitionToState(this.nextState, this.mAnimateTargetDelay);
    }
  }
  
  public static abstract interface Adapter
  {
    public abstract int count();
    
    public abstract void onNewItem(int paramInt);
    
    public abstract void populate(View paramView, int paramInt);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/helper/widget/Carousel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */