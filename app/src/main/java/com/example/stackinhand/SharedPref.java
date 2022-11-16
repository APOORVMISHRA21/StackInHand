package com.example.stackinhand;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    private static final String NAME_PREF = "app_shared_pref";
    private static final String DISCARD_BTN_COUNT_KEY = "discard_btn_click_count";

    private Context context;

    public SharedPref(Context context) {
        this.context = context;
    }

    public void recordDiscardBtnClick(int value) {
        SharedPreferences sharedPref = context.getSharedPreferences(NAME_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(DISCARD_BTN_COUNT_KEY, value);
        editor.commit();
    }

    public int getDiscardBtnClickCount() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME_PREF,Context.MODE_PRIVATE);
        return sharedPreferences.getInt(DISCARD_BTN_COUNT_KEY, 0);
    }
}
