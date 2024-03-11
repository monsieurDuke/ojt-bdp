package androidx.lifecycle;

import android.view.View;
import androidx.lifecycle.viewmodel.R.id;

public class ViewTreeViewModelStoreOwner
{
  public static ViewModelStoreOwner get(View paramView)
  {
    ViewModelStoreOwner localViewModelStoreOwner = (ViewModelStoreOwner)paramView.getTag(R.id.view_tree_view_model_store_owner);
    if (localViewModelStoreOwner != null) {
      return localViewModelStoreOwner;
    }
    Object localObject = paramView.getParent();
    paramView = localViewModelStoreOwner;
    while ((paramView == null) && ((localObject instanceof View)))
    {
      localObject = (View)localObject;
      paramView = (ViewModelStoreOwner)((View)localObject).getTag(R.id.view_tree_view_model_store_owner);
      localObject = ((View)localObject).getParent();
    }
    return paramView;
  }
  
  public static void set(View paramView, ViewModelStoreOwner paramViewModelStoreOwner)
  {
    paramView.setTag(R.id.view_tree_view_model_store_owner, paramViewModelStoreOwner);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/lifecycle/ViewTreeViewModelStoreOwner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */