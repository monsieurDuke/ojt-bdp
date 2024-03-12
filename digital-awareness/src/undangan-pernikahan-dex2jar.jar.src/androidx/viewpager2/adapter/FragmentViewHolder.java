package androidx.viewpager2.adapter;

import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

public final class FragmentViewHolder
  extends RecyclerView.ViewHolder
{
  private FragmentViewHolder(FrameLayout paramFrameLayout)
  {
    super(paramFrameLayout);
  }
  
  static FragmentViewHolder create(ViewGroup paramViewGroup)
  {
    paramViewGroup = new FrameLayout(paramViewGroup.getContext());
    paramViewGroup.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
    paramViewGroup.setId(ViewCompat.generateViewId());
    paramViewGroup.setSaveEnabled(false);
    return new FragmentViewHolder(paramViewGroup);
  }
  
  FrameLayout getContainer()
  {
    return (FrameLayout)this.itemView;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/viewpager2/adapter/FragmentViewHolder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */