package com.google.android.material.datepicker;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.core.util.Pair;
import androidx.core.util.Preconditions;
import com.google.android.material.R.attr;
import com.google.android.material.R.dimen;
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

public class RangeDateSelector
  implements DateSelector<Pair<Long, Long>>
{
  public static final Parcelable.Creator<RangeDateSelector> CREATOR = new Parcelable.Creator()
  {
    public RangeDateSelector createFromParcel(Parcel paramAnonymousParcel)
    {
      RangeDateSelector localRangeDateSelector = new RangeDateSelector();
      RangeDateSelector.access$302(localRangeDateSelector, (Long)paramAnonymousParcel.readValue(Long.class.getClassLoader()));
      RangeDateSelector.access$402(localRangeDateSelector, (Long)paramAnonymousParcel.readValue(Long.class.getClassLoader()));
      return localRangeDateSelector;
    }
    
    public RangeDateSelector[] newArray(int paramAnonymousInt)
    {
      return new RangeDateSelector[paramAnonymousInt];
    }
  };
  private final String invalidRangeEndError = " ";
  private String invalidRangeStartError;
  private Long proposedTextEnd = null;
  private Long proposedTextStart = null;
  private Long selectedEndItem = null;
  private Long selectedStartItem = null;
  
  private void clearInvalidRange(TextInputLayout paramTextInputLayout1, TextInputLayout paramTextInputLayout2)
  {
    if ((paramTextInputLayout1.getError() != null) && (this.invalidRangeStartError.contentEquals(paramTextInputLayout1.getError()))) {
      paramTextInputLayout1.setError(null);
    }
    if ((paramTextInputLayout2.getError() != null) && (" ".contentEquals(paramTextInputLayout2.getError()))) {
      paramTextInputLayout2.setError(null);
    }
  }
  
  private boolean isValidRange(long paramLong1, long paramLong2)
  {
    boolean bool;
    if (paramLong1 <= paramLong2) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private void setInvalidRange(TextInputLayout paramTextInputLayout1, TextInputLayout paramTextInputLayout2)
  {
    paramTextInputLayout1.setError(this.invalidRangeStartError);
    paramTextInputLayout2.setError(" ");
  }
  
  private void updateIfValidTextProposal(TextInputLayout paramTextInputLayout1, TextInputLayout paramTextInputLayout2, OnSelectionChangedListener<Pair<Long, Long>> paramOnSelectionChangedListener)
  {
    Long localLong = this.proposedTextStart;
    if ((localLong != null) && (this.proposedTextEnd != null))
    {
      if (isValidRange(localLong.longValue(), this.proposedTextEnd.longValue()))
      {
        this.selectedStartItem = this.proposedTextStart;
        this.selectedEndItem = this.proposedTextEnd;
        paramOnSelectionChangedListener.onSelectionChanged(getSelection());
      }
      else
      {
        setInvalidRange(paramTextInputLayout1, paramTextInputLayout2);
        paramOnSelectionChangedListener.onIncompleteSelectionChanged();
      }
      return;
    }
    clearInvalidRange(paramTextInputLayout1, paramTextInputLayout2);
    paramOnSelectionChangedListener.onIncompleteSelectionChanged();
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public int getDefaultThemeResId(Context paramContext)
  {
    Resources localResources = paramContext.getResources();
    DisplayMetrics localDisplayMetrics = localResources.getDisplayMetrics();
    int i = localResources.getDimensionPixelSize(R.dimen.mtrl_calendar_maximum_default_fullscreen_minor_axis);
    if (Math.min(localDisplayMetrics.widthPixels, localDisplayMetrics.heightPixels) > i) {
      i = R.attr.materialCalendarTheme;
    } else {
      i = R.attr.materialCalendarFullscreenTheme;
    }
    return MaterialAttributes.resolveOrThrow(paramContext, i, MaterialDatePicker.class.getCanonicalName());
  }
  
  public int getDefaultTitleResId()
  {
    return R.string.mtrl_picker_range_header_title;
  }
  
  public Collection<Long> getSelectedDays()
  {
    ArrayList localArrayList = new ArrayList();
    Long localLong = this.selectedStartItem;
    if (localLong != null) {
      localArrayList.add(localLong);
    }
    localLong = this.selectedEndItem;
    if (localLong != null) {
      localArrayList.add(localLong);
    }
    return localArrayList;
  }
  
  public Collection<Pair<Long, Long>> getSelectedRanges()
  {
    if ((this.selectedStartItem != null) && (this.selectedEndItem != null))
    {
      ArrayList localArrayList = new ArrayList();
      localArrayList.add(new Pair(this.selectedStartItem, this.selectedEndItem));
      return localArrayList;
    }
    return new ArrayList();
  }
  
  public Pair<Long, Long> getSelection()
  {
    return new Pair(this.selectedStartItem, this.selectedEndItem);
  }
  
  public String getSelectionDisplayString(Context paramContext)
  {
    paramContext = paramContext.getResources();
    Long localLong = this.selectedStartItem;
    if ((localLong == null) && (this.selectedEndItem == null)) {
      return paramContext.getString(R.string.mtrl_picker_range_header_unselected);
    }
    Object localObject = this.selectedEndItem;
    int i;
    if (localObject == null)
    {
      i = R.string.mtrl_picker_range_header_only_start_selected;
      localObject = DateStrings.getDateString(this.selectedStartItem.longValue());
      Log5ECF72.a(localObject);
      LogE84000.a(localObject);
      Log229316.a(localObject);
      return paramContext.getString(i, new Object[] { localObject });
    }
    if (localLong == null)
    {
      i = R.string.mtrl_picker_range_header_only_end_selected;
      localObject = DateStrings.getDateString(this.selectedEndItem.longValue());
      Log5ECF72.a(localObject);
      LogE84000.a(localObject);
      Log229316.a(localObject);
      return paramContext.getString(i, new Object[] { localObject });
    }
    localObject = DateStrings.getDateRangeString(localLong, (Long)localObject);
    return paramContext.getString(R.string.mtrl_picker_range_header_selected, new Object[] { ((Pair)localObject).first, ((Pair)localObject).second });
  }
  
  public boolean isSelectionComplete()
  {
    Long localLong = this.selectedStartItem;
    boolean bool;
    if ((localLong != null) && (this.selectedEndItem != null) && (isValidRange(localLong.longValue(), this.selectedEndItem.longValue()))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public View onCreateTextInputView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, final Bundle paramBundle, CalendarConstraints paramCalendarConstraints, final OnSelectionChangedListener<Pair<Long, Long>> paramOnSelectionChangedListener)
  {
    View localView = paramLayoutInflater.inflate(R.layout.mtrl_picker_text_input_date_range, paramViewGroup, false);
    final TextInputLayout localTextInputLayout = (TextInputLayout)localView.findViewById(R.id.mtrl_picker_text_input_range_start);
    paramBundle = (TextInputLayout)localView.findViewById(R.id.mtrl_picker_text_input_range_end);
    EditText localEditText = localTextInputLayout.getEditText();
    paramViewGroup = paramBundle.getEditText();
    if (ManufacturerUtils.isDateInputKeyboardMissingSeparatorCharacters())
    {
      localEditText.setInputType(17);
      paramViewGroup.setInputType(17);
    }
    this.invalidRangeStartError = localView.getResources().getString(R.string.mtrl_picker_invalid_range);
    paramLayoutInflater = UtcDates.getTextInputFormat();
    Object localObject = this.selectedStartItem;
    if (localObject != null)
    {
      localEditText.setText(paramLayoutInflater.format(localObject));
      this.proposedTextStart = this.selectedStartItem;
    }
    localObject = this.selectedEndItem;
    if (localObject != null)
    {
      paramViewGroup.setText(paramLayoutInflater.format(localObject));
      this.proposedTextEnd = this.selectedEndItem;
    }
    localObject = UtcDates.getTextInputHint(localView.getResources(), paramLayoutInflater);
    Log5ECF72.a(localObject);
    LogE84000.a(localObject);
    Log229316.a(localObject);
    localTextInputLayout.setPlaceholderText((CharSequence)localObject);
    paramBundle.setPlaceholderText((CharSequence)localObject);
    localEditText.addTextChangedListener(new DateFormatTextWatcher((String)localObject, paramLayoutInflater, localTextInputLayout, paramCalendarConstraints)
    {
      void onInvalidDate()
      {
        RangeDateSelector.access$002(RangeDateSelector.this, null);
        RangeDateSelector.this.updateIfValidTextProposal(localTextInputLayout, paramBundle, paramOnSelectionChangedListener);
      }
      
      void onValidDate(Long paramAnonymousLong)
      {
        RangeDateSelector.access$002(RangeDateSelector.this, paramAnonymousLong);
        RangeDateSelector.this.updateIfValidTextProposal(localTextInputLayout, paramBundle, paramOnSelectionChangedListener);
      }
    });
    paramViewGroup.addTextChangedListener(new DateFormatTextWatcher((String)localObject, paramLayoutInflater, paramBundle, paramCalendarConstraints)
    {
      void onInvalidDate()
      {
        RangeDateSelector.access$202(RangeDateSelector.this, null);
        RangeDateSelector.this.updateIfValidTextProposal(localTextInputLayout, paramBundle, paramOnSelectionChangedListener);
      }
      
      void onValidDate(Long paramAnonymousLong)
      {
        RangeDateSelector.access$202(RangeDateSelector.this, paramAnonymousLong);
        RangeDateSelector.this.updateIfValidTextProposal(localTextInputLayout, paramBundle, paramOnSelectionChangedListener);
      }
    });
    ViewUtils.requestFocusAndShowKeyboard(localEditText);
    return localView;
  }
  
  public void select(long paramLong)
  {
    Long localLong = this.selectedStartItem;
    if (localLong == null)
    {
      this.selectedStartItem = Long.valueOf(paramLong);
    }
    else if ((this.selectedEndItem == null) && (isValidRange(localLong.longValue(), paramLong)))
    {
      this.selectedEndItem = Long.valueOf(paramLong);
    }
    else
    {
      this.selectedEndItem = null;
      this.selectedStartItem = Long.valueOf(paramLong);
    }
  }
  
  public void setSelection(Pair<Long, Long> paramPair)
  {
    if ((paramPair.first != null) && (paramPair.second != null)) {
      Preconditions.checkArgument(isValidRange(((Long)paramPair.first).longValue(), ((Long)paramPair.second).longValue()));
    }
    Object localObject1 = paramPair.first;
    Object localObject2 = null;
    if (localObject1 == null) {
      localObject1 = null;
    } else {
      localObject1 = Long.valueOf(UtcDates.canonicalYearMonthDay(((Long)paramPair.first).longValue()));
    }
    this.selectedStartItem = ((Long)localObject1);
    if (paramPair.second == null) {
      paramPair = (Pair<Long, Long>)localObject2;
    } else {
      paramPair = Long.valueOf(UtcDates.canonicalYearMonthDay(((Long)paramPair.second).longValue()));
    }
    this.selectedEndItem = paramPair;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeValue(this.selectedStartItem);
    paramParcel.writeValue(this.selectedEndItem);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/datepicker/RangeDateSelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */