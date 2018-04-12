package com.ten.tencloud.module.app.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.reflect.TypeToken;
import com.socks.library.KLog;
import com.ten.tencloud.R;
import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.constants.Url;

import java.util.Map;

/**
 * Create by chenxh@10.com on 2018/4/10.
 */
public class WebViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_webview);
        changeTitle("Github 授权登录");

        WebView webView = (WebView) findViewById(R.id.web_view);
        if (!TextUtils.isEmpty(getIntent().getStringExtra("url"))) {
            webView.loadUrl(getIntent().getStringExtra("url"));
        }
        WebSettings setting = webView.getSettings();
        setting.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new InJavaScriptLocalObj(), "java_obj");

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候是控制网页在WebView中去打开，如果为false调用系统浏览器或第三方浏览器打开
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showLoading();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                view.loadUrl("javascript:window.java_obj.getSource(document.getElementsByTagName('pre')[0].innerHTML);");
                super.onPageFinished(view, url);
                hideLoading();
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                changeTitle(title);
            }

        });
    }

    public final class InJavaScriptLocalObj {

        @JavascriptInterface
        public void getSource(String html) {
            KLog.e(html);
            Map<String, String> resultMap = TenApp.getInstance().getGsonInstance().fromJson(html,
                    new TypeToken<Map<String, String>>() {
                    }.getType());
//            KLog.e(resultMap.get("status"));
//            KLog.e(resultMap.get("message"));
//            KLog.e(resultMap.get("data"));

            if (resultMap.get("status").equals("0")) {
                startActivityNoValue(mContext, RepositoryActivity.class);
                setResult(RESULT_OK);
            } else {
                showToastMessage(resultMap.get("message"));
            }
            finish();
        }
    }
}
