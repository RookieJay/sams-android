package pers.zjc.sams.module.devicemanage.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.lxj.xpopup.XPopup;
import com.zp.android.zlib.base.AbsRecyclerAdapter;
import com.zp.android.zlib.base.RecyclerViewHolderHelper;

import pers.zjc.sams.R;
import pers.zjc.sams.common.ScmpUtils;
import pers.zjc.sams.data.entity.Device;

public class DeviceManageAdapter extends AbsRecyclerAdapter<Device> {

    public DeviceManageAdapter(Context context) {
        super(context);
    }

    @Override
    protected View genItemView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device_manage, parent, false);
    }

    @Override
    protected void onBindDataToViewHolder(RecyclerViewHolderHelper holder, Device data, int position) {
        holder.setText(R.id.tv_device_model, data.getDeviceModel());
        holder.setText(R.id.tv_version, data.getDeviceVersion());
        holder.setText(R.id.tv_stu_name, data.getStuName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


}
