package com.ratatouille.Models;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class LocalStorage {
    //SYSTEM
    private static final String TAG = "LocalStorage";


    Context context;
    SharedPreferences.Editor Editor;
    SharedPreferences Pref;

    public LocalStorage(Context context) {
        this.context = context;
        this.Pref = context.getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        this.Editor = this.Pref.edit();
    }

    public void putData(String key, Object value){
        switch (value.getClass().getSimpleName()){
            case "String":
                Editor.putString(key, (String) value);
                break;
            case "Integer":
                Editor.putInt(key, (Integer) value);
                break;
            case "Float":
                Editor.putFloat(key, (Float) value);
                break;
            case "Boolean":
                Editor.putBoolean(key, (Boolean) value);
                break;
            default:
        }
        Editor.commit();
        Editor.apply();
    }

    public Object getData(String key,String type){
        if(Pref.contains(key)){

            switch (type){
                case "String":  return Pref.getString   (key, null);
                case "Integer": return Pref.getInt      (key, 999);
                case "Float":   return Pref.getFloat    (key,  0);
                case "Boolean": return Pref.getBoolean  (key, false);
                default:  return null;
            }
        }else return null;
    }
    public void DeleteAllData(){
        Editor.clear();
        Editor.apply();
        Editor.commit();
    }
}
