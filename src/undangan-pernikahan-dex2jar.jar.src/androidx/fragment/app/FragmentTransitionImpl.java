package androidx.fragment.app;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.os.CancellationSignal;
import androidx.core.view.OneShotPreDrawListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewGroupCompat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public abstract class FragmentTransitionImpl
{
  protected static void bfsAddViewChildren(List<View> paramList, View paramView)
  {
    int k = paramList.size();
    if (containedBeforeIndex(paramList, paramView, k)) {
      return;
    }
    Object localObject = ViewCompat.getTransitionName(paramView);
    Log5ECF72.a(localObject);
    LogE84000.a(localObject);
    Log229316.a(localObject);
    if (localObject != null) {
      paramList.add(paramView);
    }
    for (int i = k; i < paramList.size(); i++)
    {
      paramView = (View)paramList.get(i);
      if ((paramView instanceof ViewGroup))
      {
        ViewGroup localViewGroup = (ViewGroup)paramView;
        int m = localViewGroup.getChildCount();
        for (int j = 0; j < m; j++)
        {
          localObject = localViewGroup.getChildAt(j);
          if (!containedBeforeIndex(paramList, (View)localObject, k))
          {
            paramView = ViewCompat.getTransitionName((View)localObject);
            Log5ECF72.a(paramView);
            LogE84000.a(paramView);
            Log229316.a(paramView);
            if (paramView != null) {
              paramList.add(localObject);
            }
          }
        }
      }
    }
  }
  
  private static boolean containedBeforeIndex(List<View> paramList, View paramView, int paramInt)
  {
    for (int i = 0; i < paramInt; i++) {
      if (paramList.get(i) == paramView) {
        return true;
      }
    }
    return false;
  }
  
  static String findKeyForValue(Map<String, String> paramMap, String paramString)
  {
    paramMap = paramMap.entrySet().iterator();
    while (paramMap.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)paramMap.next();
      if (paramString.equals(localEntry.getValue())) {
        return (String)localEntry.getKey();
      }
    }
    return null;
  }
  
  protected static boolean isNullOrEmpty(List paramList)
  {
    boolean bool;
    if ((paramList != null) && (!paramList.isEmpty())) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public abstract void addTarget(Object paramObject, View paramView);
  
  public abstract void addTargets(Object paramObject, ArrayList<View> paramArrayList);
  
  public abstract void beginDelayedTransition(ViewGroup paramViewGroup, Object paramObject);
  
  public abstract boolean canHandle(Object paramObject);
  
  void captureTransitioningViews(ArrayList<View> paramArrayList, View paramView)
  {
    if (paramView.getVisibility() == 0) {
      if ((paramView instanceof ViewGroup))
      {
        paramView = (ViewGroup)paramView;
        if (ViewGroupCompat.isTransitionGroup(paramView))
        {
          paramArrayList.add(paramView);
        }
        else
        {
          int j = paramView.getChildCount();
          for (int i = 0; i < j; i++) {
            captureTransitioningViews(paramArrayList, paramView.getChildAt(i));
          }
        }
      }
      else
      {
        paramArrayList.add(paramView);
      }
    }
  }
  
  public abstract Object cloneTransition(Object paramObject);
  
  void findNamedViews(Map<String, View> paramMap, View paramView)
  {
    if (paramView.getVisibility() == 0)
    {
      String str = ViewCompat.getTransitionName(paramView);
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      if (str != null) {
        paramMap.put(str, paramView);
      }
      if ((paramView instanceof ViewGroup))
      {
        paramView = (ViewGroup)paramView;
        int j = paramView.getChildCount();
        for (int i = 0; i < j; i++) {
          findNamedViews(paramMap, paramView.getChildAt(i));
        }
      }
    }
  }
  
  protected void getBoundsOnScreen(View paramView, Rect paramRect)
  {
    if (!ViewCompat.isAttachedToWindow(paramView)) {
      return;
    }
    RectF localRectF = new RectF();
    localRectF.set(0.0F, 0.0F, paramView.getWidth(), paramView.getHeight());
    paramView.getMatrix().mapRect(localRectF);
    localRectF.offset(paramView.getLeft(), paramView.getTop());
    for (Object localObject = paramView.getParent(); (localObject instanceof View); localObject = ((View)localObject).getParent())
    {
      localObject = (View)localObject;
      localRectF.offset(-((View)localObject).getScrollX(), -((View)localObject).getScrollY());
      ((View)localObject).getMatrix().mapRect(localRectF);
      localRectF.offset(((View)localObject).getLeft(), ((View)localObject).getTop());
    }
    localObject = new int[2];
    paramView.getRootView().getLocationOnScreen((int[])localObject);
    localRectF.offset(localObject[0], localObject[1]);
    paramRect.set(Math.round(localRectF.left), Math.round(localRectF.top), Math.round(localRectF.right), Math.round(localRectF.bottom));
  }
  
  public abstract Object mergeTransitionsInSequence(Object paramObject1, Object paramObject2, Object paramObject3);
  
  public abstract Object mergeTransitionsTogether(Object paramObject1, Object paramObject2, Object paramObject3);
  
  ArrayList<String> prepareSetNameOverridesReordered(ArrayList<View> paramArrayList)
  {
    ArrayList localArrayList = new ArrayList();
    int j = paramArrayList.size();
    for (int i = 0; i < j; i++)
    {
      View localView = (View)paramArrayList.get(i);
      String str = ViewCompat.getTransitionName(localView);
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      localArrayList.add(str);
      ViewCompat.setTransitionName(localView, null);
    }
    return localArrayList;
  }
  
  public abstract void removeTarget(Object paramObject, View paramView);
  
  public abstract void replaceTargets(Object paramObject, ArrayList<View> paramArrayList1, ArrayList<View> paramArrayList2);
  
  public abstract void scheduleHideFragmentView(Object paramObject, View paramView, ArrayList<View> paramArrayList);
  
  void scheduleNameReset(ViewGroup paramViewGroup, final ArrayList<View> paramArrayList, final Map<String, String> paramMap)
  {
    OneShotPreDrawListener.add(paramViewGroup, new Runnable()
    {
      public void run()
      {
        int j = paramArrayList.size();
        for (int i = 0; i < j; i++)
        {
          View localView = (View)paramArrayList.get(i);
          String str = ViewCompat.getTransitionName(localView);
          Log5ECF72.a(str);
          LogE84000.a(str);
          Log229316.a(str);
          ViewCompat.setTransitionName(localView, (String)paramMap.get(str));
        }
      }
    });
  }
  
  public abstract void scheduleRemoveTargets(Object paramObject1, Object paramObject2, ArrayList<View> paramArrayList1, Object paramObject3, ArrayList<View> paramArrayList2, Object paramObject4, ArrayList<View> paramArrayList3);
  
  public abstract void setEpicenter(Object paramObject, Rect paramRect);
  
  public abstract void setEpicenter(Object paramObject, View paramView);
  
  public void setListenerForTransitionEnd(Fragment paramFragment, Object paramObject, CancellationSignal paramCancellationSignal, Runnable paramRunnable)
  {
    paramRunnable.run();
  }
  
  void setNameOverridesOrdered(View paramView, final ArrayList<View> paramArrayList, final Map<String, String> paramMap)
  {
    OneShotPreDrawListener.add(paramView, new Runnable()
    {
      public void run()
      {
        int j = paramArrayList.size();
        for (int i = 0; i < j; i++)
        {
          View localView = (View)paramArrayList.get(i);
          String str = ViewCompat.getTransitionName(localView);
          Log5ECF72.a(str);
          LogE84000.a(str);
          Log229316.a(str);
          if (str != null)
          {
            str = FragmentTransitionImpl.findKeyForValue(paramMap, str);
            Log5ECF72.a(str);
            LogE84000.a(str);
            Log229316.a(str);
            ViewCompat.setTransitionName(localView, str);
          }
        }
      }
    });
  }
  
  void setNameOverridesReordered(View paramView, final ArrayList<View> paramArrayList1, final ArrayList<View> paramArrayList2, final ArrayList<String> paramArrayList, Map<String, String> paramMap)
  {
    final int k = paramArrayList2.size();
    final ArrayList localArrayList = new ArrayList();
    for (int i = 0; i < k; i++)
    {
      Object localObject = (View)paramArrayList1.get(i);
      String str = ViewCompat.getTransitionName((View)localObject);
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      localArrayList.add(str);
      if (str != null)
      {
        ViewCompat.setTransitionName((View)localObject, null);
        localObject = (String)paramMap.get(str);
        for (int j = 0; j < k; j++) {
          if (((String)localObject).equals(paramArrayList.get(j)))
          {
            ViewCompat.setTransitionName((View)paramArrayList2.get(j), str);
            break;
          }
        }
      }
    }
    OneShotPreDrawListener.add(paramView, new Runnable()
    {
      public void run()
      {
        for (int i = 0; i < k; i++)
        {
          ViewCompat.setTransitionName((View)paramArrayList2.get(i), (String)paramArrayList.get(i));
          ViewCompat.setTransitionName((View)paramArrayList1.get(i), (String)localArrayList.get(i));
        }
      }
    });
  }
  
  public abstract void setSharedElementTargets(Object paramObject, View paramView, ArrayList<View> paramArrayList);
  
  public abstract void swapSharedElementTargets(Object paramObject, ArrayList<View> paramArrayList1, ArrayList<View> paramArrayList2);
  
  public abstract Object wrapTransitionInSet(Object paramObject);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/fragment/app/FragmentTransitionImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */