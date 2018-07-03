package com.ten.tencloud.module.app.ui;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.utils.UiUtils;
import com.ten.tencloud.widget.StatusSelectPopView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Ingress规则
 */
public class IngressModificationRuleActivity extends BaseActivity {

    @BindView(R.id.ll_layout)
    LinearLayout mLlLayout;
    @BindView(R.id.ll_add_port)
    LinearLayout mLlAddPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_ingress_modification_rule);
        initTitleBar(true, "修改Ingress规则", "确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();

            }
        });

        UiUtils.addTransitionAnim(mLlLayout);//添加动效

    }

    //保存View
    private SparseArray<View> views = new SparseArray<>();
    //初始化Key，用于记录动态创建的View
    private int initKey = 1000;

    private void createLayoutView() {
        final int key = initKey;

        final View view = getLayoutInflater().inflate(R.layout.layout_app_ingress_rule_port, mLlLayout, false);
        View removePort = view.findViewById(R.id.ll_remove_port);
        StatusSelectPopView spvProtocol = view.findViewById(R.id.spv_match);

        List<String> statusTitles = new ArrayList<>();
        statusTitles.add("aa");
        statusTitles.add("bvv");
        spvProtocol.initData(statusTitles);

        views.put(key, view);

        removePort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLlLayout.removeView(view);
                initKey -- ;
            }
        });
        mLlLayout.addView(view);
        initKey++;

    }

    private void next() {
        for (int i = 1000; i < initKey; i++) {
            View childView = views.get(i);
            EditText etHost = childView.findViewById(R.id.et_host);
            EditText etPath = childView.findViewById(R.id.et_path);
            TextView tvStatus = childView.findViewById(R.id.spv_match).findViewById(R.id.tv_status);
            String host = etHost.getText().toString().trim();
            String path = etPath.getText().toString().trim();
            String status = tvStatus.getText().toString().trim();

            if (ObjectUtils.isEmpty(host)){
                ToastUtils.showShort("请输入host");
                return;
            }
            if (ObjectUtils.isEmpty(path)){
                ToastUtils.showShort("请输入path");
                return;
            }


        }

    }

    @OnClick(R.id.ll_add_port)
    public void onViewClicked() {
        createLayoutView();

    }

}
