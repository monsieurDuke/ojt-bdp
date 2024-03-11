package androidx.constraintlayout.core.widgets;

public class Rectangle
{
  public int height;
  public int width;
  public int x;
  public int y;
  
  public boolean contains(int paramInt1, int paramInt2)
  {
    int i = this.x;
    if ((paramInt1 >= i) && (paramInt1 < i + this.width))
    {
      paramInt1 = this.y;
      if ((paramInt2 >= paramInt1) && (paramInt2 < paramInt1 + this.height))
      {
        bool = true;
        break label49;
      }
    }
    boolean bool = false;
    label49:
    return bool;
  }
  
  public int getCenterX()
  {
    return (this.x + this.width) / 2;
  }
  
  public int getCenterY()
  {
    return (this.y + this.height) / 2;
  }
  
  void grow(int paramInt1, int paramInt2)
  {
    this.x -= paramInt1;
    this.y -= paramInt2;
    this.width += paramInt1 * 2;
    this.height += paramInt2 * 2;
  }
  
  boolean intersects(Rectangle paramRectangle)
  {
    int j = this.x;
    int i = paramRectangle.x;
    if ((j >= i) && (j < i + paramRectangle.width))
    {
      j = this.y;
      i = paramRectangle.y;
      if ((j >= i) && (j < i + paramRectangle.height))
      {
        bool = true;
        break label59;
      }
    }
    boolean bool = false;
    label59:
    return bool;
  }
  
  public void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.x = paramInt1;
    this.y = paramInt2;
    this.width = paramInt3;
    this.height = paramInt4;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/widgets/Rectangle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */