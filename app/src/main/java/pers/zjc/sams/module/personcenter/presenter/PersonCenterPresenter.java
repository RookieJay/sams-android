package pers.zjc.sams.module.personcenter.presenter;

import android.content.Intent;

import com.zp.android.zlib.common.MainThread;
import com.zp.android.zlib.utils.ActivityUtils;
import com.zp.android.zlib.utils.AppUtils;
import com.zp.android.zlib.utils.IntentUtils;

import java.util.concurrent.Executor;

import javax.inject.Inject;

import pers.zjc.sams.app.AppConfig;
import pers.zjc.sams.module.personcenter.contract.PersonCenterContract;
import pers.zjc.sams.module.personcenter.model.PersonCenterModel;

public class PersonCenterPresenter implements PersonCenterContract.Presenter {

    @Inject
    AppConfig appConfig;
    @Inject
    PersonCenterModel model;
    @Inject
    Executor mExecutor;
    @Inject
    MainThread mMainThread;
    private PersonCenterContract.View view;

    @Inject
    PersonCenterPresenter(PersonCenterContract.View view) {
        this.view = view;
    }

    @Override
    public void init() {
    }

    @Override
    public void exit() {
        appConfig.setLogin(false);
        Intent intent = IntentUtils.getLaunchAppIntent(AppUtils.getAppPackageName());
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("exit", true);
            ActivityUtils.startActivity(intent);
        }
    }

}
