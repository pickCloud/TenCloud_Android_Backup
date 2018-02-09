package com.ten.tencloud.module.user.ui;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSVpPagerAdapter;
import com.ten.tencloud.base.view.BasePager;
import com.ten.tencloud.bean.PermissionTemplateBean;
import com.ten.tencloud.bean.PermissionTreeNodeBean;
import com.ten.tencloud.broadcast.RefreshBroadCastHandler;
import com.ten.tencloud.listener.OnRefreshListener;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindColor;
import butterknife.BindView;

/**
 * Created by lxq on 2017/12/26.
 */

public class PermissionTreeFilterPager extends BasePager {

    @BindColor(R.color.text_color_556278)
    int mColor556278;
    @BindColor(R.color.text_color_899ab6)
    int mColor899ab6;

    @BindView(R.id.ll_content)
    LinearLayout mLlContent;
    @BindView(R.id.indicator)
    MagicIndicator mIndicator;
    @BindView(R.id.vp_data)
    ViewPager mVpData;

    //所有模板
    private List<PermissionTreeNodeBean> mResourceData;
    //是否为查看
    private boolean isView;
    //选择的数据
    private PermissionTemplateBean mSelectData;

    private String[] mTabTitles;
    private String[] mTitles;
    private int[] totals;
    private List<BasePager> mPagers;
    private CommonNavigator mCommonNavigator;
    private RefreshBroadCastHandler mHandler;

    public PermissionTreeFilterPager(Context context) {
        super(context);
        init();
    }

    @Override
    public void init() {
        createView(R.layout.pager_permission_tree_filter);
        mHandler = new RefreshBroadCastHandler(mContext, RefreshBroadCastHandler.PERMISSION_SETTING_CHANGE_ACTION);
        mHandler.registerReceiver(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                for (int i = 0; i < mTabTitles.length; i++) {
                    Map<String, String> selectNode = ((PermissionTreeFilterItemPager) mPagers.get(i)).getSelectNode();
                    int count = 0;
                    for (String s : selectNode.keySet()) {
                        String str = selectNode.get(s);
                        if (TextUtils.isEmpty(str)) {
                            count = 0;
                        } else {
                            count = str.split(",").length;
                        }
                    }
                    mTabTitles[i] = mTitles[i] + "(" + count + "/" + totals[i] + ")";
                    notifyTitleChange();
                }
            }
        });
    }

    private void initData() {
        //文件 项目 服务器 级别
        List<PermissionTreeNodeBean> types = mResourceData;
        mTabTitles = new String[types.size()];
        mTitles = new String[types.size()];
        totals = new int[types.size()];
        mPagers = new ArrayList<>();
        //组装各类别数据
        for (int i = 0; i < types.size(); i++) {
            mTitles[i] = types.get(i).getName();
            totals[i] = getTotal(types.get(i));
            PermissionTreeFilterItemPager itemPager = new PermissionTreeFilterItemPager(mContext);
            List<PermissionTreeNodeBean> data = types.get(i).getData();
            itemPager.putArgument("data", data);
            itemPager.putArgument("isView", isView);
            itemPager.putArgument("select", mSelectData);
            itemPager.putArgument("type", types.get(i).getName());
            itemPager.initView();
            mPagers.add(itemPager);
        }
        if (isView) {
            for (int i = 0; i < mTabTitles.length; i++) {
                mTabTitles[i] = mTitles[i] + "(" + totals[i] + ")";
            }
        } else {
            for (int i = 0; i < mTabTitles.length; i++) {
                Map<String, String> selectNode = ((PermissionTreeFilterItemPager) mPagers.get(i)).getSelectNode();
                int count = 0;
                for (String s : selectNode.keySet()) {
                    String str = selectNode.get(s);
                    if (TextUtils.isEmpty(str)) {
                        count = 0;
                    } else {
                        count = str.split(",").length;
                    }
                }
                mTabTitles[i] = mTitles[i] + "(" + count + "/" + totals[i] + ")";
            }
        }
        initIndicator();
        mVpData.setAdapter(new CJSVpPagerAdapter(mTabTitles, mPagers));
        ViewPagerHelper.bind(mIndicator, mVpData);
    }

    private void initIndicator() {
        mCommonNavigator = new CommonNavigator(mContext);
        CommonNavigatorAdapter mCommonNavigatorAdapter = new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mTabTitles.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(mColor556278);
                simplePagerTitleView.setSelectedColor(mColor899ab6);
                simplePagerTitleView.setText(mTabTitles[index]);
                simplePagerTitleView.setTextSize(14);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mVpData.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        };
        mCommonNavigator.setAdapter(mCommonNavigatorAdapter);
        mIndicator.setNavigator(mCommonNavigator);
        LinearLayout titleContainer = mCommonNavigator.getTitleContainer();
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerDrawable(new ColorDrawable() {
            @Override
            public int getIntrinsicWidth() {
                return UIUtil.dip2px(mContext, 15);
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
                return UIUtil.dip2px(mContext, 15);
            }
        });
    }

    public void initView() {
        mResourceData = getArgument("resource");
        isView = getArgument("isView", false);
        mSelectData = getArgument("select");
        initData();
    }

    /**
     * 获取选择的节点
     *
     * @return
     */
    public Map<String, String> getSelectNode() {
        Map<String, String> map = new HashMap<>();
        for (BasePager pager : mPagers) {
            map.putAll(((PermissionTreeFilterItemPager) pager).getSelectNode());
        }
        return map;
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
}
