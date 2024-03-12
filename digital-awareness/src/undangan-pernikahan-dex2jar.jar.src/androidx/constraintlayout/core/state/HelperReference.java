package androidx.constraintlayout.core.state;

import androidx.constraintlayout.core.state.helpers.Facade;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.HelperWidget;
import java.util.ArrayList;
import java.util.Collections;

public class HelperReference
  extends ConstraintReference
  implements Facade
{
  private HelperWidget mHelperWidget;
  protected ArrayList<Object> mReferences = new ArrayList();
  protected final State mState;
  final State.Helper mType;
  
  public HelperReference(State paramState, State.Helper paramHelper)
  {
    super(paramState);
    this.mState = paramState;
    this.mType = paramHelper;
  }
  
  public HelperReference add(Object... paramVarArgs)
  {
    Collections.addAll(this.mReferences, paramVarArgs);
    return this;
  }
  
  public void apply() {}
  
  public ConstraintWidget getConstraintWidget()
  {
    return getHelperWidget();
  }
  
  public HelperWidget getHelperWidget()
  {
    return this.mHelperWidget;
  }
  
  public State.Helper getType()
  {
    return this.mType;
  }
  
  public void setHelperWidget(HelperWidget paramHelperWidget)
  {
    this.mHelperWidget = paramHelperWidget;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/state/HelperReference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */