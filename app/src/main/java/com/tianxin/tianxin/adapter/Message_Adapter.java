package com.tianxin.tianxin.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianxin.tianxin.R;
import com.tianxin.tianxin.bean.Msg_Bean;

/**
 * Created by PC大佬 on 2017/8/29.
 */

public class Message_Adapter extends BaseQuickAdapter<Msg_Bean, BaseViewHolder> {
    public Message_Adapter(int layout) {
        super(layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, Msg_Bean item) {
        helper.setText(R.id.tv_name,item.getName());
        helper.setText(R.id.tv_desc,item.getContent());
        helper.setText(R.id.tv_time,item.getCurrentDate());
    }
}
