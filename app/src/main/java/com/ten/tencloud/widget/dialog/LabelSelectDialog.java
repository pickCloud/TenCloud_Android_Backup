package com.ten.tencloud.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.socks.library.KLog;
import com.ten.tencloud.R;
import com.ten.tencloud.bean.LabelBean;
import com.ten.tencloud.listener.DialogListener;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    private Context context;
    private DialogListener<ArrayList<LabelBean>> mDialogListener;

    private View mLabelEditView;
    private EditText mEtLabelAdd;

    private ArrayList<LabelBean> mHistoryLabels;
    private ArrayList<LabelBean> mEditLabels;
    private Comparator<Object> mComparator = Collator.getInstance(java.util.Locale.CHINA);

    public LabelSelectDialog(@NonNull Context context, DialogListener<ArrayList<LabelBean>> dialogListener) {
        super(context, R.style.BottomSheetDialogStyle);
        this.context = context;
        mDialogListener = dialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉头
        setContentView(R.layout.dialog_label_select);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);

        mLabelEditView = View.inflate(context, R.layout.item_app_service_label_edit, null);
        mEtLabelAdd = mLabelEditView.findViewById(R.id.et_label_add);
        mEtLabelAdd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String label = mEtLabelAdd.getText().toString().trim();
                if (actionId == EditorInfo.IME_ACTION_DONE && !TextUtils.isEmpty(label)) {
                    //有重复标签置顶
                    for (int i = 0; i < mEditLabels.size(); i++) {
                        if (mEditLabels.get(i).getName().equals(label)) {

                            KLog.e();
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

        mHistoryLabels = new ArrayList<>();
        mEditLabels = new ArrayList<>();

        mHistoryLabels.add(new LabelBean("z"));
        mHistoryLabels.add(new LabelBean("b"));
        mHistoryLabels.add(new LabelBean("基础组件"));
        mHistoryLabels.add(new LabelBean("应用服务"));
        mHistoryLabels.add(new LabelBean("自定义标签"));
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
        mFblEditLabel.removeAllViews();
        if (data != null && data.size() != 0) {
            mEditLabels = data;
            for (LabelBean label : mEditLabels) {
                createEditLableView(label);
            }
        }
        mEtLabelAdd.setText("");
        mEtLabelAdd.setHint("输入  ");
        mEtLabelAdd.requestFocus();
        mFblEditLabel.addView(mLabelEditView);
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
        if (!TextUtils.isEmpty(currEditLabel)) mEditLabels.add(new LabelBean(currEditLabel));
        for (int i = 0; i < mEditLabels.size(); i++) {
            mEditLabels.get(i).setCheck(true);
            mEditLabels.get(i).setSelect(false);
            mEditLabels.get(i).setDelete(false);
        }

        mDialogListener.onRefresh(mEditLabels);
        dismiss();
    }

}
