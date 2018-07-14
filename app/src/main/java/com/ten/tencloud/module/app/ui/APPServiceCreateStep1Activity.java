package com.ten.tencloud.module.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.ObjectUtils;
import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.AppBean;
import com.ten.tencloud.constants.IntentKey;
import com.ten.tencloud.even.FinishActivityEven;
import com.ten.tencloud.module.app.contract.AppCheckNameContract;
import com.ten.tencloud.module.app.presenter.AppCheckNamePresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class APPServiceCreateStep1Activity extends BaseActivity implements AppCheckNameContract.View {
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_pod_tag)
    EditText mEtPodTag;

    private int mSourceType = -1; //（1.内部服务，2.外部服务）
    private AppBean mAppBean;
    private AppCheckNamePresenter mCheckNamePresenter;
    private String mName;
    private String mPodTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_appservice_create_step1);
        mAppBean = getIntent().getParcelableExtra(IntentKey.APP_ITEM);
        initTitleBar(true, "创建服务", "下一步", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });

        mCheckNamePresenter = new AppCheckNamePresenter();
        mCheckNamePresenter.attachView(this);

    }

    //下一步
    private void next() {
//        if (mSourceType == -1) {
//            showMessage("请选择服务来源");
//            return;
//        }
        mName = mEtName.getText().toString();
        if (TextUtils.isEmpty(mName)) {
            showMessage("请输入服务名称");
            return;
        }

        mPodTag = mEtPodTag.getText().toString();
        if (ObjectUtils.isEmpty(mPodTag)){
            showMessage("请输入服务标签");
            return;
        }
        if (TextUtils.isEmpty(mPodTag) || !mPodTag.contains("=")){
            showMessage("服务标签格式错误");
            return;
        }

        //验证名称
        mCheckNamePresenter.checkDeployName(mName, mAppBean.getId());
    }

    @Override
    public void checkResult() {
        Intent intent = new Intent(this, APPServiceCreateStep2Activity.class);
        intent.putExtra(IntentKey.APP_ITEM, mAppBean);
        intent.putExtra(IntentKey.SERVICE_NAME, mName);
        intent.putExtra(IntentKey.SERVICE_TAG, mPodTag);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCheckNamePresenter.detachView();
    }

    @Override
    protected boolean isBindEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void finish(FinishActivityEven finishActivityEven){
        finish();

    }
}
