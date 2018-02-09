package com.ten.tencloud.module.login.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.JoinComBean;
import com.ten.tencloud.bean.User;
import com.ten.tencloud.constants.GlobalStatusManager;
import com.ten.tencloud.model.AppBaseCache;
import com.ten.tencloud.module.login.contract.JoinCom2Contract;
import com.ten.tencloud.module.login.presenter.JoinCom2Presenter;

import butterknife.BindView;

public class JoinComStep2Activity extends BaseActivity implements JoinCom2Contract.View {

    @BindView(R.id.ll_password)
    View mLlPasssword;
    @BindView(R.id.ll_name)
    View mLlName;
    @BindView(R.id.ll_id_card)
    View mLlIdCard;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.et_password_verify)
    EditText mEtPasswordVerify;
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_id_card)
    EditText mEtIdCard;
    @BindView(R.id.et_phone)
    EditText mEtPhone;

    private boolean mIsNeedPW;
    private String mSetting;
    private String mCode;
    private boolean mIsName;
    private boolean mIsIdCard;
    private JoinCom2Presenter mJoinCom2Presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_join_com_step2);
        initTitleBar(true, "完善资料");

        GlobalStatusManager.getInstance().registerTask(this);

        mCode = getIntent().getStringExtra("code");
        mSetting = getIntent().getStringExtra("setting");
        mIsNeedPW = getIntent().getBooleanExtra("isNeedInfo", false);

        mJoinCom2Presenter = new JoinCom2Presenter();
        mJoinCom2Presenter.attachView(this);

        if (TextUtils.isEmpty(mSetting)) {
            mJoinCom2Presenter.getJoinInitialize(mCode);
        } else {
            initView();
        }
    }

    private void initView() {

        mIsName = mSetting.contains("name");
        mIsIdCard = mSetting.contains("id_card");

        mLlPasssword.setVisibility(mIsNeedPW ? View.VISIBLE : View.GONE);
        User userInfo = AppBaseCache.getInstance().getUserInfo();
        mEtName.setText(userInfo.getName());
        mEtPhone.setText(userInfo.getMobile());
        mLlName.setVisibility(mIsName ? View.VISIBLE : View.GONE);
//        mLlIdCard.setVisibility(mIsIdCard ? View.VISIBLE : View.GONE);
        mLlIdCard.setVisibility(View.GONE);//暂时隐藏身份证相关
    }

    public void btnOk(View view) {
        String password = mEtPassword.getText().toString().trim();
        String passwordVerify = mEtPasswordVerify.getText().toString().trim();
        String name = mEtName.getText().toString().trim();
        String idCard = mEtIdCard.getText().toString().trim();
        if (mIsNeedPW && TextUtils.isEmpty(password)) {
            showMessage(R.string.tips_verify_password_empty);
            return;
        }
        if (mIsNeedPW && password.length() < 6) {
            showMessage(R.string.tips_verify_password_length);
            return;
        }
        if (mIsNeedPW && !passwordVerify.equals(password)) {
            showMessage(R.string.tips_verify_password_unmatched);
            return;
        }
        if (mIsName && TextUtils.isEmpty(name)) {
            showMessage(R.string.tips_verify_name_empty);
            return;
        }
//        if (mIsIdCard && TextUtils.isEmpty(idCard)) {
//            showMessage(R.string.tips_verify_id_card_empty);
//            return;
//        }
        String mobile = AppBaseCache.getInstance().getUserInfo().getMobile();
        mJoinCom2Presenter.joinCompany(mIsNeedPW, password, mCode, mobile, name, idCard);
    }

    @Override
    public void success() {
        Intent intent = new Intent(this, JoinComStep3Activity.class);
        startActivity(intent);
    }

    @Override
    public void showInitialize(JoinComBean bean) {
        mSetting = bean.getSetting();
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mJoinCom2Presenter.detachView();
        GlobalStatusManager.getInstance().unRegisterTask(this);
    }
}
