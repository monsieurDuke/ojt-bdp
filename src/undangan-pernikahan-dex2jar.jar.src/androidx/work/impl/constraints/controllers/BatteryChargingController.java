package androidx.work.impl.constraints.controllers;

import android.content.Context;
import androidx.work.Constraints;
import androidx.work.impl.constraints.trackers.Trackers;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;

public class BatteryChargingController
  extends ConstraintController<Boolean>
{
  public BatteryChargingController(Context paramContext, TaskExecutor paramTaskExecutor)
  {
    super(Trackers.getInstance(paramContext, paramTaskExecutor).getBatteryChargingTracker());
  }
  
  boolean hasConstraint(WorkSpec paramWorkSpec)
  {
    return paramWorkSpec.constraints.requiresCharging();
  }
  
  boolean isConstrained(Boolean paramBoolean)
  {
    return paramBoolean.booleanValue() ^ true;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/constraints/controllers/BatteryChargingController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */