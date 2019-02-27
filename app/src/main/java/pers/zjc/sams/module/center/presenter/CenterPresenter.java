package pers.zjc.sams.module.center.presenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import pers.zjc.sams.R;
import pers.zjc.sams.data.entity.FunctionInfo;
import pers.zjc.sams.module.approval.ApprovalFragment;
import pers.zjc.sams.module.center.contract.CenterContract;
import pers.zjc.sams.module.leave.view.LeaveFragment;
import pers.zjc.sams.module.sign.view.SignFragment;

public class CenterPresenter implements CenterContract.Presenter {

    private CenterContract.View view;
    private List<FunctionInfo> functions = new ArrayList<>();

    @Inject
    CenterPresenter(CenterContract.View view) {
        this.view = view;
    }

    @Override
    public void loadData() {
        initFunctions();
        view.showData(functions);
    }

    private void initFunctions() {
        functions.add(new FunctionInfo("签到", R.drawable.icon_function_sign, SignFragment.class.getName()));
        functions.add(new FunctionInfo("请假", R.drawable.icon_function_leaving, LeaveFragment.class.getName()));
        functions.add(new FunctionInfo("审批", R.drawable.icon_function_approval, ApprovalFragment.class.getName()));
    }
}
