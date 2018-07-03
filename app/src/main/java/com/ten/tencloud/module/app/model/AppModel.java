package com.ten.tencloud.module.app.model;

import com.ten.tencloud.TenApp;
import com.ten.tencloud.bean.AppBean;
import com.ten.tencloud.bean.AppBrief;
import com.ten.tencloud.bean.AppContainerBean;
import com.ten.tencloud.bean.AppServiceYAMLBean;
import com.ten.tencloud.bean.DeploymentBean;
import com.ten.tencloud.bean.DeploymentInfoBean;
import com.ten.tencloud.bean.ImageBean;
import com.ten.tencloud.bean.LabelBean;
import com.ten.tencloud.bean.ReposBean;
import com.ten.tencloud.bean.ServiceBriefBean;
import com.ten.tencloud.model.HttpResultFunc;
import com.ten.tencloud.utils.RetrofitUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import okhttp3.RequestBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
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
                                     String repos_https_url, String logo_url, int image_id, List<Integer> labels, Integer master_app) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("name", name);
        hashMap.put("description", description);
        hashMap.put("repos_name", repos_name);
        hashMap.put("repos_ssh_url", repos_ssh_url);
        hashMap.put("repos_https_url", repos_https_url);
        hashMap.put("logo_url", logo_url);
        hashMap.put("image_id", image_id);
        if (master_app != null){
            hashMap.put("master_app", master_app);

        }
        Collections.sort(labels);
        hashMap.put("labels", labels);
        RequestBody body = RetrofitUtils.stringToJsonBody(TenApp.getInstance().getGsonInstance().toJson(hashMap));
        return TenApp.getRetrofitClient().getTenAppApi()
                .newApp(body)
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Object> updateApp(int id, String name, String description, String repos_name, String repos_ssh_url,
                                        String repos_https_url, String logo_url, List<Integer> labels, Integer master_app) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", id);
        hashMap.put("name", name);
        hashMap.put("description", description);
        hashMap.put("repos_name", repos_name);
        hashMap.put("repos_ssh_url", repos_ssh_url);
        hashMap.put("repos_https_url", repos_https_url);
        hashMap.put("logo_url", logo_url);
        if (master_app != null){
            hashMap.put("master_app", master_app);

        }
        Collections.sort(labels);
        hashMap.put("labels", labels);
        RequestBody body = RetrofitUtils.stringToJsonBody(TenApp.getInstance().getGsonInstance().toJson(hashMap));
        return TenApp.getRetrofitClient().getTenAppApi()
                .updateApp(body)
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

    public Observable<List<AppBean>> getAppListByPage(int page, int page_num, Integer label) {
        return TenApp.getRetrofitClient().getTenAppApi()
                .getAppListByPage(page, page_num, label)
                .map(new HttpResultFunc<List<AppBean>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<ServiceBriefBean>> getAppServiceBriefById(int app_id) {
        return TenApp.getRetrofitClient().getTenAppApi()
                .getAppServiceBriefById(app_id)
                .map(new HttpResultFunc<List<ServiceBriefBean>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<AppBean>> getAppById(int id) {
        return TenApp.getRetrofitClient().getTenAppApi()
                .getAppById(id)
                .map(new HttpResultFunc<List<AppBean>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<ReposBean>> getReposList(String url) {
        return TenApp.getRetrofitClient().getTenAppApi()
                .getReposList(url)
                .map(new HttpResultFunc<List<ReposBean>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<String>> getReposBranches(String repos_name, String url) {
        return TenApp.getRetrofitClient().getTenAppApi()
                .getReposBranches(repos_name, url)
                .map(new HttpResultFunc<List<Map<String, String>>>())
                .map(new Func1<List<Map<String, String>>, List<String>>() {
                    @Override
                    public List<String> call(List<Map<String, String>> maps) {
                        List<String> data = new ArrayList<>();
                        for (Map<String, String> map : maps) {
                            data.add(map.get("branch_name"));
                        }
                        return data;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<LabelBean> newLabel(String name, int type) {
        return TenApp.getRetrofitClient().getTenAppApi()
                .newLabel(name, type)
                .map(new HttpResultFunc<LabelBean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<TreeSet<LabelBean>> getLabelList(int type) {
        return TenApp.getRetrofitClient().getTenAppApi()
                .getLabelList(type)
                .map(new HttpResultFunc<TreeSet<LabelBean>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<ImageBean>> getAppImages(int appId) {
        return TenApp.getRetrofitClient().getTenAppApi()
                .getAppImages(appId)
                .map(new HttpResultFunc<List<ImageBean>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Object> checkDeployName(String name, int appId) {
        return TenApp.getRetrofitClient().getTenAppApi()
                .checkDeployName(name, appId)
                .map(new HttpResultFunc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Observable<String> generateDeployYAML(AppContainerBean bean) {
        RequestBody body = RetrofitUtils.stringToJsonBody(TenApp.getInstance().getGsonInstance().toJson(bean));
        return TenApp.getRetrofitClient().getTenAppApi()
                .generateDeployYAML(body)
                .map(new HttpResultFunc<String>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<String> generateServiceYAML(AppServiceYAMLBean bean) {
        RequestBody body = RetrofitUtils.stringToJsonBody(TenApp.getInstance().getGsonInstance().toJson(bean));
        return TenApp.getRetrofitClient().getTenAppApi()
                .generateServiceYAML(body)
                .map(new HttpResultFunc<String>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //获取部署列表
    public Observable<List<DeploymentBean>> getDeployList(Integer app_id, Integer status, Integer deployment_id, Integer show_yaml, Integer show_log, int page) {
        return TenApp.getRetrofitClient().getTenAppApi()
                .getDeployList(app_id, status, deployment_id, show_yaml, show_log, page, 20)
                .map(new HttpResultFunc<List<DeploymentBean>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //获取部署列表
    public Observable<List<DeploymentBean>> getDeployList(Integer app_id, Integer deployment_id, Integer show_yaml) {
        return TenApp.getRetrofitClient().getTenAppApi()
                .getDeployList(app_id, deployment_id, show_yaml)
                .map(new HttpResultFunc<List<DeploymentBean>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //获取部署详情
    public Observable<List<DeploymentInfoBean>> deploymentPods(Integer app_id, Integer show_verbose) {
        return TenApp.getRetrofitClient().getTenAppApi()
                .deploymentPods(app_id, show_verbose)
                .map(new HttpResultFunc<List<DeploymentInfoBean>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<DeploymentInfoBean>> deploymentReplicas(Integer app_id, Integer show_verbose) {
        return TenApp.getRetrofitClient().getTenAppApi()
                .deploymentReplicas(app_id, show_verbose)
                .map(new HttpResultFunc<List<DeploymentInfoBean>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //子应用列表
    public Observable<List<AppBean>> getAppSubApplicationList(int master_app) {
        return TenApp.getRetrofitClient().getTenAppApi()
                .getAppSubApplicationList(master_app)
                .map(new HttpResultFunc<List<AppBean>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //指定子应用
    public Observable<List<AppBean>> getAppSubApplicationById(int master_app, int id) {
        return TenApp.getRetrofitClient().getTenAppApi()
                .getAppSubApplicationById(master_app, id)
                .map(new HttpResultFunc<List<AppBean>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //最新的部署-子应用详情
    public Observable<DeploymentBean> getDeploymentLatestById(int id) {
        return TenApp.getRetrofitClient().getTenAppApi()
                .getDeploymentLatestById(id)
                .map(new HttpResultFunc<DeploymentBean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //删除应用
    public Observable<Object> applicationDel(int id) {
        return TenApp.getRetrofitClient().getTenAppApi()
                .applicationDel(id)
                .map(new HttpResultFunc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //清除github授权
    public Observable<Object> githubClear() {
        return TenApp.getRetrofitClient().getTenAppApi()
                .githubClear()
                .map(new HttpResultFunc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}