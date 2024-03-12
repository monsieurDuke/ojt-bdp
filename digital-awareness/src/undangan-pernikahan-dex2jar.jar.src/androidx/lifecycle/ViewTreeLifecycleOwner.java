package androidx.lifecycle;

import android.view.View;
import androidx.lifecycle.runtime.R.id;

public class ViewTreeLifecycleOwner
{
  public static LifecycleOwner get(View paramView)
  {
    LifecycleOwner localLifecycleOwner = (LifecycleOwner)paramView.getTag(R.id.view_tree_lifecycle_owner);
    if (localLifecycleOwner != null) {
      return localLifecycleOwner;
    }
    Object localObject = paramView.getParent();
    paramView = localLifecycleOwner;
    while ((paramView == null) && ((localObject instanceof View)))
    {
      localObject = (View)localObject;
      paramView = (LifecycleOwner)((View)localObject).getTag(R.id.view_tree_lifecycle_owner);
      localObject = ((View)localObject).getParent();
    }
    return paramView;
  }
  
  public static void set(View paramView, LifecycleOwner paramLifecycleOwner)
  {
    paramView.setTag(R.id.view_tree_lifecycle_owner, paramLifecycleOwner);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/lifecycle/ViewTreeLifecycleOwner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */