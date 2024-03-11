package com.google.android.material.textfield;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.ListPopupWindow;
import com.google.android.material.R.attr;
import com.google.android.material.R.layout;
import com.google.android.material.R.style;
import com.google.android.material.R.styleable;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.internal.ManufacturerUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

public class MaterialAutoCompleteTextView
  extends AppCompatAutoCompleteTextView
{
  private static final int MAX_ITEMS_MEASURED = 15;
  private final AccessibilityManager accessibilityManager;
  private final ListPopupWindow modalListPopup;
  private final int simpleItemLayout;
  private final Rect tempRect = new Rect();
  
  public MaterialAutoCompleteTextView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public MaterialAutoCompleteTextView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.autoCompleteTextViewStyle);
  }
  
  public MaterialAutoCompleteTextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(MaterialThemeOverlay.wrap(paramContext, paramAttributeSet, paramInt, 0), paramAttributeSet, paramInt);
    Context localContext = getContext();
    paramContext = ThemeEnforcement.obtainStyledAttributes(localContext, paramAttributeSet, R.styleable.MaterialAutoCompleteTextView, paramInt, R.style.Widget_AppCompat_AutoCompleteTextView, new int[0]);
    if ((paramContext.hasValue(R.styleable.MaterialAutoCompleteTextView_android_inputType)) && (paramContext.getInt(R.styleable.MaterialAutoCompleteTextView_android_inputType, 0) == 0)) {
      setKeyListener(null);
    }
    this.simpleItemLayout = paramContext.getResourceId(R.styleable.MaterialAutoCompleteTextView_simpleItemLayout, R.layout.mtrl_auto_complete_simple_item);
    this.accessibilityManager = ((AccessibilityManager)localContext.getSystemService("accessibility"));
    paramAttributeSet = new ListPopupWindow(localContext);
    this.modalListPopup = paramAttributeSet;
    paramAttributeSet.setModal(true);
    paramAttributeSet.setAnchorView(this);
    paramAttributeSet.setInputMethodMode(2);
    paramAttributeSet.setAdapter(getAdapter());
    paramAttributeSet.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        paramAnonymousAdapterView = MaterialAutoCompleteTextView.this;
        if (paramAnonymousInt < 0) {
          paramAnonymousAdapterView = paramAnonymousAdapterView.modalListPopup.getSelectedItem();
        } else {
          paramAnonymousAdapterView = paramAnonymousAdapterView.getAdapter().getItem(paramAnonymousInt);
        }
        MaterialAutoCompleteTextView.this.updateText(paramAnonymousAdapterView);
        paramAnonymousAdapterView = MaterialAutoCompleteTextView.this.getOnItemClickListener();
        if (paramAnonymousAdapterView != null)
        {
          int i;
          if (paramAnonymousView != null)
          {
            i = paramAnonymousInt;
            if (paramAnonymousInt >= 0) {}
          }
          else
          {
            paramAnonymousView = MaterialAutoCompleteTextView.this.modalListPopup.getSelectedView();
            i = MaterialAutoCompleteTextView.this.modalListPopup.getSelectedItemPosition();
            paramAnonymousLong = MaterialAutoCompleteTextView.this.modalListPopup.getSelectedItemId();
          }
          paramAnonymousAdapterView.onItemClick(MaterialAutoCompleteTextView.this.modalListPopup.getListView(), paramAnonymousView, i, paramAnonymousLong);
        }
        MaterialAutoCompleteTextView.this.modalListPopup.dismiss();
      }
    });
    if (paramContext.hasValue(R.styleable.MaterialAutoCompleteTextView_simpleItems)) {
      setSimpleItems(paramContext.getResourceId(R.styleable.MaterialAutoCompleteTextView_simpleItems, 0));
    }
    paramContext.recycle();
  }
  
  private TextInputLayout findTextInputLayoutAncestor()
  {
    for (ViewParent localViewParent = getParent(); localViewParent != null; localViewParent = localViewParent.getParent()) {
      if ((localViewParent instanceof TextInputLayout)) {
        return (TextInputLayout)localViewParent;
      }
    }
    return null;
  }
  
  private int measureContentWidth()
  {
    ListAdapter localListAdapter = getAdapter();
    TextInputLayout localTextInputLayout = findTextInputLayoutAncestor();
    if ((localListAdapter != null) && (localTextInputLayout != null))
    {
      int i = 0;
      Object localObject = null;
      int m = 0;
      int i2 = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 0);
      int i1 = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 0);
      int j = Math.max(0, this.modalListPopup.getSelectedItemPosition());
      int i3 = Math.min(localListAdapter.getCount(), j + 15);
      j = Math.max(0, i3 - 15);
      while (j < i3)
      {
        int n = localListAdapter.getItemViewType(j);
        int k = m;
        if (n != m)
        {
          k = n;
          localObject = null;
        }
        localObject = localListAdapter.getView(j, (View)localObject, localTextInputLayout);
        if (((View)localObject).getLayoutParams() == null) {
          ((View)localObject).setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        }
        ((View)localObject).measure(i2, i1);
        i = Math.max(i, ((View)localObject).getMeasuredWidth());
        j++;
        m = k;
      }
      localObject = this.modalListPopup.getBackground();
      j = i;
      if (localObject != null)
      {
        ((Drawable)localObject).getPadding(this.tempRect);
        j = i + (this.tempRect.left + this.tempRect.right);
      }
      return j + localTextInputLayout.getEndIconView().getMeasuredWidth();
    }
    return 0;
  }
  
  private <T extends ListAdapter,  extends Filterable> void updateText(Object paramObject)
  {
    if (Build.VERSION.SDK_INT >= 17)
    {
      setText(convertSelectionToString(paramObject), false);
    }
    else
    {
      ListAdapter localListAdapter = getAdapter();
      setAdapter(null);
      setText(convertSelectionToString(paramObject));
      setAdapter(localListAdapter);
    }
  }
  
  public CharSequence getHint()
  {
    TextInputLayout localTextInputLayout = findTextInputLayoutAncestor();
    if ((localTextInputLayout != null) && (localTextInputLayout.isProvidingHint())) {
      return localTextInputLayout.getHint();
    }
    return super.getHint();
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    TextInputLayout localTextInputLayout = findTextInputLayoutAncestor();
    if ((localTextInputLayout != null) && (localTextInputLayout.isProvidingHint()) && (super.getHint() == null) && (ManufacturerUtils.isMeizuDevice())) {
      setHint("");
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    if (View.MeasureSpec.getMode(paramInt1) == Integer.MIN_VALUE)
    {
      paramInt2 = getMeasuredWidth();
      setMeasuredDimension(Math.min(Math.max(paramInt2, measureContentWidth()), View.MeasureSpec.getSize(paramInt1)), getMeasuredHeight());
    }
  }
  
  public <T extends ListAdapter,  extends Filterable> void setAdapter(T paramT)
  {
    super.setAdapter(paramT);
    this.modalListPopup.setAdapter(getAdapter());
  }
  
  public void setSimpleItems(int paramInt)
  {
    setSimpleItems(getResources().getStringArray(paramInt));
  }
  
  public void setSimpleItems(String[] paramArrayOfString)
  {
    setAdapter(new ArrayAdapter(getContext(), this.simpleItemLayout, paramArrayOfString));
  }
  
  public void showDropDown()
  {
    AccessibilityManager localAccessibilityManager = this.accessibilityManager;
    if ((localAccessibilityManager != null) && (localAccessibilityManager.isTouchExplorationEnabled())) {
      this.modalListPopup.show();
    } else {
      super.showDropDown();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/textfield/MaterialAutoCompleteTextView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */