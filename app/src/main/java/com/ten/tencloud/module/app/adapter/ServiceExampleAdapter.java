package com.ten.tencloud.module.app.adapter;

import android.support.annotation.Nullable;

import com.blankj.utilcode.util.ObjectUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ten.tencloud.R;
import com.ten.tencloud.bean.ExampleBean;
import com.ten.tencloud.bean.ServerBean;

import java.util.List;
import java.util.Map;

public class ServiceExampleAdapter extends BaseQuickAdapter<ExampleBean, BaseViewHolder> {
    public ServiceExampleAdapter() {
        super(R.layout.adapter_service_example);
    }

    @Override
    protected void convert(BaseViewHolder helper, ExampleBean item) {

        helper.setText(R.id.tv_name, item.name);
        helper.setText(R.id.tv_count, item.pod_num);

        StringBuffer stringBuffer = new StringBuffer();
        if (!ObjectUtils.isEmpty(item.labels)){
            for (Map<String, String> label:item.labels){
                for (Map.Entry<String, String> entry : label.entrySet()) {
                    stringBuffer.append(entry.getKey() + "=").append(entry.getValue()).append("\n");
                }
            }
        }
        helper.setText(R.id.tv_tag, stringBuffer.toString());

    }
}
