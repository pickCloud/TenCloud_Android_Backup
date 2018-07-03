package com.ten.tencloud.module.app.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.NestedScrollView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.widget.blur.BlurBuilder;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 服务详情
 */
public class AppServiceDetailsActivity extends BaseActivity {

    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_id)
    TextView mTvId;
    @BindView(R.id.tv_date)
    TextView mTvDate;
    @BindView(R.id.rl_basic_detail)
    ConstraintLayout mRlBasicDetail;
    @BindView(R.id.tv_service_sourcing)
    TextView mTvServiceSourcing;
    @BindView(R.id.tv_service_tag)
    TextView mTvServiceTag;
    @BindView(R.id.tv_exposure_chamber)
    TextView mTvExposureChamber;
    @BindView(R.id.tv_status)
    TextView mTvStatus;
    @BindView(R.id.tv_status_desc)
    TextView mTvStatusDesc;
    @BindView(R.id.tv_service_access)
    TextView mTvServiceAccess;
    @BindView(R.id.tv_service_desc)
    TextView mTvServiceDesc;
    @BindView(R.id.tv_deployment)
    TextView mTvDeployment;
    @BindView(R.id.scroll_view)
    NestedScrollView mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_app_service_details);
        initTitleBar(true, "服务详情");


        StatusDialog mStatusDialog = new StatusDialog(mContext);
        mStatusDialog.show();

    }

    public class StatusDialog extends Dialog {

        public StatusDialog(@NonNull Context context) {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉头
            Window window = getWindow();
            window.setGravity(Gravity.CENTER);
            window.setContentView(R.layout.dialog_service_rule);

            window.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, IngressModificationRuleActivity.class);
                    startActivity(intent);
                    cancel();
                }
            });

            window.setBackgroundDrawable(new BitmapDrawable());
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            setCancelable(false);
            setCanceledOnTouchOutside(false);
        }
    }

    @OnClick({R.id.tv_refresh, R.id.btn_toolbox})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_refresh:
                break;
            case R.id.btn_toolbox:
                BlurBuilder.snapShotWithoutStatusBar(this);
                Intent intent = new Intent(this, ServiceToolBoxActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                break;
        }
    }
}
