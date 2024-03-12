package org.intellij.lang.annotations;

class PrintFormatPattern
{
  private static final String ARG_INDEX = "(?:\\d+\\$)?";
  private static final String CONVERSION = "(?:[tT])?(?:[a-zA-Z%])";
  private static final String FLAGS = "(?:[-#+ 0,(<]*)?";
  private static final String PRECISION = "(?:\\.\\d+)?";
  static final String PRINT_FORMAT = "(?:[^%]|%%|(?:%(?:\\d+\\$)?(?:[-#+ 0,(<]*)?(?:\\d+)?(?:\\.\\d+)?(?:[tT])?(?:[a-zA-Z%])))*";
  private static final String TEXT = "[^%]|%%";
  private static final String WIDTH = "(?:\\d+)?";
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/org/intellij/lang/annotations/PrintFormatPattern.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */