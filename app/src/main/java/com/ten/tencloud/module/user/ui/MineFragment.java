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
import android.widget.LinearLayout;
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

public class MineFragment extends BaseFragment implements UserHomeContract.View, UserInfoContract.View {

    @BindView(R.id.ll_company_layout)
    LinearLayout mLlCompanyLayout;//公司布局
    @BindView(R.id.ll_user_layout)
    LinearLayout mLlUserLayout;//个人布局

    //===个人模版
    @BindView(R.id.iv_user_avatar)
    ImageView mIvUserAvatar;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.iv_certification)
    ImageView mIvUserCertification;
    @BindView(R.id.tv_user_phone)
    TextView mTvUserPhone;

    //====公司模版
    @BindView(R.id.iv_company_logo)
    ImageView mIvCompanyLogo;
    @BindView(R.id.tv_company_name)
    TextView mTvCompanyName;
    @BindView(R.id.iv_company_certification)
    ImageView mIvCompanyCertification;
    @BindView(R.id.tv_company_contact)
    TextView mTvCompanyContact;
    @BindView(R.id.tv_company_phone)
    TextView mTvCompanyPhone;

    @BindView(R.id.tv_switch)
    TextView mTvSwitch;

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
    private boolean mIsPermissionAddTemplate;
    private boolean mIsPermissionChangeTemplate;
    private boolean mIsPermissionDelTemplate;

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
        if (GlobalStatusManager.getInstance().isCompanyListNeedRefresh()) {
            mUserHomePresenter.getCompanies();
        }
        if (cid == 0) {//个人界面
            mLlUserLayout.setVisibility(View.VISIBLE);
            mLlCompanyLayout.setVisibility(View.INVISIBLE);
            mViewRoleCompany.setVisibility(View.GONE);
            mViewRoleUser.setVisibility(View.VISIBLE);
            mViewTemplate.setVisibility(View.GONE);
            if (GlobalStatusManager.getInstance().isUserInfoNeedRefresh()) {
                mUserInfoPresenter.getUserInfo();
            } else {
                showUserInfo(AppBaseCache.getInstance().getUserInfo());
            }
        } else {
            mLlUserLayout.setVisibility(View.INVISIBLE);
            mLlCompanyLayout.setVisibility(View.VISIBLE);
            mViewRoleUser.setVisibility(View.GONE);
            mViewRoleCompany.setVisibility(View.VISIBLE);
            mViewTemplate.setVisibility(View.VISIBLE);
            mUserHomePresenter.getEmployees(cid);
            mUserHomePresenter.getPermission(cid);
            mUserHomePresenter.getCompanyByCid(cid);
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
            mUserHomePresenter.getCompanies();
            initView();
            isFirst = false;
        }
    }

    @OnClick({R.id.ll_user_layout, R.id.ll_company_layout, R.id.tv_switch, R.id.ll_setting,
            R.id.ll_company, R.id.ll_template, R.id.ll_employee})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_user_layout: {
                startActivity(new Intent(mActivity, UserInfoActivity.class));
                break;
            }
            case R.id.ll_company_layout: {
                Intent intent = new Intent(mActivity, CompanyInfoActivity.class);
                intent.putExtra("cid", cid);
                intent.putExtra("isAdmin", mSelectCompany.getIs_admin());
                startActivity(intent);
                break;
            }
            case R.id.tv_switch: {
                mTvSwitch.setSelected(true);
                mPopupWindow.showAsDropDown(mTvSwitch, 0
                        , UiUtils.dip2px(mActivity, 8));
                break;
            }
            case R.id.ll_setting: {
                startActivity(new Intent(mActivity, SettingActivity.class));
                break;
            }
            case R.id.ll_company: {
                startActivity(new Intent(mActivity, CompanyListActivity.class));
                break;
            }
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
                    mUserHomePresenter.getPermission(cid);
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
        mTvCompanyName.setText(companyInfo.getName());
        mTvCompanyContact.setText(companyInfo.getContact());
        mTvCompanyPhone.setText(companyInfo.getMobile());
        GlideUtils.getInstance().loadCircleImage(mActivity, mIvCompanyLogo,
                companyInfo.getImage_url(), R.mipmap.icon_com_photo);
    }

    @Override
    public void showUserInfo(User user) {
        user = AppBaseCache.getInstance().getUserInfo();
        mTvUserName.setText(user.getName());
        mTvUserPhone.setText(Utils.hide4Phone(user.getMobile()));
        GlideUtils.getInstance().loadCircleImage(mActivity, mIvUserAvatar,
                user.getImage_url(), R.mipmap.icon_userphoto);
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
            mSelectCompany = null;//cid为新创建的公司
            initView();
        }
    }
}
