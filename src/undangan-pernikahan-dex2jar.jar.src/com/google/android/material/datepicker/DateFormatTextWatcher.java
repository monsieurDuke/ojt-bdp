package com.google.android.material.datepicker;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import com.google.android.material.R.string;
import com.google.android.material.internal.TextWatcherAdapter;
import com.google.android.material.textfield.TextInputLayout;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

abstract class DateFormatTextWatcher
  extends TextWatcherAdapter
{
  private static final int VALIDATION_DELAY = 1000;
  private final CalendarConstraints constraints;
  private final DateFormat dateFormat;
  private final String outOfRange;
  private final Runnable setErrorCallback;
  private Runnable setRangeErrorCallback;
  private final TextInputLayout textInputLayout;
  
  DateFormatTextWatcher(final String paramString, DateFormat paramDateFormat, TextInputLayout paramTextInputLayout, CalendarConstraints paramCalendarConstraints)
  {
    this.dateFormat = paramDateFormat;
    this.textInputLayout = paramTextInputLayout;
    this.constraints = paramCalendarConstraints;
    this.outOfRange = paramTextInputLayout.getContext().getString(R.string.mtrl_picker_out_of_range);
    this.setErrorCallback = new Runnable()
    {
      public void run()
      {
        TextInputLayout localTextInputLayout = DateFormatTextWatcher.this.textInputLayout;
        DateFormat localDateFormat = DateFormatTextWatcher.this.dateFormat;
        Object localObject = localTextInputLayout.getContext();
        String str1 = ((Context)localObject).getString(R.string.mtrl_picker_invalid_format);
        String str2 = String.format(((Context)localObject).getString(R.string.mtrl_picker_invalid_format_use), new Object[] { paramString });
        Log5ECF72.a(str2);
        LogE84000.a(str2);
        Log229316.a(str2);
        localObject = String.format(((Context)localObject).getString(R.string.mtrl_picker_invalid_format_example), new Object[] { localDateFormat.format(new Date(UtcDates.getTodayCalendar().getTimeInMillis())) });
        Log5ECF72.a(localObject);
        LogE84000.a(localObject);
        Log229316.a(localObject);
        localTextInputLayout.setError(str1 + "\n" + str2 + "\n" + (String)localObject);
        DateFormatTextWatcher.this.onInvalidDate();
      }
    };
  }
  
  private Runnable createRangeErrorCallback(final long paramLong)
  {
    new Runnable()
    {
      public void run()
      {
        TextInputLayout localTextInputLayout = DateFormatTextWatcher.this.textInputLayout;
        String str1 = DateFormatTextWatcher.this.outOfRange;
        Log5ECF72.a(str1);
        LogE84000.a(str1);
        Log229316.a(str1);
        String str2 = DateStrings.getDateString(paramLong);
        Log5ECF72.a(str2);
        LogE84000.a(str2);
        Log229316.a(str2);
        str1 = String.format(str1, new Object[] { str2 });
        Log5ECF72.a(str1);
        LogE84000.a(str1);
        Log229316.a(str1);
        localTextInputLayout.setError(str1);
        DateFormatTextWatcher.this.onInvalidDate();
      }
    };
  }
  
  void onInvalidDate() {}
  
  public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
    this.textInputLayout.removeCallbacks(this.setErrorCallback);
    this.textInputLayout.removeCallbacks(this.setRangeErrorCallback);
    this.textInputLayout.setError(null);
    onValidDate(null);
    if (TextUtils.isEmpty(paramCharSequence)) {
      return;
    }
    try
    {
      paramCharSequence = this.dateFormat.parse(paramCharSequence.toString());
      this.textInputLayout.setError(null);
      long l = paramCharSequence.getTime();
      if ((this.constraints.getDateValidator().isValid(l)) && (this.constraints.isWithinBounds(l)))
      {
        onValidDate(Long.valueOf(paramCharSequence.getTime()));
        return;
      }
      paramCharSequence = createRangeErrorCallback(l);
      this.setRangeErrorCallback = paramCharSequence;
      runValidation(this.textInputLayout, paramCharSequence);
    }
    catch (ParseException paramCharSequence)
    {
      runValidation(this.textInputLayout, this.setErrorCallback);
    }
  }
  
  abstract void onValidDate(Long paramLong);
  
  public void runValidation(View paramView, Runnable paramRunnable)
  {
    paramView.postDelayed(paramRunnable, 1000L);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/datepicker/DateFormatTextWatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */