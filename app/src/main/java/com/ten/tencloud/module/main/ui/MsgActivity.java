package com.ten.tencloud.module.main.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSFragmentPagerAdapter;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.module.main.model.MsgModel;
import com.ten.tencloud.widget.StatusSelectPopView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MsgActivity extends BaseActivity {

    @BindView(R.id.tab)
    TabLayout mTab;
    @BindView(R.id.vp_msg)
    ViewPager mVpMsg;
    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.spv_mode)
    StatusSelectPopView mSpvMode;

    String[] titles = {"最新消息", "历史消息"};
    private CJSFragmentPagerAdapter mPagerAdapter;
    private MsgFragment mNewFragment;
    private MsgFragment mHistoryFragment;

    private String mode = MsgModel.MODE_ALL;

    private String[] modes = {MsgModel.MODE_ALL,
            MsgModel.MODE_JOIN,
            MsgModel.MODE_CHANGE,
            MsgModel.MODE_LEAVE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_msg);
        initTitleBar(true, "消息盒子");
        initView();
    }


    private void initView() {

        List<String> modeTitles = new ArrayList<>();
        modeTitles.add("全部");
        modeTitles.add("加入企业");
        modeTitles.add("企业变更");
        modeTitles.add("离开企业");
        mSpvMode.initData(modeTitles);
        mSpvMode.setOnSelectListener(new StatusSelectPopView.OnSelectListener() {
            @Override
            public void onSelect(int pos) {
                mode = modes[pos];
                search();
            }
        });

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
