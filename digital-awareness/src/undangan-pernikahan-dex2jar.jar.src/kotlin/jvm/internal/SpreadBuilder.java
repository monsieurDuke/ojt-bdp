package kotlin.jvm.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class SpreadBuilder
{
  private final ArrayList<Object> list;
  
  public SpreadBuilder(int paramInt)
  {
    this.list = new ArrayList(paramInt);
  }
  
  public void add(Object paramObject)
  {
    this.list.add(paramObject);
  }
  
  public void addSpread(Object paramObject)
  {
    if (paramObject == null) {
      return;
    }
    Object localObject;
    if ((paramObject instanceof Object[]))
    {
      localObject = (Object[])paramObject;
      if (localObject.length > 0)
      {
        paramObject = this.list;
        ((ArrayList)paramObject).ensureCapacity(((ArrayList)paramObject).size() + localObject.length);
        Collections.addAll(this.list, (Object[])localObject);
      }
    }
    else if ((paramObject instanceof Collection))
    {
      this.list.addAll((Collection)paramObject);
    }
    else if ((paramObject instanceof Iterable))
    {
      localObject = ((Iterable)paramObject).iterator();
      while (((Iterator)localObject).hasNext())
      {
        paramObject = ((Iterator)localObject).next();
        this.list.add(paramObject);
      }
    }
    else
    {
      if (!(paramObject instanceof Iterator)) {
        break label159;
      }
      paramObject = (Iterator)paramObject;
      while (((Iterator)paramObject).hasNext()) {
        this.list.add(((Iterator)paramObject).next());
      }
    }
    return;
    label159:
    throw new UnsupportedOperationException("Don't know how to spread " + paramObject.getClass());
  }
  
  public int size()
  {
    return this.list.size();
  }
  
  public Object[] toArray(Object[] paramArrayOfObject)
  {
    return this.list.toArray(paramArrayOfObject);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/jvm/internal/SpreadBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */