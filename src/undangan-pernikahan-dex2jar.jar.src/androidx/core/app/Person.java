package androidx.core.app;

import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.PersistableBundle;
import androidx.core.graphics.drawable.IconCompat;

public class Person
{
  private static final String ICON_KEY = "icon";
  private static final String IS_BOT_KEY = "isBot";
  private static final String IS_IMPORTANT_KEY = "isImportant";
  private static final String KEY_KEY = "key";
  private static final String NAME_KEY = "name";
  private static final String URI_KEY = "uri";
  IconCompat mIcon;
  boolean mIsBot;
  boolean mIsImportant;
  String mKey;
  CharSequence mName;
  String mUri;
  
  Person(Builder paramBuilder)
  {
    this.mName = paramBuilder.mName;
    this.mIcon = paramBuilder.mIcon;
    this.mUri = paramBuilder.mUri;
    this.mKey = paramBuilder.mKey;
    this.mIsBot = paramBuilder.mIsBot;
    this.mIsImportant = paramBuilder.mIsImportant;
  }
  
  public static Person fromAndroidPerson(android.app.Person paramPerson)
  {
    return Api28Impl.fromAndroidPerson(paramPerson);
  }
  
  public static Person fromBundle(Bundle paramBundle)
  {
    Object localObject = paramBundle.getBundle("icon");
    Builder localBuilder = new Builder().setName(paramBundle.getCharSequence("name"));
    if (localObject != null) {
      localObject = IconCompat.createFromBundle((Bundle)localObject);
    } else {
      localObject = null;
    }
    return localBuilder.setIcon((IconCompat)localObject).setUri(paramBundle.getString("uri")).setKey(paramBundle.getString("key")).setBot(paramBundle.getBoolean("isBot")).setImportant(paramBundle.getBoolean("isImportant")).build();
  }
  
  public static Person fromPersistableBundle(PersistableBundle paramPersistableBundle)
  {
    return Api22Impl.fromPersistableBundle(paramPersistableBundle);
  }
  
  public IconCompat getIcon()
  {
    return this.mIcon;
  }
  
  public String getKey()
  {
    return this.mKey;
  }
  
  public CharSequence getName()
  {
    return this.mName;
  }
  
  public String getUri()
  {
    return this.mUri;
  }
  
  public boolean isBot()
  {
    return this.mIsBot;
  }
  
  public boolean isImportant()
  {
    return this.mIsImportant;
  }
  
  public String resolveToLegacyUri()
  {
    String str = this.mUri;
    if (str != null) {
      return str;
    }
    if (this.mName != null) {
      return "name:" + this.mName;
    }
    return "";
  }
  
  public android.app.Person toAndroidPerson()
  {
    return Api28Impl.toAndroidPerson(this);
  }
  
  public Builder toBuilder()
  {
    return new Builder(this);
  }
  
  public Bundle toBundle()
  {
    Bundle localBundle = new Bundle();
    localBundle.putCharSequence("name", this.mName);
    Object localObject = this.mIcon;
    if (localObject != null) {
      localObject = ((IconCompat)localObject).toBundle();
    } else {
      localObject = null;
    }
    localBundle.putBundle("icon", (Bundle)localObject);
    localBundle.putString("uri", this.mUri);
    localBundle.putString("key", this.mKey);
    localBundle.putBoolean("isBot", this.mIsBot);
    localBundle.putBoolean("isImportant", this.mIsImportant);
    return localBundle;
  }
  
  public PersistableBundle toPersistableBundle()
  {
    return Api22Impl.toPersistableBundle(this);
  }
  
  static class Api22Impl
  {
    static Person fromPersistableBundle(PersistableBundle paramPersistableBundle)
    {
      return new Person.Builder().setName(paramPersistableBundle.getString("name")).setUri(paramPersistableBundle.getString("uri")).setKey(paramPersistableBundle.getString("key")).setBot(paramPersistableBundle.getBoolean("isBot")).setImportant(paramPersistableBundle.getBoolean("isImportant")).build();
    }
    
    static PersistableBundle toPersistableBundle(Person paramPerson)
    {
      PersistableBundle localPersistableBundle = new PersistableBundle();
      String str;
      if (paramPerson.mName != null) {
        str = paramPerson.mName.toString();
      } else {
        str = null;
      }
      localPersistableBundle.putString("name", str);
      localPersistableBundle.putString("uri", paramPerson.mUri);
      localPersistableBundle.putString("key", paramPerson.mKey);
      localPersistableBundle.putBoolean("isBot", paramPerson.mIsBot);
      localPersistableBundle.putBoolean("isImportant", paramPerson.mIsImportant);
      return localPersistableBundle;
    }
  }
  
  static class Api28Impl
  {
    static Person fromAndroidPerson(android.app.Person paramPerson)
    {
      Person.Builder localBuilder = new Person.Builder().setName(paramPerson.getName());
      IconCompat localIconCompat;
      if (paramPerson.getIcon() != null) {
        localIconCompat = IconCompat.createFromIcon(paramPerson.getIcon());
      } else {
        localIconCompat = null;
      }
      return localBuilder.setIcon(localIconCompat).setUri(paramPerson.getUri()).setKey(paramPerson.getKey()).setBot(paramPerson.isBot()).setImportant(paramPerson.isImportant()).build();
    }
    
    static android.app.Person toAndroidPerson(Person paramPerson)
    {
      android.app.Person.Builder localBuilder = new android.app.Person.Builder().setName(paramPerson.getName());
      Icon localIcon;
      if (paramPerson.getIcon() != null) {
        localIcon = paramPerson.getIcon().toIcon();
      } else {
        localIcon = null;
      }
      return localBuilder.setIcon(localIcon).setUri(paramPerson.getUri()).setKey(paramPerson.getKey()).setBot(paramPerson.isBot()).setImportant(paramPerson.isImportant()).build();
    }
  }
  
  public static class Builder
  {
    IconCompat mIcon;
    boolean mIsBot;
    boolean mIsImportant;
    String mKey;
    CharSequence mName;
    String mUri;
    
    public Builder() {}
    
    Builder(Person paramPerson)
    {
      this.mName = paramPerson.mName;
      this.mIcon = paramPerson.mIcon;
      this.mUri = paramPerson.mUri;
      this.mKey = paramPerson.mKey;
      this.mIsBot = paramPerson.mIsBot;
      this.mIsImportant = paramPerson.mIsImportant;
    }
    
    public Person build()
    {
      return new Person(this);
    }
    
    public Builder setBot(boolean paramBoolean)
    {
      this.mIsBot = paramBoolean;
      return this;
    }
    
    public Builder setIcon(IconCompat paramIconCompat)
    {
      this.mIcon = paramIconCompat;
      return this;
    }
    
    public Builder setImportant(boolean paramBoolean)
    {
      this.mIsImportant = paramBoolean;
      return this;
    }
    
    public Builder setKey(String paramString)
    {
      this.mKey = paramString;
      return this;
    }
    
    public Builder setName(CharSequence paramCharSequence)
    {
      this.mName = paramCharSequence;
      return this;
    }
    
    public Builder setUri(String paramString)
    {
      this.mUri = paramString;
      return this;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/app/Person.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */