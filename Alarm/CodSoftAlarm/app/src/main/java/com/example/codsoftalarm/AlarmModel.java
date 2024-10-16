package com.example.codsoftalarm;

public class AlarmModel {
    String time;
    boolean Status ;
    String Label="Label | ";

    public AlarmModel(int hour, int min, boolean Status, String label) {

        time= String.valueOf(hour)+":"+String.valueOf(min);
        this.Status = Status;
        this.Label = Label+label;
    }

    public AlarmModel(String time, boolean status, String label) {
        this.time = time;
        Status = status;
        Label = label;
    }
}
