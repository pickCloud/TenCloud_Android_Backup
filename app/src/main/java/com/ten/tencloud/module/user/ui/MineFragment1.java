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

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseFragment;
import com.ten.tencloud.bean.CompanyBean;
import com.ten.tencloud.bean.EmployeeBean;
import com.ten.tencloud.bean.User;
import com.ten.tencloud.constants.GlobalStatusManager;
import com.ten.tencloud.model.AppBaseCache;
import com.ten.tencloud.module.user.adapter.RvSwitchAdapter;
import com.ten.tencloud.module.user.contract.UserHomeContract;
import com.ten.tencloud.module.user.contract.UserInfoContract;
import com.ten.tencloud.module.user.presenter.UserHomePresenter;
import com.ten.tencloud.module.user.presenter.UserInfoPresenter;
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

public class MineFragment1 extends BaseFragment implements UserHomeContract.View, UserInfoContract.View {

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
    @BindView(R.id.tv_user_manger_des)
    TextView mTvEmployees;

    @BindView(R.id.ll_role_company)
    View mViewRoleCompany;
    @BindView(R.id.ll_template)
    View mViewTemplate;
    @BindView(R.id.ll_role_user)
    View mViewRoleUser;

    private boolean isFirst = true;
    private int cid = 0;//记录公司状态，0为个人
    private CompanyBean mSelectCompany;

    private UserHomePresenter mUserHomePresenter;
    private RvSwitchAdapter mAdapter;
    private PopupWindow mPopupWindow;
    private User mUserInfo;
    private UserInfoPresenter mUserInfoPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return createView(inflater, container, R.layout.fragment_mine_home);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mUserHomePresenter = new UserHomePresenter();
        mUserHomePresenter.attachView(this);
        mUserInfoPresenter = new UserInfoPresenter();
        mUserInfoPresenter.attachView(this);

        mUserInfo = AppBaseCache.getInstance().getUserInfo();
        cid = AppBaseCache.getInstance().getCid();
        mSelectCompany = AppBaseCache.getInstance().getSelectCompanyWithLogin();
        initPopupWindow();
        initView();
    }

    private void initView() {
        if (cid == 0) {//个人界面
            mViewRoleCompany.setVisibility(View.GONE);
            mViewRoleUser.setVisibility(View.VISIBLE);
            mIvCertification.setImageResource(R.mipmap.icon_idcard_off);
            mTvUserName.setVisibility(View.GONE);
            mViewTemplate.setVisibility(View.GONE);
            mUserInfoPresenter.getUserInfo();
        } else {
            mViewRoleUser.setVisibility(View.GONE);
            mViewRoleCompany.setVisibility(View.VISIBLE);
            mIvCertification.setImageResource(R.mipmap.icon_comreg_off);
            mTvUserName.setVisibility(View.VISIBLE);
            mUserHomePresenter.getEmployees(cid);
            mUserHomePresenter.getPermission(cid);
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
                mSelectCompany = company;
                AppBaseCache.getInstance().setCid(cid);
                AppBaseCache.getInstance().saveSelectCompanyWithLogin(company);
                mViewTemplate.setVisibility(mSelectCompany.getIs_admin() != 0 ? View.VISIBLE : View.GONE);
                mPopupWindow.dismiss();
                initView();
                showUserInfo(mUserInfo);
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
            mUserInfoPresenter.getUserInfo();
            mUserHomePresenter.getCompanies();
            isFirst = false;
        }
    }

    @OnClick({R.id.ll_user, R.id.tv_switch, R.id.ll_setting,
            R.id.ll_company, R.id.ll_template, R.id.ll_employee})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_user:
                if (cid == 0) {
                    startActivity(new Intent(mActivity, UserInfoActivity.class));
                } else {
                    Intent intent = new Intent(mActivity, CompanyInfoActivity.class);
                    intent.putExtra("cid", cid);
                    intent.putExtra("isAdmin", mSelectCompany.getIs_admin());
                    startActivity(intent);
                }
                break;
            case R.id.tv_switch:
                mTvSwitch.setSelected(true);
                mPopupWindow.showAsDropDown(mTvSwitch, 0
                        , UiUtils.dip2px(mActivity, 8));
                break;
            case R.id.ll_setting:
                startActivity(new Intent(mActivity, SettingActivity.class));
                break;
            case R.id.ll_company:
                startActivity(new Intent(mActivity, CompanyListActivity.class));
                break;
            case R.id.ll_template: {
                Intent intent = new Intent(mActivity, PermissionTemplateListActivity.class);
                intent.putExtra("cid", cid);
                startActivity(intent);
                break;
            }
            case R.id.ll_employee: {
                Intent intent = new Intent(mActivity, EmployeeListActivity.class);
                startActivity(intent);
                break;
            }
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
                if (mSelectCompany == null) {
                    mUserHomePresenter.getCompanyByCid(cid);
                } else {
                    mTvName.setText(mSelectCompany.getCompany_name());
                }
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
            for (CompanyBean company : companies) {
                if (company.getStatus() == 1
                        || company.getStatus() == 2) {
                    data.add(company);
                }
                if (cid == company.getCid()) {
                    mSelectCompany = company;
                    AppBaseCache.getInstance().saveSelectCompanyWithLogin(mSelectCompany);
                    mViewTemplate.setVisibility(mSelectCompany.getIs_admin() != 0 ? View.VISIBLE : View.GONE);
                }
            }
        }
        mTvCompanyDes.setText(count + "家公司");
        mAdapter.setDatas(data);
        mAdapter.setSelectCid(cid);
    }

    @Override
    public void showCompanyInfo(CompanyBean companyInfo) {
        companyInfo.setCid(companyInfo.getId());
        mSelectCompany = companyInfo;
        mTvName.setText(companyInfo.getName());
        mTvUserName.setText(companyInfo.getContact());
        mTvPhone.setText(companyInfo.getMobile());
    }

    @Override
    public void showEmployees(List<EmployeeBean> employees) {
        mTvEmployees.setText(employees.size() + "个员工");
    }

    @Override
    public void showPermissionSuccess() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (GlobalStatusManager.getInstance().isUserInfoNeedRefresh()) {
            mUserInfo = AppBaseCache.getInstance().getUserInfo();
            cid = AppBaseCache.getInstance().getCid();
            mUserInfoPresenter.getUserInfo();
            mSelectCompany = null;//cid为新创建的公司
            mUserHomePresenter.getCompanies();
            initView();
            showUserInfo(mUserInfo);
        }
    }
}