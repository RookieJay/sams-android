package pers.zjc.sams.module.leave.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zp.android.zlib.base.AbsRecyclerAdapter;
import com.zp.android.zlib.base.RecyclerViewHolderHelper;
import com.zp.android.zlib.utils.TimeUtils;

import java.util.List;

import pers.zjc.sams.R;
import pers.zjc.sams.data.entity.Leave;
import pers.zjc.sams.data.entity.SignRecord;

public class LeaveListAdapter extends AbsRecyclerAdapter<Leave> {


    public LeaveListAdapter(Context context) {
        super(context);
    }

    public LeaveListAdapter(Context context, List<Leave> data) {
        super(context, data);
    }

    @Override
    protected View genItemView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leave_list, parent, false);
    }

    @Override
    protected void onBindDataToViewHolder(RecyclerViewHolderHelper holder, Leave data, int position) {
        holder.setText(R.id.tv_course, data.getCourseName());
        holder.setText(R.id.tv_begin_time, data.getBeginTime());
        holder.setText(R.id.tv_end_time, data.getEndTime());
        holder.setText(R.id.tv_reason, data.getReason());
        ImageView ivStatus = holder.itemView.findViewById(R.id.iv_status);
        switch (data.getStatus()) {
            case 0:
                Glide.with(ivStatus).load(R.mipmap.icon_checking_leaving).into(ivStatus);
                break;
            case 1:
                Glide.with(ivStatus).load(R.mipmap.icon_cancle).into(ivStatus);
                break;
            case 2:
                Glide.with(ivStatus).load(R.mipmap.icon_pass).into(ivStatus);
                break;
            case 3:
                Glide.with(ivStatus).load(R.mipmap.icon_refused).into(ivStatus);
                break;
            default:
                break;
        }
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                return true;
            }
        });
    }
}
