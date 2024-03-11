package com.google.android.material.datepicker;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.core.util.Pair;
import com.google.android.material.R.attr;
import com.google.android.material.R.id;
import com.google.android.material.R.layout;
import com.google.android.material.R.string;
import com.google.android.material.internal.ManufacturerUtils;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.textfield.TextInputLayout;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class SingleDateSelector
  implements DateSelector<Long>
{
  public static final Parcelable.Creator<SingleDateSelector> CREATOR = new Parcelable.Creator()
  {
    public SingleDateSelector createFromParcel(Parcel paramAnonymousParcel)
    {
      SingleDateSelector localSingleDateSelector = new SingleDateSelector();
      SingleDateSelector.access$102(localSingleDateSelector, (Long)paramAnonymousParcel.readValue(Long.class.getClassLoader()));
      return localSingleDateSelector;
    }
    
    public SingleDateSelector[] newArray(int paramAnonymousInt)
    {
      return new SingleDateSelector[paramAnonymousInt];
    }
  };
  private Long selectedItem;
  
  private void clearSelection()
  {
    this.selectedItem = null;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public int getDefaultThemeResId(Context paramContext)
  {
    return MaterialAttributes.resolveOrThrow(paramContext, R.attr.materialCalendarTheme, MaterialDatePicker.class.getCanonicalName());
  }
  
  public int getDefaultTitleResId()
  {
    return R.string.mtrl_picker_date_header_title;
  }
  
  public Collection<Long> getSelectedDays()
  {
    ArrayList localArrayList = new ArrayList();
    Long localLong = this.selectedItem;
    if (localLong != null) {
      localArrayList.add(localLong);
    }
    return localArrayList;
  }
  
  public Collection<Pair<Long, Long>> getSelectedRanges()
  {
    return new ArrayList();
  }
  
  public Long getSelection()
  {
    return this.selectedItem;
  }
  
  public String getSelectionDisplayString(Context paramContext)
  {
    paramContext = paramContext.getResources();
    Object localObject = this.selectedItem;
    if (localObject == null) {
      return paramContext.getString(R.string.mtrl_picker_date_header_unselected);
    }
    localObject = DateStrings.getYearMonthDay(((Long)localObject).longValue());
    Log5ECF72.a(localObject);
    LogE84000.a(localObject);
    Log229316.a(localObject);
    return paramContext.getString(R.string.mtrl_picker_date_header_selected, new Object[] { localObject });
  }
  
  public boolean isSelectionComplete()
  {
    boolean bool;
    if (this.selectedItem != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public View onCreateTextInputView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle, CalendarConstraints paramCalendarConstraints, final OnSelectionChangedListener<Long> paramOnSelectionChangedListener)
  {
    View localView = paramLayoutInflater.inflate(R.layout.mtrl_picker_text_input_date, paramViewGroup, false);
    TextInputLayout localTextInputLayout = (TextInputLayout)localView.findViewById(R.id.mtrl_picker_text_input_date);
    EditText localEditText = localTextInputLayout.getEditText();
    if (ManufacturerUtils.isDateInputKeyboardMissingSeparatorCharacters()) {
      localEditText.setInputType(17);
    }
    paramBundle = UtcDates.getTextInputFormat();
    paramLayoutInflater = UtcDates.getTextInputHint(localView.getResources(), paramBundle);
    Log5ECF72.a(paramLayoutInflater);
    LogE84000.a(paramLayoutInflater);
    Log229316.a(paramLayoutInflater);
    localTextInputLayout.setPlaceholderText(paramLayoutInflater);
    paramViewGroup = this.selectedItem;
    if (paramViewGroup != null) {
      localEditText.setText(paramBundle.format(paramViewGroup));
    }
    localEditText.addTextChangedListener(new DateFormatTextWatcher(paramLayoutInflater, paramBundle, localTextInputLayout, paramCalendarConstraints)
    {
      void onInvalidDate()
      {
        paramOnSelectionChangedListener.onIncompleteSelectionChanged();
      }
      
      void onValidDate(Long paramAnonymousLong)
      {
        if (paramAnonymousLong == null) {
          SingleDateSelector.this.clearSelection();
        } else {
          SingleDateSelector.this.select(paramAnonymousLong.longValue());
        }
        paramOnSelectionChangedListener.onSelectionChanged(SingleDateSelector.this.getSelection());
      }
    });
    ViewUtils.requestFocusAndShowKeyboard(localEditText);
    return localView;
  }
  
  public void select(long paramLong)
  {
    this.selectedItem = Long.valueOf(paramLong);
  }
  
  public void setSelection(Long paramLong)
  {
    if (paramLong == null) {
      paramLong = null;
    } else {
      paramLong = Long.valueOf(UtcDates.canonicalYearMonthDay(paramLong.longValue()));
    }
    this.selectedItem = paramLong;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeValue(this.selectedItem);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/datepicker/SingleDateSelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */