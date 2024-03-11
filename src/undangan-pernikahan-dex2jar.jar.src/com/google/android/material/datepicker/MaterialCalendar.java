package com.google.android.material.datepicker;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import androidx.core.util.Pair;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ItemDecoration;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;
import androidx.recyclerview.widget.RecyclerView.OnScrollListener;
import androidx.recyclerview.widget.RecyclerView.State;
import com.google.android.material.R.dimen;
import com.google.android.material.R.id;
import com.google.android.material.R.integer;
import com.google.android.material.R.layout;
import com.google.android.material.R.string;
import com.google.android.material.button.MaterialButton;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;

public final class MaterialCalendar<S>
  extends PickerFragment<S>
{
  private static final String CALENDAR_CONSTRAINTS_KEY = "CALENDAR_CONSTRAINTS_KEY";
  private static final String CURRENT_MONTH_KEY = "CURRENT_MONTH_KEY";
  private static final String GRID_SELECTOR_KEY = "GRID_SELECTOR_KEY";
  static final Object MONTHS_VIEW_GROUP_TAG = "MONTHS_VIEW_GROUP_TAG";
  static final Object NAVIGATION_NEXT_TAG = "NAVIGATION_NEXT_TAG";
  static final Object NAVIGATION_PREV_TAG = "NAVIGATION_PREV_TAG";
  static final Object SELECTOR_TOGGLE_TAG = "SELECTOR_TOGGLE_TAG";
  private static final int SMOOTH_SCROLL_MAX = 3;
  private static final String THEME_RES_ID_KEY = "THEME_RES_ID_KEY";
  private CalendarConstraints calendarConstraints;
  private CalendarSelector calendarSelector;
  private CalendarStyle calendarStyle;
  private Month current;
  private DateSelector<S> dateSelector;
  private View dayFrame;
  private RecyclerView recyclerView;
  private int themeResId;
  private View yearFrame;
  private RecyclerView yearSelector;
  
  private void addActionsToMonthNavigation(View paramView, final MonthsPagerAdapter paramMonthsPagerAdapter)
  {
    final MaterialButton localMaterialButton1 = (MaterialButton)paramView.findViewById(R.id.month_navigation_fragment_toggle);
    localMaterialButton1.setTag(SELECTOR_TOGGLE_TAG);
    ViewCompat.setAccessibilityDelegate(localMaterialButton1, new AccessibilityDelegateCompat()
    {
      public void onInitializeAccessibilityNodeInfo(View paramAnonymousView, AccessibilityNodeInfoCompat paramAnonymousAccessibilityNodeInfoCompat)
      {
        super.onInitializeAccessibilityNodeInfo(paramAnonymousView, paramAnonymousAccessibilityNodeInfoCompat);
        if (MaterialCalendar.this.dayFrame.getVisibility() == 0) {
          paramAnonymousView = MaterialCalendar.this.getString(R.string.mtrl_picker_toggle_to_year_selection);
        } else {
          paramAnonymousView = MaterialCalendar.this.getString(R.string.mtrl_picker_toggle_to_day_selection);
        }
        paramAnonymousAccessibilityNodeInfoCompat.setHintText(paramAnonymousView);
      }
    });
    MaterialButton localMaterialButton3 = (MaterialButton)paramView.findViewById(R.id.month_navigation_previous);
    localMaterialButton3.setTag(NAVIGATION_PREV_TAG);
    MaterialButton localMaterialButton2 = (MaterialButton)paramView.findViewById(R.id.month_navigation_next);
    localMaterialButton2.setTag(NAVIGATION_NEXT_TAG);
    this.yearFrame = paramView.findViewById(R.id.mtrl_calendar_year_selector_frame);
    this.dayFrame = paramView.findViewById(R.id.mtrl_calendar_day_selector_frame);
    setSelector(CalendarSelector.DAY);
    localMaterialButton1.setText(this.current.getLongName());
    this.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
    {
      public void onScrollStateChanged(RecyclerView paramAnonymousRecyclerView, int paramAnonymousInt)
      {
        if (paramAnonymousInt == 0)
        {
          CharSequence localCharSequence = localMaterialButton1.getText();
          if (Build.VERSION.SDK_INT >= 16) {
            paramAnonymousRecyclerView.announceForAccessibility(localCharSequence);
          } else {
            paramAnonymousRecyclerView.sendAccessibilityEvent(2048);
          }
        }
      }
      
      public void onScrolled(RecyclerView paramAnonymousRecyclerView, int paramAnonymousInt1, int paramAnonymousInt2)
      {
        if (paramAnonymousInt1 < 0) {
          paramAnonymousInt1 = MaterialCalendar.this.getLayoutManager().findFirstVisibleItemPosition();
        } else {
          paramAnonymousInt1 = MaterialCalendar.this.getLayoutManager().findLastVisibleItemPosition();
        }
        MaterialCalendar.access$602(MaterialCalendar.this, paramMonthsPagerAdapter.getPageMonth(paramAnonymousInt1));
        localMaterialButton1.setText(paramMonthsPagerAdapter.getPageTitle(paramAnonymousInt1));
      }
    });
    localMaterialButton1.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        MaterialCalendar.this.toggleVisibleSelector();
      }
    });
    localMaterialButton2.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        int i = MaterialCalendar.this.getLayoutManager().findFirstVisibleItemPosition();
        if (i + 1 < MaterialCalendar.this.recyclerView.getAdapter().getItemCount()) {
          MaterialCalendar.this.setCurrentMonth(paramMonthsPagerAdapter.getPageMonth(i + 1));
        }
      }
    });
    localMaterialButton3.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        int i = MaterialCalendar.this.getLayoutManager().findLastVisibleItemPosition();
        if (i - 1 >= 0) {
          MaterialCalendar.this.setCurrentMonth(paramMonthsPagerAdapter.getPageMonth(i - 1));
        }
      }
    });
  }
  
  private RecyclerView.ItemDecoration createItemDecoration()
  {
    new RecyclerView.ItemDecoration()
    {
      private final Calendar endItem = UtcDates.getUtcCalendar();
      private final Calendar startItem = UtcDates.getUtcCalendar();
      
      public void onDraw(Canvas paramAnonymousCanvas, RecyclerView paramAnonymousRecyclerView, RecyclerView.State paramAnonymousState)
      {
        if (((paramAnonymousRecyclerView.getAdapter() instanceof YearGridAdapter)) && ((paramAnonymousRecyclerView.getLayoutManager() instanceof GridLayoutManager)))
        {
          YearGridAdapter localYearGridAdapter = (YearGridAdapter)paramAnonymousRecyclerView.getAdapter();
          GridLayoutManager localGridLayoutManager = (GridLayoutManager)paramAnonymousRecyclerView.getLayoutManager();
          paramAnonymousState = MaterialCalendar.this.dateSelector.getSelectedRanges().iterator();
          while (paramAnonymousState.hasNext())
          {
            Pair localPair = (Pair)paramAnonymousState.next();
            if (localPair.first != null) {
              if (localPair.second != null)
              {
                this.startItem.setTimeInMillis(((Long)localPair.first).longValue());
                this.endItem.setTimeInMillis(((Long)localPair.second).longValue());
                int i = localYearGridAdapter.getPositionForYear(this.startItem.get(1));
                int j = localYearGridAdapter.getPositionForYear(this.endItem.get(1));
                View localView2 = localGridLayoutManager.findViewByPosition(i);
                View localView1 = localGridLayoutManager.findViewByPosition(j);
                int n = i / localGridLayoutManager.getSpanCount();
                int i1 = j / localGridLayoutManager.getSpanCount();
                for (j = n; j <= i1; j++)
                {
                  View localView3 = localGridLayoutManager.findViewByPosition(localGridLayoutManager.getSpanCount() * j);
                  if (localView3 != null)
                  {
                    int i2 = localView3.getTop();
                    int i3 = MaterialCalendar.this.calendarStyle.year.getTopInset();
                    int i5 = localView3.getBottom();
                    int i4 = MaterialCalendar.this.calendarStyle.year.getBottomInset();
                    int k;
                    if (j == n) {
                      k = localView2.getLeft() + localView2.getWidth() / 2;
                    } else {
                      k = 0;
                    }
                    int m;
                    if (j == i1) {
                      m = localView1.getLeft() + localView1.getWidth() / 2;
                    } else {
                      m = paramAnonymousRecyclerView.getWidth();
                    }
                    paramAnonymousCanvas.drawRect(k, i2 + i3, m, i5 - i4, MaterialCalendar.this.calendarStyle.rangeFill);
                  }
                }
              }
            }
          }
          return;
        }
      }
    };
  }
  
  static int getDayHeight(Context paramContext)
  {
    return paramContext.getResources().getDimensionPixelSize(R.dimen.mtrl_calendar_day_height);
  }
  
  private static int getDialogPickerHeight(Context paramContext)
  {
    paramContext = paramContext.getResources();
    return paramContext.getDimensionPixelSize(R.dimen.mtrl_calendar_navigation_height) + paramContext.getDimensionPixelOffset(R.dimen.mtrl_calendar_navigation_top_padding) + paramContext.getDimensionPixelOffset(R.dimen.mtrl_calendar_navigation_bottom_padding) + paramContext.getDimensionPixelSize(R.dimen.mtrl_calendar_days_of_week_height) + (MonthAdapter.MAXIMUM_WEEKS * paramContext.getDimensionPixelSize(R.dimen.mtrl_calendar_day_height) + (MonthAdapter.MAXIMUM_WEEKS - 1) * paramContext.getDimensionPixelOffset(R.dimen.mtrl_calendar_month_vertical_padding)) + paramContext.getDimensionPixelOffset(R.dimen.mtrl_calendar_bottom_padding);
  }
  
  public static <T> MaterialCalendar<T> newInstance(DateSelector<T> paramDateSelector, int paramInt, CalendarConstraints paramCalendarConstraints)
  {
    MaterialCalendar localMaterialCalendar = new MaterialCalendar();
    Bundle localBundle = new Bundle();
    localBundle.putInt("THEME_RES_ID_KEY", paramInt);
    localBundle.putParcelable("GRID_SELECTOR_KEY", paramDateSelector);
    localBundle.putParcelable("CALENDAR_CONSTRAINTS_KEY", paramCalendarConstraints);
    localBundle.putParcelable("CURRENT_MONTH_KEY", paramCalendarConstraints.getOpenAt());
    localMaterialCalendar.setArguments(localBundle);
    return localMaterialCalendar;
  }
  
  private void postSmoothRecyclerViewScroll(final int paramInt)
  {
    this.recyclerView.post(new Runnable()
    {
      public void run()
      {
        MaterialCalendar.this.recyclerView.smoothScrollToPosition(paramInt);
      }
    });
  }
  
  public boolean addOnSelectionChangedListener(OnSelectionChangedListener<S> paramOnSelectionChangedListener)
  {
    return super.addOnSelectionChangedListener(paramOnSelectionChangedListener);
  }
  
  CalendarConstraints getCalendarConstraints()
  {
    return this.calendarConstraints;
  }
  
  CalendarStyle getCalendarStyle()
  {
    return this.calendarStyle;
  }
  
  Month getCurrentMonth()
  {
    return this.current;
  }
  
  public DateSelector<S> getDateSelector()
  {
    return this.dateSelector;
  }
  
  LinearLayoutManager getLayoutManager()
  {
    return (LinearLayoutManager)this.recyclerView.getLayoutManager();
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle == null) {
      paramBundle = getArguments();
    }
    this.themeResId = paramBundle.getInt("THEME_RES_ID_KEY");
    this.dateSelector = ((DateSelector)paramBundle.getParcelable("GRID_SELECTOR_KEY"));
    this.calendarConstraints = ((CalendarConstraints)paramBundle.getParcelable("CALENDAR_CONSTRAINTS_KEY"));
    this.current = ((Month)paramBundle.getParcelable("CURRENT_MONTH_KEY"));
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramBundle = new ContextThemeWrapper(getContext(), this.themeResId);
    this.calendarStyle = new CalendarStyle(paramBundle);
    paramLayoutInflater = paramLayoutInflater.cloneInContext(paramBundle);
    Object localObject = this.calendarConstraints.getStart();
    final int j;
    if (MaterialDatePicker.isFullscreen(paramBundle))
    {
      i = R.layout.mtrl_calendar_vertical;
      j = 1;
    }
    else
    {
      i = R.layout.mtrl_calendar_horizontal;
      j = 0;
    }
    paramLayoutInflater = paramLayoutInflater.inflate(i, paramViewGroup, false);
    paramLayoutInflater.setMinimumHeight(getDialogPickerHeight(requireContext()));
    paramViewGroup = (GridView)paramLayoutInflater.findViewById(R.id.mtrl_calendar_days_of_week);
    ViewCompat.setAccessibilityDelegate(paramViewGroup, new AccessibilityDelegateCompat()
    {
      public void onInitializeAccessibilityNodeInfo(View paramAnonymousView, AccessibilityNodeInfoCompat paramAnonymousAccessibilityNodeInfoCompat)
      {
        super.onInitializeAccessibilityNodeInfo(paramAnonymousView, paramAnonymousAccessibilityNodeInfoCompat);
        paramAnonymousAccessibilityNodeInfoCompat.setCollectionInfo(null);
      }
    });
    paramViewGroup.setAdapter(new DaysOfWeekAdapter());
    paramViewGroup.setNumColumns(((Month)localObject).daysInWeek);
    paramViewGroup.setEnabled(false);
    this.recyclerView = ((RecyclerView)paramLayoutInflater.findViewById(R.id.mtrl_calendar_months));
    paramViewGroup = new SmoothCalendarLayoutManager(getContext(), j, false)
    {
      protected void calculateExtraLayoutSpace(RecyclerView.State paramAnonymousState, int[] paramAnonymousArrayOfInt)
      {
        if (j == 0)
        {
          paramAnonymousArrayOfInt[0] = MaterialCalendar.this.recyclerView.getWidth();
          paramAnonymousArrayOfInt[1] = MaterialCalendar.this.recyclerView.getWidth();
        }
        else
        {
          paramAnonymousArrayOfInt[0] = MaterialCalendar.this.recyclerView.getHeight();
          paramAnonymousArrayOfInt[1] = MaterialCalendar.this.recyclerView.getHeight();
        }
      }
    };
    this.recyclerView.setLayoutManager(paramViewGroup);
    this.recyclerView.setTag(MONTHS_VIEW_GROUP_TAG);
    localObject = new MonthsPagerAdapter(paramBundle, this.dateSelector, this.calendarConstraints, new OnDayClickListener()
    {
      public void onDayClick(long paramAnonymousLong)
      {
        if (MaterialCalendar.this.calendarConstraints.getDateValidator().isValid(paramAnonymousLong))
        {
          MaterialCalendar.this.dateSelector.select(paramAnonymousLong);
          Iterator localIterator = MaterialCalendar.this.onSelectionChangedListeners.iterator();
          while (localIterator.hasNext()) {
            ((OnSelectionChangedListener)localIterator.next()).onSelectionChanged(MaterialCalendar.this.dateSelector.getSelection());
          }
          MaterialCalendar.this.recyclerView.getAdapter().notifyDataSetChanged();
          if (MaterialCalendar.this.yearSelector != null) {
            MaterialCalendar.this.yearSelector.getAdapter().notifyDataSetChanged();
          }
        }
      }
    });
    this.recyclerView.setAdapter((RecyclerView.Adapter)localObject);
    int i = paramBundle.getResources().getInteger(R.integer.mtrl_calendar_year_selector_span);
    paramViewGroup = (RecyclerView)paramLayoutInflater.findViewById(R.id.mtrl_calendar_year_selector_frame);
    this.yearSelector = paramViewGroup;
    if (paramViewGroup != null)
    {
      paramViewGroup.setHasFixedSize(true);
      this.yearSelector.setLayoutManager(new GridLayoutManager(paramBundle, i, 1, false));
      this.yearSelector.setAdapter(new YearGridAdapter(this));
      this.yearSelector.addItemDecoration(createItemDecoration());
    }
    if (paramLayoutInflater.findViewById(R.id.month_navigation_fragment_toggle) != null) {
      addActionsToMonthNavigation(paramLayoutInflater, (MonthsPagerAdapter)localObject);
    }
    if (!MaterialDatePicker.isFullscreen(paramBundle)) {
      new PagerSnapHelper().attachToRecyclerView(this.recyclerView);
    }
    this.recyclerView.scrollToPosition(((MonthsPagerAdapter)localObject).getPosition(this.current));
    return paramLayoutInflater;
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putInt("THEME_RES_ID_KEY", this.themeResId);
    paramBundle.putParcelable("GRID_SELECTOR_KEY", this.dateSelector);
    paramBundle.putParcelable("CALENDAR_CONSTRAINTS_KEY", this.calendarConstraints);
    paramBundle.putParcelable("CURRENT_MONTH_KEY", this.current);
  }
  
  void setCurrentMonth(Month paramMonth)
  {
    MonthsPagerAdapter localMonthsPagerAdapter = (MonthsPagerAdapter)this.recyclerView.getAdapter();
    int k = localMonthsPagerAdapter.getPosition(paramMonth);
    int m = k - localMonthsPagerAdapter.getPosition(this.current);
    int i = Math.abs(m);
    int j = 1;
    if (i > 3) {
      i = 1;
    } else {
      i = 0;
    }
    if (m <= 0) {
      j = 0;
    }
    this.current = paramMonth;
    if ((i != 0) && (j != 0))
    {
      this.recyclerView.scrollToPosition(k - 3);
      postSmoothRecyclerViewScroll(k);
    }
    else if (i != 0)
    {
      this.recyclerView.scrollToPosition(k + 3);
      postSmoothRecyclerViewScroll(k);
    }
    else
    {
      postSmoothRecyclerViewScroll(k);
    }
  }
  
  void setSelector(CalendarSelector paramCalendarSelector)
  {
    this.calendarSelector = paramCalendarSelector;
    if (paramCalendarSelector == CalendarSelector.YEAR)
    {
      this.yearSelector.getLayoutManager().scrollToPosition(((YearGridAdapter)this.yearSelector.getAdapter()).getPositionForYear(this.current.year));
      this.yearFrame.setVisibility(0);
      this.dayFrame.setVisibility(8);
    }
    else if (paramCalendarSelector == CalendarSelector.DAY)
    {
      this.yearFrame.setVisibility(8);
      this.dayFrame.setVisibility(0);
      setCurrentMonth(this.current);
    }
  }
  
  void toggleVisibleSelector()
  {
    if (this.calendarSelector == CalendarSelector.YEAR) {
      setSelector(CalendarSelector.DAY);
    } else if (this.calendarSelector == CalendarSelector.DAY) {
      setSelector(CalendarSelector.YEAR);
    }
  }
  
  static enum CalendarSelector
  {
    private static final CalendarSelector[] $VALUES;
    
    static
    {
      CalendarSelector localCalendarSelector2 = new CalendarSelector("DAY", 0);
      DAY = localCalendarSelector2;
      CalendarSelector localCalendarSelector1 = new CalendarSelector("YEAR", 1);
      YEAR = localCalendarSelector1;
      $VALUES = new CalendarSelector[] { localCalendarSelector2, localCalendarSelector1 };
    }
    
    private CalendarSelector() {}
  }
  
  static abstract interface OnDayClickListener
  {
    public abstract void onDayClick(long paramLong);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/datepicker/MaterialCalendar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */