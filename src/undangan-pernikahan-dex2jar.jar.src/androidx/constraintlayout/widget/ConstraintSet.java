package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.HelperWidget;
import androidx.constraintlayout.motion.widget.Debug;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.motion.widget.MotionScene;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class ConstraintSet
{
  private static final int ALPHA = 43;
  private static final int ANIMATE_CIRCLE_ANGLE_TO = 82;
  private static final int ANIMATE_RELATIVE_TO = 64;
  private static final int BARRIER_ALLOWS_GONE_WIDGETS = 75;
  private static final int BARRIER_DIRECTION = 72;
  private static final int BARRIER_MARGIN = 73;
  private static final int BARRIER_TYPE = 1;
  public static final int BASELINE = 5;
  private static final int BASELINE_MARGIN = 93;
  private static final int BASELINE_TO_BASELINE = 1;
  private static final int BASELINE_TO_BOTTOM = 92;
  private static final int BASELINE_TO_TOP = 91;
  public static final int BOTTOM = 4;
  private static final int BOTTOM_MARGIN = 2;
  private static final int BOTTOM_TO_BOTTOM = 3;
  private static final int BOTTOM_TO_TOP = 4;
  public static final int CHAIN_PACKED = 2;
  public static final int CHAIN_SPREAD = 0;
  public static final int CHAIN_SPREAD_INSIDE = 1;
  private static final int CHAIN_USE_RTL = 71;
  private static final int CIRCLE = 61;
  private static final int CIRCLE_ANGLE = 63;
  private static final int CIRCLE_RADIUS = 62;
  public static final int CIRCLE_REFERENCE = 8;
  private static final int CONSTRAINED_HEIGHT = 81;
  private static final int CONSTRAINED_WIDTH = 80;
  private static final int CONSTRAINT_REFERENCED_IDS = 74;
  private static final int CONSTRAINT_TAG = 77;
  private static final boolean DEBUG = false;
  private static final int DIMENSION_RATIO = 5;
  private static final int DRAW_PATH = 66;
  private static final int EDITOR_ABSOLUTE_X = 6;
  private static final int EDITOR_ABSOLUTE_Y = 7;
  private static final int ELEVATION = 44;
  public static final int END = 7;
  private static final int END_MARGIN = 8;
  private static final int END_TO_END = 9;
  private static final int END_TO_START = 10;
  private static final String ERROR_MESSAGE = "XML parser error must be within a Constraint ";
  public static final int GONE = 8;
  private static final int GONE_BASELINE_MARGIN = 94;
  private static final int GONE_BOTTOM_MARGIN = 11;
  private static final int GONE_END_MARGIN = 12;
  private static final int GONE_LEFT_MARGIN = 13;
  private static final int GONE_RIGHT_MARGIN = 14;
  private static final int GONE_START_MARGIN = 15;
  private static final int GONE_TOP_MARGIN = 16;
  private static final int GUIDELINE_USE_RTL = 99;
  private static final int GUIDE_BEGIN = 17;
  private static final int GUIDE_END = 18;
  private static final int GUIDE_PERCENT = 19;
  private static final int HEIGHT_DEFAULT = 55;
  private static final int HEIGHT_MAX = 57;
  private static final int HEIGHT_MIN = 59;
  private static final int HEIGHT_PERCENT = 70;
  public static final int HORIZONTAL = 0;
  private static final int HORIZONTAL_BIAS = 20;
  public static final int HORIZONTAL_GUIDELINE = 0;
  private static final int HORIZONTAL_STYLE = 41;
  private static final int HORIZONTAL_WEIGHT = 39;
  private static final int INTERNAL_MATCH_CONSTRAINT = -3;
  private static final int INTERNAL_MATCH_PARENT = -1;
  private static final int INTERNAL_WRAP_CONTENT = -2;
  private static final int INTERNAL_WRAP_CONTENT_CONSTRAINED = -4;
  public static final int INVISIBLE = 4;
  private static final String KEY_PERCENT_PARENT = "parent";
  private static final String KEY_RATIO = "ratio";
  private static final String KEY_WEIGHT = "weight";
  private static final int LAYOUT_CONSTRAINT_HEIGHT = 96;
  private static final int LAYOUT_CONSTRAINT_WIDTH = 95;
  private static final int LAYOUT_HEIGHT = 21;
  private static final int LAYOUT_VISIBILITY = 22;
  private static final int LAYOUT_WIDTH = 23;
  private static final int LAYOUT_WRAP_BEHAVIOR = 97;
  public static final int LEFT = 1;
  private static final int LEFT_MARGIN = 24;
  private static final int LEFT_TO_LEFT = 25;
  private static final int LEFT_TO_RIGHT = 26;
  public static final int MATCH_CONSTRAINT = 0;
  public static final int MATCH_CONSTRAINT_PERCENT = 2;
  public static final int MATCH_CONSTRAINT_SPREAD = 0;
  public static final int MATCH_CONSTRAINT_WRAP = 1;
  private static final int MOTION_STAGGER = 79;
  private static final int MOTION_TARGET = 98;
  private static final int ORIENTATION = 27;
  public static final int PARENT_ID = 0;
  private static final int PATH_MOTION_ARC = 76;
  private static final int PROGRESS = 68;
  private static final int QUANTIZE_MOTION_INTERPOLATOR = 86;
  private static final int QUANTIZE_MOTION_INTERPOLATOR_ID = 89;
  private static final int QUANTIZE_MOTION_INTERPOLATOR_STR = 90;
  private static final int QUANTIZE_MOTION_INTERPOLATOR_TYPE = 88;
  private static final int QUANTIZE_MOTION_PHASE = 85;
  private static final int QUANTIZE_MOTION_STEPS = 84;
  public static final int RIGHT = 2;
  private static final int RIGHT_MARGIN = 28;
  private static final int RIGHT_TO_LEFT = 29;
  private static final int RIGHT_TO_RIGHT = 30;
  public static final int ROTATE_LEFT_OF_PORTRATE = 4;
  public static final int ROTATE_NONE = 0;
  public static final int ROTATE_PORTRATE_OF_LEFT = 2;
  public static final int ROTATE_PORTRATE_OF_RIGHT = 1;
  public static final int ROTATE_RIGHT_OF_PORTRATE = 3;
  private static final int ROTATION = 60;
  private static final int ROTATION_X = 45;
  private static final int ROTATION_Y = 46;
  private static final int SCALE_X = 47;
  private static final int SCALE_Y = 48;
  public static final int START = 6;
  private static final int START_MARGIN = 31;
  private static final int START_TO_END = 32;
  private static final int START_TO_START = 33;
  private static final String TAG = "ConstraintSet";
  public static final int TOP = 3;
  private static final int TOP_MARGIN = 34;
  private static final int TOP_TO_BOTTOM = 35;
  private static final int TOP_TO_TOP = 36;
  private static final int TRANSFORM_PIVOT_TARGET = 83;
  private static final int TRANSFORM_PIVOT_X = 49;
  private static final int TRANSFORM_PIVOT_Y = 50;
  private static final int TRANSITION_EASING = 65;
  private static final int TRANSITION_PATH_ROTATE = 67;
  private static final int TRANSLATION_X = 51;
  private static final int TRANSLATION_Y = 52;
  private static final int TRANSLATION_Z = 53;
  public static final int UNSET = -1;
  private static final int UNUSED = 87;
  public static final int VERTICAL = 1;
  private static final int VERTICAL_BIAS = 37;
  public static final int VERTICAL_GUIDELINE = 1;
  private static final int VERTICAL_STYLE = 42;
  private static final int VERTICAL_WEIGHT = 40;
  private static final int VIEW_ID = 38;
  private static final int[] VISIBILITY_FLAGS = { 0, 4, 8 };
  private static final int VISIBILITY_MODE = 78;
  public static final int VISIBILITY_MODE_IGNORE = 1;
  public static final int VISIBILITY_MODE_NORMAL = 0;
  public static final int VISIBLE = 0;
  private static final int WIDTH_DEFAULT = 54;
  private static final int WIDTH_MAX = 56;
  private static final int WIDTH_MIN = 58;
  private static final int WIDTH_PERCENT = 69;
  public static final int WRAP_CONTENT = -2;
  private static SparseIntArray mapToConstant = new SparseIntArray();
  private static SparseIntArray overrideMapToConstant = new SparseIntArray();
  public String derivedState = "";
  private HashMap<Integer, Constraint> mConstraints = new HashMap();
  private boolean mForceId = true;
  public String mIdString;
  public int mRotate = 0;
  private HashMap<String, ConstraintAttribute> mSavedAttributes = new HashMap();
  private boolean mValidate;
  
  static
  {
    mapToConstant.append(R.styleable.Constraint_layout_constraintLeft_toLeftOf, 25);
    mapToConstant.append(R.styleable.Constraint_layout_constraintLeft_toRightOf, 26);
    mapToConstant.append(R.styleable.Constraint_layout_constraintRight_toLeftOf, 29);
    mapToConstant.append(R.styleable.Constraint_layout_constraintRight_toRightOf, 30);
    mapToConstant.append(R.styleable.Constraint_layout_constraintTop_toTopOf, 36);
    mapToConstant.append(R.styleable.Constraint_layout_constraintTop_toBottomOf, 35);
    mapToConstant.append(R.styleable.Constraint_layout_constraintBottom_toTopOf, 4);
    mapToConstant.append(R.styleable.Constraint_layout_constraintBottom_toBottomOf, 3);
    mapToConstant.append(R.styleable.Constraint_layout_constraintBaseline_toBaselineOf, 1);
    mapToConstant.append(R.styleable.Constraint_layout_constraintBaseline_toTopOf, 91);
    mapToConstant.append(R.styleable.Constraint_layout_constraintBaseline_toBottomOf, 92);
    mapToConstant.append(R.styleable.Constraint_layout_editor_absoluteX, 6);
    mapToConstant.append(R.styleable.Constraint_layout_editor_absoluteY, 7);
    mapToConstant.append(R.styleable.Constraint_layout_constraintGuide_begin, 17);
    mapToConstant.append(R.styleable.Constraint_layout_constraintGuide_end, 18);
    mapToConstant.append(R.styleable.Constraint_layout_constraintGuide_percent, 19);
    mapToConstant.append(R.styleable.Constraint_guidelineUseRtl, 99);
    mapToConstant.append(R.styleable.Constraint_android_orientation, 27);
    mapToConstant.append(R.styleable.Constraint_layout_constraintStart_toEndOf, 32);
    mapToConstant.append(R.styleable.Constraint_layout_constraintStart_toStartOf, 33);
    mapToConstant.append(R.styleable.Constraint_layout_constraintEnd_toStartOf, 10);
    mapToConstant.append(R.styleable.Constraint_layout_constraintEnd_toEndOf, 9);
    mapToConstant.append(R.styleable.Constraint_layout_goneMarginLeft, 13);
    mapToConstant.append(R.styleable.Constraint_layout_goneMarginTop, 16);
    mapToConstant.append(R.styleable.Constraint_layout_goneMarginRight, 14);
    mapToConstant.append(R.styleable.Constraint_layout_goneMarginBottom, 11);
    mapToConstant.append(R.styleable.Constraint_layout_goneMarginStart, 15);
    mapToConstant.append(R.styleable.Constraint_layout_goneMarginEnd, 12);
    mapToConstant.append(R.styleable.Constraint_layout_constraintVertical_weight, 40);
    mapToConstant.append(R.styleable.Constraint_layout_constraintHorizontal_weight, 39);
    mapToConstant.append(R.styleable.Constraint_layout_constraintHorizontal_chainStyle, 41);
    mapToConstant.append(R.styleable.Constraint_layout_constraintVertical_chainStyle, 42);
    mapToConstant.append(R.styleable.Constraint_layout_constraintHorizontal_bias, 20);
    mapToConstant.append(R.styleable.Constraint_layout_constraintVertical_bias, 37);
    mapToConstant.append(R.styleable.Constraint_layout_constraintDimensionRatio, 5);
    mapToConstant.append(R.styleable.Constraint_layout_constraintLeft_creator, 87);
    mapToConstant.append(R.styleable.Constraint_layout_constraintTop_creator, 87);
    mapToConstant.append(R.styleable.Constraint_layout_constraintRight_creator, 87);
    mapToConstant.append(R.styleable.Constraint_layout_constraintBottom_creator, 87);
    mapToConstant.append(R.styleable.Constraint_layout_constraintBaseline_creator, 87);
    mapToConstant.append(R.styleable.Constraint_android_layout_marginLeft, 24);
    mapToConstant.append(R.styleable.Constraint_android_layout_marginRight, 28);
    mapToConstant.append(R.styleable.Constraint_android_layout_marginStart, 31);
    mapToConstant.append(R.styleable.Constraint_android_layout_marginEnd, 8);
    mapToConstant.append(R.styleable.Constraint_android_layout_marginTop, 34);
    mapToConstant.append(R.styleable.Constraint_android_layout_marginBottom, 2);
    mapToConstant.append(R.styleable.Constraint_android_layout_width, 23);
    mapToConstant.append(R.styleable.Constraint_android_layout_height, 21);
    mapToConstant.append(R.styleable.Constraint_layout_constraintWidth, 95);
    mapToConstant.append(R.styleable.Constraint_layout_constraintHeight, 96);
    mapToConstant.append(R.styleable.Constraint_android_visibility, 22);
    mapToConstant.append(R.styleable.Constraint_android_alpha, 43);
    mapToConstant.append(R.styleable.Constraint_android_elevation, 44);
    mapToConstant.append(R.styleable.Constraint_android_rotationX, 45);
    mapToConstant.append(R.styleable.Constraint_android_rotationY, 46);
    mapToConstant.append(R.styleable.Constraint_android_rotation, 60);
    mapToConstant.append(R.styleable.Constraint_android_scaleX, 47);
    mapToConstant.append(R.styleable.Constraint_android_scaleY, 48);
    mapToConstant.append(R.styleable.Constraint_android_transformPivotX, 49);
    mapToConstant.append(R.styleable.Constraint_android_transformPivotY, 50);
    mapToConstant.append(R.styleable.Constraint_android_translationX, 51);
    mapToConstant.append(R.styleable.Constraint_android_translationY, 52);
    mapToConstant.append(R.styleable.Constraint_android_translationZ, 53);
    mapToConstant.append(R.styleable.Constraint_layout_constraintWidth_default, 54);
    mapToConstant.append(R.styleable.Constraint_layout_constraintHeight_default, 55);
    mapToConstant.append(R.styleable.Constraint_layout_constraintWidth_max, 56);
    mapToConstant.append(R.styleable.Constraint_layout_constraintHeight_max, 57);
    mapToConstant.append(R.styleable.Constraint_layout_constraintWidth_min, 58);
    mapToConstant.append(R.styleable.Constraint_layout_constraintHeight_min, 59);
    mapToConstant.append(R.styleable.Constraint_layout_constraintCircle, 61);
    mapToConstant.append(R.styleable.Constraint_layout_constraintCircleRadius, 62);
    mapToConstant.append(R.styleable.Constraint_layout_constraintCircleAngle, 63);
    mapToConstant.append(R.styleable.Constraint_animateRelativeTo, 64);
    mapToConstant.append(R.styleable.Constraint_transitionEasing, 65);
    mapToConstant.append(R.styleable.Constraint_drawPath, 66);
    mapToConstant.append(R.styleable.Constraint_transitionPathRotate, 67);
    mapToConstant.append(R.styleable.Constraint_motionStagger, 79);
    mapToConstant.append(R.styleable.Constraint_android_id, 38);
    mapToConstant.append(R.styleable.Constraint_motionProgress, 68);
    mapToConstant.append(R.styleable.Constraint_layout_constraintWidth_percent, 69);
    mapToConstant.append(R.styleable.Constraint_layout_constraintHeight_percent, 70);
    mapToConstant.append(R.styleable.Constraint_layout_wrapBehaviorInParent, 97);
    mapToConstant.append(R.styleable.Constraint_chainUseRtl, 71);
    mapToConstant.append(R.styleable.Constraint_barrierDirection, 72);
    mapToConstant.append(R.styleable.Constraint_barrierMargin, 73);
    mapToConstant.append(R.styleable.Constraint_constraint_referenced_ids, 74);
    mapToConstant.append(R.styleable.Constraint_barrierAllowsGoneWidgets, 75);
    mapToConstant.append(R.styleable.Constraint_pathMotionArc, 76);
    mapToConstant.append(R.styleable.Constraint_layout_constraintTag, 77);
    mapToConstant.append(R.styleable.Constraint_visibilityMode, 78);
    mapToConstant.append(R.styleable.Constraint_layout_constrainedWidth, 80);
    mapToConstant.append(R.styleable.Constraint_layout_constrainedHeight, 81);
    mapToConstant.append(R.styleable.Constraint_polarRelativeTo, 82);
    mapToConstant.append(R.styleable.Constraint_transformPivotTarget, 83);
    mapToConstant.append(R.styleable.Constraint_quantizeMotionSteps, 84);
    mapToConstant.append(R.styleable.Constraint_quantizeMotionPhase, 85);
    mapToConstant.append(R.styleable.Constraint_quantizeMotionInterpolator, 86);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_editor_absoluteY, 6);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_editor_absoluteY, 7);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_android_orientation, 27);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_goneMarginLeft, 13);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_goneMarginTop, 16);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_goneMarginRight, 14);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_goneMarginBottom, 11);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_goneMarginStart, 15);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_goneMarginEnd, 12);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintVertical_weight, 40);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintHorizontal_weight, 39);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintHorizontal_chainStyle, 41);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintVertical_chainStyle, 42);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintHorizontal_bias, 20);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintVertical_bias, 37);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintDimensionRatio, 5);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintLeft_creator, 87);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintTop_creator, 87);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintRight_creator, 87);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintBottom_creator, 87);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintBaseline_creator, 87);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_android_layout_marginLeft, 24);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_android_layout_marginRight, 28);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_android_layout_marginStart, 31);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_android_layout_marginEnd, 8);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_android_layout_marginTop, 34);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_android_layout_marginBottom, 2);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_android_layout_width, 23);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_android_layout_height, 21);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintWidth, 95);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintHeight, 96);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_android_visibility, 22);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_android_alpha, 43);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_android_elevation, 44);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_android_rotationX, 45);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_android_rotationY, 46);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_android_rotation, 60);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_android_scaleX, 47);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_android_scaleY, 48);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_android_transformPivotX, 49);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_android_transformPivotY, 50);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_android_translationX, 51);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_android_translationY, 52);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_android_translationZ, 53);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintWidth_default, 54);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintHeight_default, 55);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintWidth_max, 56);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintHeight_max, 57);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintWidth_min, 58);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintHeight_min, 59);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintCircleRadius, 62);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintCircleAngle, 63);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_animateRelativeTo, 64);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_transitionEasing, 65);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_drawPath, 66);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_transitionPathRotate, 67);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_motionStagger, 79);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_android_id, 38);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_motionTarget, 98);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_motionProgress, 68);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintWidth_percent, 69);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintHeight_percent, 70);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_chainUseRtl, 71);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_barrierDirection, 72);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_barrierMargin, 73);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_constraint_referenced_ids, 74);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_barrierAllowsGoneWidgets, 75);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_pathMotionArc, 76);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constraintTag, 77);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_visibilityMode, 78);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constrainedWidth, 80);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_constrainedHeight, 81);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_polarRelativeTo, 82);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_transformPivotTarget, 83);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_quantizeMotionSteps, 84);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_quantizeMotionPhase, 85);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_quantizeMotionInterpolator, 86);
    overrideMapToConstant.append(R.styleable.ConstraintOverride_layout_wrapBehaviorInParent, 97);
  }
  
  private void addAttributes(ConstraintAttribute.AttributeType paramAttributeType, String... paramVarArgs)
  {
    for (int i = 0; i < paramVarArgs.length; i++)
    {
      ConstraintAttribute localConstraintAttribute;
      if (this.mSavedAttributes.containsKey(paramVarArgs[i]))
      {
        localConstraintAttribute = (ConstraintAttribute)this.mSavedAttributes.get(paramVarArgs[i]);
        if ((localConstraintAttribute != null) && (localConstraintAttribute.getType() != paramAttributeType)) {
          throw new IllegalArgumentException("ConstraintAttribute is already a " + localConstraintAttribute.getType().name());
        }
      }
      else
      {
        localConstraintAttribute = new ConstraintAttribute(paramVarArgs[i], paramAttributeType);
        this.mSavedAttributes.put(paramVarArgs[i], localConstraintAttribute);
      }
    }
  }
  
  public static Constraint buildDelta(Context paramContext, XmlPullParser paramXmlPullParser)
  {
    Object localObject = Xml.asAttributeSet(paramXmlPullParser);
    paramXmlPullParser = new Constraint();
    localObject = paramContext.obtainStyledAttributes((AttributeSet)localObject, R.styleable.ConstraintOverride);
    populateOverride(paramContext, paramXmlPullParser, (TypedArray)localObject);
    ((TypedArray)localObject).recycle();
    return paramXmlPullParser;
  }
  
  private int[] convertReferenceString(View paramView, String paramString)
  {
    String[] arrayOfString = paramString.split(",");
    Context localContext = paramView.getContext();
    paramString = new int[arrayOfString.length];
    int m = 0;
    int k = 0;
    while (k < arrayOfString.length)
    {
      Object localObject = arrayOfString[k].trim();
      int i = 0;
      try
      {
        j = R.id.class.getField((String)localObject).getInt(null);
      }
      catch (Exception localException)
      {
        j = i;
      }
      i = j;
      if (j == 0) {
        i = localContext.getResources().getIdentifier((String)localObject, "id", localContext.getPackageName());
      }
      int j = i;
      if (i == 0)
      {
        j = i;
        if (paramView.isInEditMode())
        {
          j = i;
          if ((paramView.getParent() instanceof ConstraintLayout))
          {
            localObject = ((ConstraintLayout)paramView.getParent()).getDesignInformation(0, localObject);
            j = i;
            if (localObject != null)
            {
              j = i;
              if ((localObject instanceof Integer)) {
                j = ((Integer)localObject).intValue();
              }
            }
          }
        }
      }
      paramString[m] = j;
      k++;
      m++;
    }
    paramView = paramString;
    if (m != arrayOfString.length) {
      paramView = Arrays.copyOf(paramString, m);
    }
    return paramView;
  }
  
  private void createHorizontalChain(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt, float[] paramArrayOfFloat, int paramInt5, int paramInt6, int paramInt7)
  {
    if (paramArrayOfInt.length >= 2)
    {
      if ((paramArrayOfFloat != null) && (paramArrayOfFloat.length != paramArrayOfInt.length)) {
        throw new IllegalArgumentException("must have 2 or more widgets in a chain");
      }
      if (paramArrayOfFloat != null) {
        get(paramArrayOfInt[0]).layout.horizontalWeight = paramArrayOfFloat[0];
      }
      get(paramArrayOfInt[0]).layout.horizontalChainStyle = paramInt5;
      connect(paramArrayOfInt[0], paramInt6, paramInt1, paramInt2, -1);
      for (paramInt1 = 1; paramInt1 < paramArrayOfInt.length; paramInt1++)
      {
        paramInt2 = paramArrayOfInt[paramInt1];
        connect(paramArrayOfInt[paramInt1], paramInt6, paramArrayOfInt[(paramInt1 - 1)], paramInt7, -1);
        connect(paramArrayOfInt[(paramInt1 - 1)], paramInt7, paramArrayOfInt[paramInt1], paramInt6, -1);
        if (paramArrayOfFloat != null) {
          get(paramArrayOfInt[paramInt1]).layout.horizontalWeight = paramArrayOfFloat[paramInt1];
        }
      }
      connect(paramArrayOfInt[(paramArrayOfInt.length - 1)], paramInt7, paramInt3, paramInt4, -1);
      return;
    }
    throw new IllegalArgumentException("must have 2 or more widgets in a chain");
  }
  
  private Constraint fillFromAttributeList(Context paramContext, AttributeSet paramAttributeSet, boolean paramBoolean)
  {
    Constraint localConstraint = new Constraint();
    int[] arrayOfInt;
    if (paramBoolean) {
      arrayOfInt = R.styleable.ConstraintOverride;
    } else {
      arrayOfInt = R.styleable.Constraint;
    }
    paramAttributeSet = paramContext.obtainStyledAttributes(paramAttributeSet, arrayOfInt);
    populateConstraint(paramContext, localConstraint, paramAttributeSet, paramBoolean);
    paramAttributeSet.recycle();
    return localConstraint;
  }
  
  private Constraint get(int paramInt)
  {
    if (!this.mConstraints.containsKey(Integer.valueOf(paramInt))) {
      this.mConstraints.put(Integer.valueOf(paramInt), new Constraint());
    }
    return (Constraint)this.mConstraints.get(Integer.valueOf(paramInt));
  }
  
  static String getDebugName(int paramInt)
  {
    for (Object localObject : ConstraintSet.class.getDeclaredFields()) {
      if ((((Field)localObject).getName().contains("_")) && (((Field)localObject).getType() == Integer.TYPE) && (Modifier.isStatic(((Field)localObject).getModifiers())) && (Modifier.isFinal(((Field)localObject).getModifiers()))) {
        try
        {
          if (((Field)localObject).getInt(null) == paramInt)
          {
            localObject = ((Field)localObject).getName();
            return (String)localObject;
          }
        }
        catch (IllegalAccessException localIllegalAccessException)
        {
          localIllegalAccessException.printStackTrace();
        }
      }
    }
    return "UNKNOWN";
  }
  
  static String getLine(Context paramContext, int paramInt, XmlPullParser paramXmlPullParser)
  {
    StringBuilder localStringBuilder = new StringBuilder().append(".(");
    paramContext = Debug.getName(paramContext, paramInt);
    Log5ECF72.a(paramContext);
    LogE84000.a(paramContext);
    Log229316.a(paramContext);
    return paramContext + ".xml:" + paramXmlPullParser.getLineNumber() + ") \"" + paramXmlPullParser.getName() + "\"";
  }
  
  private static int lookupID(TypedArray paramTypedArray, int paramInt1, int paramInt2)
  {
    int i = paramTypedArray.getResourceId(paramInt1, paramInt2);
    paramInt2 = i;
    if (i == -1) {
      paramInt2 = paramTypedArray.getInt(paramInt1, -1);
    }
    return paramInt2;
  }
  
  static void parseDimensionConstraints(Object paramObject, TypedArray paramTypedArray, int paramInt1, int paramInt2)
  {
    if (paramObject == null) {
      return;
    }
    int j = paramTypedArray.peekValue(paramInt1).type;
    int i = 0;
    boolean bool = false;
    switch (j)
    {
    case 4: 
    default: 
      paramInt1 = paramTypedArray.getInt(paramInt1, 0);
      switch (paramInt1)
      {
      default: 
        paramInt1 = i;
      }
      break;
    case 5: 
      paramInt1 = paramTypedArray.getDimensionPixelSize(paramInt1, 0);
      break;
    case 3: 
      parseDimensionConstraintsString(paramObject, paramTypedArray.getString(paramInt1), paramInt2);
      return;
      break;
      paramInt1 = 0;
      break;
      paramInt1 = -2;
      bool = true;
    }
    if ((paramObject instanceof ConstraintLayout.LayoutParams))
    {
      paramObject = (ConstraintLayout.LayoutParams)paramObject;
      if (paramInt2 == 0)
      {
        ((ConstraintLayout.LayoutParams)paramObject).width = paramInt1;
        ((ConstraintLayout.LayoutParams)paramObject).constrainedWidth = bool;
      }
      else
      {
        ((ConstraintLayout.LayoutParams)paramObject).height = paramInt1;
        ((ConstraintLayout.LayoutParams)paramObject).constrainedHeight = bool;
      }
    }
    else if ((paramObject instanceof Layout))
    {
      paramObject = (Layout)paramObject;
      if (paramInt2 == 0)
      {
        ((Layout)paramObject).mWidth = paramInt1;
        ((Layout)paramObject).constrainedWidth = bool;
      }
      else
      {
        ((Layout)paramObject).mHeight = paramInt1;
        ((Layout)paramObject).constrainedHeight = bool;
      }
    }
    else if ((paramObject instanceof ConstraintSet.Constraint.Delta))
    {
      paramObject = (ConstraintSet.Constraint.Delta)paramObject;
      if (paramInt2 == 0)
      {
        ((ConstraintSet.Constraint.Delta)paramObject).add(23, paramInt1);
        ((ConstraintSet.Constraint.Delta)paramObject).add(80, bool);
      }
      else
      {
        ((ConstraintSet.Constraint.Delta)paramObject).add(21, paramInt1);
        ((ConstraintSet.Constraint.Delta)paramObject).add(81, bool);
      }
    }
  }
  
  static void parseDimensionConstraintsString(Object paramObject, String paramString, int paramInt)
  {
    if (paramString == null) {
      return;
    }
    int i = paramString.indexOf('=');
    int j = paramString.length();
    if ((i > 0) && (i < j - 1))
    {
      String str1 = paramString.substring(0, i);
      String str2 = paramString.substring(i + 1);
      if (str2.length() > 0)
      {
        paramString = str1.trim();
        str1 = str2.trim();
        if ("ratio".equalsIgnoreCase(paramString))
        {
          if ((paramObject instanceof ConstraintLayout.LayoutParams))
          {
            paramObject = (ConstraintLayout.LayoutParams)paramObject;
            if (paramInt == 0) {
              ((ConstraintLayout.LayoutParams)paramObject).width = 0;
            } else {
              ((ConstraintLayout.LayoutParams)paramObject).height = 0;
            }
            parseDimensionRatioString((ConstraintLayout.LayoutParams)paramObject, str1);
          }
          else if ((paramObject instanceof Layout))
          {
            ((Layout)paramObject).dimensionRatio = str1;
          }
          else if ((paramObject instanceof ConstraintSet.Constraint.Delta))
          {
            ((ConstraintSet.Constraint.Delta)paramObject).add(5, str1);
            return;
          }
        }
        else
        {
          float f;
          if ("weight".equalsIgnoreCase(paramString)) {
            try
            {
              f = Float.parseFloat(str1);
              if ((paramObject instanceof ConstraintLayout.LayoutParams))
              {
                paramObject = (ConstraintLayout.LayoutParams)paramObject;
                if (paramInt == 0)
                {
                  ((ConstraintLayout.LayoutParams)paramObject).width = 0;
                  ((ConstraintLayout.LayoutParams)paramObject).horizontalWeight = f;
                }
                else
                {
                  ((ConstraintLayout.LayoutParams)paramObject).height = 0;
                  ((ConstraintLayout.LayoutParams)paramObject).verticalWeight = f;
                }
              }
              else if ((paramObject instanceof Layout))
              {
                paramObject = (Layout)paramObject;
                if (paramInt == 0)
                {
                  ((Layout)paramObject).mWidth = 0;
                  ((Layout)paramObject).horizontalWeight = f;
                }
                else
                {
                  ((Layout)paramObject).mHeight = 0;
                  ((Layout)paramObject).verticalWeight = f;
                }
              }
              else if ((paramObject instanceof ConstraintSet.Constraint.Delta))
              {
                paramObject = (ConstraintSet.Constraint.Delta)paramObject;
                if (paramInt == 0)
                {
                  ((ConstraintSet.Constraint.Delta)paramObject).add(23, 0);
                  ((ConstraintSet.Constraint.Delta)paramObject).add(39, f);
                }
                else
                {
                  ((ConstraintSet.Constraint.Delta)paramObject).add(21, 0);
                  ((ConstraintSet.Constraint.Delta)paramObject).add(40, f);
                }
              }
            }
            catch (NumberFormatException paramObject) {}
          } else if ("parent".equalsIgnoreCase(paramString)) {
            try
            {
              f = Math.max(0.0F, Math.min(1.0F, Float.parseFloat(str1)));
              if ((paramObject instanceof ConstraintLayout.LayoutParams))
              {
                paramObject = (ConstraintLayout.LayoutParams)paramObject;
                if (paramInt == 0)
                {
                  ((ConstraintLayout.LayoutParams)paramObject).width = 0;
                  ((ConstraintLayout.LayoutParams)paramObject).matchConstraintPercentWidth = f;
                  ((ConstraintLayout.LayoutParams)paramObject).matchConstraintDefaultWidth = 2;
                }
                else
                {
                  ((ConstraintLayout.LayoutParams)paramObject).height = 0;
                  ((ConstraintLayout.LayoutParams)paramObject).matchConstraintPercentHeight = f;
                  ((ConstraintLayout.LayoutParams)paramObject).matchConstraintDefaultHeight = 2;
                }
              }
              else if ((paramObject instanceof Layout))
              {
                paramObject = (Layout)paramObject;
                if (paramInt == 0)
                {
                  ((Layout)paramObject).mWidth = 0;
                  ((Layout)paramObject).widthPercent = f;
                  ((Layout)paramObject).widthDefault = 2;
                }
                else
                {
                  ((Layout)paramObject).mHeight = 0;
                  ((Layout)paramObject).heightPercent = f;
                  ((Layout)paramObject).heightDefault = 2;
                }
              }
              else if ((paramObject instanceof ConstraintSet.Constraint.Delta))
              {
                paramObject = (ConstraintSet.Constraint.Delta)paramObject;
                if (paramInt == 0)
                {
                  ((ConstraintSet.Constraint.Delta)paramObject).add(23, 0);
                  ((ConstraintSet.Constraint.Delta)paramObject).add(54, 2);
                }
                else
                {
                  ((ConstraintSet.Constraint.Delta)paramObject).add(21, 0);
                  ((ConstraintSet.Constraint.Delta)paramObject).add(55, 2);
                }
              }
            }
            catch (NumberFormatException paramObject) {}
          }
        }
      }
    }
  }
  
  static void parseDimensionRatioString(ConstraintLayout.LayoutParams paramLayoutParams, String paramString)
  {
    float f2 = NaN.0F;
    int i = -1;
    float f1 = f2;
    int j = i;
    if (paramString != null)
    {
      int k = paramString.length();
      j = paramString.indexOf(',');
      String str1;
      if ((j > 0) && (j < k - 1))
      {
        str1 = paramString.substring(0, j);
        if (str1.equalsIgnoreCase("W")) {
          i = 0;
        } else if (str1.equalsIgnoreCase("H")) {
          i = 1;
        }
        j++;
      }
      else
      {
        j = 0;
      }
      int m = paramString.indexOf(':');
      if ((m >= 0) && (m < k - 1))
      {
        str1 = paramString.substring(j, m);
        String str3 = paramString.substring(m + 1);
        f1 = f2;
        if (str1.length() > 0)
        {
          f1 = f2;
          if (str3.length() > 0) {
            try
            {
              float f4 = Float.parseFloat(str1);
              float f3 = Float.parseFloat(str3);
              f1 = f2;
              if (f4 > 0.0F)
              {
                f1 = f2;
                if (f3 > 0.0F) {
                  if (i == 1) {
                    f1 = Math.abs(f3 / f4);
                  } else {
                    f1 = Math.abs(f4 / f3);
                  }
                }
              }
            }
            catch (NumberFormatException localNumberFormatException1)
            {
              f1 = f2;
            }
          }
        }
        j = i;
      }
      else
      {
        String str2 = paramString.substring(j);
        f1 = f2;
        j = i;
        if (str2.length() > 0) {
          try
          {
            f1 = Float.parseFloat(str2);
            j = i;
          }
          catch (NumberFormatException localNumberFormatException2)
          {
            j = i;
            f1 = f2;
          }
        }
      }
    }
    paramLayoutParams.dimensionRatio = paramString;
    paramLayoutParams.dimensionRatioValue = f1;
    paramLayoutParams.dimensionRatioSide = j;
  }
  
  private void populateConstraint(Context paramContext, Constraint paramConstraint, TypedArray paramTypedArray, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      populateOverride(paramContext, paramConstraint, paramTypedArray);
      return;
    }
    int j = paramTypedArray.getIndexCount();
    for (int i = 0; i < j; i++)
    {
      int k = paramTypedArray.getIndex(i);
      if ((k != R.styleable.Constraint_android_id) && (R.styleable.Constraint_android_layout_marginStart != k) && (R.styleable.Constraint_android_layout_marginEnd != k))
      {
        paramConstraint.motion.mApply = true;
        paramConstraint.layout.mApply = true;
        paramConstraint.propertySet.mApply = true;
        paramConstraint.transform.mApply = true;
      }
      StringBuilder localStringBuilder;
      switch (mapToConstant.get(k))
      {
      case 88: 
      case 89: 
      case 90: 
      default: 
        localStringBuilder = new StringBuilder().append("Unknown attribute 0x");
        paramContext = Integer.toHexString(k);
        Log5ECF72.a(paramContext);
        LogE84000.a(paramContext);
        Log229316.a(paramContext);
        Log.w("ConstraintSet", paramContext + "   " + mapToConstant.get(k));
        break;
      case 97: 
        paramConstraint.layout.mWrapBehavior = paramTypedArray.getInt(k, paramConstraint.layout.mWrapBehavior);
        break;
      case 96: 
        parseDimensionConstraints(paramConstraint.layout, paramTypedArray, k, 1);
        break;
      case 95: 
        parseDimensionConstraints(paramConstraint.layout, paramTypedArray, k, 0);
        break;
      case 94: 
        paramConstraint.layout.goneBaselineMargin = paramTypedArray.getDimensionPixelSize(k, paramConstraint.layout.goneBaselineMargin);
        break;
      case 93: 
        paramConstraint.layout.baselineMargin = paramTypedArray.getDimensionPixelSize(k, paramConstraint.layout.baselineMargin);
        break;
      case 92: 
        paramConstraint.layout.baselineToBottom = lookupID(paramTypedArray, k, paramConstraint.layout.baselineToBottom);
        break;
      case 91: 
        paramConstraint.layout.baselineToTop = lookupID(paramTypedArray, k, paramConstraint.layout.baselineToTop);
        break;
      case 87: 
        localStringBuilder = new StringBuilder().append("unused attribute 0x");
        paramContext = Integer.toHexString(k);
        Log5ECF72.a(paramContext);
        LogE84000.a(paramContext);
        Log229316.a(paramContext);
        Log.w("ConstraintSet", paramContext + "   " + mapToConstant.get(k));
        break;
      case 86: 
        paramContext = paramTypedArray.peekValue(k);
        if (paramContext.type == 1)
        {
          paramConstraint.motion.mQuantizeInterpolatorID = paramTypedArray.getResourceId(k, -1);
          if (paramConstraint.motion.mQuantizeInterpolatorID != -1) {
            paramConstraint.motion.mQuantizeInterpolatorType = -2;
          }
        }
        else if (paramContext.type == 3)
        {
          paramConstraint.motion.mQuantizeInterpolatorString = paramTypedArray.getString(k);
          if (paramConstraint.motion.mQuantizeInterpolatorString.indexOf("/") > 0)
          {
            paramConstraint.motion.mQuantizeInterpolatorID = paramTypedArray.getResourceId(k, -1);
            paramConstraint.motion.mQuantizeInterpolatorType = -2;
          }
          else
          {
            paramConstraint.motion.mQuantizeInterpolatorType = -1;
          }
        }
        else
        {
          paramConstraint.motion.mQuantizeInterpolatorType = paramTypedArray.getInteger(k, paramConstraint.motion.mQuantizeInterpolatorID);
        }
        break;
      case 85: 
        paramConstraint.motion.mQuantizeMotionPhase = paramTypedArray.getFloat(k, paramConstraint.motion.mQuantizeMotionPhase);
        break;
      case 84: 
        paramConstraint.motion.mQuantizeMotionSteps = paramTypedArray.getInteger(k, paramConstraint.motion.mQuantizeMotionSteps);
        break;
      case 83: 
        paramConstraint.transform.transformPivotTarget = lookupID(paramTypedArray, k, paramConstraint.transform.transformPivotTarget);
        break;
      case 82: 
        paramConstraint.motion.mAnimateCircleAngleTo = paramTypedArray.getInteger(k, paramConstraint.motion.mAnimateCircleAngleTo);
        break;
      case 81: 
        paramConstraint.layout.constrainedHeight = paramTypedArray.getBoolean(k, paramConstraint.layout.constrainedHeight);
        break;
      case 80: 
        paramConstraint.layout.constrainedWidth = paramTypedArray.getBoolean(k, paramConstraint.layout.constrainedWidth);
        break;
      case 79: 
        paramConstraint.motion.mMotionStagger = paramTypedArray.getFloat(k, paramConstraint.motion.mMotionStagger);
        break;
      case 78: 
        paramConstraint.propertySet.mVisibilityMode = paramTypedArray.getInt(k, paramConstraint.propertySet.mVisibilityMode);
        break;
      case 77: 
        paramConstraint.layout.mConstraintTag = paramTypedArray.getString(k);
        break;
      case 76: 
        paramConstraint.motion.mPathMotionArc = paramTypedArray.getInt(k, paramConstraint.motion.mPathMotionArc);
        break;
      case 75: 
        paramConstraint.layout.mBarrierAllowsGoneWidgets = paramTypedArray.getBoolean(k, paramConstraint.layout.mBarrierAllowsGoneWidgets);
        break;
      case 74: 
        paramConstraint.layout.mReferenceIdString = paramTypedArray.getString(k);
        break;
      case 73: 
        paramConstraint.layout.mBarrierMargin = paramTypedArray.getDimensionPixelSize(k, paramConstraint.layout.mBarrierMargin);
        break;
      case 72: 
        paramConstraint.layout.mBarrierDirection = paramTypedArray.getInt(k, paramConstraint.layout.mBarrierDirection);
        break;
      case 71: 
        Log.e("ConstraintSet", "CURRENTLY UNSUPPORTED");
        break;
      case 70: 
        paramConstraint.layout.heightPercent = paramTypedArray.getFloat(k, 1.0F);
        break;
      case 69: 
        paramConstraint.layout.widthPercent = paramTypedArray.getFloat(k, 1.0F);
        break;
      case 68: 
        paramConstraint.propertySet.mProgress = paramTypedArray.getFloat(k, paramConstraint.propertySet.mProgress);
        break;
      case 67: 
        paramConstraint.motion.mPathRotate = paramTypedArray.getFloat(k, paramConstraint.motion.mPathRotate);
        break;
      case 66: 
        paramConstraint.motion.mDrawPath = paramTypedArray.getInt(k, 0);
        break;
      case 65: 
        if (paramTypedArray.peekValue(k).type == 3) {
          paramConstraint.motion.mTransitionEasing = paramTypedArray.getString(k);
        } else {
          paramConstraint.motion.mTransitionEasing = androidx.constraintlayout.core.motion.utils.Easing.NAMED_EASING[paramTypedArray.getInteger(k, 0)];
        }
        break;
      case 64: 
        paramConstraint.motion.mAnimateRelativeTo = lookupID(paramTypedArray, k, paramConstraint.motion.mAnimateRelativeTo);
        break;
      case 63: 
        paramConstraint.layout.circleAngle = paramTypedArray.getFloat(k, paramConstraint.layout.circleAngle);
        break;
      case 62: 
        paramConstraint.layout.circleRadius = paramTypedArray.getDimensionPixelSize(k, paramConstraint.layout.circleRadius);
        break;
      case 61: 
        paramConstraint.layout.circleConstraint = lookupID(paramTypedArray, k, paramConstraint.layout.circleConstraint);
        break;
      case 60: 
        paramConstraint.transform.rotation = paramTypedArray.getFloat(k, paramConstraint.transform.rotation);
        break;
      case 59: 
        paramConstraint.layout.heightMin = paramTypedArray.getDimensionPixelSize(k, paramConstraint.layout.heightMin);
        break;
      case 58: 
        paramConstraint.layout.widthMin = paramTypedArray.getDimensionPixelSize(k, paramConstraint.layout.widthMin);
        break;
      case 57: 
        paramConstraint.layout.heightMax = paramTypedArray.getDimensionPixelSize(k, paramConstraint.layout.heightMax);
        break;
      case 56: 
        paramConstraint.layout.widthMax = paramTypedArray.getDimensionPixelSize(k, paramConstraint.layout.widthMax);
        break;
      case 55: 
        paramConstraint.layout.heightDefault = paramTypedArray.getInt(k, paramConstraint.layout.heightDefault);
        break;
      case 54: 
        paramConstraint.layout.widthDefault = paramTypedArray.getInt(k, paramConstraint.layout.widthDefault);
        break;
      case 53: 
        if (Build.VERSION.SDK_INT >= 21) {
          paramConstraint.transform.translationZ = paramTypedArray.getDimension(k, paramConstraint.transform.translationZ);
        }
        break;
      case 52: 
        paramConstraint.transform.translationY = paramTypedArray.getDimension(k, paramConstraint.transform.translationY);
        break;
      case 51: 
        paramConstraint.transform.translationX = paramTypedArray.getDimension(k, paramConstraint.transform.translationX);
        break;
      case 50: 
        paramConstraint.transform.transformPivotY = paramTypedArray.getDimension(k, paramConstraint.transform.transformPivotY);
        break;
      case 49: 
        paramConstraint.transform.transformPivotX = paramTypedArray.getDimension(k, paramConstraint.transform.transformPivotX);
        break;
      case 48: 
        paramConstraint.transform.scaleY = paramTypedArray.getFloat(k, paramConstraint.transform.scaleY);
        break;
      case 47: 
        paramConstraint.transform.scaleX = paramTypedArray.getFloat(k, paramConstraint.transform.scaleX);
        break;
      case 46: 
        paramConstraint.transform.rotationY = paramTypedArray.getFloat(k, paramConstraint.transform.rotationY);
        break;
      case 45: 
        paramConstraint.transform.rotationX = paramTypedArray.getFloat(k, paramConstraint.transform.rotationX);
        break;
      case 44: 
        if (Build.VERSION.SDK_INT >= 21)
        {
          paramConstraint.transform.applyElevation = true;
          paramConstraint.transform.elevation = paramTypedArray.getDimension(k, paramConstraint.transform.elevation);
        }
        break;
      case 43: 
        paramConstraint.propertySet.alpha = paramTypedArray.getFloat(k, paramConstraint.propertySet.alpha);
        break;
      case 42: 
        paramConstraint.layout.verticalChainStyle = paramTypedArray.getInt(k, paramConstraint.layout.verticalChainStyle);
        break;
      case 41: 
        paramConstraint.layout.horizontalChainStyle = paramTypedArray.getInt(k, paramConstraint.layout.horizontalChainStyle);
        break;
      case 40: 
        paramConstraint.layout.verticalWeight = paramTypedArray.getFloat(k, paramConstraint.layout.verticalWeight);
        break;
      case 39: 
        paramConstraint.layout.horizontalWeight = paramTypedArray.getFloat(k, paramConstraint.layout.horizontalWeight);
        break;
      case 38: 
        paramConstraint.mViewId = paramTypedArray.getResourceId(k, paramConstraint.mViewId);
        break;
      case 37: 
        paramConstraint.layout.verticalBias = paramTypedArray.getFloat(k, paramConstraint.layout.verticalBias);
        break;
      case 36: 
        paramConstraint.layout.topToTop = lookupID(paramTypedArray, k, paramConstraint.layout.topToTop);
        break;
      case 35: 
        paramConstraint.layout.topToBottom = lookupID(paramTypedArray, k, paramConstraint.layout.topToBottom);
        break;
      case 34: 
        paramConstraint.layout.topMargin = paramTypedArray.getDimensionPixelSize(k, paramConstraint.layout.topMargin);
        break;
      case 33: 
        paramConstraint.layout.startToStart = lookupID(paramTypedArray, k, paramConstraint.layout.startToStart);
        break;
      case 32: 
        paramConstraint.layout.startToEnd = lookupID(paramTypedArray, k, paramConstraint.layout.startToEnd);
        break;
      case 31: 
        if (Build.VERSION.SDK_INT >= 17) {
          paramConstraint.layout.startMargin = paramTypedArray.getDimensionPixelSize(k, paramConstraint.layout.startMargin);
        }
        break;
      case 30: 
        paramConstraint.layout.rightToRight = lookupID(paramTypedArray, k, paramConstraint.layout.rightToRight);
        break;
      case 29: 
        paramConstraint.layout.rightToLeft = lookupID(paramTypedArray, k, paramConstraint.layout.rightToLeft);
        break;
      case 28: 
        paramConstraint.layout.rightMargin = paramTypedArray.getDimensionPixelSize(k, paramConstraint.layout.rightMargin);
        break;
      case 27: 
        paramConstraint.layout.orientation = paramTypedArray.getInt(k, paramConstraint.layout.orientation);
        break;
      case 26: 
        paramConstraint.layout.leftToRight = lookupID(paramTypedArray, k, paramConstraint.layout.leftToRight);
        break;
      case 25: 
        paramConstraint.layout.leftToLeft = lookupID(paramTypedArray, k, paramConstraint.layout.leftToLeft);
        break;
      case 24: 
        paramConstraint.layout.leftMargin = paramTypedArray.getDimensionPixelSize(k, paramConstraint.layout.leftMargin);
        break;
      case 23: 
        paramConstraint.layout.mWidth = paramTypedArray.getLayoutDimension(k, paramConstraint.layout.mWidth);
        break;
      case 22: 
        paramConstraint.propertySet.visibility = paramTypedArray.getInt(k, paramConstraint.propertySet.visibility);
        paramConstraint.propertySet.visibility = VISIBILITY_FLAGS[paramConstraint.propertySet.visibility];
        break;
      case 21: 
        paramConstraint.layout.mHeight = paramTypedArray.getLayoutDimension(k, paramConstraint.layout.mHeight);
        break;
      case 20: 
        paramConstraint.layout.horizontalBias = paramTypedArray.getFloat(k, paramConstraint.layout.horizontalBias);
        break;
      case 19: 
        paramConstraint.layout.guidePercent = paramTypedArray.getFloat(k, paramConstraint.layout.guidePercent);
        break;
      case 18: 
        paramConstraint.layout.guideEnd = paramTypedArray.getDimensionPixelOffset(k, paramConstraint.layout.guideEnd);
        break;
      case 17: 
        paramConstraint.layout.guideBegin = paramTypedArray.getDimensionPixelOffset(k, paramConstraint.layout.guideBegin);
        break;
      case 16: 
        paramConstraint.layout.goneTopMargin = paramTypedArray.getDimensionPixelSize(k, paramConstraint.layout.goneTopMargin);
        break;
      case 15: 
        paramConstraint.layout.goneStartMargin = paramTypedArray.getDimensionPixelSize(k, paramConstraint.layout.goneStartMargin);
        break;
      case 14: 
        paramConstraint.layout.goneRightMargin = paramTypedArray.getDimensionPixelSize(k, paramConstraint.layout.goneRightMargin);
        break;
      case 13: 
        paramConstraint.layout.goneLeftMargin = paramTypedArray.getDimensionPixelSize(k, paramConstraint.layout.goneLeftMargin);
        break;
      case 12: 
        paramConstraint.layout.goneEndMargin = paramTypedArray.getDimensionPixelSize(k, paramConstraint.layout.goneEndMargin);
        break;
      case 11: 
        paramConstraint.layout.goneBottomMargin = paramTypedArray.getDimensionPixelSize(k, paramConstraint.layout.goneBottomMargin);
        break;
      case 10: 
        paramConstraint.layout.endToStart = lookupID(paramTypedArray, k, paramConstraint.layout.endToStart);
        break;
      case 9: 
        paramConstraint.layout.endToEnd = lookupID(paramTypedArray, k, paramConstraint.layout.endToEnd);
        break;
      case 8: 
        if (Build.VERSION.SDK_INT >= 17) {
          paramConstraint.layout.endMargin = paramTypedArray.getDimensionPixelSize(k, paramConstraint.layout.endMargin);
        }
        break;
      case 7: 
        paramConstraint.layout.editorAbsoluteY = paramTypedArray.getDimensionPixelOffset(k, paramConstraint.layout.editorAbsoluteY);
        break;
      case 6: 
        paramConstraint.layout.editorAbsoluteX = paramTypedArray.getDimensionPixelOffset(k, paramConstraint.layout.editorAbsoluteX);
        break;
      case 5: 
        paramConstraint.layout.dimensionRatio = paramTypedArray.getString(k);
        break;
      case 4: 
        paramConstraint.layout.bottomToTop = lookupID(paramTypedArray, k, paramConstraint.layout.bottomToTop);
        break;
      case 3: 
        paramConstraint.layout.bottomToBottom = lookupID(paramTypedArray, k, paramConstraint.layout.bottomToBottom);
        break;
      case 2: 
        paramConstraint.layout.bottomMargin = paramTypedArray.getDimensionPixelSize(k, paramConstraint.layout.bottomMargin);
        break;
      case 1: 
        paramConstraint.layout.baselineToBaseline = lookupID(paramTypedArray, k, paramConstraint.layout.baselineToBaseline);
      }
    }
    if (paramConstraint.layout.mReferenceIdString != null) {
      paramConstraint.layout.mReferenceIds = null;
    }
  }
  
  private static void populateOverride(Context paramContext, Constraint paramConstraint, TypedArray paramTypedArray)
  {
    int j = paramTypedArray.getIndexCount();
    paramContext = new ConstraintSet.Constraint.Delta();
    paramConstraint.mDelta = paramContext;
    paramConstraint.motion.mApply = false;
    paramConstraint.layout.mApply = false;
    paramConstraint.propertySet.mApply = false;
    paramConstraint.transform.mApply = false;
    for (int i = 0; i < j; i++)
    {
      int k = paramTypedArray.getIndex(i);
      Object localObject;
      String str;
      switch (overrideMapToConstant.get(k))
      {
      case 3: 
      case 4: 
      case 9: 
      case 10: 
      case 25: 
      case 26: 
      case 29: 
      case 30: 
      case 32: 
      case 33: 
      case 35: 
      case 36: 
      case 61: 
      case 88: 
      case 89: 
      case 90: 
      case 91: 
      case 92: 
      default: 
        localObject = new StringBuilder().append("Unknown attribute 0x");
        str = Integer.toHexString(k);
        Log5ECF72.a(str);
        LogE84000.a(str);
        Log229316.a(str);
        Log.w("ConstraintSet", str + "   " + mapToConstant.get(k));
        break;
      case 99: 
        paramContext.add(99, paramTypedArray.getBoolean(k, paramConstraint.layout.guidelineUseRtl));
        break;
      case 98: 
        if (MotionLayout.IS_IN_EDIT_MODE)
        {
          paramConstraint.mViewId = paramTypedArray.getResourceId(k, paramConstraint.mViewId);
          if (paramConstraint.mViewId == -1) {
            paramConstraint.mTargetString = paramTypedArray.getString(k);
          }
        }
        else if (paramTypedArray.peekValue(k).type == 3)
        {
          paramConstraint.mTargetString = paramTypedArray.getString(k);
        }
        else
        {
          paramConstraint.mViewId = paramTypedArray.getResourceId(k, paramConstraint.mViewId);
        }
        break;
      case 97: 
        paramContext.add(97, paramTypedArray.getInt(k, paramConstraint.layout.mWrapBehavior));
        break;
      case 96: 
        parseDimensionConstraints(paramContext, paramTypedArray, k, 1);
        break;
      case 95: 
        parseDimensionConstraints(paramContext, paramTypedArray, k, 0);
        break;
      case 94: 
        paramContext.add(94, paramTypedArray.getDimensionPixelSize(k, paramConstraint.layout.goneBaselineMargin));
        break;
      case 93: 
        paramContext.add(93, paramTypedArray.getDimensionPixelSize(k, paramConstraint.layout.baselineMargin));
        break;
      case 87: 
        localObject = new StringBuilder().append("unused attribute 0x");
        str = Integer.toHexString(k);
        Log5ECF72.a(str);
        LogE84000.a(str);
        Log229316.a(str);
        Log.w("ConstraintSet", str + "   " + mapToConstant.get(k));
        break;
      case 86: 
        localObject = paramTypedArray.peekValue(k);
        if (((TypedValue)localObject).type == 1)
        {
          paramConstraint.motion.mQuantizeInterpolatorID = paramTypedArray.getResourceId(k, -1);
          paramContext.add(89, paramConstraint.motion.mQuantizeInterpolatorID);
          if (paramConstraint.motion.mQuantizeInterpolatorID != -1)
          {
            paramConstraint.motion.mQuantizeInterpolatorType = -2;
            paramContext.add(88, paramConstraint.motion.mQuantizeInterpolatorType);
          }
        }
        else if (((TypedValue)localObject).type == 3)
        {
          paramConstraint.motion.mQuantizeInterpolatorString = paramTypedArray.getString(k);
          paramContext.add(90, paramConstraint.motion.mQuantizeInterpolatorString);
          if (paramConstraint.motion.mQuantizeInterpolatorString.indexOf("/") > 0)
          {
            paramConstraint.motion.mQuantizeInterpolatorID = paramTypedArray.getResourceId(k, -1);
            paramContext.add(89, paramConstraint.motion.mQuantizeInterpolatorID);
            paramConstraint.motion.mQuantizeInterpolatorType = -2;
            paramContext.add(88, paramConstraint.motion.mQuantizeInterpolatorType);
          }
          else
          {
            paramConstraint.motion.mQuantizeInterpolatorType = -1;
            paramContext.add(88, paramConstraint.motion.mQuantizeInterpolatorType);
          }
        }
        else
        {
          paramConstraint.motion.mQuantizeInterpolatorType = paramTypedArray.getInteger(k, paramConstraint.motion.mQuantizeInterpolatorID);
          paramContext.add(88, paramConstraint.motion.mQuantizeInterpolatorType);
        }
        break;
      case 85: 
        paramContext.add(85, paramTypedArray.getFloat(k, paramConstraint.motion.mQuantizeMotionPhase));
        break;
      case 84: 
        paramContext.add(84, paramTypedArray.getInteger(k, paramConstraint.motion.mQuantizeMotionSteps));
        break;
      case 83: 
        paramContext.add(83, lookupID(paramTypedArray, k, paramConstraint.transform.transformPivotTarget));
        break;
      case 82: 
        paramContext.add(82, paramTypedArray.getInteger(k, paramConstraint.motion.mAnimateCircleAngleTo));
        break;
      case 81: 
        paramContext.add(81, paramTypedArray.getBoolean(k, paramConstraint.layout.constrainedHeight));
        break;
      case 80: 
        paramContext.add(80, paramTypedArray.getBoolean(k, paramConstraint.layout.constrainedWidth));
        break;
      case 79: 
        paramContext.add(79, paramTypedArray.getFloat(k, paramConstraint.motion.mMotionStagger));
        break;
      case 78: 
        paramContext.add(78, paramTypedArray.getInt(k, paramConstraint.propertySet.mVisibilityMode));
        break;
      case 77: 
        paramContext.add(77, paramTypedArray.getString(k));
        break;
      case 76: 
        paramContext.add(76, paramTypedArray.getInt(k, paramConstraint.motion.mPathMotionArc));
        break;
      case 75: 
        paramContext.add(75, paramTypedArray.getBoolean(k, paramConstraint.layout.mBarrierAllowsGoneWidgets));
        break;
      case 74: 
        paramContext.add(74, paramTypedArray.getString(k));
        break;
      case 73: 
        paramContext.add(73, paramTypedArray.getDimensionPixelSize(k, paramConstraint.layout.mBarrierMargin));
        break;
      case 72: 
        paramContext.add(72, paramTypedArray.getInt(k, paramConstraint.layout.mBarrierDirection));
        break;
      case 71: 
        Log.e("ConstraintSet", "CURRENTLY UNSUPPORTED");
        break;
      case 70: 
        paramContext.add(70, paramTypedArray.getFloat(k, 1.0F));
        break;
      case 69: 
        paramContext.add(69, paramTypedArray.getFloat(k, 1.0F));
        break;
      case 68: 
        paramContext.add(68, paramTypedArray.getFloat(k, paramConstraint.propertySet.mProgress));
        break;
      case 67: 
        paramContext.add(67, paramTypedArray.getFloat(k, paramConstraint.motion.mPathRotate));
        break;
      case 66: 
        paramContext.add(66, paramTypedArray.getInt(k, 0));
        break;
      case 65: 
        if (paramTypedArray.peekValue(k).type == 3) {
          paramContext.add(65, paramTypedArray.getString(k));
        } else {
          paramContext.add(65, androidx.constraintlayout.core.motion.utils.Easing.NAMED_EASING[paramTypedArray.getInteger(k, 0)]);
        }
        break;
      case 64: 
        paramContext.add(64, lookupID(paramTypedArray, k, paramConstraint.motion.mAnimateRelativeTo));
        break;
      case 63: 
        paramContext.add(63, paramTypedArray.getFloat(k, paramConstraint.layout.circleAngle));
        break;
      case 62: 
        paramContext.add(62, paramTypedArray.getDimensionPixelSize(k, paramConstraint.layout.circleRadius));
        break;
      case 60: 
        paramContext.add(60, paramTypedArray.getFloat(k, paramConstraint.transform.rotation));
        break;
      case 59: 
        paramContext.add(59, paramTypedArray.getDimensionPixelSize(k, paramConstraint.layout.heightMin));
        break;
      case 58: 
        paramContext.add(58, paramTypedArray.getDimensionPixelSize(k, paramConstraint.layout.widthMin));
        break;
      case 57: 
        paramContext.add(57, paramTypedArray.getDimensionPixelSize(k, paramConstraint.layout.heightMax));
        break;
      case 56: 
        paramContext.add(56, paramTypedArray.getDimensionPixelSize(k, paramConstraint.layout.widthMax));
        break;
      case 55: 
        paramContext.add(55, paramTypedArray.getInt(k, paramConstraint.layout.heightDefault));
        break;
      case 54: 
        paramContext.add(54, paramTypedArray.getInt(k, paramConstraint.layout.widthDefault));
        break;
      case 53: 
        if (Build.VERSION.SDK_INT >= 21) {
          paramContext.add(53, paramTypedArray.getDimension(k, paramConstraint.transform.translationZ));
        }
        break;
      case 52: 
        paramContext.add(52, paramTypedArray.getDimension(k, paramConstraint.transform.translationY));
        break;
      case 51: 
        paramContext.add(51, paramTypedArray.getDimension(k, paramConstraint.transform.translationX));
        break;
      case 50: 
        paramContext.add(50, paramTypedArray.getDimension(k, paramConstraint.transform.transformPivotY));
        break;
      case 49: 
        paramContext.add(49, paramTypedArray.getDimension(k, paramConstraint.transform.transformPivotX));
        break;
      case 48: 
        paramContext.add(48, paramTypedArray.getFloat(k, paramConstraint.transform.scaleY));
        break;
      case 47: 
        paramContext.add(47, paramTypedArray.getFloat(k, paramConstraint.transform.scaleX));
        break;
      case 46: 
        paramContext.add(46, paramTypedArray.getFloat(k, paramConstraint.transform.rotationY));
        break;
      case 45: 
        paramContext.add(45, paramTypedArray.getFloat(k, paramConstraint.transform.rotationX));
        break;
      case 44: 
        if (Build.VERSION.SDK_INT >= 21)
        {
          paramContext.add(44, true);
          paramContext.add(44, paramTypedArray.getDimension(k, paramConstraint.transform.elevation));
        }
        break;
      case 43: 
        paramContext.add(43, paramTypedArray.getFloat(k, paramConstraint.propertySet.alpha));
        break;
      case 42: 
        paramContext.add(42, paramTypedArray.getInt(k, paramConstraint.layout.verticalChainStyle));
        break;
      case 41: 
        paramContext.add(41, paramTypedArray.getInt(k, paramConstraint.layout.horizontalChainStyle));
        break;
      case 40: 
        paramContext.add(40, paramTypedArray.getFloat(k, paramConstraint.layout.verticalWeight));
        break;
      case 39: 
        paramContext.add(39, paramTypedArray.getFloat(k, paramConstraint.layout.horizontalWeight));
        break;
      case 38: 
        paramConstraint.mViewId = paramTypedArray.getResourceId(k, paramConstraint.mViewId);
        paramContext.add(38, paramConstraint.mViewId);
        break;
      case 37: 
        paramContext.add(37, paramTypedArray.getFloat(k, paramConstraint.layout.verticalBias));
        break;
      case 34: 
        paramContext.add(34, paramTypedArray.getDimensionPixelSize(k, paramConstraint.layout.topMargin));
        break;
      case 31: 
        if (Build.VERSION.SDK_INT >= 17) {
          paramContext.add(31, paramTypedArray.getDimensionPixelSize(k, paramConstraint.layout.startMargin));
        }
        break;
      case 28: 
        paramContext.add(28, paramTypedArray.getDimensionPixelSize(k, paramConstraint.layout.rightMargin));
        break;
      case 27: 
        paramContext.add(27, paramTypedArray.getInt(k, paramConstraint.layout.orientation));
        break;
      case 24: 
        paramContext.add(24, paramTypedArray.getDimensionPixelSize(k, paramConstraint.layout.leftMargin));
        break;
      case 23: 
        paramContext.add(23, paramTypedArray.getLayoutDimension(k, paramConstraint.layout.mWidth));
        break;
      case 22: 
        paramContext.add(22, VISIBILITY_FLAGS[paramTypedArray.getInt(k, paramConstraint.propertySet.visibility)]);
        break;
      case 21: 
        paramContext.add(21, paramTypedArray.getLayoutDimension(k, paramConstraint.layout.mHeight));
        break;
      case 20: 
        paramContext.add(20, paramTypedArray.getFloat(k, paramConstraint.layout.horizontalBias));
        break;
      case 19: 
        paramContext.add(19, paramTypedArray.getFloat(k, paramConstraint.layout.guidePercent));
        break;
      case 18: 
        paramContext.add(18, paramTypedArray.getDimensionPixelOffset(k, paramConstraint.layout.guideEnd));
        break;
      case 17: 
        paramContext.add(17, paramTypedArray.getDimensionPixelOffset(k, paramConstraint.layout.guideBegin));
        break;
      case 16: 
        paramContext.add(16, paramTypedArray.getDimensionPixelSize(k, paramConstraint.layout.goneTopMargin));
        break;
      case 15: 
        paramContext.add(15, paramTypedArray.getDimensionPixelSize(k, paramConstraint.layout.goneStartMargin));
        break;
      case 14: 
        paramContext.add(14, paramTypedArray.getDimensionPixelSize(k, paramConstraint.layout.goneRightMargin));
        break;
      case 13: 
        paramContext.add(13, paramTypedArray.getDimensionPixelSize(k, paramConstraint.layout.goneLeftMargin));
        break;
      case 12: 
        paramContext.add(12, paramTypedArray.getDimensionPixelSize(k, paramConstraint.layout.goneEndMargin));
        break;
      case 11: 
        paramContext.add(11, paramTypedArray.getDimensionPixelSize(k, paramConstraint.layout.goneBottomMargin));
        break;
      case 8: 
        if (Build.VERSION.SDK_INT >= 17) {
          paramContext.add(8, paramTypedArray.getDimensionPixelSize(k, paramConstraint.layout.endMargin));
        }
        break;
      case 7: 
        paramContext.add(7, paramTypedArray.getDimensionPixelOffset(k, paramConstraint.layout.editorAbsoluteY));
        break;
      case 6: 
        paramContext.add(6, paramTypedArray.getDimensionPixelOffset(k, paramConstraint.layout.editorAbsoluteX));
        break;
      case 5: 
        paramContext.add(5, paramTypedArray.getString(k));
        break;
      case 2: 
        paramContext.add(2, paramTypedArray.getDimensionPixelSize(k, paramConstraint.layout.bottomMargin));
      }
    }
  }
  
  private static void setDeltaValue(Constraint paramConstraint, int paramInt, float paramFloat)
  {
    switch (paramInt)
    {
    default: 
      Log.w("ConstraintSet", "Unknown attribute 0x");
      break;
    case 87: 
      break;
    case 85: 
      paramConstraint.motion.mQuantizeMotionPhase = paramFloat;
      break;
    case 79: 
      paramConstraint.motion.mMotionStagger = paramFloat;
      break;
    case 70: 
      paramConstraint.layout.heightPercent = paramFloat;
      break;
    case 69: 
      paramConstraint.layout.widthPercent = paramFloat;
      break;
    case 68: 
      paramConstraint.propertySet.mProgress = paramFloat;
      break;
    case 67: 
      paramConstraint.motion.mPathRotate = paramFloat;
      break;
    case 63: 
      paramConstraint.layout.circleAngle = paramFloat;
      break;
    case 60: 
      paramConstraint.transform.rotation = paramFloat;
      break;
    case 53: 
      paramConstraint.transform.translationZ = paramFloat;
      break;
    case 52: 
      paramConstraint.transform.translationY = paramFloat;
      break;
    case 51: 
      paramConstraint.transform.translationX = paramFloat;
      break;
    case 50: 
      paramConstraint.transform.transformPivotY = paramFloat;
      break;
    case 49: 
      paramConstraint.transform.transformPivotX = paramFloat;
      break;
    case 48: 
      paramConstraint.transform.scaleY = paramFloat;
      break;
    case 47: 
      paramConstraint.transform.scaleX = paramFloat;
      break;
    case 46: 
      paramConstraint.transform.rotationY = paramFloat;
      break;
    case 45: 
      paramConstraint.transform.rotationX = paramFloat;
      break;
    case 44: 
      paramConstraint.transform.elevation = paramFloat;
      paramConstraint.transform.applyElevation = true;
      break;
    case 43: 
      paramConstraint.propertySet.alpha = paramFloat;
      break;
    case 40: 
      paramConstraint.layout.verticalWeight = paramFloat;
      break;
    case 39: 
      paramConstraint.layout.horizontalWeight = paramFloat;
      break;
    case 37: 
      paramConstraint.layout.verticalBias = paramFloat;
      break;
    case 20: 
      paramConstraint.layout.horizontalBias = paramFloat;
      break;
    case 19: 
      paramConstraint.layout.guidePercent = paramFloat;
    }
  }
  
  private static void setDeltaValue(Constraint paramConstraint, int paramInt1, int paramInt2)
  {
    switch (paramInt1)
    {
    default: 
      Log.w("ConstraintSet", "Unknown attribute 0x");
      break;
    case 97: 
      paramConstraint.layout.mWrapBehavior = paramInt2;
      break;
    case 94: 
      paramConstraint.layout.goneBaselineMargin = paramInt2;
      break;
    case 93: 
      paramConstraint.layout.baselineMargin = paramInt2;
      break;
    case 89: 
      paramConstraint.motion.mQuantizeInterpolatorID = paramInt2;
      break;
    case 88: 
      paramConstraint.motion.mQuantizeInterpolatorType = paramInt2;
      break;
    case 87: 
      break;
    case 84: 
      paramConstraint.motion.mQuantizeMotionSteps = paramInt2;
      break;
    case 83: 
      paramConstraint.transform.transformPivotTarget = paramInt2;
      break;
    case 82: 
      paramConstraint.motion.mAnimateCircleAngleTo = paramInt2;
      break;
    case 78: 
      paramConstraint.propertySet.mVisibilityMode = paramInt2;
      break;
    case 76: 
      paramConstraint.motion.mPathMotionArc = paramInt2;
      break;
    case 73: 
      paramConstraint.layout.mBarrierMargin = paramInt2;
      break;
    case 72: 
      paramConstraint.layout.mBarrierDirection = paramInt2;
      break;
    case 66: 
      paramConstraint.motion.mDrawPath = paramInt2;
      break;
    case 64: 
      paramConstraint.motion.mAnimateRelativeTo = paramInt2;
      break;
    case 62: 
      paramConstraint.layout.circleRadius = paramInt2;
      break;
    case 61: 
      paramConstraint.layout.circleConstraint = paramInt2;
      break;
    case 59: 
      paramConstraint.layout.heightMin = paramInt2;
      break;
    case 58: 
      paramConstraint.layout.widthMin = paramInt2;
      break;
    case 57: 
      paramConstraint.layout.heightMax = paramInt2;
      break;
    case 56: 
      paramConstraint.layout.widthMax = paramInt2;
      break;
    case 55: 
      paramConstraint.layout.heightDefault = paramInt2;
      break;
    case 54: 
      paramConstraint.layout.widthDefault = paramInt2;
      break;
    case 42: 
      paramConstraint.layout.verticalChainStyle = paramInt2;
      break;
    case 41: 
      paramConstraint.layout.horizontalChainStyle = paramInt2;
      break;
    case 38: 
      paramConstraint.mViewId = paramInt2;
      break;
    case 34: 
      paramConstraint.layout.topMargin = paramInt2;
      break;
    case 31: 
      paramConstraint.layout.startMargin = paramInt2;
      break;
    case 28: 
      paramConstraint.layout.rightMargin = paramInt2;
      break;
    case 27: 
      paramConstraint.layout.orientation = paramInt2;
      break;
    case 24: 
      paramConstraint.layout.leftMargin = paramInt2;
      break;
    case 23: 
      paramConstraint.layout.mWidth = paramInt2;
      break;
    case 22: 
      paramConstraint.propertySet.visibility = paramInt2;
      break;
    case 21: 
      paramConstraint.layout.mHeight = paramInt2;
      break;
    case 18: 
      paramConstraint.layout.guideEnd = paramInt2;
      break;
    case 17: 
      paramConstraint.layout.guideBegin = paramInt2;
      break;
    case 16: 
      paramConstraint.layout.goneTopMargin = paramInt2;
      break;
    case 15: 
      paramConstraint.layout.goneStartMargin = paramInt2;
      break;
    case 14: 
      paramConstraint.layout.goneRightMargin = paramInt2;
      break;
    case 13: 
      paramConstraint.layout.goneLeftMargin = paramInt2;
      break;
    case 12: 
      paramConstraint.layout.goneEndMargin = paramInt2;
      break;
    case 11: 
      paramConstraint.layout.goneBottomMargin = paramInt2;
      break;
    case 8: 
      paramConstraint.layout.endMargin = paramInt2;
      break;
    case 7: 
      paramConstraint.layout.editorAbsoluteY = paramInt2;
      break;
    case 6: 
      paramConstraint.layout.editorAbsoluteX = paramInt2;
      break;
    case 2: 
      paramConstraint.layout.bottomMargin = paramInt2;
    }
  }
  
  private static void setDeltaValue(Constraint paramConstraint, int paramInt, String paramString)
  {
    switch (paramInt)
    {
    default: 
      Log.w("ConstraintSet", "Unknown attribute 0x");
      break;
    case 90: 
      paramConstraint.motion.mQuantizeInterpolatorString = paramString;
      break;
    case 87: 
      break;
    case 77: 
      paramConstraint.layout.mConstraintTag = paramString;
      break;
    case 74: 
      paramConstraint.layout.mReferenceIdString = paramString;
      paramConstraint.layout.mReferenceIds = null;
      break;
    case 65: 
      paramConstraint.motion.mTransitionEasing = paramString;
      break;
    case 5: 
      paramConstraint.layout.dimensionRatio = paramString;
    }
  }
  
  private static void setDeltaValue(Constraint paramConstraint, int paramInt, boolean paramBoolean)
  {
    switch (paramInt)
    {
    default: 
      Log.w("ConstraintSet", "Unknown attribute 0x");
      break;
    case 87: 
      break;
    case 81: 
      paramConstraint.layout.constrainedHeight = paramBoolean;
      break;
    case 80: 
      paramConstraint.layout.constrainedWidth = paramBoolean;
      break;
    case 75: 
      paramConstraint.layout.mBarrierAllowsGoneWidgets = paramBoolean;
      break;
    case 44: 
      paramConstraint.transform.applyElevation = paramBoolean;
    }
  }
  
  private String sideToString(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return "undefined";
    case 7: 
      return "end";
    case 6: 
      return "start";
    case 5: 
      return "baseline";
    case 4: 
      return "bottom";
    case 3: 
      return "top";
    case 2: 
      return "right";
    }
    return "left";
  }
  
  private static String[] splitString(String paramString)
  {
    char[] arrayOfChar = paramString.toCharArray();
    paramString = new ArrayList();
    int n = 0;
    int k = 0;
    int j = 0;
    while (j < arrayOfChar.length)
    {
      int i;
      int m;
      if ((arrayOfChar[j] == ',') && (n == 0))
      {
        str = new String(arrayOfChar, k, j - k);
        Log5ECF72.a(str);
        LogE84000.a(str);
        Log229316.a(str);
        paramString.add(str);
        i = j + 1;
        m = n;
      }
      else
      {
        m = n;
        i = k;
        if (arrayOfChar[j] == '"')
        {
          if (n == 0) {
            i = 1;
          } else {
            i = 0;
          }
          m = i;
          i = k;
        }
      }
      j++;
      n = m;
      k = i;
    }
    String str = new String(arrayOfChar, k, arrayOfChar.length - k);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    paramString.add(str);
    return (String[])paramString.toArray(new String[paramString.size()]);
  }
  
  public void addColorAttributes(String... paramVarArgs)
  {
    addAttributes(ConstraintAttribute.AttributeType.COLOR_TYPE, paramVarArgs);
  }
  
  public void addFloatAttributes(String... paramVarArgs)
  {
    addAttributes(ConstraintAttribute.AttributeType.FLOAT_TYPE, paramVarArgs);
  }
  
  public void addIntAttributes(String... paramVarArgs)
  {
    addAttributes(ConstraintAttribute.AttributeType.INT_TYPE, paramVarArgs);
  }
  
  public void addStringAttributes(String... paramVarArgs)
  {
    addAttributes(ConstraintAttribute.AttributeType.STRING_TYPE, paramVarArgs);
  }
  
  public void addToHorizontalChain(int paramInt1, int paramInt2, int paramInt3)
  {
    int i;
    if (paramInt2 == 0) {
      i = 1;
    } else {
      i = 2;
    }
    connect(paramInt1, 1, paramInt2, i, 0);
    if (paramInt3 == 0) {
      i = 2;
    } else {
      i = 1;
    }
    connect(paramInt1, 2, paramInt3, i, 0);
    if (paramInt2 != 0) {
      connect(paramInt2, 2, paramInt1, 1, 0);
    }
    if (paramInt3 != 0) {
      connect(paramInt3, 1, paramInt1, 2, 0);
    }
  }
  
  public void addToHorizontalChainRTL(int paramInt1, int paramInt2, int paramInt3)
  {
    int i;
    if (paramInt2 == 0) {
      i = 6;
    } else {
      i = 7;
    }
    connect(paramInt1, 6, paramInt2, i, 0);
    if (paramInt3 == 0) {
      i = 7;
    } else {
      i = 6;
    }
    connect(paramInt1, 7, paramInt3, i, 0);
    if (paramInt2 != 0) {
      connect(paramInt2, 7, paramInt1, 6, 0);
    }
    if (paramInt3 != 0) {
      connect(paramInt3, 6, paramInt1, 7, 0);
    }
  }
  
  public void addToVerticalChain(int paramInt1, int paramInt2, int paramInt3)
  {
    int i;
    if (paramInt2 == 0) {
      i = 3;
    } else {
      i = 4;
    }
    connect(paramInt1, 3, paramInt2, i, 0);
    if (paramInt3 == 0) {
      i = 4;
    } else {
      i = 3;
    }
    connect(paramInt1, 4, paramInt3, i, 0);
    if (paramInt2 != 0) {
      connect(paramInt2, 4, paramInt1, 3, 0);
    }
    if (paramInt3 != 0) {
      connect(paramInt3, 3, paramInt1, 4, 0);
    }
  }
  
  public void applyCustomAttributes(ConstraintLayout paramConstraintLayout)
  {
    int j = paramConstraintLayout.getChildCount();
    for (int i = 0; i < j; i++)
    {
      Object localObject1 = paramConstraintLayout.getChildAt(i);
      int k = ((View)localObject1).getId();
      Object localObject2;
      if (!this.mConstraints.containsKey(Integer.valueOf(k)))
      {
        localObject2 = new StringBuilder().append("id unknown ");
        localObject1 = Debug.getName((View)localObject1);
        Log5ECF72.a(localObject1);
        LogE84000.a(localObject1);
        Log229316.a(localObject1);
        Log.w("ConstraintSet", (String)localObject1);
      }
      else
      {
        if ((this.mForceId) && (k == -1)) {
          throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
        }
        if (this.mConstraints.containsKey(Integer.valueOf(k)))
        {
          localObject2 = (Constraint)this.mConstraints.get(Integer.valueOf(k));
          if (localObject2 != null) {
            ConstraintAttribute.setAttributes((View)localObject1, ((Constraint)localObject2).mCustomConstraints);
          }
        }
      }
    }
  }
  
  public void applyDeltaFrom(ConstraintSet paramConstraintSet)
  {
    Iterator localIterator1 = paramConstraintSet.mConstraints.values().iterator();
    while (localIterator1.hasNext())
    {
      paramConstraintSet = (Constraint)localIterator1.next();
      if (paramConstraintSet.mDelta != null)
      {
        Constraint localConstraint;
        if (paramConstraintSet.mTargetString != null)
        {
          Iterator localIterator2 = this.mConstraints.keySet().iterator();
          while (localIterator2.hasNext())
          {
            localConstraint = getConstraint(((Integer)localIterator2.next()).intValue());
            if ((localConstraint.layout.mConstraintTag != null) && (paramConstraintSet.mTargetString.matches(localConstraint.layout.mConstraintTag)))
            {
              paramConstraintSet.mDelta.applyDelta(localConstraint);
              localConstraint.mCustomConstraints.putAll((HashMap)paramConstraintSet.mCustomConstraints.clone());
            }
          }
        }
        else
        {
          localConstraint = getConstraint(paramConstraintSet.mViewId);
          paramConstraintSet.mDelta.applyDelta(localConstraint);
        }
      }
    }
  }
  
  public void applyTo(ConstraintLayout paramConstraintLayout)
  {
    applyToInternal(paramConstraintLayout, true);
    paramConstraintLayout.setConstraintSet(null);
    paramConstraintLayout.requestLayout();
  }
  
  public void applyToHelper(ConstraintHelper paramConstraintHelper, ConstraintWidget paramConstraintWidget, ConstraintLayout.LayoutParams paramLayoutParams, SparseArray<ConstraintWidget> paramSparseArray)
  {
    int i = paramConstraintHelper.getId();
    if (this.mConstraints.containsKey(Integer.valueOf(i)))
    {
      Constraint localConstraint = (Constraint)this.mConstraints.get(Integer.valueOf(i));
      if ((localConstraint != null) && ((paramConstraintWidget instanceof HelperWidget))) {
        paramConstraintHelper.loadParameters(localConstraint, (HelperWidget)paramConstraintWidget, paramLayoutParams, paramSparseArray);
      }
    }
  }
  
  void applyToInternal(ConstraintLayout paramConstraintLayout, boolean paramBoolean)
  {
    int j = paramConstraintLayout.getChildCount();
    Object localObject1 = new HashSet(this.mConstraints.keySet());
    Object localObject3;
    Object localObject4;
    for (int i = 0; i < j; i++)
    {
      localObject2 = paramConstraintLayout.getChildAt(i);
      int k = ((View)localObject2).getId();
      if (!this.mConstraints.containsKey(Integer.valueOf(k)))
      {
        localObject3 = new StringBuilder().append("id unknown ");
        localObject2 = Debug.getName((View)localObject2);
        Log5ECF72.a(localObject2);
        LogE84000.a(localObject2);
        Log229316.a(localObject2);
        Log.w("ConstraintSet", (String)localObject2);
      }
      else
      {
        if ((this.mForceId) && (k == -1)) {
          throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
        }
        if (k != -1) {
          if (this.mConstraints.containsKey(Integer.valueOf(k)))
          {
            ((HashSet)localObject1).remove(Integer.valueOf(k));
            localObject3 = (Constraint)this.mConstraints.get(Integer.valueOf(k));
            if (localObject3 != null)
            {
              if ((localObject2 instanceof Barrier))
              {
                ((Constraint)localObject3).layout.mHelperType = 1;
                localObject4 = (Barrier)localObject2;
                ((Barrier)localObject4).setId(k);
                ((Barrier)localObject4).setType(((Constraint)localObject3).layout.mBarrierDirection);
                ((Barrier)localObject4).setMargin(((Constraint)localObject3).layout.mBarrierMargin);
                ((Barrier)localObject4).setAllowsGoneWidget(((Constraint)localObject3).layout.mBarrierAllowsGoneWidgets);
                if (((Constraint)localObject3).layout.mReferenceIds != null)
                {
                  ((Barrier)localObject4).setReferencedIds(((Constraint)localObject3).layout.mReferenceIds);
                }
                else if (((Constraint)localObject3).layout.mReferenceIdString != null)
                {
                  ((Constraint)localObject3).layout.mReferenceIds = convertReferenceString((View)localObject4, ((Constraint)localObject3).layout.mReferenceIdString);
                  ((Barrier)localObject4).setReferencedIds(((Constraint)localObject3).layout.mReferenceIds);
                }
              }
              localObject4 = (ConstraintLayout.LayoutParams)((View)localObject2).getLayoutParams();
              ((ConstraintLayout.LayoutParams)localObject4).validate();
              ((Constraint)localObject3).applyTo((ConstraintLayout.LayoutParams)localObject4);
              if (paramBoolean) {
                ConstraintAttribute.setAttributes((View)localObject2, ((Constraint)localObject3).mCustomConstraints);
              }
              ((View)localObject2).setLayoutParams((ViewGroup.LayoutParams)localObject4);
              if (((Constraint)localObject3).propertySet.mVisibilityMode == 0) {
                ((View)localObject2).setVisibility(((Constraint)localObject3).propertySet.visibility);
              }
              if (Build.VERSION.SDK_INT >= 17)
              {
                ((View)localObject2).setAlpha(((Constraint)localObject3).propertySet.alpha);
                ((View)localObject2).setRotation(((Constraint)localObject3).transform.rotation);
                ((View)localObject2).setRotationX(((Constraint)localObject3).transform.rotationX);
                ((View)localObject2).setRotationY(((Constraint)localObject3).transform.rotationY);
                ((View)localObject2).setScaleX(((Constraint)localObject3).transform.scaleX);
                ((View)localObject2).setScaleY(((Constraint)localObject3).transform.scaleY);
                if (((Constraint)localObject3).transform.transformPivotTarget != -1)
                {
                  localObject4 = ((View)((View)localObject2).getParent()).findViewById(((Constraint)localObject3).transform.transformPivotTarget);
                  if (localObject4 != null)
                  {
                    float f3 = (((View)localObject4).getTop() + ((View)localObject4).getBottom()) / 2.0F;
                    float f4 = (((View)localObject4).getLeft() + ((View)localObject4).getRight()) / 2.0F;
                    if ((((View)localObject2).getRight() - ((View)localObject2).getLeft() > 0) && (((View)localObject2).getBottom() - ((View)localObject2).getTop() > 0))
                    {
                      float f2 = ((View)localObject2).getLeft();
                      float f1 = ((View)localObject2).getTop();
                      ((View)localObject2).setPivotX(f4 - f2);
                      ((View)localObject2).setPivotY(f3 - f1);
                    }
                  }
                }
                else
                {
                  if (!Float.isNaN(((Constraint)localObject3).transform.transformPivotX)) {
                    ((View)localObject2).setPivotX(((Constraint)localObject3).transform.transformPivotX);
                  }
                  if (!Float.isNaN(((Constraint)localObject3).transform.transformPivotY)) {
                    ((View)localObject2).setPivotY(((Constraint)localObject3).transform.transformPivotY);
                  }
                }
                ((View)localObject2).setTranslationX(((Constraint)localObject3).transform.translationX);
                ((View)localObject2).setTranslationY(((Constraint)localObject3).transform.translationY);
                if (Build.VERSION.SDK_INT >= 21)
                {
                  ((View)localObject2).setTranslationZ(((Constraint)localObject3).transform.translationZ);
                  if (((Constraint)localObject3).transform.applyElevation) {
                    ((View)localObject2).setElevation(((Constraint)localObject3).transform.elevation);
                  }
                }
              }
            }
          }
          else
          {
            Log.v("ConstraintSet", "WARNING NO CONSTRAINTS for view " + k);
          }
        }
      }
    }
    Object localObject2 = ((HashSet)localObject1).iterator();
    while (((Iterator)localObject2).hasNext())
    {
      localObject3 = (Integer)((Iterator)localObject2).next();
      localObject1 = (Constraint)this.mConstraints.get(localObject3);
      if (localObject1 != null)
      {
        if (((Constraint)localObject1).layout.mHelperType == 1)
        {
          Barrier localBarrier = new Barrier(paramConstraintLayout.getContext());
          localBarrier.setId(((Integer)localObject3).intValue());
          if (((Constraint)localObject1).layout.mReferenceIds != null)
          {
            localBarrier.setReferencedIds(((Constraint)localObject1).layout.mReferenceIds);
          }
          else if (((Constraint)localObject1).layout.mReferenceIdString != null)
          {
            ((Constraint)localObject1).layout.mReferenceIds = convertReferenceString(localBarrier, ((Constraint)localObject1).layout.mReferenceIdString);
            localBarrier.setReferencedIds(((Constraint)localObject1).layout.mReferenceIds);
          }
          localBarrier.setType(((Constraint)localObject1).layout.mBarrierDirection);
          localBarrier.setMargin(((Constraint)localObject1).layout.mBarrierMargin);
          localObject4 = paramConstraintLayout.generateDefaultLayoutParams();
          localBarrier.validateParams();
          ((Constraint)localObject1).applyTo((ConstraintLayout.LayoutParams)localObject4);
          paramConstraintLayout.addView(localBarrier, (ViewGroup.LayoutParams)localObject4);
        }
        if (((Constraint)localObject1).layout.mIsGuideline)
        {
          localObject4 = new Guideline(paramConstraintLayout.getContext());
          ((Guideline)localObject4).setId(((Integer)localObject3).intValue());
          localObject3 = paramConstraintLayout.generateDefaultLayoutParams();
          ((Constraint)localObject1).applyTo((ConstraintLayout.LayoutParams)localObject3);
          paramConstraintLayout.addView((View)localObject4, (ViewGroup.LayoutParams)localObject3);
        }
      }
    }
    for (i = 0; i < j; i++)
    {
      localObject1 = paramConstraintLayout.getChildAt(i);
      if ((localObject1 instanceof ConstraintHelper)) {
        ((ConstraintHelper)localObject1).applyLayoutFeaturesInConstraintSet(paramConstraintLayout);
      }
    }
  }
  
  public void applyToLayoutParams(int paramInt, ConstraintLayout.LayoutParams paramLayoutParams)
  {
    if (this.mConstraints.containsKey(Integer.valueOf(paramInt)))
    {
      Constraint localConstraint = (Constraint)this.mConstraints.get(Integer.valueOf(paramInt));
      if (localConstraint != null) {
        localConstraint.applyTo(paramLayoutParams);
      }
    }
  }
  
  public void applyToWithoutCustom(ConstraintLayout paramConstraintLayout)
  {
    applyToInternal(paramConstraintLayout, false);
    paramConstraintLayout.setConstraintSet(null);
  }
  
  public void center(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, float paramFloat)
  {
    if (paramInt4 >= 0)
    {
      if (paramInt7 >= 0)
      {
        if ((paramFloat > 0.0F) && (paramFloat <= 1.0F))
        {
          Constraint localConstraint;
          if ((paramInt3 != 1) && (paramInt3 != 2))
          {
            if ((paramInt3 != 6) && (paramInt3 != 7))
            {
              connect(paramInt1, 3, paramInt2, paramInt3, paramInt4);
              connect(paramInt1, 4, paramInt5, paramInt6, paramInt7);
              localConstraint = (Constraint)this.mConstraints.get(Integer.valueOf(paramInt1));
              if (localConstraint != null) {
                localConstraint.layout.verticalBias = paramFloat;
              }
            }
            else
            {
              connect(paramInt1, 6, paramInt2, paramInt3, paramInt4);
              connect(paramInt1, 7, paramInt5, paramInt6, paramInt7);
              localConstraint = (Constraint)this.mConstraints.get(Integer.valueOf(paramInt1));
              if (localConstraint != null) {
                localConstraint.layout.horizontalBias = paramFloat;
              }
            }
          }
          else
          {
            connect(paramInt1, 1, paramInt2, paramInt3, paramInt4);
            connect(paramInt1, 2, paramInt5, paramInt6, paramInt7);
            localConstraint = (Constraint)this.mConstraints.get(Integer.valueOf(paramInt1));
            if (localConstraint != null) {
              localConstraint.layout.horizontalBias = paramFloat;
            }
          }
          return;
        }
        throw new IllegalArgumentException("bias must be between 0 and 1 inclusive");
      }
      throw new IllegalArgumentException("margin must be > 0");
    }
    throw new IllegalArgumentException("margin must be > 0");
  }
  
  public void centerHorizontally(int paramInt1, int paramInt2)
  {
    if (paramInt2 == 0) {
      center(paramInt1, 0, 1, 0, 0, 2, 0, 0.5F);
    } else {
      center(paramInt1, paramInt2, 2, 0, paramInt2, 1, 0, 0.5F);
    }
  }
  
  public void centerHorizontally(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, float paramFloat)
  {
    connect(paramInt1, 1, paramInt2, paramInt3, paramInt4);
    connect(paramInt1, 2, paramInt5, paramInt6, paramInt7);
    Constraint localConstraint = (Constraint)this.mConstraints.get(Integer.valueOf(paramInt1));
    if (localConstraint != null) {
      localConstraint.layout.horizontalBias = paramFloat;
    }
  }
  
  public void centerHorizontallyRtl(int paramInt1, int paramInt2)
  {
    if (paramInt2 == 0) {
      center(paramInt1, 0, 6, 0, 0, 7, 0, 0.5F);
    } else {
      center(paramInt1, paramInt2, 7, 0, paramInt2, 6, 0, 0.5F);
    }
  }
  
  public void centerHorizontallyRtl(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, float paramFloat)
  {
    connect(paramInt1, 6, paramInt2, paramInt3, paramInt4);
    connect(paramInt1, 7, paramInt5, paramInt6, paramInt7);
    Constraint localConstraint = (Constraint)this.mConstraints.get(Integer.valueOf(paramInt1));
    if (localConstraint != null) {
      localConstraint.layout.horizontalBias = paramFloat;
    }
  }
  
  public void centerVertically(int paramInt1, int paramInt2)
  {
    if (paramInt2 == 0) {
      center(paramInt1, 0, 3, 0, 0, 4, 0, 0.5F);
    } else {
      center(paramInt1, paramInt2, 4, 0, paramInt2, 3, 0, 0.5F);
    }
  }
  
  public void centerVertically(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, float paramFloat)
  {
    connect(paramInt1, 3, paramInt2, paramInt3, paramInt4);
    connect(paramInt1, 4, paramInt5, paramInt6, paramInt7);
    Constraint localConstraint = (Constraint)this.mConstraints.get(Integer.valueOf(paramInt1));
    if (localConstraint != null) {
      localConstraint.layout.verticalBias = paramFloat;
    }
  }
  
  public void clear(int paramInt)
  {
    this.mConstraints.remove(Integer.valueOf(paramInt));
  }
  
  public void clear(int paramInt1, int paramInt2)
  {
    if (this.mConstraints.containsKey(Integer.valueOf(paramInt1)))
    {
      Constraint localConstraint = (Constraint)this.mConstraints.get(Integer.valueOf(paramInt1));
      if (localConstraint == null) {
        return;
      }
      switch (paramInt2)
      {
      default: 
        throw new IllegalArgumentException("unknown constraint");
      case 8: 
        localConstraint.layout.circleAngle = -1.0F;
        localConstraint.layout.circleRadius = -1;
        localConstraint.layout.circleConstraint = -1;
        break;
      case 7: 
        localConstraint.layout.endToStart = -1;
        localConstraint.layout.endToEnd = -1;
        localConstraint.layout.endMargin = 0;
        localConstraint.layout.goneEndMargin = Integer.MIN_VALUE;
        break;
      case 6: 
        localConstraint.layout.startToEnd = -1;
        localConstraint.layout.startToStart = -1;
        localConstraint.layout.startMargin = 0;
        localConstraint.layout.goneStartMargin = Integer.MIN_VALUE;
        break;
      case 5: 
        localConstraint.layout.baselineToBaseline = -1;
        localConstraint.layout.baselineToTop = -1;
        localConstraint.layout.baselineToBottom = -1;
        localConstraint.layout.baselineMargin = 0;
        localConstraint.layout.goneBaselineMargin = Integer.MIN_VALUE;
        break;
      case 4: 
        localConstraint.layout.bottomToTop = -1;
        localConstraint.layout.bottomToBottom = -1;
        localConstraint.layout.bottomMargin = 0;
        localConstraint.layout.goneBottomMargin = Integer.MIN_VALUE;
        break;
      case 3: 
        localConstraint.layout.topToBottom = -1;
        localConstraint.layout.topToTop = -1;
        localConstraint.layout.topMargin = 0;
        localConstraint.layout.goneTopMargin = Integer.MIN_VALUE;
        break;
      case 2: 
        localConstraint.layout.rightToRight = -1;
        localConstraint.layout.rightToLeft = -1;
        localConstraint.layout.rightMargin = -1;
        localConstraint.layout.goneRightMargin = Integer.MIN_VALUE;
        break;
      case 1: 
        localConstraint.layout.leftToRight = -1;
        localConstraint.layout.leftToLeft = -1;
        localConstraint.layout.leftMargin = -1;
        localConstraint.layout.goneLeftMargin = Integer.MIN_VALUE;
      }
    }
  }
  
  public void clone(Context paramContext, int paramInt)
  {
    clone((ConstraintLayout)LayoutInflater.from(paramContext).inflate(paramInt, null));
  }
  
  public void clone(ConstraintLayout paramConstraintLayout)
  {
    int j = paramConstraintLayout.getChildCount();
    this.mConstraints.clear();
    for (int i = 0; i < j; i++)
    {
      View localView = paramConstraintLayout.getChildAt(i);
      Object localObject = (ConstraintLayout.LayoutParams)localView.getLayoutParams();
      int k = localView.getId();
      if ((this.mForceId) && (k == -1)) {
        throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
      }
      if (!this.mConstraints.containsKey(Integer.valueOf(k))) {
        this.mConstraints.put(Integer.valueOf(k), new Constraint());
      }
      Constraint localConstraint = (Constraint)this.mConstraints.get(Integer.valueOf(k));
      if (localConstraint != null)
      {
        localConstraint.mCustomConstraints = ConstraintAttribute.extractAttributes(this.mSavedAttributes, localView);
        localConstraint.fillFrom(k, (ConstraintLayout.LayoutParams)localObject);
        localConstraint.propertySet.visibility = localView.getVisibility();
        if (Build.VERSION.SDK_INT >= 17)
        {
          localConstraint.propertySet.alpha = localView.getAlpha();
          localConstraint.transform.rotation = localView.getRotation();
          localConstraint.transform.rotationX = localView.getRotationX();
          localConstraint.transform.rotationY = localView.getRotationY();
          localConstraint.transform.scaleX = localView.getScaleX();
          localConstraint.transform.scaleY = localView.getScaleY();
          float f2 = localView.getPivotX();
          float f1 = localView.getPivotY();
          if ((f2 != 0.0D) || (f1 != 0.0D))
          {
            localConstraint.transform.transformPivotX = f2;
            localConstraint.transform.transformPivotY = f1;
          }
          localConstraint.transform.translationX = localView.getTranslationX();
          localConstraint.transform.translationY = localView.getTranslationY();
          if (Build.VERSION.SDK_INT >= 21)
          {
            localConstraint.transform.translationZ = localView.getTranslationZ();
            if (localConstraint.transform.applyElevation) {
              localConstraint.transform.elevation = localView.getElevation();
            }
          }
        }
        if ((localView instanceof Barrier))
        {
          localObject = (Barrier)localView;
          localConstraint.layout.mBarrierAllowsGoneWidgets = ((Barrier)localObject).getAllowsGoneWidget();
          localConstraint.layout.mReferenceIds = ((Barrier)localObject).getReferencedIds();
          localConstraint.layout.mBarrierDirection = ((Barrier)localObject).getType();
          localConstraint.layout.mBarrierMargin = ((Barrier)localObject).getMargin();
        }
      }
    }
  }
  
  public void clone(ConstraintSet paramConstraintSet)
  {
    this.mConstraints.clear();
    Iterator localIterator = paramConstraintSet.mConstraints.keySet().iterator();
    while (localIterator.hasNext())
    {
      Integer localInteger = (Integer)localIterator.next();
      Constraint localConstraint = (Constraint)paramConstraintSet.mConstraints.get(localInteger);
      if (localConstraint != null) {
        this.mConstraints.put(localInteger, localConstraint.clone());
      }
    }
  }
  
  public void clone(Constraints paramConstraints)
  {
    int j = paramConstraints.getChildCount();
    this.mConstraints.clear();
    for (int i = 0; i < j; i++)
    {
      View localView = paramConstraints.getChildAt(i);
      Constraints.LayoutParams localLayoutParams = (Constraints.LayoutParams)localView.getLayoutParams();
      int k = localView.getId();
      if ((this.mForceId) && (k == -1)) {
        throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
      }
      if (!this.mConstraints.containsKey(Integer.valueOf(k))) {
        this.mConstraints.put(Integer.valueOf(k), new Constraint());
      }
      Constraint localConstraint = (Constraint)this.mConstraints.get(Integer.valueOf(k));
      if (localConstraint != null)
      {
        if ((localView instanceof ConstraintHelper)) {
          localConstraint.fillFromConstraints((ConstraintHelper)localView, k, localLayoutParams);
        }
        localConstraint.fillFromConstraints(k, localLayoutParams);
      }
    }
  }
  
  public void connect(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (!this.mConstraints.containsKey(Integer.valueOf(paramInt1))) {
      this.mConstraints.put(Integer.valueOf(paramInt1), new Constraint());
    }
    Constraint localConstraint = (Constraint)this.mConstraints.get(Integer.valueOf(paramInt1));
    if (localConstraint == null) {
      return;
    }
    switch (paramInt2)
    {
    default: 
      throw new IllegalArgumentException(sideToString(paramInt2) + " to " + sideToString(paramInt4) + " unknown");
    case 7: 
      if (paramInt4 == 7)
      {
        localConstraint.layout.endToEnd = paramInt3;
        localConstraint.layout.endToStart = -1;
      }
      else if (paramInt4 == 6)
      {
        localConstraint.layout.endToStart = paramInt3;
        localConstraint.layout.endToEnd = -1;
      }
      else
      {
        throw new IllegalArgumentException("right to " + sideToString(paramInt4) + " undefined");
      }
      break;
    case 6: 
      if (paramInt4 == 6)
      {
        localConstraint.layout.startToStart = paramInt3;
        localConstraint.layout.startToEnd = -1;
      }
      else if (paramInt4 == 7)
      {
        localConstraint.layout.startToEnd = paramInt3;
        localConstraint.layout.startToStart = -1;
      }
      else
      {
        throw new IllegalArgumentException("right to " + sideToString(paramInt4) + " undefined");
      }
      break;
    case 5: 
      if (paramInt4 == 5)
      {
        localConstraint.layout.baselineToBaseline = paramInt3;
        localConstraint.layout.bottomToBottom = -1;
        localConstraint.layout.bottomToTop = -1;
        localConstraint.layout.topToTop = -1;
        localConstraint.layout.topToBottom = -1;
      }
      else if (paramInt4 == 3)
      {
        localConstraint.layout.baselineToTop = paramInt3;
        localConstraint.layout.bottomToBottom = -1;
        localConstraint.layout.bottomToTop = -1;
        localConstraint.layout.topToTop = -1;
        localConstraint.layout.topToBottom = -1;
      }
      else if (paramInt4 == 4)
      {
        localConstraint.layout.baselineToBottom = paramInt3;
        localConstraint.layout.bottomToBottom = -1;
        localConstraint.layout.bottomToTop = -1;
        localConstraint.layout.topToTop = -1;
        localConstraint.layout.topToBottom = -1;
      }
      else
      {
        throw new IllegalArgumentException("right to " + sideToString(paramInt4) + " undefined");
      }
      break;
    case 4: 
      if (paramInt4 == 4)
      {
        localConstraint.layout.bottomToBottom = paramInt3;
        localConstraint.layout.bottomToTop = -1;
        localConstraint.layout.baselineToBaseline = -1;
        localConstraint.layout.baselineToTop = -1;
        localConstraint.layout.baselineToBottom = -1;
      }
      else if (paramInt4 == 3)
      {
        localConstraint.layout.bottomToTop = paramInt3;
        localConstraint.layout.bottomToBottom = -1;
        localConstraint.layout.baselineToBaseline = -1;
        localConstraint.layout.baselineToTop = -1;
        localConstraint.layout.baselineToBottom = -1;
      }
      else
      {
        throw new IllegalArgumentException("right to " + sideToString(paramInt4) + " undefined");
      }
      break;
    case 3: 
      if (paramInt4 == 3)
      {
        localConstraint.layout.topToTop = paramInt3;
        localConstraint.layout.topToBottom = -1;
        localConstraint.layout.baselineToBaseline = -1;
        localConstraint.layout.baselineToTop = -1;
        localConstraint.layout.baselineToBottom = -1;
      }
      else if (paramInt4 == 4)
      {
        localConstraint.layout.topToBottom = paramInt3;
        localConstraint.layout.topToTop = -1;
        localConstraint.layout.baselineToBaseline = -1;
        localConstraint.layout.baselineToTop = -1;
        localConstraint.layout.baselineToBottom = -1;
      }
      else
      {
        throw new IllegalArgumentException("right to " + sideToString(paramInt4) + " undefined");
      }
      break;
    case 2: 
      if (paramInt4 == 1)
      {
        localConstraint.layout.rightToLeft = paramInt3;
        localConstraint.layout.rightToRight = -1;
      }
      else if (paramInt4 == 2)
      {
        localConstraint.layout.rightToRight = paramInt3;
        localConstraint.layout.rightToLeft = -1;
      }
      else
      {
        throw new IllegalArgumentException("right to " + sideToString(paramInt4) + " undefined");
      }
      break;
    case 1: 
      if (paramInt4 == 1)
      {
        localConstraint.layout.leftToLeft = paramInt3;
        localConstraint.layout.leftToRight = -1;
      }
      else
      {
        if (paramInt4 != 2) {
          break label977;
        }
        localConstraint.layout.leftToRight = paramInt3;
        localConstraint.layout.leftToLeft = -1;
      }
      break;
    }
    return;
    label977:
    throw new IllegalArgumentException("left to " + sideToString(paramInt4) + " undefined");
  }
  
  public void connect(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    if (!this.mConstraints.containsKey(Integer.valueOf(paramInt1))) {
      this.mConstraints.put(Integer.valueOf(paramInt1), new Constraint());
    }
    Constraint localConstraint = (Constraint)this.mConstraints.get(Integer.valueOf(paramInt1));
    if (localConstraint == null) {
      return;
    }
    switch (paramInt2)
    {
    default: 
      throw new IllegalArgumentException(sideToString(paramInt2) + " to " + sideToString(paramInt4) + " unknown");
    case 7: 
      if (paramInt4 == 7)
      {
        localConstraint.layout.endToEnd = paramInt3;
        localConstraint.layout.endToStart = -1;
      }
      else
      {
        if (paramInt4 != 6) {
          break label213;
        }
        localConstraint.layout.endToStart = paramInt3;
        localConstraint.layout.endToEnd = -1;
      }
      localConstraint.layout.endMargin = paramInt5;
      break;
      throw new IllegalArgumentException("right to " + sideToString(paramInt4) + " undefined");
    case 6: 
      if (paramInt4 == 6)
      {
        localConstraint.layout.startToStart = paramInt3;
        localConstraint.layout.startToEnd = -1;
      }
      else
      {
        if (paramInt4 != 7) {
          break label318;
        }
        localConstraint.layout.startToEnd = paramInt3;
        localConstraint.layout.startToStart = -1;
      }
      localConstraint.layout.startMargin = paramInt5;
      break;
      throw new IllegalArgumentException("right to " + sideToString(paramInt4) + " undefined");
    case 5: 
      if (paramInt4 == 5)
      {
        localConstraint.layout.baselineToBaseline = paramInt3;
        localConstraint.layout.bottomToBottom = -1;
        localConstraint.layout.bottomToTop = -1;
        localConstraint.layout.topToTop = -1;
        localConstraint.layout.topToBottom = -1;
      }
      else if (paramInt4 == 3)
      {
        localConstraint.layout.baselineToTop = paramInt3;
        localConstraint.layout.bottomToBottom = -1;
        localConstraint.layout.bottomToTop = -1;
        localConstraint.layout.topToTop = -1;
        localConstraint.layout.topToBottom = -1;
      }
      else if (paramInt4 == 4)
      {
        localConstraint.layout.baselineToBottom = paramInt3;
        localConstraint.layout.bottomToBottom = -1;
        localConstraint.layout.bottomToTop = -1;
        localConstraint.layout.topToTop = -1;
        localConstraint.layout.topToBottom = -1;
      }
      else
      {
        throw new IllegalArgumentException("right to " + sideToString(paramInt4) + " undefined");
      }
      break;
    case 4: 
      if (paramInt4 == 4)
      {
        localConstraint.layout.bottomToBottom = paramInt3;
        localConstraint.layout.bottomToTop = -1;
        localConstraint.layout.baselineToBaseline = -1;
        localConstraint.layout.baselineToTop = -1;
        localConstraint.layout.baselineToBottom = -1;
      }
      else
      {
        if (paramInt4 != 3) {
          break label676;
        }
        localConstraint.layout.bottomToTop = paramInt3;
        localConstraint.layout.bottomToBottom = -1;
        localConstraint.layout.baselineToBaseline = -1;
        localConstraint.layout.baselineToTop = -1;
        localConstraint.layout.baselineToBottom = -1;
      }
      localConstraint.layout.bottomMargin = paramInt5;
      break;
      throw new IllegalArgumentException("right to " + sideToString(paramInt4) + " undefined");
    case 3: 
      if (paramInt4 == 3)
      {
        localConstraint.layout.topToTop = paramInt3;
        localConstraint.layout.topToBottom = -1;
        localConstraint.layout.baselineToBaseline = -1;
        localConstraint.layout.baselineToTop = -1;
        localConstraint.layout.baselineToBottom = -1;
      }
      else
      {
        if (paramInt4 != 4) {
          break label833;
        }
        localConstraint.layout.topToBottom = paramInt3;
        localConstraint.layout.topToTop = -1;
        localConstraint.layout.baselineToBaseline = -1;
        localConstraint.layout.baselineToTop = -1;
        localConstraint.layout.baselineToBottom = -1;
      }
      localConstraint.layout.topMargin = paramInt5;
      break;
      throw new IllegalArgumentException("right to " + sideToString(paramInt4) + " undefined");
    case 2: 
      if (paramInt4 == 1)
      {
        localConstraint.layout.rightToLeft = paramInt3;
        localConstraint.layout.rightToRight = -1;
      }
      else
      {
        if (paramInt4 != 2) {
          break label936;
        }
        localConstraint.layout.rightToRight = paramInt3;
        localConstraint.layout.rightToLeft = -1;
      }
      localConstraint.layout.rightMargin = paramInt5;
      break;
      throw new IllegalArgumentException("right to " + sideToString(paramInt4) + " undefined");
    case 1: 
      label213:
      label318:
      label676:
      label833:
      label936:
      if (paramInt4 == 1)
      {
        localConstraint.layout.leftToLeft = paramInt3;
        localConstraint.layout.leftToRight = -1;
      }
      else
      {
        if (paramInt4 != 2) {
          break label1037;
        }
        localConstraint.layout.leftToRight = paramInt3;
        localConstraint.layout.leftToLeft = -1;
      }
      localConstraint.layout.leftMargin = paramInt5;
    }
    return;
    label1037:
    throw new IllegalArgumentException("Left to " + sideToString(paramInt4) + " undefined");
  }
  
  public void constrainCircle(int paramInt1, int paramInt2, int paramInt3, float paramFloat)
  {
    Constraint localConstraint = get(paramInt1);
    localConstraint.layout.circleConstraint = paramInt2;
    localConstraint.layout.circleRadius = paramInt3;
    localConstraint.layout.circleAngle = paramFloat;
  }
  
  public void constrainDefaultHeight(int paramInt1, int paramInt2)
  {
    get(paramInt1).layout.heightDefault = paramInt2;
  }
  
  public void constrainDefaultWidth(int paramInt1, int paramInt2)
  {
    get(paramInt1).layout.widthDefault = paramInt2;
  }
  
  public void constrainHeight(int paramInt1, int paramInt2)
  {
    get(paramInt1).layout.mHeight = paramInt2;
  }
  
  public void constrainMaxHeight(int paramInt1, int paramInt2)
  {
    get(paramInt1).layout.heightMax = paramInt2;
  }
  
  public void constrainMaxWidth(int paramInt1, int paramInt2)
  {
    get(paramInt1).layout.widthMax = paramInt2;
  }
  
  public void constrainMinHeight(int paramInt1, int paramInt2)
  {
    get(paramInt1).layout.heightMin = paramInt2;
  }
  
  public void constrainMinWidth(int paramInt1, int paramInt2)
  {
    get(paramInt1).layout.widthMin = paramInt2;
  }
  
  public void constrainPercentHeight(int paramInt, float paramFloat)
  {
    get(paramInt).layout.heightPercent = paramFloat;
  }
  
  public void constrainPercentWidth(int paramInt, float paramFloat)
  {
    get(paramInt).layout.widthPercent = paramFloat;
  }
  
  public void constrainWidth(int paramInt1, int paramInt2)
  {
    get(paramInt1).layout.mWidth = paramInt2;
  }
  
  public void constrainedHeight(int paramInt, boolean paramBoolean)
  {
    get(paramInt).layout.constrainedHeight = paramBoolean;
  }
  
  public void constrainedWidth(int paramInt, boolean paramBoolean)
  {
    get(paramInt).layout.constrainedWidth = paramBoolean;
  }
  
  public void create(int paramInt1, int paramInt2)
  {
    Constraint localConstraint = get(paramInt1);
    localConstraint.layout.mIsGuideline = true;
    localConstraint.layout.orientation = paramInt2;
  }
  
  public void createBarrier(int paramInt1, int paramInt2, int paramInt3, int... paramVarArgs)
  {
    Constraint localConstraint = get(paramInt1);
    localConstraint.layout.mHelperType = 1;
    localConstraint.layout.mBarrierDirection = paramInt2;
    localConstraint.layout.mBarrierMargin = paramInt3;
    localConstraint.layout.mIsGuideline = false;
    localConstraint.layout.mReferenceIds = paramVarArgs;
  }
  
  public void createHorizontalChain(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt, float[] paramArrayOfFloat, int paramInt5)
  {
    createHorizontalChain(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfInt, paramArrayOfFloat, paramInt5, 1, 2);
  }
  
  public void createHorizontalChainRtl(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt, float[] paramArrayOfFloat, int paramInt5)
  {
    createHorizontalChain(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfInt, paramArrayOfFloat, paramInt5, 6, 7);
  }
  
  public void createVerticalChain(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt, float[] paramArrayOfFloat, int paramInt5)
  {
    if (paramArrayOfInt.length >= 2)
    {
      if ((paramArrayOfFloat != null) && (paramArrayOfFloat.length != paramArrayOfInt.length)) {
        throw new IllegalArgumentException("must have 2 or more widgets in a chain");
      }
      if (paramArrayOfFloat != null) {
        get(paramArrayOfInt[0]).layout.verticalWeight = paramArrayOfFloat[0];
      }
      get(paramArrayOfInt[0]).layout.verticalChainStyle = paramInt5;
      connect(paramArrayOfInt[0], 3, paramInt1, paramInt2, 0);
      for (paramInt1 = 1; paramInt1 < paramArrayOfInt.length; paramInt1++)
      {
        paramInt2 = paramArrayOfInt[paramInt1];
        connect(paramArrayOfInt[paramInt1], 3, paramArrayOfInt[(paramInt1 - 1)], 4, 0);
        connect(paramArrayOfInt[(paramInt1 - 1)], 4, paramArrayOfInt[paramInt1], 3, 0);
        if (paramArrayOfFloat != null) {
          get(paramArrayOfInt[paramInt1]).layout.verticalWeight = paramArrayOfFloat[paramInt1];
        }
      }
      connect(paramArrayOfInt[(paramArrayOfInt.length - 1)], 4, paramInt3, paramInt4, 0);
      return;
    }
    throw new IllegalArgumentException("must have 2 or more widgets in a chain");
  }
  
  public void dump(MotionScene paramMotionScene, int... paramVarArgs)
  {
    Object localObject1 = this.mConstraints.keySet();
    int i = paramVarArgs.length;
    int j = 0;
    if (i != 0)
    {
      localObject2 = new HashSet();
      k = paramVarArgs.length;
      for (i = 0;; i++)
      {
        localObject1 = localObject2;
        if (i >= k) {
          break;
        }
        ((HashSet)localObject2).add(Integer.valueOf(paramVarArgs[i]));
      }
    }
    localObject1 = new HashSet((Collection)localObject1);
    System.out.println(((HashSet)localObject1).size() + " constraints");
    paramVarArgs = new StringBuilder();
    Object localObject2 = (Integer[])((HashSet)localObject1).toArray(new Integer[0]);
    int k = localObject2.length;
    for (i = j; i < k; i++)
    {
      localObject1 = localObject2[i];
      Constraint localConstraint = (Constraint)this.mConstraints.get(localObject1);
      if (localConstraint != null)
      {
        paramVarArgs.append("<Constraint id=");
        paramVarArgs.append(localObject1);
        paramVarArgs.append(" \n");
        localConstraint.layout.dump(paramMotionScene, paramVarArgs);
        paramVarArgs.append("/>\n");
      }
    }
    System.out.println(paramVarArgs.toString());
  }
  
  public boolean getApplyElevation(int paramInt)
  {
    return get(paramInt).transform.applyElevation;
  }
  
  public Constraint getConstraint(int paramInt)
  {
    if (this.mConstraints.containsKey(Integer.valueOf(paramInt))) {
      return (Constraint)this.mConstraints.get(Integer.valueOf(paramInt));
    }
    return null;
  }
  
  public HashMap<String, ConstraintAttribute> getCustomAttributeSet()
  {
    return this.mSavedAttributes;
  }
  
  public int getHeight(int paramInt)
  {
    return get(paramInt).layout.mHeight;
  }
  
  public int[] getKnownIds()
  {
    Integer[] arrayOfInteger = (Integer[])this.mConstraints.keySet().toArray(new Integer[0]);
    int[] arrayOfInt = new int[arrayOfInteger.length];
    for (int i = 0; i < arrayOfInt.length; i++) {
      arrayOfInt[i] = arrayOfInteger[i].intValue();
    }
    return arrayOfInt;
  }
  
  public Constraint getParameters(int paramInt)
  {
    return get(paramInt);
  }
  
  public int[] getReferencedIds(int paramInt)
  {
    Constraint localConstraint = get(paramInt);
    if (localConstraint.layout.mReferenceIds == null) {
      return new int[0];
    }
    return Arrays.copyOf(localConstraint.layout.mReferenceIds, localConstraint.layout.mReferenceIds.length);
  }
  
  public int getVisibility(int paramInt)
  {
    return get(paramInt).propertySet.visibility;
  }
  
  public int getVisibilityMode(int paramInt)
  {
    return get(paramInt).propertySet.mVisibilityMode;
  }
  
  public int getWidth(int paramInt)
  {
    return get(paramInt).layout.mWidth;
  }
  
  public boolean isForceId()
  {
    return this.mForceId;
  }
  
  public void load(Context paramContext, int paramInt)
  {
    XmlResourceParser localXmlResourceParser = paramContext.getResources().getXml(paramInt);
    try
    {
      for (paramInt = localXmlResourceParser.getEventType(); paramInt != 1; paramInt = localXmlResourceParser.next()) {
        switch (paramInt)
        {
        case 1: 
        default: 
          break;
        case 3: 
          break;
        case 2: 
          String str = localXmlResourceParser.getName();
          Constraint localConstraint = fillFromAttributeList(paramContext, Xml.asAttributeSet(localXmlResourceParser), false);
          if (str.equalsIgnoreCase("Guideline")) {
            localConstraint.layout.mIsGuideline = true;
          }
          this.mConstraints.put(Integer.valueOf(localConstraint.mViewId), localConstraint);
          break;
        case 0: 
          localXmlResourceParser.getName();
        }
      }
    }
    catch (IOException paramContext)
    {
      paramContext.printStackTrace();
    }
    catch (XmlPullParserException paramContext)
    {
      paramContext.printStackTrace();
    }
  }
  
  public void load(Context paramContext, XmlPullParser paramXmlPullParser)
  {
    Object localObject = null;
    try
    {
      for (int i = paramXmlPullParser.getEventType();; i = paramXmlPullParser.next())
      {
        int j = 1;
        if (i == 1) {
          break;
        }
        int k = 3;
        String str;
        switch (i)
        {
        case 1: 
        default: 
          break;
        case 3: 
          str = paramXmlPullParser.getName().toLowerCase(Locale.ROOT);
          switch (str.hashCode())
          {
          }
          for (;;)
          {
            break;
            if (str.equals("constraintset"))
            {
              i = 0;
              break label186;
              if (str.equals("constraintoverride"))
              {
                i = 2;
                break label186;
                if (str.equals("constraint"))
                {
                  i = j;
                  break label186;
                  if (str.equals("guideline"))
                  {
                    i = 3;
                    break label186;
                  }
                }
              }
            }
          }
          i = -1;
          switch (i)
          {
          default: 
            break;
          case 1: 
          case 2: 
          case 3: 
            this.mConstraints.put(Integer.valueOf(((Constraint)localObject).mViewId), localObject);
            localObject = null;
            break;
          case 0: 
            return;
          }
          break;
        case 2: 
          str = paramXmlPullParser.getName();
          switch (str.hashCode())
          {
          }
          for (;;)
          {
            break;
            if (str.equals("Constraint"))
            {
              i = 0;
              break label526;
              if (str.equals("CustomAttribute"))
              {
                i = 8;
                break label526;
                if (str.equals("Barrier"))
                {
                  i = k;
                  break label526;
                  if (str.equals("CustomMethod"))
                  {
                    i = 9;
                    break label526;
                    if (str.equals("Guideline"))
                    {
                      i = 2;
                      break label526;
                      if (str.equals("Transform"))
                      {
                        i = 5;
                        break label526;
                        if (str.equals("PropertySet"))
                        {
                          i = 4;
                          break label526;
                          if (str.equals("ConstraintOverride"))
                          {
                            i = 1;
                            break label526;
                            if (str.equals("Motion"))
                            {
                              i = 7;
                              break label526;
                              boolean bool = str.equals("Layout");
                              if (bool)
                              {
                                i = 6;
                                break label526;
                              }
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
          i = -1;
          switch (i)
          {
          default: 
            break;
          case 8: 
          case 9: 
            if (localObject != null)
            {
              ConstraintAttribute.parse(paramContext, paramXmlPullParser, ((Constraint)localObject).mCustomConstraints);
            }
            else
            {
              paramContext = new java/lang/RuntimeException;
              localObject = new java/lang/StringBuilder;
              ((StringBuilder)localObject).<init>();
              paramContext.<init>("XML parser error must be within a Constraint " + paramXmlPullParser.getLineNumber());
              throw paramContext;
            }
            break;
          case 7: 
            if (localObject != null)
            {
              ((Constraint)localObject).motion.fillFromAttributeList(paramContext, Xml.asAttributeSet(paramXmlPullParser));
            }
            else
            {
              localObject = new java/lang/RuntimeException;
              paramContext = new java/lang/StringBuilder;
              paramContext.<init>();
              ((RuntimeException)localObject).<init>("XML parser error must be within a Constraint " + paramXmlPullParser.getLineNumber());
              throw ((Throwable)localObject);
            }
            break;
          case 6: 
            if (localObject != null)
            {
              ((Constraint)localObject).layout.fillFromAttributeList(paramContext, Xml.asAttributeSet(paramXmlPullParser));
            }
            else
            {
              paramContext = new java/lang/RuntimeException;
              localObject = new java/lang/StringBuilder;
              ((StringBuilder)localObject).<init>();
              paramContext.<init>("XML parser error must be within a Constraint " + paramXmlPullParser.getLineNumber());
              throw paramContext;
            }
            break;
          case 5: 
            if (localObject != null)
            {
              ((Constraint)localObject).transform.fillFromAttributeList(paramContext, Xml.asAttributeSet(paramXmlPullParser));
            }
            else
            {
              localObject = new java/lang/RuntimeException;
              paramContext = new java/lang/StringBuilder;
              paramContext.<init>();
              ((RuntimeException)localObject).<init>("XML parser error must be within a Constraint " + paramXmlPullParser.getLineNumber());
              throw ((Throwable)localObject);
            }
            break;
          case 4: 
            if (localObject != null)
            {
              ((Constraint)localObject).propertySet.fillFromAttributeList(paramContext, Xml.asAttributeSet(paramXmlPullParser));
            }
            else
            {
              localObject = new java/lang/RuntimeException;
              paramContext = new java/lang/StringBuilder;
              paramContext.<init>();
              ((RuntimeException)localObject).<init>("XML parser error must be within a Constraint " + paramXmlPullParser.getLineNumber());
              throw ((Throwable)localObject);
            }
            break;
          case 3: 
            localObject = fillFromAttributeList(paramContext, Xml.asAttributeSet(paramXmlPullParser), false);
            ((Constraint)localObject).layout.mHelperType = 1;
            break;
          case 2: 
            localObject = fillFromAttributeList(paramContext, Xml.asAttributeSet(paramXmlPullParser), false);
            ((Constraint)localObject).layout.mIsGuideline = true;
            ((Constraint)localObject).layout.mApply = true;
            break;
          case 1: 
            localObject = fillFromAttributeList(paramContext, Xml.asAttributeSet(paramXmlPullParser), true);
            break;
          case 0: 
            localObject = fillFromAttributeList(paramContext, Xml.asAttributeSet(paramXmlPullParser), false);
          }
          break;
        case 0: 
          label186:
          label526:
          paramXmlPullParser.getName();
        }
      }
    }
    catch (IOException paramContext)
    {
      paramContext.printStackTrace();
    }
    catch (XmlPullParserException paramContext)
    {
      paramContext.printStackTrace();
    }
  }
  
  public void parseColorAttributes(Constraint paramConstraint, String paramString)
  {
    String[] arrayOfString = paramString.split(",");
    for (int i = 0; i < arrayOfString.length; i++)
    {
      paramString = arrayOfString[i].split("=");
      if (paramString.length != 2) {
        Log.w("ConstraintSet", " Unable to parse " + arrayOfString[i]);
      } else {
        paramConstraint.setColorValue(paramString[0], Color.parseColor(paramString[1]));
      }
    }
  }
  
  public void parseFloatAttributes(Constraint paramConstraint, String paramString)
  {
    String[] arrayOfString = paramString.split(",");
    for (int i = 0; i < arrayOfString.length; i++)
    {
      paramString = arrayOfString[i].split("=");
      if (paramString.length != 2) {
        Log.w("ConstraintSet", " Unable to parse " + arrayOfString[i]);
      } else {
        paramConstraint.setFloatValue(paramString[0], Float.parseFloat(paramString[1]));
      }
    }
  }
  
  public void parseIntAttributes(Constraint paramConstraint, String paramString)
  {
    paramString = paramString.split(",");
    for (int i = 0; i < paramString.length; i++)
    {
      String[] arrayOfString = paramString[i].split("=");
      if (arrayOfString.length != 2) {
        Log.w("ConstraintSet", " Unable to parse " + paramString[i]);
      } else {
        paramConstraint.setFloatValue(arrayOfString[0], Integer.decode(arrayOfString[1]).intValue());
      }
    }
  }
  
  public void parseStringAttributes(Constraint paramConstraint, String paramString)
  {
    paramString = splitString(paramString);
    for (int i = 0; i < paramString.length; i++)
    {
      String[] arrayOfString = paramString[i].split("=");
      Log.w("ConstraintSet", " Unable to parse " + paramString[i]);
      paramConstraint.setStringValue(arrayOfString[0], arrayOfString[1]);
    }
  }
  
  public void readFallback(ConstraintLayout paramConstraintLayout)
  {
    int j = paramConstraintLayout.getChildCount();
    for (int i = 0; i < j; i++)
    {
      View localView = paramConstraintLayout.getChildAt(i);
      Object localObject = (ConstraintLayout.LayoutParams)localView.getLayoutParams();
      int k = localView.getId();
      if ((this.mForceId) && (k == -1)) {
        throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
      }
      if (!this.mConstraints.containsKey(Integer.valueOf(k))) {
        this.mConstraints.put(Integer.valueOf(k), new Constraint());
      }
      Constraint localConstraint = (Constraint)this.mConstraints.get(Integer.valueOf(k));
      if (localConstraint != null)
      {
        if (!localConstraint.layout.mApply)
        {
          localConstraint.fillFrom(k, (ConstraintLayout.LayoutParams)localObject);
          if ((localView instanceof ConstraintHelper))
          {
            localConstraint.layout.mReferenceIds = ((ConstraintHelper)localView).getReferencedIds();
            if ((localView instanceof Barrier))
            {
              localObject = (Barrier)localView;
              localConstraint.layout.mBarrierAllowsGoneWidgets = ((Barrier)localObject).getAllowsGoneWidget();
              localConstraint.layout.mBarrierDirection = ((Barrier)localObject).getType();
              localConstraint.layout.mBarrierMargin = ((Barrier)localObject).getMargin();
            }
          }
          localConstraint.layout.mApply = true;
        }
        if (!localConstraint.propertySet.mApply)
        {
          localConstraint.propertySet.visibility = localView.getVisibility();
          localConstraint.propertySet.alpha = localView.getAlpha();
          localConstraint.propertySet.mApply = true;
        }
        if ((Build.VERSION.SDK_INT >= 17) && (!localConstraint.transform.mApply))
        {
          localConstraint.transform.mApply = true;
          localConstraint.transform.rotation = localView.getRotation();
          localConstraint.transform.rotationX = localView.getRotationX();
          localConstraint.transform.rotationY = localView.getRotationY();
          localConstraint.transform.scaleX = localView.getScaleX();
          localConstraint.transform.scaleY = localView.getScaleY();
          float f2 = localView.getPivotX();
          float f1 = localView.getPivotY();
          if ((f2 != 0.0D) || (f1 != 0.0D))
          {
            localConstraint.transform.transformPivotX = f2;
            localConstraint.transform.transformPivotY = f1;
          }
          localConstraint.transform.translationX = localView.getTranslationX();
          localConstraint.transform.translationY = localView.getTranslationY();
          if (Build.VERSION.SDK_INT >= 21)
          {
            localConstraint.transform.translationZ = localView.getTranslationZ();
            if (localConstraint.transform.applyElevation) {
              localConstraint.transform.elevation = localView.getElevation();
            }
          }
        }
      }
    }
  }
  
  public void readFallback(ConstraintSet paramConstraintSet)
  {
    Iterator localIterator1 = paramConstraintSet.mConstraints.keySet().iterator();
    while (localIterator1.hasNext())
    {
      Object localObject = (Integer)localIterator1.next();
      int i = ((Integer)localObject).intValue();
      Constraint localConstraint = (Constraint)paramConstraintSet.mConstraints.get(localObject);
      if (!this.mConstraints.containsKey(Integer.valueOf(i))) {
        this.mConstraints.put(Integer.valueOf(i), new Constraint());
      }
      localObject = (Constraint)this.mConstraints.get(Integer.valueOf(i));
      if (localObject != null)
      {
        if (!((Constraint)localObject).layout.mApply) {
          ((Constraint)localObject).layout.copyFrom(localConstraint.layout);
        }
        if (!((Constraint)localObject).propertySet.mApply) {
          ((Constraint)localObject).propertySet.copyFrom(localConstraint.propertySet);
        }
        if (!((Constraint)localObject).transform.mApply) {
          ((Constraint)localObject).transform.copyFrom(localConstraint.transform);
        }
        if (!((Constraint)localObject).motion.mApply) {
          ((Constraint)localObject).motion.copyFrom(localConstraint.motion);
        }
        Iterator localIterator2 = localConstraint.mCustomConstraints.keySet().iterator();
        while (localIterator2.hasNext())
        {
          String str = (String)localIterator2.next();
          if (!((Constraint)localObject).mCustomConstraints.containsKey(str)) {
            ((Constraint)localObject).mCustomConstraints.put(str, (ConstraintAttribute)localConstraint.mCustomConstraints.get(str));
          }
        }
      }
    }
  }
  
  public void removeAttribute(String paramString)
  {
    this.mSavedAttributes.remove(paramString);
  }
  
  public void removeFromHorizontalChain(int paramInt)
  {
    if (this.mConstraints.containsKey(Integer.valueOf(paramInt)))
    {
      Constraint localConstraint = (Constraint)this.mConstraints.get(Integer.valueOf(paramInt));
      if (localConstraint == null) {
        return;
      }
      int i = localConstraint.layout.leftToRight;
      int j = localConstraint.layout.rightToLeft;
      if ((i == -1) && (j == -1))
      {
        j = localConstraint.layout.startToEnd;
        int k = localConstraint.layout.endToStart;
        if ((j != -1) || (k != -1)) {
          if ((j != -1) && (k != -1))
          {
            connect(j, 7, k, 6, 0);
            connect(k, 6, i, 7, 0);
          }
          else if (k != -1)
          {
            if (localConstraint.layout.rightToRight != -1) {
              connect(i, 7, localConstraint.layout.rightToRight, 7, 0);
            } else if (localConstraint.layout.leftToLeft != -1) {
              connect(k, 6, localConstraint.layout.leftToLeft, 6, 0);
            }
          }
        }
        clear(paramInt, 6);
        clear(paramInt, 7);
      }
      else
      {
        if ((i != -1) && (j != -1))
        {
          connect(i, 2, j, 1, 0);
          connect(j, 1, i, 2, 0);
        }
        else if (localConstraint.layout.rightToRight != -1)
        {
          connect(i, 2, localConstraint.layout.rightToRight, 2, 0);
        }
        else if (localConstraint.layout.leftToLeft != -1)
        {
          connect(j, 1, localConstraint.layout.leftToLeft, 1, 0);
        }
        clear(paramInt, 1);
        clear(paramInt, 2);
      }
    }
  }
  
  public void removeFromVerticalChain(int paramInt)
  {
    if (this.mConstraints.containsKey(Integer.valueOf(paramInt)))
    {
      Constraint localConstraint = (Constraint)this.mConstraints.get(Integer.valueOf(paramInt));
      if (localConstraint == null) {
        return;
      }
      int j = localConstraint.layout.topToBottom;
      int i = localConstraint.layout.bottomToTop;
      if ((j != -1) || (i != -1)) {
        if ((j != -1) && (i != -1))
        {
          connect(j, 4, i, 3, 0);
          connect(i, 3, j, 4, 0);
        }
        else if (localConstraint.layout.bottomToBottom != -1)
        {
          connect(j, 4, localConstraint.layout.bottomToBottom, 4, 0);
        }
        else if (localConstraint.layout.topToTop != -1)
        {
          connect(i, 3, localConstraint.layout.topToTop, 3, 0);
        }
      }
    }
    clear(paramInt, 3);
    clear(paramInt, 4);
  }
  
  public void setAlpha(int paramInt, float paramFloat)
  {
    get(paramInt).propertySet.alpha = paramFloat;
  }
  
  public void setApplyElevation(int paramInt, boolean paramBoolean)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      get(paramInt).transform.applyElevation = paramBoolean;
    }
  }
  
  public void setBarrierType(int paramInt1, int paramInt2)
  {
    get(paramInt1).layout.mHelperType = paramInt2;
  }
  
  public void setColorValue(int paramInt1, String paramString, int paramInt2)
  {
    get(paramInt1).setColorValue(paramString, paramInt2);
  }
  
  public void setDimensionRatio(int paramInt, String paramString)
  {
    get(paramInt).layout.dimensionRatio = paramString;
  }
  
  public void setEditorAbsoluteX(int paramInt1, int paramInt2)
  {
    get(paramInt1).layout.editorAbsoluteX = paramInt2;
  }
  
  public void setEditorAbsoluteY(int paramInt1, int paramInt2)
  {
    get(paramInt1).layout.editorAbsoluteY = paramInt2;
  }
  
  public void setElevation(int paramInt, float paramFloat)
  {
    if (Build.VERSION.SDK_INT >= 21)
    {
      get(paramInt).transform.elevation = paramFloat;
      get(paramInt).transform.applyElevation = true;
    }
  }
  
  public void setFloatValue(int paramInt, String paramString, float paramFloat)
  {
    get(paramInt).setFloatValue(paramString, paramFloat);
  }
  
  public void setForceId(boolean paramBoolean)
  {
    this.mForceId = paramBoolean;
  }
  
  public void setGoneMargin(int paramInt1, int paramInt2, int paramInt3)
  {
    Constraint localConstraint = get(paramInt1);
    switch (paramInt2)
    {
    default: 
      throw new IllegalArgumentException("unknown constraint");
    case 7: 
      localConstraint.layout.goneEndMargin = paramInt3;
      break;
    case 6: 
      localConstraint.layout.goneStartMargin = paramInt3;
      break;
    case 5: 
      localConstraint.layout.goneBaselineMargin = paramInt3;
      break;
    case 4: 
      localConstraint.layout.goneBottomMargin = paramInt3;
      break;
    case 3: 
      localConstraint.layout.goneTopMargin = paramInt3;
      break;
    case 2: 
      localConstraint.layout.goneRightMargin = paramInt3;
      break;
    case 1: 
      localConstraint.layout.goneLeftMargin = paramInt3;
    }
  }
  
  public void setGuidelineBegin(int paramInt1, int paramInt2)
  {
    get(paramInt1).layout.guideBegin = paramInt2;
    get(paramInt1).layout.guideEnd = -1;
    get(paramInt1).layout.guidePercent = -1.0F;
  }
  
  public void setGuidelineEnd(int paramInt1, int paramInt2)
  {
    get(paramInt1).layout.guideEnd = paramInt2;
    get(paramInt1).layout.guideBegin = -1;
    get(paramInt1).layout.guidePercent = -1.0F;
  }
  
  public void setGuidelinePercent(int paramInt, float paramFloat)
  {
    get(paramInt).layout.guidePercent = paramFloat;
    get(paramInt).layout.guideEnd = -1;
    get(paramInt).layout.guideBegin = -1;
  }
  
  public void setHorizontalBias(int paramInt, float paramFloat)
  {
    get(paramInt).layout.horizontalBias = paramFloat;
  }
  
  public void setHorizontalChainStyle(int paramInt1, int paramInt2)
  {
    get(paramInt1).layout.horizontalChainStyle = paramInt2;
  }
  
  public void setHorizontalWeight(int paramInt, float paramFloat)
  {
    get(paramInt).layout.horizontalWeight = paramFloat;
  }
  
  public void setIntValue(int paramInt1, String paramString, int paramInt2)
  {
    get(paramInt1).setIntValue(paramString, paramInt2);
  }
  
  public void setLayoutWrapBehavior(int paramInt1, int paramInt2)
  {
    if ((paramInt2 >= 0) && (paramInt2 <= 3)) {
      get(paramInt1).layout.mWrapBehavior = paramInt2;
    }
  }
  
  public void setMargin(int paramInt1, int paramInt2, int paramInt3)
  {
    Constraint localConstraint = get(paramInt1);
    switch (paramInt2)
    {
    default: 
      throw new IllegalArgumentException("unknown constraint");
    case 7: 
      localConstraint.layout.endMargin = paramInt3;
      break;
    case 6: 
      localConstraint.layout.startMargin = paramInt3;
      break;
    case 5: 
      localConstraint.layout.baselineMargin = paramInt3;
      break;
    case 4: 
      localConstraint.layout.bottomMargin = paramInt3;
      break;
    case 3: 
      localConstraint.layout.topMargin = paramInt3;
      break;
    case 2: 
      localConstraint.layout.rightMargin = paramInt3;
      break;
    case 1: 
      localConstraint.layout.leftMargin = paramInt3;
    }
  }
  
  public void setReferencedIds(int paramInt, int... paramVarArgs)
  {
    get(paramInt).layout.mReferenceIds = paramVarArgs;
  }
  
  public void setRotation(int paramInt, float paramFloat)
  {
    get(paramInt).transform.rotation = paramFloat;
  }
  
  public void setRotationX(int paramInt, float paramFloat)
  {
    get(paramInt).transform.rotationX = paramFloat;
  }
  
  public void setRotationY(int paramInt, float paramFloat)
  {
    get(paramInt).transform.rotationY = paramFloat;
  }
  
  public void setScaleX(int paramInt, float paramFloat)
  {
    get(paramInt).transform.scaleX = paramFloat;
  }
  
  public void setScaleY(int paramInt, float paramFloat)
  {
    get(paramInt).transform.scaleY = paramFloat;
  }
  
  public void setStringValue(int paramInt, String paramString1, String paramString2)
  {
    get(paramInt).setStringValue(paramString1, paramString2);
  }
  
  public void setTransformPivot(int paramInt, float paramFloat1, float paramFloat2)
  {
    Constraint localConstraint = get(paramInt);
    localConstraint.transform.transformPivotY = paramFloat2;
    localConstraint.transform.transformPivotX = paramFloat1;
  }
  
  public void setTransformPivotX(int paramInt, float paramFloat)
  {
    get(paramInt).transform.transformPivotX = paramFloat;
  }
  
  public void setTransformPivotY(int paramInt, float paramFloat)
  {
    get(paramInt).transform.transformPivotY = paramFloat;
  }
  
  public void setTranslation(int paramInt, float paramFloat1, float paramFloat2)
  {
    Constraint localConstraint = get(paramInt);
    localConstraint.transform.translationX = paramFloat1;
    localConstraint.transform.translationY = paramFloat2;
  }
  
  public void setTranslationX(int paramInt, float paramFloat)
  {
    get(paramInt).transform.translationX = paramFloat;
  }
  
  public void setTranslationY(int paramInt, float paramFloat)
  {
    get(paramInt).transform.translationY = paramFloat;
  }
  
  public void setTranslationZ(int paramInt, float paramFloat)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      get(paramInt).transform.translationZ = paramFloat;
    }
  }
  
  public void setValidateOnParse(boolean paramBoolean)
  {
    this.mValidate = paramBoolean;
  }
  
  public void setVerticalBias(int paramInt, float paramFloat)
  {
    get(paramInt).layout.verticalBias = paramFloat;
  }
  
  public void setVerticalChainStyle(int paramInt1, int paramInt2)
  {
    get(paramInt1).layout.verticalChainStyle = paramInt2;
  }
  
  public void setVerticalWeight(int paramInt, float paramFloat)
  {
    get(paramInt).layout.verticalWeight = paramFloat;
  }
  
  public void setVisibility(int paramInt1, int paramInt2)
  {
    get(paramInt1).propertySet.visibility = paramInt2;
  }
  
  public void setVisibilityMode(int paramInt1, int paramInt2)
  {
    get(paramInt1).propertySet.mVisibilityMode = paramInt2;
  }
  
  public void writeState(Writer paramWriter, ConstraintLayout paramConstraintLayout, int paramInt)
    throws IOException
  {
    paramWriter.write("\n---------------------------------------------\n");
    if ((paramInt & 0x1) == 1) {
      new WriteXmlEngine(paramWriter, paramConstraintLayout, paramInt).writeLayout();
    } else {
      new WriteJsonEngine(paramWriter, paramConstraintLayout, paramInt).writeLayout();
    }
    paramWriter.write("\n---------------------------------------------\n");
  }
  
  public static class Constraint
  {
    public final ConstraintSet.Layout layout = new ConstraintSet.Layout();
    public HashMap<String, ConstraintAttribute> mCustomConstraints = new HashMap();
    Delta mDelta;
    String mTargetString;
    int mViewId;
    public final ConstraintSet.Motion motion = new ConstraintSet.Motion();
    public final ConstraintSet.PropertySet propertySet = new ConstraintSet.PropertySet();
    public final ConstraintSet.Transform transform = new ConstraintSet.Transform();
    
    private void fillFrom(int paramInt, ConstraintLayout.LayoutParams paramLayoutParams)
    {
      this.mViewId = paramInt;
      this.layout.leftToLeft = paramLayoutParams.leftToLeft;
      this.layout.leftToRight = paramLayoutParams.leftToRight;
      this.layout.rightToLeft = paramLayoutParams.rightToLeft;
      this.layout.rightToRight = paramLayoutParams.rightToRight;
      this.layout.topToTop = paramLayoutParams.topToTop;
      this.layout.topToBottom = paramLayoutParams.topToBottom;
      this.layout.bottomToTop = paramLayoutParams.bottomToTop;
      this.layout.bottomToBottom = paramLayoutParams.bottomToBottom;
      this.layout.baselineToBaseline = paramLayoutParams.baselineToBaseline;
      this.layout.baselineToTop = paramLayoutParams.baselineToTop;
      this.layout.baselineToBottom = paramLayoutParams.baselineToBottom;
      this.layout.startToEnd = paramLayoutParams.startToEnd;
      this.layout.startToStart = paramLayoutParams.startToStart;
      this.layout.endToStart = paramLayoutParams.endToStart;
      this.layout.endToEnd = paramLayoutParams.endToEnd;
      this.layout.horizontalBias = paramLayoutParams.horizontalBias;
      this.layout.verticalBias = paramLayoutParams.verticalBias;
      this.layout.dimensionRatio = paramLayoutParams.dimensionRatio;
      this.layout.circleConstraint = paramLayoutParams.circleConstraint;
      this.layout.circleRadius = paramLayoutParams.circleRadius;
      this.layout.circleAngle = paramLayoutParams.circleAngle;
      this.layout.editorAbsoluteX = paramLayoutParams.editorAbsoluteX;
      this.layout.editorAbsoluteY = paramLayoutParams.editorAbsoluteY;
      this.layout.orientation = paramLayoutParams.orientation;
      this.layout.guidePercent = paramLayoutParams.guidePercent;
      this.layout.guideBegin = paramLayoutParams.guideBegin;
      this.layout.guideEnd = paramLayoutParams.guideEnd;
      this.layout.mWidth = paramLayoutParams.width;
      this.layout.mHeight = paramLayoutParams.height;
      this.layout.leftMargin = paramLayoutParams.leftMargin;
      this.layout.rightMargin = paramLayoutParams.rightMargin;
      this.layout.topMargin = paramLayoutParams.topMargin;
      this.layout.bottomMargin = paramLayoutParams.bottomMargin;
      this.layout.baselineMargin = paramLayoutParams.baselineMargin;
      this.layout.verticalWeight = paramLayoutParams.verticalWeight;
      this.layout.horizontalWeight = paramLayoutParams.horizontalWeight;
      this.layout.verticalChainStyle = paramLayoutParams.verticalChainStyle;
      this.layout.horizontalChainStyle = paramLayoutParams.horizontalChainStyle;
      this.layout.constrainedWidth = paramLayoutParams.constrainedWidth;
      this.layout.constrainedHeight = paramLayoutParams.constrainedHeight;
      this.layout.widthDefault = paramLayoutParams.matchConstraintDefaultWidth;
      this.layout.heightDefault = paramLayoutParams.matchConstraintDefaultHeight;
      this.layout.widthMax = paramLayoutParams.matchConstraintMaxWidth;
      this.layout.heightMax = paramLayoutParams.matchConstraintMaxHeight;
      this.layout.widthMin = paramLayoutParams.matchConstraintMinWidth;
      this.layout.heightMin = paramLayoutParams.matchConstraintMinHeight;
      this.layout.widthPercent = paramLayoutParams.matchConstraintPercentWidth;
      this.layout.heightPercent = paramLayoutParams.matchConstraintPercentHeight;
      this.layout.mConstraintTag = paramLayoutParams.constraintTag;
      this.layout.goneTopMargin = paramLayoutParams.goneTopMargin;
      this.layout.goneBottomMargin = paramLayoutParams.goneBottomMargin;
      this.layout.goneLeftMargin = paramLayoutParams.goneLeftMargin;
      this.layout.goneRightMargin = paramLayoutParams.goneRightMargin;
      this.layout.goneStartMargin = paramLayoutParams.goneStartMargin;
      this.layout.goneEndMargin = paramLayoutParams.goneEndMargin;
      this.layout.goneBaselineMargin = paramLayoutParams.goneBaselineMargin;
      this.layout.mWrapBehavior = paramLayoutParams.wrapBehaviorInParent;
      if (Build.VERSION.SDK_INT >= 17)
      {
        this.layout.endMargin = paramLayoutParams.getMarginEnd();
        this.layout.startMargin = paramLayoutParams.getMarginStart();
      }
    }
    
    private void fillFromConstraints(int paramInt, Constraints.LayoutParams paramLayoutParams)
    {
      fillFrom(paramInt, paramLayoutParams);
      this.propertySet.alpha = paramLayoutParams.alpha;
      this.transform.rotation = paramLayoutParams.rotation;
      this.transform.rotationX = paramLayoutParams.rotationX;
      this.transform.rotationY = paramLayoutParams.rotationY;
      this.transform.scaleX = paramLayoutParams.scaleX;
      this.transform.scaleY = paramLayoutParams.scaleY;
      this.transform.transformPivotX = paramLayoutParams.transformPivotX;
      this.transform.transformPivotY = paramLayoutParams.transformPivotY;
      this.transform.translationX = paramLayoutParams.translationX;
      this.transform.translationY = paramLayoutParams.translationY;
      this.transform.translationZ = paramLayoutParams.translationZ;
      this.transform.elevation = paramLayoutParams.elevation;
      this.transform.applyElevation = paramLayoutParams.applyElevation;
    }
    
    private void fillFromConstraints(ConstraintHelper paramConstraintHelper, int paramInt, Constraints.LayoutParams paramLayoutParams)
    {
      fillFromConstraints(paramInt, paramLayoutParams);
      if ((paramConstraintHelper instanceof Barrier))
      {
        this.layout.mHelperType = 1;
        paramConstraintHelper = (Barrier)paramConstraintHelper;
        this.layout.mBarrierDirection = paramConstraintHelper.getType();
        this.layout.mReferenceIds = paramConstraintHelper.getReferencedIds();
        this.layout.mBarrierMargin = paramConstraintHelper.getMargin();
      }
    }
    
    private ConstraintAttribute get(String paramString, ConstraintAttribute.AttributeType paramAttributeType)
    {
      if (this.mCustomConstraints.containsKey(paramString))
      {
        paramString = (ConstraintAttribute)this.mCustomConstraints.get(paramString);
        if (paramString.getType() != paramAttributeType) {
          throw new IllegalArgumentException("ConstraintAttribute is already a " + paramString.getType().name());
        }
      }
      else
      {
        paramAttributeType = new ConstraintAttribute(paramString, paramAttributeType);
        this.mCustomConstraints.put(paramString, paramAttributeType);
        paramString = paramAttributeType;
      }
      return paramString;
    }
    
    private void setColorValue(String paramString, int paramInt)
    {
      get(paramString, ConstraintAttribute.AttributeType.COLOR_TYPE).setColorValue(paramInt);
    }
    
    private void setFloatValue(String paramString, float paramFloat)
    {
      get(paramString, ConstraintAttribute.AttributeType.FLOAT_TYPE).setFloatValue(paramFloat);
    }
    
    private void setIntValue(String paramString, int paramInt)
    {
      get(paramString, ConstraintAttribute.AttributeType.INT_TYPE).setIntValue(paramInt);
    }
    
    private void setStringValue(String paramString1, String paramString2)
    {
      get(paramString1, ConstraintAttribute.AttributeType.STRING_TYPE).setStringValue(paramString2);
    }
    
    public void applyDelta(Constraint paramConstraint)
    {
      Delta localDelta = this.mDelta;
      if (localDelta != null) {
        localDelta.applyDelta(paramConstraint);
      }
    }
    
    public void applyTo(ConstraintLayout.LayoutParams paramLayoutParams)
    {
      paramLayoutParams.leftToLeft = this.layout.leftToLeft;
      paramLayoutParams.leftToRight = this.layout.leftToRight;
      paramLayoutParams.rightToLeft = this.layout.rightToLeft;
      paramLayoutParams.rightToRight = this.layout.rightToRight;
      paramLayoutParams.topToTop = this.layout.topToTop;
      paramLayoutParams.topToBottom = this.layout.topToBottom;
      paramLayoutParams.bottomToTop = this.layout.bottomToTop;
      paramLayoutParams.bottomToBottom = this.layout.bottomToBottom;
      paramLayoutParams.baselineToBaseline = this.layout.baselineToBaseline;
      paramLayoutParams.baselineToTop = this.layout.baselineToTop;
      paramLayoutParams.baselineToBottom = this.layout.baselineToBottom;
      paramLayoutParams.startToEnd = this.layout.startToEnd;
      paramLayoutParams.startToStart = this.layout.startToStart;
      paramLayoutParams.endToStart = this.layout.endToStart;
      paramLayoutParams.endToEnd = this.layout.endToEnd;
      paramLayoutParams.leftMargin = this.layout.leftMargin;
      paramLayoutParams.rightMargin = this.layout.rightMargin;
      paramLayoutParams.topMargin = this.layout.topMargin;
      paramLayoutParams.bottomMargin = this.layout.bottomMargin;
      paramLayoutParams.goneStartMargin = this.layout.goneStartMargin;
      paramLayoutParams.goneEndMargin = this.layout.goneEndMargin;
      paramLayoutParams.goneTopMargin = this.layout.goneTopMargin;
      paramLayoutParams.goneBottomMargin = this.layout.goneBottomMargin;
      paramLayoutParams.horizontalBias = this.layout.horizontalBias;
      paramLayoutParams.verticalBias = this.layout.verticalBias;
      paramLayoutParams.circleConstraint = this.layout.circleConstraint;
      paramLayoutParams.circleRadius = this.layout.circleRadius;
      paramLayoutParams.circleAngle = this.layout.circleAngle;
      paramLayoutParams.dimensionRatio = this.layout.dimensionRatio;
      paramLayoutParams.editorAbsoluteX = this.layout.editorAbsoluteX;
      paramLayoutParams.editorAbsoluteY = this.layout.editorAbsoluteY;
      paramLayoutParams.verticalWeight = this.layout.verticalWeight;
      paramLayoutParams.horizontalWeight = this.layout.horizontalWeight;
      paramLayoutParams.verticalChainStyle = this.layout.verticalChainStyle;
      paramLayoutParams.horizontalChainStyle = this.layout.horizontalChainStyle;
      paramLayoutParams.constrainedWidth = this.layout.constrainedWidth;
      paramLayoutParams.constrainedHeight = this.layout.constrainedHeight;
      paramLayoutParams.matchConstraintDefaultWidth = this.layout.widthDefault;
      paramLayoutParams.matchConstraintDefaultHeight = this.layout.heightDefault;
      paramLayoutParams.matchConstraintMaxWidth = this.layout.widthMax;
      paramLayoutParams.matchConstraintMaxHeight = this.layout.heightMax;
      paramLayoutParams.matchConstraintMinWidth = this.layout.widthMin;
      paramLayoutParams.matchConstraintMinHeight = this.layout.heightMin;
      paramLayoutParams.matchConstraintPercentWidth = this.layout.widthPercent;
      paramLayoutParams.matchConstraintPercentHeight = this.layout.heightPercent;
      paramLayoutParams.orientation = this.layout.orientation;
      paramLayoutParams.guidePercent = this.layout.guidePercent;
      paramLayoutParams.guideBegin = this.layout.guideBegin;
      paramLayoutParams.guideEnd = this.layout.guideEnd;
      paramLayoutParams.width = this.layout.mWidth;
      paramLayoutParams.height = this.layout.mHeight;
      if (this.layout.mConstraintTag != null) {
        paramLayoutParams.constraintTag = this.layout.mConstraintTag;
      }
      paramLayoutParams.wrapBehaviorInParent = this.layout.mWrapBehavior;
      if (Build.VERSION.SDK_INT >= 17)
      {
        paramLayoutParams.setMarginStart(this.layout.startMargin);
        paramLayoutParams.setMarginEnd(this.layout.endMargin);
      }
      paramLayoutParams.validate();
    }
    
    public Constraint clone()
    {
      Constraint localConstraint = new Constraint();
      localConstraint.layout.copyFrom(this.layout);
      localConstraint.motion.copyFrom(this.motion);
      localConstraint.propertySet.copyFrom(this.propertySet);
      localConstraint.transform.copyFrom(this.transform);
      localConstraint.mViewId = this.mViewId;
      localConstraint.mDelta = this.mDelta;
      return localConstraint;
    }
    
    public void printDelta(String paramString)
    {
      Delta localDelta = this.mDelta;
      if (localDelta != null) {
        localDelta.printDelta(paramString);
      } else {
        Log.v(paramString, "DELTA IS NULL");
      }
    }
    
    static class Delta
    {
      private static final int INITIAL_BOOLEAN = 4;
      private static final int INITIAL_FLOAT = 10;
      private static final int INITIAL_INT = 10;
      private static final int INITIAL_STRING = 5;
      int mCountBoolean = 0;
      int mCountFloat = 0;
      int mCountInt = 0;
      int mCountString = 0;
      int[] mTypeBoolean = new int[4];
      int[] mTypeFloat = new int[10];
      int[] mTypeInt = new int[10];
      int[] mTypeString = new int[5];
      boolean[] mValueBoolean = new boolean[4];
      float[] mValueFloat = new float[10];
      int[] mValueInt = new int[10];
      String[] mValueString = new String[5];
      
      void add(int paramInt, float paramFloat)
      {
        int i = this.mCountFloat;
        Object localObject = this.mTypeFloat;
        if (i >= localObject.length)
        {
          this.mTypeFloat = Arrays.copyOf((int[])localObject, localObject.length * 2);
          localObject = this.mValueFloat;
          this.mValueFloat = Arrays.copyOf((float[])localObject, localObject.length * 2);
        }
        localObject = this.mTypeFloat;
        i = this.mCountFloat;
        localObject[i] = paramInt;
        localObject = this.mValueFloat;
        this.mCountFloat = (i + 1);
        localObject[i] = paramFloat;
      }
      
      void add(int paramInt1, int paramInt2)
      {
        int i = this.mCountInt;
        int[] arrayOfInt = this.mTypeInt;
        if (i >= arrayOfInt.length)
        {
          this.mTypeInt = Arrays.copyOf(arrayOfInt, arrayOfInt.length * 2);
          arrayOfInt = this.mValueInt;
          this.mValueInt = Arrays.copyOf(arrayOfInt, arrayOfInt.length * 2);
        }
        arrayOfInt = this.mTypeInt;
        i = this.mCountInt;
        arrayOfInt[i] = paramInt1;
        arrayOfInt = this.mValueInt;
        this.mCountInt = (i + 1);
        arrayOfInt[i] = paramInt2;
      }
      
      void add(int paramInt, String paramString)
      {
        int i = this.mCountString;
        Object localObject = this.mTypeString;
        if (i >= localObject.length)
        {
          this.mTypeString = Arrays.copyOf((int[])localObject, localObject.length * 2);
          localObject = this.mValueString;
          this.mValueString = ((String[])Arrays.copyOf((Object[])localObject, localObject.length * 2));
        }
        localObject = this.mTypeString;
        i = this.mCountString;
        localObject[i] = paramInt;
        localObject = this.mValueString;
        this.mCountString = (i + 1);
        localObject[i] = paramString;
      }
      
      void add(int paramInt, boolean paramBoolean)
      {
        int i = this.mCountBoolean;
        Object localObject = this.mTypeBoolean;
        if (i >= localObject.length)
        {
          this.mTypeBoolean = Arrays.copyOf((int[])localObject, localObject.length * 2);
          localObject = this.mValueBoolean;
          this.mValueBoolean = Arrays.copyOf((boolean[])localObject, localObject.length * 2);
        }
        localObject = this.mTypeBoolean;
        i = this.mCountBoolean;
        localObject[i] = paramInt;
        localObject = this.mValueBoolean;
        this.mCountBoolean = (i + 1);
        localObject[i] = paramBoolean;
      }
      
      void applyDelta(ConstraintSet.Constraint paramConstraint)
      {
        for (int i = 0; i < this.mCountInt; i++) {
          ConstraintSet.setDeltaValue(paramConstraint, this.mTypeInt[i], this.mValueInt[i]);
        }
        for (i = 0; i < this.mCountFloat; i++) {
          ConstraintSet.setDeltaValue(paramConstraint, this.mTypeFloat[i], this.mValueFloat[i]);
        }
        for (i = 0; i < this.mCountString; i++) {
          ConstraintSet.setDeltaValue(paramConstraint, this.mTypeString[i], this.mValueString[i]);
        }
        for (i = 0; i < this.mCountBoolean; i++) {
          ConstraintSet.setDeltaValue(paramConstraint, this.mTypeBoolean[i], this.mValueBoolean[i]);
        }
      }
      
      void printDelta(String paramString)
      {
        Log.v(paramString, "int");
        for (int i = 0; i < this.mCountInt; i++) {
          Log.v(paramString, this.mTypeInt[i] + " = " + this.mValueInt[i]);
        }
        Log.v(paramString, "float");
        for (i = 0; i < this.mCountFloat; i++) {
          Log.v(paramString, this.mTypeFloat[i] + " = " + this.mValueFloat[i]);
        }
        Log.v(paramString, "strings");
        for (i = 0; i < this.mCountString; i++) {
          Log.v(paramString, this.mTypeString[i] + " = " + this.mValueString[i]);
        }
        Log.v(paramString, "boolean");
        for (i = 0; i < this.mCountBoolean; i++) {
          Log.v(paramString, this.mTypeBoolean[i] + " = " + this.mValueBoolean[i]);
        }
      }
    }
  }
  
  public static class Layout
  {
    private static final int BARRIER_ALLOWS_GONE_WIDGETS = 75;
    private static final int BARRIER_DIRECTION = 72;
    private static final int BARRIER_MARGIN = 73;
    private static final int BASELINE_MARGIN = 80;
    private static final int BASELINE_TO_BASELINE = 1;
    private static final int BASELINE_TO_BOTTOM = 78;
    private static final int BASELINE_TO_TOP = 77;
    private static final int BOTTOM_MARGIN = 2;
    private static final int BOTTOM_TO_BOTTOM = 3;
    private static final int BOTTOM_TO_TOP = 4;
    private static final int CHAIN_USE_RTL = 71;
    private static final int CIRCLE = 61;
    private static final int CIRCLE_ANGLE = 63;
    private static final int CIRCLE_RADIUS = 62;
    private static final int CONSTRAINED_HEIGHT = 88;
    private static final int CONSTRAINED_WIDTH = 87;
    private static final int CONSTRAINT_REFERENCED_IDS = 74;
    private static final int CONSTRAINT_TAG = 89;
    private static final int DIMENSION_RATIO = 5;
    private static final int EDITOR_ABSOLUTE_X = 6;
    private static final int EDITOR_ABSOLUTE_Y = 7;
    private static final int END_MARGIN = 8;
    private static final int END_TO_END = 9;
    private static final int END_TO_START = 10;
    private static final int GONE_BASELINE_MARGIN = 79;
    private static final int GONE_BOTTOM_MARGIN = 11;
    private static final int GONE_END_MARGIN = 12;
    private static final int GONE_LEFT_MARGIN = 13;
    private static final int GONE_RIGHT_MARGIN = 14;
    private static final int GONE_START_MARGIN = 15;
    private static final int GONE_TOP_MARGIN = 16;
    private static final int GUIDE_BEGIN = 17;
    private static final int GUIDE_END = 18;
    private static final int GUIDE_PERCENT = 19;
    private static final int GUIDE_USE_RTL = 90;
    private static final int HEIGHT_DEFAULT = 82;
    private static final int HEIGHT_MAX = 83;
    private static final int HEIGHT_MIN = 85;
    private static final int HEIGHT_PERCENT = 70;
    private static final int HORIZONTAL_BIAS = 20;
    private static final int HORIZONTAL_STYLE = 39;
    private static final int HORIZONTAL_WEIGHT = 37;
    private static final int LAYOUT_CONSTRAINT_HEIGHT = 42;
    private static final int LAYOUT_CONSTRAINT_WIDTH = 41;
    private static final int LAYOUT_HEIGHT = 21;
    private static final int LAYOUT_WIDTH = 22;
    private static final int LAYOUT_WRAP_BEHAVIOR = 76;
    private static final int LEFT_MARGIN = 23;
    private static final int LEFT_TO_LEFT = 24;
    private static final int LEFT_TO_RIGHT = 25;
    private static final int ORIENTATION = 26;
    private static final int RIGHT_MARGIN = 27;
    private static final int RIGHT_TO_LEFT = 28;
    private static final int RIGHT_TO_RIGHT = 29;
    private static final int START_MARGIN = 30;
    private static final int START_TO_END = 31;
    private static final int START_TO_START = 32;
    private static final int TOP_MARGIN = 33;
    private static final int TOP_TO_BOTTOM = 34;
    private static final int TOP_TO_TOP = 35;
    public static final int UNSET = -1;
    public static final int UNSET_GONE_MARGIN = Integer.MIN_VALUE;
    private static final int UNUSED = 91;
    private static final int VERTICAL_BIAS = 36;
    private static final int VERTICAL_STYLE = 40;
    private static final int VERTICAL_WEIGHT = 38;
    private static final int WIDTH_DEFAULT = 81;
    private static final int WIDTH_MAX = 84;
    private static final int WIDTH_MIN = 86;
    private static final int WIDTH_PERCENT = 69;
    private static SparseIntArray mapToConstant;
    public int baselineMargin = 0;
    public int baselineToBaseline = -1;
    public int baselineToBottom = -1;
    public int baselineToTop = -1;
    public int bottomMargin = 0;
    public int bottomToBottom = -1;
    public int bottomToTop = -1;
    public float circleAngle = 0.0F;
    public int circleConstraint = -1;
    public int circleRadius = 0;
    public boolean constrainedHeight = false;
    public boolean constrainedWidth = false;
    public String dimensionRatio = null;
    public int editorAbsoluteX = -1;
    public int editorAbsoluteY = -1;
    public int endMargin = 0;
    public int endToEnd = -1;
    public int endToStart = -1;
    public int goneBaselineMargin = Integer.MIN_VALUE;
    public int goneBottomMargin = Integer.MIN_VALUE;
    public int goneEndMargin = Integer.MIN_VALUE;
    public int goneLeftMargin = Integer.MIN_VALUE;
    public int goneRightMargin = Integer.MIN_VALUE;
    public int goneStartMargin = Integer.MIN_VALUE;
    public int goneTopMargin = Integer.MIN_VALUE;
    public int guideBegin = -1;
    public int guideEnd = -1;
    public float guidePercent = -1.0F;
    public boolean guidelineUseRtl = true;
    public int heightDefault = 0;
    public int heightMax = 0;
    public int heightMin = 0;
    public float heightPercent = 1.0F;
    public float horizontalBias = 0.5F;
    public int horizontalChainStyle = 0;
    public float horizontalWeight = -1.0F;
    public int leftMargin = 0;
    public int leftToLeft = -1;
    public int leftToRight = -1;
    public boolean mApply = false;
    public boolean mBarrierAllowsGoneWidgets = true;
    public int mBarrierDirection = -1;
    public int mBarrierMargin = 0;
    public String mConstraintTag;
    public int mHeight;
    public int mHelperType = -1;
    public boolean mIsGuideline = false;
    public boolean mOverride = false;
    public String mReferenceIdString;
    public int[] mReferenceIds;
    public int mWidth;
    public int mWrapBehavior = 0;
    public int orientation = -1;
    public int rightMargin = 0;
    public int rightToLeft = -1;
    public int rightToRight = -1;
    public int startMargin = 0;
    public int startToEnd = -1;
    public int startToStart = -1;
    public int topMargin = 0;
    public int topToBottom = -1;
    public int topToTop = -1;
    public float verticalBias = 0.5F;
    public int verticalChainStyle = 0;
    public float verticalWeight = -1.0F;
    public int widthDefault = 0;
    public int widthMax = 0;
    public int widthMin = 0;
    public float widthPercent = 1.0F;
    
    static
    {
      SparseIntArray localSparseIntArray = new SparseIntArray();
      mapToConstant = localSparseIntArray;
      localSparseIntArray.append(R.styleable.Layout_layout_constraintLeft_toLeftOf, 24);
      mapToConstant.append(R.styleable.Layout_layout_constraintLeft_toRightOf, 25);
      mapToConstant.append(R.styleable.Layout_layout_constraintRight_toLeftOf, 28);
      mapToConstant.append(R.styleable.Layout_layout_constraintRight_toRightOf, 29);
      mapToConstant.append(R.styleable.Layout_layout_constraintTop_toTopOf, 35);
      mapToConstant.append(R.styleable.Layout_layout_constraintTop_toBottomOf, 34);
      mapToConstant.append(R.styleable.Layout_layout_constraintBottom_toTopOf, 4);
      mapToConstant.append(R.styleable.Layout_layout_constraintBottom_toBottomOf, 3);
      mapToConstant.append(R.styleable.Layout_layout_constraintBaseline_toBaselineOf, 1);
      mapToConstant.append(R.styleable.Layout_layout_editor_absoluteX, 6);
      mapToConstant.append(R.styleable.Layout_layout_editor_absoluteY, 7);
      mapToConstant.append(R.styleable.Layout_layout_constraintGuide_begin, 17);
      mapToConstant.append(R.styleable.Layout_layout_constraintGuide_end, 18);
      mapToConstant.append(R.styleable.Layout_layout_constraintGuide_percent, 19);
      mapToConstant.append(R.styleable.Layout_guidelineUseRtl, 90);
      mapToConstant.append(R.styleable.Layout_android_orientation, 26);
      mapToConstant.append(R.styleable.Layout_layout_constraintStart_toEndOf, 31);
      mapToConstant.append(R.styleable.Layout_layout_constraintStart_toStartOf, 32);
      mapToConstant.append(R.styleable.Layout_layout_constraintEnd_toStartOf, 10);
      mapToConstant.append(R.styleable.Layout_layout_constraintEnd_toEndOf, 9);
      mapToConstant.append(R.styleable.Layout_layout_goneMarginLeft, 13);
      mapToConstant.append(R.styleable.Layout_layout_goneMarginTop, 16);
      mapToConstant.append(R.styleable.Layout_layout_goneMarginRight, 14);
      mapToConstant.append(R.styleable.Layout_layout_goneMarginBottom, 11);
      mapToConstant.append(R.styleable.Layout_layout_goneMarginStart, 15);
      mapToConstant.append(R.styleable.Layout_layout_goneMarginEnd, 12);
      mapToConstant.append(R.styleable.Layout_layout_constraintVertical_weight, 38);
      mapToConstant.append(R.styleable.Layout_layout_constraintHorizontal_weight, 37);
      mapToConstant.append(R.styleable.Layout_layout_constraintHorizontal_chainStyle, 39);
      mapToConstant.append(R.styleable.Layout_layout_constraintVertical_chainStyle, 40);
      mapToConstant.append(R.styleable.Layout_layout_constraintHorizontal_bias, 20);
      mapToConstant.append(R.styleable.Layout_layout_constraintVertical_bias, 36);
      mapToConstant.append(R.styleable.Layout_layout_constraintDimensionRatio, 5);
      mapToConstant.append(R.styleable.Layout_layout_constraintLeft_creator, 91);
      mapToConstant.append(R.styleable.Layout_layout_constraintTop_creator, 91);
      mapToConstant.append(R.styleable.Layout_layout_constraintRight_creator, 91);
      mapToConstant.append(R.styleable.Layout_layout_constraintBottom_creator, 91);
      mapToConstant.append(R.styleable.Layout_layout_constraintBaseline_creator, 91);
      mapToConstant.append(R.styleable.Layout_android_layout_marginLeft, 23);
      mapToConstant.append(R.styleable.Layout_android_layout_marginRight, 27);
      mapToConstant.append(R.styleable.Layout_android_layout_marginStart, 30);
      mapToConstant.append(R.styleable.Layout_android_layout_marginEnd, 8);
      mapToConstant.append(R.styleable.Layout_android_layout_marginTop, 33);
      mapToConstant.append(R.styleable.Layout_android_layout_marginBottom, 2);
      mapToConstant.append(R.styleable.Layout_android_layout_width, 22);
      mapToConstant.append(R.styleable.Layout_android_layout_height, 21);
      mapToConstant.append(R.styleable.Layout_layout_constraintWidth, 41);
      mapToConstant.append(R.styleable.Layout_layout_constraintHeight, 42);
      mapToConstant.append(R.styleable.Layout_layout_constrainedWidth, 41);
      mapToConstant.append(R.styleable.Layout_layout_constrainedHeight, 42);
      mapToConstant.append(R.styleable.Layout_layout_wrapBehaviorInParent, 76);
      mapToConstant.append(R.styleable.Layout_layout_constraintCircle, 61);
      mapToConstant.append(R.styleable.Layout_layout_constraintCircleRadius, 62);
      mapToConstant.append(R.styleable.Layout_layout_constraintCircleAngle, 63);
      mapToConstant.append(R.styleable.Layout_layout_constraintWidth_percent, 69);
      mapToConstant.append(R.styleable.Layout_layout_constraintHeight_percent, 70);
      mapToConstant.append(R.styleable.Layout_chainUseRtl, 71);
      mapToConstant.append(R.styleable.Layout_barrierDirection, 72);
      mapToConstant.append(R.styleable.Layout_barrierMargin, 73);
      mapToConstant.append(R.styleable.Layout_constraint_referenced_ids, 74);
      mapToConstant.append(R.styleable.Layout_barrierAllowsGoneWidgets, 75);
    }
    
    public void copyFrom(Layout paramLayout)
    {
      this.mIsGuideline = paramLayout.mIsGuideline;
      this.mWidth = paramLayout.mWidth;
      this.mApply = paramLayout.mApply;
      this.mHeight = paramLayout.mHeight;
      this.guideBegin = paramLayout.guideBegin;
      this.guideEnd = paramLayout.guideEnd;
      this.guidePercent = paramLayout.guidePercent;
      this.guidelineUseRtl = paramLayout.guidelineUseRtl;
      this.leftToLeft = paramLayout.leftToLeft;
      this.leftToRight = paramLayout.leftToRight;
      this.rightToLeft = paramLayout.rightToLeft;
      this.rightToRight = paramLayout.rightToRight;
      this.topToTop = paramLayout.topToTop;
      this.topToBottom = paramLayout.topToBottom;
      this.bottomToTop = paramLayout.bottomToTop;
      this.bottomToBottom = paramLayout.bottomToBottom;
      this.baselineToBaseline = paramLayout.baselineToBaseline;
      this.baselineToTop = paramLayout.baselineToTop;
      this.baselineToBottom = paramLayout.baselineToBottom;
      this.startToEnd = paramLayout.startToEnd;
      this.startToStart = paramLayout.startToStart;
      this.endToStart = paramLayout.endToStart;
      this.endToEnd = paramLayout.endToEnd;
      this.horizontalBias = paramLayout.horizontalBias;
      this.verticalBias = paramLayout.verticalBias;
      this.dimensionRatio = paramLayout.dimensionRatio;
      this.circleConstraint = paramLayout.circleConstraint;
      this.circleRadius = paramLayout.circleRadius;
      this.circleAngle = paramLayout.circleAngle;
      this.editorAbsoluteX = paramLayout.editorAbsoluteX;
      this.editorAbsoluteY = paramLayout.editorAbsoluteY;
      this.orientation = paramLayout.orientation;
      this.leftMargin = paramLayout.leftMargin;
      this.rightMargin = paramLayout.rightMargin;
      this.topMargin = paramLayout.topMargin;
      this.bottomMargin = paramLayout.bottomMargin;
      this.endMargin = paramLayout.endMargin;
      this.startMargin = paramLayout.startMargin;
      this.baselineMargin = paramLayout.baselineMargin;
      this.goneLeftMargin = paramLayout.goneLeftMargin;
      this.goneTopMargin = paramLayout.goneTopMargin;
      this.goneRightMargin = paramLayout.goneRightMargin;
      this.goneBottomMargin = paramLayout.goneBottomMargin;
      this.goneEndMargin = paramLayout.goneEndMargin;
      this.goneStartMargin = paramLayout.goneStartMargin;
      this.goneBaselineMargin = paramLayout.goneBaselineMargin;
      this.verticalWeight = paramLayout.verticalWeight;
      this.horizontalWeight = paramLayout.horizontalWeight;
      this.horizontalChainStyle = paramLayout.horizontalChainStyle;
      this.verticalChainStyle = paramLayout.verticalChainStyle;
      this.widthDefault = paramLayout.widthDefault;
      this.heightDefault = paramLayout.heightDefault;
      this.widthMax = paramLayout.widthMax;
      this.heightMax = paramLayout.heightMax;
      this.widthMin = paramLayout.widthMin;
      this.heightMin = paramLayout.heightMin;
      this.widthPercent = paramLayout.widthPercent;
      this.heightPercent = paramLayout.heightPercent;
      this.mBarrierDirection = paramLayout.mBarrierDirection;
      this.mBarrierMargin = paramLayout.mBarrierMargin;
      this.mHelperType = paramLayout.mHelperType;
      this.mConstraintTag = paramLayout.mConstraintTag;
      int[] arrayOfInt = paramLayout.mReferenceIds;
      if ((arrayOfInt != null) && (paramLayout.mReferenceIdString == null)) {
        this.mReferenceIds = Arrays.copyOf(arrayOfInt, arrayOfInt.length);
      } else {
        this.mReferenceIds = null;
      }
      this.mReferenceIdString = paramLayout.mReferenceIdString;
      this.constrainedWidth = paramLayout.constrainedWidth;
      this.constrainedHeight = paramLayout.constrainedHeight;
      this.mBarrierAllowsGoneWidgets = paramLayout.mBarrierAllowsGoneWidgets;
      this.mWrapBehavior = paramLayout.mWrapBehavior;
    }
    
    public void dump(MotionScene paramMotionScene, StringBuilder paramStringBuilder)
    {
      Field[] arrayOfField = getClass().getDeclaredFields();
      paramStringBuilder.append("\n");
      for (int i = 0; i < arrayOfField.length; i++)
      {
        Object localObject2 = arrayOfField[i];
        String str = ((Field)localObject2).getName();
        if (!Modifier.isStatic(((Field)localObject2).getModifiers())) {
          try
          {
            Object localObject1 = ((Field)localObject2).get(this);
            Class localClass = ((Field)localObject2).getType();
            localObject2 = Integer.TYPE;
            if (localClass == localObject2)
            {
              localObject1 = (Integer)localObject1;
              if (((Integer)localObject1).intValue() != -1)
              {
                localObject2 = paramMotionScene.lookUpConstraintName(((Integer)localObject1).intValue());
                paramStringBuilder.append("    ");
                paramStringBuilder.append(str);
                paramStringBuilder.append(" = \"");
                if (localObject2 != null) {
                  localObject1 = localObject2;
                }
                paramStringBuilder.append(localObject1);
                paramStringBuilder.append("\"\n");
              }
            }
            else if (localClass == Float.TYPE)
            {
              localObject1 = (Float)localObject1;
              if (((Float)localObject1).floatValue() != -1.0F)
              {
                paramStringBuilder.append("    ");
                paramStringBuilder.append(str);
                paramStringBuilder.append(" = \"");
                paramStringBuilder.append(localObject1);
                paramStringBuilder.append("\"\n");
              }
            }
          }
          catch (IllegalAccessException localIllegalAccessException)
          {
            localIllegalAccessException.printStackTrace();
          }
        }
      }
    }
    
    void fillFromAttributeList(Context paramContext, AttributeSet paramAttributeSet)
    {
      paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.Layout);
      this.mApply = true;
      int j = paramContext.getIndexCount();
      for (int i = 0; i < j; i++)
      {
        int k = paramContext.getIndex(i);
        StringBuilder localStringBuilder;
        switch (mapToConstant.get(k))
        {
        case 43: 
        case 44: 
        case 45: 
        case 46: 
        case 47: 
        case 48: 
        case 49: 
        case 50: 
        case 51: 
        case 52: 
        case 53: 
        case 54: 
        case 55: 
        case 56: 
        case 57: 
        case 58: 
        case 59: 
        case 60: 
        case 64: 
        case 65: 
        case 66: 
        case 67: 
        case 68: 
        default: 
          localStringBuilder = new StringBuilder().append("Unknown attribute 0x");
          paramAttributeSet = Integer.toHexString(k);
          Log5ECF72.a(paramAttributeSet);
          LogE84000.a(paramAttributeSet);
          Log229316.a(paramAttributeSet);
          Log.w("ConstraintSet", paramAttributeSet + "   " + mapToConstant.get(k));
          break;
        case 91: 
          localStringBuilder = new StringBuilder().append("unused attribute 0x");
          paramAttributeSet = Integer.toHexString(k);
          Log5ECF72.a(paramAttributeSet);
          LogE84000.a(paramAttributeSet);
          Log229316.a(paramAttributeSet);
          Log.w("ConstraintSet", paramAttributeSet + "   " + mapToConstant.get(k));
          break;
        case 90: 
          this.guidelineUseRtl = paramContext.getBoolean(k, this.guidelineUseRtl);
          break;
        case 89: 
          this.mConstraintTag = paramContext.getString(k);
          break;
        case 88: 
          this.constrainedHeight = paramContext.getBoolean(k, this.constrainedHeight);
          break;
        case 87: 
          this.constrainedWidth = paramContext.getBoolean(k, this.constrainedWidth);
          break;
        case 86: 
          this.widthMin = paramContext.getDimensionPixelSize(k, this.widthMin);
          break;
        case 85: 
          this.heightMin = paramContext.getDimensionPixelSize(k, this.heightMin);
          break;
        case 84: 
          this.widthMax = paramContext.getDimensionPixelSize(k, this.widthMax);
          break;
        case 83: 
          this.heightMax = paramContext.getDimensionPixelSize(k, this.heightMax);
          break;
        case 82: 
          this.heightDefault = paramContext.getInt(k, this.heightDefault);
          break;
        case 81: 
          this.widthDefault = paramContext.getInt(k, this.widthDefault);
          break;
        case 80: 
          this.baselineMargin = paramContext.getDimensionPixelSize(k, this.baselineMargin);
          break;
        case 79: 
          this.goneBaselineMargin = paramContext.getDimensionPixelSize(k, this.goneBaselineMargin);
          break;
        case 78: 
          this.baselineToBottom = ConstraintSet.lookupID(paramContext, k, this.baselineToBottom);
          break;
        case 77: 
          this.baselineToTop = ConstraintSet.lookupID(paramContext, k, this.baselineToTop);
          break;
        case 76: 
          this.mWrapBehavior = paramContext.getInt(k, this.mWrapBehavior);
          break;
        case 75: 
          this.mBarrierAllowsGoneWidgets = paramContext.getBoolean(k, this.mBarrierAllowsGoneWidgets);
          break;
        case 74: 
          this.mReferenceIdString = paramContext.getString(k);
          break;
        case 73: 
          this.mBarrierMargin = paramContext.getDimensionPixelSize(k, this.mBarrierMargin);
          break;
        case 72: 
          this.mBarrierDirection = paramContext.getInt(k, this.mBarrierDirection);
          break;
        case 71: 
          Log.e("ConstraintSet", "CURRENTLY UNSUPPORTED");
          break;
        case 70: 
          this.heightPercent = paramContext.getFloat(k, 1.0F);
          break;
        case 69: 
          this.widthPercent = paramContext.getFloat(k, 1.0F);
          break;
        case 63: 
          this.circleAngle = paramContext.getFloat(k, this.circleAngle);
          break;
        case 62: 
          this.circleRadius = paramContext.getDimensionPixelSize(k, this.circleRadius);
          break;
        case 61: 
          this.circleConstraint = ConstraintSet.lookupID(paramContext, k, this.circleConstraint);
          break;
        case 42: 
          ConstraintSet.parseDimensionConstraints(this, paramContext, k, 1);
          break;
        case 41: 
          ConstraintSet.parseDimensionConstraints(this, paramContext, k, 0);
          break;
        case 40: 
          this.verticalChainStyle = paramContext.getInt(k, this.verticalChainStyle);
          break;
        case 39: 
          this.horizontalChainStyle = paramContext.getInt(k, this.horizontalChainStyle);
          break;
        case 38: 
          this.verticalWeight = paramContext.getFloat(k, this.verticalWeight);
          break;
        case 37: 
          this.horizontalWeight = paramContext.getFloat(k, this.horizontalWeight);
          break;
        case 36: 
          this.verticalBias = paramContext.getFloat(k, this.verticalBias);
          break;
        case 35: 
          this.topToTop = ConstraintSet.lookupID(paramContext, k, this.topToTop);
          break;
        case 34: 
          this.topToBottom = ConstraintSet.lookupID(paramContext, k, this.topToBottom);
          break;
        case 33: 
          this.topMargin = paramContext.getDimensionPixelSize(k, this.topMargin);
          break;
        case 32: 
          this.startToStart = ConstraintSet.lookupID(paramContext, k, this.startToStart);
          break;
        case 31: 
          this.startToEnd = ConstraintSet.lookupID(paramContext, k, this.startToEnd);
          break;
        case 30: 
          if (Build.VERSION.SDK_INT >= 17) {
            this.startMargin = paramContext.getDimensionPixelSize(k, this.startMargin);
          }
          break;
        case 29: 
          this.rightToRight = ConstraintSet.lookupID(paramContext, k, this.rightToRight);
          break;
        case 28: 
          this.rightToLeft = ConstraintSet.lookupID(paramContext, k, this.rightToLeft);
          break;
        case 27: 
          this.rightMargin = paramContext.getDimensionPixelSize(k, this.rightMargin);
          break;
        case 26: 
          this.orientation = paramContext.getInt(k, this.orientation);
          break;
        case 25: 
          this.leftToRight = ConstraintSet.lookupID(paramContext, k, this.leftToRight);
          break;
        case 24: 
          this.leftToLeft = ConstraintSet.lookupID(paramContext, k, this.leftToLeft);
          break;
        case 23: 
          this.leftMargin = paramContext.getDimensionPixelSize(k, this.leftMargin);
          break;
        case 22: 
          this.mWidth = paramContext.getLayoutDimension(k, this.mWidth);
          break;
        case 21: 
          this.mHeight = paramContext.getLayoutDimension(k, this.mHeight);
          break;
        case 20: 
          this.horizontalBias = paramContext.getFloat(k, this.horizontalBias);
          break;
        case 19: 
          this.guidePercent = paramContext.getFloat(k, this.guidePercent);
          break;
        case 18: 
          this.guideEnd = paramContext.getDimensionPixelOffset(k, this.guideEnd);
          break;
        case 17: 
          this.guideBegin = paramContext.getDimensionPixelOffset(k, this.guideBegin);
          break;
        case 16: 
          this.goneTopMargin = paramContext.getDimensionPixelSize(k, this.goneTopMargin);
          break;
        case 15: 
          this.goneStartMargin = paramContext.getDimensionPixelSize(k, this.goneStartMargin);
          break;
        case 14: 
          this.goneRightMargin = paramContext.getDimensionPixelSize(k, this.goneRightMargin);
          break;
        case 13: 
          this.goneLeftMargin = paramContext.getDimensionPixelSize(k, this.goneLeftMargin);
          break;
        case 12: 
          this.goneEndMargin = paramContext.getDimensionPixelSize(k, this.goneEndMargin);
          break;
        case 11: 
          this.goneBottomMargin = paramContext.getDimensionPixelSize(k, this.goneBottomMargin);
          break;
        case 10: 
          this.endToStart = ConstraintSet.lookupID(paramContext, k, this.endToStart);
          break;
        case 9: 
          this.endToEnd = ConstraintSet.lookupID(paramContext, k, this.endToEnd);
          break;
        case 8: 
          if (Build.VERSION.SDK_INT >= 17) {
            this.endMargin = paramContext.getDimensionPixelSize(k, this.endMargin);
          }
          break;
        case 7: 
          this.editorAbsoluteY = paramContext.getDimensionPixelOffset(k, this.editorAbsoluteY);
          break;
        case 6: 
          this.editorAbsoluteX = paramContext.getDimensionPixelOffset(k, this.editorAbsoluteX);
          break;
        case 5: 
          this.dimensionRatio = paramContext.getString(k);
          break;
        case 4: 
          this.bottomToTop = ConstraintSet.lookupID(paramContext, k, this.bottomToTop);
          break;
        case 3: 
          this.bottomToBottom = ConstraintSet.lookupID(paramContext, k, this.bottomToBottom);
          break;
        case 2: 
          this.bottomMargin = paramContext.getDimensionPixelSize(k, this.bottomMargin);
          break;
        case 1: 
          this.baselineToBaseline = ConstraintSet.lookupID(paramContext, k, this.baselineToBaseline);
        }
      }
      paramContext.recycle();
    }
  }
  
  public static class Motion
  {
    private static final int ANIMATE_CIRCLE_ANGLE_TO = 6;
    private static final int ANIMATE_RELATIVE_TO = 5;
    private static final int INTERPOLATOR_REFERENCE_ID = -2;
    private static final int INTERPOLATOR_UNDEFINED = -3;
    private static final int MOTION_DRAW_PATH = 4;
    private static final int MOTION_STAGGER = 7;
    private static final int PATH_MOTION_ARC = 2;
    private static final int QUANTIZE_MOTION_INTERPOLATOR = 10;
    private static final int QUANTIZE_MOTION_PHASE = 9;
    private static final int QUANTIZE_MOTION_STEPS = 8;
    private static final int SPLINE_STRING = -1;
    private static final int TRANSITION_EASING = 3;
    private static final int TRANSITION_PATH_ROTATE = 1;
    private static SparseIntArray mapToConstant;
    public int mAnimateCircleAngleTo = 0;
    public int mAnimateRelativeTo = -1;
    public boolean mApply = false;
    public int mDrawPath = 0;
    public float mMotionStagger = NaN.0F;
    public int mPathMotionArc = -1;
    public float mPathRotate = NaN.0F;
    public int mPolarRelativeTo = -1;
    public int mQuantizeInterpolatorID = -1;
    public String mQuantizeInterpolatorString = null;
    public int mQuantizeInterpolatorType = -3;
    public float mQuantizeMotionPhase = NaN.0F;
    public int mQuantizeMotionSteps = -1;
    public String mTransitionEasing = null;
    
    static
    {
      SparseIntArray localSparseIntArray = new SparseIntArray();
      mapToConstant = localSparseIntArray;
      localSparseIntArray.append(R.styleable.Motion_motionPathRotate, 1);
      mapToConstant.append(R.styleable.Motion_pathMotionArc, 2);
      mapToConstant.append(R.styleable.Motion_transitionEasing, 3);
      mapToConstant.append(R.styleable.Motion_drawPath, 4);
      mapToConstant.append(R.styleable.Motion_animateRelativeTo, 5);
      mapToConstant.append(R.styleable.Motion_animateCircleAngleTo, 6);
      mapToConstant.append(R.styleable.Motion_motionStagger, 7);
      mapToConstant.append(R.styleable.Motion_quantizeMotionSteps, 8);
      mapToConstant.append(R.styleable.Motion_quantizeMotionPhase, 9);
      mapToConstant.append(R.styleable.Motion_quantizeMotionInterpolator, 10);
    }
    
    public void copyFrom(Motion paramMotion)
    {
      this.mApply = paramMotion.mApply;
      this.mAnimateRelativeTo = paramMotion.mAnimateRelativeTo;
      this.mTransitionEasing = paramMotion.mTransitionEasing;
      this.mPathMotionArc = paramMotion.mPathMotionArc;
      this.mDrawPath = paramMotion.mDrawPath;
      this.mPathRotate = paramMotion.mPathRotate;
      this.mMotionStagger = paramMotion.mMotionStagger;
      this.mPolarRelativeTo = paramMotion.mPolarRelativeTo;
    }
    
    void fillFromAttributeList(Context paramContext, AttributeSet paramAttributeSet)
    {
      paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.Motion);
      this.mApply = true;
      int j = paramContext.getIndexCount();
      for (int i = 0; i < j; i++)
      {
        int k = paramContext.getIndex(i);
        switch (mapToConstant.get(k))
        {
        default: 
          break;
        case 10: 
          paramAttributeSet = paramContext.peekValue(k);
          if (paramAttributeSet.type == 1)
          {
            k = paramContext.getResourceId(k, -1);
            this.mQuantizeInterpolatorID = k;
            if (k != -1) {
              this.mQuantizeInterpolatorType = -2;
            }
          }
          else if (paramAttributeSet.type == 3)
          {
            paramAttributeSet = paramContext.getString(k);
            this.mQuantizeInterpolatorString = paramAttributeSet;
            if (paramAttributeSet.indexOf("/") > 0)
            {
              this.mQuantizeInterpolatorID = paramContext.getResourceId(k, -1);
              this.mQuantizeInterpolatorType = -2;
            }
            else
            {
              this.mQuantizeInterpolatorType = -1;
            }
          }
          else
          {
            this.mQuantizeInterpolatorType = paramContext.getInteger(k, this.mQuantizeInterpolatorID);
          }
          break;
        case 9: 
          this.mQuantizeMotionPhase = paramContext.getFloat(k, this.mQuantizeMotionPhase);
          break;
        case 8: 
          this.mQuantizeMotionSteps = paramContext.getInteger(k, this.mQuantizeMotionSteps);
          break;
        case 7: 
          this.mMotionStagger = paramContext.getFloat(k, this.mMotionStagger);
          break;
        case 6: 
          this.mAnimateCircleAngleTo = paramContext.getInteger(k, this.mAnimateCircleAngleTo);
          break;
        case 5: 
          this.mAnimateRelativeTo = ConstraintSet.lookupID(paramContext, k, this.mAnimateRelativeTo);
          break;
        case 4: 
          this.mDrawPath = paramContext.getInt(k, 0);
          break;
        case 3: 
          if (paramContext.peekValue(k).type == 3) {
            this.mTransitionEasing = paramContext.getString(k);
          } else {
            this.mTransitionEasing = androidx.constraintlayout.core.motion.utils.Easing.NAMED_EASING[paramContext.getInteger(k, 0)];
          }
          break;
        case 2: 
          this.mPathMotionArc = paramContext.getInt(k, this.mPathMotionArc);
          break;
        case 1: 
          this.mPathRotate = paramContext.getFloat(k, this.mPathRotate);
        }
      }
      paramContext.recycle();
    }
  }
  
  public static class PropertySet
  {
    public float alpha = 1.0F;
    public boolean mApply = false;
    public float mProgress = NaN.0F;
    public int mVisibilityMode = 0;
    public int visibility = 0;
    
    public void copyFrom(PropertySet paramPropertySet)
    {
      this.mApply = paramPropertySet.mApply;
      this.visibility = paramPropertySet.visibility;
      this.alpha = paramPropertySet.alpha;
      this.mProgress = paramPropertySet.mProgress;
      this.mVisibilityMode = paramPropertySet.mVisibilityMode;
    }
    
    void fillFromAttributeList(Context paramContext, AttributeSet paramAttributeSet)
    {
      paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.PropertySet);
      this.mApply = true;
      int j = paramContext.getIndexCount();
      for (int i = 0; i < j; i++)
      {
        int k = paramContext.getIndex(i);
        if (k == R.styleable.PropertySet_android_alpha)
        {
          this.alpha = paramContext.getFloat(k, this.alpha);
        }
        else if (k == R.styleable.PropertySet_android_visibility)
        {
          this.visibility = paramContext.getInt(k, this.visibility);
          this.visibility = ConstraintSet.VISIBILITY_FLAGS[this.visibility];
        }
        else if (k == R.styleable.PropertySet_visibilityMode)
        {
          this.mVisibilityMode = paramContext.getInt(k, this.mVisibilityMode);
        }
        else if (k == R.styleable.PropertySet_motionProgress)
        {
          this.mProgress = paramContext.getFloat(k, this.mProgress);
        }
      }
      paramContext.recycle();
    }
  }
  
  public static class Transform
  {
    private static final int ELEVATION = 11;
    private static final int ROTATION = 1;
    private static final int ROTATION_X = 2;
    private static final int ROTATION_Y = 3;
    private static final int SCALE_X = 4;
    private static final int SCALE_Y = 5;
    private static final int TRANSFORM_PIVOT_TARGET = 12;
    private static final int TRANSFORM_PIVOT_X = 6;
    private static final int TRANSFORM_PIVOT_Y = 7;
    private static final int TRANSLATION_X = 8;
    private static final int TRANSLATION_Y = 9;
    private static final int TRANSLATION_Z = 10;
    private static SparseIntArray mapToConstant;
    public boolean applyElevation = false;
    public float elevation = 0.0F;
    public boolean mApply = false;
    public float rotation = 0.0F;
    public float rotationX = 0.0F;
    public float rotationY = 0.0F;
    public float scaleX = 1.0F;
    public float scaleY = 1.0F;
    public int transformPivotTarget = -1;
    public float transformPivotX = NaN.0F;
    public float transformPivotY = NaN.0F;
    public float translationX = 0.0F;
    public float translationY = 0.0F;
    public float translationZ = 0.0F;
    
    static
    {
      SparseIntArray localSparseIntArray = new SparseIntArray();
      mapToConstant = localSparseIntArray;
      localSparseIntArray.append(R.styleable.Transform_android_rotation, 1);
      mapToConstant.append(R.styleable.Transform_android_rotationX, 2);
      mapToConstant.append(R.styleable.Transform_android_rotationY, 3);
      mapToConstant.append(R.styleable.Transform_android_scaleX, 4);
      mapToConstant.append(R.styleable.Transform_android_scaleY, 5);
      mapToConstant.append(R.styleable.Transform_android_transformPivotX, 6);
      mapToConstant.append(R.styleable.Transform_android_transformPivotY, 7);
      mapToConstant.append(R.styleable.Transform_android_translationX, 8);
      mapToConstant.append(R.styleable.Transform_android_translationY, 9);
      mapToConstant.append(R.styleable.Transform_android_translationZ, 10);
      mapToConstant.append(R.styleable.Transform_android_elevation, 11);
      mapToConstant.append(R.styleable.Transform_transformPivotTarget, 12);
    }
    
    public void copyFrom(Transform paramTransform)
    {
      this.mApply = paramTransform.mApply;
      this.rotation = paramTransform.rotation;
      this.rotationX = paramTransform.rotationX;
      this.rotationY = paramTransform.rotationY;
      this.scaleX = paramTransform.scaleX;
      this.scaleY = paramTransform.scaleY;
      this.transformPivotX = paramTransform.transformPivotX;
      this.transformPivotY = paramTransform.transformPivotY;
      this.transformPivotTarget = paramTransform.transformPivotTarget;
      this.translationX = paramTransform.translationX;
      this.translationY = paramTransform.translationY;
      this.translationZ = paramTransform.translationZ;
      this.applyElevation = paramTransform.applyElevation;
      this.elevation = paramTransform.elevation;
    }
    
    void fillFromAttributeList(Context paramContext, AttributeSet paramAttributeSet)
    {
      paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.Transform);
      this.mApply = true;
      int j = paramContext.getIndexCount();
      for (int i = 0; i < j; i++)
      {
        int k = paramContext.getIndex(i);
        switch (mapToConstant.get(k))
        {
        default: 
          break;
        case 12: 
          this.transformPivotTarget = ConstraintSet.lookupID(paramContext, k, this.transformPivotTarget);
          break;
        case 11: 
          if (Build.VERSION.SDK_INT >= 21)
          {
            this.applyElevation = true;
            this.elevation = paramContext.getDimension(k, this.elevation);
          }
          break;
        case 10: 
          if (Build.VERSION.SDK_INT >= 21) {
            this.translationZ = paramContext.getDimension(k, this.translationZ);
          }
          break;
        case 9: 
          this.translationY = paramContext.getDimension(k, this.translationY);
          break;
        case 8: 
          this.translationX = paramContext.getDimension(k, this.translationX);
          break;
        case 7: 
          this.transformPivotY = paramContext.getDimension(k, this.transformPivotY);
          break;
        case 6: 
          this.transformPivotX = paramContext.getDimension(k, this.transformPivotX);
          break;
        case 5: 
          this.scaleY = paramContext.getFloat(k, this.scaleY);
          break;
        case 4: 
          this.scaleX = paramContext.getFloat(k, this.scaleX);
          break;
        case 3: 
          this.rotationY = paramContext.getFloat(k, this.rotationY);
          break;
        case 2: 
          this.rotationX = paramContext.getFloat(k, this.rotationX);
          break;
        case 1: 
          this.rotation = paramContext.getFloat(k, this.rotation);
        }
      }
      paramContext.recycle();
    }
  }
  
  class WriteJsonEngine
  {
    private static final String SPACE = "       ";
    final String BASELINE = "'baseline'";
    final String BOTTOM = "'bottom'";
    final String END = "'end'";
    final String LEFT = "'left'";
    final String RIGHT = "'right'";
    final String START = "'start'";
    final String TOP = "'top'";
    Context context;
    int flags;
    HashMap<Integer, String> idMap = new HashMap();
    ConstraintLayout layout;
    int unknownCount = 0;
    Writer writer;
    
    WriteJsonEngine(Writer paramWriter, ConstraintLayout paramConstraintLayout, int paramInt)
      throws IOException
    {
      this.writer = paramWriter;
      this.layout = paramConstraintLayout;
      this.context = paramConstraintLayout.getContext();
      this.flags = paramInt;
    }
    
    private void writeDimension(String paramString, int paramInt1, int paramInt2, float paramFloat, int paramInt3, int paramInt4, boolean paramBoolean)
      throws IOException
    {
      if (paramInt1 == 0)
      {
        if ((paramInt4 == -1) && (paramInt3 == -1)) {
          switch (paramInt2)
          {
          default: 
            break;
          case 2: 
            this.writer.write("       " + paramString + ": '" + paramFloat + "%',\n");
            return;
          case 1: 
            this.writer.write("       " + paramString + ": '???????????',\n");
            return;
          }
        } else {
          switch (paramInt2)
          {
          default: 
            break;
          case 2: 
            this.writer.write("       " + paramString + ": {'" + paramFloat + "'% ," + paramInt3 + ", " + paramInt4 + "}\n");
            return;
          case 1: 
            this.writer.write("       " + paramString + ": {'wrap' ," + paramInt3 + ", " + paramInt4 + "}\n");
            return;
          case 0: 
            this.writer.write("       " + paramString + ": {'spread' ," + paramInt3 + ", " + paramInt4 + "}\n");
          }
        }
      }
      else if (paramInt1 == -2) {
        this.writer.write("       " + paramString + ": 'wrap'\n");
      } else if (paramInt1 == -1) {
        this.writer.write("       " + paramString + ": 'parent'\n");
      } else {
        this.writer.write("       " + paramString + ": " + paramInt1 + ",\n");
      }
    }
    
    private void writeGuideline(int paramInt1, int paramInt2, int paramInt3, float paramFloat) {}
    
    String getName(int paramInt)
    {
      if (this.idMap.containsKey(Integer.valueOf(paramInt))) {
        return "'" + (String)this.idMap.get(Integer.valueOf(paramInt)) + "'";
      }
      if (paramInt == 0) {
        return "'parent'";
      }
      String str = lookup(paramInt);
      this.idMap.put(Integer.valueOf(paramInt), str);
      return "'" + str + "'";
    }
    
    String lookup(int paramInt)
    {
      if (paramInt != -1) {}
      try
      {
        return this.context.getResources().getResourceEntryName(paramInt);
      }
      catch (Exception localException)
      {
        Object localObject;
        localStringBuilder = new StringBuilder().append("unknown");
        paramInt = this.unknownCount + 1;
        this.unknownCount = paramInt;
      }
      localObject = new java/lang/StringBuilder;
      ((StringBuilder)localObject).<init>();
      localObject = ((StringBuilder)localObject).append("unknown");
      paramInt = this.unknownCount + 1;
      this.unknownCount = paramInt;
      localObject = paramInt;
      return (String)localObject;
      StringBuilder localStringBuilder;
      return paramInt;
    }
    
    void writeCircle(int paramInt1, float paramFloat, int paramInt2)
      throws IOException
    {
      if (paramInt1 == -1) {
        return;
      }
      this.writer.write("       circle");
      this.writer.write(":[");
      this.writer.write(getName(paramInt1));
      this.writer.write(", " + paramFloat);
      this.writer.write(paramInt2 + "]");
    }
    
    void writeConstraint(String paramString1, int paramInt1, String paramString2, int paramInt2, int paramInt3)
      throws IOException
    {
      if (paramInt1 == -1) {
        return;
      }
      this.writer.write("       " + paramString1);
      this.writer.write(":[");
      this.writer.write(getName(paramInt1));
      this.writer.write(" , ");
      this.writer.write(paramString2);
      if (paramInt2 != 0) {
        this.writer.write(" , " + paramInt2);
      }
      this.writer.write("],\n");
    }
    
    void writeLayout()
      throws IOException
    {
      this.writer.write("\n'ConstraintSet':{\n");
      Iterator localIterator = ConstraintSet.this.mConstraints.keySet().iterator();
      while (localIterator.hasNext())
      {
        Object localObject2 = (Integer)localIterator.next();
        Object localObject1 = (ConstraintSet.Constraint)ConstraintSet.this.mConstraints.get(localObject2);
        localObject2 = getName(((Integer)localObject2).intValue());
        this.writer.write((String)localObject2 + ":{\n");
        localObject1 = ((ConstraintSet.Constraint)localObject1).layout;
        writeDimension("height", ((ConstraintSet.Layout)localObject1).mHeight, ((ConstraintSet.Layout)localObject1).heightDefault, ((ConstraintSet.Layout)localObject1).heightPercent, ((ConstraintSet.Layout)localObject1).heightMin, ((ConstraintSet.Layout)localObject1).heightMax, ((ConstraintSet.Layout)localObject1).constrainedHeight);
        writeDimension("width", ((ConstraintSet.Layout)localObject1).mWidth, ((ConstraintSet.Layout)localObject1).widthDefault, ((ConstraintSet.Layout)localObject1).widthPercent, ((ConstraintSet.Layout)localObject1).widthMin, ((ConstraintSet.Layout)localObject1).widthMax, ((ConstraintSet.Layout)localObject1).constrainedWidth);
        writeConstraint("'left'", ((ConstraintSet.Layout)localObject1).leftToLeft, "'left'", ((ConstraintSet.Layout)localObject1).leftMargin, ((ConstraintSet.Layout)localObject1).goneLeftMargin);
        writeConstraint("'left'", ((ConstraintSet.Layout)localObject1).leftToRight, "'right'", ((ConstraintSet.Layout)localObject1).leftMargin, ((ConstraintSet.Layout)localObject1).goneLeftMargin);
        writeConstraint("'right'", ((ConstraintSet.Layout)localObject1).rightToLeft, "'left'", ((ConstraintSet.Layout)localObject1).rightMargin, ((ConstraintSet.Layout)localObject1).goneRightMargin);
        writeConstraint("'right'", ((ConstraintSet.Layout)localObject1).rightToRight, "'right'", ((ConstraintSet.Layout)localObject1).rightMargin, ((ConstraintSet.Layout)localObject1).goneRightMargin);
        writeConstraint("'baseline'", ((ConstraintSet.Layout)localObject1).baselineToBaseline, "'baseline'", -1, ((ConstraintSet.Layout)localObject1).goneBaselineMargin);
        writeConstraint("'baseline'", ((ConstraintSet.Layout)localObject1).baselineToTop, "'top'", -1, ((ConstraintSet.Layout)localObject1).goneBaselineMargin);
        writeConstraint("'baseline'", ((ConstraintSet.Layout)localObject1).baselineToBottom, "'bottom'", -1, ((ConstraintSet.Layout)localObject1).goneBaselineMargin);
        writeConstraint("'top'", ((ConstraintSet.Layout)localObject1).topToBottom, "'bottom'", ((ConstraintSet.Layout)localObject1).topMargin, ((ConstraintSet.Layout)localObject1).goneTopMargin);
        writeConstraint("'top'", ((ConstraintSet.Layout)localObject1).topToTop, "'top'", ((ConstraintSet.Layout)localObject1).topMargin, ((ConstraintSet.Layout)localObject1).goneTopMargin);
        writeConstraint("'bottom'", ((ConstraintSet.Layout)localObject1).bottomToBottom, "'bottom'", ((ConstraintSet.Layout)localObject1).bottomMargin, ((ConstraintSet.Layout)localObject1).goneBottomMargin);
        writeConstraint("'bottom'", ((ConstraintSet.Layout)localObject1).bottomToTop, "'top'", ((ConstraintSet.Layout)localObject1).bottomMargin, ((ConstraintSet.Layout)localObject1).goneBottomMargin);
        writeConstraint("'start'", ((ConstraintSet.Layout)localObject1).startToStart, "'start'", ((ConstraintSet.Layout)localObject1).startMargin, ((ConstraintSet.Layout)localObject1).goneStartMargin);
        writeConstraint("'start'", ((ConstraintSet.Layout)localObject1).startToEnd, "'end'", ((ConstraintSet.Layout)localObject1).startMargin, ((ConstraintSet.Layout)localObject1).goneStartMargin);
        writeConstraint("'end'", ((ConstraintSet.Layout)localObject1).endToStart, "'start'", ((ConstraintSet.Layout)localObject1).endMargin, ((ConstraintSet.Layout)localObject1).goneEndMargin);
        writeConstraint("'end'", ((ConstraintSet.Layout)localObject1).endToEnd, "'end'", ((ConstraintSet.Layout)localObject1).endMargin, ((ConstraintSet.Layout)localObject1).goneEndMargin);
        writeVariable("'horizontalBias'", ((ConstraintSet.Layout)localObject1).horizontalBias, 0.5F);
        writeVariable("'verticalBias'", ((ConstraintSet.Layout)localObject1).verticalBias, 0.5F);
        writeCircle(((ConstraintSet.Layout)localObject1).circleConstraint, ((ConstraintSet.Layout)localObject1).circleAngle, ((ConstraintSet.Layout)localObject1).circleRadius);
        writeGuideline(((ConstraintSet.Layout)localObject1).orientation, ((ConstraintSet.Layout)localObject1).guideBegin, ((ConstraintSet.Layout)localObject1).guideEnd, ((ConstraintSet.Layout)localObject1).guidePercent);
        writeVariable("'dimensionRatio'", ((ConstraintSet.Layout)localObject1).dimensionRatio);
        writeVariable("'barrierMargin'", ((ConstraintSet.Layout)localObject1).mBarrierMargin);
        writeVariable("'type'", ((ConstraintSet.Layout)localObject1).mHelperType);
        writeVariable("'ReferenceId'", ((ConstraintSet.Layout)localObject1).mReferenceIdString);
        writeVariable("'mBarrierAllowsGoneWidgets'", ((ConstraintSet.Layout)localObject1).mBarrierAllowsGoneWidgets, true);
        writeVariable("'WrapBehavior'", ((ConstraintSet.Layout)localObject1).mWrapBehavior);
        writeVariable("'verticalWeight'", ((ConstraintSet.Layout)localObject1).verticalWeight);
        writeVariable("'horizontalWeight'", ((ConstraintSet.Layout)localObject1).horizontalWeight);
        writeVariable("'horizontalChainStyle'", ((ConstraintSet.Layout)localObject1).horizontalChainStyle);
        writeVariable("'verticalChainStyle'", ((ConstraintSet.Layout)localObject1).verticalChainStyle);
        writeVariable("'barrierDirection'", ((ConstraintSet.Layout)localObject1).mBarrierDirection);
        if (((ConstraintSet.Layout)localObject1).mReferenceIds != null) {
          writeVariable("'ReferenceIds'", ((ConstraintSet.Layout)localObject1).mReferenceIds);
        }
        this.writer.write("}\n");
      }
      this.writer.write("}\n");
    }
    
    void writeVariable(String paramString, float paramFloat)
      throws IOException
    {
      if (paramFloat == -1.0F) {
        return;
      }
      this.writer.write("       " + paramString);
      this.writer.write(": " + paramFloat);
      this.writer.write(",\n");
    }
    
    void writeVariable(String paramString, float paramFloat1, float paramFloat2)
      throws IOException
    {
      if (paramFloat1 == paramFloat2) {
        return;
      }
      this.writer.write("       " + paramString);
      this.writer.write(": " + paramFloat1);
      this.writer.write(",\n");
    }
    
    void writeVariable(String paramString, int paramInt)
      throws IOException
    {
      if ((paramInt != 0) && (paramInt != -1))
      {
        this.writer.write("       " + paramString);
        this.writer.write(":");
        this.writer.write(", " + paramInt);
        this.writer.write("\n");
        return;
      }
    }
    
    void writeVariable(String paramString1, String paramString2)
      throws IOException
    {
      if (paramString2 == null) {
        return;
      }
      this.writer.write("       " + paramString1);
      this.writer.write(":");
      this.writer.write(", " + paramString2);
      this.writer.write("\n");
    }
    
    void writeVariable(String paramString, boolean paramBoolean)
      throws IOException
    {
      if (!paramBoolean) {
        return;
      }
      this.writer.write("       " + paramString);
      this.writer.write(": " + paramBoolean);
      this.writer.write(",\n");
    }
    
    void writeVariable(String paramString, boolean paramBoolean1, boolean paramBoolean2)
      throws IOException
    {
      if (paramBoolean1 == paramBoolean2) {
        return;
      }
      this.writer.write("       " + paramString);
      this.writer.write(": " + paramBoolean1);
      this.writer.write(",\n");
    }
    
    void writeVariable(String paramString, int[] paramArrayOfInt)
      throws IOException
    {
      if (paramArrayOfInt == null) {
        return;
      }
      this.writer.write("       " + paramString);
      this.writer.write(": ");
      for (int i = 0; i < paramArrayOfInt.length; i++)
      {
        Writer localWriter = this.writer;
        StringBuilder localStringBuilder = new StringBuilder();
        if (i == 0) {
          paramString = "[";
        } else {
          paramString = ", ";
        }
        localWriter.write(paramString + getName(paramArrayOfInt[i]));
      }
      this.writer.write("],\n");
    }
  }
  
  class WriteXmlEngine
  {
    private static final String SPACE = "\n       ";
    final String BASELINE = "'baseline'";
    final String BOTTOM = "'bottom'";
    final String END = "'end'";
    final String LEFT = "'left'";
    final String RIGHT = "'right'";
    final String START = "'start'";
    final String TOP = "'top'";
    Context context;
    int flags;
    HashMap<Integer, String> idMap = new HashMap();
    ConstraintLayout layout;
    int unknownCount = 0;
    Writer writer;
    
    WriteXmlEngine(Writer paramWriter, ConstraintLayout paramConstraintLayout, int paramInt)
      throws IOException
    {
      this.writer = paramWriter;
      this.layout = paramConstraintLayout;
      this.context = paramConstraintLayout.getContext();
      this.flags = paramInt;
    }
    
    private void writeBaseDimension(String paramString, int paramInt1, int paramInt2)
      throws IOException
    {
      if (paramInt1 != paramInt2) {
        if (paramInt1 == -2) {
          this.writer.write("\n       " + paramString + "=\"wrap_content\"");
        } else if (paramInt1 == -1) {
          this.writer.write("\n       " + paramString + "=\"match_parent\"");
        } else {
          this.writer.write("\n       " + paramString + "=\"" + paramInt1 + "dp\"");
        }
      }
    }
    
    private void writeBoolen(String paramString, boolean paramBoolean1, boolean paramBoolean2)
      throws IOException
    {
      if (paramBoolean1 != paramBoolean2) {
        this.writer.write("\n       " + paramString + "=\"" + paramBoolean1 + "dp\"");
      }
    }
    
    private void writeDimension(String paramString, int paramInt1, int paramInt2)
      throws IOException
    {
      if (paramInt1 != paramInt2) {
        this.writer.write("\n       " + paramString + "=\"" + paramInt1 + "dp\"");
      }
    }
    
    private void writeEnum(String paramString, int paramInt1, String[] paramArrayOfString, int paramInt2)
      throws IOException
    {
      if (paramInt1 != paramInt2) {
        this.writer.write("\n       " + paramString + "=\"" + paramArrayOfString[paramInt1] + "\"");
      }
    }
    
    String getName(int paramInt)
    {
      if (this.idMap.containsKey(Integer.valueOf(paramInt))) {
        return "@+id/" + (String)this.idMap.get(Integer.valueOf(paramInt)) + "";
      }
      if (paramInt == 0) {
        return "parent";
      }
      String str = lookup(paramInt);
      this.idMap.put(Integer.valueOf(paramInt), str);
      return "@+id/" + str + "";
    }
    
    String lookup(int paramInt)
    {
      if (paramInt != -1) {}
      try
      {
        return this.context.getResources().getResourceEntryName(paramInt);
      }
      catch (Exception localException)
      {
        Object localObject;
        localStringBuilder = new StringBuilder().append("unknown");
        paramInt = this.unknownCount + 1;
        this.unknownCount = paramInt;
      }
      localObject = new java/lang/StringBuilder;
      ((StringBuilder)localObject).<init>();
      localObject = ((StringBuilder)localObject).append("unknown");
      paramInt = this.unknownCount + 1;
      this.unknownCount = paramInt;
      localObject = paramInt;
      return (String)localObject;
      StringBuilder localStringBuilder;
      return paramInt;
    }
    
    void writeCircle(int paramInt1, float paramFloat, int paramInt2)
      throws IOException
    {
      if (paramInt1 == -1) {
        return;
      }
      this.writer.write("circle");
      this.writer.write(":[");
      this.writer.write(getName(paramInt1));
      this.writer.write(", " + paramFloat);
      this.writer.write(paramInt2 + "]");
    }
    
    void writeConstraint(String paramString1, int paramInt1, String paramString2, int paramInt2, int paramInt3)
      throws IOException
    {
      if (paramInt1 == -1) {
        return;
      }
      this.writer.write("\n       " + paramString1);
      this.writer.write(":[");
      this.writer.write(getName(paramInt1));
      this.writer.write(" , ");
      this.writer.write(paramString2);
      if (paramInt2 != 0) {
        this.writer.write(" , " + paramInt2);
      }
      this.writer.write("],\n");
    }
    
    void writeLayout()
      throws IOException
    {
      this.writer.write("\n<ConstraintSet>\n");
      Iterator localIterator = ConstraintSet.this.mConstraints.keySet().iterator();
      while (localIterator.hasNext())
      {
        Object localObject2 = (Integer)localIterator.next();
        Object localObject1 = (ConstraintSet.Constraint)ConstraintSet.this.mConstraints.get(localObject2);
        localObject2 = getName(((Integer)localObject2).intValue());
        this.writer.write("  <Constraint");
        this.writer.write("\n       android:id=\"" + (String)localObject2 + "\"");
        localObject2 = ((ConstraintSet.Constraint)localObject1).layout;
        writeBaseDimension("android:layout_width", ((ConstraintSet.Layout)localObject2).mWidth, -5);
        writeBaseDimension("android:layout_height", ((ConstraintSet.Layout)localObject2).mHeight, -5);
        writeVariable("app:layout_constraintGuide_begin", ((ConstraintSet.Layout)localObject2).guideBegin, -1.0F);
        writeVariable("app:layout_constraintGuide_end", ((ConstraintSet.Layout)localObject2).guideEnd, -1.0F);
        writeVariable("app:layout_constraintGuide_percent", ((ConstraintSet.Layout)localObject2).guidePercent, -1.0F);
        writeVariable("app:layout_constraintHorizontal_bias", ((ConstraintSet.Layout)localObject2).horizontalBias, 0.5F);
        writeVariable("app:layout_constraintVertical_bias", ((ConstraintSet.Layout)localObject2).verticalBias, 0.5F);
        writeVariable("app:layout_constraintDimensionRatio", ((ConstraintSet.Layout)localObject2).dimensionRatio, null);
        writeXmlConstraint("app:layout_constraintCircle", ((ConstraintSet.Layout)localObject2).circleConstraint);
        writeVariable("app:layout_constraintCircleRadius", ((ConstraintSet.Layout)localObject2).circleRadius, 0.0F);
        writeVariable("app:layout_constraintCircleAngle", ((ConstraintSet.Layout)localObject2).circleAngle, 0.0F);
        writeVariable("android:orientation", ((ConstraintSet.Layout)localObject2).orientation, -1.0F);
        writeVariable("app:layout_constraintVertical_weight", ((ConstraintSet.Layout)localObject2).verticalWeight, -1.0F);
        writeVariable("app:layout_constraintHorizontal_weight", ((ConstraintSet.Layout)localObject2).horizontalWeight, -1.0F);
        writeVariable("app:layout_constraintHorizontal_chainStyle", ((ConstraintSet.Layout)localObject2).horizontalChainStyle, 0.0F);
        writeVariable("app:layout_constraintVertical_chainStyle", ((ConstraintSet.Layout)localObject2).verticalChainStyle, 0.0F);
        writeVariable("app:barrierDirection", ((ConstraintSet.Layout)localObject2).mBarrierDirection, -1.0F);
        writeVariable("app:barrierMargin", ((ConstraintSet.Layout)localObject2).mBarrierMargin, 0.0F);
        writeDimension("app:layout_marginLeft", ((ConstraintSet.Layout)localObject2).leftMargin, 0);
        writeDimension("app:layout_goneMarginLeft", ((ConstraintSet.Layout)localObject2).goneLeftMargin, Integer.MIN_VALUE);
        writeDimension("app:layout_marginRight", ((ConstraintSet.Layout)localObject2).rightMargin, 0);
        writeDimension("app:layout_goneMarginRight", ((ConstraintSet.Layout)localObject2).goneRightMargin, Integer.MIN_VALUE);
        writeDimension("app:layout_marginStart", ((ConstraintSet.Layout)localObject2).startMargin, 0);
        writeDimension("app:layout_goneMarginStart", ((ConstraintSet.Layout)localObject2).goneStartMargin, Integer.MIN_VALUE);
        writeDimension("app:layout_marginEnd", ((ConstraintSet.Layout)localObject2).endMargin, 0);
        writeDimension("app:layout_goneMarginEnd", ((ConstraintSet.Layout)localObject2).goneEndMargin, Integer.MIN_VALUE);
        writeDimension("app:layout_marginTop", ((ConstraintSet.Layout)localObject2).topMargin, 0);
        writeDimension("app:layout_goneMarginTop", ((ConstraintSet.Layout)localObject2).goneTopMargin, Integer.MIN_VALUE);
        writeDimension("app:layout_marginBottom", ((ConstraintSet.Layout)localObject2).bottomMargin, 0);
        writeDimension("app:layout_goneMarginBottom", ((ConstraintSet.Layout)localObject2).goneBottomMargin, Integer.MIN_VALUE);
        writeDimension("app:goneBaselineMargin", ((ConstraintSet.Layout)localObject2).goneBaselineMargin, Integer.MIN_VALUE);
        writeDimension("app:baselineMargin", ((ConstraintSet.Layout)localObject2).baselineMargin, 0);
        writeBoolen("app:layout_constrainedWidth", ((ConstraintSet.Layout)localObject2).constrainedWidth, false);
        writeBoolen("app:layout_constrainedHeight", ((ConstraintSet.Layout)localObject2).constrainedHeight, false);
        writeBoolen("app:barrierAllowsGoneWidgets", ((ConstraintSet.Layout)localObject2).mBarrierAllowsGoneWidgets, true);
        writeVariable("app:layout_wrapBehaviorInParent", ((ConstraintSet.Layout)localObject2).mWrapBehavior, 0.0F);
        writeXmlConstraint("app:baselineToBaseline", ((ConstraintSet.Layout)localObject2).baselineToBaseline);
        writeXmlConstraint("app:baselineToBottom", ((ConstraintSet.Layout)localObject2).baselineToBottom);
        writeXmlConstraint("app:baselineToTop", ((ConstraintSet.Layout)localObject2).baselineToTop);
        writeXmlConstraint("app:layout_constraintBottom_toBottomOf", ((ConstraintSet.Layout)localObject2).bottomToBottom);
        writeXmlConstraint("app:layout_constraintBottom_toTopOf", ((ConstraintSet.Layout)localObject2).bottomToTop);
        writeXmlConstraint("app:layout_constraintEnd_toEndOf", ((ConstraintSet.Layout)localObject2).endToEnd);
        writeXmlConstraint("app:layout_constraintEnd_toStartOf", ((ConstraintSet.Layout)localObject2).endToStart);
        writeXmlConstraint("app:layout_constraintLeft_toLeftOf", ((ConstraintSet.Layout)localObject2).leftToLeft);
        writeXmlConstraint("app:layout_constraintLeft_toRightOf", ((ConstraintSet.Layout)localObject2).leftToRight);
        writeXmlConstraint("app:layout_constraintRight_toLeftOf", ((ConstraintSet.Layout)localObject2).rightToLeft);
        writeXmlConstraint("app:layout_constraintRight_toRightOf", ((ConstraintSet.Layout)localObject2).rightToRight);
        writeXmlConstraint("app:layout_constraintStart_toEndOf", ((ConstraintSet.Layout)localObject2).startToEnd);
        writeXmlConstraint("app:layout_constraintStart_toStartOf", ((ConstraintSet.Layout)localObject2).startToStart);
        writeXmlConstraint("app:layout_constraintTop_toBottomOf", ((ConstraintSet.Layout)localObject2).topToBottom);
        writeXmlConstraint("app:layout_constraintTop_toTopOf", ((ConstraintSet.Layout)localObject2).topToTop);
        localObject1 = new String[3];
        localObject1[0] = "spread";
        localObject1[1] = "wrap";
        localObject1[2] = "percent";
        writeEnum("app:layout_constraintHeight_default", ((ConstraintSet.Layout)localObject2).heightDefault, (String[])localObject1, 0);
        writeVariable("app:layout_constraintHeight_percent", ((ConstraintSet.Layout)localObject2).heightPercent, 1.0F);
        writeDimension("app:layout_constraintHeight_min", ((ConstraintSet.Layout)localObject2).heightMin, 0);
        writeDimension("app:layout_constraintHeight_max", ((ConstraintSet.Layout)localObject2).heightMax, 0);
        writeBoolen("android:layout_constrainedHeight", ((ConstraintSet.Layout)localObject2).constrainedHeight, false);
        writeEnum("app:layout_constraintWidth_default", ((ConstraintSet.Layout)localObject2).widthDefault, (String[])localObject1, 0);
        writeVariable("app:layout_constraintWidth_percent", ((ConstraintSet.Layout)localObject2).widthPercent, 1.0F);
        writeDimension("app:layout_constraintWidth_min", ((ConstraintSet.Layout)localObject2).widthMin, 0);
        writeDimension("app:layout_constraintWidth_max", ((ConstraintSet.Layout)localObject2).widthMax, 0);
        writeBoolen("android:layout_constrainedWidth", ((ConstraintSet.Layout)localObject2).constrainedWidth, false);
        writeVariable("app:layout_constraintVertical_weight", ((ConstraintSet.Layout)localObject2).verticalWeight, -1.0F);
        writeVariable("app:layout_constraintHorizontal_weight", ((ConstraintSet.Layout)localObject2).horizontalWeight, -1.0F);
        writeVariable("app:layout_constraintHorizontal_chainStyle", ((ConstraintSet.Layout)localObject2).horizontalChainStyle);
        writeVariable("app:layout_constraintVertical_chainStyle", ((ConstraintSet.Layout)localObject2).verticalChainStyle);
        writeEnum("app:barrierDirection", ((ConstraintSet.Layout)localObject2).mBarrierDirection, new String[] { "left", "right", "top", "bottom", "start", "end" }, -1);
        writeVariable("app:layout_constraintTag", ((ConstraintSet.Layout)localObject2).mConstraintTag, null);
        if (((ConstraintSet.Layout)localObject2).mReferenceIds != null) {
          writeVariable("'ReferenceIds'", ((ConstraintSet.Layout)localObject2).mReferenceIds);
        }
        this.writer.write(" />\n");
      }
      this.writer.write("</ConstraintSet>\n");
    }
    
    void writeVariable(String paramString, float paramFloat1, float paramFloat2)
      throws IOException
    {
      if (paramFloat1 == paramFloat2) {
        return;
      }
      this.writer.write("\n       " + paramString);
      this.writer.write("=\"" + paramFloat1 + "\"");
    }
    
    void writeVariable(String paramString, int paramInt)
      throws IOException
    {
      if ((paramInt != 0) && (paramInt != -1))
      {
        this.writer.write("\n       " + paramString + "=\"" + paramInt + "\"\n");
        return;
      }
    }
    
    void writeVariable(String paramString1, String paramString2)
      throws IOException
    {
      if (paramString2 == null) {
        return;
      }
      this.writer.write(paramString1);
      this.writer.write(":");
      this.writer.write(", " + paramString2);
      this.writer.write("\n");
    }
    
    void writeVariable(String paramString1, String paramString2, String paramString3)
      throws IOException
    {
      if ((paramString2 != null) && (!paramString2.equals(paramString3)))
      {
        this.writer.write("\n       " + paramString1);
        this.writer.write("=\"" + paramString2 + "\"");
        return;
      }
    }
    
    void writeVariable(String paramString, int[] paramArrayOfInt)
      throws IOException
    {
      if (paramArrayOfInt == null) {
        return;
      }
      this.writer.write("\n       " + paramString);
      this.writer.write(":");
      for (int i = 0; i < paramArrayOfInt.length; i++)
      {
        Writer localWriter = this.writer;
        StringBuilder localStringBuilder = new StringBuilder();
        if (i == 0) {
          paramString = "[";
        } else {
          paramString = ", ";
        }
        localWriter.write(paramString + getName(paramArrayOfInt[i]));
      }
      this.writer.write("],\n");
    }
    
    void writeXmlConstraint(String paramString, int paramInt)
      throws IOException
    {
      if (paramInt == -1) {
        return;
      }
      this.writer.write("\n       " + paramString);
      this.writer.write("=\"" + getName(paramInt) + "\"");
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/widget/ConstraintSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */