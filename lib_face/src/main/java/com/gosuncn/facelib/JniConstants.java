package com.gosuncn.facelib;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Copyright © 1997 - 2018 Gosuncn. All Rights Reserved.
 * Created by Michael on 10/9/2018.
 * Contact：2751358839@qq.com
 * Describe：
 */
public class JniConstants {
    /*人脸识别流来源*/
    public static final int STREAM_IMG = 1;
    public static final int STREAM_VIDEO = 0;
    
    @IntDef({STREAM_IMG, STREAM_VIDEO})
    @Retention(RetentionPolicy.SOURCE)
    public @interface StreamType {
    }
    
    /*没有检测到人脸*/
    public static final int CODE_NOT_DETECT_FACE = -11;
}
