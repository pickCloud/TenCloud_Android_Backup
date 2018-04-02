package com.ten.tencloud.module.app.model;

import com.ten.tencloud.TenApp;
import com.ten.tencloud.bean.AppBean;
import com.ten.tencloud.bean.AppBrief;
import com.ten.tencloud.model.HttpResultFunc;
import com.ten.tencloud.utils.RetrofitUtils;

import java.util.HashMap;
import java.util.List;

import okhttp3.RequestBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AppModel {

    private AppModel() {
    }

    private static class SingletonInstance {
        private static final AppModel INSTANCE = new AppModel();
    }

    public static AppModel getInstance() {
        return SingletonInstance.INSTANCE;
    }

    public Observable<AppBrief> getAppBrief() {
        return TenApp.getRetrofitClient().getTenAppApi()
                .getAppBrief()
                .map(new HttpResultFunc<AppBrief>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Object> newApp(String name, String description, String repos_name, String repos_ssh_url,
                                     String repos_https_url, String logo_url, int image_id) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("name", name);
        hashMap.put("description", description);
        hashMap.put("repos_name", repos_name);
        hashMap.put("repos_ssh_url", repos_ssh_url);
        hashMap.put("repos_https_url", repos_https_url);
        hashMap.put("logo_url", logo_url);
        hashMap.put("image_id", image_id);
        RequestBody body = RetrofitUtils.stringToJsonBody(TenApp.getInstance().getGsonInstance().toJson(hashMap));
        return TenApp.getRetrofitClient().getTenAppApi()
                .newApp(body)
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<AppBean>> getAppList() {
        return TenApp.getRetrofitClient().getTenAppApi()
                .getAppList()
                .map(new HttpResultFunc<List<AppBean>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<AppBean>> getAppListByPage(int page, int page_num) {
        return TenApp.getRetrofitClient().getTenAppApi()
                .getAppListByPage(page, page_num)
                .map(new HttpResultFunc<List<AppBean>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}