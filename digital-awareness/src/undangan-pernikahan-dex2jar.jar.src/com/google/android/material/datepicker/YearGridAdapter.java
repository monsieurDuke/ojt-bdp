package com.google.android.material.datepicker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.google.android.material.R.layout;
import com.google.android.material.R.string;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

class YearGridAdapter
  extends RecyclerView.Adapter<ViewHolder>
{
  private final MaterialCalendar<?> materialCalendar;
  
  YearGridAdapter(MaterialCalendar<?> paramMaterialCalendar)
  {
    this.materialCalendar = paramMaterialCalendar;
  }
  
  private View.OnClickListener createYearClickListener(final int paramInt)
  {
    new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        paramAnonymousView = Month.create(paramInt, YearGridAdapter.this.materialCalendar.getCurrentMonth().month);
        paramAnonymousView = YearGridAdapter.this.materialCalendar.getCalendarConstraints().clamp(paramAnonymousView);
        YearGridAdapter.this.materialCalendar.setCurrentMonth(paramAnonymousView);
        YearGridAdapter.this.materialCalendar.setSelector(MaterialCalendar.CalendarSelector.DAY);
      }
    };
  }
  
  public int getItemCount()
  {
    return this.materialCalendar.getCalendarConstraints().getYearSpan();
  }
  
  int getPositionForYear(int paramInt)
  {
    return paramInt - this.materialCalendar.getCalendarConstraints().getStart().year;
  }
  
  int getYearForPosition(int paramInt)
  {
    return this.materialCalendar.getCalendarConstraints().getStart().year + paramInt;
  }
  
  public void onBindViewHolder(ViewHolder paramViewHolder, int paramInt)
  {
    paramInt = getYearForPosition(paramInt);
    Object localObject1 = paramViewHolder.textView.getContext().getString(R.string.mtrl_picker_navigate_to_year_description);
    Object localObject3 = paramViewHolder.textView;
    Object localObject2 = String.format(Locale.getDefault(), "%d", new Object[] { Integer.valueOf(paramInt) });
    Log5ECF72.a(localObject2);
    LogE84000.a(localObject2);
    Log229316.a(localObject2);
    ((TextView)localObject3).setText((CharSequence)localObject2);
    localObject2 = paramViewHolder.textView;
    localObject1 = String.format((String)localObject1, new Object[] { Integer.valueOf(paramInt) });
    Log5ECF72.a(localObject1);
    LogE84000.a(localObject1);
    Log229316.a(localObject1);
    ((TextView)localObject2).setContentDescription((CharSequence)localObject1);
    localObject2 = this.materialCalendar.getCalendarStyle();
    localObject3 = UtcDates.getTodayCalendar();
    if (((Calendar)localObject3).get(1) == paramInt) {
      localObject1 = ((CalendarStyle)localObject2).todayYear;
    } else {
      localObject1 = ((CalendarStyle)localObject2).year;
    }
    Iterator localIterator = this.materialCalendar.getDateSelector().getSelectedDays().iterator();
    while (localIterator.hasNext())
    {
      ((Calendar)localObject3).setTimeInMillis(((Long)localIterator.next()).longValue());
      if (((Calendar)localObject3).get(1) == paramInt) {
        localObject1 = ((CalendarStyle)localObject2).selectedYear;
      }
    }
    ((CalendarItemStyle)localObject1).styleItem(paramViewHolder.textView);
    paramViewHolder.textView.setOnClickListener(createYearClickListener(paramInt));
  }
  
  public ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt)
  {
    return new ViewHolder((TextView)LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.mtrl_calendar_year, paramViewGroup, false));
  }
  
  public static class ViewHolder
    extends RecyclerView.ViewHolder
  {
    final TextView textView;
    
    ViewHolder(TextView paramTextView)
    {
      super();
      this.textView = paramTextView;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/datepicker/YearGridAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */