package com.example.todolist;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import com.example.todolist.*;




public class MainActivity extends AppCompatActivity {
    BottomNavigationView bnView;
    ArrayList<TaskModel> arrayList;
    Bundle args;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        arrayList=new ArrayList<TaskModel>();

        // Test cases removed after testing
//        arrayList.add(new TaskModel("Test 1","Desc for test", true,new Date().getTime(),1));
//        arrayList.add(new TaskModel("Test 2","Desc for test", true,new Date().getTime(),8));
//        arrayList.add(new TaskModel("Test 3","Desc for test", true,new Date().getTime(),6));
//        arrayList.add(new TaskModel("Test 3","Desc for test", true,new Date().getTime(),7));
//        arrayList.add(new TaskModel("Test 4","Desc for test", true,new Date().getTime(),4));
//        arrayList.add(new TaskModel("Test 5","Desc for test", false,new Date().getTime(),2));
//        arrayList.add(new TaskModel("Test 6","Desc for test", false,new Date().getTime(),3));

        bnView=findViewById(R.id.bottomNavigationView);
        PrefsHelper ph =new PrefsHelper(this);
        ph.saveTaskList(arrayList);

        bnView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                if(id==R.id.add){
                    loadFrag(Add.newInstance(ph.getTaskList()),false);
                    Toast.makeText(getApplicationContext(),"ADD",Toast.LENGTH_SHORT).show();


                }
                else if(id==R.id.all){
                    loadFrag(new All(),false);
                    Toast.makeText(getApplicationContext(),"ALL",Toast.LENGTH_SHORT).show();
                }
                else if(id==R.id.done){
                    loadFrag(Completed.newInstance(ph.getTaskList()),false);

                    Toast.makeText(getApplicationContext(),"Completed",Toast.LENGTH_SHORT).show();

                }
                else if(id==R.id.pending){
                    loadFrag(Pending.newInstance(ph.getTaskList()),true);
                    Toast.makeText(getApplicationContext(),"Pending",Toast.LENGTH_SHORT).show();

                }
                return true;
            }
        });
        bnView.setSelectedItemId(R.id.pending);
    }
    public void loadFrag(Fragment fragement, boolean flag){
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();


        if(flag){
            ft.replace(R.id.frame,fragement);
        }

        else{

            ft.replace(R.id.frame,fragement);
        }

        ft.commit();


    }


}
