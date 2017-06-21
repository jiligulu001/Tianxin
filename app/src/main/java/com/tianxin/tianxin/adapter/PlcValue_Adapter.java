package com.tianxin.tianxin.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianxin.tianxin.R;
import com.tianxin.tianxin.bean.PlcList_Bean;

import java.util.List;

/**
 * Created by PC大佬 on 2017/4/21.
 */
public class PlcValue_Adapter extends BaseQuickAdapter<List<PlcList_Bean>, BaseViewHolder> {

    private String[] title = new String[]{"Temp", "Do", "pH", "Stir", "Feed", "Acid", "Base", "CA", "O2", "N2", "CO2"};
    private String[] unit  = new String[]{"℃", "℃", "  ", "rpm", "rpm", "rpm", "rpm", "%", "%", "%", "%"};

    public PlcValue_Adapter(int item_plclist) {
        super(item_plclist);

    }

    @Override
    protected void convert(BaseViewHolder helper, List<PlcList_Bean> item) {
        helper.setText(R.id.tv_title, title[helper.getAdapterPosition()]);
        helper.setText(R.id.tv_unit, unit[helper.getAdapterPosition()]);

    }
}
