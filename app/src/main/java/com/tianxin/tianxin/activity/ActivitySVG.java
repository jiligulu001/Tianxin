package com.tianxin.tianxin.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jrummyapps.android.widget.AnimatedSvgView;
import com.tianxin.tianxin.R;
import com.tianxin.tianxin.base.BaseActivity;
import com.tianxin.tianxin.bean.SVG;
import com.tianxin.tianxin.cache.SPCache;
import com.tianxin.tianxin.config.Constants;
import com.tianxin.tianxin.utils.ScaleHelper;
import com.vondear.rxtools.RxActivityUtils;
import com.vondear.rxtools.RxBarUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ActivitySVG extends BaseActivity {


    @Bind(R.id.imageView2)
    ImageView       mImageView2;
    @Bind(R.id.animated_svg_view)
    AnimatedSvgView mSvgView;
    @Bind(R.id.app_name)
    ImageView       mAppName;
    @Bind(R.id.activity_svg)
    RelativeLayout  mActivitySvg;
    @Bind(R.id.iv_icon)
    ImageView       mIvIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxBarUtils.hideStatusBar(this);
        setContentView(R.layout.activity_svg);
        ButterKnife.bind(this);
        setSvg(SVG.values()[3]);
        CheckUpdate();
    }

    @Override
    public void initFitLayout(ScaleHelper scaleHelper) {
        scaleHelper.scaleView(mActivitySvg);
    }

    private void setSvg(SVG svg) {
        AnimationSet animationSet = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.5f, 1, 0.5f, 1,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(2500);
        animationSet.addAnimation(scaleAnimation);
        ActivitySVG.this.mIvIcon.startAnimation(animationSet);

    }

    /**
     * 检查是否有新版本，如果有就升级
     */
    private void CheckUpdate() {
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(500);
                    Message msg = checkhandler.obtainMessage();
                    checkhandler.sendMessage(msg);
                    Thread.sleep(2000);
                    toMain();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private Handler checkhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

        }
    };

    public void toMain() {
        if (SPCache.getBoolean(Constants.IS_FIRST, true)) {
            RxActivityUtils.skipActivityAndFinish(this, ActivityLoginAct.class);
        } else {
            RxActivityUtils.skipActivityAndFinish(this, MainActivity.class);
        }

    }
}
