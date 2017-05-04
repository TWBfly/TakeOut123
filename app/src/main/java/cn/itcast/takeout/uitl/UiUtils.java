package cn.itcast.takeout.uitl;


import android.view.View;
import android.view.ViewGroup;

public class UiUtils {
    /**
     * 根据id获取到父容器
     * @param v
     * @param id
     * @return
     */
    public static ViewGroup getContainder(View v ,int id){
        ViewGroup parent = (ViewGroup) v.getParent();
        if (parent.getId() == id){
            return parent;
        }
        return getContainder(parent,id);
    }
}
