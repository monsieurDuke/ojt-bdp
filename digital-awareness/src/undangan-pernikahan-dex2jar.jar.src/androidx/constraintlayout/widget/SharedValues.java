package androidx.constraintlayout.widget;

import android.util.SparseIntArray;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SharedValues
{
  public static final int UNSET = -1;
  private SparseIntArray mValues = new SparseIntArray();
  private HashMap<Integer, HashSet<WeakReference<SharedValuesListener>>> mValuesListeners = new HashMap();
  
  public void addListener(int paramInt, SharedValuesListener paramSharedValuesListener)
  {
    HashSet localHashSet2 = (HashSet)this.mValuesListeners.get(Integer.valueOf(paramInt));
    HashSet localHashSet1 = localHashSet2;
    if (localHashSet2 == null)
    {
      localHashSet1 = new HashSet();
      this.mValuesListeners.put(Integer.valueOf(paramInt), localHashSet1);
    }
    localHashSet1.add(new WeakReference(paramSharedValuesListener));
  }
  
  public void clearListeners()
  {
    this.mValuesListeners.clear();
  }
  
  public void fireNewValue(int paramInt1, int paramInt2)
  {
    int i = 0;
    int j = this.mValues.get(paramInt1, -1);
    if (j == paramInt2) {
      return;
    }
    this.mValues.put(paramInt1, paramInt2);
    HashSet localHashSet = (HashSet)this.mValuesListeners.get(Integer.valueOf(paramInt1));
    if (localHashSet == null) {
      return;
    }
    Object localObject1 = localHashSet.iterator();
    Object localObject2;
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (SharedValuesListener)((WeakReference)((Iterator)localObject1).next()).get();
      if (localObject2 != null) {
        ((SharedValuesListener)localObject2).onNewValue(paramInt1, paramInt2, j);
      } else {
        i = 1;
      }
    }
    if (i != 0)
    {
      localObject2 = new ArrayList();
      Iterator localIterator = localHashSet.iterator();
      while (localIterator.hasNext())
      {
        localObject1 = (WeakReference)localIterator.next();
        if ((SharedValuesListener)((WeakReference)localObject1).get() == null) {
          ((List)localObject2).add(localObject1);
        }
      }
      localHashSet.removeAll((Collection)localObject2);
    }
  }
  
  public int getValue(int paramInt)
  {
    return this.mValues.get(paramInt, -1);
  }
  
  public void removeListener(int paramInt, SharedValuesListener paramSharedValuesListener)
  {
    HashSet localHashSet = (HashSet)this.mValuesListeners.get(Integer.valueOf(paramInt));
    if (localHashSet == null) {
      return;
    }
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = localHashSet.iterator();
    while (localIterator.hasNext())
    {
      WeakReference localWeakReference = (WeakReference)localIterator.next();
      SharedValuesListener localSharedValuesListener = (SharedValuesListener)localWeakReference.get();
      if ((localSharedValuesListener == null) || (localSharedValuesListener == paramSharedValuesListener)) {
        localArrayList.add(localWeakReference);
      }
    }
    localHashSet.removeAll(localArrayList);
  }
  
  public void removeListener(SharedValuesListener paramSharedValuesListener)
  {
    Iterator localIterator = this.mValuesListeners.keySet().iterator();
    while (localIterator.hasNext()) {
      removeListener(((Integer)localIterator.next()).intValue(), paramSharedValuesListener);
    }
  }
  
  public static abstract interface SharedValuesListener
  {
    public abstract void onNewValue(int paramInt1, int paramInt2, int paramInt3);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/widget/SharedValues.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */