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

import java.util.Arrays;
import java.util.List;

public class TickerListFragment extends Fragment {
    public interface OnTickerSelectedListener
    {
        void onTickerSelected(String symbol);
    }

    private OnTickerSelectedListener listener;

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        if (context instanceof OnTickerSelectedListener)
        {
            listener = (OnTickerSelectedListener) context;
        }
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

        ListView list = view.findViewById(R.id.list_tickers);

        List<String> tickers = Arrays.asList("NEE", "AAPL", "DIS");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                tickers
        );
        list.setAdapter(adapter);

        list.setOnItemClickListener((parent, v, position, id) ->
        {
            if (listener != null) {
                listener.onTickerSelected(tickers.get(position));
            }
        });
    }
}
