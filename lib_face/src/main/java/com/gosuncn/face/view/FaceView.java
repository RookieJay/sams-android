package com.gosuncn.face.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.gosuncn.face.util.LogUtils;
import com.gosuncn.facelib.bean.FaceDetectInfo;


public class FaceView extends View {
    private static final String TAG = LogUtils.makeLogTag("FaceView");

    Context mContext;

    Paint mLinePaint, mTextPaint, mPointPaint;
    FaceDetectInfo faceDetectInfo;
    private int faceNum;
    int textSize = 20;
    private int surfaceW, surfaceH;
    double scale;
    int frameWidth = 480;
    int frameHight = 640;
    private  boolean isClean=false;

    public void setSurfaceWH(int surfaceW, int surfaceH, int frameWidth, int frameHight) {
        this.surfaceW = surfaceW;
        this.surfaceH = surfaceH;
        this.frameWidth = frameWidth;
        this.frameHight = frameHight;
        scale = surfaceW * 1d / frameWidth;

    }

    /**
     * setFaces:设置人脸框和人脸关键点. <br/>
     *
     * @param
     * @param faceNum
     * @author:284891377 Date: 2016-4-29 上午9:45:19
     * @since JDK 1.7
     * public void setFaces(final FaceInfo[] faceInfos, final int faceNum) {
     */
    public void setFaces(final FaceDetectInfo faceDetectInfo, final int faceNum) {
        Log.e("======", "======================msg.what2=============================");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FaceView.this.faceDetectInfo = faceDetectInfo;
                FaceView.this.faceNum = faceNum;
                FaceView.this.isClean=false;
                invalidate();
            }
        }, 50);

    }

    public FaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        initPaint();

    }

    private void initPaint() {
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // int color = Color.rgb(0, 150, 255);
        int color = Color.rgb(98, 212, 68);
        mLinePaint.setColor(color);
        mLinePaint.setStyle(Style.STROKE);
        mLinePaint.setStrokeWidth(5f);
        mLinePaint.setAlpha(180);

        mPointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPointPaint.setColor(color);
        mPointPaint.setStrokeWidth(10f);
        mPointPaint.setAlpha(180);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(color);
        mTextPaint.setTextSize(textSize);
        mTextPaint.setAlpha(180);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (faceDetectInfo != null&& !isClean) {
            Log.e("======", "======================faceDetectInfo=============================");
            if (faceDetectInfo.getFaceTrackedRects() != null && faceDetectInfo.getFaceTrackedRects().size() > 0) {
                FaceDetectInfo.FaceTrackedRect faceTrackedRect = faceDetectInfo.getFaceTrackedRects().get(0);

                Log.e("======", "======================faceTrackedRect==========getnHeight===================" + faceTrackedRect.getnHeight());
                // 人脸坐标转换
                int right = (int) ((faceTrackedRect.getnX() + faceTrackedRect.getnWidth()) * scale);
                int top = (int) (faceTrackedRect.getnY() * scale);

                int left = (int) (faceTrackedRect.getnX() * scale);
                int bottom = (int) ((faceTrackedRect.getnY() + faceTrackedRect.getnHeight()) * scale);
                canvas.drawRect(new Rect(left, top, right, bottom), mLinePaint);
            }

        }else {
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        }
    }



    public void clearDraw() {
        isClean=true;
        invalidate();

    }
}
