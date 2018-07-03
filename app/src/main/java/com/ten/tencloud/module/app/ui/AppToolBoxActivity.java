package com.ten.tencloud.module.app.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.AppBean;
import com.ten.tencloud.constants.IntentKey;
import com.ten.tencloud.module.app.contract.ApplicationDelContract;
import com.ten.tencloud.module.app.presenter.AppDelPresenter;
import com.ten.tencloud.utils.StatusBarUtils;
import com.ten.tencloud.widget.blur.BlurBuilder;

import butterknife.BindView;
import butterknife.OnClick;

public class AppToolBoxActivity extends BaseActivity implements ApplicationDelContract.View {

    @BindView(R.id.iv_blur)
    ImageView mIvBlur;

    private AppBean mAppBean;
    private AppDelPresenter mAppDelPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_app_sub_toolbox);
        hideToolBar();
        StatusBarUtils.setColor(this, Color.BLACK);
        mAppBean = getIntent().getParcelableExtra(IntentKey.APP_ITEM);
        applyBlur();

        mAppDelPresenter = new AppDelPresenter();
        mAppDelPresenter.attachView(this);
    }

    @OnClick({R.id.btn_tool1, R.id.btn_tool2, R.id.btn_tool3, R.id.btn_tool4,
            R.id.btn_tool5, /*R.id.btn_tool6, R.id.btn_tool7,*/ R.id.btn_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_tool1: {
                Intent intent = new Intent(this, AppMakeImageStep1Activity.class);
                intent.putExtra(IntentKey.APP_ITEM, mAppBean);
                startActivity(intent);
                break;
            }
            case R.id.btn_tool2: {
                Intent intent = new Intent(this, AppK8sRegularDeployStep1Activity.class);
                intent.putExtra(IntentKey.APP_ITEM, mAppBean);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.btn_tool3:

                break;
            case R.id.btn_tool4:
                Intent intent = new Intent(this, APPServiceCreateStep1Activity.class);
                intent.putExtra(IntentKey.APP_ITEM, mAppBean);
                startActivity(intent);

                break;
            case R.id.btn_tool5:
                mAppDelPresenter.deleteApp(mAppBean.getId());
                break;
//            case R.id.btn_tool6: {
//                Intent intent = new Intent(this, APPServiceCreateStep1Activity.class);
//                intent.putExtra("appBean", mAppBean);
//                startActivity(intent);
//                break;
//            }
//            case R.id.btn_tool7:
//
//                break;
            case R.id.btn_close:
                finish();
                break;
        }
    }


    private void applyBlur() {
        mIvBlur.setImageBitmap(BlurBuilder.blur(mIvBlur));
        if (BlurBuilder.isBlurFlag()) {
            mIvBlur.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BlurBuilder.recycle();
    }

    @Override
    public void showResult(Object o) {
        setResult(RESULT_OK);
        finish();

    }
}
