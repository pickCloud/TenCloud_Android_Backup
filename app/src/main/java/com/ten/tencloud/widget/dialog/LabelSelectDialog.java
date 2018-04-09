package com.ten.tencloud.widget.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.ten.tencloud.R;
import com.ten.tencloud.bean.LabelBean;
import com.ten.tencloud.listener.DialogListener;
import com.ten.tencloud.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lxq on 2017/11/24.
 */

public class LabelSelectDialog extends Dialog {

    @BindView(R.id.fbl_label_edit)
    FlexboxLayout mFblEditLabel;
    @BindView(R.id.fbl_label_list)
    FlexboxLayout mFblHistoryLabel;
    @BindView(R.id.tv_ok)
    TextView mTvOk;

    private static final int MAX_LABEL_NUM = 3;
    private static final int MAX_LABEL_LENGTH = 8;
    private Context context;
    private DialogListener<ArrayList<LabelBean>> mDialogListener;

    private View mLabelEditView;
    private EditText mEtLabelAdd;

    private ArrayList<LabelBean> mHistoryLabels;
    private ArrayList<LabelBean> mEditLabels;

    public LabelSelectDialog(@NonNull Context context, DialogListener<ArrayList<LabelBean>> dialogListener) {
        super(context, R.style.BottomSheetDialogStyle);
        this.context = context;
        mDialogListener = dialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_label_select);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);

        initLabelAddEditText();

        mHistoryLabels = new ArrayList<>();
        mEditLabels = new ArrayList<>();

        mHistoryLabels.add(new LabelBean("基础组件"));
        mHistoryLabels.add(new LabelBean("java"));
        mHistoryLabels.add(new LabelBean("应用服务"));
        mHistoryLabels.add(new LabelBean("ios"));
        mHistoryLabels.add(new LabelBean("自定义标签"));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initLabelAddEditText() {
        mLabelEditView = View.inflate(context, R.layout.item_app_service_label_add, null);
        mEtLabelAdd = mLabelEditView.findViewById(R.id.et_label_add);
        //模拟软键盘弹起
        mEtLabelAdd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN && mEditLabels != null && mEditLabels.size() != 0) {
                    for (LabelBean labelBean : mEditLabels) {
                        if (labelBean.isDelete()) {
                            labelBean.setDelete(false);
                            labelBean.setSelect(false);
                            createEditLabelView(mEditLabels);
                        }
                    }
                }
                return false;
            }
        });

        mEtLabelAdd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String label = mEtLabelAdd.getText().toString().trim();
                if (mEditLabels.size() >= MAX_LABEL_NUM) {
                    ToastUtils.showShortToast("最多只能添加三个标签");
                    return true;
                }
                if (label.length() >= MAX_LABEL_LENGTH) {
                    ToastUtils.showShortToast("应用标签长度不能超过8个字符");
                    return true;
                }
                if (actionId == EditorInfo.IME_ACTION_DONE && !TextUtils.isEmpty(label)) {
                    //有重复标签置顶
                    for (int i = 0; i < mEditLabels.size(); i++) {
                        if (mEditLabels.get(i).getName().equals(label)) {

                            LabelBean labelBean = mEditLabels.get(i);
                            mEditLabels.remove(i);
                            mEditLabels.add(labelBean);
                            createEditLabelView(mEditLabels);
                            return false;
                        }
                    }

                    //历史标签里有就选中
                    for (int i = 0; i < mHistoryLabels.size(); i++) {
                        if (mHistoryLabels.get(i).getName().equals(label)) {
                            mHistoryLabels.get(i).setCheck(true);
                            createHistoryLabelView();
                            mEditLabels.add(mHistoryLabels.get(i));
                            createEditLabelView(mEditLabels);
                            return false;
                        }
                    }

                    mEditLabels.add(new LabelBean(label));
                    createEditLabelView(mEditLabels);
                }
                return false;//返回true，保留软键盘。false，隐藏软键盘
            }
        });

        mFblEditLabel.addView(mLabelEditView);
    }

    public void setHistoryLabelData(ArrayList<LabelBean> data) {
        if (data != null && data.size() != 0) {
            for (LabelBean label : data) {
                Iterator<LabelBean> iterator = mHistoryLabels.iterator();
                while (iterator.hasNext()) {
                    if (label.getName().equals(iterator.next().getName())) {
                        iterator.remove();
                    }
                }
                label.setCheck(true);
                mHistoryLabels.add(label);
            }
        }

        Collections.sort(mHistoryLabels);

        createHistoryLabelView();
    }

    private void createHistoryLabelView() {
        mFblHistoryLabel.removeAllViews();
        for (LabelBean label : mHistoryLabels) {
            createHistoryLableView(label);
        }
    }

    private void createHistoryLableView(final LabelBean label) {
        View view = View.inflate(context, R.layout.item_app_service_history_label, null);
        final CheckBox checkBox = view.findViewById(R.id.cb_label_name);
        checkBox.setText(label.getName());
        checkBox.setChecked(label.isCheck());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mEditLabels != null && mEditLabels.size() >= MAX_LABEL_NUM) {
                    if (isChecked) {
                        ToastUtils.showShortToast("最多只能添加三个标签");
                        buttonView.setChecked(false);
                        return;
                    }
                }
                label.setCheck(!label.isCheck());
                if (label.isCheck()) {
                    mEditLabels.add(label);
                    createEditLabelView(mEditLabels);
                } else {
                    removeEditLabelView(label);
                    mEditLabels.remove(label);
                }
            }
        });
        mFblHistoryLabel.addView(view);
    }


    public void createEditLabelView(ArrayList<LabelBean> data) {
        if (data != null && data.size() != 0) {
            mFblEditLabel.removeAllViews();
            mEditLabels = data;
            for (LabelBean label : mEditLabels) {
                createEditLableView(label);
            }
            mEtLabelAdd.setText("");
            mEtLabelAdd.setHint("输入    ");
            mEtLabelAdd.requestFocus();
            mFblEditLabel.addView(mLabelEditView);
        }
    }

    private void createEditLableView(final LabelBean label) {
        View view = View.inflate(context, R.layout.item_app_service_edit_label, null);
        view.setTag(label);
        CheckBox checkBox = view.findViewById(R.id.cb_label_name);

        checkBox.setChecked(label.isSelect());
        if (label.isDelete()) {
            checkBox.setText(label.getName() + " X");
        } else {
            checkBox.setText(label.getName());
        }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (label.isDelete()) {
                    label.setCheck(false);
                    label.setDelete(false);
                    label.setSelect(false);
                    mEditLabels.remove(label);
                    createEditLabelView(mEditLabels);
                    createHistoryLabelView();
                } else {
                    //设置单选
                    for (int i = 0; i < mEditLabels.size(); i++) {
                        mEditLabels.get(i).setSelect(label.isSelect());
                        mEditLabels.get(i).setDelete(false);
                    }
                    label.setDelete(true);
                    label.setSelect(!label.isSelect());
                    createEditLabelView(mEditLabels);
                }

            }
        });
        mFblEditLabel.addView(view);
    }

    private void removeEditLabelView(LabelBean label) {
        View view = mFblEditLabel.findViewWithTag(label);
        mFblEditLabel.removeView(view);
    }

    @OnClick(R.id.tv_ok)
    public void onClick() {
        String currEditLabel = mEtLabelAdd.getText().toString().trim();
        if (!TextUtils.isEmpty(currEditLabel) && mEditLabels.size() < MAX_LABEL_NUM)
            mEditLabels.add(new LabelBean(currEditLabel));
        for (int i = 0; i < mEditLabels.size(); i++) {
            mEditLabels.get(i).setCheck(true);
            mEditLabels.get(i).setSelect(false);
            mEditLabels.get(i).setDelete(false);
        }

        mDialogListener.onRefresh(mEditLabels);
        dismiss();
    }


}
