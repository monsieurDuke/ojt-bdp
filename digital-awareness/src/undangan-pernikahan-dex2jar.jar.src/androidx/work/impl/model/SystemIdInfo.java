package androidx.work.impl.model;

public class SystemIdInfo
{
  public final int systemId;
  public final String workSpecId;
  
  public SystemIdInfo(String paramString, int paramInt)
  {
    this.workSpecId = paramString;
    this.systemId = paramInt;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof SystemIdInfo)) {
      return false;
    }
    paramObject = (SystemIdInfo)paramObject;
    if (this.systemId != ((SystemIdInfo)paramObject).systemId) {
      return false;
    }
    return this.workSpecId.equals(((SystemIdInfo)paramObject).workSpecId);
  }
  
  public int hashCode()
  {
    return this.workSpecId.hashCode() * 31 + this.systemId;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/model/SystemIdInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */