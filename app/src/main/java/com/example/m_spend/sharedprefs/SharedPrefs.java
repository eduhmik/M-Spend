package com.example.m_spend.sharedprefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.m_spend.models.User;
import com.google.gson.Gson;

public class SharedPrefs {
  SharedPreferences accountPreferences;
  SharedPreferences.Editor accountPreferencesEditor;
  Context context;
  private static final String PREF_NAME = "sessionPref";
  final String IS_LOGGED_IN = "Is logged in";
  final String USER = "User";
  final String SHARED_PREFS = "User";

  public SharedPrefs(Context context){
    this.context=context;
    accountPreferences=context.getSharedPreferences("Account", Context.MODE_PRIVATE);
    accountPreferencesEditor=accountPreferences.edit();
  }
  public void setIsloggedIn(boolean isloggedIn) {
    accountPreferencesEditor.putBoolean(IS_LOGGED_IN,isloggedIn).commit();
  }
  public boolean getIsloggedIn() {
    return accountPreferences.getBoolean(IS_LOGGED_IN,false);
  }

  public void setUser(User user) {
    accountPreferencesEditor.putString(USER,new Gson().toJson(user)).commit();
  }
  public User getUser() { return new Gson().fromJson(accountPreferences.getString(USER,""),User.class); }
  //public User getUser() {return new Gson().fromJson(accountPreferences.getString(USER, new Gson().toJson(new User(getName(), getUserEmail(), getPhoto()))),User.class);}

  public void saveEmail(Context context, String email){
    context = context;
    accountPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = accountPreferences.edit();
    editor.putString("EMAIL", email);
    editor.commit();
  }

  public void saveId(Context context, String id){
    context = context;
    accountPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = accountPreferences.edit();
    editor.putString("ID", id);
    editor.commit();
  }

  public String getUserEmail(){
    accountPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    return accountPreferences.getString("EMAIL", null);
  }

  public String getId(){
    accountPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    return accountPreferences.getString("ID", null);
  }


  public void saveName(Context context, String name){
    context = context;
    accountPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = accountPreferences.edit();
    editor.putString("NAME", name);
    editor.commit();
  }

  public String getName(){
    accountPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    return accountPreferences.getString("NAME", null);
  }

  public void saveRole(Context context, String role){
    context = context;
    accountPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = accountPreferences.edit();
    editor.putString("ROLE", role);
    editor.commit();
  }

  public String getRole(){
    accountPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    return accountPreferences.getString("ROLE", null);
  }

  public void savePhone(Context context, String phone){
    context = context;
    accountPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = accountPreferences.edit();
    editor.putString("PHONE", phone);
    editor.commit();
  }

  public String getPhone(){
    accountPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    return accountPreferences.getString("PHONE", null);
  }

  public void clear(){
    accountPreferencesEditor.clear();
    accountPreferencesEditor.apply();
  }
}