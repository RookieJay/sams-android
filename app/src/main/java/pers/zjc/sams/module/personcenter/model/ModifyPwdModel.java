package pers.zjc.sams.module.personcenter.model;

import com.zp.android.zlib.http.HttpParam;

import javax.inject.Inject;

import pers.zjc.sams.data.entity.Result;
import pers.zjc.sams.data.entity.User;
import pers.zjc.sams.module.personcenter.contract.ModifyPwdContract;
import pers.zjc.sams.service.ApiService;

public class ModifyPwdModel implements ModifyPwdContract.Model {

    @Inject
    ApiService apiService;

    @Inject
    ModifyPwdModel() {
    }

    public Result commit(User user) {
        HttpParam.Factory factory = new HttpParam.Factory();
        factory.add("id", String.valueOf(user.getId()))
               .add("account", user.getAccount())
               .add("password", user.getPassword());
        return apiService.modifyPwd(factory.create());
    }
}
