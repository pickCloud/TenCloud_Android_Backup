package com.ten.tencloud.module.app.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ten.tencloud.R;
import com.ten.tencloud.bean.MicroserviceBean;

import java.util.List;

public class MicroserviceAdapter extends BaseQuickAdapter<MicroserviceBean, BaseViewHolder> {
    public MicroserviceAdapter() {
        super(R.layout.adapter_microservice);
    }

    @Override
    protected void convert(BaseViewHolder helper, MicroserviceBean item) {
//        helper.setText(R.id.tv_name, item.);
//        helper.setText(R.id.tv_expect_count, item.);
//        helper.setText(R.id.tv_current_count, item.);

    }
}
