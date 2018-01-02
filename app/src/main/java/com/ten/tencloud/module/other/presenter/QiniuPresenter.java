package com.ten.tencloud.module.other.presenter;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.User;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.other.contract.QiniuContract;
import com.ten.tencloud.module.other.model.QiniuModel;

import org.json.JSONObject;

import java.util.UUID;

/**
 * Created by lxq on 2018/1/2.
 */

public class QiniuPresenter extends BasePresenter<QiniuContract.View> implements QiniuContract.Presenter<QiniuContract.View> {

    private final UploadManager mUploadManager;

    public QiniuPresenter() {
        Configuration config = new Configuration.Builder()
                .connectTimeout(10)           // 链接超时。默认10秒
                .useHttps(true)               // 是否使用https上传域名
                .responseTimeout(60)          // 服务器响应超时。默认60秒
                .build();
        mUploadManager = new UploadManager(config);
    }

    @Override
    public void uploadFile(final String path) {
        mSubscriptions.add(QiniuModel.getInstance().getUploadToken()
                .subscribe(new JesSubscribe<User>(mView) {
                    @Override
                    public void _onSuccess(User user) {
                        String uuid = UUID.randomUUID().toString();
                        mUploadManager.put(path, uuid, user.getToken(), new UpCompletionHandler() {
                            @Override
                            public void complete(String key, ResponseInfo info, JSONObject response) {
                                if (info.isOK()) {
                                    mView.uploadSuccess(key);
                                } else {
                                    mView.uploadFiled();
                                }
                            }
                        }, new UploadOptions(null, null, false, new UpProgressHandler() {
                            @Override
                            public void progress(String key, double percent) {
                                if (percent == 1.0) {
                                    mView.hideLoading();
                                } else {
                                    mView.showLoading();
                                }
                            }
                        }, null));
                    }
                }));
    }
}
