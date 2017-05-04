package cn.itcast.takeout.uitl;

import android.app.Application;
import android.content.Context;


public class MyApplication extends Application {
    private static Context context;
    public static int statusBarHeight;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        /**
        * 获取状态栏高度
        */
        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }






    }

    public static Context getContext() {
        return context;
    }
}
