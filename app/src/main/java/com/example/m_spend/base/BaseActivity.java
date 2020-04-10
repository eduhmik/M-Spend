package com.example.m_spend.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.m_spend.sharedprefs.SharedPrefs;
import com.example.m_spend.tools.SweetAlertDialog;
import com.google.gson.Gson;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;


public abstract class BaseActivity extends AppCompatActivity {
  public SweetAlertDialog _sweetAlertDialog;
  public SharedPrefs sharedPrefs;
  @VisibleForTesting
  public ProgressBar mProgressBar;

  public Gson gson;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    sharedPrefs = new SharedPrefs(this);
    gson = new Gson();
  }
  /**
   * show sweet alert with default duration
   * @param message to be displayed
   * @param title of alert
   * @param dialogType
   */
  public void showSweetDialog(String title, String message, int dialogType){
    _sweetAlertDialog = new SweetAlertDialog(this, dialogType);
    _sweetAlertDialog.setTitleText(title);
    _sweetAlertDialog.setContentText(message);
    _sweetAlertDialog.show();
  }
  /**
   * show sweet alert with default duration
   * @param message to be displayed
   * @param title of alert
   * @param dialogType
   * @param listener neutral button listener
   */
  public void showSweetDialog(String title, String message, int dialogType, String confirmText, SweetAlertDialog.OnSweetClickListener listener){
    _sweetAlertDialog = new SweetAlertDialog(this, dialogType);
    _sweetAlertDialog.setTitleText(title);
    _sweetAlertDialog.setContentText(message);
    _sweetAlertDialog.setConfirmText(confirmText);
    _sweetAlertDialog.setCancelable(false);
    _sweetAlertDialog.setConfirmClickListener(listener);
    _sweetAlertDialog.show();
  }
  /**
   * show toast with default duration
   * @param message to be displayed
   */
  public void showToast(String message)
  {
    Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
  }
  /**
   * init progress dialog, called by network requests
   */

  public void setProgressBar(int resId) {
    mProgressBar = findViewById(resId);
  }

  public void showProgressBar() {
    if (mProgressBar != null) {
      mProgressBar.setVisibility(View.VISIBLE);
    }
  }

  public void hideProgressBar() {
    if (mProgressBar != null) {
      mProgressBar.setVisibility(View.INVISIBLE);
    }
  }

  public void hideKeyboard(View view) {
    final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    if (imm != null) {
      imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
  }

  @Override
  public void onStop() {
    super.onStop();
    hideProgressBar();
  }


  public void startNewActivity(Class<? extends Activity> clazz){
    startActivity(new Intent(this,clazz));
  }
}