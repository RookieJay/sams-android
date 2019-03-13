package pers.zjc.sams.module.devicemanage.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zp.android.zlib.base.AbsRecyclerAdapter;
import com.zp.android.zlib.base.RecyclerViewHolderHelper;

import pers.zjc.sams.R;
import pers.zjc.sams.data.entity.Device;

public class DeviceManageAdapter extends AbsRecyclerAdapter<Device> {

    public DeviceManageAdapter(Context context) {
        super(context);
    }

    @Override
    protected View genItemView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leave_list, parent, false);
    }

    @Override
    protected void onBindDataToViewHolder(RecyclerViewHolderHelper holder, Device data, int position) {

    }


}
