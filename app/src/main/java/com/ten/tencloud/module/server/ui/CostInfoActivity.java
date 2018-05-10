package com.ten.tencloud.module.server.ui;

import android.os.Bundle;
import android.view.View;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.widget.dialog.BillingRoleDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class CostInfoActivity extends BaseActivity {

    @BindView(R.id.ll_cost_1)
    View mLlCost1;
    @BindView(R.id.ll_cost_2)
    View mLlCost2;

    private int mType;
    private BillingRoleDialog mBillingRoleDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_cost_info);
        initTitleBar(true, "费用信息");
        mType = getIntent().getIntExtra("type", 0);
        initView();
        initData();
    }

    private void initView() {
        mBillingRoleDialog = new BillingRoleDialog(this);
        mLlCost1.setVisibility(mType == 0 ? View.VISIBLE : View.GONE);
        mLlCost2.setVisibility(mType != 0 ? View.VISIBLE : View.GONE);
    }

    private void initData() {

    }

    @OnClick({R.id.tv_billing_role})
    public void onClick(View view) {
        switch (view.getId()) {
            //计费规则
            case R.id.tv_billing_role:
                mBillingRoleDialog.show();
                break;
        }
    }
}
