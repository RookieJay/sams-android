package pers.zjc.sams.module.sign;


import com.zp.android.zlib.http.HttpClient;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import pers.zjc.sams.module.sign.contract.SignContract;
import pers.zjc.sams.service.ApiService;

@Module
public class SignModule {

    private SignContract.View mView;

    @Inject
    public SignModule(SignContract.View mView) {
        this.mView = mView;
    }

    @Provides
    SignContract.View provideView() {
        return mView;
    }

    @Provides
    ApiService provideApiService(HttpClient client) {
        return client.create(ApiService.class);
    }

}
