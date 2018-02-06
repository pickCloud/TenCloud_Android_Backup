package com.ten.tencloud.module.user.ui;

import android.os.Bundle;
import android.view.View;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.constants.GlobalStatusManager;
import com.ten.tencloud.model.AppBaseCache;
import com.ten.tencloud.module.main.ui.MainActivity;

import butterknife.BindView;

public class CompanyNewResultActivity extends BaseActivity {

    @BindView(R.id.ll_success)
    View mLlSuccess;
    @BindView(R.id.ll_failed)
    View mLlFailed;

    private boolean mIsSuccess;
    private int mCid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_company_new_result);
        initTitleBar(true, "提起企业异议");
        mIsSuccess = getIntent().getBooleanExtra("isSuccess", false);
        mLlSuccess.setVisibility(mIsSuccess ? View.VISIBLE : View.GONE);
        mLlFailed.setVisibility(!mIsSuccess ? View.VISIBLE : View.GONE);
    }

    /**
     * 提交企业异议
     *
     * @param view
     */
    public void submitObjection(View view) {
        startActivityNoValue(this, CompanyObjectionActivity.class);
    }

    public void btnOk(View view) {
        mCid = getIntent().getIntExtra("cid", 0);
        AppBaseCache.getInstance().setCid(mCid);
        GlobalStatusManager.getInstance().setUserInfoNeedRefresh(true);
        startActivityNoValue(this, MainActivity.class);
    }
}
