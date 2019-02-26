/**
 * Project Name:localSdkDemo
 * File Name:DetectBean.java
 * Package Name:cn.cloudwalk.sdk
 * Date:2016年9月21日上午10:17:22
 * Copyright @ 2010-2016 Cloudwalk Information Technology Co.Ltd All Rights Reserved.
 *
*/

package com.xunmei.facelibrary.sdkproc;

/**
 * ClassName:FeatureBean <br/>
 * Description: TODO Description. <br/>
 * Date: 2016年9月21日 上午10:17:22 <br/>
 * 
 * @author 284891377
 * @version
 * @since JDK 1.7
 */
public class FeatureBean {
	/**
	 * >=20000：错误码，-1：未检测到人脸，0：成功
	 */
	public int ret;
	
	/**
	 * true：filed特征，false：probe特征
	 */
	public boolean bFiled;
	/**
	 * 人脸特征
	 */
	public byte[] btFeature;
	
	public FeatureBean(int iFeaLen, boolean bIsFiled) {
		ret = -1;
		bFiled = bIsFiled;
		if (iFeaLen > 0) {
			btFeature = new byte[iFeaLen];
		}
	}
}
