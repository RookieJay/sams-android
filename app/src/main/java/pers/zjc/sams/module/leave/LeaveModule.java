package pers.zjc.sams.module.leave;


import com.zp.android.zlib.http.HttpClient;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import pers.zjc.sams.module.leave.contract.LeaveContract;
import pers.zjc.sams.service.ApiService;

@Module
public class LeaveModule {

    private LeaveContract.View mView;

    @Inject
    public LeaveModule(LeaveContract.View mView) {
        this.mView = mView;
    }

    @Provides
    LeaveContract.View provideView() {
        return mView;
    }

    @Provides
    ApiService provideApiService(HttpClient client) {
        return client.create(ApiService.class);
    }

}
