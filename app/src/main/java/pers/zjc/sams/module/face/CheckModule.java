package pers.zjc.sams.module.face;


import com.zp.android.zlib.http.HttpClient;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import pers.zjc.sams.module.attence.contract.AttenceStatContract;
import pers.zjc.sams.module.face.contract.CheckContract;
import pers.zjc.sams.service.ApiService;

@Module
public class CheckModule {

    private CheckContract.View mView;

    @Inject
    public CheckModule(CheckContract.View mView) {
        this.mView = mView;
    }

    @Provides
    CheckContract.View provideView() {
        return mView;
    }

    @Provides
    ApiService provideApiService(HttpClient client) {
        return client.create(ApiService.class);
    }

}
