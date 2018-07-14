package com.ten.tencloud.module.app.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.AppBean;
import com.ten.tencloud.broadcast.RefreshBroadCastHandler;
import com.ten.tencloud.constants.IntentKey;
import com.ten.tencloud.model.JesException;
import com.ten.tencloud.module.app.contract.ApplicationDelContract;
import com.ten.tencloud.module.app.presenter.AppDelPresenter;
import com.ten.tencloud.utils.StatusBarUtils;
import com.ten.tencloud.widget.blur.BlurBuilder;

import butterknife.BindView;
import butterknife.OnClick;
import fr.tvbarthel.lib.blurdialogfragment.SupportBlurDialogFragment;

public class ServiceToolBoxActivity extends SupportBlurDialogFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Translucent_dialog);
        View view = getActivity().getLayoutInflater().inflate(R.layout.activity_service_toolbox, null);
        view.findViewById(R.id.btn_tool1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AppK8sRegularDeployStep3Activity.class);
                intent.putExtra(IntentKey.YAML, getArguments().getString(IntentKey.YAML));
                intent.putExtra(IntentKey.APP_ID, getArguments().getInt(IntentKey.APP_ID, -1));
                intent.putExtra(IntentKey.APP_NAME, getArguments().getString(IntentKey.APP_NAME));
                intent.putExtra(IntentKey.SERVICE_SOURCE, getArguments().getInt(IntentKey.SERVICE_SOURCE, 1));
                intent.putExtra(IntentKey.SERVICE_NAME,  getArguments().getString(IntentKey.SERVICE_NAME));
                intent.putExtra(IntentKey.SERVICE_TYPE, getArguments().getInt(IntentKey.SERVICE_TYPE, 1));
                intent.putExtra(IntentKey.SERVICE_ID, getArguments().getInt(IntentKey.SERVICE_ID, 1));
                startActivity(intent);
                dismiss();

            }
        });

        view.findViewById(R.id.btn_tool3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RefreshBroadCastHandler(RefreshBroadCastHandler.APP_SERVICE_DELETE_ACTION).sendBroadCast();
                dismiss();
            }
        });

        view.findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }


    @Override
    protected float getDownScaleFactor() {
        // Allow to customize the down scale factor.
        return (float) 5.0;
    }

    @Override
    protected int getBlurRadius() {
        // Allow to customize the blur radius factor.
        return 7;
    }

    @Override
    protected boolean isActionBarBlurred() {
        // Enable or disable the blur effect on the action bar.
        // Disabled by default.
        return true;
    }

    @Override
    protected boolean isDimmingEnable() {
        // Enable or disable the dimming effect.
        // Disabled by default.
        return true;
    }

    @Override
    protected boolean isRenderScriptEnable() {
        // Enable or disable the use of RenderScript for blurring effect
        // Disabled by default.
        return false;
    }

    @Override
    protected boolean isDebugEnable() {
        // Enable or disable debug mode.
        // False by default.
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        mBlurEngine.onDetach();
    }

    @Override
    public void onDestroyView() {
        if (getDialog() != null) {
            getDialog().setDismissMessage(null);
        }
        super.onDestroyView();
    }

}
