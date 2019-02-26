package cn.face.sdk;



public class FaceNisLiveness {

	static FaceNisLiveness nirLive = null;
	
	public FaceNisLiveness() {
		FaceCommon.loadLibrarys();
	}

	public static FaceNisLiveness getInstance() {

		if (null == nirLive) {
			nirLive = new FaceNisLiveness();
		}
		return nirLive;
	}

	// 人脸红外检测SDK基础接口
	static public native int cwCreateNirLivenessHandle(String pNirModelPath, String pPairFilePath, String pLogDir, 
			float fSkinThresh, int camType, int detModel, String pLicence);

	static public native int cwReleaseNirLivenessHandle(int pHandle);
	
	static public native int cwFaceNirLivenessDet(int pHandle, FaceNisLiveParam pLivenessDetInfo, byte[] pVisData, 
			float[] visSkinScore, float[] visKeyPtScore, float[] visKeypt_x, float[] visKeypt_y, byte[] pNirData, 
			float[] nirSkinScore, float[] nirKeyPtScore,float[] nirKeypt_x, float[] nirKeypt_y, 
			FaceNisLiveResInfo[] pNirLivenessRes);

}
