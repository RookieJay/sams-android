package pers.zjc.sams.module.personcenter.presenter;

import android.content.Intent;

import java.util.concurrent.Executor;

import javax.inject.Inject;

import pers.zjc.sams.app.AppConfig;
import pers.zjc.sams.app.SamsApplication;
import pers.zjc.sams.common.Const;
import pers.zjc.sams.data.entity.Result;
import pers.zjc.sams.data.entity.User;
import pers.zjc.sams.module.personcenter.contract.ModifyPwdContract;
import pers.zjc.sams.module.personcenter.model.ModifyPwdModel;

public class ModifyPwdPresenter implements ModifyPwdContract.Presenter {

    @Inject
    AppConfig appConfig;
    @Inject
    ModifyPwdModel model;
    @Inject
    Executor executor;

    private ModifyPwdContract.View view;

    @Inject
    ModifyPwdPresenter(ModifyPwdContract.View view) {
        this.view = view;
    }

    @Override
    public void commit(String oldPwd, String newPwd) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                User user = new User();
                user.setId(Integer.valueOf(appConfig.getUserId()));
                //暂用account代替老密码
                user.setAccount(oldPwd);
                user.setPassword(newPwd);
                try {
                    Result result = model.commit(user);
                    if (result != null) {
                        if (result.getCode().equals(Const.HttpStatusCode.HttpStatus_200)) {
                            view.showMessage("密码修改成功,请重新登录");
                            Intent intent = new Intent(Const.Actions.ACTION_LOGOUT);
                            SamsApplication.get().sendBroadcast(intent);
                            view.back();
                        } else if (result.getCode().equals(Const.HttpStatusCode.HttpStatus_403)) {  //临时增加用403表示已经提交
                            view.showMessage(result.getMessage());
                        } else {
                            view.showMessage(result.getMessage());
                        }
                    } else {
                        view.showNetWorkErro();
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                    view.showMessage(e.getMessage());
                }
            }
        });


    }
}
