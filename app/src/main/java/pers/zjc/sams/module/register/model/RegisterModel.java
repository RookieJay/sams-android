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
                .add("account", user.getAccount())
                .add("password", user.getPassword())
                .add("role", String.valueOf(user.getRole()));
        return apiService.register(factory.create(), Integer.valueOf(deviceId));
    }
}
