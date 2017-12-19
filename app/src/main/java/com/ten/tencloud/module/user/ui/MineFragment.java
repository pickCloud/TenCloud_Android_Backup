package com.ten.tencloud.module.user.ui;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.socks.library.KLog;
import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseFragment;
import com.ten.tencloud.bean.CompanyBean;
import com.ten.tencloud.bean.User;
import com.ten.tencloud.model.AppBaseCache;
import com.ten.tencloud.module.user.adapter.RvSwitchAdapter;
import com.ten.tencloud.module.user.contract.UserHomeContract;
import com.ten.tencloud.module.user.presenter.UserHomePresenter;
import com.ten.tencloud.utils.UiUtils;
import com.ten.tencloud.utils.Utils;
import com.ten.tencloud.utils.glide.GlideUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * "我的"模块
 * Created by lxq on 2017/11/23.
 */

public class MineFragment extends BaseFragment implements UserHomeContract.View {

    @BindView(R.id.tv_switch)
    TextView mTvSwitch;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.iv_certification)
    ImageView mIvCertification;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.iv_avatar)
    ImageView mIvAvatar;
    @BindView(R.id.tv_company_des)
    TextView mTvCompanyDes;

    @BindView(R.id.ll_role_company)
    View mViewRoleCompany;
    @BindView(R.id.ll_role_user)
    View mViewRoleUser;

    private boolean isFirst = true;
    private int cid = 0;//记录公司状态，0为个人
    private CompanyBean mSelectCompany;

    private UserHomePresenter mUserHomePresenter;
    private RvSwitchAdapter mAdapter;
    private PopupWindow mPopupWindow;
    private User mUserInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return createView(inflater, container, R.layout.fragment_mine_home);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mUserHomePresenter = new UserHomePresenter();
        mUserHomePresenter.attachView(this);
        mUserInfo = AppBaseCache.getInstance().getUserInfo();
        cid = AppBaseCache.getInstance().getCid();
        KLog.d("cid==>" + cid);
        initPopupWindow();
        initView();
    }

    private void initView() {
        if (cid == 0) {//个人界面
            mViewRoleCompany.setVisibility(View.GONE);
            mViewRoleUser.setVisibility(View.VISIBLE);
            mIvCertification.setImageResource(R.mipmap.icon_idcard_off);
            mTvUserName.setVisibility(View.GONE);
            mTvName.setText(mUserInfo.getName());
        } else {
            mViewRoleUser.setVisibility(View.GONE);
            mViewRoleCompany.setVisibility(View.VISIBLE);
            mIvCertification.setImageResource(R.mipmap.icon_comreg_off);
            mTvUserName.setVisibility(View.VISIBLE);
            mTvName.setText(mSelectCompany.getCompany_name());
        }
    }

    private void initPopupWindow() {
        mPopupWindow = new PopupWindow(mActivity);
        View view = LayoutInflater.from(mActivity).inflate(R.layout.pop_user_switch_role, null);
        RecyclerView contentView = view.findViewById(R.id.rv_switch);
        contentView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new RvSwitchAdapter(mActivity);
        mAdapter.setOnSelectListener(new RvSwitchAdapter.OnSelectListener() {
            @Override
            public void onSelect(CompanyBean company) {
                cid = company.getCid();
                AppBaseCache.getInstance().setCid(cid);
                mSelectCompany = company;
                mPopupWindow.dismiss();
                initView();
            }
        });
        contentView.setAdapter(mAdapter);
        mPopupWindow.setContentView(view);
        mPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mTvSwitch.setSelected(false);
            }
        });
    }

    @Override
    public void onVisible() {
        if (isFirst) {
            mUserHomePresenter.getUserInfo();
            mUserHomePresenter.getCompanies();
            isFirst = false;
        }
    }

    @OnClick({R.id.ll_user, R.id.tv_switch})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_user:
                startActivity(new Intent(mActivity, UserInfoActivity.class));
                break;
            case R.id.tv_switch:
                mTvSwitch.setSelected(true);
                mPopupWindow.showAsDropDown(mTvSwitch, 0
                        , UiUtils.dip2px(mActivity, 8));
                break;
        }
    }

    @Override
    public void showUserInfo(User user) {
        if (user != null) {
            mTvUserName.setText(user.getName());
            mTvPhone.setText(Utils.hide4Phone(user.getMobile()));
            if (cid == 0) {
                mTvName.setText(user.getName());
                GlideUtils.getInstance().loadCircleImage(mActivity, mIvAvatar, user.getImage_url(), R.mipmap.icon_userphoto);
            } else {
                mTvName.setText(mSelectCompany.getCompany_name());
            }
        }
    }

    @Override
    public void showCompanies(List<CompanyBean> companies) {
        int count = 0;
        List<CompanyBean> data = new ArrayList<>();
        CompanyBean personal = new CompanyBean();
        personal.setCompany_name(mUserInfo.getName());
        personal.setCid(0);
        data.add(personal);
        if (companies != null) {
            count = companies.size();
            data.addAll(companies);
        }
        mTvCompanyDes.setText(count + "家公司");
        mAdapter.setDatas(data);
        mAdapter.setSelectCid(cid);
    }

    @Override
    public void onResume() {
        super.onResume();
        mUserInfo = AppBaseCache.getInstance().getUserInfo();
        initView();
        showUserInfo(mUserInfo);
    }
}
