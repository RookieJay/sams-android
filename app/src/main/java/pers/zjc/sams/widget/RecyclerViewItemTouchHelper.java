package pers.zjc.sams.widget;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import pers.zjc.sams.R;


/**
 * @author: jayqiu
 * @date: 2018-05-28 15:33
 * @comment:
 */
public class RecyclerViewItemTouchHelper extends ItemTouchHelper.Callback {

    ItemMoveListener mItemMoveListener;

    public RecyclerViewItemTouchHelper(ItemMoveListener mItemMoveListener) {
        this.mItemMoveListener = mItemMoveListener;
    }

    /**
     * 获取动作标识
     * 动作标识分：dragFlags和swipeFlags
     * dragFlags：列表滚动方向的动作标识（如竖直列表就是上和下，水平列表就是左和右）
     * wipeFlags：与列表滚动方向垂直的动作标识（如竖直列表就是左和右，水平列表就是上和下）
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        int flags = makeMovementFlags(dragFlags, swipeFlags);
        return flags;
    }

    /**
     * 是否开启item长按拖拽功能
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    /**
     * 当item拖拽移动时触发
     *
     * @param viewHolder       当前被拖拽的item的viewHolder
     * @param targetViewHolder 当前被拖拽的item下方的另一个item的viewHolder
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder targetViewHolder) {
        return mItemMoveListener.onItemMove(viewHolder, targetViewHolder, viewHolder.getAdapterPosition(),
                targetViewHolder.getAdapterPosition());
    }

    /**
     * 当item被拖拽或侧滑时触发
     *
     * @param actionState 当前item的状态
     */
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        //不管是拖拽或是侧滑，背景色都要变化
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            viewHolder.itemView.setBackgroundColor(
                    viewHolder.itemView.getContext().getResources().getColor(R.color.cdcdcdc));
        }
    }

    /**
     * 当item的交互动画结束时触发
     */
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setBackgroundColor(
                viewHolder.itemView.getContext().getResources().getColor(android.R.color.white));

        viewHolder.itemView.setAlpha(1);
        viewHolder.itemView.setScaleY(1);
    }

    /**
     * 当item侧滑出去时触发（竖直列表是侧滑，水平列表是竖滑）
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY,
                            int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            float value = 1 - Math.abs(dX) / viewHolder.itemView.getWidth();
            viewHolder.itemView.setAlpha(value);
            viewHolder.itemView.setScaleY(value);
        }
    }

    public interface ItemMoveListener {

        boolean onItemMove(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder targetViewHolder,
                           int fromPosition, int toPosition);
    }

}
