package androidx.interpolator.view.animation;

public class FastOutLinearInInterpolator
  extends LookupTableInterpolator
{
  private static final float[] VALUES = { 0.0F, 1.0E-4F, 2.0E-4F, 5.0E-4F, 8.0E-4F, 0.0013F, 0.0018F, 0.0024F, 0.0032F, 0.004F, 0.0049F, 0.0059F, 0.0069F, 0.0081F, 0.0093F, 0.0106F, 0.012F, 0.0135F, 0.0151F, 0.0167F, 0.0184F, 0.0201F, 0.022F, 0.0239F, 0.0259F, 0.0279F, 0.03F, 0.0322F, 0.0345F, 0.0368F, 0.0391F, 0.0416F, 0.0441F, 0.0466F, 0.0492F, 0.0519F, 0.0547F, 0.0574F, 0.0603F, 0.0632F, 0.0662F, 0.0692F, 0.0722F, 0.0754F, 0.0785F, 0.0817F, 0.085F, 0.0884F, 0.0917F, 0.0952F, 0.0986F, 0.1021F, 0.1057F, 0.1093F, 0.113F, 0.1167F, 0.1205F, 0.1243F, 0.1281F, 0.132F, 0.1359F, 0.1399F, 0.1439F, 0.148F, 0.1521F, 0.1562F, 0.1604F, 0.1647F, 0.1689F, 0.1732F, 0.1776F, 0.182F, 0.1864F, 0.1909F, 0.1954F, 0.1999F, 0.2045F, 0.2091F, 0.2138F, 0.2184F, 0.2232F, 0.2279F, 0.2327F, 0.2376F, 0.2424F, 0.2473F, 0.2523F, 0.2572F, 0.2622F, 0.2673F, 0.2723F, 0.2774F, 0.2826F, 0.2877F, 0.2929F, 0.2982F, 0.3034F, 0.3087F, 0.3141F, 0.3194F, 0.3248F, 0.3302F, 0.3357F, 0.3412F, 0.3467F, 0.3522F, 0.3578F, 0.3634F, 0.369F, 0.3747F, 0.3804F, 0.3861F, 0.3918F, 0.3976F, 0.4034F, 0.4092F, 0.4151F, 0.421F, 0.4269F, 0.4329F, 0.4388F, 0.4448F, 0.4508F, 0.4569F, 0.463F, 0.4691F, 0.4752F, 0.4814F, 0.4876F, 0.4938F, 0.5F, 0.5063F, 0.5126F, 0.5189F, 0.5252F, 0.5316F, 0.538F, 0.5444F, 0.5508F, 0.5573F, 0.5638F, 0.5703F, 0.5768F, 0.5834F, 0.59F, 0.5966F, 0.6033F, 0.6099F, 0.6166F, 0.6233F, 0.6301F, 0.6369F, 0.6436F, 0.6505F, 0.6573F, 0.6642F, 0.671F, 0.678F, 0.6849F, 0.6919F, 0.6988F, 0.7059F, 0.7129F, 0.7199F, 0.727F, 0.7341F, 0.7413F, 0.7484F, 0.7556F, 0.7628F, 0.77F, 0.7773F, 0.7846F, 0.7919F, 0.7992F, 0.8066F, 0.814F, 0.8214F, 0.8288F, 0.8363F, 0.8437F, 0.8513F, 0.8588F, 0.8664F, 0.874F, 0.8816F, 0.8892F, 0.8969F, 0.9046F, 0.9124F, 0.9201F, 0.928F, 0.9358F, 0.9437F, 0.9516F, 0.9595F, 0.9675F, 0.9755F, 0.9836F, 0.9918F, 1.0F };
  
  public FastOutLinearInInterpolator()
  {
    super(VALUES);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/interpolator/view/animation/FastOutLinearInInterpolator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */