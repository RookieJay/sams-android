package pers.zjc.sams.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import pers.zjc.sams.R;

public class SquareGridLayout extends ViewGroup {

    private int mSpacing = 0;
    private int mColumns = 2;
    int squareWidth;

    public SquareGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SquareGridLayout);
        try {
            mSpacing = a.getDimensionPixelSize(R.styleable.SquareGridLayout_space, 0);
            mColumns = a.getInt(R.styleable.SquareGridLayout_numColumn, 2);
        }
        finally {
            a.recycle();
        }
    }

    public SquareGridLayout(Context context) {
        super(context);
    }

    public void setColumnCount(int size) {
        if (size < 1) {
            Log.d("squaregrid", "Layout must have at least one column");
            mColumns = 1;
        }
        else { mColumns = size; }
        requestLayout();
    }

    public int getColumnCount() {
        return mColumns;
    }

    public void setSpacing(int mSpacing) {
        if (mSpacing < 0) { return; }
        this.mSpacing = mSpacing;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int padw = getPaddingLeft() + getPaddingRight();
        squareWidth = (widthSize - padw - (mColumns > 1 ? (mColumns - 1) * mSpacing : 0)) / mColumns;
        int childSpec = MeasureSpec.makeMeasureSpec(squareWidth, MeasureSpec.EXACTLY);
        int width = 0;
        int height = getPaddingTop();
        int currentWidth = getPaddingLeft();
        int currentHeight = 0;
        final int count = getChildCount();
        int childHeight = 0;
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() == GONE) { continue; }
            LayoutParams lp = (LayoutParams)child.getLayoutParams();
            measureChild(child, childSpec, childSpec);
            if (currentWidth + squareWidth > widthSize) {
                height += currentHeight;
                width = Math.max(width, currentWidth);
                currentWidth = getPaddingLeft();
            }
            lp.x = currentWidth;
            lp.y = height;
            currentWidth += squareWidth + mSpacing;
            currentHeight = child.getMeasuredHeight() + mSpacing;
            childHeight = child.getMeasuredHeight();
        }
        height += childHeight;
        width = Math.max(width, currentWidth - mSpacing);
        width += getPaddingRight();
        height += getPaddingBottom();
        setMeasuredDimension(resolveSize(width, widthMeasureSpec), resolveSize(height, heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            LayoutParams lp = (LayoutParams)child.getLayoutParams();
            child.layout(lp.x, lp.y, lp.x + squareWidth, lp.y + child.getMeasuredHeight());
        }
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p.width, p.height);
    }

    public static class LayoutParams extends ViewGroup.LayoutParams {

        public int x;
        public int y;

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(Context context, AttributeSet attrs) {
            super(context, attrs);
        }
    }
}
