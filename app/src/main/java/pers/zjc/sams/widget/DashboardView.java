package pers.zjc.sams.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import pers.zjc.sams.R;


/**
 * 仪表盘控件
 */
public class DashboardView extends View {

    private final float sDefaultWidth;
    //透明度百分比单位
    private static final float sAlphaPercentageUnit = 2.55f;
    private boolean mEnableAnimation;
    private boolean isAnimFinish = true;
    //绘制角度来自于动画
    private float mAngleFromAnim;
    //控件宽高值
    private float mWidth, mHeight;
    // 起始角度
    private int mStartAngle = 140;
    // 绘制角度
    private int mSweepAngle = 260;
    //画笔
    private Paint mPaint;
    //总刻度数
    private int mTotalScale = 100;
    //一个单位刻度数
    private int mUnitScale = 1;
    //刻度图区
    private RectF mScaleRectF;
    //刻度与控件的间隔
    private float mScalePadding;
    //刻度颜色
    private int mScaleColor;
    //刻度背景色
    private int mScaleBackground;
    //刻度透明度百分比 0-100
    private int mScaleAlpha;
    //刻度背景透明度百分比 0-100
    private int mScaleBackgroundAlpha;
    //刻度宽度
    private float mScaleWidth;
    //进度图区
    private RectF mProgressRectF;
    //进度背景颜色
    private int mProgressBackgroundColor;
    //进度半径
    private float mProgressRadius;
    //进度与刻度的间隔
    private float mProgressPadding;
    //进度宽度
    private float mProgressWidth;
    //进度颜色
    private int mProgressColor;
    //进度条透明度百分比 0-100
    private int mProgressAlpha;
    //进度条背景透明度百分比 0-100
    private int mProgressBackgroundAlpha;
    //进度亮点宽度
    private float mProgressHighlightsWidth;
    //进度值
    private int mProgress = 1;
    //圆心坐标
    private float mCenterX, mCenterY;
    private float mTextPadding;
    //标题头
    private CharSequence mHeadText;
    private float mHeadSize;
    private int mHeadColor;
    //正文
    private CharSequence mBodyText;
    private float mBodySize;
    private int mBodyColor;
    //等级
    private CharSequence mLevelText;
    private float mLevelSize;
    private int mLevelColor;
    //日期
    private CharSequence mDateText;
    private float mDateSize;
    private int mDateColor;
    //标题
    private CharSequence mTitleText;
    private float mTitleSize;
    private int mTitleColor;

    public DashboardView(Context context) {
        this(context, null);
    }

    public DashboardView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DashboardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        sDefaultWidth = dp2px(100);
        initTool();
        initParameter(attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        mWidth = width > 0 ? width : sDefaultWidth;
        mHeight = mWidth;
        setMeasuredDimension((int)mWidth, (int)mHeight);
        mCenterX = mCenterY = mWidth / 2;
        float scaleL = mScalePadding + mScaleWidth / 2;
        float scaleR = mWidth - scaleL;
        mScaleRectF.set(scaleL, scaleL, scaleR, scaleR);
        mProgressRadius = mCenterX - mScalePadding - mScaleWidth - mProgressPadding;
        float progressL = mScalePadding + mScaleWidth + mProgressPadding + mProgressHighlightsWidth / 2;
        float progressR = mWidth - progressL;
        mProgressRectF.set(progressL, progressL, progressR, progressR);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画刻度圆弧
        mPaint.setColor(mScaleBackground);
        mPaint.setStyle(Style.STROKE);
        mPaint.setStrokeWidth(mScaleWidth);
        mPaint.setAlpha((int)(sAlphaPercentageUnit * mScaleBackgroundAlpha));
        mPaint.setAntiAlias(true);
        canvas.drawArc(mScaleRectF, mStartAngle, mSweepAngle, false, mPaint);
        //画刻度
        mPaint.reset();
        mPaint.setColor(mScaleColor);
        mPaint.setStyle(Style.STROKE);
        mPaint.setStrokeWidth(mScaleWidth);
        mPaint.setAlpha((int)(sAlphaPercentageUnit * mScaleAlpha));
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(dp2px(1));
        float x0 = mCenterX;
        float y0 = mScalePadding;
        float x2 = mCenterX;
        float y2 = y0 + mScaleWidth;
        canvas.save();
        canvas.drawLine(x0, y0, x2, y2, mPaint);
        float degree = (mSweepAngle * 1.0f) / (mTotalScale * 1.0f);
        for (int i = 0; i < mTotalScale / 2; i++) {
            canvas.rotate(-degree, mCenterX, mCenterY);
            canvas.drawLine(x0, y0, x2, y2, mPaint);
        }
        canvas.restore();
        canvas.save();
        for (int i = 0; i < mTotalScale / 2; i++) {
            canvas.rotate(degree, mCenterX, mCenterY);
            canvas.drawLine(x0, y0, x2, y2, mPaint);
        }
        canvas.restore();
        //画进度
        mPaint.setAlpha((int)(sAlphaPercentageUnit * mProgressBackgroundAlpha));
        mPaint.setColor(mProgressBackgroundColor);
        mPaint.setStrokeWidth(mProgressWidth);
        canvas.drawArc(mProgressRectF, mStartAngle, mSweepAngle, false, mPaint);
        mPaint.reset();
        mPaint.setColor(mProgressColor);
        mPaint.setStyle(Style.STROKE);
        mPaint.setStrokeWidth(mProgressWidth);
        mPaint.setAlpha((int)(sAlphaPercentageUnit * mProgressAlpha));
        mPaint.setAntiAlias(true);
        if (isAnimFinish) {
            float progressSweepAngle = calculateRelativeAngleWithValue(mProgress);
            canvas.drawArc(mProgressRectF, mStartAngle, progressSweepAngle, false, mPaint);
            float[] point = getCoordinatePoint(mProgressRadius - mProgressHighlightsWidth / 2f,
                    mStartAngle + progressSweepAngle);
            mPaint.setAlpha(255);
            mPaint.setStyle(Style.FILL);
            canvas.drawCircle(point[0], point[1], mProgressHighlightsWidth / 2f, mPaint);
        }
        else {
            canvas.drawArc(mProgressRectF, mStartAngle, mAngleFromAnim, false, mPaint);
            float[] point = getCoordinatePoint(mProgressRadius - mProgressHighlightsWidth / 2f,
                    mStartAngle + mAngleFromAnim);
            mPaint.setAlpha(255);
            mPaint.setStyle(Style.FILL);
            canvas.drawCircle(point[0], point[1], mProgressHighlightsWidth / 2f, mPaint);
        }
        //画文本内容
        mPaint.reset();
        mPaint.setStyle(Style.STROKE);
        mPaint.setAlpha(255);
        mPaint.setTextAlign(Align.CENTER);
        //标题头
        if (!TextUtils.isEmpty(mHeadText)) {
            mPaint.setTextSize(mHeadSize);
            mPaint.setColor(mHeadColor);
            float headY = mCenterY - mBodySize / 2 - mTextPadding;
            String head = String.valueOf(mHeadText);
            canvas.drawText(head, mCenterX, headY, mPaint);
        }
        //内容
        mPaint.setTextAlign(Align.LEFT);
        mPaint.setTextSize(mLevelSize);
        float leveW = TextUtils.isEmpty(mLevelText) ? 0 : mPaint.measureText(mLevelText.toString());
        mPaint.setTextSize(mBodySize);
        float bodyW = TextUtils.isEmpty(mBodyText) ? 0 : mPaint.measureText(mBodyText.toString());
        float bodyX = mCenterX - ((leveW + bodyW) / 2);
        if (!TextUtils.isEmpty(mBodyText)) {
            mPaint.setTextSize(mBodySize);
            mPaint.setColor(mBodyColor);
            float bodyY = mCenterY + mBodySize / 2;
            String body = String.valueOf(mBodyText);
            canvas.drawText(body, bodyX, bodyY, mPaint);
        }
        //等级
        if (!TextUtils.isEmpty(mLevelText)) {
            mPaint.setTextSize(mLevelSize);
            mPaint.setColor(mLevelColor);
            float leveY = mCenterY + mBodySize / 2;
            float leveX = bodyX + bodyW + 5;
            String level = String.valueOf(mLevelText);
            canvas.drawText(level, leveX, leveY, mPaint);
        }
        mPaint.setTextAlign(Align.CENTER);
        //时间
        if (!TextUtils.isEmpty(mDateText)) {
            mPaint.setTextSize(mDateSize);
            mPaint.setColor(mDateColor);
            float dateY = mCenterY + (mBodySize / 2) + mTextPadding + mDateSize;
            String date = String.valueOf(mDateText);
            canvas.drawText(date, mCenterX, dateY, mPaint);
        }
        //标题
        if (!TextUtils.isEmpty(mTitleText)) {
            mPaint.setTextSize(mTitleSize);
            mPaint.setColor(mTitleColor);
            float titleY = mHeight - mTextPadding;
            String title = String.valueOf(mTitleText);
            canvas.drawText(title, mCenterX, titleY, mPaint);
        }
    }

    public void setEnableAnimation(boolean enable) {
        this.mEnableAnimation = enable;
    }

    public void setTotalScale(int totalScale) {
        if (totalScale != mTotalScale) {
            this.mTotalScale = totalScale;
            postInvalidate();
        }
    }

    public void setUnitScale(int unitScale) {
        if (unitScale != mUnitScale) {
            this.mUnitScale = unitScale;
            postInvalidate();
        }
    }

    public void setScalePadding(float scalePadding) {
        if (scalePadding != mScalePadding) {
            this.mScalePadding = scalePadding;
            postInvalidate();
        }
    }

    public void setScaleColor(int scaleColor) {
        if (scaleColor != mScaleColor) {
            this.mScaleColor = scaleColor;
            postInvalidate();
        }
    }

    public void setScaleBackground(int scaleBackground) {
        if (scaleBackground != mScaleBackground) {
            this.mScaleBackground = scaleBackground;
            postInvalidate();
        }
    }

    public void setScaleAlpha(int scaleAlpha) {
        if (scaleAlpha != mScaleAlpha) {
            this.mScaleAlpha = scaleAlpha;
            postInvalidate();
        }
    }

    public void setScaleBackgroundAlpha(int scaleBackgroundAlpha) {
        if (scaleBackgroundAlpha != mScaleBackgroundAlpha) {
            this.mScaleBackgroundAlpha = scaleBackgroundAlpha;
            postInvalidate();
        }
    }

    public void setScaleWidth(float scaleWidth) {
        if (scaleWidth != mScaleWidth) {
            this.mScaleWidth = scaleWidth;
            postInvalidate();
        }
    }

    public void setProgressBackgroundColor(int progressBackgroundColor) {
        if (progressBackgroundColor != mProgressBackgroundColor) {
            this.mProgressBackgroundColor = progressBackgroundColor;
            postInvalidate();
        }
    }

    public void setProgressPadding(float progressPadding) {
        if (progressPadding != mProgressPadding) {
            this.mProgressPadding = progressPadding;
            postInvalidate();
        }
    }

    public void setProgressWidth(float progressWidth) {
        if (progressWidth != mProgressWidth) {
            this.mProgressWidth = progressWidth;
            postInvalidate();
        }
    }

    public void setProgressColor(int progressColor) {
        if (progressColor != mProgressColor) {
            this.mProgressColor = progressColor;
            postInvalidate();
        }
    }

    public void setProgressAlpha(int progressAlpha) {
        if (progressAlpha != mProgressAlpha) {
            this.mProgressAlpha = progressAlpha;
            postInvalidate();
        }
    }

    public void setProgressBackgroundAlpha(int progressBackgroundAlpha) {
        if (progressBackgroundAlpha != mProgressBackgroundAlpha) {
            this.mProgressBackgroundAlpha = progressBackgroundAlpha;
            postInvalidate();
        }
    }

    public void setProgressHighlightsWidth(float progressHighlightsWidth) {
        if (progressHighlightsWidth != mProgressHighlightsWidth) {
            this.mProgressHighlightsWidth = progressHighlightsWidth;
            postInvalidate();
        }
    }

    public void setProgress(int progress) {
        if (progress <= 0 || progress >= (mTotalScale * mUnitScale)) { return; }
        if (mProgress != progress) {
            this.mProgress = progress;
            if (mEnableAnimation) {
                playAnimator();
            }
            else { postInvalidate(); }
        }
    }

    public void setTextPadding(float textPadding) {
        if (textPadding != mTextPadding) {
            this.mTextPadding = textPadding;
            postInvalidate();
        }
    }

    public void setHeadText(CharSequence headText) {
        if (!TextUtils.equals(headText, mHeadText)) {
            this.mHeadText = headText;
            postInvalidate();
        }
    }

    public void setHeadSize(float headSize) {
        if (headSize != mHeadSize) {
            this.mHeadSize = headSize;
            postInvalidate();
        }
    }

    public void setHeadColor(int headColor) {
        if (headColor != mHeadColor) {
            this.mHeadColor = headColor;
            postInvalidate();
        }
    }

    public void setBodyText(CharSequence bodyText) {
        if (!TextUtils.equals(bodyText, mBodyText)) {
            this.mBodyText = bodyText;
            postInvalidate();
        }
    }

    public void setBodySize(float bodySize) {
        if (bodySize != mBodySize) {
            this.mBodySize = bodySize;
            postInvalidate();
        }
    }

    public void setBodyColor(int bodyColor) {
        if (bodyColor != mBodyColor) {
            this.mBodyColor = bodyColor;
            postInvalidate();
        }
    }

    public void setLevelText(CharSequence levelText) {
        if (!TextUtils.equals(levelText, mLevelText)) {
            this.mLevelText = levelText;
            postInvalidate();
        }
    }

    public void setLevelSize(float levelSize) {
        if (levelSize != mLevelSize) {
            this.mLevelSize = levelSize;
            postInvalidate();
        }
    }

    public void setLevelColor(int levelColor) {
        if (levelColor != mLevelColor) {
            this.mLevelColor = levelColor;
            postInvalidate();
        }
    }

    public void setDateText(CharSequence dateText) {
        if (!TextUtils.equals(dateText, mDateText)) {
            this.mDateText = dateText;
            postInvalidate();
        }
    }

    public void setDateSize(float dateSize) {
        if (dateSize != mDateSize) {
            this.mDateSize = dateSize;
            postInvalidate();
        }
    }

    public void setDateColor(int dateColor) {
        if (dateColor != mDateColor) {
            this.mDateColor = dateColor;
            postInvalidate();
        }
    }

    public void setTitleText(CharSequence titleText) {
        if (!TextUtils.equals(titleText, mTitleText)) {
            this.mTitleText = titleText;
            postInvalidate();
        }
    }

    public void setTitleSize(float titleSize) {
        if (titleSize != mTitleSize) {
            this.mTitleSize = titleSize;
            postInvalidate();
        }
    }

    public void setTitleColor(int titleColor) {
        if (titleColor != mTitleColor) {
            this.mTitleColor = titleColor;
            postInvalidate();
        }
    }

    /**
     * 设置padding
     * 该方法是无效方法，需要调用setPadding(int padding)
     */
    @Deprecated
    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        throw new IllegalArgumentException("Invalid padding parameter");
    }

    private void initTool() {
        mPaint = new Paint();
        mScaleRectF = new RectF();
        mProgressRectF = new RectF();
    }

    private void initParameter(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.DashboardView);
        mEnableAnimation = typedArray.getBoolean(R.styleable.DashboardView_enable_animation, true);
        mTotalScale = typedArray.getInteger(R.styleable.DashboardView_total_scale, 100);
        mUnitScale = typedArray.getInteger(R.styleable.DashboardView_unit_scale, 1);
        mProgress = typedArray.getInteger(R.styleable.DashboardView_progress, 1);
        mScaleWidth = typedArray.getDimension(R.styleable.DashboardView_scale_width, dp2px(10));
        mProgressWidth = typedArray.getDimension(R.styleable.DashboardView_progress_width, dp2px(10));
        mScalePadding = typedArray.getDimension(R.styleable.DashboardView_scale_padding, 0);
        mProgressPadding = typedArray.getDimension(R.styleable.DashboardView_progress_padding, dp2px(10));
        mProgressHighlightsWidth = typedArray.getDimension(R.styleable.DashboardView_progress_highlights_width,
                dp2px(14));
        mScaleColor = typedArray.getColor(R.styleable.DashboardView_scale_color, Color.WHITE);
        mScaleBackground = typedArray.getColor(R.styleable.DashboardView_scale_background, mScaleColor);
        mScaleBackgroundAlpha = typedArray.getInteger(R.styleable.DashboardView_scale_background_alpha, 80);
        mScaleAlpha = typedArray.getInteger(R.styleable.DashboardView_scale_alpha, 90);
        mProgressBackgroundColor = typedArray.getColor(R.styleable.DashboardView_progress_background, Color.GRAY);
        mProgressColor = typedArray.getColor(R.styleable.DashboardView_progress_color, Color.WHITE);
        mProgressBackgroundAlpha = typedArray.getInteger(R.styleable.DashboardView_progress_background_alpha, 100);
        mProgressAlpha = typedArray.getInteger(R.styleable.DashboardView_progress_alpha, 100);
        mTextPadding = typedArray.getDimension(R.styleable.DashboardView_text_padding, dp2px(20));
        //标题头
        mHeadText = typedArray.getText(R.styleable.DashboardView_head_text);
        mHeadSize = typedArray.getDimension(R.styleable.DashboardView_head_size, sp2px(12));
        mHeadColor = typedArray.getColor(R.styleable.DashboardView_head_color, Color.WHITE);
        //正文
        mBodyText = typedArray.getText(R.styleable.DashboardView_body_text);
        mBodySize = typedArray.getDimension(R.styleable.DashboardView_body_size, sp2px(18));
        mBodyColor = typedArray.getColor(R.styleable.DashboardView_body_color, Color.WHITE);
        //等级
        mLevelText = typedArray.getText(R.styleable.DashboardView_level_text);
        mLevelSize = typedArray.getDimension(R.styleable.DashboardView_level_size, sp2px(12));
        mLevelColor = typedArray.getColor(R.styleable.DashboardView_level_color, Color.WHITE);
        //日期
        mDateText = typedArray.getText(R.styleable.DashboardView_date_text);
        mDateSize = typedArray.getDimension(R.styleable.DashboardView_date_size, sp2px(12));
        mDateColor = typedArray.getColor(R.styleable.DashboardView_date_color, Color.WHITE);
        //标题
        mTitleText = typedArray.getText(R.styleable.DashboardView_title_text);
        mTitleSize = typedArray.getDimension(R.styleable.DashboardView_title_size, sp2px(16));
        mTitleColor = typedArray.getColor(R.styleable.DashboardView_title_color, Color.WHITE);
        typedArray.recycle();
    }

    /**
     * 相对起始角度计算值分所对应的角度大小
     */
    private float calculateRelativeAngleWithValue(int value) {
        float total = (mTotalScale * mUnitScale) * 1.0f;
        float unitAngle = (mSweepAngle * 1.0f) / total;
        return unitAngle * value;
    }

    /**
     * 通过角度转换为点
     *
     * @param radius 半径
     * @param angle  角度
     * @return 转换点
     */
    private float[] getCoordinatePoint(float radius, float angle) {
        float[] point = new float[2];
        double arcAngle = Math.toRadians(angle);
        if (angle < 90) {
            point[0] = (float)(mCenterX + Math.cos(arcAngle) * radius);
            point[1] = (float)(mCenterY + Math.sin(arcAngle) * radius);
        }
        else if (angle == 90) {
            point[0] = mCenterX;
            point[1] = mCenterY + radius;
        }
        else if (angle > 90 && angle < 180) {
            arcAngle = Math.PI * (180 - angle) / 180.0;
            point[0] = (float)(mCenterX - Math.cos(arcAngle) * radius);
            point[1] = (float)(mCenterY + Math.sin(arcAngle) * radius);
        }
        else if (angle == 180) {
            point[0] = mCenterX - radius;
            point[1] = mCenterY;
        }
        else if (angle > 180 && angle < 270) {
            arcAngle = Math.PI * (angle - 180) / 180.0;
            point[0] = (float)(mCenterX - Math.cos(arcAngle) * radius);
            point[1] = (float)(mCenterY - Math.sin(arcAngle) * radius);
        }
        else if (angle == 270) {
            point[0] = mCenterX;
            point[1] = mCenterY - radius;
        }
        else {
            arcAngle = Math.PI * (360 - angle) / 180.0;
            point[0] = (float)(mCenterX + Math.cos(arcAngle) * radius);
            point[1] = (float)(mCenterY - Math.sin(arcAngle) * radius);
        }
        return point;
    }

    private void playAnimator() {
        if (!isAnimFinish) { return; }
        float degree = calculateRelativeAngleWithValue(mProgress);
        ValueAnimator degreeValueAnimator = ValueAnimator.ofFloat(0, degree);
        degreeValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAngleFromAnim = (float)animation.getAnimatedValue();
                postInvalidate();
            }
        });
        float total = (mTotalScale * mUnitScale) * 1.0f;
        float percentage = mProgress / total;
        long delay;
        if (percentage <= 0.2) {
            delay = 1000;
        }
        else if (percentage <= 0.4) {
            delay = 1500;
        }
        else if (percentage <= 0.6) {
            delay = 2000;
        }
        else if (percentage <= 0.8) {
            delay = 2500;
        }
        else {
            delay = 3000;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(delay).playTogether(degreeValueAnimator);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isAnimFinish = false;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isAnimFinish = true;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                isAnimFinish = true;
            }
        });
        animatorSet.start();
    }

    private float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

    private float sp2px(float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, Resources.getSystem().getDisplayMetrics());
    }
}
