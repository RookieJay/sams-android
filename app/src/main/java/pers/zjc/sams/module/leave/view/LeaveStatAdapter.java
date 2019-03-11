package pers.zjc.sams.module.leave.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zp.android.zlib.base.AbsRecyclerAdapter;
import com.zp.android.zlib.base.RecyclerViewHolderHelper;

import java.util.List;

import pers.zjc.sams.R;
import pers.zjc.sams.common.Const;
import pers.zjc.sams.common.ScmpUtils;
import pers.zjc.sams.data.entity.Leave;

public class LeaveStatAdapter extends AbsRecyclerAdapter<Leave> {


    public LeaveStatAdapter(Context context) {
        super(context);
    }

    public LeaveStatAdapter(Context context, List<Leave> data) {
        super(context, data);
    }

    @Override
    protected View genItemView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leave_stat, parent, false);
    }

    @Override
    protected void onBindDataToViewHolder(RecyclerViewHolderHelper holder, Leave data, int position) {
        holder.setText(R.id.tv_course, data.getCourseName());
        holder.setText(R.id.tv_begin_time, data.getBeginTime());
        holder.setText(R.id.tv_end_time, data.getEndTime());
        holder.setText(R.id.tv_leaver, data.getStuName());
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
                Glide.with(ivStatus).load(R.mipmap.icon_not_pass).into(ivStatus);
                break;
            default:
                break;
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(Const.Keys.KEY_LEAVE, data);
                ScmpUtils.startWindow(view.getContext(), LeaveDetailFragment.class.getName(), bundle);
            }
        });
    }
}
