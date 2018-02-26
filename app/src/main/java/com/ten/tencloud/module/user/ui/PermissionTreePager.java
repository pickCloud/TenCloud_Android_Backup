package com.ten.tencloud.module.user.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BasePager;
import com.ten.tencloud.bean.PermissionTemplateBean;
import com.ten.tencloud.bean.PermissionTreeNodeBean;
import com.ten.tencloud.module.user.adapter.FirstLevelNodeViewBinder1;
import com.ten.tencloud.module.user.adapter.OtherLevelNodeViewBinder;

import org.greenrobot.essentials.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import me.texy.treeview.TreeNode;
import me.texy.treeview.TreeView;
import me.texy.treeview.base.BaseNodeViewBinder;
import me.texy.treeview.base.BaseNodeViewFactory;

/**
 * Created by lxq on 2017/12/26.
 */

public class PermissionTreePager extends BasePager {

    /**
     * 区分数据和功能权限
     */
    public final static int TYPE_FUNC = 1;
    public final static int TYPE_DATA = 2;

    @BindView(R.id.ll_content)
    LinearLayout mLlContent;
    @BindView(R.id.empty_view)
    View mEmptyView;

    private TreeNode mTreeRootNode;
    private TreeView mTreeView;
    private List<PermissionTreeNodeBean> mResourceData;
    private PermissionTemplateBean mSelectData;
    private boolean isView;
    private int mType;
    private boolean mIsNew;

    public PermissionTreePager(Context context) {
        super(context);
        init();
    }

    @Override
    public void init() {
        createView(R.layout.pager_permission_tree);
    }

    public void initView() {
        mTreeRootNode = TreeNode.root();
        mResourceData = getArgument("resource");
        isView = getArgument("isView", false);
        mSelectData = getArgument("select");
        if (mSelectData == null) {
            mIsNew = true;
        }
        if (mResourceData == null || mResourceData.size() == 0) {
            showEmptyView(true);
        }
        mType = getArgument("type");
        addTree(mResourceData, mTreeRootNode, 0);
        mTreeView = new TreeView(mTreeRootNode, mContext, new BaseNodeViewFactory() {
            @Override
            public BaseNodeViewBinder getNodeViewBinder(View view, int level) {
                switch (level) {
                    case 0:
                        return new FirstLevelNodeViewBinder1(view, isView);
                    default:
                        return new OtherLevelNodeViewBinder(view, isView);
                }
            }
        });
        View view = mTreeView.getView();
        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mLlContent.addView(view);
        mTreeView.expandAll();
    }

    private void showEmptyView(boolean isShow) {
        mEmptyView.setVisibility(isShow ? VISIBLE : INVISIBLE);
        mLlContent.setVisibility(isShow ? INVISIBLE : VISIBLE);
    }

    /**
     * 循环添加至Tree
     *
     * @param data
     * @param parentNode
     * @param level
     * @return
     */
    private int addTree(List<PermissionTreeNodeBean> data, TreeNode parentNode, int level) {
        int maxLevel = 0;
        if (data == null) {
            return level;
        }
        for (PermissionTreeNodeBean treeNodeBean : data) {
            TreeNode treeNode = new TreeNode(treeNodeBean);
            treeNode.setLevel(level);
            parentNode.addChild(treeNode);
            //设置填充
            if (mSelectData != null) {
                if (mType == TYPE_FUNC && mSelectData.getPermissions() != null) {
                    List ids = Arrays.asList(mSelectData.getPermissions().split(","));
                    if (ids.contains(treeNodeBean.getId() + "")) {
                        treeNode.setSelected(true);
                    }
                }
                if (mType == TYPE_DATA) {
                    String rootNodeName = findRootNodeName(treeNode);
                    if ("文件".equals(rootNodeName) && mSelectData.getAccess_filehub() != null) {
                        List ids = Arrays.asList(mSelectData.getAccess_filehub().split(","));
                        if (ids.contains(treeNodeBean.getId() + "")) {
                            treeNode.setSelected(true);
                        }
                    }
                    if ("项目".equals(rootNodeName) && mSelectData.getAccess_projects() != null) {
                        List ids = Arrays.asList(mSelectData.getAccess_projects().split(","));
                        if (ids.contains(treeNodeBean.getId() + "")) {
                            treeNode.setSelected(true);
                        }
                    }
                    if ("云服务器".equals(rootNodeName) && mSelectData.getAccess_servers() != null) {
                        List ids = Arrays.asList(mSelectData.getAccess_servers().split(","));
                        if (ids.contains(treeNodeBean.getId() + "")) {
                            treeNode.setSelected(true);
                        }
                    }
                }
            }

            if (treeNodeBean.getData() == null || treeNodeBean.getData().size() == 0) {
                maxLevel = level;
            } else {
                maxLevel = addTree(treeNodeBean.getData(), treeNode, level + 1);
            }
        }
        setParentNodeSelected(parentNode);
        return maxLevel;
    }

    /**
     * 给父接口设置勾选
     *
     * @param treeNode
     */
    private void setParentNodeSelected(TreeNode treeNode) {
        List<TreeNode> children = treeNode.getChildren();
        for (TreeNode child : children) {
            boolean selected = child.isSelected();
            if (!selected) {
                return;
            }
        }
        treeNode.setSelected(true);
    }

    /**
     * 查找最外层根路径的name
     *
     * @param treeNode
     * @return
     */
    private String findRootNodeName(TreeNode treeNode) {
        TreeNode parent = treeNode.getParent();
        if (parent == null) {
            return "";
        }
        if (parent.getLevel() == 1) {
            PermissionTreeNodeBean rootNodeBean = (PermissionTreeNodeBean) parent.getParent().getValue();
            if (rootNodeBean != null) {
                return rootNodeBean.getName();
            }
            PermissionTreeNodeBean treeNodeBean = (PermissionTreeNodeBean) parent.getValue();
            return treeNodeBean.getName();
        } else {
            return findRootNodeName(parent);
        }
    }

    /**
     * 获取选择的节点
     *
     * @return
     */
    public Map<String, String> getSelectNode() {
        Map<String, String> map = new HashMap<>();
        List<TreeNode> selectedNodes = mTreeView.getSelectedNodes();
        if (mType == TYPE_FUNC) {
            List<Integer> selects = new ArrayList<>();
            for (TreeNode selectedNode : selectedNodes) {
                PermissionTreeNodeBean nodeBean = (PermissionTreeNodeBean) selectedNode.getValue();
                if (nodeBean.getId() != 0) {
                    selects.add(nodeBean.getId());
                }
            }
            map.put("permissions", StringUtils.join(selects, ","));
        }
        if (mType == TYPE_DATA) {
            List<Integer> selectsFile = new ArrayList<>();
            List<Integer> selectsProject = new ArrayList<>();
            List<Integer> selectsServer = new ArrayList<>();
            for (TreeNode selectedNode : selectedNodes) {
                String rootNodeName = findRootNodeName(selectedNode);
                PermissionTreeNodeBean nodeBean = (PermissionTreeNodeBean) selectedNode.getValue();
                if (nodeBean.getId() != 0) {
                    if ("文件".equals(rootNodeName)) {
                        selectsFile.add(nodeBean.getId());
                    } else if ("项目".equals(rootNodeName)) {
                        selectsProject.add(nodeBean.getId());
                    } else if ("云服务器".equals(rootNodeName)) {
                        selectsServer.add(nodeBean.getId());
                    }
                }
            }
            map.put("access_filehub", StringUtils.join(selectsFile, ","));
            map.put("access_projects", StringUtils.join(selectsProject, ","));
            map.put("access_servers", StringUtils.join(selectsServer, ","));
        }
        return map;
    }
}
