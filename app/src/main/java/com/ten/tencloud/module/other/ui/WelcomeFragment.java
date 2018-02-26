package com.ten.tencloud.module.other.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.ten.tencloud.R;
import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.view.BaseFragment;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.model.AppBaseCache;
import com.ten.tencloud.model.SPFHelper;
import com.ten.tencloud.module.login.ui.LoginActivity;
import com.ten.tencloud.module.main.ui.MainActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lxq on 2018/2/7.
 */

public class WelcomeFragment extends BaseFragment {

    private int[] resources = {R.mipmap.p1, R.mipmap.p2, R.mipmap.p3, R.mipmap.p4};

    @BindView(R.id.ll_background)
    LinearLayout mLlBackground;
    @BindView(R.id.btn_ok)
    Button mBtnOk;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return createView(inflater, container, R.layout.fragment_welcome);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        int page = getArguments().getInt("page");//第几页
        mLlBackground.setBackgroundResource(resources[page]);
        mBtnOk.setVisibility(page == 3 ? View.VISIBLE : View.GONE);
    }

    @OnClick({R.id.btn_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                new SPFHelper(TenApp.getInstance(), "").putBoolean(Constants.FIRST_OPEN, false);
                String token = AppBaseCache.getInstance().getToken();
                if (TextUtils.isEmpty(token)) {
                    startActivity(new Intent(mActivity, LoginActivity.class));
                } else {
                    startActivity(new Intent(mActivity, MainActivity.class));
                }
                break;
        }
    }
}
