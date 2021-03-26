package com.papasfritas.coronavirus.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.KeyEvent;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.tabs.TabLayout;
import com.papasfritas.coronavirus.Activity.Adapters.viewPagerAdapter;
import com.papasfritas.coronavirus.Activity.Fragments.GraphFragment;
import com.papasfritas.coronavirus.Activity.Fragments.InfoFragment;
import com.papasfritas.coronavirus.Activity.Fragments.RegionFragment;
import com.papasfritas.coronavirus.Activity.Fragments.WebFragment;
import com.papasfritas.coronavirus.R;

public class StartActivity extends AppCompatActivity {
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getSupportActionBar().hide();
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                System.out.println(initializationStatus.getAdapterStatusMap());
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        init();
    }

    public void init(){

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);

        viewPagerAdapter viewPagerAdapter = new viewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new InfoFragment(), "General");
        viewPagerAdapter.addFragment(new GraphFragment(), "Gráfico");
        viewPagerAdapter.addFragment(new RegionFragment(), "Región/Comuna");
        viewPagerAdapter.addFragment(new WebFragment(), "Situación actual");


        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                System.out.println("onAdloaded");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                System.out.println(errorCode);
            }

            @Override
            public void onAdOpened() {
                System.out.println("onAdOpened");
            }

            @Override
            public void onAdClicked() {
                System.out.println("onAdClicked");
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                System.out.println("onAdLeftApplication");
            }

            @Override
            public void onAdClosed() {
                System.out.println("onAdClosed");
            }
        });

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            super.onBackPressed(); finishAffinity(); System.exit(0);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}
