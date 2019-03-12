package pers.zjc.sams.module.face.model;

import com.zp.android.zlib.http.HttpParam;

import javax.inject.Inject;

import pers.zjc.sams.data.datawrapper.SignRecordsWrapper;
import pers.zjc.sams.data.entity.Result;
import pers.zjc.sams.module.face.contract.CheckContract;
import pers.zjc.sams.service.ApiService;

public class CheckModel implements CheckContract.Model {

    @Inject
    ApiService apiService;

    @Inject
    public CheckModel() {
    }

    @Override
    public Result<SignRecordsWrapper> getAllSign() {
        return apiService.signList("0", new HttpParam.Factory().create());
    }
}
