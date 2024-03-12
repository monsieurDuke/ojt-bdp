package androidx.recyclerview.widget;

import androidx.core.util.Pools.Pool;
import androidx.core.util.Pools.SimplePool;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

class AdapterHelper
  implements OpReorderer.Callback
{
  private static final boolean DEBUG = false;
  static final int POSITION_TYPE_INVISIBLE = 0;
  static final int POSITION_TYPE_NEW_OR_LAID_OUT = 1;
  private static final String TAG = "AHT";
  final Callback mCallback;
  final boolean mDisableRecycler;
  private int mExistingUpdateTypes = 0;
  Runnable mOnItemProcessedCallback;
  final OpReorderer mOpReorderer;
  final ArrayList<UpdateOp> mPendingUpdates = new ArrayList();
  final ArrayList<UpdateOp> mPostponedList = new ArrayList();
  private Pools.Pool<UpdateOp> mUpdateOpPool = new Pools.SimplePool(30);
  
  AdapterHelper(Callback paramCallback)
  {
    this(paramCallback, false);
  }
  
  AdapterHelper(Callback paramCallback, boolean paramBoolean)
  {
    this.mCallback = paramCallback;
    this.mDisableRecycler = paramBoolean;
    this.mOpReorderer = new OpReorderer(this);
  }
  
  private void applyAdd(UpdateOp paramUpdateOp)
  {
    postponeAndUpdateViewHolders(paramUpdateOp);
  }
  
  private void applyMove(UpdateOp paramUpdateOp)
  {
    postponeAndUpdateViewHolders(paramUpdateOp);
  }
  
  private void applyRemove(UpdateOp paramUpdateOp)
  {
    int i2 = paramUpdateOp.positionStart;
    int n = 0;
    int m = paramUpdateOp.positionStart + paramUpdateOp.itemCount;
    int i1 = -1;
    int k;
    for (int i = paramUpdateOp.positionStart; i < m; i = k)
    {
      int j = 0;
      k = 0;
      if ((this.mCallback.findViewHolder(i) == null) && (!canFindInPreLayout(i)))
      {
        if (i1 == 1)
        {
          postponeAndUpdateViewHolders(obtainUpdateOp(2, i2, n, null));
          k = 1;
        }
        j = 0;
      }
      else
      {
        if (i1 == 0)
        {
          dispatchAndUpdateViewHolders(obtainUpdateOp(2, i2, n, null));
          j = 1;
        }
        i1 = 1;
        k = j;
        j = i1;
      }
      if (k != 0)
      {
        k = i - n;
        m -= n;
        i = 1;
      }
      else
      {
        n++;
        k = i;
        i = n;
      }
      k++;
      n = i;
      i1 = j;
    }
    UpdateOp localUpdateOp = paramUpdateOp;
    if (n != paramUpdateOp.itemCount)
    {
      recycleUpdateOp(paramUpdateOp);
      localUpdateOp = obtainUpdateOp(2, i2, n, null);
    }
    if (i1 == 0) {
      dispatchAndUpdateViewHolders(localUpdateOp);
    } else {
      postponeAndUpdateViewHolders(localUpdateOp);
    }
  }
  
  private void applyUpdate(UpdateOp paramUpdateOp)
  {
    int k = paramUpdateOp.positionStart;
    int j = 0;
    int i4 = paramUpdateOp.positionStart;
    int i3 = paramUpdateOp.itemCount;
    int i2 = -1;
    int i = paramUpdateOp.positionStart;
    while (i < i4 + i3)
    {
      int n;
      int m;
      if ((this.mCallback.findViewHolder(i) == null) && (!canFindInPreLayout(i)))
      {
        n = k;
        m = j;
        if (i2 == 1)
        {
          postponeAndUpdateViewHolders(obtainUpdateOp(4, k, j, paramUpdateOp.payload));
          m = 0;
          n = i;
        }
        j = 0;
        k = n;
        n = m;
        m = j;
      }
      else
      {
        int i1 = k;
        n = j;
        if (i2 == 0)
        {
          dispatchAndUpdateViewHolders(obtainUpdateOp(4, k, j, paramUpdateOp.payload));
          n = 0;
          i1 = i;
        }
        m = 1;
        k = i1;
      }
      j = n + 1;
      i++;
      i2 = m;
    }
    Object localObject = paramUpdateOp;
    if (j != paramUpdateOp.itemCount)
    {
      localObject = paramUpdateOp.payload;
      recycleUpdateOp(paramUpdateOp);
      localObject = obtainUpdateOp(4, k, j, localObject);
    }
    if (i2 == 0) {
      dispatchAndUpdateViewHolders((UpdateOp)localObject);
    } else {
      postponeAndUpdateViewHolders((UpdateOp)localObject);
    }
  }
  
  private boolean canFindInPreLayout(int paramInt)
  {
    int k = this.mPostponedList.size();
    for (int i = 0; i < k; i++)
    {
      UpdateOp localUpdateOp = (UpdateOp)this.mPostponedList.get(i);
      if (localUpdateOp.cmd == 8)
      {
        if (findPositionOffset(localUpdateOp.itemCount, i + 1) == paramInt) {
          return true;
        }
      }
      else if (localUpdateOp.cmd == 1)
      {
        int n = localUpdateOp.positionStart;
        int m = localUpdateOp.itemCount;
        for (int j = localUpdateOp.positionStart; j < n + m; j++) {
          if (findPositionOffset(j, i + 1) == paramInt) {
            return true;
          }
        }
      }
    }
    return false;
  }
  
  private void dispatchAndUpdateViewHolders(UpdateOp paramUpdateOp)
  {
    if ((paramUpdateOp.cmd != 1) && (paramUpdateOp.cmd != 8))
    {
      int i1 = updatePositionWithPostponed(paramUpdateOp.positionStart, paramUpdateOp.cmd);
      int n = 1;
      int i = paramUpdateOp.positionStart;
      int k;
      switch (paramUpdateOp.cmd)
      {
      case 3: 
      default: 
        throw new IllegalArgumentException("op should be remove or update." + paramUpdateOp);
      case 4: 
        k = 1;
        break;
      case 2: 
        k = 0;
      }
      int m = 1;
      while (m < paramUpdateOp.itemCount)
      {
        int i2 = updatePositionWithPostponed(paramUpdateOp.positionStart + k * m, paramUpdateOp.cmd);
        int i3 = 0;
        int i5 = paramUpdateOp.cmd;
        int j = 0;
        int i4 = 0;
        switch (i5)
        {
        case 3: 
        default: 
          j = i3;
          break;
        case 4: 
          j = i4;
          if (i2 == i1 + 1) {
            j = 1;
          }
          break;
        case 2: 
          if (i2 == i1) {
            j = 1;
          }
          break;
        }
        if (j != 0)
        {
          j = n + 1;
        }
        else
        {
          localObject = obtainUpdateOp(paramUpdateOp.cmd, i1, n, paramUpdateOp.payload);
          dispatchFirstPassAndUpdateViewHolders((UpdateOp)localObject, i);
          recycleUpdateOp((UpdateOp)localObject);
          j = i;
          if (paramUpdateOp.cmd == 4) {
            j = i + n;
          }
          i1 = i2;
          n = 1;
          i = j;
          j = n;
        }
        m++;
        n = j;
      }
      Object localObject = paramUpdateOp.payload;
      recycleUpdateOp(paramUpdateOp);
      if (n > 0)
      {
        paramUpdateOp = obtainUpdateOp(paramUpdateOp.cmd, i1, n, localObject);
        dispatchFirstPassAndUpdateViewHolders(paramUpdateOp, i);
        recycleUpdateOp(paramUpdateOp);
      }
      return;
    }
    throw new IllegalArgumentException("should not dispatch add or move for pre layout");
  }
  
  private void postponeAndUpdateViewHolders(UpdateOp paramUpdateOp)
  {
    this.mPostponedList.add(paramUpdateOp);
    switch (paramUpdateOp.cmd)
    {
    default: 
      throw new IllegalArgumentException("Unknown update op type for " + paramUpdateOp);
    case 8: 
      this.mCallback.offsetPositionsForMove(paramUpdateOp.positionStart, paramUpdateOp.itemCount);
      break;
    case 4: 
      this.mCallback.markViewHoldersUpdated(paramUpdateOp.positionStart, paramUpdateOp.itemCount, paramUpdateOp.payload);
      break;
    case 2: 
      this.mCallback.offsetPositionsForRemovingLaidOutOrNewView(paramUpdateOp.positionStart, paramUpdateOp.itemCount);
      break;
    case 1: 
      this.mCallback.offsetPositionsForAdd(paramUpdateOp.positionStart, paramUpdateOp.itemCount);
    }
  }
  
  private int updatePositionWithPostponed(int paramInt1, int paramInt2)
  {
    int j = this.mPostponedList.size() - 1;
    UpdateOp localUpdateOp;
    for (int i = paramInt1; j >= 0; i = paramInt1)
    {
      localUpdateOp = (UpdateOp)this.mPostponedList.get(j);
      if (localUpdateOp.cmd == 8)
      {
        int k;
        if (localUpdateOp.positionStart < localUpdateOp.itemCount)
        {
          k = localUpdateOp.positionStart;
          paramInt1 = localUpdateOp.itemCount;
        }
        else
        {
          k = localUpdateOp.itemCount;
          paramInt1 = localUpdateOp.positionStart;
        }
        if ((i >= k) && (i <= paramInt1))
        {
          if (k == localUpdateOp.positionStart)
          {
            if (paramInt2 == 1) {
              localUpdateOp.itemCount += 1;
            } else if (paramInt2 == 2) {
              localUpdateOp.itemCount -= 1;
            }
            paramInt1 = i + 1;
          }
          else
          {
            if (paramInt2 == 1) {
              localUpdateOp.positionStart += 1;
            } else if (paramInt2 == 2) {
              localUpdateOp.positionStart -= 1;
            }
            paramInt1 = i - 1;
          }
        }
        else
        {
          paramInt1 = i;
          if (i < localUpdateOp.positionStart) {
            if (paramInt2 == 1)
            {
              localUpdateOp.positionStart += 1;
              localUpdateOp.itemCount += 1;
              paramInt1 = i;
            }
            else
            {
              paramInt1 = i;
              if (paramInt2 == 2)
              {
                localUpdateOp.positionStart -= 1;
                localUpdateOp.itemCount -= 1;
                paramInt1 = i;
              }
            }
          }
        }
      }
      else if (localUpdateOp.positionStart <= i)
      {
        if (localUpdateOp.cmd == 1)
        {
          paramInt1 = i - localUpdateOp.itemCount;
        }
        else
        {
          paramInt1 = i;
          if (localUpdateOp.cmd == 2) {
            paramInt1 = i + localUpdateOp.itemCount;
          }
        }
      }
      else if (paramInt2 == 1)
      {
        localUpdateOp.positionStart += 1;
        paramInt1 = i;
      }
      else
      {
        paramInt1 = i;
        if (paramInt2 == 2)
        {
          localUpdateOp.positionStart -= 1;
          paramInt1 = i;
        }
      }
      j--;
    }
    for (paramInt1 = this.mPostponedList.size() - 1; paramInt1 >= 0; paramInt1--)
    {
      localUpdateOp = (UpdateOp)this.mPostponedList.get(paramInt1);
      if (localUpdateOp.cmd == 8)
      {
        if ((localUpdateOp.itemCount == localUpdateOp.positionStart) || (localUpdateOp.itemCount < 0))
        {
          this.mPostponedList.remove(paramInt1);
          recycleUpdateOp(localUpdateOp);
        }
      }
      else if (localUpdateOp.itemCount <= 0)
      {
        this.mPostponedList.remove(paramInt1);
        recycleUpdateOp(localUpdateOp);
      }
    }
    return i;
  }
  
  AdapterHelper addUpdateOp(UpdateOp... paramVarArgs)
  {
    Collections.addAll(this.mPendingUpdates, paramVarArgs);
    return this;
  }
  
  public int applyPendingUpdatesToPosition(int paramInt)
  {
    int m = this.mPendingUpdates.size();
    int k = 0;
    for (int i = paramInt; k < m; i = paramInt)
    {
      UpdateOp localUpdateOp = (UpdateOp)this.mPendingUpdates.get(k);
      switch (localUpdateOp.cmd)
      {
      default: 
        paramInt = i;
        break;
      case 8: 
        if (localUpdateOp.positionStart == i)
        {
          paramInt = localUpdateOp.itemCount;
        }
        else
        {
          int j = i;
          if (localUpdateOp.positionStart < i) {
            j = i - 1;
          }
          paramInt = j;
          if (localUpdateOp.itemCount <= j) {
            paramInt = j + 1;
          }
        }
        break;
      case 2: 
        paramInt = i;
        if (localUpdateOp.positionStart <= i)
        {
          if (localUpdateOp.positionStart + localUpdateOp.itemCount > i) {
            return -1;
          }
          paramInt = i - localUpdateOp.itemCount;
        }
        break;
      case 1: 
        paramInt = i;
        if (localUpdateOp.positionStart <= i) {
          paramInt = i + localUpdateOp.itemCount;
        }
        break;
      }
      k++;
    }
    return i;
  }
  
  void consumePostponedUpdates()
  {
    int j = this.mPostponedList.size();
    for (int i = 0; i < j; i++) {
      this.mCallback.onDispatchSecondPass((UpdateOp)this.mPostponedList.get(i));
    }
    recycleUpdateOpsAndClearList(this.mPostponedList);
    this.mExistingUpdateTypes = 0;
  }
  
  void consumeUpdatesInOnePass()
  {
    consumePostponedUpdates();
    int j = this.mPendingUpdates.size();
    for (int i = 0; i < j; i++)
    {
      Object localObject = (UpdateOp)this.mPendingUpdates.get(i);
      switch (((UpdateOp)localObject).cmd)
      {
      default: 
        break;
      case 8: 
        this.mCallback.onDispatchSecondPass((UpdateOp)localObject);
        this.mCallback.offsetPositionsForMove(((UpdateOp)localObject).positionStart, ((UpdateOp)localObject).itemCount);
        break;
      case 4: 
        this.mCallback.onDispatchSecondPass((UpdateOp)localObject);
        this.mCallback.markViewHoldersUpdated(((UpdateOp)localObject).positionStart, ((UpdateOp)localObject).itemCount, ((UpdateOp)localObject).payload);
        break;
      case 2: 
        this.mCallback.onDispatchSecondPass((UpdateOp)localObject);
        this.mCallback.offsetPositionsForRemovingInvisible(((UpdateOp)localObject).positionStart, ((UpdateOp)localObject).itemCount);
        break;
      case 1: 
        this.mCallback.onDispatchSecondPass((UpdateOp)localObject);
        this.mCallback.offsetPositionsForAdd(((UpdateOp)localObject).positionStart, ((UpdateOp)localObject).itemCount);
      }
      localObject = this.mOnItemProcessedCallback;
      if (localObject != null) {
        ((Runnable)localObject).run();
      }
    }
    recycleUpdateOpsAndClearList(this.mPendingUpdates);
    this.mExistingUpdateTypes = 0;
  }
  
  void dispatchFirstPassAndUpdateViewHolders(UpdateOp paramUpdateOp, int paramInt)
  {
    this.mCallback.onDispatchFirstPass(paramUpdateOp);
    switch (paramUpdateOp.cmd)
    {
    case 3: 
    default: 
      throw new IllegalArgumentException("only remove and update ops can be dispatched in first pass");
    case 4: 
      this.mCallback.markViewHoldersUpdated(paramInt, paramUpdateOp.itemCount, paramUpdateOp.payload);
      break;
    case 2: 
      this.mCallback.offsetPositionsForRemovingInvisible(paramInt, paramUpdateOp.itemCount);
    }
  }
  
  int findPositionOffset(int paramInt)
  {
    return findPositionOffset(paramInt, 0);
  }
  
  int findPositionOffset(int paramInt1, int paramInt2)
  {
    int k = this.mPostponedList.size();
    int j = paramInt2;
    for (paramInt2 = paramInt1; j < k; paramInt2 = paramInt1)
    {
      UpdateOp localUpdateOp = (UpdateOp)this.mPostponedList.get(j);
      if (localUpdateOp.cmd == 8)
      {
        if (localUpdateOp.positionStart == paramInt2)
        {
          paramInt1 = localUpdateOp.itemCount;
        }
        else
        {
          int i = paramInt2;
          if (localUpdateOp.positionStart < paramInt2) {
            i = paramInt2 - 1;
          }
          paramInt1 = i;
          if (localUpdateOp.itemCount <= i) {
            paramInt1 = i + 1;
          }
        }
      }
      else
      {
        paramInt1 = paramInt2;
        if (localUpdateOp.positionStart <= paramInt2) {
          if (localUpdateOp.cmd == 2)
          {
            if (paramInt2 < localUpdateOp.positionStart + localUpdateOp.itemCount) {
              return -1;
            }
            paramInt1 = paramInt2 - localUpdateOp.itemCount;
          }
          else
          {
            paramInt1 = paramInt2;
            if (localUpdateOp.cmd == 1) {
              paramInt1 = paramInt2 + localUpdateOp.itemCount;
            }
          }
        }
      }
      j++;
    }
    return paramInt2;
  }
  
  boolean hasAnyUpdateTypes(int paramInt)
  {
    boolean bool;
    if ((this.mExistingUpdateTypes & paramInt) != 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  boolean hasPendingUpdates()
  {
    boolean bool;
    if (this.mPendingUpdates.size() > 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  boolean hasUpdates()
  {
    boolean bool;
    if ((!this.mPostponedList.isEmpty()) && (!this.mPendingUpdates.isEmpty())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public UpdateOp obtainUpdateOp(int paramInt1, int paramInt2, int paramInt3, Object paramObject)
  {
    UpdateOp localUpdateOp = (UpdateOp)this.mUpdateOpPool.acquire();
    if (localUpdateOp == null)
    {
      paramObject = new UpdateOp(paramInt1, paramInt2, paramInt3, paramObject);
    }
    else
    {
      localUpdateOp.cmd = paramInt1;
      localUpdateOp.positionStart = paramInt2;
      localUpdateOp.itemCount = paramInt3;
      localUpdateOp.payload = paramObject;
      paramObject = localUpdateOp;
    }
    return (UpdateOp)paramObject;
  }
  
  boolean onItemRangeChanged(int paramInt1, int paramInt2, Object paramObject)
  {
    boolean bool = false;
    if (paramInt2 < 1) {
      return false;
    }
    this.mPendingUpdates.add(obtainUpdateOp(4, paramInt1, paramInt2, paramObject));
    this.mExistingUpdateTypes |= 0x4;
    if (this.mPendingUpdates.size() == 1) {
      bool = true;
    }
    return bool;
  }
  
  boolean onItemRangeInserted(int paramInt1, int paramInt2)
  {
    boolean bool = false;
    if (paramInt2 < 1) {
      return false;
    }
    this.mPendingUpdates.add(obtainUpdateOp(1, paramInt1, paramInt2, null));
    this.mExistingUpdateTypes |= 0x1;
    if (this.mPendingUpdates.size() == 1) {
      bool = true;
    }
    return bool;
  }
  
  boolean onItemRangeMoved(int paramInt1, int paramInt2, int paramInt3)
  {
    boolean bool = false;
    if (paramInt1 == paramInt2) {
      return false;
    }
    if (paramInt3 == 1)
    {
      this.mPendingUpdates.add(obtainUpdateOp(8, paramInt1, paramInt2, null));
      this.mExistingUpdateTypes |= 0x8;
      if (this.mPendingUpdates.size() == 1) {
        bool = true;
      }
      return bool;
    }
    throw new IllegalArgumentException("Moving more than 1 item is not supported yet");
  }
  
  boolean onItemRangeRemoved(int paramInt1, int paramInt2)
  {
    boolean bool = false;
    if (paramInt2 < 1) {
      return false;
    }
    this.mPendingUpdates.add(obtainUpdateOp(2, paramInt1, paramInt2, null));
    this.mExistingUpdateTypes |= 0x2;
    if (this.mPendingUpdates.size() == 1) {
      bool = true;
    }
    return bool;
  }
  
  void preProcess()
  {
    this.mOpReorderer.reorderOps(this.mPendingUpdates);
    int j = this.mPendingUpdates.size();
    for (int i = 0; i < j; i++)
    {
      Object localObject = (UpdateOp)this.mPendingUpdates.get(i);
      switch (((UpdateOp)localObject).cmd)
      {
      default: 
        break;
      case 8: 
        applyMove((UpdateOp)localObject);
        break;
      case 4: 
        applyUpdate((UpdateOp)localObject);
        break;
      case 2: 
        applyRemove((UpdateOp)localObject);
        break;
      case 1: 
        applyAdd((UpdateOp)localObject);
      }
      localObject = this.mOnItemProcessedCallback;
      if (localObject != null) {
        ((Runnable)localObject).run();
      }
    }
    this.mPendingUpdates.clear();
  }
  
  public void recycleUpdateOp(UpdateOp paramUpdateOp)
  {
    if (!this.mDisableRecycler)
    {
      paramUpdateOp.payload = null;
      this.mUpdateOpPool.release(paramUpdateOp);
    }
  }
  
  void recycleUpdateOpsAndClearList(List<UpdateOp> paramList)
  {
    int j = paramList.size();
    for (int i = 0; i < j; i++) {
      recycleUpdateOp((UpdateOp)paramList.get(i));
    }
    paramList.clear();
  }
  
  void reset()
  {
    recycleUpdateOpsAndClearList(this.mPendingUpdates);
    recycleUpdateOpsAndClearList(this.mPostponedList);
    this.mExistingUpdateTypes = 0;
  }
  
  static abstract interface Callback
  {
    public abstract RecyclerView.ViewHolder findViewHolder(int paramInt);
    
    public abstract void markViewHoldersUpdated(int paramInt1, int paramInt2, Object paramObject);
    
    public abstract void offsetPositionsForAdd(int paramInt1, int paramInt2);
    
    public abstract void offsetPositionsForMove(int paramInt1, int paramInt2);
    
    public abstract void offsetPositionsForRemovingInvisible(int paramInt1, int paramInt2);
    
    public abstract void offsetPositionsForRemovingLaidOutOrNewView(int paramInt1, int paramInt2);
    
    public abstract void onDispatchFirstPass(AdapterHelper.UpdateOp paramUpdateOp);
    
    public abstract void onDispatchSecondPass(AdapterHelper.UpdateOp paramUpdateOp);
  }
  
  static class UpdateOp
  {
    static final int ADD = 1;
    static final int MOVE = 8;
    static final int POOL_SIZE = 30;
    static final int REMOVE = 2;
    static final int UPDATE = 4;
    int cmd;
    int itemCount;
    Object payload;
    int positionStart;
    
    UpdateOp(int paramInt1, int paramInt2, int paramInt3, Object paramObject)
    {
      this.cmd = paramInt1;
      this.positionStart = paramInt2;
      this.itemCount = paramInt3;
      this.payload = paramObject;
    }
    
    String cmdToString()
    {
      switch (this.cmd)
      {
      default: 
        return "??";
      case 8: 
        return "mv";
      case 4: 
        return "up";
      case 2: 
        return "rm";
      }
      return "add";
    }
    
    public boolean equals(Object paramObject)
    {
      if (this == paramObject) {
        return true;
      }
      if ((paramObject != null) && (getClass() == paramObject.getClass()))
      {
        paramObject = (UpdateOp)paramObject;
        int i = this.cmd;
        if (i != ((UpdateOp)paramObject).cmd) {
          return false;
        }
        if ((i == 8) && (Math.abs(this.itemCount - this.positionStart) == 1) && (this.itemCount == ((UpdateOp)paramObject).positionStart) && (this.positionStart == ((UpdateOp)paramObject).itemCount)) {
          return true;
        }
        if (this.itemCount != ((UpdateOp)paramObject).itemCount) {
          return false;
        }
        if (this.positionStart != ((UpdateOp)paramObject).positionStart) {
          return false;
        }
        Object localObject = this.payload;
        if (localObject != null)
        {
          if (!localObject.equals(((UpdateOp)paramObject).payload)) {
            return false;
          }
        }
        else if (((UpdateOp)paramObject).payload != null) {
          return false;
        }
        return true;
      }
      return false;
    }
    
    public int hashCode()
    {
      return (this.cmd * 31 + this.positionStart) * 31 + this.itemCount;
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      String str = Integer.toHexString(System.identityHashCode(this));
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      return str + "[" + cmdToString() + ",s:" + this.positionStart + "c:" + this.itemCount + ",p:" + this.payload + "]";
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/recyclerview/widget/AdapterHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */