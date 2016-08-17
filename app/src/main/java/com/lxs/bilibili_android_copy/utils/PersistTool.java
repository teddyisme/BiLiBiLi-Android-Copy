package com.lxs.bilibili_android_copy.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences
 */
public class PersistTool {

    private static final String PREFERENCES_NAME = "gogoal_preferences";

    private static SharedPreferences mPreference = null;

    private static boolean isInited = false;

    public synchronized static void init(Context context) {
        if (mPreference == null) {
            mPreference = context.getSharedPreferences(PREFERENCES_NAME,
                    Context.MODE_PRIVATE);
        }
        isInited = true;
    }

    public static boolean IsInited() {
        return isInited;
    }

    public static void saveBoolean(String name, boolean value) {
        SharedPreferences.Editor editor = mPreference.edit();
        editor.putBoolean(name, value);
        editor.commit();
    }

    public static boolean getBoolean(String name, boolean defaultValue) {
        return mPreference.getBoolean(name, defaultValue);
    }

    public static void saveLong(String name, long value) {
        SharedPreferences.Editor editor = mPreference.edit();
        editor.putLong(name, value);
        editor.commit();
    }

    public static long getLong(String name, long defaultValue) {
        return mPreference.getLong(name, defaultValue);
    }

    public static void saveInt(String name, int value) {
        SharedPreferences.Editor editor = mPreference.edit();
        editor.putInt(name, value);
        editor.commit();
    }

    public static int getInt(String name, int defaultValue) {
        return mPreference.getInt(name, defaultValue);
    }

    public static boolean saveFloat(String name, float value) {
        SharedPreferences.Editor editor = mPreference.edit();
        editor.putFloat(name, value);
        return editor.commit();
    }

    public static float getFloat(String name, float defaultValue) {
        return mPreference.getFloat(name, defaultValue);
    }

    public static boolean saveString(String name, String value) {
        boolean flag = false;
        SharedPreferences.Editor editor = mPreference.edit();
        editor.putString(name, value);
        flag = editor.commit();
        return flag;
    }

    public static String getString(String name, String defaultValue) {
        return mPreference.getString(name, defaultValue);
    }

    public static boolean clear() {
        return mPreference.edit().clear().commit();
    }

    public static boolean clearItem(String KEY_NAME) {
        mPreference.edit().remove(KEY_NAME).commit();
        return false;
    }

//  public static JSONArray getJsonArray(String name, JSONArray defaultValue) {
//    String result = mPreference.getString(name, "");
//
//    if (TextUtils.isEmpty(result)) {
//      return defaultValue;
//    } else {
//      return JSONArray.parseArray(result);
//    }
//  }
//
//  public static void saveJsonArray(String name, JSONArray value) {
//    SharedPreferences.Editor editor = mPreference.edit();
//    editor.putString(name, value.toJSONString());
//    editor.commit();
//  }
//
//  public static void saveJsonObject(String name, JSONObject value) {
//    SharedPreferences.Editor editor = mPreference.edit();
//    editor.putString(name, value.toJSONString());
//    editor.commit();
//  }
//
//  public static JSONObject getJsonObject(String name, JSONObject defaultValue) {
//    String result = mPreference.getString(name, "");
//    if (TextUtils.isEmpty(result)) {
//      return defaultValue;
//    } else {
//      return JSONObject.parseObject(result);
//    }
//  }
}
