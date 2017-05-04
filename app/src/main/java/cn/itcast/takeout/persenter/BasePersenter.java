package cn.itcast.takeout.persenter;


import java.util.HashMap;

import cn.itcast.takeout.model.dao.bean.ResponseInfo;
import cn.itcast.takeout.model.net.ResponseInfoAPI;
import cn.itcast.takeout.uitl.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BasePersenter {

    protected ResponseInfoAPI responseInfoAPI;
    private HashMap<String, String> errorMap;

    public BasePersenter() {
        //封装主机地址
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //创建具体的网络请求实体对象
        // 字节码文件
        responseInfoAPI = retrofit.create(ResponseInfoAPI.class);
    }
    /**请求网络回调*/
    class CallBackAdapter implements Callback<ResponseInfo>{

        public CallBackAdapter() {
            errorMap = new HashMap<>();
            errorMap.put("5","访问服务器失败");
            errorMap.put("4","服务器忙请稍后重试");
            errorMap.put("3","服务器没有更新数据,返回缓存数据");
            errorMap.put("2","操蛋,工作真几把蛋疼");
            errorMap.put("1","你妹的,找不到工作");
        }

        @Override
        public void onResponse(Call<ResponseInfo> call, Response<ResponseInfo> response) {
            //成功
            ResponseInfo body = response.body();
            if (body.getCode().equals("0")){
                String data = body.getData();
                parseJson(data);
            }else{
                String s = errorMap.get(body.getCode());
                onFailure(call,new RuntimeException(s));
            }

        }

        @Override
        public void onFailure(Call<ResponseInfo> call, Throwable t) {
            //失败
            if (t instanceof RuntimeException){
                    showError(t.getMessage());
            }else{
                showError("你妹的,找啊找个好工作");
            }


        }
    }
    //由子类具体搞定
    protected abstract void showError(String message) ;



    //解析data数据
    protected abstract void parseJson(String data) ;


}

