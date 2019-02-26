package pers.zjc.sams.module.leave.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zp.android.zlib.base.AbsRecyclerAdapter;
import com.zp.android.zlib.base.RecyclerViewHolderHelper;

import java.util.List;

import pers.zjc.sams.R;
import pers.zjc.sams.data.entity.Course;


public class CourseAdapter extends AbsRecyclerAdapter<Course> {

    private ItemClickListener itemClickListener;

    public CourseAdapter(Context context, ItemClickListener itemClickListener) {
        super(context);
        this.itemClickListener = itemClickListener;
    }

    public CourseAdapter(Context context, List<Course> data) {
        super(context, data);
    }

    @Override
    protected View genItemView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_options, parent, false);
    }

    @Override
    protected void onBindDataToViewHolder(RecyclerViewHolderHelper holder, Course data, int position) {
        holder.setText(R.id.tv_option, data.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onPopupItemClick(data);
            }
        });
    }

    interface ItemClickListener {

        void onPopupItemClick(Course data);
    }
}
