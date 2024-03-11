package com.google.android.material.datepicker;

import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.Iterator;
import java.util.LinkedHashSet;

public final class MaterialTextInputPicker<S>
  extends PickerFragment<S>
{
  private static final String CALENDAR_CONSTRAINTS_KEY = "CALENDAR_CONSTRAINTS_KEY";
  private static final String DATE_SELECTOR_KEY = "DATE_SELECTOR_KEY";
  private static final String THEME_RES_ID_KEY = "THEME_RES_ID_KEY";
  private CalendarConstraints calendarConstraints;
  private DateSelector<S> dateSelector;
  private int themeResId;
  
  static <T> MaterialTextInputPicker<T> newInstance(DateSelector<T> paramDateSelector, int paramInt, CalendarConstraints paramCalendarConstraints)
  {
    MaterialTextInputPicker localMaterialTextInputPicker = new MaterialTextInputPicker();
    Bundle localBundle = new Bundle();
    localBundle.putInt("THEME_RES_ID_KEY", paramInt);
    localBundle.putParcelable("DATE_SELECTOR_KEY", paramDateSelector);
    localBundle.putParcelable("CALENDAR_CONSTRAINTS_KEY", paramCalendarConstraints);
    localMaterialTextInputPicker.setArguments(localBundle);
    return localMaterialTextInputPicker;
  }
  
  public DateSelector<S> getDateSelector()
  {
    DateSelector localDateSelector = this.dateSelector;
    if (localDateSelector != null) {
      return localDateSelector;
    }
    throw new IllegalStateException("dateSelector should not be null. Use MaterialTextInputPicker#newInstance() to create this fragment with a DateSelector, and call this method after the fragment has been created.");
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle == null) {
      paramBundle = getArguments();
    }
    this.themeResId = paramBundle.getInt("THEME_RES_ID_KEY");
    this.dateSelector = ((DateSelector)paramBundle.getParcelable("DATE_SELECTOR_KEY"));
    this.calendarConstraints = ((CalendarConstraints)paramBundle.getParcelable("CALENDAR_CONSTRAINTS_KEY"));
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.cloneInContext(new ContextThemeWrapper(getContext(), this.themeResId));
    this.dateSelector.onCreateTextInputView(paramLayoutInflater, paramViewGroup, paramBundle, this.calendarConstraints, new OnSelectionChangedListener()
    {
      public void onIncompleteSelectionChanged()
      {
        Iterator localIterator = MaterialTextInputPicker.this.onSelectionChangedListeners.iterator();
        while (localIterator.hasNext()) {
          ((OnSelectionChangedListener)localIterator.next()).onIncompleteSelectionChanged();
        }
      }
      
      public void onSelectionChanged(S paramAnonymousS)
      {
        Iterator localIterator = MaterialTextInputPicker.this.onSelectionChangedListeners.iterator();
        while (localIterator.hasNext()) {
          ((OnSelectionChangedListener)localIterator.next()).onSelectionChanged(paramAnonymousS);
        }
      }
    });
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putInt("THEME_RES_ID_KEY", this.themeResId);
    paramBundle.putParcelable("DATE_SELECTOR_KEY", this.dateSelector);
    paramBundle.putParcelable("CALENDAR_CONSTRAINTS_KEY", this.calendarConstraints);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/datepicker/MaterialTextInputPicker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */