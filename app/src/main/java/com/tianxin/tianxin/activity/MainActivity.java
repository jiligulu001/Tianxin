package com.tianxin.tianxin.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.socks.library.KLog;
import com.tianxin.tianxin.R;
import com.tianxin.tianxin.adapter.PlcList_Adapter;
import com.tianxin.tianxin.adapter.PlcValue_Adapter;
import com.tianxin.tianxin.bean.INfo_Bean;
import com.tianxin.tianxin.bean.PlcList_Bean;
import com.tianxin.tianxin.cache.SPCache;
import com.tianxin.tianxin.callback.StringDialogCallback;
import com.tianxin.tianxin.config.Constants;
import com.tianxin.tianxin.view.WheelView;
import com.vondear.rxtools.RxActivityUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.tv_home_time)
    TextView           mTvHomeTime;
    @Bind(R.id.tv_home_temp)
    TextView           mTvHomeTemp;
    @Bind(R.id.tv_home_do)
    TextView           mTvHomeDo;
    @Bind(R.id.tv_home_ph)
    TextView           mTvHomePh;
    @Bind(R.id.tv_home_o2)
    TextView           mTvHomeO2;
    @Bind(R.id.tv_home_stir)
    TextView           mTvHomeStir;
    @Bind(R.id.tv_home_feed)
    TextView           mTvHomeFeed;
    @Bind(R.id.tv_home_acid)
    TextView           mTvHomeAcid;
    @Bind(R.id.tv_home_base)
    TextView           mTvHomeBase;
    @Bind(R.id.tv_home_ca)
    TextView           mTvHomeCa;
    @Bind(R.id.tv_home_n2)
    TextView           mTvHomeN2;
    @Bind(R.id.tv_home_co2)
    TextView           mTvHomeCo2;
    @Bind(R.id.tv_home_current_time)
    TextView           mTvHomeCurrentTime;
    @Bind(R.id.home_refresh)
    SwipeRefreshLayout mHomeRefresh;
    private RecyclerView        mPlc_list;
    private LayoutInflater      mInflater;
    private View                mDialog;
    private PlcList_Adapter     mPlcList_adapter;
    private PlcValue_Adapter    mPlcValue_adapter;
    private AlertDialog.Builder mBuilder;
    private WheelView           mWva;
    private List<INfo_Bean>     mPlcList;
    private List<PlcList_Bean>  mPlcNameList;
    private List<String> mList = new ArrayList<>();
    private Handler handler;
    private String  mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("实时数据");
        //toolbar.setOverflowIcon(getResources().getDrawable(R.mipmap.ic_del));
        setSupportActionBar(toolbar);

        //        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //        fab.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View view) {
        //                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //                        .setAction("Action", null).show();
        //            }
        //        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mWva = (WheelView) findViewById(R.id.main_wv);

        //改变加载显示的颜色
        mHomeRefresh.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE);
        //设置初始时的大小
        mHomeRefresh.setSize(SwipeRefreshLayout.LARGE);
        //设置监听
        mHomeRefresh.setOnRefreshListener(this);
        //设置向下拉多少出现刷新
        mHomeRefresh.setDistanceToTriggerSync(100);
        //设置刷新出现的位置
        mHomeRefresh.setProgressViewEndTarget(false, 200);
        mPlcValue_adapter = new PlcValue_Adapter(R.layout.item_main_list);


        initData();
    }

    private void initData() {
        //获取设备列表
        getPlcList();
        //        mWva.setOffset(1);
        //        mWva.setItems(mList);
        //        mWva.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
        //            @Override
        //            public void onSelected(int selectedIndex, String item) {
        //                KLog.d( "selectedIndex: " + selectedIndex + ", item: " + item);
        //            }
        //        });
    }

    private void getPlcList() {
        KLog.d("http://" + SPCache.getString(Constants.IP, "127.0.0.1") + ":" + SPCache.getString(Constants.PORT, "8089"));
        OkGo.get("http://" + SPCache.getString(Constants.IP, "127.0.0.1") + ":" + SPCache.getString(Constants.PORT, "8089") + "/test/")
                .tag(this)
                .execute(new StringDialogCallback(this) {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Gson gson = new Gson();
                        mPlcList = gson.fromJson(s, new TypeToken<List<INfo_Bean>>() {
                        }.getType());
                        mUrl = mPlcList.get(0).getDeviceName();
                        display_home(mPlcList.get(0));
                        for (int i = 0; i < mPlcList.size(); i++) {
                            mList.add(mPlcList.get(i).getDeviceName());
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Toast.makeText(MainActivity.this, "链接服务器失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle action bar item clicks here. The action bar will
        //automatically handle clicks on the Home/Up button, so long
        //as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_scan:
                //Toast.makeText(MainActivity.this, "点击了", Toast.LENGTH_SHORT).show();
                show_editDialog();
                break;
            default:
                break;
        }
        return true;
    }

    private void show_editDialog() {
        View outerView = LayoutInflater.from(this).inflate(R.layout.wheel_view, null);
        final WheelView wv = (WheelView) outerView.findViewById(R.id.wheel_view_wv);
        wv.setOffset(2);
        wv.setItems(mList);
        wv.setSeletion(0);
        wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                //KLog.d(selectedIndex + "=========" + item);
            }
        });

        new AlertDialog.Builder(this)
                .setTitle("PLC列表")
                .setView(outerView)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Toast.makeText(MainActivity.this, wv.getSeletedItem(), Toast.LENGTH_SHORT).show();
                        mUrl = wv.getSeletedItem();
                        getPlcInfo();
                    }
                })
                .show();

    }

    private void getPlcInfo() {
        //OkGo.get(Constants.PLCINFO + mUrl + "/")
        OkGo.get("http://" + SPCache.getString(Constants.IP, "127.0.0.1") + ":" + SPCache.getString(Constants.PORT, "8089") + "/test/" + mUrl + "/")
                .tag(this)
                .execute(new StringDialogCallback(this) {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Gson gson = new Gson();

                        INfo_Bean bean = gson.fromJson(s, INfo_Bean.class);

                        display_home(bean);

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Toast.makeText(MainActivity.this, "链接服务器失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void display_home(INfo_Bean bean) {
        mTvHomeTime.setText("PLC设备编号：" + mUrl);
        mTvHomeTemp.setText(bean.getTemp() + " ℃");
        mTvHomeDo.setText(bean.getDO() + " %");
        mTvHomePh.setText(bean.getPH() + " ");
        mTvHomeStir.setText(bean.getStir() + " rpm");
        mTvHomeFeed.setText(bean.getFeed() + " rpm");
        mTvHomeAcid.setText(bean.getAcid() + " rpm");
        mTvHomeBase.setText(bean.getBase() + " rpm");
        mTvHomeCa.setText(bean.getCa() + " %");
        mTvHomeO2.setText(bean.getO2() + " %");
        mTvHomeN2.setText(bean.getN2() + " %");
        mTvHomeCo2.setText(bean.getCo2() + " %");
        mTvHomeCurrentTime.setText("最后刷新时间" + bean.getCurrentDate());
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_camera:
                Toast.makeText(MainActivity.this, "了解天信和反应器", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_gallery:
                Toast.makeText(MainActivity.this, "我的信息", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_slideshow:
                Toast.makeText(MainActivity.this, "报警信息", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_manage:
                Toast.makeText(MainActivity.this, "设置", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_send:
                //startActivity(new Intent(MainActivity.this, HomeActivity.class));
                Bundle bundle = new Bundle();
                bundle.putString("deviceName",mUrl);
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.nav_share:
                show_outofAPP();
                break;
            //case R.id.nav_send:
            //    Toast.makeText(MainActivity.this, "点击了了解", Toast.LENGTH_SHORT).show();
            //    break;
            default:
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void show_outofAPP() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("提示");
        builder.setMessage("是否要切换账号？");
        builder.setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SPCache.clear();
                RxActivityUtils.skipActivityAndFinish(MainActivity.this, ActivityLoginAct.class);
            }
        });
        builder.show();

    }

    @Override
    public void onRefresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //然刷新控件停留两秒后消失
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getPlcInfo();
                        mHomeRefresh.setRefreshing(false);
                    }
                });

            }
        }).start();
    }
}
