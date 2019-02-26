package com.zp.android.zlib.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class AbsBaseAdapter<T> extends BaseAdapter
{
    protected static final String TAG = BaseAdapter.class.getSimpleName();
    protected final Context mContext;
    protected final List<T> mData;
    protected final LayoutInflater inflater;

    public AbsBaseAdapter(Context context)
    {
        this(context, null);
    }

    public AbsBaseAdapter(Context context, List<T> data)
    {
        this.mData = data == null ? new ArrayList<T>() : new ArrayList<T>(data);
        this.mContext = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount()
    {
        return mData.size();
    }

    @Override
    public T getItem(int position)
    {
        if (position < 0 || position >= mData.size()) { return null; }
        return mData.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        int itemType = getItemViewType(position);
        final ViewHolderHelper helper = getAdapterHelper(itemType, position, convertView, parent);
        T item = getItem(position);
        onBindDataToView(itemType, helper, item);
        return helper.getView();
    }

    public void add(T elem)
    {
        mData.add(elem);
    }

    public void add(int location, T elem)
    {
        mData.add(location, elem);
    }

    public void addAll(List<T> elem)
    {
        mData.addAll(elem);
    }

    public void addAll(int location, List<T> elem)
    {
        mData.addAll(location, elem);
    }

    public void replace(T oldElem, T newElem)
    {
        int index = indexOf(oldElem);
        if (index >= 0) { replace(index, newElem); }
    }

    public void replace(int index, T elem)
    {
        mData.set(index, elem);
    }

    public void remove(T elem)
    {
        mData.remove(elem);
    }

    public int indexOf(T elem)
    {
        return mData.indexOf(elem);
    }

    public void remove(int index)
    {
        mData.remove(index);
    }

    public void replaceAll(List<T> elem)
    {
        mData.clear();
        mData.addAll(elem);
    }

    public boolean contains(T elem)
    {
        return mData.contains(elem);
    }

    public List<T> getAll()
    {
        return mData;
    }

    public void clear()
    {
        mData.clear();
    }

    protected abstract void onBindDataToView(int itemViewType, ViewHolderHelper helper, T item);

    protected abstract View genConvertView(int itemViewType, int position, ViewGroup parent);

    private ViewHolderHelper getAdapterHelper(int itemViewType, int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = genConvertView(itemViewType, position, parent);
            return new ViewHolderHelper(mContext, parent, convertView, position);
        }
        ViewHolderHelper holderHelper = (ViewHolderHelper)convertView.getTag();
        holderHelper.position = position;
        return holderHelper;
    }
}
