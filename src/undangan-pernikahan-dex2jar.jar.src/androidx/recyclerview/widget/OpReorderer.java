package androidx.recyclerview.widget;

import java.util.List;

class OpReorderer
{
  final Callback mCallback;
  
  OpReorderer(Callback paramCallback)
  {
    this.mCallback = paramCallback;
  }
  
  private int getLastMoveOutOfOrder(List<AdapterHelper.UpdateOp> paramList)
  {
    int j = 0;
    int i = paramList.size() - 1;
    while (i >= 0)
    {
      int k;
      if (((AdapterHelper.UpdateOp)paramList.get(i)).cmd == 8)
      {
        k = j;
        if (j != 0) {
          return i;
        }
      }
      else
      {
        k = 1;
      }
      i--;
      j = k;
    }
    return -1;
  }
  
  private void swapMoveAdd(List<AdapterHelper.UpdateOp> paramList, int paramInt1, AdapterHelper.UpdateOp paramUpdateOp1, int paramInt2, AdapterHelper.UpdateOp paramUpdateOp2)
  {
    int i = 0;
    if (paramUpdateOp1.itemCount < paramUpdateOp2.positionStart) {
      i = 0 - 1;
    }
    int j = i;
    if (paramUpdateOp1.positionStart < paramUpdateOp2.positionStart) {
      j = i + 1;
    }
    if (paramUpdateOp2.positionStart <= paramUpdateOp1.positionStart) {
      paramUpdateOp1.positionStart += paramUpdateOp2.itemCount;
    }
    if (paramUpdateOp2.positionStart <= paramUpdateOp1.itemCount) {
      paramUpdateOp1.itemCount += paramUpdateOp2.itemCount;
    }
    paramUpdateOp2.positionStart += j;
    paramList.set(paramInt1, paramUpdateOp2);
    paramList.set(paramInt2, paramUpdateOp1);
  }
  
  private void swapMoveOp(List<AdapterHelper.UpdateOp> paramList, int paramInt1, int paramInt2)
  {
    AdapterHelper.UpdateOp localUpdateOp2 = (AdapterHelper.UpdateOp)paramList.get(paramInt1);
    AdapterHelper.UpdateOp localUpdateOp1 = (AdapterHelper.UpdateOp)paramList.get(paramInt2);
    switch (localUpdateOp1.cmd)
    {
    case 3: 
    default: 
      break;
    case 4: 
      swapMoveUpdate(paramList, paramInt1, localUpdateOp2, paramInt2, localUpdateOp1);
      break;
    case 2: 
      swapMoveRemove(paramList, paramInt1, localUpdateOp2, paramInt2, localUpdateOp1);
      break;
    case 1: 
      swapMoveAdd(paramList, paramInt1, localUpdateOp2, paramInt2, localUpdateOp1);
    }
  }
  
  void reorderOps(List<AdapterHelper.UpdateOp> paramList)
  {
    for (;;)
    {
      int i = getLastMoveOutOfOrder(paramList);
      if (i == -1) {
        break;
      }
      swapMoveOp(paramList, i, i + 1);
    }
  }
  
  void swapMoveRemove(List<AdapterHelper.UpdateOp> paramList, int paramInt1, AdapterHelper.UpdateOp paramUpdateOp1, int paramInt2, AdapterHelper.UpdateOp paramUpdateOp2)
  {
    AdapterHelper.UpdateOp localUpdateOp = null;
    int k = 0;
    int m;
    int i;
    int j;
    if (paramUpdateOp1.positionStart < paramUpdateOp1.itemCount)
    {
      m = 0;
      i = k;
      j = m;
      if (paramUpdateOp2.positionStart == paramUpdateOp1.positionStart)
      {
        i = k;
        j = m;
        if (paramUpdateOp2.itemCount == paramUpdateOp1.itemCount - paramUpdateOp1.positionStart)
        {
          i = 1;
          j = m;
        }
      }
    }
    else
    {
      m = 1;
      i = k;
      j = m;
      if (paramUpdateOp2.positionStart == paramUpdateOp1.itemCount + 1)
      {
        i = k;
        j = m;
        if (paramUpdateOp2.itemCount == paramUpdateOp1.positionStart - paramUpdateOp1.itemCount)
        {
          i = 1;
          j = m;
        }
      }
    }
    if (paramUpdateOp1.itemCount < paramUpdateOp2.positionStart)
    {
      paramUpdateOp2.positionStart -= 1;
    }
    else if (paramUpdateOp1.itemCount < paramUpdateOp2.positionStart + paramUpdateOp2.itemCount)
    {
      paramUpdateOp2.itemCount -= 1;
      paramUpdateOp1.cmd = 2;
      paramUpdateOp1.itemCount = 1;
      if (paramUpdateOp2.itemCount == 0)
      {
        paramList.remove(paramInt2);
        this.mCallback.recycleUpdateOp(paramUpdateOp2);
      }
      return;
    }
    if (paramUpdateOp1.positionStart <= paramUpdateOp2.positionStart)
    {
      paramUpdateOp2.positionStart += 1;
    }
    else if (paramUpdateOp1.positionStart < paramUpdateOp2.positionStart + paramUpdateOp2.itemCount)
    {
      int n = paramUpdateOp2.positionStart;
      m = paramUpdateOp2.itemCount;
      k = paramUpdateOp1.positionStart;
      localUpdateOp = this.mCallback.obtainUpdateOp(2, paramUpdateOp1.positionStart + 1, n + m - k, null);
      paramUpdateOp2.itemCount = (paramUpdateOp1.positionStart - paramUpdateOp2.positionStart);
    }
    if (i != 0)
    {
      paramList.set(paramInt1, paramUpdateOp2);
      paramList.remove(paramInt2);
      this.mCallback.recycleUpdateOp(paramUpdateOp1);
      return;
    }
    if (j != 0)
    {
      if (localUpdateOp != null)
      {
        if (paramUpdateOp1.positionStart > localUpdateOp.positionStart) {
          paramUpdateOp1.positionStart -= localUpdateOp.itemCount;
        }
        if (paramUpdateOp1.itemCount > localUpdateOp.positionStart) {
          paramUpdateOp1.itemCount -= localUpdateOp.itemCount;
        }
      }
      if (paramUpdateOp1.positionStart > paramUpdateOp2.positionStart) {
        paramUpdateOp1.positionStart -= paramUpdateOp2.itemCount;
      }
      if (paramUpdateOp1.itemCount > paramUpdateOp2.positionStart) {
        paramUpdateOp1.itemCount -= paramUpdateOp2.itemCount;
      }
    }
    else
    {
      if (localUpdateOp != null)
      {
        if (paramUpdateOp1.positionStart >= localUpdateOp.positionStart) {
          paramUpdateOp1.positionStart -= localUpdateOp.itemCount;
        }
        if (paramUpdateOp1.itemCount >= localUpdateOp.positionStart) {
          paramUpdateOp1.itemCount -= localUpdateOp.itemCount;
        }
      }
      if (paramUpdateOp1.positionStart >= paramUpdateOp2.positionStart) {
        paramUpdateOp1.positionStart -= paramUpdateOp2.itemCount;
      }
      if (paramUpdateOp1.itemCount >= paramUpdateOp2.positionStart) {
        paramUpdateOp1.itemCount -= paramUpdateOp2.itemCount;
      }
    }
    paramList.set(paramInt1, paramUpdateOp2);
    if (paramUpdateOp1.positionStart != paramUpdateOp1.itemCount) {
      paramList.set(paramInt2, paramUpdateOp1);
    } else {
      paramList.remove(paramInt2);
    }
    if (localUpdateOp != null) {
      paramList.add(paramInt1, localUpdateOp);
    }
  }
  
  void swapMoveUpdate(List<AdapterHelper.UpdateOp> paramList, int paramInt1, AdapterHelper.UpdateOp paramUpdateOp1, int paramInt2, AdapterHelper.UpdateOp paramUpdateOp2)
  {
    AdapterHelper.UpdateOp localUpdateOp1 = null;
    AdapterHelper.UpdateOp localUpdateOp2 = null;
    if (paramUpdateOp1.itemCount < paramUpdateOp2.positionStart)
    {
      paramUpdateOp2.positionStart -= 1;
    }
    else if (paramUpdateOp1.itemCount < paramUpdateOp2.positionStart + paramUpdateOp2.itemCount)
    {
      paramUpdateOp2.itemCount -= 1;
      localUpdateOp1 = this.mCallback.obtainUpdateOp(4, paramUpdateOp1.positionStart, 1, paramUpdateOp2.payload);
    }
    if (paramUpdateOp1.positionStart <= paramUpdateOp2.positionStart)
    {
      paramUpdateOp2.positionStart += 1;
    }
    else if (paramUpdateOp1.positionStart < paramUpdateOp2.positionStart + paramUpdateOp2.itemCount)
    {
      int i = paramUpdateOp2.positionStart + paramUpdateOp2.itemCount - paramUpdateOp1.positionStart;
      localUpdateOp2 = this.mCallback.obtainUpdateOp(4, paramUpdateOp1.positionStart + 1, i, paramUpdateOp2.payload);
      paramUpdateOp2.itemCount -= i;
    }
    paramList.set(paramInt2, paramUpdateOp1);
    if (paramUpdateOp2.itemCount > 0)
    {
      paramList.set(paramInt1, paramUpdateOp2);
    }
    else
    {
      paramList.remove(paramInt1);
      this.mCallback.recycleUpdateOp(paramUpdateOp2);
    }
    if (localUpdateOp1 != null) {
      paramList.add(paramInt1, localUpdateOp1);
    }
    if (localUpdateOp2 != null) {
      paramList.add(paramInt1, localUpdateOp2);
    }
  }
  
  static abstract interface Callback
  {
    public abstract AdapterHelper.UpdateOp obtainUpdateOp(int paramInt1, int paramInt2, int paramInt3, Object paramObject);
    
    public abstract void recycleUpdateOp(AdapterHelper.UpdateOp paramUpdateOp);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/recyclerview/widget/OpReorderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */