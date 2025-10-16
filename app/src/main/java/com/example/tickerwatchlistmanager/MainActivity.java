package com.example.tickerwatchlistmanager;

import android.os.Bundle;
import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class MainActivity extends AppCompatActivity

        implements TickerListFragment.OnTickerSelectedListener {
    private static final int REQUEST_SMS_PERMISSION = 101;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECEIVE_SMS},
                    REQUEST_SMS_PERMISSION);
        }

        boolean hasTwoPaneContainers =
                findViewById(R.id.ticker_list_container) != null &&
                        findViewById(R.id.info_web_container) != null;

        if (hasTwoPaneContainers && savedInstanceState == null) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction tx = fm.beginTransaction();
            tx.replace(R.id.ticker_list_container, new TickerListFragment(), "list");
            tx.replace(R.id.info_web_container, new InfoWebFragment(), "web");
            tx.commit();
        }
        getSupportFragmentManager().executePendingTransactions();
        handleSms(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleSms(intent);
    }

    @Override
    public void onTickerSelected(String symbol) {
        InfoWebFragment web = (InfoWebFragment)
                getSupportFragmentManager().findFragmentByTag("web");
        if (web != null) {
            web.loadTicker(symbol);
        }
    }

    private void addTickerToList(String ticker)
    {
        TickerListFragment list = (TickerListFragment)
                getSupportFragmentManager().findFragmentByTag("list");
        if(list != null)
        {
            list.addTicker(ticker);
        }
    }

    private void openTickerInWebFragment(String ticker)
    {
        InfoWebFragment web = (InfoWebFragment)
                getSupportFragmentManager().findFragmentByTag("web");
        if(web != null)
        {
            web.loadTicker(ticker);
        }
    }

    private void handleSms(Intent intent)
    {
        if(intent == null) return;
        String action = intent.getStringExtra("action");
        if("openTicker".equals(action))
        {
            String ticker = intent.getStringExtra("ticker");
            addTickerToList(ticker);
            openTickerInWebFragment(ticker);
        }
    }

}
