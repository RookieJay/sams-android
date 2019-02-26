package com.gosuncn.face.listener;



public interface FaceInterface {


	// 图像旋转角度（逆时针）
	interface cw_img_angle_t extends FaceInterface {
		int CW_IMAGE_ANGLE_0 = 0;
		int CW_IMAGE_ANGLE_90 = 90;
		int CW_IMAGE_ANGLE_180 = 180;
		int CW_IMAGE_ANGLE_270 = 270;
	}

	// 图像镜像
	interface cw_img_mirror_t extends FaceInterface {
		int CW_IMAGE_MIRROR_NONE = 0;        // 不镜像
		int CW_IMAGE_MIRROR_HOR = 1;         // 水平镜像
		int CW_IMAGE_MIRROR_VER = 2;         // 垂直镜像
		int CW_IMAGE_MIRROR_HV = 3;          // 垂直和水平镜像
	}

}
