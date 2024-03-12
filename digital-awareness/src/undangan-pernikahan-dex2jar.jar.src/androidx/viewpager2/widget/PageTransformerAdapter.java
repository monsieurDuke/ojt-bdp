package androidx.viewpager2.widget;

import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.Locale;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

final class PageTransformerAdapter
  extends ViewPager2.OnPageChangeCallback
{
  private final LinearLayoutManager mLayoutManager;
  private ViewPager2.PageTransformer mPageTransformer;
  
  PageTransformerAdapter(LinearLayoutManager paramLinearLayoutManager)
  {
    this.mLayoutManager = paramLinearLayoutManager;
  }
  
  ViewPager2.PageTransformer getPageTransformer()
  {
    return this.mPageTransformer;
  }
  
  public void onPageScrollStateChanged(int paramInt) {}
  
  public void onPageScrolled(int paramInt1, float paramFloat, int paramInt2)
  {
    if (this.mPageTransformer == null) {
      return;
    }
    float f = -paramFloat;
    paramInt2 = 0;
    while (paramInt2 < this.mLayoutManager.getChildCount())
    {
      Object localObject = this.mLayoutManager.getChildAt(paramInt2);
      if (localObject != null)
      {
        paramFloat = this.mLayoutManager.getPosition((View)localObject) - paramInt1;
        this.mPageTransformer.transformPage((View)localObject, paramFloat + f);
        paramInt2++;
      }
      else
      {
        localObject = String.format(Locale.US, "LayoutManager returned a null child at pos %d/%d while transforming pages", new Object[] { Integer.valueOf(paramInt2), Integer.valueOf(this.mLayoutManager.getChildCount()) });
        Log5ECF72.a(localObject);
        LogE84000.a(localObject);
        Log229316.a(localObject);
        throw new IllegalStateException((String)localObject);
      }
    }
  }
  
  public void onPageSelected(int paramInt) {}
  
  void setPageTransformer(ViewPager2.PageTransformer paramPageTransformer)
  {
    this.mPageTransformer = paramPageTransformer;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/viewpager2/widget/PageTransformerAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */