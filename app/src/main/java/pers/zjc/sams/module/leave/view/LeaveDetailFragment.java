package pers.zjc.sams.module.leave.view;

import android.content.Intent;
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
import com.zp.android.zlib.http.HttpParam;
import com.zp.android.zlib.utils.StringUtils;

import java.util.concurrent.Executor;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pers.zjc.sams.R;
import pers.zjc.sams.app.AppConfig;
import pers.zjc.sams.app.SamsApplication;
import pers.zjc.sams.common.Const;
import pers.zjc.sams.data.entity.Leave;
import pers.zjc.sams.data.entity.Result;
import pers.zjc.sams.service.ApiService;

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
    @BindView(R.id.bar_right)
    TextView tvBarRight;
    Unbinder unbinder;
    private Leave leave;
    private AppConfig appConfig;
    private Executor executor;
    private ApiService apiService;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_leave_detail;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        unbinder = ButterKnife.bind(this, getView());
        appConfig = SamsApplication.getComponent().getAppConfig();
        executor = SamsApplication.getComponent().getExecutor();
        apiService = SamsApplication.getComponent().getHttpClient().create(ApiService.class);
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
        tvBarRight.setOnClickListener(this);
    }

    private void fillData(Leave leave) {
        tvLeaver.setText(leave.getStuName());
        tvBeginTime.setText(leave.getBeginTime());
        tvEndTime.setText(leave.getEndTime());
        tvCourse.setText(leave.getCourseName());
        etReason.setText(leave.getReason());
        switch (leave.getStatus()) {
            case 0:
                if (appConfig.getRole().equals("1")) {
                    tvBarRight.setVisibility(View.VISIBLE);
                }
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
        switch (v.getId()) {
            case R.id.btn_back:
                back();
                break;
            case R.id.bar_right:
                revoke();
                break;
            default:
                break;
        }
    }

    public void showNetWorkError() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showShortToast(getResources().getString(R.string.toast_fail_to_connect_server));
            }
        });
    }

    public void showMessage(String msg) {
        showShortToast(msg);
    }

    public void back() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (getActivity() != null) {
                    getActivity().finish();
                }
            }
        });
    }

    private void revoke() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                HttpParam.Factory factory = new HttpParam.Factory()
                        .add("id" ,leave.getId());
                Result result = apiService.revoke(factory.create());
                if (result != null) {
                    if (StringUtils.equals(result.getCode(), Const.HttpStatusCode.HttpStatus_200)) {
                        SamsApplication.get().sendBroadcast(new Intent(Const.Actions.ACTION_REVOKE_LEAVE));
                        back();
                    }
                    showMessage(result.getMessage());
                } else {
                    showNetWorkError();
                }
            }
        });
    }

}
