package pers.zjc.sams.module.course.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.zp.android.zlib.base.BaseFragment;
import com.zp.android.zlib.utils.TimeUtils;
import com.zp.android.zlib.utils.ToastUtils;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pers.zjc.sams.R;
import pers.zjc.sams.data.entity.Course;

public class CourseEditFragment extends BaseFragment implements View.OnClickListener {


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

    Course course;
    private TimePickerView pvTime;
    private Date beginTime;
    private Date endTime;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_edit_course;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        unbinder = ButterKnife.bind(this,getView());
        Bundle bundle = getArguments();
        if (bundle != null) {
            course = bundle.getParcelable("course");
        }

        initView();

    }

    private void initView() {
        barTitle.setText("课程添加");
        barRight.setText("提交");
        barRight.setVisibility(View.VISIBLE);
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

    private void back() {
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
}
