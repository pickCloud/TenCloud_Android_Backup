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
import com.orhanobut.logger.Logger;
import com.ten.tencloud.R;
import com.ten.tencloud.bean.LabelBean;
import com.ten.tencloud.listener.DialogListener;
import com.ten.tencloud.model.JesException;
import com.ten.tencloud.module.app.contract.AppLabelSelectContract;
import com.ten.tencloud.module.app.presenter.AppLabelSelectPresenter;
import com.ten.tencloud.utils.ToastUtils;
import com.ten.tencloud.utils.UiUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lxq on 2017/11/24.
 */

public class LabelSelectDialog extends Dialog implements AppLabelSelectContract.View {

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

    private TreeSet<LabelBean> mHistoryLabels;
    private ArrayList<LabelBean> mEditLabels;
    private AppLabelSelectPresenter mAppLabelSelectPresenter;

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

        mHistoryLabels = new TreeSet<>();
        mEditLabels = new ArrayList<>();

        mAppLabelSelectPresenter = new AppLabelSelectPresenter();
        mAppLabelSelectPresenter.attachView(this);
        mAppLabelSelectPresenter.getLabelList(1);
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
                    ToastUtils.showShortToast("应用标签长度不能超过" + MAX_LABEL_LENGTH + "个字符");
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
                    Iterator<LabelBean> iterator = mHistoryLabels.iterator();
                    while (iterator.hasNext()) {
                        LabelBean labelBean = iterator.next();
                        if (labelBean.getName().equals(label)) {
                            labelBean.setCheck(true);
                            createHistoryLabelView();
                            mEditLabels.add(labelBean);
                            createEditLabelView(mEditLabels);
                            return false;
                        }
                    }

                    mEditLabels.add(new LabelBean(label));
                    mAppLabelSelectPresenter.newLabel(label, 1);

                    createEditLabelView(mEditLabels);
                }
                return false;//返回true，保留软键盘。false，隐藏软键盘
            }
        });

        mFblEditLabel.addView(mLabelEditView);
    }

    public void setHistoryLabelData(ArrayList<LabelBean> data) {
        if (data == null || data.size() == 0) return;
        mHistoryLabels.clear();
        mHistoryLabels.addAll(data);
        createHistoryLabelView();
    }

    private void createHistoryLabelView() {
        mFblHistoryLabel.removeAllViews();
        for (LabelBean label : mHistoryLabels) {
            for (LabelBean editLabel : mEditLabels) {
                if (label.getName().equals(editLabel.getName())) {
                    label.setCheck(true);
                }
            }
            createHistoryLabelView(label);
        }
    }

    private void createHistoryLabelView(final LabelBean label) {
        View view = View.inflate(context, R.layout.item_app_service_history_label, null);
        final CheckBox checkBox = view.findViewById(R.id.cb_label_name);
        checkBox.setText(label.getName());
        checkBox.setChecked(label.isCheck());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mEditLabels != null && mEditLabels.size() >= MAX_LABEL_NUM) {
                    if (isChecked) {
                        ToastUtils.showShortToast("最多只能添加" + MAX_LABEL_NUM + "个标签");
                        buttonView.setChecked(false);
                        return;
                    }
                }
                label.setCheck(!label.isCheck());
                if (label.isCheck()) {
                    mEditLabels.add(label);
                    createEditLabelView(mEditLabels);
                } else {
//                    removeEditLabelView(label);
                    mEditLabels.remove(label);
                    createEditLabelView(mEditLabels);
                }
            }
        });
        mFblHistoryLabel.addView(view);
    }

    public void setEditLabelData(ArrayList<LabelBean> data) {
        if (data != null && data.size() != 0) {
            mFblEditLabel.removeAllViews();
            mEditLabels = data;
            for (LabelBean label : mEditLabels) {
                createEditLabelView(label);
            }
            mEtLabelAdd.setText("");
            mEtLabelAdd.setHint("输入    ");
            mEtLabelAdd.requestFocus();
            mFblEditLabel.addView(mLabelEditView);
        }
    }

    private void createEditLabelView(ArrayList<LabelBean> data) {
        mFblEditLabel.removeAllViews();
        if (data != null && data.size() != 0) {
            mEditLabels = data;
            for (LabelBean label : mEditLabels) {
                createEditLabelView(label);
            }
        }
        mEtLabelAdd.setText("");
        mEtLabelAdd.setHint("输入    ");
        mEtLabelAdd.requestFocus();
        mFblEditLabel.addView(mLabelEditView);

    }

    private void createEditLabelView(final LabelBean label) {
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
                    for (LabelBean historyLabel : mHistoryLabels) {
                        if (historyLabel.getName().equals(label.getName())) {
                            historyLabel.setCheck(false);
                            historyLabel.setDelete(false);
                            historyLabel.setSelect(false);
                        }
                    }
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
        if (!TextUtils.isEmpty(currEditLabel) && mEditLabels.size() < MAX_LABEL_NUM) {
            mEditLabels.add(new LabelBean(currEditLabel));
            mAppLabelSelectPresenter.newLabel(currEditLabel, 1);
        }

        for (int i = 0; i < mEditLabels.size(); i++) {
            mEditLabels.get(i).setCheck(true);
            mEditLabels.get(i).setSelect(false);
            mEditLabels.get(i).setDelete(false);
        }

        mDialogListener.onRefresh(mEditLabels);
        dismiss();
    }


    @Override
    public void labelAddResult(LabelBean bean) {
        Logger.e(bean.toString());
        bean.setSelect(true);
        bean.setCheck(true);
        mHistoryLabels.add(bean);
        createHistoryLabelView();
    }

    @Override
    public void showEmpty() {
        TextView textView = new TextView(context);
        textView.setText("暂无标签");
        textView.setPadding(0, UiUtils.dip2px(context, 10), 0, 0);
        textView.setTextColor(UiUtils.getColor(R.color.select_app_history_label));
        mFblHistoryLabel.addView(textView);
    }

    @Override
    public void showLabelList(TreeSet<LabelBean> labelBeans) {
        mHistoryLabels.clear();
        mHistoryLabels.addAll(labelBeans);
        createHistoryLabelView();
    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void showMessage(int messageId) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(JesException e) {

    }
}
