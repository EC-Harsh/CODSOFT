package com.example.todolist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link All#newInstance} factory method to
 * create an instance of this fragment.
 */
public class All extends Fragment {
    RecyclerTaskAdapter rvadap;
    RecyclerView rv;
    ArrayList<TaskModel> arrTasks ;
    PrefsHelper ph ;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "arr_tasks";


    // TODO: Rename and change types of parameters


    public All() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
//    public static All newInstance(ArrayList<TaskModel> arrTasks) {
//        All fragment = new All();
//        Bundle args = new Bundle();
//        args.putParcelableArrayList(ARG_PARAM1, arrTasks);
//
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            this.arrTasks=getArguments().getParcelableArrayList(ARG_PARAM1);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_all, container, false);
        rv = view.findViewById(R.id.rvall);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        ph=new PrefsHelper(getContext());
        arrTasks=ph.getTaskList();
        if (arrTasks != null) {
            rvadap = new RecyclerTaskAdapter(arrTasks,4);
            rv.setAdapter(rvadap);
        }
        // Inflate the layout for this fragment
        return view;
    }
}