package com.ten.tencloud.module.user.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.ten.tencloud.R;
import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.adapter.CJSVpPagerAdapter;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.base.view.BasePager;
import com.ten.tencloud.bean.PermissionTemplateBean;
import com.ten.tencloud.bean.PermissionTreeNodeBean;
import com.ten.tencloud.broadcast.RefreshBroadCastHandler;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.listener.OnRefreshListener;
import com.ten.tencloud.model.AppBaseCache;
import com.ten.tencloud.module.user.adapter.RvTreeComTemplateAdapter;
import com.ten.tencloud.module.user.contract.PermissionTreeContract;
import com.ten.tencloud.module.user.presenter.PermissionTreePresenter;
import com.ten.tencloud.utils.UiUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindColor;
import butterknife.BindView;

public class PermissionTreeActivity extends BaseActivity implements PermissionTreeContract.View {

    //模版权限
    public static final int TYPE_UPDATE = 0;
    public static final int TYPE_VIEW = 1;
    public static final int TYPE_NEW = 3;

    //给用户设置
    public static final int TYPE_USER_SETTING = 4;
    public static final int TYPE_USER_VIEW = 5;

    @BindColor(R.color.colorPrimary)
    int mColorPrimary;
    @BindColor(R.color.text_color_899ab6)
    int mColor899ab6;

    @BindView(R.id.indicator)
    MagicIndicator mIndicator;
    @BindView(R.id.vp_content)
    ViewPager mVpContent;

    @BindView(R.id.ll_template)
    LinearLayout mLlTemplate;
    @BindView(R.id.rv_template)
    RecyclerView mRvTemplate;

    String[] titles = {"功能", "数据"};

    private PermissionTemplateBean mTemplateBean;
    private CJSVpPagerAdapter mAdapter;
    private PermissionTreePresenter mTreePresenter;
    private PermissionTreePager mFuncPager;
    //    private PermissionTreePager mDataPager;
    private PermissionTreeFilterPager mDataPager;
    private List<PermissionTreeNodeBean> resource;

    private int type;
    private RvTreeComTemplateAdapter mTreeComTemplateAdapter;
    private CommonNavigatorAdapter mCommonNavigatorAdapter;
    private CommonNavigator mCommonNavigator;
    private RefreshBroadCastHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_permission_tree);
        mTemplateBean = getIntent().getParcelableExtra("obj");
        type = getIntent().getIntExtra("type", TYPE_UPDATE);
        if (type == TYPE_VIEW) {
            initTitleBar(true, "查看权限", mTemplateBean.getName());
        } else if (type == TYPE_USER_VIEW) {
            String name = getIntent().getStringExtra("name");
            initTitleBar(true, "查看权限", name);
        } else if (type == TYPE_USER_SETTING) {
            String name = getIntent().getStringExtra("name");
            initTitleBar(true, "设置权限", name, "确认", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int uid = getIntent().getIntExtra("uid", -1);
                    if (mFuncPager == null || mDataPager == null) {
                        return;
                    }
                    Map<String, String> funcSelectNode = mFuncPager.getSelectNode();
                    Map<String, String> dataSelectNode = mDataPager.getSelectNode();
                    dataSelectNode.putAll(funcSelectNode);
                    PermissionTemplateBean templateBean = new PermissionTemplateBean();
                    templateBean.setPermissions(dataSelectNode.get("permissions"));
                    templateBean.setAccess_filehub(dataSelectNode.get("access_filehub"));
                    templateBean.setAccess_projects(dataSelectNode.get("access_projects"));
                    templateBean.setAccess_servers(dataSelectNode.get("access_servers"));
                    templateBean.setUid(uid);
                    templateBean.setCid(AppBaseCache.getInstance().getSelectCompanyWithLogin().getCid());
                    mTreePresenter.updateUserPermission(templateBean);
                }
            });
        } else {
            initTitleBar("模版权限项选择", R.mipmap.icon_close, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            }, "确认", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mFuncPager == null || mDataPager == null) {
                        return;
                    }
                    Map<String, String> funcSelectNode = mFuncPager.getSelectNode();
                    Map<String, String> dataSelectNode = mDataPager.getSelectNode();
                    dataSelectNode.putAll(funcSelectNode);
                    if (type == TYPE_NEW) {
                        Intent data = new Intent();
                        data.putExtra("json", TenApp.getInstance()
                                .getGsonInstance().toJson(dataSelectNode));
                        setResult(Constants.ACTIVITY_RESULT_CODE_REFRESH, data);
                        finish();
                    } else if (type == TYPE_UPDATE) {
                        mTemplateBean.setPermissions(dataSelectNode.get("permissions"));
                        mTemplateBean.setAccess_filehub(dataSelectNode.get("access_filehub"));
                        mTemplateBean.setAccess_projects(dataSelectNode.get("access_projects"));
                        mTemplateBean.setAccess_servers(dataSelectNode.get("access_servers"));
                        mTreePresenter.updatePermission(mTemplateBean.getId(), mTemplateBean);
                    }
                }
            });
        }

        mHandler = new RefreshBroadCastHandler(RefreshBroadCastHandler.PERMISSION_SETTING_CHANGE_ACTION);
        mHandler.registerReceiver(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                Map<String, String> funcSelectNode = mFuncPager.getSelectNode();
                Map<String, String> dataSelectNode = mDataPager.getSelectNode();
                dataSelectNode.putAll(funcSelectNode);
                mTemplateBean.setPermissions(dataSelectNode.get("permissions"));
                mTemplateBean.setAccess_filehub(dataSelectNode.get("access_filehub"));
                mTemplateBean.setAccess_projects(dataSelectNode.get("access_projects"));
                mTemplateBean.setAccess_servers(dataSelectNode.get("access_servers"));
                int funcCount = handCount(mTemplateBean.getPermissions());
                int dataCount = handCount(mTemplateBean.getAccess_servers())
                        + handCount(mTemplateBean.getAccess_projects())
                        + handCount(mTemplateBean.getAccess_filehub());
                titles[0] = "功能(" + funcCount + "/" + getTotal(resource.get(0)) + ")";
                titles[1] = "数据(" + dataCount + "/" + getTotal(resource.get(1)) + ")";
                notifyTitleChange();
            }
        });

        initView();
    }

    private void initView() {
        mTreePresenter = new PermissionTreePresenter();
        mTreePresenter.attachView(this);
        if (type == TYPE_VIEW) {
            //查看具体模版的权限
            if (mTemplateBean.getId() == 0) {
                mTreePresenter.getTemplateResource(AppBaseCache.getInstance().getCid());
            } else {
                mTreePresenter.getTemplate(mTemplateBean.getId());
            }
        } else if (type == TYPE_USER_SETTING) {
            mLlTemplate.setVisibility(View.VISIBLE);
            mRvTemplate.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            mTreeComTemplateAdapter = new RvTreeComTemplateAdapter(mContext);
            mTreeComTemplateAdapter.setOnSelectListener(new RvTreeComTemplateAdapter.OnSelectListener() {
                @Override
                public void onSelect(PermissionTemplateBean bean) {
                    mTemplateBean = bean;
                    initIndicator();
                    showTemplatesAll(resource);
                }
            });
            mRvTemplate.setAdapter(mTreeComTemplateAdapter);
            mTreePresenter.getTemplatesByCid(AppBaseCache.getInstance().getCid());
            int uid = getIntent().getIntExtra("uid", -1);
            mTreePresenter.getUserPermission(uid);
        } else if (type == TYPE_USER_VIEW) {
            int uid = getIntent().getIntExtra("uid", -1);
            mTreePresenter.viewUserPermission(uid);
        } else {
            //更新权限
            mTreePresenter.getTemplateResource(AppBaseCache.getInstance().getCid());
        }
        initIndicator();
    }

    private void initIndicator() {
        mCommonNavigator = new CommonNavigator(this);
        mCommonNavigatorAdapter = new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return titles.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(mColor899ab6);
                simplePagerTitleView.setSelectedColor(mColorPrimary);
                simplePagerTitleView.setText(titles[index]);
                simplePagerTitleView.setTextSize(14);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mVpContent.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setColors(mColorPrimary);
                indicator.setLineHeight(UiUtils.dip2px(context, 2));
                return indicator;
            }
        };
        mCommonNavigator.setAdapter(mCommonNavigatorAdapter);
        mIndicator.setNavigator(mCommonNavigator);
        LinearLayout titleContainer = mCommonNavigator.getTitleContainer();
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerDrawable(new ColorDrawable() {
            @Override
            public int getIntrinsicWidth() {
                return UIUtil.dip2px(mContext, 120);
            }
        });
    }

    private void notifyTitleChange() {
        mCommonNavigator.notifyDataSetChanged();
        LinearLayout titleContainer = mCommonNavigator.getTitleContainer();
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerDrawable(new ColorDrawable() {
            @Override
            public int getIntrinsicWidth() {
                return UIUtil.dip2px(mContext, 120);
            }
        });
    }

    @Override
    public void showTemplateList(List<PermissionTemplateBean> data) {
        mTreeComTemplateAdapter.setDatas(data);
    }

    @Override
    public void showTemplatesAll(List<PermissionTreeNodeBean> data) {
        resource = data;
        ArrayList<BasePager> pagers = new ArrayList<>();
        mFuncPager = new PermissionTreePager(this);
        mDataPager = new PermissionTreeFilterPager(this);

        if (type == TYPE_VIEW && mTemplateBean.getId() == 0) {
            mTemplateBean.setAccess_servers("");
            mTemplateBean.setPermissions("");
            mTemplateBean.setAccess_projects("");
            mTemplateBean.setAccess_filehub("");
            mFuncPager.putArgument("isView", true);
            mDataPager.putArgument("isView", true);
            titles[0] = "功能(" + getTotal(data.get(0)) + ")";
            titles[1] = "功能(" + getTotal(data.get(1)) + ")";
        } else {
            int funcCount = handCount(mTemplateBean.getPermissions());
            int dataCount = handCount(mTemplateBean.getAccess_servers())
                    + handCount(mTemplateBean.getAccess_projects())
                    + handCount(mTemplateBean.getAccess_filehub());
            titles[0] = "功能(" + funcCount + "/" + getTotal(data.get(0)) + ")";
            titles[1] = "数据(" + dataCount + "/" + getTotal(data.get(1)) + ")";
        }

        mFuncPager.putArgument("resource", data.get(0).getData());
        mFuncPager.putArgument("select", mTemplateBean);
        mFuncPager.putArgument("type", PermissionTreePager.TYPE_FUNC);
        pagers.add(mFuncPager);
        mDataPager.putArgument("resource", data.get(1).getData());
        mDataPager.putArgument("select", mTemplateBean);
        mDataPager.putArgument("type", PermissionTreePager.TYPE_DATA);
        pagers.add(mDataPager);
        mFuncPager.initView();
        mDataPager.initView();
        mAdapter = new CJSVpPagerAdapter(titles, pagers);
        mVpContent.setOffscreenPageLimit(pagers.size());
        mVpContent.setAdapter(mAdapter);
        ViewPagerHelper.bind(mIndicator, mVpContent);
        notifyTitleChange();

    }

    @Override
    public void showTemplates(PermissionTreeNodeBean data) {
        ArrayList<BasePager> pagers = new ArrayList<>();
        mFuncPager = new PermissionTreePager(this);
        mFuncPager.putArgument("resource", data.getData().get(0).getData());
        mFuncPager.putArgument("type", PermissionTreePager.TYPE_FUNC);
        mFuncPager.putArgument("isNew", true);
        if (type == TYPE_VIEW || type == TYPE_USER_VIEW) {
            mFuncPager.putArgument("isView", true);
        }
        mFuncPager.initView();
        pagers.add(mFuncPager);
        mDataPager = new PermissionTreeFilterPager(this);
        mDataPager.putArgument("resource", data.getData().get(1).getData());
        mDataPager.putArgument("type", PermissionTreePager.TYPE_DATA);
        mDataPager.putArgument("isNew", true);
        if (type == TYPE_VIEW || type == TYPE_USER_VIEW) {
            mDataPager.putArgument("isView", true);
        }
        mDataPager.initView();
        pagers.add(mDataPager);
        if (mTemplateBean != null) {
            int funcCount = handCount(mTemplateBean.getPermissions());
            int dataCount = handCount(mTemplateBean.getAccess_servers())
                    + handCount(mTemplateBean.getAccess_projects())
                    + handCount(mTemplateBean.getAccess_filehub());
            titles[0] = "功能(" + funcCount + "/" + getTotal(data.getData().get(0)) + ")";
            titles[1] = "数据(" + dataCount + "/" + getTotal(data.getData().get(1)) + ")";
        } else {
            titles[0] = "功能(" + getTotal(data.getData().get(0)) + ")";
            titles[1] = "功能(" + getTotal(data.getData().get(1)) + ")";
        }

        mAdapter = new CJSVpPagerAdapter(titles, pagers);
        mVpContent.setOffscreenPageLimit(pagers.size());
        mVpContent.setAdapter(mAdapter);
        ViewPagerHelper.bind(mIndicator, mVpContent);
        notifyTitleChange();
    }

    @Override
    public void updateSuccess() {
        showMessage("模版权限更新成功");
        Intent data = new Intent();
        data.putExtra("obj", mTemplateBean);
        setResult(Constants.ACTIVITY_RESULT_CODE_REFRESH, data);
        finish();
    }

    @Override
    public void showExistPermission(PermissionTemplateBean exist) {
        mTemplateBean = exist;
    }

    @Override
    public void updateUserPermissionSuccess() {
        showMessage("用户权限更新成功");
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        if (TYPE_UPDATE == type || TYPE_VIEW == type) {
            overridePendingTransition(0, R.anim.slide_in_bttom);
        }
    }

    /**
     * 获取统计
     *
     * @param nodes
     * @return
     */
    private int getTotal(PermissionTreeNodeBean nodes) {
        int total = 0;
        List<PermissionTreeNodeBean> data = nodes.getData();
        if (data == null) {//最底层
            if (nodes.getId() != 0 || nodes.getSid() != 0) {
                return 1;
            } else {
                return 0;
            }
        } else {
            for (PermissionTreeNodeBean datum : data) {
                total += getTotal(datum);
            }
        }
        return total;
    }

    private int handCount(String permission) {
        if (TextUtils.isEmpty(permission)) {
            return 0;
        }
        return permission.split(",").length;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTreePresenter.detachView();
        mTreePresenter = null;
        mHandler.unregisterReceiver();
        mHandler = null;
    }
}
