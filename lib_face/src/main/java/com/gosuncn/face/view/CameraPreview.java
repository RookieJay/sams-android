package com.gosuncn.face.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.os.Build;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.gosuncn.face.listener.CaremaType;
import com.gosuncn.face.listener.Delegate;
import com.gosuncn.face.listener.FaceInterface;
import com.gosuncn.face.listener.OnFrameListener;
import com.gosuncn.face.util.Contants;
import com.gosuncn.face.util.LogUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 实时预览帧 setPreviewCallback
 *
 * @author yusr
 */
@SuppressWarnings("deprecation")
@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback, PreviewCallback {

    private static final String TAG = LogUtils.makeLogTag("CameraPreview");

    private Camera mCamera;

    Delegate mDelegate;
    private int orientation;

    /**
     * 设置屏幕方向
     *
     * @param orientation Configuration.ORIENTATION_LANDSCAPE 或者
     *                    Configuration.ORIENTATION_PORTRAIT
     */
    public void setScreenOrientation(int orientation) {
        this.orientation = orientation;

    }

    private OnFrameListener onFrameListener;
    //摄像头id，前置还是后置，默认后置
    int caremaId = Camera.CameraInfo.CAMERA_FACING_BACK;

    public int getCaremaId() {
        return caremaId;
    }

    public void setCaremaId(int caremaId) {
        this.caremaId = caremaId;
    }

    private boolean mPreviewing = true;
    private boolean mSurfaceCreated = false;
    private CameraConfigurationManager mCameraConfigurationManager;
    Context context;

    /**
     * setReqPrevWH:设置希望的预览分辨率. <br/>
     *
     * @author:284891377 Date: 2016/10/25 0025 10:50
     * @since JDK 1.7
     */
    public void setReqPrevWH(int reqPrevW, int reqPrevH) {
        this.reqPrevW = reqPrevW;
        this.reqPrevH = reqPrevH;
    }

    int reqPrevW = Contants.PREVIEW_W, reqPrevH = Contants.PREVIEW_H;


    public CameraPreview(Context context) {
        super(context);
        this.context = context;
    }

    public CameraPreview(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);
        this.context = context;
    }

    public CameraPreview(Context context, AttributeSet attrs) {

        super(context, attrs);
        this.context = context;
    }

    public void setCamera(Camera camera) {
        mCamera = camera;
        if (mCamera != null) {
            mCameraConfigurationManager = new CameraConfigurationManager(getContext());

            getHolder().addCallback(this);
            if (mPreviewing) {
                requestLayout();
            } else {
                showCameraPreview();
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mSurfaceCreated = true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        if (surfaceHolder.getSurface() == null) {
            return;
        }
        stopCameraPreview();
        showCameraPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mSurfaceCreated = false;
        stopCameraPreview();
    }

    public void showCameraPreview() {
        if (mCamera != null) {
            try {
                mPreviewing = true;
                mCamera.setPreviewDisplay(getHolder());

                mCameraConfigurationManager.setCameraParametersForPreviewCallBack(mCamera, caremaId, reqPrevW,
                        reqPrevH);
                mCamera.startPreview();
                mCamera.setPreviewCallback(CameraPreview.this);
            } catch (Exception e) {
                LogUtils.LOGE(TAG, e.toString());
            }
        }
    }

    public void stopCameraPreview() {
        if (mCamera != null) {
            try {

                mPreviewing = false;
                mCamera.cancelAutoFocus();
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();
            } catch (Exception e) {
                LogUtils.LOGE(TAG, e.toString());
            }
        }
    }

    public void openFlashlight() {
        if (flashLightAvaliable()) {
            mCameraConfigurationManager.openFlashlight(mCamera);
        }
    }

    public void closeFlashlight() {
        if (flashLightAvaliable()) {
            mCameraConfigurationManager.closeFlashlight(mCamera);
        }
    }

    private boolean flashLightAvaliable() {
        return mCamera != null && mPreviewing && mSurfaceCreated
                && getContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    /******************************************************************/
    public Size getPreviewSize() {
        Camera.Parameters parameters = mCamera.getParameters();
        return parameters.getPreviewSize();
    }

    public void setDelegate(Delegate mDelegate) {
        this.mDelegate = mDelegate;
    }

    /**
     * 打开摄像头开始预览，但是并未开始识别
     */
    public void cwStartCamera() {
        if (mCamera != null) {
            return;
        }

        try {
            mCamera = Camera.open(caremaId);
        } catch (Exception e) {
            if (mDelegate != null) {
                mDelegate.onOpenCameraError();
            }
        }
        setCamera(mCamera);
    }

    /**
     * 关闭摄像头预览，并且隐藏扫描框
     */
    public void cwStopCamera() {
        if (mCamera != null) {
            stopCameraPreview();

            mCamera.release();
            mCamera = null;
        }
    }

    private boolean img = false;

    public void setImg(boolean img) {
        this.img = img;
    }


    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        final Size size = camera.getParameters().getPreviewSize();


//        Bitmap bitmap = Bytes2Bimap(data,size);

        if (caremaId == Camera.CameraInfo.CAMERA_FACING_FRONT) {// 前置
            if (Configuration.ORIENTATION_PORTRAIT == orientation) {// 竖屏
                // 水平镜像+旋转90
                if (onFrameListener != null) {
//                    saveImage(data);
//                    int r = cwPushFrame(CaremaType.FRONT_PORTRAIT);
//                    bitmap = rotateBimap(r, bitmap);
//                    byte[] data2    = Bitmap2Bytes(bitmap);
                    onFrameListener.onPreviewFrame(data, cwPushFrame(CaremaType.FRONT_PORTRAIT), size.width, size.height);
                }
            } else {//横屏水平镜像
                if (onFrameListener != null) {

                    onFrameListener.onPreviewFrame(data, cwPushFrame(CaremaType.FRONT_LANDSCAPE), size.width, size.height);
                }
            }
        } else {// 后置
            if (Configuration.ORIENTATION_PORTRAIT == orientation) {// 竖屏 旋转90
                if (onFrameListener != null) {

                    onFrameListener.onPreviewFrame(data, cwPushFrame(CaremaType.BACK_PORTRAIT), size.width, size.height);
                }
            } else {
                // 横屏不做处理
                if (onFrameListener != null) {

                    onFrameListener.onPreviewFrame(data, cwPushFrame(CaremaType.BACK_LANDSCAPE), size.width, size.height);
                }
            }
        }


    }

    private byte[] rotateYUV420Degree90(byte[] data, int imageWidth, int imageHeight) {
        byte[] yuv = new byte[imageWidth * imageHeight * 3 / 2];
        // Rotate the Y luma
        int i = 0;
        for (int x = 0; x < imageWidth; x++) {
            for (int y = imageHeight - 1; y >= 0; y--) {
                yuv[i] = data[y * imageWidth + x];
                i++;
            }
        }
        // Rotate the U and V color components
        i = imageWidth * imageHeight * 3 / 2 - 1;
        for (int x = imageWidth - 1; x > 0; x = x - 2) {
            for (int y = 0; y < imageHeight / 2; y++) {
                yuv[i] = data[(imageWidth * imageHeight) + (y * imageWidth) + x];
                i--;
                yuv[i] = data[(imageWidth * imageHeight) + (y * imageWidth) + (x - 1)];
                i--;
            }
        }
        return yuv;
    }

    /**
     * 把Bitmap转Byte
     *
     * @EditTime 2010-07-19 上午11:45:56
     */
    public byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    public Bitmap Bytes2Bimap(byte[] yuvData, Size size) {
        YuvImage yuvimage = new YuvImage(yuvData, ImageFormat.NV21,size.width,size.height,null);//data是onPreviewFrame参数提供

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        yuvimage.compressToJpeg(new Rect(0, 0, yuvimage.getWidth(), yuvimage.getHeight()), 100, baos);// 80--JPG图片的质量[0-100],100最高
         byte[] rawImage =baos.toByteArray();

        BitmapFactory.Options options = new BitmapFactory.Options();
        SoftReference<Bitmap> softRef = new SoftReference<Bitmap>(BitmapFactory.decodeByteArray(rawImage, 0, rawImage.length, options));
        //方便回收
        Bitmap bitmap = (Bitmap) softRef.get();

        return bitmap;

    }

    private Bitmap rotateBimap(float degree, Bitmap srcBitmap) {
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(degree);
        Bitmap bitmap = Bitmap.createBitmap(srcBitmap, 0, 0, srcBitmap.getWidth(), srcBitmap.getHeight()
                , matrix, true);
        return bitmap;
    }


    private String saveImage(byte[] mFrame) {
        FileOutputStream outStream = null;
        String path = null;
        try {
            YuvImage yuvimage = new YuvImage(mFrame, ImageFormat.NV21, mCamera.getParameters().getPreviewSize
                    ().width, mCamera.getParameters().getPreviewSize().height, null);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            yuvimage.compressToJpeg(new Rect(0, 0, mCamera.getParameters().getPreviewSize().width, mCamera.getParameters
                    ().getPreviewSize().height), 80, baos);
            SimpleDateFormat sdf = new SimpleDateFormat("HH_mm_ss_SSS");
            String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
            String filePath = new StringBuilder(sdPath).append(File.separator).append("DCIM").append(File.separator).append("Camera").append(File.separator).toString();
            path = String.format(filePath + "veryfy_" + sdf.format(new Date()) + "_scmp.jpg");
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

    public int cwPushFrame(int caremaType) {

        int frameAngle = 0;
        switch (caremaType) {
            case CaremaType.FRONT_LANDSCAPE:
                frameAngle = FaceInterface.cw_img_angle_t.CW_IMAGE_ANGLE_0;
                break;
            case CaremaType.FRONT_PORTRAIT:
                frameAngle = FaceInterface.cw_img_angle_t.CW_IMAGE_ANGLE_270;
                break;
            case CaremaType.BACK_LANDSCAPE:
                frameAngle = FaceInterface.cw_img_angle_t.CW_IMAGE_ANGLE_0;
                break;

            case CaremaType.BACK_PORTRAIT:
                frameAngle = FaceInterface.cw_img_angle_t.CW_IMAGE_ANGLE_90;
                break;
        }
        return frameAngle;

    }

    /**
     * 切换摄像头
     */
    public int switchCarema() {
        cwStopCamera();
        if (caremaId == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            caremaId = Camera.CameraInfo.CAMERA_FACING_BACK;
        } else {
            caremaId = Camera.CameraInfo.CAMERA_FACING_FRONT;
        }
        cwStartCamera();
        return caremaId;
    }

    private OnImageData onImageData;

    public void setOnFrameListener(OnFrameListener onFrameListener) {
        this.onFrameListener = onFrameListener;
    }

    public void setOnImageData(OnImageData onImageData) {
        this.onImageData = onImageData;
    }

    public interface OnImageData {
        void onImageData(byte[] data);
    }
}