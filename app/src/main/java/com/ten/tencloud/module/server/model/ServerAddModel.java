package com.ten.tencloud.module.server.model;


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

public class ServerAddModel {

    public final static String addUrl = "/api/server/new";

    private WebSocketListener mWebSocketListener;
    private OkHttpClient mClient;
    private WebSocket mWebSocket;

    private boolean isSuccess = false;

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
                Log.d("接收", "连接成功");
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                Log.d("接收" ,"接收==>" + text);
                if ("success".equals(text)) {
                    isSuccess = true;
                    if (onServerAddListener != null)
                        onServerAddListener.onSuccess();
                } else if ("failure".equals(text)) {
                    if (onServerAddListener != null)
                        onServerAddListener.onFailure("主机添加失败");
                } else if (!"open".equals(text)) {
                    if (onServerAddListener != null)
                        onServerAddListener.onMessage(text);
                }
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
//                KLog.d("关闭");
                Log.d("接收", "hello");

            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                t.printStackTrace();
                Log.e("接收", t.getMessage());

                if (!isSuccess) {
                    if (onServerAddListener != null)
                        onServerAddListener.onFailure(t.getMessage());
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
        onServerAddListener = null;
        mWebSocket = null;
    }

    public interface onServerAddListener {

        void onSuccess();

        void onFailure(String message);

        void onMessage(String text);
    }

    private onServerAddListener onServerAddListener;
}

