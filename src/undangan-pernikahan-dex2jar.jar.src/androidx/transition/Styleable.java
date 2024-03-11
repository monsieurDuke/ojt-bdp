package androidx.transition;

class Styleable
{
  static final int[] ARC_MOTION = { 16843901, 16843902, 16843903 };
  static final int[] CHANGE_BOUNDS;
  static final int[] CHANGE_TRANSFORM;
  static final int[] FADE;
  static final int[] PATTERN_PATH_MOTION = { 16843978 };
  static final int[] SLIDE;
  static final int[] TRANSITION;
  static final int[] TRANSITION_MANAGER;
  static final int[] TRANSITION_SET;
  static final int[] TRANSITION_TARGET = { 16842799, 16843740, 16843841, 16843842, 16843853, 16843854 };
  static final int[] VISIBILITY_TRANSITION;
  
  static
  {
    TRANSITION_MANAGER = new int[] { 16843741, 16843742, 16843743 };
    TRANSITION = new int[] { 16843073, 16843160, 16843746, 16843855 };
    CHANGE_BOUNDS = new int[] { 16843983 };
    VISIBILITY_TRANSITION = new int[] { 16843900 };
    FADE = new int[] { 16843745 };
    CHANGE_TRANSFORM = new int[] { 16843964, 16843965 };
    SLIDE = new int[] { 16843824 };
    TRANSITION_SET = new int[] { 16843744 };
  }
  
  static abstract interface ArcMotion
  {
    public static final int MAXIMUM_ANGLE = 2;
    public static final int MINIMUM_HORIZONTAL_ANGLE = 0;
    public static final int MINIMUM_VERTICAL_ANGLE = 1;
  }
  
  static abstract interface ChangeBounds
  {
    public static final int RESIZE_CLIP = 0;
  }
  
  static abstract interface ChangeTransform
  {
    public static final int REPARENT = 0;
    public static final int REPARENT_WITH_OVERLAY = 1;
  }
  
  static abstract interface Fade
  {
    public static final int FADING_MODE = 0;
  }
  
  static abstract interface PatternPathMotion
  {
    public static final int PATTERN_PATH_DATA = 0;
  }
  
  static abstract interface Slide
  {
    public static final int SLIDE_EDGE = 0;
  }
  
  static abstract interface Transition
  {
    public static final int DURATION = 1;
    public static final int INTERPOLATOR = 0;
    public static final int MATCH_ORDER = 3;
    public static final int START_DELAY = 2;
  }
  
  static abstract interface TransitionManager
  {
    public static final int FROM_SCENE = 0;
    public static final int TO_SCENE = 1;
    public static final int TRANSITION = 2;
  }
  
  static abstract interface TransitionSet
  {
    public static final int TRANSITION_ORDERING = 0;
  }
  
  static abstract interface TransitionTarget
  {
    public static final int EXCLUDE_CLASS = 3;
    public static final int EXCLUDE_ID = 2;
    public static final int EXCLUDE_NAME = 5;
    public static final int TARGET_CLASS = 0;
    public static final int TARGET_ID = 1;
    public static final int TARGET_NAME = 4;
  }
  
  static abstract interface VisibilityTransition
  {
    public static final int TRANSITION_VISIBILITY_MODE = 0;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/transition/Styleable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */