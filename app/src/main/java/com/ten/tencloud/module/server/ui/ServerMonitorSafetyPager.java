package com.ten.tencloud.module.server.ui;

import android.content.Context;
import android.view.View;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BasePager;

import butterknife.BindView;

/**
 * Created by lxq on 2017/11/29.
 */

public class ServerMonitorSafetyPager extends BasePager {

    @BindView(R.id.empty_view)
    View mEmptyView;

    private boolean isFirst = true;

    public ServerMonitorSafetyPager(Context context) {
        super(context);
    }


    @Override
    public void init() {
        if (isFirst) {
            createView(R.layout.pager_server_monitor_safety);
            initView();
        }
    }

    private void initView() {
        mEmptyView.setVisibility(VISIBLE);
    }

}
