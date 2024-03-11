package androidx.recyclerview.widget;

import android.os.Handler;
import android.os.Looper;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;

public class AsyncListDiffer<T>
{
  private static final Executor sMainThreadExecutor = new MainThreadExecutor();
  final AsyncDifferConfig<T> mConfig;
  private List<T> mList;
  private final List<ListListener<T>> mListeners = new CopyOnWriteArrayList();
  Executor mMainThreadExecutor;
  int mMaxScheduledGeneration;
  private List<T> mReadOnlyList = Collections.emptyList();
  private final ListUpdateCallback mUpdateCallback;
  
  public AsyncListDiffer(ListUpdateCallback paramListUpdateCallback, AsyncDifferConfig<T> paramAsyncDifferConfig)
  {
    this.mUpdateCallback = paramListUpdateCallback;
    this.mConfig = paramAsyncDifferConfig;
    if (paramAsyncDifferConfig.getMainThreadExecutor() != null) {
      this.mMainThreadExecutor = paramAsyncDifferConfig.getMainThreadExecutor();
    } else {
      this.mMainThreadExecutor = sMainThreadExecutor;
    }
  }
  
  public AsyncListDiffer(RecyclerView.Adapter paramAdapter, DiffUtil.ItemCallback<T> paramItemCallback)
  {
    this(new AdapterListUpdateCallback(paramAdapter), new AsyncDifferConfig.Builder(paramItemCallback).build());
  }
  
  private void onCurrentListChanged(List<T> paramList, Runnable paramRunnable)
  {
    Iterator localIterator = this.mListeners.iterator();
    while (localIterator.hasNext()) {
      ((ListListener)localIterator.next()).onCurrentListChanged(paramList, this.mReadOnlyList);
    }
    if (paramRunnable != null) {
      paramRunnable.run();
    }
  }
  
  public void addListListener(ListListener<T> paramListListener)
  {
    this.mListeners.add(paramListListener);
  }
  
  public List<T> getCurrentList()
  {
    return this.mReadOnlyList;
  }
  
  void latchList(List<T> paramList, DiffUtil.DiffResult paramDiffResult, Runnable paramRunnable)
  {
    List localList = this.mReadOnlyList;
    this.mList = paramList;
    this.mReadOnlyList = Collections.unmodifiableList(paramList);
    paramDiffResult.dispatchUpdatesTo(this.mUpdateCallback);
    onCurrentListChanged(localList, paramRunnable);
  }
  
  public void removeListListener(ListListener<T> paramListListener)
  {
    this.mListeners.remove(paramListListener);
  }
  
  public void submitList(List<T> paramList)
  {
    submitList(paramList, null);
  }
  
  public void submitList(final List<T> paramList, final Runnable paramRunnable)
  {
    final int i = this.mMaxScheduledGeneration + 1;
    this.mMaxScheduledGeneration = i;
    final List localList1 = this.mList;
    if (paramList == localList1)
    {
      if (paramRunnable != null) {
        paramRunnable.run();
      }
      return;
    }
    List localList2 = this.mReadOnlyList;
    if (paramList == null)
    {
      i = localList1.size();
      this.mList = null;
      this.mReadOnlyList = Collections.emptyList();
      this.mUpdateCallback.onRemoved(0, i);
      onCurrentListChanged(localList2, paramRunnable);
      return;
    }
    if (localList1 == null)
    {
      this.mList = paramList;
      this.mReadOnlyList = Collections.unmodifiableList(paramList);
      this.mUpdateCallback.onInserted(0, paramList.size());
      onCurrentListChanged(localList2, paramRunnable);
      return;
    }
    localList1 = this.mList;
    this.mConfig.getBackgroundThreadExecutor().execute(new Runnable()
    {
      public void run()
      {
        final DiffUtil.DiffResult localDiffResult = DiffUtil.calculateDiff(new DiffUtil.Callback()
        {
          public boolean areContentsTheSame(int paramAnonymous2Int1, int paramAnonymous2Int2)
          {
            Object localObject1 = AsyncListDiffer.1.this.val$oldList.get(paramAnonymous2Int1);
            Object localObject2 = AsyncListDiffer.1.this.val$newList.get(paramAnonymous2Int2);
            if ((localObject1 != null) && (localObject2 != null)) {
              return AsyncListDiffer.this.mConfig.getDiffCallback().areContentsTheSame(localObject1, localObject2);
            }
            if ((localObject1 == null) && (localObject2 == null)) {
              return true;
            }
            throw new AssertionError();
          }
          
          public boolean areItemsTheSame(int paramAnonymous2Int1, int paramAnonymous2Int2)
          {
            Object localObject2 = AsyncListDiffer.1.this.val$oldList.get(paramAnonymous2Int1);
            Object localObject1 = AsyncListDiffer.1.this.val$newList.get(paramAnonymous2Int2);
            if ((localObject2 != null) && (localObject1 != null)) {
              return AsyncListDiffer.this.mConfig.getDiffCallback().areItemsTheSame(localObject2, localObject1);
            }
            boolean bool;
            if ((localObject2 == null) && (localObject1 == null)) {
              bool = true;
            } else {
              bool = false;
            }
            return bool;
          }
          
          public Object getChangePayload(int paramAnonymous2Int1, int paramAnonymous2Int2)
          {
            Object localObject2 = AsyncListDiffer.1.this.val$oldList.get(paramAnonymous2Int1);
            Object localObject1 = AsyncListDiffer.1.this.val$newList.get(paramAnonymous2Int2);
            if ((localObject2 != null) && (localObject1 != null)) {
              return AsyncListDiffer.this.mConfig.getDiffCallback().getChangePayload(localObject2, localObject1);
            }
            throw new AssertionError();
          }
          
          public int getNewListSize()
          {
            return AsyncListDiffer.1.this.val$newList.size();
          }
          
          public int getOldListSize()
          {
            return AsyncListDiffer.1.this.val$oldList.size();
          }
        });
        AsyncListDiffer.this.mMainThreadExecutor.execute(new Runnable()
        {
          public void run()
          {
            if (AsyncListDiffer.this.mMaxScheduledGeneration == AsyncListDiffer.1.this.val$runGeneration) {
              AsyncListDiffer.this.latchList(AsyncListDiffer.1.this.val$newList, localDiffResult, AsyncListDiffer.1.this.val$commitCallback);
            }
          }
        });
      }
    });
  }
  
  public static abstract interface ListListener<T>
  {
    public abstract void onCurrentListChanged(List<T> paramList1, List<T> paramList2);
  }
  
  private static class MainThreadExecutor
    implements Executor
  {
    final Handler mHandler = new Handler(Looper.getMainLooper());
    
    public void execute(Runnable paramRunnable)
    {
      this.mHandler.post(paramRunnable);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/recyclerview/widget/AsyncListDiffer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */