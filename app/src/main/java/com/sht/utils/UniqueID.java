package com.sht.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.fieldbuzzassignment.R;

import java.util.UUID;

public class UniqueID {

    private Context mContext;

    public UniqueID(@NonNull Context context){
        this.mContext = context;
    }

    public static String getUniqueIDFromString(String string){
        String uuidString = null;
        if(string != null){
            uuidString = UUID.nameUUIDFromBytes(string.getBytes()).toString();
        }
        return uuidString;
    }

    public String getUniqueID(UniqueIDTypes type){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(getSharedPreferenceKey(type), Context.MODE_PRIVATE);
        String NOT_FOUND = "tsync_id not found";
        String idKey = "id";
        String uuidString = sharedPreferences.getString(idKey, NOT_FOUND);
        if(uuidString.equals(NOT_FOUND)){
            uuidString = UUID.randomUUID().toString();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(idKey, uuidString);
            editor.apply();
        }
        return uuidString;
    }

    private String getSharedPreferenceKey(UniqueIDTypes type){
        String key = null;
        switch (type){
            case PERSON:
                key = mContext.getString(R.string.person_tsync_id_sp);
                break;
            case CV_FILE:
                key = mContext.getString(R.string.file_tsync_id_sp);
                break;
        }
        return key;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

}
