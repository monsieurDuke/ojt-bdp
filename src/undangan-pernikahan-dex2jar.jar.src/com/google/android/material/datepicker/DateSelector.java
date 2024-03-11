package com.google.android.material.datepicker;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.util.Pair;
import java.util.Collection;

public abstract interface DateSelector<S>
  extends Parcelable
{
  public abstract int getDefaultThemeResId(Context paramContext);
  
  public abstract int getDefaultTitleResId();
  
  public abstract Collection<Long> getSelectedDays();
  
  public abstract Collection<Pair<Long, Long>> getSelectedRanges();
  
  public abstract S getSelection();
  
  public abstract String getSelectionDisplayString(Context paramContext);
  
  public abstract boolean isSelectionComplete();
  
  public abstract View onCreateTextInputView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle, CalendarConstraints paramCalendarConstraints, OnSelectionChangedListener<S> paramOnSelectionChangedListener);
  
  public abstract void select(long paramLong);
  
  public abstract void setSelection(S paramS);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/datepicker/DateSelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */