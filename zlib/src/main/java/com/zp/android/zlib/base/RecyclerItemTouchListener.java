package com.zp.android.zlib.base;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import static android.support.v7.widget.RecyclerView.NO_POSITION;

/**
 * RecyclerView Item 事件
 */
public abstract class RecyclerItemTouchListener extends GestureDetector.SimpleOnGestureListener
        implements RecyclerView.OnItemTouchListener {

    private RecyclerView mRecyclerView;
    private GestureDetector mGestureDetector;

    public RecyclerItemTouchListener() {
    }

    @Override
    public final boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        if (null == mGestureDetector || null == mRecyclerView) {
            mRecyclerView = recyclerView;
            mGestureDetector = new GestureDetector(recyclerView.getContext(), this);
        }
        mGestureDetector.onTouchEvent(motionEvent);
        return false;
    }

    @Override
    public final void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
    }

    @Override
    public final void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    public abstract void onItemClick(RecyclerView parent, View clickedView, int position);

    public abstract void onItemLongClick(RecyclerView parent, View clickedView, int position);

    @Override
    public void onShowPress(MotionEvent e) {
        View view = getChildViewUnder(e);
        if (view != null) {
            view.setPressed(true);
        }
    }

    @Override
    public final boolean onSingleTapUp(MotionEvent e) {
        View view = getChildViewUnder(e);
        if (view == null) { return false; }
        view.setPressed(false);
        int position = mRecyclerView.getChildAdapterPosition(view);
        if (position != NO_POSITION) {
            onItemClick(mRecyclerView, view, position);
        }
        return true;
    }

    public final void onLongPress(MotionEvent e) {
        View view = getChildViewUnder(e);
        if (view == null) { return; }
        int position = mRecyclerView.getChildAdapterPosition(view);
        if (position != NO_POSITION) {
            onItemLongClick(mRecyclerView, view, position);
        }
        view.setPressed(false);
    }

    @Nullable
    private View getChildViewUnder(MotionEvent e) {
        if (null == mRecyclerView) { return null; }
        return mRecyclerView.findChildViewUnder(e.getX(), e.getY());
    }
}
