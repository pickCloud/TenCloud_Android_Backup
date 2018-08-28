package com.ten.tencloud.module.app.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ten.tencloud.R;
import com.ten.tencloud.bean.K8sNodeBean;
import com.ten.tencloud.bean.ServiceBean;

import java.util.List;
import java.util.Map;

public class ServiceEndAdapter extends BaseQuickAdapter<Map<String, List<Map<String, Object>>>, BaseViewHolder> {
    public ServiceEndAdapter() {
        super(R.layout.item_service_end);
    }

    @Override
    protected void convert(BaseViewHolder helper, Map<String, List<Map<String, Object>>> item) {
        StringBuffer addSb = new StringBuffer();
        for (int i = 0; i < item.get("addresses").size(); i++) {

            if (i == item.get("addresses").size() - 1) {
                addSb.append("ip:" + item.get("addresses").get(i).get("ip"));

            } else {
                addSb.append("ip:" + item.get("addresses").get(i).get("ip")).append("\n");

            }
        }
        StringBuffer portSb = new StringBuffer();
        for (int i = 0; i < item.get("ports").size(); i++) {

            if (i == item.get("ports").size() - 1) {
                portSb.append("name:" + item.get("ports").get(i).get("name") + ", port:" + (int)(double)item.get("ports").get(i).get("port"));

            } else {
                portSb.append("name:" + item.get("ports").get(i).get("name") + ", port:" + (int)(double)item.get("ports").get(i).get("port")).append("\n");

            }
        }

        helper.setText(R.id.tv_addr, addSb.toString());
        helper.setText(R.id.tv_port, portSb.toString());

    }
}
