package com.example.todolist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class Pending extends Fragment {

    private RecyclerView rv;
    private RecyclerTaskAdapter rvadap;
    private ArrayList<TaskModel> taskList;



    public Pending() {
        // Required empty public constructor
    }

    public static Pending newInstance(ArrayList<TaskModel> taskList) {
        Pending fragment = new Pending();
        Bundle args = new Bundle();
        args.putParcelableArrayList("task_list", taskList);
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
        View view = inflater.inflate(R.layout.fragment_pending, container, false);
        rv = view.findViewById(R.id.rvpen);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        if (getArguments() != null) {
            taskList = getArguments().getParcelableArrayList("task_list");
            if(taskList==null)
                Toast.makeText(getContext(),"Task List is null",Toast.LENGTH_SHORT).show();

        }
        else
            Toast.makeText(getContext(),"Get arguments is null",Toast.LENGTH_SHORT).show();
        //taskList=db.getAllTasks();
        if (taskList != null) {
            rvadap = new RecyclerTaskAdapter(taskList,1,getContext());
            rv.setAdapter(rvadap);
            ItemTouchHelper.SimpleCallback scb= new ItemTouchHelper.SimpleCallback( 0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    int positon =viewHolder.getAdapterPosition();
                    if(direction==ItemTouchHelper.LEFT){
                        rvadap.removeItem(positon);
                    }
                    if(direction==ItemTouchHelper.RIGHT){
                        rvadap.changeIsDone(positon);
                    }

                }
            };
            ItemTouchHelper ith=new ItemTouchHelper(scb);
            ith.attachToRecyclerView(rv);
        }
        else
            Toast.makeText(getContext(),"hello",Toast.LENGTH_LONG).show();

        return view;
    }
}
