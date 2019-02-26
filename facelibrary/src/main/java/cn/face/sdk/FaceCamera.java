package cn.face.sdk;



public class FaceCamera {

	static FaceCamera camera = null;
	
	public FaceCamera() {
		FaceCommon.loadLibrarys();
	}

	public static FaceCamera getInstance() {

		if (null == camera) {
			camera = new FaceCamera();
		}
		return camera;
	}


	// 操作双目摄像头类
	
	// 打开双目摄像头：vis_id为可见光摄像头索引值，nis_id为红外摄像头索引值
	static public native int cwOpenCameras(int vis_id, int nis_id, int width, int height, int fps);
	
	// 关闭双目摄像头
	static public native int cwCloseCameras();

	// 抓取可见光摄像头一帧图片bgr数据，yuv数据可选
	// 若yuv420p_format为0，则vis_image_yuv420p为空，否则，获取vis_image_yuv420p数据
	static public native int cwGetVisImage(byte[] vis_image_bgr, byte[] vis_image_yuv420p, int yuv420p_format);
	
	// 抓取可见光摄像头一帧图片数据，数据格式为bgr，yuv数据可选
	static public native int cwGetNisImage(byte[] nis_image_bgr, byte[] nis_image_yuv420p, int yuv420p_format);
	
	// 打开一个摄像头，主要用来扫描有哪些索引值可以打开
	static public native int cwOpenCamera(int id, int width, int height, int fps);
		
		// 关闭一个摄像头
	static public native int cwCloseCamera();

}
