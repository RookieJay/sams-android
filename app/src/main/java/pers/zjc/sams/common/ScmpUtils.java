package pers.zjc.sams.common;

import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.zp.android.zlib.utils.ImageUtils;
import com.zp.android.zlib.utils.SizeUtils;
import com.zp.android.zlib.utils.StringUtils;
import com.zp.android.zlib.utils.TimeUtils;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import pers.zjc.sams.BuildConfig;
import pers.zjc.sams.R;
import pers.zjc.sams.app.WindowActivity;
import pers.zjc.sams.widget.CustomPopWindow;

@SuppressWarnings("WeakerAccess")
public class ScmpUtils {

    public static void startWindow(Context context, String fragmentClaName) {
        startWindow(context, fragmentClaName, null);
    }

    public static void startWindow(Context context, String fragmentClaName, Bundle data) {
        Intent intent = new Intent(context, WindowActivity.class);
        intent.putExtra(WindowActivity.KEY_FRAGMENT, fragmentClaName);
        if (data != null) {
            intent.putExtras(data);
        }
        if (!(context instanceof Activity)) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    public static void startActivityForResult(Activity context, String fragmentClaName, Bundle data, int requestCode) {
        Intent intent = new Intent(context, WindowActivity.class);
        intent.putExtra(WindowActivity.KEY_FRAGMENT, fragmentClaName);
        if (data != null) {
            intent.putExtras(data);
        }
        context.startActivityForResult(intent, requestCode);
    }

    public static MaterialDialog.Builder createDialog(Context context, String title, String content, int buttonColor,
                                                      MaterialDialog.SingleButtonCallback positiveListener,
                                                      MaterialDialog.SingleButtonCallback negativeListener) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context).title(title)
                                                                            .titleColorRes(R.color.c333333)
                                                                            .titleGravity(GravityEnum.CENTER)
                                                                            .content(content)
                                                                            .contentColorRes(R.color.c333333)
                                                                            .contentGravity(GravityEnum.CENTER)
                                                                            .positiveText(R.string.dialog_confirm)
                                                                            .positiveColor(buttonColor)
                                                                            .negativeColor(buttonColor)
                                                                            .backgroundColorRes(R.color.cffffff)
                                                                            .negativeText(R.string.dialog_cancel);
        if (positiveListener != null) {
            builder.onPositive(positiveListener);
        }
        if (negativeListener != null) {
            builder.onNegative(negativeListener);
        }
        return builder;
    }

    public static String getAbsoluteUrl(String baseUrl, String relativeUrl) {
        if (baseUrl.endsWith("/")) {
            baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
        }
        if (!relativeUrl.startsWith("/")) {
            relativeUrl = "/" + relativeUrl;
        }
        return String.format("%s%s", baseUrl, relativeUrl);
    }

    public static String getDownloadUrl() {
        return getAbsoluteUrl(BuildConfig.BASE_URL, Const.UrlConst.URL_GET_FILE);
    }

    public static String getFaceDownloadUrl() {
        return getAbsoluteUrl(BuildConfig.BASE_URL, Const.UrlConst.URL_GET_FACE_FILE);
    }

    public static GlideUrl getFaceFileUrl(String fileId) {
        String url = String.format("%s?fileId=%s", ScmpUtils.getFaceDownloadUrl(), fileId);
        LazyHeaders.Builder builder = new LazyHeaders.Builder();
        builder.addHeader("fileId", fileId);
        builder.addHeader("isBase", "2");
        builder.addHeader("Content-Type", "application/json");
        return new GlideUrl(url, builder.build());
    }

    public static GlideUrl getFileUrl(String fileId, String token) {
        String url = String.format("%s?fileId=%s", ScmpUtils.getDownloadUrl(), fileId);
        LazyHeaders.Builder builder = new LazyHeaders.Builder();
        builder.addHeader("fileId", fileId);
        builder.addHeader("isBase", "2");
        builder.addHeader("Content-Type", "application/json");
        builder.addHeader("Authorization", token);
        return new GlideUrl(url, builder.build());
    }

    public static GlideUrl getGlideUrl(String url, String token, String fileId) {
        LazyHeaders.Builder builder = new LazyHeaders.Builder();
        builder.addHeader("fileId", fileId);
        builder.addHeader("isBase", "2");
        builder.addHeader("Content-Type", "application/json");
        builder.addHeader("Authorization", token);
        return new GlideUrl(url, builder.build());
    }

    public static String calcHalfHour(double diff) {
        double h = diff / (1000 * 60 * 60);
        long round = Math.round(h);
        long floor = (long)Math.floor(h);
        if (h == Math.floor(h)) {
            // 1, 2, 3 ...
            return String.valueOf((long)h);
        }
        else if (Math.ceil(h) == h + 0.5) {
            // 1.5, 2.5, 3.5 ...
            return String.valueOf(h);
        }
        else {
            if (round == floor) {
                // 2.1, 2.3, 2.4 ...
                return String.valueOf(floor + 0.5);
            }
            else {
                // 2.6, 2.7, 2.9 ...
                return String.valueOf(round);
            }
        }
    }

    public static Date dateCeil(String date) {
        Date now = TimeUtils.string2Date(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now == null ? new Date() : now);
        calendar.set(Calendar.HOUR_OF_DAY, 24);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static int tryParse(String value, int defaultValue) {
        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        }
        int result;
        try {
            result = Integer.parseInt(value);
        }
        catch (NumberFormatException e) {
            result = defaultValue;
        }
        return result;
    }

    public static long tryParse(String value, long defaultValue) {
        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        }
        long result;
        try {
            result = Integer.parseInt(value);
        }
        catch (NumberFormatException e) {
            result = defaultValue;
        }
        return result;
    }

    public static void showDescPopwind(View anchor, String content) {
        View view = LayoutInflater.from(anchor.getContext()).inflate(R.layout.pop_checkitem_des, null, false);
        TextView tvCheckDes = view.findViewById(R.id.tvCheckDes);
        if (!TextUtils.isEmpty(content)) {
            String str = content.replaceAll("\\\n", "");
            tvCheckDes.setText(str);
        }
        CustomPopWindow.PopupWindowBuilder builder = new CustomPopWindow.PopupWindowBuilder(anchor.getContext());
        builder.setView(view);
        builder.setFocusable(true);
        builder.setTouchable(true);
        builder.setOutsideTouchable(true);
        int width = SizeUtils.getMeasuredWidth(view);
        int center = anchor.getWidth() / 2 - width / 2;
        final CustomPopWindow popWindow = builder.create();
        popWindow.showAsDropDown(anchor, center, 0);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dissmiss();
            }
        });
    }
    public static boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
        boolean isSameMonth = isSameYear && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);

        return isSameDate;
    }

    public static boolean checkUsagePermission(Activity activity) {
        AppOpsManager appOps = (AppOpsManager)activity.getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow("android:get_usage_stats", android.os.Process.myUid(),
                activity.getPackageName());
        return mode == AppOpsManager.MODE_ALLOWED;
    }

    /**
     * 压缩图片 使用三种策略 1.去掉alpha值，2.缩放，3.降低质量
     * @param compressFile 压缩后的文件
     * @param srcFile 源文件
     */
    public static void compressPhoto(File compressFile, File srcFile) {
        if (compressFile == null || srcFile == null) {
            throw new IllegalArgumentException("compressFile or srcFile is null.");
        }
        // 1.去掉alpha值
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap src = BitmapFactory.decodeFile(srcFile.getAbsolutePath(), options);
        // 2.缩放 等比例缩放50%
        src = ImageUtils.scale(src, 0.5f, 0.5f);
        // 3.降低质量 质量为原图的60%
        src = ImageUtils.compressByQuality(src, 60);
        // 保存压缩后的文件
        ImageUtils.save(src, compressFile, Bitmap.CompressFormat.JPEG);
    }
}
