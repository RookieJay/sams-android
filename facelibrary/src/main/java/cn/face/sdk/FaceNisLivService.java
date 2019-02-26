package cn.face.sdk;


public class FaceNisLivService {

	static FaceNisLivService nirLivService = null;
	
	public FaceNisLivService() {
		FaceCommon.loadLibrarys();
	}

	public static FaceNisLivService getInstance() {

		if (null == nirLivService) {
			nirLivService = new FaceNisLivService();
		}
		return nirLivService;
	}

	// 人脸红外活体检测封装接口
	static public native int cwInitNirLivService(String sCWModelsPath, String sLicence, float fSkinThresh, int iMinFace, int iMaxFace, int iHandleNum);

	static public native int cwCloseNirLivService();
	
	static public native int cwNirLivDetService(byte[] imgDataVis, int iWidthVis, int iHeightVis, int formatVis,
			byte[] imgDataNir, int iWidthNir, int iHeightNir, int formatNir, FaceNisLiveResInfo pNirLivRes);

}
