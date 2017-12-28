package com.ten.tencloud.module.server.model;

import com.socks.library.KLog;
import com.ten.tencloud.TenApp;
import com.ten.tencloud.constants.Url;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

/**
 * Created by lxq on 2017/12/12.
 */

public class ServerAddModel {

    public final static String addUrl = "/api/server/new";

    private WebSocketListener mWebSocketListener;
    private OkHttpClient mClient;

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
                onServerAddListener.setData(webSocket);
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
        Request request = new Request.Builder()
                .url(Url.WEBSOCKET_UEL_DEBUG + addUrl)
                .build();
        mClient.newWebSocket(request, mWebSocketListener);
    }

    public interface onServerAddListener {
        void setData(WebSocket webSocket);

        void onSuccess();

        void onFailure(String message);
    }

    private onServerAddListener onServerAddListener;
}

