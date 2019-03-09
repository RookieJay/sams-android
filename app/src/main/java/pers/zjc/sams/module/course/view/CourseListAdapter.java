package pers.zjc.sams.module.course.view;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zp.android.zlib.base.AbsRecyclerAdapter;
import com.zp.android.zlib.base.RecyclerViewHolderHelper;
import com.zp.android.zlib.utils.TimeUtils;

import pers.zjc.sams.R;
import pers.zjc.sams.data.entity.Course;
import razerdp.basepopup.BasePopupWindow;

public class CourseListAdapter extends AbsRecyclerAdapter<Course> {

    private OnItemLongCLickListener onItemLongCLickListener;

    public CourseListAdapter(Context context, OnItemLongCLickListener onItemLongCLickListener) {
        super(context);
        this.onItemLongCLickListener = onItemLongCLickListener;
    }

    @Override
    protected View genItemView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course_list, parent, false);
    }

    @Override
    protected void onBindDataToViewHolder(RecyclerViewHolderHelper holder, Course data, int position) {
        holder.setText(R.id.tv_course_name, data.getName());
        holder.setText(R.id.tv_begin_time, TimeUtils.date2String(data.getBeginTime()));
        holder.setText(R.id.tv_end_time, TimeUtils.date2String(data.getEndTime()));
        holder.setText(R.id.tv_location, data.getClassroom());
        holder.setText(R.id.c_numb, String.valueOf(data.getId()));
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
               onItemLongCLickListener.onItemLongClick(view, data, position);
               return true;
            }
        });
    }

    public interface OnItemLongCLickListener {
        void onItemLongClick(View view, Course data, int position);
    }
}
