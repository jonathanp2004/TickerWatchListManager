package com.example.tickerwatchlistmanager;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class MainActivity extends AppCompatActivity
        implements TickerListFragment.OnTickerSelectedListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

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
    }

    @Override
    public void onTickerSelected(String symbol) {
        InfoWebFragment web = (InfoWebFragment)
                getSupportFragmentManager().findFragmentByTag("web");
        if (web != null) {
            web.loadTicker(symbol);
        }
    }
}
