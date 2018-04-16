package com.ten.tencloud.module.image.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lxq on 2018/4/16.
 */

public class ImageHomeFragment extends BaseFragment {

    @BindView(R.id.tv_image_private_count)
    TextView mTvImagePrivateCount;
    @BindView(R.id.tv_image_shop_count)
    TextView mTvImageShopCount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return createView(inflater, container, R.layout.fragment_image_home);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }

    @OnClick({R.id.rl_image_private, R.id.rl_image_shop})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_image_private:

                break;
            case R.id.rl_image_shop:

                break;
        }
    }
}
