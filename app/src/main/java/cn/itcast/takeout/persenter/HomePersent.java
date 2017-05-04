package cn.itcast.takeout.persenter;


import com.google.gson.Gson;

import cn.itcast.takeout.model.dao.bean.HomeInfo;
import cn.itcast.takeout.model.dao.bean.ResponseInfo;
import cn.itcast.takeout.ui.fragment.HomeFragment;
import retrofit2.Call;

public class HomePersent extends BasePersenter {
    private HomeFragment homeFragment;

    public HomePersent(HomeFragment homeFragment) {
        this.homeFragment = homeFragment;
    }

    @Override
    protected void showError(String message) {

    }

    @Override
    protected void parseJson(String data) {
        Gson gson = new Gson();
        HomeInfo homeInfo = gson.fromJson(data, HomeInfo.class);
        //将数据展示
        homeFragment.getHomeAdapter().setData(homeInfo);

    }
    /**发送请求的方法*/
    public void getHomeData(){
        Call<ResponseInfo> homeInfo = responseInfoAPI.getHomeInfo();
        homeInfo.enqueue(new CallBackAdapter());
    }
}
