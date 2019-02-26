package com.xunmei.facelibrary;

import android.os.Environment;

import java.io.File;

import cn.face.sdk.FaceVersion;

public class ConStant {

    public static String publicFilePath ;

    // 模型文件夹名
    public static final String ASSERT_MODULE_DIR = "CWModels";

    // 模型路径，建议将模型文件夹CWModels放置到sdcard根目录
    public static String sModelDir = Environment.getExternalStorageDirectory() + File.separator + "CWModels";

    // 授权码 ，由云从科技提供，也可调用网络授权接口cwGetLicence获取

    public static String sLicence = getLicence();
    //    public static String sLicence ="MTgxMzExYzc4OWY2ZmQ2ZmZhMzAwYmU1NjIzM2ZlYjU4ZjMwZTFjd2F1dGhvcml6Zf/m4+fk5+Li/+fg5efm4+f/4ufk4OXg5Yjm5uvl5ubrkeXm5uvl5uai6+Xm5uvl5uTm6+Xm5uDm1efr5+vn6+eB4OXm5ubl59Xj6+fr5+vn/+fi5ubm5A==";
    // 人脸检测最小最大人脸
    public static int faceMinSize = 30, faceMaxSize = 400;
    public static String getLicence(){
        return FaceVersion.getInstance().cwGetLicence("IIO90N", "R3DHAF");
    }
}
