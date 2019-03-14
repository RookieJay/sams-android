package pers.zjc.sams.module.user.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.zp.android.zlib.base.AbsRecyclerAdapter;
import com.zp.android.zlib.base.RecyclerViewHolderHelper;

import pers.zjc.sams.data.entity.User;

public class UserListAdapter extends AbsRecyclerAdapter<User> {

    public UserListAdapter(Context context) {
        super(context);
    }

    @Override
    protected View genItemView(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected void onBindDataToViewHolder(RecyclerViewHolderHelper holder, User data, int position) {

    }
}
