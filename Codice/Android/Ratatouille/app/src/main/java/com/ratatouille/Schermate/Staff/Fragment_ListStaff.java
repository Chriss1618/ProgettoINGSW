package com.ratatouille.Schermate.Staff;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ratatouille.Controllers.Controller_Login;
import com.ratatouille.R;

public class Fragment_ListStaff extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    //SYSTEM
    private static final String TAG = "Fragment_ListStaff";

    //LAYOUT
    View            Fragment_View;
    LinearLayout    Background;
    Button          Button_Start;
    TextView        Text_View_Welcome;

    //FUNCTIONAL
    private Controller_Login Manager_Login;

    //OTHER...

    public Fragment_ListStaff() {
        // Required empty public constructor
    }
    public static Fragment_ListStaff newInstance(String param1, String param2) {
        Fragment_ListStaff fragment = new Fragment_ListStaff();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_staff, container, false);
    }
}