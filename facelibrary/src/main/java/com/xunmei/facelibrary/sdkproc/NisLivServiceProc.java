/**
 * Project Name:cloudwalk2
 * File Name:CloudwalkSDK.java
 * Package Name:cn.cloudwalk
 * Date:2016-4-27下午4:57:54
 * Copyright @ 2010-2016 Cloudwalk Information Technology Co.Ltd All Rights Reserved.
 */

package com.xunmei.facelibrary.sdkproc;

import android.content.Context;

import cn.face.sdk.FaceInterface;
import cn.face.sdk.FaceNisLivService;
import cn.face.sdk.FaceNisLiveResInfo;

/**
 * ClassName: NisLivServiceProc <br/>
 * Description: 红外活体检测封装接口使用示例，建议使用该示例，相对简单<br/>
 * date: 2018-1-17 下午4:57:54 <br/>
 *
 * @author 284891377
 * @since JDK 1.7
 */
public class NisLivServiceProc {

	public static final int ERRNISCODE_MIN = FaceInterface.cw_errcode_t.CW_UNKNOWN_ERR;
	static NisLivServiceProc livService;
	FaceNisLivService m_nirLivService = null;
	boolean m_bInitService = false;   // 是否初始化服务
	
	/**
	 * 单例实例化
	 *
	 * @param mContext
	 * @return NisLivenessSDK
	 */
	public static NisLivServiceProc getInstance(Context mContext) {

		if (null == livService) {
			livService = new NisLivServiceProc();
		}
		return livService;
	}

	private NisLivServiceProc() {

	}

	/**
	 * 初始化红外活体检测服务<br/>
	 *
	 * @param pLicence
	 *            授权码
	 * @param sModelDir
	 *            模型sd卡路径，默认：/sdcard/CWModels
	 * @author:284891377 Date: 2016-4-22 下午3:46:41
	 * @since JDK 1.7
	 * 初始化成功返回0，错误返回错误码
	 */
	public int StartNirLivService(String pLicence, String sModelDir, int iMinFace, int iMaxFace) {
		
		if (m_bInitService)
		{
			return FaceInterface.cw_errcode_t.CW_SDKLIT_OK;
		}
		
		m_nirLivService = FaceNisLivService.getInstance();
		float fThresh = 0.5f;    // 肤色阈值
		
		int ret = m_nirLivService.cwInitNirLivService(sModelDir, pLicence, fThresh, iMinFace, iMaxFace, 1);
		if (ret == FaceInterface.cw_errcode_t.CW_SDKLIT_OK)
		{
			m_bInitService = true;
		}

		return ret;
	}

	/**
	 * DestoryNisHandles:销毁句柄，资源释放 <br/>
	 *
	 * @author:284891377 Date: 2016-4-27 下午5:33:14
	 * @since JDK 1.7
	 */
	public int CloseNirLivService() {

		// 程序退出时再关闭服务，不要程序中反复初始化，又关闭
		if (m_bInitService) {
			return m_nirLivService.cwCloseNirLivService();
		}
		return 0;
	}


	/**
	 * FaceNirLiveDetect:红外活体检测br/>
	 * 
	 * @author:284891377 Date: 2016年9月21日 上午10:32:39
	 * @param dataVis
	 *            图片byte数据
	 * @return DetectBean @see cn.cloudwalk.sdk.DetectBean
	 * @since JDK 1.7
	 */
	public int FaceNirLiveDetect(byte[] dataVis, int widthVis, int heightVis, int formatVis, 
			byte[] dataNir, int widthNir, int heightNir, int formatNir, FaceNisLiveResInfo nirLivRet) {
		
		if (!m_bInitService) {
			return FaceInterface.cw_errcode_t.CW_UNINITIALIZED_ERR;   // 需要先初始化
		}
		
		return m_nirLivService.cwNirLivDetService(dataVis, widthVis, heightVis, formatVis, 
				dataNir, widthNir, heightNir, formatNir, nirLivRet);
	}

}