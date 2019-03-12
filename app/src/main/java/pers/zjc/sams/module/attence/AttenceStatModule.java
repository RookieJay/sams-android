package pers.zjc.sams.module.attence;


import com.zp.android.zlib.http.HttpClient;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import pers.zjc.sams.module.approval.contract.ApprovalContract;
import pers.zjc.sams.module.approval.contract.ApprovalStatContract;
import pers.zjc.sams.module.attence.contract.AttenceStatContract;
import pers.zjc.sams.service.ApiService;

@Module
public class AttenceStatModule {

    private AttenceStatContract.View mView;

    @Inject
    public AttenceStatModule(AttenceStatContract.View mView) {
        this.mView = mView;
    }

    @Provides
    AttenceStatContract.View provideView() {
        return mView;
    }

    @Provides
    ApiService provideApiService(HttpClient client) {
        return client.create(ApiService.class);
    }

}
