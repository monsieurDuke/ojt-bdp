package androidx.work.impl.model;

import androidx.work.Data;
import java.util.List;

public abstract interface WorkProgressDao
{
  public abstract void delete(String paramString);
  
  public abstract void deleteAll();
  
  public abstract Data getProgressForWorkSpecId(String paramString);
  
  public abstract List<Data> getProgressForWorkSpecIds(List<String> paramList);
  
  public abstract void insert(WorkProgress paramWorkProgress);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/model/WorkProgressDao.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */