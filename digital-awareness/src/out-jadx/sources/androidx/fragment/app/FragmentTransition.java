package androidx.fragment.app;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.collection.ArrayMap;
import androidx.core.app.SharedElementCallback;
import androidx.core.os.CancellationSignal;
import androidx.core.view.OneShotPreDrawListener;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentTransaction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: 007E.java */
/* loaded from: classes.dex */
public class FragmentTransition {
    private static final int[] INVERSE_OPS = {0, 3, 0, 1, 5, 4, 7, 6, 9, 8, 10};
    static final FragmentTransitionImpl PLATFORM_IMPL;
    static final FragmentTransitionImpl SUPPORT_IMPL;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public interface Callback {
        void onComplete(Fragment fragment, CancellationSignal cancellationSignal);

        void onStart(Fragment fragment, CancellationSignal cancellationSignal);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class FragmentContainerTransition {
        public Fragment firstOut;
        public boolean firstOutIsPop;
        public BackStackRecord firstOutTransaction;
        public Fragment lastIn;
        public boolean lastInIsPop;
        public BackStackRecord lastInTransaction;

        FragmentContainerTransition() {
        }
    }

    static {
        PLATFORM_IMPL = Build.VERSION.SDK_INT >= 21 ? new FragmentTransitionCompat21() : null;
        SUPPORT_IMPL = resolveSupportImpl();
    }

    private FragmentTransition() {
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    private static void addSharedElementsWithMatchingNames(ArrayList<View> arrayList, ArrayMap<String, View> arrayMap, Collection<String> collection) {
        for (int size = arrayMap.size() - 1; size >= 0; size--) {
            View valueAt = arrayMap.valueAt(size);
            String transitionName = ViewCompat.getTransitionName(valueAt);
            Log5ECF72.a(transitionName);
            LogE84000.a(transitionName);
            Log229316.a(transitionName);
            if (collection.contains(transitionName)) {
                arrayList.add(valueAt);
            }
        }
    }

    private static void addToFirstInLastOut(BackStackRecord transaction, FragmentTransaction.Op op, SparseArray<FragmentContainerTransition> sparseArray, boolean isPop, boolean isReorderedTransaction) {
        int i;
        Fragment fragment = op.mFragment;
        if (fragment == null || (i = fragment.mContainerId) == 0) {
            return;
        }
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        switch (isPop ? INVERSE_OPS[op.mCmd] : op.mCmd) {
            case 1:
            case 7:
                if (isReorderedTransaction) {
                    z = fragment.mIsNewlyAdded;
                } else {
                    z = (fragment.mAdded || fragment.mHidden) ? false : true;
                }
                z4 = true;
                break;
            case 3:
            case 6:
                if (isReorderedTransaction) {
                    z3 = !fragment.mAdded && fragment.mView != null && fragment.mView.getVisibility() == 0 && fragment.mPostponedAlpha >= 0.0f;
                } else {
                    z3 = fragment.mAdded && !fragment.mHidden;
                }
                z2 = true;
                break;
            case 4:
                if (isReorderedTransaction) {
                    z3 = fragment.mHiddenChanged && fragment.mAdded && fragment.mHidden;
                } else {
                    z3 = fragment.mAdded && !fragment.mHidden;
                }
                z2 = true;
                break;
            case 5:
                if (isReorderedTransaction) {
                    z = fragment.mHiddenChanged && !fragment.mHidden && fragment.mAdded;
                } else {
                    z = fragment.mHidden;
                }
                z4 = true;
                break;
        }
        FragmentContainerTransition fragmentContainerTransition = sparseArray.get(i);
        if (z) {
            fragmentContainerTransition = ensureContainer(fragmentContainerTransition, sparseArray, i);
            fragmentContainerTransition.lastIn = fragment;
            fragmentContainerTransition.lastInIsPop = isPop;
            fragmentContainerTransition.lastInTransaction = transaction;
        }
        if (!isReorderedTransaction && z4) {
            if (fragmentContainerTransition != null && fragmentContainerTransition.firstOut == fragment) {
                fragmentContainerTransition.firstOut = null;
            }
            if (!transaction.mReorderingAllowed) {
                FragmentManager fragmentManager = transaction.mManager;
                fragmentManager.getFragmentStore().makeActive(fragmentManager.createOrGetFragmentStateManager(fragment));
                fragmentManager.moveToState(fragment);
            }
        }
        if (z3 && (fragmentContainerTransition == null || fragmentContainerTransition.firstOut == null)) {
            fragmentContainerTransition = ensureContainer(fragmentContainerTransition, sparseArray, i);
            fragmentContainerTransition.firstOut = fragment;
            fragmentContainerTransition.firstOutIsPop = isPop;
            fragmentContainerTransition.firstOutTransaction = transaction;
        }
        if (isReorderedTransaction || !z2 || fragmentContainerTransition == null || fragmentContainerTransition.lastIn != fragment) {
            return;
        }
        fragmentContainerTransition.lastIn = null;
    }

    public static void calculateFragments(BackStackRecord transaction, SparseArray<FragmentContainerTransition> sparseArray, boolean isReordered) {
        int size = transaction.mOps.size();
        for (int i = 0; i < size; i++) {
            addToFirstInLastOut(transaction, transaction.mOps.get(i), sparseArray, false, isReordered);
        }
    }

    private static ArrayMap<String, String> calculateNameOverrides(int containerId, ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2, int startIndex, int endIndex) {
        ArrayList<String> arrayList3;
        ArrayList<String> arrayList4;
        ArrayMap<String, String> arrayMap = new ArrayMap<>();
        for (int i = endIndex - 1; i >= startIndex; i--) {
            BackStackRecord backStackRecord = arrayList.get(i);
            if (backStackRecord.interactsWith(containerId)) {
                boolean booleanValue = arrayList2.get(i).booleanValue();
                if (backStackRecord.mSharedElementSourceNames != null) {
                    int size = backStackRecord.mSharedElementSourceNames.size();
                    if (booleanValue) {
                        arrayList4 = backStackRecord.mSharedElementSourceNames;
                        arrayList3 = backStackRecord.mSharedElementTargetNames;
                    } else {
                        arrayList3 = backStackRecord.mSharedElementSourceNames;
                        arrayList4 = backStackRecord.mSharedElementTargetNames;
                    }
                    for (int i2 = 0; i2 < size; i2++) {
                        String str = arrayList3.get(i2);
                        String str2 = arrayList4.get(i2);
                        String remove = arrayMap.remove(str2);
                        if (remove != null) {
                            arrayMap.put(str, remove);
                        } else {
                            arrayMap.put(str, str2);
                        }
                    }
                }
            }
        }
        return arrayMap;
    }

    public static void calculatePopFragments(BackStackRecord transaction, SparseArray<FragmentContainerTransition> sparseArray, boolean isReordered) {
        if (transaction.mManager.getContainer().onHasView()) {
            for (int size = transaction.mOps.size() - 1; size >= 0; size--) {
                addToFirstInLastOut(transaction, transaction.mOps.get(size), sparseArray, true, isReordered);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void callSharedElementStartEnd(Fragment inFragment, Fragment outFragment, boolean isPop, ArrayMap<String, View> arrayMap, boolean isStart) {
        SharedElementCallback enterTransitionCallback = isPop ? outFragment.getEnterTransitionCallback() : inFragment.getEnterTransitionCallback();
        if (enterTransitionCallback != null) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            int size = arrayMap == null ? 0 : arrayMap.size();
            for (int i = 0; i < size; i++) {
                arrayList2.add(arrayMap.keyAt(i));
                arrayList.add(arrayMap.valueAt(i));
            }
            if (isStart) {
                enterTransitionCallback.onSharedElementStart(arrayList2, arrayList, null);
            } else {
                enterTransitionCallback.onSharedElementEnd(arrayList2, arrayList, null);
            }
        }
    }

    private static boolean canHandleAll(FragmentTransitionImpl impl, List<Object> list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (!impl.canHandle(list.get(i))) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    static ArrayMap<String, View> captureInSharedElements(FragmentTransitionImpl fragmentTransitionImpl, ArrayMap<String, String> arrayMap, Object obj, FragmentContainerTransition fragmentContainerTransition) {
        SharedElementCallback enterTransitionCallback;
        ArrayList<String> arrayList;
        Fragment fragment = fragmentContainerTransition.lastIn;
        View view = fragment.getView();
        if (arrayMap.isEmpty() || obj == null || view == null) {
            arrayMap.clear();
            return null;
        }
        ArrayMap<String, View> arrayMap2 = new ArrayMap<>();
        fragmentTransitionImpl.findNamedViews(arrayMap2, view);
        BackStackRecord backStackRecord = fragmentContainerTransition.lastInTransaction;
        if (fragmentContainerTransition.lastInIsPop) {
            enterTransitionCallback = fragment.getExitTransitionCallback();
            arrayList = backStackRecord.mSharedElementSourceNames;
        } else {
            enterTransitionCallback = fragment.getEnterTransitionCallback();
            arrayList = backStackRecord.mSharedElementTargetNames;
        }
        if (arrayList != null) {
            arrayMap2.retainAll(arrayList);
            arrayMap2.retainAll(arrayMap.values());
        }
        if (enterTransitionCallback != null) {
            enterTransitionCallback.onMapSharedElements(arrayList, arrayMap2);
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                String str = arrayList.get(size);
                View view2 = arrayMap2.get(str);
                if (view2 == null) {
                    String findKeyForValue = findKeyForValue(arrayMap, str);
                    Log5ECF72.a(findKeyForValue);
                    LogE84000.a(findKeyForValue);
                    Log229316.a(findKeyForValue);
                    if (findKeyForValue != null) {
                        arrayMap.remove(findKeyForValue);
                    }
                } else {
                    String transitionName = ViewCompat.getTransitionName(view2);
                    Log5ECF72.a(transitionName);
                    LogE84000.a(transitionName);
                    Log229316.a(transitionName);
                    if (!str.equals(transitionName)) {
                        String findKeyForValue2 = findKeyForValue(arrayMap, str);
                        Log5ECF72.a(findKeyForValue2);
                        LogE84000.a(findKeyForValue2);
                        Log229316.a(findKeyForValue2);
                        if (findKeyForValue2 != null) {
                            String transitionName2 = ViewCompat.getTransitionName(view2);
                            Log5ECF72.a(transitionName2);
                            LogE84000.a(transitionName2);
                            Log229316.a(transitionName2);
                            arrayMap.put(findKeyForValue2, transitionName2);
                        }
                    }
                }
            }
        } else {
            retainValues(arrayMap, arrayMap2);
        }
        return arrayMap2;
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    private static ArrayMap<String, View> captureOutSharedElements(FragmentTransitionImpl fragmentTransitionImpl, ArrayMap<String, String> arrayMap, Object obj, FragmentContainerTransition fragmentContainerTransition) {
        SharedElementCallback exitTransitionCallback;
        ArrayList<String> arrayList;
        if (arrayMap.isEmpty() || obj == null) {
            arrayMap.clear();
            return null;
        }
        Fragment fragment = fragmentContainerTransition.firstOut;
        ArrayMap<String, View> arrayMap2 = new ArrayMap<>();
        fragmentTransitionImpl.findNamedViews(arrayMap2, fragment.requireView());
        BackStackRecord backStackRecord = fragmentContainerTransition.firstOutTransaction;
        if (fragmentContainerTransition.firstOutIsPop) {
            exitTransitionCallback = fragment.getEnterTransitionCallback();
            arrayList = backStackRecord.mSharedElementTargetNames;
        } else {
            exitTransitionCallback = fragment.getExitTransitionCallback();
            arrayList = backStackRecord.mSharedElementSourceNames;
        }
        if (arrayList != null) {
            arrayMap2.retainAll(arrayList);
        }
        if (exitTransitionCallback != null) {
            exitTransitionCallback.onMapSharedElements(arrayList, arrayMap2);
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                String str = arrayList.get(size);
                View view = arrayMap2.get(str);
                if (view == null) {
                    arrayMap.remove(str);
                } else {
                    String transitionName = ViewCompat.getTransitionName(view);
                    Log5ECF72.a(transitionName);
                    LogE84000.a(transitionName);
                    Log229316.a(transitionName);
                    if (!str.equals(transitionName)) {
                        String remove = arrayMap.remove(str);
                        String transitionName2 = ViewCompat.getTransitionName(view);
                        Log5ECF72.a(transitionName2);
                        LogE84000.a(transitionName2);
                        Log229316.a(transitionName2);
                        arrayMap.put(transitionName2, remove);
                    }
                }
            }
        } else {
            arrayMap.retainAll(arrayMap2.keySet());
        }
        return arrayMap2;
    }

    private static FragmentTransitionImpl chooseImpl(Fragment outFragment, Fragment inFragment) {
        ArrayList arrayList = new ArrayList();
        if (outFragment != null) {
            Object exitTransition = outFragment.getExitTransition();
            if (exitTransition != null) {
                arrayList.add(exitTransition);
            }
            Object returnTransition = outFragment.getReturnTransition();
            if (returnTransition != null) {
                arrayList.add(returnTransition);
            }
            Object sharedElementReturnTransition = outFragment.getSharedElementReturnTransition();
            if (sharedElementReturnTransition != null) {
                arrayList.add(sharedElementReturnTransition);
            }
        }
        if (inFragment != null) {
            Object enterTransition = inFragment.getEnterTransition();
            if (enterTransition != null) {
                arrayList.add(enterTransition);
            }
            Object reenterTransition = inFragment.getReenterTransition();
            if (reenterTransition != null) {
                arrayList.add(reenterTransition);
            }
            Object sharedElementEnterTransition = inFragment.getSharedElementEnterTransition();
            if (sharedElementEnterTransition != null) {
                arrayList.add(sharedElementEnterTransition);
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        FragmentTransitionImpl fragmentTransitionImpl = PLATFORM_IMPL;
        if (fragmentTransitionImpl != null && canHandleAll(fragmentTransitionImpl, arrayList)) {
            return fragmentTransitionImpl;
        }
        FragmentTransitionImpl fragmentTransitionImpl2 = SUPPORT_IMPL;
        if (fragmentTransitionImpl2 != null && canHandleAll(fragmentTransitionImpl2, arrayList)) {
            return fragmentTransitionImpl2;
        }
        if (fragmentTransitionImpl == null && fragmentTransitionImpl2 == null) {
            return null;
        }
        throw new IllegalArgumentException("Invalid Transition types");
    }

    static ArrayList<View> configureEnteringExitingViews(FragmentTransitionImpl impl, Object transition, Fragment fragment, ArrayList<View> arrayList, View nonExistentView) {
        ArrayList<View> arrayList2 = null;
        if (transition != null) {
            arrayList2 = new ArrayList<>();
            View view = fragment.getView();
            if (view != null) {
                impl.captureTransitioningViews(arrayList2, view);
            }
            if (arrayList != null) {
                arrayList2.removeAll(arrayList);
            }
            if (!arrayList2.isEmpty()) {
                arrayList2.add(nonExistentView);
                impl.addTargets(transition, arrayList2);
            }
        }
        return arrayList2;
    }

    private static Object configureSharedElementsOrdered(final FragmentTransitionImpl impl, ViewGroup sceneRoot, final View nonExistentView, final ArrayMap<String, String> arrayMap, final FragmentContainerTransition fragments, final ArrayList<View> arrayList, final ArrayList<View> arrayList2, final Object enterTransition, Object exitTransition) {
        Object obj;
        Rect rect;
        final Fragment fragment = fragments.lastIn;
        final Fragment fragment2 = fragments.firstOut;
        if (fragment != null && fragment2 != null) {
            final boolean z = fragments.lastInIsPop;
            Object sharedElementTransition = arrayMap.isEmpty() ? null : getSharedElementTransition(impl, fragment, fragment2, z);
            ArrayMap<String, View> captureOutSharedElements = captureOutSharedElements(impl, arrayMap, sharedElementTransition, fragments);
            if (arrayMap.isEmpty()) {
                obj = null;
            } else {
                arrayList.addAll(captureOutSharedElements.values());
                obj = sharedElementTransition;
            }
            if (enterTransition == null && exitTransition == null && obj == null) {
                return null;
            }
            callSharedElementStartEnd(fragment, fragment2, z, captureOutSharedElements, true);
            if (obj != null) {
                Rect rect2 = new Rect();
                impl.setSharedElementTargets(obj, nonExistentView, arrayList);
                setOutEpicenter(impl, obj, exitTransition, captureOutSharedElements, fragments.firstOutIsPop, fragments.firstOutTransaction);
                if (enterTransition != null) {
                    impl.setEpicenter(enterTransition, rect2);
                }
                rect = rect2;
            } else {
                rect = null;
            }
            final Object obj2 = obj;
            Object obj3 = obj;
            final Rect rect3 = rect;
            OneShotPreDrawListener.add(sceneRoot, new Runnable() { // from class: androidx.fragment.app.FragmentTransition.6
                @Override // java.lang.Runnable
                public void run() {
                    ArrayMap<String, View> captureInSharedElements = FragmentTransition.captureInSharedElements(FragmentTransitionImpl.this, arrayMap, obj2, fragments);
                    if (captureInSharedElements != null) {
                        arrayList2.addAll(captureInSharedElements.values());
                        arrayList2.add(nonExistentView);
                    }
                    FragmentTransition.callSharedElementStartEnd(fragment, fragment2, z, captureInSharedElements, false);
                    Object obj4 = obj2;
                    if (obj4 != null) {
                        FragmentTransitionImpl.this.swapSharedElementTargets(obj4, arrayList, arrayList2);
                        View inEpicenterView = FragmentTransition.getInEpicenterView(captureInSharedElements, fragments, enterTransition, z);
                        if (inEpicenterView != null) {
                            FragmentTransitionImpl.this.getBoundsOnScreen(inEpicenterView, rect3);
                        }
                    }
                }
            });
            return obj3;
        }
        return null;
    }

    private static Object configureSharedElementsReordered(final FragmentTransitionImpl impl, ViewGroup sceneRoot, View nonExistentView, ArrayMap<String, String> arrayMap, FragmentContainerTransition fragments, ArrayList<View> arrayList, ArrayList<View> arrayList2, Object enterTransition, Object exitTransition) {
        Object obj;
        Object obj2;
        ArrayMap<String, View> arrayMap2;
        Rect rect;
        View view;
        final Fragment fragment = fragments.lastIn;
        final Fragment fragment2 = fragments.firstOut;
        if (fragment != null) {
            fragment.requireView().setVisibility(0);
        }
        if (fragment != null && fragment2 != null) {
            final boolean z = fragments.lastInIsPop;
            Object sharedElementTransition = arrayMap.isEmpty() ? null : getSharedElementTransition(impl, fragment, fragment2, z);
            ArrayMap<String, View> captureOutSharedElements = captureOutSharedElements(impl, arrayMap, sharedElementTransition, fragments);
            ArrayMap<String, View> captureInSharedElements = captureInSharedElements(impl, arrayMap, sharedElementTransition, fragments);
            if (arrayMap.isEmpty()) {
                if (captureOutSharedElements != null) {
                    captureOutSharedElements.clear();
                }
                if (captureInSharedElements != null) {
                    captureInSharedElements.clear();
                }
                obj = null;
            } else {
                addSharedElementsWithMatchingNames(arrayList, captureOutSharedElements, arrayMap.keySet());
                addSharedElementsWithMatchingNames(arrayList2, captureInSharedElements, arrayMap.values());
                obj = sharedElementTransition;
            }
            if (enterTransition == null && exitTransition == null && obj == null) {
                return null;
            }
            callSharedElementStartEnd(fragment, fragment2, z, captureOutSharedElements, true);
            if (obj != null) {
                arrayList2.add(nonExistentView);
                impl.setSharedElementTargets(obj, nonExistentView, arrayList);
                obj2 = obj;
                arrayMap2 = captureInSharedElements;
                setOutEpicenter(impl, obj, exitTransition, captureOutSharedElements, fragments.firstOutIsPop, fragments.firstOutTransaction);
                Rect rect2 = new Rect();
                View inEpicenterView = getInEpicenterView(arrayMap2, fragments, enterTransition, z);
                if (inEpicenterView != null) {
                    impl.setEpicenter(enterTransition, rect2);
                }
                rect = rect2;
                view = inEpicenterView;
            } else {
                obj2 = obj;
                arrayMap2 = captureInSharedElements;
                rect = null;
                view = null;
            }
            final ArrayMap<String, View> arrayMap3 = arrayMap2;
            final View view2 = view;
            final Rect rect3 = rect;
            OneShotPreDrawListener.add(sceneRoot, new Runnable() { // from class: androidx.fragment.app.FragmentTransition.5
                @Override // java.lang.Runnable
                public void run() {
                    FragmentTransition.callSharedElementStartEnd(Fragment.this, fragment2, z, arrayMap3, false);
                    View view3 = view2;
                    if (view3 != null) {
                        impl.getBoundsOnScreen(view3, rect3);
                    }
                }
            });
            return obj2;
        }
        return null;
    }

    private static void configureTransitionsOrdered(ViewGroup container, FragmentContainerTransition fragments, View nonExistentView, ArrayMap<String, String> arrayMap, final Callback callback) {
        Object obj;
        Fragment fragment = fragments.lastIn;
        final Fragment fragment2 = fragments.firstOut;
        FragmentTransitionImpl chooseImpl = chooseImpl(fragment2, fragment);
        if (chooseImpl == null) {
            return;
        }
        boolean z = fragments.lastInIsPop;
        boolean z2 = fragments.firstOutIsPop;
        Object enterTransition = getEnterTransition(chooseImpl, fragment, z);
        Object exitTransition = getExitTransition(chooseImpl, fragment2, z2);
        ArrayList arrayList = new ArrayList();
        ArrayList<View> arrayList2 = new ArrayList<>();
        Object configureSharedElementsOrdered = configureSharedElementsOrdered(chooseImpl, container, nonExistentView, arrayMap, fragments, arrayList, arrayList2, enterTransition, exitTransition);
        if (enterTransition == null && configureSharedElementsOrdered == null) {
            obj = exitTransition;
            if (obj == null) {
                return;
            }
        } else {
            obj = exitTransition;
        }
        ArrayList<View> configureEnteringExitingViews = configureEnteringExitingViews(chooseImpl, obj, fragment2, arrayList, nonExistentView);
        Object obj2 = (configureEnteringExitingViews == null || configureEnteringExitingViews.isEmpty()) ? null : obj;
        chooseImpl.addTarget(enterTransition, nonExistentView);
        Object mergeTransitions = mergeTransitions(chooseImpl, enterTransition, obj2, configureSharedElementsOrdered, fragment, fragments.lastInIsPop);
        if (fragment2 != null && configureEnteringExitingViews != null && (configureEnteringExitingViews.size() > 0 || arrayList.size() > 0)) {
            final CancellationSignal cancellationSignal = new CancellationSignal();
            callback.onStart(fragment2, cancellationSignal);
            chooseImpl.setListenerForTransitionEnd(fragment2, mergeTransitions, cancellationSignal, new Runnable() { // from class: androidx.fragment.app.FragmentTransition.3
                @Override // java.lang.Runnable
                public void run() {
                    Callback.this.onComplete(fragment2, cancellationSignal);
                }
            });
        }
        if (mergeTransitions != null) {
            ArrayList<View> arrayList3 = new ArrayList<>();
            chooseImpl.scheduleRemoveTargets(mergeTransitions, enterTransition, arrayList3, obj2, configureEnteringExitingViews, configureSharedElementsOrdered, arrayList2);
            scheduleTargetChange(chooseImpl, container, fragment, nonExistentView, arrayList2, enterTransition, arrayList3, obj2, configureEnteringExitingViews);
            chooseImpl.setNameOverridesOrdered(container, arrayList2, arrayMap);
            chooseImpl.beginDelayedTransition(container, mergeTransitions);
            chooseImpl.scheduleNameReset(container, arrayList2, arrayMap);
        }
    }

    private static void configureTransitionsReordered(ViewGroup container, FragmentContainerTransition fragments, View nonExistentView, ArrayMap<String, String> arrayMap, final Callback callback) {
        Object obj;
        ArrayList<View> arrayList;
        Fragment fragment = fragments.lastIn;
        final Fragment fragment2 = fragments.firstOut;
        FragmentTransitionImpl chooseImpl = chooseImpl(fragment2, fragment);
        if (chooseImpl == null) {
            return;
        }
        boolean z = fragments.lastInIsPop;
        boolean z2 = fragments.firstOutIsPop;
        ArrayList<View> arrayList2 = new ArrayList<>();
        ArrayList<View> arrayList3 = new ArrayList<>();
        Object enterTransition = getEnterTransition(chooseImpl, fragment, z);
        Object exitTransition = getExitTransition(chooseImpl, fragment2, z2);
        Object configureSharedElementsReordered = configureSharedElementsReordered(chooseImpl, container, nonExistentView, arrayMap, fragments, arrayList3, arrayList2, enterTransition, exitTransition);
        if (enterTransition == null && configureSharedElementsReordered == null) {
            obj = exitTransition;
            if (obj == null) {
                return;
            }
        } else {
            obj = exitTransition;
        }
        ArrayList<View> configureEnteringExitingViews = configureEnteringExitingViews(chooseImpl, obj, fragment2, arrayList3, nonExistentView);
        ArrayList<View> configureEnteringExitingViews2 = configureEnteringExitingViews(chooseImpl, enterTransition, fragment, arrayList2, nonExistentView);
        setViewVisibility(configureEnteringExitingViews2, 4);
        Object mergeTransitions = mergeTransitions(chooseImpl, enterTransition, obj, configureSharedElementsReordered, fragment, z);
        if (fragment2 == null || configureEnteringExitingViews == null) {
            arrayList = arrayList2;
        } else if (configureEnteringExitingViews.size() > 0 || arrayList3.size() > 0) {
            final CancellationSignal cancellationSignal = new CancellationSignal();
            arrayList = arrayList2;
            callback.onStart(fragment2, cancellationSignal);
            chooseImpl.setListenerForTransitionEnd(fragment2, mergeTransitions, cancellationSignal, new Runnable() { // from class: androidx.fragment.app.FragmentTransition.1
                @Override // java.lang.Runnable
                public void run() {
                    Callback.this.onComplete(fragment2, cancellationSignal);
                }
            });
        } else {
            arrayList = arrayList2;
        }
        if (mergeTransitions != null) {
            replaceHide(chooseImpl, obj, fragment2, configureEnteringExitingViews);
            ArrayList<String> prepareSetNameOverridesReordered = chooseImpl.prepareSetNameOverridesReordered(arrayList);
            ArrayList<View> arrayList4 = arrayList;
            chooseImpl.scheduleRemoveTargets(mergeTransitions, enterTransition, configureEnteringExitingViews2, obj, configureEnteringExitingViews, configureSharedElementsReordered, arrayList4);
            chooseImpl.beginDelayedTransition(container, mergeTransitions);
            chooseImpl.setNameOverridesReordered(container, arrayList3, arrayList4, prepareSetNameOverridesReordered, arrayMap);
            setViewVisibility(configureEnteringExitingViews2, 0);
            chooseImpl.swapSharedElementTargets(configureSharedElementsReordered, arrayList3, arrayList4);
        }
    }

    private static FragmentContainerTransition ensureContainer(FragmentContainerTransition containerTransition, SparseArray<FragmentContainerTransition> sparseArray, int containerId) {
        if (containerTransition != null) {
            return containerTransition;
        }
        FragmentContainerTransition containerTransition2 = new FragmentContainerTransition();
        sparseArray.put(containerId, containerTransition2);
        return containerTransition2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String findKeyForValue(ArrayMap<String, String> arrayMap, String value) {
        int size = arrayMap.size();
        for (int i = 0; i < size; i++) {
            if (value.equals(arrayMap.valueAt(i))) {
                return arrayMap.keyAt(i);
            }
        }
        return null;
    }

    private static Object getEnterTransition(FragmentTransitionImpl impl, Fragment inFragment, boolean isPop) {
        if (inFragment == null) {
            return null;
        }
        return impl.cloneTransition(isPop ? inFragment.getReenterTransition() : inFragment.getEnterTransition());
    }

    private static Object getExitTransition(FragmentTransitionImpl impl, Fragment outFragment, boolean isPop) {
        if (outFragment == null) {
            return null;
        }
        return impl.cloneTransition(isPop ? outFragment.getReturnTransition() : outFragment.getExitTransition());
    }

    static View getInEpicenterView(ArrayMap<String, View> arrayMap, FragmentContainerTransition fragments, Object enterTransition, boolean inIsPop) {
        BackStackRecord backStackRecord = fragments.lastInTransaction;
        if (enterTransition == null || arrayMap == null || backStackRecord.mSharedElementSourceNames == null || backStackRecord.mSharedElementSourceNames.isEmpty()) {
            return null;
        }
        return arrayMap.get(inIsPop ? backStackRecord.mSharedElementSourceNames.get(0) : backStackRecord.mSharedElementTargetNames.get(0));
    }

    private static Object getSharedElementTransition(FragmentTransitionImpl impl, Fragment inFragment, Fragment outFragment, boolean isPop) {
        if (inFragment == null || outFragment == null) {
            return null;
        }
        return impl.wrapTransitionInSet(impl.cloneTransition(isPop ? outFragment.getSharedElementReturnTransition() : inFragment.getSharedElementEnterTransition()));
    }

    private static Object mergeTransitions(FragmentTransitionImpl impl, Object enterTransition, Object exitTransition, Object sharedElementTransition, Fragment inFragment, boolean isPop) {
        boolean z = true;
        if (enterTransition != null && exitTransition != null && inFragment != null) {
            z = isPop ? inFragment.getAllowReturnTransitionOverlap() : inFragment.getAllowEnterTransitionOverlap();
        }
        return z ? impl.mergeTransitionsTogether(exitTransition, enterTransition, sharedElementTransition) : impl.mergeTransitionsInSequence(exitTransition, enterTransition, sharedElementTransition);
    }

    private static void replaceHide(FragmentTransitionImpl impl, Object exitTransition, Fragment exitingFragment, final ArrayList<View> arrayList) {
        if (exitingFragment != null && exitTransition != null && exitingFragment.mAdded && exitingFragment.mHidden && exitingFragment.mHiddenChanged) {
            exitingFragment.setHideReplaced(true);
            impl.scheduleHideFragmentView(exitTransition, exitingFragment.getView(), arrayList);
            OneShotPreDrawListener.add(exitingFragment.mContainer, new Runnable() { // from class: androidx.fragment.app.FragmentTransition.2
                @Override // java.lang.Runnable
                public void run() {
                    FragmentTransition.setViewVisibility(arrayList, 4);
                }
            });
        }
    }

    private static FragmentTransitionImpl resolveSupportImpl() {
        try {
            return (FragmentTransitionImpl) Class.forName("androidx.transition.FragmentTransitionSupport").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception e) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void retainValues(ArrayMap<String, String> arrayMap, ArrayMap<String, View> arrayMap2) {
        for (int size = arrayMap.size() - 1; size >= 0; size--) {
            if (!arrayMap2.containsKey(arrayMap.valueAt(size))) {
                arrayMap.removeAt(size);
            }
        }
    }

    private static void scheduleTargetChange(final FragmentTransitionImpl impl, ViewGroup sceneRoot, final Fragment inFragment, final View nonExistentView, final ArrayList<View> arrayList, final Object enterTransition, final ArrayList<View> arrayList2, final Object exitTransition, final ArrayList<View> arrayList3) {
        OneShotPreDrawListener.add(sceneRoot, new Runnable() { // from class: androidx.fragment.app.FragmentTransition.4
            @Override // java.lang.Runnable
            public void run() {
                Object obj = enterTransition;
                if (obj != null) {
                    impl.removeTarget(obj, nonExistentView);
                    arrayList2.addAll(FragmentTransition.configureEnteringExitingViews(impl, enterTransition, inFragment, arrayList, nonExistentView));
                }
                if (arrayList3 != null) {
                    if (exitTransition != null) {
                        ArrayList<View> arrayList4 = new ArrayList<>();
                        arrayList4.add(nonExistentView);
                        impl.replaceTargets(exitTransition, arrayList3, arrayList4);
                    }
                    arrayList3.clear();
                    arrayList3.add(nonExistentView);
                }
            }
        });
    }

    private static void setOutEpicenter(FragmentTransitionImpl impl, Object sharedElementTransition, Object exitTransition, ArrayMap<String, View> arrayMap, boolean outIsPop, BackStackRecord outTransaction) {
        if (outTransaction.mSharedElementSourceNames == null || outTransaction.mSharedElementSourceNames.isEmpty()) {
            return;
        }
        View view = arrayMap.get(outIsPop ? outTransaction.mSharedElementTargetNames.get(0) : outTransaction.mSharedElementSourceNames.get(0));
        impl.setEpicenter(sharedElementTransition, view);
        if (exitTransition != null) {
            impl.setEpicenter(exitTransition, view);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setViewVisibility(ArrayList<View> arrayList, int visibility) {
        if (arrayList == null) {
            return;
        }
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            arrayList.get(size).setVisibility(visibility);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void startTransitions(Context context, FragmentContainer fragmentContainer, ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2, int startIndex, int endIndex, boolean isReordered, Callback callback) {
        ViewGroup viewGroup;
        SparseArray sparseArray = new SparseArray();
        for (int i = startIndex; i < endIndex; i++) {
            BackStackRecord backStackRecord = arrayList.get(i);
            if (arrayList2.get(i).booleanValue()) {
                calculatePopFragments(backStackRecord, sparseArray, isReordered);
            } else {
                calculateFragments(backStackRecord, sparseArray, isReordered);
            }
        }
        if (sparseArray.size() != 0) {
            View view = new View(context);
            int size = sparseArray.size();
            for (int i2 = 0; i2 < size; i2++) {
                int keyAt = sparseArray.keyAt(i2);
                ArrayMap<String, String> calculateNameOverrides = calculateNameOverrides(keyAt, arrayList, arrayList2, startIndex, endIndex);
                FragmentContainerTransition fragmentContainerTransition = (FragmentContainerTransition) sparseArray.valueAt(i2);
                if (fragmentContainer.onHasView() && (viewGroup = (ViewGroup) fragmentContainer.onFindViewById(keyAt)) != null) {
                    if (isReordered) {
                        configureTransitionsReordered(viewGroup, fragmentContainerTransition, view, calculateNameOverrides, callback);
                    } else {
                        configureTransitionsOrdered(viewGroup, fragmentContainerTransition, view, calculateNameOverrides, callback);
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean supportsTransition() {
        return (PLATFORM_IMPL == null && SUPPORT_IMPL == null) ? false : true;
    }
}
