package pers.zjc.sams.module.approval.model;

import com.zp.android.zlib.http.HttpParam;
import com.zp.android.zlib.utils.TimeUtils;

import javax.inject.Inject;

import pers.zjc.sams.data.datawrapper.AttenceRecordsWrapper;
import pers.zjc.sams.data.entity.Result;
import pers.zjc.sams.module.approval.contract.ApprovalStatContract;
import pers.zjc.sams.service.ApiService;

public class ApprovalStatModel implements ApprovalStatContract.Model {

    @Inject
    ApiService apiService;

    @Inject
    ApprovalStatModel() {
    }


}
