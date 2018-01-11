package com.ten.tencloud.module.main.ui;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSFragmentPagerAdapter;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.module.main.model.MsgModel;

import butterknife.BindView;

public class MsgActivity extends BaseActivity {

    @BindView(R.id.tab)
    TabLayout mTab;
    @BindView(R.id.vp_msg)
    ViewPager mVpMsg;
    @BindView(R.id.ll_mode)
    LinearLayout mLlMode;
    @BindView(R.id.tv_mode)
    TextView mTvMode;
    @BindView(R.id.iv_option)
    ImageView mIvOption;
    @BindView(R.id.et_search)
    EditText mEtSearch;

    String[] titles = {"最新消息", "历史消息"};
    private CJSFragmentPagerAdapter mPagerAdapter;
    private PopupWindow mStatusPopupWindow;
    private TextView[] mTvStatusArray;
    private ImageView[] mIvStatusArray;
    private MsgFragment mNewFragment;
    private MsgFragment mHistoryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_msg);
        initTitleBar(true, "消息盒子");
        initView();
    }


    private void initView() {
        mVpMsg.setOffscreenPageLimit(2);
        mPagerAdapter = new CJSFragmentPagerAdapter(getFragmentManager(), titles);
        mNewFragment = new MsgFragment();
        mNewFragment.putArgument("status", "0");
        mPagerAdapter.addFragment(mNewFragment);
        mHistoryFragment = new MsgFragment();
        mHistoryFragment.putArgument("status", "1");
        mPagerAdapter.addFragment(mHistoryFragment);
        mVpMsg.setAdapter(mPagerAdapter);
        mTab.setupWithViewPager(mVpMsg);
        mLlMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStatusPopup();
            }
        });
        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search();
                }
                return false;
            }
        });
    }

    private String statusText = "全部";
    private String mode = MsgModel.MODE_ALL;

    private void showStatusPopup() {
        if (mStatusPopupWindow == null) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.pop_msg_status, null);
            TextView tvStatusALl = view.findViewById(R.id.tv_all);
            TextView tvStatusJoin = view.findViewById(R.id.tv_join);
            TextView tvStatusChange = view.findViewById(R.id.tv_change);
            TextView tvStatusLeave = view.findViewById(R.id.tv_leave);
            ImageView ivStatusALl = view.findViewById(R.id.iv_all);
            ImageView ivStatusJoin = view.findViewById(R.id.iv_join);
            ImageView ivStatusChange = view.findViewById(R.id.iv_change);
            ImageView ivStatusLeave = view.findViewById(R.id.iv_leave);
            mTvStatusArray = new TextView[]{tvStatusALl, tvStatusLeave, tvStatusJoin, tvStatusChange};
            mIvStatusArray = new ImageView[]{ivStatusALl, ivStatusLeave, ivStatusJoin, ivStatusChange};
            tvStatusALl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ("全部".equals(statusText)) {
                        return;
                    }
                    statusText = "全部";
                    mTvMode.setText(statusText);
                    mode = MsgModel.MODE_ALL;
                    search();
                    mStatusPopupWindow.dismiss();
                }
            });
            tvStatusJoin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ("加入企业".equals(statusText)) {
                        return;
                    }
                    statusText = "加入企业";
                    mTvMode.setText(statusText);
                    mode = MsgModel.MODE_JOIN;
                    search();
                    mStatusPopupWindow.dismiss();
                }
            });
            tvStatusLeave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ("企业变更".equals(statusText)) {
                        return;
                    }
                    statusText = "企业变更";
                    mTvMode.setText(statusText);
                    mode = MsgModel.MODE_CHANGE;
                    search();
                    mStatusPopupWindow.dismiss();
                }
            });
            tvStatusChange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ("离开企业".equals(statusText)) {
                        return;
                    }
                    statusText = "离开企业";
                    mTvMode.setText(statusText);
                    mode = MsgModel.MODE_LEAVE;
                    search();
                    mStatusPopupWindow.dismiss();
                }
            });
            mStatusPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            mStatusPopupWindow.setBackgroundDrawable(new BitmapDrawable());
            mStatusPopupWindow.setFocusable(true);
            mStatusPopupWindow.setOutsideTouchable(true);
            mStatusPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    mIvOption.animate().rotation(0);
                    mTvMode.setTextColor(getResources().getColor(R.color.text_color_556278));
                    mLlMode.setBackgroundResource(R.drawable.shape_round_3f4656_30);
                }
            });
        }
        if (!mStatusPopupWindow.isShowing()) {
            mIvOption.animate().rotation(180);
            mTvMode.setTextColor(getResources().getColor(R.color.text_color_899ab6));
            mLlMode.setBackgroundResource(R.drawable.shape_round_3f4656_top);
            if ("全部".equals(statusText)) {
                setPopSelect(0);
            } else if ("加入企业".equals(statusText)) {
                setPopSelect(1);
            } else if ("企业变更".equals(statusText)) {
                setPopSelect(2);
            } else if ("离开企业".equals(statusText)) {
                setPopSelect(3);
            }
            mStatusPopupWindow.showAsDropDown(mLlMode);
        }
    }

    private void setPopSelect(int pos) {
        for (int i = 0; i < mIvStatusArray.length; i++) {
            if (i == pos) {
                mIvStatusArray[i].setVisibility(View.VISIBLE);
                mTvStatusArray[i].setSelected(true);
            } else {
                mIvStatusArray[i].setVisibility(View.INVISIBLE);
                mTvStatusArray[i].setSelected(false);
            }
        }
    }

    private void search() {
        String key = mEtSearch.getText().toString().trim();
        int currentItem = mVpMsg.getCurrentItem();
        if (currentItem == 0) {
            mNewFragment.search(key, mode);
        } else {
            mHistoryFragment.search(key, mode);
        }
    }
}
