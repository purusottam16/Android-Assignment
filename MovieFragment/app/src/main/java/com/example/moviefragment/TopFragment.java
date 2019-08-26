package com.sanjay.moviefragment;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class TopFragment extends Fragment {

    private int screenType = -1;
    Boolean mDualPane;

    TextView titleTextView;
    Button backButton;

    public static TopFragment newInstance(int screenType,Boolean mDualPane) {
        
        Bundle args = new Bundle();
        
        TopFragment fragment = new TopFragment();

        args.putInt("screenType",screenType);
        args.putBoolean("dualPane",mDualPane);

        fragment.setArguments(args);

        return fragment;
    }


    public TopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top, container, false);
        backButton = view.findViewById(R.id.backButton);

        screenType = getArguments().getInt("screenType",-1);
        mDualPane = getArguments().getBoolean("dualPane");

        if(screenType == MainActivity.MAIN_SCREEN){
            backButton.setVisibility(View.GONE);
        }
        else{
            backButton.setVisibility(View.VISIBLE);

            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(getFragmentManager().getBackStackEntryCount() > 0 && !mDualPane){
                        Log.e("Back Button IF","Value "+mDualPane);
                        getFragmentManager().popBackStack();
                    }else {
                        Log.e("Back Button ELSE","Value "+mDualPane);
                       // getActivity().onBackPressed();
                         startActivity(new Intent(getContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    }
                }
            });
        }

        return view;
    }

}
