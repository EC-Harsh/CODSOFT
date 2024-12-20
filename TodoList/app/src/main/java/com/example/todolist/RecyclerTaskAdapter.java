package com.example.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kotlinx.coroutines.scheduling.Task;

public class RecyclerTaskAdapter extends RecyclerView.Adapter<RecyclerTaskAdapter.ViewHolder> {
    @NonNull
    Context cn;
    int frag;
    ArrayList<TaskModel> arrtasks=new ArrayList<>();
    ArrayList<TaskModel> arrtemtasks;
    PrefsHelper ph;

    public RecyclerTaskAdapter(ArrayList<TaskModel> arrtemtasks,int frag,Context cn) {
        this.frag=frag;
        this.arrtemtasks=arrtemtasks;
        ph=new PrefsHelper(cn);
       for(TaskModel task:arrtemtasks){
           if(frag==1 && (!task.isDone())){
               arrtasks.add(task);
           }
           else if(frag==2 && task.isDone()){
               arrtasks.add(task);
           }
           else{
               if(frag==4){
               arrtasks.add(task);
               }

           }
       }
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.task_box,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.title.setText((arrtasks.get(position).getTitle()));
        holder.desc.setText(arrtasks.get(position).getDescription());
        holder.stat.setChecked(arrtasks.get(position).isDone());


    }

    @Override
    public int getItemCount() {
        return arrtasks.size();
    }

    public void removeItem(int position)
    {
        TaskModel temp = arrtasks.get(position);
        arrtemtasks.remove(temp);
        ph.saveTaskList(arrtemtasks);

    }

    public void changeIsDone(int positon) {
        TaskModel ob = arrtasks.get(positon);
        ob.setDone(true);

        arrtemtasks.set(arrtemtasks.indexOf(arrtasks.get(positon)),ob);
        ph.saveTaskList(arrtemtasks);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,desc;
        CheckBox stat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.task_title);
            desc=itemView.findViewById(R.id.task_description);
            stat=itemView.findViewById(R.id.task_status);
        }
    }
}
