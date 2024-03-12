package androidx.work;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class OverwritingInputMerger
  extends InputMerger
{
  public Data merge(List<Data> paramList)
  {
    Data.Builder localBuilder = new Data.Builder();
    HashMap localHashMap = new HashMap();
    paramList = paramList.iterator();
    while (paramList.hasNext()) {
      localHashMap.putAll(((Data)paramList.next()).getKeyValueMap());
    }
    localBuilder.putAll(localHashMap);
    return localBuilder.build();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/OverwritingInputMerger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */