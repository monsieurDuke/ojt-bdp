package androidx.recyclerview.widget;

import androidx.core.os.TraceCompat;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

final class GapWorker
  implements Runnable
{
  static final ThreadLocal<GapWorker> sGapWorker = new ThreadLocal();
  static Comparator<Task> sTaskComparator = new Comparator()
  {
    public int compare(GapWorker.Task paramAnonymousTask1, GapWorker.Task paramAnonymousTask2)
    {
      RecyclerView localRecyclerView = paramAnonymousTask1.view;
      int k = 1;
      int m = 1;
      if (localRecyclerView == null) {
        i = 1;
      } else {
        i = 0;
      }
      int j;
      if (paramAnonymousTask2.view == null) {
        j = 1;
      } else {
        j = 0;
      }
      if (i != j)
      {
        if (paramAnonymousTask1.view == null) {
          i = m;
        } else {
          i = -1;
        }
        return i;
      }
      if (paramAnonymousTask1.immediate != paramAnonymousTask2.immediate)
      {
        i = k;
        if (paramAnonymousTask1.immediate) {
          i = -1;
        }
        return i;
      }
      int i = paramAnonymousTask2.viewVelocity - paramAnonymousTask1.viewVelocity;
      if (i != 0) {
        return i;
      }
      i = paramAnonymousTask1.distanceToItem - paramAnonymousTask2.distanceToItem;
      if (i != 0) {
        return i;
      }
      return 0;
    }
  };
  long mFrameIntervalNs;
  long mPostTimeNs;
  ArrayList<RecyclerView> mRecyclerViews = new ArrayList();
  private ArrayList<Task> mTasks = new ArrayList();
  
  private void buildTaskList()
  {
    int n = this.mRecyclerViews.size();
    int j = 0;
    int i = 0;
    Object localObject;
    int k;
    while (i < n)
    {
      localObject = (RecyclerView)this.mRecyclerViews.get(i);
      k = j;
      if (((RecyclerView)localObject).getWindowVisibility() == 0)
      {
        ((RecyclerView)localObject).mPrefetchRegistry.collectPrefetchPositionsFromView((RecyclerView)localObject, false);
        k = j + ((RecyclerView)localObject).mPrefetchRegistry.mCount;
      }
      i++;
      j = k;
    }
    this.mTasks.ensureCapacity(j);
    i = 0;
    j = 0;
    while (j < n)
    {
      RecyclerView localRecyclerView = (RecyclerView)this.mRecyclerViews.get(j);
      int m;
      if (localRecyclerView.getWindowVisibility() != 0)
      {
        m = i;
      }
      else
      {
        LayoutPrefetchRegistryImpl localLayoutPrefetchRegistryImpl = localRecyclerView.mPrefetchRegistry;
        int i1 = Math.abs(localLayoutPrefetchRegistryImpl.mPrefetchDx) + Math.abs(localLayoutPrefetchRegistryImpl.mPrefetchDy);
        for (k = 0;; k += 2)
        {
          m = i;
          if (k >= localLayoutPrefetchRegistryImpl.mCount * 2) {
            break;
          }
          if (i >= this.mTasks.size())
          {
            localObject = new Task();
            this.mTasks.add(localObject);
          }
          else
          {
            localObject = (Task)this.mTasks.get(i);
          }
          m = localLayoutPrefetchRegistryImpl.mPrefetchArray[(k + 1)];
          boolean bool;
          if (m <= i1) {
            bool = true;
          } else {
            bool = false;
          }
          ((Task)localObject).immediate = bool;
          ((Task)localObject).viewVelocity = i1;
          ((Task)localObject).distanceToItem = m;
          ((Task)localObject).view = localRecyclerView;
          ((Task)localObject).position = localLayoutPrefetchRegistryImpl.mPrefetchArray[k];
          i++;
        }
      }
      j++;
      i = m;
    }
    Collections.sort(this.mTasks, sTaskComparator);
  }
  
  private void flushTaskWithDeadline(Task paramTask, long paramLong)
  {
    long l;
    if (paramTask.immediate) {
      l = Long.MAX_VALUE;
    } else {
      l = paramLong;
    }
    paramTask = prefetchPositionWithDeadline(paramTask.view, paramTask.position, l);
    if ((paramTask != null) && (paramTask.mNestedRecyclerView != null) && (paramTask.isBound()) && (!paramTask.isInvalid())) {
      prefetchInnerRecyclerViewWithDeadline((RecyclerView)paramTask.mNestedRecyclerView.get(), paramLong);
    }
  }
  
  private void flushTasksWithDeadline(long paramLong)
  {
    for (int i = 0; i < this.mTasks.size(); i++)
    {
      Task localTask = (Task)this.mTasks.get(i);
      if (localTask.view == null) {
        break;
      }
      flushTaskWithDeadline(localTask, paramLong);
      localTask.clear();
    }
  }
  
  static boolean isPrefetchPositionAttached(RecyclerView paramRecyclerView, int paramInt)
  {
    int j = paramRecyclerView.mChildHelper.getUnfilteredChildCount();
    for (int i = 0; i < j; i++)
    {
      RecyclerView.ViewHolder localViewHolder = RecyclerView.getChildViewHolderInt(paramRecyclerView.mChildHelper.getUnfilteredChildAt(i));
      if ((localViewHolder.mPosition == paramInt) && (!localViewHolder.isInvalid())) {
        return true;
      }
    }
    return false;
  }
  
  private void prefetchInnerRecyclerViewWithDeadline(RecyclerView paramRecyclerView, long paramLong)
  {
    if (paramRecyclerView == null) {
      return;
    }
    if ((paramRecyclerView.mDataSetHasChangedAfterLayout) && (paramRecyclerView.mChildHelper.getUnfilteredChildCount() != 0)) {
      paramRecyclerView.removeAndRecycleViews();
    }
    LayoutPrefetchRegistryImpl localLayoutPrefetchRegistryImpl = paramRecyclerView.mPrefetchRegistry;
    localLayoutPrefetchRegistryImpl.collectPrefetchPositionsFromView(paramRecyclerView, true);
    if (localLayoutPrefetchRegistryImpl.mCount != 0) {
      try
      {
        TraceCompat.beginSection("RV Nested Prefetch");
        paramRecyclerView.mState.prepareForNestedPrefetch(paramRecyclerView.mAdapter);
        for (int i = 0; i < localLayoutPrefetchRegistryImpl.mCount * 2; i += 2) {
          prefetchPositionWithDeadline(paramRecyclerView, localLayoutPrefetchRegistryImpl.mPrefetchArray[i], paramLong);
        }
      }
      finally
      {
        TraceCompat.endSection();
      }
    }
  }
  
  private RecyclerView.ViewHolder prefetchPositionWithDeadline(RecyclerView paramRecyclerView, int paramInt, long paramLong)
  {
    if (isPrefetchPositionAttached(paramRecyclerView, paramInt)) {
      return null;
    }
    RecyclerView.Recycler localRecycler = paramRecyclerView.mRecycler;
    try
    {
      paramRecyclerView.onEnterLayoutOrScroll();
      RecyclerView.ViewHolder localViewHolder = localRecycler.tryGetViewHolderForPositionByDeadline(paramInt, false, paramLong);
      if (localViewHolder != null) {
        if ((localViewHolder.isBound()) && (!localViewHolder.isInvalid())) {
          localRecycler.recycleView(localViewHolder.itemView);
        } else {
          localRecycler.addViewHolderToRecycledViewPool(localViewHolder, false);
        }
      }
      return localViewHolder;
    }
    finally
    {
      paramRecyclerView.onExitLayoutOrScroll(false);
    }
  }
  
  public void add(RecyclerView paramRecyclerView)
  {
    this.mRecyclerViews.add(paramRecyclerView);
  }
  
  void postFromTraversal(RecyclerView paramRecyclerView, int paramInt1, int paramInt2)
  {
    if ((paramRecyclerView.isAttachedToWindow()) && (this.mPostTimeNs == 0L))
    {
      this.mPostTimeNs = paramRecyclerView.getNanoTime();
      paramRecyclerView.post(this);
    }
    paramRecyclerView.mPrefetchRegistry.setPrefetchVector(paramInt1, paramInt2);
  }
  
  void prefetch(long paramLong)
  {
    buildTaskList();
    flushTasksWithDeadline(paramLong);
  }
  
  public void remove(RecyclerView paramRecyclerView)
  {
    this.mRecyclerViews.remove(paramRecyclerView);
  }
  
  public void run()
  {
    try
    {
      TraceCompat.beginSection("RV Prefetch");
      boolean bool = this.mRecyclerViews.isEmpty();
      if (bool) {
        return;
      }
      int j = this.mRecyclerViews.size();
      long l1 = 0L;
      int i = 0;
      while (i < j)
      {
        RecyclerView localRecyclerView = (RecyclerView)this.mRecyclerViews.get(i);
        long l2 = l1;
        if (localRecyclerView.getWindowVisibility() == 0) {
          l2 = Math.max(localRecyclerView.getDrawingTime(), l1);
        }
        i++;
        l1 = l2;
      }
      if (l1 == 0L) {
        return;
      }
      prefetch(TimeUnit.MILLISECONDS.toNanos(l1) + this.mFrameIntervalNs);
      return;
    }
    finally
    {
      this.mPostTimeNs = 0L;
      TraceCompat.endSection();
    }
  }
  
  static class LayoutPrefetchRegistryImpl
    implements RecyclerView.LayoutManager.LayoutPrefetchRegistry
  {
    int mCount;
    int[] mPrefetchArray;
    int mPrefetchDx;
    int mPrefetchDy;
    
    public void addPosition(int paramInt1, int paramInt2)
    {
      if (paramInt1 >= 0)
      {
        if (paramInt2 >= 0)
        {
          int i = this.mCount * 2;
          int[] arrayOfInt1 = this.mPrefetchArray;
          if (arrayOfInt1 == null)
          {
            arrayOfInt1 = new int[4];
            this.mPrefetchArray = arrayOfInt1;
            Arrays.fill(arrayOfInt1, -1);
          }
          else if (i >= arrayOfInt1.length)
          {
            arrayOfInt1 = this.mPrefetchArray;
            int[] arrayOfInt2 = new int[i * 2];
            this.mPrefetchArray = arrayOfInt2;
            System.arraycopy(arrayOfInt1, 0, arrayOfInt2, 0, arrayOfInt1.length);
          }
          arrayOfInt1 = this.mPrefetchArray;
          arrayOfInt1[i] = paramInt1;
          arrayOfInt1[(i + 1)] = paramInt2;
          this.mCount += 1;
          return;
        }
        throw new IllegalArgumentException("Pixel distance must be non-negative");
      }
      throw new IllegalArgumentException("Layout positions must be non-negative");
    }
    
    void clearPrefetchPositions()
    {
      int[] arrayOfInt = this.mPrefetchArray;
      if (arrayOfInt != null) {
        Arrays.fill(arrayOfInt, -1);
      }
      this.mCount = 0;
    }
    
    void collectPrefetchPositionsFromView(RecyclerView paramRecyclerView, boolean paramBoolean)
    {
      this.mCount = 0;
      Object localObject = this.mPrefetchArray;
      if (localObject != null) {
        Arrays.fill((int[])localObject, -1);
      }
      localObject = paramRecyclerView.mLayout;
      if ((paramRecyclerView.mAdapter != null) && (localObject != null) && (((RecyclerView.LayoutManager)localObject).isItemPrefetchEnabled()))
      {
        if (paramBoolean)
        {
          if (!paramRecyclerView.mAdapterHelper.hasPendingUpdates()) {
            ((RecyclerView.LayoutManager)localObject).collectInitialPrefetchPositions(paramRecyclerView.mAdapter.getItemCount(), this);
          }
        }
        else if (!paramRecyclerView.hasPendingAdapterUpdates()) {
          ((RecyclerView.LayoutManager)localObject).collectAdjacentPrefetchPositions(this.mPrefetchDx, this.mPrefetchDy, paramRecyclerView.mState, this);
        }
        if (this.mCount > ((RecyclerView.LayoutManager)localObject).mPrefetchMaxCountObserved)
        {
          ((RecyclerView.LayoutManager)localObject).mPrefetchMaxCountObserved = this.mCount;
          ((RecyclerView.LayoutManager)localObject).mPrefetchMaxObservedInInitialPrefetch = paramBoolean;
          paramRecyclerView.mRecycler.updateViewCacheSize();
        }
      }
    }
    
    boolean lastPrefetchIncludedPosition(int paramInt)
    {
      if (this.mPrefetchArray != null)
      {
        int j = this.mCount;
        for (int i = 0; i < j * 2; i += 2) {
          if (this.mPrefetchArray[i] == paramInt) {
            return true;
          }
        }
      }
      return false;
    }
    
    void setPrefetchVector(int paramInt1, int paramInt2)
    {
      this.mPrefetchDx = paramInt1;
      this.mPrefetchDy = paramInt2;
    }
  }
  
  static class Task
  {
    public int distanceToItem;
    public boolean immediate;
    public int position;
    public RecyclerView view;
    public int viewVelocity;
    
    public void clear()
    {
      this.immediate = false;
      this.viewVelocity = 0;
      this.distanceToItem = 0;
      this.view = null;
      this.position = 0;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/recyclerview/widget/GapWorker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */