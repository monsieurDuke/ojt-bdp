package androidx.recyclerview.widget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class DiffUtil
{
  private static final Comparator<Snake> SNAKE_COMPARATOR = new Comparator()
  {
    public int compare(DiffUtil.Snake paramAnonymousSnake1, DiffUtil.Snake paramAnonymousSnake2)
    {
      int i = paramAnonymousSnake1.x - paramAnonymousSnake2.x;
      if (i == 0) {
        i = paramAnonymousSnake1.y - paramAnonymousSnake2.y;
      }
      return i;
    }
  };
  
  public static DiffResult calculateDiff(Callback paramCallback)
  {
    return calculateDiff(paramCallback, true);
  }
  
  public static DiffResult calculateDiff(Callback paramCallback, boolean paramBoolean)
  {
    int i = paramCallback.getOldListSize();
    int j = paramCallback.getNewListSize();
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList3 = new ArrayList();
    localArrayList3.add(new Range(0, i, 0, j));
    i = i + j + Math.abs(i - j);
    int[] arrayOfInt2 = new int[i * 2];
    int[] arrayOfInt1 = new int[i * 2];
    ArrayList localArrayList2 = new ArrayList();
    while (!localArrayList3.isEmpty())
    {
      Range localRange2 = (Range)localArrayList3.remove(localArrayList3.size() - 1);
      Snake localSnake = diffPartial(paramCallback, localRange2.oldListStart, localRange2.oldListEnd, localRange2.newListStart, localRange2.newListEnd, arrayOfInt2, arrayOfInt1, i);
      if (localSnake != null)
      {
        if (localSnake.size > 0) {
          localArrayList1.add(localSnake);
        }
        localSnake.x += localRange2.oldListStart;
        localSnake.y += localRange2.newListStart;
        Range localRange1;
        if (localArrayList2.isEmpty()) {
          localRange1 = new Range();
        } else {
          localRange1 = (Range)localArrayList2.remove(localArrayList2.size() - 1);
        }
        localRange1.oldListStart = localRange2.oldListStart;
        localRange1.newListStart = localRange2.newListStart;
        if (localSnake.reverse)
        {
          localRange1.oldListEnd = localSnake.x;
          localRange1.newListEnd = localSnake.y;
        }
        else if (localSnake.removal)
        {
          localRange1.oldListEnd = (localSnake.x - 1);
          localRange1.newListEnd = localSnake.y;
        }
        else
        {
          localRange1.oldListEnd = localSnake.x;
          localRange1.newListEnd = (localSnake.y - 1);
        }
        localArrayList3.add(localRange1);
        if (localSnake.reverse)
        {
          if (localSnake.removal)
          {
            localRange2.oldListStart = (localSnake.x + localSnake.size + 1);
            localRange2.newListStart = (localSnake.y + localSnake.size);
          }
          else
          {
            localRange2.oldListStart = (localSnake.x + localSnake.size);
            localRange2.newListStart = (localSnake.y + localSnake.size + 1);
          }
        }
        else
        {
          localRange2.oldListStart = (localSnake.x + localSnake.size);
          localRange2.newListStart = (localSnake.y + localSnake.size);
        }
        localArrayList3.add(localRange2);
      }
      else
      {
        localArrayList2.add(localRange2);
      }
    }
    Collections.sort(localArrayList1, SNAKE_COMPARATOR);
    return new DiffResult(paramCallback, localArrayList1, arrayOfInt2, arrayOfInt1, paramBoolean);
  }
  
  private static Snake diffPartial(Callback paramCallback, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt5)
  {
    int k = paramInt2 - paramInt1;
    int n = paramInt4 - paramInt3;
    if ((paramInt2 - paramInt1 >= 1) && (paramInt4 - paramInt3 >= 1))
    {
      int i1 = k - n;
      int i2 = (k + n + 1) / 2;
      Arrays.fill(paramArrayOfInt1, paramInt5 - i2 - 1, paramInt5 + i2 + 1, 0);
      Arrays.fill(paramArrayOfInt2, paramInt5 - i2 - 1 + i1, paramInt5 + i2 + 1 + i1, k);
      int i;
      if (i1 % 2 != 0) {
        i = 1;
      } else {
        i = 0;
      }
      int j = 0;
      for (paramInt4 = k; j <= i2; paramInt4 = paramInt2)
      {
        boolean bool;
        int m;
        for (k = -j; k <= j; k += 2)
        {
          if ((k != -j) && ((k == j) || (paramArrayOfInt1[(paramInt5 + k - 1)] >= paramArrayOfInt1[(paramInt5 + k + 1)])))
          {
            paramInt2 = paramArrayOfInt1[(paramInt5 + k - 1)] + 1;
            bool = true;
          }
          else
          {
            paramInt2 = paramArrayOfInt1[(paramInt5 + k + 1)];
            bool = false;
          }
          for (m = paramInt2 - k; (paramInt2 < paramInt4) && (m < n) && (paramCallback.areItemsTheSame(paramInt1 + paramInt2, paramInt3 + m)); m++) {
            paramInt2++;
          }
          paramArrayOfInt1[(paramInt5 + k)] = paramInt2;
          if ((i != 0) && (k >= i1 - j + 1) && (k <= i1 + j - 1)) {
            if (paramArrayOfInt1[(paramInt5 + k)] >= paramArrayOfInt2[(paramInt5 + k)])
            {
              paramCallback = new Snake();
              paramCallback.x = paramArrayOfInt2[(paramInt5 + k)];
              paramCallback.y = (paramCallback.x - k);
              paramCallback.size = (paramArrayOfInt1[(paramInt5 + k)] - paramArrayOfInt2[(paramInt5 + k)]);
              paramCallback.removal = bool;
              paramCallback.reverse = false;
              return paramCallback;
            }
          }
        }
        k = -j;
        paramInt2 = paramInt4;
        while (k <= j)
        {
          int i3 = k + i1;
          if ((i3 != j + i1) && ((i3 == -j + i1) || (paramArrayOfInt2[(paramInt5 + i3 - 1)] >= paramArrayOfInt2[(paramInt5 + i3 + 1)])))
          {
            paramInt4 = paramArrayOfInt2[(paramInt5 + i3 + 1)] - 1;
            bool = true;
          }
          else
          {
            paramInt4 = paramArrayOfInt2[(paramInt5 + i3 - 1)];
            bool = false;
          }
          for (m = paramInt4 - i3; (paramInt4 > 0) && (m > 0) && (paramCallback.areItemsTheSame(paramInt1 + paramInt4 - 1, paramInt3 + m - 1)); m--) {
            paramInt4--;
          }
          paramArrayOfInt2[(paramInt5 + i3)] = paramInt4;
          if ((i == 0) && (k + i1 >= -j) && (k + i1 <= j) && (paramArrayOfInt1[(paramInt5 + i3)] >= paramArrayOfInt2[(paramInt5 + i3)]))
          {
            paramCallback = new Snake();
            paramCallback.x = paramArrayOfInt2[(paramInt5 + i3)];
            paramCallback.y = (paramCallback.x - i3);
            paramCallback.size = (paramArrayOfInt1[(paramInt5 + i3)] - paramArrayOfInt2[(paramInt5 + i3)]);
            paramCallback.removal = bool;
            paramCallback.reverse = true;
            return paramCallback;
          }
          k += 2;
        }
        j++;
      }
      throw new IllegalStateException("DiffUtil hit an unexpected case while trying to calculate the optimal path. Please make sure your data is not changing during the diff calculation.");
    }
    return null;
  }
  
  public static abstract class Callback
  {
    public abstract boolean areContentsTheSame(int paramInt1, int paramInt2);
    
    public abstract boolean areItemsTheSame(int paramInt1, int paramInt2);
    
    public Object getChangePayload(int paramInt1, int paramInt2)
    {
      return null;
    }
    
    public abstract int getNewListSize();
    
    public abstract int getOldListSize();
  }
  
  public static class DiffResult
  {
    private static final int FLAG_CHANGED = 2;
    private static final int FLAG_IGNORE = 16;
    private static final int FLAG_MASK = 31;
    private static final int FLAG_MOVED_CHANGED = 4;
    private static final int FLAG_MOVED_NOT_CHANGED = 8;
    private static final int FLAG_NOT_CHANGED = 1;
    private static final int FLAG_OFFSET = 5;
    public static final int NO_POSITION = -1;
    private final DiffUtil.Callback mCallback;
    private final boolean mDetectMoves;
    private final int[] mNewItemStatuses;
    private final int mNewListSize;
    private final int[] mOldItemStatuses;
    private final int mOldListSize;
    private final List<DiffUtil.Snake> mSnakes;
    
    DiffResult(DiffUtil.Callback paramCallback, List<DiffUtil.Snake> paramList, int[] paramArrayOfInt1, int[] paramArrayOfInt2, boolean paramBoolean)
    {
      this.mSnakes = paramList;
      this.mOldItemStatuses = paramArrayOfInt1;
      this.mNewItemStatuses = paramArrayOfInt2;
      Arrays.fill(paramArrayOfInt1, 0);
      Arrays.fill(paramArrayOfInt2, 0);
      this.mCallback = paramCallback;
      this.mOldListSize = paramCallback.getOldListSize();
      this.mNewListSize = paramCallback.getNewListSize();
      this.mDetectMoves = paramBoolean;
      addRootSnake();
      findMatchingItems();
    }
    
    private void addRootSnake()
    {
      DiffUtil.Snake localSnake;
      if (this.mSnakes.isEmpty()) {
        localSnake = null;
      } else {
        localSnake = (DiffUtil.Snake)this.mSnakes.get(0);
      }
      if ((localSnake == null) || (localSnake.x != 0) || (localSnake.y != 0))
      {
        localSnake = new DiffUtil.Snake();
        localSnake.x = 0;
        localSnake.y = 0;
        localSnake.removal = false;
        localSnake.size = 0;
        localSnake.reverse = false;
        this.mSnakes.add(0, localSnake);
      }
    }
    
    private void dispatchAdditions(List<DiffUtil.PostponedUpdate> paramList, ListUpdateCallback paramListUpdateCallback, int paramInt1, int paramInt2, int paramInt3)
    {
      if (!this.mDetectMoves)
      {
        paramListUpdateCallback.onInserted(paramInt1, paramInt2);
        return;
      }
      paramInt2--;
      while (paramInt2 >= 0)
      {
        Object localObject = this.mNewItemStatuses;
        int i = localObject[(paramInt3 + paramInt2)] & 0x1F;
        switch (i)
        {
        default: 
          paramList = new StringBuilder().append("unknown flag for pos ").append(paramInt3 + paramInt2).append(" ");
          paramListUpdateCallback = Long.toBinaryString(i);
          Log5ECF72.a(paramListUpdateCallback);
          LogE84000.a(paramListUpdateCallback);
          Log229316.a(paramListUpdateCallback);
          throw new IllegalStateException(paramListUpdateCallback);
        case 16: 
          paramList.add(new DiffUtil.PostponedUpdate(paramInt3 + paramInt2, paramInt1, false));
          break;
        case 4: 
        case 8: 
          int j = localObject[(paramInt3 + paramInt2)] >> 5;
          paramListUpdateCallback.onMoved(removePostponedUpdate(paramList, j, true).currentPos, paramInt1);
          if (i == 4) {
            paramListUpdateCallback.onChanged(paramInt1, 1, this.mCallback.getChangePayload(j, paramInt3 + paramInt2));
          }
          break;
        case 0: 
          paramListUpdateCallback.onInserted(paramInt1, 1);
          Iterator localIterator = paramList.iterator();
          while (localIterator.hasNext())
          {
            localObject = (DiffUtil.PostponedUpdate)localIterator.next();
            ((DiffUtil.PostponedUpdate)localObject).currentPos += 1;
          }
        }
        paramInt2--;
      }
    }
    
    private void dispatchRemovals(List<DiffUtil.PostponedUpdate> paramList, ListUpdateCallback paramListUpdateCallback, int paramInt1, int paramInt2, int paramInt3)
    {
      if (!this.mDetectMoves)
      {
        paramListUpdateCallback.onRemoved(paramInt1, paramInt2);
        return;
      }
      paramInt2--;
      while (paramInt2 >= 0)
      {
        Object localObject = this.mOldItemStatuses;
        int i = localObject[(paramInt3 + paramInt2)] & 0x1F;
        switch (i)
        {
        default: 
          paramList = new StringBuilder().append("unknown flag for pos ").append(paramInt3 + paramInt2).append(" ");
          paramListUpdateCallback = Long.toBinaryString(i);
          Log5ECF72.a(paramListUpdateCallback);
          LogE84000.a(paramListUpdateCallback);
          Log229316.a(paramListUpdateCallback);
          throw new IllegalStateException(paramListUpdateCallback);
        case 16: 
          paramList.add(new DiffUtil.PostponedUpdate(paramInt3 + paramInt2, paramInt1 + paramInt2, true));
          break;
        case 4: 
        case 8: 
          int j = localObject[(paramInt3 + paramInt2)] >> 5;
          localObject = removePostponedUpdate(paramList, j, false);
          paramListUpdateCallback.onMoved(paramInt1 + paramInt2, ((DiffUtil.PostponedUpdate)localObject).currentPos - 1);
          if (i == 4) {
            paramListUpdateCallback.onChanged(((DiffUtil.PostponedUpdate)localObject).currentPos - 1, 1, this.mCallback.getChangePayload(paramInt3 + paramInt2, j));
          }
          break;
        case 0: 
          paramListUpdateCallback.onRemoved(paramInt1 + paramInt2, 1);
          localObject = paramList.iterator();
          while (((Iterator)localObject).hasNext())
          {
            DiffUtil.PostponedUpdate localPostponedUpdate = (DiffUtil.PostponedUpdate)((Iterator)localObject).next();
            localPostponedUpdate.currentPos -= 1;
          }
        }
        paramInt2--;
      }
    }
    
    private void findAddition(int paramInt1, int paramInt2, int paramInt3)
    {
      if (this.mOldItemStatuses[(paramInt1 - 1)] != 0) {
        return;
      }
      findMatchingItem(paramInt1, paramInt2, paramInt3, false);
    }
    
    private boolean findMatchingItem(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
    {
      int j;
      int i;
      if (paramBoolean)
      {
        j = paramInt2 - 1;
        i = paramInt1;
        k = paramInt2 - 1;
        paramInt2 = i;
        i = k;
      }
      else
      {
        j = paramInt1 - 1;
        k = paramInt1 - 1;
        i = paramInt2;
        paramInt2 = k;
      }
      int k = paramInt2;
      while (paramInt3 >= 0)
      {
        DiffUtil.Snake localSnake = (DiffUtil.Snake)this.mSnakes.get(paramInt3);
        int i2 = localSnake.x;
        int i1 = localSnake.size;
        int m = localSnake.y;
        int n = localSnake.size;
        paramInt2 = 8;
        if (paramBoolean)
        {
          for (i = k - 1; i >= i2 + i1; i--) {
            if (this.mCallback.areItemsTheSame(i, j))
            {
              if (!this.mCallback.areContentsTheSame(i, j)) {
                paramInt2 = 4;
              }
              this.mNewItemStatuses[j] = (i << 5 | 0x10);
              this.mOldItemStatuses[i] = (j << 5 | paramInt2);
              return true;
            }
          }
        }
        else
        {
          i--;
          while (i >= m + n)
          {
            if (this.mCallback.areItemsTheSame(j, i))
            {
              if (!this.mCallback.areContentsTheSame(j, i)) {
                paramInt2 = 4;
              }
              this.mOldItemStatuses[(paramInt1 - 1)] = (i << 5 | 0x10);
              this.mNewItemStatuses[i] = (paramInt1 - 1 << 5 | paramInt2);
              return true;
            }
            i--;
          }
        }
        k = localSnake.x;
        i = localSnake.y;
        paramInt3--;
      }
      return false;
    }
    
    private void findMatchingItems()
    {
      int j = this.mOldListSize;
      int i = this.mNewListSize;
      for (int k = this.mSnakes.size() - 1; k >= 0; k--)
      {
        DiffUtil.Snake localSnake = (DiffUtil.Snake)this.mSnakes.get(k);
        int i3 = localSnake.x;
        int i2 = localSnake.size;
        int i1 = localSnake.y;
        int n = localSnake.size;
        int m;
        if (this.mDetectMoves)
        {
          for (;;)
          {
            m = i;
            if (j <= i3 + i2) {
              break;
            }
            findAddition(j, i, k);
            j--;
          }
          while (m > i1 + n)
          {
            findRemoval(j, m, k);
            m--;
          }
        }
        for (i = 0; i < localSnake.size; i++)
        {
          n = localSnake.x + i;
          m = localSnake.y + i;
          if (this.mCallback.areContentsTheSame(n, m)) {
            j = 1;
          } else {
            j = 2;
          }
          this.mOldItemStatuses[n] = (m << 5 | j);
          this.mNewItemStatuses[m] = (n << 5 | j);
        }
        j = localSnake.x;
        i = localSnake.y;
      }
    }
    
    private void findRemoval(int paramInt1, int paramInt2, int paramInt3)
    {
      if (this.mNewItemStatuses[(paramInt2 - 1)] != 0) {
        return;
      }
      findMatchingItem(paramInt1, paramInt2, paramInt3, true);
    }
    
    private static DiffUtil.PostponedUpdate removePostponedUpdate(List<DiffUtil.PostponedUpdate> paramList, int paramInt, boolean paramBoolean)
    {
      for (int i = paramList.size() - 1; i >= 0; i--)
      {
        DiffUtil.PostponedUpdate localPostponedUpdate2 = (DiffUtil.PostponedUpdate)paramList.get(i);
        if ((localPostponedUpdate2.posInOwnerList == paramInt) && (localPostponedUpdate2.removal == paramBoolean))
        {
          paramList.remove(i);
          for (paramInt = i; paramInt < paramList.size(); paramInt++)
          {
            DiffUtil.PostponedUpdate localPostponedUpdate1 = (DiffUtil.PostponedUpdate)paramList.get(paramInt);
            int j = localPostponedUpdate1.currentPos;
            if (paramBoolean) {
              i = 1;
            } else {
              i = -1;
            }
            localPostponedUpdate1.currentPos = (j + i);
          }
          return localPostponedUpdate2;
        }
      }
      return null;
    }
    
    public int convertNewPositionToOld(int paramInt)
    {
      if ((paramInt >= 0) && (paramInt < this.mNewListSize))
      {
        paramInt = this.mNewItemStatuses[paramInt];
        if ((paramInt & 0x1F) == 0) {
          return -1;
        }
        return paramInt >> 5;
      }
      throw new IndexOutOfBoundsException("Index out of bounds - passed position = " + paramInt + ", new list size = " + this.mNewListSize);
    }
    
    public int convertOldPositionToNew(int paramInt)
    {
      if ((paramInt >= 0) && (paramInt < this.mOldListSize))
      {
        paramInt = this.mOldItemStatuses[paramInt];
        if ((paramInt & 0x1F) == 0) {
          return -1;
        }
        return paramInt >> 5;
      }
      throw new IndexOutOfBoundsException("Index out of bounds - passed position = " + paramInt + ", old list size = " + this.mOldListSize);
    }
    
    public void dispatchUpdatesTo(ListUpdateCallback paramListUpdateCallback)
    {
      if ((paramListUpdateCallback instanceof BatchingListUpdateCallback)) {
        paramListUpdateCallback = (BatchingListUpdateCallback)paramListUpdateCallback;
      } else {
        paramListUpdateCallback = new BatchingListUpdateCallback(paramListUpdateCallback);
      }
      ArrayList localArrayList = new ArrayList();
      int k = this.mOldListSize;
      int j = this.mNewListSize;
      int i = this.mSnakes.size();
      i--;
      while (i >= 0)
      {
        DiffUtil.Snake localSnake = (DiffUtil.Snake)this.mSnakes.get(i);
        int m = localSnake.size;
        int i1 = localSnake.x + m;
        int n = localSnake.y + m;
        if (i1 < k) {
          dispatchRemovals(localArrayList, paramListUpdateCallback, i1, k - i1, i1);
        }
        if (n < j) {
          dispatchAdditions(localArrayList, paramListUpdateCallback, i1, j - n, n);
        }
        for (j = m - 1; j >= 0; j--) {
          if ((this.mOldItemStatuses[(localSnake.x + j)] & 0x1F) == 2) {
            paramListUpdateCallback.onChanged(localSnake.x + j, 1, this.mCallback.getChangePayload(localSnake.x + j, localSnake.y + j));
          }
        }
        k = localSnake.x;
        j = localSnake.y;
        i--;
      }
      paramListUpdateCallback.dispatchLastEvent();
    }
    
    public void dispatchUpdatesTo(RecyclerView.Adapter paramAdapter)
    {
      dispatchUpdatesTo(new AdapterListUpdateCallback(paramAdapter));
    }
    
    List<DiffUtil.Snake> getSnakes()
    {
      return this.mSnakes;
    }
  }
  
  public static abstract class ItemCallback<T>
  {
    public abstract boolean areContentsTheSame(T paramT1, T paramT2);
    
    public abstract boolean areItemsTheSame(T paramT1, T paramT2);
    
    public Object getChangePayload(T paramT1, T paramT2)
    {
      return null;
    }
  }
  
  private static class PostponedUpdate
  {
    int currentPos;
    int posInOwnerList;
    boolean removal;
    
    public PostponedUpdate(int paramInt1, int paramInt2, boolean paramBoolean)
    {
      this.posInOwnerList = paramInt1;
      this.currentPos = paramInt2;
      this.removal = paramBoolean;
    }
  }
  
  static class Range
  {
    int newListEnd;
    int newListStart;
    int oldListEnd;
    int oldListStart;
    
    public Range() {}
    
    public Range(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      this.oldListStart = paramInt1;
      this.oldListEnd = paramInt2;
      this.newListStart = paramInt3;
      this.newListEnd = paramInt4;
    }
  }
  
  static class Snake
  {
    boolean removal;
    boolean reverse;
    int size;
    int x;
    int y;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/recyclerview/widget/DiffUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */