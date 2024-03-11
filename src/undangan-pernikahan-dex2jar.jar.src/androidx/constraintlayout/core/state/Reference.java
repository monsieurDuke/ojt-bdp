package androidx.constraintlayout.core.state;

import androidx.constraintlayout.core.state.helpers.Facade;
import androidx.constraintlayout.core.widgets.ConstraintWidget;

public abstract interface Reference
{
  public abstract void apply();
  
  public abstract ConstraintWidget getConstraintWidget();
  
  public abstract Facade getFacade();
  
  public abstract Object getKey();
  
  public abstract void setConstraintWidget(ConstraintWidget paramConstraintWidget);
  
  public abstract void setKey(Object paramObject);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/state/Reference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */