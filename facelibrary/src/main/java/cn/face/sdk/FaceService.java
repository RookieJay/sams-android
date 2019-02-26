package cn.face.sdk;



public class FaceService {

	static FaceService service = null;
	
	public FaceService() {
		FaceCommon.loadLibrarys();
	}

	public static FaceService getInstance() {

		if (null == service) {
			service = new FaceService();
		}
		return service;
	}


	static public native int cwInitSDKService(String sDetConfPath, String sRecogConfPath, String sLicence, int iMinFace, int iMaxFace, int iHandleNum);

	static public native int cwCloseSDKService();
	
	// det
	static public native int cwFaceServiceDet(byte[] imgData, int iWidth, int iHeight, int iFormat, byte[] outResult, int iBufLen);
	
	// feature
	static public native int cwFaceServiceGetFeaLength();
	
	static public native int cwFaceServiceGetFeature(byte[] imgData, int iWidth, int iHeight, int iFormat, byte[] outFeature);
	
	// 1:1
	static public native float cwFaceVerify(byte[] imgData1, int iWidth1, int iHeight1, int iFormat1, 
			byte[] imgData2, int iWidth2, int iHeight2, int iFormat2);
	
	// 1:N
	static public native int cwRegistFace(String sId, byte[] imgData, int iWidth, int iHeight, int iFormat);
	
	static public native int cwRegistFeature(String sId, byte[] feature);
	
	static public native int cwUnRegistFace(String sId);
	
	static public native int cwSearchFace(byte[] imgData, int iWidth, int iHeight, int iFormat, int iTopN, 
			byte[] outResult, int iBufLen);

}
