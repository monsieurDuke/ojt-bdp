package kotlin.annotation;

import kotlin.Metadata;

@Metadata(d1={"\000\f\n\002\030\002\n\002\020\020\n\002\b\021\b\001\030\0002\b\022\004\022\0020\0000\001B\007\b\002¢\006\002\020\002j\002\b\003j\002\b\004j\002\b\005j\002\b\006j\002\b\007j\002\b\bj\002\b\tj\002\b\nj\002\b\013j\002\b\fj\002\b\rj\002\b\016j\002\b\017j\002\b\020j\002\b\021¨\006\022"}, d2={"Lkotlin/annotation/AnnotationTarget;", "", "(Ljava/lang/String;I)V", "CLASS", "ANNOTATION_CLASS", "TYPE_PARAMETER", "PROPERTY", "FIELD", "LOCAL_VARIABLE", "VALUE_PARAMETER", "CONSTRUCTOR", "FUNCTION", "PROPERTY_GETTER", "PROPERTY_SETTER", "TYPE", "EXPRESSION", "FILE", "TYPEALIAS", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public enum AnnotationTarget
{
  private static final AnnotationTarget[] $VALUES = $values();
  
  static
  {
    ANNOTATION_CLASS = new AnnotationTarget("ANNOTATION_CLASS", 1);
    TYPE_PARAMETER = new AnnotationTarget("TYPE_PARAMETER", 2);
    PROPERTY = new AnnotationTarget("PROPERTY", 3);
    FIELD = new AnnotationTarget("FIELD", 4);
    LOCAL_VARIABLE = new AnnotationTarget("LOCAL_VARIABLE", 5);
    VALUE_PARAMETER = new AnnotationTarget("VALUE_PARAMETER", 6);
    CONSTRUCTOR = new AnnotationTarget("CONSTRUCTOR", 7);
    FUNCTION = new AnnotationTarget("FUNCTION", 8);
    PROPERTY_GETTER = new AnnotationTarget("PROPERTY_GETTER", 9);
    PROPERTY_SETTER = new AnnotationTarget("PROPERTY_SETTER", 10);
    TYPE = new AnnotationTarget("TYPE", 11);
    EXPRESSION = new AnnotationTarget("EXPRESSION", 12);
    FILE = new AnnotationTarget("FILE", 13);
    TYPEALIAS = new AnnotationTarget("TYPEALIAS", 14);
  }
  
  private AnnotationTarget() {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/annotation/AnnotationTarget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */