package com.ten.tencloud.module.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.AppBean;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.constants.IntentKey;
import com.ten.tencloud.even.FinishActivityEven;
import com.ten.tencloud.module.app.contract.AppK8sDeployContract;
import com.ten.tencloud.module.app.presenter.AppK8sDeployPresenter;
import com.ten.tencloud.module.other.contract.SplashContract;
import com.ten.tencloud.module.server.ui.ServerClusterListActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;

import butterknife.BindView;

public class AppK8sRegularDeployStep1Activity extends BaseActivity implements AppK8sDeployContract.View {

    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.tv_cluster)
    TextView mTvCluster;

    @Override
    protected boolean isBindEventBus() {
        return true;
    }

    private AppBean mAppBean;
    private AppK8sDeployPresenter mPresenter;
    private String mName;
    public static final int REQUEST_CODE_SELECT_NODE = 1000;
    private int mMasterServerId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_app_k8s_regular_deploy_step1);
        initTitleBar(true, "常规部署", "下一步", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });

        mAppBean = getIntent().getParcelableExtra(IntentKey.APP_ITEM);
        mPresenter = new AppK8sDeployPresenter();
        mPresenter.attachView(this);

        findViewById(R.id.ll_select_node).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ServerClusterListActivity.class);
                intent.putExtra("select", true);
                startActivityForResult(intent, REQUEST_CODE_SELECT_NODE);
            }
        });

        if (!ObjectUtils.isEmpty(mAppBean.getName())){
            int anInt = SPUtils.getInstance().getInt(Constants.CODE_NO, 1);
            SPUtils.getInstance().put(Constants.CODE_NO, (anInt+1));
            mEtName.setText(mAppBean.getName() + "-" + TimeUtils.getNowString(new SimpleDateFormat("yyyyMMdd")) + "-" + anInt);
        }
    }

    private void next() {
        mName = mEtName.getText().toString().trim().toLowerCase();
        if (TextUtils.isEmpty(mName)) {
            showMessage("名称不能为空");
            return;
        }
        if (mMasterServerId == -1) {
            showMessage("请选择集群");
            return;
        }

        mPresenter.checkDeployName(mName, mAppBean.getId());
    }

    @Override
    public void checkResult() {
        Intent intent = new Intent(mContext, AppK8sRegularDeployStep2Activity.class);
        intent.putExtra("name", mName);
        intent.putExtra("appBean", mAppBean);
        intent.putExtra("masterServerId", mMasterServerId);
        startActivity(intent);
    }

    @Override
    public void showYAML(String yaml) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Constants.ACTIVITY_RESULT_CODE_FINISH) {
            if (requestCode == REQUEST_CODE_SELECT_NODE) {
                mMasterServerId = data.getIntExtra("master_server_id", -1);
                String clusterName = data.getStringExtra("clusterName");
                mTvCluster.setText(clusterName);
            }
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void finish(FinishActivityEven deployEven){
        finish();

    }

}
