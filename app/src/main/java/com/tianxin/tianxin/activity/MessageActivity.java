package com.tianxin.tianxin.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.tianxin.tianxin.R;

import butterknife.Bind;

public class MessageActivity extends AppCompatActivity {

    @Bind(R.id.error_list)
    RecyclerView mRecyleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.msg_toolbar);
        toolbar.setTitle("报警信息");
        toolbar.setOverflowIcon(getResources().getDrawable(R.mipmap.ic_back));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}


