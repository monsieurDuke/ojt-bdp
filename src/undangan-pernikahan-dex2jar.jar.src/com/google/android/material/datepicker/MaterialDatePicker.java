package com.google.android.material.datepicker;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.Insets;
import androidx.core.util.Pair;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsCompat.Type;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.R.attr;
import com.google.android.material.R.dimen;
import com.google.android.material.R.drawable;
import com.google.android.material.R.id;
import com.google.android.material.R.layout;
import com.google.android.material.R.string;
import com.google.android.material.R.style;
import com.google.android.material.dialog.InsetDialogOnTouchListener;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.internal.EdgeToEdgeUtils;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.shape.MaterialShapeDrawable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public final class MaterialDatePicker<S>
  extends DialogFragment
{
  private static final String CALENDAR_CONSTRAINTS_KEY = "CALENDAR_CONSTRAINTS_KEY";
  static final Object CANCEL_BUTTON_TAG = "CANCEL_BUTTON_TAG";
  static final Object CONFIRM_BUTTON_TAG = "CONFIRM_BUTTON_TAG";
  private static final String DATE_SELECTOR_KEY = "DATE_SELECTOR_KEY";
  public static final int INPUT_MODE_CALENDAR = 0;
  private static final String INPUT_MODE_KEY = "INPUT_MODE_KEY";
  public static final int INPUT_MODE_TEXT = 1;
  private static final String NEGATIVE_BUTTON_TEXT_KEY = "NEGATIVE_BUTTON_TEXT_KEY";
  private static final String NEGATIVE_BUTTON_TEXT_RES_ID_KEY = "NEGATIVE_BUTTON_TEXT_RES_ID_KEY";
  private static final String OVERRIDE_THEME_RES_ID = "OVERRIDE_THEME_RES_ID";
  private static final String POSITIVE_BUTTON_TEXT_KEY = "POSITIVE_BUTTON_TEXT_KEY";
  private static final String POSITIVE_BUTTON_TEXT_RES_ID_KEY = "POSITIVE_BUTTON_TEXT_RES_ID_KEY";
  private static final String TITLE_TEXT_KEY = "TITLE_TEXT_KEY";
  private static final String TITLE_TEXT_RES_ID_KEY = "TITLE_TEXT_RES_ID_KEY";
  static final Object TOGGLE_BUTTON_TAG = "TOGGLE_BUTTON_TAG";
  private MaterialShapeDrawable background;
  private MaterialCalendar<S> calendar;
  private CalendarConstraints calendarConstraints;
  private Button confirmButton;
  private DateSelector<S> dateSelector;
  private boolean edgeToEdgeEnabled;
  private boolean fullscreen;
  private TextView headerSelectionText;
  private CheckableImageButton headerToggleButton;
  private int inputMode;
  private CharSequence negativeButtonText;
  private int negativeButtonTextResId;
  private final LinkedHashSet<DialogInterface.OnCancelListener> onCancelListeners = new LinkedHashSet();
  private final LinkedHashSet<DialogInterface.OnDismissListener> onDismissListeners = new LinkedHashSet();
  private final LinkedHashSet<View.OnClickListener> onNegativeButtonClickListeners = new LinkedHashSet();
  private final LinkedHashSet<MaterialPickerOnPositiveButtonClickListener<? super S>> onPositiveButtonClickListeners = new LinkedHashSet();
  private int overrideThemeResId;
  private PickerFragment<S> pickerFragment;
  private CharSequence positiveButtonText;
  private int positiveButtonTextResId;
  private CharSequence titleText;
  private int titleTextResId;
  
  private static Drawable createHeaderToggleDrawable(Context paramContext)
  {
    StateListDrawable localStateListDrawable = new StateListDrawable();
    Drawable localDrawable = AppCompatResources.getDrawable(paramContext, R.drawable.material_ic_calendar_black_24dp);
    localStateListDrawable.addState(new int[] { 16842912 }, localDrawable);
    paramContext = AppCompatResources.getDrawable(paramContext, R.drawable.material_ic_edit_black_24dp);
    localStateListDrawable.addState(new int[0], paramContext);
    return localStateListDrawable;
  }
  
  private void enableEdgeToEdgeIfNeeded(Window paramWindow)
  {
    if (this.edgeToEdgeEnabled) {
      return;
    }
    final View localView = requireView().findViewById(R.id.fullscreen_header);
    EdgeToEdgeUtils.applyEdgeToEdge(paramWindow, true, ViewUtils.getBackgroundColor(localView), null);
    final int i = localView.getPaddingTop();
    ViewCompat.setOnApplyWindowInsetsListener(localView, new OnApplyWindowInsetsListener()
    {
      public WindowInsetsCompat onApplyWindowInsets(View paramAnonymousView, WindowInsetsCompat paramAnonymousWindowInsetsCompat)
      {
        int i = paramAnonymousWindowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars()).top;
        if (this.val$originalHeaderHeight >= 0)
        {
          localView.getLayoutParams().height = (this.val$originalHeaderHeight + i);
          paramAnonymousView = localView;
          paramAnonymousView.setLayoutParams(paramAnonymousView.getLayoutParams());
        }
        paramAnonymousView = localView;
        paramAnonymousView.setPadding(paramAnonymousView.getPaddingLeft(), i + i, localView.getPaddingRight(), localView.getPaddingBottom());
        return paramAnonymousWindowInsetsCompat;
      }
    });
    this.edgeToEdgeEnabled = true;
  }
  
  private DateSelector<S> getDateSelector()
  {
    if (this.dateSelector == null) {
      this.dateSelector = ((DateSelector)getArguments().getParcelable("DATE_SELECTOR_KEY"));
    }
    return this.dateSelector;
  }
  
  private static int getPaddedPickerWidth(Context paramContext)
  {
    paramContext = paramContext.getResources();
    int j = paramContext.getDimensionPixelOffset(R.dimen.mtrl_calendar_content_padding);
    int i = Month.current().daysInWeek;
    return j * 2 + i * paramContext.getDimensionPixelSize(R.dimen.mtrl_calendar_day_width) + (i - 1) * paramContext.getDimensionPixelOffset(R.dimen.mtrl_calendar_month_horizontal_padding);
  }
  
  private int getThemeResId(Context paramContext)
  {
    int i = this.overrideThemeResId;
    if (i != 0) {
      return i;
    }
    return getDateSelector().getDefaultThemeResId(paramContext);
  }
  
  private void initHeaderToggle(Context paramContext)
  {
    this.headerToggleButton.setTag(TOGGLE_BUTTON_TAG);
    this.headerToggleButton.setImageDrawable(createHeaderToggleDrawable(paramContext));
    paramContext = this.headerToggleButton;
    boolean bool;
    if (this.inputMode != 0) {
      bool = true;
    } else {
      bool = false;
    }
    paramContext.setChecked(bool);
    ViewCompat.setAccessibilityDelegate(this.headerToggleButton, null);
    updateToggleContentDescription(this.headerToggleButton);
    this.headerToggleButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        MaterialDatePicker.this.confirmButton.setEnabled(MaterialDatePicker.this.getDateSelector().isSelectionComplete());
        MaterialDatePicker.this.headerToggleButton.toggle();
        paramAnonymousView = MaterialDatePicker.this;
        paramAnonymousView.updateToggleContentDescription(paramAnonymousView.headerToggleButton);
        MaterialDatePicker.this.startPickerFragment();
      }
    });
  }
  
  static boolean isFullscreen(Context paramContext)
  {
    return readMaterialCalendarStyleBoolean(paramContext, 16843277);
  }
  
  static boolean isNestedScrollable(Context paramContext)
  {
    return readMaterialCalendarStyleBoolean(paramContext, R.attr.nestedScrollable);
  }
  
  static <S> MaterialDatePicker<S> newInstance(Builder<S> paramBuilder)
  {
    MaterialDatePicker localMaterialDatePicker = new MaterialDatePicker();
    Bundle localBundle = new Bundle();
    localBundle.putInt("OVERRIDE_THEME_RES_ID", paramBuilder.overrideThemeResId);
    localBundle.putParcelable("DATE_SELECTOR_KEY", paramBuilder.dateSelector);
    localBundle.putParcelable("CALENDAR_CONSTRAINTS_KEY", paramBuilder.calendarConstraints);
    localBundle.putInt("TITLE_TEXT_RES_ID_KEY", paramBuilder.titleTextResId);
    localBundle.putCharSequence("TITLE_TEXT_KEY", paramBuilder.titleText);
    localBundle.putInt("INPUT_MODE_KEY", paramBuilder.inputMode);
    localBundle.putInt("POSITIVE_BUTTON_TEXT_RES_ID_KEY", paramBuilder.positiveButtonTextResId);
    localBundle.putCharSequence("POSITIVE_BUTTON_TEXT_KEY", paramBuilder.positiveButtonText);
    localBundle.putInt("NEGATIVE_BUTTON_TEXT_RES_ID_KEY", paramBuilder.negativeButtonTextResId);
    localBundle.putCharSequence("NEGATIVE_BUTTON_TEXT_KEY", paramBuilder.negativeButtonText);
    localMaterialDatePicker.setArguments(localBundle);
    return localMaterialDatePicker;
  }
  
  static boolean readMaterialCalendarStyleBoolean(Context paramContext, int paramInt)
  {
    paramContext = paramContext.obtainStyledAttributes(MaterialAttributes.resolveOrThrow(paramContext, R.attr.materialCalendarStyle, MaterialCalendar.class.getCanonicalName()), new int[] { paramInt });
    boolean bool = paramContext.getBoolean(0, false);
    paramContext.recycle();
    return bool;
  }
  
  private void startPickerFragment()
  {
    int i = getThemeResId(requireContext());
    this.calendar = MaterialCalendar.newInstance(getDateSelector(), i, this.calendarConstraints);
    if (this.headerToggleButton.isChecked()) {
      localObject = MaterialTextInputPicker.newInstance(getDateSelector(), i, this.calendarConstraints);
    } else {
      localObject = this.calendar;
    }
    this.pickerFragment = ((PickerFragment)localObject);
    updateHeader();
    Object localObject = getChildFragmentManager().beginTransaction();
    ((FragmentTransaction)localObject).replace(R.id.mtrl_calendar_frame, this.pickerFragment);
    ((FragmentTransaction)localObject).commitNow();
    this.pickerFragment.addOnSelectionChangedListener(new OnSelectionChangedListener()
    {
      public void onIncompleteSelectionChanged()
      {
        MaterialDatePicker.this.confirmButton.setEnabled(false);
      }
      
      public void onSelectionChanged(S paramAnonymousS)
      {
        MaterialDatePicker.this.updateHeader();
        MaterialDatePicker.this.confirmButton.setEnabled(MaterialDatePicker.this.getDateSelector().isSelectionComplete());
      }
    });
  }
  
  public static long thisMonthInUtcMilliseconds()
  {
    return Month.current().timeInMillis;
  }
  
  public static long todayInUtcMilliseconds()
  {
    return UtcDates.getTodayCalendar().getTimeInMillis();
  }
  
  private void updateHeader()
  {
    String str2 = getHeaderText();
    TextView localTextView = this.headerSelectionText;
    String str1 = String.format(getString(R.string.mtrl_picker_announce_current_selection), new Object[] { str2 });
    Log5ECF72.a(str1);
    LogE84000.a(str1);
    Log229316.a(str1);
    localTextView.setContentDescription(str1);
    this.headerSelectionText.setText(str2);
  }
  
  private void updateToggleContentDescription(CheckableImageButton paramCheckableImageButton)
  {
    if (this.headerToggleButton.isChecked()) {
      paramCheckableImageButton = paramCheckableImageButton.getContext().getString(R.string.mtrl_picker_toggle_to_calendar_input_mode);
    } else {
      paramCheckableImageButton = paramCheckableImageButton.getContext().getString(R.string.mtrl_picker_toggle_to_text_input_mode);
    }
    this.headerToggleButton.setContentDescription(paramCheckableImageButton);
  }
  
  public boolean addOnCancelListener(DialogInterface.OnCancelListener paramOnCancelListener)
  {
    return this.onCancelListeners.add(paramOnCancelListener);
  }
  
  public boolean addOnDismissListener(DialogInterface.OnDismissListener paramOnDismissListener)
  {
    return this.onDismissListeners.add(paramOnDismissListener);
  }
  
  public boolean addOnNegativeButtonClickListener(View.OnClickListener paramOnClickListener)
  {
    return this.onNegativeButtonClickListeners.add(paramOnClickListener);
  }
  
  public boolean addOnPositiveButtonClickListener(MaterialPickerOnPositiveButtonClickListener<? super S> paramMaterialPickerOnPositiveButtonClickListener)
  {
    return this.onPositiveButtonClickListeners.add(paramMaterialPickerOnPositiveButtonClickListener);
  }
  
  public void clearOnCancelListeners()
  {
    this.onCancelListeners.clear();
  }
  
  public void clearOnDismissListeners()
  {
    this.onDismissListeners.clear();
  }
  
  public void clearOnNegativeButtonClickListeners()
  {
    this.onNegativeButtonClickListeners.clear();
  }
  
  public void clearOnPositiveButtonClickListeners()
  {
    this.onPositiveButtonClickListeners.clear();
  }
  
  public String getHeaderText()
  {
    return getDateSelector().getSelectionDisplayString(getContext());
  }
  
  public final S getSelection()
  {
    return (S)getDateSelector().getSelection();
  }
  
  public final void onCancel(DialogInterface paramDialogInterface)
  {
    Iterator localIterator = this.onCancelListeners.iterator();
    while (localIterator.hasNext()) {
      ((DialogInterface.OnCancelListener)localIterator.next()).onCancel(paramDialogInterface);
    }
    super.onCancel(paramDialogInterface);
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle == null) {
      paramBundle = getArguments();
    }
    this.overrideThemeResId = paramBundle.getInt("OVERRIDE_THEME_RES_ID");
    this.dateSelector = ((DateSelector)paramBundle.getParcelable("DATE_SELECTOR_KEY"));
    this.calendarConstraints = ((CalendarConstraints)paramBundle.getParcelable("CALENDAR_CONSTRAINTS_KEY"));
    this.titleTextResId = paramBundle.getInt("TITLE_TEXT_RES_ID_KEY");
    this.titleText = paramBundle.getCharSequence("TITLE_TEXT_KEY");
    this.inputMode = paramBundle.getInt("INPUT_MODE_KEY");
    this.positiveButtonTextResId = paramBundle.getInt("POSITIVE_BUTTON_TEXT_RES_ID_KEY");
    this.positiveButtonText = paramBundle.getCharSequence("POSITIVE_BUTTON_TEXT_KEY");
    this.negativeButtonTextResId = paramBundle.getInt("NEGATIVE_BUTTON_TEXT_RES_ID_KEY");
    this.negativeButtonText = paramBundle.getCharSequence("NEGATIVE_BUTTON_TEXT_KEY");
  }
  
  public final Dialog onCreateDialog(Bundle paramBundle)
  {
    Dialog localDialog = new Dialog(requireContext(), getThemeResId(requireContext()));
    Context localContext = localDialog.getContext();
    this.fullscreen = isFullscreen(localContext);
    int i = MaterialAttributes.resolveOrThrow(localContext, R.attr.colorSurface, MaterialDatePicker.class.getCanonicalName());
    paramBundle = new MaterialShapeDrawable(localContext, null, R.attr.materialCalendarStyle, R.style.Widget_MaterialComponents_MaterialCalendar);
    this.background = paramBundle;
    paramBundle.initializeElevationOverlay(localContext);
    this.background.setFillColor(ColorStateList.valueOf(i));
    this.background.setElevation(ViewCompat.getElevation(localDialog.getWindow().getDecorView()));
    return localDialog;
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    int i;
    if (this.fullscreen) {
      i = R.layout.mtrl_picker_fullscreen;
    } else {
      i = R.layout.mtrl_picker_dialog;
    }
    paramLayoutInflater = paramLayoutInflater.inflate(i, paramViewGroup);
    paramViewGroup = paramLayoutInflater.getContext();
    if (this.fullscreen) {
      paramLayoutInflater.findViewById(R.id.mtrl_calendar_frame).setLayoutParams(new LinearLayout.LayoutParams(getPaddedPickerWidth(paramViewGroup), -2));
    } else {
      paramLayoutInflater.findViewById(R.id.mtrl_calendar_main_pane).setLayoutParams(new LinearLayout.LayoutParams(getPaddedPickerWidth(paramViewGroup), -1));
    }
    paramBundle = (TextView)paramLayoutInflater.findViewById(R.id.mtrl_picker_header_selection_text);
    this.headerSelectionText = paramBundle;
    ViewCompat.setAccessibilityLiveRegion(paramBundle, 1);
    this.headerToggleButton = ((CheckableImageButton)paramLayoutInflater.findViewById(R.id.mtrl_picker_header_toggle));
    TextView localTextView = (TextView)paramLayoutInflater.findViewById(R.id.mtrl_picker_title_text);
    paramBundle = this.titleText;
    if (paramBundle != null) {
      localTextView.setText(paramBundle);
    } else {
      localTextView.setText(this.titleTextResId);
    }
    initHeaderToggle(paramViewGroup);
    this.confirmButton = ((Button)paramLayoutInflater.findViewById(R.id.confirm_button));
    if (getDateSelector().isSelectionComplete()) {
      this.confirmButton.setEnabled(true);
    } else {
      this.confirmButton.setEnabled(false);
    }
    this.confirmButton.setTag(CONFIRM_BUTTON_TAG);
    paramViewGroup = this.positiveButtonText;
    if (paramViewGroup != null)
    {
      this.confirmButton.setText(paramViewGroup);
    }
    else
    {
      i = this.positiveButtonTextResId;
      if (i != 0) {
        this.confirmButton.setText(i);
      }
    }
    this.confirmButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        paramAnonymousView = MaterialDatePicker.this.onPositiveButtonClickListeners.iterator();
        while (paramAnonymousView.hasNext()) {
          ((MaterialPickerOnPositiveButtonClickListener)paramAnonymousView.next()).onPositiveButtonClick(MaterialDatePicker.this.getSelection());
        }
        MaterialDatePicker.this.dismiss();
      }
    });
    paramViewGroup = (Button)paramLayoutInflater.findViewById(R.id.cancel_button);
    paramViewGroup.setTag(CANCEL_BUTTON_TAG);
    paramBundle = this.negativeButtonText;
    if (paramBundle != null)
    {
      paramViewGroup.setText(paramBundle);
    }
    else
    {
      i = this.negativeButtonTextResId;
      if (i != 0) {
        paramViewGroup.setText(i);
      }
    }
    paramViewGroup.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Iterator localIterator = MaterialDatePicker.this.onNegativeButtonClickListeners.iterator();
        while (localIterator.hasNext()) {
          ((View.OnClickListener)localIterator.next()).onClick(paramAnonymousView);
        }
        MaterialDatePicker.this.dismiss();
      }
    });
    return paramLayoutInflater;
  }
  
  public final void onDismiss(DialogInterface paramDialogInterface)
  {
    Object localObject = this.onDismissListeners.iterator();
    while (((Iterator)localObject).hasNext()) {
      ((DialogInterface.OnDismissListener)((Iterator)localObject).next()).onDismiss(paramDialogInterface);
    }
    localObject = (ViewGroup)getView();
    if (localObject != null) {
      ((ViewGroup)localObject).removeAllViews();
    }
    super.onDismiss(paramDialogInterface);
  }
  
  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putInt("OVERRIDE_THEME_RES_ID", this.overrideThemeResId);
    paramBundle.putParcelable("DATE_SELECTOR_KEY", this.dateSelector);
    CalendarConstraints.Builder localBuilder = new CalendarConstraints.Builder(this.calendarConstraints);
    if (this.calendar.getCurrentMonth() != null) {
      localBuilder.setOpenAt(this.calendar.getCurrentMonth().timeInMillis);
    }
    paramBundle.putParcelable("CALENDAR_CONSTRAINTS_KEY", localBuilder.build());
    paramBundle.putInt("TITLE_TEXT_RES_ID_KEY", this.titleTextResId);
    paramBundle.putCharSequence("TITLE_TEXT_KEY", this.titleText);
    paramBundle.putInt("POSITIVE_BUTTON_TEXT_RES_ID_KEY", this.positiveButtonTextResId);
    paramBundle.putCharSequence("POSITIVE_BUTTON_TEXT_KEY", this.positiveButtonText);
    paramBundle.putInt("NEGATIVE_BUTTON_TEXT_RES_ID_KEY", this.negativeButtonTextResId);
    paramBundle.putCharSequence("NEGATIVE_BUTTON_TEXT_KEY", this.negativeButtonText);
  }
  
  public void onStart()
  {
    super.onStart();
    Window localWindow = requireDialog().getWindow();
    if (this.fullscreen)
    {
      localWindow.setLayout(-1, -1);
      localWindow.setBackgroundDrawable(this.background);
      enableEdgeToEdgeIfNeeded(localWindow);
    }
    else
    {
      localWindow.setLayout(-2, -2);
      int i = getResources().getDimensionPixelOffset(R.dimen.mtrl_calendar_dialog_background_inset);
      Rect localRect = new Rect(i, i, i, i);
      localWindow.setBackgroundDrawable(new InsetDrawable(this.background, i, i, i, i));
      localWindow.getDecorView().setOnTouchListener(new InsetDialogOnTouchListener(requireDialog(), localRect));
    }
    startPickerFragment();
  }
  
  public void onStop()
  {
    this.pickerFragment.clearOnSelectionChangedListeners();
    super.onStop();
  }
  
  public boolean removeOnCancelListener(DialogInterface.OnCancelListener paramOnCancelListener)
  {
    return this.onCancelListeners.remove(paramOnCancelListener);
  }
  
  public boolean removeOnDismissListener(DialogInterface.OnDismissListener paramOnDismissListener)
  {
    return this.onDismissListeners.remove(paramOnDismissListener);
  }
  
  public boolean removeOnNegativeButtonClickListener(View.OnClickListener paramOnClickListener)
  {
    return this.onNegativeButtonClickListeners.remove(paramOnClickListener);
  }
  
  public boolean removeOnPositiveButtonClickListener(MaterialPickerOnPositiveButtonClickListener<? super S> paramMaterialPickerOnPositiveButtonClickListener)
  {
    return this.onPositiveButtonClickListeners.remove(paramMaterialPickerOnPositiveButtonClickListener);
  }
  
  public static final class Builder<S>
  {
    CalendarConstraints calendarConstraints;
    final DateSelector<S> dateSelector;
    int inputMode = 0;
    CharSequence negativeButtonText = null;
    int negativeButtonTextResId = 0;
    int overrideThemeResId = 0;
    CharSequence positiveButtonText = null;
    int positiveButtonTextResId = 0;
    S selection = null;
    CharSequence titleText = null;
    int titleTextResId = 0;
    
    private Builder(DateSelector<S> paramDateSelector)
    {
      this.dateSelector = paramDateSelector;
    }
    
    private Month createDefaultOpenAt()
    {
      if (!this.dateSelector.getSelectedDays().isEmpty())
      {
        localMonth = Month.create(((Long)this.dateSelector.getSelectedDays().iterator().next()).longValue());
        if (monthInValidRange(localMonth, this.calendarConstraints)) {
          return localMonth;
        }
      }
      Month localMonth = Month.current();
      if (!monthInValidRange(localMonth, this.calendarConstraints)) {
        localMonth = this.calendarConstraints.getStart();
      }
      return localMonth;
    }
    
    public static <S> Builder<S> customDatePicker(DateSelector<S> paramDateSelector)
    {
      return new Builder(paramDateSelector);
    }
    
    public static Builder<Long> datePicker()
    {
      return new Builder(new SingleDateSelector());
    }
    
    public static Builder<Pair<Long, Long>> dateRangePicker()
    {
      return new Builder(new RangeDateSelector());
    }
    
    private static boolean monthInValidRange(Month paramMonth, CalendarConstraints paramCalendarConstraints)
    {
      boolean bool;
      if ((paramMonth.compareTo(paramCalendarConstraints.getStart()) >= 0) && (paramMonth.compareTo(paramCalendarConstraints.getEnd()) <= 0)) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public MaterialDatePicker<S> build()
    {
      if (this.calendarConstraints == null) {
        this.calendarConstraints = new CalendarConstraints.Builder().build();
      }
      if (this.titleTextResId == 0) {
        this.titleTextResId = this.dateSelector.getDefaultTitleResId();
      }
      Object localObject = this.selection;
      if (localObject != null) {
        this.dateSelector.setSelection(localObject);
      }
      if (this.calendarConstraints.getOpenAt() == null) {
        this.calendarConstraints.setOpenAt(createDefaultOpenAt());
      }
      return MaterialDatePicker.newInstance(this);
    }
    
    public Builder<S> setCalendarConstraints(CalendarConstraints paramCalendarConstraints)
    {
      this.calendarConstraints = paramCalendarConstraints;
      return this;
    }
    
    public Builder<S> setInputMode(int paramInt)
    {
      this.inputMode = paramInt;
      return this;
    }
    
    public Builder<S> setNegativeButtonText(int paramInt)
    {
      this.negativeButtonTextResId = paramInt;
      this.negativeButtonText = null;
      return this;
    }
    
    public Builder<S> setNegativeButtonText(CharSequence paramCharSequence)
    {
      this.negativeButtonText = paramCharSequence;
      this.negativeButtonTextResId = 0;
      return this;
    }
    
    public Builder<S> setPositiveButtonText(int paramInt)
    {
      this.positiveButtonTextResId = paramInt;
      this.positiveButtonText = null;
      return this;
    }
    
    public Builder<S> setPositiveButtonText(CharSequence paramCharSequence)
    {
      this.positiveButtonText = paramCharSequence;
      this.positiveButtonTextResId = 0;
      return this;
    }
    
    public Builder<S> setSelection(S paramS)
    {
      this.selection = paramS;
      return this;
    }
    
    public Builder<S> setTheme(int paramInt)
    {
      this.overrideThemeResId = paramInt;
      return this;
    }
    
    public Builder<S> setTitleText(int paramInt)
    {
      this.titleTextResId = paramInt;
      this.titleText = null;
      return this;
    }
    
    public Builder<S> setTitleText(CharSequence paramCharSequence)
    {
      this.titleText = paramCharSequence;
      this.titleTextResId = 0;
      return this;
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface InputMode {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/datepicker/MaterialDatePicker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */