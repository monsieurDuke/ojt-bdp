package androidx.work;

import java.util.List;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public abstract class InputMerger
{
  private static final String TAG;
  
  static
  {
    String str = Logger.tagWithPrefix("InputMerger");
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    TAG = str;
  }
  
  public static InputMerger fromClassName(String paramString)
  {
    try
    {
      InputMerger localInputMerger = (InputMerger)Class.forName(paramString).newInstance();
      return localInputMerger;
    }
    catch (Exception localException)
    {
      Logger.get().error(TAG, "Trouble instantiating + " + paramString, new Throwable[] { localException });
    }
    return null;
  }
  
  public abstract Data merge(List<Data> paramList);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/InputMerger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */