package pers.zjc.sams.module.sign.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zp.android.zlib.base.AbsRecyclerAdapter;
import com.zp.android.zlib.base.RecyclerViewHolderHelper;
import com.zp.android.zlib.utils.TimeUtils;

import java.util.List;

import pers.zjc.sams.R;
import pers.zjc.sams.data.entity.SignRecord;

public class SignLitAdapter extends AbsRecyclerAdapter<SignRecord> {


    public SignLitAdapter(Context context) {
        super(context);
    }

    public SignLitAdapter(Context context, List<SignRecord> data) {
        super(context, data);
    }

    @Override
    protected View genItemView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sign_list, parent, false);
    }

    @Override
    protected void onBindDataToViewHolder(RecyclerViewHolderHelper holder, SignRecord data, int position) {
        holder.setText(R.id.tv_course, data.getCourseName());
        holder.setText(R.id.tv_time, TimeUtils.date2String(data.getSignTime()));
        holder.setText(R.id.tv_location, data.getLocation());
        holder.setText(R.id.tv_ip, data.getSignIp());
    }
}
