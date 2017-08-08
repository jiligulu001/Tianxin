package com.tianxin.tianxin.activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.socks.library.KLog;
import com.tianxin.tianxin.R;
import com.tianxin.tianxin.base.BaseActivity;
import com.tianxin.tianxin.bean.Token_Bean;
import com.tianxin.tianxin.cache.SPCache;
import com.tianxin.tianxin.callback.StringDialogCallback;
import com.tianxin.tianxin.config.Constants;
import com.tianxin.tianxin.utils.ScaleHelper;
import com.vondear.rxtools.RxActivityUtils;
import com.vondear.rxtools.RxAnimationUtils;
import com.vondear.rxtools.activity.AndroidBug5497Workaround;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import android.content.Context;
public class ActivityLoginAct extends BaseActivity {
    @Bind(R.id.logo)
    ImageView      mLogo;
    @Bind(R.id.et_mobile)
    EditText       mEtMobile;
    @Bind(R.id.iv_clean_phone)
    ImageView      mIvCleanPhone;
    @Bind(R.id.et_password)
    EditText       mEtPassword;
    @Bind(R.id.clean_password)
    ImageView      mCleanPassword;
    @Bind(R.id.iv_show_pwd)
    ImageView      mIvShowPwd;
    @Bind(R.id.btn_login)
    Button         mBtnLogin;
    @Bind(R.id.regist)
    TextView       mRegist;
    @Bind(R.id.forget_password)
    TextView       mForgetPassword;
    @Bind(R.id.content)
    LinearLayout   mContent;
    @Bind(R.id.scrollView)
    ScrollView     mScrollView;
    @Bind(R.id.service)
    LinearLayout   mService;
    @Bind(R.id.root)
    RelativeLayout mRoot;

    private int   screenHeight = 0;//屏幕高度
    private int   keyHeight    = 0; //软件盘弹起后所占高度
    private float scale        = 0.6f; //logo缩放比例
    private int   height       = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_act);
        ButterKnife.bind(this);



        if (isFullScreen(this)) {
            AndroidBug5497Workaround.assistActivity(this);
        }
        boolean is_frist = SPCache.getBoolean(Constants.IS_FIRST, true);
        if (is_frist) {
            show_firstDialog();
        }
        initView();
        initEvent();
    }

    private void show_firstDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View dialog = inflater.inflate(R.layout.dialog_frise_login, (ViewGroup) findViewById(R.id.dialog_login));
        final EditText editText_ip = (EditText) dialog.findViewById(R.id.frist_dailog_ip);
        final EditText editText_port = (EditText) dialog.findViewById(R.id.frist_dailog_port);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setView(dialog);
        builder.setTitle("欢迎使用天信和生物反映器");
        builder.setMessage("首次使用请设置服务器IP");
        editText_ip.setText(SPCache.getString(Constants.IP,""));
        editText_port.setText(SPCache.getString(Constants.PORT,""));
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String ip = editText_ip.getText().toString();
                String port = editText_port.getText().toString();
                SPCache.putString(Constants.IP, ip);
                SPCache.putString(Constants.PORT, port);
                Toast.makeText(ActivityLoginAct.this, "IP信息保存成功", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }

    @Override
    public void initFitLayout(ScaleHelper scaleHelper) {
        scaleHelper.scaleView(mRoot);
    }

    private void initView() {

        screenHeight = this.getResources().getDisplayMetrics().heightPixels; //获取屏幕高度
        keyHeight = screenHeight / 3;//弹起高度为屏幕高度的1/3
    }

    private void initEvent() {
        mEtMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && mIvCleanPhone.getVisibility() == View.GONE) {
                    mIvCleanPhone.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    mIvCleanPhone.setVisibility(View.GONE);
                }
            }
        });
        mEtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && mCleanPassword.getVisibility() == View.GONE) {
                    mCleanPassword.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    mCleanPassword.setVisibility(View.GONE);
                }
                if (s.toString().isEmpty())
                    return;
                if (!s.toString().matches("[A-Za-z0-9]+")) {
                    String temp = s.toString();
                    Toast.makeText(mContext, "请输入数字或字母", Toast.LENGTH_SHORT).show();
                    s.delete(temp.length() - 1, temp.length());
                    mEtPassword.setSelection(s.length());
                }
            }
        });
        /**
         * 禁止键盘弹起的时候可以滚动
         */
        mScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        mScrollView.addOnLayoutChangeListener(new ViewGroup.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
              /* old是改变前的左上右下坐标点值，没有old的是改变后的左上右下坐标点值
              现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起*/
                if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
                    Log.e("wenzhihao", "up------>" + (oldBottom - bottom));
                    int dist = mContent.getBottom() - bottom;
                    if (dist > 0) {
                        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(mContent, "translationY", 0.0f, -dist);
                        mAnimatorTranslateY.setDuration(300);
                        mAnimatorTranslateY.setInterpolator(new LinearInterpolator());
                        mAnimatorTranslateY.start();
                        RxAnimationUtils.zoomIn(mLogo, 0.6f, dist);
                    }
                    mService.setVisibility(View.INVISIBLE);

                } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
                    Log.e("wenzhihao", "down------>" + (bottom - oldBottom));
                    if ((mContent.getBottom() - oldBottom) > 0) {
                        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(mContent, "translationY", mContent.getTranslationY(), 0);
                        mAnimatorTranslateY.setDuration(300);
                        mAnimatorTranslateY.setInterpolator(new LinearInterpolator());
                        mAnimatorTranslateY.start();
                        //键盘收回后，logo恢复原来大小，位置同样回到初始位置
                        RxAnimationUtils.zoomOut(mLogo, 0.6f);
                    }
                    mService.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public boolean isFullScreen(Activity activity) {
        return (activity.getWindow().getAttributes().flags &
                WindowManager.LayoutParams.FLAG_FULLSCREEN) == WindowManager.LayoutParams.FLAG_FULLSCREEN;
    }

    @OnClick({R.id.iv_clean_phone, R.id.clean_password, R.id.iv_show_pwd, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_clean_phone:
                mEtMobile.setText("");
                break;
            case R.id.clean_password:
                mEtPassword.setText("");
                break;
            case R.id.iv_show_pwd:
                if (mEtPassword.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    mEtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    mIvShowPwd.setImageResource(R.drawable.pass_visuable);
                } else {
                    mEtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mIvShowPwd.setImageResource(R.drawable.pass_gone);
                }
                String pwd = mEtPassword.getText().toString();
                if (!TextUtils.isEmpty(pwd))
                    mEtPassword.setSelection(pwd.length());
                break;
            case R.id.btn_login:
                String username = mEtMobile.getText().toString();
                String password = mEtPassword.getText().toString();
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(ActivityLoginAct.this, "请输入账号或者密码", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    doLogin(username, password);
                }

                break;
        }
    }

    private void doLogin(final String username, final String password) {
        HashMap<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        JSONObject jsonObject = new JSONObject(params);
        OkGo.post("http://"+SPCache.getString(Constants.IP,"127.0.0.1")+":"+SPCache.getString(Constants.PORT,"8089")+"/api-token-auth/login/")
        //OkGo.post(Constants.LOGIN)
                .tag(this)
                .upJson(jsonObject)
                .execute(new StringDialogCallback(this) {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Gson gson = new Gson();
                        Token_Bean tokenjson = gson.fromJson(s, Token_Bean.class);
                        String tokenString = tokenjson.getTokenString();



                        SPCache.putString(Constants.MYTOKEN, tokenString);

                        JPushInterface.setAlias(getApplicationContext(),username,new TagAliasCallback() {
                            @Override
                            public void gotResult(int arg0, String arg1, Set<String> arg2) {
                                Log.d(" 绑定极光", "[initJpush] 设置别名：" + "arg0：" + arg0 + "arg1:" + arg1 + ",arg2:" + arg2);
                                if (arg0 != 0) {

                                }
                            }

                        });
                        Toast.makeText(ActivityLoginAct.this, "登录成功", Toast.LENGTH_SHORT).show();
                        RxActivityUtils.skipActivityAndFinish(ActivityLoginAct.this, MainActivity.class);
                        SPCache.putBoolean(Constants.IS_FIRST,false);
                        finish();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Toast.makeText(ActivityLoginAct.this, "连接服务器失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
