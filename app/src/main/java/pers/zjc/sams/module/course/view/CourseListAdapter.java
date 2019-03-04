package pers.zjc.sams.module.course.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zp.android.zlib.base.AbsRecyclerAdapter;
import com.zp.android.zlib.base.RecyclerViewHolderHelper;
import com.zp.android.zlib.utils.TimeUtils;

import pers.zjc.sams.R;
import pers.zjc.sams.data.entity.Course;

public class CourseListAdapter extends AbsRecyclerAdapter<Course> {

    public CourseListAdapter(Context context) {
        super(context);
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
    }
}
