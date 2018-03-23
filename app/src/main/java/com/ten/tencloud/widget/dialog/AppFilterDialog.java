package com.ten.tencloud.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.ten.tencloud.R;
import com.ten.tencloud.bean.ProviderBean;
import com.ten.tencloud.utils.UiUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lxq on 2017/11/24.
 */

public class AppFilterDialog extends Dialog {

    private Context context;
    private FlexboxLayout mFblLabel;
    private LinearLayout mLlStatus;
    private Button mBtnOk;

    public AppFilterDialog(@NonNull Context context) {
        super(context, R.style.RightDialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉头
        Window window = getWindow();
        window.setGravity(Gravity.RIGHT);
        window.setContentView(R.layout.dialog_app_filter);
        mFblLabel = window.findViewById(R.id.fbl_label);
        mBtnOk = window.findViewById(R.id.btn_ok);
        mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAppFilterListener != null) {
                    mAppFilterListener.onOkClick(selects);
                }
                cancel();
            }
        });
        mLlStatus = findViewById(R.id.ll_status);
        window.setBackgroundDrawable(new BitmapDrawable());
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = UiUtils.getScreenMetrics(context).x / 4 * 3;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                if (mAppFilterListener != null) {
                    mAppFilterListener.getFilterData();
                }
            }
        });
    }

    public interface AppFilterListener {
        void getFilterData();

        void onOkClick(Map<String, Map<String, Boolean>> select);
    }

    private AppFilterListener mAppFilterListener;

    public void setAppFilterListener(AppFilterListener appFilterListener) {
        this.mAppFilterListener = appFilterListener;
    }

    /**
     * 设置数据
     *
     * @param providers
     */
    public void setData(List<ProviderBean> providers) {
        for (ProviderBean provider : providers) {
            createProviderView(provider);
        }
    }


    /**
     * 动态创建服务商
     *
     * @param provider
     */
    private void createProviderView(final ProviderBean provider) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_server_filter, null);
        final TextView tvName = view.findViewById(R.id.tv_name);
        final ImageView ivStatus = view.findViewById(R.id.iv_status);
        final FrameLayout flItem = view.findViewById(R.id.fl_item);
        tvName.setText(provider.getProvider());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                provider.setSelect(!provider.isSelect());
                tvName.setSelected(provider.isSelect());
                flItem.setSelected(provider.isSelect());
                ivStatus.setVisibility(provider.isSelect() ? View.VISIBLE : View.INVISIBLE);
                if (provider.isSelect()) {
                    createAreaView(provider);
                } else {
                    removeAreaView(provider);
                }
            }
        });
        mFblLabel.addView(view);
    }

    private void removeAreaView(ProviderBean provider) {
        View view = mLlStatus.findViewWithTag(provider.getProvider());
        mLlStatus.removeView(view);
        selects.remove(provider.getProvider());
    }

    Map<String, Map<String, Boolean>> selects = new HashMap<>();

    /**
     * 创建区域视图
     *
     * @param provider
     */
    private void createAreaView(ProviderBean provider) {
        final Map<String, Boolean> areaSelect = new HashMap<>();//记录当前选中的区域
        final View view = LayoutInflater.from(context).inflate(R.layout.include_server_filter_area, null);
        view.setTag(provider.getProvider());
        TextView tvProvider = view.findViewById(R.id.tv_provider);
        tvProvider.setText(provider.getProvider());
        FlexboxLayout fblArea = view.findViewById(R.id.fbl_area);
        for (final String area : provider.getRegions()) {
            areaSelect.put(area, false);//默认为未选中
            final View itemArea = LayoutInflater.from(context).inflate(R.layout.item_server_filter, null);
            final TextView tvName = itemArea.findViewById(R.id.tv_name);
            final ImageView ivStatus = itemArea.findViewById(R.id.iv_status);
            final FrameLayout flItem = itemArea.findViewById(R.id.fl_item);
            tvName.setText(area);
            fblArea.addView(itemArea);
            itemArea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    areaSelect.put(area, !areaSelect.get(area));
                    tvName.setSelected(areaSelect.get(area));
                    flItem.setSelected(areaSelect.get(area));
                    ivStatus.setVisibility(areaSelect.get(area) ? View.VISIBLE : View.INVISIBLE);
                }
            });
        }
        selects.put(provider.getProvider(), areaSelect);
        mLlStatus.addView(view);
    }
}
