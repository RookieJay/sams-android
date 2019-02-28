package pers.zjc.sams.module.sign;


import com.zp.android.zlib.http.HttpClient;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import pers.zjc.sams.module.sign.contract.SignContract;
import pers.zjc.sams.module.sign.contract.SignListContract;
import pers.zjc.sams.service.ApiService;

@Module
public class SignListModule {

    private SignListContract.View mView;

    @Inject
    public SignListModule(SignListContract.View mView) {
        this.mView = mView;
    }

    @Provides
    SignListContract.View provideView() {
        return mView;
    }

    @Provides
    ApiService provideApiService(HttpClient client) {
        return client.create(ApiService.class);
    }

}
