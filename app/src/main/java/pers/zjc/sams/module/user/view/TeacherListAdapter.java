package pers.zjc.sams.module.user.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.zp.android.zlib.base.AbsRecyclerAdapter;
import com.zp.android.zlib.base.RecyclerViewHolderHelper;
import com.zp.android.zlib.utils.StringUtils;

import pers.zjc.sams.R;
import pers.zjc.sams.common.Const;
import pers.zjc.sams.common.ScmpUtils;
import pers.zjc.sams.data.entity.Student;
import pers.zjc.sams.data.entity.Teacher;
import pers.zjc.sams.module.personinfo.view.PersonInfoFragment;
import pers.zjc.sams.module.user.contract.UserManageContract;
import pers.zjc.sams.module.user.presenter.UserManagePresenter;

public class TeacherListAdapter extends AbsRecyclerAdapter<Teacher> {

    private UserManageContract.Presenter presenter;

    public TeacherListAdapter(Context context, UserManagePresenter presenter) {
        super(context);
        this.presenter = presenter;
    }

    @Override
    protected View genItemView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_list, parent, false);
    }

    @Override
    protected void onBindDataToViewHolder(RecyclerViewHolderHelper holder, Teacher data, int vPosition) {
        holder.setText(R.id.tv_user_name, data.getName());
        holder.setText(R.id.tv_user_role, "教师");
        holder.setText(R.id.tv_user_tel, data.getTel());
        holder.setText(R.id.tv_user_status, data.getSexStr());
        holder.setOnClickListener(R.id.tv_user_tel, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v instanceof TextView) {
                    TextView tvTel = (TextView)v;
                    if (StringUtils.isEmpty(tvTel.getText().toString())) {
                        return;
                    }
                    callPhone(tvTel.getText().toString());
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(Const.Keys.KEY_TEACHER, data);
                ScmpUtils.startWindow(mContext, PersonInfoFragment.class.getName(), bundle);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                XPopup.get(mContext)
                      .asCenterList("---操作---", new String[] { "删除" }, new OnSelectListener() {
                          @Override
                          public void onSelect(int position, String text) {
                              switch (position) {
                                  case 0:
                                      presenter.delete(data, vPosition);
                                      break;
                                  default:
                                      break;
                              }
                          }
                      })
                      .show();
                return true;
            }
        });
    }

    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        mContext.startActivity(intent);
    }

}
