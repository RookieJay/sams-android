package com.gosuncn.face.listener;

/**
 * @author: jayqiu
 * @date: 2018-09-27 14:53
 * @comment:
 */
public interface OnFrameListener<T> {
    void onPreviewFrame(T data, int rotation, int width, int height);
}
