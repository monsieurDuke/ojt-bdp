package androidx.constraintlayout.core;

public class Cache
{
  Pools.Pool<ArrayRow> arrayRowPool = new Pools.SimplePool(256);
  SolverVariable[] mIndexedVariables = new SolverVariable[32];
  Pools.Pool<ArrayRow> optimizedArrayRowPool = new Pools.SimplePool(256);
  Pools.Pool<SolverVariable> solverVariablePool = new Pools.SimplePool(256);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/Cache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */