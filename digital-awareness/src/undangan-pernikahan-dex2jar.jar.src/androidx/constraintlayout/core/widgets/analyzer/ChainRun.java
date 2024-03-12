package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import java.util.ArrayList;
import java.util.Iterator;

public class ChainRun
  extends WidgetRun
{
  private int chainStyle;
  ArrayList<WidgetRun> widgets = new ArrayList();
  
  public ChainRun(ConstraintWidget paramConstraintWidget, int paramInt)
  {
    super(paramConstraintWidget);
    this.orientation = paramInt;
    build();
  }
  
  private void build()
  {
    Object localObject2 = this.widget;
    ConstraintWidget localConstraintWidget;
    for (Object localObject1 = ((ConstraintWidget)localObject2).getPreviousChainMember(this.orientation); localObject1 != null; localObject1 = localConstraintWidget)
    {
      localConstraintWidget = ((ConstraintWidget)localObject1).getPreviousChainMember(this.orientation);
      localObject2 = localObject1;
    }
    this.widget = ((ConstraintWidget)localObject2);
    this.widgets.add(((ConstraintWidget)localObject2).getRun(this.orientation));
    for (localObject1 = ((ConstraintWidget)localObject2).getNextChainMember(this.orientation); localObject1 != null; localObject1 = ((ConstraintWidget)localObject1).getNextChainMember(this.orientation)) {
      this.widgets.add(((ConstraintWidget)localObject1).getRun(this.orientation));
    }
    localObject1 = this.widgets.iterator();
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (WidgetRun)((Iterator)localObject1).next();
      if (this.orientation == 0) {
        ((WidgetRun)localObject2).widget.horizontalChainRun = this;
      } else if (this.orientation == 1) {
        ((WidgetRun)localObject2).widget.verticalChainRun = this;
      }
    }
    int i;
    if ((this.orientation == 0) && (((ConstraintWidgetContainer)this.widget.getParent()).isRtl())) {
      i = 1;
    } else {
      i = 0;
    }
    if ((i != 0) && (this.widgets.size() > 1))
    {
      localObject1 = this.widgets;
      this.widget = ((WidgetRun)((ArrayList)localObject1).get(((ArrayList)localObject1).size() - 1)).widget;
    }
    if (this.orientation == 0) {
      i = this.widget.getHorizontalChainStyle();
    } else {
      i = this.widget.getVerticalChainStyle();
    }
    this.chainStyle = i;
  }
  
  private ConstraintWidget getFirstVisibleWidget()
  {
    for (int i = 0; i < this.widgets.size(); i++)
    {
      WidgetRun localWidgetRun = (WidgetRun)this.widgets.get(i);
      if (localWidgetRun.widget.getVisibility() != 8) {
        return localWidgetRun.widget;
      }
    }
    return null;
  }
  
  private ConstraintWidget getLastVisibleWidget()
  {
    for (int i = this.widgets.size() - 1; i >= 0; i--)
    {
      WidgetRun localWidgetRun = (WidgetRun)this.widgets.get(i);
      if (localWidgetRun.widget.getVisibility() != 8) {
        return localWidgetRun.widget;
      }
    }
    return null;
  }
  
  void apply()
  {
    Object localObject1 = this.widgets.iterator();
    while (((Iterator)localObject1).hasNext()) {
      ((WidgetRun)((Iterator)localObject1).next()).apply();
    }
    int i = this.widgets.size();
    if (i < 1) {
      return;
    }
    Object localObject2 = ((WidgetRun)this.widgets.get(0)).widget;
    localObject1 = ((WidgetRun)this.widgets.get(i - 1)).widget;
    Object localObject3;
    if (this.orientation == 0)
    {
      localObject3 = ((ConstraintWidget)localObject2).mLeft;
      localObject1 = ((ConstraintWidget)localObject1).mRight;
      localObject2 = getTarget((ConstraintAnchor)localObject3, 0);
      i = ((ConstraintAnchor)localObject3).getMargin();
      localObject3 = getFirstVisibleWidget();
      if (localObject3 != null) {
        i = ((ConstraintWidget)localObject3).mLeft.getMargin();
      }
      if (localObject2 != null) {
        addTarget(this.start, (DependencyNode)localObject2, i);
      }
      localObject2 = getTarget((ConstraintAnchor)localObject1, 0);
      i = ((ConstraintAnchor)localObject1).getMargin();
      localObject1 = getLastVisibleWidget();
      if (localObject1 != null) {
        i = ((ConstraintWidget)localObject1).mRight.getMargin();
      }
      if (localObject2 != null) {
        addTarget(this.end, (DependencyNode)localObject2, -i);
      }
    }
    else
    {
      localObject2 = ((ConstraintWidget)localObject2).mTop;
      localObject1 = ((ConstraintWidget)localObject1).mBottom;
      localObject3 = getTarget((ConstraintAnchor)localObject2, 1);
      i = ((ConstraintAnchor)localObject2).getMargin();
      localObject2 = getFirstVisibleWidget();
      if (localObject2 != null) {
        i = ((ConstraintWidget)localObject2).mTop.getMargin();
      }
      if (localObject3 != null) {
        addTarget(this.start, (DependencyNode)localObject3, i);
      }
      localObject2 = getTarget((ConstraintAnchor)localObject1, 1);
      i = ((ConstraintAnchor)localObject1).getMargin();
      localObject1 = getLastVisibleWidget();
      if (localObject1 != null) {
        i = ((ConstraintWidget)localObject1).mBottom.getMargin();
      }
      if (localObject2 != null) {
        addTarget(this.end, (DependencyNode)localObject2, -i);
      }
    }
    this.start.updateDelegate = this;
    this.end.updateDelegate = this;
  }
  
  public void applyToWidget()
  {
    for (int i = 0; i < this.widgets.size(); i++) {
      ((WidgetRun)this.widgets.get(i)).applyToWidget();
    }
  }
  
  void clear()
  {
    this.runGroup = null;
    Iterator localIterator = this.widgets.iterator();
    while (localIterator.hasNext()) {
      ((WidgetRun)localIterator.next()).clear();
    }
  }
  
  public long getWrapDimension()
  {
    int j = this.widgets.size();
    long l = 0L;
    for (int i = 0; i < j; i++)
    {
      WidgetRun localWidgetRun = (WidgetRun)this.widgets.get(i);
      l = l + localWidgetRun.start.margin + localWidgetRun.getWrapDimension() + localWidgetRun.end.margin;
    }
    return l;
  }
  
  void reset()
  {
    this.start.resolved = false;
    this.end.resolved = false;
  }
  
  boolean supportsWrapComputation()
  {
    int j = this.widgets.size();
    for (int i = 0; i < j; i++) {
      if (!((WidgetRun)this.widgets.get(i)).supportsWrapComputation()) {
        return false;
      }
    }
    return true;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder("ChainRun ");
    Object localObject;
    if (this.orientation == 0) {
      localObject = "horizontal : ";
    } else {
      localObject = "vertical : ";
    }
    localStringBuilder.append((String)localObject);
    Iterator localIterator = this.widgets.iterator();
    while (localIterator.hasNext())
    {
      localObject = (WidgetRun)localIterator.next();
      localStringBuilder.append("<");
      localStringBuilder.append(localObject);
      localStringBuilder.append("> ");
    }
    return localStringBuilder.toString();
  }
  
  public void update(Dependency paramDependency)
  {
    if ((this.start.resolved) && (this.end.resolved))
    {
      paramDependency = this.widget.getParent();
      boolean bool = false;
      if ((paramDependency instanceof ConstraintWidgetContainer)) {
        bool = ((ConstraintWidgetContainer)paramDependency).isRtl();
      }
      int i11 = this.end.value - this.start.value;
      int i10 = this.widgets.size();
      int j = -1;
      for (int i = 0;; i++)
      {
        i4 = j;
        if (i >= i10) {
          break label126;
        }
        if (((WidgetRun)this.widgets.get(i)).widget.getVisibility() != 8) {
          break;
        }
      }
      int i4 = i;
      label126:
      j = -1;
      for (i = i10 - 1;; i--)
      {
        i5 = j;
        if (i < 0) {
          break label177;
        }
        if (((WidgetRun)this.widgets.get(i)).widget.getVisibility() != 8) {
          break;
        }
      }
      int i5 = i;
      label177:
      int i6;
      int n;
      float f2;
      float f1;
      int k;
      int i2;
      int i3;
      for (int i1 = 0;; i1++)
      {
        i6 = 0;
        n = 0;
        f2 = 0.0F;
        f1 = 0.0F;
        j = 0;
        i = 0;
        k = 0;
        m = 0;
        if (i1 >= 2) {
          break;
        }
        i2 = 0;
        while (i2 < i10)
        {
          WidgetRun localWidgetRun = (WidgetRun)this.widgets.get(i2);
          if (localWidgetRun.widget.getVisibility() == 8)
          {
            k = i;
            f2 = f1;
          }
          else
          {
            i3 = n + 1;
            j = m;
            if (i2 > 0)
            {
              j = m;
              if (i2 >= i4) {
                j = m + localWidgetRun.start.margin;
              }
            }
            m = localWidgetRun.dimension.value;
            if (localWidgetRun.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
              k = 1;
            } else {
              k = 0;
            }
            if (k != 0)
            {
              if ((this.orientation == 0) && (!localWidgetRun.widget.horizontalRun.dimension.resolved)) {
                return;
              }
              if ((this.orientation != 1) || (localWidgetRun.widget.verticalRun.dimension.resolved)) {}
            }
            else if ((localWidgetRun.matchConstraintsType == 1) && (i1 == 0))
            {
              k = 1;
              m = localWidgetRun.dimension.wrapValue;
              i++;
            }
            else if (localWidgetRun.dimension.resolved)
            {
              k = 1;
            }
            if (k == 0)
            {
              i++;
              float f3 = localWidgetRun.widget.mWeight[this.orientation];
              f2 = f1;
              if (f3 >= 0.0F) {
                f2 = f1 + f3;
              }
              f1 = f2;
            }
            else
            {
              j += m;
            }
            m = j;
            k = i;
            f2 = f1;
            n = i3;
            if (i2 < i10 - 1)
            {
              m = j;
              k = i;
              f2 = f1;
              n = i3;
              if (i2 < i5)
              {
                m = j + -localWidgetRun.end.margin;
                n = i3;
                f2 = f1;
                k = i;
              }
            }
          }
          i2++;
          i = k;
          f1 = f2;
        }
        k = m;
        j = i;
        f2 = f1;
        i6 = n;
        if (m < i11) {
          break;
        }
        if (i == 0)
        {
          k = m;
          j = i;
          f2 = f1;
          i6 = n;
          break;
        }
      }
      int m = this.start.value;
      if (bool) {
        m = this.end.value;
      }
      i = m;
      if (k > i11) {
        if (bool) {
          i = m + (int)((k - i11) / 2.0F + 0.5F);
        } else {
          i = m - (int)((k - i11) / 2.0F + 0.5F);
        }
      }
      if (j > 0)
      {
        n = (int)((i11 - k) / j + 0.5F);
        m = 0;
        int i7 = 0;
        i1 = i;
        while (i7 < i10)
        {
          paramDependency = (WidgetRun)this.widgets.get(i7);
          if ((paramDependency.widget.getVisibility() != 8) && (paramDependency.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && (!paramDependency.dimension.resolved))
          {
            i = n;
            if (f2 > 0.0F)
            {
              f1 = paramDependency.widget.mWeight[this.orientation];
              i = (int)((i11 - k) * f1 / f2 + 0.5F);
            }
            i2 = i;
            if (this.orientation == 0)
            {
              i3 = paramDependency.widget.mMatchConstraintMaxWidth;
              i8 = paramDependency.widget.mMatchConstraintMinWidth;
            }
            else
            {
              i3 = paramDependency.widget.mMatchConstraintMaxHeight;
              i8 = paramDependency.widget.mMatchConstraintMinHeight;
            }
            int i9 = i2;
            if (paramDependency.matchConstraintsType == 1) {
              i9 = Math.min(i2, paramDependency.dimension.wrapValue);
            }
            int i8 = Math.max(i8, i9);
            i2 = i8;
            if (i3 > 0) {
              i2 = Math.min(i3, i8);
            }
            i8 = i;
            i3 = m;
            if (i2 != i)
            {
              i3 = m + 1;
              i8 = i2;
            }
            paramDependency.dimension.resolve(i8);
            m = i3;
          }
          i7++;
        }
        if (m > 0)
        {
          n = j - m;
          i = 0;
          for (j = 0; j < i10; j++)
          {
            paramDependency = (WidgetRun)this.widgets.get(j);
            if (paramDependency.widget.getVisibility() != 8)
            {
              k = i;
              if (j > 0)
              {
                k = i;
                if (j >= i4) {
                  k = i + paramDependency.start.margin;
                }
              }
              k += paramDependency.dimension.value;
              i = k;
              if (j < i10 - 1)
              {
                i = k;
                if (j < i5) {
                  i = k + -paramDependency.end.margin;
                }
              }
            }
          }
          j = n;
        }
        else
        {
          i = k;
        }
        if ((this.chainStyle == 2) && (m == 0)) {
          this.chainStyle = 0;
        }
        k = i;
        n = j;
        i = i1;
      }
      else
      {
        n = j;
      }
      if (k > i11) {
        this.chainStyle = 2;
      }
      if ((i6 > 0) && (n == 0) && (i4 == i5)) {
        this.chainStyle = 2;
      }
      j = this.chainStyle;
      if (j == 1)
      {
        j = 0;
        if (i6 > 1) {
          j = (i11 - k) / (i6 - 1);
        } else if (i6 == 1) {
          j = (i11 - k) / 2;
        }
        m = j;
        if (n > 0) {
          m = 0;
        }
        j = 0;
        for (k = i; j < i10; k = i)
        {
          i = j;
          if (bool) {
            i = i10 - (j + 1);
          }
          paramDependency = (WidgetRun)this.widgets.get(i);
          if (paramDependency.widget.getVisibility() == 8)
          {
            paramDependency.start.resolve(k);
            paramDependency.end.resolve(k);
            i = k;
          }
          else
          {
            i = k;
            if (j > 0) {
              if (bool) {
                i = k - m;
              } else {
                i = k + m;
              }
            }
            k = i;
            if (j > 0)
            {
              k = i;
              if (j >= i4) {
                if (bool) {
                  k = i - paramDependency.start.margin;
                } else {
                  k = i + paramDependency.start.margin;
                }
              }
            }
            if (bool) {
              paramDependency.end.resolve(k);
            } else {
              paramDependency.start.resolve(k);
            }
            n = paramDependency.dimension.value;
            i = n;
            if (paramDependency.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)
            {
              i = n;
              if (paramDependency.matchConstraintsType == 1) {
                i = paramDependency.dimension.wrapValue;
              }
            }
            if (bool) {
              k -= i;
            } else {
              k += i;
            }
            if (bool) {
              paramDependency.start.resolve(k);
            } else {
              paramDependency.end.resolve(k);
            }
            paramDependency.resolved = true;
            i = k;
            if (j < i10 - 1)
            {
              i = k;
              if (j < i5) {
                if (bool) {
                  i = k - -paramDependency.end.margin;
                } else {
                  i = k + -paramDependency.end.margin;
                }
              }
            }
          }
          j++;
        }
      }
      else if (j == 0)
      {
        k = (i11 - k) / (i6 + 1);
        if (n > 0) {
          k = 0;
        }
        j = 0;
        m = k;
        while (j < i10)
        {
          k = j;
          if (bool) {
            k = i10 - (j + 1);
          }
          paramDependency = (WidgetRun)this.widgets.get(k);
          if (paramDependency.widget.getVisibility() == 8)
          {
            paramDependency.start.resolve(i);
            paramDependency.end.resolve(i);
          }
          else
          {
            if (bool) {
              k = i - m;
            } else {
              k = i + m;
            }
            i = k;
            if (j > 0)
            {
              i = k;
              if (j >= i4) {
                if (bool) {
                  i = k - paramDependency.start.margin;
                } else {
                  i = k + paramDependency.start.margin;
                }
              }
            }
            if (bool) {
              paramDependency.end.resolve(i);
            } else {
              paramDependency.start.resolve(i);
            }
            n = paramDependency.dimension.value;
            k = n;
            if (paramDependency.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)
            {
              k = n;
              if (paramDependency.matchConstraintsType == 1) {
                k = Math.min(n, paramDependency.dimension.wrapValue);
              }
            }
            if (bool) {
              k = i - k;
            } else {
              k = i + k;
            }
            if (bool) {
              paramDependency.start.resolve(k);
            } else {
              paramDependency.end.resolve(k);
            }
            i = k;
            if (j < i10 - 1)
            {
              i = k;
              if (j < i5) {
                if (bool) {
                  i = k - -paramDependency.end.margin;
                } else {
                  i = k + -paramDependency.end.margin;
                }
              }
            }
          }
          j++;
        }
      }
      else if (j == 2)
      {
        if (this.orientation == 0) {
          f2 = this.widget.getHorizontalBiasPercent();
        } else {
          f2 = this.widget.getVerticalBiasPercent();
        }
        f1 = f2;
        if (bool) {
          f1 = 1.0F - f2;
        }
        j = (int)((i11 - k) * f1 + 0.5F);
        if ((j < 0) || (n > 0)) {
          j = 0;
        }
        if (bool) {
          i -= j;
        } else {
          i += j;
        }
        for (j = 0; j < i10; j++)
        {
          k = j;
          if (bool) {
            k = i10 - (j + 1);
          }
          paramDependency = (WidgetRun)this.widgets.get(k);
          if (paramDependency.widget.getVisibility() == 8)
          {
            paramDependency.start.resolve(i);
            paramDependency.end.resolve(i);
          }
          else
          {
            k = i;
            if (j > 0)
            {
              k = i;
              if (j >= i4) {
                if (bool) {
                  k = i - paramDependency.start.margin;
                } else {
                  k = i + paramDependency.start.margin;
                }
              }
            }
            if (bool) {
              paramDependency.end.resolve(k);
            } else {
              paramDependency.start.resolve(k);
            }
            i = paramDependency.dimension.value;
            if ((paramDependency.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && (paramDependency.matchConstraintsType == 1)) {
              i = paramDependency.dimension.wrapValue;
            }
            if (bool) {
              k -= i;
            } else {
              k += i;
            }
            if (bool) {
              paramDependency.start.resolve(k);
            } else {
              paramDependency.end.resolve(k);
            }
            i = k;
            if (j < i10 - 1)
            {
              i = k;
              if (j < i5) {
                if (bool) {
                  i = k - -paramDependency.end.margin;
                } else {
                  i = k + -paramDependency.end.margin;
                }
              }
            }
          }
        }
      }
      return;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/widgets/analyzer/ChainRun.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */