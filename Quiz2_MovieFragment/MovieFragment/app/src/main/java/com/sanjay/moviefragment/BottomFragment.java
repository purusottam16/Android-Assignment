package com.sanjay.moviefragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class BottomFragment extends Fragment {

    public static BottomFragment newInstance() {
        
        Bundle args = new Bundle();
        
        BottomFragment fragment = new BottomFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public BottomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottom, container, false);
    }

}
