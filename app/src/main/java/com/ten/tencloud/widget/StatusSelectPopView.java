package com.ten.tencloud.widget;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lxq on 2018/1/15.
 */

public class StatusSelectPopView extends FrameLayout {

    private Context mContext;

    @BindView(R.id.tv_status)
    TextView mTvStatus;
    @BindView(R.id.ll_status)
    LinearLayout mLlStatus;
    @BindView(R.id.iv_option)
    ImageView mIvOption;

    private List<String> mData;

    private PopupWindow mStatusPopupWindow;

    public StatusSelectPopView(Context context) {
        this(context, null);
    }

    public StatusSelectPopView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatusSelectPopView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_status_select_pop_view, this, true);
        ButterKnife.bind(this);
    }

    public void initData(List<String> data) {
        if (data == null) {
            throw new RuntimeException("数据为空");
        }
        //默认选中第一个
        mTvStatus.setText(data.get(0));
        mData = data;
    }

    @OnClick({R.id.ll_status})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_status:
                showStatusPopup();
                break;
        }
    }

    private void showStatusPopup() {
        if (mStatusPopupWindow == null) {
            RecyclerView rvStatus = new RecyclerView(mContext);
            rvStatus.setBackground(getResources().getDrawable(R.drawable.shape_round_status_select_bottom));
            rvStatus.setLayoutManager(new LinearLayoutManager(mContext));
            final StatusSelectPopAdapter adapter = new StatusSelectPopAdapter(mContext);
            adapter.setOnItemClickListener(new CJSBaseRecyclerViewAdapter.OnItemClickListener<String>() {
                @Override
                public void onObjectItemClicked(String s, int position) {
                    adapter.setSelectPos(position);
                    mTvStatus.setText(s);
                    if (onSelectListener != null) {
                        onSelectListener.onSelect(position);
                    }
                    mStatusPopupWindow.dismiss();
                }
            });
            rvStatus.setAdapter(adapter);
            adapter.setDatas(mData);
            mStatusPopupWindow = new PopupWindow(rvStatus, getWidth(),
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            mStatusPopupWindow.setBackgroundDrawable(new BitmapDrawable());
            mStatusPopupWindow.setFocusable(true);
            mStatusPopupWindow.setOutsideTouchable(true);
            mStatusPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    mIvOption.animate().rotation(0);
                    mTvStatus.setTextColor(getResources().getColor(R.color.text_color_556278));
                    mLlStatus.setBackgroundResource(R.drawable.shape_round_status_select);
                }
            });
        }
        mIvOption.animate().rotation(180);
        mTvStatus.setTextColor(getResources().getColor(R.color.text_color_899ab6));
        mLlStatus.setBackgroundResource(R.drawable.shape_round_status_select_top);
        mStatusPopupWindow.showAsDropDown(this);
    }

    public interface OnSelectListener {
        void onSelect(int pos);
    }

    private OnSelectListener onSelectListener;

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }
}