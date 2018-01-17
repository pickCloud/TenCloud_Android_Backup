package com.ten.tencloud.module.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.bean.MessageBean;
import com.ten.tencloud.constants.GlobalStatusManager;
import com.ten.tencloud.model.AppBaseCache;
import com.ten.tencloud.module.login.ui.JoinComStep2Activity;
import com.ten.tencloud.module.user.ui.CompanyInfoActivity;
import com.ten.tencloud.module.user.ui.EmployeeListActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by lxq on 2018/1/10.
 */

public class RvMsgAdapter extends CJSBaseRecyclerViewAdapter<MessageBean, RvMsgAdapter.ViewHolder> {

    public RvMsgAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder doOnCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_msg, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void doOnBindViewHolder(ViewHolder holder, int position) {
        MessageBean messageBean = datas.get(position);
        holder.tvTime.setText(messageBean.getUpdate_time());
        holder.tvContent.setText(messageBean.getContent());
        if (messageBean.getMode() == 1) {
            holder.tvMode.setText("加入企业");
        } else if (messageBean.getMode() == 2) {
            holder.tvMode.setText("企业变更");
        } else if (messageBean.getMode() == 3) {
            holder.tvMode.setText("离开企业");
        }
        final int subMode = messageBean.getSub_mode();
        if (subMode == 0) {
            holder.tvSubMode.setText("马上审核  >");
        } else if (subMode == 1) {
            holder.tvSubMode.setText("重新提交  >");
        } else if (subMode == 2) {
            holder.tvSubMode.setText("进入企业  >");
        } else if (subMode == 3) {
            holder.tvSubMode.setText("马上查看  >");
        }
        if (messageBean.getMode() == 3) {
            holder.tvSubMode.setVisibility(View.GONE);
        } else {
            holder.tvSubMode.setVisibility(View.VISIBLE);
        }
        final String tip = messageBean.getTip();

        holder.tvSubMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handClickByMode(subMode, tip);
            }
        });
    }

    private void handClickByMode(int subMode, String tip) {
        String[] tips = tip.split(":");
        String cid = tips[0];
        AppBaseCache.getInstance().setCid(Integer.parseInt(cid));
        GlobalStatusManager.getInstance().setCompanyListNeedRefresh(true);
        switch (subMode) {
            //马上审核
            case 0:
                Observable.just("").delay(1000, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                mContext.startActivity(new Intent(mContext, EmployeeListActivity.class));
                            }
                        });
                TenApp.getInstance().jumpMainActivity();
                break;
            //重新提交
            case 1:
                Intent intent = new Intent(mContext, JoinComStep2Activity.class);
                intent.putExtra("code", tips[1]);
                mContext.startActivity(intent);
                break;
            //进入企业
            case 2:
                TenApp.getInstance().jumpMainActivity();
                break;
            //马上查看
            case 3:
                Observable.just("").delay(1000, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                mContext.startActivity(new Intent(mContext, CompanyInfoActivity.class));
                            }
                        });
                TenApp.getInstance().jumpMainActivity();
                break;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_mode)
        TextView tvMode;
        @BindView(R.id.tv_sub_mode)
        TextView tvSubMode;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_content)
        TextView tvContent;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
