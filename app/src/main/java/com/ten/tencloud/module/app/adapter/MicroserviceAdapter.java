package com.ten.tencloud.module.app.adapter;

import com.blankj.utilcode.util.ObjectUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ten.tencloud.R;
import com.ten.tencloud.bean.ServiceBean;

import java.util.Map;

public class MicroserviceAdapter extends BaseQuickAdapter<ServiceBean, BaseViewHolder> {
    public MicroserviceAdapter() {
        super(R.layout.adapter_microservice);
    }

    @Override
    protected void convert(BaseViewHolder helper, ServiceBean item) {
        helper.setText(R.id.tv_name, item.getName());

        if (item.getSource() == 1)
            helper.setText(R.id.tv_service_sourcing, "内部服务，通过标签选择");
        else if (item.getSource() == 2)
            helper.setText(R.id.tv_service_sourcing, "外部服务，通过IP映射");
        else if (item.getSource() == 3)
            helper.setText(R.id.tv_service_sourcing, "外部服务，通过别名映射");

        if (item.getState() == 1) {
            helper.setText(R.id.tv_status, "未知");

        } else if (item.getState() == 2) {
            helper.setText(R.id.tv_status, "成功");

        } else if (item.getState() == 3) {
            helper.setText(R.id.tv_status, "失败");

        }

        if (!ObjectUtils.isEmpty(item.getType())) {
            if (Integer.valueOf(item.getType()) == 1)
                helper.setText(R.id.tv_exposure_chamber, "集群内访问");
            else if (Integer.valueOf(item.getType()) == 2)
                helper.setText(R.id.tv_exposure_chamber, "集群内外部可访问");
            else if (Integer.valueOf(item.getType()) == 3)
                helper.setText(R.id.tv_exposure_chamber, "负载均衡器");

        }

    }
}
