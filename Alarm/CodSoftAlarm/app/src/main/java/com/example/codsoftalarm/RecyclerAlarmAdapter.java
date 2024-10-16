package com.example.codsoftalarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.materialswitch.MaterialSwitch;

import java.util.ArrayList;

public class RecyclerAlarmAdapter extends RecyclerView.Adapter<RecyclerAlarmAdapter.ViewHolder> {
    Context context ;
    ArrayList<AlarmModel> arralarm;
    public RecyclerAlarmAdapter(Context context,ArrayList<AlarmModel> arralarm) {
        this.arralarm = arralarm;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =LayoutInflater.from(context).inflate(R.layout.row,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

 
   

    @Override
    public void onBindViewHolder(@NonNull RecyclerAlarmAdapter.ViewHolder holder, int position) {
        holder.Stat.setChecked(arralarm.get(position).Status);
        holder.Time.setText((arralarm.get(position).time));

        holder.Label.setText((arralarm.get(position).Label));


    }

    @Override
    public int getItemCount() {
        return arralarm.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
    TextView Time,Label;
    MaterialSwitch Stat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Time=itemView.findViewById(R.id.Time);
            Label = itemView.findViewById(R.id.Label);
            Stat=itemView.findViewById(R.id.Status);
        }
    }

}
