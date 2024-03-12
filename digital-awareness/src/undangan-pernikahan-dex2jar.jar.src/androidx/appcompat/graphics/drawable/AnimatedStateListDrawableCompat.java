package androidx.appcompat.graphics.drawable;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Log;
import android.util.StateSet;
import android.util.Xml;
import androidx.appcompat.resources.Compatibility.Api18Impl;
import androidx.appcompat.resources.Compatibility.Api21Impl;
import androidx.appcompat.resources.R.styleable;
import androidx.appcompat.widget.ResourceManagerInternal;
import androidx.collection.LongSparseArray;
import androidx.collection.SparseArrayCompat;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.graphics.drawable.TintAwareDrawable;
import androidx.core.util.ObjectsCompat;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class AnimatedStateListDrawableCompat
  extends StateListDrawable
  implements TintAwareDrawable
{
  private static final String ELEMENT_ITEM = "item";
  private static final String ELEMENT_TRANSITION = "transition";
  private static final String ITEM_MISSING_DRAWABLE_ERROR = ": <item> tag requires a 'drawable' attribute or child tag defining a drawable";
  private static final String LOGTAG = AnimatedStateListDrawableCompat.class.getSimpleName();
  private static final String TRANSITION_MISSING_DRAWABLE_ERROR = ": <transition> tag requires a 'drawable' attribute or child tag defining a drawable";
  private static final String TRANSITION_MISSING_FROM_TO_ID = ": <transition> tag requires 'fromId' & 'toId' attributes";
  private boolean mMutated;
  private AnimatedStateListState mState;
  private Transition mTransition;
  private int mTransitionFromIndex = -1;
  private int mTransitionToIndex = -1;
  
  public AnimatedStateListDrawableCompat()
  {
    this(null, null);
  }
  
  AnimatedStateListDrawableCompat(AnimatedStateListState paramAnimatedStateListState, Resources paramResources)
  {
    super(null);
    setConstantState(new AnimatedStateListState(paramAnimatedStateListState, this, paramResources));
    onStateChange(getState());
    jumpToCurrentState();
  }
  
  public static AnimatedStateListDrawableCompat create(Context paramContext, int paramInt, Resources.Theme paramTheme)
  {
    try
    {
      Resources localResources = paramContext.getResources();
      XmlResourceParser localXmlResourceParser = localResources.getXml(paramInt);
      AttributeSet localAttributeSet = Xml.asAttributeSet(localXmlResourceParser);
      do
      {
        paramInt = localXmlResourceParser.next();
      } while ((paramInt != 2) && (paramInt != 1));
      if (paramInt == 2) {
        return createFromXmlInner(paramContext, localResources, localXmlResourceParser, localAttributeSet, paramTheme);
      }
      paramContext = new org/xmlpull/v1/XmlPullParserException;
      paramContext.<init>("No start tag found");
      throw paramContext;
    }
    catch (IOException paramContext)
    {
      Log.e(LOGTAG, "parser error", paramContext);
    }
    catch (XmlPullParserException paramContext)
    {
      Log.e(LOGTAG, "parser error", paramContext);
    }
    return null;
  }
  
  public static AnimatedStateListDrawableCompat createFromXmlInner(Context paramContext, Resources paramResources, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, Resources.Theme paramTheme)
    throws IOException, XmlPullParserException
  {
    Object localObject = paramXmlPullParser.getName();
    if (((String)localObject).equals("animated-selector"))
    {
      localObject = new AnimatedStateListDrawableCompat();
      ((AnimatedStateListDrawableCompat)localObject).inflate(paramContext, paramResources, paramXmlPullParser, paramAttributeSet, paramTheme);
      return (AnimatedStateListDrawableCompat)localObject;
    }
    throw new XmlPullParserException(paramXmlPullParser.getPositionDescription() + ": invalid animated-selector tag " + (String)localObject);
  }
  
  private void inflateChildElements(Context paramContext, Resources paramResources, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, Resources.Theme paramTheme)
    throws XmlPullParserException, IOException
  {
    int j = paramXmlPullParser.getDepth() + 1;
    for (;;)
    {
      int k = paramXmlPullParser.next();
      if (k == 1) {
        break;
      }
      int i = paramXmlPullParser.getDepth();
      if ((i < j) && (k == 3)) {
        break;
      }
      if ((k == 2) && (i <= j)) {
        if (paramXmlPullParser.getName().equals("item")) {
          parseItem(paramContext, paramResources, paramXmlPullParser, paramAttributeSet, paramTheme);
        } else if (paramXmlPullParser.getName().equals("transition")) {
          parseTransition(paramContext, paramResources, paramXmlPullParser, paramAttributeSet, paramTheme);
        }
      }
    }
  }
  
  private void init()
  {
    onStateChange(getState());
  }
  
  private int parseItem(Context paramContext, Resources paramResources, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, Resources.Theme paramTheme)
    throws XmlPullParserException, IOException
  {
    Object localObject = TypedArrayUtils.obtainAttributes(paramResources, paramTheme, paramAttributeSet, R.styleable.AnimatedStateListDrawableItem);
    int i = ((TypedArray)localObject).getResourceId(R.styleable.AnimatedStateListDrawableItem_android_id, 0);
    Drawable localDrawable = null;
    int j = ((TypedArray)localObject).getResourceId(R.styleable.AnimatedStateListDrawableItem_android_drawable, -1);
    if (j > 0) {
      localDrawable = ResourceManagerInternal.get().getDrawable(paramContext, j);
    }
    ((TypedArray)localObject).recycle();
    localObject = extractStateSet(paramAttributeSet);
    paramContext = localDrawable;
    if (localDrawable == null)
    {
      do
      {
        j = paramXmlPullParser.next();
      } while (j == 4);
      if (j == 2)
      {
        if (paramXmlPullParser.getName().equals("vector")) {
          paramContext = VectorDrawableCompat.createFromXmlInner(paramResources, paramXmlPullParser, paramAttributeSet, paramTheme);
        } else if (Build.VERSION.SDK_INT >= 21) {
          paramContext = Compatibility.Api21Impl.createFromXmlInner(paramResources, paramXmlPullParser, paramAttributeSet, paramTheme);
        } else {
          paramContext = Drawable.createFromXmlInner(paramResources, paramXmlPullParser, paramAttributeSet);
        }
      }
      else {
        throw new XmlPullParserException(paramXmlPullParser.getPositionDescription() + ": <item> tag requires a 'drawable' attribute or child tag defining a drawable");
      }
    }
    if (paramContext != null) {
      return this.mState.addStateSet((int[])localObject, paramContext, i);
    }
    throw new XmlPullParserException(paramXmlPullParser.getPositionDescription() + ": <item> tag requires a 'drawable' attribute or child tag defining a drawable");
  }
  
  private int parseTransition(Context paramContext, Resources paramResources, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, Resources.Theme paramTheme)
    throws XmlPullParserException, IOException
  {
    Object localObject = TypedArrayUtils.obtainAttributes(paramResources, paramTheme, paramAttributeSet, R.styleable.AnimatedStateListDrawableTransition);
    int j = ((TypedArray)localObject).getResourceId(R.styleable.AnimatedStateListDrawableTransition_android_fromId, -1);
    int i = ((TypedArray)localObject).getResourceId(R.styleable.AnimatedStateListDrawableTransition_android_toId, -1);
    Drawable localDrawable = null;
    int k = ((TypedArray)localObject).getResourceId(R.styleable.AnimatedStateListDrawableTransition_android_drawable, -1);
    if (k > 0) {
      localDrawable = ResourceManagerInternal.get().getDrawable(paramContext, k);
    }
    boolean bool = ((TypedArray)localObject).getBoolean(R.styleable.AnimatedStateListDrawableTransition_android_reversible, false);
    ((TypedArray)localObject).recycle();
    localObject = localDrawable;
    if (localDrawable == null)
    {
      do
      {
        k = paramXmlPullParser.next();
      } while (k == 4);
      if (k == 2)
      {
        if (paramXmlPullParser.getName().equals("animated-vector")) {
          localObject = AnimatedVectorDrawableCompat.createFromXmlInner(paramContext, paramResources, paramXmlPullParser, paramAttributeSet, paramTheme);
        } else if (Build.VERSION.SDK_INT >= 21) {
          localObject = Compatibility.Api21Impl.createFromXmlInner(paramResources, paramXmlPullParser, paramAttributeSet, paramTheme);
        } else {
          localObject = Drawable.createFromXmlInner(paramResources, paramXmlPullParser, paramAttributeSet);
        }
      }
      else {
        throw new XmlPullParserException(paramXmlPullParser.getPositionDescription() + ": <transition> tag requires a 'drawable' attribute or child tag defining a drawable");
      }
    }
    if (localObject != null)
    {
      if ((j != -1) && (i != -1)) {
        return this.mState.addTransition(j, i, (Drawable)localObject, bool);
      }
      throw new XmlPullParserException(paramXmlPullParser.getPositionDescription() + ": <transition> tag requires 'fromId' & 'toId' attributes");
    }
    throw new XmlPullParserException(paramXmlPullParser.getPositionDescription() + ": <transition> tag requires a 'drawable' attribute or child tag defining a drawable");
  }
  
  private boolean selectTransition(int paramInt)
  {
    Object localObject = this.mTransition;
    int i;
    if (localObject != null)
    {
      if (paramInt == this.mTransitionToIndex) {
        return true;
      }
      if ((paramInt == this.mTransitionFromIndex) && (((Transition)localObject).canReverse()))
      {
        ((Transition)localObject).reverse();
        this.mTransitionToIndex = this.mTransitionFromIndex;
        this.mTransitionFromIndex = paramInt;
        return true;
      }
      i = this.mTransitionToIndex;
      ((Transition)localObject).stop();
    }
    else
    {
      i = getCurrentIndex();
    }
    this.mTransition = null;
    this.mTransitionFromIndex = -1;
    this.mTransitionToIndex = -1;
    AnimatedStateListState localAnimatedStateListState = this.mState;
    int j = localAnimatedStateListState.getKeyframeIdAt(i);
    int k = localAnimatedStateListState.getKeyframeIdAt(paramInt);
    if ((k != 0) && (j != 0))
    {
      int m = localAnimatedStateListState.indexOfTransition(j, k);
      if (m < 0) {
        return false;
      }
      boolean bool1 = localAnimatedStateListState.transitionHasReversibleFlag(j, k);
      selectDrawable(m);
      localObject = getCurrent();
      if ((localObject instanceof AnimationDrawable))
      {
        boolean bool2 = localAnimatedStateListState.isTransitionReversed(j, k);
        localObject = new AnimationDrawableTransition((AnimationDrawable)localObject, bool2, bool1);
      }
      else if ((localObject instanceof AnimatedVectorDrawableCompat))
      {
        localObject = new AnimatedVectorDrawableTransition((AnimatedVectorDrawableCompat)localObject);
      }
      else
      {
        if (!(localObject instanceof Animatable)) {
          break label272;
        }
        localObject = new AnimatableTransition((Animatable)localObject);
      }
      ((Transition)localObject).start();
      this.mTransition = ((Transition)localObject);
      this.mTransitionFromIndex = i;
      this.mTransitionToIndex = paramInt;
      return true;
      label272:
      return false;
    }
    return false;
  }
  
  private void updateStateFromTypedArray(TypedArray paramTypedArray)
  {
    AnimatedStateListState localAnimatedStateListState = this.mState;
    if (Build.VERSION.SDK_INT >= 21) {
      localAnimatedStateListState.mChangingConfigurations |= Compatibility.Api21Impl.getChangingConfigurations(paramTypedArray);
    }
    localAnimatedStateListState.setVariablePadding(paramTypedArray.getBoolean(R.styleable.AnimatedStateListDrawableCompat_android_variablePadding, localAnimatedStateListState.mVariablePadding));
    localAnimatedStateListState.setConstantSize(paramTypedArray.getBoolean(R.styleable.AnimatedStateListDrawableCompat_android_constantSize, localAnimatedStateListState.mConstantSize));
    localAnimatedStateListState.setEnterFadeDuration(paramTypedArray.getInt(R.styleable.AnimatedStateListDrawableCompat_android_enterFadeDuration, localAnimatedStateListState.mEnterFadeDuration));
    localAnimatedStateListState.setExitFadeDuration(paramTypedArray.getInt(R.styleable.AnimatedStateListDrawableCompat_android_exitFadeDuration, localAnimatedStateListState.mExitFadeDuration));
    setDither(paramTypedArray.getBoolean(R.styleable.AnimatedStateListDrawableCompat_android_dither, localAnimatedStateListState.mDither));
  }
  
  public void addState(int[] paramArrayOfInt, Drawable paramDrawable, int paramInt)
  {
    ObjectsCompat.requireNonNull(paramDrawable);
    this.mState.addStateSet(paramArrayOfInt, paramDrawable, paramInt);
    onStateChange(getState());
  }
  
  public <T extends Drawable,  extends Animatable> void addTransition(int paramInt1, int paramInt2, T paramT, boolean paramBoolean)
  {
    ObjectsCompat.requireNonNull(paramT);
    this.mState.addTransition(paramInt1, paramInt2, paramT, paramBoolean);
  }
  
  void clearMutated()
  {
    super.clearMutated();
    this.mMutated = false;
  }
  
  AnimatedStateListState cloneConstantState()
  {
    return new AnimatedStateListState(this.mState, this, null);
  }
  
  public void inflate(Context paramContext, Resources paramResources, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, Resources.Theme paramTheme)
    throws XmlPullParserException, IOException
  {
    TypedArray localTypedArray = TypedArrayUtils.obtainAttributes(paramResources, paramTheme, paramAttributeSet, R.styleable.AnimatedStateListDrawableCompat);
    setVisible(localTypedArray.getBoolean(R.styleable.AnimatedStateListDrawableCompat_android_visible, true), true);
    updateStateFromTypedArray(localTypedArray);
    updateDensity(paramResources);
    localTypedArray.recycle();
    inflateChildElements(paramContext, paramResources, paramXmlPullParser, paramAttributeSet, paramTheme);
    init();
  }
  
  public boolean isStateful()
  {
    return true;
  }
  
  public void jumpToCurrentState()
  {
    super.jumpToCurrentState();
    Transition localTransition = this.mTransition;
    if (localTransition != null)
    {
      localTransition.stop();
      this.mTransition = null;
      selectDrawable(this.mTransitionToIndex);
      this.mTransitionToIndex = -1;
      this.mTransitionFromIndex = -1;
    }
  }
  
  public Drawable mutate()
  {
    if ((!this.mMutated) && (super.mutate() == this))
    {
      this.mState.mutate();
      this.mMutated = true;
    }
    return this;
  }
  
  protected boolean onStateChange(int[] paramArrayOfInt)
  {
    int i = this.mState.indexOfKeyframe(paramArrayOfInt);
    boolean bool1;
    if ((i != getCurrentIndex()) && ((selectTransition(i)) || (selectDrawable(i)))) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    Drawable localDrawable = getCurrent();
    boolean bool2 = bool1;
    if (localDrawable != null) {
      bool2 = bool1 | localDrawable.setState(paramArrayOfInt);
    }
    return bool2;
  }
  
  void setConstantState(DrawableContainer.DrawableContainerState paramDrawableContainerState)
  {
    super.setConstantState(paramDrawableContainerState);
    if ((paramDrawableContainerState instanceof AnimatedStateListState)) {
      this.mState = ((AnimatedStateListState)paramDrawableContainerState);
    }
  }
  
  public boolean setVisible(boolean paramBoolean1, boolean paramBoolean2)
  {
    boolean bool = super.setVisible(paramBoolean1, paramBoolean2);
    Transition localTransition = this.mTransition;
    if ((localTransition != null) && ((bool) || (paramBoolean2))) {
      if (paramBoolean1) {
        localTransition.start();
      } else {
        jumpToCurrentState();
      }
    }
    return bool;
  }
  
  private static class AnimatableTransition
    extends AnimatedStateListDrawableCompat.Transition
  {
    private final Animatable mA;
    
    AnimatableTransition(Animatable paramAnimatable)
    {
      super();
      this.mA = paramAnimatable;
    }
    
    public void start()
    {
      this.mA.start();
    }
    
    public void stop()
    {
      this.mA.stop();
    }
  }
  
  static class AnimatedStateListState
    extends StateListDrawable.StateListState
  {
    private static final long REVERSED_BIT = 4294967296L;
    private static final long REVERSIBLE_FLAG_BIT = 8589934592L;
    SparseArrayCompat<Integer> mStateIds;
    LongSparseArray<Long> mTransitions;
    
    AnimatedStateListState(AnimatedStateListState paramAnimatedStateListState, AnimatedStateListDrawableCompat paramAnimatedStateListDrawableCompat, Resources paramResources)
    {
      super(paramAnimatedStateListDrawableCompat, paramResources);
      if (paramAnimatedStateListState != null)
      {
        this.mTransitions = paramAnimatedStateListState.mTransitions;
        this.mStateIds = paramAnimatedStateListState.mStateIds;
      }
      else
      {
        this.mTransitions = new LongSparseArray();
        this.mStateIds = new SparseArrayCompat();
      }
    }
    
    private static long generateTransitionKey(int paramInt1, int paramInt2)
    {
      return paramInt1 << 32 | paramInt2;
    }
    
    int addStateSet(int[] paramArrayOfInt, Drawable paramDrawable, int paramInt)
    {
      int i = super.addStateSet(paramArrayOfInt, paramDrawable);
      this.mStateIds.put(i, Integer.valueOf(paramInt));
      return i;
    }
    
    int addTransition(int paramInt1, int paramInt2, Drawable paramDrawable, boolean paramBoolean)
    {
      int i = super.addChild(paramDrawable);
      long l2 = generateTransitionKey(paramInt1, paramInt2);
      long l1 = 0L;
      if (paramBoolean) {
        l1 = 8589934592L;
      }
      this.mTransitions.append(l2, Long.valueOf(i | l1));
      if (paramBoolean)
      {
        l2 = generateTransitionKey(paramInt2, paramInt1);
        this.mTransitions.append(l2, Long.valueOf(i | 0x100000000 | l1));
      }
      return i;
    }
    
    int getKeyframeIdAt(int paramInt)
    {
      int i = 0;
      if (paramInt < 0) {
        paramInt = i;
      } else {
        paramInt = ((Integer)this.mStateIds.get(paramInt, Integer.valueOf(0))).intValue();
      }
      return paramInt;
    }
    
    int indexOfKeyframe(int[] paramArrayOfInt)
    {
      int i = super.indexOfStateSet(paramArrayOfInt);
      if (i >= 0) {
        return i;
      }
      return super.indexOfStateSet(StateSet.WILD_CARD);
    }
    
    int indexOfTransition(int paramInt1, int paramInt2)
    {
      long l = generateTransitionKey(paramInt1, paramInt2);
      return (int)((Long)this.mTransitions.get(l, Long.valueOf(-1L))).longValue();
    }
    
    boolean isTransitionReversed(int paramInt1, int paramInt2)
    {
      long l = generateTransitionKey(paramInt1, paramInt2);
      boolean bool;
      if ((((Long)this.mTransitions.get(l, Long.valueOf(-1L))).longValue() & 0x100000000) != 0L) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    void mutate()
    {
      this.mTransitions = this.mTransitions.clone();
      this.mStateIds = this.mStateIds.clone();
    }
    
    public Drawable newDrawable()
    {
      return new AnimatedStateListDrawableCompat(this, null);
    }
    
    public Drawable newDrawable(Resources paramResources)
    {
      return new AnimatedStateListDrawableCompat(this, paramResources);
    }
    
    boolean transitionHasReversibleFlag(int paramInt1, int paramInt2)
    {
      long l = generateTransitionKey(paramInt1, paramInt2);
      boolean bool;
      if ((((Long)this.mTransitions.get(l, Long.valueOf(-1L))).longValue() & 0x200000000) != 0L) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
  }
  
  private static class AnimatedVectorDrawableTransition
    extends AnimatedStateListDrawableCompat.Transition
  {
    private final AnimatedVectorDrawableCompat mAvd;
    
    AnimatedVectorDrawableTransition(AnimatedVectorDrawableCompat paramAnimatedVectorDrawableCompat)
    {
      super();
      this.mAvd = paramAnimatedVectorDrawableCompat;
    }
    
    public void start()
    {
      this.mAvd.start();
    }
    
    public void stop()
    {
      this.mAvd.stop();
    }
  }
  
  private static class AnimationDrawableTransition
    extends AnimatedStateListDrawableCompat.Transition
  {
    private final ObjectAnimator mAnim;
    private final boolean mHasReversibleFlag;
    
    AnimationDrawableTransition(AnimationDrawable paramAnimationDrawable, boolean paramBoolean1, boolean paramBoolean2)
    {
      super();
      int j = paramAnimationDrawable.getNumberOfFrames();
      int i;
      if (paramBoolean1) {
        i = j - 1;
      } else {
        i = 0;
      }
      if (paramBoolean1) {
        j = 0;
      } else {
        j--;
      }
      AnimatedStateListDrawableCompat.FrameInterpolator localFrameInterpolator = new AnimatedStateListDrawableCompat.FrameInterpolator(paramAnimationDrawable, paramBoolean1);
      paramAnimationDrawable = ObjectAnimator.ofInt(paramAnimationDrawable, "currentIndex", new int[] { i, j });
      if (Build.VERSION.SDK_INT >= 18) {
        Compatibility.Api18Impl.setAutoCancel(paramAnimationDrawable, true);
      }
      paramAnimationDrawable.setDuration(localFrameInterpolator.getTotalDuration());
      paramAnimationDrawable.setInterpolator(localFrameInterpolator);
      this.mHasReversibleFlag = paramBoolean2;
      this.mAnim = paramAnimationDrawable;
    }
    
    public boolean canReverse()
    {
      return this.mHasReversibleFlag;
    }
    
    public void reverse()
    {
      this.mAnim.reverse();
    }
    
    public void start()
    {
      this.mAnim.start();
    }
    
    public void stop()
    {
      this.mAnim.cancel();
    }
  }
  
  private static class FrameInterpolator
    implements TimeInterpolator
  {
    private int[] mFrameTimes;
    private int mFrames;
    private int mTotalDuration;
    
    FrameInterpolator(AnimationDrawable paramAnimationDrawable, boolean paramBoolean)
    {
      updateFrames(paramAnimationDrawable, paramBoolean);
    }
    
    public float getInterpolation(float paramFloat)
    {
      int j = (int)(this.mTotalDuration * paramFloat + 0.5F);
      int k = this.mFrames;
      int[] arrayOfInt = this.mFrameTimes;
      for (int i = 0; (i < k) && (j >= arrayOfInt[i]); i++) {
        j -= arrayOfInt[i];
      }
      if (i < k) {
        paramFloat = j / this.mTotalDuration;
      } else {
        paramFloat = 0.0F;
      }
      return i / k + paramFloat;
    }
    
    int getTotalDuration()
    {
      return this.mTotalDuration;
    }
    
    int updateFrames(AnimationDrawable paramAnimationDrawable, boolean paramBoolean)
    {
      int m = paramAnimationDrawable.getNumberOfFrames();
      this.mFrames = m;
      int[] arrayOfInt = this.mFrameTimes;
      if ((arrayOfInt == null) || (arrayOfInt.length < m)) {
        this.mFrameTimes = new int[m];
      }
      arrayOfInt = this.mFrameTimes;
      int j = 0;
      for (int i = 0; i < m; i++)
      {
        if (paramBoolean) {
          k = m - i - 1;
        } else {
          k = i;
        }
        int k = paramAnimationDrawable.getDuration(k);
        arrayOfInt[i] = k;
        j += k;
      }
      this.mTotalDuration = j;
      return j;
    }
  }
  
  private static abstract class Transition
  {
    public boolean canReverse()
    {
      return false;
    }
    
    public void reverse() {}
    
    public abstract void start();
    
    public abstract void stop();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/appcompat/graphics/drawable/AnimatedStateListDrawableCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */