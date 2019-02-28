package pers.zjc.sams.module.leave;


import com.zp.android.zlib.http.HttpClient;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import pers.zjc.sams.module.leave.contract.LeaveContract;
import pers.zjc.sams.module.leave.contract.LeaveListContract;
import pers.zjc.sams.service.ApiService;

@Module
public class LeaveListModule {

    private LeaveListContract.View mView;

    @Inject
    public LeaveListModule(LeaveListContract.View mView) {
        this.mView = mView;
    }

    @Provides
    LeaveListContract.View provideView() {
        return mView;
    }

    @Provides
    ApiService provideApiService(HttpClient client) {
        return client.create(ApiService.class);
    }

}
