package com.example.todolist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Completed#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Completed extends Fragment {
    RecyclerTaskAdapter rvadap;
    RecyclerView rv;
    ArrayList<TaskModel> arrTasks ;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "arr_tasks";


    // TODO: Rename and change types of parameters


    public Completed() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Completed newInstance(ArrayList<TaskModel> arrTasks) {
        Completed fragment = new Completed();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, arrTasks);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_completed, container, false);
        rv = view.findViewById(R.id.rvcomp);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        if (getArguments() != null) {
            arrTasks= getArguments().getParcelableArrayList(ARG_PARAM1);

            if(arrTasks==null)
                Toast.makeText(getContext(),"Task List is null",Toast.LENGTH_SHORT).show();
            else{
             rvadap = new RecyclerTaskAdapter(arrTasks,2);
            rv.setAdapter(rvadap);

            }
        }
        else
            Toast.makeText(getContext(),"Get arguments is null",Toast.LENGTH_SHORT).show();

        return view;
    }
}