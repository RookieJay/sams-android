package pers.zjc.sams.module.myattence.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.zp.android.zlib.base.AbsRecyclerAdapter;
import com.zp.android.zlib.base.RecyclerViewHolderHelper;
import com.zp.android.zlib.utils.TimeUtils;

import java.util.List;

import pers.zjc.sams.R;
import pers.zjc.sams.common.Const;
import pers.zjc.sams.data.entity.AttenceRecord;
import pers.zjc.sams.widget.roundedimageview.RoundedImageView;

public class MyAttenceAdapter extends AbsRecyclerAdapter<AttenceRecord> {

    public MyAttenceAdapter(Context context) {
        super(context);
    }

    public MyAttenceAdapter(Context context, List<AttenceRecord> data) {
        super(context, data);
    }

    @Override
    protected View genItemView(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attence_list, parent, false);
        return view;
    }

    @Override
    protected void onBindDataToViewHolder(RecyclerViewHolderHelper holder, AttenceRecord data, int position) {
        RoundedImageView ivStatus = holder.itemView.findViewById(R.id.iv_head);
        switch (data.getStatus()) {
            case 0:
                Glide.with(ivStatus).load(R.mipmap.normal_attence).into(ivStatus);
                holder.setText(R.id.tv_status, "正常");
                holder.setText(R.id.tv_reminding, mContext.getResources().getString(R.string.reminding_normal));
                break;
            case 1:
                holder.setText(R.id.tv_status, "迟到");
                Glide.with(ivStatus).load(R.mipmap.late).into(ivStatus);
                holder.setText(R.id.tv_reminding, mContext.getResources().getString(R.string.reminding_late));
                break;
            case 2:
                Glide.with(ivStatus).load(R.mipmap.leaving).into(ivStatus);
                holder.setText(R.id.tv_status, "请假");
                holder.setText(R.id.tv_reminding, mContext.getResources().getString(R.string.reminding_leaving));
                break;
            case 3:
                Glide.with(ivStatus).load(R.mipmap.absent).into(ivStatus);
                holder.setText(R.id.tv_status, "缺课");
                holder.setText(R.id.tv_reminding, mContext.getResources().getString(R.string.reminding_absent));
                break;
            case 4:
                Glide.with(ivStatus).load(R.mipmap.earlier_leave).into(ivStatus);
                holder.setText(R.id.tv_status, "早退");
                holder.setText(R.id.tv_reminding, mContext.getResources().getString(R.string.reminding_earlier_leave));
                break;
            default:
                break;
        }
        holder.setText(R.id.time, TimeUtils.date2String(data.getUpdateTime(), Const.DateFormat.WITH_HMS));
        holder.setText(R.id.tv_operator, data.getOperator());
        holder.setText(R.id.tv_course, data.getCourseId().toString());
        holder.setText(R.id.tvContent, data.getCourseId().toString());
    }
}
