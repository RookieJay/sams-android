package com.zp.android.zlib.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * RecyclerViewHolder 封装类
 */
public class RecyclerViewHolderHelper extends RecyclerView.ViewHolder
{
    private final SparseArray<View> views;
    private final View mView;
    private Context mContext;

    public RecyclerViewHolderHelper(Context mContext, View itemView)
    {
        super(itemView);
        mView = itemView;
        this.views = new SparseArray<View>();
    }

    public <T extends View> T getView(int viewId)
    {
        return retrieveView(viewId);
    }

    public RecyclerViewHolderHelper setText(int viewId, String value)
    {
        TextView view = retrieveView(viewId);
        if (view == null) { return this; }
        view.setText(value);
        return this;
    }

    public RecyclerViewHolderHelper setText(int viewId, String value, int drawId)
    {
        TextView view = retrieveView(viewId);
        if (view == null) { return this; }
        view.setText(value);
        view.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawId, 0);
        return this;
    }

    public RecyclerViewHolderHelper setImageResource(int viewId, int imageResId)
    {
        ImageView view = retrieveView(viewId);
        if (view == null) { return this; }
        view.setImageResource(imageResId);
        return this;
    }

    public RecyclerViewHolderHelper setBackgroundColor(int viewId, int color)
    {
        View view = retrieveView(viewId);
        if (view == null) { return this; }
        view.setBackgroundColor(color);
        return this;
    }

    public RecyclerViewHolderHelper setBackgroundRes(int viewId, int backgroundRes)
    {
        View view = retrieveView(viewId);
        if (view == null) { return this; }
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public RecyclerViewHolderHelper setTextColor(int viewId, int textColor)
    {
        TextView view = retrieveView(viewId);
        if (view == null) { return this; }
        view.setTextColor(textColor);
        return this;
    }

    public RecyclerViewHolderHelper setTextColorRes(int viewId, int textColorRes)
    {
        TextView view = retrieveView(viewId);
        if (view == null) { return this; }
        view.setTextColor(mContext.getResources().getColor(textColorRes));
        return this;
    }

    public RecyclerViewHolderHelper setImageDrawable(int viewId, Drawable drawable)
    {
        ImageView view = retrieveView(viewId);
        if (view == null) { return this; }
        view.setImageDrawable(drawable);
        return this;
    }

    public RecyclerViewHolderHelper setImageBitmap(int viewId, Bitmap bitmap)
    {
        ImageView view = retrieveView(viewId);
        if (view == null) { return this; }
        view.setImageBitmap(bitmap);
        return this;
    }

    public RecyclerViewHolderHelper setAlpha(int viewId, float value)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            retrieveView(viewId).setAlpha(value);
        }
        else
        {
            // Pre-honeycomb hack to set Alpha value
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            retrieveView(viewId).startAnimation(alpha);
        }
        return this;
    }

    public RecyclerViewHolderHelper setVisible(int viewId, boolean visible)
    {
        View view = retrieveView(viewId);
        if (view == null) { return this; }
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public RecyclerViewHolderHelper linkify(int viewId)
    {
        TextView view = retrieveView(viewId);
        if (view == null) { return this; }
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

    public RecyclerViewHolderHelper setTypeface(int viewId, Typeface typeface)
    {
        TextView view = retrieveView(viewId);
        if (view == null) { return this; }
        view.setTypeface(typeface);
        view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        return this;
    }

    public RecyclerViewHolderHelper setTypeface(Typeface typeface, int... viewIds)
    {
        for (int viewId : viewIds)
        {
            TextView view = retrieveView(viewId);
            if (view == null) { continue; }
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }

    public RecyclerViewHolderHelper setProgress(int viewId, int progress)
    {
        ProgressBar view = retrieveView(viewId);
        if (view == null) { return this; }
        view.setProgress(progress);
        return this;
    }

    public RecyclerViewHolderHelper setProgress(int viewId, int progress, int max)
    {
        ProgressBar view = retrieveView(viewId);
        if (view == null) { return this; }
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    public RecyclerViewHolderHelper setMax(int viewId, int max)
    {
        ProgressBar view = retrieveView(viewId);
        if (view == null) { return this; }
        view.setMax(max);
        return this;
    }

    public RecyclerViewHolderHelper setRating(int viewId, float rating)
    {
        RatingBar view = retrieveView(viewId);
        if (view == null) { return this; }
        view.setRating(rating);
        return this;
    }

    public RecyclerViewHolderHelper setRating(int viewId, float rating, int max)
    {
        RatingBar view = retrieveView(viewId);
        if (view == null) { return this; }
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    public RecyclerViewHolderHelper setOnClickListener(int viewId, View.OnClickListener listener)
    {
        View view = retrieveView(viewId);
        if (view == null) { return this; }
        view.setOnClickListener(listener);
        return this;
    }

    public RecyclerViewHolderHelper setOnTouchListener(int viewId, View.OnTouchListener listener)
    {
        View view = retrieveView(viewId);
        if (view == null) { return this; }
        view.setOnTouchListener(listener);
        return this;
    }

    public RecyclerViewHolderHelper setOnLongClickListener(int viewId, View.OnLongClickListener listener)
    {
        View view = retrieveView(viewId);
        if (view == null) { return this; }
        view.setOnLongClickListener(listener);
        return this;
    }

    public RecyclerViewHolderHelper setTag(int viewId, Object tag)
    {
        View view = retrieveView(viewId);
        if (view == null) { return this; }
        view.setTag(tag);
        return this;
    }

    public RecyclerViewHolderHelper setTag(int viewId, int key, Object tag)
    {
        View view = retrieveView(viewId);
        if (view == null) { return this; }
        view.setTag(key, tag);
        return this;
    }

    public RecyclerViewHolderHelper setChecked(int viewId, boolean checked)
    {
        Checkable view = (Checkable)retrieveView(viewId);
        if (view == null) { return this; }
        view.setChecked(checked);
        return this;
    }

    public RecyclerViewHolderHelper setAdapter(int viewId, Adapter adapter)
    {
        AdapterView view = retrieveView(viewId);
        if (view == null) { return this; }
        view.setAdapter(adapter);
        return this;
    }

    public View getView()
    {
        return mView;
    }

    @SuppressWarnings("unchecked")
    private <T extends View> T retrieveView(int viewId)
    {
        View view = views.get(viewId);
        if (view == null)
        {
            view = mView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T)view;
    }
}
