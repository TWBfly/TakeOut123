package cn.itcast.takeout.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.itcast.takeout.R;
import cn.itcast.takeout.ui.activity.LoginActivity;

/**
 * 个人
 */
public class UserFragment extends BaseFragment {
    @BindView(R.id.tv_user_setting)
    ImageView tvUserSetting;
    @BindView(R.id.iv_user_notice)
    ImageView ivUserNotice;
    @BindView(R.id.login)
    ImageView login;
    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.ll_userinfo)
    LinearLayout llUserinfo;
    @BindView(R.id.iv_address)
    ImageView ivAddress;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(container.getContext(), R.layout.fragment_user, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //判断用户是否处于登录状态isLogin的值为1
        //获取dao,查询数据库,获取usename,phone
    }

    @OnClick({R.id.login})
    public void onClick(View view){
        Intent intent = new Intent(view.getContext(), LoginActivity.class);
        startActivity(intent);
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
