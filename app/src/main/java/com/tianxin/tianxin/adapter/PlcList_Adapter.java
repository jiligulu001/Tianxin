package com.tianxin.tianxin.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianxin.tianxin.R;
import com.tianxin.tianxin.bean.PlcList_Bean;

import java.util.List;

/**
 * Created by PC大佬 on 2017/4/21.
 */
public class PlcList_Adapter extends BaseQuickAdapter<List<PlcList_Bean>, BaseViewHolder> {

    public PlcList_Adapter(int item_plclist, List<PlcList_Bean> list) {
        super(item_plclist);

    }

    @Override
    protected void convert(BaseViewHolder helper, List<PlcList_Bean> item) {

        helper.setText(R.id.plclist_no, item.get(helper.getLayoutPosition()).getDevice_name());
    }
}
