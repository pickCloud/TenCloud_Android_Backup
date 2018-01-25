package com.ten.tencloud;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.socks.library.KLog;
import com.squareup.leakcanary.LeakCanary;
import com.ten.tencloud.bean.MyObjectBox;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.model.AppBaseCache;
import com.ten.tencloud.model.InitRetrofit;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import io.objectbox.BoxStore;

/**
 * Created by lxq on 2017/11/20.
 */

public class TenApp extends Application {

    private static TenApp sTenApp;
    private static InitRetrofit sInitRetrofit;
    private static Gson gson;
    private static BoxStore sBoxStore;

    public static TenApp getInstance() {
        return sTenApp;
    }

    {
        PlatformConfig.setWeixin("wxc1fa4f1bfc738872", "a899820621ce623d835c4caf9381762d");
        PlatformConfig.setQQZone("1106665152", "slhcMjf52PvQoMnO");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        sTenApp = this;
        sBoxStore = MyObjectBox.builder().androidContext(this).build();
        initRefreshView();
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, null);
        UMConfigure.setLogEnabled(true);
        UMShareAPI.get(this);
        //日志打印
        KLog.init(BuildConfig.DEBUG, Constants.PROJECT_NAME);
    }

    /**
     * 初始化下拉刷新样式
     */
    private void initRefreshView() {
        //设置统一的下拉样式
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                ClassicsHeader header = new ClassicsHeader(sTenApp);
                header.setTextSizeTitle(12)
                        .setTextSizeTime(10)
                        .setAccentColorId(R.color.text_color_899ab6)
                        .setBackgroundResource(R.color.default_bg);
                return header;
            }
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                ClassicsFooter footer = new ClassicsFooter(sTenApp);
                footer.setTextSizeTitle(12);
                footer.setAccentColorId(R.color.text_color_899ab6);
                return footer;
            }
        });
    }

    public synchronized static InitRetrofit getRetrofitClient() {
        if (sInitRetrofit == null) {
            sInitRetrofit = new InitRetrofit();
        }
        return sInitRetrofit;
    }

    /**
     * 重建Retrofit
     */
    public synchronized static void resetRetrofitClient() {
        sInitRetrofit = new InitRetrofit();
    }

    /**
     * 跳转登录页
     */
    public void jumpLoginActivity() {
        AppBaseCache.getInstance().resetAppBaseCache();
        Intent intent = new Intent(Constants.LOGON_ACTION);
        sendBroadcast(intent);
    }

    /**
     * 跳转到主页
     */
    public void jumpMainActivity() {
        Intent intent = new Intent(Constants.MAIN_ACTION);
        sendBroadcast(intent);
    }

    /**
     * 加密秘钥
     *
     * @return
     */
    public String getDESKey() {
        return getString(R.string.DES_KEY);
    }

    /**
     * Gson对象
     */
    public synchronized Gson getGsonInstance() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }

    public synchronized BoxStore getBoxStore() {
        return sBoxStore;
    }
}
