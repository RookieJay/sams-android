package com.zp.android.zlib.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * RecyclerAdapter基类
 */
public abstract class AbsRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolderHelper> {

    protected final Context mContext;
    protected final List<T> mData;

    public AbsRecyclerAdapter(Context context) {
        this(context, null);
    }

    public AbsRecyclerAdapter(Context context, List<T> data) {
        this.mData = data == null ? new ArrayList<T>() : new ArrayList<>(data);
        this.mContext = context;
    }

    @Override
    public final RecyclerViewHolderHelper onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolderHelper(mContext, genItemView(parent, viewType));
    }

    @Override
    public final void onBindViewHolder(RecyclerViewHolderHelper holder, int position) {
        T data = getItem(position);
        onBindDataToViewHolder(holder, data, position);
    }

    /**
     * 根据数据项类型生成对应的数据项布局View
     *
     * @param parent   ViewGroup
     * @param viewType 数据项类型
     * @return 数据项布局View
     */
    protected abstract View genItemView(ViewGroup parent, int viewType);

    /**
     * 绑定数据到holder控件
     *
     * @param holder   控件holder
     * @param data     数据对象
     * @param position 数据索引
     */
    protected abstract void onBindDataToViewHolder(RecyclerViewHolderHelper holder, T data, int position);

    @Override
    public final int getItemViewType(int position) {
        return getViewType(getItem(position));
    }

    /**
     * 根据数据获取View类型
     *
     * @param data 数据对象
     * @return 类型id
     */
    protected int getViewType(T data) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * 根据索引获取数据
     *
     * @param position 位置索引
     * @return 数据对象。如数据位置索引不在数据集合范围内则返回null
     */
    public final T getItem(int position) {
        if (position < 0 || position >= mData.size()) { return null; }
        return mData.get(position);
    }

    /**
     * 添加数据到数据集合
     *
     * @param elem 数据对象
     */
    public void add(T elem) {
        mData.add(elem);
    }

    /**
     * 向某索引位置添加数据到数据集合
     *
     * @param location 位置索引
     * @param elem     数据对象
     */
    public void add(int location, T elem) {
        mData.add(location, elem);
    }

    /**
     * 添加一组数据到数据集合
     *
     * @param elem 数据集合
     */
    public void addAll(List<T> elem) {
        mData.addAll(elem);
    }

    /**
     * 向某索引位置添加一组数据到数据集合
     *
     * @param location 位置索引
     * @param elem     数据集合
     */
    public void addAll(int location, List<T> elem) {
        mData.addAll(location, elem);
    }

    /**
     * 替换集合中某数据
     *
     * @param oldElem 原数据对象
     * @param newElem 新数据对象
     */
    public void replace(T oldElem, T newElem) {
        int index = indexOf(oldElem);
        if (index >= 0) { replace(index, newElem); }
    }

    /**
     * 替换某索引位置数据
     *
     * @param index 位置索引
     * @param elem  数据对象
     */
    public void replace(int index, T elem) {
        mData.set(index, elem);
    }

    /**
     * 移除集合中某数据
     *
     * @param elem 需要移除的目标数据对象
     */
    public void remove(T elem) {
        mData.remove(elem);
    }

    /**
     * 获取某数据对象在数据集合中的索引位置
     *
     * @param elem 数据对象
     * @return 索引位置.如果数据集合中没有该数据则索引为-1
     */
    public int indexOf(T elem) {
        return mData.indexOf(elem);
    }

    /**
     * 移除索引位置对应的数据
     *
     * @param index 位置索引
     */
    public void remove(int index) {
        mData.remove(index);
    }

    /**
     * 移除集合中的所有数据
     *
     * @param elem 位置索引
     */
    public void remove(List<T> elem) {
        mData.removeAll(elem);
    }

    /**
     * 替换原数据集合
     *
     * @param elem 新数据集合
     */
    public void replaceAll(List<T> elem) {
        if (null != elem) {
            mData.clear();
            mData.addAll(elem);
        }
    }

    /**
     * 查询某数据是否在集合中存在
     *
     * @param elem 数据对象
     * @return 数据是否存在。true为存在，false则为不存在
     */
    public boolean contains(T elem) {
        return mData.contains(elem);
    }

    /**
     * 获取集合中的所有数据
     *
     * @return 数据集合。该数据集合不能修改
     */
    public List<T> getAll() {
        return Collections.unmodifiableList(mData);
    }

    /**
     * 获取集合中的所有数据
     *
     * @return 数据集合。该数据集合可以修改
     */
    public List<T> getData() {
        return mData;
    }

    /**
     * 清空数据集合
     */
    public void clear() {
        mData.clear();
    }
}
