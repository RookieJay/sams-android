package pers.zjc.sams.module.sign.model;

import com.zp.android.zlib.http.HttpParam;

import javax.inject.Inject;

import pers.zjc.sams.data.datawrapper.SignRecordsWrapper;
import pers.zjc.sams.data.entity.Result;
import pers.zjc.sams.module.sign.contract.SignStatContract;
import pers.zjc.sams.service.ApiService;

public class SignStatModel implements SignStatContract.Model {

    @Inject
    ApiService apiService;

    @Inject
    SignStatModel(){}

    @Override
    public Result<SignRecordsWrapper> getAllSign() {
        return apiService.signList("0", new HttpParam.Factory().create());
    }
}
