package com.example.authtechsphere.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.authtechsphere.Admin.DocShare;
import com.example.authtechsphere.R;

public class fragment_home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_settings.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_settings newInstance(String param1, String param2) {
        fragment_settings fragment = new fragment_settings();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);


        Button btn_festive = (Button) rootView.findViewById(R.id.btn_festive);
        Button btn_tt = (Button) rootView.findViewById(R.id.btn_tt);
        Button btn_cpi = (Button) rootView.findViewById(R.id.btn_cpi);
        Button btn_att = (Button) rootView.findViewById(R.id.btn_att);

        TextView tv_how = (TextView) rootView.findViewById(R.id.tv_how);

        btn_festive.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i  = new Intent(getActivity(), festiveFragmentActivity.class);
                startActivity(i);
            }
        });

        btn_tt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i  = new Intent(getActivity(), DocShare.class);
                startActivity(i);
            }
        });

        btn_cpi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i  = new Intent(getActivity(), cpiFragmentActivity.class);
                startActivity(i);
            }
        });

        btn_att.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i  = new Intent(getActivity(), attFragmentActivity.class);
                startActivity(i);
            }
        });


        tv_how.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchActivity();
            }

            public void launchActivity() {

                Intent i = new Intent(getActivity(), InstructActivity.class);
                startActivity(i);
            }
        });
        return rootView;
    }
}