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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lxq on 2017/11/24.
 */

public class LabelSelectDialog extends Dialog {

    @BindView(R.id.fbl_label_edit)
    FlexboxLayout mFblLabelEdit;
    @BindView(R.id.fbl_label_list)
    FlexboxLayout mFblLabelList;
    @BindView(R.id.tv_ok)
    TextView mTvOk;

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
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉头
        setContentView(R.layout.dialog_label_select);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);

        mHistoryLabels = new ArrayList<>();
        mEditLabels = new ArrayList<>();

        mLabelEditView = View.inflate(context, R.layout.item_label_edit, null);
        mEtLabelAdd = (EditText) mLabelEditView.findViewById(R.id.et_label_add);

        mEtLabelAdd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String label = mEtLabelAdd.getText().toString().trim();
                if (actionId == EditorInfo.IME_ACTION_DONE && !TextUtils.isEmpty(label)) {

                    for (int i = 0; i < mHistoryLabels.size(); i++) {
                        if (mHistoryLabels.get(i).getName().equals(label)) {
                            LabelBean labelBean = mHistoryLabels.get(i);
                            mHistoryLabels.remove(i);
                            mHistoryLabels.add(labelBean);
                            createEditLabelView(mEditLabels);
                            return false;
                        }
                    }

                    mEditLabels.add(new LabelBean(label, true));
                    createEditLabelView(mEditLabels);
                }
                return false;   //返回true，保留软键盘。false，隐藏软键盘
            }
        });

        mFblLabelEdit.addView(mLabelEditView);

    }

    public void setData(ArrayList<LabelBean> data) {
        mHistoryLabels = data;
        mEditLabels = data;
        createHistoryLabelView(mHistoryLabels);
        createEditLabelView(mEditLabels);
    }

    private void createHistoryLabelView(ArrayList<LabelBean> data) {
        mHistoryLabels = data;
        mFblLabelList.removeAllViews();
        for (LabelBean label : data) {
            createHistoryLableView(label);
        }
    }

    private void createHistoryLableView(final LabelBean label) {
        View view = View.inflate(context, R.layout.item_app_service_label, null);
        final CheckBox checkBox = (CheckBox) view.findViewById(R.id.cb_label_name);
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
        mFblLabelList.addView(view);
    }

    private void createEditLabelView(ArrayList<LabelBean> data) {
        mFblLabelEdit.removeAllViews();
        for (LabelBean label : data) {
            createEditLableView(label);
        }
        mEtLabelAdd.setText("");
        mEtLabelAdd.setHint("输入");
        mEtLabelAdd.requestFocus();
        mEtLabelAdd.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                KLog.e(keyCode+"---"+event);
                String desc = "";
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    desc = String.format("%s输入的软按键编码是%d,动作是按下", desc, keyCode);
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        desc = String.format("%s, 按键为回车键", desc);
                    } else if (keyCode == KeyEvent.KEYCODE_DEL) {
                        desc = String.format("%s, 按键为删除键", desc);
                    } else if (keyCode == KeyEvent.KEYCODE_SEARCH) {
                        desc = String.format("%s, 按键为搜索键", desc);
                    } else if (keyCode == KeyEvent.KEYCODE_BACK) {
                        desc = String.format("%s, 按键为返回键", desc);
                    } else if (keyCode == KeyEvent.KEYCODE_MENU) {
                        desc = String.format("%s, 按键为菜单键", desc);
                    } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
                        desc = String.format("%s, 按键为加大音量键", desc);
                    } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
                        desc = String.format("%s, 按键为减小音量键", desc);
                    }else if (keyCode == KeyEvent.KEYCODE_A){
                        desc = "字母按键";
                    }
                    KLog.e(desc);
                    return true;
                } else {
                    //返回true表示处理完了不再输入该字符，返回false表示给你输入该字符吧
                    return false;
                }
            }
        });
        mFblLabelEdit.addView(mLabelEditView);
    }

    private void createEditLableView(final LabelBean label) {
        View view = View.inflate(context, R.layout.item_app_service_label, null);
        view.setTag(label);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.cb_label_name);
        checkBox.setText(label.getName());
        checkBox.setChecked(label.isCheck());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                label.setCheck(!label.isCheck());
                if (!label.isCheck()) {
                    mEditLabels.remove(label);
                    createEditLabelView(mEditLabels);
                    createHistoryLabelView(mHistoryLabels);
                }
            }
        });
        mFblLabelEdit.addView(view);
    }


    private void removeEditLabelView(LabelBean label) {
        View view = mFblLabelEdit.findViewWithTag(label);
        mFblLabelEdit.removeView(view);
    }


    @OnClick(R.id.tv_ok)
    public void onClick() {
        mDialogListener.onRefresh(mEditLabels);
        dismiss();
    }
}
