package cn.itcast.takeout.uitl;


import es.dmoral.toasty.Toasty;

public class ToastyUtils {

    public static void error(CharSequence message){
        Toasty.error(MyApplication.getContext(), message,200,true).show();

    }
    public static void success(CharSequence message){
        Toasty.success(MyApplication.getContext(),message,200,true).show();
    }
}
