package pers.zjc.sams.module.register.model;

import com.zp.android.zlib.http.HttpParam;

import javax.inject.Inject;

import pers.zjc.sams.data.entity.Result;
import pers.zjc.sams.data.entity.User;
import pers.zjc.sams.module.register.contract.RegisterContract;
import pers.zjc.sams.service.ApiService;

public class RegisterModel implements RegisterContract.Model {

    @Inject
    ApiService apiService;

    @Inject
    RegisterModel() {}

    @Override
    public Result register(User user, String deviceId) {
        HttpParam.Factory factory = new HttpParam.Factory()
                .add("deviceId", deviceId)
                .add("account", user.getAccount())
                .add("password", user.getPassword());
        return apiService.regiser(factory.create());
    }
}
