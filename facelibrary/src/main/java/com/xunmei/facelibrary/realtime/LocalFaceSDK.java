/**
 * Project Name:cloudwalk2
 * File Name:CloudwalkLocalFaceSDK.java
 * Package Name:cn.cloudwalk
 * Date:2016-4-27下午4:57:54
 * Copyright @ 2010-2016 Cloudwalk Information Technology Co.Ltd All Rights Reserved.
 */

package com.xunmei.facelibrary.realtime;

/**
 * Project Name:cloudwalk2
 * File Name:CloudwalkLocalFaceSDK.java
 * Package Name:cn.cloudwalk
 * Date:2016-4-27下午4:57:54
 * Copyright @ 2010-2016 Cloudwalk Information Technology Co.Ltd All Rights Reserved.
 */

import android.content.Context;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.util.Log;

import com.xunmei.facelibrary.ConStant;
import com.xunmei.facelibrary.sdkproc.LocalSDK;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.face.sdk.FaceInfo;
import cn.face.sdk.FaceInterface;
import cn.face.sdk.FaceInterface.cw_img_angle_t;
import cn.face.sdk.FaceInterface.cw_img_mirror_t;

import static com.xunmei.facelibrary.util.FileUtil.TAG;


/**
 * ClassName: LocalFaceSDK <br/>
 * Description: <br/>
 * date: 2016-4-27 下午4:57:54 <br/>
 *
 * @author 284891377
 * @since JDK 1.7
 */
public class LocalFaceSDK {
    private static final int MAX_NUM = 10;

    private FaceInfoCallback faceInfoCallback;
    static LocalFaceSDK mFaceSDK;

    // FaceDetTrack faceDetTrack;
    // static FaceParam param = new FaceParam();

    // private int missFrameSet = 15;
    // private int missFrame;
    LocalSDK mLocalSDK;
    int faceOp = FaceInterface.cw_op_t.CW_OP_TRACK;

    public int getFaceOp() {
        return faceOp;
    }

    public void setFaceOp(int faceOp) {
        this.faceOp = faceOp;
    }

    Context mContext;
    /**
     * 每帧图像人脸数量
     **/
    int faceNum;
    FaceInfo[] faceInfos;


    /**
     * 单例实例化
     *
     * @param mContext
     * @return CloudwalkLocalFaceSDK
     */
    public static LocalFaceSDK getInstance(Context mContext) {

        if (null == mFaceSDK) {
            mFaceSDK = new LocalFaceSDK(mContext);

        }
        return mFaceSDK;
    }

    private LocalFaceSDK(Context mContext) {
        this.mContext = mContext;

        faceInfos = new FaceInfo[MAX_NUM];
        for (int i = 0; i < MAX_NUM; i++) {
            faceInfos[i] = new FaceInfo();
        }
    }

    public int cwInit() {
        int ret = 0;
        mLocalSDK = LocalSDK.getInstance(mContext);
        cwStart();

        return ret;
    }

    public int cwDestory() {

        faceInfoCallback = null;

        cwStop();
        return 0;

    }


    /**
     * cwFaceInfoCallback:设置人脸信息回掉. <br/>
     *
     * @param faceInfoCallback
     * @author:284891377 Date: 2016年6月16日 下午2:35:09
     * @since JDK 1.7
     */
    public void cwFaceInfoCallback(FaceInfoCallback faceInfoCallback) {
        this.faceInfoCallback = faceInfoCallback;
    }

    // 线程
    private Thread videoThread = null;
    private boolean bDetecting = false;
    private byte[] mFrame;
    private Object lockPreview = new Object(); // 视频预览中, 抓图与处理间的同步对象
    // 每帧图像
    int frameFormat;
    private int frameW;
    private int frameH;
    private int frameAngle;
    private int frameMirror;
    private int caremaType;
    private Camera camera;

    /**
     * cwStart:开始人脸检测 <br/>
     *
     * @return
     * @author:284891377 Date: 2016-4-22 下午3:51:17
     * @since JDK 1.7
     */
    private int cwStart() {
        bDetecting = true;
        if (null == videoThread) {
            videoThread = new Thread(new VideoFrameRunnable());
            videoThread.start();
        } else {

        }

        return 0;

    }

    /**
     * cwStop:停止人脸检测 <br/>
     *
     * @return
     * @author:284891377 Date: 2016-4-22 下午3:51:17
     * @since JDK 1.7
     */
    private int cwStop() {
        bDetecting = false;
        if ((null != videoThread) && !bDetecting) {
            try {
                synchronized (lockPreview) {
                    lockPreview.notify();
                }
                videoThread.join();
                videoThread = null;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return 0;

    }

    /**
     * cwPushFrame:Push视频桢数据 <br/>
     *
     * @param frameData   数据帧
     * @param frameW      图片宽
     * @param frameH      图像高
     * @param frameFormat 图像格式 {@link }
     * @param caremaType  摄像头和屏幕方向 {@link }
     * @author:284891377 Date: 2016-4-22 下午4:03:24
     * @since JDK 1.7
     */
    public void cwPushFrame(byte[] frameData, int frameW, int frameH, int frameFormat, int caremaType, Camera camera) {
        this.frameW = frameW;
        this.frameH = frameH;
        this.frameFormat = frameFormat;
        this.camera = camera;
        if (this.caremaType != caremaType) {
//			TestLog.netE(TAG, "摄像头,屏幕方向变");
        }
        this.caremaType = caremaType;
        switch (caremaType) {
            case CaremaType.FRONT_LANDSCAPE:
                this.frameAngle = cw_img_angle_t.CW_IMAGE_ANGLE_0;
                this.frameMirror = cw_img_mirror_t.CW_IMAGE_MIRROR_HOR;
                break;
            case CaremaType.FRONT_PORTRAIT:
                this.frameAngle = cw_img_angle_t.CW_IMAGE_ANGLE_270;
                this.frameMirror = cw_img_mirror_t.CW_IMAGE_MIRROR_HOR;
                break;
            case CaremaType.BACK_LANDSCAPE:
                this.frameAngle = cw_img_angle_t.CW_IMAGE_ANGLE_0;
                this.frameMirror = cw_img_mirror_t.CW_IMAGE_MIRROR_NONE;
                break;
            case CaremaType.BACK_PORTRAIT:
                this.frameAngle = cw_img_angle_t.CW_IMAGE_ANGLE_90;
                this.frameMirror = cw_img_mirror_t.CW_IMAGE_MIRROR_NONE;
                break;
        }

        synchronized (lockPreview) {
            this.mFrame = frameData;
            lockPreview.notify();
        }

    }

    // z处理线程
    class VideoFrameRunnable implements Runnable {

        public void run() {
            android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_LOWEST);
            while (bDetecting) {
                synchronized (lockPreview) {
                    try {
//                        Thread.sleep(200);
                        lockPreview.wait();

                    } catch (InterruptedException e) {

                        e.printStackTrace();
                    }
                    processVideoFrame(mFrame);
                }
            }
            // 结束
            if (null != faceInfoCallback) {
                faceInfoCallback.detectFaceInfo(faceInfos, null, frameAngle, 0);
            }
            videoThread.interrupt();

            // int ret = xfReleaseDetector();

        }
    }

    public int cwFaceDetectTrack(byte[] data,
                                 int width,
                                 int height,
                                 int format,
                                 int angle,
                                 int mirror) {
        int faceNum = 0;
//        Log.e("=====FaceDetect=====",data+"============================"+width+"==="+height+"====="+format+"==="+angle+"====>"+mirror+"====>"+faceOp+"====>"+faceInfos);

        faceNum = mLocalSDK.FaceDetectBase(data, width, height, format, angle, mirror, faceOp, faceInfos);
//        Log.e("=====faceNum=====",faceNum+"============================");
        if (faceNum > 0 && faceNum < LocalSDK.ERRCODE_MIN) {
            String path = saveImage();
            if(faceInfoCallback!=null){
                faceInfoCallback.detectFaceInfo(faceInfos, path, frameAngle, faceNum);
            }

        } else {
            if(faceInfoCallback!=null){
                faceInfoCallback.detectFaceInfo(faceInfos, null, frameAngle, 0);
            }


        }

        return faceNum;
    }


    /**
     * 处理每一帧图像
     *
     * @param yuv_data
     */
    private void processVideoFrame(byte[] yuv_data) {
        if (yuv_data == null || !bDetecting)
            return;
        faceNum = cwFaceDetectTrack(yuv_data, frameW, frameH, frameFormat, frameAngle, frameMirror);


    }

    public void setbDetecting(boolean bDetecting) {
        this.bDetecting = bDetecting;
    }

    private String saveImage() {
        FileOutputStream outStream = null;
        String path = null;
        try {
            YuvImage yuvimage = new YuvImage(mFrame, ImageFormat.NV21, camera.getParameters().getPreviewSize
                    ().width, camera.getParameters().getPreviewSize().height, null);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            yuvimage.compressToJpeg(new Rect(0, 0, camera.getParameters().getPreviewSize().width, camera.getParameters
                    ().getPreviewSize().height), 80, baos);
            SimpleDateFormat sdf = new SimpleDateFormat("HH_mm_ss_SSS");
            path = String.format(ConStant.publicFilePath + "/veryfy_"+sdf.format(new Date())+"_scmp.jpg");
            outStream = new FileOutputStream(path);
            outStream.write(baos.toByteArray());
            outStream.close();

            Log.d(TAG, "onPreviewFrame - wrote bytes: " + mFrame.length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return path;
        }
    }

}

