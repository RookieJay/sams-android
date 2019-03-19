package pers.zjc.sams.module.user.model;

import javax.inject.Inject;

import pers.zjc.sams.data.datawrapper.StudentsWrapper;
import pers.zjc.sams.data.entity.Result;
import pers.zjc.sams.module.user.contract.UserManageContract;
import pers.zjc.sams.service.ApiService;

public class UserManageModel implements UserManageContract.Model {

    @Inject
    ApiService apiService;

    @Inject
    UserManageModel() {
    }


    public Result<StudentsWrapper> getStudents() {
        return apiService.getStudents();
    }
}
