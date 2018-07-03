package com.ten.tencloud.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.ten.tencloud.TenApp;
import com.ten.tencloud.bean.CompanyBean;
import com.ten.tencloud.bean.ContentInfoBean;
import com.ten.tencloud.bean.NetSpeedBean;
import com.ten.tencloud.model.AppBaseCache;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by lxq on 2017/11/20.
 */

public class Utils {
    /**
     * 处理金额
     *
     * @param num
     * @return
     */
    public static String handPrice(double num) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(num);
    }

    /**
     * 字符串为空时的默认值
     *
     * @param str
     * @param defaultStr
     * @return
     */
    public static String isEmptyDefaultForString(String str, String defaultStr) {
        return TextUtils.isEmpty(str) ? defaultStr : str;
    }

    /**
     * 手机号验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str) {
        Pattern p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 电话号码验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isPhone(String str) {
        Matcher m;
        boolean b;
        Pattern p1 = Pattern.compile("^[0][1-9]{2,3}[0-9]{5,10}$");  // 验证带区号的
        Pattern p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的
        if (str.length() > 9) {
            m = p1.matcher(str);
            b = m.matches();
        } else {
            m = p2.matcher(str);
            b = m.matches();
        }
        return b;
    }

    /**
     * 邮件验证
     *
     * @param str
     * @return
     */
    public static boolean isEmail(String str) {
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(str);
        return matcher.matches();
    }

    /**
     * 金额验证
     */
    public static boolean isPrice(String str) {
        Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式
        Matcher match = pattern.matcher(str);
        return match.matches();
    }

    public static ContentInfoBean handContent(String str) {
        return TenApp.getInstance().getGsonInstance().fromJson(str, ContentInfoBean.class);
    }

    public static String handNetSpeed(NetSpeedBean netSpeedBean) {
        if (netSpeedBean == null) {
            return "0/0";
        }
        String recv_speed = netSpeedBean.getInput();
        if (TextUtils.isEmpty(recv_speed)) {
            recv_speed = "0";
        }
        String send_speed = netSpeedBean.getOutput();
        if (TextUtils.isEmpty(send_speed)) {
            send_speed = "0";
        }
        return recv_speed + "/" + send_speed;
    }


    /**
     * 把手机号中间4位改成*
     *
     * @param phone
     * @return
     */
    public static String hide4Phone(String phone) {
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    public static String hide6Phone(String phone) {
        return phone.replaceAll("(\\d{3})\\d{6}(\\d{2})", "$1******$2");
    }

    public static String strIsEmptyForDefault(String str, String defaultStr) {
        return TextUtils.isEmpty(str) ? defaultStr : str;
    }


    /**
     * 是否具有某项权限
     *
     * @param permission
     * @return
     */
    public static boolean hasPermission(String permission) {
        //个人状态默认拥有所有权限
        if (AppBaseCache.getInstance().getCid() == 0) {
            return true;
        }
        CompanyBean companyWithLogin = AppBaseCache.getInstance().getSelectCompanyWithLogin();
        if (companyWithLogin != null) {
            int isAdmin = companyWithLogin.getIs_admin();
            if (isAdmin != 0) {
                return true;
            }
        }
        String userPermission = AppBaseCache.getInstance().getUserPermission();
        return userPermission.contains(permission);
    }

    /**
     * 发短信
     */
    public static void sendMsg(Context context, String content) {
        String smsBody = content;
        Uri smsToUri = Uri.parse("smsto:");
        Intent sendIntent = new Intent(Intent.ACTION_VIEW, smsToUri);
        //短信内容
        sendIntent.putExtra("sms_body", smsBody);
        sendIntent.setType("vnd.android-dir/mms-sms");
        context.startActivity(sendIntent);
    }

    /**
     * 打电话
     */
    public static void callPhone(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 跳转到某个页面从首页出发
     */
    public static void startActivityWithMain(final Context context, final Intent intent) {
        Observable.just("").delay(50, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        context.startActivity(intent);
                    }
                });

        TenApp.getInstance().jumpMainActivity();
    }

    /**
     * 忽略的包名
     */
    private static final List<String> VIEW_IGNORE_PACKAGE = Arrays.asList(
            "com.gh4a", "com.fastaccess", "com.taobao.taobao"
    );

    public static void openInBrowser(@NonNull Context context, @NonNull String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri).addCategory(Intent.CATEGORY_BROWSABLE);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent = createActivityChooserIntent(context, intent, uri, VIEW_IGNORE_PACKAGE);
        if (intent != null) {
            context.startActivity(intent);
        } else {
            ToastUtils.showLongToast("未找到浏览器");
        }
    }

    private static Intent createActivityChooserIntent(Context context, Intent intent,
                                                      Uri uri, List<String> ignorPackageList) {
        final PackageManager pm = context.getPackageManager();
        final List<ResolveInfo> activities = pm.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        final ArrayList<Intent> chooserIntents = new ArrayList<>();
        final String ourPackageName = context.getPackageName();

        Collections.sort(activities, new ResolveInfo.DisplayNameComparator(pm));

        for (ResolveInfo resInfo : activities) {
            ActivityInfo info = resInfo.activityInfo;
            if (!info.enabled || !info.exported) {
                continue;
            }
            if (info.packageName.equals(ourPackageName)) {
                continue;
            }
            if (ignorPackageList != null && ignorPackageList.contains(info.packageName)) {
                continue;
            }

            Intent targetIntent = new Intent(intent);
            targetIntent.setPackage(info.packageName);
            targetIntent.setDataAndType(uri, intent.getType());
            chooserIntents.add(targetIntent);
        }

        if (chooserIntents.isEmpty()) {
            return null;
        }

        final Intent lastIntent = chooserIntents.remove(chooserIntents.size() - 1);
        if (chooserIntents.isEmpty()) {
            return lastIntent;
        }

        Intent chooserIntent = Intent.createChooser(lastIntent, null);
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
                chooserIntents.toArray(new Intent[chooserIntents.size()]));
        return chooserIntent;
    }

    /*
     * 毫秒转化
     */
    public static String formatTime(long ms) {

        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

//        String strDay = day < 10 ? "0" + day : "" + day; //天
//        String strHour = hour < 10 ? "0" + hour : "" + hour;//小时
//        String strMinute = minute < 10 ? "0" + minute : "" + minute;//分钟
//        String strSecond = second < 10 ? "0" + second : "" + second;//秒
//        String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;//毫秒
//        strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;

        if (day > 0){
            return day + "d";
        }else if (hour > 0){
            return hour + "h";
        }else if (minute > 0){
            return minute + "m";
        }else {
            return  second + "s";
        }

    }

}
