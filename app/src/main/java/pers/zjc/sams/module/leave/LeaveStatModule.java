package pers.zjc.sams.module.leave;


import com.zp.android.zlib.http.HttpClient;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import pers.zjc.sams.module.leave.contract.LeaveStatContract;
import pers.zjc.sams.service.ApiService;

@Module
public class LeaveStatModule {

    private LeaveStatContract.View mView;

    @Inject
    public LeaveStatModule(LeaveStatContract.View mView) {
        this.mView = mView;
    }

    @Provides
    LeaveStatContract.View provideView() {
        return mView;
    }

    @Provides
    ApiService provideApiService(HttpClient client) {
        return client.create(ApiService.class);
    }

}
