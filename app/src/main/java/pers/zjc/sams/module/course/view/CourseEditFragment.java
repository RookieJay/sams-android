package pers.zjc.sams.module.course.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.zp.android.zlib.base.BaseFragment;
import com.zp.android.zlib.utils.KeyboardUtils;
import com.zp.android.zlib.utils.StringUtils;
import com.zp.android.zlib.utils.TimeUtils;
import com.zp.android.zlib.utils.ToastUtils;

import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pers.zjc.sams.R;
import pers.zjc.sams.app.SamsApplication;
import pers.zjc.sams.data.entity.Course;
import pers.zjc.sams.module.center.DaggerCenterComponent;
import pers.zjc.sams.module.course.CourseEditModule;
import pers.zjc.sams.module.course.DaggerCourseEditComponent;
import pers.zjc.sams.module.course.contract.CourseEditContract;
import pers.zjc.sams.module.course.presenter.CourseEditPresenter;

public class CourseEditFragment extends BaseFragment implements View.OnClickListener, CourseEditContract.View {

    public static final String START_TYPE = "startType";

    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.bar_right)
    TextView barRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_course_name)
    EditText etCourseName;
    @BindView(R.id.tv_begin_time)
    TextView tvBeginTime;
    @BindView(R.id.tv_course_end_time)
    TextView tvCourseEndTime;
    @BindView(R.id.et_classroom)
    EditText etClassroom;
    Unbinder unbinder;

    private TimePickerView pvTime;
    private Date beginTime;
    private Date endTime;
    @Inject
    CourseEditPresenter presenter;
    Course course;
    int startType;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_edit_course;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerCourseEditComponent.builder()
                                 .appComponent(SamsApplication.getComponent())
                                 .courseEditModule(new CourseEditModule(this))
                                 .build()
                                 .inject(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        unbinder = ButterKnife.bind(this,getView());
        Bundle bundle = getArguments();
        if (bundle != null) {
            course = bundle.getParcelable("course");
            startType = bundle.getInt(START_TYPE, 0);
        }

        initView();

    }

    private void initView() {
        switch (startType) {
            case 0:
                break;
            case 1:
                barTitle.setText("课程编辑");
                break;
            case 2:
                barTitle.setText("课程添加");
                break;
            default:
                break;
        }
        barRight.setText("提交");
        barRight.setVisibility(View.VISIBLE);
        barRight.setOnClickListener(this);
        initTimePicker();
        if (null != course) {
            etCourseName.setText(course.getName());
            tvBeginTime.setText(TimeUtils.date2String(course.getBeginTime()));
            tvCourseEndTime.setText(TimeUtils.date2String(course.getEndTime()));
            etClassroom.setText(course.getClassroom());
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        tvBeginTime.setOnClickListener(this);
        tvCourseEndTime.setOnClickListener(this);
        hideKeyboardWhenLostFocus(etCourseName);
        hideKeyboardWhenLostFocus(etClassroom);
    }

    public int getStartType() {
        return startType;
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
                    case R.id.tv_course_end_time:
                        endTime = date;
                        if (isGreater(beginTime, endTime)) {
                            ToastUtils.showShort("结束时间必须大于开始时间");
                            return;
                        }
                        tvCourseEndTime.setText(TimeUtils.date2String(date));
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bar_right:
                String courseName = etCourseName.getText().toString();
                String beginTime = tvBeginTime.getText().toString();
                String endTime = tvCourseEndTime.getText().toString();
                String classRoom = etClassroom.getText().toString();
                if (StringUtils.isEmpty(courseName) || StringUtils.isEmpty(beginTime) || StringUtils.isEmpty(endTime) || StringUtils.isEmpty(classRoom)) {
                    showShortToast("请完善课程信息");
                    return;
                }
                if (course == null) {
                    course = new Course();
                }
                course.setName(courseName);
                course.setBeginTime(TimeUtils.string2Date(beginTime));
                course.setEndTime(TimeUtils.string2Date(endTime));
                course.setClassroom(classRoom);
                presenter.commit(course);
                break;
            case R.id.tv_begin_time:
                pvTime.show(tvBeginTime);
                break;
            case R.id.tv_course_end_time:
                pvTime.show(tvCourseEndTime);
                break;
            default:
                break;
        }
    }

    @Override
    public void showMessage(String message) {
        showShortToast(message);
    }

    @Override
    public void showError() {
        showShortToast(getResources().getString(R.string.toast_fail_to_connect_server));
    }
}
