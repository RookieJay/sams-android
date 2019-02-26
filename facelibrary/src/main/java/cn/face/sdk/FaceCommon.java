package cn.face.sdk;

public class FaceCommon {
	
	private static void loadLibrary(String libraryName) {
		System.loadLibrary(libraryName);
	}

	public static void loadLibrarys() {
		// TODO Auto-generated method stub
		loadLibrary("CWFaceDetTrack");
		loadLibrary("CloudWalkRecog");
		loadLibrary("CWFaceSDK");
		loadLibrary("CWFaceSDKJni");
	}
}
