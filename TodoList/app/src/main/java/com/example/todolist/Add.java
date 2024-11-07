package com.example.todolist;

import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Add#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Add extends Fragment {
    ArrayList<TaskModel> arrTasks;
    PrefsHelper ph ;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "array_list";


    // TODO: Rename and change types of parameters



    // View in the add task form
    ImageButton add;
    TextInputEditText title,desc;
    EditText priority, lastDate;


    public Add() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
   //  * @param param1 Parameter 1.
    // * @param param2 Parameter 2.
     * @return A new instance of fragment Add.
     */
    // TODO: Rename and change types and number of parameters
    public static Add newInstance(ArrayList<TaskModel> arrTask) {
        Add fragment = new Add();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1,arrTask);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           arrTasks= getArguments().getParcelableArrayList(ARG_PARAM1);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        add=view.findViewById(R.id.add);
        title=view.findViewById(R.id.title);
        desc=view.findViewById(R.id.desc);

    ph=new PrefsHelper(getActivity());
        add.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                arrTasks.add(new TaskModel(title.getText().toString(),desc.getText().toString(),false));
                ph.saveTaskList(arrTasks);
                title.setText("");
                desc.setText("");

            }
        });

        // Inflate the layout for this fragment

        return view ;
    }

}