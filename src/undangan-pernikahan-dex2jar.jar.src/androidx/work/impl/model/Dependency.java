package androidx.work.impl.model;

public class Dependency
{
  public final String prerequisiteId;
  public final String workSpecId;
  
  public Dependency(String paramString1, String paramString2)
  {
    this.workSpecId = paramString1;
    this.prerequisiteId = paramString2;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/model/Dependency.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */