package com.example.tickerwatchlistmanager;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;


public class TickerListFragment extends Fragment {
    private OnTickerSelectedListener listener;
    private ArrayList<String>data;
    private ArrayAdapter<String>adapter;
    private ListView listView;


    public interface OnTickerSelectedListener
    {
        void onTickerSelected(String symbol);
    }



    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        if (context instanceof OnTickerSelectedListener)
        {
            listener = (OnTickerSelectedListener) context;
        }
    }

    public void addTicker(String ticker)
    {
        if(data==null || adapter==null) return;
        if(data.size()<6)
        {
        data.add(ticker);
        }
        else {
            data.set(5, ticker);
        }
        adapter.notifyDataSetChanged();
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.tickerlistfrag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(R.id.list_tickers);

        data = new ArrayList<>(Arrays.asList("NEE","AAPL", "DIS"));

                adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                data
        );
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, v, position, id) ->
        {
            if (listener != null) {
                listener.onTickerSelected(data.get(position));
            }
        });

    }

    public static class InfoWebFragment {
    }
}
