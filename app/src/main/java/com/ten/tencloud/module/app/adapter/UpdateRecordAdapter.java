package com.ten.tencloud.module.app.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ten.tencloud.R;
import com.ten.tencloud.bean.UpdateRecordBean;

import java.util.List;

public class UpdateRecordAdapter extends BaseQuickAdapter<UpdateRecordBean, BaseViewHolder> {
    public UpdateRecordAdapter() {
        super(R.layout.adapter_update_record);
    }

    @Override
    protected void convert(BaseViewHolder helper, UpdateRecordBean item) {
//        helper.setText(R.id.tv_date, "");
//        helper.setText(R.id.tv_account, "");
//        helper.setText(R.id.tv_create, "");
//        helper.setText(R.id.tv_content, "");

    }
}
