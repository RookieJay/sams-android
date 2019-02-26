package pers.zjc.sams.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PieChartView extends View {

    private int cx, cy;
    private int radius;
    private Paint mPaint;
    private RectF oval;
    private List<Block> dataList = new ArrayList<>();
    // TODO: 2018/5/17 把颜色修改一下，这些颜色太丑
    private int[] colors = {Color.RED, Color.GREEN, Color.BLACK, Color.YELLOW, Color.BLUE, Color.DKGRAY};

    public PieChartView(Context context) {
        this(context, null);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        oval = new RectF();
    }


    /**
     * 设置数据时使用，使用键值对，键名为扇形图的标题，值为数据值
     * 目前没有实现显示扇形标题的功能，使用键值对这一方式是为了留后路
     * 此方法会清空所有数据再添加新数据
     *
     * @param dataList 数据
     */
    public void setData(List<Block> dataList) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
        for (Block block : dataList) {
            mappingColor(block);
        }
        invalidate();
    }

    /**
     * 使用可变参数添加数据，此方法会清空所有数据再添加新数据
     *
     * @param blocks 可变参数block键值对
     */
    public void setData(Block... blocks) {
        dataList.clear();
        if (blocks != null && blocks.length != 0) {
            dataList.addAll(Arrays.asList(blocks));
        }
        for (Block block : dataList) {
            mappingColor(block);
        }
        invalidate();
    }

    /**
     * 添加数据，调用一次添加一个，不会清空原有数据
     *
     * @param block block键值对
     */
    public void addData(Block block) {
        dataList.add(block);
        mappingColor(block);
        invalidate();
    }

    /**
     * 清空数据
     */
    public void clearData() {
        dataList.clear();
        invalidate();
    }

    private void mappingColor(Block block) {
        int index = dataList.indexOf(block);
        if (index != -1) {
            index = index % colors.length;
            block.color = colors[index];
        } else {
            block.color = colors[0];
        }
    }

    /**
     * 获取数据，此数据包含颜色信息
     *
     * @return 数据列表
     */
    public List<Block> getDataList() {
        return dataList;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode != MeasureSpec.EXACTLY || heightMode != MeasureSpec.EXACTLY) {
            throw new IllegalStateException("请设置一下view的大小，不要使用wrap content 或match parent");
        }

        cx = width / 2;
        cy = height / 2;
        radius = Math.min(width, height);
        oval.left = cx - radius / 2;
        oval.right = cx + radius / 2;
        oval.top = cy - radius / 2;
        oval.bottom = cy + radius / 2;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 如果没有数据则提示暂无数据，并结束后续的绘制
        if (dataList.size() == 0) {
            mPaint.setColor(Color.LTGRAY);
            canvas.drawCircle(cx, cy, radius / 2, mPaint);
            // 文字的大小与内圆的关系为0.4倍
            mPaint.setTextSize((radius / 4) * 0.4F);
            float txtWidth = mPaint.measureText("暂无数据");
            Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
            float txtHeight = Math.abs((fontMetrics.bottom - fontMetrics.top));

            mPaint.setColor(Color.WHITE);
            canvas.drawCircle(cx, cy, radius / 4, mPaint);

            mPaint.setColor(Color.LTGRAY);
            canvas.drawText("暂无数据", cx - txtWidth / 2, cy + txtHeight / 4, mPaint);
            return;
        }


        // 统计值的总数
        float total = 0;

        for (Block block : dataList) {
            total += block.value;
        }


        // 开始绘制扇形
        float startAngle = 0;

        for (Block block : dataList) {
            mPaint.setColor(block.color);
            float sweep = block.value / total * 360;
            canvas.drawArc(oval, startAngle, sweep, true, mPaint);
            startAngle += sweep;
        }

        // 在中间画一个白色的圆，，这样比较好看
        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(cx, cy, radius / 2 / 2, mPaint);
    }

    /**
     * 扇形的实体类，包含名称，值，与颜色，其中名称与值是实例化时，则用户传入，
     * 颜色由代码内部指定
     */
    public class Block {
        public String name;
        public float value;
        public int color;

        public Block(String name, float value) {
            this.name = name;
            this.value = value;
        }
    }
}
