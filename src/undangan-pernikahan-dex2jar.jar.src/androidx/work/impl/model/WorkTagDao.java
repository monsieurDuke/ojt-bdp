package androidx.work.impl.model;

import java.util.List;

public abstract interface WorkTagDao
{
  public abstract List<String> getTagsForWorkSpecId(String paramString);
  
  public abstract List<String> getWorkSpecIdsWithTag(String paramString);
  
  public abstract void insert(WorkTag paramWorkTag);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/model/WorkTagDao.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */