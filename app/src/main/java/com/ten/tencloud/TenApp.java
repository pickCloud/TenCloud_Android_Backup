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
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import io.objectbox.BoxStore;

/**
 * Created by lxq on 2017/11/20.
 */

/**
 *                             _ooOoo_
 *                            o8888888o
 *                            88" . "88
 *                            (| -_- |)
 *                            O\  =  /O
 *                         ____/`---'\____
 *                       .'  \\|     |//  `.
 *                      /  \\|||  :  |||//  \
 *                     /  _||||| -:- |||||-  \
 *                     |   | \\\  -  /// |   |
 *                     | \_|  ''\---/''  |   |
 *                     \  .-\__  `-`  ___/-. /
 *                   ___`. .'  /--.--\  `. . __
 *                ."" '<  `.___\_<|>_/___.'  >'"".
 *               | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 *               \  \ `-.   \_ __\ /__ _/   .-` /  /
 *          ======`-.____`-.___\_____/___.-`____.-'======
 *                             `=---='
 *          ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 *                     佛祖保佑        永无BUG
 *            佛曰:
 *                   写字楼里写字间，写字间里程序员；
 *                   程序人员写程序，又拿程序换酒钱。
 *                   酒醒只在网上坐，酒醉还来网下眠；
 *                   酒醉酒醒日复日，网上网下年复年。
 *                   但愿老死电脑间，不愿鞠躬老板前；
 *                   奔驰宝马贵者趣，公交自行程序员。
 *                   别人笑我忒疯癫，我笑自己命太贱；
 *                   不见满街漂亮妹，哪个归得程序员？
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
        //bugly
        CrashReport.initCrashReport(getApplicationContext(), "0537c3965d", BuildConfig.DEBUG);

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
     * 跳转登录页被踢出模式
     */
    public void jumpLoginForKickActivity(String msg) {
        AppBaseCache.getInstance().resetAppBaseCache();
        Intent intent = new Intent(Constants.LOGON_ACTION);
        intent.putExtra("type", 1);
        intent.putExtra("msg", msg);
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
