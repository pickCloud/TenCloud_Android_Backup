package com.ten.tencloud.module.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.PathBean;
import com.ten.tencloud.bean.RulesBean;
import com.ten.tencloud.bean.ServiceBean;
import com.ten.tencloud.constants.IntentKey;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Ingress详情
 */
public class IngressDetailsActivity extends BaseActivity {

    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_ip)
    TextView mTvIp;
    @BindView(R.id.tv_service)
    TextView mTvService;
    @BindView(R.id.tv_sub_app)
    TextView mTvSubApp;
    @BindView(R.id.rl_kzq)
    RelativeLayout mRlKzq;
    @BindView(R.id.tv_kzq_service)
    TextView mTvKzqService;
    @BindView(R.id.tv_kzq_sub_app)
    TextView mTvKzqSubApp;
    @BindView(R.id.tv_access_rule)
    TextView mTvAccessRule;
    @BindView(R.id.cv_kzq)
    CardView mCvKzq;

    private int mAppid;
    private ServiceBean serviceBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_ingress_details);
        initTitleBar(true, "Ingress详情");

        mAppid = getIntent().getIntExtra(IntentKey.APP_ID, 0);

        serviceBean = (ServiceBean) getIntent().getSerializableExtra(IntentKey.INGRESS_INFO);
        if (serviceBean != null){
//            mAppid = serviceBean.getApp_id();
            mTvName.setText(serviceBean.getName());
            mTvIp.setText("IP地址" + serviceBean.getIp());

            if (!ObjectUtils.isEmpty(serviceBean.getRules())) {
                StringBuffer stringBuffer = new StringBuffer();

                for (int i = 0; i < serviceBean.getRules().size(); i++) {

                    RulesBean rulesBean = serviceBean.getRules().get(i);
                    stringBuffer.append(rulesBean.host).append("\n");
                    for (PathBean pathBean : rulesBean.paths) {
                        stringBuffer.append("   " + pathBean.path + " => ")
                                .append("" + pathBean.serviceName + ":")
                                .append("" + pathBean.servicePort + "");
                    }

                }
                mTvAccessRule.setText(stringBuffer.toString());

            }

            mTvService.setText((String) serviceBean.getBackend().get("serviceName"));
            mTvSubApp.setText((String) serviceBean.getBackend().get("app_name"));
            mTvKzqService.setText((String) serviceBean.getController().get("serviceName"));
            mTvKzqSubApp.setText((String) serviceBean.getController().get("app_name"));

        }


    }

    @OnClick(R.id.tv_rule_setting)
    public void onViewClicked() {
        Intent intent = new Intent(this, IngressModificationRuleActivity.class);
        intent.putExtra(IntentKey.APP_ID, mAppid);
        intent.putExtra(IntentKey.INGRESS_INFO, serviceBean);
        startActivity(intent);

    }
}
