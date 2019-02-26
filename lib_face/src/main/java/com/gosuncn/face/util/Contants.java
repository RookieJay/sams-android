package com.gosuncn.face.util;
/**
 * Project Name:sdkproject
 * File Name:Contants.java
 * Package Name:
 * Date:2016年7月11日下午2:59:50
 * Copyright @ 2010-2016 Cloudwalk Information Technology Co.Ltd All Rights Reserved.
 *
*/

import android.os.Environment;

/**
 * ClassName:Contants <br/>
 * Description: . <br/>
 * Date:     2016年7月11日 下午2:59:50 <br/>
 * @author   284891377
 * @version  
 * @since    JDK 1.7	 
 */
public class Contants {
	// 预览数据建议分辨率
	public static  int PREVIEW_W = 640;
	public static  int PREVIEW_H = 480;
	/**
	 * SD卡存储路径
	 */
	public static final String SDCARD_ROOT_PATH = Environment.getExternalStorageDirectory() + "/faceRecognize";

}

