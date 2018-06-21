package com.ten.tencloud.widget.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.orhanobut.logger.Logger;
import com.ten.tencloud.R;
import com.ten.tencloud.utils.ToastUtils;
import com.ten.tencloud.utils.Utils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * 分享弹窗
 * <p>
 * Created by lxq on 2018/1/2.
 */

public class ShareDialog extends BottomSheetDialog {

    private Activity mActivity;
    private String mContent;
    private final UMShareListener mUmShareListener;

    public ShareDialog(@NonNull Activity activity) {
        super(activity);
        mActivity = activity;
        mUmShareListener = new UMShareListener() {

            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onResult(SHARE_MEDIA share_media) {
                ToastUtils.showShortToast("分享成功");
            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                throwable.printStackTrace();
                if (throwable.getMessage().contains("没有安装应用")) {
                    ToastUtils.showLongToast("分享失败，没有安装该应用");
                } else {
                    ToastUtils.showShortToast("分享失败");
                }
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {
            }
        };
    }

    public void setContent(String content) {
        mContent = content;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_share, null);
        View tvCancel = view.findViewById(R.id.tv_cancel);
        View tvWeixin = view.findViewById(R.id.tv_weixin);
        View tvQQ = view.findViewById(R.id.tv_qq);
        View tvMessage = view.findViewById(R.id.tv_message);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tvWeixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ShareAction(mActivity)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .withText(mContent)//分享内容
                        .setCallback(mUmShareListener)//回调监听器
                        .share();
                dismiss();
            }
        });
        tvQQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http" + mContent.split("http")[1];
                Logger.e(url);
                UMWeb umWeb = new UMWeb(url);
                umWeb.setTitle("拾云");
                umWeb.setDescription(mContent);
                umWeb.setThumb(new UMImage(mActivity, R.mipmap.ic_launcher));
                new ShareAction(mActivity)
                        .setPlatform(SHARE_MEDIA.QQ)
                        .withMedia(umWeb)
                        .setCallback(mUmShareListener)//回调监听器
                        .share();
                dismiss();
            }
        });
        tvMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.sendMsg(mActivity, mContent);
                dismiss();
            }
        });
        setContentView(view);
        Window window = getWindow();
        window.setBackgroundDrawable(new BitmapDrawable());
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
    }
}
