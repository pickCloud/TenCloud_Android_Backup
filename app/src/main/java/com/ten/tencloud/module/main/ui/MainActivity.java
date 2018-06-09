package com.ten.tencloud.module.main.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSFragmentPagerAdapter;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.broadcast.RefreshBroadCastHandler;
import com.ten.tencloud.model.AppBaseCache;
import com.ten.tencloud.module.app.ui.AppAddActivity;
import com.ten.tencloud.module.app.ui.AppDeployDetailsActivity;
import com.ten.tencloud.module.app.ui.AppServiceFragment;
import com.ten.tencloud.module.event.ui.EventHomeFragment;
import com.ten.tencloud.module.image.ui.ImageHomeFragment;
import com.ten.tencloud.module.main.contract.MainContract;
import com.ten.tencloud.module.main.presenter.MainPresenter;
import com.ten.tencloud.module.server.ui.ServerHomeFragment;
import com.ten.tencloud.module.user.ui.MineFragment;
import com.ten.tencloud.utils.UiUtils;
import com.ten.tencloud.widget.navlayout.NavLayout;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements MainContract.View {

    private String[] titles;
    private int[] iconRes = {R.mipmap.icon_nav01_normal, R.mipmap.icon_nav02_normal
            , R.mipmap.icon_nav03_normal, R.mipmap.icon_nav04_normal, R.mipmap.icon_nav05_normal};
    private int[] iconResSelected = {R.mipmap.icon_nav01_active, R.mipmap.icon_nav02_active
            , R.mipmap.icon_nav03_active, R.mipmap.icon_nav04_active, R.mipmap.icon_nav05_active};

    @BindView(R.id.nav_layout)
    NavLayout mNavLayout;
    @BindView(R.id.vp_content)
    ViewPager mVpContent;
    private TextView mTvMsgCount;
    private MainPresenter mMainPresenter;
    private RefreshBroadCastHandler mRefreshBroadCastHandler;
    private View mMsgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_main);
        mMsgView = View.inflate(this, R.layout.include_message, null);
        mTvMsgCount = mMsgView.findViewById(R.id.tv_msg_count);
        initTitleBar(false, getResources().getString(R.string.nav_01), mMsgView, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityNoValue(mContext, MsgActivity.class);
            }
        });
        init();
        initView();
        initMsg();
    }

    private void initMsg() {
        mMainPresenter.getMsgCount();
    }

    private void init() {
        titles = new String[]{getResources().getString(R.string.nav_01), getResources().getString(R.string.nav_02)
                , getResources().getString(R.string.nav_03), getResources().getString(R.string.nav_04)
                , getResources().getString(R.string.nav_05)};
        mMainPresenter = new MainPresenter();
        mMainPresenter.attachView(this);
        mRefreshBroadCastHandler = new RefreshBroadCastHandler(RefreshBroadCastHandler.PERMISSION_REFRESH_ACTION);
    }

    private void initView() {
        mNavLayout.setIconRes(iconRes)
                .setIconResSelected(iconResSelected)
                .setTextRes(titles)
                .setTextColor(getResources().getColor(R.color.text_color_899ab6))
                .setTextColorSelected(getResources().getColor(R.color.colorPrimary))
                .setMarginTop(UiUtils.dip2px(this, 1))
                .setTextSize(10)
                .setSelected(0);
        CJSFragmentPagerAdapter pagerAdapter = new CJSFragmentPagerAdapter(getSupportFragmentManager(), titles);
        pagerAdapter.addFragment(new ServerHomeFragment());
        pagerAdapter.addFragment(new AppServiceFragment());
        pagerAdapter.addFragment(new EventHomeFragment());
        pagerAdapter.addFragment(new ImageHomeFragment());
        pagerAdapter.addFragment(new MineFragment());
        mVpContent.setOffscreenPageLimit(titles.length);
        mVpContent.setAdapter(pagerAdapter);
        mVpContent.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mNavLayout.setSelected(position);
                changeTitle(titles[position]);
                if (position == 1){
                    initTitleBar(false, "应用列表", R.menu.menu_add_app, new OnMenuItemClickListener() {
                        @Override
                        public void onItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.menu_add_app:
                                    startActivityNoValue(mContext, AppDeployDetailsActivity.class);
                                    break;
                            }
                        }
                    });
                }else {
                    initTitleBar(false, getResources().getString(R.string.nav_01), mMsgView, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivityNoValue(mContext, MsgActivity.class);
                        }
                    });
                }
            }
        });
        mNavLayout.setOnItemSelectedListener(new NavLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int position) {
                mVpContent.setCurrentItem(position);
            }
        });
    }

    @Override
    public void showMsgCount(String count) {
        mTvMsgCount.setVisibility("0".equals(count) ? View.INVISIBLE : View.VISIBLE);
        mTvMsgCount.setText(count);
    }

    @Override
    public void updatePermission() {
        int cid = AppBaseCache.getInstance().getCid();
        mMainPresenter.getPermission(cid);
    }

    @Override
    public void updatePermissionSuccess() {
        mRefreshBroadCastHandler.sendBroadCast();
    }

    @Override
    public void updateAdminInfo() {
        mMainPresenter.isAdmin();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private long mExitTime;

    private void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            showMessage("再按一次退出");
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainPresenter.detachView();

    }
}
