package com.google.android.material.tabs;

import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver;
import androidx.viewpager2.widget.ViewPager2;
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback;
import java.lang.ref.WeakReference;

public final class TabLayoutMediator
{
  private RecyclerView.Adapter<?> adapter;
  private boolean attached;
  private final boolean autoRefresh;
  private TabLayoutOnPageChangeCallback onPageChangeCallback;
  private TabLayout.OnTabSelectedListener onTabSelectedListener;
  private RecyclerView.AdapterDataObserver pagerAdapterObserver;
  private final boolean smoothScroll;
  private final TabConfigurationStrategy tabConfigurationStrategy;
  private final TabLayout tabLayout;
  private final ViewPager2 viewPager;
  
  public TabLayoutMediator(TabLayout paramTabLayout, ViewPager2 paramViewPager2, TabConfigurationStrategy paramTabConfigurationStrategy)
  {
    this(paramTabLayout, paramViewPager2, true, paramTabConfigurationStrategy);
  }
  
  public TabLayoutMediator(TabLayout paramTabLayout, ViewPager2 paramViewPager2, boolean paramBoolean, TabConfigurationStrategy paramTabConfigurationStrategy)
  {
    this(paramTabLayout, paramViewPager2, paramBoolean, true, paramTabConfigurationStrategy);
  }
  
  public TabLayoutMediator(TabLayout paramTabLayout, ViewPager2 paramViewPager2, boolean paramBoolean1, boolean paramBoolean2, TabConfigurationStrategy paramTabConfigurationStrategy)
  {
    this.tabLayout = paramTabLayout;
    this.viewPager = paramViewPager2;
    this.autoRefresh = paramBoolean1;
    this.smoothScroll = paramBoolean2;
    this.tabConfigurationStrategy = paramTabConfigurationStrategy;
  }
  
  public void attach()
  {
    if (!this.attached)
    {
      Object localObject = this.viewPager.getAdapter();
      this.adapter = ((RecyclerView.Adapter)localObject);
      if (localObject != null)
      {
        this.attached = true;
        localObject = new TabLayoutOnPageChangeCallback(this.tabLayout);
        this.onPageChangeCallback = ((TabLayoutOnPageChangeCallback)localObject);
        this.viewPager.registerOnPageChangeCallback((ViewPager2.OnPageChangeCallback)localObject);
        localObject = new ViewPagerOnTabSelectedListener(this.viewPager, this.smoothScroll);
        this.onTabSelectedListener = ((TabLayout.OnTabSelectedListener)localObject);
        this.tabLayout.addOnTabSelectedListener((TabLayout.OnTabSelectedListener)localObject);
        if (this.autoRefresh)
        {
          localObject = new PagerAdapterObserver();
          this.pagerAdapterObserver = ((RecyclerView.AdapterDataObserver)localObject);
          this.adapter.registerAdapterDataObserver((RecyclerView.AdapterDataObserver)localObject);
        }
        populateTabsFromPagerAdapter();
        this.tabLayout.setScrollPosition(this.viewPager.getCurrentItem(), 0.0F, true);
        return;
      }
      throw new IllegalStateException("TabLayoutMediator attached before ViewPager2 has an adapter");
    }
    throw new IllegalStateException("TabLayoutMediator is already attached");
  }
  
  public void detach()
  {
    if (this.autoRefresh)
    {
      RecyclerView.Adapter localAdapter = this.adapter;
      if (localAdapter != null)
      {
        localAdapter.unregisterAdapterDataObserver(this.pagerAdapterObserver);
        this.pagerAdapterObserver = null;
      }
    }
    this.tabLayout.removeOnTabSelectedListener(this.onTabSelectedListener);
    this.viewPager.unregisterOnPageChangeCallback(this.onPageChangeCallback);
    this.onTabSelectedListener = null;
    this.onPageChangeCallback = null;
    this.adapter = null;
    this.attached = false;
  }
  
  public boolean isAttached()
  {
    return this.attached;
  }
  
  void populateTabsFromPagerAdapter()
  {
    this.tabLayout.removeAllTabs();
    Object localObject = this.adapter;
    if (localObject != null)
    {
      int j = ((RecyclerView.Adapter)localObject).getItemCount();
      for (int i = 0; i < j; i++)
      {
        localObject = this.tabLayout.newTab();
        this.tabConfigurationStrategy.onConfigureTab((TabLayout.Tab)localObject, i);
        this.tabLayout.addTab((TabLayout.Tab)localObject, false);
      }
      if (j > 0)
      {
        i = this.tabLayout.getTabCount();
        i = Math.min(this.viewPager.getCurrentItem(), i - 1);
        if (i != this.tabLayout.getSelectedTabPosition())
        {
          localObject = this.tabLayout;
          ((TabLayout)localObject).selectTab(((TabLayout)localObject).getTabAt(i));
        }
      }
    }
  }
  
  private class PagerAdapterObserver
    extends RecyclerView.AdapterDataObserver
  {
    PagerAdapterObserver() {}
    
    public void onChanged()
    {
      TabLayoutMediator.this.populateTabsFromPagerAdapter();
    }
    
    public void onItemRangeChanged(int paramInt1, int paramInt2)
    {
      TabLayoutMediator.this.populateTabsFromPagerAdapter();
    }
    
    public void onItemRangeChanged(int paramInt1, int paramInt2, Object paramObject)
    {
      TabLayoutMediator.this.populateTabsFromPagerAdapter();
    }
    
    public void onItemRangeInserted(int paramInt1, int paramInt2)
    {
      TabLayoutMediator.this.populateTabsFromPagerAdapter();
    }
    
    public void onItemRangeMoved(int paramInt1, int paramInt2, int paramInt3)
    {
      TabLayoutMediator.this.populateTabsFromPagerAdapter();
    }
    
    public void onItemRangeRemoved(int paramInt1, int paramInt2)
    {
      TabLayoutMediator.this.populateTabsFromPagerAdapter();
    }
  }
  
  public static abstract interface TabConfigurationStrategy
  {
    public abstract void onConfigureTab(TabLayout.Tab paramTab, int paramInt);
  }
  
  private static class TabLayoutOnPageChangeCallback
    extends ViewPager2.OnPageChangeCallback
  {
    private int previousScrollState;
    private int scrollState;
    private final WeakReference<TabLayout> tabLayoutRef;
    
    TabLayoutOnPageChangeCallback(TabLayout paramTabLayout)
    {
      this.tabLayoutRef = new WeakReference(paramTabLayout);
      reset();
    }
    
    public void onPageScrollStateChanged(int paramInt)
    {
      this.previousScrollState = this.scrollState;
      this.scrollState = paramInt;
    }
    
    public void onPageScrolled(int paramInt1, float paramFloat, int paramInt2)
    {
      TabLayout localTabLayout = (TabLayout)this.tabLayoutRef.get();
      if (localTabLayout != null)
      {
        paramInt2 = this.scrollState;
        boolean bool2 = false;
        boolean bool1;
        if ((paramInt2 == 2) && (this.previousScrollState != 1)) {
          bool1 = false;
        } else {
          bool1 = true;
        }
        if ((paramInt2 != 2) || (this.previousScrollState != 0)) {
          bool2 = true;
        }
        localTabLayout.setScrollPosition(paramInt1, paramFloat, bool1, bool2);
      }
    }
    
    public void onPageSelected(int paramInt)
    {
      TabLayout localTabLayout = (TabLayout)this.tabLayoutRef.get();
      if ((localTabLayout != null) && (localTabLayout.getSelectedTabPosition() != paramInt) && (paramInt < localTabLayout.getTabCount()))
      {
        int i = this.scrollState;
        boolean bool;
        if ((i != 0) && ((i != 2) || (this.previousScrollState != 0))) {
          bool = false;
        } else {
          bool = true;
        }
        localTabLayout.selectTab(localTabLayout.getTabAt(paramInt), bool);
      }
    }
    
    void reset()
    {
      this.scrollState = 0;
      this.previousScrollState = 0;
    }
  }
  
  private static class ViewPagerOnTabSelectedListener
    implements TabLayout.OnTabSelectedListener
  {
    private final boolean smoothScroll;
    private final ViewPager2 viewPager;
    
    ViewPagerOnTabSelectedListener(ViewPager2 paramViewPager2, boolean paramBoolean)
    {
      this.viewPager = paramViewPager2;
      this.smoothScroll = paramBoolean;
    }
    
    public void onTabReselected(TabLayout.Tab paramTab) {}
    
    public void onTabSelected(TabLayout.Tab paramTab)
    {
      this.viewPager.setCurrentItem(paramTab.getPosition(), this.smoothScroll);
    }
    
    public void onTabUnselected(TabLayout.Tab paramTab) {}
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/tabs/TabLayoutMediator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */