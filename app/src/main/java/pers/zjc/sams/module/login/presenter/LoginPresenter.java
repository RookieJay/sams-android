package pers.zjc.sams.module.login.presenter;

import android.util.Log;

import com.zp.android.zlib.utils.DeviceUtils;
import com.zp.android.zlib.utils.PhoneUtils;
import com.zp.android.zlib.utils.StringUtils;

import java.util.concurrent.Executor;

import javax.inject.Inject;

import pers.zjc.sams.app.AppConfig;
import pers.zjc.sams.common.Const;
import pers.zjc.sams.data.datawrapper.UserWrapper;
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
    private String imei;

    @Inject
    LoginPresenter(LoginContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void init(boolean isLogout) {
        try {
            imei = PhoneUtils.getIMEI();
        }
        catch (SecurityException e) {
            e.printStackTrace();
        }
        if (appConfig.isLogin()) {
            login(appConfig.getAccount(), appConfig.getPassWord(), true);
        }
    }

    @Override
    public void login(String account, String pwd, boolean isRemember) {
        mView.showProgress();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                appConfig.setRemember(isRemember);
                String phoneModel = DeviceUtils.getModel();
                String androidVersion = DeviceUtils.getSDKVersionName();
                Log.d("phoneModel+AndroidId", phoneModel+" :"+androidVersion);
                if (StringUtils.isEmpty(imei)) {
                    mView.showMessage("请开启电话权限以获取手机识别码");
                    mView.closeProgress();
                    return;
                }
                Result<UserWrapper> result = model.login(account, pwd, imei);
                if (result != null ) {
                    if (result.getCode().equals(Const.HttpStatusCode.HttpStatus_200)) {
                        saveToLocal(result);
                        if (result.getData() != null) {
                            mView.swithToMainFragment(result.getData().getRole());
                            mView.showMessage(result.getMessage());
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
                    appConfig.setSex(result.getData().getStudent().getSex() == 1 ? "男" : "女");
                    appConfig.setTel(result.getData().getStudent().getTel());
                    appConfig.setMajor(result.getData().getStudent().getMajor());
                }
                if (result.getData().getTeacher() != null) {
                    appConfig.setSex(result.getData().getTeacher().getSex() == 1 ? "男" : "女");
                    appConfig.setTel(result.getData().getTeacher().getTel());
                    appConfig.setMajor(result.getData().getTeacher().getMajor());
                }
                appConfig.setUserName(result.getData().getUserName());
                appConfig.save();
            }
        });
    }
}
