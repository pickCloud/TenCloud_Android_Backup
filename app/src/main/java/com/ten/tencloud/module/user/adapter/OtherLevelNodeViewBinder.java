package com.ten.tencloud.module.user.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ten.tencloud.R;
import com.ten.tencloud.TenApp;
import com.ten.tencloud.bean.PermissionTreeNodeBean;
import com.ten.tencloud.broadcast.RefreshBroadCastHandler;
import com.ten.tencloud.utils.UiUtils;

import me.texy.treeview.TreeNode;
import me.texy.treeview.base.CheckableNodeViewBinder;

/**
 * Created by lxq on 2017/12/26.
 */

public class OtherLevelNodeViewBinder extends CheckableNodeViewBinder {

    private final RefreshBroadCastHandler mHandler;
    private CheckBox mCheckBox;
    private LinearLayout mLlContent;
    private ImageView mIvArrow;
    private boolean isView;

    public OtherLevelNodeViewBinder(View view, boolean isView) {
        super(view);
        this.isView = isView;
        mCheckBox = view.findViewById(R.id.checkbox);
        mLlContent = view.findViewById(R.id.ll_content);
        mIvArrow = view.findViewById(R.id.iv_arrow);
        mHandler = new RefreshBroadCastHandler(TenApp.getInstance(), RefreshBroadCastHandler.PERMISSION_SETTING_CHANGE_ACTION);
    }

    @Override
    public int getCheckableViewId() {
        return R.id.checkbox;
    }

    @Override
    public int getLayoutId() {
        return R.layout.tree_permission_level_other;
    }

    @Override
    public void bindView(TreeNode treeNode) {
        PermissionTreeNodeBean nodeBean = (PermissionTreeNodeBean) treeNode.getValue();
        String name = nodeBean.getName();
        if (isView) {
            mCheckBox.setCompoundDrawables(null, null, null, null);
            mCheckBox.setEnabled(false);
        }
        if (TextUtils.isEmpty(name)) {//文件的情况
            name = nodeBean.getFilename();
        }
        mCheckBox.setText(name);
        int level = treeNode.getLevel();
        int padding = UiUtils.dip2px(TenApp.getInstance(), 16 + (24 * (level)));
        mLlContent.setPadding(padding, 0, 0, 0);
        if (treeNode.getChildren() == null || treeNode.getChildren().size() == 0) {
            mIvArrow.setVisibility(View.GONE);
        } else {
            mIvArrow.setVisibility(View.VISIBLE);
        }
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mHandler.sendBroadCast();
            }
        });
    }

    @Override
    public void onNodeToggled(TreeNode treeNode, boolean expand) {
        if (expand) {
            mIvArrow.animate().rotation(90);
        } else {
            mIvArrow.animate().rotation(270);
        }
    }
}
