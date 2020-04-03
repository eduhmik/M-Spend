package com.example.m_spend.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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

import androidx.annotation.NonNull;
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

    private static final int PICK_PDF_CODE = 1000;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view) NavigationView navView;
    @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;
    @BindView(R.id.container)
    ViewPager mViewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    String password = " ";
    @OnClick(R.id.fab_upload)
    public void onFabClicked() {
        // Upload an Mpesa Statement
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, PICK_PDF_CODE);
    }

    public void showDialog()
    {

        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.password_dialog, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);


        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Go",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                /** DO THE METHOD HERE WHEN PROCEED IS CLICKED*/
                                password = (userInput.getText()).toString();
//                                /** CHECK FOR USER'S INPUT **/
//                                if (user_text.equals("oeg"))
//                                {
//                                    Log.d(user_text, "HELLO THIS IS THE MESSAGE CAUGHT :)");
//                                    Search_Tips(user_text);
//
//                                }
//                                else{
//                                    Log.d(user_text,"string is empty");
//                                    String message = "The password you have entered is incorrect." + " \n \n" + "Please try again!";
//                                    AlertDialog.Builder builder = new AlertDialog.Builder(getBaseContext());
//                                    builder.setTitle("Error");
//                                    builder.setMessage(message);
//                                    builder.setPositiveButton("Cancel", null);
//                                    builder.setNegativeButton("Retry", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int id) {
//                                            showDialog();
//                                        }
//                                    });
//                                    builder.create().show();
//
//                                }
//                            }
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.dismiss();
                            }

                        }

                );

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null)
        {
            Uri selectedPdf = data.getData();
            Intent intent = new Intent(MainActivity.this, ViewActivity.class);
            intent.putExtra("ViewType", "storage");
            intent.putExtra("FileUri", selectedPdf.toString());
            startActivity(intent);
        }
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

    private static final String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private static final int PERMISSIONS_COUNT = 2;
    private static final int  REQUEST_PERMISSIONS = 231;

    @SuppressLint("NewApi")
    @Override
    protected void onResume() {
        super.onResume();
        if(notPermissions()) {
            requestPermissions(PERMISSIONS, REQUEST_PERMISSIONS);
        } else {
            // load data
        }
    }

    private boolean notPermissions() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permissionPtr = 0;
            while (permissionPtr < PERMISSIONS_COUNT) {
                if (checkSelfPermission(PERMISSIONS[permissionPtr]) != PackageManager.PERMISSION_GRANTED) {
                    return true;
                }
                permissionPtr++;
            }
        }
        return false;
    }

    @SuppressLint("NewApi")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_PERMISSIONS && grantResults.length > 0) {
            if(notPermissions()){
                ((ActivityManager) this.getSystemService(ACTIVITY_SERVICE)).clearApplicationUserData();
                recreate();
            } else {
                // load data
            }
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
