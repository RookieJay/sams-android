package com.xunmei.facelibrary.camera;

public interface Delegate {

	/**
	 * 处理打开相机出错
	 */
	void onOpenCameraError();

	void onFocus(float x, float y);

	void onFocused();
}
