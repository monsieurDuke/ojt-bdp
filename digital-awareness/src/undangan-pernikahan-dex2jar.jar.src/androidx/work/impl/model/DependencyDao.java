package androidx.work.impl.model;

import java.util.List;

public abstract interface DependencyDao
{
  public abstract List<String> getDependentWorkIds(String paramString);
  
  public abstract List<String> getPrerequisites(String paramString);
  
  public abstract boolean hasCompletedAllPrerequisites(String paramString);
  
  public abstract boolean hasDependents(String paramString);
  
  public abstract void insertDependency(Dependency paramDependency);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/model/DependencyDao.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */