package androidx.fragment.app;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.collection.ArrayMap;
import androidx.core.app.SharedElementCallback;
import androidx.core.os.CancellationSignal;
import androidx.core.view.OneShotPreDrawListener;
import androidx.core.view.ViewCompat;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

class FragmentTransition
{
  private static final int[] INVERSE_OPS = { 0, 3, 0, 1, 5, 4, 7, 6, 9, 8, 10 };
  static final FragmentTransitionImpl PLATFORM_IMPL;
  static final FragmentTransitionImpl SUPPORT_IMPL = resolveSupportImpl();
  
  static
  {
    FragmentTransitionCompat21 localFragmentTransitionCompat21;
    if (Build.VERSION.SDK_INT >= 21) {
      localFragmentTransitionCompat21 = new FragmentTransitionCompat21();
    } else {
      localFragmentTransitionCompat21 = null;
    }
    PLATFORM_IMPL = localFragmentTransitionCompat21;
  }
  
  private static void addSharedElementsWithMatchingNames(ArrayList<View> paramArrayList, ArrayMap<String, View> paramArrayMap, Collection<String> paramCollection)
  {
    for (int i = paramArrayMap.size() - 1; i >= 0; i--)
    {
      View localView = (View)paramArrayMap.valueAt(i);
      String str = ViewCompat.getTransitionName(localView);
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      if (paramCollection.contains(str)) {
        paramArrayList.add(localView);
      }
    }
  }
  
  private static void addToFirstInLastOut(BackStackRecord paramBackStackRecord, FragmentTransaction.Op paramOp, SparseArray<FragmentContainerTransition> paramSparseArray, boolean paramBoolean1, boolean paramBoolean2)
  {
    Fragment localFragment = paramOp.mFragment;
    if (localFragment == null) {
      return;
    }
    int i4 = localFragment.mContainerId;
    if (i4 == 0) {
      return;
    }
    int i;
    if (paramBoolean1) {
      i = INVERSE_OPS[paramOp.mCmd];
    } else {
      i = paramOp.mCmd;
    }
    boolean bool2 = false;
    int m = 0;
    int j = 0;
    int k = 0;
    int n = 1;
    int i2 = 1;
    int i1 = 1;
    int i3 = 1;
    boolean bool1 = true;
    boolean bool3 = true;
    switch (i)
    {
    case 2: 
    default: 
      bool1 = bool2;
      i = m;
      break;
    case 5: 
      if (paramBoolean2)
      {
        if ((localFragment.mHiddenChanged) && (!localFragment.mHidden) && (localFragment.mAdded)) {
          bool1 = bool3;
        } else {
          bool1 = false;
        }
      }
      else {
        bool1 = localFragment.mHidden;
      }
      k = 1;
      i = m;
      break;
    case 4: 
      if (paramBoolean2)
      {
        if ((localFragment.mHiddenChanged) && (localFragment.mAdded) && (localFragment.mHidden)) {
          i = n;
        } else {
          i = 0;
        }
        j = i;
      }
      else
      {
        if ((localFragment.mAdded) && (!localFragment.mHidden)) {
          i = i2;
        } else {
          i = 0;
        }
        j = i;
      }
      i = 1;
      bool1 = bool2;
      break;
    case 3: 
    case 6: 
      if (paramBoolean2)
      {
        if ((!localFragment.mAdded) && (localFragment.mView != null) && (localFragment.mView.getVisibility() == 0) && (localFragment.mPostponedAlpha >= 0.0F)) {
          i = i1;
        } else {
          i = 0;
        }
        j = i;
      }
      else
      {
        if ((localFragment.mAdded) && (!localFragment.mHidden)) {
          i = i3;
        } else {
          i = 0;
        }
        j = i;
      }
      i = 1;
      bool1 = bool2;
      break;
    case 1: 
    case 7: 
      if (paramBoolean2) {
        bool1 = localFragment.mIsNewlyAdded;
      } else if ((localFragment.mAdded) || (localFragment.mHidden)) {
        bool1 = false;
      }
      k = 1;
      i = m;
    }
    Object localObject = (FragmentContainerTransition)paramSparseArray.get(i4);
    paramOp = (FragmentTransaction.Op)localObject;
    if (bool1)
    {
      paramOp = ensureContainer((FragmentContainerTransition)localObject, paramSparseArray, i4);
      paramOp.lastIn = localFragment;
      paramOp.lastInIsPop = paramBoolean1;
      paramOp.lastInTransaction = paramBackStackRecord;
    }
    if ((!paramBoolean2) && (k != 0))
    {
      if ((paramOp != null) && (paramOp.firstOut == localFragment)) {
        paramOp.firstOut = null;
      }
      if (!paramBackStackRecord.mReorderingAllowed)
      {
        localObject = paramBackStackRecord.mManager;
        FragmentStateManager localFragmentStateManager = ((FragmentManager)localObject).createOrGetFragmentStateManager(localFragment);
        ((FragmentManager)localObject).getFragmentStore().makeActive(localFragmentStateManager);
        ((FragmentManager)localObject).moveToState(localFragment);
      }
    }
    localObject = paramOp;
    if (j != 0) {
      if (paramOp != null)
      {
        localObject = paramOp;
        if (paramOp.firstOut != null) {}
      }
      else
      {
        localObject = ensureContainer(paramOp, paramSparseArray, i4);
        ((FragmentContainerTransition)localObject).firstOut = localFragment;
        ((FragmentContainerTransition)localObject).firstOutIsPop = paramBoolean1;
        ((FragmentContainerTransition)localObject).firstOutTransaction = paramBackStackRecord;
      }
    }
    if ((!paramBoolean2) && (i != 0) && (localObject != null) && (((FragmentContainerTransition)localObject).lastIn == localFragment)) {
      ((FragmentContainerTransition)localObject).lastIn = null;
    }
  }
  
  public static void calculateFragments(BackStackRecord paramBackStackRecord, SparseArray<FragmentContainerTransition> paramSparseArray, boolean paramBoolean)
  {
    int j = paramBackStackRecord.mOps.size();
    for (int i = 0; i < j; i++) {
      addToFirstInLastOut(paramBackStackRecord, (FragmentTransaction.Op)paramBackStackRecord.mOps.get(i), paramSparseArray, false, paramBoolean);
    }
  }
  
  private static ArrayMap<String, String> calculateNameOverrides(int paramInt1, ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1, int paramInt2, int paramInt3)
  {
    ArrayMap localArrayMap = new ArrayMap();
    paramInt3--;
    while (paramInt3 >= paramInt2)
    {
      Object localObject = (BackStackRecord)paramArrayList.get(paramInt3);
      if (((BackStackRecord)localObject).interactsWith(paramInt1))
      {
        boolean bool = ((Boolean)paramArrayList1.get(paramInt3)).booleanValue();
        if (((BackStackRecord)localObject).mSharedElementSourceNames != null)
        {
          int j = ((BackStackRecord)localObject).mSharedElementSourceNames.size();
          ArrayList localArrayList1;
          ArrayList localArrayList2;
          if (bool)
          {
            localArrayList1 = ((BackStackRecord)localObject).mSharedElementSourceNames;
            localArrayList2 = ((BackStackRecord)localObject).mSharedElementTargetNames;
          }
          else
          {
            localArrayList2 = ((BackStackRecord)localObject).mSharedElementSourceNames;
            localArrayList1 = ((BackStackRecord)localObject).mSharedElementTargetNames;
          }
          for (int i = 0; i < j; i++)
          {
            String str1 = (String)localArrayList2.get(i);
            localObject = (String)localArrayList1.get(i);
            String str2 = (String)localArrayMap.remove(localObject);
            if (str2 != null) {
              localArrayMap.put(str1, str2);
            } else {
              localArrayMap.put(str1, localObject);
            }
          }
        }
      }
      paramInt3--;
    }
    return localArrayMap;
  }
  
  public static void calculatePopFragments(BackStackRecord paramBackStackRecord, SparseArray<FragmentContainerTransition> paramSparseArray, boolean paramBoolean)
  {
    if (!paramBackStackRecord.mManager.getContainer().onHasView()) {
      return;
    }
    for (int i = paramBackStackRecord.mOps.size() - 1; i >= 0; i--) {
      addToFirstInLastOut(paramBackStackRecord, (FragmentTransaction.Op)paramBackStackRecord.mOps.get(i), paramSparseArray, true, paramBoolean);
    }
  }
  
  static void callSharedElementStartEnd(Fragment paramFragment1, Fragment paramFragment2, boolean paramBoolean1, ArrayMap<String, View> paramArrayMap, boolean paramBoolean2)
  {
    if (paramBoolean1) {
      paramFragment1 = paramFragment2.getEnterTransitionCallback();
    } else {
      paramFragment1 = paramFragment1.getEnterTransitionCallback();
    }
    if (paramFragment1 != null)
    {
      ArrayList localArrayList = new ArrayList();
      paramFragment2 = new ArrayList();
      int i;
      if (paramArrayMap == null) {
        i = 0;
      } else {
        i = paramArrayMap.size();
      }
      for (int j = 0; j < i; j++)
      {
        paramFragment2.add(paramArrayMap.keyAt(j));
        localArrayList.add(paramArrayMap.valueAt(j));
      }
      if (paramBoolean2) {
        paramFragment1.onSharedElementStart(paramFragment2, localArrayList, null);
      } else {
        paramFragment1.onSharedElementEnd(paramFragment2, localArrayList, null);
      }
    }
  }
  
  private static boolean canHandleAll(FragmentTransitionImpl paramFragmentTransitionImpl, List<Object> paramList)
  {
    int i = 0;
    int j = paramList.size();
    while (i < j)
    {
      if (!paramFragmentTransitionImpl.canHandle(paramList.get(i))) {
        return false;
      }
      i++;
    }
    return true;
  }
  
  static ArrayMap<String, View> captureInSharedElements(FragmentTransitionImpl paramFragmentTransitionImpl, ArrayMap<String, String> paramArrayMap, Object paramObject, FragmentContainerTransition paramFragmentContainerTransition)
  {
    Object localObject = paramFragmentContainerTransition.lastIn;
    View localView = ((Fragment)localObject).getView();
    if ((!paramArrayMap.isEmpty()) && (paramObject != null) && (localView != null))
    {
      ArrayMap localArrayMap = new ArrayMap();
      paramFragmentTransitionImpl.findNamedViews(localArrayMap, localView);
      paramFragmentTransitionImpl = paramFragmentContainerTransition.lastInTransaction;
      if (paramFragmentContainerTransition.lastInIsPop)
      {
        paramObject = ((Fragment)localObject).getExitTransitionCallback();
        paramFragmentTransitionImpl = paramFragmentTransitionImpl.mSharedElementSourceNames;
      }
      else
      {
        paramObject = ((Fragment)localObject).getEnterTransitionCallback();
        paramFragmentTransitionImpl = paramFragmentTransitionImpl.mSharedElementTargetNames;
      }
      if (paramFragmentTransitionImpl != null)
      {
        localArrayMap.retainAll(paramFragmentTransitionImpl);
        localArrayMap.retainAll(paramArrayMap.values());
      }
      if (paramObject != null)
      {
        ((SharedElementCallback)paramObject).onMapSharedElements(paramFragmentTransitionImpl, localArrayMap);
        for (int i = paramFragmentTransitionImpl.size() - 1; i >= 0; i--)
        {
          localObject = (String)paramFragmentTransitionImpl.get(i);
          paramObject = (View)localArrayMap.get(localObject);
          if (paramObject == null)
          {
            paramObject = findKeyForValue(paramArrayMap, (String)localObject);
            Log5ECF72.a(paramObject);
            LogE84000.a(paramObject);
            Log229316.a(paramObject);
            if (paramObject != null) {
              paramArrayMap.remove(paramObject);
            }
          }
          else
          {
            paramFragmentContainerTransition = ViewCompat.getTransitionName((View)paramObject);
            Log5ECF72.a(paramFragmentContainerTransition);
            LogE84000.a(paramFragmentContainerTransition);
            Log229316.a(paramFragmentContainerTransition);
            if (!((String)localObject).equals(paramFragmentContainerTransition))
            {
              paramFragmentContainerTransition = findKeyForValue(paramArrayMap, (String)localObject);
              Log5ECF72.a(paramFragmentContainerTransition);
              LogE84000.a(paramFragmentContainerTransition);
              Log229316.a(paramFragmentContainerTransition);
              if (paramFragmentContainerTransition != null)
              {
                paramObject = ViewCompat.getTransitionName((View)paramObject);
                Log5ECF72.a(paramObject);
                LogE84000.a(paramObject);
                Log229316.a(paramObject);
                paramArrayMap.put(paramFragmentContainerTransition, paramObject);
              }
            }
          }
        }
      }
      else
      {
        retainValues(paramArrayMap, localArrayMap);
      }
      return localArrayMap;
    }
    paramArrayMap.clear();
    return null;
  }
  
  private static ArrayMap<String, View> captureOutSharedElements(FragmentTransitionImpl paramFragmentTransitionImpl, ArrayMap<String, String> paramArrayMap, Object paramObject, FragmentContainerTransition paramFragmentContainerTransition)
  {
    if ((!paramArrayMap.isEmpty()) && (paramObject != null))
    {
      paramObject = paramFragmentContainerTransition.firstOut;
      ArrayMap localArrayMap = new ArrayMap();
      paramFragmentTransitionImpl.findNamedViews(localArrayMap, ((Fragment)paramObject).requireView());
      paramFragmentTransitionImpl = paramFragmentContainerTransition.firstOutTransaction;
      if (paramFragmentContainerTransition.firstOutIsPop)
      {
        paramObject = ((Fragment)paramObject).getEnterTransitionCallback();
        paramFragmentTransitionImpl = paramFragmentTransitionImpl.mSharedElementTargetNames;
      }
      else
      {
        paramObject = ((Fragment)paramObject).getExitTransitionCallback();
        paramFragmentTransitionImpl = paramFragmentTransitionImpl.mSharedElementSourceNames;
      }
      if (paramFragmentTransitionImpl != null) {
        localArrayMap.retainAll(paramFragmentTransitionImpl);
      }
      if (paramObject != null)
      {
        ((SharedElementCallback)paramObject).onMapSharedElements(paramFragmentTransitionImpl, localArrayMap);
        for (int i = paramFragmentTransitionImpl.size() - 1; i >= 0; i--)
        {
          paramFragmentContainerTransition = (String)paramFragmentTransitionImpl.get(i);
          paramObject = (View)localArrayMap.get(paramFragmentContainerTransition);
          if (paramObject == null)
          {
            paramArrayMap.remove(paramFragmentContainerTransition);
          }
          else
          {
            String str = ViewCompat.getTransitionName((View)paramObject);
            Log5ECF72.a(str);
            LogE84000.a(str);
            Log229316.a(str);
            if (!paramFragmentContainerTransition.equals(str))
            {
              paramFragmentContainerTransition = (String)paramArrayMap.remove(paramFragmentContainerTransition);
              paramObject = ViewCompat.getTransitionName((View)paramObject);
              Log5ECF72.a(paramObject);
              LogE84000.a(paramObject);
              Log229316.a(paramObject);
              paramArrayMap.put(paramObject, paramFragmentContainerTransition);
            }
          }
        }
      }
      else
      {
        paramArrayMap.retainAll(localArrayMap.keySet());
      }
      return localArrayMap;
    }
    paramArrayMap.clear();
    return null;
  }
  
  private static FragmentTransitionImpl chooseImpl(Fragment paramFragment1, Fragment paramFragment2)
  {
    ArrayList localArrayList = new ArrayList();
    if (paramFragment1 != null)
    {
      Object localObject = paramFragment1.getExitTransition();
      if (localObject != null) {
        localArrayList.add(localObject);
      }
      localObject = paramFragment1.getReturnTransition();
      if (localObject != null) {
        localArrayList.add(localObject);
      }
      paramFragment1 = paramFragment1.getSharedElementReturnTransition();
      if (paramFragment1 != null) {
        localArrayList.add(paramFragment1);
      }
    }
    if (paramFragment2 != null)
    {
      paramFragment1 = paramFragment2.getEnterTransition();
      if (paramFragment1 != null) {
        localArrayList.add(paramFragment1);
      }
      paramFragment1 = paramFragment2.getReenterTransition();
      if (paramFragment1 != null) {
        localArrayList.add(paramFragment1);
      }
      paramFragment1 = paramFragment2.getSharedElementEnterTransition();
      if (paramFragment1 != null) {
        localArrayList.add(paramFragment1);
      }
    }
    if (localArrayList.isEmpty()) {
      return null;
    }
    paramFragment1 = PLATFORM_IMPL;
    if ((paramFragment1 != null) && (canHandleAll(paramFragment1, localArrayList))) {
      return paramFragment1;
    }
    paramFragment2 = SUPPORT_IMPL;
    if ((paramFragment2 != null) && (canHandleAll(paramFragment2, localArrayList))) {
      return paramFragment2;
    }
    if ((paramFragment1 == null) && (paramFragment2 == null)) {
      return null;
    }
    throw new IllegalArgumentException("Invalid Transition types");
  }
  
  static ArrayList<View> configureEnteringExitingViews(FragmentTransitionImpl paramFragmentTransitionImpl, Object paramObject, Fragment paramFragment, ArrayList<View> paramArrayList, View paramView)
  {
    Object localObject = null;
    if (paramObject != null)
    {
      ArrayList localArrayList = new ArrayList();
      paramFragment = paramFragment.getView();
      if (paramFragment != null) {
        paramFragmentTransitionImpl.captureTransitioningViews(localArrayList, paramFragment);
      }
      if (paramArrayList != null) {
        localArrayList.removeAll(paramArrayList);
      }
      localObject = localArrayList;
      if (!localArrayList.isEmpty())
      {
        localArrayList.add(paramView);
        paramFragmentTransitionImpl.addTargets(paramObject, localArrayList);
        localObject = localArrayList;
      }
    }
    return (ArrayList<View>)localObject;
  }
  
  private static Object configureSharedElementsOrdered(FragmentTransitionImpl paramFragmentTransitionImpl, ViewGroup paramViewGroup, final View paramView, final ArrayMap<String, String> paramArrayMap, final FragmentContainerTransition paramFragmentContainerTransition, final ArrayList<View> paramArrayList1, final ArrayList<View> paramArrayList2, final Object paramObject1, final Object paramObject2)
  {
    final Fragment localFragment2 = paramFragmentContainerTransition.lastIn;
    final Fragment localFragment1 = paramFragmentContainerTransition.firstOut;
    if ((localFragment2 != null) && (localFragment1 != null))
    {
      final boolean bool = paramFragmentContainerTransition.lastInIsPop;
      final Object localObject;
      if (paramArrayMap.isEmpty()) {
        localObject = null;
      } else {
        localObject = getSharedElementTransition(paramFragmentTransitionImpl, localFragment2, localFragment1, bool);
      }
      ArrayMap localArrayMap = captureOutSharedElements(paramFragmentTransitionImpl, paramArrayMap, localObject, paramFragmentContainerTransition);
      if (paramArrayMap.isEmpty()) {
        localObject = null;
      } else {
        paramArrayList1.addAll(localArrayMap.values());
      }
      if ((paramObject1 == null) && (paramObject2 == null) && (localObject == null)) {
        return null;
      }
      callSharedElementStartEnd(localFragment2, localFragment1, bool, localArrayMap, true);
      if (localObject != null)
      {
        Rect localRect = new Rect();
        paramFragmentTransitionImpl.setSharedElementTargets(localObject, paramView, paramArrayList1);
        setOutEpicenter(paramFragmentTransitionImpl, localObject, paramObject2, localArrayMap, paramFragmentContainerTransition.firstOutIsPop, paramFragmentContainerTransition.firstOutTransaction);
        if (paramObject1 != null) {
          paramFragmentTransitionImpl.setEpicenter(paramObject1, localRect);
        }
        paramObject2 = localRect;
      }
      else
      {
        paramObject2 = null;
      }
      OneShotPreDrawListener.add(paramViewGroup, new Runnable()
      {
        public void run()
        {
          ArrayMap localArrayMap = FragmentTransition.captureInSharedElements(FragmentTransition.this, paramArrayMap, localObject, paramFragmentContainerTransition);
          if (localArrayMap != null)
          {
            paramArrayList2.addAll(localArrayMap.values());
            paramArrayList2.add(paramView);
          }
          FragmentTransition.callSharedElementStartEnd(localFragment2, localFragment1, bool, localArrayMap, false);
          Object localObject = localObject;
          if (localObject != null)
          {
            FragmentTransition.this.swapSharedElementTargets(localObject, paramArrayList1, paramArrayList2);
            localObject = FragmentTransition.getInEpicenterView(localArrayMap, paramFragmentContainerTransition, paramObject1, bool);
            if (localObject != null) {
              FragmentTransition.this.getBoundsOnScreen((View)localObject, paramObject2);
            }
          }
        }
      });
      return localObject;
    }
    return null;
  }
  
  private static Object configureSharedElementsReordered(final FragmentTransitionImpl paramFragmentTransitionImpl, ViewGroup paramViewGroup, final View paramView, ArrayMap<String, String> paramArrayMap, final FragmentContainerTransition paramFragmentContainerTransition, ArrayList<View> paramArrayList1, ArrayList<View> paramArrayList2, Object paramObject1, Object paramObject2)
  {
    Fragment localFragment2 = paramFragmentContainerTransition.lastIn;
    final Fragment localFragment1 = paramFragmentContainerTransition.firstOut;
    if (localFragment2 != null) {
      localFragment2.requireView().setVisibility(0);
    }
    if ((localFragment2 != null) && (localFragment1 != null))
    {
      final boolean bool = paramFragmentContainerTransition.lastInIsPop;
      Object localObject;
      if (paramArrayMap.isEmpty()) {
        localObject = null;
      } else {
        localObject = getSharedElementTransition(paramFragmentTransitionImpl, localFragment2, localFragment1, bool);
      }
      ArrayMap localArrayMap2 = captureOutSharedElements(paramFragmentTransitionImpl, paramArrayMap, localObject, paramFragmentContainerTransition);
      final ArrayMap localArrayMap1 = captureInSharedElements(paramFragmentTransitionImpl, paramArrayMap, localObject, paramFragmentContainerTransition);
      if (paramArrayMap.isEmpty())
      {
        if (localArrayMap2 != null) {
          localArrayMap2.clear();
        }
        if (localArrayMap1 != null) {
          localArrayMap1.clear();
        }
        paramArrayMap = null;
      }
      else
      {
        addSharedElementsWithMatchingNames(paramArrayList1, localArrayMap2, paramArrayMap.keySet());
        addSharedElementsWithMatchingNames(paramArrayList2, localArrayMap1, paramArrayMap.values());
        paramArrayMap = (ArrayMap<String, String>)localObject;
      }
      if ((paramObject1 == null) && (paramObject2 == null) && (paramArrayMap == null)) {
        return null;
      }
      callSharedElementStartEnd(localFragment2, localFragment1, bool, localArrayMap2, true);
      if (paramArrayMap != null)
      {
        paramArrayList2.add(paramView);
        paramFragmentTransitionImpl.setSharedElementTargets(paramArrayMap, paramView, paramArrayList1);
        setOutEpicenter(paramFragmentTransitionImpl, paramArrayMap, paramObject2, localArrayMap2, paramFragmentContainerTransition.firstOutIsPop, paramFragmentContainerTransition.firstOutTransaction);
        paramView = new Rect();
        paramFragmentContainerTransition = getInEpicenterView(localArrayMap1, paramFragmentContainerTransition, paramObject1, bool);
        if (paramFragmentContainerTransition != null) {
          paramFragmentTransitionImpl.setEpicenter(paramObject1, paramView);
        }
      }
      else
      {
        paramView = null;
        paramFragmentContainerTransition = null;
      }
      OneShotPreDrawListener.add(paramViewGroup, new Runnable()
      {
        public void run()
        {
          FragmentTransition.callSharedElementStartEnd(FragmentTransition.this, localFragment1, bool, localArrayMap1, false);
          View localView = paramFragmentContainerTransition;
          if (localView != null) {
            paramFragmentTransitionImpl.getBoundsOnScreen(localView, paramView);
          }
        }
      });
      return paramArrayMap;
    }
    return null;
  }
  
  private static void configureTransitionsOrdered(ViewGroup paramViewGroup, FragmentContainerTransition paramFragmentContainerTransition, View paramView, ArrayMap<String, String> paramArrayMap, Callback paramCallback)
  {
    Fragment localFragment1 = paramFragmentContainerTransition.lastIn;
    final Fragment localFragment2 = paramFragmentContainerTransition.firstOut;
    FragmentTransitionImpl localFragmentTransitionImpl = chooseImpl(localFragment2, localFragment1);
    if (localFragmentTransitionImpl == null) {
      return;
    }
    boolean bool1 = paramFragmentContainerTransition.lastInIsPop;
    boolean bool2 = paramFragmentContainerTransition.firstOutIsPop;
    Object localObject2 = getEnterTransition(localFragmentTransitionImpl, localFragment1, bool1);
    Object localObject1 = getExitTransition(localFragmentTransitionImpl, localFragment2, bool2);
    final Object localObject4 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    Object localObject3 = configureSharedElementsOrdered(localFragmentTransitionImpl, paramViewGroup, paramView, paramArrayMap, paramFragmentContainerTransition, (ArrayList)localObject4, localArrayList2, localObject2, localObject1);
    if ((localObject2 == null) && (localObject3 == null) && (localObject1 == null)) {
      return;
    }
    ArrayList localArrayList1 = configureEnteringExitingViews(localFragmentTransitionImpl, localObject1, localFragment2, (ArrayList)localObject4, paramView);
    if ((localArrayList1 != null) && (!localArrayList1.isEmpty())) {
      break label150;
    }
    localObject1 = null;
    label150:
    localFragmentTransitionImpl.addTarget(localObject2, paramView);
    paramFragmentContainerTransition = mergeTransitions(localFragmentTransitionImpl, localObject2, localObject1, localObject3, localFragment1, paramFragmentContainerTransition.lastInIsPop);
    if ((localFragment2 != null) && (localArrayList1 != null) && ((localArrayList1.size() > 0) || (((ArrayList)localObject4).size() > 0)))
    {
      localObject4 = new CancellationSignal();
      paramCallback.onStart(localFragment2, (CancellationSignal)localObject4);
      localFragmentTransitionImpl.setListenerForTransitionEnd(localFragment2, paramFragmentContainerTransition, (CancellationSignal)localObject4, new Runnable()
      {
        public void run()
        {
          FragmentTransition.this.onComplete(localFragment2, localObject4);
        }
      });
    }
    if (paramFragmentContainerTransition != null)
    {
      paramCallback = new ArrayList();
      localFragmentTransitionImpl.scheduleRemoveTargets(paramFragmentContainerTransition, localObject2, paramCallback, localObject1, localArrayList1, localObject3, localArrayList2);
      scheduleTargetChange(localFragmentTransitionImpl, paramViewGroup, localFragment1, paramView, localArrayList2, localObject2, paramCallback, localObject1, localArrayList1);
      localFragmentTransitionImpl.setNameOverridesOrdered(paramViewGroup, localArrayList2, paramArrayMap);
      localFragmentTransitionImpl.beginDelayedTransition(paramViewGroup, paramFragmentContainerTransition);
      localFragmentTransitionImpl.scheduleNameReset(paramViewGroup, localArrayList2, paramArrayMap);
    }
  }
  
  private static void configureTransitionsReordered(ViewGroup paramViewGroup, FragmentContainerTransition paramFragmentContainerTransition, View paramView, ArrayMap<String, String> paramArrayMap, Callback paramCallback)
  {
    Object localObject3 = paramFragmentContainerTransition.lastIn;
    final Fragment localFragment = paramFragmentContainerTransition.firstOut;
    FragmentTransitionImpl localFragmentTransitionImpl = chooseImpl(localFragment, (Fragment)localObject3);
    if (localFragmentTransitionImpl == null) {
      return;
    }
    boolean bool2 = paramFragmentContainerTransition.lastInIsPop;
    boolean bool1 = paramFragmentContainerTransition.firstOutIsPop;
    ArrayList localArrayList2 = new ArrayList();
    ArrayList localArrayList1 = new ArrayList();
    Object localObject2 = getEnterTransition(localFragmentTransitionImpl, (Fragment)localObject3, bool2);
    Object localObject1 = getExitTransition(localFragmentTransitionImpl, localFragment, bool1);
    paramFragmentContainerTransition = configureSharedElementsReordered(localFragmentTransitionImpl, paramViewGroup, paramView, paramArrayMap, paramFragmentContainerTransition, localArrayList1, localArrayList2, localObject2, localObject1);
    if ((localObject2 == null) && (paramFragmentContainerTransition == null) && (localObject1 == null)) {
      return;
    }
    ArrayList localArrayList3 = configureEnteringExitingViews(localFragmentTransitionImpl, localObject1, localFragment, localArrayList1, paramView);
    paramView = configureEnteringExitingViews(localFragmentTransitionImpl, localObject2, (Fragment)localObject3, localArrayList2, paramView);
    setViewVisibility(paramView, 4);
    localObject3 = mergeTransitions(localFragmentTransitionImpl, localObject2, localObject1, paramFragmentContainerTransition, (Fragment)localObject3, bool2);
    if ((localFragment != null) && (localArrayList3 != null))
    {
      if ((localArrayList3.size() <= 0) && (localArrayList1.size() <= 0)) {
        break label239;
      }
      final CancellationSignal localCancellationSignal = new CancellationSignal();
      paramCallback.onStart(localFragment, localCancellationSignal);
      localFragmentTransitionImpl.setListenerForTransitionEnd(localFragment, localObject3, localCancellationSignal, new Runnable()
      {
        public void run()
        {
          FragmentTransition.this.onComplete(localFragment, localCancellationSignal);
        }
      });
    }
    label239:
    if (localObject3 != null)
    {
      replaceHide(localFragmentTransitionImpl, localObject1, localFragment, localArrayList3);
      paramCallback = localFragmentTransitionImpl.prepareSetNameOverridesReordered(localArrayList2);
      localFragmentTransitionImpl.scheduleRemoveTargets(localObject3, localObject2, paramView, localObject1, localArrayList3, paramFragmentContainerTransition, localArrayList2);
      localFragmentTransitionImpl.beginDelayedTransition(paramViewGroup, localObject3);
      localFragmentTransitionImpl.setNameOverridesReordered(paramViewGroup, localArrayList1, localArrayList2, paramCallback, paramArrayMap);
      setViewVisibility(paramView, 0);
      localFragmentTransitionImpl.swapSharedElementTargets(paramFragmentContainerTransition, localArrayList1, localArrayList2);
    }
  }
  
  private static FragmentContainerTransition ensureContainer(FragmentContainerTransition paramFragmentContainerTransition, SparseArray<FragmentContainerTransition> paramSparseArray, int paramInt)
  {
    FragmentContainerTransition localFragmentContainerTransition = paramFragmentContainerTransition;
    if (paramFragmentContainerTransition == null)
    {
      localFragmentContainerTransition = new FragmentContainerTransition();
      paramSparseArray.put(paramInt, localFragmentContainerTransition);
    }
    return localFragmentContainerTransition;
  }
  
  static String findKeyForValue(ArrayMap<String, String> paramArrayMap, String paramString)
  {
    int j = paramArrayMap.size();
    for (int i = 0; i < j; i++) {
      if (paramString.equals(paramArrayMap.valueAt(i))) {
        return (String)paramArrayMap.keyAt(i);
      }
    }
    return null;
  }
  
  private static Object getEnterTransition(FragmentTransitionImpl paramFragmentTransitionImpl, Fragment paramFragment, boolean paramBoolean)
  {
    if (paramFragment == null) {
      return null;
    }
    if (paramBoolean) {
      paramFragment = paramFragment.getReenterTransition();
    } else {
      paramFragment = paramFragment.getEnterTransition();
    }
    return paramFragmentTransitionImpl.cloneTransition(paramFragment);
  }
  
  private static Object getExitTransition(FragmentTransitionImpl paramFragmentTransitionImpl, Fragment paramFragment, boolean paramBoolean)
  {
    if (paramFragment == null) {
      return null;
    }
    if (paramBoolean) {
      paramFragment = paramFragment.getReturnTransition();
    } else {
      paramFragment = paramFragment.getExitTransition();
    }
    return paramFragmentTransitionImpl.cloneTransition(paramFragment);
  }
  
  static View getInEpicenterView(ArrayMap<String, View> paramArrayMap, FragmentContainerTransition paramFragmentContainerTransition, Object paramObject, boolean paramBoolean)
  {
    paramFragmentContainerTransition = paramFragmentContainerTransition.lastInTransaction;
    if ((paramObject != null) && (paramArrayMap != null) && (paramFragmentContainerTransition.mSharedElementSourceNames != null) && (!paramFragmentContainerTransition.mSharedElementSourceNames.isEmpty()))
    {
      if (paramBoolean) {
        paramFragmentContainerTransition = (String)paramFragmentContainerTransition.mSharedElementSourceNames.get(0);
      } else {
        paramFragmentContainerTransition = (String)paramFragmentContainerTransition.mSharedElementTargetNames.get(0);
      }
      return (View)paramArrayMap.get(paramFragmentContainerTransition);
    }
    return null;
  }
  
  private static Object getSharedElementTransition(FragmentTransitionImpl paramFragmentTransitionImpl, Fragment paramFragment1, Fragment paramFragment2, boolean paramBoolean)
  {
    if ((paramFragment1 != null) && (paramFragment2 != null))
    {
      if (paramBoolean) {
        paramFragment1 = paramFragment2.getSharedElementReturnTransition();
      } else {
        paramFragment1 = paramFragment1.getSharedElementEnterTransition();
      }
      return paramFragmentTransitionImpl.wrapTransitionInSet(paramFragmentTransitionImpl.cloneTransition(paramFragment1));
    }
    return null;
  }
  
  private static Object mergeTransitions(FragmentTransitionImpl paramFragmentTransitionImpl, Object paramObject1, Object paramObject2, Object paramObject3, Fragment paramFragment, boolean paramBoolean)
  {
    boolean bool2 = true;
    boolean bool1 = bool2;
    if (paramObject1 != null)
    {
      bool1 = bool2;
      if (paramObject2 != null)
      {
        bool1 = bool2;
        if (paramFragment != null)
        {
          if (paramBoolean) {
            paramBoolean = paramFragment.getAllowReturnTransitionOverlap();
          } else {
            paramBoolean = paramFragment.getAllowEnterTransitionOverlap();
          }
          bool1 = paramBoolean;
        }
      }
    }
    if (bool1) {
      paramFragmentTransitionImpl = paramFragmentTransitionImpl.mergeTransitionsTogether(paramObject2, paramObject1, paramObject3);
    } else {
      paramFragmentTransitionImpl = paramFragmentTransitionImpl.mergeTransitionsInSequence(paramObject2, paramObject1, paramObject3);
    }
    return paramFragmentTransitionImpl;
  }
  
  private static void replaceHide(FragmentTransitionImpl paramFragmentTransitionImpl, Object paramObject, Fragment paramFragment, ArrayList<View> paramArrayList)
  {
    if ((paramFragment != null) && (paramObject != null) && (paramFragment.mAdded) && (paramFragment.mHidden) && (paramFragment.mHiddenChanged))
    {
      paramFragment.setHideReplaced(true);
      paramFragmentTransitionImpl.scheduleHideFragmentView(paramObject, paramFragment.getView(), paramArrayList);
      OneShotPreDrawListener.add(paramFragment.mContainer, new Runnable()
      {
        public void run()
        {
          FragmentTransition.setViewVisibility(FragmentTransition.this, 4);
        }
      });
    }
  }
  
  private static FragmentTransitionImpl resolveSupportImpl()
  {
    try
    {
      FragmentTransitionImpl localFragmentTransitionImpl = (FragmentTransitionImpl)Class.forName("androidx.transition.FragmentTransitionSupport").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
      return localFragmentTransitionImpl;
    }
    catch (Exception localException) {}
    return null;
  }
  
  static void retainValues(ArrayMap<String, String> paramArrayMap, ArrayMap<String, View> paramArrayMap1)
  {
    for (int i = paramArrayMap.size() - 1; i >= 0; i--) {
      if (!paramArrayMap1.containsKey((String)paramArrayMap.valueAt(i))) {
        paramArrayMap.removeAt(i);
      }
    }
  }
  
  private static void scheduleTargetChange(final FragmentTransitionImpl paramFragmentTransitionImpl, ViewGroup paramViewGroup, final Fragment paramFragment, final View paramView, final ArrayList<View> paramArrayList1, Object paramObject1, final ArrayList<View> paramArrayList2, final Object paramObject2, final ArrayList<View> paramArrayList3)
  {
    OneShotPreDrawListener.add(paramViewGroup, new Runnable()
    {
      public void run()
      {
        Object localObject = FragmentTransition.this;
        if (localObject != null)
        {
          paramFragmentTransitionImpl.removeTarget(localObject, paramView);
          localObject = FragmentTransition.configureEnteringExitingViews(paramFragmentTransitionImpl, FragmentTransition.this, paramFragment, paramArrayList1, paramView);
          paramArrayList2.addAll((Collection)localObject);
        }
        if (paramArrayList3 != null)
        {
          if (paramObject2 != null)
          {
            localObject = new ArrayList();
            ((ArrayList)localObject).add(paramView);
            paramFragmentTransitionImpl.replaceTargets(paramObject2, paramArrayList3, (ArrayList)localObject);
          }
          paramArrayList3.clear();
          paramArrayList3.add(paramView);
        }
      }
    });
  }
  
  private static void setOutEpicenter(FragmentTransitionImpl paramFragmentTransitionImpl, Object paramObject1, Object paramObject2, ArrayMap<String, View> paramArrayMap, boolean paramBoolean, BackStackRecord paramBackStackRecord)
  {
    if ((paramBackStackRecord.mSharedElementSourceNames != null) && (!paramBackStackRecord.mSharedElementSourceNames.isEmpty()))
    {
      if (paramBoolean) {
        paramBackStackRecord = (String)paramBackStackRecord.mSharedElementTargetNames.get(0);
      } else {
        paramBackStackRecord = (String)paramBackStackRecord.mSharedElementSourceNames.get(0);
      }
      paramArrayMap = (View)paramArrayMap.get(paramBackStackRecord);
      paramFragmentTransitionImpl.setEpicenter(paramObject1, paramArrayMap);
      if (paramObject2 != null) {
        paramFragmentTransitionImpl.setEpicenter(paramObject2, paramArrayMap);
      }
    }
  }
  
  static void setViewVisibility(ArrayList<View> paramArrayList, int paramInt)
  {
    if (paramArrayList == null) {
      return;
    }
    for (int i = paramArrayList.size() - 1; i >= 0; i--) {
      ((View)paramArrayList.get(i)).setVisibility(paramInt);
    }
  }
  
  static void startTransitions(Context paramContext, FragmentContainer paramFragmentContainer, ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1, int paramInt1, int paramInt2, boolean paramBoolean, Callback paramCallback)
  {
    SparseArray localSparseArray = new SparseArray();
    Object localObject;
    for (int i = paramInt1; i < paramInt2; i++)
    {
      localObject = (BackStackRecord)paramArrayList.get(i);
      if (((Boolean)paramArrayList1.get(i)).booleanValue()) {
        calculatePopFragments((BackStackRecord)localObject, localSparseArray, paramBoolean);
      } else {
        calculateFragments((BackStackRecord)localObject, localSparseArray, paramBoolean);
      }
    }
    if (localSparseArray.size() != 0)
    {
      View localView = new View(paramContext);
      int j = localSparseArray.size();
      for (i = 0; i < j; i++)
      {
        int k = localSparseArray.keyAt(i);
        paramContext = calculateNameOverrides(k, paramArrayList, paramArrayList1, paramInt1, paramInt2);
        FragmentContainerTransition localFragmentContainerTransition = (FragmentContainerTransition)localSparseArray.valueAt(i);
        if (paramFragmentContainer.onHasView())
        {
          localObject = (ViewGroup)paramFragmentContainer.onFindViewById(k);
          if (localObject != null) {
            if (paramBoolean) {
              configureTransitionsReordered((ViewGroup)localObject, localFragmentContainerTransition, localView, paramContext, paramCallback);
            } else {
              configureTransitionsOrdered((ViewGroup)localObject, localFragmentContainerTransition, localView, paramContext, paramCallback);
            }
          }
        }
      }
    }
  }
  
  static boolean supportsTransition()
  {
    boolean bool;
    if ((PLATFORM_IMPL == null) && (SUPPORT_IMPL == null)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  static abstract interface Callback
  {
    public abstract void onComplete(Fragment paramFragment, CancellationSignal paramCancellationSignal);
    
    public abstract void onStart(Fragment paramFragment, CancellationSignal paramCancellationSignal);
  }
  
  static class FragmentContainerTransition
  {
    public Fragment firstOut;
    public boolean firstOutIsPop;
    public BackStackRecord firstOutTransaction;
    public Fragment lastIn;
    public boolean lastInIsPop;
    public BackStackRecord lastInTransaction;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/fragment/app/FragmentTransition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */