package pers.zjc.sams.module.devicemanage.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.zp.android.zlib.base.AbsRecyclerAdapter;
import com.zp.android.zlib.base.RecyclerViewHolderHelper;

import javax.inject.Inject;

import pers.zjc.sams.R;
import pers.zjc.sams.data.entity.Device;
import pers.zjc.sams.module.devicemanage.presenter.DeviceManagePresenter;

public class DeviceManageAdapter extends AbsRecyclerAdapter<Device> {

    @Inject
    DeviceManagePresenter presenter;

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
        holder.setText(R.id.tv_status, data.getDeviceStatusStr());
        String[] options = new String[1];
        switch (data.getDeviceStatus()) {
            case 0:
                holder.setTextColor(R.id.tv_status, R.color.black);
                options[0] = "注销";
                break;
            case 1:
                options[0] = "开通";
                break;
            default:
                break;
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                XPopup.get(mContext)
                      .asCenterList("--操作--", options, new OnSelectListener() {
                          @Override
                          public void onSelect(int position, String text) {
                                switch (position) {
                                    case 0:
                                        presenter.cancel(data);
                                        break;
                                    case 1:
                                        presenter.activate(data);
                                        break;
                                    default:
                                        break;
                                }
                          }
                      }).show();
            }
        });
    }


}
