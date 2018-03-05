package com.liu.climb.noteapp.common.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.liu.climb.noteapp.data.source.NoteSource;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pd_liu on 2018/2/27.
 *         <p>
 *         SharedPreference工具类
 *         </p>
 */

public class SharedPreferenceUtil {

    private static final String TAG_LOG = "SharedPreferenceUtil";

    private static final String NOTE_NAME_SF = "note_shared";

    public static boolean save(Context context, NoteSource noteSource) {

        if (StringUtil.isEmpty(context) || StringUtil.isEmpty(noteSource)) {
            LogUtil.w(TAG_LOG, "参数异常");
            return false;
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences(NOTE_NAME_SF, Context.MODE_PRIVATE);

        ArrayList<NoteSource> noteSources = null;

        ArrayList<NoteSource> restoreSource = restore(context);

        if (restoreSource == null) {
            noteSources = new ArrayList<>();
        } else {
            noteSources = restoreSource;
        }
        noteSources.add(noteSource);

        /*
        序列化集合
         */
        Gson gson = createGson();
        String json = gson.toJson(noteSources);

        /*
        保存数据
         */
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(NOTE_NAME_SF, json);
        boolean ok = editor.commit();
        LogUtil.w(TAG_LOG, "保存数据" + ok);
        return ok;
    }

    public static ArrayList<NoteSource> restore(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(NOTE_NAME_SF, Context.MODE_PRIVATE);

        String json = sharedPreferences.getString(NOTE_NAME_SF, null);

        if (json == null) {
            LogUtil.e(TAG_LOG, "数据为空");
            return null;
        }

        Type founderListType = new TypeToken<ArrayList<NoteSource>>() {
        }.getType();
        ArrayList<NoteSource> noteSources = createGson().fromJson(json, founderListType);
        return noteSources;
    }

    public static NoteSource findNoteByCrateDate(Context context, String date) {

        ArrayList<NoteSource> resources = restore(context);

        for (NoteSource source :
                resources) {
            if (source.getCreateDate().equals(date)) {
                return source;
            }
        }

        return null;
    }

    /**
     * @return
     * @Hide
     */
    private static Gson createGson() {
        return new GsonBuilder().enableComplexMapKeySerialization().create();
    }

    public static void printData(Context context){
        ArrayList<NoteSource> resources = restore(context);
        LogUtil.e(TAG_LOG, "打印数据：");
        for (int i = 0; i < resources.size(); i++) {
            LogUtil.e(TAG_LOG, resources.get(i).toString());
        }
    }
}
