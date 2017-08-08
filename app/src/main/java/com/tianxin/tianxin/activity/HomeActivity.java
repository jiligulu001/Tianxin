package com.tianxin.tianxin.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;

import com.google.gson.Gson;
import com.kyleduo.switchbutton.SwitchButton;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.socks.library.KLog;
import com.tianxin.tianxin.R;
import com.tianxin.tianxin.bean.PlcList_Bean;
import com.tianxin.tianxin.cache.SPCache;
import com.tianxin.tianxin.callback.StringDialogCallback;
import com.tianxin.tianxin.config.Constants;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class HomeActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar      mToolbar;
    @Bind(R.id.sb_temp)
    SwitchButton mSbTemp;
    @Bind(R.id.et_temp)
    EditText     mEtTemp;
    @Bind(R.id.sb_do)
    SwitchButton mSbDo;
    @Bind(R.id.et_do)
    EditText mEtDo;
    @Bind(R.id.sb_ph)
    SwitchButton mSbPh;
    @Bind(R.id.et_ph)
    EditText mEtPh;
    @Bind(R.id.sb_stir)
    SwitchButton mSbStir;
    @Bind(R.id.et_stir)
    EditText mEtStir;
    @Bind(R.id.sb_feed)
    SwitchButton mSbFeed;
    @Bind(R.id.et_feed)
    EditText mEtFeed;
    @Bind(R.id.sb_acid)
    SwitchButton mSbAcid;
    @Bind(R.id.et_acid)
    EditText mEtAcid;

    @Bind(R.id.container)
    LinearLayout mContainer;
    private String  mUrl;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        initView();
        initData();
        initEvent();
        getPlcList();

    }

    private void initView() {
        mToolbar.setTitle("设定值");
        mToolbar.setOverflowIcon(getResources().getDrawable(R.mipmap.ic_back));
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initData() {
        Bundle bundle = this.getIntent().getExtras();
        mUrl = bundle.getString("deviceName");
    }

    private void initEvent() {
        /**
         * 对开关状态的监听
         */
        mSbTemp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //打印开关当前的状态
                if (isChecked)
                {

                }

                Toast.makeText(HomeActivity.this, mSbTemp.isChecked() + " ", Toast.LENGTH_SHORT).show();
                KLog.d(mSbTemp.isChecked());
            }
        });

        /**
         * 对更改的textEdit 监听
         */

    }

    private void createAlertDiag(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle("提示：");
        builder.setMessage("无权限进行此操作！");
        builder.setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //SPCache.clear();
            }
        });
//        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
        builder.show();
    }

    private void getPlcList() {

        String token = SPCache.getString(Constants.MYTOKEN,"aa");
        HttpHeaders mCommonHeaders = new HttpHeaders();
        mCommonHeaders.put("Authorization","JWT "+token);
        OkGo.get("http://" + SPCache.getString(Constants.IP, "127.0.0.1") + ":" + SPCache.getString(Constants.PORT, "8089") + "/testSetting/" + mUrl + "/")
                .headers(mCommonHeaders)
                .tag(this)
                .execute(new StringDialogCallback(this) {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {

                        Gson gson = new Gson();
                        PlcList_Bean mPlcList = gson.fromJson(s, PlcList_Bean.class);
                        if(mPlcList.getTempSwitch().equals(" "))
                        {
                            KLog.d("&&&&&&&&&&&&&&&&&&&&&&&");
                            createAlertDiag();
                        }
                        else
                        {
                            display_home(mPlcList);
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Toast.makeText(HomeActivity.this, "链接服务器失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void display_home(PlcList_Bean bean) {
        mEtTemp.setText(bean.getTemp()+"");
        mSbTemp.setChecked(bean.getTempSwitch().equals("1"));

        mEtDo.setText(bean.getdO()+"");
        mSbDo.setChecked(bean.getDoSwitch().equals("1"));

        mEtPh.setText(bean.getpH()+"");
        mSbPh.setChecked(bean.getPhSwitch().equals("1"));

        mEtStir.setText(bean.getStir()+"");
        mSbStir.setChecked(bean.getStirSwitch().equals("1"));

        mEtFeed.setText(bean.getFeed() + "");
        mSbFeed.setChecked(bean.getFeedSwitch().equals("1"));

        mEtAcid.setText(bean.getAcid() + "");
        mSbFeed.setChecked(bean.getAcidSwitch().equals("1"));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.setting_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle action bar item clicks here. The action bar will
        //automatically handle clicks on the Home/Up button, so long
        //as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.setting_sure:
                Toast.makeText(HomeActivity.this, "点击了确定", Toast.LENGTH_SHORT).show();
                DoPostData();
                break;
            default:
                break;
        }
        return true;
    }

    private void DoPostData() {
        HashMap<String, Float> valueParams = new HashMap<>();
        valueParams.put("temp", Float.valueOf(mEtTemp.getText().toString()));
        valueParams.put("dO", Float.valueOf(mEtDo.getText().toString()));
        valueParams.put("pH", Float.valueOf(mEtPh.getText().toString()));
        valueParams.put("stir", Float.valueOf(mEtStir.getText().toString()));
        valueParams.put("feed", Float.valueOf(mEtFeed.getText().toString()));
        valueParams.put("acid", Float.valueOf(mEtAcid.getText().toString()));
        HashMap<String, String> switchParams = new HashMap<>();
        switchParams.put("tempSwitch", (mSbTemp.isChecked()?"1":"0"));
        switchParams.put("doSwitch", (mSbDo.isChecked()?"1":"0"));
        switchParams.put("phSwitch", (mSbPh.isChecked()?"1":"0"));
        switchParams.put("stirSwitch", (mSbStir.isChecked()?"1":"0"));
        switchParams.put("feedSwitch", (mSbFeed.isChecked()?"1":"0"));
        switchParams.put("acidSwitch", (mSbAcid.isChecked()?"1":"0"));

        JSONObject jsonObject1 = new JSONObject(valueParams);
        JSONObject jsonObject2 = new JSONObject(switchParams);


        String token = SPCache.getString(Constants.MYTOKEN,"aa");
        HttpHeaders mCommonHeaders = new HttpHeaders();
        mCommonHeaders.put("Authorization","JWT "+token);
        OkGo.post("http://"+SPCache.getString(Constants.IP,"127.0.0.1")+":"+SPCache.getString(Constants.PORT,"8089")+"/testSetting/"+mUrl+"/")
                .headers(mCommonHeaders)
                //OkGo.post(Constants.LOGIN)
                .tag(this)
                .upJson(jsonObject1)
                .execute(new StringDialogCallback(this) {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Toast.makeText(HomeActivity.this, "数值修改成功", Toast.LENGTH_SHORT).show();
                       // RxActivityUtils.skipActivityAndFinish(HomeActivity.this, MainActivity.class);
                       // finish();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Toast.makeText(HomeActivity.this, "数值修改失败", Toast.LENGTH_SHORT).show();
                    }
                });
        OkGo.post("http://"+SPCache.getString(Constants.IP,"127.0.0.1")+":"+SPCache.getString(Constants.PORT,"8089")+"/testSetting/"+mUrl+"/")
                .headers(mCommonHeaders)
                //OkGo.post(Constants.LOGIN)
                .tag(this)
                .upJson(jsonObject2)
                .execute(new StringDialogCallback(this) {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Toast.makeText(HomeActivity.this, "开关修改成功", Toast.LENGTH_SHORT).show();
                        // RxActivityUtils.skipActivityAndFinish(HomeActivity.this, MainActivity.class);
                        // finish();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Toast.makeText(HomeActivity.this, "开关修改失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    }

