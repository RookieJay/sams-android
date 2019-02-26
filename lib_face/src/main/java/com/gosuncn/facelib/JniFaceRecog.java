package com.gosuncn.facelib;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.gosuncn.facelib.bean.FaceDetectInfo;

/**
 * Copyright © 1997 - 2018 Gosuncn. All Rights Reserved.
 * Created by Michael on 9/30/2018.
 * Contact：2751358839@qq.com
 * Describe：人脸识别Jni
 */
public class JniFaceRecog {
    private static final String TAG = "JniFaceRecog";

    static {
        System.loadLibrary("GSFaceRecog");
    }


    private static class JniFaceRecogHolder {
        private static final JniFaceRecog INSTANCE = new JniFaceRecog();
    }


    public static JniFaceRecog getInstance() {
        return JniFaceRecogHolder.INSTANCE;
    }

    private FaceRecogCallBack faceRecogCallBack;

    public void setFaceRecogCallBack(FaceRecogCallBack faceRecogCallBack) {
        this.faceRecogCallBack = faceRecogCallBack;
    }

    public void removeFaceRecogCallBack() {
        this.faceRecogCallBack = null;
    }

    //-----------------------------------------native 方法---------------------------------------------//

    /**
     * 人脸识别初始化
     *
     * @return
     */
    public native int init();

    /**
     * 人脸识别反初始化
     */
    public native int cleanUp();

    /**
     * 得到最近一次的错误码
     *
     * @return
     */
    public native int getLastErrorCode();

    /**
     * 创建人脸识别实例
     *
     * @param modelPath model存放路径 ,如"/data/faceRecognize/model/"
     * @return
     */
    public native int detectorCreate(String modelPath);

   /* public int detectorCreate(Context context) throws IOException {
        String[] models = context.getAssets().list("model");
        for (String s : models) {
            Log.d(TAG, "detectorCreate: name = " + s);
        }
        // int ret = detectorCreate()
        return 1;
    }*/


    /**
     * 释放人脸识别实例
     *
     * @return
     */
    public native int detectorRelease();


    /**
     * YUV-NV21转换为BGR图像
     * 注意转换后，宽高不变
     *
     * @param imgYuv NV21图像数据
     * @param width  图像宽
     * @param height 图像高
     * @return
     */
    public native int faceImgNV21toBGR(byte[] imgYuv, int width, int height);


    /**
     * 对一帧数据检测人脸位置
     *
     * @param bgr        bgr图像数据
     * @param width      bgr图像数据 宽度
     * @param height     bgr图像数据 高度
     * @param streamType 流类型：0表示视频流(带跟踪id号)，1表示图片流
     * @param threadNums 运行该算法调用的线程数量（在合理范围内，数值越大则cpu占用越高，速度越快）
     * @return
     */
    public native int faceDetect(byte[] bgr, int width, int height, @JniConstants.StreamType int streamType, int threadNums);


    /**
     * 提取人脸特征（合并到上一步的人脸检测中）
     *
     * @param bgr        bgr图像数据
     * @param width      bgr图像数据 宽度
     * @param height     bgr图像数据 高度
     * @param faceTrack  人脸区域，实际坐标值
     *                   int nX;                                     //人脸区域左上角横坐标
     *                   int nY;                                     //人脸区域左上角纵坐标
     *                   int nWidth;                                 //人脸区域宽度
     *                   int nHeight;                                //人脸区域高度
     *                   int nID;                                    //人脸跟踪ID
     * @param facePoints 人脸特征点集
     * @param threadNums 运行该算法调用的线程数量（在合理范围内，数值越大则cpu占用越高，速度越快）
     * @return
     */
    //  public native int getFaceFeature(byte[] bgr, int width, int height, int[] faceTrack, float[][] facePoints, int threadNums);


    /**
     * 对2个人脸特征数据进行比对，得到相似度0-1
     *
     * @param faceFeature1       getFaceFeature获取到的人脸特征向量1
     * @param faceFeatureLength1 人脸特征长度1
     * @param faceFeature2       getFaceFeature获取到的人脸特征向量2
     * @param faceFeatureLength2 人脸特征长度2
     * @return
     */
    public native int compareFeature(byte[] faceFeature1, int faceFeatureLength1, byte[] faceFeature2, int faceFeatureLength2);


    //-------------------------------------------Jni层回调接口方法---------------------------------------------//

    /**
     * NV21 to BGR 数据回调
     *
     * @param bgr
     * @param outWidth
     * @param outHeight
     */
    public void JniGetNV21toBGRCallBack(byte[] bgr, int outWidth, int outHeight) {
        Log.d(TAG, "JniGetNV21toBGRCallBack: outWidth = " + outWidth + " outHeight = " + outHeight);
        if (faceRecogCallBack != null) {
            faceRecogCallBack.getNV21toBGR(bgr, outWidth, outHeight);
        }
    }

    /**
     * 人脸检测特征值回调
     *
     * @param faceFeature 特征值数组
     * @param length      特征值数组长度
     */
    public void JniGetFaceFeatureCallBack(byte[] faceFeature, int length) {

        if (faceRecogCallBack != null) {
            faceRecogCallBack.getFaceFeature(faceFeature, length);
        }
    }

    /**
     * 人脸检测结果回调
     *
     * @param json
     */
    public void JniGetFaceDetectCallBack(String json) {
        Log.d(TAG, "JniGetFaceDetectCallBack: " + json);
        if (faceRecogCallBack != null) {
            Gson gson = new Gson();
            FaceDetectInfo faceDetectInfo = null;
            try {
                faceDetectInfo = gson.fromJson(json, FaceDetectInfo.class);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
            if (faceDetectInfo != null) {
                Log.i(TAG, "JniGetFaceDetectCallBack: " + faceDetectInfo.toString());
                faceRecogCallBack.getFaceDetectResult(faceDetectInfo);
            }
        }
    }

    /**
     * 人脸相似度回调
     *
     * @param similarity
     */
    public void JniGetFaceSimilarityCallBack(float similarity) {
        Log.d(TAG, "JniGetFaceSimilarity: " + similarity);
        if (faceRecogCallBack != null) {
            faceRecogCallBack.getFaceSimilarity(similarity);
        }
    }

    public interface FaceRecogCallBack {
        /**
         * nv21 数据转换为 bgr数据
         *
         * @param bgr       [输出]转换后bgr图像数据
         * @param outWidth  [输出]转换后图像宽度
         * @param outHeight [输出]转换或图像高度
         */
        void getNV21toBGR(byte[] bgr, int outWidth, int outHeight);

        /**
         * 获取人脸特征值
         *
         * @param featureData
         */
        void getFaceFeature(byte[] featureData, int length);

        void getFaceDetectResult(FaceDetectInfo faceDetectInfo);

        /**
         * 获取人脸相似度
         */
        void getFaceSimilarity(float similarity);
    }


}
