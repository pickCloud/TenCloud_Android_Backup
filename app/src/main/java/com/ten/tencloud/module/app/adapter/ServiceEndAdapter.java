package com.ten.tencloud.module.app.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ten.tencloud.R;
import com.ten.tencloud.bean.K8sNodeBean;
import com.ten.tencloud.bean.ServiceBean;

import java.util.List;

public class ServiceEndAdapter extends BaseQuickAdapter<ServiceBean.SubsetBean, BaseViewHolder> {
    public ServiceEndAdapter() {
        super(R.layout.item_service_end);
    }

    @Override
    protected void convert(BaseViewHolder helper, ServiceBean.SubsetBean item) {
        StringBuffer addSb = new StringBuffer();
        for (int i = 0; i < item.getAddresses().size(); i++) {

            if (i == item.getAddresses().size() - 1) {
                addSb.append("ip:" + item.getAddresses().get(i).getIp());

            } else {
                addSb.append("ip:" + item.getAddresses().get(i).getIp()).append("\n");

            }
        }
        StringBuffer portSb = new StringBuffer();
        for (int i = 0; i < item.getPorts().size(); i++) {

            if (i == item.getPorts().size() - 1) {
                portSb.append("name:" + item.getPorts().get(i).getName() + ",port:" + item.getPorts().get(i).getPort());

            } else {
                portSb.append("name:" + item.getPorts().get(i).getName() + ",port:" + item.getPorts().get(i).getPort()).append("\n");

            }
        }

        helper.setText(R.id.tv_addr, addSb.toString());
        helper.setText(R.id.tv_port, portSb.toString());

    }
}
