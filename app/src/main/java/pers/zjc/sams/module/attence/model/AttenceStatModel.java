package pers.zjc.sams.module.attence.model;

import com.zp.android.zlib.http.HttpParam;

import javax.inject.Inject;

import pers.zjc.sams.data.datawrapper.AttenceRecordsWrapper;
import pers.zjc.sams.data.entity.Result;
import pers.zjc.sams.module.attence.contract.AttenceStatContract;
import pers.zjc.sams.service.ApiService;

public class AttenceStatModel implements AttenceStatContract.Model {

    @Inject
    ApiService apiService;
    @Inject
    AttenceStatModel(ApiService apiService) {
        this.apiService = apiService;
    }



    public Result<AttenceRecordsWrapper> getAllRecords() {
        return apiService.getMultiCondRecord(new HttpParam.Factory().create());
    }
}
