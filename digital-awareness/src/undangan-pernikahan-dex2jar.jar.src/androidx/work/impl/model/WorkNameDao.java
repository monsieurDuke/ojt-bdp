package androidx.work.impl.model;

import java.util.List;

public abstract interface WorkNameDao
{
  public abstract List<String> getNamesForWorkSpecId(String paramString);
  
  public abstract List<String> getWorkSpecIdsWithName(String paramString);
  
  public abstract void insert(WorkName paramWorkName);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/model/WorkNameDao.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */