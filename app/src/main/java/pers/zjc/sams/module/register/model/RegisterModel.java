package pers.zjc.sams.module.register.model;

import com.zp.android.zlib.http.HttpParam;

import javax.inject.Inject;

import pers.zjc.sams.data.entity.Result;
import pers.zjc.sams.module.register.contract.RegisterContract;
import pers.zjc.sams.service.ApiService;

public class RegisterModel implements RegisterContract.Model {

    @Inject
    ApiService apiService;

    @Inject
    RegisterModel() {}

    @Override
    public Result register(String userJson, String deviceJson, String json) {

        HttpParam.Factory factory = new HttpParam.Factory().add(null, json);
//                .add("user", userJson)
//                .add("device", deviceJson);

        return apiService.register(factory.create());
    }
}
