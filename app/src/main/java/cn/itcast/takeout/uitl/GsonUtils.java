package cn.itcast.takeout.uitl;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class GsonUtils {
    private static Gson gson = null;
    static {
        if (gson == null)
            gson = new Gson();
    }
    public GsonUtils(){

    }

    /**
     * 转成String类型
     * @param obj
     * @return
     */
    public static String GsonString(Object obj){
        String gsonString = null;
        if (gson != null)
            gsonString = gson.toJson(obj);
        return  gsonString;
    }

    /**
     * 转成bean
     * @param gsonString
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T GsonToBean(String gsonString,Class<T> cls){
        T t = null;
        if (gson != null)
            t = gson.fromJson(gsonString,cls);
        return t;
    }

    /**
     * 转成List
     * @param gsonString
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> GsonToList(String gsonString, Class<T> cls){
        List<T> list = null;
        if (gson != null)
            list = gson.fromJson(gsonString,new TypeToken<List<T>>(){}.getType());
        return list;
    }
}
