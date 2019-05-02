package com.audigosolutions.android.outlay;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class AllCards extends Fragment {


    private View view;
    private ListView listView;
    private CardAdapter adapter;
    private RealmResults<CardDetails> list;
    private Realm realm;
    private ImageView noResults;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.all_cards, container, false);

        realm = Realm.getDefaultInstance();

        listView = (view).findViewById(R.id.listview);
        List<CardDetails> cards= new ArrayList<>();

        noResults = view.findViewById(R.id.noresults);

        adapter = new CardAdapter(getContext(), R.layout.list_item, cards);
        listView.setAdapter(adapter);

        return view;
    }


    private void accessData()
    {
        list = realm.where(CardDetails.class).findAll();
        for (CardDetails i : list)
        {
            adapter.add(i);
        }
        if (listView.getAdapter().getCount()==0)
        {
            noResults.setVisibility(View.VISIBLE);
        }
        else
        {
            noResults.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        accessData();
    }

    @Override
    public void onPause() {
        super.onPause();
        adapter.clear();
    }
}
