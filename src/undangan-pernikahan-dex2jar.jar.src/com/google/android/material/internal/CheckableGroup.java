package com.google.android.material.internal;

import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CheckableGroup<T extends MaterialCheckable<T>>
{
  private final Map<Integer, T> checkables = new HashMap();
  private final Set<Integer> checkedIds = new HashSet();
  private OnCheckedStateChangeListener onCheckedStateChangeListener;
  private boolean selectionRequired;
  private boolean singleSelection;
  
  private boolean checkInternal(MaterialCheckable<T> paramMaterialCheckable)
  {
    int i = paramMaterialCheckable.getId();
    if (this.checkedIds.contains(Integer.valueOf(i))) {
      return false;
    }
    MaterialCheckable localMaterialCheckable = (MaterialCheckable)this.checkables.get(Integer.valueOf(getSingleCheckedId()));
    if (localMaterialCheckable != null) {
      uncheckInternal(localMaterialCheckable, false);
    }
    boolean bool = this.checkedIds.add(Integer.valueOf(i));
    if (!paramMaterialCheckable.isChecked()) {
      paramMaterialCheckable.setChecked(true);
    }
    return bool;
  }
  
  private void onCheckedStateChanged()
  {
    OnCheckedStateChangeListener localOnCheckedStateChangeListener = this.onCheckedStateChangeListener;
    if (localOnCheckedStateChangeListener != null) {
      localOnCheckedStateChangeListener.onCheckedStateChanged(getCheckedIds());
    }
  }
  
  private boolean uncheckInternal(MaterialCheckable<T> paramMaterialCheckable, boolean paramBoolean)
  {
    int i = paramMaterialCheckable.getId();
    if (!this.checkedIds.contains(Integer.valueOf(i))) {
      return false;
    }
    if ((paramBoolean) && (this.checkedIds.size() == 1) && (this.checkedIds.contains(Integer.valueOf(i))))
    {
      paramMaterialCheckable.setChecked(true);
      return false;
    }
    paramBoolean = this.checkedIds.remove(Integer.valueOf(i));
    if (paramMaterialCheckable.isChecked()) {
      paramMaterialCheckable.setChecked(false);
    }
    return paramBoolean;
  }
  
  public void addCheckable(T paramT)
  {
    this.checkables.put(Integer.valueOf(paramT.getId()), paramT);
    if (paramT.isChecked()) {
      checkInternal(paramT);
    }
    paramT.setInternalOnCheckedChangeListener(new MaterialCheckable.OnCheckedChangeListener()
    {
      public void onCheckedChanged(T paramAnonymousT, boolean paramAnonymousBoolean)
      {
        CheckableGroup localCheckableGroup = CheckableGroup.this;
        if (paramAnonymousBoolean ? localCheckableGroup.checkInternal(paramAnonymousT) : localCheckableGroup.uncheckInternal(paramAnonymousT, localCheckableGroup.selectionRequired)) {
          CheckableGroup.this.onCheckedStateChanged();
        }
      }
    });
  }
  
  public void check(int paramInt)
  {
    MaterialCheckable localMaterialCheckable = (MaterialCheckable)this.checkables.get(Integer.valueOf(paramInt));
    if (localMaterialCheckable == null) {
      return;
    }
    if (checkInternal(localMaterialCheckable)) {
      onCheckedStateChanged();
    }
  }
  
  public void clearCheck()
  {
    boolean bool = this.checkedIds.isEmpty();
    Iterator localIterator = this.checkables.values().iterator();
    while (localIterator.hasNext()) {
      uncheckInternal((MaterialCheckable)localIterator.next(), false);
    }
    if ((bool ^ true)) {
      onCheckedStateChanged();
    }
  }
  
  public Set<Integer> getCheckedIds()
  {
    return new HashSet(this.checkedIds);
  }
  
  public List<Integer> getCheckedIdsSortedByChildOrder(ViewGroup paramViewGroup)
  {
    Set localSet = getCheckedIds();
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; i < paramViewGroup.getChildCount(); i++)
    {
      View localView = paramViewGroup.getChildAt(i);
      if (((localView instanceof MaterialCheckable)) && (localSet.contains(Integer.valueOf(localView.getId())))) {
        localArrayList.add(Integer.valueOf(localView.getId()));
      }
    }
    return localArrayList;
  }
  
  public int getSingleCheckedId()
  {
    int i;
    if ((this.singleSelection) && (!this.checkedIds.isEmpty())) {
      i = ((Integer)this.checkedIds.iterator().next()).intValue();
    } else {
      i = -1;
    }
    return i;
  }
  
  public boolean isSelectionRequired()
  {
    return this.selectionRequired;
  }
  
  public boolean isSingleSelection()
  {
    return this.singleSelection;
  }
  
  public void removeCheckable(T paramT)
  {
    paramT.setInternalOnCheckedChangeListener(null);
    this.checkables.remove(Integer.valueOf(paramT.getId()));
    this.checkedIds.remove(Integer.valueOf(paramT.getId()));
  }
  
  public void setOnCheckedStateChangeListener(OnCheckedStateChangeListener paramOnCheckedStateChangeListener)
  {
    this.onCheckedStateChangeListener = paramOnCheckedStateChangeListener;
  }
  
  public void setSelectionRequired(boolean paramBoolean)
  {
    this.selectionRequired = paramBoolean;
  }
  
  public void setSingleSelection(boolean paramBoolean)
  {
    if (this.singleSelection != paramBoolean)
    {
      this.singleSelection = paramBoolean;
      clearCheck();
    }
  }
  
  public void uncheck(int paramInt)
  {
    MaterialCheckable localMaterialCheckable = (MaterialCheckable)this.checkables.get(Integer.valueOf(paramInt));
    if (localMaterialCheckable == null) {
      return;
    }
    if (uncheckInternal(localMaterialCheckable, this.selectionRequired)) {
      onCheckedStateChanged();
    }
  }
  
  public static abstract interface OnCheckedStateChangeListener
  {
    public abstract void onCheckedStateChanged(Set<Integer> paramSet);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/internal/CheckableGroup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */