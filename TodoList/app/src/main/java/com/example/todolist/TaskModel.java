package com.example.todolist;

import android.os.Parcel;
import android.os.Parcelable;

public class TaskModel implements Parcelable {
    private String title;
    private String description;
    private boolean isDone;
    private long date;
    private int priority;

    public TaskModel(String title, String description, boolean isDone, long date, int priority) {
        this.title = title;
        this.description = description;
        this.isDone = isDone;
        this.date = date;
        this.priority = priority;
    }

    protected TaskModel(Parcel in) {
        title = in.readString();
        description = in.readString();
        isDone = in.readByte() != 0;
        date = in.readLong();
        priority = in.readInt();
    }

    public static final Creator<TaskModel> CREATOR = new Creator<TaskModel>() {
        @Override
        public TaskModel createFromParcel(Parcel in) {
            return new TaskModel(in);
        }

        @Override
        public TaskModel[] newArray(int size) {
            return new TaskModel[size];
        }
    };

    public TaskModel(String title, String description, boolean isCompleted) {
        this.title=title;
        this.description=description;
        this.isDone=isCompleted;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeByte((byte) (isDone ? 1 : 0));
        dest.writeLong(date);
        dest.writeInt(priority);
    }
}