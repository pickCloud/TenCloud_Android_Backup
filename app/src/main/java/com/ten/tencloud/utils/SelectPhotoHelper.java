package com.ten.tencloud.utils;

import android.net.Uri;
import android.os.Environment;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.TakePhotoOptions;

import java.io.File;


/**
 * 照片选择帮助类
 */
public class SelectPhotoHelper {

    private Options mOptions;

    public SelectPhotoHelper(Options options) {
        mOptions = options;
    }

    /**
     * 点击事件
     *
     * @param isTake    是否为拍照和选择
     * @param takePhoto
     */
    public void onClick(boolean isTake, TakePhoto takePhoto) {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        Uri imageUri = Uri.fromFile(file);

        configCompress(takePhoto);
        configTakePhotoOption(takePhoto);

        if (isTake) {
            if (mOptions.isCrop) {
                takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());
            } else {
                takePhoto.onPickFromCapture(imageUri);
            }
        } else {
            if (mOptions.maxLimit > 1) {
                if (mOptions.isCrop) {
                    takePhoto.onPickMultipleWithCrop(mOptions.maxLimit, getCropOptions());
                } else {
                    takePhoto.onPickMultiple(mOptions.maxLimit);
                }
            }
            if (mOptions.isCrop) {
                takePhoto.onPickFromGalleryWithCrop(imageUri, getCropOptions());
            } else {
                takePhoto.onPickFromGallery();
            }
        }
    }

    /**
     * 拍照配置
     *
     * @param takePhoto
     */
    private void configTakePhotoOption(TakePhoto takePhoto) {
        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
        builder.setCorrectImage(true);//纠正旋转问题
        takePhoto.setTakePhotoOptions(builder.create());
    }

    /**
     * 配置压缩
     *
     * @param takePhoto
     */
    private void configCompress(TakePhoto takePhoto) {
        if (!mOptions.isCompress) {
            takePhoto.onEnableCompress(null, false);
            return;
        }
        int maxSize = mOptions.compressMaxSize;
        int width = mOptions.cropWidth;
        int height = mOptions.cropHeight;
        CompressConfig config = new CompressConfig.Builder()
                .setMaxSize(maxSize)
                .setMaxPixel(width >= height ? width : height)
                .enableReserveRaw(true)//保存原图
                .create();
        takePhoto.onEnableCompress(config, true);
    }

    /**
     * 获取剪切配置
     *
     * @return
     */
    private CropOptions getCropOptions() {
        if (!mOptions.isCrop) {
            return null;
        }
        int height = mOptions.cropHeight;
        int width = mOptions.cropWidth;
        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setOutputX(width).setOutputY(height);
        builder.setWithOwnCrop(true);//剪切工具
        return builder.create();
    }

    /**
     * 参数类
     */
    public static class Options {

        public int maxLimit = 1;//最大选择多少张

        public boolean isCrop;//是否剪切
        public int cropWidth;
        public int cropHeight;
        public boolean isCompress;//压缩
        public int compressMaxSize = 102400;//压缩最大值:b
    }

}
