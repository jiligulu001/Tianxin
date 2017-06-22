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

import com.kyleduo.switchbutton.SwitchButton;
import com.socks.library.KLog;
import com.tianxin.tianxin.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar      mToolbar;
    @Bind(R.id.sb_temp)
    SwitchButton mSbTemp;
    @Bind(R.id.et_temp)
    EditText     mEtTemp;
    @Bind(R.id.container)
    LinearLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        initView();
        initData();
        initEvent();

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
        mSbTemp.isChecked();
    }

    private void initEvent() {
        /**
         * 对开关状态的监听
         */
        mSbTemp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //打印开光当前的装状态
                Toast.makeText(HomeActivity.this, mSbTemp.isChecked() + " ", Toast.LENGTH_SHORT).show();
                KLog.d(mSbTemp.isChecked());
            }
        });
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
                DoGetData();
                break;
            default:
                break;
        }
        return true;
    }

    private void DoGetData() {
        //根据开关获取到的状态来判断是否需要上传改选项的设定的值
        if (mSbTemp.isChecked()) {
            //打印设定值
            KLog.d(mEtTemp.getText());
        }
    }
}
