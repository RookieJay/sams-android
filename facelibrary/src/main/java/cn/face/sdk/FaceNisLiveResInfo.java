package cn.face.sdk;


// 人脸红外检测输出的可见光人脸活体信息
public class FaceNisLiveResInfo {
	public FaceNisLiveResInfo()
	{

	}
	
	public int livRst;    // 是否为活体或检测过程中的相关错误码
	
	public float score;   // 红外活体检测得分
}