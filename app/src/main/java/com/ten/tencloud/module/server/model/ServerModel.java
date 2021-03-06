package com.ten.tencloud.module.server.model;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.ten.tencloud.TenApp;
import com.ten.tencloud.bean.ClusterBean;
import com.ten.tencloud.bean.ClusterInfoBean;
import com.ten.tencloud.bean.K8sNodeBean;
import com.ten.tencloud.bean.ProviderBean;
import com.ten.tencloud.bean.ServerBatchBean;
import com.ten.tencloud.bean.ServerBean;
import com.ten.tencloud.bean.ServerDetailBean;
import com.ten.tencloud.bean.ServerHeatBean;
import com.ten.tencloud.bean.ServerHistoryBean;
import com.ten.tencloud.bean.ServerLogBean;
import com.ten.tencloud.bean.ServerMonitorBean;
import com.ten.tencloud.bean.ServerProviderBean;
import com.ten.tencloud.bean.ServerSystemLoadBean;
import com.ten.tencloud.bean.ServerThresholdBean;
import com.ten.tencloud.model.AppBaseCache;
import com.ten.tencloud.model.HttpResultFunc;
import com.ten.tencloud.utils.RetrofitUtils;

import org.yaml.snakeyaml.Yaml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.RequestBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by lxq on 2017/11/23.
 */

public class ServerModel {
    private static ServerModel INSTANCE;

    private ServerModel() {
    }

    public static synchronized ServerModel getInstance() {
        if (INSTANCE == null) {
            synchronized (ServerModel.class) {
                INSTANCE = new ServerModel();
            }
        }
        return INSTANCE;
    }

    /**
     * 获取集群列表
     *
     * @return
     */
    public Observable<List<ClusterBean>> getClusters() {
        return TenApp.getRetrofitClient().getTenServerApi().getClusterNode()
                .map(new HttpResultFunc<List<ClusterBean>>())
                .map(new Func1<List<ClusterBean>, List<ClusterBean>>() {
                    @Override
                    public List<ClusterBean> call(List<ClusterBean> clusterBeans) {
                        Yaml yaml = new Yaml();
                        Gson gson = TenApp.getInstance().getGsonInstance();
                        for (ClusterBean bean : clusterBeans) {
                            String k8sNode = bean.getNode();
                            if (!TextUtils.isEmpty(k8sNode)) {
                                Object load = yaml.load(k8sNode);
                                String json = gson.toJson(load);
                                K8sNodeBean k8sNodeBean = gson.fromJson(json, K8sNodeBean.class);
                                bean.setK8sNodeBean(k8sNodeBean);
                            }
                        }
                        return clusterBeans;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 服务器列表
     *
     * @param id
     * @return
     */
    public Observable<List<ServerBean>> getServerList(int id) {
        return TenApp.getRetrofitClient().getTenServerApi().getServerList(id)
                .map(new HttpResultFunc<ClusterInfoBean>())
                .map(new Func1<ClusterInfoBean, List<ServerBean>>() {
                    @Override
                    public List<ServerBean> call(ClusterInfoBean clusterInfoBean) {
                        return clusterInfoBean.getServer_list();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取提醒的机器
     *
     * @param id
     * @return
     */
    public Observable<List<ServerBean>> getWarnServerList(int id) {
        return TenApp.getRetrofitClient().getTenServerApi().getWarnServerList(id)
                .map(new HttpResultFunc<List<ServerBean>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 搜索
     *
     * @param clusterId
     * @param serverName
     * @param region
     * @param provider
     * @return
     */
    public Observable<List<ServerBean>> search(String clusterId, String serverName,
                                               List<String> region, Set<String> provider) {
        Map<String, Object> map = new HashMap<>();
        map.put("cluster_id", clusterId);
        map.put("server_name", serverName);
        map.put("region_name", region);
        map.put("provider_name", provider);
        RequestBody body = RetrofitUtils.stringToJsonBody(TenApp.getInstance().getGsonInstance().toJson(map));
        return TenApp.getRetrofitClient().getTenServerApi()
                .searchServer(body)
                .map(new HttpResultFunc<List<ServerBean>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 获取运营商
     *
     * @param id
     * @return
     */
    public Observable<List<ProviderBean>> getProvidersByCluster(String id) {
        return TenApp.getRetrofitClient().getTenServerApi()
                .getProvidersByCluster(id)
                .map(new HttpResultFunc<List<ProviderBean>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 详情
     *
     * @param id
     * @return
     */
    public Observable<ServerDetailBean> getServerDetail(String id) {
        return TenApp.getRetrofitClient().getTenServerApi()
                .getServerDetail(id)
                .map(new HttpResultFunc<ServerDetailBean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 资源概况
     *
     * @param id
     * @return
     */
    public Observable<ServerSystemLoadBean> getServerSystemLoad(String id) {
        return TenApp.getRetrofitClient().getTenServerApi()
                .getServerSystemLoad(id)
                .map(new HttpResultFunc<ServerSystemLoadBean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 重启
     *
     * @param id
     * @return
     */
    public Observable<Object> rebootServer(String id) {
        return TenApp.getRetrofitClient().getTenServerApi()
                .rebootServer(id)
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 开机
     *
     * @param id
     * @return
     */
    public Observable<Object> startServer(String id) {
        return TenApp.getRetrofitClient().getTenServerApi()
                .startServer(id)
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 关机
     *
     * @param id
     * @return
     */
    public Observable<Object> stopServer(String id) {
        return TenApp.getRetrofitClient().getTenServerApi()
                .stopServer(id)
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 查询服务器状态
     *
     * @param id
     * @return
     */
    public Observable<String> queryServerState(String id) {
        return TenApp.getRetrofitClient().getTenServerApi()
                .queryServerState(id)
                .map(new HttpResultFunc<String>());
    }


    /**
     * 删除
     *
     * @param id
     * @return
     */
    public Observable<Object> delServer(String id) {
        Map<String, Object> map = new HashMap<>();
        List<Integer> ids = new ArrayList<>();
        ids.add(Integer.parseInt(id));
        map.put("id", ids);
        RequestBody body = RetrofitUtils.stringToJsonBody(TenApp.getInstance().getGsonInstance().toJson(map));
        return TenApp.getRetrofitClient().getTenServerApi()
                .delServer(body)
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 容器列表
     *
     * @param id
     * @return
     */
    public Observable<List<List<String>>> getContaninersByServer(String id) {
        return TenApp.getRetrofitClient().getTenServerApi()
                .getContaninersByServer(id)
                .map(new HttpResultFunc<List<List<String>>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    /**
     * 日志信息
     *
     * @param id
     * @return
     */
    public Observable<List<ServerLogBean.LogInfo>> getServerLog(String id) {
        Map<String, String> map = new HashMap<>();
        map.put("object_type", "0");
        map.put("object_id", id);
        RequestBody body = RetrofitUtils.stringToJsonBody(TenApp.getInstance().getGsonInstance().toJson(map));
        return TenApp.getRetrofitClient().getTenServerApi()
                .getServerLog(body)
                .map(new HttpResultFunc<List<ServerLogBean.LogInfo>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取机器性能详情
     *
     * @param serverId
     * @param startTime
     * @param endTime
     * @return
     */
    public Observable<ServerMonitorBean> getServerPerformance(String serverId, String startTime, String endTime) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", serverId);
        map.put("start_time", startTime);
        map.put("end_time", endTime);
        map.put("type", 0);
        RequestBody body = RetrofitUtils.stringToJsonBody(TenApp.getInstance().getGsonInstance().toJson(map));
        return TenApp.getRetrofitClient().getTenServerApi()
                .getServerPerformance(body)
                .map(new HttpResultFunc<ServerMonitorBean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取历史记录
     *
     * @param serverId
     * @param type
     * @param startTime
     * @param endTime
     * @param page
     * @return
     */
    public Observable<List<ServerHistoryBean>> getServerHistory(String serverId, int type, String startTime, String endTime, int page) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", serverId);
        map.put("start_time", startTime);
        map.put("end_time", endTime);
        map.put("type", type);
        map.put("now_page", page);
        map.put("page_number", 20);
        RequestBody body = RetrofitUtils.stringToJsonBody(TenApp.getInstance().getGsonInstance().toJson(map));
        return TenApp.getRetrofitClient().getTenServerApi()
                .getServerHistory(body)
                .map(new HttpResultFunc<List<ServerHistoryBean>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 修改服务器名字
     *
     * @param id
     * @param name
     * @return
     */
    public Observable<Object> changeServerName(String id, String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("name", name);
        RequestBody body = RetrofitUtils.stringToJsonBody(TenApp.getInstance().getGsonInstance().toJson(map));
        return TenApp.getRetrofitClient().getTenServerApi()
                .changeServerInfo(body)
                .map(new HttpResultFunc<>())
                .delay(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 服务器首页摘要信息
     *
     * @return
     */
    public Observable<Map<String, Integer>> summary() {
        return TenApp.getRetrofitClient().getTenServerApi()
                .summary()
                .map(new HttpResultFunc<Map<String, Integer>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 服务器阈值
     *
     * @return
     */
    public Observable<ServerThresholdBean> getThreshold() {
        return TenApp.getRetrofitClient().getTenServerApi()
                .getThreshold()
                .map(new HttpResultFunc<ServerThresholdBean>())
                .map(new Func1<ServerThresholdBean, ServerThresholdBean>() {
                    @Override
                    public ServerThresholdBean call(ServerThresholdBean serverThresholdBean) {
                        AppBaseCache.getInstance().saveServerThreshold(serverThresholdBean);
                        return serverThresholdBean;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<ServerHeatBean>> getServerMonitor() {
        return TenApp.getRetrofitClient().getTenServerApi()
                .getServerMonitor()
                .map(new HttpResultFunc<List<ServerHeatBean>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取服务器运营商
     *
     * @return
     */
    public Observable<List<ServerProviderBean>> getServerProvides() {
        return TenApp.getRetrofitClient().getTenServerApi()
                .getServerProvides()
                .map(new HttpResultFunc<List<ServerProviderBean>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 提交服务器证书，获取主机列表
     *
     * @param cloudId
     * @param access_key
     * @param access_secret
     * @return
     */
    public Observable<List<ServerBatchBean>> submitCloudCredential(int cloudId, String access_key, String access_secret) {
        Map<String, Object> map = new HashMap<>();
        map.put("cloud_type", cloudId);
        Map<String, Object> temp = new HashMap<>();
        temp.put("access_key", access_key);
        temp.put("access_secret", access_secret);
        map.put("content", temp);
        RequestBody body = RetrofitUtils.stringToJsonBody(TenApp.getInstance().getGsonInstance().toJson(map));
        return TenApp.getRetrofitClient().getTenServerApi()
                .submitProviderCredential(body)
                .map(new HttpResultFunc<List<ServerBatchBean>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Object> importServers(List<ServerBatchBean> list) {
        Map<String, Object> map = new HashMap<>();
        map.put("data", list);
        RequestBody body = RetrofitUtils.stringToJsonBody(TenApp.getInstance().getGsonInstance().toJson(map));
        return TenApp.getRetrofitClient().getTenServerApi()
                .importServers(body)
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
