package cn.face.sdk;


// 红外检测输入人脸图片参数
public class FaceNisLiveParam {

	public int nLandmarks;   // 人脸的关键点个数
	
	public int nirWidth;		// 输入NIR图像宽
	public int nirHeight;		// 输入NIR图像高	
	public int nirFormat;		// 输入NIR红外图像格式
	
	public int nirFaceNum;		 // NIR红外人脸个数
	
	public int visWidth;		// 输入VIS图像宽
	public int visHeight;		// 输入VIS图像高		
	public int visFormat;		// 输入VIS可见光图像格式
	
	public int visFaceNum;		 // VIR红外人脸个数
	
}