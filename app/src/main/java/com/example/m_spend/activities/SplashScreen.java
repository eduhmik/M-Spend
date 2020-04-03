package com.example.m_spend.activities;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;

import com.example.m_spend.R;
import com.example.m_spend.base.BaseActivity;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashScreen extends BaseActivity {

  @BindView(R.id.lin_Google)
  LinearLayout linGoogle;
  @OnClick(R.id.lin_Google)
  public void onViewClicked() {
    startNewActivity(RegisterActivity.class);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash_screen);
    assert getSupportActionBar() != null;
    getSupportActionBar().hide();
    ButterKnife.bind(this);
    linGoogle.setVisibility(sharedPrefs.getIsloggedIn() ? View.GONE : View.VISIBLE);
  }

  @Override
  protected void onPostCreate(@Nullable Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    final Handler handler = new Handler();
    handler.postDelayed(new Runnable() {
      @Override
      public void run() {
        if(sharedPrefs.getIsloggedIn()) {
          startNewActivity( MainActivity.class);
          SplashScreen.this.finish();
        }
      }
    }, 3000);
  }
}