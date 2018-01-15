package com.ten.tencloud.base.view;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ten.tencloud.BuildConfig;
import com.ten.tencloud.R;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.model.JesException;
import com.ten.tencloud.module.login.ui.LoginActivity;
import com.ten.tencloud.module.main.ui.MainActivity;
import com.ten.tencloud.module.other.ui.TestActivity;
import com.ten.tencloud.utils.ToastUtils;
import com.ten.tencloud.utils.UiUtils;
import com.ten.tencloud.widget.dialog.LoadDialog;

import butterknife.ButterKnife;


/**
 * Activity 基类，实现一些通用常见的方式
 * Created by lxq on 2017/11/20.
 */
public abstract class BaseActivity extends AppCompatActivity implements IBaseView, TextView.OnEditorActionListener {

    protected Toolbar mToolBar;
    private TextView mBarTitle;
    private TextView mBarTitleSub;
    private AppBarLayout appBar;
    private LinearLayout ll_search;
    private ImageButton ib_search;
    private ImageButton ib_add;

    protected Context mContext;

    //注销广播
    private BroadcastReceiver mLoginReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (mLoadDialog != null) {
                mLoadDialog.cancel();
            }
            if (Constants.LOGON_ACTION.equals(intent.getAction())) {
                Intent loginIntent = new Intent(mContext, LoginActivity.class);
                loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);//清空堆栈
                startActivity(loginIntent);
            } else if (Constants.MAIN_ACTION.equals(intent.getAction())) {
                Intent loginIntent = new Intent(mContext, MainActivity.class);
//                loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//清空堆栈
                startActivity(loginIntent);
            }

        }
    };
    private LoadDialog mLoadDialog;
    private ActionBar mActionBar;
    private int menuRes = -1;
    private EditText et_search;
    private TextView mTvRight;
    private ImageView mIvLeft;
    private FrameLayout mFlRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.LOGON_ACTION);
        filter.addAction(Constants.MAIN_ACTION);
        registerReceiver(mLoginReceiver, filter);
    }

    protected void createView(@LayoutRes int layoutId) {
        setContentView(R.layout.activity_base);
//        StatusBarUtils.setColorNoTranslucent(this, ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
        FrameLayout mFlContent = (FrameLayout) findViewById(R.id.fl_content);
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        mBarTitle = (TextView) findViewById(R.id.tv_bar_title);
        //测试
        mBarTitle.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (BuildConfig.DEBUG) {
                    startActivityNoValue(mContext, TestActivity.class);
                }
                return false;
            }
        });
        mBarTitleSub = (TextView) findViewById(R.id.tv_bar_title_sub);
        appBar = (AppBarLayout) findViewById(R.id.app_bar);
        ll_search = (LinearLayout) findViewById(R.id.ll_search);
        ib_search = (ImageButton) findViewById(R.id.ib_search);
        ib_add = (ImageButton) findViewById(R.id.ib_add);
        et_search = (EditText) findViewById(R.id.et_search);
        mTvRight = (TextView) findViewById(R.id.tv_right);
        mIvLeft = (ImageView) findViewById(R.id.iv_left);
        mFlRight = (FrameLayout) findViewById(R.id.fl_right);
        et_search.setOnEditorActionListener(this);
        mToolBar.setNavigationIcon(R.mipmap.icon_back);
        setSupportActionBar(mToolBar);
        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayShowTitleEnabled(false);
        }
        View view = View.inflate(this, layoutId, null);
        mFlContent.addView(view);
        ButterKnife.bind(this, view);
        UiUtils.getRootView(this).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
            }
        });
        setToolBarNoShade();
    }

    public void hideToolBar() {
        appBar.setVisibility(View.GONE);
    }

    /**
     * 设置Toolbar没阴影效果
     */
    public void setToolBarNoShade() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            appBar.setElevation(0);
        }
    }

    /**
     * 设置Toolbar没阴影效果
     */
    public void setToolBarHasShade() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            appBar.setElevation(10);
        }
    }

    protected void addFragment(int containerViewId, Fragment fragment, String fragmentTag) {
        addFragment(containerViewId, fragment, fragmentTag, false);
    }

    protected void addFragment(int containerViewId, Fragment fragment, String fragmentTag, boolean addToBackStack) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(containerViewId, fragment, fragmentTag);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    /**
     * 初始化标题栏
     *
     * @param isBack
     * @param title
     */
    public void initTitleBar(boolean isBack, String title) {
        mBarTitle.setText(title);
        mTvRight.setVisibility(View.GONE);
        mBarTitleSub.setVisibility(View.GONE);
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(isBack);
        }
    }

    public void initTitleBar(boolean isBack, @StringRes int title) {
        initTitleBar(isBack, getString(title));
    }

    public void initTitleBar(boolean isBack, String title, String subTitle) {
        initTitleBar(isBack, title);
        mBarTitleSub.setVisibility(View.VISIBLE);
        mBarTitleSub.setText(subTitle);
    }

    public void initTitleBar(boolean isBack, String title, String subTitle, String rightText, View.OnClickListener rightClickListener) {
        initTitleBar(isBack, title, subTitle);
        mTvRight.setVisibility(View.VISIBLE);
        mTvRight.setText(rightText);
        mTvRight.setOnClickListener(rightClickListener);
    }

    public void initTitleBar(boolean isBack, String title, String rightText, View.OnClickListener rightClickListener) {
        initTitleBar(isBack, title);
        mTvRight.setVisibility(View.VISIBLE);
        mTvRight.setText(rightText);
        mTvRight.setOnClickListener(rightClickListener);
    }

    public void initTitleBar(boolean isBack, String title, View rightView, View.OnClickListener rightClickListener) {
        initTitleBar(isBack, title);
        mFlRight.addView(rightView);
        mFlRight.setOnClickListener(rightClickListener);
    }

    public void initTitleBar(String title, @DrawableRes int leftResId, View.OnClickListener leftClickListener,
                             String rightText, View.OnClickListener rightClickListener) {
        initTitleBar(false, title);
        mIvLeft.setVisibility(View.VISIBLE);
        mIvLeft.setImageResource(leftResId);
        mIvLeft.setOnClickListener(leftClickListener);
        mTvRight.setText(rightText);
        mTvRight.setVisibility(View.VISIBLE);
        mTvRight.setOnClickListener(rightClickListener);
    }

    /**
     * 带菜单
     *
     * @param isBack
     * @param title
     * @param menuRes
     */
    public void initTitleBar(boolean isBack, String title, @MenuRes int menuRes, OnMenuItemClickListener onMenuItemClickListener) {
        initTitleBar(isBack, title);
        this.menuRes = menuRes;
        this.onMenuItemClickListener = onMenuItemClickListener;
    }

    public void initTitleBar(boolean isBack, @StringRes int title, @MenuRes int menuRes, OnMenuItemClickListener onMenuItemClickListener) {
        initTitleBar(isBack, getString(title), menuRes, onMenuItemClickListener);
    }

    /**
     * 设置Toolbar滑动Flag
     *
     * @param flag
     */
    public void setToolBarScrollFlag(int flag) {
        AppBarLayout.LayoutParams params =
                (AppBarLayout.LayoutParams) ((View) (mToolBar.getParent())).getLayoutParams();
        params.setScrollFlags(flag);
    }

    /**
     * 搜索框
     *
     * @param hintText
     * @param searchListener
     */
    public void showSearchView(String hintText, OnSearchListener searchListener) {
        showSearchView(hintText, searchListener, null);
    }

    public void showSearchView(String hintText, final OnSearchListener searchListener, View.OnClickListener addListener) {
        if (addListener == null) {
            ib_add.setVisibility(View.GONE);
        } else {
            ib_add.setVisibility(View.VISIBLE);
        }
        onSearchListener = searchListener;
        ll_search.setVisibility(View.VISIBLE);
        et_search.setHint(hintText);
        ib_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchListener.onSearch(et_search.getText().toString());
            }
        });
        ib_add.setOnClickListener(addListener);
    }

    private OnSearchListener onSearchListener;

    public interface OnSearchListener {
        void onSearch(String str);
    }

    public void hideSearchView() {
        ll_search.setVisibility(View.GONE);
    }

    public boolean onEditorAction(TextView tv, int actionId, KeyEvent event) {
        et_search.clearFocus();
        hideKeyboard();
        if (onSearchListener != null) {
            onSearchListener.onSearch(et_search.getText().toString());
        }
        return true;
    }


    /**
     * 定义菜单栏
     *
     * @param item
     * @return
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (onMenuItemClickListener != null) {
            onMenuItemClickListener.onItemClick(item);
        }

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    public interface OnMenuItemClickListener {
        void onItemClick(MenuItem item);
    }

    private OnMenuItemClickListener onMenuItemClickListener;

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.onMenuItemClickListener = onMenuItemClickListener;
    }

    /**
     * 修改标题
     *
     * @param title
     */
    public void changeTitle(String title) {
        mBarTitle.setText(title);
    }

    public void changeTitle(@StringRes int title) {
        changeTitle(getString(title));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (menuRes != -1) {
            getMenuInflater().inflate(menuRes, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    protected void startActivityNoValue(Context context, Class<?> clazz) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
    }

    @Override
    public void showLoading() {
        if (mLoadDialog == null) {
            mLoadDialog = new LoadDialog(this);
        }
        mLoadDialog.show();
    }

    @Override
    public void hideLoading() {
        if (mLoadDialog != null) {
            mLoadDialog.cancelDelay();
        }
    }

    @Override
    public void showError(JesException e) {
        showMessage(e.getMessage());
    }

    /**
     * 获取网络状态
     *
     * @param context
     * @return
     */
    protected boolean getNetworkStatus(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            return true;
        }
        return false;
    }

    protected void showToastMessage(String message) {
        if (!TextUtils.isEmpty(message)) {
            ToastUtils.showShortToast(message);
        }
    }

    protected void showToastMessage(@StringRes int messageId) {
        if (messageId != 0) {
            String message = mContext.getResources().getString(messageId);
            showToastMessage(message);
        }
    }

    @Override
    public void showMessage(@NonNull String message) {
        showToastMessage(message);
    }

    @Override
    public void showMessage(@StringRes int StringRes) {
        showToastMessage(StringRes);
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //反注册广播
        unregisterReceiver(mLoginReceiver);
        if (mLoadDialog != null) {
            mLoadDialog.cancel();
        }
        mLoadDialog = null;
    }

    public void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
