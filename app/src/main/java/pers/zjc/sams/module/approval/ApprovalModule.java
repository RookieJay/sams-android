package pers.zjc.sams.module.approval;


import com.zp.android.zlib.http.HttpClient;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import pers.zjc.sams.module.approval.contract.ApprovalContract;
import pers.zjc.sams.module.sign.contract.SignContract;
import pers.zjc.sams.service.ApiService;

@Module
public class ApprovalModule {

    private ApprovalContract.View mView;

    @Inject
    public ApprovalModule(ApprovalContract.View mView) {
        this.mView = mView;
    }

    @Provides
    ApprovalContract.View provideView() {
        return mView;
    }

    @Provides
    ApiService provideApiService(HttpClient client) {
        return client.create(ApiService.class);
    }

}
