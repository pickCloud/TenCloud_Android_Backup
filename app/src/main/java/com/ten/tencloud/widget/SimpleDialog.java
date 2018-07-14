//package com.ten.tencloud.widget;
//
//import android.app.Dialog;
//import android.content.DialogInterface;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v7.app.AlertDialog;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.TextView;
//
//import com.ten.tencloud.R;
//
//import org.greenrobot.eventbus.EventBus;
//
//import fr.tvbarthel.lib.blurdialogfragment.SupportBlurDialogFragment;
//
///**
// * Created by mqwang on 2017/5/20.
// */
//
//public class SimpleDialog extends SupportBlurDialogFragment {
//    private TextView descTv;
//
////    private BlurDialogEngine mBlurEngine;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
////        mBlurEngine = new BlurDialogEngine(getActivity());
////        mBlurEngine.setBlurRadius(8);
////        mBlurEngine.setDownScaleFactor(8f);
////        mBlurEngine.debug(true);
////        mBlurEngine.setBlurActionBar(true);
////        mBlurEngine.setUseRenderScript(true);
//
//    }
//
//    @NonNull
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        QueueModel queueModel = getArguments().getParcelable(ExtraAction.QUEUE_ITEM);
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Translucent_dialog);
//        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_fragment, null);
//        descTv = ((TextView) view.findViewById(R.id.desc_tv));
//
//        if (TextUtils.isEmpty(queueModel.channelName) && queueModel.waitCount == 0){
//            descTv.setText("请耐心等待，\n暂时没有买手在线");
//
//        }else
//            descTv.setText("排队中，前面还有" + queueModel.waitCount + "位顾客\n估计时间" + queueModel.waitCount + "分钟");
//
////        label.setMovementMethod(LinkMovementMethod.getInstance());
////        Linkify.addLinks(label, Linkify.WEB_URLS);
//        builder.setView(view);
//        Dialog dialog = builder.create();
//        dialog.setCanceledOnTouchOutside(false);
//
//        return dialog;
//    }
//
//
//    @Override
//    protected float getDownScaleFactor() {
//        // Allow to customize the down scale factor.
//        return (float) 5.0;
//    }
//
//    @Override
//    protected int getBlurRadius() {
//        // Allow to customize the blur radius factor.
//        return 7;
//    }
//
//    @Override
//    protected boolean isActionBarBlurred() {
//        // Enable or disable the blur effect on the action bar.
//        // Disabled by default.
//        return true;
//    }
//
//    @Override
//    protected boolean isDimmingEnable() {
//        // Enable or disable the dimming effect.
//        // Disabled by default.
//        return true;
//    }
//
//    @Override
//    protected boolean isRenderScriptEnable() {
//        // Enable or disable the use of RenderScript for blurring effect
//        // Disabled by default.
//        return false;
//    }
//
//    @Override
//    protected boolean isDebugEnable() {
//        // Enable or disable debug mode.
//        // False by default.
//        return false;
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
////        mBlurEngine.onResume(getRetainInstance());
//    }
//
//    @Override
//    public void onDismiss(DialogInterface dialog) {
//        super.onDismiss(dialog);
////        mBlurEngine.onDismiss();
//        EventBus.getDefault().post("dissmiss");
//
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
////        mBlurEngine.onDetach();
//    }
//
//    @Override
//    public void onDestroyView() {
//        if (getDialog() != null) {
//            getDialog().setDismissMessage(null);
//        }
//        super.onDestroyView();
//    }
//
//    public void setInfo(QueueModel queueModel){
//        if (TextUtils.isEmpty(queueModel.channelName) && queueModel.waitCount == 0){
//            descTv.setText("请耐心等待，\n暂时没有买手在线");
//
//        }else
//            descTv.setText("排队中，前面还有" + queueModel.waitCount + "位顾客\n估计时间" + queueModel.waitCount + "分钟");
////        descTv.setText("排队中，前面还有" + queueModel.waitCount + "位顾客\n估计时间" + queueModel.waitCount + "分钟");
//
//    }
//
//}
