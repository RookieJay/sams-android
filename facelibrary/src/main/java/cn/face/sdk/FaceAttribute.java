package cn.face.sdk;



public class FaceAttribute {

	static FaceAttribute attri = null;
	
	public FaceAttribute() {
		FaceCommon.loadLibrarys();
	}

	public static FaceAttribute getInstance() {

		if (null == attri) {
			attri = new FaceAttribute();
		}
		return attri;
	}


	// 人脸属性SDK
	static public native int cwCreateAttributeHandle(String pConfigurePath, String pLicence);

	static public native int cwReleaseAttributeHandle(int pAttributeHandle);
	
	static public native int cwGetAgeGenderEval(int pAttributeHandle, byte[] dataAlign, int iWidth, int iHeight, int iChannels, 
			                                    FaceAttrRet attr);

}
