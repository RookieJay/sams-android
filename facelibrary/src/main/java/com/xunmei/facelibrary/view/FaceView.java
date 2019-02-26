package com.xunmei.facelibrary.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.xunmei.facelibrary.ConStant;
import com.xunmei.facelibrary.util.DisplayUtil;
import com.xunmei.facelibrary.util.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.face.sdk.FaceInfo;

public class FaceView extends View {
	private static final String TAG = LogUtils.makeLogTag("FaceView");

	Context mContext;

	Paint mLinePaint, mTextPaint, mPointPaint;
	FaceInfo[] faceInfos;
	private int faceNum;
	int textSize = 20;
	private int surfaceW, surfaceH;
	double scale;
	int frameWidth = 480;
	int frameHight = 640;

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
	 * @author:284891377 Date: 2016-4-29 上午9:45:19
	 * @param faceInfos
	 * @param faceNum
	 * @since JDK 1.7
	 */
	public void setFaces(final FaceInfo[] faceInfos, final int faceNum) {
		Log.e("======","======================msg.what2=============================");
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				FaceView.this.faceInfos = faceInfos;
				FaceView.this.faceNum = faceNum;

				invalidate();
			}
		},500);

	}

	public FaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;

		initPaint();

	}

	private void initPaint() {
		textSize = DisplayUtil.dip2px(mContext, 14);
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

		if (faceInfos != null) {

			//for (int i = 0; i < faceNum; i++) {

				FaceInfo faceInfo = faceInfos[0];
				// 人脸坐标转换

				int left = (int) (faceInfo.x * scale);
				int top = (int) (faceInfo.y * scale);

				int right = (int) ((faceInfo.width+faceInfo.x )* scale);
				int bottom = (int) ((faceInfo.height+faceInfo.y) * scale);
				StringBuilder sb=new StringBuilder();
				sb.append("人脸跟踪中");

				canvas.drawText(sb.toString(), (left+right)/2, top - textSize, mTextPaint);
				canvas.drawPoint(left, top, mTextPaint);
				canvas.drawPoint(right, right, mTextPaint);
				canvas.drawRect(new Rect(left, top, right, bottom), mLinePaint);
		}
	}

	/**
	 * 保存图片
	 * @param cropBitmap
	 */
	public static void saveBitmap(Bitmap cropBitmap) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH_mm_ss");
		File file = new File(ConStant.publicFilePath + "/recog" + sdf.format(new Date()) + ".jpg");
		FileOutputStream os=null;
		if (file.exists())
			file.delete();
		try {
			file.createNewFile();
			os = new FileOutputStream(file);
			cropBitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
