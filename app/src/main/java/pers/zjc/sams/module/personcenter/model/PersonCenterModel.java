package pers.zjc.sams.module.personcenter.model;

import com.zp.android.zlib.db.DaoManager;

import javax.inject.Inject;

import pers.zjc.sams.module.personcenter.contract.PersonCenterContract;

public class PersonCenterModel implements PersonCenterContract.Model {


    @Inject
    public PersonCenterModel(DaoManager dm) {

    }

}
