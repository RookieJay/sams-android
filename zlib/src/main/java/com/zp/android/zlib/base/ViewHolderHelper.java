package com.zp.android.zlib.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

public class ViewHolderHelper
{
    private final SparseArray<View> views;
    private final Context context;
    int position;
    View convertView;

    ViewHolderHelper(Context context, ViewGroup parent, View convertView, int position)
    {
        this.context = context;
        this.position = position;
        this.views = new SparseArray<View>();
        this.convertView = convertView;
        convertView.setTag(this);
    }

    public <T extends View> T getView(int viewId)
    {
        return retrieveView(viewId);
    }

    public ViewHolderHelper setText(int viewId, String value)
    {
        TextView view = retrieveView(viewId);
        if (view == null) { return this; }
        view.setText(value);
        return this;
    }

    public ViewHolderHelper setText(int viewId, String value, int drawId)
    {
        TextView view = retrieveView(viewId);
        if (view == null) { return this; }
        view.setText(value);
        view.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawId, 0);
        return this;
    }

    public ViewHolderHelper setImageResource(int viewId, int imageResId)
    {
        ImageView view = retrieveView(viewId);
        if (view == null) { return this; }
        view.setImageResource(imageResId);
        return this;
    }

    public ViewHolderHelper setBackgroundColor(int viewId, int color)
    {
        View view = retrieveView(viewId);
        if (view == null) { return this; }
        view.setBackgroundColor(color);
        return this;
    }

    public ViewHolderHelper setBackgroundRes(int viewId, int backgroundRes)
    {
        View view = retrieveView(viewId);
        if (view == null) { return this; }
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public ViewHolderHelper setTextColor(int viewId, int textColor)
    {
        TextView view = retrieveView(viewId);
        if (view == null) { return this; }
        view.setTextColor(textColor);
        return this;
    }

    public ViewHolderHelper setTextColorRes(int viewId, int textColorRes)
    {
        TextView view = retrieveView(viewId);
        if (view == null) { return this; }
        view.setTextColor(context.getResources().getColor(textColorRes));
        return this;
    }

    public ViewHolderHelper setImageDrawable(int viewId, Drawable drawable)
    {
        ImageView view = retrieveView(viewId);
        if (view == null) { return this; }
        view.setImageDrawable(drawable);
        return this;
    }

    public ViewHolderHelper setImageBitmap(int viewId, Bitmap bitmap)
    {
        ImageView view = retrieveView(viewId);
        if (view == null) { return this; }
        view.setImageBitmap(bitmap);
        return this;
    }

    public ViewHolderHelper setAlpha(int viewId, float value)
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

    public ViewHolderHelper setVisible(int viewId, boolean visible)
    {
        View view = retrieveView(viewId);
        if (view == null) { return this; }
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public ViewHolderHelper linkify(int viewId)
    {
        TextView view = retrieveView(viewId);
        if (view == null) { return this; }
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

    public ViewHolderHelper setTypeface(int viewId, Typeface typeface)
    {
        TextView view = retrieveView(viewId);
        if (view == null) { return this; }
        view.setTypeface(typeface);
        view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        return this;
    }

    public ViewHolderHelper setTypeface(Typeface typeface, int... viewIds)
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

    public ViewHolderHelper setProgress(int viewId, int progress)
    {
        ProgressBar view = retrieveView(viewId);
        if (view == null) { return this; }
        view.setProgress(progress);
        return this;
    }

    public ViewHolderHelper setProgress(int viewId, int progress, int max)
    {
        ProgressBar view = retrieveView(viewId);
        if (view == null) { return this; }
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    public ViewHolderHelper setMax(int viewId, int max)
    {
        ProgressBar view = retrieveView(viewId);
        if (view == null) { return this; }
        view.setMax(max);
        return this;
    }

    public ViewHolderHelper setRating(int viewId, float rating)
    {
        RatingBar view = retrieveView(viewId);
        if (view == null) { return this; }
        view.setRating(rating);
        return this;
    }

    public ViewHolderHelper setRating(int viewId, float rating, int max)
    {
        RatingBar view = retrieveView(viewId);
        if (view == null) { return this; }
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    public ViewHolderHelper setOnClickListener(int viewId, View.OnClickListener listener)
    {
        View view = retrieveView(viewId);
        if (view == null) { return this; }
        view.setOnClickListener(listener);
        return this;
    }

    public ViewHolderHelper setOnTouchListener(int viewId, View.OnTouchListener listener)
    {
        View view = retrieveView(viewId);
        if (view == null) { return this; }
        view.setOnTouchListener(listener);
        return this;
    }

    public ViewHolderHelper setOnLongClickListener(int viewId, View.OnLongClickListener listener)
    {
        View view = retrieveView(viewId);
        if (view == null) { return this; }
        view.setOnLongClickListener(listener);
        return this;
    }

    public ViewHolderHelper setTag(int viewId, Object tag)
    {
        View view = retrieveView(viewId);
        if (view == null) { return this; }
        view.setTag(tag);
        return this;
    }

    public ViewHolderHelper setTag(int viewId, int key, Object tag)
    {
        View view = retrieveView(viewId);
        if (view == null) { return this; }
        view.setTag(key, tag);
        return this;
    }

    public ViewHolderHelper setChecked(int viewId, boolean checked)
    {
        Checkable view = (Checkable)retrieveView(viewId);
        if (view == null) { return this; }
        view.setChecked(checked);
        return this;
    }

    public ViewHolderHelper setAdapter(int viewId, Adapter adapter)
    {
        AdapterView view = retrieveView(viewId);
        if (view == null) { return this; }
        view.setAdapter(adapter);
        return this;
    }

    public View getView()
    {
        return convertView;
    }

    public int getPosition()
    {
        if (position == -1)
        {
            throw new IllegalStateException(
                    "Use ViewHolderHelper constructor " + "with position if you need to retrieve the position.");
        }
        return position;
    }

    @SuppressWarnings("unchecked")
    private <T extends View> T retrieveView(int viewId)
    {
        View view = views.get(viewId);
        if (view == null)
        {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T)view;
    }
}
