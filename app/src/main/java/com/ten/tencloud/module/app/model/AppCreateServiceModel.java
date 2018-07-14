package com.ten.tencloud.module.app.model;

import android.util.Log;

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

public class AppCreateServiceModel {

    // TODO: 2018/5/15
    public final static String addUrl = "/api/k8s_service";

    private WebSocketListener mWebSocketListener;
    private OkHttpClient mClient;
    private WebSocket mWebSocket;

    private boolean isSuccess = false;

    public AppCreateServiceModel(OnAppServiceListener OnAppServiceListener) {
        this.OnAppServiceListener = OnAppServiceListener;
        initSocket();
    }

    /**
     * 初始化Socket
     */
    private void initSocket() {
        mWebSocketListener = new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                Log.d("接收","连接成功");
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                Log.d("接收", "接收==>" + text);
//                if ("success".equals(text)) {
//                    isSuccess = true;
//                    if (OnAppServiceListener != null) {
//                        OnAppServiceListener.onSuccess();
//                    }
//
//
//                }
                if (text.contains("service ID")) {
                    isSuccess = true;
                    if (OnAppServiceListener != null) {
                        OnAppServiceListener.onSuccess(text);
                    }
                }else if ("failure".equals(text)) {
                    if (OnAppServiceListener != null) {
                        OnAppServiceListener.onFailure("创建失败");
                    }
                } else if (!"open".equals(text)) {
                    if (OnAppServiceListener != null) {
                        OnAppServiceListener.onMessage(text);
                    }
                }
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                Log.d("接收", "关闭");
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                t.printStackTrace();
                Log.e("接收",t.getMessage());
                if (!isSuccess) {
                    if (OnAppServiceListener != null)
                        OnAppServiceListener.onFailure(t.getMessage());
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
        OnAppServiceListener = null;
        mWebSocket = null;
    }

    public interface OnAppServiceListener {

        void onSuccess(String service_id);

        void onFailure(String message);

        void onMessage(String text);
    }

    private OnAppServiceListener OnAppServiceListener;
}

