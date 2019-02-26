package pers.zjc.sams.module.center.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.zp.android.zlib.base.AbsRecyclerAdapter;
import com.zp.android.zlib.base.RecyclerViewHolderHelper;

import java.util.List;

import pers.zjc.sams.R;
import pers.zjc.sams.data.entity.FunctionInfo;

public class CenterAdapter extends AbsRecyclerAdapter<FunctionInfo> {

    private OnItemClickListener onItemClickListener;

    public CenterAdapter(Context context, OnItemClickListener onItemClickListener) {
        super(context);
        this.onItemClickListener = onItemClickListener;
    }

    public CenterAdapter(Context context, List<FunctionInfo> data) {
        super(context, data);
    }

    @Override
    protected View genItemView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_center, parent, false);
    }

    @Override
    protected void onBindDataToViewHolder(RecyclerViewHolderHelper holder, FunctionInfo data, int position) {
        holder.setImageResource(R.id.icon, data.getIcon());
        holder.setText(R.id.name, data.getFunctionName());
        holder.setOnClickListener(R.id.lygriditem, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(view, data);
            }
        });
    }

    interface OnItemClickListener{

        void onItemClick(View view ,FunctionInfo data);
    }
}
