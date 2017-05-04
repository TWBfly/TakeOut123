package cn.itcast.takeout.persenter;


import android.content.Context;

import com.j256.ormlite.android.AndroidDatabaseConnection;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.List;

import cn.itcast.takeout.model.DBHelper;
import cn.itcast.takeout.model.dao.bean.ResponseInfo;
import cn.itcast.takeout.model.dao.bean.UserInfo;
import cn.itcast.takeout.ui.activity.LoginActivity;
import cn.itcast.takeout.uitl.GsonUtils;
import cn.itcast.takeout.uitl.MyApplication;
import retrofit2.Call;

public class UserPersent extends BasePersenter {
    private final Context ctx;

    public UserPersent(Context ctx) {
        this.ctx = ctx;
    }

    DBHelper dbHelper = new DBHelper(MyApplication.getContext());
    private Savepoint start;

    @Override
    protected void showError(String message) {

    }

    @Override
    protected void parseJson(String data) {
        UserInfo userInfo = GsonUtils.GsonToBean(data, UserInfo.class);
        //将数据存储到数据库中,先连接
        AndroidDatabaseConnection androidDatabaseConnection = new AndroidDatabaseConnection(DBHelper.getInstance(MyApplication.getContext()).getWritableDatabase(),true);
        //得到Dao,进行操作数据库
        Dao dao = dbHelper.getDao(UserInfo.class);

        try {
            start = androidDatabaseConnection.setSavePoint("start");//设置数据库操作回滚点
            dao.setAutoCommit(androidDatabaseConnection,false);//设置自动提交事物为否
            List<UserInfo> userInfoList = dao.queryForAll();//全部查询
            if (userInfoList!=null&&userInfoList.size()>0){
                for (int i = 0; i < userInfoList.size(); i++) {
                    //将所有用户信息设为未登录状态
                    UserInfo user = userInfoList.get(i);
                    user.setLogin(0);
                    //更新数据库
                    dao.update(user);
                }
            }
            //指定id用户修改登录状态为true
            UserInfo userBean = (UserInfo) dao.queryForId(userInfo.get_id());
            if (userBean != null){
                //此用户之前登录过,只需要修改其登录状态即可
                userBean.setLogin(1);
                dao.update(userBean);
            }else {
                //此用户之前没有登录过,则需要插入一条新的数据,并且修改器状态
                UserInfo userLogin = new UserInfo();
                userLogin.setLogin(1);
                userLogin.set_id(userInfo.get_id());
                userLogin.setBalance(userInfo.getBalance());
                userLogin.setDiscount(userInfo.getDiscount());
                userLogin.setIntegral(userInfo.getIntegral());
                userLogin.setPhone(userInfo.getPhone());
                userLogin.setName(userInfo.getName());
                dao.create(userLogin);
            }
            //提交事物
            androidDatabaseConnection.commit(start);

        } catch (SQLException e) {
            e.printStackTrace();
            if (androidDatabaseConnection!=null){
                try {
                    //如果出现异常,则回滚到初始位置
                    androidDatabaseConnection.rollback(start);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }

        //LoginActivity界面结束
        ((LoginActivity) ctx).finish();


    }

    public void getLoginInfo(String userName, String password, String phone, int type) {
        Call<ResponseInfo> loginInfo = responseInfoAPI.getLoginInfo(userName, password, phone, type);
        loginInfo.enqueue(new CallBackAdapter());
    }
}
