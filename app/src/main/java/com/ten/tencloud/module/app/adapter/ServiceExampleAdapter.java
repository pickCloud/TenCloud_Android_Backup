package com.ten.tencloud.module.app.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ten.tencloud.R;
import com.ten.tencloud.bean.ServerBean;

import java.util.List;

public class ServiceExampleAdapter extends BaseQuickAdapter<ServerBean, BaseViewHolder> {
    public ServiceExampleAdapter() {
        super(R.layout.adapter_service_example);
    }

    @Override
    protected void convert(BaseViewHolder helper, ServerBean item) {

        helper.setText(R.id.tv_name, item.getAddress());
        helper.setText(R.id.tv_count, item.getAddress());
        helper.setText(R.id.tv_tag, item.getAddress());

    }
}
