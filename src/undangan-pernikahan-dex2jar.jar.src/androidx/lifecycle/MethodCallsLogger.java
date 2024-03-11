package androidx.lifecycle;

import java.util.HashMap;
import java.util.Map;

public class MethodCallsLogger
{
  private Map<String, Integer> mCalledMethods = new HashMap();
  
  public boolean approveCall(String paramString, int paramInt)
  {
    Integer localInteger = (Integer)this.mCalledMethods.get(paramString);
    boolean bool = false;
    int i;
    if (localInteger != null) {
      i = localInteger.intValue();
    } else {
      i = 0;
    }
    int j;
    if ((i & paramInt) != 0) {
      j = 1;
    } else {
      j = 0;
    }
    this.mCalledMethods.put(paramString, Integer.valueOf(i | paramInt));
    if (j == 0) {
      bool = true;
    }
    return bool;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/lifecycle/MethodCallsLogger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */