package com.ten.tencloud.module.other.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.orhanobut.logger.Logger;
import com.ten.tencloud.R;
import com.ten.tencloud.utils.SelectPhotoHelper;

import java.util.ArrayList;

public class SelectPhotoActivity extends TakePhotoActivity {

    private SelectPhotoHelper mPhotoHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = LayoutInflater.from(this).inflate(R.layout.select_photo_layout, null);
        setContentView(contentView);
        SelectPhotoHelper.Options options = new SelectPhotoHelper.Options();
        options.isCrop = true;
        options.isCompress = true;
        options.cropHeight = 400;
        options.cropWidth = 400;
        mPhotoHelper = new SelectPhotoHelper(options);
    }

    public void onClick(View view) {
        mPhotoHelper.onClick(false, getTakePhoto());
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        showImg(result.getImages());
    }

    private void showImg(ArrayList<TImage> images) {
        Logger.e(images.toString());
//        Intent intent=new Intent(this,ResultActivity.class);
//        intent.putExtra("images",images);
//        startActivity(intent);
    }

    public void onClickTake(View view) {
        mPhotoHelper.onClick(true, getTakePhoto());
    }
}
