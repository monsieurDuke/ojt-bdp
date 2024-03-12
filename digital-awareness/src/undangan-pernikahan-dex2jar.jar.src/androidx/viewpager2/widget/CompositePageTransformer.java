package androidx.viewpager2.widget;

import android.view.View;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class CompositePageTransformer
  implements ViewPager2.PageTransformer
{
  private final List<ViewPager2.PageTransformer> mTransformers = new ArrayList();
  
  public void addTransformer(ViewPager2.PageTransformer paramPageTransformer)
  {
    this.mTransformers.add(paramPageTransformer);
  }
  
  public void removeTransformer(ViewPager2.PageTransformer paramPageTransformer)
  {
    this.mTransformers.remove(paramPageTransformer);
  }
  
  public void transformPage(View paramView, float paramFloat)
  {
    Iterator localIterator = this.mTransformers.iterator();
    while (localIterator.hasNext()) {
      ((ViewPager2.PageTransformer)localIterator.next()).transformPage(paramView, paramFloat);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/viewpager2/widget/CompositePageTransformer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */