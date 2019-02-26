package pers.zjc.sams.module.login.model;

import com.zp.android.zlib.http.HttpParam;

import javax.inject.Inject;

import pers.zjc.sams.data.datawrapper.UserWrapper;
import pers.zjc.sams.data.entity.Result;
import pers.zjc.sams.module.login.contract.LoginContract;
import pers.zjc.sams.service.ApiService;

public class LoginModel implements LoginContract.Model {


    @Inject
    ApiService apiService;

    @Inject
    LoginModel(){}

    @Override
    public Result<UserWrapper> login(String account, String pwd) {
        HttpParam.Factory factory = new HttpParam.Factory()
                                    .add("account", account)
                                    .add("password", pwd);
        return apiService.login(factory.create());
    }
}
