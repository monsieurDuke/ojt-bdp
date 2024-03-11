package androidx.constraintlayout.core.parser;

import java.io.PrintStream;
import java.util.ArrayList;

public class CLParser
{
  static boolean DEBUG = false;
  private boolean hasComment = false;
  private int lineNumber;
  private String mContent;
  
  public CLParser(String paramString)
  {
    this.mContent = paramString;
  }
  
  private CLElement createElement(CLElement paramCLElement, int paramInt, TYPE paramTYPE, boolean paramBoolean, char[] paramArrayOfChar)
  {
    Object localObject = null;
    if (DEBUG) {
      System.out.println("CREATE " + paramTYPE + " at " + paramArrayOfChar[paramInt]);
    }
    switch (1.$SwitchMap$androidx$constraintlayout$core$parser$CLParser$TYPE[paramTYPE.ordinal()])
    {
    default: 
      paramTYPE = (TYPE)localObject;
      break;
    case 6: 
      paramTYPE = CLToken.allocate(paramArrayOfChar);
      break;
    case 5: 
      paramTYPE = CLKey.allocate(paramArrayOfChar);
      break;
    case 4: 
      paramTYPE = CLNumber.allocate(paramArrayOfChar);
      break;
    case 3: 
      paramTYPE = CLString.allocate(paramArrayOfChar);
      break;
    case 2: 
      paramTYPE = CLArray.allocate(paramArrayOfChar);
      paramInt++;
      break;
    case 1: 
      paramTYPE = CLObject.allocate(paramArrayOfChar);
      paramInt++;
    }
    if (paramTYPE == null) {
      return null;
    }
    paramTYPE.setLine(this.lineNumber);
    if (paramBoolean) {
      paramTYPE.setStart(paramInt);
    }
    if ((paramCLElement instanceof CLContainer)) {
      paramTYPE.setContainer((CLContainer)paramCLElement);
    }
    return paramTYPE;
  }
  
  private CLElement getNextJsonElement(int paramInt, char paramChar, CLElement paramCLElement, char[] paramArrayOfChar)
    throws CLParsingException
  {
    switch (paramChar)
    {
    default: 
      if ((!(paramCLElement instanceof CLContainer)) || ((paramCLElement instanceof CLObject))) {
        break label466;
      }
      localCLElement = createElement(paramCLElement, paramInt, TYPE.TOKEN, true, paramArrayOfChar);
      paramCLElement = (CLToken)localCLElement;
      if (!paramCLElement.validate(paramChar, paramInt)) {
        break;
      }
      break;
    case '{': 
      localCLElement = createElement(paramCLElement, paramInt, TYPE.OBJECT, true, paramArrayOfChar);
      break;
    case ']': 
    case '}': 
      paramCLElement.setEnd(paramInt - 1);
      localCLElement = paramCLElement.getContainer();
      localCLElement.setEnd(paramInt);
      break;
    case '[': 
      localCLElement = createElement(paramCLElement, paramInt, TYPE.ARRAY, true, paramArrayOfChar);
      break;
    case '/': 
      localCLElement = paramCLElement;
      if (paramInt + 1 >= paramArrayOfChar.length) {
        break label480;
      }
      localCLElement = paramCLElement;
      if (paramArrayOfChar[(paramInt + 1)] != '/') {
        break label480;
      }
      this.hasComment = true;
      localCLElement = paramCLElement;
      break;
    case '+': 
    case '-': 
    case '.': 
    case '0': 
    case '1': 
    case '2': 
    case '3': 
    case '4': 
    case '5': 
    case '6': 
    case '7': 
    case '8': 
    case '9': 
      localCLElement = createElement(paramCLElement, paramInt, TYPE.NUMBER, true, paramArrayOfChar);
      break;
    case '"': 
    case '\'': 
      if ((paramCLElement instanceof CLObject)) {
        localCLElement = createElement(paramCLElement, paramInt, TYPE.KEY, true, paramArrayOfChar);
      } else {
        localCLElement = createElement(paramCLElement, paramInt, TYPE.STRING, true, paramArrayOfChar);
      }
      break;
    case '\t': 
    case '\n': 
    case '\r': 
    case ' ': 
    case ',': 
    case ':': 
      localCLElement = paramCLElement;
      break;
    }
    throw new CLParsingException("incorrect token <" + paramChar + "> at line " + this.lineNumber, paramCLElement);
    label466:
    CLElement localCLElement = createElement(paramCLElement, paramInt, TYPE.KEY, true, paramArrayOfChar);
    label480:
    return localCLElement;
  }
  
  public static CLObject parse(String paramString)
    throws CLParsingException
  {
    return new CLParser(paramString).parse();
  }
  
  public CLObject parse()
    throws CLParsingException
  {
    char[] arrayOfChar = this.mContent.toCharArray();
    int i1 = arrayOfChar.length;
    this.lineNumber = 1;
    int m = -1;
    int n;
    int k;
    for (int j = 0;; j++)
    {
      n = 10;
      k = m;
      if (j >= i1) {
        break;
      }
      k = arrayOfChar[j];
      if (k == 123)
      {
        k = j;
        break;
      }
      if (k == 10) {
        this.lineNumber += 1;
      }
    }
    if (k != -1)
    {
      CLObject localCLObject = CLObject.allocate(arrayOfChar);
      localCLObject.setLine(this.lineNumber);
      localCLObject.setStart(k);
      Object localObject2 = localCLObject;
      m = k + 1;
      j = n;
      n = k;
      while (m < i1)
      {
        int i = arrayOfChar[m];
        if (i == j) {
          this.lineNumber += 1;
        }
        if (this.hasComment) {
          if (i == j)
          {
            this.hasComment = false;
          }
          else
          {
            k = j;
            break label710;
          }
        }
        if (localObject2 == null) {
          break;
        }
        Object localObject1;
        if (((CLElement)localObject2).isDone())
        {
          localObject1 = getNextJsonElement(m, i, (CLElement)localObject2, arrayOfChar);
        }
        else if ((localObject2 instanceof CLObject))
        {
          if (i == 125)
          {
            ((CLElement)localObject2).setEnd(m - 1);
            localObject1 = localObject2;
          }
          else
          {
            localObject1 = getNextJsonElement(m, i, (CLElement)localObject2, arrayOfChar);
          }
        }
        else if ((localObject2 instanceof CLArray))
        {
          if (i == 93)
          {
            ((CLElement)localObject2).setEnd(m - 1);
            localObject1 = localObject2;
          }
          else
          {
            localObject1 = getNextJsonElement(m, i, (CLElement)localObject2, arrayOfChar);
          }
        }
        else if ((localObject2 instanceof CLString))
        {
          if (arrayOfChar[((int)localObject2.start)] == i)
          {
            ((CLElement)localObject2).setStart(((CLElement)localObject2).start + 1L);
            ((CLElement)localObject2).setEnd(m - 1);
          }
          localObject1 = localObject2;
        }
        else
        {
          if ((localObject2 instanceof CLToken))
          {
            localObject1 = (CLToken)localObject2;
            if (!((CLToken)localObject1).validate(i, m)) {
              throw new CLParsingException("parsing incorrect token " + ((CLToken)localObject1).content() + " at line " + this.lineNumber, (CLElement)localObject1);
            }
          }
          if ((!(localObject2 instanceof CLKey)) && (!(localObject2 instanceof CLString))) {
            break label502;
          }
          j = arrayOfChar[((int)localObject2.start)];
          if ((j != 39) && (j != 34)) {
            break label502;
          }
          if (j == i)
          {
            ((CLElement)localObject2).setStart(((CLElement)localObject2).start + 1L);
            ((CLElement)localObject2).setEnd(m - 1);
          }
          label502:
          if (!((CLElement)localObject2).isDone())
          {
            if ((i != 125) && (i != 93) && (i != 44) && (i != 32) && (i != 9) && (i != 13))
            {
              j = 10;
              if (i != 10)
              {
                localObject1 = localObject2;
                if (i != 58) {
                  break label659;
                }
              }
            }
            k = 10;
            ((CLElement)localObject2).setEnd(m - 1);
            if (i != 125)
            {
              localObject1 = localObject2;
              j = k;
              if (i != 93) {}
            }
            else
            {
              localObject2 = ((CLElement)localObject2).getContainer();
              ((CLElement)localObject2).setEnd(m - 1);
              localObject1 = localObject2;
              j = k;
              if ((localObject2 instanceof CLKey))
              {
                localObject1 = ((CLElement)localObject2).getContainer();
                ((CLElement)localObject1).setEnd(m - 1);
                j = k;
              }
            }
          }
          else
          {
            j = 10;
            localObject1 = localObject2;
          }
        }
        label659:
        localObject2 = localObject1;
        k = j;
        if (((CLElement)localObject1).isDone()) {
          if ((localObject1 instanceof CLKey))
          {
            localObject2 = localObject1;
            k = j;
            if (((CLKey)localObject1).mElements.size() <= 0) {}
          }
          else
          {
            localObject2 = ((CLElement)localObject1).getContainer();
            k = j;
          }
        }
        label710:
        m++;
        j = k;
      }
      while ((localObject2 != null) && (!((CLElement)localObject2).isDone()))
      {
        if ((localObject2 instanceof CLString)) {
          ((CLElement)localObject2).setStart((int)((CLElement)localObject2).start + 1);
        }
        ((CLElement)localObject2).setEnd(i1 - 1);
        localObject2 = ((CLElement)localObject2).getContainer();
      }
      if (DEBUG) {
        System.out.println("Root: " + localCLObject.toJSON());
      }
      return localCLObject;
    }
    throw new CLParsingException("invalid json content", null);
  }
  
  static enum TYPE
  {
    private static final TYPE[] $VALUES;
    
    static
    {
      TYPE localTYPE7 = new TYPE("UNKNOWN", 0);
      UNKNOWN = localTYPE7;
      TYPE localTYPE3 = new TYPE("OBJECT", 1);
      OBJECT = localTYPE3;
      TYPE localTYPE5 = new TYPE("ARRAY", 2);
      ARRAY = localTYPE5;
      TYPE localTYPE6 = new TYPE("NUMBER", 3);
      NUMBER = localTYPE6;
      TYPE localTYPE1 = new TYPE("STRING", 4);
      STRING = localTYPE1;
      TYPE localTYPE4 = new TYPE("KEY", 5);
      KEY = localTYPE4;
      TYPE localTYPE2 = new TYPE("TOKEN", 6);
      TOKEN = localTYPE2;
      $VALUES = new TYPE[] { localTYPE7, localTYPE3, localTYPE5, localTYPE6, localTYPE1, localTYPE4, localTYPE2 };
    }
    
    private TYPE() {}
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/parser/CLParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */