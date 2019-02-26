package pers.zjc.sams.module.login.presenter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import pers.zjc.sams.app.AppConfig;
import pers.zjc.sams.common.Const;
import pers.zjc.sams.common.EventBusUtil;
import pers.zjc.sams.data.datawrapper.UserWrapper;
import pers.zjc.sams.data.entity.AttenceRecord;
import pers.zjc.sams.data.entity.Result;
import pers.zjc.sams.module.login.contract.LoginContract;
import pers.zjc.sams.module.login.model.LoginModel;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View mView;
    @Inject
    LoginModel model;
    @Inject
    Executor executor;
    @Inject
    AppConfig appConfig;

    @Inject
    LoginPresenter(LoginContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void init() {

    }

    @Override
    public void login(String account, String pwd, boolean isRemember) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mView.showProgress();
                Result<UserWrapper> result = model.login(account, pwd);
                if (result != null ) {
                    if (result.getCode().equals(Const.HttpStatusCode.HttpStatus_200)) {
                        saveToLocal(result);
                        if (result.getData() != null) {
                            List<AttenceRecord> records = result.getData().getRecords();
                            if (records == null) {
                                records = new ArrayList<>();
                            }
                            EventBus.getDefault().post(records);
                            mView.swithToMainFragment(records);
                        }
                        mView.showMessage(result.getMessage());

                    } else {
                        mView.showMessage(result.getMessage());
                    }
                    mView.closeProgress();
                } else {
                    mView.showNetWorkErroMessage();
                    mView.closeProgress();
                }
            }

            private void saveToLocal(Result<UserWrapper> result) {
                appConfig.setLogin(true);
                String token = result.getData().getToken();
                appConfig.setToken(token);
                appConfig.setAccount(account);
                appConfig.setPassWord(pwd);

                appConfig.setUserId(result.getData().getUserId());
                appConfig.setRole(result.getData().getRole());
                if (result.getData().getStudent() != null) {
                    appConfig.setUserName(result.getData().getStudent().getName());
                    appConfig.setTel(result.getData().getStudent().getTel());
                }
                if (result.getData().getTeacher() != null) {
                    appConfig.setUserName(result.getData().getTeacher().getName());
                    appConfig.setTel(result.getData().getTeacher().getTel());
                }
                if (result.getData().getAdmin() != null) {
                    appConfig.setUserName(result.getData().getAdmin().getName());
                }
                appConfig.save();
            }
        });
    }
}
