package com.google.android.material.datepicker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.LayoutParams;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.google.android.material.R.id;
import com.google.android.material.R.layout;

class MonthsPagerAdapter
  extends RecyclerView.Adapter<ViewHolder>
{
  private final CalendarConstraints calendarConstraints;
  private final DateSelector<?> dateSelector;
  private final int itemHeight;
  private final MaterialCalendar.OnDayClickListener onDayClickListener;
  
  MonthsPagerAdapter(Context paramContext, DateSelector<?> paramDateSelector, CalendarConstraints paramCalendarConstraints, MaterialCalendar.OnDayClickListener paramOnDayClickListener)
  {
    Month localMonth3 = paramCalendarConstraints.getStart();
    Month localMonth2 = paramCalendarConstraints.getEnd();
    Month localMonth1 = paramCalendarConstraints.getOpenAt();
    if (localMonth3.compareTo(localMonth1) <= 0)
    {
      if (localMonth1.compareTo(localMonth2) <= 0)
      {
        int k = MonthAdapter.MAXIMUM_WEEKS;
        int j = MaterialCalendar.getDayHeight(paramContext);
        int i;
        if (MaterialDatePicker.isFullscreen(paramContext)) {
          i = MaterialCalendar.getDayHeight(paramContext);
        } else {
          i = 0;
        }
        this.itemHeight = (k * j + i);
        this.calendarConstraints = paramCalendarConstraints;
        this.dateSelector = paramDateSelector;
        this.onDayClickListener = paramOnDayClickListener;
        setHasStableIds(true);
        return;
      }
      throw new IllegalArgumentException("currentPage cannot be after lastPage");
    }
    throw new IllegalArgumentException("firstPage cannot be after currentPage");
  }
  
  public int getItemCount()
  {
    return this.calendarConstraints.getMonthSpan();
  }
  
  public long getItemId(int paramInt)
  {
    return this.calendarConstraints.getStart().monthsLater(paramInt).getStableId();
  }
  
  Month getPageMonth(int paramInt)
  {
    return this.calendarConstraints.getStart().monthsLater(paramInt);
  }
  
  CharSequence getPageTitle(int paramInt)
  {
    return getPageMonth(paramInt).getLongName();
  }
  
  int getPosition(Month paramMonth)
  {
    return this.calendarConstraints.getStart().monthsUntil(paramMonth);
  }
  
  public void onBindViewHolder(final ViewHolder paramViewHolder, int paramInt)
  {
    Month localMonth = this.calendarConstraints.getStart().monthsLater(paramInt);
    paramViewHolder.monthTitle.setText(localMonth.getLongName());
    paramViewHolder = (MaterialCalendarGridView)paramViewHolder.monthGrid.findViewById(R.id.month_grid);
    if ((paramViewHolder.getAdapter() != null) && (localMonth.equals(paramViewHolder.getAdapter().month)))
    {
      paramViewHolder.invalidate();
      paramViewHolder.getAdapter().updateSelectedStates(paramViewHolder);
    }
    else
    {
      MonthAdapter localMonthAdapter = new MonthAdapter(localMonth, this.dateSelector, this.calendarConstraints);
      paramViewHolder.setNumColumns(localMonth.daysInWeek);
      paramViewHolder.setAdapter(localMonthAdapter);
    }
    paramViewHolder.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        if (paramViewHolder.getAdapter().withinMonth(paramAnonymousInt)) {
          MonthsPagerAdapter.this.onDayClickListener.onDayClick(paramViewHolder.getAdapter().getItem(paramAnonymousInt).longValue());
        }
      }
    });
  }
  
  public ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt)
  {
    LinearLayout localLinearLayout = (LinearLayout)LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.mtrl_calendar_month_labeled, paramViewGroup, false);
    if (MaterialDatePicker.isFullscreen(paramViewGroup.getContext()))
    {
      localLinearLayout.setLayoutParams(new RecyclerView.LayoutParams(-1, this.itemHeight));
      return new ViewHolder(localLinearLayout, true);
    }
    return new ViewHolder(localLinearLayout, false);
  }
  
  public static class ViewHolder
    extends RecyclerView.ViewHolder
  {
    final MaterialCalendarGridView monthGrid;
    final TextView monthTitle;
    
    ViewHolder(LinearLayout paramLinearLayout, boolean paramBoolean)
    {
      super();
      TextView localTextView = (TextView)paramLinearLayout.findViewById(R.id.month_title);
      this.monthTitle = localTextView;
      ViewCompat.setAccessibilityHeading(localTextView, true);
      this.monthGrid = ((MaterialCalendarGridView)paramLinearLayout.findViewById(R.id.month_grid));
      if (!paramBoolean) {
        localTextView.setVisibility(8);
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/datepicker/MonthsPagerAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */