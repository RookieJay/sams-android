package pers.zjc.sams.module.leave.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zp.android.zlib.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pers.zjc.sams.R;
import pers.zjc.sams.common.Const;
import pers.zjc.sams.data.entity.Leave;

public class LeaveDetailFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_leaver)
    TextView tvLeaver;
    @BindView(R.id.rl_leaver)
    RelativeLayout rlLeaver;
    @BindView(R.id.tv_course)
    TextView tvCourse;
    @BindView(R.id.rl_choose_course)
    RelativeLayout rlChooseCourse;
    @BindView(R.id.tv_begin_time)
    TextView tvBeginTime;
    @BindView(R.id.rl_begin_time)
    RelativeLayout rlBeginTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.rl_end_time)
    RelativeLayout rlEndTime;
    @BindView(R.id.txt_reason)
    TextView txtReason;
    @BindView(R.id.et_reason)
    EditText etReason;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    Unbinder unbinder;
    private Leave leave;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_leave_detail;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        unbinder = ButterKnife.bind(this, getView());
        Bundle bundle = getArguments();
        if (bundle != null) {
            leave = bundle.getParcelable(Const.Keys.KEY_LEAVE);
            if (leave != null) {
                fillData(leave);
            }
        } else {
            showShortToast("获取请假详情失败");
        }
        initView();

    }

    private void initView() {
        etReason.setFocusable(false);
        btnBack.setOnClickListener(this);
    }

    private void fillData(Leave leave) {
        tvLeaver.setText(leave.getStuName());
        tvBeginTime.setText(leave.getBeginTime());
        tvEndTime.setText(leave.getEndTime());
        tvCourse.setText(leave.getCourseName());
        etReason.setText(leave.getReason());
        switch (leave.getStatus()) {
            case 0:
                tvStatus.setText("审批中");
                tvStatus.setTextColor(ContextCompat.getColor(getContext(), R.color.blue_color_picker));
                break;
            case 1:
                tvStatus.setText("已撤回");
                tvStatus.setTextColor(ContextCompat.getColor(getContext(), R.color.yellow_color_picker));
                break;
            case 2:
                tvStatus.setText("已通过");
                tvStatus.setTextColor(ContextCompat.getColor(getContext(), R.color.green_color_picker));
                break;
            case 3:
                tvStatus.setText("未通过");
                tvStatus.setTextColor(ContextCompat.getColor(getContext(), R.color.red_color_picker));
                break;
            default:
                tvStatus.setText("未知");
                tvStatus.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                break;
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

}
