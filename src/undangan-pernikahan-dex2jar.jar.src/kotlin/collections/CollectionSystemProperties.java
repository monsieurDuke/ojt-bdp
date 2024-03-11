package kotlin.collections;

import kotlin.Metadata;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000\022\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\013\n\000\bÀ\002\030\0002\0020\001B\007\b\002¢\006\002\020\002R\020\020\003\032\0020\0048\000X\004¢\006\002\n\000¨\006\005"}, d2={"Lkotlin/collections/CollectionSystemProperties;", "", "()V", "brittleContainsOptimizationEnabled", "", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public final class CollectionSystemProperties
{
  public static final CollectionSystemProperties INSTANCE = new CollectionSystemProperties();
  public static final boolean brittleContainsOptimizationEnabled;
  
  static
  {
    String str = System.getProperty("kotlin.collections.convert_arg_to_set_in_removeAll");
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    boolean bool;
    if (str != null) {
      bool = Boolean.parseBoolean(str);
    } else {
      bool = false;
    }
    brittleContainsOptimizationEnabled = bool;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/collections/CollectionSystemProperties.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */