package cn.itcast.takeout.model.net;

import cn.itcast.takeout.model.dao.bean.ResponseInfo;
import cn.itcast.takeout.uitl.Constant;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ResponseInfoAPI {
    /**不带参数的请求*/
    @GET(Constant.HOME_URL)
    Call<ResponseInfo> getHomeInfo();
    @GET(Constant.GOODS_URL)
    Call<ResponseInfo> getGoodsInfo(@Query("sellerId")int sellerId);
    @GET(Constant.LOGIN_URL)
    Call<ResponseInfo> getLoginInfo(@Query("username")String username,@Query("password")String password,@Query("phone")String phone,@Query("type")int type);

}
