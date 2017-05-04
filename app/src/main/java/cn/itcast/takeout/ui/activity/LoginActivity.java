package cn.itcast.takeout.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.itcast.takeout.R;
import cn.itcast.takeout.persenter.UserPersent;
import cn.itcast.takeout.uitl.SMSUtil;
import cn.itcast.takeout.uitl.ToastyUtils;
import cn.smssdk.EventHandler;
import cn.smssdk.OnSendMessageHandler;
import cn.smssdk.SMSSDK;


public class LoginActivity extends BaseActivity {

    @BindView(R.id.iv_user_back)
    ImageView ivUserBack;
    @BindView(R.id.iv_user_password_login)
    TextView ivUserPasswordLogin;
    @BindView(R.id.et_user_phone)
    EditText etUserPhone;
    @BindView(R.id.tv_user_code)
    TextView tvUserCode;
    @BindView(R.id.et_user_code)
    EditText etUserCode;
    @BindView(R.id.login)
    TextView login;
    @BindView(R.id.et_user_psd)
    EditText etUserPsd;
    private static final int GET_CODE_SUCCESS = 1001;
    private static final int GET_CODE_ERROR = 1002;
    private static final int KEEP_TIMR = 1003;
    private static final int RESET_TIMR = 1004;
    private static final int CODE_SUCCESS = 1005;
    private static final int CODE_FAIL = 1006;
    private int time = 60;
    private String phone;
    private String passw;
    UserPersent userPersent = new UserPersent(this);

    //防止内存泄漏
    class MyHandler extends Handler {
        WeakReference<LoginActivity> loginActivityWeakReference;

        public MyHandler(LoginActivity loginActivity) {
            loginActivityWeakReference = new WeakReference<>(loginActivity);
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_CODE_SUCCESS:
                    ToastyUtils.success("发送成功");
                    break;
                case GET_CODE_ERROR:
                    ToastyUtils.error("发送失败,请重新发送");
                    break;
                case KEEP_TIMR:
                    tvUserCode.setText("稍后发送(" + (--time) + ")s");
                    tvUserCode.setBackgroundColor(R.color.text_gray);
                    break;
                case RESET_TIMR:
                    time = 60;
                    tvUserCode.setText("重新发送");
                    tvUserCode.setEnabled(true);
                    tvUserCode.setBackgroundColor(R.color.blue);
                    break;
                case CODE_SUCCESS:
                   ToastyUtils.success("进行登录");
                    ToLogin();
                    break;
                case CODE_FAIL:
                   ToastyUtils.error("验证码错误!");
                    break;

                default:
                    break;

            }

        }
    }

    private void ToLogin() {
        userPersent.getLoginInfo(phone,passw,phone,2);
    }

    private MyHandler handler = new MyHandler(LoginActivity.this);

    private EventHandler eventHandler = new EventHandler() {
        @Override
        public void afterEvent(int event, int result, Object data) {
            super.afterEvent(event, result, data);
            if (result == SMSSDK.RESULT_COMPLETE) {//发送成功
                if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    handler.sendEmptyMessage(GET_CODE_SUCCESS);
                }
                //验证码正确
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    handler.sendEmptyMessage(CODE_SUCCESS);
                }
            } else {
                Logger.e(data+"");
                Logger.e(event+""+result+"怎么就发送失败");
                //发送失败
                if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    handler.sendEmptyMessage(GET_CODE_ERROR);
                }
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    handler.sendEmptyMessage(CODE_FAIL);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        //1.初始化SDK
        SMSSDK.initSDK(this, "1d84dc5e53923", "8b5da46179591a610494c90f0d92ecb8");
        //2.注册
        SMSSDK.registerEventHandler(eventHandler);

    }

    @OnClick({R.id.tv_user_code, R.id.login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_user_code://获取验证码
                String phoneNum = etUserPhone.getText().toString().trim();
                String passWord = etUserPsd.getText().toString().trim();

                boolean mobileNO = SMSUtil.isMobileNO(phoneNum);
                if (TextUtils.isEmpty(phoneNum)) {
                    ToastyUtils.error("请输入手机号码");
                    return;
                } else if (!mobileNO) {
                    ToastyUtils.error("手机号码错误");
                    return;
                } else if (TextUtils.isEmpty(passWord)) {
                    ToastyUtils.error("密码不能为空");
                    return;
                } else if (passWord.length() < 5) {
                    ToastyUtils.error("密码太简单,请增加长度");
                    return;
                } else {
                    tvUserCode.setEnabled(false);//不可被点击
                    //发送短信
                    SMSSDK.getVerificationCode("86", phoneNum, new OnSendMessageHandler() {
                        @Override
                        public boolean onSendMessage(String s, String s1) {
                            return false;
                        }
                    });
                    //60s发送一次
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            while (time > 0) {
                                try {
                                    Thread.sleep(998);
                                    handler.sendEmptyMessage(KEEP_TIMR);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            handler.sendEmptyMessage(RESET_TIMR);
                        }
                    }.start();
                }
                break;
            case R.id.login://登录
                phone = etUserPhone.getText().toString().trim();
                passw = etUserPsd.getText().toString().trim();
                String code = etUserCode.getText().toString().trim();
                boolean phoneNO = SMSUtil.isMobileNO(phone);
                if (TextUtils.isEmpty(phone)) {
                    ToastyUtils.error("请输入手机号码");
                    return;
                } else if (!phoneNO) {
                    ToastyUtils.error("手机号码错误");
                    return;
                } else if (TextUtils.isEmpty(passw)) {
                    ToastyUtils.error("密码不能为空");
                    return;
                } else if (passw.length() < 5) {
                    ToastyUtils.error("密码太简单,请增加长度");
                    return;
                } else if (TextUtils.isEmpty(code)) {
                    ToastyUtils.error("请输入验证码");
                } else {
                    //shareSDK 验证,在eventHandler中监听
//                    SMSSDK.submitVerificationCode("86", phone, code);
                    ToLogin();
                }

                break;

            default:
                break;

        }
    }

    @Override
    protected void onDestroy() {
        SMSSDK.unregisterAllEventHandler();
        super.onDestroy();
    }
}
