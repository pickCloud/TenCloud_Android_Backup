package com.ten.tencloud.module.server.model;

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

public class ServerAddModel {

    public final static String addUrl = "api/server/new";

    private WebSocketListener mWebSocketListener;
    private OkHttpClient mClient;
    private WebSocket mWebSocket;

    public ServerAddModel(ServerAddModel.onServerAddListener onServerAddListener) {
        this.onServerAddListener = onServerAddListener;
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
                    webSocket.cancel();
                    onServerAddListener.onSuccess();
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
                onServerAddListener.onFailure(t.getMessage());
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
        if (mWebSocket == null) {
            connect();
        }
        mWebSocket.send(data);
    }

    public void close() {
        mWebSocket.close(0, "");
    }

    public interface onServerAddListener {

        void onSuccess();

        void onFailure(String message);
    }

    private onServerAddListener onServerAddListener;
}

