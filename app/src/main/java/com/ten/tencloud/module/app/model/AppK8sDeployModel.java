package com.ten.tencloud.module.app.model;

import com.socks.library.KLog;
import com.ten.tencloud.TenApp;
import com.ten.tencloud.constants.Url;
import com.ten.tencloud.model.AppBaseCache;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

/**
 * Created by lxq on 2017/12/12.
 */

public class AppK8sDeployModel {

    public final static String addUrl = "/api/k8s_deploy";

    private WebSocketListener mWebSocketListener;
    private OkHttpClient mClient;
    private WebSocket mWebSocket;

    private boolean isSuccess = false;

    public AppK8sDeployModel(OnAppK8sDeployListener OnAppK8sDeployListener) {
        this.OnAppK8sDeployListener = OnAppK8sDeployListener;
        initSocket();
    }

    /**
     * 初始化Socket
     */
    private void initSocket() {
        mWebSocketListener = new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                KLog.d("连接成功");
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                KLog.d("接收==>" + text);
                if ("success".equals(text)) {
                    isSuccess = true;
                    if (OnAppK8sDeployListener != null) {
                        OnAppK8sDeployListener.onSuccess();
                    }
                } else if ("failure".equals(text)) {
                    if (OnAppK8sDeployListener != null) {
                        OnAppK8sDeployListener.onFailure("构建失败");
                    }
                } else if (!"open".equals(text)) {
                    if (OnAppK8sDeployListener != null) {
                        OnAppK8sDeployListener.onMessage(text);
                    }
                }
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                KLog.d("关闭");
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                t.printStackTrace();
                KLog.e(t.getMessage());
                if (!isSuccess) {
                    if (OnAppK8sDeployListener != null)
                        OnAppK8sDeployListener.onFailure(t.getMessage());
                }
            }
        };
//        mClient = new OkHttpClient();
        mClient = TenApp.getRetrofitClient().getOkHttpClient();
    }


    /**
     * 设置ip地址和端口号，请求连接
     */
    public void connect() {
        int cid = AppBaseCache.getInstance().getCid();
        String token = AppBaseCache.getInstance().getToken();
        String url = Url.BASE_WEBSOCTET_URL + addUrl + "?Cid=" + cid + "&Authorization=" + token;
        Request request = new Request.Builder()
                .url(url)
                .build();
        mWebSocket = mClient.newWebSocket(request, mWebSocketListener);
    }

    public void send(String data) {
        isSuccess = false;
        if (mWebSocket == null) {
            connect();
        }
        mWebSocket.send(data);
    }

    public void close() {
        if (mWebSocket != null) {
            mWebSocket.close(1000, "");
        }
    }

    public void onDestroy() {
        OnAppK8sDeployListener = null;
        mWebSocket = null;
    }

    public interface OnAppK8sDeployListener {

        void onSuccess();

        void onFailure(String message);

        void onMessage(String text);
    }

    private OnAppK8sDeployListener OnAppK8sDeployListener;
}

