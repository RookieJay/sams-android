package pers.zjc.sams.module.leave.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.zp.android.zlib.base.AbsRecyclerAdapter;
import com.zp.android.zlib.base.RecyclerViewHolderHelper;

import java.util.List;

import pers.zjc.sams.R;
import pers.zjc.sams.common.Const;
import pers.zjc.sams.common.ScmpUtils;
import pers.zjc.sams.data.entity.Leave;

public class LeaveListAdapter extends AbsRecyclerAdapter<Leave> {

    private OnChangeLeaveStatusListener onChangeLeaveStatusListener;
    private String role = "1";
    private int leaveStatus;

    public void setOnChangeLeaveStatusListener(OnChangeLeaveStatusListener onChangeLeaveStatusListener) {
        this.onChangeLeaveStatusListener = onChangeLeaveStatusListener;
    }

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
                Glide.with(ivStatus).load(R.mipmap.icon_not_pass).into(ivStatus);
                break;
            default:
                break;
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(Const.Keys.KEY_LEAVE, data);
                ScmpUtils.startWindow(v.getContext(), LeaveDetailFragment.class.getName(), bundle);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (getRole().equals("2") && data.getStatus() == 0) {
                    XPopup.get(v.getContext()).asCenterList("---请假审批操作---",
                            new String[]{"通过", "不通过"}, new OnSelectListener() {
                                @Override
                                public void onSelect(int position, String text) {
                                    switch (position) {
                                        case 0:
                                            leaveStatus = 2;
                                            break;
                                        case 1:
                                            leaveStatus = 3;
                                            break;
                                        default:
                                            break;
                                    }
                                    onChangeLeaveStatusListener.onLeaveStatusChange(data.getId(), leaveStatus, position);
                                }
                            }).show();
                }
                return true;
            }
        });
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public interface OnChangeLeaveStatusListener {
        void onLeaveStatusChange(String id, int leaveStatus, int position);
    }
}
