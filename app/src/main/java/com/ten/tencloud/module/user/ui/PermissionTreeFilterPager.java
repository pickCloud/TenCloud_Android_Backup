package com.ten.tencloud.module.user.ui;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSVpPagerAdapter;
import com.ten.tencloud.base.view.BasePager;
import com.ten.tencloud.bean.PermissionTemplateBean;
import com.ten.tencloud.bean.PermissionTreeNodeBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by lxq on 2017/12/26.
 */

public class PermissionTreeFilterPager extends BasePager {

    @BindView(R.id.ll_content)
    LinearLayout mLlContent;
    @BindView(R.id.tab)
    TabLayout mTab;
    @BindView(R.id.vp_data)
    ViewPager mVpData;

    //所有模板
    private List<PermissionTreeNodeBean> mResourceData;
    //是否为查看
    private boolean isView;
    //选择的数据
    private PermissionTemplateBean mSelectData;

    private String[] mTabTitles;

    public PermissionTreeFilterPager(Context context) {
        super(context);
        init();
    }

    @Override
    public void init() {
        createView(R.layout.pager_permission_tree_filter);
    }

    private void initData() {
        //文件 项目 服务器 级别
        List<PermissionTreeNodeBean> types = mResourceData;
        mTabTitles = new String[types.size()];
        List<BasePager> pagers = new ArrayList<>();
        //组装各类别数据
        for (int i = 0; i < types.size(); i++) {
            mTabTitles[i] = types.get(i).getName();
            PermissionTreeFilterItemPager itemPager = new PermissionTreeFilterItemPager(mContext);
            List<PermissionTreeNodeBean> data = types.get(i).getData();
            itemPager.putArgument("data", data);
            itemPager.putArgument("type", types.get(i).getName());
            itemPager.initView();
            pagers.add(itemPager);
        }
        mVpData.setAdapter(new CJSVpPagerAdapter(mTabTitles, pagers));
        mTab.setupWithViewPager(mVpData);
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

//        map.put("access_filehub", StringUtils.join(selectsFile, ","));
//        map.put("access_projects", StringUtils.join(selectsProject, ","));
//        map.put("access_servers", StringUtils.join(selectsServer, ","));
        return map;
    }
}
