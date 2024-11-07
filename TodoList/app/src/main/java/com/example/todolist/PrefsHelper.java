package com.example.todolist;
import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PrefsHelper {
    private static final String PREFS_NAME = "my_prefs";
    private static final String KEY_TASK_LIST = "task_list";

    private SharedPreferences sharedPreferences;
    private Gson gson;

    public PrefsHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void saveTaskList(ArrayList<TaskModel> taskList) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(taskList);
        editor.putString(KEY_TASK_LIST, json);
        editor.apply();
    }

    public ArrayList<TaskModel> getTaskList() {
        String json = sharedPreferences.getString(KEY_TASK_LIST, null);
        Type type = new TypeToken<ArrayList<TaskModel>>() {}.getType();
        return gson.fromJson(json, type);
    }
}