package pers.zjc.sams.module.register.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.zp.android.zlib.utils.PhoneUtils;

import java.util.concurrent.Executor;

import javax.inject.Inject;

import pers.zjc.sams.app.AppConfig;
import pers.zjc.sams.common.Const;
import pers.zjc.sams.data.entity.Result;
import pers.zjc.sams.data.entity.User;
import pers.zjc.sams.module.register.contract.RegisterContract;
import pers.zjc.sams.module.register.model.RegisterModel;

public class RegisterPresenter implements RegisterContract.Presenter {

    private RegisterContract.View view;
    @Inject
    RegisterModel model;
    @Inject
    AppConfig appConfig;
    @Inject
    Executor executor;

    @Inject
    RegisterPresenter(RegisterContract.View view) {
        this.view = view;
    }

    @Override
    public void loadImei() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                String imei = null;
                try {
                    imei = PhoneUtils.getIMEI();
                }
                catch (SecurityException e) {
                    e.printStackTrace();
                }
                view.fillImei(TextUtils.isEmpty(imei) ? "" : imei);
            }
        });
    }

    @Override
    public void register(User user, String deviceId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Result result = model.register(user, deviceId);
                    if (result != null) {
                        if (result.getCode().equals(Const.HttpStatusCode.HttpStatus_200)) {
                            view.showMessage(result.getMessage());
                        } else {
                            view.showMessage(result.getMessage());
                        }
                    }
                    else {
                        view.showNetWorkErro();
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                    Log.d("捕获异常", e.getMessage());
                    view.showMessage(e.getMessage());
                }
            }
        });
    }


}
