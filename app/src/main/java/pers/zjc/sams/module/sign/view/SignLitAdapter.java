package pers.zjc.sams.module.sign.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.zp.android.zlib.base.AbsRecyclerAdapter;
import com.zp.android.zlib.base.RecyclerViewHolderHelper;
import com.zp.android.zlib.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import pers.zjc.sams.R;
import pers.zjc.sams.data.entity.SignRecord;

public class SignLitAdapter extends AbsRecyclerAdapter<SignRecord> {

    private String role = "";
    private int attenceStatus;

    private OnChangeSignStatusListener onChangeSignStatusListener;

    public void setOnChangeSignStatusListener(OnChangeSignStatusListener onChangeSignStatusListener) {
        this.onChangeSignStatusListener = onChangeSignStatusListener;
    }

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
        holder.setText(R.id.tv_ip, data.getStuName());
        //教师审批
        if (role.equals("2")) {
            holder.setText(R.id.tv_ip, data.getStuName());
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    XPopup.get(view.getContext()).asCenterList("---对学生" + data.getStuName() + "标记---",
                            new String[]{"正常行课", "迟到", "请假", "旷课", "早退"}, new OnSelectListener() {
                                @Override
                                public void onSelect(int position, String text) {
                                    switch (position) {
                                        case 0:
                                            attenceStatus = 0;
                                            break;
                                        case 1:
                                            attenceStatus = 1;
                                            break;
                                        case 2:
                                            attenceStatus = 2;
                                            break;
                                        case 3:
                                            attenceStatus = 3;
                                            break;
                                        case 4:
                                            attenceStatus = 4;
                                            break;
                                        default:
                                            break;
                                    }
                                    onChangeSignStatusListener.onChange(attenceStatus, data);
                                }
                            }).show();
                    return true;
                }
            });
        }
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void filter(int i) {
        List<SignRecord> records = new ArrayList<>();
        for (SignRecord record : mData) {
            if (record.getSignStatus() == i) {
                records.add(record);
            }
        }
        replaceAll(records);
        notifyDataSetChanged();
    }

    public interface OnChangeSignStatusListener {
        void onChange(int attenceStatus, SignRecord data);
    }
}
