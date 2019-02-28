package pers.zjc.sams.module.leave.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.zp.android.zlib.base.BaseFragment;
import com.zp.android.zlib.utils.StringUtils;
import com.zp.android.zlib.utils.TimeUtils;
import com.zp.android.zlib.utils.ToastUtils;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pers.zjc.sams.R;
import pers.zjc.sams.app.AppConfig;
import pers.zjc.sams.app.SamsApplication;
import pers.zjc.sams.common.ScmpUtils;
import pers.zjc.sams.data.entity.Course;
import pers.zjc.sams.data.entity.Leave;
import pers.zjc.sams.module.leave.DaggerLeaveComponent;
import pers.zjc.sams.module.leave.LeaveModule;
import pers.zjc.sams.module.leave.contract.LeaveContract;
import pers.zjc.sams.module.leave.presenter.LeavePresenter;

public class LeaveFragment extends BaseFragment implements LeaveContract.View, View.OnClickListener, CoursePopup.PopupItemClick {

    @Inject
    LeavePresenter presenter;
    @Inject
    AppConfig appConfig;
    Unbinder unbinder;
    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txt_char)
    TextView txtChar;
    @BindView(R.id.iv_course_icon)
    ImageView ivCourseIcon;
    @BindView(R.id.rl_choose_course)
    RelativeLayout rlChooseCourse;
    @BindView(R.id.txt_char_start)
    TextView txtCharStart;
    @BindView(R.id.iv_start_time_icon)
    ImageView ivStartTimeIcon;
    @BindView(R.id.rl_begin_time)
    RelativeLayout rlBeginTime;
    @BindView(R.id.txt_char_end)
    TextView txtCharEnd;
    @BindView(R.id.iv_end_time_icon)
    ImageView ivEndTimeIcon;
    @BindView(R.id.rl_end_time)
    RelativeLayout rlEndTime;
    @BindView(R.id.txt_char_reason)
    TextView txtCharReason;
    @BindView(R.id.txt_reason)
    TextView txtReason;
    @BindView(R.id.tv_course)
    TextView tvCourse;
    @BindView(R.id.tv_begin_time)
    TextView tvBeginTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.et_reason)
    EditText etReason;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.iv_history)
    ImageView ivHistory;

    private TimePickerView pvTime;

    private Date beginTime;
    private Date endTime;
    private int courseId;
    private Leave commitLeave = new Leave();


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_leave;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerLeaveComponent.builder().appComponent(SamsApplication.getComponent())
                .leaveModule(new LeaveModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        unbinder = ButterKnife.bind(this, getView());
        initView();
    }

    private void initView() {
        btnBack.setOnClickListener(this);
        rlChooseCourse.setOnClickListener(this);
        rlBeginTime.setOnClickListener(this);
        rlEndTime.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
        ivHistory.setOnClickListener(this);
        initTimePicker();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                back();
                break;
            case R.id.rl_choose_course:
                presenter.loadCourse();
                break;
            case R.id.rl_begin_time:
                pvTime.show(tvBeginTime);
                break;
            case R.id.rl_end_time:
                pvTime.show(tvEndTime);
                break;
            case R.id.tv_submit:
                if (courseId == 0) {
                    showShortToast("请选择课程");
                    return;
                }
                if (beginTime == null) {
                    showShortToast("请选择开始时间");
                    return;
                }
                if (endTime == null) {
                    showShortToast("请选择结束时间");
                    return;
                }
                String reason = etReason.getText().toString();
                if (StringUtils.isEmpty(reason)) {
                    showShortToast("请填写请假原因");
                    return;
                }
                commitLeave.setCourseId(courseId);
                commitLeave.setBeginTime(beginTime);
                commitLeave.setEndTime(endTime);
                commitLeave.setReason(reason);
                commitLeave.setStuId(Integer.valueOf(appConfig.getUserId()));
                presenter.commit(commitLeave);
                break;
            case R.id.iv_history:
                switchToLeaveListFragment();
                break;
            default:
                break;
        }
    }

    private void switchToLeaveListFragment() {
        ScmpUtils.startWindow(getContext(), LeaveListFragment.class.getName());
    }

    private void initTimePicker() {
        pvTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                Log.d("onTimeSelect: ", TimeUtils.date2String(date));
                switch (v.getId()) {
                    case R.id.tv_begin_time:
                        beginTime = date;
                        if (isGreater(beginTime, endTime)) {
                            ToastUtils.showShort("结束时间必须大于开始时间");
                            return;
                        }
                        tvBeginTime.setText(TimeUtils.date2String(date));
                        break;
                    case R.id.tv_end_time:
                        endTime = date;
                        if (isGreater(beginTime, endTime)) {
                            ToastUtils.showShort("结束时间必须大于开始时间");
                            return;
                        }
                        tvEndTime.setText(TimeUtils.date2String(date));
                        break;
                    default:
                        break;
                }
            }
        }).setType(new boolean[]{true, true, true, true, true, false}) //显示年月日时分
                .build();
    }

    public boolean isGreater(Date date1, Date date2) {
        if (date1 != null && date2 != null) {
            if (date1.getTime() > date2.getTime()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void showCoursePopupWindow(List<Course> data) {
        LeaveFragment context = this;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CoursePopup coursePopup = new CoursePopup(getContext(), context);
                coursePopup.setData(data);
                coursePopup.setPopupGravity(Gravity.CENTER).showPopupWindow();
            }
        });

    }

    public void back() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                FragmentManager fm = getFragmentManager();
                if (fm != null) {
                    fm.popBackStackImmediate();
                }
            }
        });
    }

    @Override
    public void onPopupItemclick(Course data) {
        tvCourse.setText(data.getName());
        courseId = data.getId();
        beginTime = data.getBeginTime();
        endTime = data.getEndTime();
        tvBeginTime.setText(TimeUtils.date2String(beginTime));
        tvEndTime.setText(TimeUtils.date2String(endTime));
    }

    @Override
    public void showMessage(String msg) {
        showShortToast(msg);
    }

    @Override
    public void showNetWorkErro() {
        showShortToast(getResources().getString(R.string.toast_fail_to_connect_server));
    }

    @Override
    public void showDialogProgress() {
        showProgressDialogInternal(getResources().getString(R.string.txt_loading));
    }

    @Override
    public void closeDialogProgress() {
        closeProgressDialogInternal();
    }
}
