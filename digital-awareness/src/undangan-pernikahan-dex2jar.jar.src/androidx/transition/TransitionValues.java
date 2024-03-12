package androidx.transition;

import android.view.View;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class TransitionValues
{
  final ArrayList<Transition> mTargetedTransitions = new ArrayList();
  public final Map<String, Object> values = new HashMap();
  public View view;
  
  @Deprecated
  public TransitionValues() {}
  
  public TransitionValues(View paramView)
  {
    this.view = paramView;
  }
  
  public boolean equals(Object paramObject)
  {
    return ((paramObject instanceof TransitionValues)) && (this.view == ((TransitionValues)paramObject).view) && (this.values.equals(((TransitionValues)paramObject).values));
  }
  
  public int hashCode()
  {
    return this.view.hashCode() * 31 + this.values.hashCode();
  }
  
  public String toString()
  {
    Object localObject1 = new StringBuilder().append("TransitionValues@");
    Object localObject2 = Integer.toHexString(hashCode());
    Log5ECF72.a(localObject2);
    LogE84000.a(localObject2);
    Log229316.a(localObject2);
    localObject1 = (String)localObject2 + ":\n";
    localObject1 = (String)localObject1 + "    view = " + this.view + "\n";
    localObject1 = (String)localObject1 + "    values:";
    localObject2 = this.values.keySet().iterator();
    while (((Iterator)localObject2).hasNext())
    {
      String str = (String)((Iterator)localObject2).next();
      localObject1 = (String)localObject1 + "    " + str + ": " + this.values.get(str) + "\n";
    }
    return (String)localObject1;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/transition/TransitionValues.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */