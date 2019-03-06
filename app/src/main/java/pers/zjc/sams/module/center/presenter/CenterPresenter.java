package pers.zjc.sams.module.center.presenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import pers.zjc.sams.R;
import pers.zjc.sams.app.AppConfig;
import pers.zjc.sams.data.entity.FunctionInfo;
import pers.zjc.sams.module.approval.ApprovalFragment;
import pers.zjc.sams.module.center.contract.CenterContract;
import pers.zjc.sams.module.course.view.CourseListFragment;
import pers.zjc.sams.module.face.CheckFragment;
import pers.zjc.sams.module.leave.view.LeaveFragment;
import pers.zjc.sams.module.myattence.view.MyAttenceFragment;
import pers.zjc.sams.module.sign.view.SignFragment;

public class CenterPresenter implements CenterContract.Presenter {

    private CenterContract.View view;
    private List<FunctionInfo> functions = new ArrayList<>();
    @Inject
    AppConfig appConfig;

    @Inject
    CenterPresenter(CenterContract.View view) {
        this.view = view;
    }

    @Override
    public void loadCenterData() {
        initFunctions();
        view.showData(functions);
    }

    private void initFunctions() {
        switch (appConfig.getRole()) {
            case "0":
                functions.add(new FunctionInfo("用户管理", R.drawable.icon_function_user_manage, SignFragment.class.getName()));
                functions.add(new FunctionInfo("设备管理", R.drawable.icon_function_device_manage, SignFragment.class.getName()));
//                functions.add(new FunctionInfo("公告管理", R.drawable.icon_function_sign, SignFragment.class.getName()));
                break;
            case "1":
                functions.add(new FunctionInfo("签到", R.drawable.icon_function_sign, SignFragment.class.getName()));
                functions.add(new FunctionInfo("请假", R.drawable.icon_function_leaving, LeaveFragment.class.getName()));
                functions.add(new FunctionInfo("课程", R.drawable.icon_function_course, CourseListFragment.class.getName()));
                break;
            case "2":
                functions.add(new FunctionInfo("点到", R.drawable.icon_function_check, CheckFragment.class.getName()));
                functions.add(new FunctionInfo("审批", R.drawable.icon_function_approval, ApprovalFragment.class.getName()));
                break;
            default:
                break;
        }
    }

    @Override
    public void loadSatisticsData() {
        switch (appConfig.getRole()) {
            case "0":
                functions.add(new FunctionInfo("用户信息", R.drawable.icon_function_user_manage, SignFragment.class.getName()));
                functions.add(new FunctionInfo("设备信息", R.drawable.icon_function_device_manage, SignFragment.class.getName()));
//                functions.add(new FunctionInfo("公告管理", R.drawable.icon_function_sign, SignFragment.class.getName()));
                break;
            case "1":
                functions.add(new FunctionInfo("签到统计", R.drawable.icon_function_sign, SignFragment.class.getName()));
                functions.add(new FunctionInfo("请假统计", R.drawable.icon_function_leaving, LeaveFragment.class.getName()));
                functions.add(new FunctionInfo("考勤统计", R.drawable.icon_function_attence, MyAttenceFragment.class.getName()));
                functions.add(new FunctionInfo("课程信息", R.drawable.icon_function_course, LeaveFragment.class.getName()));
                break;
            case "2":
                functions.add(new FunctionInfo("审批统计", R.drawable.icon_function_approval, ApprovalFragment.class.getName()));
                functions.add(new FunctionInfo("考勤统计", R.drawable.icon_function_attence, MyAttenceFragment.class.getName()));
                break;
            default:
                break;
        }
        view.showData(functions);
    }

}
