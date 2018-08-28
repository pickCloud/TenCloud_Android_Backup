package com.ten.tencloud.module.app.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.constant.TimeConstants;
import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.bean.DeploymentBean;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.utils.UiUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.internal.Utils;

/**
 * Created by chenxh@10.com on 2018/3/27.
 */
public class RvAppServiceDeploymentAdapter extends BaseQuickAdapter<DeploymentBean, BaseViewHolder> {


    public RvAppServiceDeploymentAdapter() {
        super(R.layout.item_app_service_deployment);
    }

    @Override
    protected void convert(BaseViewHolder helper, DeploymentBean item) {

        helper.setText(R.id.tv_name, item.getName());

        switch (item.getStatus()) {
            case Constants.DEPLOYMENT_STATUS_INIT:
//                holder.mTvStatus.setBackgroundResource(R.drawable.shape_app_status_init_round);
//                holder.mTvStatus.setCompoundDrawablesWithIntrinsicBounds(UiUtils.getDrawable(R.mipmap.icon_detail_green), null, null, null);
//                holder.mTvStatus.setTextColor(UiUtils.getColor(R.color.text_color_09bb07));
                helper.setText(R.id.tv_status, "进行中");
                break;
            case Constants.DEPLOYMENT_STATUS_NORMAL:
//                holder.mTvStatus.setBackgroundResource(R.drawable.shape_app_status_normal_round);
//                holder.mTvStatus.setCompoundDrawablesWithIntrinsicBounds(UiUtils.getDrawable(R.mipmap.icon_detail), null, null, null);
//                holder.mTvStatus.setTextColor(UiUtils.getColor(R.color.text_color_48bbc0));
                helper.setText(R.id.tv_status, "已完成");

                break;
            case Constants.DEPLOYMENT_STATUS_ERROR:
//                holder.mTvStatus.setBackgroundResource(R.drawable.shape_app_status_error_round);
//                holder.mTvStatus.setCompoundDrawablesWithIntrinsicBounds(UiUtils.getDrawable(R.mipmap.icon_detail_pink), null, null, null);
//                holder.mTvStatus.setTextColor(UiUtils.getColor(R.color.text_color_ef9a9a));
                helper.setText(R.id.tv_status, "失败");
                break;
        }

        helper.setText(R.id.tv_ysyy, item.getReplicas() + "");
        helper.setText(R.id.tv_dqyy, item.getReadyReplicas() + "");
        helper.setText(R.id.tv_kyyy, item.getAvailableReplicas() + "");
        helper.setText(R.id.tv_gxyy, item.getUpdatedReplicas() + "");
        helper.setText(R.id.tv_link_app, item.getApp_name());
        helper.setText(R.id.tv_create_date, item.getCreate_time());
        long sec = -TimeUtils.getTimeSpanByNow(item.getCreate_time(), TimeConstants.SEC);

        String formatTime = com.ten.tencloud.utils.Utils.formatTime(sec * 1000);
//        String utilStr = formatTime.substring(0, formatTime.length()-1);
        helper.setText(R.id.tv_running_time, formatTime.substring(0, formatTime.length()-1));
        helper.setText(R.id.tv_util, formatTime.substring(formatTime.length()-1, formatTime.length()));



    }

}
