package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.InflateException;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.collection.ArrayMap;
import androidx.collection.LongSparseArray;
import androidx.collection.SimpleArrayMap;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.view.ViewCompat;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public abstract class Transition
  implements Cloneable
{
  static final boolean DBG = false;
  private static final int[] DEFAULT_MATCH_ORDER = { 2, 1, 3, 4 };
  private static final String LOG_TAG = "Transition";
  private static final int MATCH_FIRST = 1;
  public static final int MATCH_ID = 3;
  private static final String MATCH_ID_STR = "id";
  public static final int MATCH_INSTANCE = 1;
  private static final String MATCH_INSTANCE_STR = "instance";
  public static final int MATCH_ITEM_ID = 4;
  private static final String MATCH_ITEM_ID_STR = "itemId";
  private static final int MATCH_LAST = 4;
  public static final int MATCH_NAME = 2;
  private static final String MATCH_NAME_STR = "name";
  private static final PathMotion STRAIGHT_PATH_MOTION = new PathMotion()
  {
    public Path getPath(float paramAnonymousFloat1, float paramAnonymousFloat2, float paramAnonymousFloat3, float paramAnonymousFloat4)
    {
      Path localPath = new Path();
      localPath.moveTo(paramAnonymousFloat1, paramAnonymousFloat2);
      localPath.lineTo(paramAnonymousFloat3, paramAnonymousFloat4);
      return localPath;
    }
  };
  private static ThreadLocal<ArrayMap<Animator, AnimationInfo>> sRunningAnimators = new ThreadLocal();
  private ArrayList<Animator> mAnimators = new ArrayList();
  boolean mCanRemoveViews = false;
  ArrayList<Animator> mCurrentAnimators = new ArrayList();
  long mDuration = -1L;
  private TransitionValuesMaps mEndValues = new TransitionValuesMaps();
  private ArrayList<TransitionValues> mEndValuesList;
  private boolean mEnded = false;
  private EpicenterCallback mEpicenterCallback;
  private TimeInterpolator mInterpolator = null;
  private ArrayList<TransitionListener> mListeners = null;
  private int[] mMatchOrder = DEFAULT_MATCH_ORDER;
  private String mName = getClass().getName();
  private ArrayMap<String, String> mNameOverrides;
  private int mNumInstances = 0;
  TransitionSet mParent = null;
  private PathMotion mPathMotion = STRAIGHT_PATH_MOTION;
  private boolean mPaused = false;
  TransitionPropagation mPropagation;
  private ViewGroup mSceneRoot = null;
  private long mStartDelay = -1L;
  private TransitionValuesMaps mStartValues = new TransitionValuesMaps();
  private ArrayList<TransitionValues> mStartValuesList;
  private ArrayList<View> mTargetChildExcludes = null;
  private ArrayList<View> mTargetExcludes = null;
  private ArrayList<Integer> mTargetIdChildExcludes = null;
  private ArrayList<Integer> mTargetIdExcludes = null;
  ArrayList<Integer> mTargetIds = new ArrayList();
  private ArrayList<String> mTargetNameExcludes = null;
  private ArrayList<String> mTargetNames = null;
  private ArrayList<Class<?>> mTargetTypeChildExcludes = null;
  private ArrayList<Class<?>> mTargetTypeExcludes = null;
  private ArrayList<Class<?>> mTargetTypes = null;
  ArrayList<View> mTargets = new ArrayList();
  
  public Transition() {}
  
  public Transition(Context paramContext, AttributeSet paramAttributeSet)
  {
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, Styleable.TRANSITION);
    paramAttributeSet = (XmlResourceParser)paramAttributeSet;
    long l = TypedArrayUtils.getNamedInt(localTypedArray, paramAttributeSet, "duration", 1, -1);
    if (l >= 0L) {
      setDuration(l);
    }
    l = TypedArrayUtils.getNamedInt(localTypedArray, paramAttributeSet, "startDelay", 2, -1);
    if (l > 0L) {
      setStartDelay(l);
    }
    int i = TypedArrayUtils.getNamedResourceId(localTypedArray, paramAttributeSet, "interpolator", 0, 0);
    if (i > 0) {
      setInterpolator(AnimationUtils.loadInterpolator(paramContext, i));
    }
    paramContext = TypedArrayUtils.getNamedString(localTypedArray, paramAttributeSet, "matchOrder", 3);
    Log5ECF72.a(paramContext);
    LogE84000.a(paramContext);
    Log229316.a(paramContext);
    if (paramContext != null) {
      setMatchOrder(parseMatchOrder(paramContext));
    }
    localTypedArray.recycle();
  }
  
  private void addUnmatched(ArrayMap<View, TransitionValues> paramArrayMap1, ArrayMap<View, TransitionValues> paramArrayMap2)
  {
    for (int i = 0; i < paramArrayMap1.size(); i++)
    {
      TransitionValues localTransitionValues = (TransitionValues)paramArrayMap1.valueAt(i);
      if (isValidTarget(localTransitionValues.view))
      {
        this.mStartValuesList.add(localTransitionValues);
        this.mEndValuesList.add(null);
      }
    }
    for (i = 0; i < paramArrayMap2.size(); i++)
    {
      paramArrayMap1 = (TransitionValues)paramArrayMap2.valueAt(i);
      if (isValidTarget(paramArrayMap1.view))
      {
        this.mEndValuesList.add(paramArrayMap1);
        this.mStartValuesList.add(null);
      }
    }
  }
  
  private static void addViewValues(TransitionValuesMaps paramTransitionValuesMaps, View paramView, TransitionValues paramTransitionValues)
  {
    paramTransitionValuesMaps.mViewValues.put(paramView, paramTransitionValues);
    int i = paramView.getId();
    if (i >= 0) {
      if (paramTransitionValuesMaps.mIdValues.indexOfKey(i) >= 0) {
        paramTransitionValuesMaps.mIdValues.put(i, null);
      } else {
        paramTransitionValuesMaps.mIdValues.put(i, paramView);
      }
    }
    paramTransitionValues = ViewCompat.getTransitionName(paramView);
    Log5ECF72.a(paramTransitionValues);
    LogE84000.a(paramTransitionValues);
    Log229316.a(paramTransitionValues);
    if (paramTransitionValues != null) {
      if (paramTransitionValuesMaps.mNameValues.containsKey(paramTransitionValues)) {
        paramTransitionValuesMaps.mNameValues.put(paramTransitionValues, null);
      } else {
        paramTransitionValuesMaps.mNameValues.put(paramTransitionValues, paramView);
      }
    }
    if ((paramView.getParent() instanceof ListView))
    {
      paramTransitionValues = (ListView)paramView.getParent();
      if (paramTransitionValues.getAdapter().hasStableIds())
      {
        long l = paramTransitionValues.getItemIdAtPosition(paramTransitionValues.getPositionForView(paramView));
        if (paramTransitionValuesMaps.mItemIdValues.indexOfKey(l) >= 0)
        {
          paramView = (View)paramTransitionValuesMaps.mItemIdValues.get(l);
          if (paramView != null)
          {
            ViewCompat.setHasTransientState(paramView, false);
            paramTransitionValuesMaps.mItemIdValues.put(l, null);
          }
        }
        else
        {
          ViewCompat.setHasTransientState(paramView, true);
          paramTransitionValuesMaps.mItemIdValues.put(l, paramView);
        }
      }
    }
  }
  
  private static boolean alreadyContains(int[] paramArrayOfInt, int paramInt)
  {
    int j = paramArrayOfInt[paramInt];
    for (int i = 0; i < paramInt; i++) {
      if (paramArrayOfInt[i] == j) {
        return true;
      }
    }
    return false;
  }
  
  private void captureHierarchy(View paramView, boolean paramBoolean)
  {
    if (paramView == null) {
      return;
    }
    int k = paramView.getId();
    Object localObject = this.mTargetIdExcludes;
    if ((localObject != null) && (((ArrayList)localObject).contains(Integer.valueOf(k)))) {
      return;
    }
    localObject = this.mTargetExcludes;
    if ((localObject != null) && (((ArrayList)localObject).contains(paramView))) {
      return;
    }
    localObject = this.mTargetTypeExcludes;
    int j;
    int i;
    if (localObject != null)
    {
      j = ((ArrayList)localObject).size();
      for (i = 0; i < j; i++) {
        if (((Class)this.mTargetTypeExcludes.get(i)).isInstance(paramView)) {
          return;
        }
      }
    }
    if ((paramView.getParent() instanceof ViewGroup))
    {
      localObject = new TransitionValues(paramView);
      if (paramBoolean) {
        captureStartValues((TransitionValues)localObject);
      } else {
        captureEndValues((TransitionValues)localObject);
      }
      ((TransitionValues)localObject).mTargetedTransitions.add(this);
      capturePropagationValues((TransitionValues)localObject);
      if (paramBoolean) {
        addViewValues(this.mStartValues, paramView, (TransitionValues)localObject);
      } else {
        addViewValues(this.mEndValues, paramView, (TransitionValues)localObject);
      }
    }
    if ((paramView instanceof ViewGroup))
    {
      localObject = this.mTargetIdChildExcludes;
      if ((localObject != null) && (((ArrayList)localObject).contains(Integer.valueOf(k)))) {
        return;
      }
      localObject = this.mTargetChildExcludes;
      if ((localObject != null) && (((ArrayList)localObject).contains(paramView))) {
        return;
      }
      localObject = this.mTargetTypeChildExcludes;
      if (localObject != null)
      {
        j = ((ArrayList)localObject).size();
        for (i = 0; i < j; i++) {
          if (((Class)this.mTargetTypeChildExcludes.get(i)).isInstance(paramView)) {
            return;
          }
        }
      }
      paramView = (ViewGroup)paramView;
      for (i = 0; i < paramView.getChildCount(); i++) {
        captureHierarchy(paramView.getChildAt(i), paramBoolean);
      }
    }
  }
  
  private ArrayList<Integer> excludeId(ArrayList<Integer> paramArrayList, int paramInt, boolean paramBoolean)
  {
    Object localObject = paramArrayList;
    if (paramInt > 0) {
      if (paramBoolean) {
        localObject = ArrayListManager.add(paramArrayList, Integer.valueOf(paramInt));
      } else {
        localObject = ArrayListManager.remove(paramArrayList, Integer.valueOf(paramInt));
      }
    }
    return (ArrayList<Integer>)localObject;
  }
  
  private static <T> ArrayList<T> excludeObject(ArrayList<T> paramArrayList, T paramT, boolean paramBoolean)
  {
    Object localObject = paramArrayList;
    if (paramT != null) {
      if (paramBoolean) {
        localObject = ArrayListManager.add(paramArrayList, paramT);
      } else {
        localObject = ArrayListManager.remove(paramArrayList, paramT);
      }
    }
    return (ArrayList<T>)localObject;
  }
  
  private ArrayList<Class<?>> excludeType(ArrayList<Class<?>> paramArrayList, Class<?> paramClass, boolean paramBoolean)
  {
    Object localObject = paramArrayList;
    if (paramClass != null) {
      if (paramBoolean) {
        localObject = ArrayListManager.add(paramArrayList, paramClass);
      } else {
        localObject = ArrayListManager.remove(paramArrayList, paramClass);
      }
    }
    return (ArrayList<Class<?>>)localObject;
  }
  
  private ArrayList<View> excludeView(ArrayList<View> paramArrayList, View paramView, boolean paramBoolean)
  {
    Object localObject = paramArrayList;
    if (paramView != null) {
      if (paramBoolean) {
        localObject = ArrayListManager.add(paramArrayList, paramView);
      } else {
        localObject = ArrayListManager.remove(paramArrayList, paramView);
      }
    }
    return (ArrayList<View>)localObject;
  }
  
  private static ArrayMap<Animator, AnimationInfo> getRunningAnimators()
  {
    ArrayMap localArrayMap2 = (ArrayMap)sRunningAnimators.get();
    ArrayMap localArrayMap1 = localArrayMap2;
    if (localArrayMap2 == null)
    {
      localArrayMap1 = new ArrayMap();
      sRunningAnimators.set(localArrayMap1);
    }
    return localArrayMap1;
  }
  
  private static boolean isValidMatch(int paramInt)
  {
    boolean bool = true;
    if ((paramInt < 1) || (paramInt > 4)) {
      bool = false;
    }
    return bool;
  }
  
  private static boolean isValueChanged(TransitionValues paramTransitionValues1, TransitionValues paramTransitionValues2, String paramString)
  {
    paramTransitionValues1 = paramTransitionValues1.values.get(paramString);
    paramTransitionValues2 = paramTransitionValues2.values.get(paramString);
    boolean bool;
    if ((paramTransitionValues1 == null) && (paramTransitionValues2 == null)) {
      bool = false;
    } else if ((paramTransitionValues1 != null) && (paramTransitionValues2 != null)) {
      bool = paramTransitionValues1.equals(paramTransitionValues2) ^ true;
    } else {
      bool = true;
    }
    return bool;
  }
  
  private void matchIds(ArrayMap<View, TransitionValues> paramArrayMap1, ArrayMap<View, TransitionValues> paramArrayMap2, SparseArray<View> paramSparseArray1, SparseArray<View> paramSparseArray2)
  {
    int j = paramSparseArray1.size();
    for (int i = 0; i < j; i++)
    {
      View localView1 = (View)paramSparseArray1.valueAt(i);
      if ((localView1 != null) && (isValidTarget(localView1)))
      {
        View localView2 = (View)paramSparseArray2.get(paramSparseArray1.keyAt(i));
        if ((localView2 != null) && (isValidTarget(localView2)))
        {
          TransitionValues localTransitionValues1 = (TransitionValues)paramArrayMap1.get(localView1);
          TransitionValues localTransitionValues2 = (TransitionValues)paramArrayMap2.get(localView2);
          if ((localTransitionValues1 != null) && (localTransitionValues2 != null))
          {
            this.mStartValuesList.add(localTransitionValues1);
            this.mEndValuesList.add(localTransitionValues2);
            paramArrayMap1.remove(localView1);
            paramArrayMap2.remove(localView2);
          }
        }
      }
    }
  }
  
  private void matchInstances(ArrayMap<View, TransitionValues> paramArrayMap1, ArrayMap<View, TransitionValues> paramArrayMap2)
  {
    for (int i = paramArrayMap1.size() - 1; i >= 0; i--)
    {
      Object localObject = (View)paramArrayMap1.keyAt(i);
      if ((localObject != null) && (isValidTarget((View)localObject)))
      {
        TransitionValues localTransitionValues = (TransitionValues)paramArrayMap2.remove(localObject);
        if ((localTransitionValues != null) && (isValidTarget(localTransitionValues.view)))
        {
          localObject = (TransitionValues)paramArrayMap1.removeAt(i);
          this.mStartValuesList.add(localObject);
          this.mEndValuesList.add(localTransitionValues);
        }
      }
    }
  }
  
  private void matchItemIds(ArrayMap<View, TransitionValues> paramArrayMap1, ArrayMap<View, TransitionValues> paramArrayMap2, LongSparseArray<View> paramLongSparseArray1, LongSparseArray<View> paramLongSparseArray2)
  {
    int j = paramLongSparseArray1.size();
    for (int i = 0; i < j; i++)
    {
      View localView1 = (View)paramLongSparseArray1.valueAt(i);
      if ((localView1 != null) && (isValidTarget(localView1)))
      {
        View localView2 = (View)paramLongSparseArray2.get(paramLongSparseArray1.keyAt(i));
        if ((localView2 != null) && (isValidTarget(localView2)))
        {
          TransitionValues localTransitionValues1 = (TransitionValues)paramArrayMap1.get(localView1);
          TransitionValues localTransitionValues2 = (TransitionValues)paramArrayMap2.get(localView2);
          if ((localTransitionValues1 != null) && (localTransitionValues2 != null))
          {
            this.mStartValuesList.add(localTransitionValues1);
            this.mEndValuesList.add(localTransitionValues2);
            paramArrayMap1.remove(localView1);
            paramArrayMap2.remove(localView2);
          }
        }
      }
    }
  }
  
  private void matchNames(ArrayMap<View, TransitionValues> paramArrayMap1, ArrayMap<View, TransitionValues> paramArrayMap2, ArrayMap<String, View> paramArrayMap3, ArrayMap<String, View> paramArrayMap4)
  {
    int j = paramArrayMap3.size();
    for (int i = 0; i < j; i++)
    {
      View localView1 = (View)paramArrayMap3.valueAt(i);
      if ((localView1 != null) && (isValidTarget(localView1)))
      {
        View localView2 = (View)paramArrayMap4.get(paramArrayMap3.keyAt(i));
        if ((localView2 != null) && (isValidTarget(localView2)))
        {
          TransitionValues localTransitionValues1 = (TransitionValues)paramArrayMap1.get(localView1);
          TransitionValues localTransitionValues2 = (TransitionValues)paramArrayMap2.get(localView2);
          if ((localTransitionValues1 != null) && (localTransitionValues2 != null))
          {
            this.mStartValuesList.add(localTransitionValues1);
            this.mEndValuesList.add(localTransitionValues2);
            paramArrayMap1.remove(localView1);
            paramArrayMap2.remove(localView2);
          }
        }
      }
    }
  }
  
  private void matchStartAndEnd(TransitionValuesMaps paramTransitionValuesMaps1, TransitionValuesMaps paramTransitionValuesMaps2)
  {
    ArrayMap localArrayMap1 = new ArrayMap(paramTransitionValuesMaps1.mViewValues);
    ArrayMap localArrayMap2 = new ArrayMap(paramTransitionValuesMaps2.mViewValues);
    for (int i = 0;; i++)
    {
      int[] arrayOfInt = this.mMatchOrder;
      if (i >= arrayOfInt.length) {
        break;
      }
      switch (arrayOfInt[i])
      {
      default: 
        break;
      case 4: 
        matchItemIds(localArrayMap1, localArrayMap2, paramTransitionValuesMaps1.mItemIdValues, paramTransitionValuesMaps2.mItemIdValues);
        break;
      case 3: 
        matchIds(localArrayMap1, localArrayMap2, paramTransitionValuesMaps1.mIdValues, paramTransitionValuesMaps2.mIdValues);
        break;
      case 2: 
        matchNames(localArrayMap1, localArrayMap2, paramTransitionValuesMaps1.mNameValues, paramTransitionValuesMaps2.mNameValues);
        break;
      case 1: 
        matchInstances(localArrayMap1, localArrayMap2);
      }
    }
    addUnmatched(localArrayMap1, localArrayMap2);
  }
  
  private static int[] parseMatchOrder(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",");
    paramString = new int[localStringTokenizer.countTokens()];
    int i = 0;
    while (localStringTokenizer.hasMoreTokens())
    {
      Object localObject = localStringTokenizer.nextToken().trim();
      if ("id".equalsIgnoreCase((String)localObject))
      {
        paramString[i] = 3;
      }
      else if ("instance".equalsIgnoreCase((String)localObject))
      {
        paramString[i] = 1;
      }
      else if ("name".equalsIgnoreCase((String)localObject))
      {
        paramString[i] = 2;
      }
      else if ("itemId".equalsIgnoreCase((String)localObject))
      {
        paramString[i] = 4;
      }
      else
      {
        if (!((String)localObject).isEmpty()) {
          break label133;
        }
        localObject = new int[paramString.length - 1];
        System.arraycopy(paramString, 0, localObject, 0, i);
        paramString = (String)localObject;
        i--;
      }
      i++;
      continue;
      label133:
      throw new InflateException("Unknown match type in matchOrder: '" + (String)localObject + "'");
    }
    return paramString;
  }
  
  private void runAnimator(Animator paramAnimator, final ArrayMap<Animator, AnimationInfo> paramArrayMap)
  {
    if (paramAnimator != null)
    {
      paramAnimator.addListener(new AnimatorListenerAdapter()
      {
        public void onAnimationEnd(Animator paramAnonymousAnimator)
        {
          paramArrayMap.remove(paramAnonymousAnimator);
          Transition.this.mCurrentAnimators.remove(paramAnonymousAnimator);
        }
        
        public void onAnimationStart(Animator paramAnonymousAnimator)
        {
          Transition.this.mCurrentAnimators.add(paramAnonymousAnimator);
        }
      });
      animate(paramAnimator);
    }
  }
  
  public Transition addListener(TransitionListener paramTransitionListener)
  {
    if (this.mListeners == null) {
      this.mListeners = new ArrayList();
    }
    this.mListeners.add(paramTransitionListener);
    return this;
  }
  
  public Transition addTarget(int paramInt)
  {
    if (paramInt != 0) {
      this.mTargetIds.add(Integer.valueOf(paramInt));
    }
    return this;
  }
  
  public Transition addTarget(View paramView)
  {
    this.mTargets.add(paramView);
    return this;
  }
  
  public Transition addTarget(Class<?> paramClass)
  {
    if (this.mTargetTypes == null) {
      this.mTargetTypes = new ArrayList();
    }
    this.mTargetTypes.add(paramClass);
    return this;
  }
  
  public Transition addTarget(String paramString)
  {
    if (this.mTargetNames == null) {
      this.mTargetNames = new ArrayList();
    }
    this.mTargetNames.add(paramString);
    return this;
  }
  
  protected void animate(Animator paramAnimator)
  {
    if (paramAnimator == null)
    {
      end();
    }
    else
    {
      if (getDuration() >= 0L) {
        paramAnimator.setDuration(getDuration());
      }
      if (getStartDelay() >= 0L) {
        paramAnimator.setStartDelay(getStartDelay() + paramAnimator.getStartDelay());
      }
      if (getInterpolator() != null) {
        paramAnimator.setInterpolator(getInterpolator());
      }
      paramAnimator.addListener(new AnimatorListenerAdapter()
      {
        public void onAnimationEnd(Animator paramAnonymousAnimator)
        {
          Transition.this.end();
          paramAnonymousAnimator.removeListener(this);
        }
      });
      paramAnimator.start();
    }
  }
  
  protected void cancel()
  {
    for (int i = this.mCurrentAnimators.size() - 1; i >= 0; i--) {
      ((Animator)this.mCurrentAnimators.get(i)).cancel();
    }
    ArrayList localArrayList = this.mListeners;
    if ((localArrayList != null) && (localArrayList.size() > 0))
    {
      localArrayList = (ArrayList)this.mListeners.clone();
      int j = localArrayList.size();
      for (i = 0; i < j; i++) {
        ((TransitionListener)localArrayList.get(i)).onTransitionCancel(this);
      }
    }
  }
  
  public abstract void captureEndValues(TransitionValues paramTransitionValues);
  
  void capturePropagationValues(TransitionValues paramTransitionValues)
  {
    if ((this.mPropagation != null) && (!paramTransitionValues.values.isEmpty()))
    {
      String[] arrayOfString = this.mPropagation.getPropagationProperties();
      if (arrayOfString == null) {
        return;
      }
      int k = 1;
      int i;
      for (int j = 0;; j++)
      {
        i = k;
        if (j >= arrayOfString.length) {
          break;
        }
        if (!paramTransitionValues.values.containsKey(arrayOfString[j]))
        {
          i = 0;
          break;
        }
      }
      if (i == 0) {
        this.mPropagation.captureValues(paramTransitionValues);
      }
    }
  }
  
  public abstract void captureStartValues(TransitionValues paramTransitionValues);
  
  void captureValues(ViewGroup paramViewGroup, boolean paramBoolean)
  {
    clearValues(paramBoolean);
    Object localObject;
    if ((this.mTargetIds.size() > 0) || (this.mTargets.size() > 0))
    {
      localObject = this.mTargetNames;
      if ((localObject == null) || (((ArrayList)localObject).isEmpty()))
      {
        localObject = this.mTargetTypes;
        if ((localObject == null) || (((ArrayList)localObject).isEmpty())) {
          break label75;
        }
      }
    }
    captureHierarchy(paramViewGroup, paramBoolean);
    break label294;
    label75:
    View localView;
    for (int i = 0; i < this.mTargetIds.size(); i++)
    {
      localView = paramViewGroup.findViewById(((Integer)this.mTargetIds.get(i)).intValue());
      if (localView != null)
      {
        localObject = new TransitionValues(localView);
        if (paramBoolean) {
          captureStartValues((TransitionValues)localObject);
        } else {
          captureEndValues((TransitionValues)localObject);
        }
        ((TransitionValues)localObject).mTargetedTransitions.add(this);
        capturePropagationValues((TransitionValues)localObject);
        if (paramBoolean) {
          addViewValues(this.mStartValues, localView, (TransitionValues)localObject);
        } else {
          addViewValues(this.mEndValues, localView, (TransitionValues)localObject);
        }
      }
    }
    for (i = 0; i < this.mTargets.size(); i++)
    {
      localObject = (View)this.mTargets.get(i);
      paramViewGroup = new TransitionValues((View)localObject);
      if (paramBoolean) {
        captureStartValues(paramViewGroup);
      } else {
        captureEndValues(paramViewGroup);
      }
      paramViewGroup.mTargetedTransitions.add(this);
      capturePropagationValues(paramViewGroup);
      if (paramBoolean) {
        addViewValues(this.mStartValues, (View)localObject, paramViewGroup);
      } else {
        addViewValues(this.mEndValues, (View)localObject, paramViewGroup);
      }
    }
    label294:
    if (!paramBoolean)
    {
      paramViewGroup = this.mNameOverrides;
      if (paramViewGroup != null)
      {
        int j = paramViewGroup.size();
        paramViewGroup = new ArrayList(j);
        for (i = 0; i < j; i++)
        {
          localObject = (String)this.mNameOverrides.keyAt(i);
          paramViewGroup.add(this.mStartValues.mNameValues.remove(localObject));
        }
        for (i = 0; i < j; i++)
        {
          localView = (View)paramViewGroup.get(i);
          if (localView != null)
          {
            localObject = (String)this.mNameOverrides.valueAt(i);
            this.mStartValues.mNameValues.put(localObject, localView);
          }
        }
      }
    }
  }
  
  void clearValues(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.mStartValues.mViewValues.clear();
      this.mStartValues.mIdValues.clear();
      this.mStartValues.mItemIdValues.clear();
    }
    else
    {
      this.mEndValues.mViewValues.clear();
      this.mEndValues.mIdValues.clear();
      this.mEndValues.mItemIdValues.clear();
    }
  }
  
  public Transition clone()
  {
    try
    {
      Transition localTransition = (Transition)super.clone();
      Object localObject = new java/util/ArrayList;
      ((ArrayList)localObject).<init>();
      localTransition.mAnimators = ((ArrayList)localObject);
      localObject = new androidx/transition/TransitionValuesMaps;
      ((TransitionValuesMaps)localObject).<init>();
      localTransition.mStartValues = ((TransitionValuesMaps)localObject);
      localObject = new androidx/transition/TransitionValuesMaps;
      ((TransitionValuesMaps)localObject).<init>();
      localTransition.mEndValues = ((TransitionValuesMaps)localObject);
      localTransition.mStartValuesList = null;
      localTransition.mEndValuesList = null;
      return localTransition;
    }
    catch (CloneNotSupportedException localCloneNotSupportedException) {}
    return null;
  }
  
  public Animator createAnimator(ViewGroup paramViewGroup, TransitionValues paramTransitionValues1, TransitionValues paramTransitionValues2)
  {
    return null;
  }
  
  protected void createAnimators(ViewGroup paramViewGroup, TransitionValuesMaps paramTransitionValuesMaps1, TransitionValuesMaps paramTransitionValuesMaps2, ArrayList<TransitionValues> paramArrayList1, ArrayList<TransitionValues> paramArrayList2)
  {
    ArrayMap localArrayMap = getRunningAnimators();
    long l1 = Long.MAX_VALUE;
    SparseIntArray localSparseIntArray = new SparseIntArray();
    int k = paramArrayList1.size();
    int i = 0;
    int j;
    while (i < k)
    {
      TransitionValues localTransitionValues1 = (TransitionValues)paramArrayList1.get(i);
      TransitionValues localTransitionValues2 = (TransitionValues)paramArrayList2.get(i);
      if ((localTransitionValues1 != null) && (!localTransitionValues1.mTargetedTransitions.contains(this))) {
        localTransitionValues1 = null;
      }
      if ((localTransitionValues2 != null) && (!localTransitionValues2.mTargetedTransitions.contains(this))) {
        localTransitionValues2 = null;
      }
      long l2;
      if ((localTransitionValues1 == null) && (localTransitionValues2 == null))
      {
        l2 = l1;
      }
      else
      {
        if ((localTransitionValues1 != null) && (localTransitionValues2 != null) && (!isTransitionRequired(localTransitionValues1, localTransitionValues2))) {
          j = 0;
        } else {
          j = 1;
        }
        if (j != 0)
        {
          paramTransitionValuesMaps1 = createAnimator(paramViewGroup, localTransitionValues1, localTransitionValues2);
          if (paramTransitionValuesMaps1 != null)
          {
            Object localObject2 = null;
            Object localObject3;
            Object localObject1;
            if (localTransitionValues2 != null)
            {
              localObject3 = localTransitionValues2.view;
              String[] arrayOfString = getTransitionProperties();
              if (arrayOfString != null) {
                if (arrayOfString.length > 0)
                {
                  localObject1 = new TransitionValues((View)localObject3);
                  localObject2 = (TransitionValues)paramTransitionValuesMaps2.mViewValues.get(localObject3);
                  if (localObject2 != null) {
                    for (j = 0; j < arrayOfString.length; j++) {
                      ((TransitionValues)localObject1).values.put(arrayOfString[j], ((TransitionValues)localObject2).values.get(arrayOfString[j]));
                    }
                  }
                  int m = localArrayMap.size();
                  for (j = 0; j < m; j++)
                  {
                    localObject2 = (AnimationInfo)localArrayMap.get((Animator)localArrayMap.keyAt(j));
                    if ((((AnimationInfo)localObject2).mValues != null) && (((AnimationInfo)localObject2).mView == localObject3) && (((AnimationInfo)localObject2).mName.equals(getName())) && (((AnimationInfo)localObject2).mValues.equals(localObject1)))
                    {
                      paramTransitionValuesMaps1 = (TransitionValuesMaps)localObject1;
                      localObject1 = null;
                      break label408;
                    }
                  }
                  localObject2 = localObject1;
                  localObject1 = paramTransitionValuesMaps1;
                  paramTransitionValuesMaps1 = (TransitionValuesMaps)localObject2;
                  break label408;
                }
              }
              localObject1 = paramTransitionValuesMaps1;
              paramTransitionValuesMaps1 = (TransitionValuesMaps)localObject2;
              label408:
              localObject2 = paramTransitionValuesMaps1;
              paramTransitionValuesMaps1 = (TransitionValuesMaps)localObject1;
              localObject1 = localObject3;
              j = i;
            }
            else
            {
              localObject1 = localTransitionValues1.view;
              localObject2 = null;
              j = i;
            }
            l2 = l1;
            i = j;
            if (paramTransitionValuesMaps1 != null)
            {
              localObject3 = this.mPropagation;
              if (localObject3 != null)
              {
                l2 = ((TransitionPropagation)localObject3).getStartDelay(paramViewGroup, this, localTransitionValues1, localTransitionValues2);
                localSparseIntArray.put(this.mAnimators.size(), (int)l2);
                l1 = Math.min(l2, l1);
              }
              localArrayMap.put(paramTransitionValuesMaps1, new AnimationInfo((View)localObject1, getName(), this, ViewUtils.getWindowId(paramViewGroup), (TransitionValues)localObject2));
              this.mAnimators.add(paramTransitionValuesMaps1);
              l2 = l1;
              i = j;
            }
          }
          else
          {
            l2 = l1;
          }
        }
        else
        {
          l2 = l1;
        }
      }
      i++;
      l1 = l2;
    }
    if (localSparseIntArray.size() != 0) {
      for (i = 0; i < localSparseIntArray.size(); i++)
      {
        j = localSparseIntArray.keyAt(i);
        paramViewGroup = (Animator)this.mAnimators.get(j);
        paramViewGroup.setStartDelay(localSparseIntArray.valueAt(i) - l1 + paramViewGroup.getStartDelay());
      }
    }
  }
  
  protected void end()
  {
    int i = this.mNumInstances - 1;
    this.mNumInstances = i;
    if (i == 0)
    {
      Object localObject = this.mListeners;
      if ((localObject != null) && (((ArrayList)localObject).size() > 0))
      {
        localObject = (ArrayList)this.mListeners.clone();
        int j = ((ArrayList)localObject).size();
        for (i = 0; i < j; i++) {
          ((TransitionListener)((ArrayList)localObject).get(i)).onTransitionEnd(this);
        }
      }
      for (i = 0; i < this.mStartValues.mItemIdValues.size(); i++)
      {
        localObject = (View)this.mStartValues.mItemIdValues.valueAt(i);
        if (localObject != null) {
          ViewCompat.setHasTransientState((View)localObject, false);
        }
      }
      for (i = 0; i < this.mEndValues.mItemIdValues.size(); i++)
      {
        localObject = (View)this.mEndValues.mItemIdValues.valueAt(i);
        if (localObject != null) {
          ViewCompat.setHasTransientState((View)localObject, false);
        }
      }
      this.mEnded = true;
    }
  }
  
  public Transition excludeChildren(int paramInt, boolean paramBoolean)
  {
    this.mTargetIdChildExcludes = excludeId(this.mTargetIdChildExcludes, paramInt, paramBoolean);
    return this;
  }
  
  public Transition excludeChildren(View paramView, boolean paramBoolean)
  {
    this.mTargetChildExcludes = excludeView(this.mTargetChildExcludes, paramView, paramBoolean);
    return this;
  }
  
  public Transition excludeChildren(Class<?> paramClass, boolean paramBoolean)
  {
    this.mTargetTypeChildExcludes = excludeType(this.mTargetTypeChildExcludes, paramClass, paramBoolean);
    return this;
  }
  
  public Transition excludeTarget(int paramInt, boolean paramBoolean)
  {
    this.mTargetIdExcludes = excludeId(this.mTargetIdExcludes, paramInt, paramBoolean);
    return this;
  }
  
  public Transition excludeTarget(View paramView, boolean paramBoolean)
  {
    this.mTargetExcludes = excludeView(this.mTargetExcludes, paramView, paramBoolean);
    return this;
  }
  
  public Transition excludeTarget(Class<?> paramClass, boolean paramBoolean)
  {
    this.mTargetTypeExcludes = excludeType(this.mTargetTypeExcludes, paramClass, paramBoolean);
    return this;
  }
  
  public Transition excludeTarget(String paramString, boolean paramBoolean)
  {
    this.mTargetNameExcludes = excludeObject(this.mTargetNameExcludes, paramString, paramBoolean);
    return this;
  }
  
  void forceToEnd(ViewGroup paramViewGroup)
  {
    Object localObject = getRunningAnimators();
    int i = ((ArrayMap)localObject).size();
    if ((paramViewGroup != null) && (i != 0))
    {
      paramViewGroup = ViewUtils.getWindowId(paramViewGroup);
      ArrayMap localArrayMap = new ArrayMap((SimpleArrayMap)localObject);
      ((ArrayMap)localObject).clear();
      i--;
      while (i >= 0)
      {
        localObject = (AnimationInfo)localArrayMap.valueAt(i);
        if ((((AnimationInfo)localObject).mView != null) && (paramViewGroup != null) && (paramViewGroup.equals(((AnimationInfo)localObject).mWindowId))) {
          ((Animator)localArrayMap.keyAt(i)).end();
        }
        i--;
      }
      return;
    }
  }
  
  public long getDuration()
  {
    return this.mDuration;
  }
  
  public Rect getEpicenter()
  {
    EpicenterCallback localEpicenterCallback = this.mEpicenterCallback;
    if (localEpicenterCallback == null) {
      return null;
    }
    return localEpicenterCallback.onGetEpicenter(this);
  }
  
  public EpicenterCallback getEpicenterCallback()
  {
    return this.mEpicenterCallback;
  }
  
  public TimeInterpolator getInterpolator()
  {
    return this.mInterpolator;
  }
  
  TransitionValues getMatchedTransitionValues(View paramView, boolean paramBoolean)
  {
    Object localObject = this.mParent;
    if (localObject != null) {
      return ((TransitionSet)localObject).getMatchedTransitionValues(paramView, paramBoolean);
    }
    if (paramBoolean) {
      localObject = this.mStartValuesList;
    } else {
      localObject = this.mEndValuesList;
    }
    if (localObject == null) {
      return null;
    }
    int m = ((ArrayList)localObject).size();
    int k = -1;
    int j;
    for (int i = 0;; i++)
    {
      j = k;
      if (i >= m) {
        break;
      }
      TransitionValues localTransitionValues = (TransitionValues)((ArrayList)localObject).get(i);
      if (localTransitionValues == null) {
        return null;
      }
      if (localTransitionValues.view == paramView)
      {
        j = i;
        break;
      }
    }
    paramView = null;
    if (j >= 0)
    {
      if (paramBoolean) {
        paramView = this.mEndValuesList;
      } else {
        paramView = this.mStartValuesList;
      }
      paramView = (TransitionValues)paramView.get(j);
    }
    return paramView;
  }
  
  public String getName()
  {
    return this.mName;
  }
  
  public PathMotion getPathMotion()
  {
    return this.mPathMotion;
  }
  
  public TransitionPropagation getPropagation()
  {
    return this.mPropagation;
  }
  
  public long getStartDelay()
  {
    return this.mStartDelay;
  }
  
  public List<Integer> getTargetIds()
  {
    return this.mTargetIds;
  }
  
  public List<String> getTargetNames()
  {
    return this.mTargetNames;
  }
  
  public List<Class<?>> getTargetTypes()
  {
    return this.mTargetTypes;
  }
  
  public List<View> getTargets()
  {
    return this.mTargets;
  }
  
  public String[] getTransitionProperties()
  {
    return null;
  }
  
  public TransitionValues getTransitionValues(View paramView, boolean paramBoolean)
  {
    Object localObject = this.mParent;
    if (localObject != null) {
      return ((TransitionSet)localObject).getTransitionValues(paramView, paramBoolean);
    }
    if (paramBoolean) {
      localObject = this.mStartValues;
    } else {
      localObject = this.mEndValues;
    }
    return (TransitionValues)((TransitionValuesMaps)localObject).mViewValues.get(paramView);
  }
  
  public boolean isTransitionRequired(TransitionValues paramTransitionValues1, TransitionValues paramTransitionValues2)
  {
    boolean bool3 = false;
    boolean bool2 = false;
    boolean bool1 = bool3;
    if (paramTransitionValues1 != null)
    {
      bool1 = bool3;
      if (paramTransitionValues2 != null)
      {
        Object localObject = getTransitionProperties();
        if (localObject != null)
        {
          int j = localObject.length;
          for (int i = 0;; i++)
          {
            bool1 = bool2;
            if (i >= j) {
              break;
            }
            if (isValueChanged(paramTransitionValues1, paramTransitionValues2, localObject[i]))
            {
              bool1 = true;
              break;
            }
          }
        }
        else
        {
          localObject = paramTransitionValues1.values.keySet().iterator();
          for (;;)
          {
            bool1 = bool3;
            if (!((Iterator)localObject).hasNext()) {
              break;
            }
            if (isValueChanged(paramTransitionValues1, paramTransitionValues2, (String)((Iterator)localObject).next()))
            {
              bool1 = true;
              break;
            }
          }
        }
      }
    }
    return bool1;
  }
  
  boolean isValidTarget(View paramView)
  {
    int k = paramView.getId();
    Object localObject1 = this.mTargetIdExcludes;
    if ((localObject1 != null) && (((ArrayList)localObject1).contains(Integer.valueOf(k)))) {
      return false;
    }
    localObject1 = this.mTargetExcludes;
    if ((localObject1 != null) && (((ArrayList)localObject1).contains(paramView))) {
      return false;
    }
    localObject1 = this.mTargetTypeExcludes;
    int i;
    if (localObject1 != null)
    {
      int j = ((ArrayList)localObject1).size();
      for (i = 0; i < j; i++) {
        if (((Class)this.mTargetTypeExcludes.get(i)).isInstance(paramView)) {
          return false;
        }
      }
    }
    Object localObject2;
    if (this.mTargetNameExcludes != null)
    {
      localObject1 = ViewCompat.getTransitionName(paramView);
      Log5ECF72.a(localObject1);
      LogE84000.a(localObject1);
      Log229316.a(localObject1);
      if (localObject1 != null)
      {
        localObject2 = this.mTargetNameExcludes;
        localObject1 = ViewCompat.getTransitionName(paramView);
        Log5ECF72.a(localObject1);
        LogE84000.a(localObject1);
        Log229316.a(localObject1);
        if (((ArrayList)localObject2).contains(localObject1)) {
          return false;
        }
      }
    }
    if ((this.mTargetIds.size() == 0) && (this.mTargets.size() == 0))
    {
      localObject1 = this.mTargetTypes;
      if ((localObject1 == null) || (((ArrayList)localObject1).isEmpty()))
      {
        localObject1 = this.mTargetNames;
        if ((localObject1 == null) || (((ArrayList)localObject1).isEmpty())) {
          return true;
        }
      }
    }
    if ((!this.mTargetIds.contains(Integer.valueOf(k))) && (!this.mTargets.contains(paramView)))
    {
      localObject1 = this.mTargetNames;
      if (localObject1 != null)
      {
        localObject2 = ViewCompat.getTransitionName(paramView);
        Log5ECF72.a(localObject2);
        LogE84000.a(localObject2);
        Log229316.a(localObject2);
        if (((ArrayList)localObject1).contains(localObject2)) {
          return true;
        }
      }
      if (this.mTargetTypes != null) {
        for (i = 0; i < this.mTargetTypes.size(); i++) {
          if (((Class)this.mTargetTypes.get(i)).isInstance(paramView)) {
            return true;
          }
        }
      }
      return false;
    }
    return true;
  }
  
  public void pause(View paramView)
  {
    if (!this.mEnded)
    {
      ArrayMap localArrayMap = getRunningAnimators();
      int i = localArrayMap.size();
      WindowIdImpl localWindowIdImpl = ViewUtils.getWindowId(paramView);
      i--;
      while (i >= 0)
      {
        paramView = (AnimationInfo)localArrayMap.valueAt(i);
        if ((paramView.mView != null) && (localWindowIdImpl.equals(paramView.mWindowId))) {
          AnimatorUtils.pause((Animator)localArrayMap.keyAt(i));
        }
        i--;
      }
      paramView = this.mListeners;
      if ((paramView != null) && (paramView.size() > 0))
      {
        paramView = (ArrayList)this.mListeners.clone();
        int j = paramView.size();
        for (i = 0; i < j; i++) {
          ((TransitionListener)paramView.get(i)).onTransitionPause(this);
        }
      }
      this.mPaused = true;
    }
  }
  
  void playTransition(ViewGroup paramViewGroup)
  {
    this.mStartValuesList = new ArrayList();
    this.mEndValuesList = new ArrayList();
    matchStartAndEnd(this.mStartValues, this.mEndValues);
    ArrayMap localArrayMap = getRunningAnimators();
    int i = localArrayMap.size();
    WindowIdImpl localWindowIdImpl = ViewUtils.getWindowId(paramViewGroup);
    i--;
    while (i >= 0)
    {
      Animator localAnimator = (Animator)localArrayMap.keyAt(i);
      if (localAnimator != null)
      {
        AnimationInfo localAnimationInfo = (AnimationInfo)localArrayMap.get(localAnimator);
        if ((localAnimationInfo != null) && (localAnimationInfo.mView != null) && (localWindowIdImpl.equals(localAnimationInfo.mWindowId)))
        {
          TransitionValues localTransitionValues4 = localAnimationInfo.mValues;
          View localView = localAnimationInfo.mView;
          int j = 1;
          TransitionValues localTransitionValues3 = getTransitionValues(localView, true);
          TransitionValues localTransitionValues2 = getMatchedTransitionValues(localView, true);
          TransitionValues localTransitionValues1 = localTransitionValues2;
          if (localTransitionValues3 == null)
          {
            localTransitionValues1 = localTransitionValues2;
            if (localTransitionValues2 == null) {
              localTransitionValues1 = (TransitionValues)this.mEndValues.mViewValues.get(localView);
            }
          }
          if (((localTransitionValues3 == null) && (localTransitionValues1 == null)) || (!localAnimationInfo.mTransition.isTransitionRequired(localTransitionValues4, localTransitionValues1))) {
            j = 0;
          }
          if (j != 0) {
            if ((!localAnimator.isRunning()) && (!localAnimator.isStarted())) {
              localArrayMap.remove(localAnimator);
            } else {
              localAnimator.cancel();
            }
          }
        }
      }
      i--;
    }
    createAnimators(paramViewGroup, this.mStartValues, this.mEndValues, this.mStartValuesList, this.mEndValuesList);
    runAnimators();
  }
  
  public Transition removeListener(TransitionListener paramTransitionListener)
  {
    ArrayList localArrayList = this.mListeners;
    if (localArrayList == null) {
      return this;
    }
    localArrayList.remove(paramTransitionListener);
    if (this.mListeners.size() == 0) {
      this.mListeners = null;
    }
    return this;
  }
  
  public Transition removeTarget(int paramInt)
  {
    if (paramInt != 0) {
      this.mTargetIds.remove(Integer.valueOf(paramInt));
    }
    return this;
  }
  
  public Transition removeTarget(View paramView)
  {
    this.mTargets.remove(paramView);
    return this;
  }
  
  public Transition removeTarget(Class<?> paramClass)
  {
    ArrayList localArrayList = this.mTargetTypes;
    if (localArrayList != null) {
      localArrayList.remove(paramClass);
    }
    return this;
  }
  
  public Transition removeTarget(String paramString)
  {
    ArrayList localArrayList = this.mTargetNames;
    if (localArrayList != null) {
      localArrayList.remove(paramString);
    }
    return this;
  }
  
  public void resume(View paramView)
  {
    if (this.mPaused)
    {
      if (!this.mEnded)
      {
        ArrayMap localArrayMap = getRunningAnimators();
        int i = localArrayMap.size();
        paramView = ViewUtils.getWindowId(paramView);
        i--;
        while (i >= 0)
        {
          AnimationInfo localAnimationInfo = (AnimationInfo)localArrayMap.valueAt(i);
          if ((localAnimationInfo.mView != null) && (paramView.equals(localAnimationInfo.mWindowId))) {
            AnimatorUtils.resume((Animator)localArrayMap.keyAt(i));
          }
          i--;
        }
        paramView = this.mListeners;
        if ((paramView != null) && (paramView.size() > 0))
        {
          paramView = (ArrayList)this.mListeners.clone();
          int j = paramView.size();
          for (i = 0; i < j; i++) {
            ((TransitionListener)paramView.get(i)).onTransitionResume(this);
          }
        }
      }
      this.mPaused = false;
    }
  }
  
  protected void runAnimators()
  {
    start();
    ArrayMap localArrayMap = getRunningAnimators();
    Iterator localIterator = this.mAnimators.iterator();
    while (localIterator.hasNext())
    {
      Animator localAnimator = (Animator)localIterator.next();
      if (localArrayMap.containsKey(localAnimator))
      {
        start();
        runAnimator(localAnimator, localArrayMap);
      }
    }
    this.mAnimators.clear();
    end();
  }
  
  void setCanRemoveViews(boolean paramBoolean)
  {
    this.mCanRemoveViews = paramBoolean;
  }
  
  public Transition setDuration(long paramLong)
  {
    this.mDuration = paramLong;
    return this;
  }
  
  public void setEpicenterCallback(EpicenterCallback paramEpicenterCallback)
  {
    this.mEpicenterCallback = paramEpicenterCallback;
  }
  
  public Transition setInterpolator(TimeInterpolator paramTimeInterpolator)
  {
    this.mInterpolator = paramTimeInterpolator;
    return this;
  }
  
  public void setMatchOrder(int... paramVarArgs)
  {
    if ((paramVarArgs != null) && (paramVarArgs.length != 0))
    {
      int i = 0;
      while (i < paramVarArgs.length) {
        if (isValidMatch(paramVarArgs[i]))
        {
          if (!alreadyContains(paramVarArgs, i)) {
            i++;
          } else {
            throw new IllegalArgumentException("matches contains a duplicate value");
          }
        }
        else {
          throw new IllegalArgumentException("matches contains invalid value");
        }
      }
      this.mMatchOrder = ((int[])paramVarArgs.clone());
    }
    else
    {
      this.mMatchOrder = DEFAULT_MATCH_ORDER;
    }
  }
  
  public void setPathMotion(PathMotion paramPathMotion)
  {
    if (paramPathMotion == null) {
      this.mPathMotion = STRAIGHT_PATH_MOTION;
    } else {
      this.mPathMotion = paramPathMotion;
    }
  }
  
  public void setPropagation(TransitionPropagation paramTransitionPropagation)
  {
    this.mPropagation = paramTransitionPropagation;
  }
  
  Transition setSceneRoot(ViewGroup paramViewGroup)
  {
    this.mSceneRoot = paramViewGroup;
    return this;
  }
  
  public Transition setStartDelay(long paramLong)
  {
    this.mStartDelay = paramLong;
    return this;
  }
  
  protected void start()
  {
    if (this.mNumInstances == 0)
    {
      ArrayList localArrayList = this.mListeners;
      if ((localArrayList != null) && (localArrayList.size() > 0))
      {
        localArrayList = (ArrayList)this.mListeners.clone();
        int j = localArrayList.size();
        for (int i = 0; i < j; i++) {
          ((TransitionListener)localArrayList.get(i)).onTransitionStart(this);
        }
      }
      this.mEnded = false;
    }
    this.mNumInstances += 1;
  }
  
  public String toString()
  {
    return toString("");
  }
  
  String toString(String paramString)
  {
    paramString = new StringBuilder().append(paramString).append(getClass().getSimpleName()).append("@");
    String str = Integer.toHexString(hashCode());
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    paramString = str + ": ";
    str = paramString;
    if (this.mDuration != -1L) {
      str = paramString + "dur(" + this.mDuration + ") ";
    }
    paramString = str;
    if (this.mStartDelay != -1L) {
      paramString = str + "dly(" + this.mStartDelay + ") ";
    }
    str = paramString;
    if (this.mInterpolator != null) {
      str = paramString + "interp(" + this.mInterpolator + ") ";
    }
    if (this.mTargetIds.size() <= 0)
    {
      paramString = str;
      if (this.mTargets.size() <= 0) {}
    }
    else
    {
      str = str + "tgts(";
      paramString = str;
      int i;
      if (this.mTargetIds.size() > 0) {
        for (i = 0;; i++)
        {
          paramString = str;
          if (i >= this.mTargetIds.size()) {
            break;
          }
          paramString = str;
          if (i > 0) {
            paramString = str + ", ";
          }
          str = paramString + this.mTargetIds.get(i);
        }
      }
      str = paramString;
      if (this.mTargets.size() > 0) {
        for (i = 0;; i++)
        {
          str = paramString;
          if (i >= this.mTargets.size()) {
            break;
          }
          str = paramString;
          if (i > 0) {
            str = paramString + ", ";
          }
          paramString = str + this.mTargets.get(i);
        }
      }
      paramString = str + ")";
    }
    return paramString;
  }
  
  private static class AnimationInfo
  {
    String mName;
    Transition mTransition;
    TransitionValues mValues;
    View mView;
    WindowIdImpl mWindowId;
    
    AnimationInfo(View paramView, String paramString, Transition paramTransition, WindowIdImpl paramWindowIdImpl, TransitionValues paramTransitionValues)
    {
      this.mView = paramView;
      this.mName = paramString;
      this.mValues = paramTransitionValues;
      this.mWindowId = paramWindowIdImpl;
      this.mTransition = paramTransition;
    }
  }
  
  private static class ArrayListManager
  {
    static <T> ArrayList<T> add(ArrayList<T> paramArrayList, T paramT)
    {
      Object localObject = paramArrayList;
      if (paramArrayList == null) {
        localObject = new ArrayList();
      }
      if (!((ArrayList)localObject).contains(paramT)) {
        ((ArrayList)localObject).add(paramT);
      }
      return (ArrayList<T>)localObject;
    }
    
    static <T> ArrayList<T> remove(ArrayList<T> paramArrayList, T paramT)
    {
      ArrayList<T> localArrayList = paramArrayList;
      if (paramArrayList != null)
      {
        paramArrayList.remove(paramT);
        localArrayList = paramArrayList;
        if (paramArrayList.isEmpty()) {
          localArrayList = null;
        }
      }
      return localArrayList;
    }
  }
  
  public static abstract class EpicenterCallback
  {
    public abstract Rect onGetEpicenter(Transition paramTransition);
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface MatchOrder {}
  
  public static abstract interface TransitionListener
  {
    public abstract void onTransitionCancel(Transition paramTransition);
    
    public abstract void onTransitionEnd(Transition paramTransition);
    
    public abstract void onTransitionPause(Transition paramTransition);
    
    public abstract void onTransitionResume(Transition paramTransition);
    
    public abstract void onTransitionStart(Transition paramTransition);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/transition/Transition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */