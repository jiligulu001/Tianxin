package com.tianxin.tianxin.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.tianxin.tianxin.R;
import com.tianxin.tianxin.adapter.Message_Adapter;
import com.tianxin.tianxin.bean.Msg_Bean;
import com.tianxin.tianxin.callback.StringDialogCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class MessageActivity extends AppCompatActivity {

    @Bind(R.id.error_list)
    RecyclerView mRecyleView;
    @Bind(R.id.msg_toolbar)
    Toolbar      mMsgToolbar;

    private Message_Adapter msg_Adapter = new Message_Adapter(R.layout.item_msg_list);
    private List<Msg_Bean> mMsgList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        initToolbar();
        initView();
        initData();
        initEvent();
    }

    private void initToolbar() {
        mMsgToolbar = (Toolbar) findViewById(R.id.msg_toolbar);
        mMsgToolbar.setTitle("报警信息");
        mMsgToolbar.setOverflowIcon(getResources().getDrawable(R.mipmap.ic_back));
        setSupportActionBar(mMsgToolbar);
        mMsgToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView() {
        mRecyleView.setLayoutManager(new LinearLayoutManager(this));
        mRecyleView.setAdapter(msg_Adapter);
    }

    private void initData() {
        /**
         * 在这里写数据请求
         */
        OkGo.post("mmmmmm")
                .upJson("udhfvd")
                .execute(new StringDialogCallback(this) {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Gson gson = new Gson();
                        mMsgList = gson.fromJson(s, new TypeToken<List<Msg_Bean>>() {
                        }.getType());
                        msg_Adapter.setNewData(mMsgList);
                    }
                });
    }

    private void initEvent() {

    }
}


