package com.example.m_spend.activities;

import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.m_spend.R;
import com.example.m_spend.adapters.MainPagerAdapter;
import com.example.m_spend.base.BaseActivity;
import com.example.m_spend.database.AppDatabase;
import com.example.m_spend.fragments.SummaryFragment;
import com.example.m_spend.interfaces.OnFragmentInteractionListener;
import com.example.m_spend.models.User;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, OnFragmentInteractionListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view) NavigationView navView;
    @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;
    @BindView(R.id.container)
    ViewPager mViewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @OnClick(R.id.fab_upload)
    public void onFabClicked() {
        // Upload an Mpesa Statement
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setTitle("M-Spend");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navView.setNavigationItemSelectedListener(this);
        loadViewPager();
    }
    public void loadViewPager(){
        ArrayList<Fragment> mFragments = new ArrayList<>();
        mFragments.add(SummaryFragment.newInstance());
        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mainPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        User user = AppDatabase.getAppDatabase(this).userDao().findById(1);
        View headerView = navView.getHeaderView(0);
        TextView tvEmail = (TextView) headerView.findViewById(R.id.tv_Email);
        TextView tvName = (TextView) headerView.findViewById(R.id.tv_Name);
        ImageView ivImage = (ImageView) headerView.findViewById(R.id.iv_ProfileImage);
        tvName.setText(user.getFirstName()+" "+user.getLastName());
        tvEmail.setText(user.getEmail());
        Glide.with(this).load(user.getImage()).apply(RequestOptions.circleCropTransform()).into(ivImage);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.action_log_out){
            logOut();
        }

        return super.onOptionsItemSelected(item);
    }
    public void logOut(){
        sharedPrefs.setIsloggedIn(false);
        AppDatabase.getAppDatabase(this).userDao().delete(AppDatabase.getAppDatabase(this).userDao().findById(1));
        startNewActivity(SplashScreen.class);
        finish();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_edit) {
            startNewActivity(EditProfileActivity.class);
        } else if (id == R.id.nav_logout) {
            logOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
